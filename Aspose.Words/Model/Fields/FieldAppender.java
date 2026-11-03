// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2015 by Vadim Polienko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Provides functions for inserting/appending fields either from DocumentBuilder or Paragraph.
/// </summary>
/*static*/ class FieldAppender
{
	/* Simulation of static class by using private constructor */
	private FieldAppender()
	{}

    /// <summary>Insert Field.</summary>
    /// <remarks>This method is called from DocumentBuilder or Paragraph.</remarks>
    /// <param name="fieldType">The field type to insert.</param>
    /// <param name="updateField">Whether to update the field immediately or not.</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    static Field insertField(/*FieldType*/int fieldType, boolean updateField, RunPr runPr, CompositeNode parent,
        Node refNode, boolean isAfter) throws Exception
    {
        String fieldCode = FieldUtil.fetchFieldCode(fieldType);

        runPr = AttrCollectionExtension.deepClone(runPr);
        RevisionUtil.removeRevisions(runPr);

        Field field = insertField(msString.format(" {0} ", fieldCode), null, runPr, parent, refNode, isAfter);

        if (updateField)
            field.update();

        return field;
    }

    /// <summary>Insert Field.</summary>
    /// <remarks>This method is called from DocumentBuilder or Paragraph.</remarks>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    static Field insertField(String fieldCode, RunPr runPr, CompositeNode parent, Node refNode, boolean isAfter) throws Exception
    {
        Field field = insertField(fieldCode, null, runPr, parent, refNode, isAfter);
        field.update();
        return field;
    }

    /// <summary>Insert Field.</summary>
    /// <remarks>This method is called from DocumentBuilder or Paragraph.</remarks>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="fieldValue">The field value to insert. Pass null for fields that do not have a value.</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="initialRefNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    static Field insertField(String fieldCode, String fieldValue, RunPr runPr, CompositeNode parent,
        Node initialRefNode, boolean isAfter)
    {
        FieldSeparator separator;
        FieldEnd end;
        Node refNode = initialRefNode;

        /*FieldType*/int fieldType = FieldUtil.getFieldType(fieldCode);

        FieldStart start = insertFieldStart(fieldType, runPr, parent, refNode, isAfter);
        refNode = start;

        Run fieldCodeNode = insertFieldCode(fieldCode, runPr, parent, refNode, true);
        refNode = fieldCodeNode;

        if (FieldUtil.getSeparatorPresence(fieldType) == FieldSeparatorPresence.ALWAYS)
        {
            separator = insertFieldSeparator(fieldType, runPr, parent, refNode, true);
            refNode = separator;

            if (fieldValue != null)
            {
                Run fieldValueNode = insertRun(fieldValue, runPr, parent, refNode, true);
                refNode = fieldValueNode;
            }

            end = insertFieldEnd(fieldType, true, runPr, parent, refNode, true);
        }
        else
        {
            separator = null;
            end = insertFieldEnd(fieldType, false, runPr, parent, refNode, true);
        }

        return FieldFactory.createField(start, separator, end);
    }

    /// <summary>
    /// Insert FieldStart (this method is called from DocumentBuilder or internally).
    /// </summary>
    /// <param name="fieldType">The field type to insert.</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="FieldStart"/> object that represents the inserted field start.</returns>
    static FieldStart insertFieldStart(/*FieldType*/int fieldType, RunPr runPr, CompositeNode parent,
        Node refNode, boolean isAfter)
    {
        FieldStart fieldStart = new FieldStart(parent.getDocument(), AttrCollectionExtension.deepClone(runPr), fieldType);
        insertNode(fieldStart, parent, refNode, isAfter);
        return fieldStart;
    }

    /// <summary>
    /// Insert FieldCode (this method is called from DocumentBuilder or internally).
    /// </summary>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="Run"/> object that represents the inserted code.</returns>
    static Run insertFieldCode(String fieldCode, RunPr runPr, CompositeNode parent, Node refNode,
        boolean isAfter)
    {
        Run fieldCodeNode = insertRun(fieldCode, runPr, parent, refNode, isAfter);
        return fieldCodeNode;
    }

    /// <summary>
    /// Insert FieldSeparator (this method is called from DocumentBuilder or internally).
    /// </summary>
    /// <param name="fieldType">The field type to insert.</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="FieldSeparator"/> object that represents the inserted field separator.</returns>
    static FieldSeparator insertFieldSeparator(/*FieldType*/int fieldType, RunPr runPr, CompositeNode parent,
        Node refNode, boolean isAfter)
    {
        FieldSeparator fieldSeparator = new FieldSeparator(parent.getDocument(), AttrCollectionExtension.deepClone(runPr), fieldType);
        insertNode(fieldSeparator, parent, refNode, isAfter);
        return fieldSeparator;
    }

    /// <summary>
    /// Insert FieldEnd (this method is called from DocumentBuilder or internally).
    /// </summary>
    /// <param name="fieldType">The field type to insert.</param>
    /// <param name="hasSeparator">Whether this field has a field separator.</param>
    /// <param name="runPr">Attributes for inserted field.</param>
    /// <param name="parent">A composite node to insert the field into.</param>
    /// <param name="refNode">A reference node inside the parent node.</param>
    /// <param name="isAfter">Whether to insert the field after or before the reference node.</param>
    /// <returns>A <see cref="FieldEnd"/> object that represents the inserted field end.</returns>
    static FieldEnd insertFieldEnd(/*FieldType*/int fieldType, boolean hasSeparator, RunPr runPr,
        CompositeNode parent, Node refNode, boolean isAfter)
    {
        FieldEnd fieldEnd = new FieldEnd(parent.getDocument(), AttrCollectionExtension.deepClone(runPr), fieldType, hasSeparator);
        insertNode(fieldEnd, parent, refNode, isAfter);
        return fieldEnd;
    }

    /// <summary>
    /// Insert text (either from DocumentBuilder or Paragraph).
    /// </summary>
    /// <param name="text">Text to insert.</param>
    /// <param name="runPr"></param>
    /// <param name="parent"></param>
    /// <param name="refNode"></param>
    /// <param name="isAfter"></param>
    private static Run insertRun(String text, RunPr runPr, CompositeNode parent, Node refNode, boolean isAfter)
    {
        Run newRunNode = new Run(parent.getDocument(), text, AttrCollectionExtension.deepClone(runPr));
        insertNode(newRunNode, parent, refNode, isAfter);
        return newRunNode;
    }

    /// <summary>
    /// Insert node (either from DocumentBuilder or Paragraph).
    /// </summary>
    /// <param name="node">Node to insert.</param>
    /// <param name="parent"></param>
    /// <param name="refNode"></param>
    /// <param name="isAfter"></param>
    private static void insertNode(Node node, CompositeNode parent, Node refNode, boolean isAfter)
    {
        if (isAfter)
        {
            if (refNode == null)
                parent.appendChild(node);
            else
                parent.insertAfter(node, refNode);
        }
        else
        {
            parent.insertBefore(node, (refNode != null ? refNode : parent.getLastChild()));
        }
    }
}

