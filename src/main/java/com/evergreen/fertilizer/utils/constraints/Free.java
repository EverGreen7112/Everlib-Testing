package com.evergreen.fertilizer.utils.constraints;

import java.util.List;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableString;

/**
 * Free
 */
public class Free implements Constraint {

    private String m_name;

    public Free(String name) {
        m_name = name;
    }

    public Free() {
        this("Free");
    }

    @Override
    public boolean allowed(double value) {
        return true;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableString("Type", () -> "Free")
        );
    }
}