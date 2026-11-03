// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents single record in glyf table in OpenType file.
/// </summary>
abstract class GlyfTableRecord
{
    /// <summary>
    /// Writes record to binary writer.
    /// </summary>
    public abstract void write(BigEndianBinaryWriter writer) throws Exception;

    /// <summary>
    /// Writes number of contours and bounding box;
    /// </summary>
    public void writeNumContoursAndBoundingBox(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeInt16(getNumContours());
        writer.writeInt16(getXMin());
        writer.writeInt16(getYMin());
        writer.writeInt16(getXMax());
        writer.writeInt16(getYMax());
    }

    /// <summary>
    /// Read record from binary reader.
    /// </summary>
    public static GlyfTableRecord read(BigEndianBinaryReader reader) throws Exception
    {
        short numContours = reader.readInt16();
        reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() - 2);

        if (numContours < 0)
            return CompositeGlyfTableRecord.readCompositeRecord(reader);

        return SimpleGlyfTableRecord.readSimpleRecord(reader);
    }

    protected void readHeader(BigEndianBinaryReader reader) throws Exception
    {
        setNumContours(reader.readInt16());
        setXMin(reader.readInt16());
        setYMin(reader.readInt16());
        setXMax(reader.readInt16());
        setYMax(reader.readInt16());
    }

    public void initPhantomPoints(short lsb, short advance)
    {
        setPhantomPoint1X(getXMin() - lsb);
        setPhantomPoint2X(getPhantomPoint1X() + advance);
    }

    /// <summary>
    /// True when glyph is empty.
    /// </summary>
    public boolean isEmpty() { return getNumContours() == 0; }

    public boolean isComposite() { return getNumContours() < 0; }

    public short getNumContours() { return mNumContours; }; public void setNumContours(short value) { mNumContours = value; };

    private short mNumContours;
    public short getXMin() { return mXMin; }; public void setXMin(short value) { mXMin = value; };

    private short mXMin;
    public short getXMax() { return mXMax; }; public void setXMax(short value) { mXMax = value; };

    private short mXMax;
    public short getYMin() { return mYMin; }; public void setYMin(short value) { mYMin = value; };

    private short mYMin;
    public short getYMax() { return mYMax; }; public void setYMax(short value) { mYMax = value; };

    private short mYMax;
    public int getPhantomPoint1X() { return mPhantomPoint1X; }; public void setPhantomPoint1X(int value) { mPhantomPoint1X = value; };

    private int mPhantomPoint1X;
    public int getPhantomPoint2X() { return mPhantomPoint2X; }; public void setPhantomPoint2X(int value) { mPhantomPoint2X = value; };

    private int mPhantomPoint2X;
}

