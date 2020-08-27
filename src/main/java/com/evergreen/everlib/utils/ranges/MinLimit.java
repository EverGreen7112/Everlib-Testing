package com.evergreen.everlib.utils.ranges;

import java.util.function.Supplier;

public class MinLimit implements Range {
    
    Supplier<Double> m_minDistance;
    Supplier<Double> m_tolerance;

    public MinLimit(double minDistance) {
        m_minDistance = () -> minDistance;
    }

    public MinLimit(Supplier<Double> minDistance) {
        m_minDistance = minDistance;
    }

    public MinLimit(Supplier<Double> minDistance, Supplier<Double> tolerance) {
        m_minDistance = minDistance;
        m_tolerance = tolerance;
    }

    public MinLimit(double minDistance, double tolerance) {
        m_minDistance = () -> minDistance;
        m_tolerance = () -> tolerance;
    }
    
    @Override
    public boolean inRange(double distance) {
        return distance > m_minDistance.get() - m_tolerance.get();
    }
}