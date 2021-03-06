package com.evergreen.fertilizer.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * LoggableNumber
 */
public class ConstantInt extends Constant implements Supplier<Integer> {
    
    int m_defaultVal;

    public ConstantInt(String key, int initValue) {
        super(key, initValue);
        m_defaultVal = initValue;
    }
    
    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }

    @Override
    protected void addToDashboard(Object value) {
        Preferences.getInstance().putInt(getPath(), (int)value);
    }


    @Override
    public String getType() {
        return "Integer";
    }

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

    public void setValue(int newValue) {
        m_defaultVal = newValue;
        reset();
    }

    @Override
    public Integer get() {
        return Preferences.getInstance().getInt(getPath(), m_defaultVal);
    }
}