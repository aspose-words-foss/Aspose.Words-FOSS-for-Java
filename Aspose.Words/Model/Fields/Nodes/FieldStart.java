// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a start of a Word field in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="FieldStart"/> is an inline-level node and represented by the
/// <see cref="ControlChar.FieldStartChar"/> control character in the document.</p>
/// 
/// <p><see cref="FieldStart"/> can only be a child of <see cref="Paragraph"/>.</p>
/// 
/// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="Field.Common"]/*'/>
/// </remarks>
public class FieldStart extends FieldChar
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    FieldStart(DocumentBase doc, RunPr runPr, /*FieldType*/int type)
    {
    	super(doc, ControlChar.FIELD_START_CHAR, runPr, type);
	
    }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.FieldStart"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.FIELD_START; }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitFieldStart"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><b>False</b> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitFieldStart(this));
    }

    /// <summary>
    /// Gets an extended data corresponding to this field start. Should be used for form fields' starts only.
    /// </summary>
    /// <remarks>
    /// ECMA-376 states that w:ffData element must be a child of w:fldChar element, i.e. its parent is not
    /// necessary to be a field start. But MS Word stores this data only for field starts. Moreover,
    /// manual adding of this data to a field end and document resaving via MS Word removes this data from
    /// the field end. So it makes no sense to expand this property for <see cref="FieldChar"/> class.
    /// </remarks>
    FormField getFormField()
    {
        Debug.assertTrue(FieldUtil.isFormField(getFieldType()));

        return findFormField();
    }

    private FormField findFormField()
    {
        int level = 0;
        DocumentPosition position = DocumentPosition.createPositionAfter(this);

        while (true)
        {
            if (!position.move(null, true, true, true, false, false))
                return null;

            Node node = position.getNode();

            switch (node.getNodeType())
            {
                case NodeType.FIELD_START:
                    level++;
                    break;

                case NodeType.FIELD_SEPARATOR:
                    if (level == 0)
                        return null;

                    break;

                case NodeType.FIELD_END:
                    if (level == 0)
                        return null;

                    level--;
                    break;

                case NodeType.FORM_FIELD:
                    if (level == 0)
                        return (FormField)node;

                    break;

                default:
                    // Do nothing.
                    break;
            }
        }

    }

    /// <summary>
    /// Gets custom field data which is associated with the field.
    /// </summary>
    public byte[] getFieldData() { return mFieldData; }; void setFieldData(byte[] value) { mFieldData = value; };

    private byte[] mFieldData;
}

