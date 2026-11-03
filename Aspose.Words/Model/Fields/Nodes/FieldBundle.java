// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// A lightweight structure to deal with all three nodes of a field at once.
/// </summary>
/*struct*/ final class FieldBundle
{
	public FieldBundle(){}
	
    FieldBundle(FieldStart start, FieldSeparator separator, FieldEnd end)
    {
        setStart(start);
        setSeparator(separator);
        setEnd(end);
    }

    /// <summary>
    /// Returns text between field start and field separator (or field end if there is no separator).
    /// </summary>
    String getFieldCode()
    {
        return getFieldCode(true);
    }

    /// <summary>
    /// Returns text between field start and field separator (or field end if there is no separator).
    /// </summary>
    String getFieldCode(boolean includeChildFieldCodes)
    {
        NodeTextCollectorOptions options = new NodeTextCollectorOptions();

        boolean hidden = getStart().getFont().getHidden();
        options.setAllowHiddenText(hidden);
        options.setAllowDeletedText(hidden);
        options.isFieldResultMode(!includeChildFieldCodes);
        return NodeTextCollector.getText(getStart(), false, getFieldCodeEnd(), false, options);
    }

    /// <summary>
    /// Gets field code, just enough to determine the field type.
    /// Will not return complete field code for fields that don't have same grand parent.
    /// </summary>
    private String getFieldCodeLimited(boolean allowHiddenText)
    {
        StringBuilder builder = new StringBuilder();
        Node curNode = getStart().getNextSibling();
        Node fieldCodeEnd = getFieldCodeEnd();

        int fieldDepth = 0;
        while ((curNode != null) && (curNode != fieldCodeEnd))
        {
            boolean breakLoop = false;
            switch (curNode.getNodeType())
            {
                case NodeType.RUN:

                    if (fieldDepth != 0)
                        break;

                    if (!allowHiddenText && ((Run)curNode).isHiddenOrDeleted())
                        break;

                    msStringBuilder.append(builder, curNode.getText());

                    break;

                case NodeType.FIELD_START:

                    if (FieldUtil.isDead(((FieldStart)curNode).getFieldType()))
                    {
                        fieldDepth++;
                    }
                    // WORDSNET-5572 put whole content of INCLUDEPICTURE field into SourceFullname (in field code), include nested fields
                    else if (getStart().getFieldType() != FieldType.FIELD_INCLUDE_PICTURE)
                    {
                        // WORDSNET-5997 We should prevent the reading runs of nested field because it might lead to wrong field type determination.
                        // For example, outer field for such fields as {{MERGEFIELD somename}} gets type MergeField instead of FieldNone.
                        // This will lead to errors during executing MailMerge.Execute method.
                        breakLoop = fieldDepth == 0;
                    }

                    break;

                case NodeType.FIELD_END:

                    if (FieldUtil.isDead(((FieldEnd)curNode).getFieldType()))
                        fieldDepth--;

                    break;

                default:
                    // Do nothing.
                    break;
            }

            if (breakLoop)
                break;

            curNode = curNode.getNextSibling();
        }

        return builder.toString();
    }

    /// <summary>
    /// Returns a field for the field bundle.
    /// </summary>
    Field getField()
    {
        return FieldFactory.createField(this);
    }

    /*FieldType*/int parseFieldType()
    {
        String fieldCode = getStart().getParentNode() != getFieldCodeEnd().getParentNode()
                               ? getFieldCode()
                               : getFieldCodeLimited(getStart().isHiddenOrDeleted());
        return FieldUtil.getFieldType(fieldCode);
    }

    void determineFieldType()
    {
        setFieldType(parseFieldType());
    }

    void removeFieldNodes()
    {
        if (getStart() != null)
            getStart().remove();

        if (getSeparator() != null)
            getSeparator().remove();

        if (getEnd() != null)
            getEnd().remove();
    }

    /// <summary>
    /// Returns either separator or field end node.
    /// </summary>
    FieldChar getFieldCodeEnd() { return getSeparator() != null ? (FieldChar)getSeparator() : getEnd(); }

    /*FieldType*/int getFieldType() { return getStart().getFieldType(); }
    void setFieldType(/*FieldType*/int value)
    {
        getStart().setFieldType(value);
        getEnd().setFieldType(value);
        if (getSeparator() != null)
            getSeparator().setFieldType(value);
    }

    
    FieldStart getStart() { return mStart; }; 
    void setStart(FieldStart value) { mStart = value; };

    private FieldStart mStart;

    FieldSeparator getSeparator() { return mSeparator; }; void setSeparator(FieldSeparator value) { mSeparator = value; };

    private FieldSeparator mSeparator;

    boolean hasSeparator() { return getSeparator() != null; }

    FieldEnd getEnd() { return mEnd; }; void setEnd(FieldEnd value) { mEnd = value; };

    private FieldEnd mEnd;

    boolean isLocked() { return getStart().isLocked(); }
    void isLocked(boolean value)
    {
        getStart().isLocked(value);
        getEnd().isLocked(value);
        if (getSeparator() != null)
            getSeparator().isLocked(value);
    }

    boolean isDirty() { return getStart().isDirty(); }
    void isDirty(boolean value)
    {
        getStart().isDirty(value);
        getEnd().isDirty(value);
        if (getSeparator() != null)
            getSeparator().isDirty(value);
    }

    /// <summary>
    /// Updates flags IsDirty, IsLocked in FieldStart, FieldSeparator and FieldEnd if any of them has these flags set.
    /// </summary>
    void updateDirtyLocked()
    {
        isDirty(getStart().isDirty() || getEnd().isDirty() || ((getSeparator() != null) && getSeparator().isDirty()));
        isLocked(getStart().isLocked() || getEnd().isLocked() || ((getSeparator() != null) && getSeparator().isLocked()));
    }

    /// <summary>
    /// Gets the whole field bundle by the given field char.
    /// </summary>
    static FieldBundle getFieldBundle(FieldChar fieldChar)
    {
        FieldBundle fieldBundle = getFieldBundleNoSeparatorCheck(fieldChar);

        // We must find the corresponding field separator if the field end specifies that it has one and
        // we must not find it otherwise. If it is not satisfied then the model is invalid.
        if (fieldBundle.getEnd().hasSeparator() != (fieldBundle.getSeparator() != null))
            throw createInvalidModelException();

        return fieldBundle;
    }

    /// <summary>
    /// Gets the whole field bundle by the given field char.
    /// </summary>
    /// <remarks>
    /// Doesn't throw Invalid Model Exception for missed separator.
    /// </remarks>
    static FieldBundle getFieldBundleNoSeparatorCheck(FieldChar fieldChar)
    {
        FieldBundle fieldBundle = new FieldBundle();

        switch (fieldChar.getNodeType())
        {
            case NodeType.FIELD_START:
                fieldBundle.fillFieldBundleOneWay(fieldChar, true);
                break;
            case NodeType.FIELD_SEPARATOR:
                fieldBundle.fillFieldBundleOneWay(fieldChar, true);
                fieldBundle.fillFieldBundleOneWay(fieldChar, false);
                break;
            case NodeType.FIELD_END:
                fieldBundle.fillFieldBundleOneWay(fieldChar, false);
                break;
            default:
                // A field char can not have a node type other than one of the listed above.
                // If it has then the model is invalid.
                throw createInvalidModelException();
        }

        return fieldBundle;
    }

    /// <summary>
    /// Fills the specified field bundle by field chars starting from the specified field char and
    /// moving one way (forward or backward) through the document.
    /// </summary>
    void fillFieldBundleOneWay(FieldChar fieldChar, boolean isForward)
    {
        boolean isFieldSeparator = fieldChar.getNodeType() == NodeType.FIELD_SEPARATOR;
        FieldNestingLevel nestingLevel = new FieldNestingLevel(isForward, isFieldSeparator);
        if (isFieldSeparator)
        {
            // We need to set the bundle's field separator to null in case when we are moving from a field separator
            // to be able to determine that we encounter not more than one corresponding field separator while moving.
            setSeparator(null);
        }

        DocumentPosition currentPosition = isForward
            ? DocumentPosition.createPositionAfter(fieldChar)
            : DocumentPosition.createPositionBefore(fieldChar);

        while (true) // Return inside.
        {
            Node currentNode = currentPosition.getNode();
            switch (currentNode.getNodeType())
            {
                case NodeType.FIELD_START:
                    if (nestingLevel.acceptFieldStart())
                    {
                        setStart((FieldStart)currentNode);
                        validateFieldChar(getStart(), fieldChar);

                        // If we are moving backward and the corresponding field start is found, it's time to return.
                        if (!isForward)
                            return;
                    }
                    break;
                case NodeType.FIELD_SEPARATOR:
                    if (nestingLevel.isTop())
                    {
                        // If we encountered more than one corresponding field separator while moving then the model is invalid.
                        if (getSeparator() != null)
                            throw createInvalidModelException();

                        setSeparator((FieldSeparator)currentNode);
                        validateFieldChar(getSeparator(), fieldChar);
                    }
                    break;
                case NodeType.FIELD_END:
                    if (nestingLevel.acceptFieldEnd())
                    {
                        setEnd((FieldEnd)currentNode);
                        validateFieldChar(getEnd(), fieldChar);

                        // If we are moving forward and the corresponding field end is found, it's time to return.
                        if (isForward)
                            return;
                    }
                    break;
                default:
                    break;
            }

            // Move to the next node.
            do
            {
                // If the movement is failed then the model is invalid, i.e. a field start must have
                // the corresponding field end and vice versa.
                if (!currentPosition.move(null, isForward, true, true, false, false))
                    throw createInvalidModelException();
            }
            while (currentPosition.getNode() == currentNode);
        }
    }

    /// <summary>
    /// Validates the given field char. Throws if field chars' field types are different.
    /// </summary>
    /// <param name="fieldChar">A field char to validate.</param>
    /// <param name="referenceFieldChar">A valid field char to compare with.</param>
    private static void validateFieldChar(FieldChar fieldChar, FieldChar referenceFieldChar)
    {
        if (fieldChar.getFieldType() != referenceFieldChar.getFieldType())
            throw createInvalidModelException();
    }

    /// <summary>
    /// Creates an exception object used to denote that the document model is invalid.
    /// </summary>
    private static IllegalStateException createInvalidModelException()
    {
        return new IllegalStateException("Invalid document model. Operation can not be completed.");
    }

    /// <summary>
    /// Represents a relative field nesting level.
    /// </summary>
    private class FieldNestingLevel
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="isForward">
        /// A value indicating whether a movement through the document is forward or backward.
        /// </param>
        /// <param name="startFromTop">
        /// A value indicating whether the counting should be started from the top level instead of zero.
        /// </param>
        FieldNestingLevel(boolean isForward, boolean startFromTop)
        {
            mIsForward = isForward;
            mValue = startFromTop ? getTopValue() : 0;
        }

        /// <summary>
        /// Changes the nesting level accordingly to a field start encountering and the document movement direction.
        /// </summary>
        /// <returns>
        /// A value indicating that the operation has led the nesting level to become zero or to stop being zero.
        /// </returns>
        boolean acceptFieldStart()
        {
            int boundaryValue = mIsForward ? 1 : 0;
            return ++mValue == boundaryValue;
        }

        /// <summary>
        /// Changes the nesting level accordingly to a field end encountering and the document movement direction.
        /// </summary>
        /// <returns>
        /// A value indicating that the operation has led the nesting level to become zero or to stop being zero.
        /// </returns>
        boolean acceptFieldEnd()
        {
            int boundaryValue = mIsForward ? 0 : -1;
            return --mValue == boundaryValue;
        }

        /// <summary>
        /// Gets a value indicating whether the nesting level is top according to the document movement direction.
        /// </summary>
        boolean isTop() { return mValue == getTopValue(); }

        /// <summary>
        /// Gets a value of the top nesting level according to the document movement direction.
        /// </summary>
        private int getTopValue() { return mIsForward ? 1 : -1; }

        private /*final*/ boolean mIsForward;
        private int mValue;
    }
}

