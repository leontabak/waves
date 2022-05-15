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
    private final ColorMaker colorMaker;
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
        this.colorMaker = new ColorMaker( this.rng );
        this.numberOfColors = 64;
        this.numberOfSites = 6;
        this.colorPattern = ColorPattern.RANDOM;
        this.pointPattern = PointPattern.GRID;
        this.frequency = 40;

        this.palette = this.colorMaker.getPalette(
                ColorPattern.RANDOM, this.numberOfColors );

        //this.sites = gridSites();
        this.sites = polygonSites();
        //this.sites = randomSites();

        this.setBackground(Color.DARK_GRAY);
    } // WavesPanel()

    public void setNumberOfColors(int count) {
        this.numberOfColors = count;

        this.palette = this.colorMaker.getPalette(
                this.colorPattern,
                this.numberOfColors
        );

        this.repaint();
    } // setNumberOfColors( int )

    public void setColorPattern(ColorPattern pattern) {
        this.colorPattern = pattern;

        this.palette = this.colorMaker.getPalette(
                this.colorPattern,
                this.numberOfColors
        );

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

//    public Color shade(int x, int y,
//                       double u, double v,
//                       List<Ripple> ripples) {
//        double distances =
//                getSumOfHeights(u, v, ripples);
//
//        double t = (distances + 1) / 2.0;
//
//        Color continuous = weightedAverage(
//                palette.get(0), palette.get(1), t);
//
//        int index = (int) (t * palette.size());
//        Color discrete = palette.get(index);
//
//        double weight = 1.0;
//        Color c = weightedAverage(continuous, discrete, weight);
//
//        return c;
//    } // shade( int, int, double, double, List<Ripple> )

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

                double distances =
                        getSumOfHeights(u, v, ripples);

                double t = (distances + 1) / 2.0;

                int index = (int) (t * palette.size());
                Color c = palette.get(index);

                raster.setPixel(x, y, makeColorArray(c));
            } // for
        } // for

        g2D.drawImage(image, null, null);
    } // paintComponent( Graphics )
} // JPanel
