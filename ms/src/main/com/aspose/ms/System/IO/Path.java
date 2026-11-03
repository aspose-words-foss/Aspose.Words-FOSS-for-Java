package com.aspose.ms.System.IO;

import com.aspose.ms.java.util.JavaUtil;
import com.aspose.words.NativeLibSettings;

import java.io.File;

/**
 * User: sk
 * Date: Jan 18, 2007
 */
public final class Path
{
	private Path() {}

	/**
	 * Changes the extension of a file path. The path parameter
	 * specifies a file path, and the extension parameter
	 * specifies a file extension (with a leading period, such as
	 * ".exe" or ".cs").
	 *
	 * The function returns a file path with the same root, directory, and base
	 * name parts as path, but with the file extension changed to
	 * the specified extension. If path is null, the function
	 * returns null. If path does not contain a file extension,
	 * the new file extension is appended to the path. If extension
	 * is null, any exsiting extension is removed from path.
	 */
	public static String changeExtension(String path, String extension)
	{
        // .Net returns null for path == null and "" for path == "". Let's do the same.
		if (path == null || path.isEmpty())
			return path;

		String s = path;
		for (int i = path.length(); --i >= 0; )
		{
			char ch = path.charAt(i);
			if (ch == '.')
			{
				s = path.substring(0, i);
				break;
			}

			if (isDirectoryOrVolumeSeparator(ch))
				break;
		}

		if (extension != null && path.length() != 0)
		{
			if (extension.length() == 0 || extension.charAt(0) != '.')
				s = s + ".";

			s = s + extension;
		}

		return s;
	}

	/**
	 * Copycat of .Net Path.GetFileName(string)
	 *
	 * If the last character of path is a directory or volume separator character,
	 * or if path is empty or null this method returns Empty.
	 * .Net doc states that this method returns a null reference if path is null but this not true.
	 */
	public static java.lang.String getFileName(String fileName)
	{
        // .Net returns null for fileName == null and "" for fileName == "". Let's do the same.
		if (fileName == null || fileName.isEmpty())
			return fileName;

		char lastChar = fileName.charAt(fileName.length() - 1);
		if (isDirectorySeparator(lastChar))
			return "";

		java.io.File file = new java.io.File(fileName);
		return file.getName();
	}

	/**
	 * Copycat of .Net Path.GetFileNameWithoutExtension(string).
	 *
	 * .Net method returns "" for "C:\\tmp\\".
	 */
	public static String getFileNameWithoutExtension(String fileName)
	{
        // .Net returns null for fileName == null and "" for fileName == "". Let's do the same.
		if (fileName == null || fileName.isEmpty())
			return fileName;

		char lastChar = fileName.charAt(fileName.length() - 1);
		if (isDirectorySeparator(lastChar))
			return "";

		java.io.File file = new java.io.File(fileName);
		String theName = file.getName();

		int lastIndexOfdot = theName.lastIndexOf('.');
		if (lastIndexOfdot > 0)
			return theName.substring(0, lastIndexOfdot);

		return theName;
	}

    // It is hard to fully understand what .NET's Path.GetFullPath does,
    // but canonicalizing seems to be the case. Correct later if needed.
    public static String getFullPath(String path) throws Exception
    {
        java.io.File file = new java.io.File(path);
        return file.getCanonicalPath();
    }

	/**
	 * As for contract of .Net System.IO.Path.GetExtension() method
	 * returns the file extension (with dot!) or an empty string.
	 */
	public static String getExtension(String fileName)
	{
        // .Net returns null for fileName == null and "" for fileName == "". Let's do the same.
		if (fileName == null || fileName.isEmpty())
			return fileName;

		java.io.File file = new java.io.File(fileName);
		String theName = file.getName();
		int lastIndexOfdot = theName.lastIndexOf('.');

		if (lastIndexOfdot > 0)
			return theName.substring(lastIndexOfdot);

		if (lastIndexOfdot == 0)
			return theName;

		return "";
	}

