package net.sourceforge.jlatexit.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 * ColorPicker is a component that will display a colored rectangle and will
 * allow to select a color by opening a {@link JColorChooser} dialog.
 * <p>
 * A property change event will be fired on the property
 * <code>COLOR_CHANGED</code> and will notify all the attached
 * {@link PropertyChangeListener}s
 * <p>
 * Copyright (C) 2014 Michael Clavier, see
 * {@link net.sourceforge.jlatexit.JLatexIt} for the GPL licence
 */
public class ColorPicker extends JPanel {

    private static final long serialVersionUID = 6413643454494208152L;

    public static final String COLOR_CHANGED = "COLOR_CHANGED";

    private Color color;

    /**
     * Constructor
     */
    public ColorPicker(final Color color) {
        this.color = color;
        this.addMouseListener();
    }

    /**
     * Paints a rectangle with filled with the given <code>color</code>
     */
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g;
        g2.setColor(this.color);
        g2.fillRect(0, 0, 10, 10);
    }

    /**
     * Adds a {@link MouseListener} to {@link ColorPicker} that will display a
     * {@link JColorChooser} dialog and fire the property
     * <code>COLOR_CHANGED</code> at the end
     */
    private void addMouseListener() {
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(final MouseEvent e) {
                final Color oldColor = ColorPicker.this.color;
                ColorPicker.this.color = JColorChooser.showDialog(
                    ColorPicker.this, "Pick a Color", ColorPicker.this.color);
                ColorPicker.this.repaint();
                ColorPicker.this.firePropertyChange(COLOR_CHANGED, oldColor,
                    ColorPicker.this.color);
            }

            public void mouseEntered(final MouseEvent e) {}

            public void mouseExited(final MouseEvent e) {}

            public void mousePressed(final MouseEvent e) {}

            public void mouseReleased(final MouseEvent e) {}
        });
    }

}
