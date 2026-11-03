// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


class OpenTypeReader
{
    public OpenTypeReader(Stream stream)
    {
        mSfntReader = new SfntReader(stream);
    }

    public static OpenTypeReader create(Stream stream, PhysicalFontData data)
    {
        if (data.isTtc())
            return new TtcOpenTypeReader(stream, (TtcPhysicalFontData)data);
        return new OpenTypeReader(stream);
    }

    public static OpenTypeReader create(Stream stream, IFontData fontData, String fontName) throws Exception
    {
        TtcOpenTypeReader ttcReader = new TtcOpenTypeReader(stream, new TtcPhysicalFontData(fontData, -1, fontName));
        boolean isTtc = ttcReader.tryReadHeader();
        stream.setPosition(0);

        return isTtc
            ? ttcReader
            : new OpenTypeReader(stream);
    }

    public void readHeader() throws Exception
    {
        if (!tryReadHeader())
            throw new IllegalStateException(getInvalidDataMessage());
    }

    public /*virtual*/ boolean tryReadHeader() throws Exception
    {
        if (mIsInitialized)
            return true;

        mIsInitialized = true;
        if (!getSfntReader().tryReadHeader())
            return false;

        // Check TrueType tables.
        if (containsTable(OpenTypeTableTag.GLYF) && containsTable(OpenTypeTableTag.LOCA))
            return true;

        // Check CFF tables.
        if (containsTable(OpenTypeTableTag.CFF))
            return true;

        // There also may be bitmap fonts which we do not support for now.
        return false;
    }

    public /*virtual*/ PhysicalFontData getUpdatedFontData()
    {
        return null;
    }

    public boolean containsTable(String tag)
    {
        return getSfntReader().getTableRecordEntries().containsKey(tag);
    }

    public /*virtual*/ byte[] getTableData(String tag) throws Exception
    {
        return containsTable(tag)
            ? getSfntReader().readTable(tag)
            : null;
    }

    protected /*virtual*/ FontMetrics readOs2() throws Exception
    {
        getSfntReader().seekToTable(OpenTypeTableTag.OS_2);
        return new FontMetrics(getBinaryReader());
    }

    protected /*virtual*/ VerticalHeader readVhea() throws Exception
    {
        if (!containsTable(OpenTypeTableTag.VHEA))
            return null;
        getSfntReader().seekToTable(OpenTypeTableTag.VHEA);
        return new VerticalHeader(getBinaryReader());
    }

    protected /*virtual*/ HorizontalHeader readHhea() throws Exception
    {
        getSfntReader().seekToTable(OpenTypeTableTag.HHEA);
        return new HorizontalHeader(getBinaryReader());
    }

    protected /*virtual*/ PostScript readPost() throws Exception
    {
        getSfntReader().seekToTable(OpenTypeTableTag.POST);
        return new PostScript(getBinaryReader(), (getSfntReader().getTableRecordEntries().get(OpenTypeTableTag.POST).Length & 0xFFFFFFFFL));
    }

    protected /*virtual*/ CvtTable readCvt() throws Exception
    {
        if (!containsTable(OpenTypeTableTag.CVT))
            return null;
        getSfntReader().seekToTable(OpenTypeTableTag.CVT);
        return CvtTable.read(getBinaryReader(), (getSfntReader().getTableRecordEntries().get(OpenTypeTableTag.CVT).Length & 0xFFFFFFFFL));
    }

    protected /*virtual*/ HorizontalMetrics readHmtx() throws Exception
    {
        int numberOfHMetrics = (getHhea().NumberOfHMetrics & 0xFFFF);
        int numGlyphs = (getMaxp().NumGlyphs & 0xFFFF);
        getSfntReader().seekToTable(OpenTypeTableTag.HMTX);
        return HorizontalMetrics.read(getBinaryReader(), numberOfHMetrics, numGlyphs);
    }

