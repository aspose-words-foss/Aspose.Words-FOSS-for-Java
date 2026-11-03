// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ConvertUtilCore;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Provides access to the chart title properties.
/// </summary>
/// <dev>
/// 5.7.2.211 title (Title) element.
/// </dev>
class DmlChartTitle implements IDmlExtensionListSource, IChartItemTextProperties, IChartFormatSource, Cloneable
{
    DmlChartTitle(IDmlChartTitleHolder chartTitleHolder)
    {
        mTitleHolder = chartTitleHolder;
    }

    /*virtual*/ DmlChartTitle deepClone()
    {
        DmlChartTitle lhs = (DmlChartTitle)memberwiseClone();

        copyComplexPropertiesTo(lhs);

        return lhs;
    }

    /// <summary>
    /// Sets the holder of this title.
    /// </summary>
    void setTitleHolder(IDmlChartTitleHolder value)
    {
        mTitleHolder = value;
    }

    /// <summary>
    /// Copies complex properties to the specified chart title instance.
    /// </summary>
    protected void copyComplexPropertiesTo(DmlChartTitle destination)
    {
        if (mSpPr != null)
            destination.mSpPr = mSpPr.deepClone();

        if (mTx != null)
            destination.mTx = mTx.deepClone();

        if (mTxPr != null)
            destination.mTxPr = mTxPr.deepClone();

        if (mLayout != null)
            destination.mLayout = mLayout.deepClone();

        if (mExtensions != null)
            destination.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);
    }

    /// <summary>
    /// Ensures that the title format is initialized.
    /// </summary>
    void ensureInitialized()
    {
        if ((mTx != null) || (getTxPr().getParagraphs().size() > 0))
            return;

        getTxPr().ensureParagraphExists();
        getTxPr().getFirstParagraph().getProperties().setDefaultRunProperties(getDefaultRunProperties());
        getTxPr().getFirstParagraph().getProperties().hasDefaultRunProperties(true);
        mTitleHolder.getChartSpace().applyChartStyle(getTxPr(), mTitleHolder.getStyleItem());
    }

    /// <summary>
    /// Creates and assigns <see cref="Tx"/> and its <see cref="DmlChartTx.RichText"/> property if they are not
    /// defined yet.
    /// </summary>
    private void ensureTxMinimum()
    {
        if (mTx == null)
            mTx = new DmlChartTx();

        if (mTx.getRichText() == null)
            mTx.setRichText(createEmptyReachTx());
    }

    /// <summary>
    /// Creates empty reach text with one paragraph and one run.
    /// Applies default run properties.
    /// </summary>
    private DmlTextBody createEmptyReachTx()
    {
        // Create new empty chart text.
        DmlTextBody textBody = new DmlTextBody();

        // Assign existing TxPr to preserve text formatting.
        if ((mTxPr != null) && !mTxPr.getBodyPr().isEmpty())
            textBody.setProperties(mTxPr.getBodyPr().deepClone());

        // There is an issue with Word 2016 charts if these properties are not set.
        if (isChartEx())
            setTitleAnchor(textBody.getProperties());

        DmlRunProperties runProperties = getFontRunPropertiesSource().getGeneralRunProperties();
        if ((runProperties == null) || runProperties.isEmpty())
            runProperties = !getTxPr().isEmpty() ? getTxPr().getRunPr() : getDefaultRunProperties();

        DmlParagraph paragraph = new DmlParagraph(null, null);

        paragraph.getProperties().hasDefaultRunProperties(true);
        paragraph.getProperties().setDefaultRunProperties(runProperties.deepClone());

        DmlRun run = new DmlRun();
        paragraph.addElement(run);
        textBody.addParagraph(paragraph);

        return textBody;
    }

    /// <summary>
    /// Sets the necessary text body anchor properties, which is required for Word 2016 charts.
    /// </summary>
    private static void setTitleAnchor(DmlTextBodyProperties textBodyProperties)
    {
        textBodyProperties.setAnchor(DmlTextAnchoringType.CENTER);
        textBodyProperties.setAnchorCenter(true);
    }

    /// <summary>
    /// Gets default run properties for the title.
    /// </summary>
    private DmlRunProperties getDefaultRunProperties()
    {
        if (mTitleHolder.getChartSpace().getDmlChartStyle() == null)
        {
            DmlRunProperties runProperties = new DmlRunProperties();
            runProperties.setFontSize(DmlTextPoints.fromPoints(mTitleHolder.getDefaultDisplayedFontSize()));
            runProperties.setBold(false);
            runProperties.setItalics(false);
            runProperties.setUnderline(Underline.NONE);
            runProperties.setStrikethrough(DmlTextStrike.NO);
            runProperties.setKerning(new DmlTextPoints(isChartEx() ? 0 : 1200));
            runProperties.setSpacing(new DmlTextPoints(0));
            runProperties.setBaseline(0.0);

            DmlSchemeColor schemeColor = new DmlSchemeColor(ThemeColor.TEXT_1);
            DmlLuminanceModulation lMod = new DmlLuminanceModulation();
            lMod.setValue(0.65);
            DmlLuminanceOffset lOff = new DmlLuminanceOffset();
            lOff.setValue(0.35);
            msArrayList.add(schemeColor.getColorModifiers(), lMod);
            msArrayList.add(schemeColor.getColorModifiers(), lOff);
            runProperties.setFill(new DmlSolidFill(schemeColor));

            return runProperties;
        }
        else
        {
            DmlChartTxPr txPr = new DmlChartTxPr();
            mTitleHolder.getChartSpace().applyChartStyle(txPr, mTitleHolder.getStyleItem());
            return txPr.getRunPr();
        }
    }

    /// <summary>
    /// Gets properties of the first element of the specified text body, if the element exists, or run properties
    /// of the first paragraph if it doesn't. These are default run properties of the title.
    /// </summary>
    private static DmlRunProperties getFirstElementProperties(DmlTextBody textBody)
    {
        for (DmlParagraph paragraph : textBody.getParagraphs())
        {
            for (DmlParagraphTextElementBase element : paragraph.getElements())
                return element.getRunProperties();
        }

        return textBody.getParagraphs().get(0).getEndParagraphRunProperties();
    }

    //>>>>>>>> #region  IChartItemTextProperties members

    public String /*IChartItemTextProperties.*/generateItemText()
    {
        return mTitleHolder.getDefaultTitleText();
    }

    public Object /*IChartItemTextProperties.*/fetchSpecialDefaultRunPropertyValue(int key)
    {
        if (key == FontAttr.SIZE)
            return ConvertUtilCore.pointToHalfPoint(mTitleHolder.getDefaultFontSize());

        if ((key == FontAttr.BOLD) && !isChartEx())
            return AttrBoolEx.TRUE;

        return null;
    }

    public Object /*IChartItemTextProperties.*/getRelativePropertyValue(int key, Object value)
    {
        if ((value != null) && ((key == FontAttr.SIZE) || (key == FontAttr.SIZE_BI)))
            return mTitleHolder.getRelativeFontSize((/*int*/Integer)value);
        else
            return value;
    }

    public DmlChartTx /*IChartItemTextProperties.*/getItemTx() { return getTx(); }

    public DmlChartTxPr /*IChartItemTextProperties.*/getItemTxPr() { return getTxPr(); }
    public void /*IChartItemTextProperties.*/setItemTxPr(DmlChartTxPr value) { mTxPr = value; }

    public DmlChartSpPr /*IChartItemTextProperties.*/getItemSpPr() { return getSpPr(); }

    public DmlChartTxPr /*IChartItemTextProperties.*/getCollectionTxPr() { return null; }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets the text of the chart title.
    /// If <c>null</c> or empty value is specified, auto generated title will be shown.
    /// </summary>
    String getText()
    {
        return (getTx() == null) ? mTitleHolder.getDefaultTitleText() : getTx().getText();
    }
    void setText(String value)
    {
        // If no text is specified, the auto generated title will be shown.
        if (!StringUtil.hasChars(value))
        {
            if ((mTx == null) && ((mTxPr == null) || mTxPr.isEmpty()))
                return;

            // Keep the existing text format: just set the default title text.
            value = mTitleHolder.getDefaultTitleText();
        }

        ensureTxMinimum();

        // Get properties of the first run or of paragraph end if there are no runs.
        DmlRunProperties runProperties = getFirstElementProperties(getTx().getRichText());

        // Remove all dml paragraphs except first one, to preserve formatting.
        ArrayList<DmlParagraph> paragraphs = getTx().getRichText().getParagraphs();
        while (paragraphs.size() != 1)
            paragraphs.remove(paragraphs.size() - 1);

        DmlParagraph firstPara = paragraphs.get(0);
        firstPara.getElements().clear();

        DmlRun run = new DmlRun(runProperties.deepClone());
        firstPara.addElement(run);

        getTx().setStrRef(null);
        run.setText(value);
    }

    /// <summary>
    /// Gets or sets the orientation of the title text.
    /// </summary>
    public /*ShapeTextOrientation*/int getOrientation() { return getBodyPr().getTextOrientation(); }
    public void setOrientation(/*ShapeTextOrientation*/int value) { getBodyPrForUpdate().setTextOrientation(value); }

    /// <summary>
    /// Gets or sets the rotation of the title in degrees.
    /// </summary>
    /// <remarks>
    /// The range of acceptable values is from -180 to 180 inclusive.
    /// </remarks>
    public int getRotation() { return (int)msMath.round(getBodyPr().getRotation().getValueInDegrees()); }
    public void setRotation(int value) { getBodyPrForUpdate().setRotation(DmlAngle.fromDegrees(value)); }

    /// <summary>
    /// Gets or sets the side position of the chart title.
    /// </summary>
    /// <remarks>
    /// Default side position is top.
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </remarks>
    /*SidePosition*/int getSidePosition() { return mSidePosition; }
    void setSidePosition(/*SidePosition*/int value) { mSidePosition = value; }

    /// <summary>
    /// Gets or sets the alignment along the side position of the chart title.
    /// </summary>
    /// <remarks>
    /// Default side position is center.
    /// Supported by charts of the schema http://schemas.microsoft.com/office/drawing/2014/chartex.
    /// </remarks>
    /*PositionAlignment*/int getPositionAlignment() { return mPositionAlignment; }
    void setPositionAlignment(/*PositionAlignment*/int value) { mPositionAlignment = value; }

    /// <summary>
    /// Determines whether other chart elements shall be allowed to overlap title.
    /// By default overlay is <c>false</c>.
    /// </summary>
    boolean getOverlay() { return mOverlay; }
    void setOverlay(boolean value) { mOverlay = value; }

    /// <summary>
    /// Determines whether the title shall be shown for this chart.
    /// </summary>
    boolean getShow() { return !mTitleHolder.getTitleDeleted(); }

    /// <summary>
    /// Returns an object that represents current font formatting properties.
    /// </summary>
    Font getFont()
    {
        if (mFont == null)
            mFont = new Font(getFontRunPropertiesSource(), getDocument());

        return mFont;
    }

    /// <summary>
    /// Provides access to fill and line formatting of the title.
    /// </summary>
    ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    DmlChartManualLayout getLayout() { return mLayout; }
    void setLayout(DmlChartManualLayout value) { mLayout = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }
    void setSpPr(DmlChartSpPr value) { mSpPr = value; }

    DmlChartTx getTx() { return mTx; }
    void setTx(DmlChartTx value) { mTx = value; }

    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to get text properties of the title.
    /// </summary>
    private DmlTextBodyProperties getBodyPr()
    {
        if ((getTx() != null) && (getTx().getRichText() != null))
            return getTx().getRichText().getProperties();

        return getTxPr().getBodyPr();
    }

    /// <summary>
    /// Gets a <see cref="DmlTextBodyProperties"/> instance to set text properties of the title.
    /// </summary>
    private DmlTextBodyProperties getBodyPrForUpdate()
    {
        if ((getTx() != null) && (getTx().getRichText() != null))
            return getTx().getRichText().getProperties();

        getTxPr().ensureBodyPrExists();
        return getTxPr().getBodyPr();
    }

    /// <summary>
    /// Gets a <see cref="ChartItemDmlRunPropertiesSource"/> instance that is the source of the title font properties.
    /// </summary>
    private ChartItemDmlRunPropertiesSource getFontRunPropertiesSource()
    {
        if (mFontRunPropertiesSource == null)
            mFontRunPropertiesSource = new ChartItemDmlRunPropertiesSource(this, mTitleHolder.getChartSpace());

        return mFontRunPropertiesSource;
    }

    /// <summary>
    /// Gets the parent document.
    /// </summary>
    private DocumentBase getDocument() { return mTitleHolder.getChartSpace().getDml().getDocument(); }

    /// <summary>
    /// Gets a flag indicating whether the parent chart is a Word 2016 chart.
    /// </summary>
    private boolean isChartEx() { return mTitleHolder.getChartSpace().isChartEx(); }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList ([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    ///  </summary>
    /// <remarks>
    /// Explicit implementation hides this from public.
    /// </remarks>
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    //>>>>>>>> #region  IChartFormatSource implementation

    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // Do nothing for a title.
    }

    public boolean /*IChartFormatSource.*/isFillSupported() { return true; }

    public DmlFill /*IChartFormatSource.*/getFill() { return getSpPr().getFill(); }
    public void /*IChartFormatSource.*/setFill(DmlFill value) { getSpPr().setFill(value); }

    public DmlOutline /*IChartFormatSource.*/getOutline() { return getSpPr().getOutline(); }
    public void /*IChartFormatSource.*/setOutline(DmlOutline value) { getSpPr().setOutline(value); }

    public /*ChartShapeType*/int /*IChartFormatSource.*/getShapeType()
    {
        // Not supported by a title.
        return ChartShapeType.DEFAULT;
    }
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        // Not supported by a title.
    }

    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return getDocument().getThemeInternal(); }

    public boolean /*IChartFormatSource.*/isFormatDefined() { return !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    private StringToObjDictionary<DmlExtension> mExtensions;
    private DmlChartManualLayout mLayout;
    private boolean mOverlay;
    private DmlChartSpPr mSpPr;
    private DmlChartTx mTx;
    private DmlChartTxPr mTxPr;
    private Font mFont;
    private ChartItemDmlRunPropertiesSource mFontRunPropertiesSource;
    private ChartFormat mFormat;
    private IDmlChartTitleHolder mTitleHolder;
    private /*SidePosition*/int mSidePosition = SidePosition.TOP;
    private /*PositionAlignment*/int mPositionAlignment = PositionAlignment.CENTER;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

