// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2005 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.HashMap;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.VarEnum;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Represents a section in a property set in a structured storage.
/// This is the actual container for properties.
/// 
/// http://msdn.microsoft.com/archive/default.asp?url=/archive/en-us/dnarolegen/html/msdn_propset.asp
/// </summary>
public class PropertySetSection
{
    /// <summary>
    /// Common private ctor code.
    /// </summary>
    private PropertySetSection(Guid fmtId)
    {
        mFmtId = fmtId;
        mProperties = new PropertyCollection();
    }

    /// <summary>
    /// Ctor. creates a property set section. Properties will be written to OLE in the specified code page.
    /// </summary>
    public PropertySetSection(Guid fmtId, int codePage)
    {
    	this(fmtId);
	
        if (codePage <= 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "codePage");

        mCodePage = codePage;
    }

    /// <summary>
    /// Ctor, reads a section of properties.
    /// </summary>
    public PropertySetSection(Guid fmtId, BinaryReader reader) throws Exception
    {
    	this(fmtId);
	
        long sectionStart = reader.getBaseStream().getPosition();

        // Read property section header
        // Ignore size.
        reader.readInt32();
        int count = reader.readInt32();

        // Read property ids and offsets.
        ArrayList<PropIdOffset> propIdOffsets = new ArrayList<PropIdOffset>();
        for (int i = 0; i < count; i++)
            msArrayList.add(propIdOffsets, new PropIdOffset(reader));

        // Read optional code page indicator for non-Unicode strings.
        mCodePage = DEFAULT_CODE_PAGE;
        int codePageOffset = getPropOffset(propIdOffsets, CODE_PAGE_PROP_ID);
        if (codePageOffset != -1)
        {
            reader.getBaseStream().setPosition(sectionStart + codePageOffset);
            Object v = readPropValue(reader, 0);
            if (v instanceof /*short*/Short)
            {
                // This property is stored as VT_I2 so I have to cast it to short first to unbox.
                short s = (/*short*/Short)v;
                // But then I have to mask it to 16 bits when extending to int32 so it works correctly 
                // for code pages like UTF-7 (65000) to work properly.
                mCodePage = s & 0xffff;
            }
        }

        // Read optional property names.
        HashMap<Integer, String> propNames = new HashMap<Integer, String>();
        int propNamesOffset = getPropOffset(propIdOffsets, DICTIONARY_PROP_ID);
        if (propNamesOffset != -1)
        {
            reader.getBaseStream().setPosition(sectionStart + propNamesOffset);

            // WORDSNET-19571 Remove all custom properties if custom property name contain garbage.
            // ArgumentOutOfRangeException for .Net. IndexOutOfBoundsException for Java.
            try
            {
                readPropNames(reader, propNames, mCodePage);
            }
            catch(Exception e)
            {
                propNames.clear();
            }
            
        }

        // Read property values
        for (int i = 0; i < propIdOffsets.size(); i++)
        {
            PropIdOffset propIdOffset = propIdOffsets.get(i);

            // Ignore all system properties since we have already read the ones we wanted.
            // But to do a compare we extend int into long while making sure the sign is not extended.
            // This is needed because the actual property id range is uint but we don't want to use uint here for better autoporting.
            long longPropId = propIdOffset.Id & 0xfffffffL;
            if ((longPropId < MIN_USER_PROP_ID) || (longPropId > MAX_USER_PROP_ID))
                continue;

            // Property offset is from the section start.
            reader.getBaseStream().setPosition(sectionStart + propIdOffset.Offset);
            Object propValue = readPropValue(reader, mCodePage);
            if (propValue != null)
            {
                String propName = DictionaryExtension.getValueOrNull(propNames, propIdOffset.Id);
                mProperties.add(new Property(propIdOffset.Id, propName, propValue));
            }
        }
    }

    private static int getPropOffset(ArrayList<PropIdOffset> propIdOffsets, int propId)
    {
        for (int i = 0; i < propIdOffsets.size(); i++)
        {
            PropIdOffset propIdOffset = propIdOffsets.get(i);
            if (propIdOffset.Id == propId)
                return propIdOffset.Offset;
        }
        return -1;
    }

    /// <summary>
    /// Writes this property set section into a byte array.
    /// </summary>
    public byte[] toByteArray() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(stream);

        // Size and Count.
        final int SECTION_HEADER_SIZE = 4 + 4;    
        // PropertyId and Offset
        final int PROP_ID_OFFSET_SIZE = 4 + 4;        

        int firstPropertyOffset = 
            SECTION_HEADER_SIZE +
            PROP_ID_OFFSET_SIZE + // Add one entry for code page.
            PROP_ID_OFFSET_SIZE * mProperties.getCount();
        
