package com.evergreen.fertilizer.subsystems.motors.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.evergreen.fertilizer.subsystems.sensors.EvergreenEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;

import com.evergreen.fertilizer.Exceptions.SensorDoesNotExistException;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableObject;





/**
 * This is a wrapper class for {@link SpeedController}, which allowes easier and more generic
 * construction of SpeedControlelr objects.
 * 
 * @author Atai Ambus
 */
public class MotorController implements SpeedController, LoggableObject {

    /**
     * MotorInitialize
     */
    public interface MotorInitializer {
        SpeedController generate(int channel);
    }

    /**
     * A list of all motors this objects controlls.
      */
    private final ArrayList<SpeedController> m_motors = new ArrayList<>();
    private final List<EvergreenEncoder> m_encoders = new ArrayList<>();
    private final String m_name;


    /**
     * Constructs a new {@link MotorController} which controlls one of more motors with the same 
     * type of electronic controller.
     * 
     * @param type - The {@link ControllerType} type of the electronic speed controllers that 
     * produce the voltage for the motors. (Victor, Talon, ect.)
     * 
     * @param ports - The roborio ports the voltage controllers conect to.
     */
    public MotorController(String name, ControllerType type, int... ports) {
        
        m_name = name;
        SpeedController iterationMotor;

        //intializing them.
        for(int i = 0; i < ports.length; i++)
        {
            iterationMotor = type.initlize(ports[i]);

            if (type == ControllerType.SPARKMAX_BRUSHED || 
                type == ControllerType.SPARKMAX_BRUSHLESS) {
                    m_encoders.add(new EvergreenEncoder( 
                        getName() + "/Encoder #" + i,
                        //Casting the motor to sparkmax to get its encoder
                        ((CANSparkMax)iterationMotor) 
                            .getEncoder()));
            }
                
            else if (type == ControllerType.TALON_SRX) {
                m_encoders.add(new EvergreenEncoder(
                    getName() + "/Encoder #" + i,
                    (WPI_TalonSRX)iterationMotor));
            }

            m_motors.add(iterationMotor);
        }
    }


    public List<SpeedController> getMotors() {
        return m_motors;
    }

    /**
     * @return A list of all encoders this motor uses.
     */
    public List<EvergreenEncoder> getEncoders() {
        return m_encoders;
    }

    /**
     * Returns the encoder of the first encoder this controller uses. 
     * Usefull if there is only one.
     * <p>
     * If the controller has no encoders, a  {@link SensorDoesNotExistException} will be thrown
     */
    public EvergreenEncoder getEncoder() {

        if (m_encoders.isEmpty())
            throw new SensorDoesNotExistException("Tried to get an encoder of \"" + getName() + "\","
            + " but it has no encoders!");
        return m_encoders.get(0);
    }


    /**
     * Consrtucts a {@link MotorController} which combines multiple other MotorControllers.
     * Also works as a copy constructor. <p>
     * 
     * The new Controller's methods will aplly on all the given controllers.
     * 
     * @param controllers - The controllers to combine.
     */
    public MotorController(String name, MotorController... controllers)
    {
        m_name = name;
        
        //Foreach of the motors, add its controller to the controller list.
        for(MotorController controller : controllers)
        {
            getMotors().addAll(controller.getMotors());
            m_encoders.addAll(controller.getEncoders());
        }
    }

    @Override
    public void set(double speed) {

        for (SpeedController controller : getMotors()) {
            controller.set(speed);   
        }
    }

    public void setAt(int index, double speed) {
        getMotors().get(index).set(speed);
    }

    @Override
    public double get() {

        double sum = 0;
        
        for (SpeedController controller : getMotors()) {
            sum += controller.get();
        }

        return sum / getMotors().size();
    }

    public double getAt(int index) {
        return getMotors().get(index).get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        for (SpeedController controller : getMotors()) {
            controller.setInverted(isInverted);
        }
    }

    public void setInverted(int index, boolean isInverted) {
        getMotors().get(index).setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        
        for (SpeedController controller : getMotors()) {
            if (!controller.getInverted()) return false;
        }

        return true;
    }

    public boolean getInverted(int index) {
        return getMotors().get(index).getInverted();
    }

    @Override
    public void disable() {
        
        for (SpeedController controller : getMotors()) {
            controller.disable();
        }
    }

    public void disableAt(int index) {
        getMotors().get(index).disable();
    }

    @Override
    public void stopMotor() {
        for (SpeedController controller : getMotors() ) {
            controller.stopMotor();
        }
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> result = new ArrayList<>();

        for (int i = 0; i < getMotors().size(); i++) {
            result.add(new LoggableDouble("Motors/Values/#" + i, getMotors().get(i)::get));
            result.add(new LoggableBoolean("Motors/Inverted/#" + i, getMotors().get(i)::getInverted));
        }

        for (int i = 0; i < getEncoders().size(); i++) {
            result.add(new LoggableDouble("Encoders/Ticks/#" + i, getEncoders().get(i)::getTicks));
            result.add(new LoggableDouble("Encoders/Distance/#" + i, getEncoders().get(i)::getPosition));
            result.add(new LoggableDouble("Encoders/Speed/#" + i, getEncoders().get(i)::getVelocity));
        }

        return result;

    }

    /**A controller model - Victor SPX, Talon SRX, ect. */
    public enum ControllerType
    {
        VICTOR_SPX(WPI_VictorSPX::new),
        TALON_SRX(WPI_TalonSRX::new),
        SPARKMAX_BRUSHED( (port) -> new CANSparkMax(port, MotorType.kBrushed)),
        SPARKMAX_BRUSHLESS( (port) -> new CANSparkMax(port, MotorType.kBrushless));

        MotorInitializer m_init;

        SpeedController initlize(int channel) {
            return m_init.generate(channel);
        }

        ControllerType(MotorInitializer init) {
            m_init = init;
        }
    }

    @Deprecated
    @Override
    public void pidWrite(double output) {
        for (SpeedController controller : getMotors()) {
            controller.pidWrite(output);
        }
    }
}
