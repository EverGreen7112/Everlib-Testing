package com.evergreen.fertilizer;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Main
 */
public class Main {

    private Main() {}

    public static void main(String[] args) {
        RobotBase.startRobot(Test::new);
    }
}