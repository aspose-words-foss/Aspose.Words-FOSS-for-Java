// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Implements fields updating tracking state.
/// </summary>
class FieldUpdateProgressContext
{
    FieldUpdateProgressContext(IFieldUpdatingProgressCallback callback, Object owner)
    {
        mOwner = owner;
        mCallback = callback;
    }

    Object getOwner() { return mOwner; };

    private Object mOwner;

    void notifyInternal()
    {
        if(!mHasChanges)
            return;

        mHasChanges = false;

        notifyCore(false);
    }

    void onFieldToBeUpdated()
    {
        mTotal++;
        mHasChanges = true;
    }

    void onFieldUpdated()
    {
        mUpdated++;
        mHasChanges = true;
    }

    void onUpdateCompleted()
    {
        Debug.assertTrue(mTotal == mUpdated, "mTotal != mUpdated");

        notifyCore(true);
    }

    private void notifyCore(boolean completed)
    {
        if (mCallback == null)
            return;

        mCallback.notify(new FieldUpdatingProgressArgs(mTotal, mUpdated, completed));
    }

    private int mTotal;
    private int mUpdated;
    private boolean mHasChanges;

    private /*final*/ IFieldUpdatingProgressCallback mCallback;
}

