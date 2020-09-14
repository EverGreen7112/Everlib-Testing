package com.evergreen.fertilizer.utils.constraints;

import java.util.List;
import java.util.function.Supplier;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableString;

/**
 * DoubleLimit
 */
public class Range implements Constraint {

    private Supplier<Double> m_maxLimit, m_minLimit, m_tolerance;
    private String m_name;

    
    public Range(String name, Supplier<Double> maxLimit, Supplier<Double> minLimit, Supplier<Double> tolerance) {
        m_name = name;
        m_maxLimit = maxLimit;
        m_minLimit = minLimit;
        m_tolerance = tolerance;
    }

    public Range(String name, Supplier<Double> maxLimit, Supplier<Double> minLimit) {
        this(name, maxLimit, minLimit, () -> 0.0);
    }
    
    @Override
    public boolean allowed(double value) {
        return value > m_minLimit.get() - m_tolerance.get()
            && value < m_maxLimit.get() + m_tolerance.get();
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableString("Type", () -> "Range"),
            new LoggableDouble("Set Minima", m_minLimit),
            new LoggableDouble("Set Maxima", m_maxLimit),
            new LoggableDouble("Tolerance", m_tolerance),
            new LoggableDouble("True Minima", () -> m_minLimit.get() - m_tolerance.get()),
            new LoggableDouble("True Maxima", () -> m_maxLimit.get() + m_tolerance.get())
        );
    }
}