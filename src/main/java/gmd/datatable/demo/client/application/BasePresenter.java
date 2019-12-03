package gmd.datatable.demo.client.application;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import gmd.datatable.demo.client.events.PageRevealEvent;
import gmd.datatable.demo.client.widget.HeaderTitle;
import gwt.material.design.client.base.MaterialWidget;

public class BasePresenter<V extends View, Proxy_ extends Proxy<?>> extends Presenter<V, Proxy_> {

    protected HeaderTitle headerTitle;

    public BasePresenter(EventBus eventBus, V view, Proxy_ proxy, GwtEvent.Type<RevealContentHandler<?>> slot) {
        super(eventBus, view, proxy, slot);
    }

    @Override
    protected void onBind() {
        super.onBind();

        if (asWidget() instanceof MaterialWidget) {
            headerTitle = new HeaderTitle();
            ((MaterialWidget) asWidget()).insert(headerTitle, 0);
        }
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        headerTitle.setVisible(false);
        PageRevealEvent.fire(this, getView());
        RootPanel.get().getElement().setId(getView().getClass().getSimpleName().toLowerCase());
    }

    public void setHeaderTitle(String title, String description, String link) {
        headerTitle.setDetails(title, description, link);
        headerTitle.setVisible(true);
    }
}
