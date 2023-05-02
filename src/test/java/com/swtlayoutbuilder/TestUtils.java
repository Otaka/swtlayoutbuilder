package com.swtlayoutbuilder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestUtils {
    protected Control createColorBlock(Shell parent, String message, Color color) {
        CLabel label = new CLabel(parent, SWT.CENTER);
        label.setBackground(color);
        label.setText(message);
        return label;
    }

    protected Control createColorBlock(Shell parent, String message, Color color, boolean visible) {
        CLabel label = new CLabel(parent, SWT.CENTER);
        label.setBackground(color);
        label.setText(message);
        label.setVisible(visible);
        return label;
    }

    protected Color color(int systemColor) {
        return Display.getCurrent().getSystemColor(systemColor);
    }

    protected void runSwt(SwtRunnable runnable) {
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


    protected interface SwtRunnable {
        void init(Shell shell);
    }
}