    protected /*virtual*/ VerticalMetrics readVmtx() throws Exception
    {
        if (!containsTable(OpenTypeTableTag.VHEA) || !containsTable(OpenTypeTableTag.VMTX))
            return null;

        int numberOfVMetrics = (getVhea().NumberOfVMetrics & 0xFFFF);
        int numGlyphs = (getMaxp().NumGlyphs & 0xFFFF);

        getSfntReader().seekToTable(OpenTypeTableTag.VMTX);
        return new VerticalMetrics(getBinaryReader(), numberOfVMetrics, numGlyphs);
    }

    public FontHeader getHead() throws Exception
    {
        if (mHead == null)
        {
            getSfntReader().seekToTable(OpenTypeTableTag.HEAD);
            mHead = FontHeader.read(getBinaryReader());
        }

        return mHead;
    }

    public HorizontalHeader getHhea() throws Exception { return (mHhea != null ? mHhea : (mHhea = readHhea())); }

    public MaximumProfile getMaxp() throws Exception
    {
        if (mMaxp == null)
        {
            getSfntReader().seekToTable(OpenTypeTableTag.MAXP);
            mMaxp = new MaximumProfile(getBinaryReader());
        }

        return mMaxp;
    }

    public TTFontNames getName() throws Exception
    {
        if (mName == null)
        {
            getSfntReader().seekToTable(OpenTypeTableTag.NAME);
            mName = TTFontNames.read(getBinaryReader());
        }

        return mName;
    }

    public FontMetrics getOs2() throws Exception { return (mOs2 != null ? mOs2 : (mOs2 = readOs2())); }

    public Cmap getCmap() throws Exception
    {
        if (mCmap == null)
        {
            getSfntReader().seekToTable(OpenTypeTableTag.CMAP);
            mCmap = Cmap.read(getBinaryReader());
        }

        return mCmap;
    }

    public PostScript getPost() throws Exception { return (mPost != null ? mPost : (mPost = readPost())); }

    public CvtTable getCvt() throws Exception { return (mCvt != null ? mCvt : (mCvt = readCvt())); }

    public HorizontalMetrics getHmtx() throws Exception { return (mHmtx != null ? mHmtx : (mHmtx = readHmtx())); }

    public VerticalHeader getVhea() throws Exception { return (mVhea != null ? mVhea : (mVhea = readVhea())); }

    public VerticalMetrics getVmtx() throws Exception { return (mVmtx != null ? mVmtx : (mVmtx = readVmtx())); }

    public ColrTable getColr() throws Exception
    {
        if (mColr == null && containsTable(OpenTypeTableTag.COLR))
        {
            getSfntReader().seekToTable(OpenTypeTableTag.COLR);
            mColr = new ColrTable(getBinaryReader());
        }

        return mColr;
    }

    public CpalTable getCpal() throws Exception
    {
        if (mCpal == null && containsTable(OpenTypeTableTag.CPAL))
        {
            getSfntReader().seekToTable(OpenTypeTableTag.CPAL);
            mCpal = new CpalTable(getBinaryReader());
        }

        return mCpal;
    }

    public SfntReader getSfntReader() { return mSfntReader; };

    private SfntReader mSfntReader;
    public BigEndianBinaryReader getBinaryReader() { return getSfntReader().getBinaryReader(); }
    protected Stream getBaseStream() { return getSfntReader().getBaseStream(); }
    protected /*virtual*/ String getInvalidDataMessage() { return "The file is not recognized as a valid font."; }

    private boolean mIsInitialized;
    private FontHeader mHead;
    private HorizontalHeader mHhea;
    private VerticalHeader mVhea;
    private MaximumProfile mMaxp;
    private TTFontNames mName;
    private FontMetrics mOs2;
    private HorizontalMetrics mHmtx;
    private VerticalMetrics mVmtx;
    private Cmap mCmap;
    private PostScript mPost;
    private CvtTable mCvt;
    private ColrTable mColr;
    private CpalTable mCpal;
}

