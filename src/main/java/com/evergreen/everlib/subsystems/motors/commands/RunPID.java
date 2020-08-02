package com.evergreen.everlib.subsystems.motors.commands;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.evergreen.everlib.utils.PIDSettings;

import edu.wpi.first.wpilibj2.command.PIDCommand;

import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.subsystems.EvergreenCommand;
import com.evergreen.everlib.subsystems.EvergreenSubsystem;


/**
 * MotorSubsystemPID
 */
public class RunPID extends EvergreenCommand {

    PIDCommand m_command;
    PIDSettings m_settings;
    Supplier<Double> m_measurement;
    
    public RunPID(String name, 
                  PIDSettings pidSettings, 
                  Supplier<Double> target,
                  Consumer<Double> output,
                  Supplier<Double> mesurement,
                  EvergreenSubsystem... requirements) {
        super(name);
        m_command = new PIDCommand(
            pidSettings.getController(), 
            () -> mesurement.get(), 
            () -> target.get(), 
            (v) -> output.accept(v), 
            requirements);

        m_settings = pidSettings;
        m_measurement = mesurement;
        
    }

    @Override
    public void schedule(boolean interruptible) {
        super.schedule(interruptible);
        m_command.schedule(interruptible);
    }

    @Override
    public void execute() {
        m_command.getController().setPID(m_settings.kP(), m_settings.kI(), m_settings.kD());
        m_command.getController().setTolerance(m_settings.getTolerance());
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = super.getLoggableData();

        loggables.addAll(List.of(
            new LoggableDouble("kP", m_settings::kP),
            new LoggableDouble("kI", m_settings::kI),
            new LoggableDouble("kD", m_settings::kD),
            new LoggableDouble("kF", m_settings::kF),
            new LoggableDouble("Tolerance", m_settings::getTolerance),
            new LoggableDouble("Distance", () -> m_settings.getController().getSetpoint() - m_settings.getController().getPositionError()),
            new LoggableDouble("Period", m_settings::getPeriod),
            new LoggableDouble("Setpoint", m_settings.getController()::getSetpoint),
            
            new LoggableDouble("Error", 
                () -> m_settings.getController().getSetpoint()),

            new LoggableDouble("Calculated PIDF", () -> 
                m_command.getController().calculate(m_settings.getController().calculate(m_measurement.get())))
            ));
        
        return loggables;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || m_settings.getController().atSetpoint() || m_command.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        m_command.cancel();
    }


}