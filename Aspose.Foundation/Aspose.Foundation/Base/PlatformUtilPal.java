// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import com.aspose.ms.System.Text.msStringBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

/// <summary>
/// Provides methods to detect the current platform, operating system etc.
/// </summary>
//[JavaManual("Platform abstraction for system utilities. Manual porting by design.")]
//[CppManual("Platform abstraction for system utilities. Manual porting by design.")]
public /*static*/ class PlatformUtilPal
{
    /* Simulation of static class by using private constructor */
	private PlatformUtilPal()
	{}

    /// <summary>
    /// Returns the operating system type in a way that is suitable for our purposes.
    /// </summary>
    public static /*Platform*/ int getPlatform()
    {
        return gPlatformID;
    }

    public static boolean isWindows()
    {
        return getPlatform() == Platform.WINDOWS;
    }

    /// <summary>
    /// Is current platform Linux or other Unix clone?
    /// </summary>
    public static boolean isLinux()
    {
        return getPlatform() == Platform.UNIX;
    }

    public static boolean isAndroid()
    {
        return getPlatform() == Platform.ANDROID;
    }

    public static boolean isMacOS()
    {
        return getPlatform() == Platform.MAC_OS;
    }


    /// <summary>
    /// Returns true for Unix-like platforms: Linux, MacOs, Android.
    /// </summary>
    public static boolean isUnixLike()
    {
        /*Platform*/int platform = getPlatform();
        return platform == Platform.UNIX || platform == Platform.MAC_OS || platform == Platform.ANDROID || platform == Platform.I_OS;
    }

    //JAVA-deleted: not used in java.
/*
    /// <summary>
    /// Shows if the operating is Windows and is equal or greater that Windows 7.
    /// </summary>
    public static boolean isOsEqualOrGreaterThenWindows7()
    {
        //Get Operating system information.
        OperatingSystem os = Environment.OSVersion;
        //Get version information about the os.
        Version vs = os.Version;

        //+---------------------------------------------------------------------------------------------------------------------------------------------------+
        //|           |   Windows    |   Windows    |   Windows    |Windows NT| Windows | Windows | Windows | Windows | Windows | Windows | Windows | Windows |
        //|           |     95       |      98      |     Me       |    4.0   |  2000   |   XP    |  2003   |  Vista  |  2008   |    7    | 2008 R2 |    8    |
        //+---------------------------------------------------------------------------------------------------------------------------------------------------+
        //|PlatformID | Win32Windows | Win32Windows | Win32Windows | Win32NT  | Win32NT | Win32NT | Win32NT | Win32NT | Win32NT | Win32NT | Win32NT | Win32NT |
        //+---------------------------------------------------------------------------------------------------------------------------------------------------+
        //|Major      |              |              |              |          |         |         |         |         |         |         |         |         |
        //| version   |      4       |      4       |      4       |    4     |    5    |    5    |    5    |    6    |    6    |    6    |    6    |    6    |
        //+---------------------------------------------------------------------------------------------------------------------------------------------------+
        //|Minor      |              |              |              |          |         |         |         |         |         |         |         |         |
        //| version   |      0       |     10       |     90       |    0     |    0    |    1    |    2    |    0    |    0    |    1    |    1    |    2    |
        //+---------------------------------------------------------------------------------------------------------------------------------------------------+

        if (os.Platform == PlatformID.Win32NT)
            if (vs.Major == 6)
                return vs.Minor > 0;
            else if (vs.Major > 6)
                return true;
        return false;
    }

    /// <summary>
    /// Shows if the operating is Windows and is greater that Windows 7.
    /// </summary>
    public static boolean isOsGreaterThenWindows7()
    {
        OperatingSystem os = Environment.OSVersion;
        Version vs = os.Version;

        if (os.Platform == PlatformID.Win32NT)
            if (vs.Major == 6)
                return vs.Minor > 1;
            else if (vs.Major > 6)
                return true;
        return false;
    }
*/

    /// <summary>
    /// Returns true, if the current runtime is Mono.
    /// </summary>
    public static boolean isRunningOnMono()
    {
        //JAVA-changed: always false.
        return false;
    }

    //JAVA-added:
    // WORDSJAVA-1646 (fixed) - System property access causes unnecessary blocking (and performance
    // degradation). Called only once to initialize static gPlatformID.
    private static /*Platform*/ int getPlatformProperty()
    {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows"))
            return Platform.WINDOWS;
        else if (os.startsWith("Mac OS"))
            return Platform.MAC_OS;
        else if (System.getProperty("java.runtime.name").startsWith("Android"))
            return Platform.ANDROID;
        else
            return Platform.UNIX;
    }

    /// <summary>
    /// Runs process and returns process output.
    /// </summary>
    public static ProcessExecutionResult runProcess( String... args)
    {
        if(getPlatform() != Platform.UNIX)
            return new ProcessExecutionResult(null, false, null, new Exception("not implement yet"));

        ProcessBuilder pb = new ProcessBuilder();

        if (args != null && args.length > 0)
            pb.command(args);

        try
        {
            Process process = pb.start();
            try
            {
                process.waitFor();
                int exitCode = process.exitValue();

                BufferedReader reader;
                StringBuilder outputSb = new StringBuilder();
                StringBuilder errorSb = new StringBuilder();
                String line;

                // Result
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = reader.readLine()) != null)
                    outputSb.append(line).append("\n");

                // Errors
                reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = reader.readLine()) != null)
                    errorSb.append(line).append("\n");

                return new ProcessExecutionResult(outputSb.toString(), exitCode == 0, errorSb.toString(), null);
            }
            finally { if (process != null) process.destroy(); }
        }
        catch (Exception anyException)
        {
            return new ProcessExecutionResult(null, false, null, anyException);
        }
    }

    // Static field initialization: no need in double check blocking etc.
    private static final /*Platform*/ int gPlatformID = getPlatformProperty();
}
