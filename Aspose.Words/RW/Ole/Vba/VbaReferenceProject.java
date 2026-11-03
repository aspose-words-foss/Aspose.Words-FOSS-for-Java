// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a reference to an external VBA project.
/// </summary>
/// <dev>
/// This is an implementation of [MS-OVBA] 2.3.4.2.2.6 REFERENCEPROJECT Record.
/// </dev>
class VbaReferenceProject extends VbaReference
{
    /// <summary>
    /// Initializes a new VbaReferenceProject object with a specified name.
    /// </summary>
    VbaReferenceProject(String name)
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

        byte[] libIdAbsolutebytes = writer.getEncoding().getBytes(getLibIdAbsolute());
        byte[] libIdRelativebytes = writer.getEncoding().getBytes(getLibIdRelative());
        int totalSize = libIdAbsolutebytes.length + libIdRelativebytes.length + 14;
        writer.writeInt32(totalSize);
        writer.writeString(getLibIdAbsolute());
        writer.writeString(getLibIdRelative());
        writer.writeUInt32(getMajorVersion());
        writer.writeUInt16(getMinorVersion());
    }

    /// <summary>
    /// Reads reference data from a specified reader.
    /// </summary>
    /*override*/ void readCore(VbaRecordReader reader) throws Exception
    {
        reader.readInt32(); // ignored
        setLibIdAbsolute(reader.readString());
        setLibIdRelative(reader.readString());
        setMajorVersion(reader.readUInt32());
        setMinorVersion(reader.readUInt16());
    }

    /// <summary>
    /// Gets <see cref="VbaReferenceType"/> object that indicates the type of reference that a VbaReference object represents.
    /// </summary>
    public /*override*/ /*VbaReferenceType*/int getType() { return VbaReferenceType.PROJECT; }

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
    public /*override*/ String getLibId() { return getLibIdAbsolute(); }

    /// <summary>
    /// Specifies the referenced VBA project’s identifier with an absolute path.
    /// </summary>
    String getLibIdAbsolute() { return mLibIdAbsolute; }; private void setLibIdAbsolute(String value) { mLibIdAbsolute = value; };

    private String mLibIdAbsolute;

    /// <summary>
    /// Specifies the referenced VBA project’s identifier with a relative path, that is relative to the current VBA project.
    /// </summary>
    String getLibIdRelative() { return mLibIdRelative; }; private void setLibIdRelative(String value) { mLibIdRelative = value; };

    private String mLibIdRelative;

    /// <summary>
    /// Specifies the major version of the referenced VBA project.
    /// </summary>
    /*uint*/int getMajorVersion() { return mMajorVersion; }; private void setMajorVersion(/*uint*/int value) { mMajorVersion = value; };

    private /*uint*/int mMajorVersion;

    /// <summary>
    /// Specifies the minor version of the external VBA project.
    /// </summary>
    /*ushort*/short getMinorVersion() { return mMinorVersion; }; private void setMinorVersion(/*ushort*/short value) { mMinorVersion = value; };

    private /*ushort*/short mMinorVersion;
}

