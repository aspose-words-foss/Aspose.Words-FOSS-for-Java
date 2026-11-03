// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.Text.RegularExpressions.GroupCollection;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.words.ref.Ref;
import org.testng.SkipException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// <summary>
/// Code that is platform specific and used for tests only is located here.
///
/// Note: Please do not make this class dependent on TestEnvironment.
/// This class is supposed to be implemented manually for each platform so it is okay to have any platform specific paths hardcoded here.
/// Otherwise it creates a cyclical dependency during execution of static constructors between TestEnvironment and TestUtilPal that is hard to resolve.
/// </summary>
public /*static*/ class TestUtilPal
{
    /* Simulation of static class by using private constructor */
    private TestUtilPal()
    {}


    /// <summary>
    /// This throws an exception that causes the testing framework to skip/ignore the current running test.
    /// </summary>
    public static void throwIgnoreTestException(String message)
    {
        throw new SkipException("Ignored because 'noweb' test setting is active.");
    }

	//JAVA: tempFolder not used yet.
    public static int executeProcess(String exe, String cmdLine, String tempFolder, /*out*/ Ref<String> output) throws Exception
    {
        Process process;
        if (JavaUtil.isWindows())
        {
            process = Runtime.getRuntime().exec(exe + " " + cmdLine);
        }
        else
        {
            List<String> cmd = new ArrayList<String>();
            // At the moment anything that we attempt to execute on Linux will execute via Mono.
            cmd.add("mono");
            cmd.add(TestFxUtil.correctPath(exe));
            // Only argument strings with slashes will be processed correctly at the moment.
            List<String> args = splitArgsAtSlashes(TestFxUtil.correctPath(cmdLine));
            cmd.addAll(args);

            process = Runtime.getRuntime().exec(cmd.toArray(new String[cmd.size()]));
        }

        output.set(readStreamToEnd(process.getInputStream()));
        output.set(output.get() + readStreamToEnd(process.getErrorStream()));

        return process.waitFor();
    }

    public static int executeProcess(String exe, String cmdLine, /*out*/ Ref<String> output) throws Exception
    {
        return executeProcess(exe, cmdLine, "", /*out*/ output);
    }

    public static int executeProcess(String exe, String cmdLine) throws Exception
    {
        Ref<String> refToOutput = new Ref<String>(null);
        int result = executeProcess(exe, cmdLine, refToOutput);

        // Java-added: .Net doesn't use returned error string. Let's just print it for Java.
        // -- it can help to fix TestUI.exe or other tricky bugs.
        String errror = refToOutput.get();
        if (errror != null)
            System.err.println(errror);

        return result;
    }

    /**
     * <p>Splits the given string into arguments at "/" slashes only, remove quotes to avoid problems with whitespaces and quotes</p>
     *
     * @param args
     *          a string formatted like this:
     *          /task:CompareImage /out:"/path/to/gold 0 file.png" /gold:"/path/to/gold 0 file.png" /src:"" /message:"msg."
     *
     * @return a list of the command line's arguments
     *          e.g. [/task:CompareImage, /out:/path/to/gold 0 file.png etc] to be passed into {@link Runtime#exec(String[] args)}
     *
     * */
    public static List<String> splitArgsAtSlashes(String args)
    {
        String regArgs = "([/])(\\w*)[:](\"((.*?)(?<!\\\\)\")|([\\w]*))";
        Pattern argsPattern = Pattern.compile(regArgs);
        Matcher argsMatcher = argsPattern.matcher(args);

        List<String> argsArr = new ArrayList<String>();
        while (argsMatcher.find())
            argsArr.add(argsMatcher.group().replaceAll("\"",""));

        return argsArr;
    }

    static String readStreamToEnd(InputStream stream) throws Exception
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        try
        {
            StringBuffer stringBuilder = new StringBuffer();
            String line;
            while ((line = input.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append("\r\n"); // This is what happens on .NET and we need to do the same here.
            }

            return stringBuilder.toString();
        }
        finally
        {
            input.close();
        }
    }

    public static boolean isScrollLockOn()
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            return toolkit.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
        }
        catch (Throwable e)
        {
            // java.awt on Ubuntu throws
            // java.lang.UnsupportedOperationException: Toolkit.getLockingKeyState
	        // at java.awt.Toolkit.getLockingKeyState(Toolkit.java:1399)
            // because  the host system doesn't allow getting the state of this key programmatically
            return false;
        }
    }

    /// <summary>
    /// On Java this method replaces TestGold\ in the path with TestGoldJava\ if the specified file in TestGoldJava\
    /// exists, otherwise just returns the name without changes.
    /// On .NET this method just returns the given parameter without changes.
    /// </summary>
    public static String getMostActualExistingGold(String dotNetGold)
    {
        String javaGold = getMostActualGold(dotNetGold);
        return File.exists(javaGold) ? javaGold : dotNetGold;
    }

    /// <summary>
    /// On Java this method replaces TestGold\ in the path with TestGoldJava\.
    /// On .NET this method just returns the given parameter without changes.
    /// </summary>
    public static String getMostActualGold(String dotNetGold)
    {
		//JAVA-changed the whole method body: 
		// return dotNetGold;
        String goldDirName = TestEnvironment.getGoldDirName();
        if (PlatformUtilPal.isLinux())
            return dotNetGold.replace(TestEnvironment.getDirAwnet(), TestEnvironment.getDirWordsJava())
                             .replace("TestGold", goldDirName);
        else
            return dotNetGold.replace(TestEnvironment.getDirAwnet() , TestEnvironment.getDirWordsJava())
                             .replace("TestGold\\", goldDirName + "\\");
    }

    public static String correctGoldFileNameIfNeeded(String sourceFileName)
    {
        return sourceFileName;
    }
    
    public static String correctOutFileNameIfNeeded(String sourceFileName)
    {
        return sourceFileName;
    }
    
    /// <summary>
    /// Gets an array of int constant values defined in a given class.
    /// </summary>
    public static int[] getIntConstantValues(Class type) throws Exception
    {
        Field[] fields = type.getDeclaredFields();
        int[] intValues = new int[fields.length];
        int count = 0;

        for (int attrIdx = 0; attrIdx < fields.length; ++attrIdx)
        {
            Field info = fields[attrIdx];
            if (info.getType() == int.class)
            {
                // Idea from http://www.java2s.com/Code/Java/Reflection/Setprivatefieldvalue.htm
                info.setAccessible(true);
                intValues[count] = (Integer)info.get(null);
                count++;
            }
        }

        int[] justIntValues = new int[count];
        System.arraycopy(intValues, 0, justIntValues, 0, count);

        return justIntValues;
    }

	/// <summary>
	/// Gets all enum values defined in a given type as an array of integer constants.
	/// </summary>
	public static int[] getEnumValues(Class type) throws Exception
	{
		Field[] fields = type.getFields();
        // The last field is java-added enum length field.
        int length = fields.length - 1;
        int[] intValues = new int[length];

		for (int i = 0; i < length; i++)
			intValues[i] = fields[i].getInt(type);

		return intValues;
	}

    public static String getComputerName()
    {
        return System.getenv("COMPUTERNAME");
    }

    /// <summary>
    /// Returns "awnet" on .NET and "words-java" for Java.
    /// </summary>
    public static String getProjectName()
    {
        return "words-java";
    }

    /// <summary>
    /// Returns "words-net" on .NET and "words-java" for Java.
    /// </summary>
    public static String getAlternativeProjectName()
    {
        return "words-java";
    }

    public static String getExecutingAssemblyPath()
    {
        return JavaUtil.getJarFolder(SystemPal.class);
    }

    /// <summary>
    /// Returns true if the OS is 64 bit. At the moment this is only needed for Windows to determine which XPS validator exe to launch.
    /// </summary>
    public static boolean is64BitWindows()
    {
        return (System.getenv("ProgramFiles(x86)") != null);
    }

    /// <summary>
    /// Returns the current desktop resolution.
    /// 
    /// All tests are really expected to be independent of the desktop resolution,
    /// but there is a couple that is quite hard to make independent. So this method
    /// is used in those tests.
    /// </summary>
    public static boolean isNonStandardDesktopResolution()
    {
        // See http://stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        return dpi != ImageConstants.STANDARD_RESOLUTION;
    }

    /// <summary>
    /// If IsNonStandardDesktopResolution() returns true - we can use this method
    /// to get the scale of current non standard desktop resolution.
    /// </summary>
    public static float getNonStandardDesktopResolutionScale()
    {
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();

        if (dpi == ImageConstants.STANDARD_RESOLUTION)
            return 1;

        return dpi / ImageConstants.STANDARD_RESOLUTION;
    }

    //JAVA-deleted show-TestSettings-Form-If-Needed functionality - always false.
    /// <summary>
    /// Shows the settings UI if Alt+Shift is depressed.
    /// This lets the user to change (and save) the test settings before running the tests.
    /// </summary>
    public static void showTestSettingsFormIfNeeded() throws Exception
    {
        return;
    }
}
