// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2018 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.Debug;
import com.aspose.UriUtil;
import com.aspose.StackExtension;


/// <summary>
/// Base class of WML and DOCX fields writers.
/// </summary>
abstract class NrxFieldsWriter
{
    protected NrxFieldsWriter()
    {
        mFieldCharsStack = new msStack<FieldChar>();
        mFieldStartCountStack = new msStack<Integer>();
    }

    /// <summary>
    /// Writes field delimiter with the specified name.
    /// Used to write field blocks for FieldStart, FieldSeparator and FieldEnd nodes.
    /// </summary>
    void writeFldChar(FieldChar fieldChar) throws Exception
    {
        // WORDSNET-22185 Skip field chars in a hyperlink field code.
        if (getSkipThisInline() && (getTopFieldChar() != fieldChar.getField().getStart()))
            return;

        String fldCharName;

        switch (fieldChar.getNodeType())
        {
            case NodeType.FIELD_START:
            {
                fldCharName = "begin";

                mFieldCharsStack.push(fieldChar);
                setFieldStartCount(getFieldStartCount() + 1)/*Property++*/;
                break;
            }
            case NodeType.FIELD_SEPARATOR:
            {
                fldCharName = "separate";

                // Substitute FieldStart with FieldSeparator.
                // We do not check here if it was actually FieldStart that was popped. It should be FieldStart in a
                // valid document. And if the document is invalid, it is not our responsibility to validate it.
                mFieldCharsStack.pop();
                mFieldCharsStack.push(fieldChar);

                setFieldStartCount(getFieldStartCount() - 1)/*Property--*/;

                break;
            }
            case NodeType.FIELD_END:
            {
                fldCharName = "end";

                if (mFieldCharsStack.pop().getNodeType() == NodeType.FIELD_START)
                    setFieldStartCount(getFieldStartCount() - 1)/*Property--*/;
                break;
            }
            default:
                throw new IllegalStateException("Field delimiter expected.");
        }

        if (isHyperlink(fieldChar) && canWriteAsHyperlink(fieldChar))
        {
            // If shape is only child we assume link is written as shape property.
            if (!isSingleShapeResult(fieldChar.getField()))
                writeHyperlink(fieldChar);
        }
        else
        {
            writeField(fieldChar, fldCharName);
        }
    }

    /// <summary>
    /// Writes hyperlink field start for a shape.
    /// </summary>
    void writeHyperlinkStart(Shape shape) throws Exception
    {
        writeHyperlinkStart(
            shape.getHyperlinkAddress(),
            shape.getHyperlinkSubAddress(),
            shape.getTarget(),
            shape.getScreenTip(),
            null,
            true);
    }

    void pushFieldStartCounter()
    {
        mFieldStartCountStack.push(0);
    }

    void popFieldStartCounter()
    {
        mFieldStartCountStack.pop();
    }

    /// <summary>
    /// Writes hyperlink field start.
    /// </summary>
    protected abstract void writeHyperlinkStart(
        String dest,
        String bookmark,
        String target,
        String screenTip,
        String docLocation,
        boolean history) throws Exception;

    /// <summary>
    /// Closes hyperlink element. Symmetrical to WriteHyperlinkStart.
    /// </summary>
    abstract void writeHyperlinkEnd();

    /// <summary>
    /// Writes hyperlink field start.
    /// </summary>
    protected abstract void writeField(FieldChar fieldChar, String fldCharName) throws Exception;

    private static boolean isHyperlink(FieldChar fieldChar)
    {
        return fieldChar.getFieldType() == FieldType.FIELD_HYPERLINK;
    }

