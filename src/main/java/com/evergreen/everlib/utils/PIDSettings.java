package com.evergreen.everlib.utils;

import com.evergreen.everlib.shuffleboard.constants.ConstantDouble;
import com.evergreen.everlib.shuffleboard.constants.DashboardConstants;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * A utillity class to easily keep constant parameters, and to avoid cluttering constructors.
 */
public class PIDSettings {

    /**Proportional constant supplier*/
    private final ConstantDouble m_P;
    /**Integral Constant supplier*/
    private final ConstantDouble m_I;
    /**Derivative constant supplier */
    private final ConstantDouble m_D;
    /**Feedforward constant supplier*/
    private final ConstantDouble m_F;
    /**Tolernace supplier */
    private final ConstantDouble m_tolerance;
    /**Time between each calculation cycle */
    private final ConstantDouble m_period;

    private final PIDController m_controller;

    private static final double 
        DEFAULT_TOLEANCE = 0.0,
        DEFAULT_F  = 0.0,
        DEFAULT_PERIOD = 0.02;

    /**
     * Constructs {@link PIDSettings} according to input P, I and D and F constants and given subsystem.
     * @param subsystem - The subsystem to use the PIDF loop on.
     * @param kP - The proportional constants.
     * @param kI - The integral constant
     * @param kD - The derivative constant.
     * @param tolerance - the loop's tolerance; At what point away from the target should the loop
     * be satisfied?
     * @param kF - The feed-forward constant.
     * @param period - The time between each controller update. 
    */
    public PIDSettings(String systemName, double kP, double kI, double kD, 
        double tolerance, double kF, double period) {

        DashboardConstants.getInstance().pushd("/" + systemName + "/PID");

        m_P = new ConstantDouble("kP", kP);
        m_I = new ConstantDouble("kI", kI);
        m_D = new ConstantDouble("kD", kD);
        m_F = new ConstantDouble("kF", kF);
        
        m_tolerance = new ConstantDouble(
            systemName + "Tolerance", tolerance);
        
        m_period = new ConstantDouble(
            systemName + "Period", period);   

        m_controller = new PIDController(kP, kI, kD, period);
        m_controller.setTolerance(tolerance);
        DashboardConstants.getInstance().popd();
    }

    /**
     * Constructs {@link PIDSettings} according to input P, I and D and F 
     * constants and given subsystem, with a default period of 0.02.
     * @param subsystem - The subsystem to use the PIDF loop on.
     * @param kP - The proportional constants.
     * @param kI - The integral constant
     * @param kD - The derivative constant.
     * @param tolerance - the loop's tolerance; St what point away from the target should the loop
     * be satisfied?
     * @param kF - The feed-forward constant.
    */
    public PIDSettings(String systemName, double kP, double kI, double kD, 
        double tolerance, double kF) {
            this(systemName, kP, kI, kD, tolerance, kF, DEFAULT_PERIOD);
    }


    /**
     * Constructs {@link PIDSettings} according to input P, I and D
     * constants and given subsystem, with a default period of 0.02.
     * @param subsystem - The subsystem to use the PIDF loop on.
     * @param kP - The proportional constants.
     * @param kI - The integral constant
     * @param kD - The derivative constant.
     * @param tolerance - the loop's tolerance; St what point away from the target should the loop
     * be satisfied?
    */
    public PIDSettings(String systemName, double kP, double kI, double kD, 
        double tolerance) {
            this(systemName, kP, kI, kD, tolerance, DEFAULT_F, DEFAULT_PERIOD);
    }

    /**
     * Constructs {@link PIDSettings} according to input P, I and D 
     * constants and given subsystem, with a default period of 0.02 and a default tolerance of 0.
     * 
     * @param subsystem - The subsystem to use the PIDF loop on.
     * @param kP - The proportional constants.
     * @param kI - The integral constant
     * @param kD - The derivative constant.
    */
    public PIDSettings(String systemName, double kP, double kI, double kD) {
            this(systemName, kP, kI, kD, DEFAULT_TOLEANCE, DEFAULT_F, DEFAULT_PERIOD);
    }


    public double kP() {
        return m_P.get();
    }

    public double kI() {
        return m_I.get();
    }

    public double kD() {
        return m_D.get();
    }

    public double kF() {
        return m_F.get();
    }


    public void setP(double kP) {
        m_P.setValue(kP);
    }

    public void setI(double kI) {
        m_I.setValue(kI);
    }

    public void setD(double kD) {
        m_D.setValue(kD);
    }

    public void setF(double kF) {
        m_F.setValue(kF);

    }

    public void setTolerance(double tolerance) {
        m_tolerance.setValue(tolerance);
    }

    public void setPID(double kP, double kI, double kD) {
        setP(kP);
        setI(kI);
        setD(kD);   
    }

    public void setPID(double kP, double kI, double kD, double tolerance) {
        setPID(kP, kI, kD);
        setTolerance(tolerance);
    }

    public void setPID(double kP, double kI, double kD, double tolerance, double kF) {
        setPID(kP, kI, kD, tolerance);
        setF(kF);
    }

    public double getTolerance() {
        return m_tolerance.get();
    }


    public double getPeriod() {
        return m_period.get();
    }


    public PIDController getController() {
        return m_controller;
    }

}