// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2011 by Kamimura Andrey

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides data for the <see cref="IResourceLoadingCallback.ResourceLoading"/> method.
/// </summary>
public class ResourceLoadingArgs
{
    /// <summary>
    /// Internal ctor.
    /// </summary>
    /// <param name="absoluteUri">Absolute URI of the resource. Aspose.Words uses it to download the resource by default.</param>
    /// <param name="originalUri">Original URI of the resource as specified in imported document.</param>
    /// <param name="resourceType">Type of resource.</param>
    ResourceLoadingArgs(String absoluteUri, String originalUri, /*ResourceType*/int resourceType)
    {
        mOriginalUri = originalUri;
        mResourceType = resourceType;
        setUri(absoluteUri);
    }

    /// <summary>
    /// Type of resource.
    /// </summary>
    public /*ResourceType*/int getResourceType() { return mResourceType; };

    private /*ResourceType*/int mResourceType;

    /// <summary>
    /// <para>URI of the resource which is used for downloading
    /// if <see cref="IResourceLoadingCallback.ResourceLoading"/>
    /// returns <see cref="ResourceLoadingAction.Default"/>.</para>
    /// <para>Initially it's set to absolute URI of the resource,
    /// but user can redefine it to any value.</para>
    /// </summary>
    public String getUri() { return mUri; }; public void setUri(String value) { mUri = value; };

    private String mUri;

    /// <summary>
    /// Original URI of the resource as specified in imported document.
    /// </summary>
    public String getOriginalUri() { return mOriginalUri; };

    private  String mOriginalUri;

    /// <summary>
    /// Sets user provided data of the resource which is used
    /// if <see cref="IResourceLoadingCallback.ResourceLoading"/>
    /// returns <see cref="ResourceLoadingAction.UserProvided"/>.
    /// </summary>
    public void setData(byte[] data)
    {
        mData = data;
    }

    /// <summary>
    /// Returns user provided data of the resource.
    /// </summary>
    byte[] getData()
    {
        return mData;
    }

    /// <summary>
    /// Returns <c>true</c> if data is empty.
    /// </summary>
    boolean isDataEmpty() { return (mData == null) || (mData.length == 0); }

    private byte[] mData;
}

