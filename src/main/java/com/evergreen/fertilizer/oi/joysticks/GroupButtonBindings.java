package com.evergreen.fertilizer.oi.joysticks;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;


/**
 * A utility class, allowing binding multiple buttons to the same command simultaneously.
 * 
 * @author Atai Ambus
 */
public class GroupButtonBindings {

    /**
     * Makes input buttons to run the input command's {@link Command#start()} constantly, as long
     * as they are held. 
     * @param command - The command to be run while the buttons are held.
     * @param buttons - The buttons to wo hold to run the command.
     */
    public static void whileHeld(Command command, Button...buttons)
    {
        for (Button button : buttons) {
            button.whileHeld(command);
        }
    }
    
    /**
     * Make input buttons run the input command's {@link Command#start()} once, when they they are released.
     * @param command - The command to be run when the buttons are released.
     * @param buttons - The buttons to release to run the command
     */
    public static void whenReleased(Command command, Button... buttons)
    {
        for (Button button : buttons) {
            button.whenReleased(command);
        }
    }

    /**
     * Make input buttons run the input command's {@link Command#start()} once, when they they are first
     * pressed.
     * @param command - The command to be run when the buttons are pressed.
     * @param buttons - The buttons to press to run the comand
     */
    public static void whenPressed(Command command, Button... buttons)
    {
        for (Button button : buttons) {
            button.whenPressed(command);
        }
    }
}
