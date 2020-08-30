package com.evergreen.everlib.utils.constraints;

import java.util.List;

import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableString;

/**
 * Limitless
 */
public class Free implements Constraint {

    private String m_name;

    public Free(String name) {
        m_name = name;
    }

    public Free() {
        this("Limitless");
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
            new LoggableString("Type", () -> "Limitless")
        );
    }
}