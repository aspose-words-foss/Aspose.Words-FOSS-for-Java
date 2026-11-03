// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements pooled attribute collection.
/// </summary>
class InternPoolItem
{
    InternPoolItem(InternManager internManager)
    {
        mInternManager = internManager;
    }

    InternManager getInternManager() { return mInternManager; }

    AttrCollection Pr;
    int RefCount;
    int Id;

    private /*final*/ InternManager mInternManager;
}

