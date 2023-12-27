package com.swtlayoutbuilder;

import com.swtlayoutbuilder.rulelayout.Edge;
import org.eclipse.swt.SWT;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.Color;

import static com.swtlayoutbuilder.rulelayout.Edge.BOTTOM;
import static com.swtlayoutbuilder.rulelayout.Edge.HOR_CENTER;
import static com.swtlayoutbuilder.rulelayout.Edge.LEFT;
import static com.swtlayoutbuilder.rulelayout.Edge.RIGHT;
import static com.swtlayoutbuilder.rulelayout.Edge.VER_CENTER;


public class SwtLayoutBuilderTest extends TestUtils {
    @Test
    @Ignore
    public void rowWithWrappingLayout() {
        runInWindow((shell) -> {
            createLayoutBuilder(shell).rowWithWrappingLayout().margins(10, 10, 10, 10)
                    .gapBetweenComponents(5)
                    .horizontal()

                    .componentPlacementDirection(RowWithWrappingBuilder.ComponentOrientation.RIGHT_TO_LEFT)
                    .add(createColorBlock("Component 1", Color.PINK, true))
                    .preferredSize(SWT.DEFAULT, 10)
                    .add(createColorBlock("Component 2", Color.RED, true))
                    .add(createColorBlock("Component 3", Color.BLUE, true))
                    .finish();
            shell.pack();
        });
    }

    @Test
    @Ignore
    public void ruleLayoutCenter() {
        runInWindow((shell) -> {
            createLayoutBuilder(shell).ruleLayout()
                    .add(createColorBlock("1 component", Color.WHITE, true))
                    .moveToParent(HOR_CENTER, HOR_CENTER, 0)
                    .moveToParent(VER_CENTER, VER_CENTER, 0)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void ruleLayout() {
        runInWindow((shell) -> createLayoutBuilder(shell).ruleLayout().preferredSize(350, 250)
                .add(createColorBlock("1 component", Color.WHITE, true)).preferredSize(100, 100)
                .add(createColorBlock("2 component", Color.GREEN, true)).preferredSize(100, 100)
                .moveToPrevious(Edge.TOP, BOTTOM, 5)
                .add(createColorBlock("Center component", Color.YELLOW, true)).preferredSize(100, 100)
                .moveToParent(HOR_CENTER, HOR_CENTER, 0)
                .moveToParent(VER_CENTER, VER_CENTER, 0)
                .add(createLabel("some label 1"))
                .moveToPrevious(VER_CENTER, VER_CENTER, 0)
                .moveToPrevious(LEFT, RIGHT, 5)

                .add(createColorBlock( "Bottom component", Color.BLUE, true))
                .moveToParent(BOTTOM, BOTTOM, 1)
                .moveToParent(LEFT, LEFT, 10)
                .moveToParent(RIGHT, RIGHT, -10)

                .add("square", createColorBlock("Square component", Color.GRAY, true)).preferredSize(100, 5)
                .moveToParent(RIGHT, RIGHT, 1)
                .moveToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
                .finish());
    }

    @Test
    @Ignore
    public void ruleLayout_form() {
        runInWindow((shell) -> {
            createLayoutBuilder(shell).ruleLayout().parentPadding(10, 10, 10, 10)
                    .createGroup("mygroup")
                    .add(createLabel("mylabel"))
                    .addToCurrentGroup()
                    .moveToParent(LEFT, LEFT, 0)//move the component to left top position
                    .moveToParent(Edge.TOP, Edge.TOP, 0)
                    .add(createTextField("2 component")).preferredSize(100, 100)
                    .addToCurrentGroup()
                    .moveToPrevious(Edge.TOP, Edge.TOP, 0)//align text field to the label bottom position(just to show how group moving works)
                    .moveToPrevious(LEFT, RIGHT) //and put it right from the label
                    .moveIdToParent("mygroup", Edge.TOP, Edge.TOP, 0)//adjust group position
                    .finish();
        });
    }

    @Test
    @Ignore
    public void ruleLayout_CenterGroup() {
        runInWindow((shell) -> createLayoutBuilder(shell).ruleLayout().preferredSize(300, 300)
                    .createGroup("mygroup")
                .add(createColorBlock( "A", Color.GREEN, true)).preferredSize(50, 50)
                    .addToCurrentGroup()
                .add(createColorBlock( "B", Color.RED, true)).preferredSize(25, 25)
                    .addToCurrentGroup()
                    .moveToPrevious(LEFT, RIGHT, 5)
                    .moveToPrevious(Edge.TOP, BOTTOM, 5)
                    .moveIdToParent("mygroup", HOR_CENTER, HOR_CENTER)
                    .moveIdToParent("mygroup", VER_CENTER, VER_CENTER)
                .finish());
    }

    @Test
    @Ignore
    public void ruleLayout_formTemplate() {
        runInWindow((shell) -> {
            createLayoutBuilder(shell).ruleLayout().debug(true).parentPadding(10, 10, 10, 10)
                    .templateForm(5, (t, ruleLayout) -> {
                        t.setAlignLabelsLeft(false);
                        t.setRowsGap(5);
                        t.addRow("label1 jhkh", createColorBlock("A", Color.RED, true));
                        ruleLayout.anchorCurrentComponentEdgesToParentMovingEdges(false, false, false, false);
                        t.addRow("label2", createTextField( "hello")).setLabelToFieldVerticalAlignment(RuleLayoutBuilder.FormRowAlignment.TOP);
                        ruleLayout.preferredSize(200,200);
                        ruleLayout.anchorCurrentComponentEdgesToParentMovingEdges(false, false, true, false);
                        t.addRow("la3", createColorBlock("B", Color.GREEN, true));
                    }).moveToParent(Edge.LEFT, Edge.LEFT, 0)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void borderLayout() {
        runInWindow((shell) -> {
            createLayoutBuilder(shell).borderLayout().gapBetweenComponents(5, 5)
                    .addToCenter(createColorBlock("A", Color.GREEN))
                    .addToLeft(createColorBlock("B", Color.RED))
                    .addToRight(createColorBlock("B", Color.YELLOW))
                    .addToTop(createColorBlock("B", Color.MAGENTA))
                    .addToBottom(createColorBlock("B", Color.BLUE))
                    .finish();
        });
    }
}