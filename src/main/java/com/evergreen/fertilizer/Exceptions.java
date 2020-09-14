package com.evergreen.fertilizer;

/**
 * 
 */
public interface Exceptions {

    /**An exception thrown when a switch method is called, but the given key does no match
     * any boolean on the shuffleboard.*/
    public class SwitchNotFoundException extends RuntimeException
    {
        private static final long serialVersionUID = -8189765457487943481L;

        public SwitchNotFoundException(String message, Throwable err)
        {
            super(message, err);
        }

        public SwitchNotFoundException(String message)
        {
            super(message);
        }
    }



    public class IlleagalSpeedInputException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public IlleagalSpeedInputException(String message)
        {
            super(message);
        }

        public IlleagalSpeedInputException(String message, Throwable error)
        {
            super(message, error);
        }

    }

    public class NegativeTargetSpeedException extends IlleagalSpeedInputException
    {

        private static final long serialVersionUID = 1L;

        public NegativeTargetSpeedException(String message)
        {
            super(message);
        }

        public NegativeTargetSpeedException(String message, Throwable error)
        {
            super(message, error);
        }

    }

    public class IlleagalAdjustersNumException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        public IlleagalAdjustersNumException(String message)
        {
            super(message);
        }

        public IlleagalAdjustersNumException(String message, Throwable error)
        {
            super(message, error);
        }

    }

    public class SensorDoesNotExistException extends NullPointerException
    {
        private static final long serialVersionUID = 1L;
    
        public SensorDoesNotExistException(String message)
        {
            super(message);
        }
    }
}
