package com.evergreen.fertilizer.subsystems.motors.commands;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.subsystems.EvergreenCommand;
import com.evergreen.fertilizer.subsystems.EvergreenSubsystem;
import com.evergreen.fertilizer.utils.PIDSettings;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;


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
        PIDController controller = m_settings.getController();

        loggables.addAll(List.of(
            new LoggableDouble("kP", m_settings::kP),
            new LoggableDouble("kI", m_settings::kI),
            new LoggableDouble("kD", m_settings::kD),
            new LoggableDouble("kF", m_settings::kF),
            new LoggableDouble("Tolerance", m_settings::getTolerance),
            new LoggableDouble("Distance", () -> controller.getSetpoint() - controller.getPositionError()),
            new LoggableDouble("Period", m_settings::getPeriod),
            new LoggableDouble("Setpoint", controller::getSetpoint),
            
            new LoggableDouble("Error", controller::getSetpoint),
            new LoggableDouble("Calculated PIDF", () -> controller.calculate(m_measurement.get()))
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