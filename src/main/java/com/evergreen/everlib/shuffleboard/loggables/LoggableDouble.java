package com.evergreen.everlib.shuffleboard.loggables;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * LoggableDouble represent double that could be logged to the dashboard and updated continuously.
 */
public class LoggableDouble extends LoggableData {
    /**
     * the double supplier which will update the value of the object.
     */
    Supplier<Double> m_stream;
    /**
     * Creates new instance of LoggableDouble, by key and double supplier.
     * 
     * @param key    String that use for accessing through the dashboard.
     * @param stream the double supplier which will update the value of the object.
     */
    public LoggableDouble(String key, Supplier<Double> stream) {
        super(key);
        m_stream = stream;
    }
    
    @Override
    public void addToDashboard() {
        SmartDashboard.putNumber(getKey(), m_stream.get());
    }

    @Override
    public String getType() {
        return "Double";
    }

    @Override
    public String getStringValue() {
        return m_stream.get().toString();
    }

}