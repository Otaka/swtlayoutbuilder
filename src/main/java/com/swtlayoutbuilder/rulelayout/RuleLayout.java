package com.swtlayoutbuilder.rulelayout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

import java.awt.Dimension;

@SuppressWarnings("UnusedReturnValue")
public class RuleLayout extends Layout {

    private final RuleLayoutCalculationEngine engine;
    private final ComponentActionsWrapper componentActionsWrapper;

    public RuleLayout(Composite parent) {
        componentActionsWrapper = new ComponentActionsWrapper();
        engine = new RuleLayoutCalculationEngine(parent, componentActionsWrapper);
    }

    public LayoutGroup<Control> createGroup() {
        return engine.createGroup();
    }

    public RuleLayout addRule(Rule... rules) {
        engine.addRule(rules);
        return this;
    }

    public RuleLayout setPadding(int left, int top, int right, int bottom) {
        engine.setPadding(left, top, right, bottom);
        return this;
    }

    public void setPreferredSize(Object component, Dimension dimension) {
        componentActionsWrapper.setPreferredSize(component, dimension);
    }

    public void setMinimumSize(Object component, Dimension dimension) {
        componentActionsWrapper.setMinimumSize(component, dimension);
    }

    public RuleLayout setComponentEdgeAnchoredToParentMovingEdge(Object component,
                                                                 boolean left, boolean top, boolean right, boolean bottom,
                                                                 float leftMultiplier, float topMultiplier, float rightMultiplier, float bottomMultiplier) {
        engine.setComponentAnchoredToParentEdges(new AnchoredToParentEdges(component, left, top, right, bottom)
                .setMultipliers(leftMultiplier, topMultiplier, rightMultiplier, bottomMultiplier));
        return this;
    }

    private void checkParent(Composite container) {
        if (container != engine.getParent()) {
            throw new IllegalStateException("One instance of RuleLayout cannot be assigned to several containers");
        }
    }

    @Override
    protected Point computeSize(Composite parent, int x, int y, boolean invalidate) {
        checkParent(parent);
        Dimension dimension = engine.getPreferredSize();
        return new Point(dimension.width, dimension.height);
    }

    @Override
    protected void layout(Composite container, boolean b) {
        checkParent(container);
        engine.layoutContainer();
    }

}