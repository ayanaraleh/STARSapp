package model;

// Represents the 6 most common types of telescopes and their corresponding 
// experience levels

public enum TelescopeType {
    REFRACTOR("BEGINNER"),
    REFLECTOR("INTERMEDIATE"),
    NEWTONIAN("INTERMEDIATE"),
    MAKSUTOV_CASSEGRAIN("ADVANCED"),
    RITCHEY_CHRETIEN("ADVANCED"),
    BINOCULARS("BEGINNER");

    private final String experienceLevel;

    TelescopeType(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    // EFFECTS: Returns the experience level needed to operate the
    // telescope type

    public String getExperienceLevel() {
        return experienceLevel;

    }

}
