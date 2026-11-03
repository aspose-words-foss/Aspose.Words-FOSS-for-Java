// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2024 by Anton Savko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Stores information about the results of 'gold' test for OPC import/export.
/// </summary>
public class ZipComparisonInfo extends PackageComparisonInfo
{
    public ZipComparisonInfo(String filenameSrc, String filenameOut, String filenameGold, String filenameMS)
    {
    	super(filenameSrc, filenameOut, filenameGold, filenameMS);
	
        // Do nothing.
    }

    public ZipComparisonInfo(String filenameSrc, String filenameOut, String filenameGold, String filenameOriginalGold, String filenameMS)
    {
    	super(filenameSrc, filenameOut, filenameGold, filenameOriginalGold, filenameMS);
	
        // Do nothing.
    }

    public ZipComparisonInfo(IComparisonStreamProvider streamProvider)
    {
    	super(null, null, null, null, null);
	
        // Do nothing.
    }

    protected /*override*/ ComparisonPackage createPackage(Stream stream) throws Exception
    {
        return new ComparisonPackage(ZipPackageReader.readEntries(stream));
    }
}

