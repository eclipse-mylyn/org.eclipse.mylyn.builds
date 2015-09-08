/*******************************************************************************
 * Copyright (c) 2010, 2014 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.builds.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.mylyn.builds.tests.core.BuildModelManagerTest;
import org.eclipse.mylyn.builds.tests.operations.RefreshOperationTest;
import org.eclipse.mylyn.builds.tests.ui.BuildsViewTest;
import org.eclipse.mylyn.builds.tests.util.BuildsUrlHandlerTest;
import org.eclipse.mylyn.builds.tests.util.JUnitResultGeneratorTest;

/**
 * @author Steffen Pingel
 */
public class AllBuildsTests {

	public static Test suite() {
		return suite(false);
	}

	public static Test suite(boolean defaultOnly) {
		TestSuite suite = new TestSuite(AllBuildsTests.class.getName());
		suite.addTestSuite(BuildModelManagerTest.class);
		suite.addTestSuite(JUnitResultGeneratorTest.class);
		suite.addTestSuite(RefreshOperationTest.class);
		suite.addTestSuite(BuildsUrlHandlerTest.class);
		suite.addTestSuite(BuildsViewTest.class);
		return suite;
	}

}
