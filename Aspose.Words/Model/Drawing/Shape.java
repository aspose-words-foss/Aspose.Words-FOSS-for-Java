// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.DrColor;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.Collections.msArrayList;
import java.awt.Color;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents an object in the drawing layer, such as an AutoShape, textbox, freeform, OLE object, ActiveX control, or picture.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Using the <see cref="Shape"/> class you can create or modify shapes in a Microsoft Word document.</p>
///
/// <p>An important property of a shape is its <see cref="ShapeBase.ShapeType"/>. Shapes of different
/// types can have different capabilities in a Word document. For example, only image and OLE shapes
/// can have images inside them. Most of the shapes can have text, but not all.</p>
///
/// <p>Shapes that can have text, can contain <see cref="Aspose.Words.Paragraph"/> and
/// <see cref="Aspose.Words.Tables.Table"/> nodes as children.</p>
///
/// <seealso cref="ShapeBase"/>
/// <seealso cref="GroupShape"/>
/// </remarks>
public final class Shape extends ShapeBase implements ITextBox, IStrokable
{
    /// <summary>
    /// Ctor. Use this internally only.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    Shape(DocumentBase doc)
    {
    	this(doc, ShapeMarkupLanguage.VML);
	
    }

    /// <summary>
    /// Ctor. Use this internally only.
    /// WORDSNET-11493 Implement possibility to create DML shapes by default.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    /// <param name="markupLanguage">Shape markup language: DrawingML or Vml</param>
    Shape(DocumentBase doc, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
    	super(doc, markupLanguage);
	 }

    /// <summary>
    /// Creates a new shape object.
    /// </summary>
    /// <remarks>
    /// <p>You should specify desired shape properties after you created a shape.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <param name="shapeType">The type of the shape to create.</param>
    public Shape(DocumentBase doc, /*ShapeType*/int shapeType)
    {
    	this(doc, ShapeMarkupLanguage.VML);
	
        if (!isAllowedShapeTypes(shapeType) || !DmlEnum.hasVmlPresetGeometry(shapeType))
            throw new UnsupportedOperationException("Cannot create shapes of this type.");

        setShapeType(shapeType);
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitShapeStart"/>, then calls <see cref="Node.Accept"/>
    /// for all child nodes of the shape and calls <see cref="DocumentVisitor.VisitShapeEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the shape.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitShapeStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the shape.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitShapeEnd(this);
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Clone(bool,INodeCloningListener)"]/*'/>
    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Shape lhs = (Shape)super.deepClone(isCloneChildren, cloningListener);
        lhs.mStroke = null;
        lhs.mStrokeCore = null;
        lhs.mTextBoxCache = null;
        lhs.mSignatureLineCache = null;
        lhs.mChart = null;
        lhs.mImageDataCache = null;
        ((IFillable)lhs).setFill(null);

        return lhs;
    }

    /*override*/ boolean isPercentWidthInapplicable()
    {
        if (isInline())
            return true;

        if (!hasTextbox())
            return false;

        /*LayoutFlow*/int layoutFlow = getTextBox().getLayoutFlow();

        return (layoutFlow == LayoutFlow.VERTICAL)
               || (layoutFlow == LayoutFlow.BOTTOM_TO_TOP)
               || (layoutFlow == LayoutFlow.TOP_TO_BOTTOM)
               || (layoutFlow == LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC);
    }

    /// <summary>
    /// Can only insert paragraphs and tables into this node.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return NodeUtil.isBlockLevelNode(newChild);

        /*DmlNodeType*/int dmlNodeType = getDmlNode().getDmlNodeType();

        // Only WordprocessingShape can contain other BlockLevel nodes.
        if ((dmlNodeType == DmlNodeType.WORDPROCESSING_SHAPE) && NodeUtil.isBlockLevelNode(newChild))
            return true;

        if ((newChild.getNodeType() != NodeType.SHAPE) && (newChild.getNodeType() != NodeType.GROUP_SHAPE))
            return false;

        if (!(getDmlNode() instanceof DmlCompositeNode) &&
            (dmlNodeType != DmlNodeType.CHART) &&
            (dmlNodeType != DmlNodeType.CHART_EX))
            return false;

        // Current shape can not hold WordprocessingCanvas, WordprocessingGroupShape, LockedCanvas or GroupShape
        // DML node types in DmlNode property. DML nodes with this types have to be placed into GroupShape.
        // But actually code has not any restrictions for it, so the code below formally is valid.

        /*DmlNodeType*/int newNodeType = ((ShapeBase)newChild).getDmlNode().getDmlNodeType();

        if (((dmlNodeType == DmlNodeType.WORDPROCESSING_CANVAS) || (dmlNodeType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE)) &&
            canInsertInWpCanvasAndWpGroupShape(newNodeType))
            return true;

        if (((dmlNodeType == DmlNodeType.LOCKED_CANVAS) || (dmlNodeType == DmlNodeType.GROUP_SHAPE)) &&
            canInsertInLockedCanvasAndGroupShape(newNodeType))
            return true;

        if (((dmlNodeType == DmlNodeType.CHART) || (dmlNodeType == DmlNodeType.CHART_EX)) &&
            canInsertInChart(newNodeType))
            return true;

        // If current shape has GraphicFrame then it can hold diagram or chart.
        // Generally content of the graphic frame expects object which is serialized out as xml.
        if ((dmlNodeType == DmlNodeType.GRAPHIC_FRAME) &&
            ((newNodeType == DmlNodeType.CHART) || (newNodeType == DmlNodeType.CHART_EX) ||
                (newNodeType == DmlNodeType.DIAGRAM)))
            return true;

