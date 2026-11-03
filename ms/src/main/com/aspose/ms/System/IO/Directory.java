/**
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, May 29, 2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.System.IO;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Copycat of .Net Directory class.
 * Only static methods used by Aspose.words are implemented.
 */
public class Directory
{
    public static String getCurrentDirectory() throws Exception
    {
        return System.getProperty("user.dir");
    }

    // Note this only changes the "user.dir" property, but does not change the underlying OS current directory.
    // There is no way in Java to change the OS current directory... and we probably don't need that.
    public static void setCurrentDirectory(String value) throws Exception
    {
        System.setProperty("user.dir", value);
    }

	/**
	 * Determines whether the file denoted by this
     * abstract pathname exists <em>and</em> is a directory
	 */
	public static boolean exists(String folderName)
	{
		return new File(folderName).isDirectory();
	}

	/**
	 * Deletes the specified directory and, if indicated, any subdirectories in the directory.
	 */
	public static void delete(String folderName, boolean recursive)
	{
        delete(new File(folderName), recursive);
	}

	/**
	 * JAVA-added
	 */
	public static void delete(File dir, boolean recursive)
	{
		assert dir.isDirectory();

		if (recursive)
		{
			for (File file : dir.listFiles())
			{
				if (file.isDirectory())
					Directory.delete(file, true);
				else
					file.delete();
			}
		}

		dir.delete();
	}

	/**
	 * Creates all directories and subdirectories as specified by path.
	 */
	public static void createDirectory(String folderName)
	{
		new File(folderName).mkdirs();
	}

	public static String[] getFiles(String folderName)
	{
		File[] files = new File(folderName).listFiles();

		ArrayList<String> fileNames = new ArrayList<String>();
		for (File f : files)
		{
			if (f.isFile())
				fileNames.add(f.getAbsolutePath());
		}

		return fileNames.toArray(new String[fileNames.size()]); //JAVA-changed
	}

	//JAVA-added, returns java.io.File
	public static File[] getFilesInternal(String folderName, String wildCard)
	{
		File dir = new File(folderName);
		return dir.listFiles(new WildCardFileFilter(wildCard));
	}

    public static String[] getFiles(String folderName, String wildCard)
    {
	    //checks for nulls performed in called ctors.

	    File dir = new File(folderName);
	    if (!dir.isDirectory())
		    throw new IllegalArgumentException("'" + folderName + "' is not directory");

	    //dir.list() returns just names, so we have to use listFiles() and get full path from returned Files.
	    File[] files = dir.listFiles(new WildCardFileNameFilter(wildCard));
	    
	    String[] names = new String[files.length];
	    for (int i = 0; i < files.length; i++)
		    names[i] = files[i].getAbsolutePath();

	    return names;
    }

	public static String[] getFiles(String folderName, String wildCard, /*SearchOption*/int option)
	{
		//checks for nulls performed in called ctors.

		if (option == SearchOption.TOP_DIRECTORY_ONLY)
			return getFiles(folderName, wildCard);
		else if (option == SearchOption.ALL_DIRECTORIES)
			return getFilesRecursive(folderName, wildCard);
		else
			throw new IllegalArgumentException("Unknown SearchOption: " + option);
	}

	//JAVA-added
	public static String[] getFilesRecursive(String folderName, String wildCard)
	{
		//checks for nulls performed in called ctors.

		File dir = new File(folderName);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("'" + folderName + "' is not directory");

		Collection<File> files = new LinkedList<File>();
		listFilesRecursive(dir, files, new WildCardFileFilterRecursive(wildCard));
		
		String[] names = new String[files.size()];
		int i = 0;
		for (File file : files)
			names[i++] = file.getAbsolutePath();

		return names;
	}

	//JAVA-added
	private static void listFilesRecursive(File directory, Collection<File> files, WildCardFileFilterRecursive filter)
	{
		File[] list = directory.listFiles(filter);
		if (list == null)
			return;

		for (File file : list)
		{
			if (file.isDirectory())
				listFilesRecursive(file, files, filter);
			else
				files.add(file);
		}
	}

	// Taken from http://code.hammerpig.com/search-for-files-in-directory-using-wildcards-in-java.html
    private static class WildCardFileFilter implements FileFilter
    {
        private Pattern mPattern;

        public WildCardFileFilter(String pattern)
        {
            mPattern = Pattern.compile(pattern.replace("*", ".*").replace("?", "."));
        }

        public boolean accept(File file)
        {
            return mPattern.matcher(file.getName()).find();
        }
    }

    private static class WildCardFileNameFilter implements FilenameFilter
    {
        private Pattern mPattern;

        public WildCardFileNameFilter(String pattern)
        {
	        if (pattern == null)
		        throw new NullPointerException("pattern");

            mPattern = Pattern.compile(pattern.replace("*", ".*").replace("?", "."));
        }

	    public boolean accept(File dir, String name)
	    {
		    return mPattern.matcher(name).find();
	    }
    }

	/**
	 * Recursive filter means that it accepts:
	 * 1) files with name that accepts given wildcard (exactly like WildCardFileNameFilter).
	 * 2) AND directories (with any name) -- to recursively search subdirectories.
	 */
	private static class WildCardFileFilterRecursive implements FileFilter
	{
		private Pattern mPattern;

		public WildCardFileFilterRecursive(String pattern)
		{
			mPattern = Pattern.compile(pattern.replace("*", ".*").replace("?", "."));
		}

		public boolean accept(File file)
		{
			return file.isDirectory() || mPattern.matcher(file.getName()).find();
		}
	}

	public static String[] getDirectories(String folderName)
	{
		File[] files = new File(folderName).listFiles();

		ArrayList<String> dirNames = new ArrayList<String>();
		for (File f : files)
		{
			if (f.isDirectory())
				dirNames.add(f.getAbsolutePath());
		}

		return dirNames.toArray(new String[dirNames.size()]);
	}
}
