package edu.cornellcollege;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class WavesPanel extends JPanel {

    // TO-DO: Experiment with different colors.
    // TO-DO: Create a List of more than 2 colors.
    private static final Color FG_COLOR =
            new Color( 236, 228, 232 );
    private static final Color BG_COLOR =
            new Color( 72, 64, 140 );

    public WavesPanel() {
        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    /**
     * Compute the weighted average of 2 numbers.
     *
     * @param a is one of the numbers to averaged.
     * @param b is the other number to be averaged.
     * @param t is the weight (0 <= t <= 1)
     * @return the weighted average of a and b
     */
    public double weightedAverage(
            double a,
            double b,
            double t) {
        return (1 - t) * a + t * b;
    } // weightedAverage( double, double, double )

    /**
     * Compute the weighted average of 2 colors.
     *
     * @param a is one color to be averaged.
     * @param b is the other color to be averaged.
     * @param t is the weight (0 <= t <= 1).
     * @return the weighted average of the 2 colors.
     */
    public Color weightedAverage(
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
                (int) (red),
                (int) (green),
                (int) (blue)
        );
    } // weightedAverage( Color, Color, double )

    /**
     * Construct an array of integer primary color values
     * from a Color object.
     *
     * @param c is the Color.
     * @return an array of 3 integers, all in the interval
     * [0, 255].
     */
    public int[] makeColorArray(Color c) {
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

        // Here are the dimensions (measured in pixels)
        // of the panel in which the program draws
        // a picture.
        // We call these device coordinates.
        int xMin = raster.getMinX();
        int xMax = xMin + raster.getWidth();
        int yMin = raster.getMinY();
        int yMax = yMin + raster.getHeight();

        double xWidth = raster.getWidth();
        double yHeight = raster.getHeight();

        // Here are the dimensions of our world.
        // These dimensions are our choice.
        // We choose dimensions for our own convenience.
        // These are the world coordinates.
        double uMin = -1.0;
        double uMax = +1.0;
        double vMin = -1.0;
        double vMax = +1.0;

        double uWidth = uMax - uMin;
        double vHeight = vMax - vMin;

        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {

                // Given coordinates of a point in the
                // x-y coordinate system (the devices
                // coordinates) find the coordinates of
                // the corresponding point in our u-v
                // coordinate system (the world
                // coordinates).
                double u = uMin
                        + (x - xMin) / xWidth * uWidth;
                double v = vMin
                        + (y - yMin) / yHeight * vHeight;

                // TO-DO: If you wish to create an image
                // of some part of the Mandelbrot set...
                // 1) Define a class that models a
                //    complex number.
                // 2) Create a complex number whose
                //    real component is u and whose
                //    imaginary component is v.
                //
                //    Complex z = new Complex( 0.0, 0.0 );
                //    Complex c = new Complex( u, v );
                //
                //  3) Write a loop that repeatedly updates
                //     the value of z.
                //
                //    int i = 0;
                //    while( z.magnitudeSquare() < 4.0 &&
                //            i < 50 ) {
                //        z = z.multiply(z);
                //        z = z.add(c);
                //        i = i + 1;
                //    } // while
                //
                //  4) Let the value of i when this
                //     loop ends determine the color
                //     of the pixel.

//                double t = (x - xMin)/xWidth;

                // TO-DO: Here, I have assumed that
                // the pebble has fallen into the
                // center of our world. Can you
                // modify this program to show the
                // ripples that move outward from a
                // point that is not at the center of
                // the world, but somewhere else?

                // Distance of a point from the center
                // of our world.
                double distance =
                        Math.sqrt( u * u + v * v );

                // A wave has 3 properties:
                //   * amplitude is the height of wave
                //   * frequency is spacing between waves
                //   * phase is position of a crest
                //     relative to some fixed location
                // TO-DO: Experiment with different
                // values of amplitude, frequency, and
                // phase.
                double amplitude = 1.0;
                double frequency = 48.0;
                double phase = 0.0;

                double t = amplitude * Math.sin(
                        frequency * distance + phase);
                t = (t + 1)/2.0;

                Color c = weightedAverage(
                        FG_COLOR, BG_COLOR, t );

                // TO-DO: Can you draw overlapping
                // ripples from several stones?

                raster.setPixel( x, y, makeColorArray(c));

            } // for
        } // for

        g2D.drawImage(image, null, null);
    } // paintComponent( Graphics )
} // JPanel
