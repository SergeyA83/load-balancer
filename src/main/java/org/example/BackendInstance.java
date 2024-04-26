package org.example;

public class BackendInstance {
    public String getAddress() {
        return address;
    }

    public BackendInstance(String address) {
        this.address = address;
    }

    private final String address;
}
