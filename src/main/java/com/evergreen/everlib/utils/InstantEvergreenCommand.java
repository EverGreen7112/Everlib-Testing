package com.evergreen.everlib.utils;

import com.evergreen.everlib.subsystems.EvergreenCommand;

/**
 * InstantCommandEG
 */
public class InstantEvergreenCommand extends EvergreenCommand {

    Runnable m_toRun;

    public InstantEvergreenCommand(String name, Runnable command) {
        super(name);
        m_toRun = command;
    }

    @Override
    public void initialize() {
        m_toRun.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}