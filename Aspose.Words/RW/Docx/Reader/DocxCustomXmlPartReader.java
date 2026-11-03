// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.OpcRelationship;
import com.aspose.ms.System.msString;
import com.aspose.OpcContentType;
import com.aspose.StreamUtil;
import com.aspose.RandomUtil;


/// <summary>
/// Reads the Custom XML Data Storage and Custom XML Data Storage Properties Parts.
/// </summary>
/*static*/ class DocxCustomXmlPartReader
{
	/* Simulation of static class by using private constructor */
	private DocxCustomXmlPartReader()
	{}

    static void read(DocxDocumentReader reader, IWarningCallback warningCallback) throws Exception
    {


        // The main document part can have an implicit relationship to zero or more Custom XML Data Storage parts.
        OpcPackagePart documentPart = reader.getDocumentPart();

        for (OpcRelationship rel : documentPart.getRels())
        {
            if (msString.equals(rel.getType(), reader.getRelTypes().getCustomXml()))
                readCustomXmlPart(reader, documentPart.getRelatedPartName(rel), warningCallback);
        }
    }

    /// <summary>
    /// Reads a Custom XML Data Storage Part and its properties and adds to the document.
    /// </summary>
    private static void readCustomXmlPart(DocxDocumentReader reader, String customXmlPartName, IWarningCallback warningCallback) throws Exception
    {
        OpcPackagePart opcDataPart = reader.fetchPartByName(customXmlPartName);

        // Do not read DrawingML related XmlPart (ink) because it will be read
        // upon DrawingML reading (see DmlDrawingReader.ReadRId method)
        // The relationship type of the explicit relationship specified for this part is CustomXml
        // and that is why we have to avoid reading this as customXmlPart here.
        // Next condition includes this one, but leave it here for better code readability.
        if (DocxContentType.INK_ML.equals(opcDataPart.getContentType()))
            return;

        // WORDSNET-15911 All Custom XML Parts with content type which is not equals “application/xml”
        // have to be ignored according to MS Word behavior.
        if (!OpcContentType.XML.equals(opcDataPart.getContentType()))
            return;

        // We don't need to preserve the content type because it is always application/xml.
        // We don't need to preserve the name because we auto generate it on write.
        // But we preserve the data of course.
        CustomXmlPart customXmlPart = new CustomXmlPart();
        customXmlPart.setData(StreamUtil.copyStreamToByteArray(opcDataPart.getStream()));

        // Read the related Custom XML Data Storage Properties Part.
        OpcPackagePart opcPropsPart = reader.getPartByRelationshipType(opcDataPart, reader.getRelTypes().getCustomXmlProperties());

        // If Custom XML Data does not have a corresponding Custom XML Data Properties part it is an invalid document I suppose.
        // In this case let's just skip this data part.
        // AM. We should read such parts anyway. See WORDSNET-3913
        if (opcPropsPart == null)
        {
            // andrnosk: WORDSNET-6369 Make sure warningCallback is not null.
            if (warningCallback != null)
                // Issue warning and read part without properties.
                warningCallback.warning(new WarningInfo(WarningType.DATA_LOSS, WarningSource.DOCX, msString.format("Custom XML part properties are missed for {0}.", customXmlPartName)));

            // WORDSNET-28700 Assign unique Id for orphan parts.
            customXmlPart.setId(RandomUtil.newGuid().toString());
        }
        else
        {
            // Read properties.
            CustomXmlDataStorePropertiesReader.read(opcPropsPart.getStream(), customXmlPart, warningCallback);
        }

        // Finally add the custom xml part to the model.
        reader.getDocument().getCustomXmlParts().add(customXmlPart);
    }
}

