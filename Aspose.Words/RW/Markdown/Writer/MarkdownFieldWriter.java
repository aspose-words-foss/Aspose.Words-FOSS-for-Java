// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msChar;
import com.aspose.FontUtil;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// The class responsible for writing Fields into markdown.
/// </summary>
class MarkdownFieldWriter
{
    /// <summary>
    /// Initializes a new instance of the <see cref="MarkdownFieldWriter" /> class.
    /// </summary>
    MarkdownFieldWriter(FieldStart fieldStart, MarkdownWriter writer)
    {
        ArgumentUtil.checkNotNull(fieldStart, "FieldStart");

        setFieldStart(fieldStart);

        mEmphasesWriter = new MarkdownEmphasesWriter(
            mFieldResult,
            null,
            writer);
    }

    /// <summary>
    /// Creates instance of <see cref="MarkdownFieldWriter"/> object.
    /// </summary>
    static MarkdownFieldWriter create(FieldStart fieldStart, MarkdownLinkDefinitionWriter linkDefinitionWriter, MarkdownWriter writer)
    {
        switch (fieldStart.getFieldType())
        {
            case FieldType.FIELD_REF:
            case FieldType.FIELD_HYPERLINK:
            {
                return new MarkdownHyperlinkWriter(fieldStart, linkDefinitionWriter, writer);
            }

            case FieldType.FIELD_PAGE_REF:
            {
                // Simple check for nested ref fields to avoid escaping in the child field.
                return (writer.getFieldWriter() != null) && isRefField(writer.getFieldWriter().getFieldStart())
                    ? new MarkdownFieldWriter(fieldStart, writer)
                    : new MarkdownHyperlinkWriter(fieldStart, linkDefinitionWriter, writer);
            }

            default:
                return new MarkdownFieldWriter(fieldStart, writer);
        }
    }

    /// <summary>
    /// Processes a FieldSeparator.
    /// </summary>
    /*virtual*/ void onFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        isFieldResult(true);

        // WORDSNET-23165 By default, lets ignore text in FieldCode part.
        mFieldResult.clear();
    }

    /// <summary>
    /// Processes a specified FieldEnd.
    /// </summary>
    String onFieldEnd(FieldEnd fieldEnd)
    {
        appendNextSiblingEmphases(null);
        return getText();
    }

    /// <summary>
    /// Processes a specified run.
    /// </summary>
    void onRun(Run run) throws Exception
    {
        Debug.assertTrue(run != null);

        String text = TxtWriterBase.getText(run);
        if (isFieldResult())
            mEmphasesWriter.appendEmphases(run);
        appendText(text);
    }

    /// <summary>
    /// Returns true, if a specified <see cref="FieldStart"/> type is one of the field reference types.
    /// </summary>
    private static boolean isRefField(FieldStart fieldStart)
    {
        return (fieldStart.getFieldType() == FieldType.FIELD_REF) ||
               (fieldStart.getFieldType() == FieldType.FIELD_HYPERLINK) ||
               (fieldStart.getFieldType() == FieldType.FIELD_PAGE_REF);
    }

    /// <summary>
    /// Appends a specified text to the FieldResult builder.
    /// </summary>
    private void appendText(String text)
    {
        if (text == null)
            return;

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (isFieldResult() && !msChar.isWhiteSpace(c))
                mEmphasesWriter.flushPendingOpeningEmphases();

            getFieldResult().append(FontUtil.unicodeToSymbol(c));
        }
    }

    /// <summary>
    /// Flushes current <see cref="Text"/>.
    /// </summary>
    String flushText()
    {
        String text = onFieldEnd(null);
        getFieldResult().setLength(0);

        return text;
    }

    /// <summary>
    /// Appends a specified text to a FieldResult.
    /// </summary>
    void appendToFieldResult(String text)
    {
        msStringBuilder.append(getFieldResult(), text);
    }

    /// <summary>
    /// Appends emphases for a next sibling IInline node of a specified FieldChar.
    /// </summary>
    private void appendNextSiblingEmphases(FieldChar fieldChar)
    {
        IInline emphasisSrc = (fieldChar == null)
            ? null
            : (IInline)fieldChar.nextSiblingOfTypes(NodeType.RUN, NodeType.FOOTNOTE);

        mEmphasesWriter.appendEmphases(emphasisSrc);
    }

    /// <summary>
    /// Gets a FieldResult builder.
    /// </summary>
    /// <remarks>
    /// Field result in Hyperlink contains DisplayText of the link.
    /// </remarks>
    protected StringBuilder getFieldResult() { return mFieldResult.getCurrentLine(); }

    /// <summary>
    /// Gets a string that is ready for writing into markdown.
    /// </summary>
    protected /*virtual*/ String getText() { return getFieldResult().toString(); }

    /// <summary>
    /// Gets a boolean value indicating either we are inside FieldResult for a moment.
    /// </summary>
    protected boolean isFieldResult() { return mIsFieldResult; }; private void isFieldResult(boolean value) { mIsFieldResult = value; };

    private boolean mIsFieldResult;

    /// <summary>
    /// Gets a FieldStart object.
    /// </summary>
    private FieldStart getFieldStart() { return mFieldStart; }; private void setFieldStart(FieldStart value) { mFieldStart = value; };

    private FieldStart mFieldStart;

    private /*final*/ MarkdownEmphasesWriter mEmphasesWriter;
    private /*final*/ TxtContentLines mFieldResult = new TxtContentLines();
}

