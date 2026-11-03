// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-OLEDS] 2.2.4 ObjectHeader.
/// </summary>
class ObjectHeader
{
    ObjectHeader(/*OleObjectType*/int oleObjectType, String className, String topicName, String itemName)
    {
        mOleObjectType = oleObjectType;
        mClassName = className;
        mTopicName = topicName;
        mItemName = itemName;
    }

    static ObjectHeader read(BinaryReader reader) throws Exception
    {
        // Ignore oleVersion.
        reader.readInt32();
        int formatId = reader.readInt32();

        /*OleObjectType*/int oleObjectType;

        switch (formatId)
        {
            case 0x01:
            {
                oleObjectType = OleObjectType.LINKED;
                break;
            }
            case 0x02:
            case 0x03:
            {
                oleObjectType = OleObjectType.EMBEDDED;
                break;
            }
            default:
            {
                return null;
            }
        }

        String className = OleUtil.readLengthPrefixedAnsiString(reader);
        String topicName = "";
        String itemName = "";

        if (oleObjectType == OleObjectType.LINKED)
        {
            topicName = OleUtil.readLengthPrefixedAnsiString(reader);
            itemName = OleUtil.readLengthPrefixedAnsiString(reader);
        }
        else
        {
            // If the ObjectHeader structure is contained by an EmbeddedObject structure, the TopicName and ItemName fields SHOULD 
            // contain an empty string and MUST be ignored on processing.
            reader.readInt32();
            reader.readInt32();
        }

        return new ObjectHeader(oleObjectType, className, topicName, itemName);
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(OleUtil.OLE_VERSION);
        writer.writeInt32(mOleObjectType == OleObjectType.LINKED ? 0x01 : 0x02);

        OleUtil.writeLengthPrefixedAnsiStringCore(writer, mClassName);
        OleUtil.writeLengthPrefixedAnsiString(writer, mTopicName);
        OleUtil.writeLengthPrefixedAnsiString(writer, mItemName);
    }

    /// <summary>
    /// Possible values are limited to OleObjectType.Linked and OleObjectType.Embedded.
    /// </summary>
    /*OleObjectType*/int getOleObjectType() { return mOleObjectType; }

    /// <summary>
    /// Contains a value identifying the creating application.
    /// </summary>
    String getClassName() { return mClassName; }

    /// <summary>
    /// Contains the absolute path name of the linked file. 
    /// The path name either MUST start with a drive letter or 
    /// MUST be in the Universal Naming Convention (UNC) format.
    /// </summary>
    String getTopicName() { return mTopicName; }

    /// <summary>
    /// Contains a string that is used by the application or 
    /// higher-level protocol to identify the item within the file to which is being linked.
    /// </summary>
    String getItemName() { return mItemName; }

    private /*final*/ /*OleObjectType*/int mOleObjectType;
    private /*final*/ String mClassName;
    private /*final*/ String mTopicName;
    private /*final*/ String mItemName;
}

