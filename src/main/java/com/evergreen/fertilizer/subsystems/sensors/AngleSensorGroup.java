package com.evergreen.fertilizer.subsystems.sensors;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableObject;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * AngleSensorGroup
 */
public class AngleSensorGroup implements Gyro, LoggableObject {
    private List<Gyro> m_sensors = new ArrayList<>();
    private String m_name;

    public AngleSensorGroup(String name) {
        m_name = name;
    }

    public void addSensor(Gyro angleSensor) {
        m_sensors.add(angleSensor);
    }

    @Override
    public void close() throws Exception {
        for (Gyro gyro : m_sensors) {
            gyro.close();
        }

    }

    @Override
    public void calibrate() {
        for (Gyro gyro : m_sensors) {
            gyro.calibrate();
        }

    }

    @Override
    public void reset() {
        for (Gyro gyro : m_sensors) {
            gyro.reset();
        }
    }

    @Override
    public double getAngle() {
        double sum = 0;

        for (Gyro gyro : m_sensors) {
            sum += gyro.getAngle();
        }

        return sum / m_sensors.size();
    }

    @Override
    public double getRate() {
        double sum = 0;

        for (Gyro gyro : m_sensors) {
            sum += gyro.getRate();
        }

        return sum / m_sensors.size();
    }

    public Gyro getAt(int index) {
        return m_sensors.get(index);
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of();
    }

    public boolean isEmpty() {
        return m_sensors.size() < 1;
    }

    public double size() {
        return m_sensors.size();
    }

    
    
}