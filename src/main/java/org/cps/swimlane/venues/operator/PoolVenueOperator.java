package org.cps.swimlane.venues.operator;

import org.cps.swimlane.model.LaneAvailability;
import org.cps.swimlane.model.Venue;

import java.time.LocalDate;
import java.util.List;

public interface PoolVenueOperator {

    /**
     * Get the lane availability for a given venue for a given date
     */
    List<LaneAvailability> getAvailability(String venueSlug, LocalDate date);

    List<Venue> getVenues();

    String getOperatorName();
}
