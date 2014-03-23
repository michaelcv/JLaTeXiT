package net.sourceforge.jlatexit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Copyright (C) 2014 Michael Clavier
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 9187012458479999868L;

    private JTextArea latexSource;

    private JPanel drawingArea;

    private JSpinner fontSizeSpinner;

    private ColorPicker colorPicker;

    private JCheckBox transparencyCheckBox;

    private JButton saveButton;

    private JButton copyButton;

    private final JLabel message;

    /**
     * 
     */
    public MainFrame() {
        this.initFrame();
        this.initCenterPanel();
        this.message = new JLabel("Initializing...");
        this.getContentPane().add(this.message, BorderLayout.SOUTH);
    }

    /**
     * @return the colorPicker.
     */
    public ColorPicker getColorPicker() {
        return this.colorPicker;
    }

    /**
     * @return the copyButton.
     */
    public JButton getCopyButton() {
        return this.copyButton;
    }

    /**
     * @return the drawingArea.
     */
    public JPanel getDrawingArea() {
        return this.drawingArea;
    }

    /**
     * @return the fontSizeSpinner.
     */
    public JSpinner getFontSizeSpinner() {
        return this.fontSizeSpinner;
    }

    /**
     * @return the latexSource.
     */
    public JTextArea getLatexSource() {
        return this.latexSource;
    }

    /**
     * @return the message.
     */
    public JLabel getMessage() {
        return this.message;
    }

    /**
     * @return the saveButton.
     */
    public JButton getSaveButton() {
        return this.saveButton;
    }

    /**
     * @return the transparencyCheckBox.
     */
    public JCheckBox getTransparencyCheckBox() {
        return this.transparencyCheckBox;
    }

    /**
     * 
     */
    private void initCenterPanel() {
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        this.initEditorArea(centerPanel);
        this.initDrawingPanel(centerPanel);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * @param centerPanel
     */
    private void initDrawingPanel(final JPanel centerPanel) {
        final JPanel drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());
        this.drawingArea = new JPanel();
        drawingPanel.add(this.drawingArea, BorderLayout.CENTER);
        this.initOptionsPanel(drawingPanel);
        centerPanel.add(drawingPanel);
    }

    /**
     * @param centerPanel
     */
    private void initEditorArea(final JPanel centerPanel) {
        this.latexSource = new JTextArea();
        final JPanel editorArea = new JPanel();
        editorArea.setLayout(new BorderLayout());
        editorArea.add(new JScrollPane(this.latexSource), BorderLayout.CENTER);
        centerPanel.add(editorArea);
    }

    /**
     * 
     */
    private void initFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JLaTeXiT (JLaTeXMath)");
        this.setSize(500, 500);
        this.getContentPane().setLayout(new BorderLayout());
    }

    /**
     * @param drawingPanel
     */
    private void initOptionsPanel(final JPanel drawingPanel) {
        final JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));

        // Left options panel initialisation
        final JPanel leftOptionsPanel = new JPanel();

        final SpinnerNumberModel snm = new SpinnerNumberModel(20, 0, 80, 1);
        this.fontSizeSpinner = new JSpinner(snm);
        final JPanel fontSizePanel = new JPanel();
        fontSizePanel.add(new JLabel("Font size : "));
        fontSizePanel.add(this.fontSizeSpinner);
        leftOptionsPanel.add(fontSizePanel);

        final JPanel formulaColorPanel = new JPanel();
        formulaColorPanel.add(new JLabel("Font color : "));
        this.colorPicker = new ColorPicker(new Color(0, 0, 0));
        formulaColorPanel.add(this.colorPicker);
        leftOptionsPanel.add(formulaColorPanel);

        this.transparencyCheckBox = new JCheckBox("Transparency?", true);
        this.transparencyCheckBox
            .setHorizontalTextPosition(SwingConstants.LEFT);
        leftOptionsPanel.add(this.transparencyCheckBox);

        optionsPanel.add(leftOptionsPanel, BorderLayout.WEST);

        // Right options panel initialisation
        final JPanel rightOptionsPanel = new JPanel();

        this.saveButton = new JButton("Save");
        this.copyButton = new JButton("Copy");

        rightOptionsPanel.add(this.copyButton);
        rightOptionsPanel.add(this.saveButton);

        optionsPanel.add(rightOptionsPanel, BorderLayout.EAST);

        drawingPanel.add(optionsPanel, BorderLayout.SOUTH);
    }

}
