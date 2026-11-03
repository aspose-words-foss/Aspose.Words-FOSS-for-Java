// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class FieldUpdateListenerResponse
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldUpdateListenerResponse(
        Field field,
        /*FieldUpdatePreferredStageStrategy*/int preferredStageStrategy,
        boolean requiresImmediateUpdate,
        /*FieldUpdateCleanupActions*/int baseCleanupActions)
    {
        mField = field;
        mPreferredStageStrategy = preferredStageStrategy;
        mRequiresImmediateUpdate = requiresImmediateUpdate;
        mBaseCleanupActions = baseCleanupActions;
    }

    /// <summary>
    /// Gets a field to update.
    /// </summary>
    Field getField() { return mField; };

    private Field mField;

    /// <summary>
    /// Gets a value specifying how to update the corresponding field.
    /// </summary>
    /*FieldUpdatePreferredStageStrategy*/int getPreferredStageStrategy() { return mPreferredStageStrategy; };

    private /*FieldUpdatePreferredStageStrategy*/int mPreferredStageStrategy;

    /// <summary>
    /// Gets a value indicating whether the corresponding field should be updated on the stage where it is
    /// supposed to regardless of any conditions.
    /// </summary>
    boolean getRequiresImmediateUpdate() { return mRequiresImmediateUpdate; };

    private boolean mRequiresImmediateUpdate;

    /// <summary>
    /// Gets base cleanup actions to be applied to the corresponding field.
    /// </summary>
    /*FieldUpdateCleanupActions*/int getBaseCleanupActions() { return mBaseCleanupActions; };

    private /*FieldUpdateCleanupActions*/int mBaseCleanupActions;

    /// <summary>
    /// An instance of the class representing "no response" situation. Note, that its <see cref="Field"/> value is null.
    /// </summary>
    static /*final*/ FieldUpdateListenerResponse DEFAULT = new FieldUpdateListenerResponse(
        null,
        FieldUpdatePreferredStageStrategy.MAIN_LOOP,
        false,
        FieldUpdateCleanupActions.NONE);
}

