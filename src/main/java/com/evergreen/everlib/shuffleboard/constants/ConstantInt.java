package com.evergreen.everlib.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * ConstantInt
 * ConstantInt represent an int value in the shuffleboard - set with a
 * value at RobotInit, and tuned by drivers and users in the shuffleboard.
 */
public class ConstantInt extends Constant implements Supplier<Integer> {
    
    /**
     * The constant's default value.
     */
    int m_defaultVal;

    /***
     * ConstantInt
     * @param key the path, relative to the workin directory.
     * @param initValue the constant's value
     * Sets the constant in the given path with the value of initValue parameter.
     */
    public ConstantInt(String key, int initValue) {
        super(key, initValue);
        m_defaultVal = initValue;
    }
    
    /***
     * addToDashboard
     * Adds the constant to the Dashboard with the default value.
     */
    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }

    /***
     * addToDashboard
     * @param value the value to be added to the Dashboard with the constant.
     * Adds the constant to the Dashboard with the given value.
     */
    @Override
    protected void addToDashboard(Object value) {
        Preferences.getInstance().putInt(getPath(), (int)value);
    }

    /***
     * getType
     * Returns the type of the constant. It is Integer.
     */
    @Override
    public String getType() {
        return "Integer";
    }

    /***
     * increaseAbsolute
     * @param increaseBy increases the constant's value by this parameter's value.
     * Increase the value of the constant by the value of the @param increaseBy parameter.
     */
    public void increaseAbsolute(int increaseBy) {
        setValue(get() + increaseBy);
    }

    /**
     * Increases the constants value in percentage, according to an input 100%.
     * @param percentage - The percentage to rise
     * @param maxValue - The 100% the percentage are a part of
     */
    public void increasePercentage(int percentage, int maxValue) {
        setValue(get() + maxValue * percentage/100);
    } 

    /***
     * setValue
     * @param newValue set the constant's valu to this parameter's value.
     * Sets the constant's value to the value of the @param newValue parameter.
     */
    public void setValue(int newValue) {
        m_defaultVal = newValue;
        reset();
    }

    /***
     * get
     * Returns the constant's current value.
     */
    @Override
    public Integer get() {
        return Preferences.getInstance().getInt(getPath(), m_defaultVal);
    }
}