// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// This class is invoked by <see cref="DocumentValidator"/> to correct any issues with custom XML parts 
/// including validation of part ID with changing references to them from structured document tags.
/// </summary>
class CustomXmlValidator
{
    CustomXmlValidator(SaveInfo saveInfo)
    {
        mSaveInfo = saveInfo;
    }

    /// <summary>
    /// Validates ID of custom XML parts. Text values are replaced with GUIDs on saving as ISO/IEC 29500 
    /// OOXML formats. For ECMA-376 a warning is generated only.
    /// </summary>
    void visitDocumentStart(Document doc) throws Exception
    {
        if (mSaveInfo.SaveOptions instanceof OoxmlSaveOptions)
            validateCustomXmlContent(doc);

        if (!mSaveInfo.isDocxFormat())
            return;

        mIdMapping.clear();
        ArrayList<CustomXmlPart> xmlParts = getXmlPartsWithNonGuidId(doc);

        for (CustomXmlPart xmlPart : xmlParts)
        {
            if (getCompliance() != OoxmlComplianceCore.ECMA_376)
            {
                String oldId = xmlPart.getId();
                xmlPart.setId(Guid.newGuid().toString("B").toUpperCase());

                mIdMapping.add(oldId, xmlPart.getId());

                warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.VALIDATOR,
                     msString.format(WarningStrings.CHANGED_XML_PART_ID, oldId, xmlPart.getId()));
            }
            else
            {
                warn(WarningType.HINT, WarningSource.VALIDATOR,
                     msString.format(WarningStrings.NON_GUID_XML_PART_ID, xmlPart.getId()));
            }
        }
    }

    /// <summary>
    /// Checks assignment of custom XML parts in data binding of structured document tags after part ID changes.
    /// </summary>
    void visitStructuredDocumentTagStart(StructuredDocumentTag sdt, XmlMappingContext xmlMappingContext) throws Exception
    {
        if (!mSaveInfo.isDocxFormat())
            return;

        // Now reference to a custom XML part is stored directly in XmlMapping, but let's keep this check
        // for more care.
        XmlMapping mapping = sdt.getXmlMapping();
        if (mapping.isEmpty())
            return;

        if (mapping.getCustomXmlPart() == null)
        {
            if (mIdMapping.containsKey(mapping.getStoreItemId()))
                mapping.setStoreItemId(mIdMapping.get(mapping.getStoreItemId()));
        }
        else
        {
            // Note, for a moment only SDT of type Date may need to update its content.
            if (sdt.getNeedToUpdateContent())
            {
                mapping.setValue(sdt.getFullDateInternal().toString(sdt.getDateDisplayFormat()));
                // Reset cache to get correctly updated SDT on the validation stage.
                // See, "TestJira15651" for details.
                xmlMappingContext.remove(mapping.getStoreItemId());
            }
        }
    }

    /// <summary>
    /// Checks content of the custom part and removes the content, when content is invalid.
    /// </summary>
    private void validateCustomXmlContent(Document doc) throws Exception
    {
        // WORDSNET-9970 Check, that specified XML is well formed (XML, which adheres to the XML standard). otherwise
        // output document can not be opened by the Word.  
        for(int i = doc.getCustomXmlParts().getCount() - 1; i >= 0; --i)
        {
            CustomXmlPart customXmlPart = doc.getCustomXmlParts().get(i);
            if (!customXmlPart.validateCustomXmlData())
            {
                // Remove invalid parts. See "TestJira9970Fopc" for details.
                doc.getCustomXmlParts().removeAt(i);
                warn(WarningType.DATA_LOSS_CATEGORY, WarningSource.VALIDATOR,
                    msString.format("Custom XML part with id={0} has invalid content and was removed.",
                    customXmlPart.getId()));
            }
        }    
    }

    /// <summary>
    /// Returns a list of custom XML parts that have non-GUID value of the Id property.
    /// </summary>
    private static ArrayList<CustomXmlPart> getXmlPartsWithNonGuidId(Document doc)
    {
        ArrayList<CustomXmlPart> xmlParts = new ArrayList<CustomXmlPart>();

        for (CustomXmlPart xmlPart : doc.getCustomXmlParts())
        {
            if (StringUtil.hasChars(xmlPart.getId()) && !isValidGuid(xmlPart.getId()))
                msArrayList.add(xmlParts, xmlPart);
        }

        return xmlParts;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified string is a valid GUID.
    /// </summary>
    private static boolean isValidGuid(String value)
    {
        return G_GUID_REGEX.isMatch(value);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (mSaveInfo.Document.getWarningCallback() != null)
            mSaveInfo.Document.getWarningCallback().warning(new WarningInfo(type, source, description));
    }

    /// <summary>
    /// Specifies the OOXML version for the output document on saving as DOCX formats.
    /// </summary>
    private /*OoxmlComplianceCore*/int getCompliance()
    {
        return OoxmlComplianceInfo.getCompliance(mSaveInfo.Document.getComplianceInfo(),
            ms.as(mSaveInfo.SaveOptions, OoxmlSaveOptions.class));
    }

    private /*final*/ SaveInfo mSaveInfo;
    private /*final*/ StringToObjDictionary<String> mIdMapping = new StringToObjDictionary<String>();

    private static /*final*/ Regex G_GUID_REGEX =
        new Regex("^[{|(]?[0-9A-F]{8}[-]?([0-9A-F]{4}[-]?){3}[0-9A-F]{12}[)|}]?$",
            RegexOptions.COMPILED | RegexOptions.IGNORE_CASE);
}

