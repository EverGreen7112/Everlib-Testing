package com.evergreen.fertilizer.oi.joysticks;

import com.evergreen.fertilizer.utils.Adjuster;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * A class for <a href="https://www.logitechg.com/en-roeu/products/gamepads/extreme-3d-pro-joystick.html">
 * Logitech's Extreme 3D Pro Joystick</a>, using our knowledge of the model to create easy 
 * access to its buttons (see {@link #getButton(X, Y, Z)})
 * <p><p>
 * Note that this class inherits {@link JoystickEG}, so it can be constructed with an
 * {@link Adjuster} which will automatically adjust its getRawAxis values, and can be easily
 * exponentiated with {@link #setExponential()} and {@link #setInverted()}. 
 * 
 * @author Atai Ambus
 */
public class ExtremeProJoystick extends EvergreenJoystick {


    /**
     * Constructs an {@link ExtremeProJoystick} object at given port.
     * 
     * @param port - The joystick's port, as tuned in the DriverStation.
     */
    public ExtremeProJoystick(String name, int port)
    {
        super(name, port);
    }

    /**
     * Constructs an {@link ExtremeProJoystick} object at given port,
     * its values adjusted by input {@link Adjuster}
     * 
     * @param port - The joystick port, as tuned in the DriverStation
     * @param adjuster - The function to adjust Joystick output.
     */
    public ExtremeProJoystick(String name, int port, Adjuster<Double> adjuster)
    {
        super(name, port, adjuster);
    }

    /**An {@link ExtremeProJoystick} button position on the x axis - left or right*/
    public enum X {
        LEFT(),
        RIGHT();
    }
    
    /**A section of buttons on the joystick - either forward (farthest away form driver),
     * back (closest to driver), or middle (between right and left).
     * <p>
     * Each y section divides further into {@link X Left and Right buttons}.
    */
    public enum Y {
        FORWARD(),
        MIDDLE(),
        BACK();
    }

    /**A section of buttons on the joystick - either the top, right on the joystick itself, 
     * or bottom, on the base of the joystick.
     * <p>
     * Each Z section divides further by closeness to the driver ({@link Y forward, back, or middle})
     */
    public enum Z {
        TOP(),
        BOTTOM();
    }

    /**
     * Gets a {@link JoystickButton} from this joystick by input {@link X}, {@link Y} and {@link Z}
     * sections <p>
     * 
     * The buttons of each {@link ExtremeProJoystick} are divided thrice - once on the Z axis, 
     * into top (on top of the joystick and on its base), then in each top and bottom divided
     * into back (closest to driver) and forward (farthest from driver). In the bottoms ection 
     * it is divided more, to also a middle section (between forward and back). Each Y swction 
     * is divided once more - into Left and Right buttons (X axis.)
     * 
     * As such we require three -
     * 
     * @param x - The button's position on the X axis - left or right.
     * @param y - The button's section on the Y axis - forward, middle or back.
     * @param z - The button's section on the Z axis - top or bottom
     *  
     * @return - The {@link JoystickButton} at the matching position.
     */
    public Button getButton(X x, Y y, Z z) {
        int port = 3; // Start after trigger and thumb

        if (x.equals(X.RIGHT)) {
            port += 1; //Right shifts by one
        }

        if (z.equals(Z.TOP)) {


            if (y.equals(Y.MIDDLE)) { //No middle button in top
                    throw new IllegalArgumentException("There is no middle button at"
                    + " an ExtremeProJoystick's top section!");
            } else if (y.equals(Y.FORWARD)) {
                port += 2;
            }

        } else {
            port += 4; //If bottom, Skip Top buttons

            if (y.equals(Y.MIDDLE)) {
                port += 2; //Skip over the back row (left and right)
            } else if (y.equals(Y.FORWARD)) {
                port += 4; //Skip over the back and middle row (in each left and right)
            }

        }

        return new JoystickButton(
            this, port);
    }

    /**
     * @return - this joystick's trigger button
     */
    public Button trigger() {
        return new JoystickButton(this, 1);
    }

    /**
     * @return - this joystick's thumb button.
    */
    public Button thumb() {
        return new JoystickButton(this, 2);
    }
}
