package com.swtlayoutbuilder;

import com.swtlayoutbuilder.rulelayout.ComponentWrapper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public abstract class AbstractBuilder<T> {
    protected final Composite container;
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

    protected void paintComponent(Component component) {
        if (component instanceof JComponent) {
            JComponent jcomponent = (JComponent) component;
            jcomponent.setOpaque(true);
            jcomponent.setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
    }

    protected Control setPreferredSize(Control c, int width, int height) {
        c.setData("preferredSize", new org.eclipse.swt.graphics.Point(width, height));
        return c;
    }

    public abstract T exec(Consumer currentComponentProcessor);
    public interface Consumer{
        void process(Control currentControl);
    }
}