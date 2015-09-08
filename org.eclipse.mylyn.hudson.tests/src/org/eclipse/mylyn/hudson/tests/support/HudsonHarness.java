/*******************************************************************************
 * Copyright (c) 2011, 2013 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.hudson.tests.support;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.Callable;

import org.eclipse.mylyn.commons.sdk.util.CommonTestUtil.PrivilegeLevel;
import org.eclipse.mylyn.internal.hudson.core.client.HudsonException;
import org.eclipse.mylyn.internal.hudson.core.client.RestfulHudsonClient;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelBallColor;
import org.eclipse.mylyn.internal.hudson.model.HudsonModelJob;

/**
 * @author Steffen Pingel
 */
public class HudsonHarness {

	private static final String PLAN_DISABLED = "test-disabled";

	private static final String PLAN_FAILING = "test-failing";

	private static final String PLAN_PARAMETERIZED = "test-parameterized";

	private static final String PLAN_SUCCEEDING = "test-succeeding";

	private static final String PLAN_WHITESPACE = "test-white space";

	private static final String PLAN_GIT = "test-git";

	private RestfulHudsonClient client;

	private final HudsonFixture fixture;

	public HudsonHarness(HudsonFixture fixture) {
		this.fixture = fixture;
	}

	public RestfulHudsonClient connect() throws Exception {
		return connect(PrivilegeLevel.USER);
	}

	public RestfulHudsonClient connect(PrivilegeLevel level) throws Exception {
		client = HudsonFixture.connect(fixture.location(level));
		return client;
	}

	public RestfulHudsonClient privilegedClient() throws Exception {
		return HudsonFixture.connect(fixture.location(PrivilegeLevel.USER));
	}

	public void dispose() {
	}

	public HudsonFixture getFixture() {
		return fixture;
	}

	public HudsonModelJob getJob(String name) throws HudsonException {
		return getJob(client, name);
	}

	private HudsonModelJob getJob(RestfulHudsonClient client, String name) throws HudsonException {
		for (HudsonModelJob job : client.getJobs(null, null)) {
			if (job.getName().equals(name)) {
				return job;
			}
		}
		return null;
	}

	public String getPlanDisabled() {
		return PLAN_DISABLED;
	}

	public String getPlanFailing() {
		return PLAN_FAILING;
	}

	public String getPlanParameterized() {
		return PLAN_PARAMETERIZED;
	}

	public String getPlanSucceeding() {
		return PLAN_SUCCEEDING;
	}

	public String getPlanWhitespace() {
		return PLAN_WHITESPACE;
	}

	public String getPlanGit() {
		return PLAN_GIT;
	}

	public HudsonModelJob ensureHasRun(final String plan) throws Exception {
		final RestfulHudsonClient privilegedClient = privilegedClient();
		HudsonModelJob job = getJob(privilegedClient, plan);
		if (job.getLastCompletedBuild() == null) {
			privilegedClient.runBuild(job, null, null);
			job = HudsonTestUtil.poll(new Callable<HudsonModelJob>() {
				public HudsonModelJob call() throws Exception {
					HudsonModelJob job = getJob(privilegedClient, plan);
					assertNotNull(job.getLastCompletedBuild());
					return job;
				}
			});
		}
		return job;
	}

	public HudsonModelBallColor getSuccessColor() {
		if (getFixture().isHudson() && getFixture().getVersion().compareTo("3.0.1") >= 0) {
			return HudsonModelBallColor.GREEN;
		} else {
			return HudsonModelBallColor.BLUE;
		}
	}

	public HudsonModelBallColor getSuccessAnimeColor() {
		if (getFixture().isHudson() && getFixture().getVersion().compareTo("3.0.1") >= 0) {
			return HudsonModelBallColor.GREEN_ANIME;
		} else {
			return HudsonModelBallColor.BLUE_ANIME;
		}
	}

}
