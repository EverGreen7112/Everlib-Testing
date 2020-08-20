package com.evergreen.everlib.subsystems.motors.commands;

import java.util.function.Supplier;

import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;

/**
 * AnglePIDCommand
 */
public class VelocityPID extends RunPID {

    public VelocityPID(MotorSubsystem subsystem, Supplier<Double> setpoint) {
        super(
            subsystem.getName() + "/Velocity PID",
            subsystem.getVelocityPID(), 
            setpoint, 
            subsystem::move,
            subsystem::getVelocity,
            subsystem);
    }
    
}