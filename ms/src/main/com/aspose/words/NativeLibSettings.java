package com.aspose.words;

import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;

/**
 * Process-wide options of the library: the folder for temporary files and the reaction to image errors.
 * Aspose.Words FOSS has no native libraries; the class keeps its name for compatibility.
 */
public final class NativeLibSettings
{
    private NativeLibSettings() {}

    /**
     * Specifies the path to the directory for temporary files.
     * @param path the path to the directory for temporary files.
     * @throws IllegalArgumentException if path is invalid.
     */
    public static void setTmpDirectoryPath(String path) throws IllegalArgumentException
    {
        if(msString.isNullOrEmpty(path) || !Path.isPathRooted(path))
            throw new IllegalArgumentException("Custom path " + path + " is invalid");

        System.setProperty(CUSTOM_PROPERTY_TMPDIR, path);
    }

    /**
     * Returns the path to the directory for temporary files; the system one unless another was specified.
     */
    public static String getTmpDirectoryPath()
    {
        return msString.isNullOrEmpty(System.getProperty(CUSTOM_PROPERTY_TMPDIR)) ?
                System.getProperty(SYSTEM_PROPERTY_TMPDIR) : System.getProperty(CUSTOM_PROPERTY_TMPDIR);
    }

    /**
     * Sets the property that defines behavior when handling image exceptions.
     * If the property is set to true, the execution thread will be interrupted when an exception
     * occurs during image processing.
     * <p><strong>Remarks:</strong></p>
     * <p>The default value is <code>false</code>.</p>
     *
     * @param abortSavingIfImageExceptionThrown true - interrupt thread on image exceptions,
     *                                         false - do not interrupt
     */
    public static void setInterruptThreadIfImageExceptionThrown(boolean abortSavingIfImageExceptionThrown)
    {
        System.setProperty(CUSTOM_PROPERTY_INTERRUPT_THREAD_IF_IMAGE_EXCEPTION_THROWN, abortSavingIfImageExceptionThrown ? "true" : "false");
    }

    /**
     * Returns the current value of the property that controls thread interruption on image exceptions.
     * <p><strong>Remarks:</strong></p>
     * <p>The default value is <code>false</code>.</p>
     *
     * @return is the thread should be interrupted on image exceptions.
     */
    public static boolean getInterruptThreadIfImageExceptionThrown()
    {
        if (System.getProperty(CUSTOM_PROPERTY_INTERRUPT_THREAD_IF_IMAGE_EXCEPTION_THROWN) != null)
            return Boolean.parseBoolean(System.getProperty(CUSTOM_PROPERTY_INTERRUPT_THREAD_IF_IMAGE_EXCEPTION_THROWN));

        return false;
    }

    private static final String CUSTOM_PROPERTY_TMPDIR = "com.aspose.tmpdir";
    private static final String SYSTEM_PROPERTY_TMPDIR = "java.io.tmpdir";
    private static final String CUSTOM_PROPERTY_INTERRUPT_THREAD_IF_IMAGE_EXCEPTION_THROWN = "com.aspose.interrupt_thread_if_image_exception_thrown";
}