        // Add one entry for dictionary of property names if we are writing it.
        // WORDSNET-If we don't write it for user defined properties, MS Excel seems to lose the 
        // digital signature when you look at it in the VBA editor.
        // If we write it for built in properties, MS Excel does not show any properties at all.
        boolean isWritingPropNames = (mProperties.hasNames() || mFmtId.equals(FMTID_USER_DEFINED));
        if (isWritingPropNames)
            firstPropertyOffset += PROP_ID_OFFSET_SIZE;

        stream.setPosition(firstPropertyOffset);

        // Written property ids and offset accumulate here so we write them at the end.
        ArrayList<PropIdOffset> propIdOffsets = new ArrayList<PropIdOffset>();

        if (isWritingPropNames)
        {
            msArrayList.add(propIdOffsets, new PropIdOffset(DICTIONARY_PROP_ID, (int)stream.getPosition()));
            writePropNames(writer, mProperties, mCodePage);
        }

        // Write code page. 
        msArrayList.add(propIdOffsets, new PropIdOffset(CODE_PAGE_PROP_ID, (int)stream.getPosition()));
        writePropValue(writer, (short)mCodePage, mCodePage, true);    // This property has to be VT_I2.

        // Write properties and remember their offsets.
        for (int i = 0; i < mProperties.getCount(); i++)
        {
            Property prop = mProperties.get(i);
            // Current position in the stream is the offset where the property will be written.
            msArrayList.add(propIdOffsets, new PropIdOffset(prop.getId(), (int)stream.getPosition()));    
            writePropValue(writer, prop.getValue(), mCodePage, true);
        }

        // Write section header
        stream.setPosition(0);
        writer.writeInt32((int)stream.getLength());
        writer.writeInt32((int)propIdOffsets.size());

        // Write prop offsets
        for (int i = 0; i < propIdOffsets.size(); i++)
        {
            PropIdOffset propIdOffset = propIdOffsets.get(i);
            propIdOffset.write(writer);
        }

