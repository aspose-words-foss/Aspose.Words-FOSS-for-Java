// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.XmlUtilPal;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a Custom XML Data Storage Part (custom XML data within a package).
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>A DOCX or DOC document can contain one or more Custom XML Data Storage parts. Aspose.Words preserves and 
/// allows to create and extract Custom XML Data via the <see cref="Document.CustomXmlParts"/> collection.</para>
/// 
/// <seealso cref="Document.CustomXmlParts"/>
/// <seealso cref="CustomXmlPartCollection"/>
/// </remarks>
public class CustomXmlPart implements Cloneable
{
    /// <summary>
    /// Gets or sets the string that identifies this custom XML part within an OOXML document.
    /// </summary>
    /// <remarks>
    /// <para>ISO/IEC 29500 specifies that this value is a GUID, but old versions of Microsoft Word allowed any
    /// string here. Aspose.Words does the same for ECMA-376 format. But note, that Microsoft Word Online fails 
    /// to open a document created with a non-GUID value. So, a GUID is preferred value for this property.</para>
    /// 
    /// <para>A valid value must be an identifier that is unique among all custom XML data parts in this document.
    /// </para>
    /// 
    /// <para>The default value is an empty string. The value cannot be <c>null</c>.</para>
    /// </remarks>
    public String getId() { return mId; }
    public void setId(String value)
    {
        ArgumentUtil.checkNotNull(value, "id");
        mId = value;
    }

    /// <summary>
    /// Specifies the set of XML schemas that are associated with this custom XML part.
    /// </summary>
    public CustomXmlSchemaCollection getSchemas() { return mSchemas; }

    /// <summary>
    /// Gets or sets the XML content of this Custom XML Data Storage Part.
    /// </summary>
    /// <remarks>
    /// <para>The default value is an empty byte array. The value cannot be <c>null</c>.</para>
    /// </remarks>
    
    public byte[] getData() { return mData; }
    
    public void setData(byte[] value)
    {
        ArgumentUtil.checkNotNull(value, "data");
        mData = value;
    }

    /// <summary>
    /// Makes a "deep enough" copy of the object. 
    /// Does not duplicate the bytes of the <see cref="Data"/> value.
    /// </summary>
    public CustomXmlPart deepClone()
    {
        // Shallow copy some fields.
        CustomXmlPart lhs = (CustomXmlPart)memberwiseClone();

        // Deep copy others.
        lhs.mSchemas = mSchemas.deepClone();

        return lhs;
    }

    /// <summary>
    /// Specifies a cyclic redundancy check (CRC) checksum of the <see cref="Data"/> content.
    /// </summary>
    public long getDataChecksum() { return (com.aspose.Crc32Xfer.getCrc(mData) & 0xFFFFFFFFL); }

    /// <summary>
    /// Returns "True", when error occurred, while validating custom xml data.
    /// </summary>
    boolean validateCustomXmlData() throws Exception
    {
        boolean isXmlValid = true;

        if (getData().length > 0)
        {
            MemoryStream ms = new MemoryStream(getData(), false);
            try /*JAVA: was using*/
            {
                try
                {                       
                    // Custom XML data properties can contain XML schemas that are associated with the custom XML data
                    // part, however decided to skip validating XML according to schemas for a while (see section 
                    // "7.5 Custom XML Data Properties" of the OOXML spec).
                    XmlTextReader reader = XmlUtilPal.createXmlTextReader(ms);
                    while (reader.read())
                    {
                        // Read all data.
                    }
                }
                catch(IllegalStateException e)
                {
                    isXmlValid = false;
                }
            }
            finally { if (ms != null) ms.close(); }
        }

        return isXmlValid;
    }

    private String mId = "";
    private CustomXmlSchemaCollection mSchemas = new CustomXmlSchemaCollection();
    private byte[] mData = ArrayUtil.EMPTY_BYTE_ARRAY;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

