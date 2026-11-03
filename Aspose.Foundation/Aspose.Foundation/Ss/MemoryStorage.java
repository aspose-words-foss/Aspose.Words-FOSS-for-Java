// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2005 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;


/// <summary>
/// When I read all structured storage into memory, I read it into MemoryStream and MemoryStorage objects.
/// This represents a directory that can contain other files (streams and storages).
/// The key is the name of the element.
/// </summary>
public class MemoryStorage extends SortedList<String, Object>
{
    /// <summary>
    /// Creates a storage with an empty guid.
    /// </summary>
    public MemoryStorage()
    {
    	this(Guid.Empty);
	
    }

    /// <summary>
    /// Creates a storage with a specified guid.
    /// </summary>
    public MemoryStorage(Guid clsid)
    {
    	super(new MemoryStorageNameComparer());
	
        mClsid = clsid;
    }

    public void add(String name, MemoryStream stream)
    {
        super.add(name, stream);
    }

    public void add(String name, MemoryStorage storage)
    {
        super.add(name, storage);
    }

    public String getKey(int index)
    {
        return getKeys().get(index);
    }

    public Object getByIndex(int index)
    {
        return getValues().get(index);
    }

    /// <summary>
    /// Gets a stream by the specified name or null. Sets the stream position to 0.
    /// </summary>
    public MemoryStream getStreamZeroPositioned(String name) throws Exception
    {
        Object result = DictionaryExtension.getValueOrNull(this, name);
        MemoryStream memoryStream = ms.as(result, MemoryStream.class);
        if (memoryStream != null)
            memoryStream.setPosition(0);
        return memoryStream;
    }

    /// <summary>
    /// Gets a stream by the specified name or null.
    /// </summary>
    public MemoryStream getStreamSafe(String name)
    {
        Object result = DictionaryExtension.getValueOrNull(this, name);
        return ms.as(result, MemoryStream.class);
    }

    /// <summary>
    /// Gets a stream by the specified name or throws. Sets the stream position to 0.
    /// </summary>
    public MemoryStream fetchStream(String name) throws Exception
    {
        MemoryStream result = getStreamZeroPositioned(name);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find stream '{0}' in the storage.", name));
        return result;
    }

    public MemoryStorage getStorageSafe(String name)
    {
        Object result = DictionaryExtension.getValueOrNull(this, name);
        return ms.as(result, MemoryStorage.class);
    }

    public MemoryStorage fetchStorage(String name)
    {
        MemoryStorage result = getStorageSafe(name);
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

