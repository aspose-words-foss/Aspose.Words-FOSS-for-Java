// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Represents an Office Math object such as function, equation, matrix or alike. Can contain child elements
/// including runs of mathematical text, bookmarks, comments, other <see cref="OfficeMath"/> instances and some other nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-officemath/">Working with OfficeMath</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>In this version of Aspose.Words, <see cref="OfficeMath"/> nodes do not provide public methods
/// and properties to create or modify a <see cref="OfficeMath"/> object. In this version you are not able to instantiate
/// <see cref="Aspose.Words.Math"/> nodes or modify existing except deleting them.</para>
///
/// <p><see cref="OfficeMath"/> can only be a child of <see cref="Paragraph"/>.</p>
/// </remarks>
public class OfficeMath extends CompositeNode<Node> implements IInline, ITrackableNode
{
    OfficeMath(DocumentBase doc, MathObject mathObject, RunPr runPr)
    {
    	super(doc);
	
        mMathObject = mathObject;
        if (runPr == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "runPr");

        mRunPr = runPr;
    }

    OfficeMath(DocumentBase doc, MathObject mathObject)
    {
    	this(doc, mathObject, new RunPr());
	
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitOfficeMathStart"/>, then calls <see cref="Node.Accept"/> for all
    /// child nodes of the Office Math and calls <see cref="DocumentVisitor.VisitOfficeMathEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        OfficeMath lhs = (OfficeMath)super.deepClone(isCloneChildren, cloningListener);
        lhs.mRunPr = AttrCollectionExtension.deepClone(mRunPr);
        lhs.mMathObject = AttrCollectionExtension.deepClone(mMathObject);
        return lhs;
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the office math.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitOfficeMathStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the office math.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitOfficeMathEnd(this);
    }

