// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2024 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Extracts entries (inner files) from ZIP archives.
/// </summary>
public /*static*/ class ZipPackageReader
{
	/* Simulation of static class by using private constructor */
	private ZipPackageReader()
	{}

    public static PackageEntryList readEntries(Stream stream) throws Exception
    {
        PackageEntryList entries = new PackageEntryList();
        ZipReaderPal zipReader = new ZipReaderPal(stream);
        try /*JAVA: was using*/
        {
            while (zipReader.moveToNextEntry())
            {
                byte[] data = zipReader.loadEntryToByteArray();
                entries.addEntry(zipReader.getEntryFileName(), data);
            }
        }
        finally { if (zipReader != null) zipReader.dispose(); }
        return entries;
    }
}

