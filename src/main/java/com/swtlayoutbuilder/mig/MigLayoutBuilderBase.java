package com.swtlayoutbuilder.mig;

import com.swtlayoutbuilder.AbstractBuilder;
import net.miginfocom.swt.MigLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

class MigLayoutBuilderBase extends AbstractBuilder<MigLayoutBuilder> {
    protected final StringBuilder layoutConstraints = new StringBuilder();
    protected final StringBuilder rowConstraints = new StringBuilder();
    protected final StringBuilder columnConstraints = new StringBuilder();
    protected Control currentComponent;
    protected StringBuilder componentConstraints = new StringBuilder();
    private MigLayout layout;

    public MigLayoutBuilderBase() {
        super(null);
    }

    public void setContainer(Composite container) {
        this.container = container;
    }

    /**
     * add JLabel component
     */
    public MigLayoutBuilder label(String text) {
        Label label = new Label(container, SWT.NONE);
        label.setText(text);
        add(label);
        return (MigLayoutBuilder) this;
    }

    public MigLayoutBuilder add(Control component) {
        checkLayoutCreated();
        if (currentComponent != null) {
            currentComponent.setParent(container);
            currentComponent.setLayoutData(componentConstraints.toString());
        }
        componentConstraints = new StringBuilder();
        currentComponent = component;
        return (MigLayoutBuilder) this;
    }

    protected void checkLayoutCreated() {
        if (layout == null) {
            layout = new MigLayout(layoutConstraints.toString(), columnConstraints.toString(), rowConstraints.toString());
            container.setLayout(layout);
        }
    }

    protected void checkLayoutAlreadyInitialized() {
        if (layout != null) {
            throw new RuntimeException("Cannot set layout properties, because MigLayout already initialized. You can modify layout/row/column configuration only before adding actual components");
        }
    }

    @Override
    public MigLayoutBuilder exec(Consumer currentComponentProcessor) {
        return null;
    }

    @Override
    public Composite finish() {
        if (currentComponent != null) {
            currentComponent.setParent(container);
            currentComponent.setLayoutData(componentConstraints.toString());
        }
        return super.finish();
    }
}
