package org.cps.swimlane.operator.core;

import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;

import java.time.LocalDate;
import java.util.List;

public interface PoolVenueOperator {

    /**
     * Get the unique operator id
     */
    String getOperatorId();

    /**
     * Get a list of supported venues for the given operator
     */
    List<Venue> getVenues();

    /**
     * Get the lane availability for a given venue for a given date
     */
    List<LaneAvailability> getAvailability(String venueSlug, LocalDate date);
}
