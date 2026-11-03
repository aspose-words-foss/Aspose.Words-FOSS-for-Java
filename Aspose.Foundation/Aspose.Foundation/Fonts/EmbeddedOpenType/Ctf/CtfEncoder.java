// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to encode to CTF format.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#CTF for more info.
/// </remarks>
/*static*/ class CtfEncoder
{
	/* Simulation of static class by using private constructor */
	private CtfEncoder()
	{}

    /// <summary>
    /// Encodes font data to CTF format.
    /// </summary>
    static CtfEncodedData encode(byte[] fontTables) throws Exception
    {
        MemoryStream fontTablesStream = new MemoryStream(fontTables);
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(fontTablesStream);
            if (!fontTablesReader.tryReadHeader())
                return null;

            // These tables are required to encode data.
            fontTablesReader.seekToTable(OpenTypeTableTag.HEAD);
            FontHeader head = FontHeader.read(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.MAXP);
            MaximumProfile maxp = new MaximumProfile(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HHEA);
            HorizontalHeader hhea = new HorizontalHeader(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HMTX);
            HorizontalMetrics hmtx = HorizontalMetrics.read(fontTablesReader.getBinaryReader(), (hhea.NumberOfHMetrics & 0xFFFF), (maxp.NumGlyphs & 0xFFFF));

            SfntBuilder builder = new SfntBuilder(fontTablesReader.getOffsetTable().getVersion());

            CtfEncodedData ctfEncodedData = new CtfEncodedData();
            for (SfntTableRecordEntry tableRecord : fontTablesReader.getTableRecordEntries().getValues())
            {
                String tag = tableRecord.Tag;
                switch (gStringSwitchMap.of(tag))
                {
                    case/*OpenTypeTableTag.HDMX*/0:
                    {
                        byte[] hdmx = fontTablesReader.readTable(tag);
                        byte[] hdmxEncoded = encodeHdmxTable(hdmx, maxp.NumGlyphs, head.UnitsPerEm, hmtx);
                        builder.addTable(tag, hdmxEncoded);
                        break;
                    }
                    case/*OpenTypeTableTag.VDMX*/1:
                        builder.addTable(tag, encodeVdmxTable(fontTablesReader.readTable(tag)));
                        break;
                    case/*OpenTypeTableTag.CVT*/2:
                        builder.addTable(tag, encodeCvtTable(fontTablesReader.readTable(tag)));
                        break;
                    case/*OpenTypeTableTag.LOCA*/3:
                        // 'loca' table will be constructed from the 'glyf' table in decoder,
                        // but it must be written with zero length.
                        builder.addTable(tag, new byte[] { });
                        break;
                    case
/*OpenTypeTableTag.GLYF*/4:
                    {
                        ctfEncodedData = CtfGlyphEncoder.encodeGlyphData(fontTablesReader, head.isLocaShort());
                        builder.addTable(tag, ctfEncodedData.getFontTable());
                        break;
                    }
                    default:
                        // All other tables should be added not encoded.
                        builder.addTable(tag, fontTablesReader.readTable(tag));
                        break;
                }
            }

            ctfEncodedData.setFontTable(builder.writeFileToByteArray());
            return ctfEncodedData;
        }
        finally { if (fontTablesStream != null) fontTablesStream.close(); }
    }

    /// <summary>
    /// Encodes cvt table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#CVTTable for more info.
    /// </remarks>
    static byte[] encodeCvtTable(byte[] tableData) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);

            MemoryStream outStream = new MemoryStream();
            try /*JAVA: was using*/
            {
                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);

                int cvtLength = tableData.length / 2;
                writer.writeInt16(cvtLength);

                int previousValue = 0;
                for (int i = 0; i < cvtLength; i++)
                {
                    int value = reader.readInt16();
                    int difference = value - previousValue;
                    previousValue = value;

                    boolean isNegative = (difference < 0);
                    int positiveDifference = (isNegative) ? 0x00 - difference : difference;

                    int index = positiveDifference / CtfCoderUtil.CVT_LOWEST_CODE;

                    if ((index <= 8) && (difference != -32768))
                    {
                        if (isNegative)
                        {
                            int code = CtfCoderUtil.CVT_NEGATIVE_0 + index;
                            writer.writeByte((byte)code);
                            positiveDifference = (positiveDifference - index * CtfCoderUtil.CVT_LOWEST_CODE);
                            writer.writeByte((byte)positiveDifference);
                        }
                        else
                        {
                            if (index > 0)
                            {
                                int code = CtfCoderUtil.CVT_POSITIVE_1 + index - 1;
                                writer.writeByte((byte)code);
                                positiveDifference = positiveDifference - index * CtfCoderUtil.CVT_LOWEST_CODE;
                            }

                            writer.writeByte((byte)positiveDifference);
                        }
                    }
                    else
                    {
                        writer.writeByte((byte)CtfCoderUtil.CVT_LOWEST_CODE);
                        writer.writeInt16(difference);
                    }
                }

                return outStream.toArray();
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (inStream != null) inStream.close(); }
    }

    /// <summary>
    /// Encodes hdmx table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#hdmxTable for more info.
    /// </remarks>
    static byte[] encodeHdmxTable(
        byte[] tableData,
        /*ushort*/short numGlyphs,
        /*ushort*/short unitsPerEm,
        HorizontalMetrics hmtx) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);

            HdmxTable hdmx = HdmxTable.read(reader, (numGlyphs & 0xFFFF));

            MemoryStream outStream = new MemoryStream();
            try /*JAVA: was using*/
            {
                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);

                hdmx.writeHeader(writer);

                for (int i = 0; i < hdmx.DeviceRecords.length; i++)
                    hdmx.DeviceRecords[i].write(writer);

                MagnitudeWriter magnitudeWriter = new MagnitudeWriter(outStream);
                int bitsOffset = 0;
                for (int i = 0; i < hdmx.DeviceRecords.length; i++)
                {
                    HdmxDeviceRecord record = hdmx.DeviceRecords[i]; // extract stuff below in the loop's body into EncodeRecord method to simplify
                    for (int glyphId = 0; glyphId < record.Widths.length; glyphId++)
                    {
                        int width = (record.Widths[glyphId] & 0xFF);

                        int prediction = CtfCoderUtil.calculatePrediction(hdmx.DeviceRecords[i], hmtx.getHMetric(glyphId), unitsPerEm);
                        int predictionError = width - prediction;

                        // CtfHeader size = 8 and each width is byte size.
                        int newLength = 8 + (bitsOffset + Math.abs(predictionError) + 2 + 7) / 8;

                        if (newLength > tableData.length)
                        {
                            // Encoded table size is greater than original table size.
                            // Set version to 0xFFFF-real version and just copy data.
                            return CtfCoderUtil.cloneWithVersion(tableData, (/*ushort*/short)(0xFFFF - (hdmx.Version & 0xFFFF)));
                        }

                        bitsOffset += magnitudeWriter.writeValue((short)predictionError);
                    }
                }
                magnitudeWriter.flush();
                return outStream.toArray();
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (inStream != null) inStream.close(); }
    }

    /// <summary>
    /// Encodes VDMX table.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#VDMXTable for more info.
    /// </remarks>
    static byte[] encodeVdmxTable(byte[] tableData) throws Exception
    {
        MemoryStream inStream = new MemoryStream(tableData);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(inStream);

            MemoryStream outStream = new MemoryStream();
            try /*JAVA: was using*/
            {
                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);

                /*ushort*/short version = reader.readUInt16();
                /*ushort*/short numRecs = reader.readUInt16();
                /*ushort*/short numRatios = reader.readUInt16();

                writer.writeUInt16((version & 0xFFFF));
                writer.writeUInt16((numRecs & 0xFFFF));
                writer.writeUInt16((numRatios & 0xFFFF));

                // Ratios (4 bytes) and offsets (2 bytes) are not encoded.
                writer.writeBytes(reader.readBytes(4 * (numRatios & 0xFFFF)));
                /*ushort*/short[] offsets = new /*ushort*/short[(numRatios & 0xFFFF)];
                for (int i = 0; i < (numRatios & 0xFFFF); i++)
                {
                    offsets[i] = reader.readUInt16();
                    writer.writeUInt16((offsets[i] & 0xFFFF));
                }

                VdmxGroup[] vdmxGroups = new VdmxGroup[(numRatios & 0xFFFF)];
                for (int i = 0; i < (numRatios & 0xFFFF); i++)
                {
                    reader.getBaseStream().setPosition((offsets[i] & 0xFFFF));
                    vdmxGroups[i] = VdmxGroup.read(reader);
                }

                for (int i = 0; i < (numRatios & 0xFFFF); i++)
                {
                    writeVdmxGroup(writer, vdmxGroups[i]);

                    if (outStream.getPosition() > tableData.length)
                    {
                        // Encoded table size is greater than original table size.
                        // Set version to 0xFFFF-real version and just copy data.
                        return CtfCoderUtil.cloneWithVersion(tableData, (/*ushort*/short)(0xFFFF - (version & 0xFFFF)));
                    }
                }

                return outStream.toArray();
            }
            finally { if (outStream != null) outStream.close(); }
        }
        finally { if (inStream != null) inStream.close(); }
    }

    /// <summary>
    /// Writes VDMX group to binary writer.
    /// </summary>
    private static void writeVdmxGroup(BigEndianBinaryWriter writer, VdmxGroup vdmxGroup) throws Exception
    {
        writer.writeUInt16(vdmxGroup.VTables.length);
        writer.writeInt16(vdmxGroup.getYMaxMultiplier());
        writer.writeInt16(vdmxGroup.getYMinMultiplier());

        /*ushort*/short predictedYPelHeight = (short) 8;
        MemoryStream outStream = (MemoryStream)writer.getBaseStream();
        MagnitudeWriter magnitudeWriter = new MagnitudeWriter(outStream);
        for (VdmxVTable vTable : vdmxGroup.VTables)
        {
            writeVdmxVTable(magnitudeWriter, vTable, vdmxGroup, (predictedYPelHeight & 0xFFFF));
            predictedYPelHeight++;
        }
        magnitudeWriter.flush();
    }

    /// <summary>
    /// Writes VDMX VTable to the binary writer.
    /// </summary>
    private static void writeVdmxVTable(MagnitudeWriter magnitudeWriter, VdmxVTable vTable, VdmxGroup vdmxGroup, int predictedYPelHeight) throws Exception
    {
        int predictedYMax = ((vTable.YPelHeight & 0xFFFF) * vdmxGroup.getYMaxMultiplier() + 1024) / 2048;
        int predictedYMin = -((vTable.YPelHeight & 0xFFFF) * vdmxGroup.getYMinMultiplier() + 1024) / 2048;

        int yPelHeightError = (vTable.YPelHeight & 0xFFFF) - predictedYPelHeight;
        int yMaxError = vTable.YMax - predictedYMax;
        int yMinError = vTable.YMin - predictedYMin;

        magnitudeWriter.writeValue((short)yPelHeightError);
        magnitudeWriter.writeValue((short)yMaxError);
        magnitudeWriter.writeValue((short)yMinError);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"hdmx",
		"VDMX",
		"cvt ",
		"loca",
		"glyf"
	);

}

