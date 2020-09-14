package com.evergreen.fertilizer.oi.joysticks;

import java.util.ArrayList;
import java.util.List;

import com.evergreen.fertilizer.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.utils.Adjuster;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * A class for <a href="https://www.logitechg.com/en-roeu/products/gamepads/f310-gamepad.html">
 * Logitech's F310 Gamepad</a>, with all of its buttons as members, so there is no need to 
 * initialize or keep track of individual buttons. <p><p>
 * Note that this class inherits {@link EvergreenJoystick}, so it can be constructed with an
 * {@link Adjuster} which will automatically adjust its getRawAxis values.
 * 
 * @author Atai Ambus
 */
public class F310GamePad extends EvergreenJoystick {
    
    /**Constructs an {@link F310GamePad} at input port
     * 
     * @param port - the joystick's port, as tuned in the DriverStation
     */
    public F310GamePad(String name, int port)
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
    public F310GamePad(String name, int port, Adjuster<Double> adjuster)
    {
        super(name, port, adjuster);
    }

    
    /**A list of the {@link F310GamePad}'s buttons*/
    public enum F310 {
        X(1),
        A(2),
        B(3),
        Y(4),
        LB(5),
        RB(6),
        LT(7),
        RT(8),
        BACK(9),
        START(10),
        JOYSTICK_LEFT(11),
        JOYSTICK_RIGHT(12);
        
        /**The button's port on the joystick */
        int m_buttonPort;

        private F310(int buttonPort) {
            m_buttonPort = buttonPort;
        }

        public int getPort() {
            return m_buttonPort;
        }
    }


    /**
     * Gets a button from an {@link F310GamePad}.
     * @param button - the button's value in the {@link F310} enum.
     * @return A {@link Button} object of this joystick, 
     * matching the input value.
     */
    public Button getButton(F310 button) {
        return new JoystickButton(this, button.m_buttonPort);
    }

    @Override
    public List<LoggableData> getLoggableData() {
        ArrayList<LoggableData> res = new ArrayList<>(super.getLoggableData());
        for (F310 button : F310.values()) {
            res.add((LoggableData)(new LoggableBoolean(button.name(), getButton(button)::get)));
        }
        return res;
    }
    
}
