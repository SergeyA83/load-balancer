package org.balancer;

import org.balancer.exceptions.AddressExistException;
import org.balancer.exceptions.MaxCapacityExceedException;

import java.util.*;

public class LoadBalancer {
    private static final int MAX_CAPACITY = 10;
    private final List<BackendInstance> instancesList = new ArrayList<>();
    private final SelectStrategy selectStrategy;

    public LoadBalancer(SelectStrategy selectStrategy) {
        this.selectStrategy = selectStrategy;
    }

    public void register(BackendInstance backendInstance){
        if (backendInstance==null){
            throw new IllegalArgumentException("Instance can't be null");
        }
        if (backendInstance.getAddress()==null || backendInstance.getAddress().isBlank()){
            throw new IllegalArgumentException("Address can't be null or empty");
        }

        synchronized (this) {
            if (instancesList.size()==MAX_CAPACITY) {
                throw new MaxCapacityExceedException();
            }

            if (instancesList.contains(backendInstance)) {
                throw new AddressExistException();
            }
            instancesList.add(backendInstance);
        }
    }

    public Optional<BackendInstance> select(){
        return selectStrategy.selectInstance(instancesList);
    }
}
