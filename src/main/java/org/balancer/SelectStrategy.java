package org.balancer;

import java.util.List;
import java.util.Optional;

public interface SelectStrategy {
    Optional<BackendInstance> selectInstance(List<BackendInstance> instancesList);
}
