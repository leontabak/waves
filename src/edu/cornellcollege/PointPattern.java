package edu.cornellcollege;

public enum PointPattern {
    GRID ("Points on a grid"),
    POLYGON ("Vertices of a regular polygon"),
    STAR_POLYGON ("Vertices of a star polygon"),
    RANDOM ("Points scattered randomly");

    private String name;

    private PointPattern( String name ) {
        this.name = name;
    } // PointPattern( String )

    public String getName() {
        return this.name;
    } // getName()

} // PointPattern
