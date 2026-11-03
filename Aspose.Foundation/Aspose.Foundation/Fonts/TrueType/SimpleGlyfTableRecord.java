// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;
import com.aspose.ms.System.msByte;


/// <summary>
/// Represents simple record in glyf table in OpenType file.
/// </summary>
class SimpleGlyfTableRecord extends GlyfTableRecord
{
    public static int readPointsCount(BigEndianBinaryReader reader, short numContours) throws Exception
    {
        IntList contourEndPoints = readContourEndPoints(reader, numContours);
        return contourEndPoints.get(contourEndPoints.getCount() - 1) + 1;
    }

    /// <summary>
    /// Reads simple record from binary reader.
    /// </summary>
    public static SimpleGlyfTableRecord readSimpleRecord(BigEndianBinaryReader reader) throws Exception
    {
        SimpleGlyfTableRecord record = new SimpleGlyfTableRecord();
        record.readHeader(reader);

        if (record.isEmpty())
            return record;

        IntList contourEndPoints = readContourEndPoints(reader, record.getNumContours());

        // Points count equals last contour end point + 1.
        int pointsCount = 1 + contourEndPoints.get(record.getNumContours() - 1);

        int instructionsLength = (reader.readUInt16() & 0xFFFF);
        record.setInstructions(reader.readBytes(instructionsLength));

        byte[] flags = new byte[pointsCount];
        int flagIndex = 0;
        while (flagIndex < pointsCount)
        {
            byte flag = reader.readByte();
            flags[flagIndex++] = flag;

            if (BitUtil.isSetByte(flag, FLAG_REPEAT))
            {
                // If FlagRepeat is set, the next byte specifies the number of additional
                // times this set of flags is to be repeated.
                int count = (reader.readByte() & 0xFF);
                for (int i = 0; i < count; i++)
                {
                    flags[flagIndex++] = flag;
                }
            }
        }

        short[] xCoordinates = new short[pointsCount];
        for (int i = 0; i < pointsCount; i++)
        {
            xCoordinates[i] =
                readXyValue(
                    reader,
                    BitUtil.isSetByte(flags[i], FLAG_SHORT_X),
                    BitUtil.isSetByte(flags[i], FLAG_POSITIVE_SHORT_OR_SAME_X));
        }

        short[] yCoordinates = new short[pointsCount];
        for (int i = 0; i < pointsCount; i++)
        {
            yCoordinates[i] =
               readXyValue(
                   reader,
                   BitUtil.isSetByte(flags[i], FLAG_SHORT_Y),
                   BitUtil.isSetByte(flags[i], FLAG_POSITIVE_SHORT_OR_SAME_Y));
        }

        // Build points array.
        record.setPoints(new SimpleGlyfPoint[pointsCount]);
        for (int i = 0; i < pointsCount; i++)
        {
            SimpleGlyfPoint point = new SimpleGlyfPoint(
                xCoordinates[i],
                yCoordinates[i],
                BitUtil.isSetByte(flags[i], FLAG_ON_CURVE),
                contourEndPoints.contains(i),
                BitUtil.isSetByte(flags[i], FLAG_OVERLAP_SIMPLE));
            record.getPoints()[i] = point;
        }

        return record;
    }

    private static IntList readContourEndPoints(BigEndianBinaryReader reader, int numContours) throws Exception
    {
        IntList result = new IntList(numContours);
        for (int i = 0; i < numContours; i++)
            result.add((reader.readUInt16() & 0xFFFF));

        result.sort();
        return result;
    }

    /// <summary>
    /// Read x or y value from binary reader according to flags logic.
    /// </summary>
    private static short readXyValue(BigEndianBinaryReader reader, boolean isShort, boolean isPositiveOrSame) throws Exception
    {
        // isShort means value is written as 8-bit unsigned, 16-bit signed otherwise.
        // isPositiveOrSame for short values means actual value is positive, negative otherwise.
        // isPositiveOrSame for long values means that this value is 0.
        if (isShort)
            return (short)((reader.readByte() & 0xFF) * (isPositiveOrSame ? 1 : -1));

        return isPositiveOrSame ? (short)0 : reader.readInt16();
    }

    /// <summary>
    /// Calculate bounding box from points coordinates.
    /// </summary>
    public void calculateBoundingBox()
    {
        if (getPoints().length == 0)
            return;

        short curX = getPoints()[0].getDX();
        short curY = getPoints()[0].getDY();
        setXMin(curX);
        setXMax(curX);
        setYMin(curY);
        setYMax(curY);

        for (int i = 1; i < getPoints().length; i++)
        {
            curX += getPoints()[i].getDX();
            curY += getPoints()[i].getDY();

            setXMin(msMath.min(getXMin(), curX));
            setXMax(msMath.max(getXMax(), curX));
            setYMin(msMath.min(getYMin(), curY));
            setYMax(msMath.max(getYMax(), curY));
        }
    }

    /// <summary>
    /// Writes simple record to binary writer.
    /// </summary>
    public /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        // Empty glyphs could be written as 0 bytes. So do it always.
        if (isEmpty())
            return;

