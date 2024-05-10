package org.balancer;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SelectStrategyRandom implements SelectStrategy {
    private final Random random = new Random();

    @Override
    public Optional<BackendInstance> selectInstance(List<BackendInstance> instancesList) {
        if (instancesList.isEmpty()){
            return Optional.empty();
        }
        int index = random.nextInt(0, 9);

        return Optional.ofNullable(selectInstanceByIndex(instancesList, index));
    }

    private BackendInstance selectInstanceByIndex(List<BackendInstance> instancesList, int index){
        try {
            return instancesList.get(index);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
