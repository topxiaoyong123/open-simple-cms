package com.opencms.wcm.client.widget.extend;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.file.WcmFile;
import com.opencms.wcm.client.widget.file.FilePanel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 11:41:55
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({"ALL"})
public class ImageHtmlHelper extends Dialog {

    private String imageHtml;

    public String getImageHtml() {
        return imageHtml;
    }

    WcmMessages msgs = GWT.create(WcmMessages.class);

    final TextField<String> url = new TextField<String>();
    final NumberField width = new NumberField();
    final NumberField height = new NumberField();
    final HtmlEditor a = new HtmlEditor();

    public ImageHtmlHelper() {
        this.setHeading("文件上传");
        this.setMinWidth(500);
        this.setMinHeight(440);
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setBodyStyle("border: none;background: none");
        this.setBodyBorder(false);
        this.setButtons("");
        FormData formData = new FormData("100%");
        FormPanel panel = new FormPanel();
        panel.setHeaderVisible(false);
        panel.setBodyBorder(false);
        panel.setSize(487, -1);
        panel.setLabelAlign(FormPanel.LabelAlign.TOP);
        panel.setButtonAlign(Style.HorizontalAlignment.CENTER);

        LayoutContainer main = new LayoutContainer();
        main.setLayout(new ColumnLayout());

        LayoutContainer left = new LayoutContainer();
        left.setStyleAttribute("paddingRight", "10px");
        FormLayout layout = new FormLayout();
        layout.setLabelAlign(FormPanel.LabelAlign.TOP);
        left.setLayout(layout);

        final Listener l = new Listener(){
            @Override
            public void handleEvent(BaseEvent baseEvent) {
                if(url.getValue() != null && !"".equals(url.getValue().trim())){
                    String html = "<img src=\"" + url.getValue() + "\" ";
                    if(width.getValue() != null && width.validate()){
                        html += "width=\"" + width.getValue() + "px\"";
                    }
                    if(height.getValue() != null && height.validate()){
                        html += "height=\"" + height.getValue() + "px\"";        
                    }
                    html += ">";
                    a.setValue(html);
                } else{
                    a.setValue(null);
                }
            }
        };


        url.addListener(Events.KeyUp, l);
        url.setName("url");
        url.setFieldLabel(msgs.file_image_url());
        left.add(url, formData);

        width.addListener(Events.KeyUp, l);
        width.setName("width");
        width.setFieldLabel(msgs.file_image_width());
        width.setMinValue(0);
        left.add(width, new FormData("40%"));

        height.addListener(Events.KeyUp, l);
        height.setName("height");
        height.setFieldLabel(msgs.file_image_height());
        width.setMinValue(0);
        left.add(height, new FormData("40%"));

        LayoutContainer right = new LayoutContainer();
        right.setStyleAttribute("paddingTop", "19px");
        layout = new FormLayout();
//        layout.setLabelAlign(FormPanel.LabelAlign.TOP);
        right.setLayout(layout);

        final Button viewserver = new Button(msgs.file_viewserver());
        viewserver.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                Dialog d = new Dialog();
                d.setHeading(msgs.file_manager());
                d.setLayout(new FitLayout());
                d.setSize(1000, 500);
                final FilePanel filePanel = new FilePanel();
                d.add(filePanel);
                d.setHideOnButtonClick(true);
                d.addListener(Events.Hide, new Listener(){
                    @Override
                    public void handleEvent(BaseEvent be) {
                        List<BeanModel> list = filePanel.getSelections();
                        if(list != null && list.size() == 1){
                            WcmFile file = list.get(0).getBean();
                            String filetype = file.getFilename().substring(file.getFilename().lastIndexOf(".") + 1);
                            if (("jpg").equalsIgnoreCase(filetype) || ("bmp").equalsIgnoreCase(filetype) || ("gif").equalsIgnoreCase(filetype) || ("png").equalsIgnoreCase(filetype)) {
                                url.setValue(file.getUrl());
                                l.handleEvent(new BaseEvent(null));
                            } else{
                                MessageBox.alert(msgs.warn(), msgs.file_image_only(), null);  
                            }
                        }
                    }
                });
                d.show();
            }
        });
        right.add(viewserver);

        main.add(left, new ColumnData(.8));
        main.add(right, new ColumnData(.2));

        panel.add(main, new FormData("100%"));

        a.setFieldLabel(msgs.preview());
        a.setShowToolbar(false);
        a.setReadOnly(true);
        a.setName("content");
        a.setHeight(200);
        panel.add(a, new FormData("100%"));

        Button save = new Button(msgs.ok());
        save.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                String html = "<img src=\"" + url.getValue() + "\" ";
                if(width.getValue() != null && width.validate()){
                    html += "width=\"" + width.getValue() + "px\"";
                }
                if(height.getValue() != null && height.validate()){
                    html += "height=\"" + height.getValue() + "px\"";
                }
                html += ">";
                imageHtml = html;
                hide();
            }
        });
        panel.addButton(save);
        Button cancel = new Button(msgs.cancel());
        panel.addButton(cancel);
        cancel.addSelectionListener(new SelectionListener(){
            @Override
            public void componentSelected(ComponentEvent componentEvent) {
                hide();
            }
        });
        this.add(panel);
    }
}
