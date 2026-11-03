// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.FormatterPal;


/// <summary>
/// This class enforces standard compliance for ISO29500 capable AW model.
/// Particularly it:
/// - Deals with unsupported ISO29500-specific features of AW model when saving to pre-ISO29500 formats.
/// - When saving AW model to ISO29500 OOXML, it makes sure all non-compliant stuff is handled.
/// </summary>
/// <remarks>
/// ISO29500 introduces some new data types that are neither present in the older DOCX (ECMA-376) nor
/// in other save formats supported by AW. To complicate matter ISO29500 does not support 
/// some little things supported by ECMA-376. to comply to older/newer formats we have to alter
/// AW document contents. This is the dedicated place for this type of code.
/// Called exclusively from <see cref="DocumentValidator"/>.</remarks>
class Iso29500ComplianceEnforcer
{
    Iso29500ComplianceEnforcer(SaveOptions saveOptions, IWarningCallback warningCallback, OoxmlComplianceInfo cinfo)
    {
        OoxmlSaveOptions ooxmlSaveOptions = ms.as(saveOptions, OoxmlSaveOptions.class);
        mCompliance = OoxmlComplianceInfo.getCompliance(cinfo, ooxmlSaveOptions);
        mIsComplianceSetByUser = (ooxmlSaveOptions != null) && ooxmlSaveOptions.getUserSetCompliance();
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// MS Word 2010 up shows CompatibilityMode for any OOXML that does not contain a set of these CompatibilityOptions,
    /// so we have to add these properties for any iso29500 OOXML document.
    /// </summary>
    /// <remarks>Strictly speaking it seems only "compatibilityMode" is required for MS Word 2010, others are for 2012</remarks>
    void updateCompatibilityOptions(CompatibilityOptions co)
    {
        // Update the compatibility options to prevent showing Compatibility Mode info in MS Word 2013 
        // on saving as Strict OOXML. Strict OOXML is supported since MS Word 2013.
        boolean upgradeForIsoStrict = mIsComplianceSetByUser && (co.getMswVersion() < MsWordVersionCore.WORD_2013) &&
            (mCompliance == OoxmlComplianceCore.ISO_STRICT);
        if (upgradeForIsoStrict)
            co.setMswVersion(MsWordVersionCore.WORD_2013);

        final String SCHEMA_URI = "http://schemas.microsoft.com/office/word";

        // WORDSNET-4777 Add compatibilityMode in order to suppress "Compatibility Mode" ribbon in MS Word.
        if (co.getMswVersion() >= MsWordVersionCore.WORD_2003)
            verifyExistenceOrSetProperty(co, "compatibilityMode", SCHEMA_URI, 
                FormatterPal.intToStr((int)co.getMswVersion()), upgradeForIsoStrict);

        if (isIso29500() || (co.getMswVersion() >= MsWordVersionCore.WORD_2013))
        {
            // Without these MS Word 2013 turns compatibility mode on the document.

            // WORDSNET-13797 Do not force this setting to be 1 for Word2007 files unless we explicitly upgrade to ISO Strict.
            String overrideTableStyleFontSizeAndJustification = getTargetCompatibilityOptionValue(upgradeForIsoStrict, co.getMswVersion());

            verifyExistenceOrSetProperty(co, "overrideTableStyleFontSizeAndJustification", SCHEMA_URI, 
                overrideTableStyleFontSizeAndJustification, upgradeForIsoStrict);

            verifyExistenceOrSetProperty(co, "enableOpenTypeFeatures", SCHEMA_URI, "1", upgradeForIsoStrict);
            verifyExistenceOrSetProperty(co, "doNotFlipMirrorIndents", SCHEMA_URI, "1", upgradeForIsoStrict);
            verifyExistenceOrSetProperty(co, "differentiateMultirowTableHeaders", SCHEMA_URI, "1", upgradeForIsoStrict);
        }
    }

    /// <summary>
    /// Returns value for compatibility option depending on document version and compliance chosen.
    /// </summary>
    private static String getTargetCompatibilityOptionValue(boolean upgradeForIsoStrict, /*MsWordVersionCore*/int mswVersion)
    {
        // Force value to be 1 when upgrade for IsoStrict.
        if (upgradeForIsoStrict)
            return "1";

        // For certain document versions write 0 if value is unspecified.
        if ((mswVersion == MsWordVersionCore.WORD_2007) || 
            (mswVersion == MsWordVersionCore.WORD_2010) || 
            (mswVersion == MsWordVersionCore.UNSPECIFIED))
            return "0";
        
        // Write 1 if value is unspecified.
        return "1";
    }

    private static void verifyExistenceOrSetProperty(CompatibilityOptions co, String propertyName, String uri,
        String value, boolean setProperty)
    {

        CustomCompatibilitySetting setting = co.getCustomCompatibilitySettings().get(propertyName);

        if (setting == null)
            co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting(propertyName, uri, value));
        else if (setProperty)
            setting.setValue(value);
    }

