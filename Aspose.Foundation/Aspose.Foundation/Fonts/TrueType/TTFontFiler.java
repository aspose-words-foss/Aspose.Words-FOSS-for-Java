// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Responsible for reading and subsetting of true type font files.
/// See http://www.microsoft.com/typography/otspec/otff.htm for more info.
/// </summary>
public /*static*/ class TTFontFiler
{
	/* Simulation of static class by using private constructor */
	private TTFontFiler()
	{}

    /// <summary>
    /// Extracts specified font from a TTC collection and writes it to <paramref name="dstStream"/>.
    /// </summary>
    static void extractFontFromTtc(TTFont font, Stream dstStream) throws Exception
    {
        Stream srcStream = font.getData().openStream();
        try /*JAVA: was using*/
        {
            OpenTypeReader reader = OpenTypeReader.create(srcStream, font.getPhysicalData());
            reader.readHeader();

            // Copy all font tables from TTC file to builder.
            SfntBuilder builder = new SfntBuilder(reader.getSfntReader().getOffsetTable().getVersion());
            for (String tag : reader.getSfntReader().getTableRecordEntries().getKeys())
                builder.addTable(tag, reader.getSfntReader().readTable(tag));

            builder.writeFileToStream(dstStream);
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Joins subsets embedded to MW document.
    /// </summary>
    /// <remarks>
    /// This method relies on some features of MW font embedding. So it may be required to review this method
    /// if use in other cases.
    /// </remarks>
    public static TTFont joinEmbeddedSubsets(ArrayList<TTFont> toJoin)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (toJoin == null || toJoin.size() == 0)
            	return null;

        	if (toJoin.size() == 1)
            	return toJoin.get(0);

        	// Note: In most cases MW only clears the unneeded glyphs data. But in rare cases MW also
        	// clears cmap, hmtx so we should join them too.
        	// Note: At the moment cmap writing code is not optimal and large cmap's could cause overflow. Try to
        	// use already encoded cmap as a base if nothing is joined into it.
        	// Note: AW at the moment only clears the glyph data when subsetting and embedding to DOCX. In some older
        	// versions AW used compact subsets but joining such subsets is not supported here.
        	IFontBuilder builder = new SfntBuilder();
        	FontHeader joinedHead;
        	FontMetrics joinedOs2;
        	HorizontalHeader joinedHhea;
        	MaximumProfile joinedMaxp;
        	Cmap joinedCmap;
        	ArrayList<HorizontalMetric> joinedMetrics;
        	short[] joinedLsb;
        	IntToObjDictionary<byte[]> glyphData;
        	String[] tagsToJoin = new String[]
        	{
            	OpenTypeTableTag.HEAD, OpenTypeTableTag.OS_2, OpenTypeTableTag.HHEA, OpenTypeTableTag.MAXP,
            	OpenTypeTableTag.CMAP, OpenTypeTableTag.HMTX, OpenTypeTableTag.GLYF, OpenTypeTableTag.LOCA
        	};
        	boolean useUnparsedCmap = true;
        	byte[] unparsedCmapData;
        	Stream baseSubsetData = toJoin.get(0).getData().openStream();
        	try /*JAVA: was using*/
        	{
            	SfntReader reader = new SfntReader(baseSubsetData);
            	reader.readHeader();
            	builder.setSfntVersion(reader.getOffsetTable().getVersion());
            	reader.seekToTable(OpenTypeTableTag.MAXP);
            	joinedMaxp = new MaximumProfile(reader.getBinaryReader());
            	reader.seekToTable(OpenTypeTableTag.HEAD);
            	joinedHead = FontHeader.read(reader.getBinaryReader());
            	reader.seekToTable(OpenTypeTableTag.OS_2);
            	joinedOs2 = new FontMetrics(reader.getBinaryReader());
            	reader.seekToTable(OpenTypeTableTag.HHEA);
            	joinedHhea = new HorizontalHeader(reader.getBinaryReader());

            	unparsedCmapData = reader.readTable(OpenTypeTableTag.CMAP);
            	reader.seekToTable(OpenTypeTableTag.CMAP);
            	joinedCmap = Cmap.read(reader.getBinaryReader());

            	reader.seekToTable(OpenTypeTableTag.HMTX);
            	HorizontalMetrics hmtx = HorizontalMetrics.read(
                	reader.getBinaryReader(),
                	(joinedHhea.NumberOfHMetrics & 0xFFFF),
                	(joinedMaxp.NumGlyphs & 0xFFFF));
            	joinedMetrics = msArrayList.ctor(hmtx.getWidthPairs());
            	joinedLsb = hmtx.getLeftSideBearings();
            	glyphData = new IntToObjDictionary<byte[]>((joinedMaxp.NumGlyphs & 0xFFFF));
            	joinGlyf(reader, glyphData);

            	for (SfntTableRecordEntry entry : reader.getTableRecordEntries().getValues())
                	if (!containsTag(tagsToJoin, entry.Tag))
                    	builder.addTable(entry.Tag, reader.readTable(entry.Tag));
        	}
        	finally { if (baseSubsetData != null) baseSubsetData.close(); }

        	for (int subsetNumber = 1; subsetNumber < toJoin.size(); subsetNumber++)
        	{
            	TTFont subset = toJoin.get(subsetNumber);
            	Stream stream = subset.getData().openStream();
            	try /*JAVA: was using*/
            	{
                	SfntReader reader = new SfntReader(stream);
                	reader.readHeader();
                	joinOs2(joinedOs2, reader);
                	joinMaxp(joinedMaxp, reader);
                	joinGlyf(reader, glyphData);

                	// Note: Sometimes MW writes full cmap into one subset (mostly regular style) and truncated cmaps
                	// into other subsets. Take the largest cmap as a base cmap and join others into it.
                	reader.seekToTable(OpenTypeTableTag.CMAP);
                	Cmap newCmap = Cmap.read(reader.getBinaryReader());
                	if (newCmap.CharMap.getCount() > joinedCmap.CharMap.getCount())
                	{
                    	unparsedCmapData = reader.readTable(OpenTypeTableTag.CMAP);
                    	Cmap temp = joinedCmap;
                    	joinedCmap = newCmap;
                    	newCmap = temp;
                    	useUnparsedCmap = true;
                	}

                	if (joinCmap(newCmap, joinedCmap))
                    	useUnparsedCmap = false;

                	reader.seekToTable(OpenTypeTableTag.HHEA);
                	HorizontalHeader hhea = new HorizontalHeader(reader.getBinaryReader());
                	reader.seekToTable(OpenTypeTableTag.HMTX);
                	HorizontalMetrics hmtx = HorizontalMetrics.read(
                    	reader.getBinaryReader(),
                    	(hhea.NumberOfHMetrics & 0xFFFF),
                    	(joinedMaxp.NumGlyphs & 0xFFFF));
                	if ((hhea.NumberOfHMetrics & 0xFFFF) > joinedMetrics.size())
                	{
                    	for (int i = joinedMetrics.size(); i < (hhea.NumberOfHMetrics & 0xFFFF); i++)
                        	msArrayList.add(joinedMetrics, hmtx.getWidthPairs()[i]);
                    	joinedLsb = hmtx.getLeftSideBearings();
                	}

                	for (int i = 0; i < hmtx.getWidthPairs().length; i++)
                    	joinedMetrics.set(i, chooseHMetric(joinedMetrics.get(i), hmtx.getWidthPairs()[i]));
            	}
            	finally { if (stream != null) stream.close(); }
        	}

        	GlyfTableBuilder glyfBuilder = new GlyfTableBuilder(joinedHead.isLocaShort());
        	for (int i = 0; i < (joinedMaxp.NumGlyphs & 0xFFFF); i++)
        	{
            	if (!glyphData.containsKey(i))
            	{
                	glyfBuilder.writeEmptyGlyph();
                	continue;
            	}

            	glyfBuilder.writeGlyph(glyphData.get(i));
        	}

        	glyfBuilder.endTable();
        	glyfBuilder.getLocaTable().chooseFormat();
        	glyfBuilder.buildLocaStream();

        	joinedHead.isLocaShort(glyfBuilder.getLocaTable().isLocaShort());
        	joinedHhea.NumberOfHMetrics = (/*ushort*/short)joinedMetrics.size();
        	HorizontalMetrics joinedHmtx = new HorizontalMetrics(msArrayList.toArray(joinedMetrics, new HorizontalMetric[0]), joinedLsb);
        	builder.addTable(OpenTypeTableTag.HEAD, joinedHead.toByteArray());
        	builder.addTable(OpenTypeTableTag.OS_2, joinedOs2.toByteArray());
        	builder.addTable(OpenTypeTableTag.MAXP, joinedMaxp.toByteArray());
        	builder.addTable(OpenTypeTableTag.GLYF, glyfBuilder.getGlyfStream().toArray());
        	builder.addTable(OpenTypeTableTag.LOCA, glyfBuilder.getLocaStream().toArray());
        	builder.addTable(OpenTypeTableTag.HHEA, joinedHhea.toByteArray());
        	builder.addTable(
            	OpenTypeTableTag.CMAP,
            	useUnparsedCmap
                	? unparsedCmapData
                	: joinedCmap.toByteArray());
        	builder.addTable(OpenTypeTableTag.HMTX, joinedHmtx.toByteArray());

        	byte[] joinedData = builder.writeFileToByteArray();
        	boolean isEmbedded = toJoin.get(0).isEmbedded();
        	return TTFontBuilder.read(new PhysicalFontData(new MemoryFontData(joinedData, null, isEmbedded)));
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static boolean joinCmap(Cmap newCmap, Cmap joinedCmap)
    {
        boolean newEntriesAdded = false;
        for (int i = 0; i < newCmap.CharMap.getCount(); i++)
        {
            int charCode = newCmap.CharMap.getKey(i);
            int glyphIndex = newCmap.CharMap.getByIndex(i);
            if (!joinedCmap.CharMap.containsKey(charCode))
            {
                joinedCmap.CharMap.set(charCode, glyphIndex);
                newEntriesAdded = true;
            }
        }

        return newEntriesAdded;
    }

    private static void joinGlyf(SfntReader reader, IntToObjDictionary<byte[]> glyphData) throws Exception
    {
        GlyfTableReader glyfReader = initGlyfTableReader(reader);
        for (int i = 0; i < glyfReader.getGlyphCount(); i++)
            if (glyfReader.getGlyphLength(i) > 0 && !glyphData.containsKey(i))
                glyphData.set(i, glyfReader.getGlyphBytes(i));
    }

    private static boolean containsTag(String[] tagsToWrite, String tag)
    {
        for (String tagToWrite : tagsToWrite)
            if (msString.equals(tag, tagToWrite, StringComparison.ORDINAL))
                return true;
        return false;
    }

    private static void joinOs2(FontMetrics joined, SfntReader reader) throws Exception
    {
        reader.seekToTable(OpenTypeTableTag.OS_2);
        FontMetrics os2 = new FontMetrics(reader.getBinaryReader());
        joined.usLastCharIndex = ushortMax(joined.usLastCharIndex, os2.usLastCharIndex);
    }

    private static void joinMaxp(MaximumProfile joined, SfntReader reader) throws Exception
    {
        reader.seekToTable(OpenTypeTableTag.MAXP);
        MaximumProfile maxp = new MaximumProfile(reader.getBinaryReader());
        joined.MaxContours = ushortMax(joined.MaxContours, maxp.MaxContours);
        joined.MaxPoints = ushortMax(joined.MaxPoints, maxp.MaxPoints);
        joined.MaxStorage = ushortMax(joined.MaxStorage, maxp.MaxStorage);
        joined.MaxZones = ushortMax(joined.MaxZones, maxp.MaxZones);
        joined.MaxComponentDepth = ushortMax(joined.MaxComponentDepth, maxp.MaxComponentDepth);
        joined.MaxComponentElements = ushortMax(joined.MaxComponentElements, maxp.MaxComponentElements);
        joined.MaxCompositeContours = ushortMax(joined.MaxCompositeContours, maxp.MaxCompositeContours);
        joined.MaxCompositePoints = ushortMax(joined.MaxCompositePoints, maxp.MaxCompositePoints);
        joined.MaxFunctionDefs = ushortMax(joined.MaxFunctionDefs, maxp.MaxFunctionDefs);
        joined.MaxInstructionDefs = ushortMax(joined.MaxInstructionDefs, maxp.MaxInstructionDefs);
        joined.MaxStackElements = ushortMax(joined.MaxStackElements, maxp.MaxStackElements);
        joined.MaxTwilightPoints = ushortMax(joined.MaxTwilightPoints, maxp.MaxTwilightPoints);
        joined.MaxSizeOfInstructions = ushortMax(joined.MaxSizeOfInstructions, maxp.MaxSizeOfInstructions);
    }

    private static /*ushort*/short ushortMax(/*ushort*/short val1, /*ushort*/short val2)
    {
        // Java doesn't have unsigned short value and need some conversion to compare and save max value
        return (/*ushort*/short)Math.max((int)(val1 & 0xFFFF), (int)(val2 & 0xFFFF));
    }

    private static HorizontalMetric chooseHMetric(HorizontalMetric m1, HorizontalMetric m2)
    {
        // Note: Ideally, we should check the presence of the glyph in cmap or glyph table. But probably
        // it is OK just to check if advance is not zero.
        if (m2.getAdvanceWidth() != 0 || m2.getLeftSideBearing() != 0)
            return m2;
        return m1;
    }

    private static GlyfTableReader initGlyfTableReader(SfntReader reader) throws Exception
    {
        reader.seekToTable(OpenTypeTableTag.HEAD);
        FontHeader fontHeader = FontHeader.read(reader.getBinaryReader());

        SfntTableRecordEntry loca = reader.getTableRecordEntries().get(OpenTypeTableTag.LOCA);
        SfntTableRecordEntry glyf = reader.getTableRecordEntries().get(OpenTypeTableTag.GLYF);
        return new GlyfTableReader(reader.getBinaryReader(), loca, glyf, fontHeader.isLocaShort());
    }

}

