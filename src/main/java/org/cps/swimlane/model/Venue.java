package org.cps.swimlane.model;

public class Venue {

    private final String name;
    private final String slug;

    private final String operator;


    public Venue(String name, String slug, String operator) {
        this.name = name;
        this.slug = slug;
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getOperator() {
        return operator;
    }
}
