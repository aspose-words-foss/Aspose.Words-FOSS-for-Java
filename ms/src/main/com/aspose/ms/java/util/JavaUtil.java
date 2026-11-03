//19.08.2010 by Konstantin Sidorenko

package com.aspose.ms.java.util;

import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Miscellaneous utils used by ms classes.
 */
public class JavaUtil
{
	/**
	 * Returns a class from this jar. Returns this class for simplicity.
	 *
	 * Early we used sun.reflect.Reflection.getCallerClass(2) - it was perfect but it is sun.* class
	 * that formally shouldn't be used in production.
	 *
	 * Than we used "SecurityManager method" - it was most optimal till WORDSNET-8773 issue (Java issue
     * mistakenly logged as .Net one): some of user's test and production environments are conflicting
     * with our SecurityManager instantiated for this method.
     *
     * So i decided simply return this class.
     *
     * For different options see:
	 * http://stackoverflow.com/questions/421280/in-java-how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection.
	 */
	public static Class getAClassFromJar()
	{
		return new JavaUtil().getClass();
	}

	/**
     * Returns the folder that contains the jar of the specified class.
     * I.e. from class root url resource "wsjar:file:/<path>/WEB-INF/lib/awj.jar!" the method
     * returns "/<path>/WEB-INF/lib".
     *
     * Works both for jarred and not jarred classes. For jarred classes strips both jar scheme prefix and
     * jar file itself.
     */
    public static String getJarFolder(Class clazz)
    {
        String classRootPath = getClassRootURL(clazz);

        // Strips protocol ("jar:file", "wsjar:file:" or "code-source") from the beginning of the string.
        // On Windows it will look like "jar:file:/X:/awnet/..." and we need to strip the first slash too.
        // On Linux it will look like "jar:file:/myfolder/awnet/.,." and we must preserve the first slash.
        int startPos = classRootPath.indexOf('/');
        if (isWindows())
            startPos++;

        // Remove the name of the jar file from the end of the path for jars.
        // Recognized jar schemes:
        // - "jar:file:" - common jar.
        // - "wsjar:file:" - IBM WebSphere jar.
        // - "code-source:" - Oracle OC4J jar.
        int endPos = classRootPath.length();
        if (classRootPath.startsWith("jar:") || classRootPath.startsWith("wsjar:") || classRootPath.startsWith("code-source:"))
            endPos = classRootPath.lastIndexOf('/');

        return classRootPath.substring(startPos, endPos);
    }

    /**
     * If the package is jarred, returns "jar:file:/Path/JarFileName!" (used in production).
     * If the package is not jarred, returns "file:/Path/classes" (used in unit tests).
     * And "code-source:/Path/JarFileName" (w/o end '!') for Oracle OC4J.
     *
     * @return the folder or the jar file from where given class loaded.
     */
    private static String getClassRootURL(Class clazz)
    {
        String classFileShortPath = "/" + msString.replace(clazz.getName(), '.', '/') + ".class";
        String classFileFullPath = clazz.getResource(classFileShortPath).toString();
        // Obfuscated classes can contain non-ascii symbols in name that will be encoded by
        // clazz.getResource() (returns URL) - decode them back.
        try
        {
            classFileFullPath = URLDecoder.decode(classFileFullPath, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            // wrap the exception to Runtime one to delete throws clause.
            throw new RuntimeException(e);
        }
        int classRootIndex = classFileFullPath.length() - classFileShortPath.length();
        return classFileFullPath.substring(0, classRootIndex);
    }

    /// <summary>
    /// Returns true if we are running on Windows.
    /// </summary>
    public static boolean isWindows()
    {
        // Useful list: http://lopica.sourceforge.net/os.html
        return (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0);
    }

	/**
	 * Returns true if we are running on java 1.4.
	 */
	public static boolean isJdk14()
	{
		return getJdkVersion().startsWith("1.4");
	}

    /**
     * Returns string representation of JDK version
     */
    public static String getJdkVersion()
    {
        return System.getProperty("java.version").toUpperCase();
    }

    /**
     * Returns true if JDK version is older than given
     */
    public static boolean isJdkOlderThan(String version)
    {
        String actualVersion = getJdkVersion();

        int sampleBeginIndex = 0;
        int actualBeginIndex = 0;
        while (true)
        {
            if (sampleBeginIndex >= version.length() || actualBeginIndex >= actualVersion.length())
                return false;

            // Split version into segments and compare them respectively

            int sampleEndIndex = version.indexOf('.', sampleBeginIndex);
            if (sampleEndIndex < 0)
            {
                sampleEndIndex = version.indexOf('_', sampleBeginIndex);
                if (sampleEndIndex < 0)
                    sampleEndIndex = version.length();
            }

            int actualEndIndex = actualVersion.indexOf('.', actualBeginIndex);
            if (actualEndIndex < 0)
            {
                actualEndIndex = actualVersion.indexOf('_', actualBeginIndex);
                if (actualEndIndex < 0)
                    actualEndIndex = actualVersion.length();
            }

            String sample = version.substring(sampleBeginIndex, sampleEndIndex);
            sampleBeginIndex = sampleEndIndex + 1;

            String actual = actualVersion.substring(actualBeginIndex, actualEndIndex);
            actualBeginIndex = actualEndIndex + 1;

            boolean  isActualNumber = false;
            Integer actualNumber = null;
            try
            {
                actualNumber = Integer.parseInt(actual);
                isActualNumber = true;
            } catch (NumberFormatException ex) {
                // Ignore
            }

            boolean isSampleNumber = false;
            Integer sampleNumber = null;
            try
            {
                sampleNumber = Integer.parseInt(sample);
                isSampleNumber = true;
            } catch (NumberFormatException ex) {
                // Ignore
            }

            if (isActualNumber && isSampleNumber)
            {
                if (actualNumber < sampleNumber)
                    return true;
            }
            else
            {
                if (actual.compareTo(sample) < 0)
                    return true;
            }
        }
    }

    // Indicates whether a specified string is empty or consists only of white-space characters.
    public static boolean isWhiteSpace(String text)
    {
        if (text == null)
            return false;

        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (!msChar.isWhiteSpace(c))
                return false;
        }
        return true;
    }

