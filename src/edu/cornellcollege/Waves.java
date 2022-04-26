package edu.cornellcollege;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Show students another kind of bit-mapped graphics.
 *
 * <ul>
 *     <li>Apples</li>
 *     <li>Peaches</li>
 *     <li>Pears</li>
 * </ul>
 *
 * @version 26 April 2022
 * @author Leon Hannah Tabak
 */
public class Waves extends JFrame {

    private static final int FRAME_WIDTH = 768;
    private static final int FRAME_HEIGHT = 768;
    private static final String FRAME_TITLE = "Waves";

    public Waves() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        WavesPanel panel = new WavesPanel();
        pane.add(panel);

        this.setVisible(true);
    } // Waves()

    public static void main(String[] args) {
        Waves waves = new Waves();
    } // main( String [] )
} // Waves
