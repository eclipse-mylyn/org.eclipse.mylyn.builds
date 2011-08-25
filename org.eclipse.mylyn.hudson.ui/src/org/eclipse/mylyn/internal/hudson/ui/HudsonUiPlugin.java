/*******************************************************************************
 * Copyright (c) 2011 Tasktop Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.hudson.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Steffen Pingel
 */
public class HudsonUiPlugin extends AbstractUIPlugin {

	public HudsonUiPlugin() {
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (HudsonDiscovery.getInstance() != null) {
			HudsonDiscovery.getInstance().stop();
		}
		super.stop(context);
	}

}
