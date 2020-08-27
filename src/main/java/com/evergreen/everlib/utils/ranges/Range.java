package com.evergreen.everlib.utils.ranges;

@FunctionalInterface
public interface Range {
    public boolean inRange(double value);
}
