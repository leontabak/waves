package edu.cornellcollege;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorMaker {
    //private List<Color> palette;

    private static final int MIN = 128;
    private static final int MAX = 255;

    private static final int DARK_LO = 64;
    private static final int DARK_HI = 80;
    private static final int LIGHT_LO = 192;
    private static final int LIGHT_HI = 255;

    private Random rng;

    public ColorMaker(Random rng ) {
       this.rng = rng;
    } // Palette( Random)

    public List<Color> getPalette(ColorPattern cp,
                                  int numberOfColors ) {
        switch (cp) {
            case BLACK_AND_WHITE:
                return blackAndWhitePalette( numberOfColors );
            case GRADIENT:
                return gradientPalette(
                        DARK_LO,
                        DARK_HI,
                        LIGHT_LO,
                        LIGHT_HI,
                        numberOfColors);
            case INTERLEAVED:
                return interleavedPalette(
                        DARK_LO,
                        DARK_HI,
                        LIGHT_LO,
                        LIGHT_HI,
                        numberOfColors
                );
            case RANDOM:
            default:
                return randomPalette(
                        MIN,
                        MAX,
                        numberOfColors);
        } // switch

    } // getPalette()

    private Color randomColor(int min, int max) {

        int red = min
                + this.rng.nextInt(max - min);
        int green = min
                + this.rng.nextInt(max - min);
        int blue = min
                + this.rng.nextInt(max - min);

        return new Color(red, green, blue);
    } // randomColor( int, int )

    private List<Color> blackAndWhitePalette(
            int numberOfColors
    ) {
        List<Color> result = new ArrayList<>();

        for (int i = 0; i < numberOfColors; i++) {
            if (i % 2 == 0) {
                result.add(Color.BLACK);
            } // if
            else {
                result.add(Color.WHITE);
            } // else
        } // for

        return result;
    } // blackAndWhitePalette()

    private List<Color> gradientPalette(
            int darkLo,
            int darkHi,
            int lightLo,
            int lightHi,
            int numberOfColors) {
        List<Color> result = new ArrayList<>();

        Color dark = randomColor(darkLo, darkHi);
        Color light = randomColor(lightLo, lightHi);

        int n = numberOfColors;

        for (int i = 0; i < n; i++) {
            double t = ((double) i) / n;

            Color c = weightedAverage(dark, light, t);
            result.add(c);
        } // for

        return result;
    } // gradientPalette( int, int, int, int )

    private List<Color> interleavedPalette(
            int darkLo,
            int darkHi,
            int lightLo,
            int lightHi,
            int numberOfColors
    ) {
        List<Color> result = new ArrayList<>();

        Color dark0 = randomColor(darkLo, darkHi);
        Color light0 = randomColor(lightLo, lightHi);
        Color dark1 = randomColor(darkLo, darkHi);
        Color light1 = randomColor(lightLo, lightHi);

        int n = numberOfColors;

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

    private List<Color> randomPalette(
            int min,
            int max,
            int numberOfColors) {

        List<Color> result = new ArrayList<>();

        for (int i = 0; i < numberOfColors; i++) {
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

} // ColorMaker
