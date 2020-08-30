package com.evergreen.everlib.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * ConstantBoolean represent a boolean value in the shuffleboard- set with a
 * value at RobotInit, and tuned by drivers and users in the shuffleboard.
 */
public class ConstantBoolean extends Constant implements Supplier<Boolean> {
    
    /**
     * The default value of this constant.
     */
    boolean m_defaultVal;
    
    /**
     * Construct a ConstantBoolean object by input path and value, and adds it at the current
     * {@link DashboardConstants} working directory. 
     * <p>
     * Trying to add a constant at an already used path will cause an {@link IllegalArgumentException}.
     * 
     * @param relPath - The path, relative to the current working directory.
     * @param value - The boolean value
     */
    public ConstantBoolean(String relPath, boolean initValue) {
        super(relPath, initValue);
        m_defaultVal = initValue;
    }
    /**
     * Construct a ConstantBoolean object with input path, and default value of true. and adds it at the current
     * {@link DashboardConstants} working directory. 
     * <p>
     * Trying to add a constant at an already used path will cause an {@link IllegalArgumentException}.
     * 
     * @param relPath -The boolean path,relative to the current working directory.
     */
    public ConstantBoolean(String relPath) {
        this(relPath, true);
    }
    
    @Override
    public String getType() {
        return "Boolean";
    }
    /**
     * @return the value of this constant.
     */
    @Override
    public Boolean get() {
        return Preferences.getInstance().getBoolean(getPath(), m_defaultVal);
    }

    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }
    /**
     * Set the value of this constant.
     * @param value the new value.
     */
    public void set(boolean value) {
        m_defaultVal = value;
        addToDashboard();
    }
    /**
     * Toggle the value of this constant.
     */
    public void toggle() {
        set(!m_defaultVal);
    }
    /**
     * Set the value of this constant to false.
     */
    public void disable()
    {
        set(false);
    }
    /**
     * Set the value of this constant to true.
     */
    public void enable() 
    {
        set(true);
    }
    
    @Override
    protected void addToDashboard(Object value) {
        Preferences.getInstance().putBoolean(getPath(), (boolean)value);
    }

}