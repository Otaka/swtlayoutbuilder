package com.swtlayoutbuilder;

import com.swtlayoutbuilder.rulelayout.Edge;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;


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
            shell.setSize(100,100);
            new SwtLayoutBuilder(shell).ruleLayout().preferredSize(100, 100)
                .add(createColorBlock(shell,"1 component", color(SWT.COLOR_WHITE), true))

                .moveToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
                .moveToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .finish();
        });
    }

    @Test
    @Ignore
    public void ruleLayout() {
        runSwt((shell) -> new SwtLayoutBuilder(shell).ruleLayout()
                .add(createColorBlock(shell, "1 component", color(SWT.COLOR_WHITE), true)).preferredSize(100, 100)
                .add(createColorBlock(shell, "2 component", color(SWT.COLOR_GREEN), true)).preferredSize(100, 100)
                .moveToPrevious(Edge.TOP, Edge.BOTTOM, 5)
                .add(createColorBlock(shell, "Center component", color(SWT.COLOR_YELLOW), true)).preferredSize(100, 100)
                .moveToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
                .moveToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .add(createLabel(shell, "some label 1"))
                .moveToPrevious(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .moveToPrevious(Edge.LEFT, Edge.RIGHT, 5)

                .add(createColorBlock(shell, "Bottom component", color(SWT.COLOR_BLUE), true))
                .moveToParent(Edge.BOTTOM, Edge.BOTTOM, 1)
                .moveToParent(Edge.LEFT, Edge.LEFT, 10)
                .moveToParent(Edge.RIGHT, Edge.RIGHT, -10)

                .add("square", createColorBlock(shell, "Square component", color(SWT.COLOR_GRAY), true)).preferredSize(100, 5)
                .moveToParent(Edge.RIGHT, Edge.RIGHT, 1)
                .moveToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
                .finish());
    }

    @Test
    @Ignore
    public void ruleLayout_form() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).ruleLayout().globalMargin(10, 10, 10, 10).gapBetweenComponents(10)
                    .createGroup("mygroup")
                    .add(createLabel(shell, "mylabel"))
                    .addToCurrentGroup()
                    .moveToParent(Edge.LEFT, Edge.LEFT, 0)//move the component to left top position
                    .moveToParent(Edge.TOP, Edge.TOP, 0)
                    .add(createTextField(shell, "2 component")).preferredSize(100, 100)
                    .addToCurrentGroup()
                    .moveToPrevious(Edge.TOP, Edge.TOP, 0)//align text field to the label bottom position(just to show how group moving works)
                    .moveToPrevious(Edge.LEFT, Edge.RIGHT) //and put it right from the label
                    .moveIdToParent("mygroup", Edge.TOP, Edge.TOP, 0)//adjust group position
                    .finish();
        });
    }

    @Test
    @Ignore
    public void ruleLayout_CenterGroup() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).ruleLayout().gapBetweenComponents(0).preferredSize(300, 300)
                    .createGroup("mygroup")
                    .add(createColorBlock(shell, "A", color(SWT.COLOR_GREEN), true)).preferredSize(50, 50)
                    .addToCurrentGroup()
                    .add(createColorBlock(shell, "B", color(SWT.COLOR_RED), true)).preferredSize(25, 25)
                    .addToCurrentGroup()
                    .moveToPrevious(Edge.LEFT, Edge.RIGHT, 5)
                    .moveToPrevious(Edge.TOP, Edge.BOTTOM, 5)
                    .moveIdToParent("mygroup", Edge.HOR_CENTER, Edge.HOR_CENTER)
                    .moveIdToParent("mygroup", Edge.VER_CENTER, Edge.VER_CENTER)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void borderLayout() {
        runSwt((shell) -> {
            new SwtLayoutBuilder(shell).borderLayout().gapBetweenComponents(5,5)
                    .addToCenter(createColorBlock(shell, "A",color(SWT.COLOR_GREEN)))
                    .addToLeft(createColorBlock(shell, "B",color(SWT.COLOR_RED)))
                    .addToRight(createColorBlock(shell, "B",color(SWT.COLOR_YELLOW)))
                    .addToTop(createColorBlock(shell, "B",color(SWT.COLOR_MAGENTA)))
                    .addToBottom(createColorBlock(shell, "B",color(SWT.COLOR_BLUE)))
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