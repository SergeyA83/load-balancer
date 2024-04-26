package org.example;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SelectStrategyRandom implements SelectStrategy {
    private final Random random = new Random();
    @Override
    public Optional<BackendInstance> selectInstance(Map<String, BackendInstance> addressToInstanceMap) {
        if (addressToInstanceMap.isEmpty()){
            return Optional.empty();
        }
        int index = random.nextInt(0, 9);
        return addressToInstanceMap.values()
                .stream()
                .skip(index)
                .findAny();
    }
}
