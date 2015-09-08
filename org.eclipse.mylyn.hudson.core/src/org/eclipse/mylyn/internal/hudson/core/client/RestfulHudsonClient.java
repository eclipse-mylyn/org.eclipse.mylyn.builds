/*******************************************************************************
 * Copyright (c) 2010, 2014 Markus Knittig and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Markus Knittig - initial API and implementation
 *     Tasktop Technologies - improvements
 *     Eike Stepper - improvements for bug 323759
 *     Benjamin Muskalla - 323920: [build] config retrival fails for jobs with whitespaces
 *******************************************************************************/

package org.eclipse.mylyn.internal.hudson.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.mylyn.builds.core.spi.AbstractConfigurationCache;
import org.eclipse.mylyn.commons.core.operations.IOperationMonitor;
import org.eclipse.mylyn.commons.repositories.core.RepositoryLocation;
import org.eclipse.mylyn.commons.repositories.http.core.CommonHttpClient;
import org.eclipse.mylyn.commons.repositories.http.core.CommonHttpResponse;
import org.eclipse.mylyn.commons.repositories.http.core.HttpUtil;
import org.eclipse.mylyn.internal.hudson.core.client.HudsonServerInfo.Type;
import org.eclipse.mylyn.internal.hudson.model.HudsonMavenReportersSurefireAggregatedReport;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelBuild;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelHudson;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelJob;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelProject;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelRun;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelRunArtifact;
import org.eclipse.mylyn.internal.hudson.model.HudsonTasksJunitTestResult;
import org.eclipse.mylyn.internal.hudson.model.HudsonTasksTestAggregatedTestResultActionChildReport;
import org.eclipse.osgi.util.NLS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Represents the Hudson repository that is accessed through REST.
 * 
 * @author Markus Knittig
 * @author Steffen Pingel
 * @author Eike Stepper
 */
public class RestfulHudsonClient {

	public enum BuildId {
		LAST(-1), LAST_FAILED(-5), LAST_STABLE(-2), LAST_SUCCESSFUL(-3), LAST_UNSTABLE(-4);

		private HudsonModelBuild build;

		BuildId(int id) {
			this.build = new HudsonModelBuild();
			this.build.setNumber(id);
		}

		public HudsonModelBuild getBuild() {
			return build;
		}

	};

	private static final String URL_API = "/api/xml"; //$NON-NLS-1$

	private AbstractConfigurationCache<HudsonConfiguration> cache;

	private final CommonHttpClient client;

	private volatile HudsonServerInfo info;

	public RestfulHudsonClient(RepositoryLocation location, HudsonConfigurationCache cache) {
		// FIXME register listener to location to handle credential changes
		client = new CommonHttpClient(location);
		setCache(cache);
	}