    /// <summary>
    /// Checks if HYPERLINK field can be written as hyperlink.
    /// </summary>
    /// <remarks>
    /// See WORDSNET-10424 test for the details.
    /// </remarks>
    protected /*virtual*/ boolean canWriteAsHyperlink(FieldChar fieldChar)
    {
        Debug.assertTrue(fieldChar.getFieldType() == FieldType.FIELD_HYPERLINK);

        Field field = fieldChar.getField();
        // All field parts should have same parent.
        if ((field.getStart().getParentNode() != field.getSeparator().getParentNode()) || (field.getStart().getParentNode() != field.getEnd().getParentNode()))
            return false;

        if (field.getStart().getRunPr().hasRevisions())
            return false;

        FieldCodeHyperlink fieldCode = FieldCodeHyperlink.parse(field.getFieldCode());

        if (UriUtil.isSubAddressOnly(fieldCode.getAddress()))
            return false;

        if (hasNestedField(field))
            return false;

        // Field code containing revision cannot be written as hyperlink element.
        if (hasFieldCodeRevision(field))
            return false;

        return true;
    }

    /// <summary>
    /// Checks that result of field is single shape node.
    /// </summary>
    private static boolean isSingleShapeResult(Field hyperlinkField)
    {
        Node lastChild = hyperlinkField.getEnd().getPreviousSibling();

        return (lastChild instanceof ShapeBase) && (lastChild.getPreviousSibling() == hyperlinkField.getSeparator());
    }

    /// <summary>
    /// Writes HYPERLINK field in a "w:hyperlink" or "w:hlink" form (according to field format).
    /// </summary>
    private void writeHyperlink(FieldChar fieldChar) throws Exception
    {
        switch (fieldChar.getNodeType())
        {
            case NodeType.FIELD_START:
                FieldCodeHyperlink fieldCode = FieldCodeHyperlink.parse(fieldChar.getField().getFieldCode());

                writeHyperlinkStart(
                    fieldCode.getAddress(),
                    fieldCode.getSubAddress(),
                    fieldCode.getTarget(),
                    fieldCode.getScreenTip(),
                    fieldCode.getDocLocation(),
                    !fieldCode.getNoHistory());
                break;

            case NodeType.FIELD_SEPARATOR:
                break;

            case NodeType.FIELD_END:
                writeHyperlinkEnd();
                break;

            default:
                throw new IllegalStateException("Unknown field char occurred.");
        }
    }

    private static boolean hasNestedField(Field field)
    {
        NodeRange fieldCodeRange = field.getFieldCodeRange();
        for (Node node : fieldCodeRange)
        {
            if (node instanceof FieldChar)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Checks that field has revision in field code area.
    /// </summary>
    private static boolean hasFieldCodeRevision(Field field)
    {
        NodeRange fieldCodeRange = field.getFieldCodeRange();
        for (Node node : fieldCodeRange)
        {
            ITrackableNode trackable = ms.as(node, ITrackableNode.class);
            if (trackable == null)
                continue;

            if ((trackable.getDeleteRevision() != null) || (trackable.getInsertRevision() != null))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Skips field code for HYPERLINK field if it is written as hyperlink.
    /// </summary>
    boolean getSkipThisInline()
    {
        return isInsideFieldCode() &&
            isHyperlink(getTopFieldChar()) &&
            canWriteAsHyperlink(getTopFieldChar()) &&
            // WORDSNET-21886 Do not skip display text for nested hyperlinks.
            getTopFieldChar().getNodeType() == NodeType.FIELD_START;
    }

    /// <summary>
    /// Indicates that field code is currently written.
    /// </summary>
    boolean isInsideFieldCode() { return getFieldStartCount() != 0; }

    boolean isInsideField() { return mFieldCharsStack.getCount() > 0; }

    FieldChar getTopFieldChar() { return StackExtension.top(mFieldCharsStack); }

    private int getFieldStartCount()
    {
        return (mFieldStartCountStack.getCount() > 0) ? mFieldStartCountStack.peek() : 0;
    }
    private void setFieldStartCount(int value)
    {
        // Replace current amount of field beginnings.
        if (mFieldStartCountStack.getCount() > 0)
            mFieldStartCountStack.pop();

        mFieldStartCountStack.push(value);
    }

    private /*final*/ msStack<Integer> mFieldStartCountStack;
    private /*final*/ msStack<FieldChar> mFieldCharsStack;
}

