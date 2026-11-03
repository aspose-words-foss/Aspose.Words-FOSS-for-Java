// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides data for the field updating progress event.
/// </summary>
public final class FieldUpdatingProgressArgs
{
    FieldUpdatingProgressArgs(int totalFieldsCount, int updatedFieldsCount, boolean updateCompleted)
    {
        mTotalFieldsCount = totalFieldsCount;
        mUpdatedFieldsCount = updatedFieldsCount;
        mUpdateCompleted = updateCompleted;
    }

    /// <summary>
    /// Gets the total fields count to be updated.
    /// </summary>
    /// <remarks>
    /// The value is not constant and may be increased during updating process.
    /// </remarks>
    public int getTotalFieldsCount() { return mTotalFieldsCount; };

    private  int mTotalFieldsCount;

    /// <summary>
    /// Gets the number of updated fields.
    /// </summary>
    public int getUpdatedFieldsCount() { return mUpdatedFieldsCount; };

    private  int mUpdatedFieldsCount;

    /// <summary>
    /// Gets a value indicating whether field updating is completed.
    /// </summary>
    public boolean getUpdateCompleted() { return mUpdateCompleted; };

    private  boolean mUpdateCompleted;
}

