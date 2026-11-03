// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/26/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcRelationship;
import com.aspose.OpcPackagePart;


class DmlContentPartReader extends DmlReaderBase
{
    private DmlContentPartReader()
    {
    }

    static Shape read(DocxDocumentReaderBase reader) throws Exception
    {
        String tagName = reader.getXmlReader().getLocalName();
        if (!"contentPart".equals(tagName))
            return null;

        Shape dml = new Shape(reader.getDocument(), ShapeMarkupLanguage.DML);
        reader.addAndPushContainer(dml);
        dml.setDmlNode(readCore(reader));
        reader.popContainer(NodeType.SHAPE);

        return dml;
    }

    private static DmlContentPart readCore(DocxDocumentReaderBase reader) throws Exception
    {
        DmlContentPart contentPart = new DmlContentPart();

        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        contentPart.setBWMode(DmlEnum.dmlToBWMode(xmlReader.readAttribute("bwMode", "auto")));

        // Keep content of content part unparsed.
        String contentPartId = xmlReader.readAttribute("id", "");
        contentPart.setContentPart(reader.fetchPartByName(reader.getRelationshipTarget(contentPartId)));

        // Also store unparsed in the model all internal child parts that are related to the theme.
        for (OpcRelationship rel : contentPart.getContentPart().getRels())
        {
            if (!rel.isExternal())
            {
                OpcPackagePart part = reader.fetchPartByName(contentPart.getContentPart().getRelatedPartName(rel));
                contentPart.getRelatedParts().put(rel.getId(), part);
            }
        }

        xmlReader.moveToElement();

        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"nvContentPartPr"*/0:
                {
                    if (contentPart.getNonVisualPr() == null)
                        contentPart.setNonVisualPr(new DmlNvPrContentPart());
                    DmlNodePropertiesReader.readNonVisualProperties(contentPart.getNonVisualPr(), reader);
                    break;
                }
                case /*"xfrm"*/1:
                    contentPart.setTransform(DmlTransformReader.readTransform(xmlReader, complianceInfo));
                    break;
                case /*"extLst"*/2:
                    contentPart.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                case /*"nvPr"*/3:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return contentPart;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"nvContentPartPr",
		"xfrm",
		"extLst",
		"nvPr"
	);

}

