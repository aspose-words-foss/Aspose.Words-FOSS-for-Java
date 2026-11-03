// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2019 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Provides access to VBA project module.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-vba-macros/">Working with VBA Macros</a> documentation article.</para>
/// </summary>
public class VbaModule implements Cloneable
{
    /// <summary>
    /// Creates an empty module.
    /// </summary>
    public VbaModule()
    {
    }

    /// <summary>
    /// Creates an empty procedural module.
    /// </summary>        
    VbaModule(String name)
    {
        mName = name;
        mDocString = "";

        mType = VbaModuleType.PROCEDURAL_MODULE;
    }

    /// <summary>
    /// Reads module content. Returns null in case of any error.
    /// </summary>
    static VbaModule read(VbaRecordReader reader, MemoryStorage storage) throws Exception
    {
        VbaModule module = new VbaModule();

        try
        {
            module.setName(reader.readStringRecords((VbaRecord.MODULE_NAME & 0xFFFF), (VbaRecord.MODULE_NAME_UNICODE & 0xFFFF)));
            String streamName = reader.readStringRecords((VbaRecord.STREAM_NAME & 0xFFFF), (VbaRecord.STREAM_NAME_UNICODE & 0xFFFF));
            module.setDocString(reader.readStringRecords((VbaRecord.DOC_STRING & 0xFFFF), (VbaRecord.DOC_STRING_UNICODE & 0xFFFF)));

           
            int offset = reader.readInt32Record((VbaRecord.OFFSET & 0xFFFF));
            module.setHelpContext(reader.readInt32Record((VbaRecord.MODULE_HELP_CONTEXT & 0xFFFF)));      
            
            // CookieRecord - MUST be ignored on read. MUST be 0xFFFF on write
            reader.readInt16Record((VbaRecord.MODULE_COOKIE_RECORD_ID & 0xFFFF));

            while (true)
            {
                // There are can be TypeRecord, ReadOnlyRecord or PrivateRecord.
                // Ignore it for a while and look for Terminator record.
                /*ushort*/short id = reader.readUInt16();
                reader.readInt32();     // Reserved. MUST be 0x00000000. MUST be ignored.

                if (id == VbaRecord.TERMINATOR)
                    break;
            }

            module.extractSourceCode(storage.fetchStorage(VbaRecord.VBA_STORAGE_NAME).fetchStream(streamName), offset, reader.getEncoding());
            module.extractModuleType(storage.fetchStream(VbaRecord.PROJECT_STREAM_NAME), reader.getEncoding());

            // Save additional (currently unparsed) data if exists.
            module.mFormMemoryStorage = storage.getStorageSafe(module.getName());

            return module;
        }
        catch(Exception e) 
        {
            return null;
        }
    }

    /// <summary>
    /// Performs a copy of the <see cref="VbaModule"/>.
    /// </summary>
    /// <returns>The cloned <see cref="VbaModule"/>.</returns>
    public VbaModule deepClone()
    {
        VbaModule lhs = (VbaModule)memberwiseClone();
        
        // Set the parent project as null, it will be specified once module added to the project.
        lhs.setProject(null);
        if (getFormMemoryStorage() != null)
        {
            try
            {
                // Clone memory storage using temporary memory stream.
                FileSystem fs = new FileSystem(getFormMemoryStorage());
                MemoryStream ms = new MemoryStream();
                fs.save(ms);

                ms.setPosition(0);
                fs = new FileSystem(ms);
                lhs.mFormMemoryStorage = fs.getRoot();
            }
            catch(Exception e)
            {
                return lhs;
            }
        }

        return lhs;
    }

    /// <summary>
    /// Gets information about modules types for "PROJECT" stream.
    /// </summary>
    /// <remarks>
    /// 2.3.4.2.3.2.8 MODULETYPE Record contains either 0x0021 for a procedural module or 0x0022 for all other types.
    /// This method retrieves exact module type.
    /// </remarks>
    private void extractModuleType(MemoryStream stream, Encoding encoding) throws Exception
    {
        String stringProj = encoding.getString(stream.toArray());

        /*VbaModuleType*/int[] allTypes = getAllTypesOfVbaModule();

        for (/*VbaModuleType*/int type : allTypes)
        {
            String searchString = msString.concat(moduleTypeToString(type), "=", mName);
            if (msString.indexOf(stringProj, searchString, StringComparison.ORDINAL) >=0)
                mType = type;
        }
    }

