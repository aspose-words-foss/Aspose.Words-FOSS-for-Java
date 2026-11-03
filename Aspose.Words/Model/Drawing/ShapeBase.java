// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DrColor;
import com.aspose.HashUtil;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msArrayList;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ArgumentUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ImageSizeCore;
import com.aspose.ImageUtil;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msNumber;
import com.aspose.GeometryUtil;
import com.aspose.UriUtil;


/// <summary>
/// Base class for objects in the drawing layer, such as an AutoShape, freeform, OLE object, ActiveX control, or picture.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>This is an abstract class. The two derived classes that you can instantiate
/// are <see cref="Shape"/> and <see cref="GroupShape"/>.</p>
///
/// <p>A shape is a node in the document tree.</p>
///
/// <p>If the shape is a child of a <see cref="Paragraph"/> object, then the shape is said to be "top-level".
/// Top-level shapes are measured and positioned in points.</p>
///
/// <p>A shape can also occur as a child of a <see cref="GroupShape"/> object when several shapes
/// are grouped. Child shapes of a group shape are positioned in the coordinate space and units
/// defined by the <see cref="CoordSize"/> and <see cref="CoordOrigin"/> properties of the parent
/// group shape.</p>
///
/// <p>A shape can be positioned inline with text or floating. The positioning method is controlled
/// using the <see cref="WrapType"/> property.</p>
///
/// <p>When a shape is floating, it is positioned relative to something (e.g the current paragraph,
/// the margin or the page). The relative positioning of the shape is specified using the
/// <see cref="RelativeHorizontalPosition"/> and <see cref="RelativeVerticalPosition"/> properties.</p>
///
/// <p>A floating shape be positioned explicitly using the <see cref="Left"/> and <see cref="Top"/>
/// properties or aligned relative to some other object using the <see cref="HorizontalAlignment"/>
/// and <see cref="VerticalAlignment"/> properties.</p>
///
/// <seealso cref="Shape"/>
/// <seealso cref="GroupShape"/>
/// </remarks>
public abstract class ShapeBase extends
    CompositeNode<Node> implements
    IInline,
    IShape,
    IShapeAttrSource,
    ITrackableNode,
    IFillable,
    IGlow,
    IReflection,
    ISoftEdge,
    IShadow
{
    /// <summary>
    /// Ctor, available to derived classes only.
    /// </summary>
    /// <param name="doc">The owner document. Will be <c>null</c> when loading VML shape
    /// type definitions from the embedded resource.</param>
    /// <param name="markupLanguage">Shape markup language: DrawingML or Vml</param>
    protected ShapeBase(DocumentBase doc, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
    	super(doc);
	
        mMarkupLanguage = markupLanguage;

        if (doc != null)
            setId(doc.getNextShapeId());
    }

    //>>>>>>>> #region  IInline interface implementation.

    @ReservedForInternalUse @Deprecated
    public Paragraph /*IInline.*/getParentParagraph_IInline() { return getParentParagraph(); }

    @ReservedForInternalUse @Deprecated
    public DocumentBase /*IInline.*/getDocument_IInline() { return getDocument(); }

    /// <summary>
    /// Fully expands font properties of the shape character into direct formatting.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getExpandedRunPr_IInline(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    /// <summary>
    /// Provides direct access to the font formatting of the shape anchor character.
    /// These attributes only have effect for top level inline shapes.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getRunPr_IInline() { return mRunPr; }
    @ReservedForInternalUse @Deprecated
    public void /*IInline.*/setRunPr_IInline(RunPr value) { mRunPr = value; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShape interface implementation.

    @ReservedForInternalUse @Deprecated
    public int /*IShape.*/getZOrder_IShape() { return getZOrder(); }
    @ReservedForInternalUse @Deprecated
    public void /*IShape.*/setZOrder_IShape(int value) { setZOrder(value); }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShapeAttrSource interface implementation.

    @ReservedForInternalUse @Deprecated
    public Object /*IShapeAttrSource.*/getDirectShapeAttr(int key)
    {
        return mShapePr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IShapeAttrSource.*/fetchInheritedShapeAttr(int key)
    {
        ShapePr parentPr = ShapeTypeLibrary.getShapeTypePr(getShapeType());

        // For some shape types (such as rectangle, oval etc) I do not have VML shape type definitions.
        // Let's resolve to the default value for such shape types.
        if (parentPr != null)
            return parentPr.fetchAttr(key);
        else
            return ShapePr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IShapeAttrSource.*/fetchShapeAttr(int key)
    {
        Object value = getDirectShapeAttrInternal(key);
        return (value != null) ? value : fetchInheritedShapeAttrInternal(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IShapeAttrSource.*/setShapeAttr(int key, Object value)
    {
        mShapePr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IShapeAttrSource.*/removeShapeAttr(int key)
    {
        mShapePr.remove(key);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShapeAttrSource wrappers.

    Object getDirectShapeAttrInternal(int key)
    {
        return ((IShapeAttrSource)this).getDirectShapeAttr(key);
    }

    void setShapeAttrInternal(int key, Object value)
    {
        ((IShapeAttrSource)this).setShapeAttr(key, value);
    }

    Object fetchShapeAttrInternal(int key)
    {
        return ((IShapeAttrSource)this).fetchShapeAttr(key);
    }

    Object fetchInheritedShapeAttrInternal(int key)
    {
        return ((IShapeAttrSource)this).fetchInheritedShapeAttr(key);
    }

    void removeShapeAttrInternal(int key)
    {
        ((IShapeAttrSource)this).removeShapeAttr(key);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  ITrackableNode interface implementation.

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

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IRunAttrSource interface implementation.

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int fontAttr)
    {
        return mRunPr.getDirectAttr(fontAttr);
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

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IFillable implementation

    /// <summary>
    /// Changes type of the fill to Solid.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/solid()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            getFillCore().setFillType(FillTypeCore.SOLID);
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            if ((curFill.getDmlFillType() != DmlFillType.SOLID_FILL) && (curFill.getDmlFillType() != DmlFillType.NO_FILL))
            {
                // Mimic Word VBA: use either existing color if any, or a new white color otherwise.
                // Also, after color is set, Word VBA resets its transparency to a fully opaque color.
                DmlColor dmlColor = (curFill.getDmlColorInternal() != null)
                    ? curFill.getDmlColorInternal().deepClone().updateAlpha(1.0)
                    : DmlColor.createFromDrColor(DrColor.WHITE);
                curFill = new DmlSolidFill(dmlColor);

                ((IFillable)this).setFill(curFill);
            }
        }
    }

    /// <summary>
    /// Gets a PresetTexture for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PresetTexture*/int /*IFillable.*/getPresetTexture() throws Exception
    {
        int textureHash = HashUtil.getSHA512Hash(getFillCore().getImageBytes()).hashCode();
        return FillUtil.getTextureByHash(textureHash);
    }

    /// <summary>
    /// Gets a PatternType for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PatternType*/int /*IFillable.*/getPatternType() throws Exception
    {
        if (getFillCore().getFillType() != FillTypeCore.PATTERN)
            return PatternType.NONE;

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            /*PatternType*/int pattern = FillUtil.getPatternType(getFillCore().getImageBytes());
            // MS Word returns PatternType.LargeGrid for PatternType.Cross.
            if (pattern == PatternType.CROSS)
                return PatternType.LARGE_GRID;

            return pattern;
        }
        else
        {
            if (getFillCore() instanceof DmlPatternFill)
                return ((DmlPatternFill)getFillCore()).getFillPresetPattern();

            return PatternType.NONE;
        }
    }

    /// <summary>
    /// Changes type of the fill to preset texture.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/presetTextured(/*PresetTexture*/int presetTexture)
    {
        String textureName = FillUtil.getTextureName(presetTexture);
        if (msString.equals(textureName, ""))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "The specified texture is out of range.");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            getFillCore().setFillType(FillTypeCore.TEXTURE);
            getShapePr().set(ShapeAttr.FILLED, true);
            getShapePr().set(ShapeAttr.FILL_IMAGE_BYTES, FillUtil.getTextureBytes(textureName));
            // Mimics MS Word behaviour.
            getShapePr().set(ShapeAttr.FILL_BLIP_NAME, FillUtil.getTextureTitle(presetTexture));
            DrColor fillColor = FillUtil.getFillColor(presetTexture);
            if (fillColor != null)
                getShapePr().set(ShapeAttr.FILL_COLOR, fillColor);
            else
                removeShapeAttrInternal(ShapeAttr.FILL_COLOR);
        }
        else
        {
            DmlBlipFill blipFill = new DmlBlipFill();
            blipFill.setBlipFillMode(new DmlBlipFillTile());
            blipFill.getBlip().setEmbedImage(FillUtil.getTextureBytes(textureName));

            ((IFillable)this).setFill(blipFill);
        }
    }

    /// <summary>
    /// Changes type of the fill to a pattern.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/patterned(/*PatternType*/int patternType)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            getFillCore().setFillType(FillTypeCore.PATTERN);
            getShapePr().set(ShapeAttr.FILLED, true);
            if (getShapePr().get(ShapeAttr.FILL_COLOR) == null)
                getFillCore().setColorInternal(DrColor.BLACK);
            if (getShapePr().get(ShapeAttr.FILL_BACK_COLOR) == null)
                getFillCore().setColor2Internal(DrColor.WHITE);
            String patternName = FillUtil.getPatternName(patternType);
            getShapePr().set(ShapeAttr.FILL_IMAGE_BYTES, FillUtil.getPatternBytes(patternName));
        }
        else
        {
            DmlPatternFill patternFill = new DmlPatternFill();
            patternFill.setFillPresetPattern(patternType);

            DmlFill curFill = (DmlFill)getFillCore();

            DmlColor dmlColor = (curFill.getDmlColorInternal() != null)
                ? curFill.getDmlColorInternal().deepClone().updateAlpha(1.0)
                : DmlColor.createFromDrColor(DrColor.fromNativeColor(getDocumentTheme().getColors().getAccent1()));
            patternFill.setForegroundColor(dmlColor);

            dmlColor = (curFill.getDmlColor2Internal() != null)
                ? curFill.getDmlColor2Internal().deepClone().updateAlpha(1.0)
                : DmlColor.createFromDrColor(DrColor.WHITE);
            patternFill.setBackgroundColor(dmlColor);

            ((IFillable)this).setFill(patternFill);
        }
    }

    /// <summary>
    /// Changes type of the fill to two-color gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (getShapePr().get(ShapeAttr.FILL_COLOR) == null)
                getFillCore().setColorInternal(DrColor.BLACK);
            if (getShapePr().get(ShapeAttr.FILL_BACK_COLOR) == null)
                getFillCore().setColor2Internal(DrColor.WHITE);

            setVmlGradientFill(style, variant);
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            DmlColor color1 = (curFill.getDmlColorInternal() != null)
                ? curFill.getDmlColorInternal().deepClone()
                : DmlColor.createFromDrColor(DrColor.fromNativeColor(getDocumentTheme().getColors().getAccent1()));

            DmlColor color2 = (curFill.getDmlColor2Internal() != null)
                ? curFill.getDmlColor2Internal().deepClone()
                : DmlColor.createFromDrColor(DrColor.WHITE);

            DmlGradientFill gradientFill = new DmlGradientFill(color1, color2, style, variant, getDocumentTheme());
            ((IFillable)this).setFill(gradientFill);

            // Reset fallback shape.
            getRunPr().remove(FontAttr.ALTERNATE_CONTENT);
        }

        getFillCore().setRotateWithShape(true);
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (getShapePr().get(ShapeAttr.FILL_COLOR) == null)
                getFillCore().setColorInternal(DrColor.WHITE);

            DrColor color2 = new DrColor(getFillCore().getColorInternal().toArgb());
            if (MathUtil.areEqual(degree, 0.5))
            {
                getFillCore().setColor2Internal(color2);
            }
            else
            {
                // The max value for TintAndShade is equal to 255. But we should additionally divide it onto two parts,
                // because when degree is less than 0.5, then this will be a Shade, otherwise this will be a Tint.
                final int MAX_TINT_AND_SHADE = (int)(255.0 / 0.5);
                if (MathUtil.isLessOrEqual(degree, 0.5))
                {
                    int shade = (int)Math.ceil(degree * MAX_TINT_AND_SHADE);
                    getFillCore().setColor2Internal(OfficeColor.shade(color2, shade));
                }
                else
                {
                    // The Tint is not perfect for a moment and this should be investigated additionally.
                    int tint = (int)Math.ceil((1.0 - degree) * MAX_TINT_AND_SHADE);
                    getFillCore().setColor2Internal(OfficeColor.tint(color2, tint));
                }
            }

            setVmlGradientFill(style, variant);
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            DmlColor color1 = (curFill.getDmlColorInternal() != null)
                ? curFill.getDmlColorInternal().deepClone()
                : DmlColor.createFromDrColor(DrColor.fromNativeColor(getDocumentTheme().getColors().getAccent1()));

            DmlColor color2 = color1.deepClone();
            // Check either we need Tint or Shade modifier.
            if (!MathUtil.areEqual(degree, 0.5))
            {
                if (MathUtil.isLessOrEqual(degree, 0.5))
                    msArrayList.add(color2.getColorModifiers(), new DmlShade(degree * 2.0));
                else
                    msArrayList.add(color2.getColorModifiers(), new DmlTint((1.0 - degree) * 2.0));
            }

            DmlGradientFill gradientFill = new DmlGradientFill(color1, color2, style, variant, getDocumentTheme());
            ((IFillable)this).setFill(gradientFill);

            // Reset fallback shape.
            getRunPr().remove(FontAttr.ALTERNATE_CONTENT);
        }

        getFillCore().setRotateWithShape(true);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setImage(byte[] imageBytes)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlBlipFill blipFill = new DmlBlipFill();
            ((IFillable)this).setFill(blipFill);
        }

        getFillCore().setImageBytes(imageBytes);
    }

    /// <summary>
    /// Sets specified fill to this object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFill(IFill fill)
    {
        if (fill == null)
        {
            mFill = null;
            return;
        }

        if (((fill instanceof VmlFill) && (getMarkupLanguage() != ShapeMarkupLanguage.VML)) ||
            ((fill instanceof DmlFill) && (getMarkupLanguage() != ShapeMarkupLanguage.DML)))
            throw new IllegalStateException("Invalid fill type for this node.");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            mFillCore = fill;
        }
        else
        {
            ((IDmlCommonShapePrSource)getDmlNode()).setFill((DmlFill)fill);
        }

        fill.setParent(this);
    }

    /// <summary>
    /// Returns a double value representing transparency of a specified color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getTransparency(DmlColor color)
    {
        if (color.getAlpha() == null)
            return 0.0;

        return (1.0 - color.getAlpha().getValue());
    }

    /// <summary>
    /// Sets a specified value to a transparency of specified color .
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTransparency(DmlColor color, double value)
    {
        color.updateAlpha(1.0 - value);
    }

    //>>>>>>>> #region  The public properties of old Fill object for compatibility.

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFilledColor() { return getFillCore().getColorInternal().toNativeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFilledColor(Color value)
    {
        getFillCore().setColorInternal(DrColor.fromNativeColor(value));
        // WORDSNET-5726 Alpha equal to 255 means color is fully opaque.
        // Hence, if it less then 255 we should convert it to the Opacity.
        if ((value.getAlpha() & 0xFF) < 255)
            getFillCore().setOpacity((value.getAlpha() & 0xFF) / 255d);
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getOldOn() { return getFillCore().getOn(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOn(boolean value) { getFillCore().setOn(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (clear) and 1.0 (opaque) representing the degree
    /// of opacity of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getOldOpacity() { return getFillCore().getOpacity(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOpacity(double value)
    {
        // WORDSNET-12980 If color was not initialized yet, then let's initialize it
        // with the new empty color, otherwise opacity will not be set
        // (see DmlFill.Opacity for details).
        // This case is related to DML shapes with "DmlNodeType.Picture" type. Such shapes already have picture
        // fill and additional fill can be specified (there are two properties BlipFill and Fill). Word automation
        // sets opacity for second fill, so it is necessary to initialize color of additional fill. Other DML
        // fillable nodes with blip fill have to change opacity for the blip, but not for the color. In common case
        // VML picture can also contain image data and the fill definition in the markup.
        if (msColor.isEmpty(((IFillable)this).getFilledColor()))
            ((IFillable)this).setFilledColor(msColor.Empty);

        getFillCore().setOpacity(value);
    }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IFillable.*/getFillableImageBytes() throws Exception { return getFillCore().getImageBytes(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    /// <dev>
    /// Word VBA also changes transparency to fully opaque color only for gradient fills.
    /// However, we do it for some other fill types for a moment as well (Solid, for example).
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableForeColor() { return getFillCore().getColorInternal().toColorFixAlpha().toNativeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeColor(Color value) { getFillCore().setColorInternal(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets a Color object that represents the base foreground color without modifiers for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBaseForeColor() { return getFillCore().getColorInternalUnmodified().toColorFixAlpha().toNativeColor(); }

    /// <summary>
    /// Gets or sets a Color object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBackColor() { return getFillCore().getColor2Internal().toColorFixAlpha().toNativeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackColor(Color value)
    {
        // Word VBA allows to set up color for NoFill and changes the type of the fill to Patterned implicitly.
        if (getFillCore() instanceof DmlNoFill)
            ((IFillable)this).setFill(new DmlPatternFill());

        getFillCore().setColor2Internal(DrColor.fromNativeColor(value));
    }


    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableForeThemeColor() throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return (canBeFilled(getFillCore().getColorInternal()))
                ? FillUtil.nativeToThemeColor(getFillCore().getColorInternal(), getDocumentTheme())
                : ThemeColor.NONE;

        DmlFill curFill = (DmlFill)getFillCore();
        DmlColor curDmlColor = curFill.getDmlColorInternal();
        return ((curDmlColor == null) || (curDmlColor.getColorType() != DmlColorType.SCHEME_COLOR))
            ? ThemeColor.NONE
            : ((DmlSchemeColor)curDmlColor).getValue();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeThemeColor(/*ThemeColor*/int value) throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            // Implemented for uniformity with ShapeMarkupLanguage.Dml.
            if ((getFillCore().getFillType() == FillTypeCore.PICTURE) || (getFillCore().getFillType() == FillTypeCore.TEXTURE))
                throw new IllegalStateException("Cannot set ForeThemeColor to this Fill.");
            if (value == ThemeColor.NONE)
                getShapePr().set(ShapeAttr.FILLED, false);
            else if ((getFillCore().getColorInternal() == null) || !getFillCore().getOn())
            {
                getFillCore().setFillType(FillTypeCore.SOLID);
                getShapePr().set(ShapeAttr.FILLED, true);
            }

            getFillCore().setColorInternal(FillUtil.themeToNativeColor(value, getDocumentTheme()));
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            if (curFill.getDmlFillType() == DmlFillType.BLIP_FILL)
                throw new IllegalStateException("Cannot set ForeThemeColor to this Fill.");

            if (value == ThemeColor.NONE)
            {
                DrColor curDrColor = curFill.getColorInternal();
                curFill.setColorInternal(DrColor.EMPTY);
                curFill.setColorInternal(curDrColor);
            }
            else
            {
                curFill.setDmlColorInternal(new DmlSchemeColor(value));
            }
        }
    }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableBackThemeColor() throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return (canBeFilled(getFillCore().getColor2Internal()))
                ? FillUtil.nativeToThemeColor(getFillCore().getColor2Internal(), getDocumentTheme())
                : ThemeColor.NONE;

        DmlFill curFill = (DmlFill)getFillCore();
        DmlColor curDmlColor = curFill.getDmlColor2Internal();
        return ((curDmlColor == null) || (curDmlColor.getColorType() != DmlColorType.SCHEME_COLOR))
            ? ThemeColor.NONE
            : ((DmlSchemeColor)curDmlColor).getValue();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackThemeColor(/*ThemeColor*/int value) throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            // Implemented for uniformity with ShapeMarkupLanguage.Dml.
            if ((getFillCore().getFillType() == FillTypeCore.PICTURE) || (getFillCore().getFillType() == FillTypeCore.TEXTURE))
                throw new IllegalStateException("Cannot set BackThemeColor to this Fill.");
            if (value == ThemeColor.NONE)
                getShapePr().set(ShapeAttr.FILLED, false);
            else if ((getFillCore().getColor2Internal() == null) || !getFillCore().getOn())
            {
                // For NoFill the pattern fill is set by default with the same ForeThemeColor and BackThemeColor.
                getFillCore().setFillType(FillTypeCore.PATTERN);
                getShapePr().set(ShapeAttr.FILLED, true);
                getFillCore().setColorInternal(FillUtil.themeToNativeColor(value, getDocumentTheme()));
            }

            getFillCore().setColor2Internal(FillUtil.themeToNativeColor(value, getDocumentTheme()));
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();

            if (value == ThemeColor.NONE)
            {
                DrColor curDrColor = curFill.getColor2Internal();
                curFill.setColor2Internal(DrColor.EMPTY);
                curFill.setColor2Internal(curDrColor);
                return;
            }

            DmlSchemeColor curThemeColor = new DmlSchemeColor(value);
            switch (curFill.getDmlFillType())
            {
                case DmlFillType.GRADIENT_FILL:
                case DmlFillType.PATTERN_FILL:
                    curFill.setDmlColor2Internal(curThemeColor);
                    break;
                case DmlFillType.GROUP_FILL:
                case DmlFillType.NO_FILL:
                case DmlFillType.SOLID_FILL:
                case DmlFillType.STYLE_FILL:
                    // For NoFill the pattern fill is set by default with the same ForeThemeColor and BackThemeColor.
                    // VBA doesn't apply BackThemeColor to Solid fill, it just does nothing. I think this behavior is wrong,
                    // so I made the behavior similar to assigning BackThemeColor for NoFill.
                    DmlPatternFill patternFill = new DmlPatternFill();
                    patternFill.setForegroundColor(curThemeColor);
                    patternFill.setBackgroundColor(curThemeColor);

                    ((IFillable)this).setFill(patternFill);
                    break;
                case DmlFillType.BLIP_FILL:
                default:
                    throw new IllegalStateException("Cannot set BackThemeColor to this Fill.");
            }
        }
    }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableForeTintAndShade()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (!canBeFilled(getFillCore().getColorInternal()) ||
                (getShapePr().get(ShapeAttr.FILL_COLOR_EXT) == null) || (getShapePr().get(ShapeAttr.FILL_COLOR_EXT_MOD) == null))
                return 0.0d;

            int modifier = (/*int*/Integer)getShapePr().get(ShapeAttr.FILL_COLOR_EXT_MOD);
            if ((modifier > -256) && (modifier < 256))
                return modifier >= 0
                    ? 1.0d - ((double)modifier / 255.0)
                    : -1.0d - ((double)modifier / 255.0);

            return 0.0d;
        }
        else
        {
            DmlColor curColor = ((DmlFill)getFillCore()).getDmlColorInternal();
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
    public void /*IFillable.*/setFillableForeTintAndShade(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "ForeTintAndShade");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (!canBeFilled(getFillCore().getColorInternal()))
                throw new IllegalStateException("Cannot apply ForeTintAndShade to this Fill.");

            DrColor baseColor = (getShapePr().get(ShapeAttr.FILL_COLOR_EXT) != null)
                ? (DrColor)getShapePr().get(ShapeAttr.FILL_COLOR_EXT)
                : getFillCore().getColorInternal();
            getShapePr().set(ShapeAttr.FILL_COLOR_EXT, baseColor);

            int tintAndShadeModifier = FillUtil.vmlTintAndShadeModifier(value);
            getShapePr().set(ShapeAttr.FILL_COLOR_EXT_MOD, value >= 0
                ? tintAndShadeModifier
                : -tintAndShadeModifier);

            if (value > 0)
                getFillCore().setColorInternal(FillUtil.vmlTint(baseColor, tintAndShadeModifier));
            if (value < 0)
                getFillCore().setColorInternal(FillUtil.vmlShade(baseColor, tintAndShadeModifier));
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            DmlColor curColor = curFill.getDmlColorInternal();
            if (curColor == null)
                throw new IllegalStateException("Cannot apply ForeTintAndShade to this Fill.");

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
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableBackTintAndShade()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (!canBeFilled(getFillCore().getColor2Internal()) ||
                (getShapePr().get(ShapeAttr.FILL_BACK_COLOR_EXT) == null) || (getShapePr().get(ShapeAttr.FILL_BACK_COLOR_EXT_MOD) == null))
                return 0.0d;

            int modifier = (/*int*/Integer)getShapePr().get(ShapeAttr.FILL_BACK_COLOR_EXT_MOD);
            if ((modifier > -256) && (modifier < 256))
                return modifier >= 0
                    ? 1.0d - ((double)modifier / 255.0)
                    : -1.0d - ((double)modifier / 255.0);

            return 0.0d;
        }
        else
        {
            DmlColor curColor = ((DmlFill)getFillCore()).getDmlColor2Internal();
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
    public void /*IFillable.*/setFillableBackTintAndShade(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "BackTintAndShade");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (!canBeFilled(getFillCore().getColor2Internal()))
                throw new IllegalStateException("Cannot apply BackTintAndShade to this Fill.");

            DrColor baseColor = (getShapePr().get(ShapeAttr.FILL_BACK_COLOR_EXT) != null)
                ? (DrColor)getShapePr().get(ShapeAttr.FILL_BACK_COLOR_EXT)
                : getFillCore().getColor2Internal();
            getShapePr().set(ShapeAttr.FILL_BACK_COLOR_EXT, baseColor);

            int tintAndShadeModifier = FillUtil.vmlTintAndShadeModifier(value);
            getShapePr().set(ShapeAttr.FILL_BACK_COLOR_EXT_MOD, (value > 0)
                ? tintAndShadeModifier
                : -tintAndShadeModifier);

            if (value > 0)
                getFillCore().setColor2Internal(FillUtil.vmlTint(baseColor, tintAndShadeModifier));
            if (value < 0)
                getFillCore().setColor2Internal(FillUtil.vmlShade(baseColor, tintAndShadeModifier));
        }
        else
        {
            DmlFill curFill = (DmlFill)getFillCore();
            DmlColor curColor = curFill.getDmlColor2Internal();
            if (curColor == null)
                throw new IllegalStateException("Cannot apply BackTintAndShade to this Fill.");

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
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getFillableVisible() { return getFillCore().getOn(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableVisible(boolean value) { getFillCore().setOn(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableTransparency() { return (1.0 - getFillCore().getOpacity()); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableTransparency(double value)
    {
        // Word VBA allows to set up opacity (transparency) for NoFill and
        // changes the type of fill to Solid implicitly.
        if (getFillCore() instanceof DmlNoFill)
            ((IFillable)this).setFill(new DmlPatternFill());

        getFillCore().setOpacity((1.0 - value));
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether the fill rotates with the specified object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getRotateWithObject() { return getFillCore().getRotateWithShape(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setRotateWithObject(boolean value) { getFillCore().setRotateWithShape(value); }

    /// <summary>
    /// Gets fill type.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*FillTypeCore*/int /*IFillable.*/getFillType() { return getFillCore().getFillType(); }

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*TextureAlignment*/int /*IFillable.*/getTextureAlignment()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return TextureAlignment.NONE;

        DmlBlipFill dmlBlipFill = ms.as(getFillCore(), DmlBlipFill.class);
        if (dmlBlipFill == null)
            return TextureAlignment.NONE;

        DmlBlipFillTile dmlBlipFillTile = ms.as(dmlBlipFill.getBlipFillMode(), DmlBlipFillTile.class);

        return (dmlBlipFillTile == null)
            ? TextureAlignment.NONE
            : FillUtil.dmlRectangleToTextureAlignment(dmlBlipFillTile.getAlignment());
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTextureAlignment(/*TextureAlignment*/int value)
    {
        if (value == TextureAlignment.NONE)
            throw new IllegalStateException("TextureNone cannot be applied directly.");

        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            throw new IllegalStateException("TextureAlignment cannot be applied to VML shapes.");

        DmlBlipFill dmlBlipFill = ms.as(getFillCore(), DmlBlipFill.class);
        if (dmlBlipFill == null)
            throw new IllegalStateException("TextureAlignment can be applied to texture fill only.");

        DmlBlipFillTile dmlBlipFillTile = ms.as(dmlBlipFill.getBlipFillMode(), DmlBlipFillTile.class);
        if (dmlBlipFillTile == null)
            throw new IllegalStateException("TextureAlignment can be applied to tile texture fill only.");

        dmlBlipFillTile.setAlignment(FillUtil.textureToDmlRectangleAlignment(value));
    }

    /// <summary>
    /// Gets IThemeProvider object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IFillable.*/getFillableThemeProvider() { return getDocumentTheme(); }

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getGradientAngle()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            throw new IllegalStateException(INVALID_ACTION);

        return getFillCore().getAngle();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setGradientAngle(double value)
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            throw new IllegalStateException(INVALID_ACTION);

        getFillCore().setAngle(value);
    }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientVariant*/int /*IFillable.*/getGradientVariant() { return getFillCore().getGradientVariant(); }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientStyle*/int /*IFillable.*/getGradientStyle() { return getFillCore().getGradientStyle(); }

    /// <summary>
    /// Gets a collection of <see cref="GradientStop"/> objects for the fill.
    /// </summary>
    /// <remarks>This is facade for <see cref="DmlGradientFill.GradientStops"/> collection.</remarks>
    @ReservedForInternalUse @Deprecated
    public GradientStopCollection /*IFillable.*/getGradientStops()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            DmlGradientFill fill = ms.as(getFillCore(), DmlGradientFill.class);
            if (fill != null)
                return fill.getGradientStopCollection();
        }

        throw new IllegalStateException(INVALID_ACTION);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IGlow implementation

    /// <summary>
    /// Removes glow from the parent object;
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IGlow.*/removeGlow()
    {
        if ((getDmlShape() == null) || (getDmlShape().getEffects() == null))
            return;

        getDmlShape().getEffects().removeEffect(DmlShapeEffectType.GLOW);
    }

    /// <summary>
    /// Gets or sets a Color object that represents the color for a glow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IGlow.*/getColor()
    {
        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(false);
        if (glow == null)
            return Color.BLACK;

        return glow.getColor().createDrColor(getDocumentTheme(), null).toNativeColor();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IGlow.*/setColor(Color value)
    {
        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(true);
        glow.setColor(DmlHexRgbColor.fromDrColor(DrColor.fromNativeColor(value)));
    }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency for the glow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IGlow.*/getTransparency()
    {
        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(false);
        if ((glow == null) || (glow.getColor() == null))
            return 0.0;

        DmlAlpha alpha = glow.getColor().getAlpha();
        if (alpha == null)
            return 0.0;

        return 1.0 - alpha.getValue();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IGlow.*/setTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Glow.Transparency");

        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(true);
        if (glow.getColor() == null)
            glow.setColor(new DmlHexRgbColor());

        glow.getColor().updateAlpha(1.0 - value);
    }

    /// <summary>
    /// Gets or sets the length of the radius for a glow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IGlow.*/getRadius()
    {
        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(false);
        if (glow == null)
            return 0.0;

        return ConvertUtilCore.emuToPoint(glow.getRadius());
    }
    @ReservedForInternalUse @Deprecated
    public void /*IGlow.*/setRadius(double value)
    {
        DmlShapeGlowEffect glow = getDmlShapeGlowEffect(true);
        glow.setRadius(ConvertUtilCore.pointToEmu(value));
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IReflection implementation

    /// <summary>
    /// Removes reflection from the parent object;
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IReflection.*/removeReflection()
    {
        if ((getDmlShape() == null) || (getDmlShape().getEffects() == null))
            return;

        getDmlShape().getEffects().removeEffect(DmlShapeEffectType.REFLECTION);
    }

    /// <summary>
    /// Gets or sets a double value that specifies the degree of blur effect applied to the reflection effect in points.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IReflection.*/getBlur()
    {
        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(false);
        if (reflection == null)
            return 0.0;

        return ConvertUtilCore.emuToPoint(reflection.getBlurRadius());
    }
    @ReservedForInternalUse @Deprecated
    public void /*IReflection.*/setBlur(double value)
    {
        ArgumentUtil.checkNonNegative(value, "Reflection.Blur");

        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(true);
        reflection.setBlurRadius(ConvertUtilCore.pointToEmu(value));
    }

    /// <summary>
    /// Gets or sets the amount of separation of the reflected image from the object in points.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IReflection.*/getDistance()
    {
        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(false);
        if (reflection == null)
            return 0.0;

        return ConvertUtilCore.emuToPoint(reflection.getDistance());
    }
    @ReservedForInternalUse @Deprecated
    public void /*IReflection.*/setDistance(double value)
    {
        ArgumentUtil.checkNonNegative(value, "Reflection.Distance");

        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(true);
        reflection.setDistance(ConvertUtilCore.pointToEmu(value));
    }

    /// <summary>
    /// Gets or sets a double value between 0.0 and 1.0 representing the size of the reflection
    /// as a percentage of the reflected object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IReflection.*/getReflectionSize()
    {
        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(false);
        if (reflection == null)
            return 0.0;

        return reflection.getEndPosition();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IReflection.*/setReflectionSize(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Reflection.Size");

        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(true);
        reflection.setEndPosition(value);
    }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency for the reflection effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IReflection.*/getReflectionTransparency()
    {
        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(false);
        if (reflection == null)
            return 0.0;

        return 1.0 - reflection.getStartAlpha();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IReflection.*/setReflectionTransparency(double value)
    {
        ArgumentUtil.checkRangeInclusive(value, 0.0, 1.0, "Reflection.Transparency");

        DmlShapeReflectionEffect reflection = getDmlShapeReflectionEffect(true);
        reflection.setStartAlpha(1.0 - value);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  ISoftEdge implementation

    /// <summary>
    /// Removes soft edge from the parent object;
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*ISoftEdge.*/removeSoftEdge()
    {
        if ((getDmlShape() == null) || (getDmlShape().getEffects() == null))
            return;

        getDmlShape().getEffects().removeEffect(DmlShapeEffectType.SOFT_EDGES);
    }

    /// <summary>
    /// Gets or sets the length of the radius for a soft edge effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*ISoftEdge.*/getEdgeRadius()
    {
        DmlShapeSoftEdgeEffect softEdge = getDmlShapeSoftEdgeEffect(false);
        if (softEdge == null)
            return 0.0;

        return ConvertUtilCore.emuToPoint(softEdge.getRadius());
    }
    @ReservedForInternalUse @Deprecated
    public void /*ISoftEdge.*/setEdgeRadius(double value)
    {
        DmlShapeSoftEdgeEffect softEdge = getDmlShapeSoftEdgeEffect(true);
        softEdge.setRadius(ConvertUtilCore.pointToEmu(value));
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShadow implementation

    /// <summary>
    /// Removes shadow from the parent object;
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IShadow.*/removeShadow()
    {
        getGraphicData().removeShadow();
    }

    /// <summary>
    /// Gets or sets a <see cref="ShadowType"/> value for the shadow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ShadowType*/int /*IShadow.*/getShadowType()
    {
        return getGraphicData().getShadowType();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IShadow.*/setShadowType(/*ShadowType*/int value)
    {
        getGraphicData().removeShadow();
        getGraphicData().setShadowType(value);
    }

    /// <summary>
    /// Gets a boolean value indicating whether shadow effect is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IShadow.*/getVisible() { return getGraphicData().getShadowEnabled(); }

    /// <summary>
    /// Gets or sets <see cref="System.Drawing.Color"/> object that represents the color for a shadow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IShadow.*/getShadowColors() { return getGraphicData().getShadowColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IShadow.*/setShadowColors(Color value) { getGraphicData().setShadowColor(value); }

    /// <summary>
    /// Gets or sets the degree of transparency for a shadow effect.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IShadow.*/getShadowTransparency() { return getGraphicData().getShadowTransparency(); }
    @ReservedForInternalUse @Deprecated
    public void /*IShadow.*/setShadowTransparency(double value) { getGraphicData().setShadowTransparency(value); }
    //<<<<<<<< #endregion 

    /// <summary>
    /// Converts a value from the local coordinate space into the coordinate space of the parent shape.
    /// </summary>
    //JAVA: method is internalized
	/*PointF*/long localToParentInternal(/*PointF*/long value)
    {
        // Offset the value by the inverse of coordorigin (subtract).
        // The value is still in local coordinates.
        float x = msPointF.getX(value) - msPoint.getX(getCoordOriginInternal());
        float y = msPointF.getY(value) - msPoint.getY(getCoordOriginInternal());

        double widthFactor = !MathUtil.isZero(msSize.getWidth(getCoordSizeInternal())) ? getWidth() / msSize.getWidth(getCoordSizeInternal()) : 1.0;
        double heightFactor = !MathUtil.isZero(msSize.getHeight(getCoordSizeInternal())) ? getHeight() / msSize.getHeight(getCoordSizeInternal()) : 1.0;

        // Scale the value from local coordinates into parent coordinates.
        x = (float)(x * widthFactor);
        y = (float)(y * heightFactor);

        // Offset the value by the position of the parent shape in parent coordinates.
        x += (float)getLeft();
        y += (float)getTop();

        return msPointF.ctor(x, y);
    }

	//JAVA-added public wrapper for internalized method
	public java.awt.geom.Point2D.Float localToParent(java.awt.geom.Point2D.Float value)
	{
		return msPointF.toJava(localToParentInternal(msPointF.fromJava(value)));
	}

    /// <summary>
    /// Converts a rectangle that is in the coordinate space of the child shape into a value in points
    /// of the coordinate space of the topmost parent shape.
    /// </summary>
    RectangleF convertLocalToTopmostAnchor(RectangleF rect)
    {
        // WORDSNET-27303 If a child shape is rotated 90 degrees, parent Y scale is used to calculate X coordinated
        // and vice versa. Mimic Word's behavior when rotation is not a multiple of 90 degrees.
        // Just rotate the rect before scaling.
        boolean swapXY = ((int)msMath.round(getRotation() / 90.0, 0, MidpointRounding.AWAY_FROM_ZERO) % 2 == 1);
        if (swapXY)
            rect = rotate90Degrees(rect);

        CompositeNode parent = getParentNode();
        /*PointF*/long topLeft = rect.getLocation();
        /*PointF*/long bottomRight = msPointF.ctor(rect.getRight(), rect.getBottom());

        boolean parentWidthZeroSize = false;
        boolean parentHeightZeroSize = false;
        while (parent instanceof ShapeBase)
        {
            ShapeBase parentShape = (ShapeBase)parent;

            topLeft = parentShape.localToParentInternal(topLeft);
            bottomRight = parentShape.localToParentInternal(bottomRight);

            parentWidthZeroSize = (parentShape.getWidth() == 0) ||
                ((msSize.getWidth(parentShape.getCoordSizeInternal()) == 0) && (msPointF.getX(bottomRight) - msPointF.getX(topLeft) > parentShape.getWidth()));
            parentHeightZeroSize = (parentShape.getHeight() == 0) ||
                ((msSize.getHeight(parentShape.getCoordSizeInternal()) == 0) && (msPointF.getY(bottomRight) - msPointF.getY(topLeft) > parentShape.getHeight()));

            parent = parent.getParentNode();
        }

        float width = msPointF.getX(bottomRight) - msPointF.getX(topLeft);
        float height = msPointF.getY(bottomRight) - msPointF.getY(topLeft);

        // WORDSNET-28254, WORDSNET-28627 In case of a parent group with zero dimensions we shall convert
        // Width and Height from the local coordinate system, EMU in case of DML and Twip in case of VML.
        if (parentWidthZeroSize)
            width = getMarkupLanguage() == ShapeMarkupLanguage.DML
                ? (float)ConvertUtilCore.emuToPoint(msPointF.getX(bottomRight) - msPointF.getX(topLeft))
                : (float)ConvertUtilCore.twipToPoint(msPointF.getX(bottomRight) - msPointF.getX(topLeft));

        if (parentHeightZeroSize)
            height = getMarkupLanguage() == ShapeMarkupLanguage.DML
                ? (float)ConvertUtilCore.emuToPoint(msPointF.getY(bottomRight) - msPointF.getY(topLeft))
                : (float)ConvertUtilCore.twipToPoint(msPointF.getY(bottomRight) - msPointF.getY(topLeft));

        RectangleF result = new RectangleF(msPointF.getX(topLeft), msPointF.getY(topLeft), width, height);
        if (swapXY)
        {
            // Unrotate the result rect back.
            result = rotate90Degrees(result);
        }

        return result;
    }

    /// <summary>
    /// Rotates the rectangle 90 degrees around its center.
    /// </summary>
    static RectangleF rotate90Degrees(RectangleF rect)
    {
        double centerX = rect.getLeft() + rect.getWidth() / 2d;
        double centerY = rect.getTop() + rect.getHeight() / 2d;
        float left = (float)(centerX - rect.getHeight() / 2d);
        float top = (float)(centerY - rect.getWidth() / 2d);
        return new RectangleF(left, top, rect.getHeight(), rect.getWidth());
    }

    /// <summary>
    /// Returns <c>true</c> if this shape or one of it's children down the tree has the textbox.
    /// </summary>
    boolean hasTextBoxes()
    {
        if (getNodeType() == NodeType.SHAPE)
            return hasChildNodes();

        for (Shape childShape : (Iterable<Shape>) getChildNodes(NodeType.SHAPE, true))
        {
            if (childShape.hasChildNodes())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if one of shape's children down the tree has an image.
    /// </summary>
    boolean hasImages() throws Exception
    {
        for (Shape childShape : (Iterable<Shape>) getChildNodes(NodeType.SHAPE, true))
            if (childShape.hasImage())
                return true;

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape or one of it's children down the tree has fields.
    /// </summary>
    boolean hasFields()
    {
        return getChildNodes(NodeType.FIELD_START, true).getCount() > 0;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape or one of it's children down the tree has HRefs.
    /// </summary>
    boolean hasHRefs()
    {
        if (getNodeType() == NodeType.SHAPE)
            return !msString.isNullOrEmpty(getHRef());

        for (Shape childShape : (Iterable<Shape>) getChildNodes(NodeType.SHAPE, true))
        {
            if (!msString.isNullOrEmpty(getHRef()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape or one of it's children down the tree has bookmarks.
    /// </summary>
    boolean hasBookmarks()
    {
        return getChildNodes(NodeType.BOOKMARK_START, true).getCount() > 0;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape placed inside header or footer.
    /// </summary>
    boolean isHeaderFooter()
    {
        return getStoryAncestor(NodeType.HEADER_FOOTER) != null;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape or one of it's children down the tree is of the specified DML node type.
    /// </summary>
    boolean hasDmlNodes(/*DmlNodeType*/int dmlNodeType)
    {
        if (getDmlNode() != null && getDmlNode().getDmlNodeType() == dmlNodeType)
            return true;

        Node[] childShapes = getChildNodes(NodeType.SHAPE, true).toArray();
        for (Node childNode : childShapes)
        {
            Shape childShape = (Shape)childNode; // casting for java
            if (childShape.getDmlNode() != null && childShape.getDmlNode().getDmlNodeType() == dmlNodeType)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if this shape has textbox with text or image.
    /// </summary>
    /// <remarks> Used on the rendering side for shadow creation.</remarks>
    boolean hasNonEmptyTextbox() throws Exception
    {
        if (!hasTextbox())
            return false;

        return !StringUtil.containsOnlyWhitespaces(getText()) || hasImages();
    }

    /// <summary>
    /// Sets the shape type. Users are not to modify shape type directly.
    /// </summary>
    void setShapeType(/*ShapeType*/int shapeType)
    {
        // Some shape types can not be created with VML markup language.
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            Debug.assertTrue(DmlEnum.hasVmlPresetGeometry(shapeType) || !isAllowedShapeTypes(shapeType));

        setShapeAttrInternal(ShapeAttr.SHAPE_TYPE, shapeType);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        ShapeBase lhs = (ShapeBase)super.deepClone(isCloneChildren, cloningListener);
        lhs.mShapePr = AttrCollectionExtension.deepClone(mShapePr);
        lhs.mRunPr = AttrCollectionExtension.deepClone(mRunPr);
        lhs.mLocksCache = null;
        lhs.mFontCache = null;
        lhs.mGraphicData = null;
        if (getDmlNode() != null)
        {
            lhs.setDmlNode(getDmlNode().deepClone(isCloneChildren, cloningListener));
            lhs.getDmlNode().setDrawingML(lhs);
        }
        return lhs;
    }

    /// <summary>
    /// Fully expands font properties of the shape character into direct formatting.
    /// </summary>
    RunPr getExpandedRunPr(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    void setSizeSafe(double width, double height) throws Exception
    {
        setSizeSafe(width, height, null, 0.0);
    }

    /// <summary>
    /// Sets width and height of the shape, if width or height exceeded the maximum shape size,
    /// Scales shape to fit the container size (cell, textbox or page).
    /// Use this method only if image size rescaling is required. Normally upon reading Office file formats,
    /// <see cref="SetWidthSafe"/> and <see cref="SetHeightSafe"/> method must be used.
    /// </summary>
    void setSizeSafe(double width, double height, SizeD imageSize, double rotationAng) throws Exception
    {
        SizeD scaledImageSize = ShapeSizeValidationHelper.getScaledImageSize(this, width, height, imageSize, rotationAng);

        setWidthSafe(scaledImageSize.getWidth());
        setHeightSafe(scaledImageSize.getHeight());
    }

    /// <summary>
    /// Sets width of the shape. Brings the value into the valid range.
    /// </summary>
    /// <remarks>
    /// Method processes shape width without processing <see cref="AspectRatioLocked"/> attribute.
    /// AspectRatioLocked have to be taken in account only when client is changing width programmatically
    /// through the object model.
    /// </remarks>
    void setWidthSafe(double value)
    {
        setWidthCore(value, false);
    }

    /// <summary>
    /// Sets height of the shape. Brings the value into the valid range.
    /// </summary>
    /// <remarks>
    /// Method processes shape height without processing <see cref="AspectRatioLocked"/> attribute.
    /// AspectRatioLocked have to be taken in account only when client is changing height programmatically
    /// through the object model.
    /// </remarks>
    void setHeightSafe(double value)
    {
        setHeightCore(value, false);
    }

    /// <summary>
    /// We normally validate shape size when width or height is set, but we also allow validation
    /// of the shape size before document save. This is needed in case some child-level
    /// shape with huge local coordinates was turned into a top-level shape by the user.
    /// </summary>
    void makeSizeValid() throws Exception
    {
        validateZeroSizeShape();

        setWidthSafe(getWidth());
        setHeightSafe(getHeight());
    }

    /// <summary>
    /// Gets the topmost parent shape (root) of the shape tree.
    /// </summary>
    ShapeBase getTopLevelParentShape()
    {
        ShapeBase shape = this;
        while (!shape.isTopLevel())
            shape = (ShapeBase)shape.getParentNode();

        return shape;
    }

    /// <summary>
    /// Verify whether shape Bounds need to be updated.
    /// </summary>
    /// <remarks>
    /// Currently update size only for <see cref="Aspose.Words.Drawing.ShapeType.NonPrimitive"/> shapes with zero size
    /// if there are <see cref="ShapeAttr.GeometryVertices"/>.
    /// </remarks>
    boolean requiresBoundsRecalculation()
    {
        PathPoint[] pathPoints = (PathPoint[])getShapePr().getDirectAttr(ShapeAttr.GEOMETRY_VERTICES);
        return ((getShapeType() == ShapeType.NON_PRIMITIVE) && (MathUtil.isZero(getWidth())) && (MathUtil.isZero(getHeight()))
                && (pathPoints != null) && (pathPoints.length > 0));
    }

    /// <summary>
    /// Updates shape size and position.
    /// It is not enough just update shape size, we also need update position of content inside shape,
    /// because previously it was drawn from top left corner of page.
    /// </summary>
    void updateSizeAndPosition()
    {
        // Gets the bounding rectangle.
        RectangleF boundingRect = getPathBoundingRect();
        if (msSizeF.isEmpty(boundingRect.getSize()))
            return;

        // Set new size and margins.
        double width = ConvertUtilCore.twipToPoint(boundingRect.getWidth());
        double height = ConvertUtilCore.twipToPoint(boundingRect.getHeight());

        setWidthSafe(width);
        setHeightSafe(height);

        setLeft(ConvertUtilCore.twipToPoint(boundingRect.getX()));
        setTop(ConvertUtilCore.twipToPoint(boundingRect.getY()));

        // Position of path should be changed according to margins.
        PathPoint[] pathPoints = (PathPoint[])getShapePr().get(ShapeAttr.GEOMETRY_VERTICES);
        for (int i = 0; i < pathPoints.length; i++)
        {
            pathPoints[i] = new PathPoint(pathPoints[i].getX().getValue() - (int)boundingRect.getX(),
                                          pathPoints[i].getY().getValue() - (int)boundingRect.getY());
        }

        setShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES, pathPoints);
    }

    /// <summary>
    /// Adjust shape height if horizontal rule is specified.
    /// </summary>
    void adjustHorizontalRuleHeight()
    {
        if (isHorizontalRule() && getShapePr().contains(ShapeAttr.HR_HEIGHT))
        {
            setHeightSafe(ConvertUtilCore.twipToPoint((/*int*/Integer)getDirectShapeAttrInternal(ShapeAttr.HR_HEIGHT)));
            removeShapeAttrInternal(ShapeAttr.HR_HEIGHT);
        }
    }

    /// <summary>
    /// If the size or position of the shape is specified using relative (percentage) then updates the
    /// actual size and position of the shape. But uses a very simplistic and crude algorithm for this.
    /// Only suitable for down converting when exporting to formats that do not support relative position and size.
    /// </summary>
    void updateSizeAndPositionFromRelative()
    {
        // I think only top level shapes have relative positioning.
        if (!isTopLevel())
            return;

        // In DrawingMl there are cases when size is not updated from relative. Do the same.
        if ((getMarkupLanguage() == ShapeMarkupLanguage.DML) && !getDmlNode().isUpdateDimensionsFromRelative())
            return;

        Section section = (Section)getAncestor(NodeType.SECTION);
        if (section == null)
            return;

        PageSetup ps = section.getPageSetup();
        updateLeftFromPercent(ps);
        updateTopFromPercent(ps);
        updateWidthFromPercent(ps);
        updateHeightFromPercent(ps);
    }

    void setCoordSizeSafe(/*Size*/long value)
    {
        getGraphicData().setCoordSizeSafe(value);
    }

    void setCoordSizeWidthSafe(int width)
    {
        getGraphicData().setCoordSizeWidthSafe(width);
    }

    void setCoordSizeHeightSafe(int height)
    {
        getGraphicData().setCoordSizeHeightSafe(height);
    }

    /// <summary>
    /// Returns <c>true</c> if Dml shape was changed since load.
    /// </summary>
    boolean isChangedSinceLoad() throws Exception
    {
        return (mHashCodeCache != calculateHashCode());
    }

    /// <summary>
    /// Stores original hash code, calculated right after
    /// document reading (please see <see cref="Aspose.Words.Validation.DocumentPostLoader.ProcessFallback(ShapeBase)"/>)
    /// </summary>
    void markLoaded() throws Exception
    {
        mHashCodeCache = calculateHashCode();
    }

    /// <summary>
    /// Sets correct parent for FallBack node.
    /// </summary>
    /// <remarks>
    /// andrnosk: After cloning/importing document DrawingML belongs to cloned/imported document, but FallBack
    /// still belongs to the original document because it is alternate node
    /// outside of the main tree.
    /// That is why we need to set parent directly to FallBack.
    /// DS: Null node must be set as parent. At this case the FallBack still outside of the main tree and it has valid
    /// reference to the document.
    /// </remarks>
    void updateFallBackParent()
    {
        if ((getFallBack() != null) && (getFallBack().getDocument() != getDocument()))
            getFallBack().setParent(getDocument().getNullNode());
    }

    ImageSizeCore getShapeImageSize() throws Exception
    {
        Shape shape = ms.as(this, Shape.class);
        if ((shape != null) && shape.hasImage())
        {
            ImageData imageData = shape.getImageData();

            // If an image is linked, but not stored in the document, try to load ImageBytes which will be used to get size.
            byte[] imageBytes = shape.hasImageBytes() ? imageData.getImageBytes() : imageData.loadImageBytes();

            if (imageBytes != null)
                return ImageUtil.getImageSize(imageBytes);
        }

        return null;
    }

    /// <summary>
    /// Sets a flag indicating that shape is a signature line.
    /// </summary>
    void setIsSignatureLine(boolean value)
    {
        if (isSignatureLine() != value)
        {
            setShapeAttrInternal(ShapeAttr.IS_SIGNATURE_LINE, value);
            if (value)
                setShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID, Guid.Empty.toString("B").toUpperCase());
        }
    }

    /// <summary>
    /// Generated alternative text.
    /// In case older format conversion MS Word formats following string "Title: {titleText} - Description: {descText}"
    /// If there is no description "Title: {titleText}"
    /// If there is no title "{descText}"
    /// </summary>
    static String generateAltText(String title, String description)
    {
        String altText;

        if (StringUtil.hasChars(title))
        {
            String titleText = msString.format("Title: {0}", title);

            if (StringUtil.hasChars(description))
                altText = msString.format("{0} - Description: {1}", titleText, description);
            else
                altText = titleText;
        }
        else
        {
            altText = description;
        }

        return altText;
    }

    /// <summary>
    /// True when specified shape type can be created from the customers code.
    /// </summary>
    static boolean isAllowedShapeTypes(/*ShapeType*/int shapeType)
    {
        // Let's block some shapes from user creation until we provide full API for them.
        switch (shapeType)
        {
            case ShapeType.OLE_CONTROL:
            case ShapeType.OLE_OBJECT:
            case ShapeType.NON_PRIMITIVE:
            case ShapeType.CUSTOM_SHAPE:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Indicates whether the shape contains an SVG image in a DML extension.
    /// </summary>
    boolean hasSvgBlip()
    {
        DmlPicture picture = ms.as(getDmlNode(), DmlPicture.class);

        if ((picture == null) || (picture.getBlipFill() == null) || !picture.getBlipFill().getBlip().hasExtensions())
            return false;

        DmlExtension svgExtension = picture.getBlipFill().getBlip().getExtensions().get(DmlExtensionUri.SVG_BLIP);

        if ((svgExtension == null) || (svgExtension.getSvgBlip() == null))
            return false;

        return true;
    }

    /// <summary>
    /// Returns SVG image bytes from a DML extenstion, if the shape contains one.
    /// </summary>
    /// <returns>SVG image bytes or <c>null</c>.</returns>
    byte[] getImageBytesFromSvgBlip() throws Exception
    {
        if (!hasSvgBlip())
            return null;

        DmlExtension svgExtension = ((DmlPicture)getDmlNode()).getBlipFill().getBlip().getExtensions().get(DmlExtensionUri.SVG_BLIP);
        return svgExtension.getSvgBlip().getImageBytes();
    }

    private void updateLeftFromPercent(PageSetup ps)
    {
        Object objLeftPercent = getShapePr().get(ShapeAttr.LEFT_PERCENT);
        if ((objLeftPercent == null) || ((/*int*/Integer)objLeftPercent == DEFAULT_PERCENT_VALUE))
            return;

        double percent = (/*int*/Integer)objLeftPercent / 1000.0;
        switch (getRelativeHorizontalPosition())
        {
            case RelativeHorizontalPosition.MARGIN:
                setLeft(ps.getContentWidth() * percent);
                break;
            case RelativeHorizontalPosition.PAGE:
                setLeft(ps.getPageWidth() * percent);
                break;
            case RelativeHorizontalPosition.LEFT_MARGIN:
            case RelativeHorizontalPosition.OUTSIDE_MARGIN:  // In this simplistic algorithm outside margin is a left margin.
                setLeft(ps.getLeftMargin() * percent);
                break;
            case RelativeHorizontalPosition.RIGHT_MARGIN:
            case RelativeHorizontalPosition.INSIDE_MARGIN:   // In this simplistic algorithm inside margin is a right margin.
                setLeft(ps.getRightMargin() * percent);
                break;
            default:
                // All others are not valid for relative position.
                break;
        }

        // WORDSNET-12639 Take effects size in account when calculate relative position of shape.
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            // Get left and right effect extent.
            double effectExtentLeft = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_LEFT));

            // Left position must be calculated from shapes edge, if there is effect at the left side
            // it is required to add its extent to left position calculated from relative.
            if (!MathUtil.isZero(effectExtentLeft))
                setLeft(getLeft() + effectExtentLeft);
        }
    }

    private void updateTopFromPercent(PageSetup ps)
    {
        // WORDSNET-12671 Mimic MS Word behavior and ignore relative vertical position if shape is inside table.
        Node parentTable = getAncestor(NodeType.TABLE);
        if (parentTable != null)
            return;

        Object objTopPercent = getShapePr().get(ShapeAttr.TOP_PERCENT);
        if ((objTopPercent == null) || ((/*int*/Integer)objTopPercent == DEFAULT_PERCENT_VALUE))
            return;

        double percent = (/*int*/Integer)objTopPercent / 1000.0;
        switch (getRelativeVerticalPosition())
        {
            case RelativeVerticalPosition.MARGIN:
                setTop(ps.getContentHeight() * percent);
                break;
            case RelativeVerticalPosition.PAGE:
                setTop(ps.getPageHeight() * percent);
                break;
            case RelativeVerticalPosition.TOP_MARGIN:
            case RelativeVerticalPosition.OUTSIDE_MARGIN:    // We some reason MS Word calculates outside and inside margins as the top margin.
            case RelativeVerticalPosition.INSIDE_MARGIN:
                setTop(ps.getTopMargin() * percent);
                break;
            case RelativeVerticalPosition.BOTTOM_MARGIN:
                setTop(ps.getBottomMargin() * percent);
                break;
            default:
                // All others are not valid for relative position.
                break;
        }

        // WORDSNET-12639 Take effects size in account when calculate relative position of shape.
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            // Get left and right effect extent.
            double effectExtentTop = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_TOP));

            // Top position must be calculated from shapes edge, if there is effect at the top side
            // it is required to add its extent to top position calculated from relative.
            if (!MathUtil.isZero(effectExtentTop))
                setTop(getTop() + effectExtentTop);
        }
    }

    private void updateWidthFromPercent(PageSetup ps)
    {
        // andrnosk: WORDSNET-9684 Mimic MS Word behavior and do not update
        // shape width in case when TextBox shape is Square and text layout is vertical.
        // WORDSNET-13632 MSW for another shapes with vertical text layout and in-line shapes
        // does not update shape width.
        if (isPercentWidthInapplicable())
            return;

        Object objWidthPercent = getWidthPercent();
        if (objWidthPercent == null)
            return;

        double percent = (/*int*/Integer)objWidthPercent / 1000.0;
        // Width cannot be negative or zero.
        if (percent <= 0)
            return;

        double newWidth = getWidth();
        switch (getRelativeHorizontalSize())
        {
            case RelativeHorizontalSize.MARGIN:
                newWidth = ps.getContentWidth() * percent;
                break;
            case RelativeHorizontalSize.PAGE:
                newWidth = ps.getPageWidth() * percent;
                break;
            case RelativeHorizontalSize.LEFT_MARGIN:
            case RelativeHorizontalSize.OUTER_MARGIN: // In this simplistic algorithm outside margin is a left margin.
                newWidth = ps.getLeftMargin() * percent;
                break;
            case RelativeHorizontalSize.RIGHT_MARGIN:
            case RelativeHorizontalSize.INNER_MARGIN: // In this simplistic algorithm inside margin is a right margin.
                newWidth = ps.getRightMargin() * percent;
                break;
            default:
                // All others are not valid.
                break;
        }

        mBoundsForRelativeSize = msSizeF.ctor((float)newWidth, msSizeF.getHeight(mBoundsForRelativeSize));

        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            // Get left and right effect extent.
            double effectExtentLeft = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_LEFT));
            double effectExtentRight = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_RIGHT));

            // Effects extends are absolute and their values does not depend on relative width.
            if (!MathUtil.isZero(effectExtentLeft) || !MathUtil.isZero(effectExtentRight))
            {
                // In case of 3D effects size might be negative, it seems min size is acceptable here.
                newWidth = Math.max(ShapeSizeValidationHelper.MIN_SHAPE_SIZE, newWidth - effectExtentLeft - effectExtentRight);
            }
        }

        setPercentWidth(Math.abs(newWidth));
    }

    /// <summary>
    /// Check that width of the <see cref="ShapeBase"/> object does not need to be updated.
    /// </summary>
    /// <returns>"True" when need to skip width update.</returns>
    abstract boolean isPercentWidthInapplicable();

    private void updateHeightFromPercent(PageSetup ps)
    {
        Object objHeightPercent = getHeightPercent();
        if (objHeightPercent == null)
            return;

        double percent = (/*int*/Integer)objHeightPercent / 1000.0;

        // Height cannot be negative or zero.
        if (percent <= 0)
            return;

        double newHeight = getHeight();
        switch (getRelativeVerticalSize())
        {
            case RelativeVerticalSize.MARGIN:
                newHeight = ps.getContentHeight() * percent;
                break;
            case RelativeVerticalSize.PAGE:
                newHeight = ps.getPageHeight() * percent;
                break;
            case RelativeVerticalSize.TOP_MARGIN:
            case RelativeVerticalSize.OUTER_MARGIN:    // For some reason MS Word calculates outside and inside margins as the top margin.
            case RelativeVerticalSize.INNER_MARGIN:
                newHeight = ps.getTopMargin() * percent;
                break;
            case RelativeVerticalSize.BOTTOM_MARGIN:
                newHeight = ps.getBottomMargin() * percent;
                break;
            default:
                // All others are not valid.
                break;
        }

        mBoundsForRelativeSize = msSizeF.ctor(msSizeF.getWidth(mBoundsForRelativeSize), (float)newHeight);

        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            // Get left and right effect extent.
            double effectExtentTop = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_TOP));
            double effectExtentBottom = ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM));

            // ItEffects extends are absolute and their values does not depend on relative width.
            if (!MathUtil.isZero(effectExtentTop) || !MathUtil.isZero(effectExtentBottom))
            {
                // In case of 3D effects size might be negative, it seems min size is acceptable here.
                newHeight = Math.max(ShapeSizeValidationHelper.MIN_SHAPE_SIZE, newHeight - effectExtentTop - effectExtentBottom);
            }
        }

        setPercentHeight(Math.abs(newHeight));
    }

    private void setPercentWidth(double value)
    {
        // When angle is between 45 and 135 degrees or between 225 and 315 degrees, MS Word switches relative dimensions.
        if (getSwitchPercentDimensions())
           setHeightSafe(value);
        else
           setWidthSafe(value);
    }

    private void setPercentHeight(double value)
    {
        // When angle is between 45 and 135 degrees or between 225 and 315 degrees, MS Word switches relative dimensions.
        if (getSwitchPercentDimensions())
            setWidthSafe(value);
        else
            setHeightSafe(value);
    }

    private void setWidthLockAspectRatioSensitive(double value, boolean isThrow) throws Exception
    {
        double validWidth = ShapeSizeValidationHelper.validateDimension(this, value, isThrow, "width");

        // If LockAspectRatio is set, we have to set width and height proportionally.
        if (getAspectRatioLocked())
        {
            /*SizeF*/long calcSize = calculateNotZeroSize();
            double ratio = validWidth / msSizeF.getWidth(calcSize);
            setHeightCore(ShapeSizeValidationHelper.validateDimension(this, msSizeF.getHeight(calcSize) * ratio, isThrow, "height"),
                isThrow);
        }

        setWidthCore(validWidth, isThrow);
    }

    private void setHeightLockAspectRatioSensitive(double value, boolean isThrow) throws Exception
    {
        double validHeight = ShapeSizeValidationHelper.validateDimension(this, value, isThrow, "height");

        // If LockAspectRatio is set, we have to set width and height proportionally.
        if (getAspectRatioLocked())
        {
            /*SizeF*/long calcSize = calculateNotZeroSize();
            double ratio = validHeight / msSizeF.getHeight(calcSize);
            setWidthCore(ShapeSizeValidationHelper.validateDimension(this, msSizeF.getWidth(calcSize) * ratio, isThrow, "width"),
                isThrow);
        }

        setHeightCore(validHeight, isThrow);
    }

    private void resetRelSizeValue(int key)
    {
        Debug.assertTrue((key == ShapeAttr.HEIGHT_PERCENT) || (key == ShapeAttr.WIDTH_PERCENT));

        if (getShapePr().get(key) != null)
            setShapeAttrInternal(key, 0);
    }

    private void setWidthCore(double value, boolean isThrow)
    {
        getGraphicData().setWidthCore(value, isThrow);
    }

    private void setHeightCore(double value, boolean isThrow)
    {
        getGraphicData().setHeightCore(value, isThrow);
    }

    /// <summary>
    /// Returns true if the current VML shape can be filled with the specified color.
    /// </summary>
    private boolean canBeFilled(DrColor color)
    {
        Debug.assertTrue(getMarkupLanguage() == ShapeMarkupLanguage.VML, "Valid for VML only.");

        return ((color != null) && getFillCore().getOn() && (getFillCore().getFillType() != FillTypeCore.PICTURE) &&
                (getFillCore().getFillType() != FillTypeCore.TEXTURE));
    }

    /// <summary>
    /// Sets image size for corresponding zero size shape.
    /// </summary>
    void validateZeroSizeShape() throws Exception
    {
        // andrnosk: WORDSNET-775 If shape size equals zero but shape has image,
        // we have to set shape size equals image size.

        if (!MathUtil.isZero(getWidth()) || !MathUtil.isZero(getHeight()))
            return;

        ImageSizeCore imageSize = getShapeImageSize();
        if (imageSize != null)
        {
            setWidthSafe(imageSize.getWidthPoints());
            setHeightSafe(imageSize.getHeightPoints());
        }
    }

    private /*SizeF*/long calculateNotZeroSize() throws Exception
    {
        double width = getWidth();
        double height = getHeight();

        if (MathUtil.isZero(width) || MathUtil.isZero(height))
        {
            ImageSizeCore imageSizeCore = getShapeImageSize();
            if ((imageSizeCore != null) && (MathUtil.isZero(width) && MathUtil.isZero(height)))
            {
                width = imageSizeCore.getWidth();
                height = imageSizeCore.getHeight();
            }
            else
            {
                // This seems to be the value that MS Word uses when image shape size is zero.
                width = DefaultShapeSize;
                height = DefaultShapeSize;
            }
        }

        return msSizeF.ctor((float)width, (float)height);
    }

    /// <summary>
    /// Calculates simplified hash code to catch changes made from the public API.
    /// This method is used to invalidate fallback.
    /// </summary>
    private long calculateHashCode() throws Exception
    {
        /*unchecked*/
        {
            int hash = (int)(int) 2166136261L;
            // Get all text from the Dml and use it in hash code computation
            // (fixed WORDSNET-10669, WORDSNET-11322, WORDSNET-11344)
            hash = (hash * 16777619 ^ toString(SaveFormat.TEXT).hashCode());
            // We have to add to hash code computation all fields which can be modified through the public API.
            // Currently not all of fields are added. The other will be added by request.
            hash = (hash * 16777619 ^ getName().hashCode());
            hash = (hash * 16777619 ^ getHRef().hashCode());
            hash = (hash * 16777619 ^ getTarget().hashCode());
            hash = (hash * 16777619 ^ getScreenTip().hashCode());
            hash = (hash * 16777619 ^ getAlternativeText().hashCode());
            hash = (hash * 16777619 ^ (/*int.hashCode()*/getZOrder()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getWidth()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getHeight()));

            hash = (hash * 16777619 ^ msNumber.booleanHashCode(getAnchorLocked()));
            hash = (hash * 16777619 ^ msNumber.booleanHashCode(getAllowOverlap()));
            hash = (hash * 16777619 ^ msNumber.booleanHashCode(getBehindText()));

            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getLeft()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getTop()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getRight()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getBottom()));

            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getDistanceTop()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getDistanceBottom()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getDistanceLeft()));
            hash = (hash * 16777619 ^ msNumber.doubleHashCode(getDistanceRight()));

            hash = (hash * 16777619 ^ (/*int.hashCode()*/((int)getWrapType())));//casting for Java that can't get hashcode of enum.

            return hash;
        }
    }

    /// <summary>
    /// Convert <see cref="PathPoint"/> array to <see cref="PointF"/> array.
    /// </summary>
    private static /*PointF*/long[] convertPathPointToPointF(PathPoint[] pathPoints)
    {
        if ((pathPoints != null) && (pathPoints.length > 0))
        {
            /*PointF*/long[] pointF = new /*PointF*/long[pathPoints.length];
            for (int i = 0; i < pathPoints.length; i++)
            {
                PathPoint pathPoint = pathPoints[i];
                pointF[i] = msPointF.ctor(pathPoint.getX().getValue(), pathPoint.getY().getValue());
            }
            return pointF;
        }
        return null;
    }

    /// <summary>
    /// Returns bounding rectangle for shape Path.
    /// </summary>
    private RectangleF getPathBoundingRect()
    {
        PathPoint[] pathPoints = (PathPoint[])getShapePr().getDirectAttr(ShapeAttr.GEOMETRY_VERTICES);

        /*PointF*/long[] pointF = convertPathPointToPointF(pathPoints);

        if (pointF == null)
            return RectangleF.Empty;

        return GeometryUtil.getBounds(pointF);
    }

    /// <summary>
    /// Gets the bounding box with applied effectExtents.
    /// </summary>
    private RectangleF getsBoundsWithEffects()
    {
        float addToLeft = getExtentEffectValue(ShapeAttr.DML_EFFECT_EXTENT_LEFT);
        float addToRight = getExtentEffectValue(ShapeAttr.DML_EFFECT_EXTENT_RIGHT);
        float addToTop = getExtentEffectValue(ShapeAttr.DML_EFFECT_EXTENT_TOP);
        float addToBottom = getExtentEffectValue(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM);

        RectangleF sizeBox = getsSizeBoxForBounds(getRotation());

        return new RectangleF(
            sizeBox.getX() - addToLeft,
            sizeBox.getY() - addToTop,
            sizeBox.getWidth() + addToLeft + addToRight,
            sizeBox.getHeight() + addToTop + addToBottom);
    }

    /// <summary>
    /// Gets the size box for bounding box calculation.
    /// </summary>
    RectangleF getsSizeBoxForBounds(double rotation)
    {
        // WORDSNET-16661, WORDSNET-17515 MS Word uses height and width values
        // that are rounded to twips (see TestInlineDmlShapeWidth).
        float height = getIntegerNumberOfTwips((float)getHeight());
        float width = getIntegerNumberOfTwips((float)getWidth());

        RectangleF sizeBox = new RectangleF(0f, 0f, width, height);

        // FOSS

        return sizeBox;
    }

    /// <summary>
    /// Gets integer number of the twips in the specified value converts into target measurement units.
    /// </summary>
    /// <param name="value">The specified value</param>
    /// <returns>Integer number of the twips converted into target measurement units</returns>
    private float getIntegerNumberOfTwips(float value)
    {
        if (!isTopLevel() || (getDmlNode() == null))
            return value;

        // Convert to EMU for greater accuracy.
        float valueInEmus = ConvertUtilCore.pointToEmu(value);
        double result = Math.floor(valueInEmus / ConvertUtilCore.EMUS_PER_TWIP) * ConvertUtilCore.EMUS_PER_TWIP;
        return (float)ConvertUtilCore.emuToPoint(result);
    }

    /// <summary>
    /// Gets the effectExtent value and checks it.
    /// </summary>
    /// <param name="attr">The specified shape attribute key</param>
    /// <returns>The checked effectExtent value</returns>
    private float getExtentEffectValue(int attr)
    {
        float value = (float)ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(attr));

        if (isInline())
            return value;

        // If the shape is not inline, is placed in a table cell, wrapType is not TopBottom and compatibility version
        // is less the Word2013, the bottom value is ignored (see TestJira13608NestingInAlignedCell).
        if (attr == ShapeAttr.DML_EFFECT_EXTENT_BOTTOM)
        {
            boolean isTopLevelCell = (getParentParagraph() != null)
                                    && (getParentParagraph().getParentNode() != null) &&
                                    (getParentParagraph().getParentNode().getNodeType() == NodeType.CELL);
            boolean isWrapTopBottom = (getWrapType() == WrapType.TOP_BOTTOM);
            boolean compatVersion = (getDocument().getDocPr().CompatibilityOptions.getMswVersion() != MsWordVersionCore.UNSPECIFIED)
                                    && (getDocument().getDocPr().CompatibilityOptions.getMswVersion() < MsWordVersionCore.WORD_2013);
            value = (compatVersion && !isWrapTopBottom && isTopLevelCell) ? 0f : value;
        }

        // WORDSNET-9014 If shape is not inline, and value is less then 635 EMU (1 Twip) MS Word uses the zero value.
        // Checked for top and bottom values. Use the same logic for the left and right values.
        // This change in the right and left values does not affect the existing tests.
        return (value < ConvertUtilCore.POINTS_PER_TWIP) ? 0f : value;
    }

    /// <summary>
    /// Sets gradient fill in accordance with a specified style and variant.
    /// </summary>
    private void setVmlGradientFill(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        // Clear gradient colors that might be in the current fill.
        getShapePr().remove(ShapeAttr.FILL_SHADE_COLORS);
        // Clear filled flag.
        getShapePr().remove(ShapeAttr.FILLED);

        // Path gradient
        if ((style == GradientStyle.FROM_CENTER) || (style == GradientStyle.FROM_CORNER))
        {
            VmlFill.setFocusPositions(getShapePr(), style, variant);
            getShapePr().setAttrIfNotDefault(
                ShapeAttr.FILL_TYPE,
                (style == GradientStyle.FROM_CORNER) ? FillTypeCore.SHADE_CENTER : FillTypeCore.SHADE_SHAPE);
        }
        else // Linear gradient
        {
            getShapePr().setAttrIfNotDefault(
                ShapeAttr.FILL_ANGLE,
                ConvertUtilCore.doubleToFixed(VmlFill.getLinearGradientAngle(style)));
            getShapePr().setAttrIfNotDefault(ShapeAttr.FILL_TYPE, FillTypeCore.SHADE_SCALE);
        }

        // Need to remove the attribute because if the new value equals to default, it will be not set by
        // SetAttrIfNotDefault and the old value will be preserved.
        getShapePr().remove(ShapeAttr.FILL_FOCUS);
        getShapePr().setAttrIfNotDefault(ShapeAttr.FILL_FOCUS, VmlFill.getGradientFocus(style, variant));
    }

    /// <summary>
    /// Returns <see cref="DmlShapeEffectsCollection"/> object of the shape.
    /// </summary>
    /// <param name="isCreate">Indicates whether to create collection automatically, if it doesn't exist.</param>
    private DmlShapeEffectsCollection getDmlShapeEffectsCollection(boolean isCreate)
    {
        if (getDmlShape() == null)
            throw new IllegalStateException(INVALID_ACTION);

        if (getDmlShape().getEffects() == null && isCreate)
            getDmlShape().setEffects(new DmlShapeEffectsCollection());

        return getDmlShape().getEffects();
    }

    /// <summary>
    /// Returns <see cref="DmlShapeGlowEffect"/> object of the shape.
    /// </summary>
    /// <param name="isCreate">Indicates whether to create glow effect automatically, if it doesn't exist.</param>
    private DmlShapeGlowEffect getDmlShapeGlowEffect(boolean isCreate)
    {
        if (getDmlShape() == null)
            throw new IllegalStateException(INVALID_ACTION);

        return (DmlShapeGlowEffect)getDmlShapeEffect(DmlShapeEffectType.GLOW, isCreate);
    }

    /// <summary>
    /// Returns <see cref="DmlShapeReflectionEffect"/> object of the shape.
    /// </summary>
    /// <param name="isCreate">Indicates whether to create reflection effect automatically, if it doesn't exist.</param>
    private DmlShapeReflectionEffect getDmlShapeReflectionEffect(boolean isCreate)
    {
        if (getDmlShape() == null)
            throw new IllegalStateException(INVALID_ACTION);

        return (DmlShapeReflectionEffect)getDmlShapeEffect(DmlShapeEffectType.REFLECTION, isCreate);
    }

    /// <summary>
    /// Returns <see cref="DmlShapeSoftEdgeEffect"/> object of the shape.
    /// </summary>
    /// <param name="isCreate">Indicates whether to create soft edge effect automatically, if it doesn't exist.</param>
    private DmlShapeSoftEdgeEffect getDmlShapeSoftEdgeEffect(boolean isCreate)
    {
        if (getDmlShape() == null)
            throw new IllegalStateException(INVALID_ACTION);

        return (DmlShapeSoftEdgeEffect)getDmlShapeEffect(DmlShapeEffectType.SOFT_EDGES, isCreate);
    }

    /// <summary>
    /// Returns <see cref="DmlShapeEffect"/> object of a specified type.
    /// </summary>
    /// <param name="type">The type of the effect to get.</param>
    /// <param name="isCreate">Indicates whether to create effect automatically, if it doesn't exist.</param>
    private DmlShapeEffect getDmlShapeEffect(/*DmlShapeEffectType*/int type, boolean isCreate)
    {
        DmlShapeEffectsCollection effects = getDmlShapeEffectsCollection(isCreate);
        if (effects == null)
            return null;

        DmlShapeEffect effect = effects.getByDmlShapeEffectType(type);
        if ((effect == null) && isCreate)
        {
            effect = DmlShapeEffect.createEffect(type);
            effects.addEffect(effect);
        }

        return effect;
    }

    /// <summary>
    /// Gets fill formatting for the shape.
    /// </summary>
    public Fill getFill()
    {
        if (mFill == null)
            mFill = new Fill(this);

        return mFill;
    }

    /// <summary>
    /// Gets shadow formatting for the shape.
    /// </summary>
    public ShadowFormat getShadowFormat()
    {
        if (mShadow == null)
            mShadow = new ShadowFormat(this);

        return mShadow;
    }

    /// <summary>
    /// Gets glow formatting for the shape.
    /// </summary>
    public GlowFormat getGlow()
    {
        if (mGlowFormat == null)
            mGlowFormat = new GlowFormat(this);

        return mGlowFormat;
    }

    /// <summary>
    /// Gets reflection formatting for the shape.
    /// </summary>
    public ReflectionFormat getReflection()
    {
        if (mReflectionFormat == null)
            mReflectionFormat = new ReflectionFormat(this);

        return mReflectionFormat;
    }

    /// <summary>
    /// Gets soft edge formatting for the shape.
    /// </summary>
    public SoftEdgeFormat getSoftEdge()
    {
        if (mSoftEdgeFormat == null)
            mSoftEdgeFormat = new SoftEdgeFormat(this);

        return mSoftEdgeFormat;
    }

    /// <summary>
    /// Defines the text displayed when the mouse pointer moves over the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getScreenTip() { return (String)fetchShapeAttrInternal(ShapeAttr.SCREEN_TIP); }
    public void setScreenTip(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setShapeAttrInternal(ShapeAttr.SCREEN_TIP, value);
    }

    /// <summary>
    /// Gets or sets the full hyperlink address for a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    ///
    /// <p>Below are examples of valid values for this property:</p>
    /// <p>Full URI: <c>https://www.aspose.com/</c>.</p>
    /// <p>Full file name: <c>C:\\My Documents\\SalesReport.doc</c>.</p>
    /// <para>Relative URI: <c>../../../resource.txt</c></para>
    /// <p>Relative file name: <c>..\\My Documents\\SalesReport.doc</c>.</p>
    /// <p>Bookmark within another document: <c>https://www.aspose.com/Products/Default.aspx#Suites</c></p>
    /// <p>Bookmark within this document: <c>#BookmakName</c>.</p>
    /// </remarks>
    public String getHRef() { return (String)fetchShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS); }
    public void setHRef(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        getGraphicData().setHRef(value);
    }

    /// <summary>
    /// Gets or sets the target frame for the shape hyperlink.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getTarget() { return (String)fetchShapeAttrInternal(ShapeAttr.HYPERLINK_TARGET); }
    public void setTarget(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setShapeAttrInternal(ShapeAttr.HYPERLINK_TARGET, value);
    }

    /// <summary>
    /// Defines alternative text to be displayed instead of a graphic.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getAlternativeText()
    {
        String altText = getGraphicData().getAlternativeText();
        return StringUtil.hasChars(altText) ? altText : "";
    }
    public void setAlternativeText(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");

        getGraphicData().setAlternativeText(value);
    }

    /// <summary>
    /// Gets or sets the flag that specifies whether the shape is decorative in the document.
    /// </summary>
    /// <remarks>
    /// Note that shape having not empty <see cref="ShapeBase.AlternativeText"/> cannot be decorative.
    /// </remarks>
    public boolean isDecorative() { return getGraphicData().getDecorative(); }
    public void isDecorative(boolean value) { getGraphicData().setDecorative(value); }

    /// <summary>
    /// Gets or sets the title (caption) of the current shape object.
    /// </summary>
    /// <remarks>
    /// <para>Default is empty string.</para>
    /// <para>Cannot be <c>null</c>, but can be an empty string.</para>
    /// </remarks>
    public String getTitle()
    {
        String title = getGraphicData().getTitle();
        return StringUtil.hasChars(title) ? title : "";
    }
    public void setTitle(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");

        getGraphicData().setTitle(value);
    }

    /// <summary>
    /// Gets or sets the optional shape name.
    /// </summary>
    /// <remarks>
    /// <para>Default is empty string.</para>
    /// <para>Cannot be <c>null</c>, but can be an empty string.</para>
    /// </remarks>
    public String getName()
    {
        String name = getGraphicData().getName();
        return StringUtil.hasChars(name) ? name : "";
    }
    public void setName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");

        getGraphicData().setName(value);
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsInsertRevision"]/*'/>
    public boolean isInsertRevision() { return InlineHelper.isInsertRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsDeleteRevision"]/*'/>
    public boolean isDeleteRevision() { return InlineHelper.isDeleteRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveFromRevision"]/*'/>
    public boolean isMoveFromRevision() { return InlineHelper.isMoveFromRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveToRevision"]/*'/>
    public boolean isMoveToRevision() { return InlineHelper.isMoveToRevision(this); }

    /// <summary>
    /// Returns <c>true</c> if this shape is not a child of a group shape.
    /// </summary>
    public boolean isTopLevel()
    {
        return (getParentNode() == null) ||
            ((getParentNode().getNodeType() != NodeType.GROUP_SHAPE) && (getParentNode().getNodeType() != NodeType.SHAPE));
    }

    /// <summary>
    /// Returns <c>true</c> if this is a group shape.
    /// </summary>
    public boolean isGroup() { return (getShapeType() == ShapeType.GROUP); }

    /// <summary>
    /// Returns <c>true</c> if this shape is an image shape.
    /// </summary>
    public boolean isImage() { return (getShapeType() == ShapeType.IMAGE); }

    /// <summary>
    /// Return <c>true</c> is this shape is a TextBox shape.
    /// </summary>
    boolean isTextBox() { return getShapeType() == ShapeType.TEXT_BOX; }

    /// <summary>
    /// Returns <c>true</c> if this shape is a horizontal rule.
    /// </summary>
    public boolean isHorizontalRule() { return getGraphicData().isHorizontalRule(); }

    /// <summary>
    /// Returns <c>true</c> if this shape is a WordArt object.
    /// </summary>
    /// <remarks>
    /// Works till 2007 compatibility mode.
    /// In 2010 and higher compatibility mode WordArt is just a TextBox with fancy fonts.
    /// </remarks>
    public boolean isWordArt() { return getGraphicData().isWordArt(); }

    /// <summary>
    /// Returns <c>true</c> if the shape type allows the shape to have an image.
    /// </summary>
    /// <remarks>
    /// <para>Although Microsoft Word has a special shape type for images, it appears that in Microsoft Word documents any shape
    /// except a group shape can have an image, therefore this property returns <c>true</c> for all shapes except <see cref="GroupShape"/>.</para>
    /// </remarks>
    public boolean canHaveImage()
    {
        // WORDSNET-19771 We used to think that only Image and Ole shapes can have an image, but it appears
        // more shape types, for example rectangle can have an image. So I changed to include all shape types
        // and exclude only well known ones that cannot have an image.
        switch (getShapeType())
        {
            case ShapeType.GROUP:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Specifies whether the shape's anchor is locked.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// <p>Has effect only for top level shapes.</p>
    ///
    /// <p>This property affects behavior of the shape's anchor in Microsoft Word.
    /// When the anchor is not locked, moving the shape in Microsoft Word can move
    /// the shape's anchor too.</p>
    /// </remarks>
    public boolean getAnchorLocked() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.ANCHOR_LOCKED); }
    public void setAnchorLocked(boolean value) { setShapeAttrInternal(ShapeAttr.ANCHOR_LOCKED, value); }

    /// <summary>
    /// Specifies whether the shape's aspect ratio is locked.
    /// </summary>
    /// <remarks>
    /// <p>The default value depends on the <see cref="Drawing.ShapeType"/>, for the <see cref="Drawing.ShapeType.Image"/> it is <c>true</c>
    /// but for the other shape types it is <c>false</c>.</p>
    /// <p>Has effect for top level shapes only.</p>
    /// </remarks>
    public boolean getAspectRatioLocked() { return getGraphicData().getAspectRatioLocked(); }
    public void setAspectRatioLocked(boolean value) { getGraphicData().setAspectRatioLocked(value); }

    /// <summary>
    /// Gets or sets a value that specifies whether this shape can overlap other shapes.
    /// </summary>
    /// <remarks>
    /// <para>This property affects behavior of the shape in Microsoft Word.
    /// Aspose.Words ignores the value of this property.</para>
    /// <para>This property is applicable only to top level shapes.</para>
    /// <p>The default value is <c>true</c>.</p>
    /// </remarks>
    public boolean getAllowOverlap() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.ALLOW_OVERLAP); }
    public void setAllowOverlap(boolean value) { setShapeAttrInternal(ShapeAttr.ALLOW_OVERLAP, value); }

    /// <summary>
    /// Specifies whether the shape is below or above text.
    /// </summary>
    /// <remarks>
    /// <p>Has effect only for top level shapes.</p>
    /// <p>The default value is <c>false</c>.</p>
    ///
    /// <seealso cref="ZOrder"/>
    /// </remarks>
    public boolean getBehindText() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.BEHIND_TEXT); }
    public void setBehindText(boolean value) { setShapeAttrInternal(ShapeAttr.BEHIND_TEXT, value); }

    /// <summary>
    /// A quick way to determine if this shape is positioned inline with text.
    /// </summary>
    /// <remarks>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public boolean isInline() { return (getWrapType() == WrapType.INLINE); }

    /// <summary>
    /// Gets or sets the position of the left edge of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="LocationUnits"]/*'/>
    /// <p>The default value is 0.</p>
    /// <p>Has effect only for floating shapes.</p>
    /// </remarks>
    public double getLeft() { return (/*double*/Double)fetchShapeAttrInternal(ShapeAttr.LEFT); }
    public void setLeft(double value) { setShapeAttrInternal(ShapeAttr.LEFT, value); }

    /// <summary>
    /// Gets or sets the position of the top edge of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="LocationUnits"]/*'/>
    /// <p>The default value is 0.</p>
    /// <p>Has effect only for floating shapes.</p>
    /// </remarks>
    public double getTop() { return (/*double*/Double)fetchShapeAttrInternal(ShapeAttr.TOP); }
    public void setTop(double value) { setShapeAttrInternal(ShapeAttr.TOP, value); }

    /// <summary>
    /// Gets the position of the right edge of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="LocationUnits"]/*'/>
    /// </remarks>
    public double getRight() { return getLeft() + getWidth(); }

    /// <summary>
    /// Gets the position of the bottom edge of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="LocationUnits"]/*'/>
    /// </remarks>
    public double getBottom() { return getTop() + getHeight(); }

    /// <summary>
    /// Gets or sets the width of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="SizeUnits"]/*'/>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getWidth() { return (/*double*/Double)fetchShapeAttrInternal(ShapeAttr.WIDTH); }
    public void setWidth(double value) throws Exception
    {
        setWidthLockAspectRatioSensitive(value, true);
        resetRelSizeValue(ShapeAttr.WIDTH_PERCENT);
    }

    /// <summary>
    /// Gets or sets the height of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="SizeUnits"]/*'/>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getHeight() { return (/*double*/Double)fetchShapeAttrInternal(ShapeAttr.HEIGHT); }
    public void setHeight(double value) throws Exception
    {
        setHeightLockAspectRatioSensitive(value, true);
        // WORDSNET-16362 MSW automation resets relative height while absolute height value changing.
        resetRelSizeValue(ShapeAttr.HEIGHT_PERCENT);
    }

    /// <summary>
    /// Returns or sets the distance (in points) between the document text and the top edge of the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public double getDistanceTop() { return ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DISTANCE_TOP)); }
    public void setDistanceTop(double value) { setShapeAttrInternal(ShapeAttr.DISTANCE_TOP, ConvertUtilCore.pointToEmu(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the document text and the bottom edge of the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public double getDistanceBottom() { return ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DISTANCE_BOTTOM)); }
    public void setDistanceBottom(double value) { setShapeAttrInternal(ShapeAttr.DISTANCE_BOTTOM, ConvertUtilCore.pointToEmu(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the document text and the left edge of the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/8 inch.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public double getDistanceLeft() { return ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DISTANCE_LEFT)); }
    public void setDistanceLeft(double value) { setShapeAttrInternal(ShapeAttr.DISTANCE_LEFT, ConvertUtilCore.pointToEmu(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the document text and the right edge of the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1/8 inch.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public double getDistanceRight() { return ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DISTANCE_RIGHT)); }
    public void setDistanceRight(double value) { setShapeAttrInternal(ShapeAttr.DISTANCE_RIGHT, ConvertUtilCore.pointToEmu(value)); }

    /// <summary>
    /// Defines the angle (in degrees) that a shape is rotated.
    /// Positive value corresponds to clockwise rotation angle.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public double getRotation() { return getGraphicData().getRotation(); }
    public void setRotation(double value) { getGraphicData().setRotation(value); }

    /// <summary>
    /// Determines the display order of overlapping shapes.
    /// </summary>
    /// <remarks>
    /// <p>Has effect only for top level shapes.</p>
    /// <p>The default value is 0.</p>
    ///
    /// <p>The number represents the stacking precedence. A shape with a higher number will be displayed
    /// as if it were overlapping (in "front" of) a shape with a lower number. </p>
    ///
    /// <p>The order of overlapping shapes is independent for shapes in the header and in the main
    /// text of the document.</p>
    ///
    /// <p>The display order of child shapes in a group shape is determined by their order
    /// inside the group shape.</p>
    ///
    /// <seealso cref="BehindText"/>
    /// </remarks>
    public int getZOrder() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.Z_ORDER); }
    public void setZOrder(int value) { setShapeAttrInternal(ShapeAttr.Z_ORDER, value); }

    /// <summary>
    /// Returns the immediate parent paragraph.
    /// </summary>
    /// <remarks>For child shapes of a group shape and child shapes of an Office Math object always returns <c>null</c>.</remarks>
    public Paragraph getParentParagraph() { return ms.as(getParentNode(), Paragraph.class); }

    /// <summary>
    /// Gets or sets the location and size of the containing block of the shape.
    /// </summary>
    /// <remarks>
    /// Ignores aspect ratio lock upon setting.
    /// </remarks>
    /// <remarks>
    /// <include file='..\Docs\Drawing.xml' path='Topics/Topic[@name="LocationUnits"]/*'/>
    /// </remarks>
    //JAVA: property accessor is internalized
	
    RectangleF getBoundsInternal() { return new RectangleF((float)getLeft(), (float)getTop(), (float)getWidth(), (float)getHeight()); }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.geom.Rectangle2D.Float getBounds() { return RectangleF.toJava(getBoundsInternal()); }
    //JAVA: property accessor is internalized
	
    void setBoundsInternal(RectangleF value)
    {
        setLeft(value.getLeft());
        setTop(value.getTop());
        setWidthCore(value.getWidth(), false);
        setHeightCore(value.getHeight(), false);
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setBounds(java.awt.geom.Rectangle2D.Float value) { setBoundsInternal(RectangleF.fromJava(value)); }

    /// <summary>
    /// Gets the location and size of the containing block of the shape in points, relative to the anchor of the topmost shape.
    /// </summary>
    /// <remarks>
    /// The returned bounds do not include the rotation of this shape or the rotation of the parent group shape, if any.
    /// </remarks>
    //JAVA: property accessor is internalized
	
    RectangleF getBoundsInPointsInternal()
    {
        // Technically speaking, bounds of the shape are coordinates in the parent's coordinate space.
        return convertLocalToTopmostAnchor(getBoundsInternal());
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.geom.Rectangle2D.Float getBoundsInPoints() { return RectangleF.toJava(getBoundsInPointsInternal()); }

    /// <summary>
    /// Gets final extent that this shape object has after applying drawing effects.
    /// Value is measured in points.
    /// </summary>
    //JAVA: property accessor is internalized
	RectangleF getBoundsWithEffectsInternal() { return getsBoundsWithEffects(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.geom.Rectangle2D.Float getBoundsWithEffects() { return RectangleF.toJava(getBoundsWithEffectsInternal()); }

    /// <summary>
    /// Shows if effect extent is empty (all values are zero).
    /// </summary>
    boolean isEffectExtentEmpty()
    {
        return ((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_LEFT) == 0)
               && ((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_RIGHT) == 0)
               && ((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_TOP) == 0)
               && ((/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM) == 0);
    }

    /// <summary>
    /// Adds to the source rectangle values of the effect extent and returns the final rectangle.
    /// </summary>
    //JAVA: method is internalized
	RectangleF adjustWithEffectsInternal(RectangleF source)
    {
        float left = source.getLeft() - fetchEmuShapeAttrInPoints(ShapeAttr.DML_EFFECT_EXTENT_LEFT);
        float right = source.getRight() + fetchEmuShapeAttrInPoints(ShapeAttr.DML_EFFECT_EXTENT_RIGHT);
        float top = source.getTop() - fetchEmuShapeAttrInPoints(ShapeAttr.DML_EFFECT_EXTENT_TOP);
        float bottom = source.getBottom() + fetchEmuShapeAttrInPoints(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM);
        return RectangleF.fromLTRB(left, top, right, bottom);
    }

	//JAVA-added public wrapper for internalized method
	public java.awt.geom.Rectangle2D.Float adjustWithEffects(java.awt.geom.Rectangle2D.Float source)
	{
		return RectangleF.toJava(adjustWithEffectsInternal(RectangleF.fromJava(source)));
	}

    private float fetchEmuShapeAttrInPoints(int attr)
    {
        return (float)ConvertUtilCore.emuToPoint((/*int*/Integer)fetchShapeAttrInternal(attr));
    }

    /// <summary>
    /// Gets the shape type.
    /// </summary>
    public /*ShapeType*/int getShapeType() { return getGraphicData().getShapeType(); }

    /// <summary>
    /// Gets MarkupLanguage used for this graphic object.
    /// </summary>
    public /*ShapeMarkupLanguage*/byte getMarkupLanguage() { return mMarkupLanguage; }

    /// <summary>
    /// Gets the size of the shape in points.
    /// </summary>
    ///
    /// <javaMember type="method" name="java.awt.geom.Point2D.Float com.aspose.words.ShapeBase.getSizeInPoints()">
    /// <summary>
    /// Gets the size of the shape in points.
    /// </summary>
    /// <remarks>
    /// <p>Point2D.Float is used as return type because we need in float dimension values here.
    /// One should to assume that Point2D's <i>x == width</i> and <i>y == height</i>.</p>
    /// </remarks>
    /// </javaMember>
    //JAVA: property accessor is internalized
	/*SizeF*/long getSizeInPointsInternal() { return getBoundsInPointsInternal().getSize(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.geom.Point2D.Float getSizeInPoints() { return msSizeF.toJava(getSizeInPointsInternal()); }

    /// <summary>
    /// Switches the orientation of a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.FlipOrientation.None"/>.</p>
    /// </remarks>
    public /*FlipOrientation*/int getFlipOrientation() { return getGraphicData().getFlipOrientation(); }
    public void setFlipOrientation(/*FlipOrientation*/int value) { getGraphicData().setFlipOrientation(value); }

    /// <summary>
    /// Gets the orientation of parent (group) shape.
    /// </summary>
    /// <remarks>
    /// <p>If there is no parent shape, returns <see cref="Aspose.Words.Drawing.FlipOrientation.None"/>.</p>
    /// </remarks>
    /*FlipOrientation*/int getParentFlipOrientation()
    {
        GroupShape parentGroup = ms.as(getAncestor(NodeType.GROUP_SHAPE), GroupShape.class);
        return parentGroup == null ? FlipOrientation.NONE : parentGroup.getFlipOrientation();
    }

    /// <summary>
    /// Specifies relative to what the shape is positioned horizontally.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeRelativeHorizontalPosition.Column"/>.</p>
    /// <p>Has effect only for top level floating shapes.</p>
    /// </remarks>
    public /*RelativeHorizontalPosition*/int getRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)fetchShapeAttrInternal(ShapeAttr.RELATIVE_HORIZONTAL_POSITION); }
    public void setRelativeHorizontalPosition(/*RelativeHorizontalPosition*/int value) { setShapeAttrInternal(ShapeAttr.RELATIVE_HORIZONTAL_POSITION, value); }

    /// <summary>
    /// Specifies relative to what the shape is positioned vertically.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeRelativeVerticalPosition.Paragraph"/>.</p>
    /// <p>Has effect only for top level floating shapes.</p>
    /// </remarks>
    public /*RelativeVerticalPosition*/int getRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)fetchShapeAttrInternal(ShapeAttr.RELATIVE_VERTICAL_POSITION); }
    public void setRelativeVerticalPosition(/*RelativeVerticalPosition*/int value) { setShapeAttrInternal(ShapeAttr.RELATIVE_VERTICAL_POSITION, value); }

    /// <summary>
    /// Gets or sets the value of shape's relative size in horizontal direction.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.RelativeHorizontalSize"/>.</p>
    /// <p>Has effect only if <see cref="ShapeBase.WidthRelative"/> is set.</p>
    /// </remarks>
    public /*RelativeHorizontalSize*/int getRelativeHorizontalSize()
    {
        return (/*RelativeHorizontalSize*/Integer)fetchShapeAttrInternal(ShapeAttr.RELATIVE_WIDTH);
    }
    public void setRelativeHorizontalSize(/*RelativeHorizontalSize*/int value)
    {
        setShapeAttrInternal(ShapeAttr.RELATIVE_WIDTH, value);
    }

    /// <summary>
    /// Gets or sets the value of shape's relative size in vertical direction.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.RelativeVerticalSize.Margin"/>.</p>
    /// <p>Has effect only if <see cref="ShapeBase.HeightRelative"/> is set.</p>
    /// </remarks>
    public /*RelativeVerticalSize*/int getRelativeVerticalSize()
    {
        return (/*RelativeVerticalSize*/Integer)fetchShapeAttrInternal(ShapeAttr.RELATIVE_HEIGHT);
    }
    public void setRelativeVerticalSize(/*RelativeVerticalSize*/int value)
    {
        setShapeAttrInternal(ShapeAttr.RELATIVE_HEIGHT, value);
    }

    /// <summary>
    /// Gets or sets the value that represents shape's relative left position in percent.
    /// </summary>
    public float getLeftRelative()
    {
        return getShapePr().get(ShapeAttr.LEFT_PERCENT) == null ? 0f : ((float)(/*int*/Integer)getLeftPercent() / 10f);
    }
    public void setLeftRelative(float value)
    {
        if (value == 0)
            getShapePr().remove(ShapeAttr.LEFT_PERCENT);
        else
            setShapeAttrInternal(ShapeAttr.LEFT_PERCENT, (int)msMath.truncate(value * 10f));
    }

    /// <summary>
    /// Gets or sets the value that represents shape's relative top position in percent.
    /// </summary>
    public float getTopRelative()
    {
        return getShapePr().get(ShapeAttr.TOP_PERCENT) == null ? 0f : ((float)(/*int*/Integer)getTopPercent() / 10f);
    }
    public void setTopRelative(float value)
    {
        if (value == 0)
            getShapePr().remove(ShapeAttr.TOP_PERCENT);
        else
            setShapeAttrInternal(ShapeAttr.TOP_PERCENT, (int)msMath.truncate(value * 10f));
    }

    /// <summary>
    /// Gets or sets the value that represents the percentage of shape's relative height.
    /// </summary>
    public float getHeightRelative()
    {
        return getShapePr().get(ShapeAttr.HEIGHT_PERCENT) == null ? 0f : ((float)(/*int*/Integer)getHeightPercent() / 10f);
    }
    public void setHeightRelative(float value)
    {
        if (value < 0)
            throw new IllegalStateException("HeightRelative cannot be set to a negative value.");

        if (value == 0)
        {
            // MS Word VBA resets RelativeVerticalPosition when setting HeightRelative value to 0.
            getShapePr().remove(ShapeAttr.RELATIVE_HEIGHT);
            getShapePr().remove(ShapeAttr.HEIGHT_PERCENT);
        }
        else
        {
            setShapeAttrInternal(ShapeAttr.HEIGHT_PERCENT, (int)msMath.truncate(value * 10f));
            // MS Word VBA automatically sets RelativeVerticalSize to Margin value when setting HeightRelative to a valid value.
            if (getShapePr().get(ShapeAttr.RELATIVE_HEIGHT) == null)
                setRelativeVerticalSize(RelativeVerticalSize.MARGIN);
        }
    }

    /// <summary>
    /// Gets or sets the value that represents the percentage of shape's relative width.
    /// </summary>
    public float getWidthRelative()
    {
        return getShapePr().get(ShapeAttr.WIDTH_PERCENT) == null ? 0f : ((float)(/*int*/Integer)getWidthPercent() / 10f);
    }
    public void setWidthRelative(float value)
    {
        if (value < 0)
            throw new IllegalStateException("WidthRelative cannot be set to a negative value.");

        if (value == 0)
        {
            // MS Word VBA resets RelativeHorizontalSize when setting WidthRelative value to 0.
            getShapePr().remove(ShapeAttr.RELATIVE_WIDTH);
            getShapePr().remove(ShapeAttr.WIDTH_PERCENT);
        }
        else
        {
            setShapeAttrInternal(ShapeAttr.WIDTH_PERCENT, (int)msMath.truncate(value * 10f));
            // MS Word VBA automatically sets RelativeHorizontalSize to Margin value when setting WidthRelative to a valid value.
            if (getShapePr().get(ShapeAttr.RELATIVE_WIDTH) == null)
                setRelativeHorizontalSize(RelativeHorizontalSize.MARGIN);
        }
    }

    /// <summary>
    /// Specifies how the shape is positioned horizontally.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeHorizontalAlignment.None"/>.</p>
    /// <p>Has effect only for top level floating shapes.</p>
    /// </remarks>
    public /*HorizontalAlignment*/int getHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)fetchShapeAttrInternal(ShapeAttr.HORIZONTAL_ALIGNMENT); }
    public void setHorizontalAlignment(/*HorizontalAlignment*/int value) { setShapeAttrInternal(ShapeAttr.HORIZONTAL_ALIGNMENT, value); }

    /// <summary>
    /// Specifies how the shape is positioned vertically.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeVerticalAlignment.None"/>.</p>
    /// <p>Has effect only for top level floating shapes.</p>
    /// </remarks>
    public /*VerticalAlignment*/int getVerticalAlignment() { return (/*VerticalAlignment*/Integer)fetchShapeAttrInternal(ShapeAttr.VERTICAL_ALIGNMENT); }
    public void setVerticalAlignment(/*VerticalAlignment*/int value) { setShapeAttrInternal(ShapeAttr.VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Defines whether the shape is inline or floating. For floating shapes defines the wrapping mode for text around the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeWrapType.None"/>.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public /*WrapType*/int getWrapType() { return (/*WrapType*/Integer)fetchShapeAttrInternal(ShapeAttr.WRAP_TYPE); }
    public void setWrapType(/*WrapType*/int value) { setShapeAttrInternal(ShapeAttr.WRAP_TYPE, value); }

    /// <summary>
    /// Specifies how the text is wrapped around the shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="AsposeWrapSide.Both"/>.</p>
    /// <p>Has effect only for top level shapes.</p>
    /// </remarks>
    public /*WrapSide*/int getWrapSide() { return (/*WrapSide*/Integer)fetchShapeAttrInternal(ShapeAttr.WRAP_SIDE); }
    public void setWrapSide(/*WrapSide*/int value) { setShapeAttrInternal(ShapeAttr.WRAP_SIDE, value); }

    /// <summary>
    /// The coordinates at the top-left corner of the containing block of this shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is (0,0).</p>
    /// </remarks>
    //JAVA: property accessor is internalized
	/*Point*/long getCoordOriginInternal() { return msPoint.ctor(getCoordOriginX(), getCoordOriginY()); }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.Point getCoordOrigin() { return msPoint.toJava(getCoordOriginInternal()); }
    //JAVA: property accessor is internalized
	void setCoordOriginInternal(/*Point*/long value)
    {
        setCoordOriginX(msPoint.getX(value));
        setCoordOriginY(msPoint.getY(value));
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setCoordOrigin(java.awt.Point value) { setCoordOriginInternal(msPoint.fromJava(value)); }

    /// <summary>
    /// The width and height of the coordinate space inside the containing block of this shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is (1000, 1000).</p>
    /// </remarks>
    //JAVA: property accessor is internalized
	/*Size*/long getCoordSizeInternal() { return msSize.ctor(getCoordSizeWidth(), getCoordSizeHeight()); }
	//JAVA-added public wrapper for internalized property accessor
	public java.awt.Dimension getCoordSize() { return msSize.toJava(getCoordSizeInternal()); }
    //JAVA: property accessor is internalized
	void setCoordSizeInternal(/*Size*/long value)
    {
        // WORDSNET-13775 According to MSW behavior coordsize width and height can equals zero
        // but cannot be less than zero (it is true for VML, DML and for ODT shapes).
        // Zero width or height means this shape looks like vertical or horizontal line.
        if ((msSize.getWidth(value) < 0) || (msSize.getHeight(value) < 0))
        {
            throw new IllegalArgumentException("Local coordinate space size cannot be less than zero." + "\r\nParameter name: " + "value");
        }

        setCoordSizeSafe(value);
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setCoordSize(java.awt.Dimension value) { setCoordSizeInternal(msSize.fromJava(value)); }

    /// <summary>
    /// Provides access to the font formatting of this object.
    /// </summary>
    public Font getFont()
    {
        if (mFontCache == null)
            mFontCache = new Font(this, getDocument());
        return mFontCache;
    }

    /// <summary>
    /// Indicates that shape is a <see cref="SignatureLine"/>.
    /// </summary>
    public boolean isSignatureLine() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.IS_SIGNATURE_LINE); }

    /// <summary>
    /// Gets or sets a flag indicating whether the shape is displayed inside a table or outside of it.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>true</c>.</p>
    /// <p>Has effect only for top level shapes, the property <see cref="WrapType"/> of which is set to value
    /// other than <see cref="Inline"/>.</p>
    /// </remarks>
    public boolean isLayoutInCell() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.ALLOW_IN_CELL); }
    public void isLayoutInCell(boolean value) { setShapeAttrInternal(ShapeAttr.ALLOW_IN_CELL, value); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether the shape is visible.
    /// </summary>
    /// <remarks>
    /// The default value is <c>false</c>.
    /// </remarks>
    public boolean getHidden()
    {
        return getGraphicData().getHidden();
    }
    public void setHidden(boolean value)
    {
        getGraphicData().setHidden(value);
    }

    /// <summary>
    /// Gets IFill object for the shape.
    /// </summary>
    IFill getFillCore()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
        {
            if (mFillCore == null)
                mFillCore = new VmlFill();
        }
        else
        {
            mFillCore = ((IDmlCommonShapePrSource)getDmlNode()).getFill();
        }

        mFillCore.setParent(this);
        return mFillCore;
    }

    /// <summary>
    /// Defines locks for a shape.
    /// </summary>
    Locks getLocks()
    {
        if (mLocksCache == null)
            mLocksCache = new Locks(this);
        return mLocksCache;
    }

    int getCoordOriginX() { return getGraphicData().getCoordOriginX(); }
    void setCoordOriginX(int value) { getGraphicData().setCoordOriginX(value); }

    int getCoordOriginY() { return getGraphicData().getCoordOriginY(); }
    void setCoordOriginY(int value) { getGraphicData().setCoordOriginY(value); }

    /// <summary>
    /// Gets the unique shape identifier.
    /// </summary>
    int getId() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.SHAPE_ID); }
    void setId(int value) { setShapeAttrInternal(ShapeAttr.SHAPE_ID, value); }

    /// <summary>
    /// Returns <c>true</c> if this shape is an OLE object.
    /// </summary>
    boolean isOleObject() { return (getShapeType() == ShapeType.OLE_OBJECT); }

    /// <summary>
    /// Returns <c>true</c> if this shape is an ActiveX control.
    /// </summary>
    boolean isOleControl() { return (getShapeType() == ShapeType.OLE_CONTROL); }

    /// <summary>
    /// Returns <c>true</c> if this shape is an OLE object or ActiveX control.
    /// </summary>
    boolean isOle() { return (isOleObject() || isOleControl()); }

    /// <summary>
    /// Returns <c>true</c> if this shape is a standard horizontal rule.
    /// </summary>
    boolean isStandardHorizontalRule() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.HR_STANDARD); }

    /// <summary>
    /// A low level property used by RTF and DOC writers to find out whether the shape
    /// needs to be enclosed inside a SHAPE field when written as an inline shape.
    /// Returns <c>true</c> if the shape is not a picture, ole object/control, horizontal rule or word art.
    /// </summary>
    boolean isNeedDummyWhenInline() { return !(isImage() || isOle() || isHorizontalRule() || isWordArt() || isCustomShape()); }

    /// <summary>
    /// Returns <c>true</c> if this shape is a picture bullet.
    ///
    /// This property is important in DOC and RTF importers/exporters and it is automatically
    /// set when a shape is added to the list of picture bullets.
    /// In WML and DOCX this property does not seem to exist.
    /// In the model this property does not make much difference.
    /// </summary>
    boolean isPictureBullet() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.PICTURE_BULLET); }
    void isPictureBullet(boolean value) { setShapeAttrInternal(ShapeAttr.PICTURE_BULLET, value); }

    /// <summary>
    /// Gets the size of the shape in its native units. Top level shapes are in points. Child shapes are in parent coordinate system.
    /// </summary>
    /*SizeF*/long getSize() { return msSizeF.ctor((float)getWidth(), (float)getHeight()); }

    /// <summary>
    /// When not null, the shape is horizontally positioned using percentage relative to some object.
    /// The value is integer 1/10th percent.
    /// </summary>
    Object getLeftPercent() { return getDirectShapeAttrInternal(ShapeAttr.LEFT_PERCENT); }

    /// <summary>
    /// When not null, the shape is vertically positioned using percentage relative to some object.
    /// The value is integer 1/10th percent.
    /// </summary>
    Object getTopPercent() { return getDirectShapeAttrInternal(ShapeAttr.TOP_PERCENT); }

    /// <summary>
    /// When not null, the shape width is in percentage relative to some object.
    /// The value is integer 1/10th percent.
    /// </summary>
    Object getWidthPercent() { return getDirectShapeAttrInternal(ShapeAttr.WIDTH_PERCENT); }

    /// <summary>
    /// When not null, the shape height is in percentage relative to some object.
    /// The value is integer 1/10th percent.
    /// </summary>
    Object getHeightPercent() { return getDirectShapeAttrInternal(ShapeAttr.HEIGHT_PERCENT); }

    int getCoordSizeWidth() { return getGraphicData().getCoordSizeWidth(); }

    int getCoordSizeHeight() { return getGraphicData().getCoordSizeHeight(); }

    /// <summary>
    /// Gets the total rotation of this object up to the hierarchy root.
    /// </summary>
    double getTotalRotation()
    {
        double angle = 0.0;
        Node shapeNode = this;
        do
        {
            angle += ((ShapeBase)shapeNode).getRotation();
            shapeNode = shapeNode.getParentNode();
        }
        while ((shapeNode != null) && (shapeNode.getNodeType() == NodeType.GROUP_SHAPE));

        return angle;
    }

    /// <summary>
    /// Returns <c>true</c> if the shape has a hyperlink.
    /// </summary>
    boolean hasHyperlink() { return StringUtil.hasChars(getHRef()); }

    /// <summary>
    /// Returns <c>true</c> if shape should be included into HYPERLINK field.
    /// </summary>
    boolean isHyperlinkedInline() { return isInline() && hasHyperlink() && (isImage() || isOleObject()); }

    /// <summary>
    /// Gets the hyperlink part that is before the "#".
    /// </summary>
    String getHyperlinkAddress() { return UriUtil.getAddress(getHRef()); }

    /// <summary>
    /// Gets the hyperlink part that is after the "#" excluding the "#".
    /// </summary>
    String getHyperlinkSubAddress() { return UriUtil.getSubAddress(getHRef()); }

    /// <summary>
    /// Provides direct access to shape attributes.
    /// </summary>
    ShapePr getShapePr() { return mShapePr; }
    void setShapePr(ShapePr value) { mShapePr = value; }

    /// <summary>
    /// Provides direct access to the font formatting of the shape anchor character.
    /// These attributes only have effect for top level inline shapes.
    /// </summary>
    RunPr getRunPr() { return mRunPr; }
    void setRunPr(RunPr value) { mRunPr = value; }

    /// <summary>
    /// This should actually belong to Shape, but I have it on ShapeBase to avoid casting in some places.
    /// Indicates the type of the connector if this shape is a connector.
    /// </summary>
    /*ConnectorType*/int getConnectorType() { return (/*ConnectorType*/Integer)fetchShapeAttrInternal(ShapeAttr.CONNECTOR_TYPE); }
    void setConnectorType(/*ConnectorType*/int value) { setShapeAttrInternal(ShapeAttr.CONNECTOR_TYPE, value); }

    boolean isConnector() { return (getConnectorType() != ConnectorType.NONE); }

    /// <summary>
    /// This is a helper property for DOC file only.
    /// </summary>
    int getTxid() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TEXTBOX_TXID); }
    void setTxid(int value) { setShapeAttrInternal(ShapeAttr.TEXTBOX_TXID, value); }

    /// <summary>
    /// Gets or sets the id of the next shape in the chain of linked textboxes.
    /// This value is correct after loading and it is "corrected" during saving.
    /// Cloning or removing shapes might break chains in the model and document validator just "corrects" this before saving.
    /// </summary>
    int getTextboxNextShapeId() { return (/*int*/Integer)fetchShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID); }
    void setTextboxNextShapeId(int value) { setShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, value); }

    /// <summary>
    /// Gets InkData.
    /// </summary>
    byte[] getInkData() { return (byte[])getDirectShapeAttrInternal(ShapeAttr.INK_DATA); }

    /// <summary>
    /// Indicates that this Ink is annotation.
    /// </summary>
    boolean getInkAnnotation() { return (/*boolean*/Boolean)fetchShapeAttrInternal(ShapeAttr.INK_ANNOTATION); }

    /// <summary>
    /// Gets <see cref="IEmbeddedObject"/> object specifies OLE/ActiveX/OOXML embedded object stored in a document.
    /// </summary>
    IEmbeddedObject getEmbeddedObject() { return (IEmbeddedObject)fetchShapeAttrInternal(ShapeAttr.OLE_OBJECT); }

    /// <summary>
    /// A low level property used by RTF and DOC writers.
    /// Returns <c>true</c> when the PseudoInline attribute must be set in the output document.
    /// It is some obscure flag. The RTF doc says:
    /// The shape is pseudo-inline, meaning it behaves like an inline image as far as positioning goes,
    /// but has the features of shapes.
    /// </summary>
    boolean isPseudoInline() { return (isInline() && isNeedDummyWhenInline()); }

    /// <summary>
    /// Shows if the shape is a callout shape.
    /// </summary>
    boolean isCalloutShapeType()
    {
        switch (getShapeType())
        {
            case ShapeType.ACCENT_BORDER_CALLOUT_90:
            case ShapeType.BORDER_CALLOUT_90:
            case ShapeType.ACCENT_CALLOUT_90:
            case ShapeType.CALLOUT_90:
            case ShapeType.CLOUD_CALLOUT:
            case ShapeType.QUAD_ARROW_CALLOUT:
            case ShapeType.UP_DOWN_ARROW_CALLOUT:
            case ShapeType.LEFT_RIGHT_ARROW_CALLOUT:
            case ShapeType.DOWN_ARROW_CALLOUT:
            case ShapeType.UP_ARROW_CALLOUT:
            case ShapeType.RIGHT_ARROW_CALLOUT:
            case ShapeType.LEFT_ARROW_CALLOUT:
            case ShapeType.WEDGE_ELLIPSE_CALLOUT:
            case ShapeType.WEDGE_R_RECT_CALLOUT:
            case ShapeType.WEDGE_RECT_CALLOUT:
            case ShapeType.ACCENT_BORDER_CALLOUT_3:
            case ShapeType.ACCENT_BORDER_CALLOUT_2:
            case ShapeType.ACCENT_BORDER_CALLOUT_1:
            case ShapeType.BORDER_CALLOUT_3:
            case ShapeType.BORDER_CALLOUT_2:
            case ShapeType.BORDER_CALLOUT_1:
            case ShapeType.ACCENT_CALLOUT_3:
            case ShapeType.ACCENT_CALLOUT_2:
            case ShapeType.ACCENT_CALLOUT_1:
            case ShapeType.CALLOUT_3:
            case ShapeType.CALLOUT_2:
            case ShapeType.CALLOUT_1:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Shows if the shape is a connector shape.
    /// </summary>
    boolean isConnectorShapeType()
    {
        switch (getShapeType())
        {
            case ShapeType.BENT_CONNECTOR_2:
            case ShapeType.BENT_CONNECTOR_3:
            case ShapeType.BENT_CONNECTOR_4:
            case ShapeType.BENT_CONNECTOR_5:
            case ShapeType.CURVED_CONNECTOR_2:
            case ShapeType.CURVED_CONNECTOR_3:
            case ShapeType.CURVED_CONNECTOR_4:
            case ShapeType.CURVED_CONNECTOR_5:
            case ShapeType.FLOW_CHART_CONNECTOR:
            case ShapeType.FLOW_CHART_OFFPAGE_CONNECTOR:
            case ShapeType.STRAIGHT_CONNECTOR_1:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Shows if the shape is a bracket shape.
    /// </summary>
    boolean isBracket()
    {
        switch (getShapeType())
        {
            case ShapeType.LEFT_BRACKET:
            case ShapeType.RIGHT_BRACKET:
            case ShapeType.BRACKET_PAIR:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Shows if the shape is a brace shape.
    /// </summary>
    boolean isBrace()
    {
        switch (getShapeType())
        {
            case ShapeType.LEFT_BRACE:
            case ShapeType.RIGHT_BRACE:
            case ShapeType.BRACE_PAIR:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Checks if DmlNode is <see cref="DmlShape"/>.
    /// </summary>
    boolean isDmlShape()
    {
        return (getDmlShape() != null);
    }

    /// <summary>
    /// Gets the layout flow (i.e. text orientation) of the shape's textbox.
    /// </summary>
    /*LayoutFlow*/int getLayoutFlow()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            return (getDmlShape() != null)
                ? DmlUtilCore.dmlToTextVerticalType(getDmlShape().getTextBodyPr().getTextOrientation(),
                    getDmlShape().getNormalEastAsianFlow())
                : LayoutFlow.HORIZONTAL;
        }

        return (/*LayoutFlow*/Integer)getShapePr().fetchAttr(ShapeAttr.TEXTBOX_LAYOUT_FLOW);
    }

    //>>>>>>>> #region  Linked textboxes attributes.

    /// <summary>
    /// Integer min value means id is not set.
    /// </summary>
    int getTextboxId() { return mTextboxId; }
    void setTextboxId(int value) { mTextboxId = value; }

    /// <summary>
    /// Integer min value means id is not set.
    /// </summary>
    int getLinkedTextboxId() { return mLinkedTextboxId; }
    void setLinkedTextboxId(int value) { mLinkedTextboxId = value; }

    /// <summary>
    /// Ordinal number of textbox in linked textbox sequence.
    /// </summary>
    int getLinkedTextboxSeq() { return mLinkedTextboxSeq; }
    void setLinkedTextboxSeq(int value) { mLinkedTextboxSeq = value; }

    /// <summary>
    /// Returns <c>true</c> if this shape has textbox (including linked).
    /// </summary>
    boolean hasTextbox()
    {
        if ((mTextboxId > 0) || (mLinkedTextboxId > 0))
            return true;

        Node child = getFirstNonAnnotationChild();
        return (child != null) && (child.getNodeLevel() == NodeLevel.BLOCK);
    }


    //<<<<<<<< #endregion  Linked textboxes attributes.

    Theme getDocumentTheme() { return getDocument().getThemeInternal(); }

    /// <summary>
    /// Shortcut for AlternateContent.FallBack shape.
    /// </summary>
    ShapeBase getFallbackShape()
    {
        return (getFallBack() != null) ? ms.as(getFallBack().getFirstChild(), ShapeBase.class) : null;
    }

    /// <summary>
    /// Indicates that this shape's media can be rendered.
    /// </summary>
    /// <remarks>
    /// WORDSNET-8002 It appears that DrawingML object can contain not only images
    /// but any media file such as QuickTime movie in the problematic file.
    /// Avoid to pass such DrawingML into layout engine.
    /// </remarks>
    boolean getSupportRendering() throws Exception
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.DML && getNodeType() == NodeType.SHAPE)
        {
            ImageData imageData = ((Shape)this).getImageData();
            if (imageData.hasImageBytes())
                return !ImageUtil.isMov(imageData.getImageBytes());
        }

        return true;
    }

    /// <summary>
    /// Indicates whether the shape bounds should be taken into account, when calculating the width of a table cell.
    /// </summary>
    /// <remarks>
    /// The shape sizes in AW and in MS Word for some shapes are different. If these shapes are placed in a table cell,
    /// and the AutoFit property is set for the cell, discrepancies in the shape size can cause incorrect calculation
    /// of the table column width. To avoid this, we use this method, which selects only those shapes whose size is the
    /// same in AW and in MS Word. As discrepancies in shape sizes are eliminated, this property will change.
    /// </remarks>
    /// <returns>"True" if shape bounds are taken into account, otherwise "false"</returns>
    boolean isGridCalculationSupported()
    {
        if (getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return true;

        DmlShapeBase dmlShapeBase = ms.as(getDmlNode(), DmlShapeBase.class);
        return (dmlShapeBase != null) && ("rect".equals(dmlShapeBase.getGeometry().getPresetName())) && isEffectExtentEmpty();
    }

    /// <summary>
    /// Returns user shape anchor type. Depending on the type,
    /// <see cref="From"/> and <see cref="To"/> properties must be interpreted differently.
    /// </summary>
    /*DmlChartUserShapeAnchorType*/int getAnchorType() { return mAnchorType; }
    void setAnchorType(/*DmlChartUserShapeAnchorType*/int value) { mAnchorType = value; }

    /// <summary>
    /// The from element specifies the top left corner of the shape bounding box
    /// in a RTL(right-to-left) implementation.
    /// </summary>
    /*PointF*/long getFrom() { return mFrom; }
    void setFrom(/*PointF*/long value) { mFrom = value; }

    /// <summary>
    /// The to element then specifies the bottom right corner of the shape bounding box
    /// in a RTL(right-to-left) implementation and thus the size of the shape.
    /// </summary>
    /*PointF*/long getTo() { return mTo; }
    void setTo(/*PointF*/long value) { mTo = value; }

    /// <summary>
    /// Dml node that represents graphics data of the Dml shape.
    /// </summary>
    DmlNode getDmlNode() { return mDmlNode; }
    void setDmlNode(DmlNode value)
    {
        if (value != null)
            value.setDrawingML(this);
        mDmlNode = value;
        mGraphicData = mDmlNode;
    }

    /// <summary>
    /// Wrapper for all common public properties for Vml and Dml shapes which have different implementation.
    /// </summary>
    Graphic getGraphicData()
    {
        if (mGraphicData == null)
            mGraphicData = new VmlNode(this);

        return mGraphicData;
    }

    /// <summary>
    /// True if shape contains properties that conforms to
    /// [MS-ODRAWXML]: Office Drawing Extensions to Office Open XML Structure.
    /// </summary>
    boolean hasDrawingExtensions()
    {
        return ((getShapePr().contains(ShapeAttr.RELATIVE_WIDTH)) ||
                (getShapePr().contains(ShapeAttr.RELATIVE_HEIGHT)));
    }

    /// <summary>
    /// True if this shape is <see cref="FallbackShape"/>.
    /// </summary>
    boolean isFallback() { return mIsFallback; }
    void isFallback(boolean value) { mIsFallback = value; }

    /// <summary>
    /// Effective <see cref="CoordSize"/> for rendering purposes that has zero dimensions
    /// replaced with bounding rectangle shape path dimensions. If, in turn, it has width
    /// or height equals to zero again, then it is replaced with 1.
    /// </summary>
    /*Size*/long getEffectiveCoordSize()
    {
        if ((getCoordSizeWidth() > 0) && (getCoordSizeHeight() > 0))
            return getCoordSizeInternal();

        if (msSize.isEmpty(mEffectiveCoordSize))
        {
            // Gets the bounding rectangle.
            RectangleF boundingRect = getPathBoundingRect();

            // If there is no Path in this shape or it has zero size in one of
            // the bounding rectangle dimensions, then assume rectangle is 1x1
            // to allow Layout work properly.
            if (boundingRect.isEmpty())
                boundingRect = new RectangleF(0f, 0f, 1f, 1f);

            int width = (getCoordSizeWidth() <= 0) ? (int)boundingRect.getWidth() : getCoordSizeWidth();
            int height = (getCoordSizeHeight() <= 0) ? (int)boundingRect.getHeight() : getCoordSizeHeight();
            mEffectiveCoordSize = msSize.ctor(width, height);
        }

        return mEffectiveCoordSize;
    }

    /// <summary>
    /// Effective <see cref="CoordSizeWidth"/> for rendering purposes that has zero value
    /// replaced with bounding rectangle shape path width. If, in turn, it has zero value,
    /// then returns 1.
    /// </summary>
    int getEffectiveCoordSizeWidth() { return msSize.getWidth(getEffectiveCoordSize()); }

    /// <summary>
    /// Effective <see cref="CoordSizeHeight"/> for rendering purposes that has zero value
    /// replaced with bounding rectangle shape path height. If, in turn, it has zero value,
    /// then returns 1.
    /// </summary>
    int getEffectiveCoordSizeHeight() { return msSize.getHeight(getEffectiveCoordSize()); }

    DmlShape getDmlShape()
    {
        if (mDmlShape != null)
            return mDmlShape;

        mDmlShape = ms.as(getDmlNode(), DmlShape.class);
        return mDmlShape;
    }

    /// <summary>
    /// Returns <c>true</c> if shape can have fill image.
    /// </summary>
    boolean canHaveFillImage()
    {
        return (getShapeType() != ShapeType.IMAGE) &&
            (getShapeType() != ShapeType.CUSTOM_SHAPE) &&
            (getShapeType() != ShapeType.NON_PRIMITIVE) &&
            !isOle();
    }

    boolean isWatermark()
    {
        if (getMarkupLanguage() != ShapeMarkupLanguage.VML)
            return false;

        if (canBeTextWatermark() || canBeImageWatermark())
        {
            HeaderFooter headerFooter = ms.as(getStoryAncestor(NodeType.HEADER_FOOTER), HeaderFooter.class);
            return (headerFooter != null) && headerFooter.isHeader();
        }

        return false;
    }

    boolean canBeTextWatermark() { return getName().contains("PowerPlusWaterMarkObject") && isWordArt(); }

    boolean canBeImageWatermark() { return (getName().contains("WordPictureWatermark") && isImage()); }

    /// <summary>
    /// Gets bounds for shapes with relative sizes. The value is used only for rendering.
    /// </summary>
    /*SizeF*/long getBoundsForRelativeSize() { return mBoundsForRelativeSize; }

    boolean isInLockedCanvas()
    {
        ShapeBase topShape = getTopLevelParentShape();
        return (topShape.getDmlNode() != null) && (topShape.getDmlNode().getDmlNodeType() == DmlNodeType.LOCKED_CANVAS);
    }

    private boolean isCustomShape() { return (getShapeType() == ShapeType.CUSTOM_SHAPE); }

    /// <summary>
    /// When angle is between 45 and 135 degrees or between 225 and 315 degrees, MS Word switches relative dimensions.
    /// </summary>
    private boolean getSwitchPercentDimensions()
    {
        double rotation = MathUtil.normalizeAngle(getRotation());
        return ((rotation >= 45 && rotation < 135) || (rotation >= 225 && rotation < 315));
    }

    /// <summary>
    /// Get "Fallback" of the "AlternateContent" if it exists.
    /// </summary>
    private CompositeNode getFallBack()
    {
        AlternateContent ac = getRunPr().getAlternateContent();
        return (ac != null) && (ac.getFallBack() != null) ? ac.getFallBack() : null;
    }

    /// <summary>
    /// 3" in points - used in case we cannot determine shape size for the image shape.
    /// </summary>
    static double DefaultShapeSize = 216.0;

    /// <summary>
    /// This is the starting shape number for shapes in the main text of a Word document.
    /// </summary>
    static final int MAIN_DRAWING_PATRIARCH_SHAPE_ID = 0x0400;

    /// <summary>
    /// MS Word allocates shape ids in clusters of 1024 shapes, we have to honor this during writing.
    /// </summary>
    static final int SHAPE_ID_CLUSTER_SIZE = 0x0400;

    /// <summary>
    /// This seems to be the value that MS Word uses when shape size is invalid.
    /// </summary>
    static final double INVALID_SHAPE_SIZE_DEFAULT = 50.25;

    private ShapePr mShapePr = new ShapePr();

    /// <summary>
    /// Since any shape can become a top-level shape, we have to have run attributes.
    /// </summary>
    private RunPr mRunPr = new RunPr();

    private DmlShape mDmlShape;
    private Locks mLocksCache;
    private Font mFontCache;
    private DmlNode mDmlNode;
    private Graphic mGraphicData;
    private /*DmlChartUserShapeAnchorType*/int mAnchorType;
    private /*PointF*/long mFrom = msPointF.Empty;
    private /*PointF*/long mTo = msPointF.Empty;
    private /*final*/ /*ShapeMarkupLanguage*/byte mMarkupLanguage;
    private int mTextboxId;
    private int mLinkedTextboxId;
    private int mLinkedTextboxSeq;
    private long mHashCodeCache;

    /// <summary>
    /// Default value which specifies that position of shape is not relative. See [MS-ODRAW] 2.3.5.3 pctHorizPos.
    /// </summary>
    private static final int DEFAULT_PERCENT_VALUE = -10001;      /* 0xFFFFD8EF */

    private boolean mIsFallback;

    private /*Size*/long mEffectiveCoordSize = msSize.ctor(0, 0);
    private /*SizeF*/long mBoundsForRelativeSize = msSizeF.ctor(0f, 0f);

    private Fill mFill;
    private IFill mFillCore;
    private ShadowFormat mShadow;
    private GlowFormat mGlowFormat;
    private ReflectionFormat mReflectionFormat;
    private SoftEdgeFormat mSoftEdgeFormat;

    private static final String INVALID_ACTION = "Object doesn't support this action.";
}

