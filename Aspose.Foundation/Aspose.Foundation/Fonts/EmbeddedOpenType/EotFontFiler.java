// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.BitConverter;


/// <summary>
/// Responsible for reading of Embedded Open Type (EOT) font files.
/// See http://www.w3.org/Submission/EOT/ for more info.
/// </summary>
public /*static*/ class EotFontFiler
{
	/* Simulation of static class by using private constructor */
	private EotFontFiler()
	{}

    /// <summary>
    /// Compresses OpenType font to EOT format.
    /// </summary>
    public static byte[] compressOpenTypeToEot(byte[] openTypeFile, String fontName, boolean isSubsetted) throws Exception
    {
        MemoryStream outStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            // MS Word uses MTX encoding for EOT files when font size is not greater than 4 Mb.
            // Otherwise it performs XOR encoding only.
            int fontDataLength = openTypeFile.length;
            byte[] fontData;
            /*uint*/int processingFlags;
            if (fontDataLength < FontUtil.MTX_ENCODE_SIZE_LIMIT)
            {
                fontData = MtxCoder.encode(openTypeFile);
                processingFlags = COMPRESSED_FLAG;
            }
            else
            {
                fontData = new byte[fontDataLength];
                System.arraycopy((openTypeFile), 0, (fontData), 0, (fontDataLength));
                xorFontData(fontData);
                processingFlags = XOR_ENCRYPTED_FLAG;
            }

            if (fontData == null)
                return null;

            if (isSubsetted)
                processingFlags |= SUBSET_FLAG;

            BinaryWriter writer = new BinaryWriter(outStream);

            /*uint*/int headerSize;
            MemoryStream fontStream = new MemoryStream(openTypeFile);
            try /*JAVA: was using*/
            {
                OpenTypeReader openTypeReader = OpenTypeReader.create(fontStream, new MemoryFontData(openTypeFile), fontName);
                openTypeReader.readHeader();
                // MS Word seem uses EOT format version 2.2 for a moment.
                // The RootString contains URLs from which the embedded font
                // object may be referenced and for a moment it is empty.
                headerSize = writeEotHeaderV22(writer, openTypeReader, new byte[] { });
            }
            finally { if (fontStream != null) fontStream.close(); }

            // Write version specific values to a header.
            long savedPosition = writer.getBaseStream().getPosition();
            writer.getBaseStream().setPosition(0);

            // EOT Size
            /*uint*/int eotSize = (/*uint*/int)((headerSize & 0xFFFFFFFFL) + fontData.length);
            writer.writeUInt32(eotSize);

            // Font data Size.
            writer.writeUInt32((/*uint*/int)fontData.length);

            // EOT format version.
            writer.writeUInt32((/*uint*/int)EotVersion.VERSION_22);

            // Processing flags.
            writer.writeUInt32(processingFlags);
            writer.getBaseStream().setPosition(savedPosition);

            // Write font data.
            writer.writeBytes(fontData);

            return outStream.toArray();
        }
        finally { if (outStream != null) outStream.close(); }
    }

    private static /*uint*/int writeEotHeaderV22(BinaryWriter writer, OpenTypeReader reader, byte[] rootString) throws Exception
    {
        writeEotHeaderV21(writer, reader, rootString);

        writer.writeUInt32(getRootStringChecksum(rootString));

        // Codepage value needed for EUDC font support. Default Windows ANSI for a moment.
        writer.writeUInt32((/*uint*/int)CodePage.WINDOWS_LATIN_1_CODE_PAGE);
        writer.writeUInt16(PADDING);

        // Number of bytes used by the Signature array. Currently reserved and should be set to 0x0000.
        writer.writeUInt16((/*ushort*/short)0x0000);

        // Processing flags for the EUDC font. Zero for a moment.
        writer.writeUInt32((/*uint*/int)0x0);

        // Number of bytes used for the EUDC font data. Zero for a moment.
        writer.writeUInt32((/*uint*/int)0x0);

        return (/*uint*/int)writer.getBaseStream().getPosition();
    }

    private static /*uint*/int getRootStringChecksum(byte[] rootString)
    {
        /*uint*/int checksum = 0;
        for (byte t : rootString)
            checksum = (/*uint*/int)((checksum & 0xFFFFFFFFL) + (t & 0xFF));

        // Value taken from the EOT spec - 4.3.2 RootString Checksum Calculation.
        final /*uint*/int ROOT_STRING_XOR_KEY = 0x50475342;
        return (/*uint*/int)((checksum & 0xFFFFFFFFL) ^ (ROOT_STRING_XOR_KEY & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Calculates root string checksum according to EOT spec.
    /// </summary>
    private static void writeEotHeaderV21(BinaryWriter writer, OpenTypeReader reader, byte[] rootString) throws Exception
    {
        writeEotHeaderV10(writer, reader);

        writer.writeUInt16(PADDING);
        writer.writeUInt16((/*ushort*/short)rootString.length);
        writer.writeBytes(rootString);
    }

    private static void writeEotHeaderV10(BinaryWriter writer, OpenTypeReader reader) throws Exception
    {
        // Family name should be ended with '\0' character.
        String familyNameStr = msString.format("{0}{1}", reader.getName().getFamilyName(), '\u0000');
        byte[] familyName = Encoding.getUnicode().getBytes(familyNameStr);
        byte[] styleName = getStyleUnicodeName(reader.getOs2().getStyle());
        byte[] versionName = Encoding.getUnicode().getBytes(reader.getName().getVersionString());
        byte[] fullName = Encoding.getUnicode().getBytes(reader.getName().getFullFontName());

        // We are not writing EOT size, font data size and processing flags upon writing of a header.
        // We will write it later along with EOT font data.
        // EOT size.
        writer.writeUInt32((/*uint*/int)0x0);

        // Font data size.
        writer.writeUInt32((/*uint*/int)0x0);

        // EOT format version.
        writer.writeUInt32((/*uint*/int)EotVersion.VERSION_10);

        // Processing flags.
        writer.writeUInt32((/*uint*/int)0x0);
        writer.writeBytes(reader.getOs2().panose.getValues());
        writer.writeByte(reader.getOs2().ulCodePageRanges.getCharset());

        // Italic.
        writer.writeByte((byte)((((reader.getOs2().fsSelection & 0xFFFF) & 0x01) != 0) ? 1 : 0));
        writer.writeUInt32((/*uint*/int)(reader.getOs2().usWeightClass & 0xFFFF));

        // Type flags that provide information about embedding permissions.
        writer.writeUInt16(reader.getOs2().fsType.getValue());

        // Magic number for EOT file - 0x504C. Used to check for data corruption.
        writer.writeInt16(MAGIC_NUMBER);

        // os/2.UnicodeRange1 (bits 0-31)
        writer.writeUInt32(reader.getOs2().ulUnicodeRanges.getRange1());
        // os/2.UnicodeRange2 (bits 32-63)
        writer.writeUInt32(reader.getOs2().ulUnicodeRanges.getRange2());
        // os/2.UnicodeRange3 (bits 64-95)
        writer.writeUInt32(reader.getOs2().ulUnicodeRanges.getRange3());
        // os/2.UnicodeRange4 (bits 96-127)
        writer.writeUInt32(reader.getOs2().ulUnicodeRanges.getRange4());

        // CodePageRange1 (bits 0-31)
        writer.writeUInt32(reader.getOs2().ulCodePageRanges.getRange1());
        // CodePageRange2 (bits 32-63)
        writer.writeUInt32(reader.getOs2().ulCodePageRanges.getRange2());

        // head.CheckSumAdjustment - See http://www.microsoft.com/typography/otspec/head.htm
        writer.writeUInt32(reader.getHead().CheckSumAdjustment);

        // Reserved1
        writer.writeUInt32((/*uint*/int)0x0);

        // Reserved2
        writer.writeUInt32((/*uint*/int)0x0);

        // Reserved3
        writer.writeUInt32((/*uint*/int)0x0);

        // Reserved4
        writer.writeUInt32((/*uint*/int)0x0);

        writer.writeUInt16(PADDING);
        writer.writeUInt16((/*ushort*/short)familyName.length);
        writer.writeBytes(familyName);

        writer.writeUInt16(PADDING);
        writer.writeUInt16((/*ushort*/short)styleName.length);
        writer.writeBytes(styleName);

        writer.writeUInt16(PADDING);
        writer.writeUInt16((/*ushort*/short)versionName.length);
        writer.writeBytes(versionName);

        writer.writeUInt16(PADDING);
        writer.writeUInt16((/*ushort*/short)fullName.length);
        writer.writeBytes(fullName);
    }

    /// <summary>
    /// Returns byte array with font's style Unicode name. Name string will be ended with '\0' character.
    /// </summary>
    private static byte[] getStyleUnicodeName(/*FontStyle*/int style)
    {
        if (((style & FontStyle.ITALIC) != 0) && ((style & FontStyle.BOLD) != 0))
            return Encoding.getUnicode().getBytes("BoldItalic\u0000");
        if ((style & FontStyle.ITALIC) != 0)
            return Encoding.getUnicode().getBytes("Italic\u0000");
        if ((style & FontStyle.BOLD) != 0)
            return Encoding.getUnicode().getBytes("Bold\u0000");

        return Encoding.getUnicode().getBytes("Regular\u0000");
    }

    /// <summary>
    /// Tries to extract Open Type font from EOT file.
    /// Returns Open Type font data if possible and null otherwise.
    /// </summary>
    public static byte[] tryExtractOpenTypeFromEot(byte[] eotFile)
    {
        try
        {
            return extractOpenTypeFromEot(eotFile);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /// <summary>
    /// Extracts Open Type font from EOT file.
    /// </summary>
    public static byte[] extractOpenTypeFromEot(byte[] eotFile) throws Exception
    {
        if (eotFile == null || eotFile.length == 0)
            return null;

        BinaryReader reader = new BinaryReader(new MemoryStream(eotFile));
        try /*JAVA: was using*/
        {
            // Verify EOTSize field.
            /*uint*/int eotSize = reader.readUInt32();
            if ((eotSize & 0xFFFFFFFFL) != reader.getBaseStream().getLength())
                throw new IllegalStateException("The EOT file is not valid.");

            /*uint*/int fontDataSize = reader.readUInt32();

            // Verify Version field.
            /*EotVersion*/int version = parseVersion(reader.readInt32());
            if (version == EotVersion.UNKNOWN)
                throw new IllegalStateException("Unsupported EOT version.");

            int flags = reader.readInt32();

            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 18);

            // Verify MagicNumber field.
            short magicNumber = reader.readInt16();
            if (magicNumber != MAGIC_NUMBER)
                throw new IllegalStateException("The EOT file is not valid.");

            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 46);

            /*ushort*/short familyNameSize = reader.readUInt16();
            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + ((familyNameSize & 0xFFFF) + 2));

            /*ushort*/short styleNameSize = reader.readUInt16();
            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + ((styleNameSize & 0xFFFF) + 2));

            /*ushort*/short versionNameSize = reader.readUInt16();
            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + ((versionNameSize & 0xFFFF) + 2));

            /*ushort*/short fullNameSize = reader.readUInt16();
            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (fullNameSize & 0xFFFF));

            if (version == EotVersion.VERSION_21 || version == EotVersion.VERSION_22)
            {
                reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 2);

                /*ushort*/short rootStringSize = reader.readUInt16();
                reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (rootStringSize & 0xFFFF));

                if (version == EotVersion.VERSION_22)
                {
                    reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 10);

                    /*ushort*/short signatureSize = reader.readUInt16();
                    reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (signatureSize & 0xFFFF));

                    reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 4);

                    /*uint*/int eudcFontSize = reader.readUInt32();
                    reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (eudcFontSize & 0xFFFFFFFFL));
                }
            }

            // Verify FontDataSize filed.
            if (reader.getBaseStream().getPosition() + (fontDataSize & 0xFFFFFFFFL) != reader.getBaseStream().getLength())
                throw new IllegalStateException("The EOT file is not valid.");

            byte[] fontData = reader.readBytes((int)(fontDataSize & 0xFFFFFFFFL));

            if (BitUtil.isSetInt32(flags, XOR_ENCRYPTED_FLAG))
                xorFontData(fontData);

            if (BitUtil.isSetInt32(flags, COMPRESSED_FLAG))
                fontData = MtxCoder.decode(fontData);

            return fontData;
        }
        finally { if (reader != null) reader.close(); }
    }

    /// <summary>
    /// Extracts style name of embedded font from EOT file.
    /// </summary>
    public static String getStyleName(byte[] eotFile)
    {
        // Font family name goes before font style name and has variable length,
        // so we need to determine it to skip a number of it's bytes.
        int familyNameSize = getFamilyNameSize(eotFile);
        // Offset of the style name size is equal to offset of font family size
        // plus 2 bytes of it's value plus font family name size plus 2 bytes of font style name size.
        int offsetStyleNameSize = OFFSET_FAMILY_NAME_SIZE + 2 + familyNameSize + 2;

        /*ushort*/short styleNameSize = BitConverter.toUInt16(eotFile, offsetStyleNameSize);

        // The string with style name starts from the offset of style name size plus 2 bytes of it's value.
        // This string is always stored in UTF16 encoding.
        return Encoding.getUnicode().getString(eotFile, offsetStyleNameSize + 2, (styleNameSize & 0xFFFF));
    }

    /// <summary>
    /// Returns size of EOT file header.
    /// </summary>
    public static int getHeaderSize(byte[] eotFile)
    {
        return getEotSize(eotFile) - getFontDataSize(eotFile);
    }

    /// <summary>
    /// Returns value of 'Subset' flag from EOT file header processing flags.
    /// </summary>
    public static boolean isSubset(byte[] eotFile)
    {
        /*uint*/int flags = getFlags(eotFile);
        return (((flags & 0xFFFFFFFFL) & SUBSET_FLAG) != 0);
    }

    /// <summary>
    /// Performs XOR operation with font data according to EOT processing spec.
    /// </summary>
    static void xorFontData(byte[] fontData)
    {
        // See http://www.w3.org/Submission/EOT/#Processing.
        for (int i = 0; i < fontData.length; i++)
        {
            fontData[i] = (byte)((fontData[i] & 0xFF) ^ (XOR_KEY & 0xFF));
        }
    }

    /// <summary>
    /// Returns size of EOT file.
    /// </summary>
    private static int getEotSize(byte[] eotFile)
    {
        return BitConverter.toInt32(eotFile, 0);
    }

    /// <summary>
    /// Returns size of EOT file font data.
    /// </summary>
    private static int getFontDataSize(byte[] eotFile)
    {
        return BitConverter.toInt32(eotFile, 4);
    }

    /// <summary>
    /// Parses specified integer to <see cref="EotVersion"/>.
    /// </summary>
    private static /*EotVersion*/int parseVersion(int version)
    {
        switch (version)
        {
            case (int)EotVersion.VERSION_10:
                return EotVersion.VERSION_10;
            case (int)EotVersion.VERSION_21:
                return EotVersion.VERSION_21;
            case (int)EotVersion.VERSION_22:
                return EotVersion.VERSION_22;
            default:
                return EotVersion.UNKNOWN;
        }
    }

    /// <summary>
    /// Returns length of font family name string inside EOT file.
    /// </summary>
    private static int getFamilyNameSize(byte[] eotFile)
    {
        return (BitConverter.toUInt16(eotFile, OFFSET_FAMILY_NAME_SIZE) & 0xFFFF);
    }

    /// <summary>
    /// Extracts processing Flags from EOT file.
    /// </summary>
    private static /*uint*/int getFlags(byte[] eotFile)
    {
        final int OFFSET_FLAGS = 12;
        return BitConverter.toUInt32(eotFile, OFFSET_FLAGS);
    }

    // EOT font processing flags.
    static final int SUBSET_FLAG = 0x00000001;
    static final int COMPRESSED_FLAG = 0x00000004;
    static final int XOR_ENCRYPTED_FLAG = 0x10000000;
    // Magic number used in EOT header.
    static final short MAGIC_NUMBER = 0x504C;
    private static final byte XOR_KEY = (byte) 0x50;

    // Offset of font family name size inside EOT file.
    // All values before this element have fixed lengths.
    private static final int OFFSET_FAMILY_NAME_SIZE = 82;

    /// <summary>
    /// Padding value used in EOT header. Must always be set to 0x0000.
    /// </summary>
    private static final /*ushort*/short PADDING = (short) 0x0000;
}

