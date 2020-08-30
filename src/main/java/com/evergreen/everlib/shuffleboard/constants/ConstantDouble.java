package com.evergreen.everlib.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;


/**
 * ConstantDouble 
 * ConstantDouble represent a double value in the shuffleboard - set with a
 * value at RobotInit, and tuned by drivers and users in the shuffleboard.
 */
public class ConstantDouble extends Constant implements Supplier<Double> {
    
    /**
     * The constant's default value.
     */
    double m_defaultVal; 


    /***
     * ConstantDouble
     * @param key the constant's relative path to the working directory.
     * @param initValue the valueof the constant.
     * Sets the constant in the given path with the value of initValue parameter.
     */
    public ConstantDouble(String key, double initValue) {
        super(key, initValue);
        m_defaultVal = initValue;
    }

    /***
     * addToDashboard
     * Adds the constant to the Dasboard.
     */
    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }

    /***
     * addToDashboard
     * @param value add the given value to the Dashboard using current path.
     */
    @Override
    public void addToDashboard(Object value) {
        Preferences.getInstance().putDouble(getPath(), (double)value);
    }

    /***
     * getType
     * Returns the type of the constant. It is Double.
     */
    @Override
    public String getType() {
        return "Double";
    }

    /***
     * setValuethe
     * @param value a value to become default.
     * Sets the default value to given value, then resets the constant.
     */
    public void setValue(double value) {
        m_defaultVal = value;
        reset();
    }

    /***
     * increaseAbsolute
     * Increases the constants value in absolute by the @param increaseBy parameter.
     * @param increaseBy the absolute value to increase the current value by.
     */
    public void increaseAbsolute(double increaseBy) {
        setValue(get() + increaseBy);
    }

    /**
     * increasePercentage
     * Increases the constants value in percentage, according to an input 100%.
     * @param percentage - The percentage to rise
     * @param maxValue - The 100% the percentage are a part of
     */
    public void increasePercentage(double percentage, int maxValue) {
        setValue(get() + maxValue * percentage/100);
    } 

    /***
     * get
     * Returns the current value of the constant.
     */
    @Override
    public Double get() {
        return Preferences.getInstance().getDouble(getPath(), m_defaultVal);
    }

}