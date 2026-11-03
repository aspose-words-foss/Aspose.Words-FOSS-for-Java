// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/02/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.Environment;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Threading.CurrentThread;


public /*static*/ class TestEnvironment
{
	/* Simulation of static class by using private constructor */
	private TestEnvironment()
	{}


    // Paths
    public static String getTools(String path) { return normalizePath(G_DIR_TOOLS + path); }
    /// <summary>
    /// This is the root (for example X:) without the slash separator at the end.
    /// </summary>
    public static String getRawRoot() { return G_ROOT_NO_SLASH; }
    public static String getDirWordsJava() { return G_DIR_WORDS_JAVA; }
    public static String getJavaProjectName() { return G_JAVA_PROJECT; }
    public static String getDirAwnet() { return G_DIR_AWNET; }
    public static String getDirAwnet(String path) { return normalizePath(G_DIR_AWNET + path); }
    public static String getDirCsporter() { return G_DIR_CSPORTER; }
    public static String getDirAwcpp() { return G_DIR_AWCPP; }
    public static String getFoundationTestData() { return G_DIR_TEST_DATA_FOUNDATION; }
    public static String getFoundationTestOut() { return G_DIR_TEST_OUT_FOUNDATION; }
    public static String getTestGold() { return G_DIR_TEST_GOLD; }
    public static String getTestGoldLocal() { return G_DIR_TEST_GOLD_LOCAL; }
    public static String getGoldDirName() { return G_GOLD_DIR_NAME; }
    public static String getTestData() { return G_DIR_TEST_DATA; }
    public static String getTestOut() { return G_DIR_TEST_OUT; }
    public static String getTestData(String relativePath) { return normalizePath(G_DIR_TEST_DATA + relativePath); }
    public static String getFoundationTestData(String relativePath) { return normalizePath(G_DIR_TEST_DATA_FOUNDATION + relativePath); }
    public static String getDirTools() { return G_DIR_TOOLS; }
    public static String getWinFontsFolder() { return G_WIN_FONTS_FOLDER; }

    // Env
    public static String getUserHome() { return G_USER_HOME; }
    public static String getUserHome(String path) { return normalizePath(G_USER_HOME + path); }
    public static String getLocalAppDataTmp() { return G_LOCAL_APP_DATA_TMP; }

    public static String getRemoteComparerIP() { return G_REMOTE_COMPARER_IP; }
    public static String getRemoteComparerPort() { return G_REMOTE_COMPARER_PORT; }
    public static String getSharedOutRoot() { return G_SHARED_OUT_ROOT; }

    // Consts
    private static /*final*/ String G_ROOT;
    private static /*final*/ String G_ROOT_NO_SLASH;
    private static /*final*/ String G_DIR_WORDS_JAVA;
    private static /*final*/ String G_JAVA_PROJECT;
    private static /*final*/ String G_DIR_AWNET;
    private static /*final*/ String G_DIR_CSPORTER;
    private static /*final*/ String G_DIR_AWCPP;
    private static /*final*/ String G_DIR_TEST_DATA;
    private static /*final*/ String G_DIR_TEST_GOLD;
    private static /*final*/ String G_DIR_TEST_GOLD_LOCAL;
    private static /*final*/ String G_GOLD_DIR_NAME;
    private static /*final*/ String G_DIR_TEST_OUT;
    private static /*final*/ String G_DIR_TEST_DATA_FOUNDATION;
    private static /*final*/ String G_DIR_TEST_OUT_FOUNDATION;
    private static /*final*/ String G_DIR_TOOLS;
    private static /*final*/ String G_REMOTE_COMPARER_IP;
    private static /*final*/ String G_REMOTE_COMPARER_PORT;
    private static /*final*/ String G_SHARED_OUT_ROOT;

    private static /*final*/ String G_USER_HOME;
    private static /*final*/ boolean G_IS_X_64;

    private static /*final*/ String G_LOCAL_APP_DATA_TMP;
    private static /*final*/ String G_LOCAL_APP_DATA;
    private static final String G_WINDOWS_PATH_SEPARATOR = "\\";
    private static final String G_UNIX_PATH_SEPARATOR = "/";
    private static final String G_BR = "\n";

    private static /*final*/ String G_WIN_FONTS_FOLDER;

    /// <summary>
    /// Returns the directory that is the parent of the project directory.
    /// Basically, it returns "X:\" for "X:\awnet\Aspose.Words" on Windows and
    /// "/media/sf_F_DRIVE/" for "/media/sf_F_DRIVE/awnet/Aspose.Words" on Linux.
    /// </summary>
    private static String getProjectParentPath()
    {
        String assemblyPath = TestUtilPal.getExecutingAssemblyPath();
        // AS: Modified this a bit so test classes in Foundation can be used in Express as well.
        // Find the first instance of "aw" or "words" in the path instead.
        int projectNamePos = msString.indexOf(assemblyPath, TestUtilPal.getProjectName().substring((0), (0) + (2)), StringComparison.ORDINAL);
        if (projectNamePos < 0)
        {
            projectNamePos = msString.indexOf(assemblyPath, TestUtilPal.getAlternativeProjectName().substring((0), (0) + (5)), StringComparison.ORDINAL);
        }

        if (projectNamePos < 0)
            return null;

        return assemblyPath.substring((0), (0) + (projectNamePos));
    }

    public static String normalizePath(String path)
    {
        if (!msString.isNullOrEmpty(path))
        {
            String sep = PlatformUtilPal.isUnixLike() ? G_UNIX_PATH_SEPARATOR : G_WINDOWS_PATH_SEPARATOR;
            path = PlatformUtilPal.isUnixLike() ?
                    path.replace(G_WINDOWS_PATH_SEPARATOR, G_UNIX_PATH_SEPARATOR)
                    : path.replace(G_UNIX_PATH_SEPARATOR, G_WINDOWS_PATH_SEPARATOR);

            if (Directory.exists(path) && !path.endsWith(sep))
                path = msString.plusEqOperator(path, sep);
        }
        return path;
    }

    private static String getEnv(String envKey, String defaultValue)
    {
        String value = System.getenv(envKey);
        return normalizePath(getDefaultIfEmpty(value, defaultValue));
    }

    private static String getProp(String propName)
    {
        return getProp(propName, null);
    }

    private static String getProp(String propName, String defaultValue)
    {
        String value = System.getProperty(propName);
        if (!msString.isNullOrEmpty(value))
            return normalizePath(getDefaultIfEmpty(value, defaultValue));

        return getEnv(propName, defaultValue);
    }

    private static String getDefaultIfEmpty(String value, String defaultValue)
    {
        return (value != null) ? value : defaultValue;
    }

    private static String removeLastPathSeparator(String path)
    {
        StringBuilder str = new StringBuilder(path);
        for (int i = str.length() - 1; i >= 0; i--)
        {
            if (str.charAt(i) == '/' || str.charAt(i) == '\\')
                str.delete((i), (i) + (1));
            else
                break;
        }
        return str.toString();
    }

    private static String getDirectoryName(String path) throws Exception
    {
        path = addPathSeparatorAtEnd(path);
        return Path.getFileNameWithoutExtension(Path.getDirectoryName(path));
    }

    private static String addPathSeparatorAtEnd(String path)
    {
        char lastChar = path.charAt(path.length() - 1);
        if (lastChar != '/' && lastChar != '\\')
            return path + Path.DirectorySeparatorChar;

        return path;
    }

    public static String toStringData()
    {
        StringBuilder str = new StringBuilder();
        msStringBuilder.append(msStringBuilder.append(str, "gRoot=" + G_ROOT), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gRawRoot=" + G_ROOT_NO_SLASH), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirWordsJava=" + G_DIR_WORDS_JAVA), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirAwnet=" + G_DIR_AWNET), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirAwcpp=" + G_DIR_AWCPP), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirCsporter=" + G_DIR_CSPORTER), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestData=" + G_DIR_TEST_DATA), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestGold=" + G_DIR_TEST_GOLD), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestGoldLocal=" + G_DIR_TEST_GOLD_LOCAL), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gGoldDirName=" + G_GOLD_DIR_NAME), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestOut=" + G_DIR_TEST_OUT), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestDataFoundation=" + G_DIR_TEST_DATA_FOUNDATION), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gDirTestOutFoundation=" + G_DIR_TEST_OUT_FOUNDATION), G_BR);

        msStringBuilder.append(msStringBuilder.append(str, "gDirTools=" + G_DIR_TOOLS), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gUserHome=" + G_USER_HOME), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gLocalAppData=" + G_LOCAL_APP_DATA), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gLocalAppDataTmp=" + G_LOCAL_APP_DATA_TMP), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "gIsX64=" + Boolean.toString(G_IS_X_64)), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "Locale=" + CurrentThread.getCurrentCulture().toString()), G_BR);
        msStringBuilder.append(msStringBuilder.append(str, "TZ=" + CurrentThread.getTimeZone()), G_BR);
        return str.toString();
    }

    static/* TestEnvironment()*/
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	G_ROOT = getProp("dir.root", getProjectParentPath());

        	// Originally, the exception was thrown inside the GetProjectParentPath method in case if
        	// the default AW project root path is not found. So for non-AW projects using this class, 
        	// the exception was always thrown and the GetEnv method was not even invoked. But let's 
        	// give GetEnv a chance to override the default AW project path, even if the default path 
        	// can not be extracted from the executing assembly path.
        	if (msString.isNullOrEmpty(G_ROOT))
            	throw new IllegalStateException("Cannot initialize project parent path.");

        	G_ROOT_NO_SLASH = removeLastPathSeparator(G_ROOT);

        	G_DIR_WORDS_JAVA = getProp("dir.words-java", G_ROOT + "words-java\\");
        	G_JAVA_PROJECT = getDirectoryName(G_DIR_WORDS_JAVA);


        	G_DIR_AWNET = getProp("dir.awnet", G_ROOT + "awnet\\");
        	G_DIR_CSPORTER = getProp("dir.csporter", G_ROOT + "csporter\\");

        	G_DIR_AWCPP = getProp("dir.awcpp", G_ROOT + "words-cpp\\");

        	G_DIR_TEST_DATA = getProp("dir.test.data", G_DIR_AWNET + "TestData\\");
        	G_DIR_TEST_GOLD = getProp("dir.test.gold", G_DIR_AWNET + "TestGold\\");
        	G_DIR_TEST_GOLD_LOCAL = getProp("dir.test.gold.local", G_ROOT + "TestGoldLocal\\");
        	G_DIR_TEST_OUT = getProp("dir.test.out", G_DIR_AWNET + "TestOut\\");

        	G_GOLD_DIR_NAME = getProp("dir.test.gold.name", "TestGoldJava");

        	G_DIR_TEST_DATA_FOUNDATION = getProp("dir.test.data.foundation", G_DIR_AWNET + "Aspose.Foundation\\TestData\\");
        	G_DIR_TEST_OUT_FOUNDATION = getProp("dir.test.out.foundation", G_DIR_AWNET + "Aspose.Foundation\\TestOut\\");

        	G_DIR_TOOLS = getProp("dir.tools", G_DIR_AWNET + "Aspose.Foundation\\Tools\\");
        	if (PlatformUtilPal.isUnixLike())
        	{
            	G_USER_HOME = getProp("HOME", "");
            	if (!G_USER_HOME.endsWith("/"))
            	{
                	G_USER_HOME = msString.plusEqOperator(G_USER_HOME, '/');
            	}
        	}
        	else
        	{
            	G_USER_HOME = "C:\\Users\\user\\";
        	}
        	G_LOCAL_APP_DATA = getEnv("LocalAppData", G_USER_HOME);
        	G_LOCAL_APP_DATA_TMP = (G_LOCAL_APP_DATA != null) ? Path.combine(G_LOCAL_APP_DATA, "temp") : "";
        	G_IS_X_64 = TestUtilPal.is64BitWindows();

        	if (PlatformUtilPal.isWindows())
        	{
            	G_WIN_FONTS_FOLDER = SystemPal.getWindowsFontsFolder();
        	}
        	else if (PlatformUtilPal.isMacOS())
        	{
            	G_WIN_FONTS_FOLDER = Path.combine(G_DIR_AWCPP, "fonts");
        	}
        	else if (PlatformUtilPal.isUnixLike())
        	{
            	G_WIN_FONTS_FOLDER = G_USER_HOME + ".fonts";
        	}

        	String ip =
            	"192.168.0.105";
        	G_REMOTE_COMPARER_IP = getProp("remote_comparer_ip", ip);

        	G_REMOTE_COMPARER_PORT = getProp("remote_comparer_port", "8092");
        	G_SHARED_OUT_ROOT = getProp("shared_out_root");
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    public static void main(String[] args) throws Exception { System.out.println(TestEnvironment.toStringData()); }
}

