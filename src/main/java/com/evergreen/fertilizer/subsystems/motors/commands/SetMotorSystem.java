package com.evergreen.fertilizer.subsystems.motors.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.evergreen.fertilizer.subsystems.EvergreenCommand;
import com.evergreen.fertilizer.subsystems.EvergreenSubsystem;
import com.evergreen.fertilizer.subsystems.motors.subsystems.MotorController;
import com.evergreen.fertilizer.subsystems.motors.subsystems.MotorSubsystem;
import com.evergreen.fertilizer.utils.constraints.Constraint;
import com.evergreen.fertilizer.utils.constraints.Free;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableBoolean;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableData;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableDouble;
import com.evergreen.fertilizer.shuffleboard.loggables.LoggableString;

/**A {@link Command} which moves a {@link MotorSubsystem} according to a given speed map.
 * 
 * @author Atai Ambus
*/
public class SetMotorSystem extends EvergreenCommand {

  /**The command's subsystem to be moved. */
  protected MotorSubsystem m_subsystem;

  /**The {@link Map} matching the index of the */
  protected Map<Integer, Supplier<Double>> m_speedMap;

  protected Supplier<Double> m_speedModifier;
  
  protected Constraint m_limit;

  protected static double s_defaultModifier = 0.5;
  
  private static final Constraint DEFAULT_RANGE = new Free();
  private static final Supplier<Double> DEFAULT_MODIFIER = () -> 1.0;

  
  
  /**
   * Constructs a new {@link SetMotorSystem} with ranged movement accoeding to input parameters, not
   * logging it on the shuffleboard. 
   * 
   * @param name - The command's name (corresponding to its {@link #getName()} method, shuffleboard switch
   * and Loggable values.
   * 
   * @param subsystem - The subsystem to move.
   * 
   * @param limit - The range for this movement - when it is out of the range, the command will end.
   * This limit is tested against the subsystem's {@link SubsystemEG#getPosition() getDistance()} method. 
   * This limit is tested against the subsystem's {@link EvergreenSubsystem#getPosition() getDistance()} method. 
   * 
   * @param speedModifier - A modifier for the speeds given. Espically usefull when the main suppliers come,
   * for example, from joystick.
   * 
   * @param speedMap - A {@link Map} which {@link MotorController} indexes on the subsystem to speed
   * suppliers to set.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Constraint limit, Supplier<Double> speedModifier,
    Map<Integer, Supplier<Double>> speedMap) {
      super(name, subsystem);
      
      m_subsystem = subsystem;
      m_speedMap = speedMap;
      m_speedModifier = speedModifier;
      m_limit = limit;
  }

    /**
   * Constructs a new limitless {@link SetMotorSystem} according to input parameters, not logging it
   * on the shuffleboard
   * 
   * @param name - The command's name (corresponding to its {@link #getName()} method, shuffleboard switch
   * and Loggable values.
   * 
   * @param subsystem - The subsystem to move.
   * 
   * @param limit - The range for this movement - when it is out of the range, the command will end.
   * This limit is tested against the subsystem's {@link SubsystemEG#getPosition() getDistance()} method. 
   * This limit is tested against the subsystem's {@link EvergreenSubsystem#getPosition() getDistance()} method. 
   * 
   * @param speedModifier - A modifier for the speeds given. Espically usefull when the main suppliers come,
   * for example, from joystick.
   * 
   * @param speedMap - A {@link Map} which {@link MotorController} indexes on the subsystem to speed
   * suppliers to set.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Supplier<Double> speedModifier,
   Map<Integer, Supplier<Double>> speedMap) {
     this(name, subsystem, DEFAULT_RANGE, speedModifier, speedMap);
  }
  
  /**
   * Constructs a new unmodified {@link SetMotorSystem} with ranged movement according to input
   * parameters, not logging it on the shuffleboard. 
   * 
   * @param name - The command's name (corresponding to its {@link #getName()} method, shuffleboard switch
   * and Loggable values.
   * 
   * @param subsystem - The subsystem to move.
   * 
   * @param limit - The range for this movement - when it is out of the range, the command will end.
   * This limit is tested against the subsystem's {@link EvergreenSubsystem#getPosition() getDistance()} method. 
   * @param speedMap - A {@link Map} which {@link MotorController} indexes on the subsystem to speed
   * suppliers to set.
   *  
   * @param log - Wether to log this commandn the shuffleboard or not.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Constraint limit, Map<Integer, Supplier<Double>> speedMap) {
    this(name, subsystem, limit, DEFAULT_MODIFIER, speedMap);
  }

  /**
   * Constructs a new limitless unmodified {@link SetMotorSystem} according to input parameters, 
   * not logging it to the shuffleboard
   * 
   * @param name - The command's name (corresponding to its {@link #getName()} method, shuffleboard switch
   * and Loggable values.
   * 
   * @param subsystem - The subsystem to move.
   * 
   * @param speedMap - A {@link Map} which {@link MotorController} indexes on the subsystem to speed
   * suppliers to set.
   */
  public SetMotorSystem(String name, MotorSubsystem subsystem, Map<Integer, Supplier<Double>> speedMap) {
    this(name, subsystem, DEFAULT_RANGE, DEFAULT_MODIFIER, speedMap);
  }


  
/**As the command starts: stop the subsystem, making sure motors 
   * that are not explicitly moved by the command won't move.  */
  @Override
  public void initialize() {
    m_subsystem.stop();
  }

  /**Repeatedly as it runs: set the subsystem's motors according to the suppliers */
  @Override
  public void execute() {
    m_speedMap.forEach( (index, speed) -> m_subsystem.set(index, speed.get() * m_speedModifier.get()) );
  }

  /**Finish if the subsystem goes out of its permitted range, or if the command times out. */
  @Override
  public boolean isFinished()  {
    return !m_limit.allowed(m_subsystem.getPosition()) || !m_subsystem.canMove();
  }

  
  /**As the command ends, stop the subsystem */
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }


  public static void setDefaultModifier(double modifier) {
      s_defaultModifier = modifier;
    }

  @Override
  public List<LoggableData> getLoggableData() {
    List<LoggableData> loggables = super.getLoggableData();

    loggables.addAll(List.of(new LoggableData[] 
    {
      new LoggableString(getName() + " - Subsystem", m_subsystem::getName),
      new LoggableBoolean(getName() + " - In Range", () -> m_limit.allowed(m_subsystem.getPosition())),
      new LoggableDouble(getName() + " - Speed Modifier", m_speedModifier),
      new LoggableDouble(getName() + " - Position", () -> m_subsystem.getPosition())
    }));

    loggables.addAll(getSpeedLoggables());

    return loggables;
  }


  protected List<LoggableDouble> getSpeedLoggables() {
    List<LoggableDouble> loggables = new ArrayList<>();
  
    for (Map.Entry<Integer, Supplier<Double>> speedEntry : m_speedMap.entrySet()) {
      loggables.add(new LoggableDouble(
        getName() + "/Speed #" + speedEntry.getKey(), speedEntry.getValue())); 
    }

    return loggables;
  }

}
