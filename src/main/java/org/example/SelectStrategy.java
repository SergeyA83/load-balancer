package org.example;

import java.util.Map;
import java.util.Optional;

public interface SelectStrategy {
    Optional<BackendInstance> selectInstance(Map<String, BackendInstance> addressToInstanceMap);
}
