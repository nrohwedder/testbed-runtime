package de.uniluebeck.itm.wiseui.client.testbedselection.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionActivity;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionViewImpl;

public class TestbedSelectionModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TestbedSelectionView.class).to(TestbedSelectionViewImpl.class).in(Singleton.class);
        bind(ConfigurationView.class).to(ConfigurationViewImpl.class).in(Singleton.class);
        bind(DetailView.class).to(DetailViewImpl.class).in(Singleton.class);
        bind(NetworkView.class).to(NetworkViewImpl.class).in(Singleton.class);
        bind(LoginDialogView.class).to(LoginDialogViewImpl.class).in(Singleton.class);
        
        bind(ConfigurationPresenter.class);
        bind(NetworkPresenter.class);
        bind(DetailPresenter.class);
        bind(LoginDialogPresenter.class);
        
        bind(TestbedSelectionActivity.class);
	}

}
