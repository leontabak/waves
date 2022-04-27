package edu.cornellcollege;

import java.awt.geom.Point2D;

public class Ripple {
    private Point2D center;
    private double amplitude;
    private double frequency;
    private double phase;

    public Ripple(Point2D center) {
        this.setCenter(center);
        this.setAmplitude(1.0);
        this.setFrequency(1.0);
        this.setPhase(0.0);
    } // Ripple( double, double )

    public Ripple(Point2D center, double frequency) {
        this(center);
        this.setFrequency(frequency);
    } // Ripple( double, double, double )

    public Point2D getCenter() {
        return this.center;
    } // getCenter()

    public void setCenter(Point2D center) {
        this.center = center;
    } // setCenter( Point2D )

    public double getAmplitude() {
        return this.amplitude;
    } // getAmplitude()

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    } // setAmplitude( double )

    public double getFrequency() {
        return this.frequency;
    } // getFrequency()

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    } // setFrequency( double )

    public double getPhase() {
        return this.phase;
    } // getPhase()

    public void setPhase(double phase) {
        this.phase = phase;
    } // setPhase( double )

    public double getHeight(Point2D p) {
        double distance = this.getCenter().distance(p);

        return this.getAmplitude() * Math.sin(
                this.getFrequency() * distance
                        + this.getPhase());
    } // getHeight( Point2D )
} // Ripple
