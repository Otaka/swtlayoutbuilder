package com.swtlayoutbuilder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TestUtils {
    private Composite currentParent;

    protected Control createColorBlock(String message, java.awt.Color color) {
        CLabel label = new CLabel(currentParent, SWT.CENTER);
        label.setBackground(color(color));
        label.setText(message);
        return label;
    }

    protected Composite createPanel(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        return composite;
    }

    protected Composite createPanel(Composite parent, java.awt.Color color) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(color(color));
        return composite;
    }

    protected Control createColorBlock(String message, java.awt.Color color, boolean visible) {
        CLabel label = new CLabel(currentParent, SWT.CENTER);
        label.setBackground(color(color));
        label.setText(message);
        label.setVisible(visible);
        return label;
    }

    protected Label createLabel(String text) {
        Label label = new Label(currentParent, SWT.NONE);
        label.setText(text);
        return label;
    }

    protected Button createButton(String text) {
        Button label = new Button(currentParent, SWT.PUSH);
        label.setText(text);
        return label;
    }

    protected Text createTextField(String text) {
        Text textField = new Text(currentParent, SWT.NONE);
        textField.setText(text);
        return textField;
    }

    protected void runInWindow(SwtRunnable runnable) {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        runnable.init(shell);
        final Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        shell.setSize(newSize);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    private Color color(java.awt.Color awtColor) {
        return new Color(Display.getCurrent(), awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    protected interface SwtRunnable {
        void init(Shell shell);
    }

    protected SwtLayoutBuilder createLayoutBuilder(Composite shell) {
        currentParent = shell;
        return new SwtLayoutBuilder(shell);
    }
}
