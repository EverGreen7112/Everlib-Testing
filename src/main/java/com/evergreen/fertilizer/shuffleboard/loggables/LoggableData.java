package com.evergreen.fertilizer.shuffleboard.loggables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Loggable data represent data that could be logged to the dashboard (int,
 * boolean, string, double) and updated continuously.
 */
public abstract class LoggableData {
    /**
     * The key of the object, use for accessing it through the dashboard.
     */
    private String m_key;

    /**
     * Creates new loggable data object and set the {@link #m_key key} (use for
     * accessing through the dashboard).
     * 
     * @param key the {@link #m_key key}
     */
    public LoggableData(String key) {
        m_key = key;
    }

    /**
     * @return The {@link #m_key key} (use for accessing through the dashboard).
     */
    public String getKey() {
        return m_key;
    }

    /**
     * Set the {@link #m_key key} (use for accessing through the dashboard).
     * 
     * @param key the {@link #m_key key}
     */
    public void setKey(String key) {
        m_key = key;
    }

    /**
     * Add the object to the dashboard.
     */
    public abstract void addToDashboard();

    /**
     * Remove the object from the dashboard.
     */
    public void remove() {
        SmartDashboard.delete(m_key);
    }

    /**
     * 
     * @return the type of the object (int, double, string, boolean)
     */
    public abstract String getType();

    /**
     * @return the value of the object as a string.
     */
    public abstract String getStringValue();
}
