package com.evergreen.fertilizer.utils.constraints;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableObject;

public interface Constraint extends LoggableObject {
    public boolean allowed(double value);
}
