// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/10/2016 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Stores information about resource loading result.
/// </summary>
class ResourceLoadResult
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ResourceLoadResult(
        byte[] resourceBytes,
        /*ResourceLoadingAction*/int action)
    {
        mResourceBytes = resourceBytes;
        mLoadingAction = action;
    }

    /// <summary>
    /// Stores loaded resource bytes. 
    /// Can be null in case if resource loading was skipped by user, it was unknown resource type 
    /// or in case exception occurred during loading.
    /// </summary>
    byte[] getResourceBytes() { return mResourceBytes; }

    /// <summary>
    /// Indicates action that was used by user.
    /// </summary>
    /*ResourceLoadingAction*/int getLoadingAction() { return mLoadingAction; }

    private /*final*/ byte[] mResourceBytes;
    private /*final*/ /*ResourceLoadingAction*/int mLoadingAction;
}
