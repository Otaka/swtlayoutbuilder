package com.swtlayoutbuilder;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.ArrayList;
import java.util.List;

public class RowWithWrappingBuilder extends AbstractBuilder<RowWithWrappingBuilder> {

    private final RowLayout layout;
    private final List<Control> children = new ArrayList<>();
    private Control currentComponent;
    private ComponentOrientation componentPlacementDirection;

    public RowWithWrappingBuilder(Composite container) {
        super(container);
        layout = new RowLayout();
        margins(0, 0, 0, 0);
        gapBetweenComponents(0);
        container.setLayout(layout);
        currentComponent = container;
    }

    public RowWithWrappingBuilder horizontal() {
        layout.type = SWT.HORIZONTAL;
        return this;
    }

    public RowWithWrappingBuilder vertical() {
        layout.type = SWT.VERTICAL;
        return this;
    }

    public RowWithWrappingBuilder componentPlacementDirection(ComponentOrientation direction) {
        componentPlacementDirection = direction;
        return this;
    }

    public RowWithWrappingBuilder gapBetweenComponents(int spacing) {
        layout.spacing = spacing;
        return this;
    }

    /**
     * If the justify field is true, widgets are spread across the available space horizontally or vertically(depending on horizontal()/vertical()).
     * If the parent Composite grows wider, the extra space is distributed evenly among the widgets.
     */
    public RowWithWrappingBuilder justify(boolean justify) {
        layout.justify = justify;
        return this;
    }

    public RowWithWrappingBuilder pack(boolean pack) {
        layout.pack = pack;
        return this;
    }

    public RowWithWrappingBuilder wrap(boolean wrap) {
        layout.wrap = wrap;
        return this;
    }

    public RowWithWrappingBuilder center(boolean center) {
        layout.center = center;
        return this;
    }

    public RowWithWrappingBuilder fill(boolean fill) {
        layout.fill = fill;
        return this;
    }

    public RowWithWrappingBuilder margins(int left, int top, int right, int bottom) {
        layout.marginLeft = left;
        layout.marginTop = top;
        layout.marginRight = right;
        layout.marginBottom = bottom;
        return this;
    }


    public RowWithWrappingBuilder add(Control component) {
        component.setParent(container);
        currentComponent = component;
        children.add(component);
        return this;
    }

    @Override
    public Composite finish() {
        if (componentPlacementDirection == ComponentOrientation.RIGHT_TO_LEFT) {
            Composite tempParent = new Composite(container, SWT.NONE);
            for (Control control : children) {
                control.setParent(tempParent);
            }
            for (int i = children.size() - 1; i >= 0; i--) {
                children.get(i).setParent(container);
            }
            tempParent.dispose();
        }
        return super.finish();
    }

    /**
     * Set preferred size of last added component
     */
    public RowWithWrappingBuilder preferredSize(int width, int height) {
        currentComponent.setLayoutData(new RowData(width, height));
        return this;
    }

    @Override
    public RowWithWrappingBuilder exec(Consumer currentComponentProcessor) {
        currentComponentProcessor.process(currentComponent);
        return this;
    }

    public enum ComponentOrientation {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT;
    }
}