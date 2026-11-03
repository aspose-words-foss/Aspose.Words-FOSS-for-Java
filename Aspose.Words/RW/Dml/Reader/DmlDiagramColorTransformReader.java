// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Class used to read DML diagram color transform.
/// </summary>
class DmlDiagramColorTransformReader extends DmlReaderBase
{
    private DmlDiagramColorTransformReader(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo)
    {
        mDocumentReader = reader;
        mXmlReader = reader.getXmlReader();
        mComplianceInfo = complianceInfo;
    }

    static DmlDiagramColorTransform read(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo) throws Exception
    {
        DmlDiagramColorTransformReader colorTransformReader = new DmlDiagramColorTransformReader(reader, complianceInfo);
        return colorTransformReader.readCode();
    }

    private DmlDiagramColorTransform readCode() throws Exception
    {
        if (!"colorsDef".equals(mXmlReader.getLocalName()))
            return null;

        DmlDiagramColorTransform transform = new DmlDiagramColorTransform();
        transform.setUniqueId(mXmlReader.readAttribute("uniqueId", ""));
        transform.setMinVersion(mXmlReader.readAttribute("minVer", ""));

        ArrayList<DmlColorTransformationStyleLabel> lableList = new ArrayList<DmlColorTransformationStyleLabel>();
        ArrayList<DmlDiagramString> titles = new ArrayList<DmlDiagramString>();
        ArrayList<DmlDiagramString> descriptions = new ArrayList<DmlDiagramString>();
        while (mXmlReader.readChild("colorsDef"))
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
                    transform.setCategories(DmlDiagramComplexTypesReader.readCategories(mXmlReader));
                    break;
                case /*"styleLbl"*/3:
                    msArrayList.add(lableList, readStyleLabel());
                    break;
                case /*"extLst"*/4:
                    transform.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        transform.setStyleLabels(msArrayList.toArray(lableList, new DmlColorTransformationStyleLabel[0]));
        transform.setTitles(msArrayList.toArray(titles, new DmlDiagramString[0]));
        transform.setDescriptions(msArrayList.toArray(descriptions, new DmlDiagramString[0]));

        return transform;
    }

    private DmlColorTransformationStyleLabel readStyleLabel() throws Exception
    {
        DmlColorTransformationStyleLabel label = new DmlColorTransformationStyleLabel();
        label.setName(mXmlReader.readAttribute("name", ""));

        while (mXmlReader.readChild("styleLbl"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"fillClrLst"*/5:
                    label.setFillColorList(readColors());
                    break;
                case /*"linClrLst"*/6:
                    label.setLineColorList(readColors());
                    break;
                case /*"effectClrLst"*/7:
                    label.setEffectColorList(readColors());
                    break;
                case /*"txLinClrLst"*/8:
                    label.setTextLineColorList(readColors());
                    break;
                case /*"txFillClrLst"*/9:
                    label.setTextFillColorList(readColors());
                    break;
                case /*"txEffectClrLst"*/10:
                    label.setTextEffectColorList(readColors());
                    break;
                case /*"extLst"*/4:
                    label.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }
        return label;
    }

    private DmlDiagramColors readColors()
    {
        String tag = mXmlReader.getLocalName();

        DmlDiagramColors colors = new DmlDiagramColors();
        colors.setColorApplicationMethod(DmlDiagramEnum.dmlToColorApplicationMethod(mXmlReader.readAttribute("meth", "span")));
        colors.setHueDirection(DmlDiagramEnum.dmlToHueDirection(mXmlReader.readAttribute("hueDir", "cw")));

        ArrayList<DmlColor> colorList = new ArrayList<DmlColor>();
        while (mXmlReader.readChild(tag))
        {
            DmlColor color = DmlColorReader.read(mXmlReader, mComplianceInfo);
            if(color != null)
                msArrayList.add(colorList, color);
        }
        colors.setColors(msArrayList.toArray(colorList, new DmlColor[0]));

        return colors;
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
		"extLst",
		"fillClrLst",
		"linClrLst",
		"effectClrLst",
		"txLinClrLst",
		"txFillClrLst",
		"txEffectClrLst"
	);

}

