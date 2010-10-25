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
package de.uniluebeck.itm;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Window;
import de.uniluebeck.itm.common.UiUtil;
import de.uniluebeck.itm.events.DefaultEventBus;
import de.uniluebeck.itm.events.EventBus;
import de.uniluebeck.itm.ui.presenter.Presenter;
import de.uniluebeck.itm.ui.presenter.ReservationPresenter;
import de.uniluebeck.itm.ui.presenter.TabPresenter;
import de.uniluebeck.itm.ui.presenter.TestbedSelectionPresenter;
import de.uniluebeck.itm.ui.presenter.ToolbarPresenter;
import de.uniluebeck.itm.ui.presenter.MainPresenter;
import de.uniluebeck.itm.ui.presenter.WiseMlNativePresenter;
import de.uniluebeck.itm.ui.view.MainView;
import de.uniluebeck.itm.ui.view.ReservationView;
import de.uniluebeck.itm.ui.view.TabView;
import de.uniluebeck.itm.ui.view.TestbedSelectionView;
import de.uniluebeck.itm.ui.view.ToolbarView;
import de.uniluebeck.itm.ui.view.WiseMlNativeView;
import javax.servlet.http.HttpSession;

/**
 * @author Soenke Nommensen
 */
public class WebUiApp extends Application {

    /*
     * The one and only event-bus per application instance.
     */
    private final EventBus eventBus = new DefaultEventBus();

    /*
     * Toolbar
     */
    private final ToolbarPresenter.Display toolbarView = new ToolbarView();
    private final Presenter toolbarPresenter = new ToolbarPresenter(toolbarView, eventBus);

    /*
     * Tabs
     */
    private final TabPresenter.Display tabView = new TabView();
    private final Presenter tabPresenter = new TabPresenter(tabView, eventBus);

    /*
     * Testbed Selection tab
     */
    private final TestbedSelectionPresenter.Display testbedSelectionView = new TestbedSelectionView();
    private final Presenter testbedSelectionPresenter = new TestbedSelectionPresenter(testbedSelectionView, eventBus);

    /*
     * Reservation tab
     */
    private final ReservationPresenter.Display reservationView = new ReservationView();
    private final ReservationPresenter reservationPresenter = new ReservationPresenter(reservationView, eventBus);

    /*
     * WiseML Native tab
     */
    private final WiseMlNativePresenter.Display wiseMlNativeView = new WiseMlNativeView();
    private final Presenter wiseMlNativePresenter = new WiseMlNativePresenter(wiseMlNativeView, eventBus);

    /*
     * Main view
     */
    private final MainPresenter.Display mainView = new MainView(
            toolbarView.asComponent(),
            tabView.asComponent(),
            testbedSelectionView.asComponent(),
            reservationView.asComponent(),
            wiseMlNativeView.asComponent());
    private final MainPresenter mainPresenter = new MainPresenter(mainView, eventBus);

    @Override
    public void init() {
        toolbarPresenter.bind();
        tabPresenter.bind();
        testbedSelectionPresenter.bind();
        reservationPresenter.bind();
        wiseMlNativePresenter.bind();
        mainPresenter.bind();

        setMainWindow((Window) mainPresenter.getDisplay().asComponent());
        UiUtil.setMainWindow(getMainWindow());

//        HttpSession httpSession = ((WebApplicationContext) getContext()).getHttpSession();
    }
}
