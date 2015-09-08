/*******************************************************************************
 * Copyright (c) 2010, 2013 Markus Knittig and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Markus Knittig - initial API and implementation
 *     Tasktop Technologies - improvements
 *******************************************************************************/

package org.eclipse.mylyn.internal.builds.ui.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.builds.core.IBuild;
import org.eclipse.mylyn.builds.core.IBuildPlan;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Markus Knittig
 * @author Steffen Pingel
 */
public class BuildEditorInput implements IEditorInput {
	public static enum BuildInfo {
		PARTIAL, COMPLETE, ERROR;
	}

	private BuildInfo buildInfo = BuildInfo.COMPLETE;

	private IBuildPlan plan;

	private IBuild build;

	public BuildEditorInput(IBuildPlan plan) {
		Assert.isNotNull(plan);
		this.plan = plan;
		this.build = null;
	}

	public BuildEditorInput(IBuild build) {
		Assert.isNotNull(build);
		Assert.isNotNull(build.getPlan());
		this.plan = build.getPlan();
		this.build = build;
	}

	public BuildEditorInput(IBuildPlan plan, boolean partial) {
		this(plan);
		if (partial) {
			this.buildInfo = BuildInfo.PARTIAL;
		}
	}

	public BuildEditorInput(IBuild build, boolean partial) {
		this(build);
		if (partial) {
			this.buildInfo = BuildInfo.PARTIAL;
		}
	}

	public IBuild getBuild() {
		return build;
	}

	public IBuildPlan getPlan() {
		return plan;
	}

	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter == IEditorInput.class) {
			return this;
		}
		return null;
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		if (build != null) {
			return NLS.bind("{0}#{1}", plan.getLabel(), build.getLabel());
		} else {
			return plan.getLabel();
		}
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "";
	}

	protected BuildInfo getBuildInfo() {
		return buildInfo;
	}

	public void updateBuildInfo(IBuild build, BuildInfo buildInfo) {
		Assert.isTrue(getBuildInfo() != BuildInfo.COMPLETE);
		this.build = build;
		this.plan = build.getPlan();
		this.buildInfo = buildInfo;
	}

}
