package ui.gui;

import javax.swing.*;
import java.awt.event.MouseEvent;

// CREDIT TO SimpleDrawingEditor in CPSC 210 Repository for template
public abstract class StarsTools {
    protected JButton button;
    protected StarsEditor editor;
    private boolean active;

    public StarsTools(StarsEditor editor, JComponent parent) {
        this.editor = editor;
        createButton(parent);
        addToParent(parent);
        addListener();
        active = false;
    }

    protected abstract void createButton(JComponent parent);

    protected abstract void addListener();

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        return button;
    }

    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    // Default mouse event handlers
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }
}