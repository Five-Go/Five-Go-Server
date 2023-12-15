package com.fievgo.server.enums;

public enum FactorWeight {
    CAPTAIN_WEIGHT(4),
    FIRST_OFFICER_WEIGHT(2),
    MECHANIC_WEIGHT(2),
    AIRCRAFT_WEIGHT(4),
    AIRPORT_WEIGHT(2);

    private final Integer weight;

    FactorWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }
}