	public List<HudsonModelRun> getBuilds(final HudsonModelJob job, final IOperationMonitor monitor)
			throws HudsonException {
		return new HudsonOperation<List<HudsonModelRun>>(client) {
			@Override
			public List<HudsonModelRun> execute() throws IOException, HudsonException, JAXBException {
				String url = HudsonUrl.create(getJobUrl(job))
						.depth(1)
						.tree("builds[number,url,building,result,duration,timestamp,actions[causes[shortDescription],failCount,totalCount,skipCount]]") //$NON-NLS-1$
						.toUrl();
				HttpRequestBase request = createGetRequest(url);
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected List<HudsonModelRun> doProcess(CommonHttpResponse response, IOperationMonitor monitor)
					throws IOException, HudsonException, JAXBException {
				InputStream in = response.getResponseEntityAsStream();
				HudsonModelProject project = unmarshal(parse(in, response.getRequestPath()), HudsonModelProject.class);
				return project.getBuild();
			}
		}.run();
	}

	public HudsonModelBuild getBuild(final HudsonModelJob job, final HudsonModelRun build,
			final IOperationMonitor monitor) throws HudsonException {
		return new HudsonOperation<HudsonModelBuild>(client) {
			@Override
			public HudsonModelBuild execute() throws IOException, HudsonException, JAXBException {
				String url = getBuildUrl(job, build) + URL_API;
				HttpRequestBase request = createGetRequest(url);
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected HudsonModelBuild doProcess(CommonHttpResponse response, IOperationMonitor monitor)
					throws IOException, HudsonException, JAXBException {
				InputStream in = response.getResponseEntityAsStream();
				HudsonModelBuild hudsonBuild = unmarshal(parse(in, response.getRequestPath()), HudsonModelBuild.class);
				return hudsonBuild;
			}
		}.run();
	}

	public HudsonTestReport getTestReport(final HudsonModelJob job, final HudsonModelRun build,
			final IOperationMonitor monitor) throws HudsonException {
		return new HudsonOperation<HudsonTestReport>(client) {
			@Override
			public HudsonTestReport execute() throws IOException, HudsonException, JAXBException {
				//				String url = HudsonUrl.create(getBuildUrl(job, build) + "/testReport" + URL_API).exclude(
				//						"/testResult/suite/case/stdout").exclude("/testResult/suite/case/stderr").toUrl();
				// need to scope retrieved data due to http://issues.hudson-ci.org/browse/HUDSON-7399
				String resultTree = "duration,failCount,passCount,skipCount,suites[cases[className,duration,errorDetails,errorStackTrace,failedSince,name,skipped,status],duration,name,stderr,stdout]"; //$NON-NLS-1$
				String aggregatedTree = "failCount,skipCount,totalCount,childReports[child[number,url],result[" //$NON-NLS-1$
						+ resultTree + "]]"; //$NON-NLS-1$
				String url = HudsonUrl.create(getBuildUrl(job, build) + "/testReport") //$NON-NLS-1$
						.tree(resultTree + "," + aggregatedTree) //$NON-NLS-1$
						.toUrl();
				HttpRequestBase request = createGetRequest(url);
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected HudsonTestReport doProcess(CommonHttpResponse response, IOperationMonitor monitor)
					throws IOException, HudsonException, JAXBException {
				InputStream in = response.getResponseEntityAsStream();
				Element element = parse(in, response.getRequestPath());
				if ("surefireAggregatedReport".equals(element.getNodeName())) { //$NON-NLS-1$
					HudsonMavenReportersSurefireAggregatedReport report = unmarshal(element,
							HudsonMavenReportersSurefireAggregatedReport.class);
					// unmarshal nested test results
					for (HudsonTasksTestAggregatedTestResultActionChildReport child : report.getChildReport()) {
						child.setResult(RestfulHudsonClient.unmarshal((Node) child.getResult(),
								HudsonTasksJunitTestResult.class));
					}
					return new HudsonTestReport(report);
				}
				return new HudsonTestReport(unmarshal(element, HudsonTasksJunitTestResult.class));
			}
		}.run();
	}

	protected String getBuildUrl(final HudsonModelJob job, final HudsonModelRun build) throws HudsonException {
		if (build.getNumber() == -1) {
			return getJobUrl(job) + "/lastBuild"; //$NON-NLS-1$
		} else {
			return getJobUrl(job) + "/" + build.getNumber(); //$NON-NLS-1$
		}
	}

	public String getArtifactUrl(final HudsonModelJob job, final HudsonModelRun build, HudsonModelRunArtifact artifact)
			throws HudsonException {
		return getBuildUrl(job, build) + "/artifact/" + artifact.getRelativePath(); //$NON-NLS-1$
	}

	public AbstractConfigurationCache<HudsonConfiguration> getCache() {
		return cache;
	}

	public HudsonConfiguration getConfiguration() {
		return getCache().getConfiguration(client.getLocation().getUrl());
	}

	public Reader getConsole(final HudsonModelJob job, final HudsonModelBuild hudsonBuild,
			final IOperationMonitor monitor) throws HudsonException {
		return new HudsonOperation<Reader>(client) {
			@Override
			public Reader execute() throws IOException, HudsonException, JAXBException {
				HttpRequestBase request = createGetRequest(getBuildUrl(job, hudsonBuild) + "/consoleText"); //$NON-NLS-1$
				CommonHttpResponse response = execute(request, monitor);
				return process(response, monitor);
			}

			@Override
			protected Reader doProcess(CommonHttpResponse response, IOperationMonitor monitor) throws IOException,
					HudsonException {
				InputStream in = response.getResponseEntityAsStream();
				String charSet = response.getResponseCharSet();
				if (charSet == null) {
					charSet = "UTF-8"; //$NON-NLS-1$
				}
				return new InputStreamReader(in, charSet);
			}
		}.run();
	}

	private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	public List<HudsonModelJob> getJobs(final List<String> ids, final IOperationMonitor monitor) throws HudsonException {
		if (ids != null && ids.isEmpty()) {
			return Collections.emptyList();
		}

		return new HudsonOperation<List<HudsonModelJob>>(client) {
			@Override
			public List<HudsonModelJob> execute() throws IOException, HudsonException, JAXBException {
				String url = HudsonUrl.create(baseUrl()).depth(1).include("/hudson/job") //$NON-NLS-1$
						.match("name", ids) //$NON-NLS-1$
						.exclude("/hudson/job/build") //$NON-NLS-1$
						.toUrl();
				HttpRequestBase request = createGetRequest(url);
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected List<HudsonModelJob> doProcess(CommonHttpResponse response, IOperationMonitor monitor)
					throws IOException, HudsonException, JAXBException {
				InputStream in = response.getResponseEntityAsStream();

				Map<String, String> jobNameById = new HashMap<String, String>();

				HudsonModelHudson hudson = unmarshal(parse(in, response.getRequestPath()), HudsonModelHudson.class);

				List<HudsonModelJob> buildPlans = new ArrayList<HudsonModelJob>();
				List<Object> jobsNodes = hudson.getJob();
				for (Object jobNode : jobsNodes) {
					Node node = (Node) jobNode;
					HudsonModelJob job = unmarshal(node, HudsonModelJob.class);
					if (job.getDisplayName() != null && job.getDisplayName().length() > 0) {
						jobNameById.put(job.getName(), job.getDisplayName());
					} else {
						jobNameById.put(job.getName(), job.getName());
					}
					buildPlans.add(job);
				}

				if (ids == null) {
					// update list of known jobs if all jobs were retrieved
					HudsonConfiguration configuration = new HudsonConfiguration();
					configuration.jobNameById = jobNameById;
					setConfiguration(configuration);
				}

				return buildPlans;
			}
		}.run();
	}

	String getJobUrl(HudsonModelJob job) throws HudsonException {
		String encodedJobname = ""; //$NON-NLS-1$
		try {
			encodedJobname = new URI(null, job.getName(), null).toASCIIString();
		} catch (URISyntaxException e) {
			throw new HudsonException(e);
		}
		String url = client.getLocation().getUrl();
		if (!url.endsWith("/")) { //$NON-NLS-1$
			url += "/"; //$NON-NLS-1$
		}
		return url + "job/" + encodedJobname; //$NON-NLS-1$
	}

	Element parse(InputStream in, String url) throws HudsonException {
		try {
			return getDocumentBuilder().parse(in).getDocumentElement();
		} catch (OperationCanceledException e) {
			throw e;
		} catch (SAXException e) {
			throw new HudsonException(NLS.bind("Failed to parse response from {0}", url), e); //$NON-NLS-1$
		} catch (Exception e) {
			throw new HudsonException(NLS.bind("Failed to parse response from {0}", url), e); //$NON-NLS-1$
		}
	}

	public Document getJobConfig(final HudsonModelJob job, final IOperationMonitor monitor) throws HudsonException {
		return new HudsonOperation<Document>(client) {
			@Override
			public Document execute() throws IOException, HudsonException, JAXBException {
				HttpRequestBase request = createGetRequest(getJobUrl(job) + "/config.xml"); //$NON-NLS-1$
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected Document doProcess(CommonHttpResponse response, IOperationMonitor monitor) throws IOException,
					HudsonException, JAXBException {
				InputStream in = response.getResponseEntityAsStream();
				try {
					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					return builder.parse(in); // TODO Enhance progress monitoring
				} catch (ParserConfigurationException e) {
					throw new HudsonException(e);
				} catch (SAXException e) {
					throw new HudsonException(e);
				}
			}
		}.run();
	}

	public void runBuild(final HudsonModelJob job, final Map<String, String> parameters, final IOperationMonitor monitor)
			throws HudsonException {
		new HudsonOperation<Object>(client) {
			@Override
			public Object execute() throws IOException, HudsonException, JAXBException {
				HttpPost request = createPostRequest(getJobUrl(job) + "/build"); //$NON-NLS-1$
				if (parameters != null) {
					HudsonRunBuildForm form = new HudsonRunBuildForm();
					for (Entry<String, String> entry : parameters.entrySet()) {
						form.add(entry.getKey(), entry.getValue());
					}
					request.setEntity(form.createEntity());
				}

				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected void doValidate(CommonHttpResponse response, IOperationMonitor monitor) throws IOException,
					HudsonException {

				int statusCode = response.getStatusCode();
				if (statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_MOVED_TEMPORARILY) {
					throw new HudsonException(NLS.bind("Unexpected response from Hudson server for ''{0}'': {1}", //$NON-NLS-1$
							response.getRequestPath(), HttpUtil.getStatusText(statusCode)));
				}
			};
		}.run();
	}

	public void setCache(AbstractConfigurationCache<HudsonConfiguration> cache) {
		Assert.isNotNull(cache);
		this.cache = cache;
	}

	protected void setConfiguration(HudsonConfiguration configuration) {
		getCache().setConfiguration(client.getLocation().getUrl(), configuration);
	}

	public static <T> T unmarshal(Node node, Class<T> clazz) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(clazz);

		Unmarshaller unmarshaller = ctx.createUnmarshaller();

		JAXBElement<T> hudsonElement = unmarshaller.unmarshal(node, clazz);
		return hudsonElement.getValue();
	}

	public HudsonServerInfo validate(final IOperationMonitor monitor) throws HudsonException {
		info = new HudsonOperation<HudsonServerInfo>(client) {
			@Override
			public HudsonServerInfo execute() throws IOException, HudsonException, JAXBException {
				// XXX should use createHeadRequest() which is broken on Jenkins 1.459, see bug 376468
				HttpRequestBase request = createGetRequest(baseUrl());
				CommonHttpResponse response = execute(request, monitor);
				return processAndRelease(response, monitor);
			}

			@Override
			protected HudsonServerInfo doProcess(CommonHttpResponse response, IOperationMonitor monitor)
					throws IOException, HudsonException, JAXBException {
				Header header = response.getResponse().getFirstHeader("X-Jenkins"); //$NON-NLS-1$
				Type type;
				if (header == null) {
					type = Type.HUDSON;
					header = response.getResponse().getFirstHeader("X-Hudson"); //$NON-NLS-1$
					if (header == null) {
						throw new HudsonException(NLS.bind("{0} does not appear to be a Hudson or Jenkins instance", //$NON-NLS-1$
								baseUrl()));
					}
				} else {
					type = Type.JENKINS;
				}
				HudsonServerInfo info = new HudsonServerInfo(type, header.getValue());
				return info;
			}
		}.run();
		return info;
	}

	public RepositoryLocation getLocation() {
		return client.getLocation();
	}

	public HudsonServerInfo getInfo() {
		return info;
	}

	public HudsonServerInfo getInfo(final IOperationMonitor monitor) throws HudsonException {
		HudsonServerInfo info = this.info;
		if (info != null) {
			return info;
		}
		return validate(monitor);
	}

	public void reset() {
		client.getHttpClient().getCookieStore().clear();
	}

}