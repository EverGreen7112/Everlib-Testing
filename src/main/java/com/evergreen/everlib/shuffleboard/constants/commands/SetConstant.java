package com.evergreen.everlib.shuffleboard.constants.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableString;
import com.evergreen.everlib.utils.InstantEvergreenCommand;

/**
 * A command to set a {@link ConstantDouble shuffleboard double constant} a certain value.
 * 
 * @author Atai Ambus
 */
public class SetConstant extends InstantEvergreenCommand {

    /**The value the command will set the constant to */
    Supplier<Double> m_valueToSet;

    /**The path of the constant this command will set the value of */
    String m_targetPath;
    String m_targetName;

    /**
     * Constructs a new {@link SetConstant} command, which sets an input {@link ConstantDouble 
     * Shuffleboard double constant} an input value.
     * 
     * @param name - The name for this command (for switch and logging)
     * @param constant - The constant to set
     * @param value - The value to set it
     */
    public SetConstant(String name, ConstantDouble constant, double value) {
        this(name, constant, () -> value);
    }


    /**
     * Constructs a new {@link SetConstant} command, which sets an input {@link ConstantDouble 
     * Shuffleboard double constant} an input value.
     * 
     * @param name - The name for this command (for switch and logging)
     * @param constant - The constant to set
     * @param valueSupplier - A supplier of the value to set it to. (for example a 
     * {@link ConstantDouble})
     */
    public SetConstant(String name, ConstantDouble constant, Supplier<Double> valueSupplier) {
        super(name, () -> constant.setValue(valueSupplier.get()));
        m_valueToSet = valueSupplier;
        m_targetPath = constant.getPath();
        m_targetName = constant.getName();
    }

    @Override
    public List<LoggableData> getLoggableData() {
        ArrayList<LoggableData> res = new ArrayList<>(super.getLoggableData());
        res.addAll(List.of(new LoggableData[] {
            new LoggableDouble("Value to Set", m_valueToSet),
            new LoggableString("Target Path", () -> m_targetPath),
            new LoggableString("Target Name", () -> m_targetName)
        }));
        return res;
    }


}