    // Indicates that the current text value was obtained using StringUtil.getNewLine().
    public static boolean isNewLine(String text)
    {
        if(text == null)
            return false;

        // Unix
        if (text.length() == 1)
            return text.charAt(0) == '\n';

        // Windows
        if(text.length() == 2)
            return text.charAt(0) == '\r' && text.charAt(1) == '\n';

        return false;
    }

    // *** Copied from MiscUtil.
    /// <summary>
    /// Returns true if the string is not null and not empty.
    /// We cannot use String.IsNullOrEmpty because it was introduced in .NET 2.0.
    /// </summary>
    public static boolean hasChars(String value)
    {
        return (value != null) && (value.length() > 0);
    }

    /// *** copied from UriUtil
	/// <summary>
	/// Returns true if the URI, UNC path or file path is absolute (rooted).
	///
	/// This method is useful to call before trying to construct a .NET Uri object because
	/// the .NET Uri object will throw if the uri is relative.
	/// </summary>
	public static boolean isAbsoluteHref(String href)
	{
	    return
	        isHrefWithScheme(href) ||
	        isAbsoluteLocalFilePath(href) ||
	        isUncPath(href);
	}

    /// *** copied from UriUtil
	/// <summary>
	/// Returns true if the string seems to be a URI with a scheme.
	/// </summary>
	public static boolean isHrefWithScheme(String href)
	{
	    // Example: http://myserver/myfolder/myfile.doc returns true,
	    // but X:\test\test.txt returns false;
	    return (href.indexOf(':') > 1);
	}

    /// *** copied from UriUtil
	/// <summary>
	/// Returns true if the string seems to be an absolute local file path.
	/// </summary>
	public static boolean isAbsoluteLocalFilePath(String href)
	{
	    // Example: X:\MyFolder\MyFile.doc
	    return (href.length() > 2) && isDriveLetter(href.charAt(0)) && (href.charAt(1) == ':') && ((href.charAt(2) == '\\') || (href.charAt(2) == '/'));
	}

    /// *** copied from UriUtil
	/// <summary>
	/// Returns true if the string seems to be a UNC path.
	/// </summary>
	public static boolean isUncPath(String href)
	{
	    // Example: \\myserver\myfolder\myfile.doc
	    return href.startsWith("\\\\");
	}

    /// *** copied from UriUtil
	private static boolean isDriveLetter(char c)
	{
	    return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
	}

    /**
     * Formats the number as a hex string with a 'digits' specified length.
     *
     * Used instead of format strings like "{0:x2}" not supported by java MessageFormat.format().
     * Note: Integer.toHexString(number) returns lowercased chars.
     * Note2: Integer.toHexString() is not localized so it emulates .net CultureInfo.INVARIANT_CULTURE.
     */
    public static String toHexString(int number, int digits)
    {
        assert digits > 0;

        String str = Integer.toHexString(number);
        return zeroPadString(str, digits);
    }

    /**
     * Emulates .net ParseNumbers.IntToString(number, 10, digits, '0', 0)
	 * or intValue.ToString("d3") (where intValue < 1000).
     * - Fоr instance, intToString(3, 3) returns "003".
     */
    public static String intToString(int number, int digits)
    {
        assert digits > 0;

        //JAVA: Integer.toString() is not localized so it emulates .net CultureInfo.INVARIANT_CULTURE.
        String str = Integer.toString(number);
        return zeroPadString(str, digits);
    }

    public static String zeroPadString(String str, int digits)
    {
        int length = str.length();

        if (length == digits)
            return str;

        //zero padding missing digits
        if (length < digits)
            return msString.newString('0', digits - length) + str;

        //cut off excess digits
        return str.substring(length - digits, length);
    }

    public static String getParamName(IllegalArgumentException exeception)
    {
        String[] mesages = exeception.getMessage().split("\r\n");
        for (String message :mesages)
        {
            if (message.contains("Parameter name: "))
                return message.replace("Parameter name: ","");
        }
        return "";
    }
}
