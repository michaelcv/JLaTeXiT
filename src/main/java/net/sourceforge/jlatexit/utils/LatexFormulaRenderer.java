/*
 * This is the CW header.
 */
package net.sourceforge.jlatexit.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * Class that will render a latex formula with different parameters :
 * <ul>
 * <li>The font size</li>
 * <li>Background transparency</li>
 * <li>The foreground color</li>
 * <li>The background color</li>
 * </ul>
 * <p>
 * The image rendered can either be retrieved with {@link #getRenderedImage()}
 * or {@link #getRenderedIcon()}. These methods will do the rendering if it was
 * not done before or if a parameter has been changed.
 * <p>
 * Copyright (C) 2014 Michael Clavier, see
 * {@link net.sourceforge.jlatexit.JLatexIt} for the GPL licence
 */
public class LatexFormulaRenderer {

    private String latexFormula;

    private float fontSize;

    private BufferedImage renderedImage;

    private boolean transparentBackground;

    private Color foregroundColor;

    private Color backgroundColor;

    private TeXIcon renderedIcon;

    private boolean renderingNeeded;

    /**
     * @param latexFormula
     *            The latex formula to render
     * @param fontSize
     *            The font size
     * @param foregroundColor
     *            The foreground color
     * @param backgroundColor
     *            The background color
     * @param transparentBackground
     *            A boolean value to tell if the background is transparent
     */
    public LatexFormulaRenderer(final String latexFormula,
            final float fontSize, final Color foregroundColor,
            final Color backgroundColor, final boolean transparentBackground) {
        this.latexFormula = latexFormula;
        this.fontSize = fontSize;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.transparentBackground = transparentBackground;
        this.renderingNeeded = true;
    }

    /**
     * @return the backgroundColor.
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * @return the renderedIcon.
     */
    public TeXIcon getCurrentRenderedIcon() {
        return this.renderedIcon;
    }

    /**
     * @return the renderedImage.
     */
    public BufferedImage getCurrentRenderedImage() {
        if (this.renderingNeeded) {
            this.renderFormula();
        }
        return this.renderedImage;
    }

    /**
     * @return the fontSize.
     */
    public float getFontSize() {
        return this.fontSize;
    }

    /**
     * @return the foregroundColor.
     */
    public Color getForegroundColor() {
        return this.foregroundColor;
    }

    /**
     * @return the latexFormula.
     */
    public String getLatexFormula() {
        return this.latexFormula;
    }

    /**
     * Renders the formula if it is needed (initialisation, parameter change)
     * and returns the renderedIcon
     * 
     * @return the renderedIcon.
     */
    public TeXIcon getRenderedIcon() {
        if (this.renderingNeeded) {
            this.renderFormula();
        }
        return this.renderedIcon;
    }

    /**
     * Renders the formula if it is needed (initialisation, parameter change)
     * and returns the renderedImage
     * 
     * @return the renderedImage.
     */
    public BufferedImage getRenderedImage() {
        if (this.renderingNeeded) {
            this.renderFormula();
        }
        return this.renderedImage;
    }

    /**
     * @return the transparentBackground.
     */
    public boolean isTransparentBackground() {
        return this.transparentBackground;
    }

    /**
     * @param backgroundColor
     *            The backgroundColor to set.
     */
    public void setBackgroundColor(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.renderingNeeded = true;
    }

    /**
     * @param fontSize
     *            The fontSize to set.
     */
    public void setFontSize(final float fontSize) {
        this.fontSize = fontSize;
        this.renderingNeeded = true;
    }

    /**
     * @param foregroundColor
     *            The foregroundColor to set.
     */
    public void setForegroundColor(final Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.renderingNeeded = true;
    }

    /**
     * @param latexFormula
     *            The latexFormula to set.
     */
    public void setLatexFormula(final String latexFormula) {
        this.latexFormula = latexFormula;
        this.renderingNeeded = true;
    }

    /**
     * @param transparentFormulaBackground
     *            The transparentFormulaBackground to set.
     */
    public void setTransparentBackground(
            final boolean transparentFormulaBackground) {
        this.transparentBackground = transparentFormulaBackground;
        this.renderingNeeded = true;
    }

    /**
     * Renders the latex formula into a {@link TeXIcon} (
     * <code>renderedIcon</code>) or a {@link BufferedImage} (
     * <code>renderedImage</code>)
     */
    private void renderFormula() {
        // creates a TexFormula
        final TeXFormula formula = new TeXFormula(this.latexFormula);

        // creates a TexIcon with the same size as the formula by using the
        // given fontSize and foregroundColor
        this.renderedIcon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY,
            this.fontSize, 0, this.foregroundColor);

        // inserts a border
        this.renderedIcon.setInsets(new Insets(5, 5, 5, 5));

        // creates an image of the formula with a transparent background if
        // required
        this.renderedImage = new BufferedImage(
            this.renderedIcon.getIconWidth(),
            this.renderedIcon.getIconHeight(), this.transparentBackground
                    ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2 = this.renderedImage.createGraphics();
        if (!this.transparentBackground) {
            g2.setColor(this.backgroundColor);
            g2.fillRect(0, 0, this.renderedIcon.getIconWidth(),
                this.renderedIcon.getIconHeight());
        }
        final JLabel jl = new JLabel();
        jl.setBackground(this.backgroundColor);
        jl.setForeground(this.foregroundColor);
        // paints the image
        this.renderedIcon.paintIcon(jl, g2, 0, 0);
        this.renderingNeeded = false;
    }

}
