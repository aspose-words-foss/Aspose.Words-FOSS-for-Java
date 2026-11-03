// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;


/// <summary>
/// Represents a group of shapes in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/how-to-add-group-shape-into-a-word-document/">How to Add Group Shape into a Word Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A <see cref="GroupShape"/> is a composite node and can have <see cref="Shape"/> and
/// <see cref="GroupShape"/> nodes as children.</p>
/// 
/// <p>Each <see cref="GroupShape"/> defines a new coordinate system for its child shapes.
/// The coordinate system is defined using the <see cref="ShapeBase.CoordSize"/> and 
/// <see cref="ShapeBase.CoordOrigin"/> properties.</p>
/// 
/// <seealso cref="ShapeBase"/>
/// <seealso cref="Shape"/>
/// </remarks>
public class GroupShape extends ShapeBase
{
    /// <summary>
    /// Creates a new group shape.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    /// <remarks>
    /// <p>By default, the shape is floating and has default location and size.</p>
    /// <p>You should specify desired shape properties after you created a shape.</p>
    /// </remarks>
    public GroupShape(DocumentBase doc)
    {
    	this(doc, ShapeMarkupLanguage.VML);
	
    }

    /// <summary>
    /// Creates a new group shape.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    /// <param name="markupLanguage">Shape markup language: DrawingML or Vml</param>
    /// <remarks>
    /// <p>By default, the shape is floating and has default location and size.</p>
    /// <p>You should specify desired shape properties after you created a shape.</p>
    /// </remarks>
    GroupShape(DocumentBase doc, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
    	super(doc, markupLanguage);
	
        setShapeType(ShapeType.GROUP);
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitGroupShapeStart"/>, then calls <see cref="Node.Accept"/> for all 
    /// child shapes of this group shape and calls <see cref="DocumentVisitor.VisitGroupShapeEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the GroupShape.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitGroupShapeStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the GroupShape.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitGroupShapeEnd(this);
    }

    /// <summary>
    /// Can only insert other shapes and group shapes into this node.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        Debug.assertTrue(newChild != null);

        ShapeBase newChildShape = ms.as(newChild, ShapeBase.class);

        if (newChildShape == null)
            return false;

        if (getMarkupLanguage() != newChildShape.getMarkupLanguage())
        {
            throw new IllegalArgumentException(
                "Cannot insert a Shape into a GroupShape with a different markup language"
                );
        }

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return canInsertBase(newChild);

        /*DmlNodeType*/int nodeType = getDmlNode().getDmlNodeType();
        /*DmlNodeType*/int newNodeType = newChildShape.getDmlNode().getDmlNodeType();

        if ((nodeType != DmlNodeType.GRAPHIC_FRAME) && (newNodeType != DmlNodeType.GRAPHIC_FRAME))
            return canInsertBase(newChild);

        // According to spec graphic frame can be child element of the group or canvas.
        // But actually MSW does not allow insert graphic frame into canvas.
        // Chart can contain graphic frame among user shapes. Although DML chart is initialized
        // as DML node of the Shape type and DML node property has not any restrictions,
        // so for group shape DML node can be assigned with "Chart" type.
        if (newNodeType == DmlNodeType.GRAPHIC_FRAME)
            return (nodeType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) ||
                   (nodeType == DmlNodeType.GROUP_SHAPE) ||
                   (nodeType == DmlNodeType.LOCKED_CANVAS) ||
                   (nodeType == DmlNodeType.CHART) ||
                   (nodeType == DmlNodeType.CHART_EX);

        // Generally content of the graphic frame expected object which is serialized out as xml.
        return (newNodeType == DmlNodeType.CHART) || (newNodeType == DmlNodeType.CHART_EX) ||
            (newNodeType == DmlNodeType.DIAGRAM);                       
    }

    /// <summary>
    /// Checks ability to insert node into group shape for all types of nodes
    /// exclude graphic frame shape.
    /// </summary>
    /// <param name="newChild">Node to insert.</param>
    /// <returns>True, when specified node can be inserted.</returns>
    private static boolean canInsertBase(Node newChild)
    {
        Debug.assertTrue(newChild != null);

        switch (newChild.getNodeType())
        {
            case NodeType.GROUP_SHAPE:
            case NodeType.SHAPE:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.GroupShape"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.GROUP_SHAPE; }

    /// <summary>
    /// Specifies how the group shape should be edited in Microsoft Word.
    /// </summary>
    /*EditAs*/int getEditAs() { return (/*EditAs*/Integer)fetchShapeAttrInternal(ShapeAttr.EDIT_AS); }
    void setEditAs(/*EditAs*/int value) { setShapeAttrInternal(ShapeAttr.EDIT_AS, value); }

    /*override*/ boolean isPercentWidthInapplicable() { return isInline() && (getMarkupLanguage() != ShapeMarkupLanguage.VML); }
}

