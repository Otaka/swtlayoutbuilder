package com.swtlayoutbuilder.rulelayout;

import org.eclipse.swt.widgets.Control;

import javax.swing.*;
import java.awt.Component;

public class Rule {
    private final Edge anchorEdge;
    private final ComponentWrapper anchorComponent;
    private final Edge edge;
    private ComponentWrapper component;
    private final int offset;


    public Rule(Edge anchorEdge, ComponentWrapper anchorComponent, int offset, Edge edge, ComponentWrapper component) {
        this.anchorEdge = anchorEdge;
        this.anchorComponent = anchorComponent;
        this.edge = edge;
        this.component = component;
        this.offset = offset;
    }

    public void setComponent(ComponentWrapper component) {
        this.component = component;
    }

    public int getOffset() {
        return offset;
    }

    public Edge getAnchorEdge() {
        return anchorEdge;
    }

    public ComponentWrapper getAnchorComponent() {
        return anchorComponent;
    }

    public Edge getEdge() {
        return edge;
    }

    public ComponentWrapper getComponent() {
        return component;
    }

}