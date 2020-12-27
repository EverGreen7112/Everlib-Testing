package com.evergreen.fertilizer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.evergreen.fertilizer.oi.joysticks.ExtremeProJoystick;
import com.evergreen.fertilizer.oi.joysticks.F310GamePad;
import com.evergreen.fertilizer.oi.joysticks.F310GamePad.F310;
import com.evergreen.fertilizer.shuffleboard.constants.ConstantDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.DashboardStreams;
import com.evergreen.fertilizer.structure.Tree;
import com.evergreen.fertilizer.subsystems.motors.subsystems.MotorController;
import com.evergreen.fertilizer.subsystems.motors.subsystems.MotorController.ControllerType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * Test
 */
public class Test extends Tree {
    
    WPI_VictorSPX m_leftVictorA = new WPI_VictorSPX(3);
    WPI_VictorSPX m_leftVictorB = new WPI_VictorSPX(14);
    WPI_TalonSRX m_leftTalon = new WPI_TalonSRX(15);
    WPI_VictorSPX m_rightVictorA = new WPI_VictorSPX(0);
    WPI_VictorSPX m_rightVictorB = new WPI_VictorSPX(2);
    WPI_TalonSRX m_rightTalon = new WPI_TalonSRX(1);
    
    MotorController m_motorRight = new MotorController(
        "Right Motors", 
        new MotorController("Right Victor A", ControllerType.VICTOR_SPX, 0), 
        new MotorController("Right Talon", ControllerType.TALON_SRX, 1), 
        new MotorController("Right Victor B", ControllerType.VICTOR_SPX, 2)
        );

    MotorController m_motorLeft = new MotorController(
        "Left Motors", 
        new MotorController("Left Victor A", ControllerType.VICTOR_SPX, 3), 
        new MotorController("Left Talon", ControllerType.TALON_SRX, 15), 
        new MotorController("Left Victor B", ControllerType.VICTOR_SPX, 14)
        );

    MotorController m_rightVictors = new MotorController("Right Victors", ControllerType.VICTOR_SPX, 0, 2);
    MotorController m_leftVictors = new MotorController("Left Victors", ControllerType.VICTOR_SPX, 3, 14);
    MotorController m_chassisTalons = new MotorController("Chassis Talons", ControllerType.TALON_SRX, 1, 15);

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
        DashboardStreams.getInstance().addDouble("Chassis Left Encoder Direct", 
            () -> (double)((WPI_TalonSRX)m_leftTalon).getSelectedSensorPosition());
        DashboardStreams.getInstance().addDouble("Chassis Right Encoder Direct", 
            () -> (double)((WPI_TalonSRX)m_rightTalon).getSelectedSensorPosition());
        DashboardStreams.getInstance().addDouble(
            "Chassis Left Encoder Motor List", 
            () -> (double)((WPI_TalonSRX)m_motorLeft.getMotors().get(1)).getSelectedSensorPosition());
        DashboardStreams.getInstance().addDouble(
            "Chassis Right Encoder Motor List",
            () -> (double)((WPI_TalonSRX)m_motorRight.getMotors().get(1)).getSelectedSensorPosition());
        DashboardStreams.getInstance().addDouble("Chassis Left Encoder List",
            () -> m_motorLeft.getEncoder().getPosition());
        DashboardStreams.getInstance().addDouble("Chassis Right Encoder List",
            () -> m_motorRight.getEncoder().getPosition());
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
        if (new F310GamePad("F310", 0).getButton(F310.A).get()) m_motorRight.stopMotor();
        m_motorRight.set(0.2);
        m_motorLeft.set(0.2);
    }
}