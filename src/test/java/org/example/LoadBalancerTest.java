package org.example;

import org.example.exceptions.AddressExistException;
import org.example.exceptions.MaxCapacityExceedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
                () -> loadBalancer.registerInstance(null, "A"));
    }

    @Test
    void registerInstance_when_addressIsNull_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance();
        assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.registerInstance(backendInstance, null));
    }

    @Test
    void registerInstance_when_addressIsBlank_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance();
        assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.registerInstance(backendInstance, "  "));
    }

    @Test
    void registerInstance_success() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance();
        assertDoesNotThrow(() -> loadBalancer.registerInstance(backendInstance, "A"));
    }

    @Test
    void registerInstance_when_maxCapacityExceeded_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());

        for (int i=0; i<10; i++){
            BackendInstance backendInstance = new BackendInstance();
            loadBalancer.registerInstance(backendInstance, "A"+i);
        }
        BackendInstance backendInstance = new BackendInstance();
        assertThrows(MaxCapacityExceedException.class,
                () -> loadBalancer.registerInstance(backendInstance, "A10"));
    }

    @Test
    void registerInstance_when_addressExists_then_exception() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        BackendInstance backendInstance = new BackendInstance();
        loadBalancer.registerInstance(backendInstance, "A");

        assertThrows(AddressExistException.class,
                () -> loadBalancer.registerInstance(backendInstance, "A"));
    }

    @Test
    void selectInstance_success() {
        LoadBalancer loadBalancer = new LoadBalancer(new SelectStrategyRandom());
        List<BackendInstance> backendInstanceList = new ArrayList<>();

        for (int i=0; i<10; i++){
            BackendInstance backendInstance = new BackendInstance();
            backendInstanceList.add(backendInstance);
            loadBalancer.registerInstance(backendInstance, "A"+i);
        }
        Optional<BackendInstance> backendInstanceOpt = loadBalancer.selectInstance();
        assertTrue(backendInstanceOpt.isPresent());
        BackendInstance backendInstance = backendInstanceOpt.get();
        assertTrue(backendInstanceList.contains(backendInstance));
    }
}