package com.evergreen.everlib.utils.constraints;

import java.util.List;
import java.util.function.Supplier;

import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableString;

/**
 * MaxLimit
 */
public class MaxLimit implements Constraint {

    private Supplier<Double> m_maxLimit, m_tolerance;
    private String m_name;

    public MaxLimit(String name, double maxLimit) {
        this(name, () -> maxLimit, () -> 0.0);
    }

    public MaxLimit(String name, Supplier<Double> maxLimit) {
        this(name, maxLimit, () -> 0.0);
    }

    public MaxLimit(String name, Supplier<Double> limit, Supplier<Double> tolerance) {
        m_name = name;
        m_maxLimit = limit;
        m_tolerance = tolerance;
    }

    public MaxLimit(String name, Supplier<Double> limit, double tolerance) {
        this(name, limit, () -> tolerance);
    }

    @Override
    public boolean allowed(double value) {
        return value < m_maxLimit.get() + m_tolerance.get();
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableString("Type", () -> "Maximum"),
            new LoggableDouble("Set Maxima", m_maxLimit),
            new LoggableDouble("Tolerance", m_tolerance),
            new LoggableDouble("True Maxima", () -> m_maxLimit.get() + m_tolerance.get())
        );
    }
}