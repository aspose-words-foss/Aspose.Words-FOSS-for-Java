// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2011 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Classes that have comment or editable range id should implement this interface.
/// </summary>
interface INodeWithAnnotationId
{
    /// <summary>
    /// These methods have such ugly names to avoid name clashes during autoporting to Java.
    /// </summary>
    public int getIdInternal(); public void setIdInternal(int value);

    /// <summary>
    /// Parent comment Id. <see cref="Comment.NoParent" /> value means that comment has no parent.
    /// </summary>
    public int getParentIdInternal(); public void setParentIdInternal(int value);
}

