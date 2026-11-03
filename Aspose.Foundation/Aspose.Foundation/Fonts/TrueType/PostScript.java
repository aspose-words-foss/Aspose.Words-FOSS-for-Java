// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// http://www.microsoft.com/typography/otspec/post.htm
///
/// Corresponds to the "post" table in a TTF file. Contains information about PostScript glyph names.
/// </summary>
class PostScript extends TTTable
{
    // --- common fields
    int Version;
    int ItalicAngle;
    short UnderlinePosition;
    short UnderlineThickness;
    int IsFixedPitch;
    int MinMemType42;
    int MaxMemType42;
    int MinMemType1;
    int MaxMemType1;

    /// <summary>
    /// No glyph names stored here.
    /// </summary>
    static final int VERSION_10 = 0x00010000;
    /// <summary>
    /// This is the version used for fonts on Windows. Names are stored as per the fields below.
    /// </summary>
    static final int VERSION_20 = 0x00020000;
    /// <summary>
    /// The spec says this format was deprecated. We don't support it.
    /// </summary>
    static final int VERSION_25 = 0x00025000;
    /// <summary>
    /// No glyph names stored here.
    /// </summary>
    static final int VERSION_30 = 0x00030000;

    // --- fields for Version20
    /// <summary>
    /// Number of glyphs (this should be the same as numGlyphs in 'maxp' table).
    /// ushort in file.
    /// </summary>
    int NumberOfGlyphs;
    /// <summary>
    /// Each item in this array corresponds to a glyph. E.g. item 0 is for glyph index 0.
    /// The value in this array is an index into the names table.
    /// Null for other versions.
    ///
    /// The glyph name array maps the glyphs in this font to name index. If the name index is
    /// between 0 and 257, treat the name index as a glyph index in the Macintosh standard order.
    /// If the name index is between 258 and 32767, then subtract 258 and use that to index into
    /// the list of Pascal strings at the end of the table. Thus a given font may map some of its
    /// glyphs to the standard glyph names, and some to its own names.
    /// Index numbers 32768 through 65535 are reserved for future use. If you do not want to associate
    /// a PostScript name with a particular glyph, use index number 0 which points the name .notdef.
    ///
    /// ushort[] in file.
    /// </summary>
    int[] GlyphNameIndexes;
    /// <summary>
    /// Glyph names.
    /// Null for other versions.
    /// </summary>
    ArrayList<String> Names;


    PostScript(BigEndianBinaryReader reader, long tableLength) throws Exception
    {
        long tableStartPosition = reader.getBaseStream().getPosition();
        Version = reader.readInt32();   // uint in file
        ItalicAngle = reader.readInt32();   // uint in file
        UnderlinePosition = reader.readInt16();
        UnderlineThickness = reader.readInt16();
        IsFixedPitch = reader.readInt32();  // uint in file
        MinMemType42 = reader.readInt32();  // uint in file
        MaxMemType42 = reader.readInt32();  // uint in file
        MinMemType1 = reader.readInt32();   // uint in file
        MaxMemType1 = reader.readInt32();   // uint in file

        switch (Version)
        {
            case VERSION_20:
            {
                NumberOfGlyphs = (reader.readUInt16() & 0xFFFF);

                GlyphNameIndexes = new int[NumberOfGlyphs];
                int maxGlyphNameIndex = 0;
                for (int i = 0; i < GlyphNameIndexes.length; i++)
                {
                    /*ushort*/short index = reader.readUInt16();
                    GlyphNameIndexes[i] = (index & 0xFFFF);

                    // According to the spec, indexes over 32767 are reserved.
                    if ((index & 0xFFFF) <= 32767)
                        maxGlyphNameIndex = Math.max((index & 0xFFFF), maxGlyphNameIndex);
                }

                // RK First 258 indexes are standard system glyph names that are not stored on file.
                // Therefore need to subtract 258 to use as an index into the name array.
                // Add one to turn max index into a count of items in the array.
                int nameCount = maxGlyphNameIndex - STANDARD_MACINTOSH_GLYPH_COUNT + 1;
                // RESILIENCY 15704 - nameCount cannot be negative. So set it to zero if it is negative.
                nameCount = nameCount < 0 ? 0 : nameCount;
                Names = new ArrayList<String>(nameCount);

                int nameIndex = STANDARD_MACINTOSH_GLYPH_COUNT;
                while (true)
                {
                    // There is no need to read names that are not referenced even if they are present in the table.
                    if (nameIndex > maxGlyphNameIndex)
                        break;

                    // WORDSNET-10428 Stop reading when we reach the end of the table.
                    if (reader.getBaseStream().getPosition() >= tableStartPosition + tableLength)
                        break;

                    Names.add(readPascalString(reader));
                    nameIndex++;
                }

                break;
            }
            case VERSION_10:
            case VERSION_30:
                // No PostScript names are provided in this file.
                break;
            default:
                // WORDSNET-26737 Custom font with version 2.8. For now just change the version to 3.0 (which is the
                // simplest version). But maybe it will be better to ignore the parsed values and set some
                // default values. Other option is to ignore the table at all and not include it into subsets,
                // but it seems that this table is required.
                Version = VERSION_30;
                break;
        }
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeInt32(Version); //uint in file
        writer.writeInt32(ItalicAngle); // uint in file
        writer.writeInt16(UnderlinePosition);
        writer.writeInt16(UnderlineThickness);
        writer.writeInt32(IsFixedPitch);    // uint in file
        writer.writeInt32(MinMemType42);    // uint in file
        writer.writeInt32(MaxMemType42);    // uint in file
        writer.writeInt32(MinMemType1);     // uint in file
        writer.writeInt32(MaxMemType1);     // uint in file

        switch (Version)
        {
            case VERSION_20:
            {
                writer.writeInt16(NumberOfGlyphs);

                for (int index : GlyphNameIndexes)
                    writer.writeInt16(index);

                for (int i = 0; i < Names.size(); i++)
                    writePascalString(Names.get(i), writer);

                break;
            }
            case VERSION_10:
            case VERSION_30:
                // No PostScript names are provided in this file.
                break;
            default:
                throw new IllegalStateException("Unexpected PostScript table version.");
        }
    }

