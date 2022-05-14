package edu.cornellcollege;

public enum FrequencyCount {
    FREQ02("2"),
    FREQ04("4"),
    FREQ08("8"),
    FREQ16("16"),
    FREQ24("24"),
    FREQ32("32"),
    FREQ48("48"),
    FREQ64("64"),
    FREQ80("80");

    private String name;

    private FrequencyCount(String name) {
        this.name = name;
    } // FrequencyCount( String )

    public String getName() {
        return this.name;
    } // getName()

} // FrequencyCount
