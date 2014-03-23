package net.sourceforge.jlatexit;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Implementation of Transferable by using only the imageFlavor
 */
public class TransferableImage implements Transferable {

    Image i;

    public TransferableImage(final Image i) {
        this.i = i;
    }

    public Object getTransferData(final DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.imageFlavor) && (this.i != null)) {
            return this.i;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        final DataFlavor[] flavors = new DataFlavor[1];
        flavors[0] = DataFlavor.imageFlavor;
        return flavors;
    }

    public boolean isDataFlavorSupported(final DataFlavor flavor) {
        final DataFlavor[] flavors = this.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            if (flavor.equals(flavors[i])) {
                return true;
            }
        }

        return false;
    }
}
