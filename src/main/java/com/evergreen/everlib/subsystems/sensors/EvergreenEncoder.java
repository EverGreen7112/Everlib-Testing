package com.evergreen.everlib.subsystems.sensors;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Encoder;

/**
 * EncoderBase
 */
public class EvergreenEncoder extends DistanceSensor {

    private double m_distancePerPulse;
    private Supplier<Integer> m_ticksSupplier;
    private Consumer<Integer> m_ticksSetter;


    private EvergreenEncoder(
        String name, Supplier<Integer> ticksSupplier,
        Consumer<Integer> ticksSetter, double distancePerPulse) {
        super(name);
        m_ticksSupplier = ticksSupplier;
        m_distancePerPulse = distancePerPulse;
    }

    public EvergreenEncoder(String name, CANEncoder wrappedEncoder, double distancePerPulse) {
        this(
            name,  
            () -> (int)wrappedEncoder.getPosition() / 
                //Divide by pos factor to make sure to get ticksSupplier instead of distance.
                (int)wrappedEncoder.getPositionConversionFactor(),
            (position) -> wrappedEncoder.setPosition(position), 
            distancePerPulse);
    }
    
    public EvergreenEncoder(String name, CANEncoder wrappedEncoder) {
        this(name, wrappedEncoder, 1);
    }


    public EvergreenEncoder(String name, WPI_TalonSRX wrappedEncoder, double distancePerPulse) {
        this(
            name, 
            wrappedEncoder::getSelectedSensorPosition, 
            wrappedEncoder::setSelectedSensorPosition,
            distancePerPulse);
    }
    
    public EvergreenEncoder(String name, WPI_TalonSRX wrappedEncoder) {
        this(name, wrappedEncoder, 1);
    }

    public EvergreenEncoder(String name, Encoder wrappedEncoder, double distancePerPulse) {
        this(
            name, 
            wrappedEncoder::getRaw,
            (position) -> wrappedEncoder.reset(),
            distancePerPulse
        );
    }
    
    public EvergreenEncoder(String name, Encoder wrappedEncoder) {
        this(name, wrappedEncoder, 1);
    }
    
    public EvergreenEncoder(String name, int portA, int portB, double distancePerPulse) {
        this(name, new Encoder(portA, portB), distancePerPulse);
    }

    public EvergreenEncoder(String name, int portA, int portB) {
        this(name, portA, portB, 1);
    }

    public double getTicks() {
        return m_ticksSupplier.get();
    }

    public double _getPosition() {
        return m_ticksSupplier.get() * m_distancePerPulse;
    }

    public double getDistancePerPulse() {
        return m_distancePerPulse;
    }
    public void setDistancePerPulse(double distancePerPulse) {
        m_distancePerPulse = distancePerPulse;
    }

    public void setPosition(double position) {
        m_ticksSetter.accept( (int)(position / m_distancePerPulse) );
        getPosition();
    }

    public void reset() {
        setPosition(0);
    }

    @Override
    protected double _getDistance() {
        return m_ticksSupplier.get() * m_distancePerPulse;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        List<LoggableData> result = super.getLoggableData();
        result.addAll(List.of(
            new LoggableDouble("Ticks", this::getTicks)
        ));

        return result;
    }

    

}