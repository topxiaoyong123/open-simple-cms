package com.opencms.wcm.client.widget.category;

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
import com.opencms.wcm.client.model.category.Category;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 11:15:38
 * To change this template use File | Settings | File Templates.
 */
public class CategoryPanel extends FormPanel {

    private FormBinding binding;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public CategoryPanel(final Category category, final WcmNodeModel parent) {
        this.setFrame(true);
        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        final TextField<String> name = new TextField<String>(); // name
        name.setName("name");
        name.setAllowBlank(false);
        name.setFieldLabel(msgs.category_name());
        this.add(name);

        final TextField<String> title = new TextField<String>(); // title
        title.setName("title");
        title.setAllowBlank(false);
        title.setFieldLabel(msgs.category_title());
        this.add(title);

        final NumberField no = new NumberField(); // title
        no.setName("no");
        no.setFieldLabel(msgs.category_no());
        this.add(no);

        final TextField<String> keywords = new TextField<String>(); // keyword
        keywords.setName("keywords");
        keywords.setFieldLabel(msgs.category_keywords());
        this.add(keywords);

        final TextArea description = new TextArea(); // description
        description.setName("description");
        description.setFieldLabel(msgs.category_description());
        this.add(description);

        final Radio visable = new Radio();
        visable.setName("visable");
        visable.setBoxLabel(msgs.yes());

        final Radio novisable = new Radio();
        novisable.setName("visable");
        novisable.setBoxLabel(msgs.no());

        if (category.isVisible()) {
            visable.setValue(true);
        } else {
            novisable.setValue(true);
        }
        final RadioGroup visibleGroup = new RadioGroup("isvisible");
        visibleGroup.setFieldLabel(msgs.visible());
        visibleGroup.add(visable);
        visibleGroup.add(novisable);
        this.add(visibleGroup);

        final Radio staticC = new Radio();
        staticC.setName("static");
        staticC.setBoxLabel(msgs.yes());

        final Radio nostaticC = new Radio();
        nostaticC.setName("static");
        nostaticC.setBoxLabel(msgs.no());

        if (category.isStaticCategory()) {
            staticC.setValue(true);
        } else {
            nostaticC.setValue(true);
        }
        final RadioGroup staticGroup = new RadioGroup("static");
        staticGroup.setFieldLabel(msgs.category_static());
        staticGroup.add(staticC);
        staticGroup.add(nostaticC);
        this.add(staticGroup);

        BeanModelFactory factory = BeanModelLookup.get().getFactory(Category.class);

        BeanModel model = factory.createModel(category);
        binding = new FormBinding(this, true);
        binding.bind(model);

        Button save = new Button(msgs.ok());
        this.addButton(save);
        save.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                if(name.validate() && title.validate()){
                    if(category.getId() == null){
                        if("0".equals(parent.getNodetype())){
                            category.setSiteId(parent.getId());
                        } else {
                            category.setParentId(parent.getId());
                        }
                    }
                    if(visable.getValue()){
                        category.setVisible(true);
                    } else{
                        category.setVisible(false);
                    }
                    if(staticC.getValue()){
                        category.setStaticCategory(true);
                    } else{
                        category.setStaticCategory(false);
                    }
                    Dispatcher.forwardEvent(AppEvents.CATEGORY_MANAGER_SAVE, category);
                }
            }
        });
        Button cancel = new Button(msgs.cancel());
        cancel.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                Dispatcher.forwardEvent(AppEvents.CATEGORY_MANAGER_CANCEL);
            }
        });
        this.addButton(cancel);
    }
}
