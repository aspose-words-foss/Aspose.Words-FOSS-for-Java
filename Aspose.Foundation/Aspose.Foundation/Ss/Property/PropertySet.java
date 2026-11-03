// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2005 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;


/// <summary>
/// Property set in a structured storage.
/// </summary>
public class PropertySet
{
    public PropertySet()
    {
    }

    public PropertySet(Stream stream) throws Exception
    {
    	this(stream, 2);
	
    }

    public PropertySet(Stream stream, int maxSections) throws Exception
    {
        stream.setPosition(0);
        BinaryReader reader = new BinaryReader(stream, Encoding.getUnicode());

        //Read property set header
        reader.readUInt16();    //byte order
        //I support format version 0 only at the moment.
        //http://msdn2.microsoft.com/en-us/library/aa379124.aspx
        /*ushort*/short format = reader.readUInt16();
        if (format != 0)
            throw new UnsupportedOperationException("Cannot read property set in this format.");
        reader.readUInt32();    //os version
        mClsid = new Guid(reader.readBytes(16));
        int sectionCount = reader.readInt32();    //this is specified as reserved in MSDN.

        // WORDSNET-7686 Writers of property sets should set this value to 1; readers of property sets should ensure that this value is at least 1.
        // An exception to this guideline is that, for The DocumentSummaryInformation and UserDefined Property Sets, this value may be 2.
        // Any other value means that property set is corrupted.
        if((sectionCount < 0) || (sectionCount > maxSections))
        {
            // WARN.
            return;
        }

        //Read Format Identifier/Offset Pairs of the sections.
        Guid[] fmtIds = new Guid[sectionCount];
        int[] sectionOffsets = new int[sectionCount];
        for (int i = 0; i < sectionCount; i++)
        {
            fmtIds[i] = new Guid(reader.readBytes(16));
            sectionOffsets[i] = reader.readInt32();
        }

        //Read the sections themselves.
        for (int i = 0; i < sectionCount; i++)
        {
            reader.getBaseStream().setPosition(sectionOffsets[i]);
            msArrayList.add(mSections, new PropertySetSection(fmtIds[i], reader));
        }
    }

    public void save(Stream stream) throws Exception
    {
        BinaryWriter writer = new BinaryWriter(stream, Encoding.getUnicode());

        //Write header
        writer.writeUInt16((/*ushort*/short)0xfffe);        //byte order
        writer.writeUInt16((/*ushort*/short)0);            //format
        writer.writeUInt32((/*uint*/int)0x00020105);    //os version, this is what my WinXP writes.
        writer.writeBytes(mClsid.toByteArray());
        writer.writeInt32((int)mSections.size());    //marked as reserved in MSDN

        //First section will start after the header and after the Format Identifier/Offset Pairs.
        int firstSectionOffset = (int)stream.getPosition() + FMT_ID_OFFSET_PAIR_LENGTH * mSections.size();

        //Write Format Identifier/Offset Pairs of the sections.
        //And at the same time write sections into a memory buffer first so we know
        //their length and can arrange offsets.
        MemoryStream sectionsStream = new MemoryStream();
        for (int i = 0; i < mSections.size(); i++)
        {
            PropertySetSection section = (PropertySetSection)mSections.get(i);
            writer.writeBytes(section.getFmtId().toByteArray());
            int sectionOffset = firstSectionOffset + (int)sectionsStream.getLength();
            writer.writeInt32(sectionOffset);
            //Write the section to the buffer. The next section will start right after this, thus
            //the length of the buffer can be used to determine the next section offset.
            byte[] sectionData = section.toByteArray();
            sectionsStream.write(sectionData, 0, sectionData.length);
        }

        //Write the sections
        stream.write(sectionsStream.getBuffer(), 0, (int)sectionsStream.getLength());
    }

    /// <summary>
    /// Contains <see cref="PropertySetSection"/> objects.
    /// </summary>
    public ArrayList<PropertySetSection> getSections() { return mSections; }

    /// <summary>
    /// Sometimes this is zero.
    ///
    /// MSDN: The class identifier is the CLSID of a class that can display and/or provide programmatic
    /// access to the property values. If there is no such class, it is recommended that the Format ID be
    /// used (see below), though a value of all zeros is also acceptable; the former simply allows for
    /// greater future extensibility.
    /// </summary>
    private /*final*/ Guid mClsid = Guid.Empty;    // Must be initialized for Java to work.
    private /*final*/ ArrayList<PropertySetSection> mSections = new ArrayList<PropertySetSection>();

    private static final int FMT_ID_OFFSET_PAIR_LENGTH = 16 + 4;
}

