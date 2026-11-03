// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A boolean that can also act as toggle.
/// </summary>
/// <remarks>
/// In the model this class is used for all "boolean" attributes of character properties.
/// Everywhere in the code (default attribute values, values stored in collection,
/// expanded attribute values etc) AttrBoolEx are used. AttrBoolEx are only converted to boolean
/// in the public properties of the Font object and in the writers that need to write booleans.
/// </remarks>
class AttrBoolEx extends Attr
{
    /// <summary>
    /// We only have four global instances. No public creation of instances of this class is allowed.
    /// </summary>
    private AttrBoolEx(int value)
    {
        mValue = value;
    }

    /// <summary>
    /// Creates an AttrBoolEx from a boolean.
    /// </summary>
    static AttrBoolEx fromBool(boolean value)
    {
        return (value) ? TRUE : FALSE;
    }

    /// <summary>
    /// This is the "raw" value. Should only be used in the binary DOC format I suppose.
    /// </summary>
    int getValue() { return mValue; }

    /// <summary>
    /// Converts a true or false AttrBoolEx into a boolean.
    /// If the boolex value is "same" or "toggle", throws an exception.
    /// </summary>
    boolean toBool()
    {
        switch (mValue)
        {
            case TRUE_VALUE:
                return true;
            case FALSE_VALUE:
                return false;
            default:
                throw new IllegalStateException("Not expected other boolex values here.");
        }
    }

    /// <summary>
    /// Inverts a boolex value.
    /// If the boolex value is "same" or "toggle", throws an exception.
    /// </summary>
    AttrBoolEx invert()
    {
        switch (mValue)
        {
            case TRUE_VALUE:
                return FALSE;
            case FALSE_VALUE:
                return TRUE;
            default:
                throw new IllegalStateException("Not expected other boolex values here.");
        }
    }

    /// <summary>
    /// RK This is a result of refactoring of earlier BoolEx to new AttrBoolEx.
    /// Will need more refactoring with other methods in this class, but later on.
    /// </summary>
    AttrBoolEx resolveFetchAttr(AttrCollection parentAttrs, int key)
    {
        switch (mValue)
        {
            case TRUE_VALUE:
            case FALSE_VALUE:
                return this;
            case SAME_VALUE:
                // New value should be same as the existing value.
                return (AttrBoolEx)parentAttrs.fetchAttr(key);
            case TOGGLE_VALUE:
                AttrBoolEx parentValue = (AttrBoolEx)parentAttrs.fetchAttr(key);

                // New value should be the opposite of the existing value.
                // AM. There are might be the case when we trying to resolve AttrBoolEx.Toggle over AttrBoolEx.Toggle.
                // For example if collection having Bold = AttrBoolEx.Toggle expanded to the same collection.
                // I don't fully understand value we need to get in this case and suspect it should be True.
                return (parentValue.getValue() == TOGGLE_VALUE) ? TRUE : parentValue.invert();
            default:
                throw new IllegalStateException("Unknown boolex value.");
        }
    }

    /// <summary>
    /// RK This is a result of refactoring of earlier BoolEx to new AttrBoolEx.
    /// Will need more refactoring with other methods in this class, but later on.
    /// </summary>
    AttrBoolEx resolveFetchAttr(Font dstAttrs, int key)
    {
        switch (mValue)
        {
            case TRUE_VALUE:
            case FALSE_VALUE:
                return this;
            case SAME_VALUE:
                // New value should be same as the existing value.
                return (AttrBoolEx)dstAttrs.fetchAttr(key);
            case TOGGLE_VALUE:
                // New value should be the opposite of the existing value.
                return ((AttrBoolEx)dstAttrs.fetchAttr(key)).invert();
            default:
                throw new IllegalStateException("Unknown boolex value.");
        }
    }

    /// <summary>
    /// RK This is a result of refactoring of earlier BoolEx to new AttrBoolEx.
    /// Will need more refactoring with other methods in this class, but later on.
    /// </summary>
    AttrBoolEx resolveFetchInheritedRunAttr(IRunAttrSource src, int key)
    {
        switch (mValue)
        {
            case TRUE_VALUE:
            case FALSE_VALUE:
                return this;
            case SAME_VALUE:
                return (AttrBoolEx)src.fetchInheritedRunAttr(key);
            case TOGGLE_VALUE:
                return ((AttrBoolEx)src.fetchInheritedRunAttr(key)).invert();
            default:
                throw new IllegalStateException("Unknown BoolEx value.");
        }
    }

    /// <summary>
    /// RK This is a result of refactoring of earlier BoolEx to new AttrBoolEx.
    /// Will need more refactoring with other methods in this class, but later on.
    /// </summary>
    Object resolveFetchInheritedRunAttrWithNull(IRunAttrSource src, int key)
    {
        switch (mValue)
        {
            case TRUE_VALUE:
                return true;
            case FALSE_VALUE:
                return false;
            case SAME_VALUE:
                return null;
            case TOGGLE_VALUE:
            {
                // Invert the inherited BoolEx value and convert to boolean.
                if (src != null)
                    return ((AttrBoolEx)src.fetchInheritedRunAttr(key)).invert().toBool();
                else
                    return null;
            }
            default:
                throw new IllegalStateException("Unknown BoolEx value.");
        }
    }

    public /*override*/ int hashCode()
    {
        return (/*int.hashCode()*/mValue);
    }


    private /*final*/ int mValue;

    private static final int FALSE_VALUE = 0;
    private static final int TRUE_VALUE = 1;
    private static final int SAME_VALUE = 128;
    private static final int TOGGLE_VALUE = 129;

    static /*final*/ AttrBoolEx FALSE = new AttrBoolEx(FALSE_VALUE);
    static /*final*/ AttrBoolEx TRUE = new AttrBoolEx(TRUE_VALUE);

    /// <summary>
    /// The value is same as the inherited value.
    /// </summary>
    static /*final*/ AttrBoolEx SAME = new AttrBoolEx(SAME_VALUE);

    /// <summary>
    /// The value is the opposite of the inherited value.
    /// </summary>
    static /*final*/ AttrBoolEx TOGGLE = new AttrBoolEx(TOGGLE_VALUE);
}

