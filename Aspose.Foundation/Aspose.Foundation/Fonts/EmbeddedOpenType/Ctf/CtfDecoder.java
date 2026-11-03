// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to decode CTF files.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#CTF for more info.
/// </remarks>
/*static*/ class CtfDecoder
{
	/* Simulation of static class by using private constructor */
	private CtfDecoder()
	{}

    /// <summary>
    /// Decodes CTF data.
    /// </summary>
    /// <param name="fontTables">CTF font tables block.</param>
    /// <param name="glyphPushData">CTF glyph push data block.</param>
    /// <param name="glyphInstructions">CTF glyph instructions block.</param>
    /// <returns>Decoded OpenType font.</returns>
    static byte[] decode(byte[] fontTables, byte[] glyphPushData, byte[] glyphInstructions) throws Exception
    {
        MemoryStream fontTablesStream = new MemoryStream(fontTables);
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(fontTablesStream);
            fontTablesReader.readHeader();

            // These tables required to decode data.
            fontTablesReader.seekToTable(OpenTypeTableTag.HEAD);
            FontHeader head = FontHeader.read(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.MAXP);
            MaximumProfile maxp = new MaximumProfile(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HHEA);
            HorizontalHeader hhea = new HorizontalHeader(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HMTX);
            HorizontalMetrics hmtx = HorizontalMetrics.read(fontTablesReader.getBinaryReader(), (hhea.NumberOfHMetrics & 0xFFFF), (maxp.NumGlyphs & 0xFFFF));

            SfntBuilder builder = new SfntBuilder(fontTablesReader.getOffsetTable().getVersion());

            for (SfntTableRecordEntry tableRecord : fontTablesReader.getTableRecordEntries().getValues())
            {
                switch (gStringSwitchMap.of(tableRecord.Tag))
                {
                    case/*OpenTypeTableTag.HDMX*/0:
                        builder.addTable(
                            tableRecord.Tag,
                            decodeHdmxTable(
                                fontTablesReader.readTable(tableRecord.Tag),
                                maxp.NumGlyphs,
                                head.UnitsPerEm,
                                hmtx));
                        break;
                    case/*OpenTypeTableTag.VDMX*/1:
                        builder.addTable(tableRecord.Tag, decodeVdmxTable(fontTablesReader.readTable(tableRecord.Tag)));
                        break;
                    case/*OpenTypeTableTag.CVT*/2:
                        builder.addTable(tableRecord.Tag, decodeCvtTable(fontTablesReader.readTable(tableRecord.Tag)));
                        break;
                    case/*OpenTypeTableTag.LOCA*/3:
                        // loca table will be created during glyf table processing.
                        break;
                    case/*OpenTypeTableTag.GLYF*/4:
                        CtfGlyphDecoder.decodeGlyphData(
                            builder,
                            fontTablesReader.readTable(tableRecord.Tag),
                            glyphPushData,
                            glyphInstructions,
                            head);
                        break;
                    case/*OpenTypeTableTag.HEAD*/5:
                        // head table could be updated and will be added to builder after decoding.
                        break;
                    default:
                        // All other table are not encoded.
                        builder.addTable(tableRecord.Tag, fontTablesReader.readTable(tableRecord.Tag));
                        break;
                }
            }

            builder.addTable(OpenTypeTableTag.HEAD, head.toByteArray());

            return builder.writeFileToByteArray();
        }
        finally { if (fontTablesStream != null) fontTablesStream.close(); }
    }

    /// <summary>
    /// Decodes cvt table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#CVTTable for more info.
    /// </remarks>
    static byte[] decodeCvtTable(byte[] tableData) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);
            /*ushort*/short numEntries = reader.readUInt16();

            MemoryStream outStream = new MemoryStream((numEntries & 0xFFFF) * 2);
            try /*JAVA: was using*/
            {
                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);

                short previousValue = 0;
                for (int i = 0; i < (numEntries & 0xFFFF); i++)
                {
                    int newVlaue;
                    short byte1 = (short)(reader.readByte() & 0xFF);
                    if (byte1 < CtfCoderUtil.CVT_LOWEST_CODE)
                    {
                        newVlaue = previousValue + byte1;
                    }
                    else if (byte1 >= CtfCoderUtil.CVT_POSITIVE_1 && byte1 <= CtfCoderUtil.CVT_POSITIVE_8)
                    {
                        short byte2 = (short)(reader.readByte() & 0xFF);
                        int difference = (CtfCoderUtil.CVT_LOWEST_CODE * (byte1 - CtfCoderUtil.CVT_POSITIVE_1 + 1) + byte2);
                        newVlaue = previousValue + difference;
                    }
                    else if (byte1 >= CtfCoderUtil.CVT_NEGATIVE_0 && byte1 <= CtfCoderUtil.CVT_NEGATIVE_8)
                    {
                        short byte2 = (short)(reader.readByte() & 0xFF);
                        int difference = (-(CtfCoderUtil.CVT_LOWEST_CODE * (byte1 - CtfCoderUtil.CVT_NEGATIVE_0) + byte2));
                        newVlaue = previousValue + difference;
                    }
                    else
                    {
                        newVlaue = previousValue + reader.readInt16();
                    }

                    if (newVlaue < Short.MIN_VALUE)
                        newVlaue += 1 << 16;
                    else if (newVlaue > Short.MAX_VALUE)
                        newVlaue -= 1 << 16;

                    writer.writeInt16(newVlaue);
                    previousValue = (short)newVlaue;
                }

                return outStream.toArray();
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (inStream != null) inStream.close(); }
    }

    /// <summary>
    /// Decodes hdmx table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#hdmxTable for more info.
    /// </remarks>
    private static byte[] decodeHdmxTable(
        byte[] tableData,
        /*ushort*/short numGlyphs,
        /*ushort*/short unitsPerEm,
        HorizontalMetrics hmtx) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);

            /*ushort*/short version = reader.readUInt16();
            if ((version & 0xFFFF) == 0xFFFF)
            {
                // Table is not encoded.
                return CtfCoderUtil.cloneWithVersion(tableData, (/*ushort*/short)(0xFFFF - (version & 0xFFFF)));
            }

            HdmxTable hdmx = new HdmxTable();
            hdmx.Version = version;
            hdmx.NumGlyphs = (numGlyphs & 0xFFFF);
            /*ushort*/short numRecords = reader.readUInt16();
            hdmx.DeviceRecordSize = reader.readInt32();

            hdmx.DeviceRecords = new HdmxDeviceRecord[(numRecords & 0xFFFF)];
            for (int i = 0; i < (numRecords & 0xFFFF); i++)
            {
                CtfHdmxDeviceRecord ctfRecord = CtfHdmxDeviceRecord.read(reader);
                HdmxDeviceRecord record = new HdmxDeviceRecord();
                record.MaxWidth = ctfRecord.MaxWidth;
                record.PixelSize = ctfRecord.PixelSize;
                record.Widths = new byte[(numGlyphs & 0xFFFF)];
                hdmx.DeviceRecords[i] = record;
            }

            MagnitudeReader magnitudeReader = new MagnitudeReader(inStream);
            for (int i = 0; i < (numRecords & 0xFFFF); i++)
            {
                for (int glyphId = 0; glyphId < (numGlyphs & 0xFFFF); glyphId++)
                {
                    // Each width is encoded by its prediction error.
                    // Prediction is calculated by following formula for correct rounding.
                    int prediction = CtfCoderUtil.calculatePrediction(hdmx.DeviceRecords[i], hmtx.getHMetric(glyphId), unitsPerEm);
                    short predictionError = magnitudeReader.readValue();
                    hdmx.DeviceRecords[i].Widths[glyphId] = (byte)(prediction + predictionError);
                }
            }

            // Ignore original record size and use optimal value.
            hdmx.setOptimalRecordSize();

            return hdmx.toByteArray();
        }
        finally { if (inStream != null) inStream.close(); }
    }

    /// <summary>
    /// Decodes VDMX table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#VDMXTable for more info.
    /// </remarks>
    private static byte[] decodeVdmxTable(byte[] tableData) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);

            MemoryStream outStream = new MemoryStream();
            try /*JAVA: was using*/
            {
                /*ushort*/short version = reader.readUInt16();
                if ((version & 0xFFFF) == 0xFFFF || (version & 0xFFFF) == (0xFFFF - 0x0001))
                {
                    // Table is not encoded.
                    return CtfCoderUtil.cloneWithVersion(tableData, (/*ushort*/short)(0xFFFF - (version & 0xFFFF)));
                }

                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);

                /*ushort*/short numRecs = reader.readUInt16();
                /*ushort*/short numRatios = reader.readUInt16();

                writer.writeUInt16((version & 0xFFFF));
                writer.writeUInt16((numRecs & 0xFFFF));
                writer.writeUInt16((numRatios & 0xFFFF));

                // Ratios and offsets are not encoded.
                writer.writeBytes(reader.readBytes((numRatios & 0xFFFF) * 6));

                for (int i = 0; i < (numRecs & 0xFFFF); i++)
                {
                    /*ushort*/short predictedPpem = (short) 8;
                    /*ushort*/short groupNumRecs = reader.readUInt16();
                    short yMaxMult = reader.readInt16();
                    short yMinMult = reader.readInt16();

                    VdmxGroup group = new VdmxGroup();
                    group.VTables = new VdmxVTable[(groupNumRecs & 0xFFFF)];

                    MagnitudeReader magnitudeReader = new MagnitudeReader(inStream);
                    for (int j = 0; j < (groupNumRecs & 0xFFFF); j++)
                    {
                        group.VTables[j] = new VdmxVTable();

                        short ppemError = magnitudeReader.readValue();
                        short yMaxError = magnitudeReader.readValue();
                        short yMinError = magnitudeReader.readValue();

                        group.VTables[j].YPelHeight = (/*ushort*/short)((predictedPpem & 0xFFFF) + ppemError);
                        int predictedYMax = ((group.VTables[j].YPelHeight & 0xFFFF) * yMaxMult + 1024) / 2048;
                        int predictedYMin = -((group.VTables[j].YPelHeight & 0xFFFF) * yMinMult + 1024) / 2048;
                        group.VTables[j].YMax = (short)(predictedYMax + yMaxError);
                        group.VTables[j].YMin = (short)(predictedYMin + yMinError);

                        predictedPpem++;
                    }

                    group.write(writer);
                }

                return outStream.toArray();
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (inStream != null) inStream.close(); }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"hdmx",
		"VDMX",
		"cvt ",
		"loca",
		"glyf",
		"head"
	);

}

