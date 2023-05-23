package org.cps.swimlane.operator.plugin.everyoneactive;

import java.util.HashSet;
import java.util.Set;

public class VenueDetails {

    private final String name;
    private final String slug;

    private final Set<String> sessionScope = new HashSet<>();

    public VenueDetails(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public VenueDetails withScope(String scope) {
        sessionScope.add(scope);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Set<String> getSessionScope() {
        return sessionScope;
    }
}