	/**
	 * Emulates .Net Path.GetDirectoryName(string) method.
	 *
	 * "Returns a String containing directory information for path,
	 * or a null reference if path denotes a root directory, is the empty string (""), or is a null reference.
	 * Returns String.Empty if path does not contain directory information."
	 * - it is doc. Really returns "" for root, empty or filename w/o dir and throws if path "".
	 * - in java not throws if path "".
	 */
	public static String getDirectoryName(String path)
	{
		if (path == null || path.isEmpty())
			return path;

		java.io.File file = new java.io.File(path);

		//we have to return "tmp" for "C:/tmp/" and "" for "C:/"
		char lastChar = path.charAt(path.length() - 1);
		String fileName = file.getName();
		if (isDirectorySeparator(lastChar) && fileName != null && fileName.length() > 0)
			return file.getPath();

		String parent = file.getParent();
		if (parent == null)
			return isPathRooted(path)? null : "";

		return parent;
	}

	/**
	 * Corresponds to .Net Path.GetFullPath(Path.Combine(baseFolder, fileName).
	 *
	 * If one of the specified paths is a zero-length string, this method returns the other path.
	 * If path2 contains an absolute path, this method returns path2.
	 */
	public static String combine(String parent, String child)
	{
		if (parent == null)
			throw new NullPointerException("parent");

		if (child == null)
			throw new NullPointerException("child");

		//.Net Path.Combine returns child fileName without changes if it is an absolute path.
		if (isPathRooted(child))
			return child;

		if ("".equals(parent))
			return child;

        java.io.File combined = new File(parent, child);
        // .NET processes ".." in the path, but Java does that only when calling getCanonicalPath,
        // but I can't call this because getCanonicalPath in Java will always make it absolute.
		if (child.endsWith("\\"))
        	return combined.getPath() + "\\";
		else
			return combined.getPath();
	}

	/**
	 * Corresponds to .Net Path.GetTempPath().
	 *
	 * Returns the path of the current system's temporary folder.
	 */
	public static String getTempPath()
	{
		if (tmpdir == null)
		{
			//getting path using the new feature
            tmpdir = NativeLibSettings.getTmpDirectoryPath();
			tmpdir = new java.io.File(tmpdir).getAbsolutePath();//instead of fs.normalize(tmpdir);
		}

        return tmpdir;
	}

	/**
	 * Tests if a path includes a file extension. The result is
	 * true if the characters that follow the last directory
	 * separator ('\\' or '/') or volume separator (':') in the path include
	 * a period (".") other than a terminal period. The result is false otherwise.
	 */
	public static boolean hasExtension(String path) //@todo 1 sk test unit this
	{
	    if (path == null)
		    return false;

		for (int i = path.length(); --i >= 0;)
		{
			char ch = path.charAt(i);
			if (ch == '.')
				return i != path.length() - 1;

			if ( isDirectoryOrVolumeSeparator(ch))
				break;
		}

		return false;
	}

	/**
	 * Emulates .Net Path.IsPathRooted(string) method.
	 *
	 * IsPathRooted returns true for path strings such as "C:\\MyDir", "\\\\MyDir\\MyFile.txt" and "//MyDir/MyFile.txt".
	 * It returns false for path strings such as "MyDir".
	 *
	 * Note: C# returns true for "C:", "\\MyDir\MyFile.txt" and "/MyDir/MyFile.txt".
	 */
	public static boolean isPathRooted(String path)
	{
		if (new java.io.File(path).isAbsolute())
			return true;

        int length = path.length();
		
		if (length >= 1 && isDirectorySeparator(path.charAt(0)))
			return true;

		if (length >= 2 && JavaUtil.isWindows() && path.charAt(1) == ':')
			return true;

		return false;
	}

	/**
	 * Determine if a character is a directory separator.
	 * We always check for '/' (windows directory separator) and '\\' (unix one)
	 * because Windows programmers have been known to hard-wire both of them into their code.
	 */
	public static boolean isDirectorySeparator(char testChar)
	{
		return ((testChar == DirectorySeparatorChar) || (testChar == AltDirectorySeparatorChar));
	}

	/**
	 * Determine if a character is a directory or volume separator.
	 * We always check for '/', '\\', and ':' because Windows programmers
	 * have been known to hard-wire them into their code.
	 */
	private static boolean isDirectoryOrVolumeSeparator(char ch)
	{
	    return (isDirectorySeparator(ch) || (ch == ':'));
	}

    public static final char DirectorySeparatorChar;

    public static final char AltDirectorySeparatorChar;

    private static String tmpdir;

    static
    {
        if (JavaUtil.isWindows())
        {
            DirectorySeparatorChar = '\\';
            AltDirectorySeparatorChar = '/';
        }
        else
        {
            DirectorySeparatorChar = '/';
            AltDirectorySeparatorChar = '\\';
        }
    }
}
