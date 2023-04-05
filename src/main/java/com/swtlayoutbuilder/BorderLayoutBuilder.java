package com.swtlayoutbuilder;

import com.swtlayoutbuilder.borderlayout.BorderData;
import com.swtlayoutbuilder.borderlayout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class BorderLayoutBuilder extends AbstractBuilder<BorderLayoutBuilder> {
    private final BorderLayout layout;
    private Control currentComponent;

    protected BorderLayoutBuilder(Composite container) {
        super(container);
        layout = new BorderLayout();
        container.setLayout(layout);
        currentComponent = container;
    }

    public BorderLayoutBuilder gapBetweenComponents(int hor, int ver) {
        layout.setHgap(hor);
        layout.setVgap(ver);
        return this;
    }

    public BorderLayoutBuilder addToCenter(Control component) {
        component.setLayoutData(new BorderData(BorderLayout.CENTER));
        component.setParent(container);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToLeft(Control component) {
        component.setLayoutData(new BorderData(BorderLayout.WEST));
        component.setParent(container);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToRight(Control component) {
        component.setLayoutData(new BorderData(BorderLayout.EAST));
        component.setParent(container);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToTop(Control component) {
        component.setLayoutData(new BorderData(BorderLayout.NORTH));
        component.setParent(container);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToBottom(Control component) {
        component.setLayoutData(new BorderData(BorderLayout.SOUTH));
        component.setParent(container);
        currentComponent = component;
        return this;
    }

    /**
     * Set preferred size of the last added component
     */
    public BorderLayoutBuilder preferredSize(int width, int height) {
        setPreferredSize(currentComponent, width, height);
        return this;
    }

    /**
     * just execute some arbitrary code with currentComponent.<br>
     * Method introduced to be able to execute some code without breaking builder call chain
     */
    public BorderLayoutBuilder exec(Consumer currentComponentProcessor) {
        currentComponentProcessor.process(currentComponent);
        return this;
    }
}