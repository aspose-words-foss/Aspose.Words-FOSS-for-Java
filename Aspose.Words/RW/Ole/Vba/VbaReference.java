// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/09/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements a reference to an Automation type library or VBA project.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-vba-macros/">Working with VBA Macros</a> documentation article.</para>
/// </summary>
public abstract class VbaReference implements Cloneable
{
    /// <summary>
    /// Initializes a new VbaReference object with a specified name.
    /// </summary>
    VbaReference(String name)
    {
        mName = name;
    }

    /// <summary>
    /// Performs a copy of the <see cref="VbaReference"/>.
    /// </summary>
    /// <returns>The cloned <see cref="VbaReference"/>.</returns>
    VbaReference deepClone()
    {
        return (VbaReference)memberwiseClone();
    }

    /// <summary>
    /// Reads VbaReference from a specified reader.
    /// </summary>
    static VbaReference read(VbaRecordReader reader) throws Exception
    {
        String name = reader.readStringRecords((VbaRecord.REFERENCE_NAME & 0xFFFF), (VbaRecord.REFERENCE_NAME_UNICODE & 0xFFFF));
        /*VbaReferenceType*/int type = (/*VbaReferenceType*/int)(reader.readUInt16() & 0xFFFF);

        // Create concrete empty reference in accordance with read type.
        VbaReference vbaReference = create(type, name);

        // Populate reference with a related data from the reader.
        vbaReference.readCore(reader);

        return vbaReference;
    }

    /// <summary>
    /// Writes the reference into a specified writer.
    /// </summary>
    abstract void write(VbaRecordWriter writer) throws Exception;

    /// <summary>
    ///  Writes <see cref="VbaRecord.Name"/> and <see cref="VbaRecord.ReferenceNameUnicode"/> to a specified writer.
    /// </summary>
    void writeNames(VbaRecordWriter writer) throws Exception
    {
        writer.writeStringRecord(VbaRecord.REFERENCE_NAME, getName());
        writer.writeStringRecord(VbaRecord.REFERENCE_NAME_UNICODE, Encoding.getUnicode(), getName());
    }

    /// <summary>
    /// Reads reference data from a specified reader.
    /// </summary>
    abstract void readCore(VbaRecordReader reader) throws Exception;

    /// <summary>
    /// Creates VbaReference instance with a specified name and type.
    /// </summary>
    private static VbaReference create(/*VbaReferenceType*/int type, String name)
    {
        switch (type)
        {
            case VbaReferenceType.REGISTERED:
                return new VbaReferenceRegistered(name);
            case VbaReferenceType.PROJECT:
                return new VbaReferenceProject(name);
            case VbaReferenceType.ORIGINAL:
                return new VbaReferenceOriginal(name);
            case VbaReferenceType.CONTROL:
                return new VbaReferenceControl(name);
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + msString.format("Unexpected VBA reference type: {0}", type));
        }
    }


    /// <summary>
    /// Gets <see cref="VbaReferenceType"/> object that indicates the type of reference that a <see cref="VbaReference"/> object represents.
    /// </summary>
    public abstract /*VbaReferenceType*/int getType();

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
    public abstract String getLibId();

    /// <summary>
    /// Specifies the name of a referenced VBA project or Automation type library.
    /// </summary>
    String getName() { return mName; }

    private /*final*/ String mName;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

