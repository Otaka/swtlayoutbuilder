package com.swtlayoutbuilder.rulelayout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.function.Function;

class ComponentActionsWrapper {
    private boolean firstTimeGetShellRect = true;

    private Object map(Object component, Function<LayoutGroup<Control>, Object> groupMapping, Function<Control, Object> componentMapper) {
        if (component instanceof ComponentWrapper) {
            ComponentWrapper<Control> wrapper = (ComponentWrapper<Control>) component;
            if (wrapper.isGroup()) {
                return groupMapping.apply(wrapper.getGroup());
            } else {
                return componentMapper.apply(wrapper.getComponent());
            }
        } else {
            return componentMapper.apply((Control) component);
        }
    }

    private Rectangle swt2awtRect(org.eclipse.swt.graphics.Rectangle r) {
        return new Rectangle(r.x, r.y, r.width, r.height);
    }

    private Rectangle componentRect2awtRect(ComponentRect r) {
        return new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public Rectangle getComponentRect(Object component) {
        if (component instanceof Shell) {
            Shell shell = (Shell) component;
            Rectangle rect = swt2awtRect(shell.getClientArea());
            if (firstTimeGetShellRect) {
                Dimension preferredSize= (Dimension) shell.getData("preferred");
                if(preferredSize!=null){
                    rect.width= preferredSize.width;
                    rect.height= preferredSize.height;
                }else {
                    rect.width = 1;
                    rect.height = 1;
                }
                firstTimeGetShellRect = false;
            }
            return rect;
        } else {
            return (Rectangle) map(component, (g) ->
                    componentRect2awtRect(g.getRect()), (c) ->
                    swt2awtRect(c.getBounds()));
        }
    }

    public int getBaseLine(Object component, int width, int height) {
        throw new IllegalArgumentException("Baseline does is not supported in SWT");
    }

    public void setComponentRect(Object component, Rectangle rect) {
        map(component, (g) -> null, (c) -> {
            c.setBounds(rect.x, rect.y, rect.width, rect.height);
            return null;
        });
    }

    public Object[] getChildren(Object component) {
        return (Object[]) map(component, LayoutGroup::getChildren, (c) -> ((Composite) c).getChildren());
    }

    public Dimension getMinimumSize(Object component) {
        return (Dimension) map(component, LayoutGroup::getRect, (c) -> c.getData("minimum"));
    }

    public Dimension getPreferredSize(Object component) {
        return (Dimension) map(component, LayoutGroup::getRect, (c) -> {
            if (c.getData("preferred") != null) return c.getData("preferred");
            Point p= c.computeSize(-1, -1);
            return new Dimension(p.x, p.y);
        });
    }

    public void setPreferredSize(Object component, Dimension dimension) {
        map(component, (g) -> null, (c) -> {
            c.setData("preferred", dimension);
            return null;
        });
    }

    public void setMinimumSize(Object component, Dimension dimension) {
        map(component, (g) -> null, (c) -> {
            c.setData("minimum", dimension);
            return null;
        });
    }
}
