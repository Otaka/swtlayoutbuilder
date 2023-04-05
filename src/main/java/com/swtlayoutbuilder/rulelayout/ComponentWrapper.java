package com.swtlayoutbuilder.rulelayout;

import org.eclipse.swt.widgets.Control;

public class ComponentWrapper {
    private Control control;
    private RuleLayoutGroup group;
    private final ComponentRect rect = new ComponentRect();

    public ComponentWrapper(Control control) {
        this.control = control;
    }

    public ComponentWrapper(RuleLayoutGroup group) {
        this.group = group;
    }

    public boolean isGroup() {
        return group != null;
    }

    public ComponentRect getRect() {
        return rect;
    }

    public Control getControl() {
        return control;
    }

    public RuleLayoutGroup getGroup() {
        return group;
    }
}
