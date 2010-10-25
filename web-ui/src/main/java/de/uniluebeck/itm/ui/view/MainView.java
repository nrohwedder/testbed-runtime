/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                  *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote *
 *   products derived from this software without specific prior written permission.                                   *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/
package de.uniluebeck.itm.ui.view;

import de.uniluebeck.itm.ui.presenter.MainPresenter;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * @author Soenke Nommensen
 */
public class MainView extends Window implements MainPresenter.Display {

    private static final String APPLICATION_NAME = "WISEBED Experimentation Facility";
    private static final String TESTBED_SELECTION_TAB_LABEL = "Testbed Selection";
    private static final String RESERVATION_TAB_LABEL = "Reservation";
    private static final String EXPERIMENTATION_TAB_LABEL = "Experimentation";
    private static final String ADMINISTRATION_TAB_LABEL = "Administration";
    private final VerticalLayout screen;
    private final ToolbarView toolbarView;
    private final TabView tabView;
    private final TestbedSelectionView testbedSelectionView;
    private final ReservationView reservationView;
    private final WiseMlNativeView wiseMlNativeView;

    public MainView(AbstractComponent toolbar, AbstractComponent tabs,
            AbstractComponent testbedSelection, AbstractComponent reservation,
            AbstractComponent wiseMlNative) {
        super(APPLICATION_NAME);

        screen = new VerticalLayout();
        screen.setSizeFull();
        screen.setSpacing(true);
        screen.setMargin(true);
        screen.addStyleName(Reindeer.LAYOUT_BLUE);

        setContent(screen);

        /* Init sub-views */
        this.toolbarView = (ToolbarView) toolbar;
        this.tabView = (TabView) tabs;
        this.testbedSelectionView = (TestbedSelectionView) testbedSelection;
        this.reservationView = (ReservationView) reservation;
        this.wiseMlNativeView = (WiseMlNativeView) wiseMlNative;

        tabView.addTab(testbedSelectionView, TESTBED_SELECTION_TAB_LABEL, null);
        tabView.addTab(reservationView, RESERVATION_TAB_LABEL, null);
        tabView.addTab(new Label(EXPERIMENTATION_TAB_LABEL), EXPERIMENTATION_TAB_LABEL, null);
        tabView.addTab(new Label(ADMINISTRATION_TAB_LABEL), ADMINISTRATION_TAB_LABEL, null);
        tabView.addTab(wiseMlNativeView, "WiseML Native", null);

        screen.addComponent(toolbarView);
        screen.addComponent(tabView);
        screen.setExpandRatio(tabView, 1);
    }

    public Window asComponent() {
        return this;
    }

    public ToolbarView getToolbarView() {
        return toolbarView;
    }

    public TabView getTabView() {
        return tabView;
    }

    public TestbedSelectionView getTestbedSelectionView() {
        return testbedSelectionView;
    }

    public ReservationView getReservationView() {
        return reservationView;
    }

    public WiseMlNativeView getWiseMlNativeView() {
        return wiseMlNativeView;
    }
}
