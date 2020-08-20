package com.evergreen.everlib.shuffleboard.loggables;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * LoggableBoolean represent boolean that could be logged to the dashboard and updated continuously.
 */
public class LoggableBoolean extends LoggableData {
    /**
     * the boolean supplier which will update the value of the object.
     */
    Supplier<Boolean> m_stream;

    /**
     * Creates new instance of LoggableBoolean, by key and boolean supplier.
     * 
     * @param key    String that use for accessing through the dashboard.
     * @param stream the boolean supplier which will update the value of the object.
     */
    public LoggableBoolean(String key, Supplier<Boolean> stream) {
        super(key);
        m_stream = stream;
    }

    @Override
    public void addToDashboard() {
        SmartDashboard.putBoolean(getKey(), m_stream.get());
    }

    @Override
    public String getType() {
        return "Boolean";
    }

    @Override
    public String getStringValue() {
        return m_stream.get().toString();
    }
}