package com.evergreen.everlib.shuffleboard.constants;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.everlib.shuffleboard.handlers.Explorer;
import com.evergreen.everlib.subsystems.EvergreenSubsystem;

/**
 * DashboardConstants
 */
public class DashboardConstants extends Explorer {
    private final List<Constant> m_constants = new ArrayList<>();
    private static final DashboardConstants m_instance = new DashboardConstants();

    /**Singleton Constructor (private empty)*/
    private DashboardConstants() {
        super("Constants Explorer");
    }

    
    public void move(String folder, Constant... constants) {
        List.of(constants).forEach((c) -> c.move(folder));
    }

    public void resetValues()
    {
        for (Constant constant : m_constants) {
            constant.reset();
        }
    }

    public void resetBoard()
    {
        for (Constant constant : m_constants) {
            if (constant.wasAdded()) {
                constant.remove();
            }
        }
    }

    
    public static DashboardConstants getInstance() {
        return m_instance;
    }

    void addConstant(Constant constant) {
        m_constants.add(constant);
    }


    public void startConstantsOf(EvergreenSubsystem subsystem) {
        startConstantsOf(subsystem.getName());
    }

    public void startConstantsOf(String subsystemName) {
        cd("/" + subsystemName + "/Constants");
    }

    
    
}