        return stream.toArray();
    }

    /// <summary>
    /// Gets the properties read from the structured storage.
    /// </summary>
    public PropertyCollection getProperties() { return mProperties; }

    public Guid getFmtId() { return mFmtId; }

    public int getCodePage() { return mCodePage; }

    /// <summary>
    /// Reads a map of prop ids to prop names (property name dictionary). Reader must be positioned properly.
    /// </summary>
    private static void readPropNames(BinaryReader reader, HashMap<Integer, String> propNames, int codePage) throws Exception
    {
        int count = reader.readInt32();

        for (int i = 0; i < count; i++)
        {
            // WORDSNET-13831 Resilience against truncated stream.
            if (!StreamUtil.hasEnoughBytesToRead(reader, 4 /* Int32 */))
                break;

            int propId = reader.readInt32();
            String propName = readString(reader, codePage);

            // WORDSNET-5247 Stop reading in case of error.
            if (!StringUtil.hasChars(propName))
                break;

            msDictionary.add(propNames, propId, propName);
        }
    }

    /// <summary>
    /// Writes a map of propids to prop names.
    /// </summary>
    private static void writePropNames(BinaryWriter writer, PropertyCollection props, int codePage) throws Exception
    {
        writer.writeInt32((int)props.getCountOfPropertiesWithNames());
        for (int i = 0; i < props.getCount(); i++)
        {
            Property prop = props.get(i);
            if (prop.hasName())
            {
                writer.writeInt32((int)prop.getId());
                writeString(writer, prop.getName(), codePage);
            }
        }
    }

    /// <summary>
    /// Read one property value. The reader must be positioned at the beginning of the property.
    /// </summary>
    /// <remarks>
    /// Normally returns a property value. Returns null for some property types that we don't support.
    /// These property types are used for properties that we can ignore.
    /// </remarks>
    private static Object readPropValue(BinaryReader reader, int codePage) throws Exception
    {
        /*VarEnum*/int propType = (/*VarEnum*/int)reader.readInt32();
        switch (propType)
        {
            case VarEnum.VT_BSTR:
            {
                return INVALID_PROPERTY_TYPE;
            }
            case VarEnum.VT_LPSTR:
            {
                return readLPSTR(reader, codePage);
            }
            case VarEnum.VT_LPWSTR:
            {
                String result = readLPWSTR(reader);
                // It looks like all LPWSTR strings in property sets are padded to 32 bits.
                // This line of code is needed for correctly reading a vector of LPWSTR.
                StreamUtil.seekToNextPage(reader.getBaseStream(), PROP_VALUE_ALIGNMENT);
                return result;
            }
            case VarEnum.VT_FILETIME:
            {
                long ticks = reader.readInt64();

                //Date's boundaries check: accidental negative or too big ticks count causes an exception.
                //"No date/time" (ticks == 0) is represented as DateTime.MinValue in the model.
                //Lets display wrong ticks value by DateTime.MinValue too.
                if (ticks <= DateTime.MinValue.getTicks() || ticks > DateTime.MaxValue.getTicks())
                    return DateTime.MinValue;

                //The time is stored in UTC in the file, return it as UTC too.
                return DateTimeUtil.fromFileTimeUtc(ticks, "ticks");
            }
            case VarEnum.VT_UI_4:
                return reader.readUInt32();
            case VarEnum.VT_I_4:
                return reader.readInt32();
            case VarEnum.VT_I_2:
                return reader.readInt16();
            case VarEnum.VT_BOOL:
                return (reader.readInt16() != 0);
            case VarEnum.VT_R_8:
                return reader.readDouble();
            case VarEnum.VT_BLOB:
            {
                int length = reader.readInt32();
                byte[] data = reader.readBytes(length);
                return data;
            }
            case (VarEnum.VT_VECTOR | VarEnum.VT_VARIANT):
            {
                // This code presumes this property is HeadingPairs.
                // http://msdn2.microsoft.com/en-us/library/aa380374(VS.85).aspx
                return readHeadingPairs(reader, codePage);
            }
            case (VarEnum.VT_VECTOR | VarEnum.VT_LPSTR):
            {
                // WORDSNET-3942 Aspose.Words.FileCorruptedException” exception occurs during opening the document.
                // RK The property set specifies Unicode code page, but TitlesOfParts are stored as
                // ASCII strings so we have to override the code page.
                int realCodePage = (codePage != UNICODE_CODE_PAGE) ? codePage : DEFAULT_CODE_PAGE;

                return readTitlesOfParts(reader, realCodePage);
            }
            case (VarEnum.VT_VECTOR | VarEnum.VT_LPWSTR):
            {
                return readTitlesOfParts(reader, codePage);
            }
            default:
            {
                //VarEnum.VT_CF used for Thumbnail that we do not support.
                //http://msdn2.microsoft.com/en-us/library/aa380376(VS.85).aspx
                return null;
            }
        }
    }

    private static String[] readTitlesOfParts(BinaryReader reader, int codePage) throws Exception
    {
        // TitlesofParts as per the MSDN spec. Related to HeadingPairs.
        int count = reader.readInt32();
        String[] result = new String[count];
        for (int i = 0; i < count; i++)
            result[i] = readString(reader, codePage);

        return result;
    }

    private static Object[] readHeadingPairs(BinaryReader reader, int codePage) throws Exception
    {
        // This code presumes this property is HeadingPairs.
        // http://msdn2.microsoft.com/en-us/library/aa380374(VS.85).aspx
        int itemCount = reader.readInt32();
        // This is an array of <string, int> that is <part name, heading count>
        Object[] result = new Object[itemCount];
        for (int i = 0; i < itemCount; i++)
            result[i] = readPropValue(reader, codePage); 

        // Perform quick validation.
        // AM. This property MUST be <string, int> array. Otherwise DOCX output is failed to open by Word.
        // Also I found that <int> value must be greater than 0.
        // So I do quick check and return empty property in case validation is failed.
        boolean isValid = true;
        for (int i = 0; i < result.length; i++)
        {
            Object o = result[i];
            isValid &= (MathUtil.isOdd(i) ? (o instanceof /*int*/Integer) && ((/*int*/Integer)o > 0) : o instanceof String);
        }

        return isValid ? result : null;
    }

    /// <summary>
    /// Writes a property type.
    /// Normally, properties need to be aligned to 4 byte boundary.
    /// However, when writing elements of an VT_VECTOR, there must be no alignment between elements.
    /// </summary>
    private static void writePropValue(BinaryWriter writer, Object value, int codePage, boolean isAlignRequired) throws Exception
    {
        String valueString = ms.as(value, String.class);

        if (valueString != null)
        {
            if (codePage == UNICODE_CODE_PAGE)
                writer.writeInt32((int)VarEnum.VT_LPWSTR);
            else
                writer.writeInt32((int)VarEnum.VT_LPSTR);

            writeString(writer, valueString, codePage);
        }
        else if (value instanceof /*short*/Short)
        {
            writer.writeInt32((int)VarEnum.VT_I_2);
            writer.writeInt16((/*short*/Short)value);
        }
        else if (value instanceof /*int*/Integer)
        {
            writer.writeInt32((int)VarEnum.VT_I_4);
            writer.writeInt32((/*int*/Integer)value);
        }
        else if (value instanceof /*uint*/Integer)               // RK I don't like this because it only works in autoporting because we don't write VT_I8.
        {
            writer.writeInt32((int)VarEnum.VT_UI_4);
            writer.writeUInt32((/*uint*/Integer)value);
        }
        else if (value instanceof /*double*/Double)
        {
            writer.writeInt32((int)VarEnum.VT_R_8);
            writer.writeDouble((/*double*/Double)value);
        }
        else if (value instanceof /*boolean*/Boolean)
        {
            writer.writeInt32((int)VarEnum.VT_BOOL);
            boolean b = (/*boolean*/Boolean)value;
            writer.writeInt16((short)((b) ? -1 : 0));
        }
        else if (value instanceof DateTime)
        {
            writer.writeInt32((int)VarEnum.VT_FILETIME);
            DateTime dateTime = (DateTime)value;
            if (!DateTime.equals(dateTime, DateTime.MinValue))
                writer.writeInt64(DateTimeUtil.toFileTimeUtc(dateTime, "dateTime"));
            else
                writer.writeInt64((long)0);
        }
        else if (value instanceof byte[])
        {
            writer.writeInt32((int)VarEnum.VT_BLOB);
            byte[] data = (byte[])value;
            writer.writeInt32(data.length);
            writer.writeBytes(data);
        }
        else if (value instanceof String[])
        {
            // This code presumes we are writing TitlesofParts.
            // WORDSNET-3393 Programmatically added custom properties are invisible in MSWord but visible.
            // It took me some time to figure out how to properly write this property in Unicode.
            if (codePage == UNICODE_CODE_PAGE)
                writer.writeInt32((int)VarEnum.VT_VECTOR | (int)VarEnum.VT_LPWSTR);   
            else
                writer.writeInt32((int)VarEnum.VT_VECTOR | (int)VarEnum.VT_LPSTR);
            
            String[] strings = (String[])value;
            writer.writeInt32(strings.length);
            for (String s : strings)
                writeString(writer, s, codePage);
        }
        else if (value instanceof Object[])
        {
            // This code presumes we are writing HeadingPairs.
            writer.writeInt32((int)VarEnum.VT_VECTOR | (int)VarEnum.VT_VARIANT);
            
            Object[] items = (Object[])value;
            writer.writeInt32(items.length);
            for (Object item : items)
                writePropValue(writer, item, codePage, false);
        }
        else
        {
            throw new UnsupportedOperationException("Do not know how to write a property value of this type.");
        }

        if (isAlignRequired)
            StreamUtil.seekToNextPage(writer.getBaseStream(), PROP_VALUE_ALIGNMENT);
    }

    /// <summary>
    /// Reads an LPSTR in the OLE property storage format.
    /// </summary>
    private static String readLPSTR(BinaryReader reader, int codePage) throws Exception
    {
        // For an LPSTR a count of BYTES that includes a terminating zero is stored.
        // But since encoding can be Unicode there could actually be more than one zero byte,
        // therefore I don't strip the zero bytes yet and read all bytes.
        int byteCount = reader.readInt32();

        if (!StreamUtil.hasEnoughBytesToRead(reader, byteCount))
        {
            // WORDSNET-5247 Stop reading if beyond of stream and return empty string.
            byteCount = 0;
        }

        byte[] data = reader.readBytes(byteCount);
        String s = Encoding.getEncoding(codePage).getString(data, 0, byteCount);

        // It is time to remove the terminating zero, but I saw \0\0\0\0 for an empty string.
        // Does it means it pads minimum to 4 chars? Certainly not always, but we can have 
        // more than one zero at the end. Lets strip them all.
        s = msString.trimEnd(s, '\u0000');

        return s;
    }

    /// <summary>
    /// Writes an LPSTR in the OLE property storage format.
    /// </summary>
    private static void writeLPSTR(BinaryWriter writer, String s, int codePage) throws Exception
    {
        // Add terminating zero.
        s = msString.plusEqOperator(s, '\u0000');
        byte[] data = Encoding.getEncoding(codePage).getBytes(s);
        // For an LPSTR a count of BYTES that includes a terminating zero is stored.
        writer.writeInt32((int)data.length);
        writer.writeBytes(data);
    }

    /// <summary>
    /// Reads an LPWSTR in the OLE property storage format.
    /// </summary>
    private static String readLPWSTR(BinaryReader reader) throws Exception
    {
        // Ugly MS. For LPWSTR a count of CHARS is stored. 
        // We have to multiply it to get the number of bytes.
        int byteCount = reader.readInt32() * 2;
        byte[] data = reader.readBytes(byteCount);
        String result = Encoding.getUnicode().getString(data, 0, byteCount - 2); //Do not need terminating null.

        // WORDSNET-8605 If the number of meaningful characters is even, an extra terminator is added by MS Word,
        // since LPWSTR strings are padded to 32 bits. It should be trimmed either.
        if (StringUtil.hasChars(result))
            result = msString.trimEnd(result, '\u0000');

        return result;
    }

    /// <summary>
    /// Writes an LPWSTR in the OLE property storage format.
    /// </summary>
    private static void writeLPWSTR(BinaryWriter writer, String s) throws Exception
    {
        // For an LPWSTR we need to write count of CHARS including zero terminator.
        writer.writeInt32((int)s.length() + 1);    //One extra for terminator.
        writer.writeBytes(Encoding.getUnicode().getBytes(s));
        writer.writeInt16((short)0);    //Terminating null.
    }

    /// <summary>
    /// Reads a string either as LPWSTR or as LPSTR. When reading LPWSTR pads to 32 bits.
    /// </summary>
    private static String readString(BinaryReader reader, int codePage) throws Exception
    {
        String result;
        if (codePage == UNICODE_CODE_PAGE)
        {
            // When code page is Unicode, LPWSTR strings and padded to 32 bits.
            result = readLPWSTR(reader);
            StreamUtil.seekToNextPage(reader.getBaseStream(), PROP_VALUE_ALIGNMENT);
        }
        else
        {
            result = readLPSTR(reader, codePage);
        }
        return result;
    }

    /// <summary>
    /// Writes a string either as LPWSTR or as LPSTR. When Unicode, writes as LPWSTR and pads to 32 bits.
    /// </summary>
    private static void writeString(BinaryWriter writer, String s, int codePage) throws Exception
    {
        if (codePage == UNICODE_CODE_PAGE)
        {
            // When code page is Unicode, LPWSTR strings and padded to 32 bits.
            writeLPWSTR(writer, s);
            StreamUtil.seekToNextPage(writer.getBaseStream(), PROP_VALUE_ALIGNMENT);
        }
        else
        {
            writeLPSTR(writer, s, codePage);
        }
    }

    private /*final*/ Guid mFmtId = Guid.Empty;    // Must be initialized for Java to work.
    private /*final*/ PropertyCollection mProperties;
    private /*final*/ int mCodePage;

    /// <summary>
    /// We use 1252 code when reading properties and code page is not specified by the property set.
    /// </summary>
    private static final short DEFAULT_CODE_PAGE = 1252;
    private static final short UNICODE_CODE_PAGE = 1200;
    
    /// <summary>
    /// Property values are aligned on 32 bit boundaries.
    /// </summary>
    private static final int PROP_VALUE_ALIGNMENT = 4;        

    /// <summary>
    /// This property contains user defined property names.
    /// </summary>
    private static final int DICTIONARY_PROP_ID = 0x00000000;
    /// <summary>
    /// Contains indicator as to what code page the strings are stored in.
    /// </summary>
    private static final int CODE_PAGE_PROP_ID = 0x00000001;
    private static final int MIN_USER_PROP_ID = 0x00000002;
    private static final int MAX_USER_PROP_ID = 0x7fffffff;
    /// <summary>
    /// Sometimes present in a property set. Not sure what to do with it. Let's ignore.
    /// </summary>
    private static final int LOCALE_PROP_ID = /*unchecked*/((int)(int) 0x80000000L);
    /// <summary>
    /// When this property value is 0x00 it means case-insensitive compare for property names (default).
    /// When this properyt value is 0x01 it means case-sensitive compare.
    /// Don't want to bother handling this, let's ignore.
    /// </summary>
    private static final int BEHAVIOR_PROP_ID = /*unchecked*/((int)(int) 0x80000003L);

    /// <summary>
    /// Predefined property set identifiers (in fact these are section identifiers).
    /// </summary>
    public static /*final*/ Guid FMTID_SUMMARY_INFO = new Guid("F29F85E0-4FF9-1068-AB91-08002B27B3D9");
    public static /*final*/ Guid FMTID_DOC_SUMMARY_INFO = new Guid("D5CDD502-2E9C-101B-9397-08002B2CF9AE");
    public static /*final*/ Guid FMTID_USER_DEFINED = new Guid("D5CDD505-2E9C-101B-9397-08002B2CF9AE");

    public static /*final*/ String INVALID_PROPERTY_TYPE = new Guid().toString();
}

