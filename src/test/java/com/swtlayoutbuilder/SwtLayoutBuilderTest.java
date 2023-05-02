package com.swtlayoutbuilder;

import com.swtlayoutbuilder.rulelayout.Edge;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Ignore;
import org.junit.Test;

import static com.swtlayoutbuilder.rulelayout.Edge.*;


public class SwtLayoutBuilderTest extends TestUtils {
    @Test
    @Ignore
    public void rowWithWrappingLayout() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).rowWithWrappingLayout().margins(10, 10, 10, 10)
                    .gapBetweenComponents(5)
                    .horizontal()

                    .componentPlacementDirection(RowWithWrappingBuilder.ComponentOrientation.RIGHT_TO_LEFT)
                    .add(createColorBlock(shell, "Component 1", color(SWT.COLOR_DARK_CYAN), true))
                    .preferredSize(SWT.DEFAULT, 10)
                    .add(createColorBlock(shell, "Component 2", color(SWT.COLOR_RED), true))
                    .add(createColorBlock(shell, "Component 3", color(SWT.COLOR_YELLOW), true))
                    .finish();
            shell.pack();
        });
    }

    @Test
    @Ignore
    public void ruleLayoutCenter() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).ruleLayout()
                    .add(createColorBlock(shell, "1 component", color(SWT.COLOR_WHITE), true))
                    .moveToParent(HOR_CENTER, HOR_CENTER, 0)
                    .moveToParent(VER_CENTER, VER_CENTER, 0)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void ruleLayout() {
        runSwt((shell) -> new SwtLayoutBuilder(shell).ruleLayout().preferredSize(350,250)
                .add(createColorBlock(shell, "1 component", color(SWT.COLOR_WHITE), true)).preferredSize(100, 100)
                .add(createColorBlock(shell, "2 component", color(SWT.COLOR_GREEN), true)).preferredSize(100, 100)
                .moveToPrevious(Edge.TOP, BOTTOM, 5)
                .add(createColorBlock(shell, "Center component", color(SWT.COLOR_YELLOW), true)).preferredSize(100, 100)
                .moveToParent(HOR_CENTER, HOR_CENTER, 0)
                .moveToParent(VER_CENTER, VER_CENTER, 0)
                .add(createLabel(shell, "some label 1"))
                .moveToPrevious(VER_CENTER, VER_CENTER, 0)
                .moveToPrevious(LEFT, RIGHT, 5)

                .add(createColorBlock(shell, "Bottom component", color(SWT.COLOR_BLUE), true))
                .moveToParent(BOTTOM, BOTTOM, 1)
                .moveToParent(LEFT, LEFT, 10)
                .moveToParent(RIGHT, RIGHT, -10)

                .add("square", createColorBlock(shell, "Square component", color(SWT.COLOR_GRAY), true)).preferredSize(100, 5)
                .moveToParent(RIGHT, RIGHT, 1)
                .moveToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
                .finish());
    }

    @Test
    @Ignore
    public void ruleLayout_form() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).ruleLayout().parentPadding(10, 10, 10, 10)
                    .createGroup("mygroup")
                    .add(createLabel(shell, "mylabel"))
                    .addToCurrentGroup()
                    .moveToParent(LEFT, LEFT, 0)//move the component to left top position
                    .moveToParent(Edge.TOP, Edge.TOP, 0)
                    .add(createTextField(shell, "2 component")).preferredSize(100, 100)
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
        runSwt((shell) -> new SwtLayoutBuilder(shell).ruleLayout().preferredSize(300, 300)
                    .createGroup("mygroup")
                    .add(createColorBlock(shell, "A", color(SWT.COLOR_GREEN), true)).preferredSize(50, 50)
                    .addToCurrentGroup()
                    .add(createColorBlock(shell, "B", color(SWT.COLOR_RED), true)).preferredSize(25, 25)
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
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).ruleLayout().debug(true).parentPadding(10, 10, 10, 10)
                    .templateForm(5, (t, ruleLayout) -> {
                        t.setAlignLabelsLeft(false);
                        t.setRowsGap(5);
                        t.addRow("label1 jhkh", createColorBlock(shell, "A", color(SWT.COLOR_GREEN), true));
                        ruleLayout.anchorCurrentComponentEdgesToParentMovingEdges(false, false, false, false);
                        t.addRow("label2", createTextField(shell, "hello")).setLabelToFieldVerticalAlignment(RuleLayoutBuilder.FormRowAlignment.TOP);
                        ruleLayout.preferredSize(200,200);
                        ruleLayout.anchorCurrentComponentEdgesToParentMovingEdges(false, false, true, false);
                        t.addRow("la3", createColorBlock(shell, "B", color(SWT.COLOR_GREEN), true));
                    }).moveToParent(Edge.LEFT, Edge.LEFT, 0)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void borderLayout() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).borderLayout().gapBetweenComponents(5, 5)
                    .addToCenter(createColorBlock(shell, "A", color(SWT.COLOR_GREEN)))
                    .addToLeft(createColorBlock(shell, "B", color(SWT.COLOR_RED)))
                    .addToRight(createColorBlock(shell, "B", color(SWT.COLOR_YELLOW)))
                    .addToTop(createColorBlock(shell, "B", color(SWT.COLOR_MAGENTA)))
                    .addToBottom(createColorBlock(shell, "B", color(SWT.COLOR_BLUE)))
                    .finish();
            shell.pack();
        });
    }

    private Label createLabel(Shell shell, String text) {
        Label label = new Label(shell, SWT.NONE);
        label.setText(text);
        return label;
    }

    private Text createTextField(Shell shell, String text) {
        Text textField = new Text(shell, SWT.NONE);
        textField.setText(text);
        return textField;
    }
}