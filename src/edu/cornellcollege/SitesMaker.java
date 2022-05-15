package edu.cornellcollege;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SitesMaker {

    private Random rng;

    public SitesMaker(Random rng) {
        this.rng = rng;
    } // SitesMaker( Random )

    public List<Point2D> getSites(
            PointPattern pattern,
            int numberOfSites) {
        switch (pattern) {
            case GRID:
                return gridSites(numberOfSites);
            case POLYGON:
                return polygonSites(numberOfSites);
            case RANDOM:
                return randomSites(numberOfSites);
            case STAR_POLYGON:
            default:
                return starPolygonSites(numberOfSites);
        } // switch
    }// getSites( PointPattern, int )

    public final List<Point2D> randomSites(
            int numberOfSites
    ) {
        List<Point2D> result = new ArrayList<>();

        for (int i = 0; i < numberOfSites; i++) {
            double u = 2 * rng.nextDouble() - 1;
            double v = 2 * rng.nextDouble() - 1;

            Point2D p = new Point2D.Double(u, v);

            result.add(p);
        } // for

        return result;
    } // randomSites()

    public final List<Point2D> gridSites(
            int numberOfSites
    ) {
        List<Point2D> result = new ArrayList<>();

        double xMin = -0.8;
        double xMax = +0.8;
        double yMin = -0.8;
        double yMax = +0.8;

        int n = numberOfSites;

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

    public final List<Point2D> polygonSites(
            int numberOfSites
    ) {
        List<Point2D> result = new ArrayList<>();

        int n = numberOfSites;
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

    public final List<Point2D> starPolygonSites(
            int numberOfSites
    ) {
        List<Point2D> result = new ArrayList<>();

        int n = numberOfSites;
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

} // SitesMaker
