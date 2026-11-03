// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-OLEDS] 2.2.6 LinkedObject.
/// </summary>
class Ole1LinkedObject extends Ole1Object
{
    Ole1LinkedObject(OleFormat oleFormat, byte[] metafileData) throws Exception
    {
        mClassName = oleFormat.getProgId();
        mTopicName = oleFormat.getSourceFullName();
        mItemName = oleFormat.getSourceItem();
        mAutoUpdate = oleFormat.getAutoUpdate();

        mPresentationObject = (metafileData != null) ? 
            (PresentationObject)new PresentationObjectMetafile(metafileData) : 
            new PresentationObjectEmpty();
    }

    Ole1LinkedObject(ObjectHeader objectHeader, BinaryReader reader) throws Exception
    {
        mClassName = objectHeader.getClassName();
        mTopicName = objectHeader.getTopicName();
        mItemName = objectHeader.getItemName();

        mNetworkName = OleUtil.readLengthPrefixedAnsiString(reader);

        int reserved = reader.readInt32();
        Debug.assertTrue(reserved == 0x00);

        int linkUpdateOptions = reader.readInt32();
        // There is something wrong with spec. 
        // It defines that linkUpdateOptions is OLEUPDATE type which defined as:
        // OLEUPDATE_ALWAYS   = 1
        // OLEUPDATE_ONCALL   = 3
        // But values only seen is 0 and 2.
        mAutoUpdate = linkUpdateOptions == 0x00;
        Debug.assertTrue(linkUpdateOptions == 0x00 || linkUpdateOptions == 0x02);

        mPresentationObject = PresentationObject.read(reader);
    }

    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        ObjectHeader objectHeader = new ObjectHeader(OleObjectType.LINKED, mClassName, mTopicName, mItemName);
        objectHeader.write(writer);

        OleUtil.writeLengthPrefixedAnsiString(writer, mNetworkName);
        // Reserved. Must be 0x00000000.
        writer.writeInt32((int)0x00);

        writer.writeInt32(mAutoUpdate ? 0x00 : 0x02);
        
        mPresentationObject.write(writer);
    }

    /*override*/ /*OleObjectType*/int getObjectType() { return OleObjectType.LINKED; }

    /// <summary>
    /// Specifies the absolute path name of the linked file.
    /// </summary>
    String getTopicName() { return mTopicName; }

    /// <summary>
    /// Specifies a string that is used by the application or higher-level protocol 
    /// to identify the item within the file to which is being linked.
    /// </summary>
    String getItemName() { return mItemName; }

    /// <summary>
    /// If the <see cref="TopicName" /> field contains a path that starts with a drive letter and if the drive letter is for a remote drive, 
    /// the NetworkName field MUST contain the path name of the linked file in the Universal Naming Convention (UNC) format.
    /// </summary>
    String getNetworkName() { return mNetworkName; }

    boolean getAutoUpdate() { return mAutoUpdate; }

    /// <summary>
    /// Specifies OLE1.0 presentation data to display this object in container applications.
    /// </summary>
    PresentationObject getPresentationObject() { return mPresentationObject; }

    private /*final*/ String mTopicName;
    private /*final*/ String mItemName;
    private /*final*/ String mNetworkName;
    private /*final*/ boolean mAutoUpdate;
    private /*final*/ PresentationObject mPresentationObject;
}

