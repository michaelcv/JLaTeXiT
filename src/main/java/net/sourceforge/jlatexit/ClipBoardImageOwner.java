package net.sourceforge.jlatexit;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;

/**
 * Transforms a BufferedImage into a TrasferableImage and copies it to the
 * clipboard<br/>
 * Copyright (C) 2014 Michael Clavier, see
 * {@link net.sourceforge.jlatexit.JLatexIt} for the GPL licence
 */
public class ClipBoardImageOwner implements ClipboardOwner {

    private final BufferedImage image;

    /**
     * Constructor
     */
    public ClipBoardImageOwner(final BufferedImage image) {
        this.image = image;
    }

    /**
     * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard,
     *      java.awt.datatransfer.Transferable)
     */
    public void lostOwnership(final Clipboard clipboard,
            final Transferable contents) {
        System.out.println("Lost ownership");
    }

    /**
     * Copies a BufferedImage to the clipboard after transforming it into a
     * TransferableImage
     */
    public void toClipBoard() {
        final TransferableImage trans = new TransferableImage(this.image);
        final Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }

}
