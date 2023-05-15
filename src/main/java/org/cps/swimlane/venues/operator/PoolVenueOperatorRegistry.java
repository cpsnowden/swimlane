package org.cps.swimlane.venues.operator;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PoolVenueOperatorRegistry {

    private final Map<String, PoolVenueOperator> operators;

    @Inject
    public PoolVenueOperatorRegistry(@All List<PoolVenueOperator> poolVenueOperators) {
        this.operators = poolVenueOperators.stream().collect(Collectors.toMap(PoolVenueOperator::getOperatorName, Function.identity()));

    }

    public Optional<PoolVenueOperator> findWithName(String operatorName) {
        return Optional.ofNullable(operators.get(operatorName));
    }

    public Stream<PoolVenueOperator> all() {
        return operators.values().stream();
    }

}
