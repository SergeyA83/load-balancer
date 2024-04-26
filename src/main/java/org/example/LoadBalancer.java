package org.example;

import org.example.exceptions.AddressExistException;
import org.example.exceptions.MaxCapacityExceedException;

import java.util.*;

public class LoadBalancer {
    private static final int MAX_CAPACITY = 10;
    private final Map<String, BackendInstance> addressToInstanceMap = new HashMap<>();
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
            if (addressToInstanceMap.size()==MAX_CAPACITY) {
                throw new MaxCapacityExceedException();
            }

            if (addressToInstanceMap.containsKey(backendInstance.getAddress())) {
                throw new AddressExistException();
            }
            addressToInstanceMap.put(backendInstance.getAddress(), backendInstance);
        }
    }

    public Optional<BackendInstance> select(){
        return selectStrategy.selectInstance(addressToInstanceMap);
    }
}
