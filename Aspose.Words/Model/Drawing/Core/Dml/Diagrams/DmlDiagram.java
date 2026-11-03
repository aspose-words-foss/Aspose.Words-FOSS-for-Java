// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents DML diagram.
/// </summary>
class DmlDiagram extends DmlNode implements IDmlCommonShapePrSource
{
    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return DmlNodeType.DIAGRAM; }

    DmlDiagramLayout getLayout() { return mLayout; }
    void setLayout(DmlDiagramLayout value) { mLayout = value; }

    DmlDiagramDataModel getData() { return mData; }
    void setData(DmlDiagramDataModel value) { mData = value; }

    DmlDiagramStyleDefinition getStyleDefinition() { return mStyleDefinition; }
    void setStyleDefinition(DmlDiagramStyleDefinition value) { mStyleDefinition = value; }

    DmlDiagramColorTransform getColorTransformDefinition() { return mColorTransformDefinition; }
    void setColorTransformDefinition(DmlDiagramColorTransform value) { mColorTransformDefinition = value; }

    //>>>>>>>> #region  IDmlCommonShapePrSource implementation

    public DmlFill getFill() { return mData.getBackground().getFill(); }
    public void setFill(DmlFill value) { mData.getBackground().setFill(value); }

    public DmlOutline getOutline() { return mData.getWholeFormatting().getOutline(); }
    public void setOutline(DmlOutline value) { mData.getWholeFormatting().setOutline(value); }

    public DmlShapeStyle getStyle()
    {
        // According to spec ShapeStyle is not supported by DmlDiagram, null is always returned.
        return null;
    }
    public void setStyle(DmlShapeStyle value)
    {
        // Do nothing.
    }

    //<<<<<<<< #endregion 

    public /*override*/ String getAlternativeText()
    {
        // WORDSNET-14547 DML diagram object has not non visual shape properties.
        // Non-visual properties moves to graphic frame after grouping shapes.
        return getDml().isTopLevel() ? super.getAlternativeText() : "";
    }
    public /*override*/ void setAlternativeText(String value)
    {
        if (getDml().isTopLevel())
            super.setAlternativeText(value);
    }

    public /*override*/ String getTitle()
    {
        return getDml().isTopLevel() ? super.getTitle() : "";
    }
    public /*override*/ void setTitle(String value)
    {
        if (getDml().isTopLevel())
            super.setTitle(value);
    }

    public /*override*/ String getName()
    {
        return getDml().isTopLevel() ? super.getName() : "";
    }
    public /*override*/ void setName(String value)
    {
        if (getDml().isTopLevel())
            super.setName(value);
    }

    /*override*/ boolean getHidden() { return getDml().isTopLevel() && super.getHidden(); }
    /*override*/ void setHidden(boolean value)
    {
        if (getDml().isTopLevel())
            super.setHidden(value);
    }

    /*override*/ boolean getAspectRatioLocked()
    {
        return getDml().isTopLevel() && super.getAspectRatioLocked();
    }
    /*override*/ void setAspectRatioLocked(boolean value)
    {
        if (getDml().isTopLevel())
            super.setAspectRatioLocked(value);
    }

    /// <summary>
    /// Flag for enabling SmartArt cold rendering logging.
    /// </summary>
    boolean getEnableColdRenderingLogging() { return mEnableColdRenderingLogging; }
    void setEnableColdRenderingLogging(boolean value) { mEnableColdRenderingLogging = value; }

    private DmlDiagramLayout mLayout;
    private DmlDiagramDataModel mData;
    private DmlDiagramColorTransform mColorTransformDefinition;
    private DmlDiagramStyleDefinition mStyleDefinition;
    private boolean mEnableColdRenderingLogging;
}

