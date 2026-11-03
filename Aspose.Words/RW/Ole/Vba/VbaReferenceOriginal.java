// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Specifies the identifier of the Automation type library the containing
/// reference control twiddled type library was generated from.
/// </summary>
/// <dev>
/// This is an implementation of [MS-OVBA] 2.3.4.2.2.4 REFERENCEORIGINAL Record.
/// </dev>
class VbaReferenceOriginal extends VbaReferenceControl
{

    /// <summary>
    /// Initializes a new VbaReferenceOriginal object with a specified name.
    /// </summary>
    VbaReferenceOriginal(String name)
    {
    	super(name);
	
    }

    /// <summary>
    /// Writes the reference into a specified writer.
    /// </summary>
    /*override*/ void write(VbaRecordWriter writer) throws Exception
    {
        writeNames(writer);
        writer.writeUInt16((/*ushort*/short)getType());
        writer.writeString(getLibIdOriginal());

         // WORDSNET-22807 Write reference to a twiddled type library and its extended type library.
        if (StringUtil.hasChars(getNameExtended()))
        {
            writer.writeUInt16(VbaRecord.REFERENCE_CONTROL_ID);
            writeTypeLibraries(writer);
        }
    }

    /// <summary>
    /// Reads reference data from a specified reader.
    /// </summary>
    /*override*/ void readCore(VbaRecordReader reader) throws Exception
    {
        setLibIdOriginal(reader.readString());
        if (reader.peekId() == (VbaRecord.REFERENCE_CONTROL_ID & 0xFFFF))
        {
            reader.readUInt16();
            super.readCore(reader);
        }
    }

    /// <summary>
    /// Gets <see cref="VbaReferenceType"/> object that indicates the type of reference that a VbaReference object represents.
    /// </summary>
    public /*override*/ /*VbaReferenceType*/int getType() { return VbaReferenceType.ORIGINAL; }

    /// <summary>
    /// Gets a string value containing the identifier of an Automation type library.
    /// </summary>
    /// <remarks>
    /// Depending on reference type, the value of this property can be:
    /// <list type="bullet">
    /// <item>a LibidReference specified at 2.1.1.8 LibidReference of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/3737ef6e-d819-4186-a5f2-6e258ddf66a5</item>
    /// <item>a ProjectReference specified at 2.1.1.12 ProjectReference of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/9a45ac1a-f1ff-4ebd-958e-537701aa8131</item>
    /// </list>
    ///  </remarks>
    public /*override*/ String getLibId() { return getLibIdOriginal(); }

    /// <summary>
    /// Specifies the identifier of the Automation type library a REFERENCECONTROL was generated from.
    /// </summary>
    String getLibIdOriginal() { return mLibIdOriginal; }; private void setLibIdOriginal(String value) { mLibIdOriginal = value; };

    private String mLibIdOriginal;
}

