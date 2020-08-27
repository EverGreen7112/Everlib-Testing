package com.evergreen.everlib.subsystems;

import java.util.List;

import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableInt;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * The basic command for the Eververgreen Framework.
 * 
 * It contains a switch and will only start if it is on.
 * 
 * @author Atai Ambus
 */
public abstract class EvergreenCommand extends CommandBase implements LoggableObject {
    private ConstantBoolean m_commandSwitch;

    private int m_scheduleCounter = 0;
    private int m_ranCounter = 0;
    int m_interruptCounter = 0;
    
    /**
     * Constructs a new {@link EvergreenCommand} with input name, and without logging it in the shuffleboard
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link ConstantBoolean Shuffleboard Switch}.
     */
    public EvergreenCommand(String name) {
        setName(name);
        m_commandSwitch = new ConstantBoolean(name + " | Switch");
    }

    
    /**
     * Constructs a new {@link EvergreenCommand} with input name.
     * 
     * @param name - The subsystem's name, corresponding to is {@link #getName()} method
     * as well as its {@link ConstantBoolean Shuffleboard Switch}.
     * 
     * @param subsystems - Any subsystems the command requires.
     */
    public EvergreenCommand(String name, EvergreenSubsystem... subsystems) {
        this(name);
        
        for (EvergreenSubsystem subsystem : subsystems) {
            addRequirements((Subsystem)subsystem);
        }

        m_commandSwitch = new ConstantBoolean(subsystems[0].getName() + "/command switches/" + name);
    }

    /**Schedules this command, defaulting to interruptible, 
     * as long both it and all of its required commands are enabled.*/
    @Override
    public void schedule(boolean interruptible) {
        
        m_scheduleCounter++;
        


        if (canStart()) {
            for (Subsystem subsystem : m_requirements) {
                if (subsystem instanceof EvergreenSubsystem) {
                    EvergreenSubsystem sub = (EvergreenSubsystem)subsystem;
                    boolean allowed = sub.useWith(this, interruptible);
                    if (!allowed) {
                        return;
                    }
                }
            }
            m_ranCounter++;
            super.schedule(interruptible);
        }
        
    }


    /**
     * Disables the command and prevents it from starting.
     */
    public void disable() {
        m_commandSwitch.disable();
    }

    /**
     * Enables the command, allowing it to start.
     */
    public void enable() {
        m_commandSwitch.enable();
    }

    /**
     * Toggles the command - if it is enabled disable it, and vice versa.
     */
    public void toggle() {
        m_commandSwitch.toggle();
    }

    /** 
     * @return the command's switch.
     */
    public ConstantBoolean getSwitch() {
        return m_commandSwitch;
    }

    @Override
    public boolean isFinished() {
        return !m_commandSwitch.get();
    }


    private boolean canStart() {
        
        for (Subsystem subsystem : getRequirements()) {
            if (subsystem instanceof EvergreenSubsystem) {
                EvergreenSubsystem sub = (EvergreenSubsystem)subsystem;
                if (!sub.getSwitchState())
                    return false;
            }
        }

        return m_commandSwitch.get();
    }

    @Override
    public List<LoggableData> getLoggableData() {

        String[] evergreenRequirements;
        
        if (m_requirements.size() == 0) {
            evergreenRequirements = new String[] {"None"};
        } else {
            evergreenRequirements = 
                (String[])m_requirements.stream()
                .filter(v -> v instanceof EvergreenSubsystem)
                .map(v -> ((EvergreenSubsystem)v).getName())
                .toArray(String[]::new);
        }
        List<LoggableData> res = List.of(new LoggableData[] {
            new LoggableInt("Schedules", () -> m_scheduleCounter),
            new LoggableInt("Runs", () -> m_ranCounter),
            new LoggableInt("Interruptions", () -> m_interruptCounter),
            new LoggableString("Requirements", () -> String.join(", ", evergreenRequirements))
        });
        return res;
    }

    public void addRequirements(EvergreenSubsystem... subsystems ) {
        super.addRequirements(subsystems);
    }
    
    @Override
    public void cancel() {
        m_interruptCounter++;
        super.cancel();
    }


    @Override
    public void initSendable(SendableBuilder builder) { }
}