package edu.cornellcollege;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WavesPanel extends JPanel {

    private final Random rng;
    private final List<Color> palette;
    private final List<Point2D> sites;
    private final double frequency;

    public WavesPanel() {
        this.rng = new Random();
        this.frequency = 16;

        int numberOfColors = 20;
        int min = 64;
        this.palette = randomPalette(numberOfColors, min);

        int number = 12;
        this.sites = sites(number);

        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    private final List<Color> randomPalette(
            int numberOfColors,
            int min) {

        List<Color> result = new ArrayList<>();

        for (int i = 0; i < numberOfColors; i++) {
            int red = min
                    + this.rng.nextInt(256 - min);
            int green = min
                    + this.rng.nextInt(256 - min);
            int blue = min
                    + this.rng.nextInt(256 - min);
            Color c = new Color(red, green, blue);
            result.add(c);
        } // for

        return result;
    } // randomPalette()

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

        int[] result = {red, green, blue};
        return result;
    } // makeColorArray( Color )

    public final List<Point2D> sites(int number) {
        List<Point2D> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            double u = rng.nextDouble();
            double v = rng.nextDouble();

            Point2D p = new Point2D.Double(u, v);

            result.add(p);
        } // for

        return result;
    } // sites( int )

    public final List<Ripple> ripples(
            Rectangle2D bounds) {
        List<Ripple> ripples = new ArrayList<>();
        for (Point2D p : this.sites) {
            double u = bounds.getMinX()
                    + p.getX() * bounds.getWidth();
            double v = bounds.getMinY()
                    + p.getY() * bounds.getHeight();

            Point2D center = new Point2D.Double(u, v);

            Ripple r = new Ripple(center, frequency);
            ripples.add(r);
        } // for

        return ripples;
    } // ripples()

    public double getSumOfHeights(double u, double v,
                                  List<Ripple> ripples) {
        double distances = 0.0;
        Point2D p = new Point2D.Double(u, v);
        for (Ripple r : ripples) {
            distances += r.getHeight(p);
        } // for
        distances /= ripples.size();

        return distances;
    } // getSumOfHeights()

    public Color shade(int x, int y,
                       double u, double v,
                       List<Ripple> ripples) {
        double distances =
                getSumOfHeights(u, v, ripples);

        double t = (distances + 1) / 2.0;

        Color continuous = weightedAverage(
                palette.get(0), palette.get(1), t);

        int index = (int) (t * palette.size());
        Color discrete = palette.get(index);

        double weight = 1.0;
        Color c = weightedAverage(continuous, discrete, weight);

        return c;
    } // shade( int, int, double, double, List<Ripple> )

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

        Rectangle2D bounds = new Rectangle2D.Double(
                uMin, vMin, uWidth, vHeight
        );
        List<Ripple> ripples = ripples(bounds);

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

                Color c = shade(x, y, u, v, ripples);
                raster.setPixel(x, y, makeColorArray(c));
            } // for
        } // for

        g2D.drawImage(image, null, null);
    } // paintComponent( Graphics )
} // JPanel
