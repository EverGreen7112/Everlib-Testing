package com.evergreen.fertilizer.shuffleboard.loggables;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * LoggableString represent string that could be logged to the dashboard and updated continuously.
 */
public class LoggableString extends LoggableData {
    /**
     * the string supplier which will update the value of the object.
     */
    Supplier<String> m_stream;
    /**
     * Creates new instance of LoggableString, by key and string supplier.
     * 
     * @param key    String that use for accessing through the dashboard.
     * @param stream the string supplier which will update the value of the object.
     */
    public LoggableString(String key, Supplier<String> stream) {
        super(key);
        m_stream = stream;
    }

    @Override
    public void addToDashboard() {
        SmartDashboard.putString(getKey(), m_stream.get());
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public String getStringValue() {
        return m_stream.get().toString();
    }


}