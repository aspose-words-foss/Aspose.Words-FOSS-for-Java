// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to control how Aspose.Words saves external resources (images, fonts and css) when 
/// saving a document to fixed page HTML or SVG.
/// </summary>
public interface IResourceSavingCallback
{
    /// <summary>
    /// Called when Aspose.Words saves an external resource to fixed page HTML or SVG formats.
    /// </summary>
    public void resourceSaving(ResourceSavingArgs args) throws Exception;
}