    /// <summary>
    /// Returns the array of all types of VbaModuleType.
    /// </summary>        
    private static /*VbaModuleType*/int[] getAllTypesOfVbaModule()
    {
        /*VbaModuleType*/int[] allTypes = new /*VbaModuleType*/int[4];
        allTypes[0] = VbaModuleType.DOCUMENT_MODULE;
        allTypes[1] = VbaModuleType.PROCEDURAL_MODULE;
        allTypes[2] = VbaModuleType.CLASS_MODULE;
        allTypes[3] = VbaModuleType.DESIGNER_MODULE;
        return allTypes;
    }

    static String moduleTypeToString(/*VbaModuleType*/int type)
    {
        String typeString;
        switch (type)
        {
            case VbaModuleType.DOCUMENT_MODULE:
                typeString = "Document";
                break;
            case VbaModuleType.PROCEDURAL_MODULE:
                typeString = "Module";
                break;
            case VbaModuleType.CLASS_MODULE:
                typeString = "Class";
                break;
            case VbaModuleType.DESIGNER_MODULE:
                typeString = "BaseClass";
                break;
            default:
                throw new IllegalArgumentException("Unknown module type");
        }

        return typeString;
    }

    /// <summary>
    /// Extracts source code from the module stream.
    /// </summary>
    private void extractSourceCode(MemoryStream moduleStream, int offset, Encoding encoding) throws Exception
    {
        moduleStream.setPosition(offset);

        MemoryStream decompressedCode = new MemoryStream();
        RleCompressor.decompress(moduleStream, decompressedCode);

        mSourceCode = encoding.getString(decompressedCode.toArray());
    }

    /// <summary>
    /// Gets or sets VBA project module name.
    /// </summary>
    public String getName() { return mName; }
    public void setName(String value)
    {
        mName = value;
        if (mProject != null)
            mProject.markModified();
    }

    /// <summary>
    /// Gets or sets VBA project module source code.
    /// </summary>
    public String getSourceCode() { return mSourceCode; }
    public void setSourceCode(String value)
    {
        mSourceCode = value;
        if (mProject != null)
            mProject.markModified();
    }

    /// <summary>
    /// Specifies whether the module is a procedural module, document module, class module, or designer module.
    /// </summary>
    public /*VbaModuleType*/int getType() { return mType; }
    public void setType(/*VbaModuleType*/int value) { mType = value; }

    /// <summary>
    /// Parent VBA project.
    /// </summary>
    void setProject(VbaProject value) { mProject = value; }

    /// <summary>
    /// Specifies the description for the module.
    /// </summary>
    String getDocString() { return mDocString; }
    void setDocString(String value) { mDocString = value; }

    /// <summary>
    /// Specifies the Help topic identifier for the module.
    /// </summary>
    int getHelpContext() { return mHelpContext; }
    void setHelpContext(int value) { mHelpContext = value; }

    /// <summary>
    /// Stores extra module data (designer form) as raw memory storage.
    /// </summary>
    MemoryStorage getFormMemoryStorage() { return mFormMemoryStorage; }

    void write(VbaRecordWriter writer) throws Exception
    {
        writer.writeStringRecord(VbaRecord.MODULE_NAME, mName);
        writer.writeStringRecord(VbaRecord.MODULE_NAME_UNICODE, Encoding.getUnicode(), mName);

        writer.writeStringRecord(VbaRecord.STREAM_NAME, mName);
        writer.writeStringRecord(VbaRecord.STREAM_NAME_UNICODE, Encoding.getUnicode(), mName);

        writer.writeStringRecord(VbaRecord.DOC_STRING, mDocString);
        writer.writeStringRecord(VbaRecord.DOC_STRING_UNICODE, Encoding.getUnicode(), mDocString);

        writer.writeInt32Record(VbaRecord.OFFSET, 0);
        writer.writeInt32Record(VbaRecord.MODULE_HELP_CONTEXT, mHelpContext);

        // CookieRecord - MUST be ignored on read. MUST be 0xFFFF on write
        writer.writeUInt16(VbaRecord.MODULE_COOKIE_RECORD_ID);
        // Write size of ushort. 
        writer.writeInt32(2);            
        writer.writeUInt16(VbaRecord.COOKIE_RECORD);

        int moduleType = (mType == VbaModuleType.PROCEDURAL_MODULE) ? (VbaRecord.PROCEDURAL_MODULE & 0xFFFF) : (VbaRecord.NON_PROCEDURAL_MODULE & 0xFFFF);
        writer.writeUInt16((/*ushort*/short)moduleType);
        writer.writeInt32(0);

        writer.writeUInt16(VbaRecord.TERMINATOR);
        writer.writeInt32(0);
    }

    private String mName;
    private String mSourceCode;
    private String mDocString;
    private int mHelpContext;
    private /*VbaModuleType*/int mType;
    private MemoryStorage mFormMemoryStorage;

    private VbaProject mProject;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