        writeNumContoursAndBoundingBox(writer);
        writeContourEndPoints(writer);
        writeInstructions(writer);
        writePoints(writer);
    }

    private void writeContourEndPoints(BigEndianBinaryWriter writer) throws Exception
    {
        for (int i = 0; i < getPoints().length; i++)
        {
            if (getPoints()[i].isEndOfContour())
                writer.writeUInt16(i);
        }
    }

    private void writeInstructions(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16(getInstructions().length);
        writer.writeBytes(getInstructions(), 0, getInstructions().length);
    }

    private void writePoints(BigEndianBinaryWriter writer) throws Exception
    {
        byte[] flags = buildFlags();
        writeFlags(writer, flags);
        writeXCoordinates(writer, flags);
        writeYCoordinates(writer, flags);
    }

    private byte[] buildFlags()
    {
        byte[] flags = new byte[getPoints().length];
        for (int i = 0; i < getPoints().length; i++)
        {
            SimpleGlyfPoint point = getPoints()[i];
            byte flag = (byte) 0;
            if (point.isOnCurve())
                flag = (byte)((flag & 0xFF) + (FLAG_ON_CURVE & 0xFF));
            if (point.isOverlap())
                flag = (byte)((flag & 0xFF) + (FLAG_OVERLAP_SIMPLE & 0xFF));
            flag = (byte)((flag & 0xFF) + (selectCoordinateFlag(point.getDX(), FLAG_SHORT_X, FLAG_POSITIVE_SHORT_OR_SAME_X) & 0xFF));
            flag = (byte)((flag & 0xFF) + (selectCoordinateFlag(point.getDY(), FLAG_SHORT_Y, FLAG_POSITIVE_SHORT_OR_SAME_Y) & 0xFF));
            flags[i] = flag;
        }
        return flags;
    }

    private static byte selectCoordinateFlag(short coord, byte shortFlag, byte sameOrPositiveFlag)
    {
        byte result = (byte) 0;
        if (coord == 0)
        {
            result = (byte)((result & 0xFF) + (sameOrPositiveFlag & 0xFF));
        }
        else if (Math.abs(coord) <= (msByte.MAX_VALUE & 0xFF))
        {
            result = (byte)((result & 0xFF) + (shortFlag & 0xFF));
            if (coord >= 0)
                result = (byte)((result & 0xFF) + (sameOrPositiveFlag & 0xFF));
        }

        return result;
    }

    private static void writeFlags(BigEndianBinaryWriter writer, byte[] flags) throws Exception
    {
        int i = 0;
        while (i < flags.length)
        {
            byte flag = flags[i++];

            byte repeatCount = (byte) 0;
            while (i + (repeatCount & 0xFF) < flags.length && flags[i + (repeatCount & 0xFF)] == flag && repeatCount != msByte.MAX_VALUE)
                repeatCount++;

            if ((repeatCount & 0xFF) > 1)
            {
                writer.writeByte((byte)((flag & 0xFF) + (FLAG_REPEAT & 0xFF)));
                writer.writeByte(repeatCount);
                i += (repeatCount & 0xFF);
            }
            else
            {
                writer.writeByte(flag);
            }
        }
    }

    private void writeXCoordinates(BigEndianBinaryWriter writer, byte[] flags) throws Exception
    {
        for (int i = 0; i < getPoints().length; i++)
        {
            byte flag = flags[i];
            writeCoordinate(
                writer,
                getPoints()[i].getDX(),
                BitUtil.isSetByte(flag, FLAG_SHORT_X),
                BitUtil.isSetByte(flag, FLAG_POSITIVE_SHORT_OR_SAME_X));
        }
    }

    private void writeYCoordinates(BigEndianBinaryWriter writer, byte[] flags) throws Exception
    {
        for (int i = 0; i < getPoints().length; i++)
        {
            byte flag = flags[i];
            writeCoordinate(
                writer,
                getPoints()[i].getDY(),
                BitUtil.isSetByte(flag, FLAG_SHORT_Y),
                BitUtil.isSetByte(flag, FLAG_POSITIVE_SHORT_OR_SAME_Y));
        }
    }

    private static void writeCoordinate(
        BigEndianBinaryWriter writer,
        short value,
        boolean isShort,
        boolean isPositiveOrSame) throws Exception
    {
        if (isShort)
        {
            writer.writeByte((byte)Math.abs(value));
        }
        else if (!isPositiveOrSame)
        {
            writer.writeInt16(value);
        }
    }

    /// <summary>
    /// Array of glyph points.
    /// </summary>
    public SimpleGlyfPoint[] getPoints() { return mPoints; }; public void setPoints(SimpleGlyfPoint[] value) { mPoints = value; };

    private SimpleGlyfPoint[] mPoints;

    /// <summary>
    /// TrueType instructions.
    /// </summary>
    public byte[] getInstructions() { return mInstructions; }; public void setInstructions(byte[] value) { mInstructions = value; };

    private byte[] mInstructions;

    private static final byte FLAG_ON_CURVE = (byte) 0x01;
    private static final byte FLAG_SHORT_X = (byte) 0x02;
    private static final byte FLAG_SHORT_Y = (byte) 0x04;
    private static final byte FLAG_REPEAT = (byte) 0x08;
    private static final byte FLAG_POSITIVE_SHORT_OR_SAME_X = (byte) 0x10;
    private static final byte FLAG_POSITIVE_SHORT_OR_SAME_Y = (byte) 0x20;
    private static final byte FLAG_OVERLAP_SIMPLE = (byte) 0x40;
}

