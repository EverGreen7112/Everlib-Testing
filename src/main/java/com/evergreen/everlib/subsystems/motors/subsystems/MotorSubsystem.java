package com.evergreen.everlib.subsystems.motors.subsystems;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.everlib.subsystems.EvergreenSubsystem;
import com.evergreen.everlib.subsystems.motors.commands.AnglePID;
import com.evergreen.everlib.subsystems.motors.commands.DisplacementPID;
import com.evergreen.everlib.subsystems.motors.commands.VelocityPID;
import com.evergreen.everlib.subsystems.sensors.DistanceSensor;
import com.evergreen.everlib.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.utils.PIDSettings;
import com.evergreen.everlib.utils.ranges.Limitless;
import com.evergreen.everlib.utils.ranges.Range;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A {@link Subsystem} consisting of one or more motor m_controllers.
 */
public class MotorSubsystem extends EvergreenSubsystem {
    /**The subsystem's motor controllers. */
    protected MotorController[] m_controllers;

    /**The range in which the subsystem is allowed to move. */
    protected Range m_Range;

    protected PIDSettings m_anglePID;
    protected PIDSettings m_displacementPID;
    protected PIDSettings m_velocityPID;

    public MotorSubsystem(String name, MotorController... motors)
    {
        super(name);
        m_controllers = motors;
        m_Range = new Limitless();
        
        for (MotorController motor : m_controllers) {
            for (DistanceSensor sensor : motor.getEncoders()) {
                addDistanceSensor(sensor);
            }
        }
    }

    public MotorSubsystem(String name, DistanceSensor distanceSensor, MotorController... motors) {
        this(name, motors);
        addDistanceSensor(distanceSensor);
    }


    public MotorSubsystem(String name, DistanceSensor sensor, Range range, MotorController... motors)
    {
        this(name, sensor, motors);
        m_Range = range;
        
    }

    public MotorSubsystem(String name, DistanceSensor distanceSensor, Range range, 
        Command defaultCommand, MotorController... motors)
    {
        this(name, distanceSensor, range, motors);
        setDefaultCommand(defaultCommand);
    }


    public void move(double speed) {
        for (MotorController control : m_controllers) {
            control.set(speed);
        }
    }

    public void rotate(double speed) {
        move(speed);
    }

    public void set(int index, double speed)
    {
        if(canMove()) 
            m_controllers[index].set(speed);
    }

    public void set(Map<Integer, Double> speedMap)
    {
        if(canMove()) 
            speedMap.forEach(this::set);
    }

    public void setAnglePID(double kP, double kI, double kD) {
        m_anglePID.setPID(kP, kI, kD);
    }

    public void stop()
    {
        for(MotorController motor : m_controllers)
        {
            motor.stopMotor();
        }

    }

    public DistanceSensor getDistanceSensor() {
        return m_distanceSensor;
    }

    public MotorController[] getMotorControllers() {
        return m_controllers;
    }

    public boolean canMove() {
        return m_Range.inRange(getPosition()) && m_subsystemSwitch.get();
    }

    public PIDSettings getAnglePID() {
        return m_anglePID;
    }

    public PIDSettings getDisplacementPID() {
        return m_displacementPID;
    }

    public PIDSettings getVelocityPID() {
        return m_velocityPID;
    }

    public void moveTo(Supplier<Double> distance) {
        new DisplacementPID(this, distance).schedule();
    }

    public void rotateTo(Supplier<Double> angle) {
        new AnglePID(this, angle).schedule();
    }

    public void accelerateTo(Supplier<Double> speed) {
        new VelocityPID(this, speed).schedule();
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = super.getLoggableData();

        for (int i = 0; i < m_controllers.length; i++) {
            loggables.addAll(List.of(
                new LoggableDouble("Motors/Set Speed/#" + i, m_controllers[i]::get),
                new LoggableBoolean("Motors/Inverted/#" + i, m_controllers[i]::getInverted)
            ));            
        }

        for (int i = 0; i < m_angleSensors.size(); i++) {
            Gyro sensor = m_angleSensors.getAt(i);
            loggables.addAll(List.of(
                    new LoggableDouble("Angle Sensors/Angle/#" + i, sensor::getAngle),
                    new LoggableDouble("Angle Sensors/Velocity/#" + i, sensor::getRate)
            ));
        }

        if (!m_angleSensors.isEmpty()) {
            loggables.addAll(List.of(
                new LoggableDouble("Angle", m_angleSensors::getAngle),
                new LoggableDouble("Angular Velocity", m_angleSensors::getRate)
            ));
        }

        for (int i = 0; i < m_distanceSensor.getSensors().size(); i++) {
            loggables.addAll(List.of(
                    new LoggableDouble(
                        "Distance Sensors/Distance/#" + i, 
                        m_distanceSensor.getSensors().get(i)::getPosition),

                    new LoggableDouble(
                        "Distance Sensors/Velocity/#" + i,
                         m_distanceSensor.getSensors().get(i)::getVelocity)
            ));
        }

        if (!m_distanceSensor.isEmpty()) {
            loggables.addAll(List.of(
                new LoggableDouble("Distance", m_distanceSensor::getPosition),
                new LoggableDouble("Velocity", m_distanceSensor::getVelocity)
            ));
        }
        

        return super.getLoggableData();
    }



    
}
