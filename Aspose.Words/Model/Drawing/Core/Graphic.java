// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/10/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msSize;
import com.aspose.StringUtil;
import java.awt.Color;


/// <summary>
/// Base class to define all common public properties for Vml and Dml shapes which have different implementation.
/// </summary>
abstract class Graphic
{
    void setCoordSizeSafe(/*Size*/long value)
    {
        setCoordSizeWidthSafe(msSize.getWidth(value));
        setCoordSizeHeightSafe(msSize.getHeight(value));
    }

    abstract void setCoordSizeWidthSafe(int width);
    abstract void setCoordSizeHeightSafe(int height);

    abstract void setWidthCore(double value, boolean isThrow);
    abstract void setHeightCore(double value, boolean isThrow);

    /// <summary>
    /// Returns true if this shape is a horizontal rule.
    /// </summary>
    public abstract boolean isHorizontalRule();

    /// <summary>
    /// Returns true if this shape is a WordArt object.
    /// </summary>
    public abstract boolean isWordArt();

    public abstract /*ShapeType*/int getShapeType();

    /// <summary>
    /// Provides access to the OLE data of a shape. For a shape that is not an OLE object or ActiveX control, returns <c>null</c>.
    /// </summary>
    public abstract OleFormat getOleFormat();

    /// <summary>
    /// Switches the orientation of a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.FlipOrientation.None"/>.</p>
    /// </remarks>
    public abstract /*FlipOrientation*/int getFlipOrientation(); public abstract void setFlipOrientation(/*FlipOrientation*/int value);

    /// <summary>
    /// Defines the angle (in degrees) that a shape is rotated.
    /// Positive value corresponds to clockwise rotation angle.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 0.</p>
    /// </remarks>
    public abstract double getRotation(); public abstract void setRotation(double value);

    /// <summary>
    /// Provides access to adjustment raw values of a shape.
    /// For a shape that is not contains any adjustment raw values, returns empty collection.
    /// </summary>
    public abstract AdjustmentCollection getAdjustments();

    /// <summary>
    /// Defines the text of the text path (of a WordArt object).
    /// </summary>
    public abstract TextPath getTextPath();

    /// <summary>
    /// Returns true if an extrusion effect is enabled.
    /// </summary>
    public abstract boolean getExtrusionEnabled();

    /// <summary>
    /// Gets or sets the optional shape name.
    /// </summary>
    public abstract String getName(); public abstract void setName(String value);

    /// <summary>
    /// Defines alternative text (description) to be displayed instead of a graphic.
    /// </summary>
    public abstract String getAlternativeText(); public abstract void setAlternativeText(String value);

    /// <summary>
    /// Gets or sets the flag that specifies whether the shape is decorative in the document or not.
    /// </summary>
    public abstract boolean getDecorative(); public abstract void setDecorative(boolean value);

    /// <summary>
    /// Defines the title (caption) of the current shape object.
    /// </summary>
    public abstract String getTitle(); public abstract void setTitle(String value);

    /// <summary>
    /// Gets or sets the flag the specifies whether the shape is visible in the document or not.
    /// </summary>
    abstract boolean getHidden(); abstract void setHidden(boolean value);

    /// <summary>
    /// Returns true if a shadow effect is enabled.
    /// </summary>
    public abstract boolean getShadowEnabled();

    abstract int getCoordSizeWidth();

    abstract int getCoordSizeHeight();

    abstract int getCoordOriginX(); abstract void setCoordOriginX(int value);

    abstract int getCoordOriginY(); abstract void setCoordOriginY(int value);

    abstract boolean getAspectRatioLocked(); abstract void setAspectRatioLocked(boolean value);

    /// <summary>
    /// Provides access to the properties of the horizontal rule shape.
    /// </summary>
    abstract HorizontalRule getHorizontalRule();

    // ImageData common attributes.
    public abstract double getBrightness(); public abstract void setBrightness(double value);
    public abstract double getContrast(); public abstract void setContrast(double value);
    public abstract boolean getBiLevel(); public abstract void setBiLevel(boolean value);
    public abstract boolean getGrayScale(); public abstract void setGrayScale(boolean value);

    public abstract double getCropTop(); public abstract void setCropTop(double value);
    public abstract double getCropBottom(); public abstract void setCropBottom(double value);
    public abstract double getCropLeft(); public abstract void setCropLeft(double value);
    public abstract double getCropRight(); public abstract void setCropRight(double value);

    // TextBox common attributes.
    public abstract double getInternalMarginLeft(); public abstract void setInternalMarginLeft(double value);
    public abstract double getInternalMarginRight(); public abstract void setInternalMarginRight(double value);
    public abstract double getInternalMarginTop(); public abstract void setInternalMarginTop(double value);
    public abstract double getInternalMarginBottom(); public abstract void setInternalMarginBottom(double value);

    public abstract boolean getFitShapeToText(); public abstract void setFitShapeToText(boolean value);
    public abstract /*LayoutFlow*/int getLayoutFlow(); public abstract void setLayoutFlow(/*LayoutFlow*/int value);
    public abstract /*TextBoxWrapMode*/int getTextBoxWrapMode(); public abstract void setTextBoxWrapMode(/*TextBoxWrapMode*/int value);
    abstract /*TextBoxAnchor*/int getTextBoxAnchor(); abstract void setTextBoxAnchor(/*TextBoxAnchor*/int value);

    /// <summary>
    /// Gets or sets a boolean value indicating either text of the TextBox should not rotate when the shape is rotated.
    /// </summary>
    abstract boolean getTextBoxNoTextRotation(); abstract void setTextBoxNoTextRotation(boolean value);

    /*virtual*/ void setHRef(String href)
    {
        if (StringUtil.hasChars(href))
            mShape.setShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS, href);
        else
            mShape.removeShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS);
    }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Drawing.ShadowType"/> object for a shape.
    /// </summary>
    abstract /*ShadowType*/int getShadowType(); abstract void setShadowType(/*ShadowType*/int value);

    /// <summary>
    /// Removes shadow effect from a shape.
    /// </summary>
    abstract void removeShadow();

    /// <summary>
    /// Gets or sets color of a shadow effect.
    /// </summary>
    abstract Color getShadowColor(); abstract void setShadowColor(Color value);

    /// <summary>
    /// Gets or sets transparency of a shadow effect.
    /// </summary>
    abstract double getShadowTransparency(); abstract void setShadowTransparency(double value);


    protected ShapeBase mShape;
}

