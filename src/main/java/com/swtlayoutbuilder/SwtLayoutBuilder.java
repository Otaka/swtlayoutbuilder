package com.swtlayoutbuilder;

import com.swtlayoutbuilder.mig.MigLayoutBuilder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class SwtLayoutBuilder {
    public static final String PARENT = "ConstraintLayoutBuilder_PARENT";
    protected final Composite container;

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

    public MigLayoutBuilder migLayout() {
        try {
            Class.forName("net.miginfocom.swt.MigLayout");
        } catch (Exception ex) {
            throw new RuntimeException("MigLayout dependency is not in classpath. Please add it.");
        }
        MigLayoutBuilder builder = new MigLayoutBuilder();
        builder.setContainer(container);
        return builder;
    }
}