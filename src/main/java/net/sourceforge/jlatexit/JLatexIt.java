package net.sourceforge.jlatexit;

import net.sourceforge.jlatexit.controler.MainFrameControler;
import net.sourceforge.jlatexit.view.MainFrame;

/**
 * Converts dynamically a latex formula into an image <br/>
 * by using the library <a
 * href="http://forge.scilab.org/index.php/p/jlatexmath/">JLatexMath</a><br/>
 * It is based on <a href="http://www.heatonresearch.com/node/2868">Jeff Heaton
 * LatexExample</a><br/>
 * <br/>
 * Copyright (C) 2014 Michael Clavier <br/>
 * <br/>
 * This program is free software: you can redistribute it and/or modify <br/>
 * it under the terms of the GNU General Public License as published by <br/>
 * the Free Software Foundation, either version 3 of the License, or <br/>
 * (at your option) any later version. <br/>
 * <br/>
 * This program is distributed in the hope that it will be useful, <br/>
 * but WITHOUT ANY WARRANTY; without even the implied warranty of <br/>
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the <br/>
 * GNU General Public License for more details. <br/>
 * <br/>
 * You should have received a copy of the GNU General Public License <br/>
 * along with this program. If not, see <a
 * href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a> <br/>
 * <br/>
 */
public class JLatexIt {

    public static void main(final String[] args) {
        final MainFrame frame = new MainFrame();
        new MainFrameControler(frame);
    }

}
