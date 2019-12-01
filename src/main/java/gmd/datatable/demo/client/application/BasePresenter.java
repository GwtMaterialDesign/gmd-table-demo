package gmd.datatable.demo.client.application;

import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import gmd.datatable.demo.client.events.PageRevealEvent;
import gwt.material.design.client.ui.MaterialToast;

public class BasePresenter<V extends View, Proxy_ extends Proxy<?>> extends Presenter<V, Proxy_>  {

    public BasePresenter(boolean autoBind, EventBus eventBus, V view, Proxy_ proxy) {
        super(autoBind, eventBus, view, proxy);
    }

    public BasePresenter(EventBus eventBus, V view, Proxy_ proxy) {
        super(eventBus, view, proxy);
    }

    public BasePresenter(EventBus eventBus, V view, Proxy_ proxy, RevealType revealType) {
        super(eventBus, view, proxy, revealType);
    }

    public BasePresenter(EventBus eventBus, V view, Proxy_ proxy, GwtEvent.Type<RevealContentHandler<?>> slot) {
        super(eventBus, view, proxy, slot);
    }

    public BasePresenter(EventBus eventBus, V view, Proxy_ proxy, RevealType revealType, GwtEvent.Type<RevealContentHandler<?>> slot) {
        super(eventBus, view, proxy, revealType, slot);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        PageRevealEvent.fire(this, getView());
    }
}
