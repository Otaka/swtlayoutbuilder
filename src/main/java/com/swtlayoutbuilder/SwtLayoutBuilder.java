package com.swtlayoutbuilder;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class SwtLayoutBuilder {
    private final Composite container;
    public static final String PARENT = "ConstraintLayoutBuilder_PARENT";

    public SwtLayoutBuilder(Composite container) {
        this.container = container;
    }

    public RowWithWrappingBuilder rowWithWrappingLayout() {
        return new RowWithWrappingBuilder(container);
    }

    public BorderLayoutBuilder borderLayout() {
        return new BorderLayoutBuilder(container);
    }

    public RuleLayoutBuilder<Control> ruleLayout() {
        return new RuleLayoutBuilder(container);
    }

//    public FormLayoutBuilder formLayout() {
//        return new FormLayoutBuilder(container);
//    }
}