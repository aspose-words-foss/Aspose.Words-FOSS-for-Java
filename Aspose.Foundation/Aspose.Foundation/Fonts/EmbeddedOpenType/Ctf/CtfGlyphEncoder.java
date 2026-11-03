// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to encode glyph table to CTF.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#glyfTable for more info.
/// </remarks>
/*static*/ class CtfGlyphEncoder
{
	/* Simulation of static class by using private constructor */
	private CtfGlyphEncoder()
	{}

    /// <summary>
    /// Encodes glyph table data and returns it in <see cref="CtfEncodedData"/>.
    /// </summary>
    static CtfEncodedData encodeGlyphData(SfntReader fontReader, boolean isLocaShort) throws Exception
    {
        fontReader.seekToTable(OpenTypeTableTag.LOCA);
        TTLocaTable loca = TTLocaTable.read(
            fontReader.getBinaryReader(),
            fontReader.getTableRecordEntries().get(OpenTypeTableTag.LOCA).Length,
            isLocaShort);

        CtfEncodedData encodedData = new CtfEncodedData();
        MemoryStream
            encodedGlyphTableStream = new MemoryStream(),
            encodedInstructionsStream = new MemoryStream(),
            encodedPushDataStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            CtfGlyphEncoderContext context = new CtfGlyphEncoderContext(
                encodedGlyphTableStream,
                encodedPushDataStream,
                encodedInstructionsStream);

            fontReader.seekToTable(OpenTypeTableTag.GLYF);
            int glyphTableStartPosition = (int)fontReader.getBaseStream().getPosition();
            BigEndianBinaryReader reader = fontReader.getBinaryReader();
            IntList glyphLocations = loca.getGlyphLocations();
            for (int i = 0; i < glyphLocations.getCount() - 1; i++)
            {
                // Write zero for duplicated glyphs.
                if (((i + 1) < glyphLocations.getCount()) && (glyphLocations.get(i) == glyphLocations.get(i + 1)))
                {
                    context.getGlyphTableWriter().writeInt16(0x0);
                    continue;
                }

                reader.getBaseStream().setPosition(glyphTableStartPosition + loca.getGlyphLocations().get(i));
                GlyfTableRecord glyph = GlyfTableRecord.read(reader);
                encodeGlyph(glyph, context);
            }

            encodedData.setFontTable(encodedGlyphTableStream.toArray());
            encodedData.setPushData(encodedPushDataStream.toArray());
            encodedData.setInstructions(encodedInstructionsStream.toArray());
            return encodedData;
        }
        finally { if (encodedGlyphTableStream != null) encodedGlyphTableStream.close(); if (encodedInstructionsStream != null) encodedInstructionsStream.close(); if (encodedPushDataStream != null) encodedPushDataStream.close(); }
    }

    /// <summary>
    /// Encodes glyph to CTF format.
    /// </summary>
    static void encodeGlyph(GlyfTableRecord glyph, CtfGlyphEncoderContext context) throws Exception
    {
        if (glyph.getNumContours() >= 0)
            encodeSimpleGlyph((SimpleGlyfTableRecord)glyph, context);
        else
            encodeCompositeGlyph((CompositeGlyfTableRecord)glyph, context);
    }

    /// <summary>
    /// Encodes composite glyph to CTF format.
    /// </summary>
    static void encodeCompositeGlyph(CompositeGlyfTableRecord glyph, CtfGlyphEncoderContext context) throws Exception
    {
        glyph.writeNumContoursAndBoundingBox(context.getGlyphTableWriter());
        for (ComponentGlyphRecord component : glyph.getComponents())
            component.write(context.getGlyphTableWriter());

        if (glyph.getWeHaveInstructions())
            encodeInstructions(glyph.getInstructions(), context);
    }

    /// <summary>
    /// Encodes simple glyph to CTF format.
    /// </summary>
    static void encodeSimpleGlyph(SimpleGlyfTableRecord glyph, CtfGlyphEncoderContext context) throws Exception
    {
        // IN. By the MTX spec 5.7.1. Bounding Box,
        // the bounding box information can be computed from the data point coordinates and,
        // therefore, bounding box information may be omitted in glyph record.
        // At the same time it is said that in some event it is necessary to store the bounding box.
        // But I cannot see such events, so for a moment bounding box is not writing and therefore, it
        // will be always reconstructed in decoder.
        context.getGlyphTableWriter().writeInt16(glyph.getNumContours());

        // If glyph is empty, then it is nothing to write else.
        if (glyph.isEmpty())
            return;

        // Write number of points in each contour.
        // First value is offset of second end point.
        int numberOfContourPoints = -1;
        for (SimpleGlyfPoint point : glyph.getPoints())
        {
            numberOfContourPoints++;
            if (point.isEndOfContour())
            {
                UShort255.write((((/*ushort*/short)numberOfContourPoints) & 0xFFFF), context.getGlyphTableWriter());
                numberOfContourPoints = 0;
            }
        }
        // Write triple encoding flags for each point.
        byte[] flags = new byte[glyph.getPoints().length];
        for (int i = 0; i < flags.length; i++)
        {
            SimpleGlyfPoint point = glyph.getPoints()[i];
            flags[i] = TripletEncoding.getTripletEncodingIndex(point.getDX(), point.getDY());
            if (!point.isOnCurve())
                flags[i] |= 0x80;

            context.getGlyphTableWriter().writeByte(flags[i]);
        }

        // Write coordinates for each point.
        BitBinaryWriter bitWriter = new BitBinaryWriter(context.getGlyphTableWriter().getBaseStream(), true);
        for (int i = 0; i < glyph.getPoints().length; i++)
            TripletEncoding.writeTriplet(bitWriter, flags[i], glyph.getPoints()[i]);
        bitWriter.flush();

        // Write glyph instructions.
        encodeInstructions(glyph.getInstructions(), context);
    }

    /// <summary>
    /// Encodes TrueType instructions to CTF format and writes it to binary writer.
    /// </summary>
    static void encodeInstructions(byte[] instructions, CtfGlyphEncoderContext context) throws Exception
    {
        MemoryStream instructionsStream = new MemoryStream(instructions);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader instructionsReader = new BigEndianBinaryReader(instructionsStream);
            IntList pushData = TTInstructionsUtil.readPushData(instructionsReader);

            // Write push data length.
            UShort255.write((((/*ushort*/short)pushData.getCount()) & 0xFFFF), context.getGlyphTableWriter());
            // Write code length.
            /*ushort*/short codeSize = (/*ushort*/short)(instructionsStream.getLength() - instructionsStream.getPosition());
            UShort255.write((codeSize & 0xFFFF), context.getGlyphTableWriter());

            encodePushData(pushData, context.getPushDataWriter());

            byte[] code = instructionsReader.readBytes((codeSize & 0xFFFF));
            context.getInstructionsWriter().writeBytes(code, 0, code.length);
        }
        finally { if (instructionsStream != null) instructionsStream.close(); }
    }

    /// <summary>
    /// Encodes push data to CTF format and writes it to binary writer.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#PushData for more info.
    /// </remarks>
    static void encodePushData(IntList pushData, BigEndianBinaryWriter writer) throws Exception
    {
        int i = 0;
        while (i < pushData.getCount())
        {
            int a = pushData.get(i);
            // Check for Hop3 sequence.
            // If the previous time we already wrote down a hop-code with the same value 'a',
            // then we have to lookup back to check it.
            // In this case we should add just Hop3 code and 'X2' value.
            if ((((i - 2) >= 0) && ((i + 2) < pushData.getCount())) && ((pushData.get(i - 2) == a) && (pushData.get(i + 2) == a)))
            {
                // Check for Hop4 sequence.
                byte hopCode = (((i + 4) < pushData.getCount()) && (pushData.get(i + 4) == a)) ? HOP_4_CODE : HOP_3_CODE;

                // Write values for Hop3 code.
                writer.writeByte(hopCode);
                Short255.write((short)pushData.get(i + 1), writer);
                i += 3;

                if (hopCode == HOP_4_CODE)
                {
                    // Write values for Hop4 code.
                    Short255.write((short)pushData.get(i), writer);
                    i += 2;
                }
            }// Check for Hop3 sequence again, but now we check forward.
            else if (((i + 4) < pushData.getCount()) && ((pushData.get(i + 2) == a) && (pushData.get(i + 4) == a)))
            {
                // Sequence for Hop3Code found.
                Short255.write((short)a, writer);
                Short255.write((short)pushData.get(i + 1), writer);

                // Check for Hop4 sequence.
                byte hopCode = (((i + 6) < pushData.getCount()) && (pushData.get(i + 6) == a)) ? HOP_4_CODE : HOP_3_CODE;

                // Write rest values for Hop3 code.
                writer.writeByte(hopCode);
                Short255.write((short)pushData.get(i + 3), writer);
                i += 5;

                if (hopCode == HOP_4_CODE)
                {
                    // Write values for Hop4 code.
                    Short255.write((short)pushData.get(i), writer);
                    i += 2;
                }
            }
            else
            {
                Short255.write((short)pushData.get(i++), writer);
            }
        }
    }

    private static final byte HOP_3_CODE = (byte) 251;
    private static final byte HOP_4_CODE = (byte) 252;
}

