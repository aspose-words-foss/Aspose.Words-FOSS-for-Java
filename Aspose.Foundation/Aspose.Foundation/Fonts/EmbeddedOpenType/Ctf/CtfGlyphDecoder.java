// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to decode CTF Glyph table.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#glyfTable for more info.
/// </remarks>
/*static*/ class CtfGlyphDecoder
{
	/* Simulation of static class by using private constructor */
	private CtfGlyphDecoder()
	{}

    /// <summary>
    /// Decodes glyph data and writes glyf and loca tables to sfnt builder.
    /// </summary>
    /// <remarks>
    /// Head table could be updated during decoding.
    /// </remarks>
    static void decodeGlyphData(
        SfntBuilder sfntBuilder,
        byte[] glyphTableData,
        byte[] glyphPushData,
        byte[] glyphInstructions,
        FontHeader head) throws Exception
    {
        MemoryStream
            glyphTableStream = new MemoryStream(glyphTableData),
            pushDataStream = new MemoryStream(glyphPushData),
            instructionStream = new MemoryStream(glyphInstructions);
        try /*JAVA: was using*/
        {
            CtfGlyphDecoderContext context = new CtfGlyphDecoderContext(glyphTableStream, pushDataStream, instructionStream);

            MemoryStream glyfTable = new MemoryStream();
            try /*JAVA: was using*/
            {
                BigEndianBinaryWriter writer = new BigEndianBinaryWriter(glyfTable);

                TTLocaTable loca = new TTLocaTable();

                while (glyphTableStream.getPosition() < glyphTableStream.getLength())
                {
                    // Loca table contains start positions of each glyph relative to glyf table start.
                    loca.getGlyphLocations().add((int)glyfTable.getPosition());

                    GlyfTableRecord glyph = decodeGlyph(context);

                    if (!glyph.isEmpty())
                        glyph.write(writer);

                    // Specification says:
                    // Local offsets should be long-aligned, i.e., multiples of 4.
                    // Offsets which are not long-aligned may seriously degrade performance of some processors.
                    StreamUtil.seekToNextPage(glyfTable, 4);
                }

                // Specification says:
                // In the particular case of the last glyph(s), loca[n] will be equal the length of the
                // glyph data ('glyf') table.
                loca.getGlyphLocations().add((int)glyfTable.getPosition());

                // If original font uses short loca we could exceed short values limit by producing more
                // glyph data. So it is better to define which loca version to use after writing glyph data.
                // Or we could optimize glyph writing.
                loca.chooseFormat();
                head.isLocaShort(loca.isLocaShort());

                sfntBuilder.addTable(OpenTypeTableTag.GLYF, glyfTable.toArray());
                sfntBuilder.addTable(OpenTypeTableTag.LOCA, loca.toByteArray());
            }
            finally { if (glyfTable != null) glyfTable.close(); }
        }
        finally { if (glyphTableStream != null) glyphTableStream.close(); if (pushDataStream != null) pushDataStream.close(); if (instructionStream != null) instructionStream.close(); }
    }

    /// <summary>
    /// Decodes single glyph.
    /// </summary>
    private static GlyfTableRecord decodeGlyph(CtfGlyphDecoderContext context) throws Exception
    {
        short numContours = context.getGlyphTableReader().readInt16();
        context.getGlyphTableReader().getBaseStream().setPosition(context.getGlyphTableReader().getBaseStream().getPosition() - 2);

        GlyfTableRecord glyph = numContours >= 0
            ? decodeSimpleGlyph(context)
            : decodeCompositeGlyph(context);

        return glyph;
    }

    /// <summary>
    /// Decodes composite glyph from CTF format.
    /// </summary>
    private static GlyfTableRecord decodeCompositeGlyph(CtfGlyphDecoderContext context) throws Exception
    {
        short numContours = context.getGlyphTableReader().readInt16();
        CompositeGlyfTableRecord glyf = new CompositeGlyfTableRecord();
        glyf.setNumContours(numContours);
        glyf.setXMin(context.getGlyphTableReader().readInt16());
        glyf.setYMin(context.getGlyphTableReader().readInt16());
        glyf.setXMax(context.getGlyphTableReader().readInt16());
        glyf.setYMax(context.getGlyphTableReader().readInt16());

        // Internal data of composite glyph is not encoded so we can read it as TrueType data.
        glyf.readComponents(context.getGlyphTableReader());

        if (glyf.getWeHaveInstructions())
            glyf.setInstructions(decodeInstructions(context));

        return glyf;
    }

    /// <summary>
    /// Decodes simple glyph from CTF format.
    /// </summary>
    static GlyfTableRecord decodeSimpleGlyph(CtfGlyphDecoderContext context) throws Exception
    {
        short numContours = context.getGlyphTableReader().readInt16();

        SimpleGlyfTableRecord glyph = new SimpleGlyfTableRecord();

        // When numContours = 0x7FFF actual number of contours and bounding box are written further.
        // Otherwise bounding box should be calculated from points coordinates.
        boolean isBoundingBoxPresent = (numContours == 0x7FFF);
        if (isBoundingBoxPresent)
        {
            glyph.setNumContours(context.getGlyphTableReader().readInt16());
            glyph.setXMin(context.getGlyphTableReader().readInt16());
            glyph.setYMin(context.getGlyphTableReader().readInt16());
            glyph.setXMax(context.getGlyphTableReader().readInt16());
            glyph.setYMax(context.getGlyphTableReader().readInt16());
        }
        else
        {
            glyph.setNumContours(numContours);
        }

        // For empty glyph nothing is written further.
        if (!glyph.isEmpty())
        {
            // Spec says:
            // End point of first contour, number of points in each subsequent contour, cumulative sum should
            // be calculated to define total number of points.
            IntList contourEndPoints = new IntList(glyph.getNumContours());
            int currentPointIndex = 0;
            for (int i = 0; i < glyph.getNumContours(); i++)
            {
                currentPointIndex += UShort255.read(context.getGlyphTableReader());
                contourEndPoints.add(currentPointIndex);
            }
            int numPoints = currentPointIndex + 1;

            // Read points.
            byte[] flags = context.getGlyphTableReader().readBytes(numPoints);
            glyph.setPoints(new SimpleGlyfPoint[numPoints]);
            for (int i = 0; i < numPoints; i++)
            {
                CtfGlyphPoint point = CtfGlyphPoint.read(context.getGlyphTableReader(), flags[i]);
                SimpleGlyfPoint ttPoint = new SimpleGlyfPoint(point.X, point.Y, point.IsOnCurve,
                                                        contourEndPoints.contains(i));
                glyph.getPoints()[i] = ttPoint;
            }

            // Now we can calculate bounding box.
            if (!isBoundingBoxPresent)
            {
                glyph.calculateBoundingBox();
            }

            glyph.setInstructions(decodeInstructions(context));
        }

        return glyph;
    }

    /// <summary>
    /// Decodes TrueType instructions.
    /// </summary>
    static byte[] decodeInstructions(CtfGlyphDecoderContext context) throws Exception
    {
        int pushCount = UShort255.read(context.getGlyphTableReader());
        int codeSize = UShort255.read(context.getGlyphTableReader());

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(stream);

            short[] pushValues = decodePushData(context.getPushDataReader(), pushCount);
            TTInstructionsUtil.writePushData(writer, pushValues);

            byte[] code = context.getInstructionsReader().readBytes(codeSize);

            writer.writeBytes(code, 0, code.length);

            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Decodes push data.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#PushData for more info.
    /// </remarks>
    static short[] decodePushData(BigEndianBinaryReader reader, int pushCount) throws Exception
    {
        short[] values = new short[pushCount];

        int i = 0;
        while (i < pushCount)
        {
            // kvk: Read255Short should not be used here because it can return
            // hop3Code or hop4Code in two situations:
            // 1. First byte value is a hop code.
            // 2. Value is encoded by 2 bytes and result equals a hop code.
            // In 2nd situation we should not treat the value as hop code.
            short code = (short)(reader.readByte() & 0xFF);

            if (code == (HOP_3_CODE & 0xFF))
            {
                short a = values[i - 2];
                values[i++] = a;
                values[i++] = Short255.read(reader);
                values[i++] = a;
            }
            else if (code == (HOP_4_CODE & 0xFF))
            {
                short a = values[i - 2];
                values[i++] = a;
                values[i++] = Short255.read(reader);
                values[i++] = a;
                values[i++] = Short255.read(reader);
                values[i++] = a;
            }
            else
            {
                reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() - 1)/*Property--*/;
                values[i++] = Short255.read(reader);
            }
        }

        return values;
    }

    private static final byte HOP_3_CODE = (byte) 251;
    private static final byte HOP_4_CODE = (byte) 252;
}

