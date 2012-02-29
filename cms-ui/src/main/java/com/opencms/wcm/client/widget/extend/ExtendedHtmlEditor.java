package com.opencms.wcm.client.widget.extend;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 11:38:30
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedHtmlEditor extends HtmlEditor {

    List<Button> bs = new ArrayList<Button>();

    public void addButton(Button b) {
        bs.add(b);
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        Button image = new Button();
        image.setIconStyle("editor_image");
        ToolTipConfig config = new ToolTipConfig();  
        config.setTitle("Insert an image");
        config.setText("Insert an image");
        image.setToolTip(config);
        image.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                final ImageHtmlHelper imageHtmlHelper = new ImageHtmlHelper();
                imageHtmlHelper.show();
                imageHtmlHelper.addListener(Events.Hide, new Listener(){
                    @Override
                    public void handleEvent(BaseEvent be) {
                        String imageHtml = imageHtmlHelper.getImageHtml();
                        if(imageHtml != null){
                            getFormatter().insertHTML(imageHtml);    
                        }
                    }
                });
            }
        });
        this.tb.add(image);
        for (Button b : bs) {
            this.tb.add(b);
        }
    }

    public RichTextArea.Formatter getFormatter() {
        if (impl instanceof RichTextArea.Formatter) {
            return (RichTextArea.Formatter) impl;
        }
        return null;
    }
}
