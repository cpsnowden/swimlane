package org.cps.swimlane.venues.better.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * {
 *       "starts_at": {
 *         "format_12_hour": "9:00pm",
 *         "format_24_hour": "21:00"
 *       },
 *       "ends_at": {
 *         "format_12_hour": "9:50pm",
 *         "format_24_hour": "21:50"
 *       },
 *       "duration": "50min",
 *       "price": {
 *         "is_estimated": false,
 *         "formatted_amount": "£5.92"
 *       },
 *       "composite_key": "dd617086",
 *       "timestamp": 1681848000,
 *       "booking": null,
 *       "action_to_show": {
 *         "status": "BOOK",
 *         "reason": null
 *       },
 *       "category_slug": "adult-lane-swimming",
 *       "date": "2023-04-18",
 *       "venue_slug": "clapham-leisure-centre",
 *       "location": "Main Pool Lane 1, Clapham Leisure Centre",
 *       "spaces": 4,
 *       "name": "Adult Lane Swimming",
 *       "allows_anonymous_bookings": false
 *     }
 */

public class Activity {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("starts_at")
    private FormatTime startsAt;

    @JsonProperty("ends_at")
    private FormatTime endsAt;

    @JsonProperty("location")
    private String location;

    @JsonProperty("spaces")
    private int spaces;

    @JsonProperty("venue_slug")
    private String venueSlug;

    public String getLocation() {
        return location;
    }

    public int getSpaces() {
        return spaces;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getVenueSlug() {
        return venueSlug;
    }

    public FormatTime getStartsAt() {
        return startsAt;
    }

    public FormatTime getEndsAt() {
        return endsAt;
    }
}
