package com.evergreen.everlib.shuffleboard.loggables;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * LoggableInt represent int that could be logged to the dashboard and
 * updated continuously.
 */
public class LoggableInt extends LoggableData {
    /**
     * the int supplier which will update the value of the object.
     */
    Supplier<Integer> m_stream;
    /**
     * Creates new instance of LoggableInt, by key and int supplier.
     * 
     * @param key    String that use for accessing through the dashboard.
     * @param stream the int supplier which will update the value of the object.
     */
    public LoggableInt(String key, Supplier<Integer> stream) {
        super(key);
        m_stream = stream;
    }

    @Override
    public void addToDashboard() {
        SmartDashboard.putNumber(getKey(), m_stream.get());
    }

    @Override
    public String getType() {
        return "Integer";
    }

    @Override
    public String getStringValue() {
        return m_stream.get().toString();
    }

}