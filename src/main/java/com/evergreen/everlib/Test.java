package com.evergreen.everlib;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.evergreen.everlib.oi.joysticks.ExtremeProJoystick;
import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.loggables.DashboardStreams;
import com.evergreen.everlib.structure.Tree;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController;
import com.evergreen.everlib.subsystems.motors.subsystems.MotorController.ControllerType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * Test
 */
public class Test extends Tree {
    
    
    MotorController m_motorRight = new MotorController(
        "Motor Right",
        new MotorController("Talon", ControllerType.TALON_SRX, 9),
        new MotorController("Victor", ControllerType.VICTOR_SPX, 0));

    MotorController m_motorLeft = new MotorController(
        "Motor Left",
        new MotorController("Talon", ControllerType.TALON_SRX, 1),
        new MotorController("Victor", ControllerType.VICTOR_SPX, 4));

    WPI_TalonSRX m_leftA = new WPI_TalonSRX(9);
    WPI_TalonSRX m_rightA = new WPI_TalonSRX(1);
    WPI_VictorSPX m_leftB = new WPI_VictorSPX(0);
    WPI_VictorSPX m_rightB = new WPI_VictorSPX(4);

    ConstantDouble m_speedModifier = new ConstantDouble("Speed", 0.5);
    ConstantDouble m_change = new ConstantDouble("Right Adjust", 1.2);

    ExtremeProJoystick m_jsLeft = new ExtremeProJoystick("Joystick Left", 0, (v) -> v * 0.5 * 0.85);
    ExtremeProJoystick m_jsRight = new ExtremeProJoystick("Joystick Right", 1, (v) -> v * 0.5);

    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  
    @Override
    protected void componentSetup() {
    }

    @Override
    protected void bindButtons() {
        
    }

    @Override
    protected void commandConfig() {
        

    }

    @Override
    protected void log() {
        DashboardStreams.getInstance().addLoggable(m_motorLeft);
        DashboardStreams.getInstance().addLoggable(m_motorRight);
        DashboardStreams.getInstance().addDouble(
            "Chassis Left Encoder", 
            () -> (double)((WPI_TalonSRX)m_motorLeft.getMotors().get(0)).getSelectedSensorPosition());
        DashboardStreams.getInstance().addDouble(
            "Talon Encoder", () -> (double)m_leftA.getSelectedSensorPosition());
    }

    @Override
    protected void whenEnabled() {
        

    }

    @Override
    protected void autoConfig() {
        

    }

    @Override
    protected void teleopConfig() {
        

    }

    @Override
    protected void test() {
        
    }

    @Override
    public void testPeriodic() {
        
        m_leftA.set(m_jsLeft.getY());
        m_leftB.set(m_jsLeft.getY());
        m_rightA.set(m_jsRight.getY());
        m_rightB.set(m_jsRight.getY());
    }

    
}