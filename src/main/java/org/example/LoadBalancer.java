package org.example;

import org.example.exceptions.AddressExistException;
import org.example.exceptions.MaxCapacityExceedException;

import java.util.*;

public class LoadBalancer {
    private final int MAX_CAPACITY = 10;
    private final Map<String, BackendInstance> addressToInstanceMap = new HashMap<>();
    private final SelectStrategy selectStrategy;

    public LoadBalancer(SelectStrategy selectStrategy) {
        this.selectStrategy = selectStrategy;
    }

    public void registerInstance(BackendInstance backendInstance, String address){
        if (backendInstance==null){
            throw new IllegalArgumentException("Instance can't be null");
        }
        if (address==null || address.isBlank()){
            throw new IllegalArgumentException("Address can't be null or empty");
        }

        synchronized (this) {
            if (addressToInstanceMap.size()==MAX_CAPACITY) {
                throw new MaxCapacityExceedException();
            }

            if (addressToInstanceMap.containsKey(address)) {
                throw new AddressExistException();
            }
            addressToInstanceMap.put(address, backendInstance);
        }
    }

    public Optional<BackendInstance> selectInstance(){
        return selectStrategy.selectInstance(addressToInstanceMap);
    }
}
