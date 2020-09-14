package com.evergreen.fertilizer.utils.constraints;

import java.util.List;
import java.util.function.Supplier;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableString;

public class MinLimit implements Constraint {

    private Supplier<Double> m_minLimit;
    private Supplier<Double> m_tolerance;
    private String m_name;

    public MinLimit(String name, Supplier<Double> minDistance, Supplier<Double> tolerance) {
        m_name = name;
        m_minLimit = minDistance;
        m_tolerance = tolerance;
    }

    public MinLimit(String name, double minDistance) {
        this(name, () -> minDistance, () -> 0.0);
    }

    public MinLimit(String name, Supplier<Double> minDistance) {
        this(name, minDistance, () -> 0.0);
    }


    public MinLimit(String name, double minDistance, double tolerance) {
        this(name, () -> minDistance, () -> tolerance);
    }

    @Override
    public boolean allowed(double distance) {
        return distance > m_minLimit.get() - m_tolerance.get();
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableString("Type", () -> "Minimum"),
            new LoggableDouble("Set Minima", m_minLimit),
            new LoggableDouble("Tolerance", m_tolerance),
            new LoggableDouble("True Minima", () -> m_minLimit.get() - m_tolerance.get())
        );
    }
}