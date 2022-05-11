package edu.cornellcollege;

public enum PointCount {
    PC03 ("3"),
    PC04 ("4"),
    PC05 ("5"),
    PC06 ("6"),
    PC07 ("7"),
    PC08 ("8"),
    PC09 ("9"),
    PC10 ("10"),
    PC11 ("11"),
    PC12 ("12");

    private String name;

    private PointCount( String name ) {
        this.name = name;
    } // ColorCount( String, int )

    public String getName() {
        return this.name;
    } // getName()

    } // PointCount
