// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2013 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.Debug;


/// <summary>
/// Stores OOXML compliance info about the document.
/// Used for automated identification of spec features during document reading.
/// Once all the features are identified we can do a better saving job, e.g. set correct <see cref="OoxmlCompliance"/> upon saving.
/// </summary>
/// <remarks>
/// DD: One very important TODO item here is to accurately support updates to this class after DOM updates. 
/// For example users imports a DrawingML node with Drawing Extensions, or an SdtCheckbox (DOCX extensions), 
/// but AW writes this document using old ComplianceInfo resulting in wrong behavior.
/// Presently I've added code to <see cref="DocumentValidator.VisitStructuredDocumentTagStart"/> 
/// for checking SdtCheckbox, all other elements and even properties will have to be checked there as well.
/// </remarks>
class OoxmlComplianceInfo implements Cloneable
{
    static /*OoxmlComplianceCore*/int getCompliance(OoxmlComplianceInfo cInfo, OoxmlSaveOptions options)
    {
        // Note! ISO Strict format may be returned only if it is set in the options until the support of 
        // ISO Strict is finished.
        if (options == null)
        {
            return (cInfo == null) ? OoxmlComplianceCore.ECMA_376 : cInfo.getCompatibleCompliance();
        }
        else
        {
            /*OoxmlComplianceCore*/int result = 
                ((options.getUserSetCompliance()) || (cInfo == null)) ? 
                    options.getComplianceCore() : cInfo.getCompatibleCompliance();
            if (cInfo != null)
            {
                // DD: It seems having these elements in the model, we always have to write transitional namespaces,
                // otherwise MS Word fails to open such files. Extra research required.
                // TODO: need to think what to do when user wants to save as ECMA386
                if ((cInfo.isDrawingExtensions() || cInfo.isDocxExtensions()) &&
                    (result < OoxmlComplianceCore.ISO_TRANSITIONAL))
                    result = OoxmlComplianceCore.ISO_TRANSITIONAL;
            }
            return result;
        }
    }

    OoxmlComplianceInfo deepClone()
    {
        return (OoxmlComplianceInfo) memberwiseClone();
    }

    /// <summary>
    /// Marks the document that it conforms to ISO/IEC 29500 Transitional specification.
    /// </summary>
    void markAsIsoTransitional()
    {
        // We can set isoTransitional if we have not yet determined isoStrict.
        if (mCompliance < OoxmlComplianceCore.ISO_TRANSITIONAL)
            mCompliance = OoxmlComplianceCore.ISO_TRANSITIONAL;
    }

    /// <summary>
    /// Marks the document that it conforms to ISO/IEC 29500 Strict specification.
    /// </summary>
    void markAsIsoStrict()
    {
        // We can set IsoStrict if we have not yet determined higher version.
        if (mCompliance < OoxmlComplianceCore.ISO_STRICT)
            mCompliance = OoxmlComplianceCore.ISO_STRICT;
    }

    /// <summary>
    /// Marks the document that it conforms to [MS-DOCX]: Word Extensions to the Office Open XML (.docx) File.
    /// </summary>
    static void markAsHasDocxExtensions(Document document)
    {
        if (document.getComplianceInfo() == null)
            document.setComplianceInfo(new OoxmlComplianceInfo());
        document.getComplianceInfo().isDocxExtensions(true);
    }

    /// <summary>
    /// Marks the document as containing Extensions introduced in specified version of MS Word.
    /// </summary>
    static void markAsHasDocxExtensionsOf(Document document, /*MsWordVersionCore*/int version)
    {
        if (document.getComplianceInfo() == null)
            document.setComplianceInfo(new OoxmlComplianceInfo());

        document.getComplianceInfo().markAsHasDocxExtensionsOf(version);
    }

    /// <summary>
    /// Marks the document as containing Extensions introduced in specified version of MS Word.
    /// </summary>
    void markAsHasDocxExtensionsOf(/*MsWordVersionCore*/int version)
    {
        if (mMsWordExtensionsVersion >= version)
            return;
        
        mMsWordExtensionsVersion = version;

        if (isDocxExtensions())
            markAsIsoTransitional();
    }

    /// <summary>
    /// Marks the document that it conforms to [MS-ODRAWXML]: Office Drawing Extensions to Office Open XML Structure.
    /// </summary>
    static void markAsHasDrawingExtensions(Document document)
    {
        if (document.getComplianceInfo() == null)
            document.setComplianceInfo(new OoxmlComplianceInfo());
        document.getComplianceInfo().isDrawingExtensions(true);
    }

