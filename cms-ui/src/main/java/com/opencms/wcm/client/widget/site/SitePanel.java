package com.opencms.wcm.client.widget.site;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.model.site.Site;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.widget.template.CmsTemplateChooser;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 15:08:49
 * To change this template use File | Settings | File Templates.
 */
public class SitePanel extends FormPanel {

    private FormBinding binding;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public SitePanel(final Site site) {
        this.setFrame(true);
        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        final TextField<String> name = new TextField<String>(); // name
        name.setName("name");
        name.setAllowBlank(false);
        name.setFieldLabel(msgs.site_name());
        this.add(name);

        final TextField<String> title = new TextField<String>(); // title
        title.setName("title");
        title.setAllowBlank(false);
        title.setFieldLabel(msgs.site_title());
        this.add(title);

        final TextField<String> url = new TextField<String>(); // url
        url.setName("url");
        url.setAllowBlank(false);
        url.setFieldLabel(msgs.site_url());
        this.add(url);

        final TextField<String> template = new TextField<String>(); // template
        template.setName("template");
        template.setAllowBlank(false);
        template.setFieldLabel(msgs.site_template());
        this.add(template);

        final TextField<String> indexTemplate = new TextField<String>(); // template
        indexTemplate.setName("indexTemplate");
        indexTemplate.setFieldLabel(msgs.site_index_template());
        this.add(indexTemplate);
        template.addListener(Events.Focus, new Listener(){
            @Override
            public void handleEvent(BaseEvent be) {
                final CmsTemplateChooser templateChooser = new CmsTemplateChooser(template.getValue(), template.getValue(), "0");
                templateChooser.setFrame(true);
                templateChooser.show();
                templateChooser.addListener(Events.Hide, new Listener<BaseEvent>() {
                    @Override
                    public void handleEvent(BaseEvent be) {
                        String templateName = templateChooser.getTemplateName();
                        if(templateName != null){
                            template.setValue(templateName);
                        }
                        if("".equals(templateName)){
                            indexTemplate.setValue(null);
                        }
                    }
                });
            }
        });
        indexTemplate.addListener(Events.Focus, new Listener(){
            @Override
            public void handleEvent(BaseEvent be) {
                if(template.getValue() == null || "".equals(template.getValue())){
                    MessageBox.alert(msgs.warn(), msgs.site_template() , null);
                    return;
                }
                final CmsTemplateChooser templateChooser = new CmsTemplateChooser(template.getValue(), template.getValue(), "1");
                templateChooser.setFrame(true);
                templateChooser.show();
                templateChooser.addListener(Events.Hide, new Listener<BaseEvent>() {
                    @Override
                    public void handleEvent(BaseEvent be) {
                        String templateName = templateChooser.getTemplateName();
                        if(templateName != null){
                            indexTemplate.setValue(templateName);
                        }
                    }
                });
            }
        });

        final TextField<String> keywords = new TextField<String>(); // keyword
        keywords.setName("keywords");
        keywords.setFieldLabel(msgs.site_keywords());
        this.add(keywords);

        final TextArea description = new TextArea(); // description
        description.setName("description");
        description.setFieldLabel(msgs.site_description());
        this.add(description);

        BeanModelFactory factory = BeanModelLookup.get().getFactory(Site.class);

        BeanModel model = factory.createModel(site);
        binding = new FormBinding(this, true);
        binding.bind(model);

        Button save = new Button(msgs.ok());
        this.addButton(save);
        save.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                if(name.validate() && title.validate() && template.validate()){
                    site.setTemplate(template.getValue());
                    site.setIndexTemplate(indexTemplate.getValue());
                    Dispatcher.forwardEvent(AppEvents.SITE_MANAGER_SAVE, site);
                }
            }
        });
        Button cancel = new Button(msgs.cancel());
        cancel.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                Dispatcher.forwardEvent(AppEvents.SITE_MANAGER_CANCEL);     
            }
        });
        this.addButton(cancel);
    }
}
