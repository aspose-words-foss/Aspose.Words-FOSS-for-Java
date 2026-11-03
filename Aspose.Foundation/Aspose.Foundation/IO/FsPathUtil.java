// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/09/2020 by Tengiz Sharafiev

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.Directory;


/// <summary>
/// Contains utility methods for working with folder and file names.
/// </summary>
public /*static*/ class FsPathUtil
{
	/* Simulation of static class by using private constructor */
	private FsPathUtil()
	{}

    /// <summary>
    /// Returns the names of files in the specified folder.
    /// </summary>
    /// <remarks>
    /// The method is a replacement of
    /// Directory.GetFiles(string path, string searchPattern, SearchOption searchOption)
    /// existing in .NET 2.0 and higher.
    /// </remarks>
    public static ArrayList<String> getFilesFromFolder(String folderPath, boolean scanSubfolders) throws Exception
    {
        ArrayList<String> files = new ArrayList<String>();

        if (scanSubfolders)
            getFilesFromFolderCore(files, folderPath);
        else
            msArrayList.addRange(files, Directory.getFiles(folderPath));

        return files;
    }

    private static void getFilesFromFolderCore(ArrayList<String> files, String folderPath) throws Exception
    {
        msArrayList.addRange(files, Directory.getFiles(folderPath));
        for (String subfolderPath : Directory.getDirectories(folderPath))
            getFilesFromFolderCore(files, subfolderPath);
    }
}

