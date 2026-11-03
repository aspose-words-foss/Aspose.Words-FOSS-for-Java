// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a reference to an Automation type library.
/// </summary>
/// <dev>
/// This is an implementation of [MS-OVBA] 2.3.4.2.2.5 REFERENCEREGISTERED Record.
/// </dev>
class VbaReferenceRegistered extends VbaReference
{
    /// <summary>
    /// Initializes a new VbaReferenceRegistered object with a specified name.
    /// </summary>
    VbaReferenceRegistered(String name)
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

        byte[] libIdbytes = writer.getEncoding().getBytes(getLibId());
        int totalSize = libIdbytes.length + 10;
        writer.writeInt32(totalSize);
        writer.writeString(getLibId());
        writer.writeInt32(0);
        writer.writeInt16((short)0); // casting added for Java
    }

    /// <summary>
    /// Reads reference data from a specified reader.
    /// </summary>
    /*override*/ void readCore(VbaRecordReader reader) throws Exception
    {
        reader.readInt32(); // ignored
        mLibId = reader.readString(); // libId
        reader.readInt32(); // reserved1
        reader.readInt16(); // reserved2
    }

    /// <summary>
    /// Gets <see cref="VbaReferenceType"/> object that indicates the type of reference that a VbaReference object represents.
    /// </summary>
    public /*override*/ /*VbaReferenceType*/int getType() { return VbaReferenceType.REGISTERED; }

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
    public /*override*/ String getLibId() { return mLibId; }

    private String mLibId;
}

