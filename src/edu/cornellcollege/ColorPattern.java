package edu.cornellcollege;

public enum ColorPattern {
    BLACK_AND_WHITE ("Black & white"),
    GRADIENT ("Gradient (graduated blend of 2 colors)"),
    INTERLEAVED ("Interleaved (mix of 2 blends)"),
    RANDOM ("Random");

    private String name;

    private ColorPattern( String name ) {
        this.name = name;
    } // ColorPattern( String )

    public String getName() {
        return this.name;
    } // getName();
} // ColorPattern
