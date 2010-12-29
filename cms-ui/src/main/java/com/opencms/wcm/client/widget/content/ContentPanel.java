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
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.widget.extend.ExtendedHtmlEditor;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 0:23:00
 * To change this template use File | Settings | File Templates.
 */
public class ContentPanel  extends LayoutContainer {

    private FormBinding binding;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public ContentPanel(final Content content, final WcmNodeModel parent) {
        FormData formData = new FormData("100%");
        FormPanel panel = new FormPanel();
//        panel.setFrame(true);
        panel.setHeaderVisible(false);
        panel.setBodyBorder(false);
        panel.setSize(600, -1);
        panel.setLabelAlign(FormPanel.LabelAlign.TOP);
        panel.setButtonAlign(Style.HorizontalAlignment.CENTER);

        LayoutContainer main = new LayoutContainer();
        main.setLayout(new ColumnLayout());  

        LayoutContainer left = new LayoutContainer();
        left.setStyleAttribute("paddingRight", "10px");
        FormLayout layout = new FormLayout();
        layout.setLabelAlign(FormPanel.LabelAlign.TOP);
        left.setLayout(layout);

        final TextField<String> title = new TextField<String>(); // title
        title.setName("title");
        title.setAllowBlank(false);
        title.setFieldLabel(msgs.content_title());
        left.add(title, formData);

        final TextField<String> author = new TextField<String>(); // title
        author.setName("author");
        author.setFieldLabel(msgs.content_author());
        left.add(author, formData);

        final TextField<String> keywords = new TextField<String>(); // keyword
        keywords.setName("keywords");
        keywords.setFieldLabel(msgs.content_keywords());
        left.add(keywords, formData);

        LayoutContainer right = new LayoutContainer();
        right.setStyleAttribute("paddingLeft", "10px");  
        layout = new FormLayout();
        layout.setLabelAlign(FormPanel.LabelAlign.TOP);
        right.setLayout(layout);

        final TextField<String> template = new TextField<String>(); // keyword
        template.setName("template");
        template.setFieldLabel(msgs.content_template());
        right.add(template, formData);

        final TextField<String> source = new TextField<String>(); // keyword
        source.setName("source");
        source.setFieldLabel(msgs.content_source());
        right.add(source, formData);

        final NumberField no = new NumberField(); // title
        no.setName("no");
        no.setFieldLabel(msgs.content_no());
        right.add(no, formData);

        main.add(left, new ColumnData(.5));  
        main.add(right, new ColumnData(.5));

        panel.add(main, new FormData("100%"));

        final TextField<String> description = new TextField<String>(); // description
        description.setName("description");
        description.setFieldLabel(msgs.content_description());
        panel.add(description, new FormData("100%"));

        final ExtendedHtmlEditor a = new ExtendedHtmlEditor();
        a.setFieldLabel(msgs.content_content());
        a.setName("content");
        a.setHeight(200);
        a.setWidth(600);
        panel.add(a, new FormData("100%"));

        BeanModelFactory factory = BeanModelLookup.get().getFactory(Content.class);

        BeanModel model = factory.createModel(content);
        binding = new FormBinding(panel, true);
        binding.bind(model);

        Button save = new Button(msgs.ok());
        panel.addButton(save);
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
        panel.addButton(cancel);
        this.add(panel);
    }

}
