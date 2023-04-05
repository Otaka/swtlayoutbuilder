package com.swtlayoutbuilder.rulelayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleLayout extends Layout {
    private final List<Rule> rules = new ArrayList<>();
    private final Set<RuleLayoutGroup> groups = new HashSet<>();
    private final Insets layoutMargin = new Insets(0, 0, 0, 0);
    private final Composite parent;

    public RuleLayout(Composite parent) {
        this.parent = parent;
    }

    public RuleLayoutGroup createGroup() {
        RuleLayoutGroup newGroup = new RuleLayoutGroup(this);
        groups.add(newGroup);
        return newGroup;
    }

    public RuleLayout addRule(Rule... rules) {
        for (Rule c : rules) this.rules.add(c);
        return this;
    }

    public RuleLayout setMargin(int top, int left, int bottom, int right) {
        layoutMargin.set(top, left, bottom, right);
        return this;
    }

    @Override
    protected Point computeSize(Composite composite, int i, int i1, boolean b) {
        checkParent(parent);
        runCalculations(parent);
        return calculateBounds(parent);
    }

    private Point calculateBounds(Composite parent) {
        int maxX = 0;
        int maxY = 0;
        for (Control component : parent.getChildren()) {
            ComponentRect rect = getRect(getWrapper(component));
            maxX = Math.max(maxX, rect.getX2());
            maxY = Math.max(maxY, rect.getY2());
        }

        ComponentRect rect = getRect(getWrapper(parent));
        maxX = Math.max(maxX, rect.getX2());
        maxY = Math.max(maxY, rect.getY2());

        maxX += layoutMargin.left + layoutMargin.right;
        maxY += layoutMargin.top + layoutMargin.bottom;
        return new Point(maxX, maxY);
    }

    @Override
    protected void layout(Composite composite, boolean b) {
        checkParent(parent);
        runCalculations(parent);

        for (Control component : parent.getChildren()) {
            ComponentWrapper wrapper = getWrapper(component);
            ComponentRect rect = getRect(wrapper);
            component.setBounds(rect.getX() + layoutMargin.left, rect.getY() + layoutMargin.top, rect.getWidth(), rect.getHeight());
        }
    }

    private void fillSizes(Composite parent) {
        for (Control component : parent.getChildren()) {
            Point sizeDimension = getPreferredSize(component);
            ComponentWrapper wrapper = getWrapper(component);
            wrapper.getRect().reset(0, 0, sizeDimension.x, sizeDimension.y);
        }

        ComponentWrapper parentWrapper = getWrapper(parent);
        Rectangle parentSize = parent.getClientArea();
        parentWrapper.getRect().reset(parentSize.x, parentSize.y, parentSize.width - layoutMargin.left - layoutMargin.right, parentSize.height - layoutMargin.top - layoutMargin.bottom);
        parentWrapper.getRect().fixX1Y1Position();

    }

    private void runCalculations(Composite parent) {
        fillSizes(parent);
        for (Rule rule : rules) {
            ComponentRect anchorComponentRect = getRect(rule.getAnchorComponent());
            ComponentRect componentRect = getRect(rule.getComponent());
            if (rule.getComponent().isGroup()) {
                int position = getPosition(anchorComponentRect, rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            } else {
                int position = getPosition(anchorComponentRect, rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            }
        }
    }

    private void checkParent(Composite container) {
        if (container != parent) {
            throw new IllegalStateException("One instance of RuleLayout cannot be assigned to several containers");
        }
    }

    ComponentRect getRect(ComponentWrapper component) {
        if (component.isGroup()) {
            RuleLayoutGroup group = component.getGroup();
            return group.getRect();
        }

        return component.getRect();
    }

    private int getPosition(ComponentRect rect, Edge edge) {
        switch (edge) {
            case LEFT:
                return rect.getX();
            case RIGHT:
                return rect.getX2();
            case TOP:
                return rect.getY();
            case BOTTOM:
                return rect.getY2();
            case WIDTH:
                return rect.getWidth();
            case HEIGHT:
                return rect.getHeight();
            case HOR_CENTER:
                return rect.getX() + rect.getWidth() / 2;
            case VER_CENTER:
                return rect.getY() + rect.getHeight() / 2;
            case BASELINE:
                return rect.getY();
            default:
                throw new RuntimeException("Unknown edge " + edge);
        }
    }

    private void setPosition(ComponentRect rect, ComponentWrapper component, Edge edge, int value) {
        if (component.isGroup()) {
            int diffX = 0;
            int diffY = 0;
            switch (edge) {
                case LEFT: {
                    diffX = value - rect.getX();
                    break;
                }
                case TOP: {
                    diffY = value - rect.getY();
                    break;
                }
                case RIGHT: {
                    diffX = value - rect.getX2();
                    break;
                }
                case BOTTOM: {
                    diffY = value - rect.getY2();
                    break;
                }
                case HOR_CENTER: {
                    diffX = value - (rect.getX() + ((rect.getX2() - rect.getX()) / 2));
                    break;
                }
                case VER_CENTER: {
                    diffY = value - (rect.getY() + ((rect.getY2() - rect.getY()) / 2));
                    break;
                }
            }


            component.getGroup().moveChildren(diffX, diffY);
        } else {
            switch (edge) {
                case LEFT: {
                    rect.setX(value);
                    break;
                }
                case RIGHT: {
                    rect.setX2(value);
                    break;
                }
                case TOP: {
                    rect.setY(value);
                    break;
                }
                case BOTTOM: {
                    rect.setY2(value);
                    break;
                }
                case WIDTH: {
                    rect.setWidth(value);
                    break;
                }
                case HEIGHT: {
                    rect.setHeight(value);
                    break;
                }
                case HOR_CENTER: {
                    rect.moveX(value - rect.getWidth() / 2);
                    break;
                }
                case VER_CENTER: {
                    rect.moveY(value - rect.getHeight() / 2);
                    break;
                }
                case BASELINE: {
//                    int baseLine = component.getBaseline(rect.getWidth(), rect.getHeight()) + rect.getY();
//                    int diff = value - baseLine;
//                    rect.moveY(rect.getY() + diff);
                    rect.moveY(value);
                    break;
                }
                default:
                    throw new RuntimeException("Unknown edge " + edge);
            }
        }
    }

    private ComponentWrapper getWrapper(Control control) {
        return (ComponentWrapper) control.getData("wrapper");
    }

    private ComponentWrapper getWrapper(RuleLayoutGroup group) {
        return group.getComponentWrapper();
    }

    private Point getPreferredSize(Control control) {
        Point p = (Point) control.getData("preferredSize");
        if (p != null) {
            return p;
        }
        return control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    }
}