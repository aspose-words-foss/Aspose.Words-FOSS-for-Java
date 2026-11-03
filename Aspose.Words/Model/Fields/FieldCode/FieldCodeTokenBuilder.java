// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Encapsulates some common operations used to build a field code token.
/// </summary>
class FieldCodeTokenBuilder
{
    FieldCodeTokenBuilder()
    {
        setStartPosition(DocumentPosition.VOID);
        setEndPosition(DocumentPosition.VOID);
    }

    /// <summary>
    /// Appends a current character provided by the specified tokenizer to the token text.
    /// </summary>
    /// <param name="tokenizer"></param>
    void appendToken(IFieldCodeTokenizer tokenizer)
    {
        if (!hasContent())
            setStartPosition(tokenizer.getCurrentPosition());

        if (tokenizer.getCurrentToken() == FieldCodeToken.TEXT_CHAR)
        {
            getBuilder().append(tokenizer.getCurrentChar());

            getRichBuilder().append(tokenizer.getCurrentChar(), resolveRunFormatting(tokenizer.getCurrentNode()));
        }
    }

    private static RunPr resolveRunFormatting(Node node)
    {
        Run run = ms.as(node, Run.class);
        return run != null
            ? run.getRunPr()
            : new RunPr();
    }

    void visitFieldStart(IFieldCodeTokenizer tokenizer)
    {
        Debug.assertTrue(tokenizer.getCurrentToken() == FieldCodeToken.CHILD_FIELD_START);
        if (getStartPosition().isVoid())
            mCompleteFieldStartPosition = DocumentPosition.createPositionBefore(tokenizer.getCurrentNode());
    }

    /// <summary>
    /// Sets the end position to the current tokenizer position.
    /// </summary>
    /// <param name="tokenizer"></param>
    void finalizeToken(IFieldCodeTokenizer tokenizer)
    {
        if (!getStartPosition().isVoid())
            setEndPosition(tokenizer.getCurrentPosition());

        if (!mCompleteFieldStartPosition.isVoid() && tokenizer.getCurrentToken() == FieldCodeToken.CHILD_FIELD_END)
            mCompleteFieldEndPosition = DocumentPosition.createPositionAfter(tokenizer.getCurrentNode());
    }

    void appendChar(char c, RunPr runPr)
    {
        getBuilder().append(c);

        if (runPr == null)
            runPr = new RunPr();

        getRichBuilder().append(c, runPr);
    }

    void clear()
    {
        if (mBuilder != null)
        {
            mBuilder.setLength(0);
            mRichBuilder.clear();
        }

        setStartPosition(DocumentPosition.VOID);
        setEndPosition(DocumentPosition.VOID);

        mCompleteFieldStartPosition = DocumentPosition.VOID;
        mCompleteFieldEndPosition = DocumentPosition.VOID;
    }

    /// <summary>
    /// Appends the content of the specified builder to this builder and clears the source builder.
    /// Also updates the current end point.
    /// </summary>
    /// <param name="builder"></param>
    void mergeWith(FieldCodeTokenBuilder builder)
    {
        if (!builder.hasContent())
            return;

        if (!hasContent())
            setStartPosition(builder.getStartPosition());

        setEndPosition(builder.getEndPosition());

        msStringBuilder.append(getBuilder(), builder.getText());
        for (int i = 0; i < builder.getRichText().getLength(); i++)
            getRichBuilder().appendInternal(builder.getRichText().getInternal(i));

        builder.clear();
    }

    /// <summary>
    /// Gets the current token's node range.
    /// </summary>
    /// <returns></returns>
    NodeRange getNodeRange()
    {
        return new NodeRange(getStartPosition(), getEndPosition());
    }

    NodeRange getCompleteFieldNodeRange()
    {
        return new NodeRange(mCompleteFieldStartPosition, mCompleteFieldEndPosition);
    }

    /// <summary>
    /// Gets the token's text.
    /// </summary>
    String getText() { return (mBuilder != null) ? mBuilder.toString() : ""; }

    /// <summary>
    /// Gets the token's rich text.
    /// </summary>
    RichString getRichText() { return (mRichBuilder != null) ? mRichBuilder.toRichString() : RichString.EMPTY; }

    /// <summary>
    /// Gets the length of the token's text.
    /// </summary>
    int getLength() { return (mBuilder != null) ? mBuilder.length() : 0; }

    /// <summary>
    /// True if the builder contains some contents (represented by a text or node range).
    /// </summary>
    boolean hasContent()
    {
        if (getLength() > 0)
            return true;

        return (!getStartPosition().isVoid() || !getEndPosition().isVoid());
    }

    /// <summary>
    /// Gets the start position of the current token.
    /// </summary>
    DocumentPosition getStartPosition() { return mStartPosition; }; private void setStartPosition(DocumentPosition value) { mStartPosition = value; };

    private DocumentPosition mStartPosition;

    /// <summary>
    /// Gets the end position of the current token.
    /// </summary>
    DocumentPosition getEndPosition() { return mEndPosition; }; private void setEndPosition(DocumentPosition value) { mEndPosition = value; };

    private DocumentPosition mEndPosition;

    private StringBuilder getBuilder() { return (mBuilder != null ? mBuilder : (mBuilder = new StringBuilder())); }

    private RichStringBuilder getRichBuilder() { return (mRichBuilder != null ? mRichBuilder : (mRichBuilder = new RichStringBuilder())); }

    private StringBuilder mBuilder;
    private RichStringBuilder mRichBuilder;

    private DocumentPosition mCompleteFieldStartPosition = DocumentPosition.VOID;
    private DocumentPosition mCompleteFieldEndPosition = DocumentPosition.VOID;
}

