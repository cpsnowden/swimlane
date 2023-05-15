package org.cps.swimlane.operator.core;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A registry responsible for collating all supported venue operators
 */
@ApplicationScoped
public class PoolVenueOperatorRegistry {

    private final Map<String, PoolVenueOperator> operators;

    @Inject
    public PoolVenueOperatorRegistry(@All List<PoolVenueOperator> poolVenueOperators) {
        this.operators = poolVenueOperators.stream()
                .collect(Collectors.toMap(PoolVenueOperator::getOperatorId, Function.identity()));

    }

    public Optional<PoolVenueOperator> findWithName(String operatorName) {
        return Optional.ofNullable(operators.get(operatorName));
    }

    /**
     * @throws NotFoundException if the operator id is unknown
     */
    public PoolVenueOperator findWithNameOrThrow(String operatorId) {
        return findWithName(operatorId).orElseThrow(() -> new NotFoundException("Unknown operator " + operatorId));
    }

    public Stream<PoolVenueOperator> all() {
        return operators.values().stream();
    }

}