    /// <summary>
    /// Allows to insert other Office Math objects and some non-math nodes.
    /// Types of nodes allowed to be inserted depends on the particular math node type.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return mMathObject.canInsert(newChild);
    }

    /// <summary>
    /// Returns top level office math that is ancestor of this office math.
    /// </summary>
    OfficeMath getTopLevelOfficeMath()
    {
        OfficeMath parentMath = ms.as(getParentNode(), OfficeMath.class);
        return (parentMath == null) ? this : parentMath.getTopLevelOfficeMath();
    }

    /// <summary>
    /// This method determines if this OfficeMath object has a delete revision.
    /// </summary>
    /// <summary>
    /// In HTML we can't write "ins" and "del" elements inside MathML.
    /// Thereby to produce valid HTML we consider that OfficeMath object has a revision
    /// if all its children that can have revisions have them.
    /// </summary>
    boolean hasDeleteRevision()
    {
        return hasRevisableParts() && allRevisablePartsHaveDeleteRevisions();
    }

    /// <summary>
    /// This method determines if this OfficeMath object has an insert revision.
    /// </summary>
    /// <summary>
    /// In HTML we can't write "ins" and "del" elements inside MathML.
    /// Thereby to produce valid HTML we consider that OfficeMath object has a revision
    /// if all its children that can have revisions have them.
    /// </summary>
    boolean hasInsertRevision()
    {
        return hasRevisableParts() && allRevisablePartsHaveInsertRevisions();
    }

    /// <summary>
    /// Recursively checks if this OfficeMath has at least one revisable part.
    /// </summary>
    private boolean hasRevisableParts()
    {
        if (isRevisable())
        {
            return true;
        }

        Node childNode = getFirstChild();
        while (childNode != null)
        {
            OfficeMath childMath = ms.as(childNode, OfficeMath.class);
            if ((childMath != null) && childMath.hasRevisableParts())
            {
                return true;
            }
            childNode = childNode.getNextSibling();
        }

        return false;
    }

    private boolean allRevisablePartsHaveDeleteRevisions()
    {
        if (isRevisable() && !getRunPr().hasDeleteRevision())
        {
            return false;
        }

        Node childNode = getFirstChild();
        while (childNode != null)
        {
            OfficeMath childMath = ms.as(childNode, OfficeMath.class);
            if ((childMath != null) && !childMath.allRevisablePartsHaveDeleteRevisions())
            {
                return false;
            }
            childNode = childNode.getNextSibling();
        }

        return true;
    }

    private boolean allRevisablePartsHaveInsertRevisions()
    {
        if (isRevisable() && !getRunPr().hasInsertRevision())
        {
            return false;
        }

        Node childNode = getFirstChild();
        while (childNode != null)
        {
            OfficeMath childMath = ms.as(childNode, OfficeMath.class);
            if ((childMath != null) && !childMath.allRevisablePartsHaveInsertRevisions())
            {
                return false;
            }
            childNode = childNode.getNextSibling();
        }

        return true;
    }


    private String toStringInternal(String indent)
    {
        StringBuilder sb = new StringBuilder();
        msStringBuilder.appendLine(sb, indent + "MathObject:" + mMathObject.getMathObjectType());
        childNodesToString(sb, this, indent + "  ");
        return sb.toString();
    }

    /// <summary>
    /// Determines if OfficeMath object can have revisions.
    /// </summary>
    private boolean isRevisable()
    {
        return (getMathObjectType() != MathObjectType.O_MATH_PARA) &&
               (getMathObjectType() != MathObjectType.O_MATH) &&
               (getMathObjectType() != MathObjectType.ARGUMENT) &&
               (getMathObjectType() != MathObjectType.SUBSCRIPT_PART) &&
               (getMathObjectType() != MathObjectType.SUPERSCRIPT_PART) &&
               (getMathObjectType() != MathObjectType.NUMERATOR) &&
               (getMathObjectType() != MathObjectType.DENOMINATOR) &&
               (getMathObjectType() != MathObjectType.FUNCTION_NAME) &&
               (getMathObjectType() != MathObjectType.DEGREE);
    }

    private static void childNodesToString(StringBuilder sb, CompositeNode rootNode, String indent)
    {
        for (Node node : (Iterable<Node>) rootNode)
        {
            if (node instanceof OfficeMath)
            {
                msStringBuilder.appendLine(sb, ((OfficeMath)node).toStringInternal(indent));
            }
            else
            {
                msStringBuilder.appendLine(sb, indent + nodeTypeToString(node.getNodeType()));
                if (node instanceof CompositeNode)
                    childNodesToString(sb, (CompositeNode)node, indent + "  ");
            }
        }
    }

    boolean isTopLevel() { return (getParentNode() != null) && (getParentNode().getNodeType() != NodeType.OFFICE_MATH); }

    /// <summary>
    /// Returns true if office math must be rendered in the inline mode.
    /// </summary>
    boolean isInline() { return OfficeMathUtil.isInline(this); }

    /// <summary>
    /// Returns true if office math is converted from Ruby.
    /// </summary>
    boolean isConvertedFromRuby() { return mIsConvertedFromRuby; }
    void isConvertedFromRuby(boolean value) { mIsConvertedFromRuby = value; }

    /// <summary>
    /// Returns true if office math is converted from EQ field.
    /// </summary>
    boolean isConvertedFromEQ() { return mIsConvertedFromEQ; }
    void isConvertedFromEQ(boolean value) { mIsConvertedFromEQ = value; }

    /// <summary>
    /// Specifies the horizontal offset.
    /// </summary>
    /// <remarks>
    /// Specifies the offset applied to the element defined by the \d EQ token (displacement).
    /// </remarks>
    int getDisplacement() { return mDisplacement; }; void setDisplacement(int value) { mDisplacement = value; };

    private int mDisplacement;

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return mRunPr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mRunPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int fontAttr)
    {
        return InlineHelper.fetchInheritedAttr(this, fontAttr);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int fontAttr, Object value)
    {
        mRunPr.setAttr(fontAttr, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        mRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        mRunPr.clear();
    }

    //<<<<<<<< #endregion  IRunAttrSource

    /// <summary>
    /// Returns <see cref="NodeType.OfficeMath"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.OFFICE_MATH; }

    /// <summary>
    /// Retrieves the parent <see cref="Paragraph"/> of this node.
    /// </summary>
    public Paragraph getParentParagraph() { return (Paragraph)getAncestor(NodeType.PARAGRAPH); }

    /// <summary>
    /// All properties specific to particular math entities are encapsulated inside these descendants.
    /// </summary>
    MathObject getMathObject() { return mMathObject; }
    void setMathObject(MathObject value) { mMathObject = value; }

    RunPr getRunPr() { return mRunPr; }

    @ReservedForInternalUse @Deprecated
    public Paragraph /*IInline.*/getParentParagraph_IInline() { return this.getParentParagraph(); }

    @ReservedForInternalUse @Deprecated
    public DocumentBase /*IInline.*/getDocument_IInline() { return super.getDocument(); }

    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getExpandedRunPr_IInline(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getRunPr_IInline() { return mRunPr; }
    @ReservedForInternalUse @Deprecated
    public void /*IInline.*/setRunPr_IInline(RunPr value) { mRunPr = value; }

    /// <summary>
    /// Full copy of #DEBUG Node.ToString(). Used by OdtWriter to generate Id for OfficeMath objects.
    /// </summary>
    String getId()
    {
        StringBuilder sb = new StringBuilder(nodeTypeToString(getNodeType()));
        sb.append(' ');

        for (Node node = this; node != null && node.getParentNode() != null; node = node.getParentNode())
        {
            int index = 0;
            for (Node n = node.getParentNode().getFirstChild(); n != null && n != node; n = n.getNextSibling())
                index++;
            msStringBuilder.appendFormat(sb, "{0}{1}", (node == this ? "" : "."), index);
        }

        return sb.toString();
    }

    /// <summary>
    /// Gets type <see cref="MathObjectType"/> of this Office Math object.
    /// </summary>
    public /*MathObjectType*/int getMathObjectType() { return mMathObject.getMathObjectType(); }

    /// <summary>
    /// Gets/sets an encoding that was used to encode equation XML, if this office math object is read from
    /// equation XML.
    /// </summary>
    /// <dev>
    /// We use the encoding on saving a document to write in same encoding that it was read.
    /// </dev>
    Encoding getEquationXmlEncoding() { return mEquationXmlEncoding; }
    void setEquationXmlEncoding(Encoding value) { mEquationXmlEncoding = value; }

    /// <summary>
    /// Gets/sets Office Math justification.
    /// </summary>
    /// <remarks>
    /// <para>Justification cannot be set to the Office Math with display format type <see cref="OfficeMathDisplayType.Inline"/>.</para>
    /// <para>Inline justification cannot be set to the Office Math with display format type <see cref="OfficeMathDisplayType.Display"/>.</para>
    /// <para>Corresponding <see cref="DisplayType"/> has to be set before setting Office Math justification.</para>
    /// </remarks>
    public /*OfficeMathJustification*/int getJustification()
    {
        if (getDisplayType() == OfficeMathDisplayType.DISPLAY)
            return ((MathObjectOMathPara)getMathObject()).getJustification();

        return OfficeMathJustification.INLINE;
    }
    public void setJustification(/*OfficeMathJustification*/int value)
    {
        switch (getDisplayType())
        {
            case OfficeMathDisplayType.DISPLAY:
            {
                if (value == OfficeMathJustification.INLINE)
                    throw new IllegalArgumentException("Inline justification cannot be set to the Office Math displayed on its own line. " +
                        "Please, use OfficeMath.DisplayType property to change OfficeMathDisplayType.");

                ((MathObjectOMathPara)getMathObject()).setJustification(value);
                break;
            }
            case OfficeMathDisplayType.INLINE:
            {
                if (value != OfficeMathJustification.INLINE)
                    throw new IllegalArgumentException("Justification cannot be set to the Office Math displayed inline with text. " +
                        "Please, use OfficeMath.DisplayType property to change OfficeMathDisplayType.");
                break;
            }
            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// Gets/sets Office Math display format type which represents whether an equation is displayed inline with the text
    /// or displayed on its own line.
    /// </summary>
    /// <remarks>
    /// <para>Display format type has effect for top level Office Math only.</para>
    /// <para>Returned display format type is always <see cref="OfficeMathDisplayType.Inline"/> for nested Office Math.</para>
    /// </remarks>
    public /*OfficeMathDisplayType*/int getDisplayType()
    {
        if (getMathObject().getMathObjectType() == MathObjectType.O_MATH_PARA)
            return OfficeMathDisplayType.DISPLAY;

        return OfficeMathDisplayType.INLINE;
    }
    public void setDisplayType(/*OfficeMathDisplayType*/int value)
    {
        // DisplayType can be changed for top level Office Math only.
        // OMath paragraph cannot be nested inside another OMath paragraph.
        if (!isTopLevel())
            throw new IllegalArgumentException("DisplayType cannot be changed for the nested Office Math. Please, check the parent node type to make sure it is top level Office Math.");

        switch (value)
        {
            case OfficeMathDisplayType.INLINE:
                OfficeMathUtil.changeToInline(this);
                break;
            case OfficeMathDisplayType.DISPLAY:
                OfficeMathUtil.changeToDisplay(this);
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private MathObject mMathObject;
    private RunPr mRunPr;
    private Encoding mEquationXmlEncoding;
    private boolean mIsConvertedFromRuby;
    private boolean mIsConvertedFromEQ;

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getRunPr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getRunPr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getRunPr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getRunPr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getRunPr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getRunPr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getRunPr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getRunPr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getRunPr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getRunPr().remove(RevisionAttr.MOVE_TO_REVISION);
    }
}

