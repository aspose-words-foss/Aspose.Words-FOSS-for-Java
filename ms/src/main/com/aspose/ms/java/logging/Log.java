package com.aspose.ms.java.logging;

import com.aspose.ms.java.debug.JavaDebug;

import java.util.logging.*;

/**
 * This class is a facade to the java.util.logging facilities.
 * At the moment it prints the messages to the standard output only.
 */
public class Log
{
    private Log()
    {
    }

    private static void configure()
    {
        // SEVERE means - serious errors that even users should be aware of.
        // WARNING means - debug warnings, mostly stacks of suppressed exceptions.
        //     Early we used for this 'if (JavaDebug.ON) { e.printStackTrace(); }'.
        // INFO means - print some useful for debugging hints.

        // Set up the level. INFO for development, SEVERE for production.
        // So users will see errors only.
        Level loggerLevel = JavaDebug.ON ? Level.INFO : Level.SEVERE;
        // all records less and equal to Level.WARNING (e.g. info, fine etc) will be printed to System.out, the rest to System.err
        EnhancedConsoleHandler recordHandler = new EnhancedConsoleHandler(new AsposeLogFormatter(), Level.WARNING);
        configureLogger(LOGGER, recordHandler, loggerLevel);
    }

    private static void configureLogger(Logger logger, Handler handler, Level level)
    {
        // overwrite the console handler's functionality.
        Handler[] oldHandlers = logger.getHandlers();
        if (oldHandlers != null)
            for (Handler h : oldHandlers)
                logger.removeHandler(h);

        logger.setUseParentHandlers(false);

        // set up our own formatter
        logger.addHandler(handler);
        logger.setLevel(level);
    }

    /**
     * INFO level - print some useful debugging hints.
     */
    public static void info(String... messages)
    {
        if (messages != null)
            for (String s : messages)
                LOGGER.info(s);
    }

    /**
     * WARNING level - debug warnings, mostly stacks of suppressed exceptions.
     * Early we used for this 'if (JavaDebug.ON) { e.printStackTrace(); }'.
     */
    public static void warn(String... messages)
    {
        if (messages != null)
            for (String s : messages)
                LOGGER.warning(s);
    }

    /**
     * WARNING level - debug warnings, mostly stacks of suppressed exceptions.
     * Early we used for this 'if (JavaDebug.ON) { e.printStackTrace(); }'.
     */
    public static void warn(String message, Throwable thrown)
    {
        LOGGER.log(Level.WARNING, message, thrown);
    }

    /**
     * WARNING level - debug warnings, mostly stacks of suppressed exceptions.
     * Early we used for this 'if (JavaDebug.ON) { e.printStackTrace(); }'.
     */
    public static void warn(Throwable thrown)
    {
        LOGGER.log(Level.WARNING, "", thrown);
    }

    /**
     * SEVERE level - serious errors that even users should be aware of.
     */
    public static void error(String... messages)
    {
        if (messages != null)
            for (String s : messages)
                LOGGER.severe(s);
    }

    /**
     * SEVERE level - serious errors that even users should be aware of.
     */
    public static void error(Throwable thrown)
    {
        LOGGER.log(Level.SEVERE, "", thrown);
    }

    /**
     * SEVERE level - serious errors that even users should be aware of.
     */
    public static void error(String message, Throwable thrown)
    {
        LOGGER.log(Level.SEVERE, message, thrown);
    }

    private static class AsposeLogFormatter extends Formatter
    {
        @Override
        public String format(LogRecord record)
        {
            String thrown = record.getThrown() == null
                    ? EMPTY
                    : String.format(EXCEPTION_FORMAT,
                    record.getThrown().getClass(),
                    record.getThrown().getMessage(),
                    toString(record.getThrown().getStackTrace())
            );

            return String.format(MAIN_LOG_RECORD_LINE,
                    record.getMillis(),
                    MAIN_PACKAGE_TAG,
                    record.getMessage(),
                    thrown
            );
        }

        private String toString(StackTraceElement[] stackTrace)
        {
            if (stackTrace == null || stackTrace.length == 0)
                return EMPTY;

            StringBuilder s = new StringBuilder();
            for (StackTraceElement el : stackTrace)
                s.append('\t').append('\t').append(el).append('\n');

            return s.toString();
        }

        private static final String EMPTY = "";
        private static final String EXCEPTION_FORMAT = "\t%s: %s\n%s";
        private static final String MAIN_LOG_RECORD_LINE = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$tZ %2$s: %3$s\n%4$s";
    }

    private static class EnhancedConsoleHandler extends StreamHandler
    {
        private final ConsoleHandler stderrHandler;
        private final int level;

        EnhancedConsoleHandler(Formatter formatter, Level level)
        {
            super(System.out, formatter);
            stderrHandler = new ConsoleHandler();
            if (level == null)
                throw new IllegalArgumentException("Level");

            this.level = level.intValue();
        }

        @Override
        public synchronized void publish(LogRecord record)
        {
            if (record.getLevel().intValue() <= level)
            {
                super.publish(record);
                super.flush();
            }
            else
            {
                stderrHandler.publish(record);
                stderrHandler.flush();
            }
        }
    }

    private static final String MAIN_PACKAGE_TAG = "com.aspose.words";
    private static final Logger LOGGER = Logger.getLogger(MAIN_PACKAGE_TAG);

    static
    {
        configure();
    }
}
