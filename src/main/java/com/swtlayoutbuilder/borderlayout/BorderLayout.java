package com.swtlayoutbuilder.borderlayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class BorderLayout extends Layout {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int CENTER = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
    private final Control[] controls = new Control[5];
    private int vgap;
    private int hgap;
    private final Point[] sizes = new Point[5];


    public BorderLayout() {
    }

    public BorderLayout(int vgap, int hgap) {
        this.vgap = vgap;
        this.hgap = hgap;
    }

    public BorderLayout setVgap(int vgap) {
        this.vgap = vgap;
        return this;
    }

    public BorderLayout setHgap(int hgap) {
        this.hgap = hgap;
        return this;
    }

    @Override
    protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
        rereadChildren(composite);
        int width = 0;
        int height = 0;
        Control c;
        if ((c = controls[WEST]) != null) {
            Point d = sizes[WEST];
            width += d.x;
        }
        if ((c = controls[CENTER]) != null) {
            width += sizes[CENTER].x;
            if (controls[WEST] != null) {
                width += hgap;
            }
        }
        if ((c = controls[EAST]) != null) {
            Point d = sizes[EAST];
            width += d.x;
            if (controls[WEST] != null || controls[CENTER] != null) {
                width += hgap;
            }
        }
        if ((c = controls[NORTH]) != null) {
            Point d = sizes[NORTH];
            width = Math.max(d.x, width);
        }
        if ((c = controls[SOUTH]) != null) {
            Point d = sizes[SOUTH];
            width = Math.max(d.x, width);
        }


        if ((c = controls[NORTH]) != null) {
            Point d = sizes[NORTH];
            height += d.y;
        }
        int centerPartHeight = 0;
        if ((c = controls[CENTER]) != null) {
            centerPartHeight = Math.max(centerPartHeight, sizes[CENTER].y);
        }
        if ((c = controls[WEST]) != null) {
            centerPartHeight = Math.max(centerPartHeight, sizes[WEST].y);
        }
        if ((c = controls[EAST]) != null) {
            centerPartHeight = Math.max(centerPartHeight, sizes[EAST].y);
        }

        height += centerPartHeight;
        if (centerPartHeight > 0 && controls[NORTH] != null) {
            height += vgap;
        }
        if ((c = controls[SOUTH]) != null) {
            Point d = sizes[SOUTH];
            height += d.y;
            if (controls[NORTH] != null || controls[CENTER] != null) {
                height += vgap;
            }
        }

        return new Point(width, height);
    }

    @Override
    protected void layout(Composite composite, boolean flushCache) {
        rereadChildren(composite);
        Rectangle parentRect = composite.getClientArea();
        Control c;

        if ((c = controls[NORTH]) != null) {
            Point size = sizes[NORTH];
            c.setBounds(parentRect.x, parentRect.y, parentRect.width, size.y);
            int _gp = (controls[SOUTH] != null || controls[CENTER] != null ||controls[WEST] != null ||controls[EAST] != null) ? vgap : 0;
            parentRect.y += size.y + _gp;
            parentRect.height -= size.y + _gp;
        }
        if ((c = controls[SOUTH]) != null) {
            Point size = sizes[SOUTH];
            c.setBounds(parentRect.x, (parentRect.y + parentRect.height) - size.y, parentRect.width, size.y);
            int _gp = (controls[CENTER] != null || controls[WEST] != null || controls[EAST] != null) ? vgap : 0;
            parentRect.height -= size.y;
            parentRect.height -= _gp;
        }
        if ((c = controls[WEST]) != null) {
            int _gp = (controls[EAST] != null || controls[CENTER] != null) ? hgap : 0;
            Point size = sizes[WEST];
            c.setBounds(0, parentRect.y, size.x, parentRect.height);
            parentRect.x += size.x + _gp;
            parentRect.width -= size.x + _gp;
        }

        if ((c = controls[EAST]) != null) {
            int _gp = (controls[CENTER] != null) ? hgap : 0;
            Point size = sizes[EAST];
            c.setBounds((parentRect.x + parentRect.width) - size.x, parentRect.y, size.x, parentRect.height);
            parentRect.width -= size.x + _gp;
        }
        if ((c = controls[CENTER]) != null && parentRect.width > 0 && parentRect.height > 0) {
            c.setBounds(parentRect.x, parentRect.y, parentRect.width, parentRect.height);
        }
    }

    private void rereadChildren(Composite composite) {
        for (int i = 0; i < 5; i++) {
            controls[i] = null;
            sizes[i] = null;
        }
        for (Control c : composite.getChildren()) {
            BorderData bd = getLayoutData(c);
            if (c.getVisible()) {
                controls[bd.position] = c;
                sizes[bd.position] = getPreferredSize(c);
            }
        }
    }

    private Point getPreferredSize(Control control) {
        Point p = (Point) control.getData("preferredSize");
        if (p != null) {
            return p;
        }
        return control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    }

    private BorderData getLayoutData(Control c) {
        Object layoutData = c.getLayoutData();
        if (layoutData == null) {
            return new BorderData(CENTER);
        }
        if (!(layoutData instanceof BorderData)) {
            throw new IllegalArgumentException("BorderLayout expects " + BorderData.class.getSimpleName() + " layout data, but it is " + layoutData.getClass().getSimpleName());
        }
        return (BorderData) layoutData;
    }
}
