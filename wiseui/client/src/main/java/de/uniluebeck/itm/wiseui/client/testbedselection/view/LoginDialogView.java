package de.uniluebeck.itm.wiseui.client.testbedselection.view;

import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;

public interface LoginDialogView {

    HasText getUsernameText();

    HasText getPasswordText();

    HasEnabled getUsernameEnabled();

    HasEnabled getPasswordEnabled();

    void setPresenter(Presenter presenter);

    void show(String title);

    void hide();

    void addError(String error);

    void clearErrors();

    public interface Presenter {

        void setPlace(TestbedSelectionPlace place);

        void submit();

        void cancel();
    }
}
