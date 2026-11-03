// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2016 by Alexey Morozov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements structured storage.
/// </summary>
/// <remarks>
/// This almost duplicates <see cref="FileSystem"/> class but deals with <see cref="CompoundStream"/> class 
/// instead of MemoryStream.
/// </remarks>
public class CompoundStorage extends SortedList<String, Object>
{
    /// <summary>
    /// Creates a storage with an empty guid.
    /// </summary>
    public CompoundStorage()
    {
    	this(Guid.Empty);
	
    }

    /// <summary>
    /// Creates a storage with a specified guid.
    /// </summary>
    public CompoundStorage(Guid clsid)
    {
    	super(new MemoryStorageNameComparer());
	
        mClsid = clsid;
    }

    public void add(String name, CompoundStream stream)
    {
        super.add(name, stream);
    }

    public void add(String name, CompoundStorage storage)
    {
        super.add(name, storage);
    }

    /// <summary>
    /// Gets a stream by the specified name or null. Sets the stream position to 0.
    /// </summary>
    public CompoundStream getStream(String name) throws Exception
    {
        Object result = DictionaryExtension.getValueOrNull(this, name);
        CompoundStream compoundStream = ms.as(result, CompoundStream.class);
        if (compoundStream != null)
            compoundStream.setPosition(0);
        return compoundStream;
    }

    /// <summary>
    /// Gets a stream by the specified name or throws. Sets the stream position to 0.
    /// </summary>
    public CompoundStream fetchStream(String name) throws Exception
    {
        CompoundStream result = getStream(name);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find stream '{0}' in the storage.", name));
        return result;
    }

    public CompoundStorage getStorage(String name)
    {
        Object result = DictionaryExtension.getValueOrNull(this, name);
        return ms.as(result, CompoundStorage.class);
    }

    public CompoundStorage fetchStorage(String name)
    {
        CompoundStorage result = getStorage(name);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find storage '{0}'.", name));
        return result;
    }

    /// <summary>
    /// A storage can optionally have a clsid.
    /// </summary>
    public Guid getClsid() { return mClsid; }
    public void setClsid(Guid value) { mClsid = value; }

    private Guid mClsid = Guid.Empty;    // Must be initialized for Java to work.
}

