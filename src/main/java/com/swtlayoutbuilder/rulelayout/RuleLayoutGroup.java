package com.swtlayoutbuilder.rulelayout;

import java.util.ArrayList;
import java.util.List;

public class RuleLayoutGroup {
    private final List<ComponentWrapper> children = new ArrayList<>();
    private final ComponentRect rectangle = new ComponentRect();
    private final RuleLayout owner;
    private final ComponentWrapper componentWrapper;

    public RuleLayoutGroup(RuleLayout owner) {
        this.owner = owner;
        componentWrapper = new ComponentWrapper(this);
    }

    public ComponentWrapper getComponentWrapper() {
        return componentWrapper;
    }

    public RuleLayoutGroup addComponent(ComponentWrapper component) {
        children.add(component);
        return this;
    }

    public ComponentRect getRect() {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (ComponentWrapper component : children) {
            ComponentRect rect = owner.getRect(component);
            if (rect.getX() < minX) {
                minX = rect.getX();
            }
            if (rect.getY() < minY) {
                minY = rect.getY();
            }
            if (rect.getX2() > maxX) {
                maxX = rect.getX2();
            }
            if (rect.getY2() > maxY) {
                maxY = rect.getY2();
            }
        }

        rectangle.reset(minX, minY, maxX - minX, maxY - minY);
        return rectangle;
    }

    public void moveChildren(int x, int y) {
        for (ComponentWrapper component : children) {
            ComponentRect rect = owner.getRect(component);
            rect.moveX(rect.getX() + x);
            rect.moveY(rect.getY() + y);
        }
    }

    public List<ComponentWrapper> getGroupChildren() {
        return children;
    }
}