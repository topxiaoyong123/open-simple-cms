package com.opencms.wcm.client.widget.site;

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
                if(name.validate() && title.validate()){
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
