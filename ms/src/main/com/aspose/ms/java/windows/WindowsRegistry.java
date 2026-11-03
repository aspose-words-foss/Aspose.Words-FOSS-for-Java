package com.aspose.ms.java.windows;

import com.aspose.ms.java.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads string values of the Windows registry with reg.exe, the command line tool that is part of every Windows.
 * Java has no API for the registry, and a native library of our own is not worth the few values we read.
 *
 * Starting a process takes tens of milliseconds, so callers are expected to cache what they read.
 */
public class WindowsRegistry
{
    /***
     * Reads string values from the Windows Registry Tree.
     * @param key Windows registry branch identificator: HKEY_LOCAL_MACHINE or HKEY_CURRENT_USER.
     * @param subkeys Relative subtree path. e.g. SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts
     * @return a map of the values. e.g. Key=Myanmar Text Bold (TrueType), Value=mmrtextb.ttf.
     *         Empty when this is not Windows, the key does not exist or cannot be read.
     */
    public Map<String, String> readRegistryStringValues(int key, String subkeys)
    {
        try
        {
            File regExe = findRegExe();
            if (regExe == null)
                return Collections.emptyMap();

            return parse(run(regExe.getPath(), "query", getRootName(key) + "\\" + subkeys));
        }
        catch (Throwable any)
        {
            Log.error(any);
        }

        return Collections.emptyMap();
    }

    /**
     * Takes the values of the queried key out of the output of "reg query", which looks like this:
     * <pre>
     *
     * HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Nls\Language
     *     Default    REG_SZ    0809
     *     InstallLanguage    REG_SZ    0409
     *
     * HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Nls\Language\Groups
     * </pre>
     * Value lines are indented; name, type and data are separated by four spaces, and a name may contain single spaces.
     */
    static Map<String, String> parse(String regQueryOutput)
    {
        Map<String, String> values = new LinkedHashMap<String, String>();
        for (String line : regQueryOutput.split("\r?\n"))
        {
            Matcher matcher = VALUE_LINE.matcher(line);
            if (matcher.matches())
                values.put(matcher.group(1), matcher.group(2) != null ? matcher.group(2) : "");
        }

        return values;
    }

    private static String getRootName(int key)
    {
        switch (key)
        {
            case HKEY_CURRENT_USER:
                return "HKCU";
            case HKEY_LOCAL_MACHINE:
                return "HKLM";
            default:
                throw new IllegalArgumentException("key: " + key);
        }
    }

    /**
     * reg.exe is taken from the Windows folder by its full path, never from PATH.
     */
    private static File findRegExe()
    {
        String osName = System.getProperty("os.name");
        if (osName == null || !osName.startsWith("Windows"))
            return null;

        String windowsFolder = System.getenv("SystemRoot");
        if (windowsFolder == null || windowsFolder.length() == 0)
            return null;

        File regExe = new File(windowsFolder, "System32\\reg.exe");
        return regExe.isFile() ? regExe : null;
    }

    /**
     * Runs the command and returns what it printed. The output is in the console code page of the system;
     * it is read in the default encoding, which is the same for the ASCII data this class is used for.
     */
    private static String run(String... command) throws Exception
    {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try
        {
            process.getOutputStream().close();

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try
            {
                for (String line = reader.readLine(); line != null; line = reader.readLine())
                    output.append(line).append('\n');
            }
            finally
            {
                reader.close();
            }

            // A key that does not exist is not an error for the caller: the output has no value lines then.
            process.waitFor();
            return output.toString();
        }
        finally
        {
            process.destroy();
        }
    }

    public static final int HKEY_CURRENT_USER = 0x80000001;
    public static final int HKEY_LOCAL_MACHINE = 0x80000002;

    private static final Pattern VALUE_LINE = Pattern.compile("^ {4}(\\S.*?) {4}REG_(?:EXPAND_)?SZ(?: {4}(.*))?$");
}
