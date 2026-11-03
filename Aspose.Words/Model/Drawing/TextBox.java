// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;


/// <summary>
/// Defines attributes that specify how a text is displayed inside a shape.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="Shape.TextBox"/> property to access text properties of a shape.
/// You do not create instances of the <see cref="TextBox"/> class directly.</p>
///
/// <seealso cref="Shape.TextBox"/>
/// </remarks>
public class TextBox
{
    TextBox(Shape parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Specifies the inner left margin in points for a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/10 inch.</p>
    /// </remarks>
    public double getInternalMarginLeft() { return mParent.getGraphicData().getInternalMarginLeft(); }
    public void setInternalMarginLeft(double value) { mParent.getGraphicData().setInternalMarginLeft(value); }

    /// <summary>
    /// Specifies the inner right margin in points for a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/10 inch.</p>
    /// </remarks>
    public double getInternalMarginRight() { return mParent.getGraphicData().getInternalMarginRight(); }
    public void setInternalMarginRight(double value) { mParent.getGraphicData().setInternalMarginRight(value); }

    /// <summary>
    /// Specifies the inner top margin in points for a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/20 inch.</p>
    /// </remarks>
    public double getInternalMarginTop() { return mParent.getGraphicData().getInternalMarginTop(); }
    public void setInternalMarginTop(double value) { mParent.getGraphicData().setInternalMarginTop(value); }

    /// <summary>
    /// Specifies the inner bottom margin in points for a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/20 inch.</p>
    /// </remarks>
    public double getInternalMarginBottom() { return mParent.getGraphicData().getInternalMarginBottom(); }
    public void setInternalMarginBottom(double value) { mParent.getGraphicData().setInternalMarginBottom(value); }

    /// <summary>
    /// Determines whether Microsoft Word will grow the shape to fit text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getFitShapeToText() { return mParent.getGraphicData().getFitShapeToText(); }
    public void setFitShapeToText(boolean value) { mParent.getGraphicData().setFitShapeToText(value); }

    /// <summary>
    /// Determines the flow of the text layout in a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.LayoutFlow.Horizontal"/>.</p>
    /// </remarks>
    public /*LayoutFlow*/int getLayoutFlow() { return mParent.getGraphicData().getLayoutFlow(); }
    public void setLayoutFlow(/*LayoutFlow*/int value) { mParent.getGraphicData().setLayoutFlow(value); }

    /// <summary>
    /// Determines how text wraps inside a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Words.Drawing.TextBoxWrapMode.Square"/>.</p>
    /// </remarks>
    public /*TextBoxWrapMode*/int getTextBoxWrapMode() { return mParent.getGraphicData().getTextBoxWrapMode(); }
    public void setTextBoxWrapMode(/*TextBoxWrapMode*/int value) { mParent.getGraphicData().setTextBoxWrapMode(value); }

    /// <summary>
    /// Gets or sets a boolean value indicating either text of the TextBox should not rotate when the shape is rotated.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c></p>
    /// </remarks>
    public boolean getNoTextRotation() { return mParent.getGraphicData().getTextBoxNoTextRotation(); }
    public void setNoTextRotation(boolean value) { mParent.getGraphicData().setTextBoxNoTextRotation(value); }

    /// <summary>
    /// Specifies the vertical alignment of the text within a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.TextBoxAnchor.Top"/>.</p>
    /// </remarks>
    public /*TextBoxAnchor*/int getVerticalAnchor()
    {
        return getTextBoxAnchor();
    }

    public void setVerticalAnchor(/*TextBoxAnchor*/int value)
    {
        switch (value)
        {
            case TextBoxAnchor.TOP:
            case TextBoxAnchor.MIDDLE:
            case TextBoxAnchor.BOTTOM:
                setTextBoxAnchor(value);
                break;
            default:
                setTextBoxAnchor(TextBoxAnchor.TOP);
                break;
        }
    }

    /// <summary>
    /// Determines whether this <see cref="TextBox"/> can be linked to the target <see cref="TextBox"/>.
    /// </summary>
    public boolean isValidLinkTarget(TextBox target)
    {
        return msString.isNullOrEmpty(validateLinkTarget(target));
    }

    /// <summary>
    /// Returns or sets a <see cref="TextBox"/> that represents the next <see cref="TextBox"/> in a sequence of shapes.
    /// </summary>
    public TextBox getNext()
    {
        // Check cached value is valid (shape can be removed, for example).
        if (!isNext(this, mNext))
        {
            // We don't hold Shape reference in DOM and have to iterate all shape to find next in chain.
            mNext = null;
            ShapeCollection shapes = new ShapeCollection(mParent.getDocument());

            for (Shape shape : (Iterable<Shape>) shapes)
                if (isNext(mParent, shape))
                {
                    mNext = shape.getTextBox();
                    break;
                }
        }

        return mNext;
    }
    public void setNext(TextBox value)
    {
        if (value == null)
        {
            breakForwardLink();
        }
        else
        {
            addLink(value);
        }
    }

    /// <summary>
    /// Returns a <see cref="TextBox"/> that represents the previous <see cref="TextBox"/> in a sequence of shapes.
    /// </summary>
    public TextBox getPrevious()
    {
        // Check cached value is valid (shape can be removed, for example).
        if (!isNext(mPrevious, this))
        {
            // We don't hold Shape reference in DOM and have to iterate all shape to find next in chain.
            mPrevious = null;
            ShapeCollection shapes = new ShapeCollection(mParent.getDocument());

            for (Shape shape : (Iterable<Shape>) shapes)
                if (isNext(shape, mParent))
                {
                    mPrevious = shape.getTextBox();
                    break;
                }
        }

        return mPrevious;
    }

    /// <summary>
    /// Breaks the link to the next <see cref="TextBox"/>.
    /// </summary>
    /// <remarks>
    /// <see cref="BreakForwardLink"/> doesn't break all other links in the current sequence of shapes.
    /// For example: 1-2-3-4 sequence and <see cref="BreakForwardLink"/> at the 2-nd textbox will create
    /// two sequences 1-2, 3-4.
    /// </remarks>
    public void breakForwardLink()
    {
        if (mParent.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            // int key - shape's 0-based index in a chain, value - shape.
            SortedIntegerListGeneric<ShapeBase> linkedChain = new SortedIntegerListGeneric<ShapeBase>();

            int id = (mParent.getTextboxId() > 0) ? mParent.getTextboxId() : mParent.getLinkedTextboxId();

            ShapeCollection shapes = new ShapeCollection(mParent.getDocument());
            for (Shape shape : (Iterable<Shape>) shapes)
            {
                if ((shape.getLinkedTextboxId() == id) || (shape.getTextboxId() == id))
                {
                    int index = shape.getTextboxId() > 0 ? 0 : shape.getLinkedTextboxSeq();
                    linkedChain.add(index, shape);
                }
            }

            int currentIndex = mParent.getTextboxId() > 0 ? 0 : mParent.getLinkedTextboxSeq();

            if (linkedChain.getCount() <= 1)
                return;

            // We have a chain, which consists of more than two textboxes. Split it into two parts.
            createNewChain(linkedChain, 0, currentIndex);
            createNewChain(linkedChain, currentIndex + 1, linkedChain.getCount() - 1);

            mParent.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);

            Shape fallBack = (Shape)mParent.getFallbackShape();

            if (fallBack != null)
                fallBack.getTextBox().breakForwardLink();
        }
        else
        {
            mParent.removeShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID);
        }
    }

    /// <summary>
    /// Gets a parent shape for the <see cref="TextBox"/>.
    /// </summary>
    public Shape getParent() { return mParent; }

    /// <summary>
    /// Defines the vertical anchoring of text in a textbox. Word 2007 only.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Words.Drawing.TextBoxAnchor.Top"/>.</p>
    /// </remarks>
    /*TextBoxAnchor*/int getTextBoxAnchor() { return mParent.getGraphicData().getTextBoxAnchor(); }
    void setTextBoxAnchor(/*TextBoxAnchor*/int value) { mParent.getGraphicData().setTextBoxAnchor(value); }

    /// <summary>
    /// Creates a new chain of linked textboxes.
    /// </summary>
    /// <param name="linkedChain"> Initial chain of textboxes. int Key - sequence index, value - textbox</param>
    /// <param name="firstIndex"> Index of a textbox, which will be a head of new chain. </param>
    /// <param name="lastIndex"> Index of a textbox, which will be a tail of new chain</param>
    private void createNewChain(SortedIntegerListGeneric<ShapeBase> linkedChain, int firstIndex, int lastIndex)
    {
        if (firstIndex == lastIndex)
        {
            // Dettach a single shape from the chain.
            ShapeBase shape = linkedChain.get(firstIndex);
            shape.setTextboxId(0);
            shape.setLinkedTextboxId(0);
            shape.setLinkedTextboxSeq(0);
        }
        else
        {
            int id = mParent.getDocument().getNextDmlTextBoxId();

            ShapeBase shape = linkedChain.get(firstIndex);

            // Create a head for a new chain.
            shape.setTextboxId(id);
            shape.setLinkedTextboxId(0);
            shape.setLinkedTextboxSeq(0);

            // Mimic MSW, add an empty paragraph to a newly created head of the chain.
            if (!shape.hasChildNodes())
            {
                Paragraph para = new Paragraph(mParent.getDocument());
                shape.appendChild(para);
            }

            for (int i = 1; i <= lastIndex - firstIndex; i++)
            {
                // Create a chain.
                shape = linkedChain.get(firstIndex + i);
                shape.setTextboxId(0);
                shape.setLinkedTextboxId(id);
                shape.setLinkedTextboxSeq(i);
            }
        }
    }

    /// <summary>
    /// Verifies if link from source TextBox to target TextBox is valid.
    /// </summary>
    private static boolean isNext(TextBox source, TextBox target)
    {
        return !ArgumentUtil.oneIsNull(source, target) && isNext(source.getParent(), target.getParent());
    }

    /// <summary>
    /// Verifies that link from source shape to target shape is valid.
    /// </summary>
    static boolean isNext(Shape sourceShape, Shape targetShape)
    {
        if (sourceShape.getMarkupLanguage() != targetShape.getMarkupLanguage())
            return false;

        // Make sure, that both shapes are still in the document.
        if ((sourceShape.getDocument() != null) && (sourceShape.getDocument() != targetShape.getDocument()))
            return false;

        if (sourceShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            int textboxId = sourceShape.getTextboxId();
            int linkedTextboxId = sourceShape.getLinkedTextboxId();

            if ((textboxId > 0) || (linkedTextboxId > 0))
            {
                int id = (textboxId > 0) ? textboxId : linkedTextboxId;
                int seq = (textboxId > 0) ? 1 : sourceShape.getLinkedTextboxSeq() + 1;

                return (targetShape.getLinkedTextboxId() == id) && (targetShape.getLinkedTextboxSeq() == seq);
            }

            return false;
        }
        else
        {
            return (sourceShape.getTextboxNextShapeId() == targetShape.getId());
        }
    }

    /// <summary>
    /// Creates a link between textboxes.
    /// </summary>
    private void addLink(TextBox target)
    {
        String errorMessage = validateLinkTarget(target);

        if (StringUtil.hasChars(errorMessage))
            throw new IllegalArgumentException(errorMessage);

        Shape sourceShape = mParent;
        Shape targetShape = target.getParent();

        if (sourceShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            int textboxId = mParent.getTextboxId();
            int linkedTextboxId = mParent.getLinkedTextboxId();

            if (textboxId > 0)
            {
                // Head of the linked chain.
                targetShape.setLinkedTextboxId(textboxId);
                targetShape.setLinkedTextboxSeq(1);
            }
            else if (linkedTextboxId > 0)
            {
                // Tail of the linked chain.
                targetShape.setLinkedTextboxId(linkedTextboxId);
                targetShape.setLinkedTextboxSeq(mParent.getLinkedTextboxSeq() + 1);
            }
            else
            {
                // Create a new link.
                textboxId = mParent.getDocument().getNextDmlTextBoxId();
                mParent.setTextboxId(textboxId);
                targetShape.setLinkedTextboxId(textboxId);
                targetShape.setLinkedTextboxSeq(1);
            }

            targetShape.removeAllChildren();

            TextBox sourceFallbackTextbox = getFallBackTextBox(sourceShape);
            TextBox targetFallbackTextbox = getFallBackTextBox(targetShape);

            if ((sourceFallbackTextbox != null) && (targetFallbackTextbox != null))
                sourceFallbackTextbox.setNext(targetFallbackTextbox);
        }
        else
        {
            sourceShape.setTextboxNextShapeId(targetShape.getId());
        }
    }

    /// <summary>
    /// Determines whether the TextBox can be linked to the target Textbox, and returns error message
    /// if it's not possible. Returns empty string, if everything is Ok.
    /// </summary>
    private String validateLinkTarget(TextBox target)
    {
        Shape sourceShape = mParent;
        Shape targetShape = target.getParent();

        // Check for the target textbox.
        if ((mParent == null) || (target.getParent() == null))
            return WarningStrings.LINK_TEXTBOX_INVALID_TARGET;

        // Check for the target textbox.
        if (mParent.getId() == targetShape.getId())
            return WarningStrings.LINK_TEXTBOX_INVALID_TARGET;

        // Check for the document.
        if ((sourceShape.getDocument() == null) || (sourceShape.getDocument() != targetShape.getDocument()))
            return WarningStrings.LINK_TEXTBOX_SAME_DOCUMENT;

        // Linked shapes must be able to store text.
        if (!shapeCanStoreText(sourceShape))
            return WarningStrings.LINK_TEXTBOX_BOTH_SHAPES_TEXT;

        if (!shapeCanStoreText(targetShape))
            return WarningStrings.LINK_TEXTBOX_BOTH_SHAPES_TEXT;

        // Target textbox must be empty. The case with a single empty paragraph is good enough for linking.
        String targetText = targetShape.getText();
        if ((!msString.equals(targetText, "")) && (!"\r".equals(targetText)))
            return WarningStrings.LINK_TEXTBOX_EMPTY_TARGET;

        // Source and target must be one type DML/VML.
        if (sourceShape.getMarkupLanguage() != targetShape.getMarkupLanguage())
            return WarningStrings.LINK_TEXTBOX_SAME_MARKUP;

        // Verify if textbox already has a link.
        if (getNext() != null)
            return WarningStrings.LINK_TEXTBOX_HAS_LINK;

        // Verify if target textbox is linked.
        if (target.getPrevious() != null)
            return WarningStrings.LINK_TEXTBOX_TARGET_IS_LINKED;

        // Verify if textboxes are from different story types.
        if (differentStoryTypes(targetShape, NodeType.BODY) || differentStoryTypes(targetShape, NodeType.HEADER_FOOTER))
            return WarningStrings.LINK_TEXTBOX_DIFFERENT_STORY_TYPES;

        // Get fallback ValidateLinkTarget.
        if (mParent.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            TextBox sourceFallbackTextbox = getFallBackTextBox(sourceShape);
            TextBox targetFallbackTextbox = getFallBackTextBox(targetShape);

            if ((sourceFallbackTextbox != null) && (targetFallbackTextbox != null))
                return sourceFallbackTextbox.validateLinkTarget(targetFallbackTextbox);
        }

        return "";
    }

    /// <summary>
    /// If exists, gets a Textbox for a fallback shape, returns null otherwise.
    /// </summary>
    private static TextBox getFallBackTextBox(Shape shape)
    {
        return (shape.getFallbackShape() == null) ? null : ((Shape)shape.getFallbackShape()).getTextBox();
    }

    /// <summary>
    /// Verifies story type for the linked textboxes. Returns true if we should throw an exception.
    /// </summary>
    /// <remarks>
    /// There are several story types which are suitable for linking. For example, textboxes from MainText are good
    /// for linking. Textboxes from the different types of Headers/Footers are also good for linking. But when one textbox
    /// belongs to MainText and other textbox belongs to Headers/Footers - MSW refuses linking.
    /// </remarks>
    private boolean differentStoryTypes(ShapeBase targetShape, /*NodeType*/int nodeType)
    {
        boolean source = mParent.getAncestor(nodeType) != null;
        boolean target = targetShape.getAncestor(nodeType) != null;

        return source != target;
    }

    /// <summary>
    /// Determines if this shape is able to store a text.
    /// </summary>
    private static boolean shapeCanStoreText(ShapeBase shape)
    {
        return (shape.getShapeType() == ShapeType.TEXT_BOX) || (shape.hasChildNodes());
    }

    private /*final*/ Shape mParent;

    private TextBox mPrevious;
    private TextBox mNext;
}

