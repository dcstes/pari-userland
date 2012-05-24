/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at usr/src/OPENSOLARIS.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.solaris.vp.panel.swing.control;

import java.awt.Window;
import com.oracle.solaris.vp.panel.common.control.*;
import com.oracle.solaris.vp.util.misc.finder.Finder;
import com.oracle.solaris.vp.util.swing.GUIUtil;

/**
 * The {@code SwingNavigationErrorHandler} class graphically displays an
 * appropriate message for navigation errors.
 */
public class SwingNavigationErrorHandler implements NavigationListener {
    //
    // NavigationListener methods
    //

    @Override
    public void navigationStarted(NavigationStartEvent e) {
    }

    @Override
    public void navigationStopped(NavigationStopEvent event) {
	NavigationException exception = event.getNavigationException();
	if (exception != null) {
	    try {
		throw exception;

	    } catch (NavigationAbortedException e) {
		// Not an error requiring user notification

	    } catch (NavigationFailedException e) {
		showError(event, e.getMessage());

	    } catch (InvalidAddressException e) {
		showError(event, Finder.getString(
		    "navigation.error.address",
		    e.getInvalidPathComponent().getId()));

	    } catch (MissingParameterException e) {
		showError(event, Finder.getString(
		    "navigation.error.parameter.missing",
		    e.getControlId(), e.getParameter()));

	    } catch (InvalidParameterException e) {
		showError(event, Finder.getString(
		    "navigation.error.parameter.invalid",
		    e.getControlId(), e.getParameter(), e.getValue()));

	    } catch (EmptyNavigationStackException e) {
		showError(event, Finder.getString(
		    "navigation.error.emptystack"));

	    } catch (RootNavigableNotControlException e) {
		showError(event, Finder.getString(
		    "navigation.error.rootnotcontrol",
		    e.getNavigable().getId()));

	    } catch (NavigationException e) {
		// All NavigationExceptions should have been handled above
		assert false;
	    }
	}
    }

    //
    // Private methods
    //

    private void showError(NavigationStopEvent event, final String message) {
	final Window window = SwingNavigator.getLastWindow(event.getSource());
	GUIUtil.invokeAndWait(
	    new Runnable() {
		@Override
		public void run() {
		    GUIUtil.showError(window, null, message);
		}
	    });
    }
}
