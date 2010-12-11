package com.opencms.wcm.client.widget.content;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.content.Content;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 0:23:00
 * To change this template use File | Settings | File Templates.
 */
public class ContentPanel extends FormPanel {

    private FormBinding binding;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public ContentPanel(final Content content, final WcmNodeModel parent) {
        this.setFrame(true);
        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        final TextField<String> title = new TextField<String>(); // title
        title.setName("title");
        title.setAllowBlank(false);
        title.setFieldLabel(msgs.content_title());
        this.add(title);

        final NumberField no = new NumberField(); // title
        no.setName("no");
        no.setFieldLabel(msgs.content_no());
        this.add(no);

        final TextField<String> keywords = new TextField<String>(); // keyword
        keywords.setName("keywords");
        keywords.setFieldLabel(msgs.content_keywords());
        this.add(keywords);

        final TextArea description = new TextArea(); // description
        description.setName("description");
        description.setFieldLabel(msgs.content_description());
        this.add(description);

        final HtmlEditor a = new HtmlEditor();
        a.setFieldLabel(msgs.content_content());
        a.setName("content");
        a.setHeight(200);
        a.setWidth(300);
        this.add(a);

        BeanModelFactory factory = BeanModelLookup.get().getFactory(Content.class);

        BeanModel model = factory.createModel(content);
        binding = new FormBinding(this, true);
        binding.bind(model);

        Button save = new Button(msgs.ok());
        this.addButton(save);
        save.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                if(title.validate()){
                    if(content.getId() == null){
                        if("0".equals(parent.getNodetype())){
                        } else {
                            content.setCategoryId(parent.getId());
                        }
                    }
                    content.setContent(a.getValue());
                    Dispatcher.forwardEvent(AppEvents.CONTENT_MANAGER_SAVE, content);
                }
            }
        });
        Button cancel = new Button(msgs.cancel());
        cancel.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                Dispatcher.forwardEvent(AppEvents.CONTENT_MANAGER_CANCEL);
            }
        });
        this.addButton(cancel);
    }
}
