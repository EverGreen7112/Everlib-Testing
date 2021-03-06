package com.evergreen.fertilizer.shuffleboard.constants.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.evergreen.fertilizer.shuffleboard.constants.ConstantDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;

/**
 * A command to set a {@link ConstantDouble} a certain value,
 * and reverts it back once a specified condition is met,
 * or once the command is intterupted.
 * 
 * @author Atai Ambus
 */
public class SetConstantUntil extends SetConstant {
    /**The {@link #isFinished()} condition.*/
    Supplier<Boolean> m_until;
    /**The constant to set.*/
    ConstantDouble m_constant;
    /**The value to revert the cosntant back to*/
    double m_initValue;

    /**
     * Constructs a new {@link SetConstantUntil} command, setting an input {@link ConstantDouble}
     * an input value and reverting it once a input condition is met.
     * 
     * @param name - The command's name, for switch and logging
     * @param constant - The constant to set
     * @param value - The value to set the constant to
     * @param until - The condition under which to revert the constant back
     */
    public SetConstantUntil(String name, 
        ConstantDouble constant, Supplier<Double> value, Supplier<Boolean> until) {
        super(name, constant, value);
        m_constant = constant;
        m_initValue = constant.get();
        m_until = until;
    }

    /**
     * Constructs a new {@link SetConstantUntil} command, setting an input {@link ConstantDouble}
     * an input value and reverting it once the command is intterupted.
     * 
     * @param name - The command's name, for switch and logging
     * @param constant - The constant to set
     * @param value - The value to set the constant to
     */
    public SetConstantUntil(String name, ConstantDouble constant, Supplier<Double> value) {
        this(name, constant, value, () -> false);
    }

    /**When the input condition is met - end the command*/
    @Override
    public boolean isFinished() {
        return m_until.get();
    }

    /**When the command ends (that is, the condition is met) or gets interrupted, 
     * revert the constant back to its original value. */
    @Override
    public void end(boolean interrupted) {
        m_constant.setValue(m_initValue);
    }

    @Override
    public List<LoggableData> getLoggableData() {
        ArrayList<LoggableData> res = new ArrayList<>(super.getLoggableData());
        res.addAll(List.of(
            new LoggableBoolean("Revert Condition", m_until)
        ));
        return res;
    }
}