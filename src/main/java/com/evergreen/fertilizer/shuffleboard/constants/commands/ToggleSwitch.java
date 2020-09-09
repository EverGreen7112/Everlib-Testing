package com.evergreen.fertilizer.shuffleboard.constants.commands;

import com.evergreen.fertilizer.shuffleboard.constants.ConstantBoolean;


/**A command to toggle a shuffleboard {@link ConstantBoolean}.
 * @author Atai Ambus
*/
public class ToggleSwitch extends SetSwitch {
  

  /**
   * Constructs a {@link ToggleSwitch} command, which toggles an input
   * {@link ConstantBoolean} switch.
   * 
   * @param name - The name of <i>this</i> com mand, for logging purposes (and its own switch)
   * @param booleanSwitch - The switch to toggle.
   */
  public ToggleSwitch(String name, ConstantBoolean booleanSwitch) {
    super(name, booleanSwitch, () -> !booleanSwitch.get());
  }

}
