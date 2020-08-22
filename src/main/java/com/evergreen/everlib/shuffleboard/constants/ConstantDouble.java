package com.evergreen.everlib.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;


/**
 * LoggableNumber
 */
public class ConstantDouble extends Constant implements Supplier<Double> {
    
    double m_defaultVal;



    public ConstantDouble(String key, double initValue) {
        super(key, initValue);
        m_defaultVal = initValue;
    }

    @Override
    public void addToDashboard() {
        addToDashboard(m_defaultVal);
    }
    @Override
    public void addToDashboard(Object value) {
        Preferences.getInstance().putDouble(getPath(), (double)value);
        
    }

    @Override
    public String getType() {
        return "Double";
    }

    public void setValue(double value) {
        m_defaultVal = value;
        reset();
    }


    public void increaseAbsolute(double increaseBy) {
        setValue(get() + increaseBy);
    }

    /**
     * Increases the constants value in percentage, according to an input 100%.
     * @param percentage - The percentage to rise
     * @param maxValue - The 100% the percentage are a part of
     */
    public void increasePercentage(double percentage, int maxValue) {
        setValue(get() + maxValue * percentage/100);
    } 

    @Override
    public Double get() {
        return Preferences.getInstance().getDouble(getPath(), m_defaultVal);
    }

}