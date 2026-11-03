// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Passes data between the parts of the evaluation module.
/// </summary>
class FieldContext
{
    FieldContext(Field field)
    {
        mField = field;
    }

    boolean documentContainsBookmark(String bookmarkName) throws Exception
    {
        // SPEED Get a bookmark from a cache.
        return FieldUtil.getCachedBookmark(getField(), bookmarkName) != null;
    }

    /// <summary>
    /// Gets the field that owns the expression.
    /// </summary>
    Field getField() { return mField; };

    private Field mField;

    /// <summary>
    /// Gets a parent table if the field is in a table, null otherwise.
    /// </summary>
    Table getParentTable() { return (getParentCell() != null) ? getParentCell().getParentTable() : null; }

    /// <summary>
    /// Gets a parent cell if the field is in a table, null otherwise.
    /// </summary>
    Cell getParentCell() { return (Cell)getField().getStart().getAncestor(NodeType.CELL); }
}
