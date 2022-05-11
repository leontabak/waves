package edu.cornellcollege;

public enum FrequencyCount {
    FREQ02 ("2"),
    FREQ04 ("4"),
    FREQ08 ("8"),
    FREQ16 ("16"),
    FREQ32 ("32"),
    FREQ64 ("64");

    private String name;

    private FrequencyCount( String name ) {
        this.name = name;
    } // FrequencyCount( String )

    public String getName() {
        return this.name;
    } // getName()

    public int getValue() {
        return Integer.parseInt( this.name );
    } // getValue()

} // FrequencyCount
