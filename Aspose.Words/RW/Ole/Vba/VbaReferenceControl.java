// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Specifies a reference to a twiddled type library and its extended type library.
/// </summary>
/// <dev>
/// This is an implementation of [MS-OVBA] 2.3.4.2.2.3 REFERENCECONTROL Record.
/// </dev>
class VbaReferenceControl extends VbaReference
{
    /// <summary>
    /// Initializes a new VbaReferenceControl object with a specified name.
    /// </summary>
    VbaReferenceControl(String name)
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
        writeTypeLibraries(writer);
    }

    /// <summary>
    /// Writes twiddled type library and its extended type library.
    /// </summary>
    /// <remarks>
    /// See [MS-OVBA] 2.3.4.2.2.3 REFERENCECONTROL Record.
    /// </remarks>
    void writeTypeLibraries(VbaRecordWriter writer) throws Exception
    {
        byte[] libIdTwiddledbytes = writer.getEncoding().getBytes(getLibIdTwiddled());
        int totalSize = libIdTwiddledbytes.length + 10;
        writer.writeInt32(totalSize);
        writer.writeString(getLibIdTwiddled());
        writer.writeInt32(0);
        writer.writeInt16((short)0); // casting added for Java

        writer.writeUInt16(VbaRecord.REFERENCE_RESERVED);

        byte[] libIdExtendedbytes = writer.getEncoding().getBytes(getLibIdExtended());
        totalSize = libIdExtendedbytes.length + 30;
        writer.writeInt32(totalSize);
        writer.writeString(getLibIdExtended());
        writer.writeInt32(0);
        writer.writeInt16((short)0); // casting added for Java
        writer.writeBytes(getOriginalTypeLib());
        writer.writeInt32(getCookie());
    }

    /// <summary>
    /// Reads reference data from a specified reader.
    /// </summary>
    /*override*/ void readCore(VbaRecordReader reader) throws Exception
    {
        reader.readInt32(); // ignored
        setLibIdTwiddled(reader.readString());
        reader.readInt32(); // reserved1
        reader.readInt16(); // reserved2

        if (reader.peekId() == (VbaRecord.REFERENCE_NAME & 0xFFFF))
        {
            setNameExtended(reader.readStringRecords((VbaRecord.REFERENCE_NAME & 0xFFFF), (VbaRecord.REFERENCE_NAME_UNICODE & 0xFFFF)));
        }

        int id = reader.readInt16();
        Debug.assertTrue(id == (VbaRecord.REFERENCE_RESERVED & 0xFFFF));
        reader.readInt32(); // ignored
        setLibIdExtended(reader.readString());
        reader.readInt32(); // reserved1
        reader.readInt16(); // reserved2
        setOriginalTypeLib(reader.readBytes(16)); // originalTypeLib
        setCookie(reader.readInt32()); // cookie
    }

    /// <summary>
    /// Gets <see cref="VbaReferenceType"/> object that indicates the type of reference that a VbaReference object represents.
    /// </summary>
    public /*override*/ /*VbaReferenceType*/int getType() { return VbaReferenceType.CONTROL; }

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
    public /*override*/ String getLibId() { return getLibIdTwiddled(); }

    /// <summary>
    /// Specifies the name of the extended type library. This property is optional.
    /// </summary>
    String getNameExtended() { return mNameExtended; }; private void setNameExtended(String value) { mNameExtended = value; };

    private String mNameExtended;

    /// <summary>
    /// Specifies a twiddled type library’s identifier that MUST conform to the ABNF grammar LibidReference.
    /// </summary>
    String getLibIdTwiddled() { return mLibIdTwiddled; }; private void setLibIdTwiddled(String value) { mLibIdTwiddled = value; };

    private String mLibIdTwiddled;

    /// <summary>
    /// Specifies the extended type library’s identifier.
    /// </summary>
    String getLibIdExtended() { return mLibIdExtended; }; private void setLibIdExtended(String value) { mLibIdExtended = value; };

    private String mLibIdExtended;

    /// <summary>
    /// A GUID that specifies the Automation type library the extended type library was generated from.
    /// </summary>
    byte[] getOriginalTypeLib() { return mOriginalTypeLib; }; private void setOriginalTypeLib(byte[] value) { mOriginalTypeLib = value; };

    private byte[] mOriginalTypeLib;

    /// <summary>
    /// An unsigned integer that specifies the extended type library's cookie. Must be unique for each
    /// referenceControl in the project with the same OriginalTypeLib.
    /// </summary>
    private int getCookie() { return mCookie; }; private void setCookie(int value) { mCookie = value; };

    private int mCookie;
}