        // Graphic frame can be inserted into group shape or user shapes of the chart.
        if (((dmlNodeType == DmlNodeType.GROUP_SHAPE) || (dmlNodeType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE)) &&
              (newNodeType == DmlNodeType.GRAPHIC_FRAME))
            return true;

        return false;
    }

    /// <summary>
    /// Gets the sum of horizontal margins.
    /// </summary>
    /// <remarks>
    /// It is used to calculate how much space is left for text box contents in a container of a certain size.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public float /*ITextBox.*/getHorizontalMargins_ITextBox()
    {
        if (hasVerticalTextFlow())
        {
            // Margins are not used for vertical text flow.
            return 0f;
        }

        // DM I considered using DmlRenderer,
        // but by looking at the code it may return other insets instead of LeftInset depending on rotation.
        // It would not work correctly for text boxes.
        ShapeAdaptor shapeAdaptor = new ShapeAdaptor(this);
        return (float)shapeAdaptor.getMargins();

        // DM However, eventually, we need to get effective text rectangle size for complex shapes from rendering.
        // We will need to decide how to handle margins then.
    }

    /// <summary>
    /// WordprocessingCanvas and WordprocessingGroupShape can only contain WordprocessingGroupShape,
    /// WordprocessingShape, GroupShape and Picture.
    /// </summary>
    private static boolean canInsertInWpCanvasAndWpGroupShape(/*DmlNodeType*/int dmlNodeType)
    {
        return ((dmlNodeType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) ||
               (dmlNodeType == DmlNodeType.WORDPROCESSING_SHAPE) ||
               canInsertInCompositeShapeExcludeGrFrame(dmlNodeType));
    }

    /// <summary>
    /// LockedCanvas and GroupShape can only contain GroupShape, Shape, Picture, ConnectorShape and WordprocessingShape.
    /// </summary>
    private static boolean canInsertInLockedCanvasAndGroupShape(/*DmlNodeType*/int dmlNodeType)
    {
        return ((dmlNodeType == DmlNodeType.SHAPE) ||
                (dmlNodeType == DmlNodeType.CONNECTOR_SHAPE) ||
                (dmlNodeType == DmlNodeType.WORDPROCESSING_SHAPE) ||
                canInsertInCompositeShapeExcludeGrFrame(dmlNodeType));
    }

    private static boolean canInsertInCompositeShapeExcludeGrFrame(/*DmlNodeType*/int dmlNodeType)
    {
        // Content of the graphic frame expects object which is serialized out as xml.
        return ((dmlNodeType == DmlNodeType.GROUP_SHAPE) ||
                (dmlNodeType == DmlNodeType.PICTURE));
    }

    /// <summary>
    /// WORDSNET-11246 Chart can only contain GroupShape, Shape, Picture, ConnectorShape or GraphicFrame.
    /// </summary>
    private static boolean canInsertInChart(/*DmlNodeType*/int dmlNodeType)
    {
        return ((dmlNodeType == DmlNodeType.SHAPE) ||
                (dmlNodeType == DmlNodeType.GRAPHIC_FRAME) ||
                (dmlNodeType == DmlNodeType.CONNECTOR_SHAPE) ||
                canInsertInCompositeShapeExcludeGrFrame(dmlNodeType));
    }

    /// <summary>
    /// Creates and returns a horizontal rule shape.
    /// </summary>
    /// <remarks>
    /// <p>After you create a shape, you need to insert it in the document where you want it.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <returns>A horizontal rule shape with default settings.</returns>
    static Shape createHorizontalRule(Document doc) throws Exception
    {
        // This is how Microsoft Word creates a horizontal rule.
        Shape shape = new Shape(doc, ShapeType.RECTANGLE);
        shape.setWrapType(WrapType.INLINE);
        shape.setFilled(true);
        shape.setFillColor(HorizontalRule.DEFAULT_COLOR.toNativeColor());
        shape.setStroked(false);

        shape.getHorizontalRule().setOn(true);
        shape.getHorizontalRule().setStandard(true);

        shape.setHeight(1.5);
        shape.setWidthSafe(doc.getFirstSection().getPageSetup().getContentWidth()); // This is pretty simplistic, but okay.
        shape.getHorizontalRule().setPercent(100.0);

        return shape;
    }

    /// <summary>
    /// True when the shape is not null and filled or has an image.
    /// Used one a document background shape to check if it is visible or dummy.
    /// </summary>
    static boolean isVisibleAsBackground(Shape shape) throws Exception
    {
        return (shape != null) &&
               (shape.getFilled() || shape.hasImage() || shape.getMarkupLanguage() == ShapeMarkupLanguage.DML);
    }

    /// <summary>
    /// Fixes zero size of shape. Gets it from the default or a corresponding image.
    /// </summary>
    void fixZeroSize(boolean isFromDefault) throws Exception
    {
        if (hasImage())
        {
            if (isFromDefault)
            {
                if (MathUtil.isZero(getWidth()))
                    setWidthSafe(DefaultShapeSize);
                if (MathUtil.isZero(getHeight()))
                    setHeightSafe(DefaultShapeSize);
            }
            else
            {
                if (hasImageBytes() && MathUtil.isZero(getWidth()) && MathUtil.isZero(getHeight()))
                {
                    setWidthSafe(getImageData().getImageSize().getWidthPoints());
                    setHeightSafe(getImageData().getImageSize().getHeightPoints());
                }
            }
        }
    }

    /// <summary>
    /// Removes image bytes from ImageData, and places them into FillImageBytes.
    /// </summary>
    /// <remarks>
    /// Can be executed for VML shape only.
    /// </remarks>
    void replaceVmlImageDataWithFill() throws Exception
    {
        Debug.assertTrue(getMarkupLanguage() == ShapeMarkupLanguage.VML);

        getFillCore().setImageBytes(getImageData().getImageBytes());
        getFillCore().setFillType(FillTypeCore.PICTURE);
        // WORDSNET-17052 Converted image data is not visible due to attribute "Filled" does not set.
        setFilled(true);

        // Reset ImageBytes.
        getImageData().setImageBytes(null);
    }

    private DmlOutline getThemeOutlineProperties()
    {
        Debug.assertTrue(getMarkupLanguage() == ShapeMarkupLanguage.DML);

        DmlLineReference lineReference = ((DmlShapeBase)getDmlNode()).getStyle().getLineReference();
        int styleMatrixIndex = lineReference.getStyleMatrixIndex();
        IThemeProvider themeProvider = getDocumentTheme();

        // It seems that 0 index disables outline and also if there are no Themes inside document return
        // default outline.
        DmlOutline outline = ((styleMatrixIndex == 0) || (themeProvider == null))
            ? new DmlOutline()
            : themeProvider.getLineStyle(styleMatrixIndex - 1);

        outline.applyStyleColor(lineReference.getColor());

        return outline;
    }

    //>>>>>>>> #region  IStrokable implementation

    /// <summary>
    /// Gets or sets the foreground color for the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="DrColor.Black"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeForeColor() { return getStrokeCore().getColorInternal(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeColor(DrColor value) { getStrokeCore().setColorInternal(value); }

    /// <summary>
    /// Gets the base foreground color without modifiers for the stroke.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeBaseForeColor() { return getStrokeCore().getColorInternalUnmodified(); }

    /// <summary>
    /// Gets or sets the background color for the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="DrColor.White"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public DrColor /*IStrokable.*/getStrokeBackColor() { return getStrokeCore().getColor2Internal(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackColor(DrColor value) { getStrokeCore().setColor2Internal(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IStrokable.*/getStrokeForeThemeColor() throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return FillUtil.nativeToThemeColor(getStrokeCore().getColorInternal(), getDocumentTheme());

        DmlFill fill = getStrokeCore().getStrokeFill();
        if (fill == null)
            return ThemeColor.NONE;

        DmlColor curDmlColor = fill.getDmlColorInternal();
        return ((curDmlColor == null) || (curDmlColor.getColorType() != DmlColorType.SCHEME_COLOR))
            ? ThemeColor.NONE
            : ((DmlSchemeColor)curDmlColor).getValue();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeThemeColor(/*ThemeColor*/int value) throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            getStrokeCore().setColorInternal((value != ThemeColor.NONE)
                ? FillUtil.themeToNativeColor(value, getDocumentTheme())
                : getStrokeCore().getColorInternal());
        }
        else
        {
            if (value == ThemeColor.NONE)
            {
                // MS Word converts ThemeColor to RGB in this case.
                DmlColor curDmlColor = getStrokeCore().getStrokeFill().getDmlColorInternal();
                if (curDmlColor.getColorType() == DmlColorType.SCHEME_COLOR)
                {
                    DmlHexRgbColor color = ((DmlSchemeColor)curDmlColor).resolve(getDocumentTheme());
                    getStrokeCore().setColorInternal(color.createUnmodifiedDrColor(getDocumentTheme()));
                }
            }
            else
            {
                getStrokeCore().setColorInternal(DrColor.EMPTY);
                getStrokeCore().getStrokeFill().setDmlColorInternal(new DmlSchemeColor(value));
            }
        }
    }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the stroke background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IStrokable.*/getStrokeBackThemeColor() throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return FillUtil.nativeToThemeColor(getStrokeCore().getColor2Internal(), getDocumentTheme());

        DmlFill fill = getStrokeCore().getStrokeFill();
        if (fill == null)
            return ThemeColor.NONE;

        DmlColor curDmlColor = fill.getDmlColor2Internal();
        return ((curDmlColor == null) || (curDmlColor.getColorType() != DmlColorType.SCHEME_COLOR))
            ? ThemeColor.NONE
            : ((DmlSchemeColor)curDmlColor).getValue();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackThemeColor(/*ThemeColor*/int value) throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            getStrokeCore().setColor2Internal((value != ThemeColor.NONE)
                ? FillUtil.themeToNativeColor(value, getDocumentTheme())
                : getStrokeCore().getColor2Internal());
        else
        {
            if (value == ThemeColor.NONE)
            {
                // MS Word converts ThemeColor to RGB in this case.
                DmlColor curDmlColor = getStrokeCore().getStrokeFill().getDmlColor2Internal();
                if ((curDmlColor != null) && (curDmlColor.getColorType() == DmlColorType.SCHEME_COLOR))
                {
                    DmlHexRgbColor color = ((DmlSchemeColor)curDmlColor).resolve(getDocumentTheme());
                    getStrokeCore().setColor2Internal(color.createUnmodifiedDrColor(getDocumentTheme()));
                }
            }
            else
            {
                getStrokeCore().setColor2Internal(DrColor.EMPTY);
                getStrokeCore().getStrokeFill().setDmlColor2Internal(new DmlSchemeColor(value));
            }
        }
    }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeForeTintAndShade()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if ((getShapePr().get(ShapeAttr.LINE_COLOR_EXT) == null) || (getShapePr().get(ShapeAttr.LINE_COLOR_EXT_MOD) == null))
                return 0.0d;

            int modifier = (/*int*/Integer)getShapePr().get(ShapeAttr.LINE_COLOR_EXT_MOD);
            if ((modifier > -256) && (modifier < 256))
                return modifier >= 0
                    ? 1.0d - ((double)modifier / 255.0)
                    : -1.0d - ((double)modifier / 255.0);

            return 0.0d;
        }
        else
        {
            DmlColor curColor = getStrokeCore().getStrokeFill().getDmlColorInternal();
            if (curColor == null)
                return 0.0d;

            return (curColor.getColorModifier(DmlColorModifierType.TINT) != null)
                ? 1.0d - ((DmlTint)curColor.getColorModifier(DmlColorModifierType.TINT)).getValue()
                : (curColor.getColorModifier(DmlColorModifierType.SHADE) != null)
                    ? -1.0d + ((DmlShade)curColor.getColorModifier(DmlColorModifierType.SHADE)).getValue()
                    : 0.0d;
        }
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeForeTintAndShade(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "ForeTintAndShade");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            DrColor baseColor = (getShapePr().get(ShapeAttr.LINE_COLOR_EXT) != null)
                ? (DrColor)getShapePr().get(ShapeAttr.LINE_COLOR_EXT)
                : getStrokeCore().getColorInternal();
            getShapePr().set(ShapeAttr.LINE_COLOR_EXT, baseColor);

            int tintAndShadeModifier = FillUtil.vmlTintAndShadeModifier(value);
            getShapePr().set(ShapeAttr.LINE_COLOR_EXT_MOD, value >= 0
                ? tintAndShadeModifier
                : -tintAndShadeModifier);

            if (value > 0)
                getStrokeCore().setColorInternal(FillUtil.vmlTint(baseColor, tintAndShadeModifier));
            if (value < 0)
                getStrokeCore().setColorInternal(FillUtil.vmlShade(baseColor, tintAndShadeModifier));
        }
        else
        {
            DmlFill curFill = getStrokeCore().getStrokeFill();
            DmlColor curColor = curFill.getDmlColorInternal();

            IDmlColorModifier dmlColorModifier = curColor.getColorModifier(DmlColorModifierType.SHADE);
            if (dmlColorModifier != null)
                curColor.getColorModifiers().remove(dmlColorModifier);
            dmlColorModifier = curColor.getColorModifier(DmlColorModifierType.TINT);
            if (dmlColorModifier != null)
                curColor.getColorModifiers().remove(dmlColorModifier);

            if (value < 0)
                msArrayList.add(curColor.getColorModifiers(), new DmlShade(1.0 + value));
            if (value > 0)
                msArrayList.add(curColor.getColorModifiers(), new DmlTint(1.0 - value));
        }
    }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the stroke background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeBackTintAndShade()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if ((getShapePr().get(ShapeAttr.LINE_BACK_COLOR_EXT) == null) || (getShapePr().get(ShapeAttr.LINE_BACK_COLOR_EXT_MOD) == null))
                return 0.0d;

            int modifier = (/*int*/Integer)getShapePr().get(ShapeAttr.LINE_BACK_COLOR_EXT_MOD);
            if ((modifier > -256) && (modifier < 256))
                return modifier >= 0
                    ? 1.0d - ((double)modifier / 255.0)
                    : -1.0d - ((double)modifier / 255.0);

            return 0.0d;
        }
        else
        {
            DmlColor curColor = getStrokeCore().getStrokeFill().getDmlColor2Internal();
            if (curColor == null)
                return 0.0d;

            return (curColor.getColorModifier(DmlColorModifierType.TINT) != null)
                ? 1.0d - ((DmlTint)curColor.getColorModifier(DmlColorModifierType.TINT)).getValue()
                : (curColor.getColorModifier(DmlColorModifierType.SHADE) != null)
                    ? -1.0d + ((DmlShade)curColor.getColorModifier(DmlColorModifierType.SHADE)).getValue()
                    : 0.0d;
        }
    }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeBackTintAndShade(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "BackTintAndShade");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            DrColor baseColor = (getShapePr().get(ShapeAttr.LINE_BACK_COLOR_EXT) != null)
                ? (DrColor)getShapePr().get(ShapeAttr.LINE_BACK_COLOR_EXT)
                : getStrokeCore().getColor2Internal();
            getShapePr().set(ShapeAttr.LINE_BACK_COLOR_EXT, baseColor);

            int tintAndShadeModifier = FillUtil.vmlTintAndShadeModifier(value);
            getShapePr().set(ShapeAttr.LINE_BACK_COLOR_EXT_MOD, value >= 0
                ? tintAndShadeModifier
                : -tintAndShadeModifier);

            if (value > 0)
                getStrokeCore().setColor2Internal(FillUtil.vmlTint(baseColor, tintAndShadeModifier));
            if (value < 0)
                getStrokeCore().setColor2Internal(FillUtil.vmlShade(baseColor, tintAndShadeModifier));
        }
        else
        {

            DmlFill curFill = getStrokeCore().getStrokeFill();
            DmlColor curColor = curFill.getDmlColor2Internal();

            if (curColor != null)
            {
                IDmlColorModifier dmlColorModifier = curColor.getColorModifier(DmlColorModifierType.SHADE);
                if (dmlColorModifier != null)
                    curColor.getColorModifiers().remove(dmlColorModifier);
                dmlColorModifier = curColor.getColorModifier(DmlColorModifierType.TINT);
                if (dmlColorModifier != null)
                    curColor.getColorModifiers().remove(dmlColorModifier);

                if (value < 0)
                    msArrayList.add(curColor.getColorModifiers(), new DmlShade(1.0 + value));
                if (value > 0)
                    msArrayList.add(curColor.getColorModifiers(), new DmlTint(1.0 - value));
            }
        }
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the stroke is visible.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public boolean /*IStrokable.*/getStrokeVisible() { return getStrokeCore().getOn(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeVisible(boolean value) { getStrokeCore().setOn(value); }

    /// <summary>
    /// Gets or sets a value between 0.0 (opaque) and 1.0 (clear) representing the degree of transparency
    /// of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is 0.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getStrokeTransparency() { return 1.0 - getStrokeCore().getOpacity(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeTransparency(double value) { getStrokeCore().setOpacity(1.0 - value); }

    /// <summary>
    /// Defines the brush thickness that strokes the path of a shape in points.
    /// </summary>
    /// <remarks>
    /// The default value is 0.75.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public double /*IStrokable.*/getWeight() { return getStrokeCore().getWeight(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setWeight(double value) { getStrokeCore().setWeight(value); }

    /// <summary>
    /// Specifies the dot and dash pattern for a stroke.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="DashStyle.Solid"/>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*DashStyle*/int /*IStrokable.*/getDashStyle() { return getStrokeCore().getDashStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setDashStyle(/*DashStyle*/int value) { getStrokeCore().setDashStyle(value); }

    /// <summary>
    /// Defines the join style of a polyline.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="JoinStyle.Round"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*JoinStyle*/int /*IStrokable.*/getJoinStyle() { return getStrokeCore().getJoinStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setJoinStyle(/*JoinStyle*/int value) { getStrokeCore().setJoinStyle(value); }

    /// <summary>
    /// Defines the cap style for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="EndCap.Flat"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*EndCap*/int /*IStrokable.*/getEndCap() { return getStrokeCore().getEndCap(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndCap(/*EndCap*/int value) { getStrokeCore().setEndCap(value); }

    /// <summary>
    /// Defines the line style of the stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ShapeLineStyle.Single"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ShapeLineStyle*/int /*IStrokable.*/getLineStyle() { return getStrokeCore().getLineStyle(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setLineStyle(/*ShapeLineStyle*/int value) { getStrokeCore().setLineStyle(value); }

    /// <summary>
    /// Defines the arrowhead for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowType.None"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowType*/int /*IStrokable.*/getStartArrowType() { return getStrokeCore().getStartArrowType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowType(/*ArrowType*/int value) { getStrokeCore().setStartArrowType(value); }

    /// <summary>
    /// Defines the arrowhead for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowType.None"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowType*/int /*IStrokable.*/getEndArrowType() { return getStrokeCore().getEndArrowType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowType(/*ArrowType*/int value) { getStrokeCore().setEndArrowType(value); }

    /// <summary>
    /// Defines the arrowhead width for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowWidth.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowWidth*/int /*IStrokable.*/getStartArrowWidth() { return getStrokeCore().getStartArrowWidth(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowWidth(/*ArrowWidth*/int value) { getStrokeCore().setStartArrowWidth(value); }

    /// <summary>
    /// Defines the arrowhead length for the start of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowLength.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowLength*/int /*IStrokable.*/getStartArrowLength() { return getStrokeCore().getStartArrowLength(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStartArrowLength(/*ArrowLength*/int value) { getStrokeCore().setStartArrowLength(value); }

    /// <summary>
    /// Defines the arrowhead width for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowWidth.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowWidth*/int /*IStrokable.*/getEndArrowWidth() { return getStrokeCore().getEndArrowWidth(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowWidth(/*ArrowWidth*/int value) { getStrokeCore().setEndArrowWidth(value); }

    /// <summary>
    /// Defines the arrowhead length for the end of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ArrowLength.Medium"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*ArrowLength*/int /*IStrokable.*/getEndArrowLength() { return getStrokeCore().getEndArrowLength(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setEndArrowLength(/*ArrowLength*/int value) { getStrokeCore().setEndArrowLength(value); }

    /// <summary>
    /// Defines the type of fill used for the background of a stroke.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="LineFillType.Solid"/>.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*LineFillType*/int /*IStrokable.*/getLineFillType() { return getStrokeCore().getLineFillType(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setLineFillType(/*LineFillType*/int value) { getStrokeCore().setLineFillType(value); }

    /// <summary>
    /// Gets the image for a stroke image or pattern fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IStrokable.*/getStrokeImageBytes() throws Exception { return getStrokeCore().getImageBytes(); }

    /// <summary>
    /// Gets a <see cref="IThemeProvider"/> object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IStrokable.*/getStrokeThemeProvider() { return getDocumentTheme(); }

    /// <summary>
    /// Gets or sets a <see cref="DmlFill"/> object of the stroke fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public 
    DmlFill /*IStrokable.*/getStrokeFill() { return getStrokeCore().getStrokeFill(); }
    @ReservedForInternalUse @Deprecated
    public void /*IStrokable.*/setStrokeFill(DmlFill value) { getStrokeCore().setStrokeFill(value); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.Shape"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SHAPE; }

    /// <summary>
    /// Returns <see cref="Aspose.Words.StoryType.Textbox"/>.
    /// </summary>
    
    public /*StoryType*/int getStoryType() { return StoryType.TEXTBOX; }

    /// <summary>
    /// Returns <c>true</c> if an extrusion effect is enabled.
    /// </summary>
    public boolean getExtrusionEnabled() { return getGraphicData().getExtrusionEnabled(); }

    /// <summary>
    /// Returns <c>true</c> if a shadow effect is enabled.
    /// </summary>
    public boolean getShadowEnabled() { return getGraphicData().getShadowEnabled(); }

    /// <summary>
    /// Defines a stroke for a shape.
    /// </summary>
    public Stroke getStroke()
    {
        if (mStroke == null)
            mStroke = new Stroke(this);

        return mStroke;
    }

    /// <summary>
    /// Defines whether the path will be stroked.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Aspose.Words.Drawing.Stroke.On"/> property.</p>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    public boolean getStroked() { return getStroke().getOn(); }
    public void setStroked(boolean value) { getStroke().setOn(value); }

    /// <summary>
    /// Defines the brush thickness that strokes the path of a shape in points.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Aspose.Words.Drawing.Stroke.Weight"/> property.</p>
    /// <p>The default value is 0.75.</p>
    /// </remarks>
    public double getStrokeWeight() { return getStroke().getWeight(); }
    public void setStrokeWeight(double value) { getStroke().setWeight(value); }

    /// <summary>
    /// Defines the color of a stroke.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Aspose.Words.Drawing.Stroke.Color"/> property.</p>
    /// <p>The default value is
    /// <see cref="System.Drawing.Color.Black"/>.
    /// </p>
    /// </remarks>
    public Color getStrokeColor() { return getStroke().getColor(); }
    public void setStrokeColor(Color value) { getStroke().setColor(value); }

    /// <summary>
    /// Determines whether the closed path of the shape will be filled.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Fill.Visible"/> property.</p>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    public boolean getFilled() { return getFillCore().getOn(); }
    public void setFilled(boolean value) { getFillCore().setOn(value); }

    /// <summary>
    /// Defines the brush color that fills the closed path of the shape.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut to the <see cref="Fill.Color"/> property.</p>
    /// <p>The default value is
    /// <see cref="System.Drawing.Color.White"/>.
    /// </p>
    /// </remarks>
    public Color getFillColor() { return ((IFillable)this).getFilledColor(); }
    public void setFillColor(Color value) { ((IFillable)this).setFilledColor(value); }

    /// <summary>
    /// Returns <c>true</c> if the shape has image bytes or links an image.
    /// </summary>
    public boolean hasImage() throws Exception { return (canHaveImage() && getImageData().hasImage()); }

    /// <summary>
    /// Provides access to the image of the shape.
    /// Returns <c>null</c> if the shape cannot have an image.
    /// </summary>
    public ImageData getImageData()
    {
        if (!canHaveImage())
            return null;

        if (mImageDataCache == null)
        {
            // That's not good to call FetchDocument because it throws inside the glossary.
            // Let's do the same "by hand".
            mImageDataCache = new ImageData(this, (ms.as(getDocument(), Document.class)));
        }

        return mImageDataCache;
    }

    /// <summary>
    /// Provides access to the OLE data of a shape. For a shape that is not an OLE object or ActiveX control, returns <c>null</c>.
    /// </summary>
    public OleFormat getOleFormat() { return getGraphicData().getOleFormat(); }

    /// <summary>
    /// Defines attributes that specify how text is displayed in a shape.
    /// </summary>
    public TextBox getTextBox()
    {
        if (mTextBoxCache == null)
            mTextBoxCache = new TextBox(this);
        return mTextBoxCache;
    }

    /// <summary>
    /// Defines the text of the text path (of a WordArt object).
    /// </summary>
    public TextPath getTextPath() { return getGraphicData().getTextPath(); }

    /// <summary>
    /// Gets the first paragraph in the shape.
    /// </summary>
    public Paragraph getFirstParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, 0, false); }

    /// <summary>
    /// Gets the last paragraph in the shape.
    /// </summary>
    public Paragraph getLastParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, -1, false); }

    /// <summary>
    /// Provides access to the properties of the horizontal rule shape.
    /// For a shape that is not a horizontal rule, returns <c>null</c>.
    /// </summary>
    public HorizontalRuleFormat getHorizontalRuleFormat()
    {
        if (isHorizontalRule())
        {
            if (mHorizontalRuleFormatCache == null)
                mHorizontalRuleFormatCache = new HorizontalRuleFormat(this);
            return mHorizontalRuleFormatCache;
        }

        return null;
    }

    /// <summary>
    /// Provides access to the adjustment raw values of a shape.
    /// For a shape that does not contain any adjustment raw values, it returns an empty collection.
    /// </summary>
    public AdjustmentCollection getAdjustments() { return getGraphicData().getAdjustments(); }

    /// <summary>
    /// Looks like JavaScript can be associated with a shape.
    /// </summary>
    String getScriptText() { return (String)fetchShapeAttrInternal(ShapeAttr.SCRIPT_TEXT); }
    void setScriptText(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setShapeAttrInternal(ShapeAttr.SCRIPT_TEXT, value);
    }

    /// <summary>
    /// Looks like " type="text/javascript"" type description of the script.
    /// </summary>
    String getScriptType() { return (String)fetchShapeAttrInternal(ShapeAttr.SCRIPT_TYPE); }
    void setScriptType(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setShapeAttrInternal(ShapeAttr.SCRIPT_TYPE, value);
    }

    /// <summary>
    /// Returns <c>true</c> if the shape has image bytes.
    /// </summary>
    boolean hasImageBytes() throws Exception { return (canHaveImage() && getImageData().hasImageBytes()); }

    /// <summary>
    /// Returns <c>true</c> if shape is Ooxml control.
    /// </summary>
    boolean isOoxmlControl() { return isOleControl() && (getEmbeddedObject() instanceof OoxmlObject); }

    /// <summary>
    /// Gets <see cref="Aspose.Words.Drawing.SignatureLine"/> object if the shape is a signature line. Returns <c>null</c> otherwise.
    /// </summary>
    public SignatureLine getSignatureLine()
    {
        if (getDirectShapeAttrInternal(ShapeAttr.IS_SIGNATURE_LINE) == null)
            return null;

        if (mSignatureLineCache == null)
            mSignatureLineCache = new SignatureLine(this);
        return mSignatureLineCache;
    }

    /// <summary>
    /// Provides access to the properties of the horizontal rule shape.
    /// </summary>
    HorizontalRule getHorizontalRule() { return getGraphicData().getHorizontalRule(); }

    /// <summary>
    /// Indicates the type of the connector if this shape is a connector.
    /// </summary>
    /*new*/ /*ConnectorType*/int getConnectorType() { return super.getConnectorType(); }
    /*new*/void setConnectorType(/*ConnectorType*/int value) { super.setConnectorType(value); }

    /// <summary>
    /// Gets the OLE object type as one enum (as opposed to querying several IsXXX properties).
    /// </summary>
    /*OleObjectType*/int getOleObjectType() throws Exception
    {
        if (isOleControl())
        {
            return OleObjectType.CONTROL;
        }
        else if (isOleObject())
        {
            return (getOleFormat().isLink()) ? OleObjectType.LINKED : OleObjectType.EMBEDDED;
        }
        else
        {
            return OleObjectType.NONE;
        }
    }

    PathInfo[] getSegmentInfo() { return (PathInfo[])fetchShapeAttrInternal(ShapeAttr.GEOMETRY_SEGMENT_INFO); }

    PathPoint[] getVertices() { return (PathPoint[])fetchShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES); }

    Formula[] getFormulas() { return (Formula[])fetchShapeAttrInternal(ShapeAttr.GEOMETRY_FORMULAS); }

    /// <summary>
    /// Gets the adjustment value that is applied to the shape.
    /// </summary>
    int getAdjust(int index)
    {
        int adjKey = VmlUtil.getAdjustKey(index);
        return (/*int*/Integer)fetchShapeAttrInternal(adjKey);
    }

    DrColor getImageRecolor()
    {
        Object imageRecolor = getDirectShapeAttrInternal(ShapeAttr.IMAGE_RECOLOR);
        // 0xffffffff - is the default value for this attribute.
        if ((imageRecolor == null) || ((/*int*/Integer)imageRecolor) == /*unchecked*/((int)(int) 0xffffffffL))
            return DrColor.EMPTY;

        // Color as int number has reversed order of color components.
        // Use temporary color to create correct one.
        DrColor color = new DrColor((int)((/*int*/Integer)imageRecolor | /*unchecked*/((int)(int) 0xff000000L)));
        return new DrColor(color.getB(), color.getG(), color.getR());
    }

    int getLimoX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.GEOMETRY_X_LIMO); }
    void setLimoX(int value) { setShapeAttrInternal(ShapeAttr.GEOMETRY_X_LIMO, value); }

    int getLimoY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.GEOMETRY_Y_LIMO); }
    void setLimoY(int value) { setShapeAttrInternal(ShapeAttr.GEOMETRY_Y_LIMO, value); }

    boolean isFitTextboxToText() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT); }
    void isFitTextboxToText(boolean value) { setShapeAttrInternal(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT, value); }

    //>>>>>>>> #region  Shadow attrs.

    int getShadowOpacity() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_OPACITY); }

    int getShadowOffsetX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_OFFSET_X); }

    int getShadowOffsetY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_OFFSET_Y); }

    int getShadowSecondOffsetX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SECOND_OFFSET_X); }

    int getShadowSecondOffsetY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SECOND_OFFSET_Y); }

    int getShadowScaleXtoX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SCALE_XTO_X); }

    int getShadowScaleYtoY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SCALE_YTO_Y); }

    int getShadowScaleXtoY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SCALE_XTO_Y); }

    int getShadowScaleYtoX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_SCALE_YTO_X); }

    int getShadowOriginX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_ORIGIN_X); }

    int getShadowOriginY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_ORIGIN_Y); }

    int getShadowPerspectiveX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_PERSPECTIVE_X); }

    int getShadowPerspectiveY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_PERSPECTIVE_Y); }

    /*ShadowTypeCore*/int getShadowTypeCore() { return (/*ShadowTypeCore*/Integer)fetchShapeAttrInternal(ShapeAttr.SHADOW_TYPE); }

    //<<<<<<<< #endregion 

    boolean isParallelExtrusion() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.TD_PARALLEL); }

    int getExtrusionSkewAngle() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_SKEW_ANGLE); }

    int getExtrusionForeDepth() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_EXTRUDE_FORWARD); }

    int getExtrusionBackDepth() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_EXTRUDE_BACKWARD); }

    DrColor getExtrusionColor() { return (DrColor)fetchShapeAttrInternal(ShapeAttr.TD_EXTRUSION_COLOR); }

    boolean shouldUpdateRelativeSizeConsideringExtrusion() { return ((getWidthPercent() != null || getHeightPercent() != null) && getExtrusionEnabled()); }

    int getViewpointX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_VIEWPOINT_X); }

    int getViewpointY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_VIEWPOINT_Y); }

    int getViewpointZ() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_VIEWPOINT_Z); }

    int getViewpointOriginX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_ORIGIN_X); }

    int getViewpointOriginY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_ORIGIN_Y); }

    int getTDRotationAngleX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_ROTATION_ANGLE_X); }

    int getTDRotationAngleY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_ROTATION_ANGLE_Y); }

    int getTDLight1PositionX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_KEY_X); }

    int getTDLight1PositionY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_KEY_Y); }

    int getTDLight1PositionZ() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_KEY_Z); }

    int getTDLight1Intensity() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_KEY_INTENSITY); }

    boolean getTDLight1Harsh() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.TD_KEY_HARSH); }

    int getTDLight2PositionX() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_FILL_X); }

    int getTDLight2PositionY() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_FILL_Y); }

    int getTDLight2PositionZ() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_FILL_Z); }

    int getTDLight2Intensity() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_FILL_INTENSITY); }

    boolean getTDLight2Harsh() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.TD_FILL_HARSH); }

    int getTDAmbientIntensity() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_AMBIENT_INTENSITY); }

    int getTDSpecularAmount() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_SPECULAR_AMOUNT); }

    int getTDDiffuseAmount() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_DIFFUSE_AMOUNT); }

    boolean getTDMetallic() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.TD_METALLIC); }

    /*ThreeDRenderMode*/int getRenderMode() { return (/*ThreeDRenderMode*/Integer)fetchShapeAttrInternal(ShapeAttr.TD_RENDER_MODE); }

    /// <summary>
    /// Gets shape adjust handles.
    /// This can return <c>null</c> or an empty array.
    /// </summary>
    Handle[] getHandles() { return (Handle[])fetchShapeAttrInternal(ShapeAttr.GEOMETRY_HANDLES); }

    /// <summary>
    /// This never returns <c>null</c> and never returns an empty array.
    /// Can return 1, 2, 3 or 6 rectangles. The algorithm is specified in MS-ODRAW.pdf in the 2.3.6.29 pInscribe_complex section.
    /// </summary>
    PathRectangle[] getTextBoxRects()
    {
        // Try to get text box rectangle from attributes if specified.
        PathRectangle[] rects = (PathRectangle[])fetchShapeAttrInternal(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS);
        if ((rects != null) && (rects.length > 0))
            return rects;

        // Text box rectangle is not specified so return the bounding rectangle of shape
        PathRectangle pathRectangle = new PathRectangle();
        pathRectangle.Left = new PathValue(-getCoordOriginX(), false);
        pathRectangle.Top = new PathValue(-getCoordOriginY(), false);
        pathRectangle.Right = new PathValue(getCoordSizeWidth() - getCoordOriginX(), false);
        pathRectangle.Bottom = new PathValue(getCoordSizeHeight() - getCoordOriginY(), false);
        return new PathRectangle[] {pathRectangle};
    }
    void setTextBoxRects(PathRectangle[] value) { setShapeAttrInternal(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS, value); }

    boolean isTextureRotated() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.FILL_USE_SHAPE_ANCHOR); }

    /// <summary>
    /// Shows if shape's fill should be ignored while rendering.
    /// </summary>
    /// <remarks>
    /// For now returns <c>true</c> only for connector shapes and lines.
    /// </remarks>
    boolean getIgnoreFill() { return isConnector() || getShapeType() == ShapeType.LINE; }

    /// <summary>
    /// Returns <c>true</c> if shape fill has image.
    /// </summary>
    boolean hasFillImage() throws Exception { return (canHaveFillImage() && ArrayUtil.hasData(getFillCore().getImageBytes())); }

    @ReservedForInternalUse @Deprecated
    public /*TextBoxWrapMode*/int /*ITextBox.*/getTextBoxWrapMode_ITextBox() { return getTextBox().getTextBoxWrapMode(); }

    @ReservedForInternalUse @Deprecated
    public /*LayoutFlow*/int /*ITextBox.*/getTextboxLayoutFlow_ITextBox() { return getLayoutFlow(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*ITextBox.*/hasVerticalTextFlow_ITextBox() { return hasVerticalTextFlow(); }

    private boolean hasVerticalTextFlow()
    {
        boolean hasVerticalTextFlow;
        switch (getLayoutFlow())
        {
            case LayoutFlow.VERTICAL:
            case LayoutFlow.TOP_TO_BOTTOM:
            case LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC:
            case LayoutFlow.BOTTOM_TO_TOP:
                hasVerticalTextFlow = true;
                break;
            default:
                hasVerticalTextFlow = false;
                break;
        }

        return hasVerticalTextFlow;
    }

    @ReservedForInternalUse @Deprecated
    public /*ShapeMarkupLanguage*/byte /*ITextBox.*/getMarkupLanguage_ITextBox() { return getMarkupLanguage(); }

    /// <summary>
    /// Returns <c>true</c> if this <see cref="Shape"/> has a <see cref="Drawing.Charts.Chart"/>.
    /// </summary>
    public boolean hasChart()
    {
        return (getDmlNode() != null) &&
            ((getDmlNode().getDmlNodeType() == DmlNodeType.CHART) || (getDmlNode().getDmlNodeType() == DmlNodeType.CHART_EX));
    }

    /// <summary>
    /// Returns <c>true</c> if this <see cref="Shape"/> has a SmartArt object.
    /// </summary>
    public boolean hasSmartArt()
    {
        return ((getDmlNode() != null) && (getDmlNode().getDmlNodeType() == DmlNodeType.DIAGRAM));
    }

    /// <summary>
    /// Provides access to the chart properties if this shape has a <see cref="Drawing.Charts.Chart"/>.
    /// </summary>
    /// <remarks>This property will return the <see cref="Drawing.Charts.Chart"/> object only if <see cref="HasChart"/>
    /// property is <c>true</c> for this <see cref="Shape"/>, and will throw an exception otherwise.</remarks>
    public Chart getChart()
    {
        if (hasChart())
        {
            if (mChart == null)
                mChart = new Chart((DmlChartSpace)getDmlNode());

            return mChart;
        }

        throw new IllegalStateException("This Shape does not have a Chart.");
    }

    /// <summary>
    /// Gets <see cref="IStroke"/> object for the shape.
    /// </summary>
    private IStroke getStrokeCore()
    {
        if (mStrokeCore == null)
        {
            if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            {
                mStrokeCore = new VmlOutline(this);
            }
            else
            {
                IDmlCommonShapePrSource dmlShape = (IDmlCommonShapePrSource)getDmlNode();
                DmlOutline dmlOutline = dmlShape.getOutline();

                if (dmlShape.getStyle() != null)
                {
                    // Set parent properties here, because previously we have not Document Themes to achieve this.
                    dmlOutline.setParentProperties(getThemeOutlineProperties());
                }

                // Set Shape for outline fill, we need this to get color from
                // Document Themes and to reset fill, please see DmlFill class.
                dmlOutline.getFill().setParent(this);

                mStrokeCore = dmlOutline;
            }
        }

        return mStrokeCore;
    }

    private Stroke mStroke;
    private TextBox mTextBoxCache;
    private ImageData mImageDataCache;
    private Chart mChart;
    private SignatureLine mSignatureLineCache;
    private HorizontalRuleFormat mHorizontalRuleFormatCache;
    private IStroke mStrokeCore;
}

