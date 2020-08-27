package com.evergreen.everlib.shuffleboard.constants.commands;

import com.evergreen.everlib.shuffleboard.constants.ConstantBoolean;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


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
