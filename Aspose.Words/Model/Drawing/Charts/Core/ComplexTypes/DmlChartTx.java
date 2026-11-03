// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents ether tx (Chart Text) (5.7.2.215) or tx (Series Text) (5.7.2.216) element.
/// </summary>
class DmlChartTx extends DmlExtensionListSource implements Cloneable
{
    DmlChartTx deepClone()
    {
        DmlChartTx lhs = (DmlChartTx)memberwiseClone();
        if (mStrRef != null)
            lhs.mStrRef = mStrRef.deepClone();

        if (mRichText != null)
            lhs.mRichText = mRichText.deepClone();

        if (mFormula != null)
            lhs.mFormula = mFormula.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Returns text representation of the Tx.
    /// </summary>
    String getText()
    {
        if (getRichText() != null)
        {
            StringBuilder reachStrBuilder = new StringBuilder();

            for (DmlParagraph paragraph : getRichText().getParagraphs())
                msStringBuilder.append(reachStrBuilder, paragraph.getRunText());

            return reachStrBuilder.toString();
        }
        else
        {
            return getPlainText();
        }
    }

    /// <summary>
    /// Returns the font size in EMU of the first paragraph.
    /// </summary>
    /// <returns>Font size in EMU</returns>
    float getFirstParagraphFontSizeInEmu()
    {
        float fontSizeInEmu = Float.NaN;

        if ((getFirstParagraph() != null) && getFirstParagraph().getProperties().hasDefaultRunProperties())
            fontSizeInEmu = (float)getFirstParagraph().getProperties().getDefaultRunProperties().getFontSize().getValueInEmus();

        return fontSizeInEmu;
    }
   
    /*DmlChartTxType*/int getTxType() { return mTxType; }

    DmlChartValueRef getStrRef() { return mStrRef; }
    void setStrRef(DmlChartValueRef value) { mStrRef = value; }

    String getPlainText()
    {
        if (!StringUtil.hasChars(mPlainText) && mStrRef != null)
        {
            // It seems when there is string ref, plain text must return concatenated string of all points,
            // instead of the first one only.
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mStrRef.getValues().getValueCount(); i++)
            {
                DmlChartValue value = mStrRef.getValues().get(i);
                if (value != null)
                {
                    msStringBuilder.append(builder, value.getStringValue());
                    msStringBuilder.append(builder, " ");
                }
            }

            // Trim redundant whitespace at the end.
            mPlainText = msString.trim(builder.toString());
        }

        return mPlainText;
    }
    void setPlainText(String value)
    {
        // If plain text property is set consider tx as series text.
        mTxType = DmlChartTxType.SERIES_TEXT;
        mSourcePlainText = value;
        mPlainText = value;
        mStrRef = null;
    }

    /// <summary>
    /// Returns value of plain text read from the document.
    /// Used for writing correct value from the model.
    /// </summary>
    String getSourcePlainText() { return mSourcePlainText; }

    DmlTextBody getRichText() { return mRichText; }
    void setRichText(DmlTextBody value)
    {
        // If reach text property is set consider tx as chart text.
        mTxType = DmlChartTxType.CHART_TEXT;
        mRichText = value;
    }

    /// <summary>
    /// Gets or sets a formula that specifies text data reference.
    /// </summary>
    /// <remarks>
    /// This is the f element of the 2.24.3.79 CT_TextData complex type [MS-ODRAWXML]
    /// </remarks>
    DmlChartFormula getFormula() { return mFormula; }
    void setFormula(DmlChartFormula value)
    {
        mFormula = value;
        // If formula is set consider tx as text data reference.
        if (mFormula != null)
            mTxType = DmlChartTxType.TEXT_DATA;
    }

    /// <summary>
    /// Indicates whether the default properties are specified for the first paragraph.
    /// </summary>
    boolean hasDefaultRunPr() { return (getFirstParagraph() != null) && getFirstParagraph().getProperties().hasDefaultRunProperties(); }

    /// <summary>
    /// Shortcut to the first paragraph in the reach text collection.
    /// If the collection is empty, return null.
    /// </summary>
    DmlParagraph getFirstParagraph()
    {
        // WORDSNET-21691, WORDSNET-21478 Check the number of paragraphs before taking the first one.
        if ((getRichText() != null)  && (getRichText().getParagraphs().size() > 0) && (getRichText().getParagraphs().get(0) != null))
            return getRichText().getParagraphs().get(0);

        return null;
    }

    /// <summary>
    /// Lets consider tx as series text by default.
    /// </summary>
    private /*DmlChartTxType*/int mTxType = DmlChartTxType.SERIES_TEXT;
    private DmlChartValueRef mStrRef;
    private String mPlainText;
    private String mSourcePlainText;
    private DmlTextBody mRichText;
    private DmlChartFormula mFormula;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

/*enum*/ final class DmlChartTxType
{
    private DmlChartTxType(){}
    
    /// <summary>
    /// Text is series text (the 21.2.2.215 tx element). Text may be defined as plain text only.
    /// </summary>
    public static final int SERIES_TEXT = 0;
    /// <summary>
    /// Text is chart text (the 21.2.2.214 tx element). Text includes rich text formatting.
    /// </summary>
    public static final int CHART_TEXT = 1;
    // In chartEx the elements are defined in another way, but meaning is the same. So, same values are used.
    /// <summary>
    /// The text is a text data element (2.24.3.79 CT_TextData [MS-ODRAWXML]).
    /// </summary>
    public static final int TEXT_DATA = SERIES_TEXT;
    /// <summary>
    /// The text is a rich text element (CT_TextBody [ISO/IEC29500-1:2012] section A.4.1).
    /// </summary>
    public static final int RICH_TEXT = CHART_TEXT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartTxType value.
    /// </summary>
    public static String getName(/*DmlChartTxType*/int dmlChartTxType)
    {
    	switch (dmlChartTxType)
    	{
    		case SERIES_TEXT:
    		/*case TEXT_DATA:*/
    			return "SERIES_TEXT" + " | " + "TEXT_DATA";
    		case CHART_TEXT:
    		/*case RICH_TEXT:*/
    			return "CHART_TEXT" + " | " + "RICH_TEXT";
    		default: return "Unknown DmlChartTxType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartTxType value.
    /// </summary>
    public static String toString(/*DmlChartTxType*/int dmlChartTxType)
    {
    	switch (dmlChartTxType)
    	{
    		case SERIES_TEXT:
    		/*case TEXT_DATA:*/
    			return "SeriesText" + " | " + "TextData";
    		case CHART_TEXT:
    		/*case RICH_TEXT:*/
    			return "ChartText" + " | " + "RichText";
    		default: return "Unknown DmlChartTxType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartTxType name.
    /// </summary>
    public static /*DmlChartTxType*/int fromName(String dmlChartTxTypeName)
    {
    	if ("SERIES_TEXT".equals(dmlChartTxTypeName)) return SERIES_TEXT;
    	if ("CHART_TEXT".equals(dmlChartTxTypeName)) return CHART_TEXT;
    	if ("TEXT_DATA".equals(dmlChartTxTypeName)) return TEXT_DATA;
    	if ("RICH_TEXT".equals(dmlChartTxTypeName)) return RICH_TEXT;
    	throw new IllegalArgumentException("Unknown DmlChartTxType name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartTxType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SERIES_TEXT,
    		CHART_TEXT,
    		TEXT_DATA,
    		RICH_TEXT,
    	};
    }

    public static final int length = 4;
}

