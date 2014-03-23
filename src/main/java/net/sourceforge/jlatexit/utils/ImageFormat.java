package net.sourceforge.jlatexit.utils;

/**
 * A utility class that provides methods linked to image formats
 * <p>
 * Copyright (C) 2014 Michael Clavier, see
 * {@link net.sourceforge.jlatexit.JLatexIt} for the GPL licence
 */
public class ImageFormat {

    public enum Format {
        BMP, GIF, JPEG, JPG, PNG, WBMP
    };

    /**
     * Returns true if an image <code>format</code> is supporting transparency
     * 
     * @param format
     */
    public static boolean hasTransparency(final Format format) {
        boolean hasTransparency = false;
        switch (format) {
            case GIF:
                hasTransparency = true;
                break;
            case PNG:
                hasTransparency = true;
                break;

            default:
                hasTransparency = false;
                break;
        }
        return hasTransparency;
    }

}
