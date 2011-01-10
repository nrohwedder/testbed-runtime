package de.uniluebeck.itm.wiseui.client.testbedselection.gin;

import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.failure.presenter.FailureBoxPresenter;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxView;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionActivity;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.TestbedSelectionPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;

public interface TestbedSelectionGinjector extends Ginjector {

    TestbedSelectionActivity getTestbedSelectionActivity();
	
    ConfigurationPresenter getConfigurationPresenter();

    TestbedSelectionPresenter getTestbedSelectionPresenter();

    DetailPresenter getDetailPresenter();

    NetworkPresenter getNetworkPresenter();

    LoginDialogPresenter getLoginDialogPresenter();

    FailureBoxPresenter getFailureBoxPresenter();

    TestbedSelectionView getTestbedSelectionView();
    
    ConfigurationView getConfigurationView();

    DetailView getDetailView();

    NetworkView getNetworkView();

    LoginDialogView getLoginDialogView();

    FailureBoxView getFailureBoxView();
}
