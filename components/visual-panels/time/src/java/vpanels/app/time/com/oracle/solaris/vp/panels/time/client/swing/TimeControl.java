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
 * Copyright (c) 2010, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.solaris.vp.panels.time.client.swing;

import com.oracle.solaris.vp.panel.common.action.*;
import com.oracle.solaris.vp.panel.swing.model.SimpleModelControl;
import com.oracle.solaris.vp.util.misc.finder.Finder;
import com.oracle.solaris.vp.util.swing.time.SimpleTimeModel;

public class TimeControl
    extends SimpleModelControl<TimeModel, TimePanelDescriptor, TimePanel> {

    //
    // Static data
    //

    public static final String ID = "time";
    private static final String NAME = Finder.getString("time.title");

    //
    // Instance data
    //

    private SimpleTimeModel model;

    //
    // Constructors
    //

    public TimeControl(TimePanelDescriptor descriptor, TimeModel timemodel,
	SimpleTimeModel model) {

	super(ID, NAME, descriptor, timemodel);
	setHelpMapID("timeanddate");
	this.model = model;
    }

    //
    // SwingControl methods
    //

    @Override
    protected TimePanel createComponent() {
	return new TimePanel(model);
    }

    @Override
    protected void save() throws ActionAbortedException, ActionFailedException,
	ActionUnauthorizedException {

	super.save();

	// Normally not necessary here, but since time is stored as an offset
	// from the local host, and we may be administering the local host, the
	// offset may no longer be accurate
	initComponentOnEventThread();
    }
}
