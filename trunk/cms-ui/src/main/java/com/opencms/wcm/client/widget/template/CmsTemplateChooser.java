package com.opencms.wcm.client.widget.template;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-17
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class CmsTemplateChooser extends Dialog {

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    private String templateName;

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    public String getTemplateName() {
        return templateName;
    }

    public CmsTemplateChooser(final String baseTemplate, final String name, final String type) {
        ContentPanel panel = new ContentPanel();
        final LayoutContainer details = new LayoutContainer();
        details.setBorders(true);
        details.setStyleAttribute("backgroundColor", "white");
        RpcProxy proxy = new RpcProxy() {
            @Override
            protected void load(Object loadConfig, AsyncCallback asyncCallback) {
                service.getCmsTemplates(baseTemplate, type, asyncCallback);
            }
        };
        ListLoader loader = new BaseListLoader(proxy, new BeanModelReader());
        ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
        loader.load();
        final ListView<BeanModel> view = new ListView<BeanModel>();
        view.setLoadingText("loading..");
        view.setId("img-chooser-view");
        view.setStore(store);
        view.setBorders(false);
        view.setItemSelector("div.thumb-wrap");
        view.setTemplate(getTemplate());
        final XTemplate detailTp = XTemplate.create(getDetailTemplate(msgs));
        view.getSelectionModel().addListener(Events.SelectionChange, new Listener<SelectionChangedEvent<BeanModel>>() {
            public void handleEvent(SelectionChangedEvent<BeanModel> be) {
                if (be.getSelection().size() > 0) {
                    detailTp.overwrite(details.getElement(), Util.getJsObject(be.getSelection().get(0)));
                }
            }
        });
        panel.setId("images-view");
        panel.add(view);
        panel.setHeaderVisible(false);
        panel.setLayoutOnChange(true);
        panel.setBorders(true);
        panel.setBodyBorder(false);
        panel.setLayout(new FitLayout());
        panel.setHeaderVisible(false);

        BorderLayoutData eastData = new BorderLayoutData(Style.LayoutRegion.SOUTH, 50, 50, 100);
        eastData.setSplit(true);

        BorderLayoutData centerData = new BorderLayoutData(Style.LayoutRegion.CENTER);
        centerData.setMargins(new Margins(0, 5, 0, 0));

        this.setId("img-chooser-dlg");
        this.setHeading(msgs.template_manager_header());
        this.setMinWidth(900);
        this.setMinHeight(600);
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setBodyStyle("border: none;background: none");
        this.setBodyBorder(false);
        this.setButtons("");
        this.add(panel, centerData);
        this.add(details, eastData);

        Button save = new Button(msgs.ok());
        save.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                if(view.getSelectionModel().getSelection().size() > 0){
                    templateName = view.getSelectionModel().getSelection().get(0).get("name");
                }
                hide();
            }
        });
        this.addButton(save);
        Button clear = new Button(msgs.clear());
        clear.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                templateName = "";
                hide();
            }
        });
        this.addButton(clear);
        Button cancel = new Button(msgs.cancel());
        this.addButton(cancel);
        cancel.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                hide();
            }
        });
    }

    private native String getTemplate() /*-{
         return ['<tpl for=".">',
         '<div class="thumb-wrap" id="{name}" style="border: 1px solid white">',
         '<div><img src="{shortcut}" title="{title}" width="400px" height="400px"></div>',
         '<span class="x-editable">{description}</span></div>',
         '</tpl>',
         '<div class="x-clear"></div>'].join("");
     }-*/;

    public native String getDetailTemplate(WcmMessages msgs) /*-{
        var template_selected = msgs.@com.opencms.wcm.client.WcmMessages::template_selected()();
        var template_description = msgs.@com.opencms.wcm.client.WcmMessages::template_description()();
        return ['<div class="details" style="text-align:left">',
        '<tpl for=".">',
        '<b>' + template_selected + ':<a href="{shortcut}" target="_blank">{title}</a></b> ',
        '<b>' + template_description + ':{description}</b>',
        '</tpl>',
        '</div>'].join("");
    }-*/;
}
