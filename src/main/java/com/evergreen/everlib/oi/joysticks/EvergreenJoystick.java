package com.evergreen.everlib.oi.joysticks;
import java.util.Arrays;
import java.util.List;

import com.evergreen.everlib.oi.OIExceptions;
import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableDouble;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;
import com.evergreen.everlib.utils.Adjuster;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The base class for {@link Joystick}s in the Evergreen Framework.
* <p>
 * Most importantly, it provides easy methods to invert and expontiate the
 * joytsick axis, and allowes {@link #getRawAxis(AxisType) getRawAxis} by
 * {@link AxisType}, for clearer code.
* <p>
 * For more advance adjustments,this class contains an {@link Adjuster adjuster}
 * for each of the axes, which can be set using its
 * {@link #setAxisAdjuster(int, Adjuster)} method.
* <p>
 * It also allowes a quick disable with its {@link #kill()} method, which converts all outputs to 0.
 * 
 * @author Atai Ambus
 */
public class EvergreenJoystick extends Joystick implements LoggableObject {


    /** if none were specified, the joystick's adjuster will be set to this */
    private static final Adjuster<Double> DEFAULT_ADJUSTER = (val) -> val;

    /** The joystick's name (for logging purposes) */
    private final String m_name;

    /**
     * Whether the joystick is quadratic - the position-to-value function
     * ({@link Joystick#getRawAxis(int)} is quadratic or linear.
     */
    private boolean m_quadratic = false;

    /**
     * Whether the joystick is inverted = should we multiply its values by -1?
     */
    private boolean m_inverted = false;

    /**
     * An array of the adjusters for each axis.
     */
    @SuppressWarnings("unchecked")
    private Adjuster<Double>[] m_adjusters = (Adjuster<Double>[]) new Adjuster[AXES_NUM()];

    /**
     * Constructs a {@link EvergreenJoystick} at input port.
     * 
     * @param name - A name for the joystick (for logging purposes)
     * @param port - The joystick's port, as tuned in the Driver Station
     */
    public EvergreenJoystick(String name, int port) {
        this(name, port, DEFAULT_ADJUSTER);
    }

    /**
     * Constructs a {@link EvergreenJoystick} at input port, adjusting its 
     * values according to input adjuster
     * 
     * @param name - A name for the joystick (for logging purposes)
     * @param port - The joystick's port, as tuned in the Driver Station
     * @param adjuster - A function to adjust the joystick's output
     */
    public EvergreenJoystick(String name, int port, Adjuster<Double> adjuster) {
        super(port);
        m_name = name;
        Arrays.fill(m_adjusters, DEFAULT_ADJUSTER);

    }

    public void setAxisAdjuster(Joystick.AxisType axis, Adjuster<Double> adjuster) {
        m_adjusters[axis.value] = adjuster;
    }

    public void setAxisAdjuster(Adjuster<Double> adjuster) {
        Arrays.fill(m_adjusters,adjuster);
    }

    @Override
    public double getRawAxis(int axis) throws OIExceptions.AxisDoesNotExistException {
        if (axis > AXES_NUM())
            throw new OIExceptions.AxisDoesNotExistException();

        double value = super.getRawAxis(axis);
        value = m_adjusters[axis].adjust(value);

        if (m_quadratic) {
            value *= Math.abs(value);
        }

        if (m_inverted) {
            value *= -1;
        }

        return value;
    }

    public double getRawAxis(Joystick.AxisType axis) throws OIExceptions.AxisDoesNotExistException {
        return getRawAxis(axis.value);
    }

    public void setQuadratic() {
        m_quadratic = true;
    }


    public void setInverted() {
        m_inverted = true;
    }

    public void kill() {
        Adjuster<Double> dead = (v) -> 0.0; // Compiler does not like one line.
        Arrays.fill(m_adjusters, dead);
    }

    @Override
    public String getName() {
        return m_name;
    }

    

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableDouble("X axis", this::getX),
            new LoggableDouble("Y axis", this::getY),
            new LoggableDouble("Z axis", this::getZ),
            new LoggableDouble("Throttle", this::getThrottle),
            new LoggableDouble("Twist", this::getTwist)
        );
    }

    private static int AXES_NUM() {
        return 5;
    }
}
