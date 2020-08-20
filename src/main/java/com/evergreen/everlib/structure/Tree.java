package com.evergreen.everlib.structure;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.subsystems.EvergreenCommand;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Tree
 */
public abstract class Tree extends TimedRobot {
    
    @Override
    public void robotInit() {
        bindButtons();
        commandConfig();
        log();
    }

    @Override
    public void autonomousInit() {
        whenEnabled();
        autoConfig();
        for (EvergreenCommand autoCommand : getAutoCommands()) {
            autoCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        teleopConfig();

        for (EvergreenCommand teleopCommand : getTeleopCommands()) {
            teleopCommand.schedule();
        }
    }

    @Override
    public void robotPeriodic() {
        DashboardStreams.getInstance().update();
        update();
    }

    @Override
    public void testInit() {
        whenEnabled();
        test();
    }

    protected abstract void componentSetup();
    protected abstract void bindButtons();
    protected abstract void commandConfig();
    protected abstract void log();

    protected abstract void whenEnabled();

    protected abstract void autoConfig();
    protected abstract void teleopConfig();


    protected abstract void test();


    protected void update() { }

    protected List<EvergreenCommand> getAutoCommands() { return new ArrayList<>(); }
    protected List<EvergreenCommand> getTeleopCommands() { return new ArrayList<>(); }

}