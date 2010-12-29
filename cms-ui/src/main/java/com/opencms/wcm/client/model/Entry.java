package com.opencms.wcm.client.model;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 15:28:23
 * To change this template use File | Settings | File Templates.
 */
public class Entry {
    private String id;
    private String name;
    private String owner;
    private LayoutContainer body;
    private boolean fill;
    private boolean closable = true;

    private boolean otherApp = false;

    private EventType eventType;

    public Entry(String id, String name, String owner, LayoutContainer body, boolean fill, boolean closable, EventType eventType, boolean otherApp) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.body = body;
        this.fill = fill;
        this.closable = closable;
        this.eventType = eventType;
        this.otherApp = otherApp;
    }

    public Entry(String id, String name, String owner, LayoutContainer body,
                 boolean fill, boolean closable) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.body = body;
        this.fill = fill;
        this.closable = closable;
    }

    public Entry(String id, String name, String owner, LayoutContainer body, boolean fill) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.body = body;
        this.fill = fill;
    }

    public Entry(String id, String name, String owner, LayoutContainer body) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.body = body;
    }

    public Entry(String id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Entry(String id, String name, String owner, boolean otherApp) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.otherApp = otherApp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LayoutContainer getBody() {
        return body;
    }

    public void setBody(LayoutContainer body) {
        this.body = body;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    public boolean isOtherApp() {
        return otherApp;
    }

    public void setOtherApp(boolean otherApp) {
        this.otherApp = otherApp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String toString() {
        return "id=[" + this.id + "],name=[" + name + "],owner=[" + owner
                + "],body=[body],fill=[" + fill + "],closable=[" + closable + "]";
    }
}
