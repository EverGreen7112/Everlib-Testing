package com.evergreen.fertilizer.subsystems.sensors;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;

/**
 * SensorGroup
 */
public class DistanceSensorGroup extends DistanceSensor {
    List<DistanceSensor> m_sensors;
    int sum;

    public DistanceSensorGroup(String name, DistanceSensor... sensors) {
        super(name);
        m_sensors = List.of(sensors);
    }

    @Override
    public double _getDistance() {
        int sum = 0;


        for (DistanceSensor sensor : m_sensors) {

            if (sensor.killed()) {
                m_sensors.remove(sensor);
                continue;
            }
            
            sum += sensor.getPosition();
        }

        return sum / m_sensors.size();
    }

    public boolean isEmpty() {
        return m_sensors.size() == 0;
    }

    @Override
    public String getName() {
        return getSubsystem() + "/Distance sensor";
    }

    public void addSensor(DistanceSensor sensor) {
        m_sensors.add(sensor);
    }

    public List<DistanceSensor> getSensors() {
        return m_sensors;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> loggables = new ArrayList<>();

        for (DistanceSensor sensor : m_sensors) { 
            loggables.add(new LoggableDouble(getSubsystem() + "/sensors/" + sensor.toString(),
            sensor::getPosition));
        }

        return loggables;
    }
}