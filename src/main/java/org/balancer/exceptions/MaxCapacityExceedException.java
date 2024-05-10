package org.balancer.exceptions;

public class MaxCapacityExceedException extends RuntimeException{
    public MaxCapacityExceedException() {
        super("Capacity is exceeded");
    }
}
