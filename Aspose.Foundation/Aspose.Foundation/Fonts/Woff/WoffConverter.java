// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2022 by Nikolay Sezganov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// Converts WOFF font to SFNT format.
/// https://www.w3.org/TR/WOFF/#OverallStructure
/// </summary>
public /*static*/ class WoffConverter
{
	/* Simulation of static class by using private constructor */
	private WoffConverter()
	{}

    public static byte[] convert(Stream woffStream) throws Exception
    {
        BigEndianBinaryReader woffBinaryReader = new BigEndianBinaryReader(woffStream);
        WoffHeader header = WoffHeader.read(woffBinaryReader);
        if (header == null)
        {
            return null;
        }

        WoffTableDirectory[] woffTables = readTableDirectories(woffBinaryReader, header.getNumTables());
        SfntBuilder sfntBuilder = new SfntBuilder();

        for (WoffTableDirectory table : woffTables)
        {
            woffBinaryReader.getBaseStream().seek((table.getOffset() & 0xFFFFFFFFL), SeekOrigin.BEGIN);
            byte[] tableData = woffBinaryReader.readBytes((int)(table.getCompLength() & 0xFFFFFFFFL));
            if (table.isCompressed())
            {
                tableData = ZipUtilPal.inflate(tableData, (int)(table.getOrigLength() & 0xFFFFFFFFL), ZipMethod.ZLIB);
                Debug.assertTrue(tableData.length == (table.getOrigLength() & 0xFFFFFFFFL));
            }
            sfntBuilder.addTable(table.getName(), tableData);
        }

        return sfntBuilder.writeFileToByteArray();
    }

    private static WoffTableDirectory[] readTableDirectories(
        BigEndianBinaryReader reader,
        int tableCount) throws Exception
    {
        WoffTableDirectory[] tableDirectories = new WoffTableDirectory[tableCount];
        for (int i = 0; i < tableCount; ++i)
        {
            tableDirectories[i] = WoffTableDirectory.read(reader);
        }
        return tableDirectories;
    }
}

