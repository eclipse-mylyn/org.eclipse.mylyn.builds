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
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.mylyn.commons.workbench.WorkbenchUtil;
import org.eclipse.mylyn.internal.builds.ui.BuildsUiInternal;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * @author Steffen Pingel
 */
public class OpenBuildsPreferencesAction extends Action {

	public OpenBuildsPreferencesAction() {
		setText("Preferences...");
		setToolTipText("Build Preferences");
	}

	@Override
	public void run() {
		PreferenceDialog dlg = PreferencesUtil.createPreferenceDialogOn(WorkbenchUtil.getShell(),
				BuildsUiInternal.ID_PREFERENCE_PAGE_BUILDS,
				new String[] { BuildsUiInternal.ID_PREFERENCE_PAGE_BUILDS }, null);
		dlg.open();
	}

}
