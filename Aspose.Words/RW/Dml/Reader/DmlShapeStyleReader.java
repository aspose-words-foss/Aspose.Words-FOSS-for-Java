// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents a class building DmlShapeStyle objects from xml.
/// </summary>
class DmlShapeStyleReader extends DmlReaderBase
{
    private DmlShapeStyleReader(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        mReader = reader;
        mComplianceInfo = complianceInfo;
    }

    static DmlShapeStyle read(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        if (!"style".equals(reader.getLocalName()))
            return null;

        DmlShapeStyleReader shapeStyleReader = new DmlShapeStyleReader(reader, complianceInfo);

        DmlShapeStyle style = new DmlShapeStyle();
        while (reader.readChild("style"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"effectRef"*/0:
                    style.setEffectReference(shapeStyleReader.readEffectReference());
                    break;
                case /*"fillRef"*/1:
                    style.setFillReference(shapeStyleReader.readFillReference());
                    break;
                case /*"fontRef"*/2:
                    style.setFontReference(shapeStyleReader.readFontReference());
                    break;
                case /*"lnRef"*/3:
                    style.setLineReference(shapeStyleReader.reaLineReference());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
        return style;
    }

    private DmlLineReference reaLineReference()
    {
        DmlLineReference lineReference = new DmlLineReference();
        lineReference.setStyleMatrixIndex(mReader.readIntAttribute("idx", 0));
        readReferenceContent(lineReference);
        return lineReference;
    }


    private DmlFontReference readFontReference()
    {
        DmlFontReference fontReference = new DmlFontReference();
        String idxValue = mReader.readAttribute("idx", "");
        fontReference.setFontCollectionIndex(DmlEnum.dmlToFontCollectionIndex(idxValue));
        readReferenceContent(fontReference);
        return fontReference;
    }

    private DmlFillReference readFillReference()
    {
        DmlFillReference fillReference = new DmlFillReference();
        fillReference.setStyleMatrixIndex(mReader.readIntAttribute("idx", 0));
        readReferenceContent(fillReference);
        return fillReference;
    }

    private DmlEffectReference readEffectReference()
    {
        DmlEffectReference effectReference = new DmlEffectReference();
        effectReference.setStyleMatrixIndex(mReader.readIntAttribute("idx", 0));
        readReferenceContent(effectReference);
        return effectReference;
    }

    private void readReferenceContent(DmlStyleReferenceBase reference)
    {
        String currentTagName = mReader.getLocalName();
        while (mReader.readChild(currentTagName))
        {
            DmlColor newColor = DmlColorReader.read(mReader, mComplianceInfo);
            // Reader returns null if it founds unknown tag.
            // We filter null value to disable overwriting of
            // already initialized Color property by null values.
            if (newColor != null)
                reference.setColor(newColor);
        }
    }

    private /*final*/ NrxXmlReader mReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"effectRef",
		"fillRef",
		"fontRef",
		"lnRef"
	);

}

