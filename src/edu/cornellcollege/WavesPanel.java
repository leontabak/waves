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
    private final SitesMaker sitesMaker;
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
        this.sitesMaker = new SitesMaker( this.rng );
        this.numberOfSites = 6;
        this.colorPattern = ColorPattern.RANDOM;
        this.pointPattern = PointPattern.GRID;
        this.frequency = 40;

        this.palette = this.colorMaker.getPalette(
                ColorPattern.RANDOM, this.numberOfColors );

        this.sites = this.sitesMaker.getSites(
                PointPattern.RANDOM, this.numberOfColors
        );

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

        this.sites = this.sitesMaker.getSites(
                this.pointPattern,
                this.numberOfSites
        );

        this.repaint();
    } // setNumberOfPoints( int )

    public void setPointPattern(PointPattern pattern) {
        this.pointPattern = pattern;

        this.sites = this.sitesMaker.getSites(
                this.pointPattern,
                this.numberOfSites
        );

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

        //distances = Math.pow( distances, 0.5 );
        //distances = Math.pow( distances, 3.0 );

        //distances = 1.0 / (1.0 + Math.exp( -distances) );

        return distances;
    } // getSumOfHeights()

    public void writeToFile( File file ) {
        try {
            ImageIO.write(this.image,
                    FILE_FORMAT,
                    file);
        } // try
        catch (IOException e) {
            System.err.println("Cannot write to file");
        } // catch( IOException)
    } // writeToFile( File )

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
