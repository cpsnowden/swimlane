package org.cps.swimlane.resource;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.cps.swimlane.model.Venue;
import org.cps.swimlane.operator.core.PoolVenueOperator;
import org.cps.swimlane.operator.core.PoolVenueOperatorRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.cps.swimlane.resource.RestAssuredTestUtils.get;
import static org.mockito.Mockito.when;

@QuarkusTest
public class VenuesResourceTest {

    PoolVenueOperator operator1;

    PoolVenueOperator operator2;

    @BeforeEach
    public void setUp() {
        operator1 = Mockito.mock(PoolVenueOperator.class);
        operator2 = Mockito.mock(PoolVenueOperator.class);
        when(operator1.getOperatorId()).thenReturn("operator1");
        when(operator2.getOperatorId()).thenReturn("operator2");
        QuarkusMock.installMockForType(
                new PoolVenueOperatorRegistry(Arrays.asList(operator1, operator2)),
                PoolVenueOperatorRegistry.class
        );
    }

    @Test
    public void testVenuesApi() {
        when(operator1.getVenues()).thenReturn(Arrays.asList(
                new Venue("Venue 1", "venue1", "operator1"),
                new Venue("Venue 2", "venue2", "operator1")
        ));
        when(operator2.getVenues()).thenReturn(Arrays.asList(
                new Venue("Venue A", "venueA", "operator2")
        ));

        Venue[] venues = get("/venues", Venue[].class);

        Venue venue1 = venues[0];
        Assertions.assertEquals("operator1", venue1.getOperator());
        Assertions.assertEquals("Venue 1", venue1.getName());
        Assertions.assertEquals("venue1", venue1.getSlug());
        Venue venue2 = venues[1];
        Assertions.assertEquals("operator1", venue2.getOperator());
        Assertions.assertEquals("Venue 2", venue2.getName());
        Assertions.assertEquals("venue2", venue2.getSlug());
        Venue venueA = venues[2];
        Assertions.assertEquals("operator2", venueA.getOperator());
        Assertions.assertEquals("Venue A", venueA.getName());
        Assertions.assertEquals("venueA", venueA.getSlug());
    }



}