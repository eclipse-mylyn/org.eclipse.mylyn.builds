/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.hudson.tests.client;

import junit.framework.TestCase;

import org.eclipse.mylyn.commons.core.operations.OperationUtil;
import org.eclipse.mylyn.commons.repositories.core.RepositoryLocation;
import org.eclipse.mylyn.hudson.tests.support.HudsonFixture;
import org.eclipse.mylyn.internal.hudson.core.client.HudsonException;
import org.eclipse.mylyn.internal.hudson.core.client.RestfulHudsonClient;

/**
 * @author Steffen Pingel
 */
public class HudsonValidationTest extends TestCase {

	public void testValidateNonExistantUrl() throws Exception {
		// invalid url
		RepositoryLocation location = new RepositoryLocation();
		location.setUrl("http://non.existant/repository");
		RestfulHudsonClient client = HudsonFixture.connect(location);
		try {
			client.validate(OperationUtil.convert(null));
			fail("Expected HudsonException");
		} catch (HudsonException e) {
		}
	}

	public void testValidateNonHudsonUrl() throws Exception {
		// non Hudson url
		RepositoryLocation location = new RepositoryLocation();
		location.setUrl("http://eclipse.org/mylyn");
		RestfulHudsonClient client = HudsonFixture.connect(location);
		try {
			client.validate(OperationUtil.convert(null));
			fail("Expected HudsonException");
		} catch (HudsonException e) {
		}
	}

}
