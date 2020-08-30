package com.evergreen.everlib.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * ConstantString represent a String value in the shuffleboard- set with a
 * value at RobotInit, and tuned by drivers and users in the shuffleboard.
 */
public class ConstantString extends Constant implements Supplier<String> {
    
    /**
     * The default value of this constant.
     */
    String m_defaultVal;
/**
     * Construct a ConstantBString object by input path and value, and adds it at the current
     * {@link DashboardConstants} working directory. 
     * <p>
     * Trying to add a constant at an already used path will cause an {@link IllegalArgumentException}.
     * 
     * @param relPath - The path, relative to the current working directory.
     * @param value - The String value
     */
    public ConstantString(String relPath, String initValue) {
        super(relPath, initValue);
        m_defaultVal = initValue;
    }
    
    @Override
    public String getType() {
        return "String";
    }

    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }

    @Override
    protected void addToDashboard(Object value) {
        Preferences.getInstance().putString(getPath(), value.toString());
    }
    /**
     * @return the value of this constant.
     */
    @Override
    public String get() {
        return Preferences.getInstance().getString(getPath(), m_defaultVal);
    }


}