    /// <summary>
    /// Gets/sets the OOXML version determined from document contents.
    /// </summary>
    /*OoxmlComplianceCore*/int getCompliance() { return mCompliance; }
    void setCompliance(/*OoxmlComplianceCore*/int value) { mCompliance = value; }

    /// <summary>
    /// Returns the version in which the all MS Word Extensions contained in the document are supported.
    /// </summary>
    /*MsWordVersionCore*/int getMsWordExtensionsVersion() { return mMsWordExtensionsVersion; }

    /// <summary>
    /// Gets the OOXML version determined from document contents and that can be used on saving the document.
    /// If Aspose.Words does not support saving as OOXML format that is stored in <see cref="mCompliance"/>,
    /// this property returns nearest supported format or <see cref="mCompliance"/> otherwise.
    /// </summary>
    private /*OoxmlComplianceCore*/int getCompatibleCompliance()
    {
        return mCompliance;
    }

    /// <summary>
    /// Gets flag signaling that the document conforms to ECMA-376 specification.
    /// </summary>
    boolean isEcma376() { return mCompliance == OoxmlComplianceCore.ECMA_376; }

    /// <summary>
    /// Gets flag signaling that the document conforms to ISO/IEC 29500 Transitional specification.
    /// </summary>
    boolean isIsoTransitional() { return mCompliance == OoxmlComplianceCore.ISO_TRANSITIONAL; }

    /// <summary>
    /// Gets flag signaling that the document conforms to ISO/IEC 29500 Strict specification.
    /// </summary>
    boolean isIsoStrict() { return mCompliance == OoxmlComplianceCore.ISO_STRICT; }

    /// <summary>
    /// Gets/sets flag signaling that the document conforms to 
    /// [MS-ODRAWXML]: Office Drawing Extensions to Office Open XML Structure
    /// </summary>
    boolean isDrawingExtensions() { return mIsDrawingExtensions; }
    void isDrawingExtensions(boolean value)
    {
        mIsDrawingExtensions = value;
        if (mIsDrawingExtensions)
            markAsIsoTransitional(); // at least iso29500 transitional level
    }

    /// <summary>
    /// Gets/sets flag signaling that the document conforms to 
    /// [MS-DOCX]: Word Extensions to the Office Open XML (.docx) File
    /// </summary>
    boolean isDocxExtensions() { return mMsWordExtensionsVersion >= MsWordVersionCore.WORD_2010; }
    void isDocxExtensions(boolean value)
    {
        if (value)
        {
            if (mMsWordExtensionsVersion < MsWordVersionCore.WORD_2010)
                mMsWordExtensionsVersion = MsWordVersionCore.WORD_2010;

            markAsIsoTransitional(); // at least iso29500 transitional level
        }
        else
        {
            if (mMsWordExtensionsVersion >= MsWordVersionCore.WORD_2010)
                mMsWordExtensionsVersion = MsWordVersionCore.WORD_2007;
        }
    }

    /// <summary>
    /// Gets or sets the ODT UseFormerTextWrapping setting.
    /// </summary>
    /// <dev>
    /// This property is separated from IsIsoStrict to prevent conflict with DOCX format.
    /// It was a fix for WORDSNET-9652, WORDSNET-9832
    /// </dev>
    boolean isOdtFormerTextWrapping() { return mIsOdtFormerTextWrapping; }
    void isOdtFormerTextWrapping(boolean value) { mIsOdtFormerTextWrapping = value; }

    static /*MsWordVersionCore*/int msWordVersionFromCompatibilityMode(String compatibilityModeValue)
    {
        /*MsWordVersionCore*/int result;

        int version = FormatterPal.xmlToInt(compatibilityModeValue);
        switch (version)
        {
            case 11: // least version of MSW known to produce DOCX DD: Actually it is 12, but I've seen 11 in the customer docs.
            case 12:
            case 14:
            case 15: // latest version of MSW known to date. Add more when found.
                result = (/*MsWordVersionCore*/int) version;
                break;
            default:
                Debug.assertTrue(version <= 15); // DD: We would like to know whenever document created with a new MS Word versions is found. 
                result = MsWordVersionCore.UNSPECIFIED; // DD: Lets return unspecified for all unknown stuff.
                break;
        }
        return result;
    }

    // By default DOCX should conform to the common base spec Ecma386
    private /*OoxmlComplianceCore*/int mCompliance = OoxmlComplianceCore.ECMA_376; 

    private boolean mIsDrawingExtensions;
    private /*MsWordVersionCore*/int mMsWordExtensionsVersion;
    private boolean mIsOdtFormerTextWrapping;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}


