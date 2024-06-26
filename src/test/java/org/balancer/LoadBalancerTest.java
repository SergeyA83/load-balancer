package org.balancer;

import org.balancer.exceptions.AddressExistException;
import org.balancer.exceptions.MaxCapacityExceedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerTest {
    @Test
    void registerInstance_when_instanceIsNull_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.register(null));
    }

    @Test
    void registerInstance_when_addressIsNull_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance(null);
        assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.register(backendInstance));
    }

    @Test
    void registerInstance_when_addressIsBlank_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance("  ");
        assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.register(backendInstance));
    }

    @Test
    void registerInstance_success() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance("A");
        assertDoesNotThrow(() -> loadBalancer.register(backendInstance));
    }

    @Test
    void registerInstance_when_maxCapacityExceeded_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());

        for (int i=0; i<10; i++){
            BackendInstance backendInstance = new BackendInstance("A"+i);
            loadBalancer.register(backendInstance);
        }
        BackendInstance backendInstance = new BackendInstance("A10");
        assertThrows(MaxCapacityExceedException.class,
                () -> loadBalancer.register(backendInstance));
    }

    @Test
    void registerInstance_when_addressExists_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance("A");
        loadBalancer.register(backendInstance);

        assertThrows(AddressExistException.class,
                () -> loadBalancer.register(backendInstance));
    }

    @Test
    void selectInstance_success() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        List<BackendInstance> backendInstanceList = new ArrayList<>();

        for (int i=0; i<10; i++){
            BackendInstance backendInstance = new BackendInstance("A"+i);
            backendInstanceList.add(backendInstance);
            loadBalancer.register(backendInstance);
        }
        Optional<BackendInstance> backendInstanceOpt = loadBalancer.select();
        assertTrue(backendInstanceOpt.isPresent());
        BackendInstance backendInstance = backendInstanceOpt.get();
        assertTrue(backendInstanceList.contains(backendInstance));
    }
}