    /// <summary>
    /// Verify section compliance and modify attributes if needed.
    /// </summary>
    void enforceSectionCompliance(Section sections)
    {
        enforceCompliantBorderLineStyle(sections.getSectPr().getBorderTop());
        enforceCompliantBorderLineStyle(sections.getSectPr().getBorderBottom());
        enforceCompliantBorderLineStyle(sections.getSectPr().getBorderLeft());
        enforceCompliantBorderLineStyle(sections.getSectPr().getBorderRight());
    }

    /// <summary>
    /// Validate LineStyle of a single border.
    /// </summary>
    private void enforceCompliantBorderLineStyle(Border border)
    {
        if (border.isPageBorderArt())
            border.setLineStyle(getCompliantLineStyle(border.getLineStyle()));
    }

    /// <summary>
    /// In Iso29500 LineStyle enum has some items added and removed. We have to map them when
    /// saving.
    /// </summary>
    private /*LineStyle*/int getCompliantLineStyle(/*LineStyle*/int lineStyle)
    {
        /*LineStyle*/int result = lineStyle;
        if (isIso29500())
        {
            // The following enumeration values were removed from the ST_Border simple type (§17.18.2): 
            // tribal1, tribal2, tribal3, tribal4, tribal5, tribal6
            // so we need to map them to something when saving to ISO29500
            result = (/*LineStyle*/int)PageBorderArtRepository.getIso29500BorderArt((/*PageBorderArt*/int)lineStyle);
        }
        else if ((/*PageBorderArt*/int)lineStyle >= PageBorderArt.EARTH_3)
        {
            // The following enumeration values were added to the ST_Border simple type (§17.18.2): 
            // earth3, triangle1, triangle2, triangleCircle1, triangleCircle2, shapes1, shapes2, custom
            // so we need to map them to something when saving to pre-ISO29500 formats.
            switch((/*PageBorderArt*/int)lineStyle)
            {
                case PageBorderArt.EARTH_3:                        
                    result = (/*LineStyle*/int)PageBorderArt.EARTH_2;
                    break;
                case PageBorderArt.CUSTOM:                        
                    result = (/*LineStyle*/int)PageBorderArt.TWISTED_LINES_1;
                    break;
                default:
                    result = (/*LineStyle*/int)PageBorderArtRepository.getEcma376BorderArt((/*PageBorderArt*/int)lineStyle);                        
                    break;
            }
        }

        if (result != lineStyle)
            warn(WarningType.MINOR_FORMATTING_LOSS, WarningStrings.ISO_29500_COMPLIANCE_PAGE_BORDER_ART_CHANGED);

        return result;
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(warningType, WarningSource.DOCX, description));
    }
    
    /// <summary>
    /// True when saving to ISO29500. False when saving to any other format.
    /// </summary>
    private boolean isIso29500() { return mCompliance != OoxmlComplianceCore.ECMA_376; }

    private /*final*/ /*OoxmlComplianceCore*/int mCompliance;
    private /*final*/ boolean mIsComplianceSetByUser;
    private /*final*/ IWarningCallback mWarningCallback;
}

