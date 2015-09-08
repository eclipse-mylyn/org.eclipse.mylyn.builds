/*******************************************************************************
 * Copyright (c) 2010, 2011 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.builds.ui.view;

import org.eclipse.jface.action.Action;
import org.eclipse.mylyn.builds.ui.BuildsUiConstants;
import org.eclipse.mylyn.commons.repositories.ui.RepositoryUi;
import org.eclipse.mylyn.tasks.ui.TasksUiImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author Steffen Pingel
 */
public class NewBuildServerAction extends Action {

	public NewBuildServerAction() {
		setImageDescriptor(TasksUiImages.REPOSITORY_NEW);
		setToolTipText("Add Build Server Location");
		setText("Add Build Server...");
	}

	@Override
	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		RepositoryUi.openNewRepositoryDialog(window, BuildsUiConstants.ID_REPOSITORY_CATEGORY_BUILDS);
	}

}
