package edu.cornellcollege;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class gives us a rectangular region
 * within a window in which we can draw a picture.
 */
public class WavesPanel extends JPanel {

    private static final String FILE_FORMAT = "png";
    private static final String FILE_NAME = "picture.png";

    private final Random rng;
    private List<Color> palette;
    private List<Point2D> sites;
    private int numberOfColors;
    private int numberOfSites;
    private ColorPattern colorPattern;
    private PointPattern pointPattern;
    private double frequency;
    private BufferedImage image;

    public WavesPanel() {
        this.rng = new Random();
        this.numberOfColors = 64;
        this.numberOfSites = 6;
        this.colorPattern = ColorPattern.RANDOM;
        this.pointPattern = PointPattern.GRID;
        this.frequency = 40;

        int min = 64;
        this.palette = randomPalette(min, 256 - min);
//        this.palette = gradientPalette(
//                64, 96,
//                128, 255);
//        this.palette = interleavedPalette(
//                40, 80,
//                192, 255);

        //this.sites = gridSites();
        this.sites = polygonSites();
        //this.sites = randomSites();

        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    public void setNumberOfColors(int count) {
        this.numberOfColors = count;

        int min = 64;

        if (this.colorPattern == ColorPattern.GRADIENT) {
            this.palette = gradientPalette(
                    64, 96,
                    128, 255);
        } // if
        else if (this.colorPattern == ColorPattern.INTERLEAVED) {
            this.palette = interleavedPalette(
                    40, 80,
                    192, 255);
        } // else if
        else if (this.colorPattern == ColorPattern.RANDOM) {
            this.palette = randomPalette(min, 256 - min);
        } // else if


        this.repaint();
    } // setNumberOfColors( int )

    public void setColorPattern(ColorPattern pattern) {
        this.colorPattern = pattern;

        int min = 64;
        if (this.colorPattern == ColorPattern.BLACK_AND_WHITE) {
            this.palette = blackAndWhitePalette();
        } // if
        else if (this.colorPattern == ColorPattern.GRADIENT) {
            this.palette = gradientPalette(
                    64, 96,
                    128, 255);
        } // if
        else if (this.colorPattern == ColorPattern.INTERLEAVED) {
            this.palette = interleavedPalette(
                    40, 80,
                    192, 255);
        } // else if
        else if (this.colorPattern == ColorPattern.RANDOM) {
            this.palette = randomPalette(min, 256 - min);
        } // else if

        this.repaint();
    } // setColorPattern( ColorPattern )

    public void setNumberOfPoints(int count) {
        this.numberOfSites = count;

        if (this.pointPattern == PointPattern.GRID) {
            this.sites = gridSites();
        } // if
        else if (this.pointPattern == PointPattern.POLYGON) {
            this.sites = polygonSites();
        } // else if
        else if (this.pointPattern == PointPattern.RANDOM) {
            this.sites = randomSites();
        } // else if
        else if (this.pointPattern == PointPattern.STAR_POLYGON) {
            this.sites = starPolygonSites();
        } // else if

        this.repaint();
    } // setNumberOfPoints( int )

    public void setPointPattern(PointPattern pattern) {
        this.pointPattern = pattern;

        if (this.pointPattern == PointPattern.GRID) {
            this.sites = gridSites();
        } // if
        else if (this.pointPattern == PointPattern.POLYGON) {
            this.sites = polygonSites();
        } // else if
        else if (this.pointPattern == PointPattern.STAR_POLYGON) {
            this.sites = starPolygonSites();
        } // else if
        else if (this.pointPattern == PointPattern.RANDOM) {
            this.sites = randomSites();
        } // else if

        this.repaint();
    } // setPointPattern( PointPattern )

    public void setFrequency(int frequency) {
        this.frequency = frequency;
        this.repaint();
    } // setFrequency( int )

    private Color randomColor(int min, int max) {

        int red = min
                + this.rng.nextInt(max - min);
        int green = min
                + this.rng.nextInt(max - min);
        int blue = min
                + this.rng.nextInt(max - min);

        return new Color(red, green, blue);
    } // randomColor( int, int )

    private final List<Color> blackAndWhitePalette() {
        List<Color> result = new ArrayList<>();

        for (int i = 0; i < this.numberOfColors; i++) {
            if (i % 2 == 0) {
                result.add(Color.BLACK);
            } // if
            else {
                result.add(Color.WHITE);
            } // else
        } // for

        return result;
    } // blackAndWhitePalette()

    private final List<Color> gradientPalette(
            int darkLo,
            int darkHi,
            int lightLo,
            int lightHi) {
        List<Color> result = new ArrayList<>();

        Color dark = randomColor(darkLo, darkHi);
        Color light = randomColor(lightLo, lightHi);

        int n = this.numberOfColors;

        for (int i = 0; i < n; i++) {
            double t = ((double) i) / n;

            Color c = weightedAverage(dark, light, t);
            result.add(c);
        } // for

        return result;
    } // gradientPalette( int, int, int, int )

    private final List<Color> interleavedPalette(
            int darkLo,
            int darkHi,
            int lightLo,
            int lightHi
    ) {
        List<Color> result = new ArrayList<>();

        Color dark0 = randomColor(darkLo, darkHi);
        Color light0 = randomColor(lightLo, lightHi);
        Color dark1 = randomColor(darkLo, darkHi);
        Color light1 = randomColor(lightLo, lightHi);

        int n = this.numberOfColors;

        for (int i = 0; i < n; i++) {
            double t = ((double) i) / n;

            if (i % 2 == 0) {
                Color c = weightedAverage(dark0, light0, t);
                result.add(c);
            } // if
            else {
                Color c = weightedAverage(light1, dark1, t);
                result.add(c);
            } // else
        } // for

        return result;
    } // interleavedPalette( int, int, int, int )

    private final List<Color> randomPalette(
            int min,
            int max) {

        List<Color> result = new ArrayList<>();

        for (int i = 0; i < this.numberOfColors; i++) {
            Color c = randomColor(min, max);
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

    public final List<Point2D> randomSites() {
        List<Point2D> result = new ArrayList<>();

        for (int i = 0; i < this.numberOfSites; i++) {
            double u = 2 * rng.nextDouble() - 1;
            double v = 2 * rng.nextDouble() - 1;

            Point2D p = new Point2D.Double(u, v);

            result.add(p);
        } // for

        return result;
    } // randomSites()

    public final List<Point2D> gridSites() {
        List<Point2D> result = new ArrayList<>();

        double xMin = -0.8;
        double xMax = +0.8;
        double yMin = -0.8;
        double yMax = +0.8;

        int n = this.numberOfSites;

        for (int i = 0; i < n; i++) {
            double vertical = ((double) i) / n;
            double y = yMin + vertical * (yMax - yMin);

            for (int j = 0; j < n; j++) {
                double horizontal = ((double) j) / n;
                double x = xMin + horizontal * (xMax - xMin);

                Point2D p = new Point2D.Double(x, y);
                result.add(p);
            } // for
        } // for

        return result;
    } // gridSites()

    public final List<Point2D> polygonSites() {
        List<Point2D> result = new ArrayList<>();

        int n = this.numberOfSites;
        double radius = (Math.sqrt(5) + 1) / 2;

        for (int i = 0; i < n; i++) {
            double fraction = ((double) i) / n;
            double angle = fraction * 2.0 * Math.PI;

            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            Point2D p = new Point2D.Double(x, y);

            result.add(p);
        } // for

        return result;
    } // polygonSites()

    public final List<Point2D> starPolygonSites() {
        List<Point2D> result = new ArrayList<>();

        int n = this.numberOfSites;
        double radius = (Math.sqrt(5) + 1) / 2;

        for (int i = 0; i < 2 * n; i++) {
            double fraction = ((double) i) / n;
            double angle = fraction * 2.0 * Math.PI;

            double r = radius;
            if (i % 2 == 0) {
                r *= 2 / (Math.sqrt(5) + 1);
            }
            double x = r * Math.cos(angle);
            double y = r * Math.sin(angle);

            Point2D p = new Point2D.Double(x, y);

            result.add(p);
        } // for

        return result;
    } // starPolygonSites()

    public final List<Ripple> ripples(
            Rectangle2D bounds) {
        List<Ripple> ripples = new ArrayList<>();
        for (Point2D p : this.sites) {
            double u = bounds.getMinX()
                    + (p.getX() + 1) / 2 * bounds.getWidth();
            double v = bounds.getMinY()
                    + (p.getY() + 1) / 2 * bounds.getHeight();

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

    public void writeToFile() {
        try {
            ImageIO.write(this.image,
                    FILE_FORMAT,
                    new File(FILE_NAME));

        } // try
        catch (IOException e) {
            System.err.println("Cannot write to file");
        } // catch( IOException)
    } // writeToFile()

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2D = (Graphics2D) g;

        this.image = new BufferedImage(
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
