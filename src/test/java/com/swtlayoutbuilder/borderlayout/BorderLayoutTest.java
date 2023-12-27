package com.swtlayoutbuilder.borderlayout;

import com.swtlayoutbuilder.TestUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.Ignore;
import org.junit.Test;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


public class BorderLayoutTest extends TestUtils {
    @Test
    @Ignore
    public void borderLayout() {
        runInWindow(new SwtRunnable() {
            final Map<Integer, Control> controls = new HashMap<>();
            Shell shell;

            private Button addSelectionListener(Button button) {
                button.setSelection(true);
                button.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        switch (button.getText()) {
                            case "TOP":
                                controls.get(SWT.TOP).setVisible(button.getSelection());
                                break;
                            case "BOTTOM":
                                controls.get(SWT.BOTTOM).setVisible(button.getSelection());
                                break;
                            case "CENTER":
                                controls.get(SWT.CENTER).setVisible(button.getSelection());
                                break;
                            case "LEFT":
                                controls.get(SWT.LEFT).setVisible(button.getSelection());
                                break;
                            case "RIGHT":
                                controls.get(SWT.RIGHT).setVisible(button.getSelection());
                                break;
                        }
                        shell.layout(true, true);
                        shell.pack();
                    }
                });
                return button;
            }

            @Override
            public void init(Shell shell) {
                this.shell = shell;
                shell.setLayout(new BorderLayout());
                Composite testPanel = new Composite(shell, SWT.NONE);
                testPanel.setLayoutData(new BorderData(BorderLayout.CENTER));

                createLayoutBuilder(testPanel).borderLayout()
                        .gapBetweenComponents(5, 5)
                        .addToTop(createColorBlock("Top", Color.PINK, true)).preferredSize(10, 100).exec(current -> controls.put(SWT.TOP, current))
                        .addToRight(createColorBlock("Right", Color.YELLOW, true)).exec(current -> controls.put(SWT.RIGHT, current))
                        .addToBottom(createColorBlock("Bottom", Color.GREEN, true)).preferredSize(10, 100).exec(current -> controls.put(SWT.BOTTOM, current))
                        .addToLeft(createColorBlock("Left", Color.BLUE, true)).preferredSize(100, 100).exec(current -> controls.put(SWT.LEFT, current))
                        .addToCenter(createColorBlock("Center", Color.RED, true)).exec(current -> controls.put(SWT.CENTER, current))
                        .finish();

                Composite buttonsPanel = new Composite(shell, SWT.NONE);
                buttonsPanel.setLayoutData(new BorderData(BorderLayout.SOUTH));
                buttonsPanel.setLayout(new FillLayout(SWT.HORIZONTAL));
                addSelectionListener(new Button(buttonsPanel, SWT.CHECK)).setText("TOP");
                addSelectionListener(new Button(buttonsPanel, SWT.CHECK)).setText("LEFT");
                addSelectionListener(new Button(buttonsPanel, SWT.CHECK)).setText("RIGHT");
                addSelectionListener(new Button(buttonsPanel, SWT.CHECK)).setText("CENTER");
                addSelectionListener(new Button(buttonsPanel, SWT.CHECK)).setText("BOTTOM");
                shell.pack();
            }
        });
    }
}