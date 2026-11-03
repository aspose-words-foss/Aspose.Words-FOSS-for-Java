// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Michael Morozoff

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Display context is passed to the document so it can compute field display value.
/// </summary>
abstract class FieldDisplayContext
{
    /// <summary>
    /// Seeks for the first paragraph satisfying the given <see cref="FieldParagraphFinder"/> scanning pages backward
    /// from the corresponding field's page to the first page. Returns <c>null</c> if nothing is found.
    /// </summary>
    abstract Paragraph findParagraph(FieldStart fieldStart, FieldParagraphFinder finder) throws Exception;

    /// <summary>
    /// Returns field value computed by layout.
    /// </summary>
    protected abstract int getFieldValueCore(Field field) throws Exception;

    /// <summary>
    /// Returns a value for the field if provided.
    /// </summary>
    Constant getFieldValue(Field field) throws Exception
    {
        int value = getFieldValueCore(field);
        return (value != -1) ? new Int32Constant(value) : null;
    }

    /// <summary>
    /// Returns a section that contains the given field start node.
    /// </summary>
    /// <remarks>
    /// For a field inside header/footer, this section may be different from the parent section in a document model.
    /// This may happen because header/footer may be inherited from the previous sections.
    /// </remarks>
    Section getSection(FieldStart fieldStart) throws Exception
    {
        int sectionIndex = getSectionIndex(fieldStart);
        return (sectionIndex >= 0) ? fieldStart.fetchDocument().getSections().get(sectionIndex) : null;
    }

    /// <summary>
    /// Gets a zero-based index of the section that contains the given field start node.
    /// Returns a negative index if the section is not found.
    /// </summary>
    /// <remarks>
    /// For a field inside header/footer, this section index may be different from the parent section in a document model.
    /// This may happen because header/footer may be inherited from the previous sections.
    /// </remarks>
    protected abstract int getSectionIndex(FieldStart fieldStart) throws Exception;

    /// <summary>
    /// Requests layout to update the specified field. Returns <c>true</c> if the field was updated by layout.
    /// Returns <c>false</c> if the field's update was rejected by layout and hence the field should be updated
    /// in a common way.
    /// </summary>
    abstract boolean updateField(Field field, boolean isTopmost) throws Exception;

    /// <summary>
    /// Gets a fields updating session associated with this instance.
    /// </summary>
    abstract FieldUpdateSession getFieldUpdateSession();
}

