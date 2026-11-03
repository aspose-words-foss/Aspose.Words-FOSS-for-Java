// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2009 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Thrown when a comparer detects a difference between the Out file and the Gold file.
/// </summary>
public class GoldDifferenceException extends Exception
{
    /// <summary>
    /// Exception constructor with additional parameter which helps to identify filenames being compared.
    /// </summary>
    public GoldDifferenceException(String filename) throws Exception
    {
    	super(msString.format("Files are not the same. {0}", joinFileNames(filename)));
	
    }

    public GoldDifferenceException(String f1, String f2) throws Exception
    {
    	super(msString.format("Files are not the same. {0}", joinFileNames(f1, f2)));
	
    }

    private static String joinFileNames(/*params*/ String... filenames) throws Exception
    {
        Debug.assertTrue(filenames.length != 0);

        ArrayList<String> fullFilePaths = new ArrayList<String>();
        for (String filename : filenames)
        {
            if (msString.isNullOrEmpty(filename))
                continue;

            msArrayList.add(fullFilePaths, Path.getFullPath(filename));
        }

        if (fullFilePaths.size() == 1)
            return fullFilePaths.get(0);

        fullFilePaths.add(0, "");
        return msString.join(StringUtil.getNewLine() + "\t", msArrayList.toArray(fullFilePaths, new String[0]));
    }
}

