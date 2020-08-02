package com.evergreen.everlib.subsystems.motors.commands;

import java.util.function.Supplier;

import com.evergreen.everlib.subsystems.motors.subsystems.MotorSubsystem;

/**
 * AnglePIDCommand
 */
public class DisplacementPID extends RunPID {

    public DisplacementPID(MotorSubsystem subsystem, Supplier<Double> setpoint) {
        super(
            subsystem.getName() + "/Displacement PID",
            subsystem.getVelocityPID(), 
            setpoint, 
            subsystem::move, 
            subsystem::getPosition,
            subsystem);
    }
    
}