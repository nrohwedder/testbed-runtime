/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote*
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
package de.uniluebeck.itm.ui.presenter;

import de.uniluebeck.itm.events.EventBus;
import de.uniluebeck.itm.model.TestbedConfiguration;
import de.uniluebeck.itm.ui.view.ReservationView;
import de.uniluebeck.itm.ui.presenter.ReservationPresenter.Display;


/**
 * @author Soenke Nommensen
 */
public class ReservationPresenter extends BasicPresenter<ReservationPresenter.Display> {

    private static String rsEndpointUrl = null;

    static {
        TestbedConfiguration testbedConfiguration = new TestbedConfiguration(
                "WISEBED UZL Tested",
                "http://testbedurl.eu",
                "WISEBED Testbed in Lübeck, Germany.",
                "http://wisebed.itm.uni-luebeck.de:8890/snaa?wsdl",
                "",
                "http://wisebed.itm.uni-luebeck.de:8888/sessions?wsdl",
                false);
        rsEndpointUrl = testbedConfiguration.getRsEndpointUrl();
    }

    public ReservationPresenter(ReservationPresenter.Display display, EventBus eventBus) {
        super(display, eventBus);
    }

    @Override
    protected void onBind() {
    }

    @Override
    protected void onUnbind() {
    }

    @Override
    protected void onRevealDisplay() {
    }

    public interface Display extends Presenter.Display {   
    }
}