    /// <summary>
    /// Updates the table so it is valid only for the specified subset of glyphs.
    /// </summary>
    /// <param name="usedGlyphs">The glyphs to keep in this font. The key is the old glyph index.
    /// The value is the new glyph index.</param>
    void subset(SortedIntegerListGeneric<Integer> usedGlyphs)
    {
        // It is easier for us to set these all to zeroes so the print driver figures it out for itself.
        MinMemType42 = 0;
        MaxMemType42 = 0;
        MinMemType1 = 0;
        MaxMemType1 = 0;

        switch (Version)
        {
            case VERSION_20:
                subsetVersion20Fields(usedGlyphs);
                break;
            case VERSION_10:
            case VERSION_30:
                // No PostScript names are provided in this file.
                break;
            default:
                throw new IllegalStateException("Unexpected PostScript table version.");
        }
    }

    private void subsetVersion20Fields(SortedIntegerListGeneric<Integer> usedGlyphs)
    {
        int[] newGlyphNameIndexes = new int[usedGlyphs.getCount()];
        ArrayList<String> newNames = new ArrayList<String>();

        // This loop copies only used glyph names into new arrays.
        for (int i = 0; i < usedGlyphs.getCount(); i++)
        {
            int oldGlyphIndex = usedGlyphs.getKey(i);
            int newGlyphIndex = usedGlyphs.getByIndex(i);

            // Get the glyph name for the old glyph index.
            // WORDSNET-21910 The NumberOfGlyphs in post table is less than actual number of glyphs in the font
            // for subsets embedded into the customer document.
            int oldNameIndex = oldGlyphIndex < NumberOfGlyphs ? GlyphNameIndexes[oldGlyphIndex] : 0;

            if ((oldNameIndex < STANDARD_MACINTOSH_GLYPH_COUNT) || (oldNameIndex > 32767))
            {
                // System or reserved value. Names for these are not stored in the file.
                newGlyphNameIndexes[newGlyphIndex] = (((/*ushort*/short)oldNameIndex) & 0xFFFF);
                continue;
            }

            // For values 258..32767 need to subtract 258 and then use as an index into the name array.
            int oldNameIndexNormalized = oldNameIndex - STANDARD_MACINTOSH_GLYPH_COUNT;

            // WORDSNET-10428 If there is no name for the glyph in the file then set it to 0.
            if (oldNameIndexNormalized >= Names.size())
            {
                newGlyphNameIndexes[newGlyphIndex] = 0;
                continue;
            }

            String name = Names.get(oldNameIndexNormalized);

            // Store the name and its index in the new array.
            msArrayList.add(newNames, name);
            int newNameIndex = newNames.size() - 1;
            newGlyphNameIndexes[newGlyphIndex] = (((/*ushort*/short)(newNameIndex + STANDARD_MACINTOSH_GLYPH_COUNT)) & 0xFFFF);
        }

        NumberOfGlyphs = (((/*ushort*/short)usedGlyphs.getCount()) & 0xFFFF);
        GlyphNameIndexes = newGlyphNameIndexes;
        Names = newNames;
    }

    private static final int STANDARD_MACINTOSH_GLYPH_COUNT = 258;

    private static String readPascalString(BigEndianBinaryReader reader) throws Exception
    {
        int charCount = (reader.readByte() & 0xFF);

        if (reader.getBaseStream().getPosition() + charCount > reader.getBaseStream().getLength())
            charCount = (int)(reader.getBaseStream().getLength() - reader.getBaseStream().getPosition());

        byte[] data = reader.readBytes(charCount);

        Encoding encoding = Encoding.getASCII();
        return encoding.getString(data);
    }

    private static void writePascalString(String s, BigEndianBinaryWriter writer) throws Exception
    {
        if (s.length() > 255)
            throw new IllegalStateException("The PostScript glyph name is too long.");

        Encoding encoding = Encoding.getASCII();
        byte[] data = encoding.getBytes(s);

        writer.writeByte((byte)data.length);
        writer.writeBytes(data, 0, data.length);
    }
}

