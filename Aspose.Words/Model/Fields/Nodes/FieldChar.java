// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for nodes that represent field characters in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="Field.Common"]/*'/>
/// </remarks>
/// <seealso cref="FieldStart"/>
/// <seealso cref="FieldSeparator"/>
/// <seealso cref="FieldEnd"/>
public abstract class FieldChar extends SpecialChar
{
    FieldChar(DocumentBase doc, char fieldChar, RunPr runPr, /*FieldType*/int type)
    {
    	super(doc, fieldChar, runPr);
	
        setFieldType(type);
    }

    /// <summary>
    /// Returns a field for the field char.
    /// </summary>
    /// <remarks>
    /// A new <see cref="Field"/> object is created each time the method is called.
    /// </remarks>
    /// <returns>A field for the field char.</returns>
    public Field getField()
    {
        return FieldBundle
            .getFieldBundle(this)
            .getField();
    }

    /// <summary>
    /// Returns the type of the field.
    /// </summary>
    public /*FieldType*/int getFieldType() { return mFieldType; };
    // Sets field type for this field char node. Used during WordML import for late assignment of field type.
    void setFieldType(/*FieldType*/int value) { mFieldType = value; };

    private /*FieldType*/int mFieldType;

    /// <summary>
    /// Gets or sets whether the parent field is locked (should not recalculate its result).
    /// </summary>
    public boolean isLocked() { return mIsLocked; }; public void isLocked(boolean value) { mIsLocked = value; };

    private boolean mIsLocked;

    /// <summary>
    /// Gets or sets whether the current result of the field is no longer correct (stale) due to other modifications
    /// made to the document.
    /// </summary>
    public boolean isDirty() { return mIsDirty; }; public void isDirty(boolean value) { mIsDirty = value; };

    private boolean mIsDirty;

    /// <summary>
    /// If true field result is not intended to be visible to the user.
    /// </summary>
    boolean isPrivate() { return mIsPrivate; }; void isPrivate(boolean value) { mIsPrivate = value; };

    private boolean mIsPrivate;

}

