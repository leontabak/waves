package edu.cornellcollege;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class WavesPanel extends JPanel {

    public WavesPanel() {
        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    private double weightedAverage(
            double a,
            double b,
            double t) {
        return (1 - t) * a + t * b;
    } // weightedAverage( double, double, double )

    private Color weightedAverage(
            Color a,
            Color b,
            double t
    ) {
        double red = weightedAverage(
                a.getRed(), b.getRed(), t);
        double green = weightedAverage(
                a.getGreen(), b.getGreen(), t);
        double blue = weightedAverage(
                a.getBlue(),
                b.getBlue(),
                t);
        return new Color(
                Math.round(red),
                Math.round(green),
                Math.round(blue)
        );
    } // weightedAverage( Color, Color, double )

    private int [] makeColorArray( Color c ) {
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();

        int [] result = { red, green, blue };
        return result;
    } // makeColorArray( Color )

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2D = (Graphics2D) g;

        BufferedImage image = new BufferedImage(
                this.getWidth(),
                this.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        WritableRaster raster = image.getRaster();

        int xMin = raster.getMinX();
        int xMax = xMin + raster.getWidth();
        int yMin = raster.getYMin();
        int yMax = yMin + raster.getHeight();

        double uMin = -1.0;
        double uMax = +1.0;
        double vMin = -1.0;
        double vMax = +1.0;

        for( double y = yMin; y < yMax; y++ ) {
            for( double x = xMin, x < xMax; x++ ) {

            } // for
        } // for

        g2D.drawImage( image, null, null );
    } // paintComponent( Graphics )
} // JPanel
