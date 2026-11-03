// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/06/2025 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



class ComponentGlyphRecord
{
    public static ComponentGlyphRecord read(BigEndianBinaryReader reader) throws Exception
    {
        ComponentGlyphRecord result = new ComponentGlyphRecord();
        result.setFlags(reader.readUInt16());
        result.setGlyphIndex(reader.readUInt16());
        result.readArgs(reader);
        result.readScale(reader);
        return result;
    }

    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16((getFlags() & 0xFFFF));
        writer.writeUInt16((getGlyphIndex() & 0xFFFF));
        writeArgs(writer);
        writeScale(writer);
    }

    private void readArgs(BigEndianBinaryReader reader) throws Exception
    {
        if (getArg1And2AreWords() && getArgsAreXYValues())
        {
            setArg1(reader.readInt16());
            setArg2(reader.readInt16());
        }
        else if (getArg1And2AreWords() && !getArgsAreXYValues())
        {
            setArg1((reader.readUInt16() & 0xFFFF));
            setArg2((reader.readUInt16() & 0xFFFF));
        }
        else if (!getArg1And2AreWords() && getArgsAreXYValues())
        {
            setArg1(reader.readSByte());
            setArg2(reader.readSByte());
        }
        else if (!getArg1And2AreWords() && !getArgsAreXYValues())
        {
            setArg1((reader.readByte() & 0xFF));
            setArg2((reader.readByte() & 0xFF));
        }
    }

    public void writeArgs(BigEndianBinaryWriter writer) throws Exception
    {
        if (getArg1And2AreWords() && getArgsAreXYValues())
        {
            writer.writeInt16(getArg1());
            writer.writeInt16(getArg2());
        }
        else if (getArg1And2AreWords() && !getArgsAreXYValues())
        {
            writer.writeUInt16(getArg1());
            writer.writeUInt16(getArg2());
        }
        else if (!getArg1And2AreWords() && getArgsAreXYValues())
        {
            writer.writeSByte(getArg1());
            writer.writeSByte(getArg2());
        }
        else if (!getArg1And2AreWords() && !getArgsAreXYValues())
        {
            writer.writeByte((byte)getArg1());
            writer.writeByte((byte)getArg2());
        }
    }

    private void readScale(BigEndianBinaryReader reader) throws Exception
    {
        if (getWeHaveAScale())
        {
            setXScale(new FixedPoint2Dot14(reader.readInt16()));
        }
        else if (getWeHaveXAndYScale())
        {
            setXScale(new FixedPoint2Dot14(reader.readInt16()));
            setYScale(new FixedPoint2Dot14(reader.readInt16()));
        }
        else if (getWeHaveATwoByTwo())
        {
            setXScale(new FixedPoint2Dot14(reader.readInt16()));
            setScale01(new FixedPoint2Dot14(reader.readInt16()));
            setScale10(new FixedPoint2Dot14(reader.readInt16()));
            setYScale(new FixedPoint2Dot14(reader.readInt16()));
        }
    }

    private void writeScale(BigEndianBinaryWriter writer) throws Exception
    {
        if (getWeHaveAScale())
        {
            writer.writeInt16(getXScale().getValue());
        }
        else if (getWeHaveXAndYScale())
        {
            writer.writeInt16(getXScale().getValue());
            writer.writeInt16(getYScale().getValue());
        }
        else if (getWeHaveATwoByTwo())
        {
            writer.writeInt16(getXScale().getValue());
            writer.writeInt16(getScale01().getValue());
            writer.writeInt16(getScale10().getValue());
            writer.writeInt16(getYScale().getValue());
        }
    }

    public void updateXYSizeFlag()
    {
        // Update Arg1And2AreWords flag after updating args for the case of X/Y values.
        // For now only increase the size if it is required.
        Debug.assertTrue(getArgsAreXYValues());
        if (!getArg1And2AreWords() && (valueExceedsSByte(getArg1()) || valueExceedsSByte(getArg2())))
            setFlags((/*ushort*/short)((getFlags() & 0xFFFF) | (FLAGS_ARG_1_AND_2_ARE_WORDS & 0xFFFF)));
    }

    private static boolean valueExceedsSByte(int value)
    {
        return value > Byte.MAX_VALUE || value < Byte.MIN_VALUE;
    }

    public /*ushort*/short getFlags() { return mFlags; }; private void setFlags(/*ushort*/short value) { mFlags = value; };

    private /*ushort*/short mFlags;
    public /*ushort*/short getGlyphIndex() { return mGlyphIndex; }; public void setGlyphIndex(/*ushort*/short value) { mGlyphIndex = value; };

    private /*ushort*/short mGlyphIndex;
    public int getArg1() { return mArg1; }; public void setArg1(int value) { mArg1 = value; };

    private int mArg1;
    public int getArg2() { return mArg2; }; public void setArg2(int value) { mArg2 = value; };

    private int mArg2;
    public FixedPoint2Dot14 getXScale() { return mXScale; }; private void setXScale(FixedPoint2Dot14 value) { mXScale = value; };

    private FixedPoint2Dot14 mXScale;
    public FixedPoint2Dot14 getYScale() { return mYScale; }; private void setYScale(FixedPoint2Dot14 value) { mYScale = value; };

    private FixedPoint2Dot14 mYScale;
    public FixedPoint2Dot14 getScale01() { return mScale01; }; private void setScale01(FixedPoint2Dot14 value) { mScale01 = value; };

    private FixedPoint2Dot14 mScale01;
    public FixedPoint2Dot14 getScale10() { return mScale10; }; private void setScale10(FixedPoint2Dot14 value) { mScale10 = value; };

    private FixedPoint2Dot14 mScale10;

    public boolean getArg1And2AreWords() { return BitUtil.isSetUInt16(getFlags(), FLAGS_ARG_1_AND_2_ARE_WORDS); }

    public boolean getArgsAreXYValues() { return BitUtil.isSetUInt16(getFlags(), FLAGS_ARGS_ARE_XY_VALUES); }

    public boolean getWeHaveAScale() { return BitUtil.isSetUInt16(getFlags(), FLAGS_WE_HAVE_A_SCALE); }

    public boolean getMoreComponents() { return BitUtil.isSetUInt16(getFlags(), FLAGS_MORE_COMPONENTS); }

    public boolean getWeHaveXAndYScale() { return BitUtil.isSetUInt16(getFlags(), FLAGS_WE_HAVE_X_AND_Y_SCALE); }

    public boolean getWeHaveATwoByTwo() { return BitUtil.isSetUInt16(getFlags(), FLAGS_WE_HAVE_A_TWO_BY_TWO); }

    public boolean getWeHaveInstructions() { return BitUtil.isSetUInt16(getFlags(), FLAGS_WE_HAVE_INSTRUCTIONS); }

    public boolean getRoundXYToGrid() { return BitUtil.isSetUInt16(getFlags(), FLAGS_ROUND_XY_TO_GRID); }

    public boolean getUseMyMetrics() { return BitUtil.isSetUInt16(getFlags(), FLAGS_USE_MY_METRICS); }

    public boolean getOverlapCompound() { return BitUtil.isSetUInt16(getFlags(), FLAGS_OVERLAP_COMPOUND); }

    public boolean getScaledComponentOffset() { return BitUtil.isSetUInt16(getFlags(), FLAGS_SCALED_COMPONENT_OFFSET); }

    public boolean getUnscaledComponentOffset() { return BitUtil.isSetUInt16(getFlags(), FLAGS_UNSCALED_COMPONENT_OFFSET); }

    public boolean getActualScaledComponentOffset()
    {
        return !getUnscaledComponentOffset() && getScaledComponentOffset();
    }

    public static final /*ushort*/short FLAGS_ARG_1_AND_2_ARE_WORDS = (short) 0x0001;
    public static final /*ushort*/short FLAGS_ARGS_ARE_XY_VALUES = (short) 0x0002;
    public static final /*ushort*/short FLAGS_ROUND_XY_TO_GRID = (short) 0x0004;
    public static final /*ushort*/short FLAGS_WE_HAVE_A_SCALE = (short) 0x0008;
    public static final /*ushort*/short FLAGS_MORE_COMPONENTS = (short) 0x0020;
    public static final /*ushort*/short FLAGS_WE_HAVE_X_AND_Y_SCALE = (short) 0x0040;
    public static final /*ushort*/short FLAGS_WE_HAVE_A_TWO_BY_TWO = (short) 0x0080;
    public static final /*ushort*/short FLAGS_WE_HAVE_INSTRUCTIONS = (short) 0x0100;
    public static final /*ushort*/short FLAGS_USE_MY_METRICS = (short) 0x0200;
    public static final /*ushort*/short FLAGS_OVERLAP_COMPOUND = (short) 0x0400;
    public static final /*ushort*/short FLAGS_SCALED_COMPONENT_OFFSET = (short) 0x0800;
    public static final /*ushort*/short FLAGS_UNSCALED_COMPONENT_OFFSET = (short) 0x1000;
}

