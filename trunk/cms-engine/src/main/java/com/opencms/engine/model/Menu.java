package com.opencms.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午9:07
 * To change this template use File | Settings | File Templates.
 */
public class Menu {

    private Item item;

    private List<Menu> children;

    private boolean current;

    public Item getItem() {
        if(null == item)
			item = new Item();
		return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public List<Menu> getChildren() {
        if(null == children)
			children = new ArrayList<Menu>();
		return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public void addMenu(Menu menu) {
		this.getChildren().add(menu);
	}
}
