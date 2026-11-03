// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, listens to field update events.
/// </summary>
interface IFieldUpdateListener
{
    /// <summary>
    /// Called when a field is about to be updated. Also controls whether it should happen.
    /// </summary>
    /// <returns>A value indicating what to do with the field.</returns>
    public FieldUpdateListenerResponse notifyUpdating(Field field, Field parentField) throws Exception;

    /// <summary>
    /// Called when a field has been updated with specified strategy.
    /// </summary>
    public void notifyUpdated(FieldUpdateContext context, /*FieldUpdateStrategy*/int strategy) throws Exception;

    /// <summary>
    /// Called when a field is updating.
    /// </summary>
    /// <returns>
    /// Returns <c>true</c> if the field update was performed by listener and the field should not be updated in common way.
    /// </returns>
    public boolean update(FieldUpdateContext context) throws Exception;
}

