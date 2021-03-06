package com.evergreen.fertilizer.shuffleboard.constants;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;

/**
 * LoggableNumber
 */
public class ConstantString extends Constant implements Supplier<String> {
    
    String m_defaultVal;

    public ConstantString(String name, String initValue) {
        super(name, initValue);
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

    @Override
    public String get() {
        return Preferences.getInstance().getString(getPath(), m_defaultVal);
    }


}