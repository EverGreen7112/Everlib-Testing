package com.evergreen.everlib.utils.constraints;

import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;

public interface Constraint extends LoggableObject {
    public boolean allowed(double value);
}
