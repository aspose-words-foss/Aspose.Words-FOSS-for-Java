// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Class used to read DML diagram style definition.
/// </summary>
class DmlDiagramStylesReader extends DmlReaderBase
{
    private DmlDiagramStylesReader(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo)
    {
        mDocumentReader = reader;
        mXmlReader = reader.getXmlReader();
        mComplianceInfo = complianceInfo;
    }

    static DmlDiagramStyleDefinition read(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo) throws Exception
    {
        DmlDiagramStylesReader diagramStylesReader = new DmlDiagramStylesReader(reader, complianceInfo);
        return diagramStylesReader.readCore();
    }

    private DmlDiagramStyleDefinition readCore() throws Exception
    {
        if (!"styleDef".equals(mXmlReader.getLocalName()))
            return null;

        DmlDiagramStyleDefinition styles = new DmlDiagramStyleDefinition();
        styles.setUniqueId(mXmlReader.readAttribute("uniqueId", ""));
        styles.setMinVersion(mXmlReader.readAttribute("minVer", ""));

        ArrayList<DmlStyleLabel> labelList = new ArrayList<DmlStyleLabel>();
        ArrayList<DmlDiagramString> titles = new ArrayList<DmlDiagramString>();
        ArrayList<DmlDiagramString> descriptions = new ArrayList<DmlDiagramString>();
        while (mXmlReader.readChild("styleDef"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"title"*/0:
                    msArrayList.add(titles, DmlDiagramComplexTypesReader.readString(mXmlReader));
                    break;
                case /*"desc"*/1:
                    msArrayList.add(descriptions, DmlDiagramComplexTypesReader.readString(mXmlReader));
                    break;
                case /*"catLst"*/2:
                    styles.setCategories(DmlDiagramComplexTypesReader.readCategories(mXmlReader));
                    break;
                case /*"styleLbl"*/3:
                    msArrayList.add(labelList, readStyleLabel());
                    break;
                case /*"scene3d"*/4:
                    styles.setScene3D(DmlScene3DReader.readScene3DProperties(mDocumentReader, mComplianceInfo));
                    break;
                case /*"extLst"*/5:
                    styles.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        styles.setStyleLabels(msArrayList.toArray(labelList, new DmlStyleLabel[0]));
        styles.setTitles(msArrayList.toArray(titles, new DmlDiagramString[0]));
        styles.setDescriptions(msArrayList.toArray(descriptions, new DmlDiagramString[0]));

        return styles;
    }

    private DmlStyleLabel readStyleLabel() throws Exception
    {
        DmlStyleLabel label = new DmlStyleLabel();
        label.setName(mXmlReader.readAttribute("name", ""));

        while (mXmlReader.readChild("styleLbl"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"style"*/6:
                    label.setStyle(DmlShapeStyleReader.read(mXmlReader, mComplianceInfo));
                    break;
                case /*"txPr"*/7:
                    label.setTextProperties(readTextProperties());
                    break;
                case /*"scene3d"*/4:
                    label.setScene3D(DmlScene3DReader.readScene3DProperties(mDocumentReader, mComplianceInfo));
                    break;
                case /*"sp3d"*/8:
                    label.setShape3D(DmlScene3DReader.readShape3DProperties(mDocumentReader, mComplianceInfo));
                    break;
                case /*"extLst"*/5:
                    label.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return label;
    }

    private DmlDiagramTextPr readTextProperties() throws Exception
    {
        DmlDiagramTextPr txPr = new DmlDiagramTextPr();

        while (mXmlReader.readChild("txPr"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"flatTx"*/9:
                    txPr.setFlatText(readFlatText());
                    break;
                case /*"sp3d"*/8:
                    txPr.setShape3DProperties(DmlScene3DReader.readShape3DProperties(mDocumentReader, mComplianceInfo));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return txPr;
    }

    private DmlFlatText readFlatText()
    {
        DmlFlatText flatText = new DmlFlatText();
        flatText.setZCoordinate(mXmlReader.readAttributeAsEmus("z", 0.0, mComplianceInfo));
        return flatText;
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;
    private /*final*/ NrxXmlReader mXmlReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"title",
		"desc",
		"catLst",
		"styleLbl",
		"scene3d",
		"extLst",
		"style",
		"txPr",
		"sp3d",
		"flatTx"
	);

}

