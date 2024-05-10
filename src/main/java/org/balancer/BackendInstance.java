package org.balancer;

import java.util.Objects;

public class BackendInstance {
    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackendInstance that = (BackendInstance) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    public BackendInstance(String address) {
        this.address = address;
    }

    private final String address;
}
