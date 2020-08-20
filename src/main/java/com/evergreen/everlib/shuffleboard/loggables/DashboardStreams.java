package com.evergreen.everlib.shuffleboard.loggables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.evergreen.everlib.shuffleboard.handlers.Explorer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DashboardStreams
 */
public class DashboardStreams extends Explorer {

    private final List<LoggableData> m_loggables = new ArrayList<>();

    private static DashboardStreams m_instance;

    private DashboardStreams() {
        super("Streams Explorer");
    }

    public static DashboardStreams getInstance() {
        if (m_instance == null) m_instance = new DashboardStreams();
        return m_instance;
    }

    private void log(LoggableData loggable) {
        String name = loggable.getKey();
        String key = pwd() + "/" + name;

        if (!SmartDashboard.containsKey(key)) {
            loggable.setKey(key);
            loggable.addToDashboard();
            m_loggables.add(loggable);
        }

        else {
            System.out.println(String.format("Tried to add \"%s\" %s value to the shuffleboard under "
                    + " %s, but couldn't becuase there was already a value under that "
                    + "key.", name, loggable.getType(), pwd()));
        }
    }

    public void addString(String key, Supplier<String> valueSupplier) {
        log(new LoggableString(key, valueSupplier));
    }

    public void addInt(String key, Supplier<Integer> valueSupplier) {
        log(new LoggableInt(key, valueSupplier));
    }

    public void addDouble(String key, Supplier<Double> valueSupplier) {

        log(new LoggableDouble(key, valueSupplier));
    }

    public void addBoolean(String key, Supplier<Boolean> valueSupplier) {

        log(new LoggableBoolean(key, valueSupplier));
    }

    public void addLoggable(LoggableObject loggable) {

        pushd(loggable.getName());

        for (LoggableData loggableData : loggable.getLoggableData()) 
        {
            log(loggableData);
        }

        popd();
    }


    public void update() {
        for (LoggableData loggableData: m_loggables) {
            loggableData.addToDashboard();
        }
    }


}