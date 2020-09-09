package com.evergreen.fertilizer.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.fertilizer.Exceptions;
import com.evergreen.fertilizer.shuffleboard.constants.ConstantBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableObject;
import com.evergreen.fertilizer.subsystems.sensors.AngleSensorGroup;
import com.evergreen.fertilizer.subsystems.sensors.DistanceSensor;
import com.evergreen.fertilizer.subsystems.sensors.DistanceSensorGroup;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * The subsys
 */
public abstract class EvergreenSubsystem extends SubsystemBase implements Exceptions, LoggableObject {

    protected ConstantBoolean m_subsystemSwitch;

    protected DistanceSensorGroup m_distanceSensor = 
        new DistanceSensorGroup(getName() + "/Distance Sensor");

    protected AngleSensorGroup m_angleSensors = 
        new AngleSensorGroup(getName() + "/Angle Sensor");
        
    protected EvergreenCommand m_currentlyUsedBy;
    protected boolean m_currentCommandInterruptable  = true;


    public EvergreenSubsystem(String name) {
        setName(name);
        m_subsystemSwitch  = new ConstantBoolean(name);
        
    }

    public ConstantBoolean getSwitch() {
        return m_subsystemSwitch;
    }

    public boolean getSwitchState() {
        return m_subsystemSwitch.get();
    }

    public void addDistanceSensor(DistanceSensor sensor) {
        m_distanceSensor.addSensor(sensor);
    }

    public void addAngleSensor(Gyro angleSensor) {
        m_angleSensors.addSensor(angleSensor);
    }

    public double getAngle() {
        if (!m_angleSensors.isEmpty()) {
            return m_angleSensors.getAngle();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the angle of " + getName() 
            + ", but it does not have a angle sensor!");
    }


    public double getPosition() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            return m_distanceSensor.getPosition();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the distance of " + getName() 
            + ", but it does not have a distance sensor!");
    }

    boolean useWith(EvergreenCommand command, boolean interruptible) {
        
        if (m_currentlyUsedBy == null ) {
            m_currentlyUsedBy = command;
        } else if (m_currentCommandInterruptable) {
            m_currentlyUsedBy.m_interruptCounter++;
            m_currentCommandInterruptable = interruptible;
            m_currentlyUsedBy = command;
        } else {
            return false;
        }
        
        return true;
        
    }

    public double getVelocity() throws SensorDoesNotExistException {
        if (!m_distanceSensor.isEmpty()) {
            return m_distanceSensor.getVelocity();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the velocity of " + getName() 
            + ", but it does not have a distance sensor!");
    }

    public double getAngularVelocity() throws SensorDoesNotExistException {
        if (!m_angleSensors.isEmpty()) {
            return m_angleSensors.getRate();
        }

        throw new SensorDoesNotExistException(
            "Tried to get the angular velocity of " + getName()
            + ", but it does not have any angke sensors!"
        );
    }



    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = new ArrayList<>();
        
        if (m_distanceSensor instanceof DistanceSensorGroup) {
            DistanceSensorGroup sensorGroup = (DistanceSensorGroup)m_distanceSensor;
            loggables.addAll(sensorGroup.getLoggableData());
        }

        loggables.add(new LoggableDouble(getName() + "/Position", this::getPosition));
        loggables.add(new LoggableDouble(getName() + "/Velocity", this::getVelocity));

        return loggables;
    }
}