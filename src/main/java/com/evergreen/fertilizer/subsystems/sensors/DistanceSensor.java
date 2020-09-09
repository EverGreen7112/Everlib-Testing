package com.evergreen.fertilizer.subsystems.sensors;

import java.util.List;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableObject;
import com.evergreen.fertilizer.subsystems.EvergreenSubsystem;
import com.evergreen.fertilizer.utils.constraints.Constraint;
import com.evergreen.fertilizer.utils.constraints.Free;

/**
 * Add your docs here.
 */
public abstract class DistanceSensor implements LoggableObject {

    private String m_name;

    private Constraint m_absoluteLimits;
    private double m_offset;
    private String m_subsystemName;


    protected double m_lastPosition;
    protected double m_lastCalcualtionTime;

    private boolean m_killSwitch;

    public DistanceSensor(String name) {
        this(name, new Free(), 0);
    }

    public DistanceSensor(String name, double offset) {
        this(name, new Free(), offset);
    }

    public DistanceSensor(String name, Constraint absoluteLimit) {
        this(name, absoluteLimit, 0);
    }

    public DistanceSensor(String name, Constraint absoluteLimit, double offset) {
        m_name = name;
        m_absoluteLimits = absoluteLimit;
        m_offset = offset;
    }

    protected abstract double _getDistance();

    public final double getPosition() {
        double distance = _getDistance() + m_offset;

        if (!m_killSwitch && !m_absoluteLimits.allowed(distance)) {
            m_killSwitch = true;
        }

        if (m_killSwitch)
            return 0;

        
        m_lastPosition = distance;
        m_lastCalcualtionTime = System.currentTimeMillis() / 1000;

        return distance;

    }

    public final synchronized double getVelocity() {
        
        double lastPos = m_lastPosition;
        double lastPosTime = m_lastCalcualtionTime;
        double now = System.currentTimeMillis() / 1000;
        
        return (getPosition() - lastPos) / (now - lastPosTime);
    }

    public boolean inRange(double minDistance, double maxDistance) {
        return getPosition() > minDistance && getPosition() < maxDistance;
    }

    public boolean atLeast(double minDistance) {
        return getPosition() > minDistance;
    }

    public boolean atMost(double maxDistance) {
        return getPosition() < maxDistance;
    }

    public void setSubsystem(EvergreenSubsystem subsystem) {
        m_subsystemName = subsystem.getName();
    }

    public String getSubsystem() {
        return m_subsystemName;
    }

    public boolean killed() {
        return m_killSwitch;
    }

    public void setAbsoluteLimits(Constraint absoluteLimits) {
        m_absoluteLimits = absoluteLimits;
    }

    public void setOffset(double offset) {
        m_offset = offset;
    }

    public double getOffset() {
        return m_offset;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableDouble("Raw Distance", this::_getDistance),
            new LoggableDouble("Returned Distance", this::getPosition),
            new LoggableDouble("Offset", this::getOffset),
            new LoggableBoolean("Killed", this::killed)
        );
    }


}
