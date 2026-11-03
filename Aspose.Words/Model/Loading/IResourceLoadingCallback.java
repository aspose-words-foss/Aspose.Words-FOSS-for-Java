// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2011 by Kamimura Andrey

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to control how Aspose.Words loads external resource when 
/// importing a document and inserting images using <see cref="DocumentBuilder"/>.
/// </summary>
public interface IResourceLoadingCallback
{
    /// <summary>
    /// Called when Aspose.Words loads any external resource.
    /// </summary>
    public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args) throws Exception;
}

