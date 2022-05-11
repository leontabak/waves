package edu.cornellcollege;

public enum ColorCount {

//    private static final String CC002 = "2";
//    private static final String CC004 = "4";
//    private static final String CC008 = "8";
//    private static final String CC016 = "16";
//    private static final String CC032 = "32";
//    private static final String CC064 = "64";
//    private static final String CC128 = "128";
//    private static final String CC256 = "256";

    CC002  ("2"),
    CC004  ("4"),
    CC008  ("8"),
    CC016  ("16"),
    CC032  ("32"),
    CC064  ("64"),
    CC128  ("128"),
    CC256  ("256");

    private String name;

    private ColorCount( String name ) {
        this.name = name;
    } // ColorCount( String, int )

    public String getName() {
        return this.name;
    } // getName()

} // ColorCount
