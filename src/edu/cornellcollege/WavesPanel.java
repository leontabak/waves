package edu.cornellcollege;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WavesPanel extends JPanel {

    public WavesPanel() {
        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        java.awt.Graphics2D g2D = (Graphics2D) g;
    } // paintComponent( Graphics )
} // JPanel
