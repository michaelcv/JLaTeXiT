package net.sourceforge.jlatexit.controler;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sourceforge.jlatexit.ClipBoardImageOwner;
import net.sourceforge.jlatexit.utils.ImageFormat;
import net.sourceforge.jlatexit.utils.LatexFormulaRenderer;
import net.sourceforge.jlatexit.view.ColorPicker;
import net.sourceforge.jlatexit.view.MainFrame;

/**
 * Adds the different listeners and renders the latex formula coming from the
 * <code>JTextArea</code> into a <code>JLabel</code><br />
 * Copyright (C) 2014 Michael Clavier, see
 * {@link net.sourceforge.jlatexit.JLatexIt} for the GPL licence
 */
public class MainFrameControler implements ActionListener, ChangeListener,
        PropertyChangeListener {

    /**
     * Renders the latex formula when the document is modified
     */
    private class LatexDocumentListener implements DocumentListener {

        public void changedUpdate(final DocumentEvent e) {
            // Plain text components do not fire these events
        }

        public void insertUpdate(final DocumentEvent e) {
            MainFrameControler.this.latexFormulaRenderer
                .setLatexFormula(MainFrameControler.this.frame.getLatexSource()
                    .getText());
            MainFrameControler.this.render();
        }

        public void removeUpdate(final DocumentEvent e) {
            MainFrameControler.this.latexFormulaRenderer
                .setLatexFormula(MainFrameControler.this.frame.getLatexSource()
                    .getText());
            MainFrameControler.this.render();
        }
    }

    private final MainFrame frame;

    private float fontSize;

    private boolean transparentFormulaBackground;

    private Color formulaColor;

    private LatexFormulaRenderer latexFormulaRenderer;

    private Thread currentThread;

    /**
     * 
     */
    public MainFrameControler(final MainFrame frame) {
        this.frame = frame;
        this.fontSize = 20;
        this.transparentFormulaBackground = true;
        this.formulaColor = Color.BLACK;
        this.frame
            .getLatexSource()
            .setText(
                "\\begin{eqnarray} \n x + 3y - 6z & = & -4a + 5b -7b \\\\ \n & = & -4a - 2b \n \\end{eqnarray}");
        this.addListeners();
        this.frame.setVisible(true);
        this.render();
    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.frame.getSaveButton()) {
            final JFileChooser fc = new JFileChooser(
                System.getProperty("user.home"));
            final String[] fileSuffixes = ImageIO.getReaderFileSuffixes();
            Arrays.sort(fileSuffixes);
            final StringBuilder filterDescription = new StringBuilder(
                "Images (");
            for (int i = 0; i < fileSuffixes.length; i++) {
                if (i != 0) {
                    filterDescription.append(", ");
                }
                filterDescription.append(fileSuffixes[i]);
            }
            filterDescription.append(")");

            final FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                filterDescription.toString(), fileSuffixes);
            fc.setFileFilter(imageFilter);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            final int returnVal = fc.showSaveDialog(this.frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    final File selectedFile = fc.getSelectedFile();
                    final String filePath = selectedFile.getAbsolutePath();
                    final String extension = filePath.substring(filePath
                        .lastIndexOf(".") + 1);
                    // Check if the choosen format has transparency and modifies
                    if (!ImageFormat.hasTransparency(ImageFormat.Format
                        .valueOf(extension.toUpperCase()))
                        && this.transparentFormulaBackground) {
                        this.latexFormulaRenderer
                            .setTransparentBackground(false);
                    }
                    ImageIO.write(this.latexFormulaRenderer.getRenderedImage(),
                        extension, selectedFile);
                    if (!ImageFormat.hasTransparency(ImageFormat.Format
                        .valueOf(extension.toUpperCase()))
                        && this.transparentFormulaBackground) {
                        this.latexFormulaRenderer
                            .setTransparentBackground(true);
                    }
                } catch (final IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == this.frame.getCopyButton()) {
            final ClipBoardImageOwner imageOwner = new ClipBoardImageOwner(
                this.latexFormulaRenderer.getRenderedImage());
            imageOwner.toClipBoard();
        }

    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(final PropertyChangeEvent evt) {
        if (ColorPicker.COLOR_CHANGED.equals(evt.getPropertyName())) {
            this.formulaColor = (Color)evt.getNewValue();
            this.latexFormulaRenderer.setForegroundColor(this.formulaColor);
            this.render();
        }
    }

    public void render() {
        if ((this.currentThread != null) && this.currentThread.isAlive()) {
            this.currentThread.interrupt();
            try {
                this.currentThread.join();
            } catch (final InterruptedException exc) {
                exc.printStackTrace();
            }
            // System.out.println("Killed thread");
        }
        this.currentThread = new Thread() {

            /**
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {
                try {
                    MainFrameControler.this.setMessage("Compiling...",
                        Color.BLACK);
                    if (MainFrameControler.this.latexFormulaRenderer == null) {
                        MainFrameControler.this.latexFormulaRenderer = new LatexFormulaRenderer(
                            MainFrameControler.this.frame.getLatexSource()
                                .getText(),
                            MainFrameControler.this.fontSize,
                            MainFrameControler.this.formulaColor,
                            Color.white,
                            MainFrameControler.this.transparentFormulaBackground);
                    }
                    // now draw it to the screen
                    final JPanel drawingArea = MainFrameControler.this.frame
                        .getDrawingArea();
                    drawingArea.setLayout(new GridBagLayout());
                    drawingArea.removeAll();
                    final JLabel label = new JLabel();

                    label.setIcon(new ImageIcon(
                        MainFrameControler.this.latexFormulaRenderer
                            .getRenderedImage()));
                    drawingArea.add(label);

                    drawingArea.repaint();
                    MainFrameControler.this.setMessage(
                        "Compilation successful", Color.GREEN);
                } catch (final Exception ex) {
                    MainFrameControler.this.setMessage(ex.getMessage(),
                        Color.RED);
                }
            }
        };
        this.currentThread.start();

    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(final ChangeEvent e) {
        boolean hasChanged = false;
        if (e.getSource() == this.frame.getFontSizeSpinner()) {
            final JSpinner source = (JSpinner)e.getSource();
            final float newSize = (Integer)source.getValue();
            hasChanged = this.fontSize != newSize;
            this.fontSize = newSize;
        } else if (e.getSource() == this.frame.getTransparencyCheckBox()) {
            final JCheckBox source = (JCheckBox)e.getSource();
            final boolean newValue = source.isSelected();
            hasChanged = this.transparentFormulaBackground != newValue;
            this.transparentFormulaBackground = newValue;
        }
        if (hasChanged) {
            this.latexFormulaRenderer.setFontSize(this.fontSize);
            this.latexFormulaRenderer
                .setTransparentBackground(this.transparentFormulaBackground);
            this.render();
        }
    }

    /**
     * 
     */
    private void addListeners() {
        this.frame.getCopyButton().addActionListener(this);
        this.frame.getSaveButton().addActionListener(this);
        this.frame.getFontSizeSpinner().addChangeListener(this);
        this.frame.getTransparencyCheckBox().addChangeListener(this);
        this.frame.getColorPicker().addPropertyChangeListener(this);
        this.frame.getLatexSource().getDocument()
            .addDocumentListener(new LatexDocumentListener());
    }

    private void setMessage(final String message, final Color color) {
        this.frame.getMessage().setText(message);
        this.frame.getMessage().setForeground(color);
    }

}
