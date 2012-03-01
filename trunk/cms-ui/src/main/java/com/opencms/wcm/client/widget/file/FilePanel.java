package com.opencms.wcm.client.widget.file;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.file.WcmFile;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 11:51:23
 * To change this template use File | Settings | File Templates.
 */
public class FilePanel extends ContentPanel {

    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);// 定义所要引用的异步服务

    private TreePanel<WcmFile> tree;

    private ListLoader<WcmFile> lloader;

    private final WcmFile ffile = new WcmFile(null, null, null, "0", null, null);

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    final ListView<BeanModel> view = new ListView<BeanModel>();
    
    public List<BeanModel> getSelections(){
        return view.getSelectionModel().getSelection();
    }

    public FilePanel() {
        this.setHeaderVisible(false);
        this.setLayout(new BorderLayout());
        this.setBodyBorder(false);
        createWest();
        createCenter();
    }

    private void createWest() {
        // west
        ContentPanel1 panel = new ContentPanel1();
        // data proxy
        RpcProxy<List<WcmFile>> proxy = new RpcProxy<List<WcmFile>>() {
            @Override
            protected void load(Object loadConfig, AsyncCallback<List<WcmFile>> callback) {
                service.getFileForders((WcmFile) loadConfig, callback);
            }
        };

        TreeLoader<WcmFile> loader = new BaseTreeLoader<WcmFile>(proxy) {
            @Override
            public boolean hasChildren(WcmFile modelData) {
                return (modelData instanceof WcmFile);
            }
        };
        // trees store
        final TreeStore<WcmFile> store = new TreeStore<WcmFile>(loader);
        tree = new TreePanel<WcmFile>(store); 
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.setIconProvider(new ModelIconProvider<WcmFile>() {
			@Override
			public AbstractImagePrototype getIcon(WcmFile model) {
				if("0".equals(model.getHasChild()) && !tree.getStore().hasChildren(model)) {
					return IconHelper.createPath("images/icons/page_white.png");
				}
				return null;
			}
        });
        tree.setCaching(false);
        tree.setDisplayProperty("filename");
        tree.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<WcmFile>() {
            public void selectionChanged(SelectionChangedEvent<WcmFile> se) {
                if (!se.getSelection().isEmpty()) {
                    WcmFile f = (WcmFile) se.getSelection().get(0);
                    ffile.setFilename(f.getFilename());
                    ffile.setPath(f.getPath());
                    ffile.setUrl(f.getUrl());
                    ffile.setType(f.getType());
                    ffile.setShortcut(f.getShortcut());
                    ffile.setTemplate(f.getTemplate());
                    lloader.load(null);
                }
            }
        });

        Menu contextMenu = new Menu();
        MenuItem insert = new MenuItem();
        insert.setText(msgs.file_manager_create_folder());
        insert.setIconStyle("icon-plugin");
        insert.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                final WcmFile folder = tree.getSelectionModel().getSelectedItem();
                if (folder != null) {
                    MessageBox.prompt(msgs.file_manager_create_folder(), msgs.file_manager_filename()).addCallback(new Listener<MessageBoxEvent>() {
                        public void handleEvent(MessageBoxEvent mbe) {
                            if (mbe.getButtonClicked().getText().equals(msgs.ok())) {
                                final String name = mbe.getValue();
                                if (name != null && !"".equals(name)) {
                                    service.createForder(folder, name, new AsyncCallback<WcmFile>() {
                                        public void onFailure(Throwable throwable) {
                                            MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                                        }
                                        public void onSuccess(WcmFile o) {
                                            if (o == null) {
                                                MessageBox.alert(msgs.tips(), msgs.file_manager_create_folder_fail(), null);
                                            } else {
                                                store.add(folder, o, false);
                                                tree.setExpanded(folder, true);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
        contextMenu.add(insert);
        MenuItem upload = new MenuItem();
        upload.setText(msgs.file_manager_upload());
        upload.setIconStyle("icon-add");
        upload.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent menuEvent) {
            	final WcmFile folder = tree.getSelectionModel().getSelectedItem();
                FileUpload up = new FileUpload(folder);
                up.show();
                up.initJS(folder);
                up.addListener(Events.Hide, new Listener<WindowEvent>() {
                    public void handleEvent(WindowEvent windowEvent) {
                        lloader.load(null);
                    }
                });
            }
        });
        contextMenu.add(upload);
        MenuItem delete = new MenuItem();
        delete.setText(msgs.file_manager_delete_folder());
        delete.setIconStyle("icon-delete");
        delete.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent menuEvent) {
            	final WcmFile folder = tree.getSelectionModel().getSelectedItem();
                Listener<MessageBoxEvent> listener = new Listener<MessageBoxEvent>() {
                    public void handleEvent(MessageBoxEvent mbe) {
                        Button btn = mbe.getButtonClicked();
                        if (btn.getText().equals(msgs.yes())) {
                            List<WcmFile> files = new ArrayList<WcmFile>();
                            files.add(folder);
                            service.deleteFiles(files, new AsyncCallback<Integer>() {
                                public void onFailure(Throwable throwable) {
                                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                                }

                                public void onSuccess(Integer o) {
                                    if (o == 0) {
                                        MessageBox.alert(msgs.error(), msgs.file_manager_delete_folder_fail(), null);
                                    } else {
                                        if (store.getParent(folder) != null)
                                            store.remove(store.getParent(folder), folder);
                                        else {
                                            store.remove(folder);
                                        }
                                    }
                                }
                            });
                        }
                    }
                };
                MessageBox.confirm(msgs.confirm(), msgs.confirm_delete_selected(), listener);
            }
        });
        contextMenu.add(delete);
        tree.setContextMenu(contextMenu);
        contextMenu = new Menu();
        MenuItem addfolder = new MenuItem();
        addfolder.setText(msgs.file_manager_create_folder());
        addfolder.setIconStyle("icon-plugin");
        addfolder.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                MessageBox.prompt(msgs.file_manager_create_folder(), msgs.file_manager_filename()).addCallback(new Listener<MessageBoxEvent>() {
                    public void handleEvent(MessageBoxEvent mbe) {
                        if (mbe.getButtonClicked().getText().equals(msgs.ok())) {
                            final String name = mbe.getValue();
                            if (name != null && !"".equals(name)) {
                                service.createForder(null, name, new AsyncCallback<WcmFile>() {
                                    public void onFailure(Throwable throwable) {
                                        MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                                    }

                                    public void onSuccess(WcmFile o) {
                                        if (o == null) {
                                            MessageBox.alert(msgs.tips(), msgs.file_manager_create_folder_fail(), null);
                                        } else {
                                            store.add(o, true);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        contextMenu.add(addfolder);
        panel.setContextMenu(contextMenu);

        loader.load(null);
        panel.add(tree);
        panel.setScrollMode(Style.Scroll.AUTO);
        panel.setHeaderVisible(false);
        panel.setHeading("West");
        panel.setLayout(new FitLayout());
        BorderLayoutData data = new BorderLayoutData(Style.LayoutRegion.WEST, 150, 100, 250);
        data.setMargins(new Margins(1, 1, 1, 1));
        data.setSplit(true);
        this.add(panel, data);
    }

    @SuppressWarnings({ "all" })
	private void createCenter() {
        // center
        ContentPanel1 panel = new ContentPanel1();
        view.setTemplate(getTemplate());
        RpcProxy<List<WcmFile>> proxy1 = new RpcProxy<List<WcmFile>>() {
            @Override
            protected void load(Object loadConfig, AsyncCallback<List<WcmFile>> callback) {
                service.getFiles(ffile, callback);
            }
        };
        lloader = new BaseListLoader(proxy1, new BeanModelReader());
        ListStore<BeanModel> lstore = new ListStore<BeanModel>(lloader) {
            @Override
            protected void onLoad(LoadEvent le) {
                this.config = (ListLoadConfig) le.getConfig();
                Object data = le.getData();
                removeAll();
                if (data == null) {
                    all = new ArrayList();
                } else if (data instanceof List) {
                    List<BeanModel> list = (List) data;
                    all = new ArrayList(list);
                } else if (data instanceof ListLoadResult) {
                    all = new ArrayList(((ListLoadResult) data).getData());
                }
                view.refresh();
                for (BeanModel m : all) {
                    registerModel(m);
                }
                if (config.getSortInfo() != null && !Util.isEmptyString(config.getSortInfo().getSortField())) {
                    sortInfo = config.getSortInfo();
                } else {
                    sortInfo = new SortInfo();
                }
                if (filtersEnabled) {
                    filtersEnabled = false;
                    applyFilters(filterProperty);
                }
                if (storeSorter != null) {
                    applySort(true);
                }
                fireEvent(DataChanged, createStoreEvent());
            }

        };
        view.setLoadingText("loading..");
        view.setStore(lstore);
        view.setBorders(false);
        panel.setId("images-view");
        view.setItemSelector("div.thumb-wrap");
        view.addListener(Events.DoubleClick, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent baseEvent) {
                BeanModel file = view.getSelectionModel().getSelectedItem();
                Window.open((String) file.get("url"), "", "");
            }
        });
        panel.setScrollMode(Style.Scroll.AUTO);
        panel.setHeading("Center");
        panel.setFrame(true);
        panel.setHeaderVisible(false);
        panel.setLayoutOnChange(true);
//        panel.setId("images-view");
        BorderLayoutData data = new BorderLayoutData(Style.LayoutRegion.CENTER);
        data.setMargins(new Margins(1, 1, 1, 1));
        panel.add(view);
        this.add(panel, data);

        final Menu contextMenu = new Menu();
        MenuItem open = new MenuItem();
        open.setText(msgs.file_manager_open());
        open.setIconStyle("icon-add");
        contextMenu.add(open);
        open.addListener(Events.Select, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent baseEvent) {
                List<BeanModel> l = view.getSelectionModel().getSelection();
                if (l.size() == 0) {
                    MessageBox.alert(msgs.warn(), msgs.choose_one(), null);
                    return;
                }
                for (BeanModel file : l) {
                    Window.open((String) file.get("url"), "", "");
                }
            }
        });
        MenuItem remove = new MenuItem();
        remove.setText(msgs.file_manager_delete());
        remove.setIconStyle("icon-delete");
        contextMenu.add(remove);
        remove.addListener(Events.Select, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent baseEvent) {
                if (view.getSelectionModel().getSelection().size() == 0) {
                    MessageBox.alert(msgs.warn(), msgs.choose_one(), null);
                    return;
                } else {
                    Listener<MessageBoxEvent> listener = new Listener<MessageBoxEvent>() {
                        public void handleEvent(MessageBoxEvent mbe) {
                            Button btn = mbe.getButtonClicked();
                            if (btn.getText().equals(msgs.yes())) {
                                final List<WcmFile> list = new ArrayList<WcmFile>();
                                for (BeanModel file : view.getSelectionModel().getSelection()) {
                                    WcmFile f = file.getBean();
                                    list.add(f);
                                }
                                service.deleteFiles(list, new AsyncCallback<Boolean>() {
                                    public void onFailure(Throwable throwable) {
                                        MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                                    }

                                    public void onSuccess(Boolean o) {
                                        if (!o) {
                                            MessageBox.alert(msgs.warn(), msgs.file_manager_delete_fail(), null);
                                        } else {
                                            lloader.load(null);
                                        }
                                    }
                                });
                            }
                        }
                    };
                    MessageBox.confirm(msgs.confirm(), msgs.confirm_delete_selected(), listener);
                }
            }
        });
        MenuItem disselect = new MenuItem();
        disselect.setText(msgs.file_manager_release_selection());
        disselect.setIconStyle("icon-plugin");
        contextMenu.add(disselect);
        disselect.addListener(Events.Select, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent baseEvent) {
                view.getSelectionModel().deselectAll();
            }
        });
        panel.setContextMenu(contextMenu);
        lloader.load(null);
    }

    class ContentPanel1 extends ContentPanel {
        public void setContextMenu(Menu menu) {
            super.setContextMenu(menu);
        }
    }

    private native String getTemplate() /*-{
     return ['<tpl for=".">',
     '<div class="thumb-wrap" id="{filename}" style="border: 1px solid white">',
     '<div class="thumb"><img src="{shortcut}" title="{filename}"></div>',
     '<span class="x-editable" style="width:80px;overflow:hidden;height:16px;font: 12px Arial, Helvetica, sans-serif;">{filename}</span></div>',
     '</tpl>',
     '<div class="x-clear"></div>'].join("");
     }-*/;

}

