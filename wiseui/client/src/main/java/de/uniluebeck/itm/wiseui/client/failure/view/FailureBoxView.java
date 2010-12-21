package de.uniluebeck.itm.wiseui.client.failure.view;

public interface FailureBoxView {

    void addMessage(String message);

    void addStacktrace(String stacktrace);

    void showBox();

    void hideBox();

    void setPresenter(Presenter presenter);

    public interface Presenter {

    }
}
