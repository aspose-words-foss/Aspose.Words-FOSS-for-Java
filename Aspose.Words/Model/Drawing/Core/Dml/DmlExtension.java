// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// Container class for Office Drawing Extensions to Office Open XML Structure.
/// </summary>
class DmlExtension implements Cloneable
{
    DmlExtension(String uri, String xmlDoc)
    {
        mUri = uri;
        mXmlDoc = xmlDoc;
    }

    /// <summary>
    /// Clones this <see cref="DmlExtension"/> object.
    /// </summary>
    /*virtual*/ DmlExtension deepClone()
    {
        DmlExtension lhs = (DmlExtension)memberwiseClone();

        if (mNvPr != null)
            lhs.mNvPr = mNvPr.deepClone();

        if (mDmlFillPr != null)
            lhs.setFillPr(mDmlFillPr.deepClone());

        if (mOutlinePr != null)
            lhs.mOutlinePr = mOutlinePr.deepClone();

        if (mWebVideoPr != null)
            lhs.mWebVideoPr = mWebVideoPr.deepClone();

        if (mDataLabelPr != null)
            lhs.mDataLabelPr = mDataLabelPr.deepClone();

        if (mDatalabelsRangeData != null)
            lhs.mDatalabelsRangeData = mDatalabelsRangeData.deepClone();

        if (mSvgBlip != null)
            lhs.mSvgBlip = mSvgBlip.deepClone();

        return lhs;
    }

    /// <summary>
    /// Searches for an extension by its URI in the specified dictionary and creates it if not found.
    /// </summary>
    static DmlExtension getOrCreateExtension(StringToObjDictionary<DmlExtension> extensions, String uri)
    {
        DmlExtension extension = extensions.get(uri);
        if (extension == null)
        {
            extension = new DmlExtension(uri, null);
            extensions.set(uri, extension);
        }

        return extension;
    }

    /// <summary>
    /// Creates svgBlip extension with the specified svg image bytes. 
    /// </summary>
    static DmlExtension createSvgBlipExtension(byte[] svgImageBytes, DocumentBase doc)
    {
        DmlBlip svgBlip = new DmlBlip();
        svgBlip.setDocument(doc);
        svgBlip.setEmbedImage(svgImageBytes);
        DmlExtension svgBlipExt = new DmlExtension(DmlExtensionUri.SVG_BLIP, null);
        svgBlipExt.setSvgBlip(svgBlip);
        return svgBlipExt;
    }

