package com.swtlayoutbuilder;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractBuilder<T> {
    protected Composite container;
    private boolean debug;

    protected AbstractBuilder(Composite container) {
        this.container = container;
    }

    public Composite finish() {
        return container;
    }

    public T debug(boolean debug) {
        this.debug = debug;
        return (T) this;
    }

    protected void paintComponent(Object component) {
        if (component instanceof Control) {
            Control control = (Control) component;
            control.setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
    }

    protected void add(Composite parent, Control component, Object constraint) {
        //do not do anything, because in SWT you add elements to parent in constructor
        if (debug) {
            paintComponent(component);
        }
    }

    protected Control setPreferredSize(Control c, int width, int height) {
        c.setData("preferredSize", new org.eclipse.swt.graphics.Point(width, height));
        return c;
    }

    public abstract T exec(Consumer currentComponentProcessor);

    public interface Consumer {
        void process(Control currentControl);
    }
}