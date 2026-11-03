// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a field argument with original formatting preserved.
/// Field argument may sometimes be a source for field result (TrueText or FalseText in IF fields), so
/// it implements the <see cref="IFieldResult"/> interface.
/// </summary>
class FieldArgument implements IFieldArgument, IFieldResult
{
    FieldArgument(
        Field field,
        String text,
        RichString richText,
        NodeRange range,
        NodeRange completeFieldRange,
        boolean isInDoubleQuotes)
    {
        Debug.assertTrue(text != null);

        mField = field;
        mText = text;
        mRichText = richText;
        setRange(range);
        mCompleteFieldRange = completeFieldRange;
        mIsInDoubleQuotes = isInDoubleQuotes;
    }

    /// <summary>
    /// Gets the value of the argument as a plain text. Also decodes it, translates to invariant language
    /// if it is translatable and trims double quotes.
    /// </summary>
    String getNormalizedText()
    {
        return getNormalizedText(true);
    }

    /// <summary>
    /// Gets the value of the argument as a plain text. Also decodes it and translates to invariant language
    /// if it is translatable.
    /// Double quotes should be trimmed in most cases except of in comparison expression operands because
    /// they are used there to determine whether to compare the operands as strings.
    /// </summary>
    /// <param name="trimDoubleQuotes">True to trim double quotes if present.</param>
    String getNormalizedText(boolean trimDoubleQuotes)
    {
        return FieldTokenDecoder.decodeToken(getText(), getRange(), trimDoubleQuotes && isInDoubleQuotes());
    }

    RichString getNormalizedRichText()
    {
        return getNormalizedRichText(true);
    }

    RichString getNormalizedRichText(boolean trimDoubleQuotes)
    {
        return FieldTokenDecoder.decodeToken(getRichText(), getRange(), trimDoubleQuotes && isInDoubleQuotes());
    }

    /// <summary>
    /// Returns a value, indicating contains this argument child fields or not
    /// </summary>
    boolean containsChildFields()
    {
        for (Node node : getRange())
        {
            if (node.getNodeType() == NodeType.FIELD_START)
                return true;
        }

        return false;
    }

    public Constant /*IFieldResult.*/getFieldResultValue()
    {
        return new StringConstant(getNormalizedText());
    }

    public NodeRange /*IFieldResult.*/getFieldResultRange()
    {
        return getRange();
    }

    public void /*IFieldArgument.*/invalidateText()
    {
        mText = null;
        mRichText = null;

        // WORDSNET-18544 Adjust argument range to nested field result, if nested field did not have
        // separator when field code was parsed.
        adjustNestedFieldArgumentRangeWithoutSeparator();
    }

    private void adjustNestedFieldArgumentRangeWithoutSeparator()
    {
        if (!getRange().isSameNode())
            return;

        if (getRange().getEnd().getNode().getNodeType() != NodeType.FIELD_END)
            return;

        FieldEnd fieldEnd = (FieldEnd)getRange().getEnd().getNode();
        if (!fieldEnd.hasSeparator())
            return;

        setRange(fieldEnd.getField().getFieldResultRange());
    }

    void ensureText()
    {
        if (mText == null)
        {
            mText = FieldCodeParser.getArgumentTextByRange(this);
            Debug.assertTrue(mText != null);
        }
    }

    /// <summary>
    /// Gets a string representation of this argument.
    /// </summary>
    String getText()
    {
        ensureText();
        return mText;
    }

    /// <summary>
    /// Gets a rich string representation of this argument.
    /// </summary>
    RichString getRichText()
    {
        if (mRichText == null)
        {
            mRichText = FieldCodeParser.getArgumentRichTextByRange(this);
            Debug.assertTrue(mRichText != null);
        }

        return mRichText;
    }

    /// <summary>
    /// Gets a value indicating whether this argument is enclosed in double quotes.
    /// This relates to the original argument content, i.e. before any translation.
    /// </summary>
    boolean isInDoubleQuotes() { return mIsInDoubleQuotes; };

    private boolean mIsInDoubleQuotes;

    /// <summary>
    /// Gets a value indicating whether this argument is represented by a single field's result.
    /// </summary>
    boolean isSingleFieldResult() { return FieldUtil.endsWithFieldEnd(getRange()); }

    /// <summary>
    /// Gets a node range of the field argument.
    /// </summary>
    NodeRange getRange() { return mRange; }; private void setRange(NodeRange value) { mRange = value; };

    private NodeRange mRange;

    Field getField() { return mField; }

    NodeRange getCompleteFieldRange() { return mCompleteFieldRange; };

    private NodeRange mCompleteFieldRange;

    FieldArgument deepClone(NodeRange range)
    {
        return new FieldArgument(
            mField,
            mText,
            mRichText,
            range,
            getCompleteFieldRange(),
            isInDoubleQuotes());
    }

    private /*final*/ Field mField;
    private String mText;
    private RichString mRichText;
}