    /// <summary>
    /// Returns XML converted to ISO Transitional or ISO Strict format depending on the 'asIsoStrict' parameter.
    /// </summary>
    String getXmlToWrite(/*OoxmlComplianceCore*/int compliance)
    {
        AnyXmlReader xmlReader = new AnyXmlReader(mXmlDoc, null);

        boolean asIsoStrict = (compliance == OoxmlComplianceCore.ISO_STRICT);

        boolean isSourceIsoStrict = !msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.toIsoTransitional(xmlReader.getNamespaceURI()));
        boolean isSourceIsoTransitional = !msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.toIsoStrict(xmlReader.getNamespaceURI()));

        // If format of stored source XML is recognized and we write into the same format then just return
        // the stored XML.
        if ((isSourceIsoStrict != isSourceIsoTransitional) && (isSourceIsoStrict == asIsoStrict))
            return mXmlDoc;

        return xmlReader.readOuterXml(new DmlIsoStrictXmlUpdater(asIsoStrict), null);
    }

    /// <summary>
    /// Raw XML string that represents DrawingML extension.
    /// </summary>
    /// <remarks>
    /// We need this to provide round-trip of unknown extensions.
    /// The root element of this XML is a:extLst.
    /// </remarks>
    String getXmlDoc() { return mXmlDoc; }
    void setXmlDoc(String value) { mXmlDoc = value; }

    /// <summary>
    /// Uri of this DrawingML extension. Each extension has an Uri attribute, which serves as an identifier
    /// to indicate information about the extension.
    /// </summary>
    String getUri() { return mUri; }
    void setUri(String value) { mUri = value; }

    /// <summary>
    /// DrawingML fill properties.
    /// </summary>
    DmlFill getFillPr() { return mDmlFillPr; }
    void setFillPr(DmlFill value) { mDmlFillPr = value; }

    /// <summary>
    /// Represents SpPr element in charts.
    /// </summary>
    DmlChartSpPr getDmlChartSpPr() { return mDmlChartSpPr; }
    void setDmlChartSpPr(DmlChartSpPr value) { mDmlChartSpPr = value; }

    /// <summary>
    /// Represents content of the outline properties.
    /// </summary>
    /// <remarks>
    ///  Stores the line information of an object when the line fill
    ///  has been set to invisible (on VML->DML conversion).
    /// </remarks>
    DmlOutline getOutlinePr() { return mOutlinePr; }
    void setOutlinePr(DmlOutline value) { mOutlinePr = value; }

    /// <summary>
    /// Non-visual drawing properties.
    /// </summary>
    DmlNvDrawingProperties getNvPr() { return mNvPr; }
    void setNvPr(DmlNvDrawingProperties value) { mNvPr = value; }

    /// <summary>
    /// Indicating that the local BLIP compression setting overrides the document default compression setting.
    /// </summary>
    boolean getUseLocalDpi() { return mUseLocalDpi; }
    void setUseLocalDpi(boolean value) { mUseLocalDpi = value; }

    /// <summary>
    /// Element that specifies the properties for displaying an online video to the user.
    /// </summary>
    DmlWebVideoProperties getWebVideoPr() { return mWebVideoPr; }
    void setWebVideoPr(DmlWebVideoProperties value) { mWebVideoPr = value; }

    /// <summary>
    /// Chart data label properties.
    /// </summary>
    DmlChartDataLabelPr getDataLabelPr() { return mDataLabelPr; }
    void setDataLabelPr(DmlChartDataLabelPr value) { mDataLabelPr = value; }

    /// <summary>
    /// Specifies the data for the data labels. 
    /// </summary>
    DmlChartDataSource getDataLabelsRangeData()
    {
        if (mDatalabelsRangeData == null)
            mDatalabelsRangeData = new DmlChartDataSource();

        return mDatalabelsRangeData;
    }

    /// <summary>
    /// Specifies unique identifier of a data label. 
    /// </summary>
    Guid getDataLabelId() { return mDataLabelId; }
    void setDataLabelId(Guid value) { mDataLabelId = value; }

    /// <summary>
    /// Defines the explicit part location of the Diagram Drawing and the minimum application 
    /// version required to layout this diagram.
    /// </summary>
    String getDrawingRelId() { return mDrawingRelId; }
    void setDrawingRelId(String value) { mDrawingRelId = value; }

    /// <summary>
    /// Specifies a graphic element in Scalable Vector Graphics (SVG) format.
    /// https://msdn.microsoft.com/en-us/library/mt765155(v=office.12).aspx
    /// </summary>
    DmlBlip getSvgBlip() { return mSvgBlip; }
    void setSvgBlip(DmlBlip value) { mSvgBlip = value; }

    /// <summary>
    /// Stores information about embedded images, which placed in a non-prased xml content. 
    /// </summary>
    SortedStringListGeneric<byte[]> getEmbeddedImages() { return mEmbeddedImages; }

    /// <summary>
    /// Indicates that the shape is decorative.
    /// </summary>
    boolean getDecorative() { return mDecorative; }
    void setDecorative(boolean value) { mDecorative = value; }

    private String mUri;
    private String mXmlDoc;
    private DmlFill mDmlFillPr;
    private DmlChartSpPr mDmlChartSpPr;
    private boolean mUseLocalDpi;
    private DmlOutline mOutlinePr;
    private DmlNvDrawingProperties mNvPr;
    private DmlWebVideoProperties mWebVideoPr;
    private DmlChartDataLabelPr mDataLabelPr;
    private DmlChartDataSource mDatalabelsRangeData;
    private String mDrawingRelId;
    private DmlBlip mSvgBlip;
    private Guid mDataLabelId = Guid.Empty;
    private boolean mDecorative;

    /// <summary>
    /// Maps relations id with the binary data of embedded images.
    /// </summary>
    private /*final*/ SortedStringListGeneric<byte[]> mEmbeddedImages = new SortedStringListGeneric<byte[]>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}

