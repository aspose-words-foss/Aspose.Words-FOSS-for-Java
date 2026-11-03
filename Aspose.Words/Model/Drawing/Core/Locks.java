// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Defines locks for a shape.
/// </summary>
/// <seealso cref="ShapeBase.Locks"/>
class Locks
{
    Locks(IShapeAttrSource parent)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;
    }

    /// <summary>
    /// Determines whether the handles of a shape can be edited.
    /// </summary>
    public boolean getAdjustHandlesLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_ADJUST_HANDLES); }
    public void setAdjustHandlesLocked(boolean value) { setAttr(ShapeAttr.LOCK_ADJUST_HANDLES, value); }

    /// <summary>
    /// Determines whether the aspect ratio of a shape can be changed by an editor.
    /// </summary>
    public boolean getAspectRatioLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_ASPECT_RATIO); }
    public void setAspectRatioLocked(boolean value) { setAttr(ShapeAttr.LOCK_ASPECT_RATIO, value); }

    /// <summary>
    /// Determines whether cropping will be allowed in an editor.
    /// </summary>
    public boolean getCroppingLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_CROPPING); }
    public void setCroppingLocked(boolean value) { setAttr(ShapeAttr.LOCK_CROPPING, value); }

    /// <summary>
    /// Determines whether shapes can be grouped in an editor.
    /// </summary>
    public boolean getGroupingLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_AGAINST_GROUPING); }
    public void setGroupingLocked(boolean value) { setAttr(ShapeAttr.LOCK_AGAINST_GROUPING, value); }
    
    /// <summary>
    /// Determines whether the position of a shape is locked in an editor.
    /// </summary>
    public boolean getPositionLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_POSITION); }
    public void setPositionLocked(boolean value) { setAttr(ShapeAttr.LOCK_POSITION, value); }

    /// <summary>
    /// Determines whether rotation of shapes will be allowed in an editor.
    /// </summary>
    public boolean getRotationLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_ROTATION); }
    public void setRotationLocked(boolean value) { setAttr(ShapeAttr.LOCK_ROTATION, value); }

    /// <summary>
    /// Determines whether the shape is selectable in an editor.
    /// </summary>
    public boolean getSelectionLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_AGAINST_SELECT); }
    public void setSelectionLocked(boolean value) { setAttr(ShapeAttr.LOCK_AGAINST_SELECT, value); }

    /// <summary>
    /// Determines whether the AutoShapes type can be changed by an editor.
    /// </summary>
    public boolean getShapeTypeLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_SHAPE_TYPE); }
    public void setShapeTypeLocked(boolean value) { setAttr(ShapeAttr.LOCK_SHAPE_TYPE, value); }

    /// <summary>
    /// Determines whether the text attached to a shape can be edited.
    /// </summary>
    public boolean getTextLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_TEXT); }
    public void setTextLocked(boolean value) { setAttr(ShapeAttr.LOCK_TEXT, value); }

    /// <summary>
    /// Determines whether the vertices of a path can be changed by an editor.
    /// </summary>
    public boolean getVerticesLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.LOCK_VERTICES); }
    public void setVerticesLocked(boolean value) { setAttr(ShapeAttr.LOCK_VERTICES, value); }

    private Object fetchAttr(int key)
    {
        return mParent.fetchShapeAttr(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setShapeAttr(key, value);
    }

    private /*final*/ IShapeAttrSource mParent;
}

