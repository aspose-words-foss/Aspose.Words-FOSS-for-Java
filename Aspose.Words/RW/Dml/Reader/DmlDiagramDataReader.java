// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.MathUtil;


/// <summary>
/// Class used to read DML diagram data model.
/// </summary>
class DmlDiagramDataReader extends DmlReaderBase
{
    private DmlDiagramDataReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static DmlDiagramDataModel read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        if (!"dataModel".equals(xmlReader.getLocalName()))
            return null;

        DmlDiagramDataReader diagramDataReader = new DmlDiagramDataReader(reader);

        DmlDiagramDataModel data = new DmlDiagramDataModel();
        while (xmlReader.readChild("dataModel"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"ptLst"*/0:
                    data.setPointList(diagramDataReader.readPointList());
                    break;
                case /*"cxnLst"*/1:
                    data.setConnectionList(diagramDataReader.readConnectionList());
                    break;
                case /*"whole"*/2:
                    data.setWholeFormatting(diagramDataReader.readWhole());
                    break;
                case /*"bg"*/3:
                    data.setBackground(diagramDataReader.readBackground());
                    break;
                case /*"extLst"*/4:
                    data.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return data;
    }

    private DmlDiagramBackground readBackground() throws Exception
    {
        DmlDiagramBackground result = new DmlDiagramBackground();
        while (getXmlReader().readChild("bg"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"noFill"*/5:
                case /*"solidFill"*/6:
                case /*"gradFill"*/7:
                case /*"blipFill"*/8:
                case /*"pattFill"*/9:
                case /*"grpFill"*/10:
                    result.setFill(DmlFillReader.read(mDocumentReader));
                    break;
                case /*"effectLst"*/11:
                    result.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, false, false));
                    break;
                case /*"effectDag"*/12:
                    result.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, false, true));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return result;
    }

    private DmlDiagramWholeFormatting readWhole() throws Exception
    {
        DmlDiagramWholeFormatting result = new DmlDiagramWholeFormatting();
        while (getXmlReader().readChild("whole"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"ln"*/13:
                    result.setOutline(DmlOutlineReader.read(mDocumentReader));
                    break;
                case /*"effectLst"*/11:
                    result.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, false, false));
                    break;
                case /*"effectDag"*/12:
                    result.setEffects(DmlShapeEffectReader.readEffects(mDocumentReader, false, true));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return result;
    }

    private DmlDiagramConnection[] readConnectionList()
    {
        ArrayList<DmlDiagramConnection> result = new ArrayList<DmlDiagramConnection>();
        while (getXmlReader().readChild("cxnLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"cxn"*/14:
                    msArrayList.add(result, readConnection());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return msArrayList.toArray(result, new DmlDiagramConnection[0]);
    }

    private DmlDiagramConnection readConnection()
    {
        DmlDiagramConnection connection = new DmlDiagramConnection();
        connection.setModelId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("modelId", "")));
        connection.setType(DmlDiagramEnum.dmlToConnectionType(getXmlReader().readAttribute("type", "parOf")));
        connection.setSourceId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("srcId", "")));
        connection.setDestinationId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("destId", "")));
        connection.setSourceOrder(getXmlReader().readIntAttribute("srcOrd", 0));
        connection.setDestinationOrder(getXmlReader().readIntAttribute("destOrd", 0));
        connection.setParentTransitionId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("parTransId", "0")));
        connection.setSiblingTransitionId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("sibTransId", "0")));
        connection.setPresentationId(getXmlReader().readAttribute("presId", ""));
        return connection;
    }

    private DmlDiagramPoint[] readPointList() throws Exception
    {
        ArrayList<DmlDiagramPoint> result = new ArrayList<DmlDiagramPoint>();
        while (getXmlReader().readChild("ptLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"pt"*/15:
                    msArrayList.add(result, readPoint());
                    break;
                case /*"AlternateContent"*/16:
                    msArrayList.add(result, readPointAlternateContent());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return msArrayList.toArray(result, new DmlDiagramPoint[0]);
    }

    private DmlDiagramPoint readPointAlternateContent() throws Exception
    {
        DmlDiagramPoint point = new DmlDiagramPoint();

        // Preserve all attributes of AlternateContent to restore them on write.
        while (getXmlReader().moveToNextAttribute(false))
        {
            String attr = StringUtil.hasChars(getXmlReader().getPrefix())
                ? msString.format("{0}:{1}", getXmlReader().getPrefix(), getXmlReader().getLocalName())
                : getXmlReader().getLocalName();
            msDictionary.add(point.getAlternateContentAttributes(), attr, getXmlReader().getValue());
        }

        getXmlReader().moveToElement();
        while (getXmlReader().readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"Choice"*/17:
                    readPointChoice(point);
                    break;
                case /*"Fallback"*/18:
                    readPointFallback(point);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return point;
    }

    private void readPointFallback(DmlDiagramPoint point) throws Exception
    {
        point.setAlternateContentFallbackPoint(new DmlDiagramPoint());
        while (getXmlReader().readChild("Fallback"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"pt"*/15:
                    readPointCore(point.getAlternateContentFallbackPoint());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private void readPointChoice(DmlDiagramPoint point) throws Exception
    {
        while (getXmlReader().moveToNextAttribute(false))
        {
            String attr = StringUtil.hasChars(getXmlReader().getPrefix())
                ? msString.format("{0}:{1}", getXmlReader().getPrefix(), getXmlReader().getLocalName())
                : getXmlReader().getLocalName();
            msDictionary.add(point.getAlternateContentChoiceAttributes(), attr, getXmlReader().getValue());
        }

        getXmlReader().moveToElement();
        while (getXmlReader().readChild("Choice"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"pt"*/15:
                    readPointCore(point);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private DmlDiagramPoint readPoint() throws Exception
    {
        DmlDiagramPoint point = new DmlDiagramPoint();
        readPointCore(point);
        return point;
    }

    private void readPointCore(DmlDiagramPoint point) throws Exception
    {
        point.setModelId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("modelId", "")));
        point.setType(DmlDiagramEnum.dmlToPointType(getXmlReader().readAttribute("type", "node")));
        point.setConnectionId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("cxnId", "0")));

        while (getXmlReader().readChild("pt"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"spPr"*/19:
                    point.setShapeProperties(new DmlShapeProperties(mDocumentReader.getDocument()));
                    DmlNodePropertiesReader.readShapeProperties(mDocumentReader, point.getShapeProperties());
                    break;
                case /*"t"*/20:
                    point.setTextBody(new DmlTextBody());
                    DmlTextShapeReader.readTextBody(mDocumentReader, point.getTextBody(), "t");
                    break;
                case /*"prSet"*/21:
                    point.setPropertySet(readPropertySet());
                    break;
                case /*"extLst"*/4:
                    point.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private DmlPropertySet readPropertySet()
    {
        DmlPropertySet prSet = new DmlPropertySet();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"presAssocID"*/22:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PRES_ASSOC_ID, DmlDiagramEnum.dmlToModelId(getXmlReader().getValue()));
                    break;
                case /*"presName"*/23:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PRES_NAME, getXmlReader().getValue());
                    break;
                case /*"presStyleLbl"*/24:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PRES_STYLE_LBL, getXmlReader().getValue());
                    break;
                case /*"presStyleIdx"*/25:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PRES_STYLE_IDX, getXmlReader().getValueAsInt());
                    break;
                case /*"presStyleCnt"*/26:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PRES_STYLE_CNT, getXmlReader().getValueAsInt());
                    break;
                case /*"loTypeId"*/27:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.LO_TYPE_ID, getXmlReader().getValue());
                    break;
                case /*"loCatId"*/28:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.LO_CAT_ID, getXmlReader().getValue());
                    break;
                case /*"qsTypeId"*/29:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.QS_TYPE_ID, getXmlReader().getValue());
                    break;
                case /*"qsCatId"*/30:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.QS_CAT_ID, getXmlReader().getValue());
                    break;
                case /*"csTypeId"*/31:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CS_TYPE_ID, getXmlReader().getValue());
                    break;
                case /*"csCatId"*/32:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CS_CAT_ID, getXmlReader().getValue());
                    break;
                case /*"coherent3DOff"*/33:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.COHERENT_3_D_OFF, getXmlReader().getValueAsBool());
                    break;
                case /*"phldrT"*/34:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PHLDR_T, getXmlReader().getValue());
                    break;
                case /*"phldr"*/35:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.PHLDR, getXmlReader().getValueAsBool());
                    break;
                case /*"custAng"*/36:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_ANG, getXmlReader().getValueAsInt());
                    break;
                case /*"custFlipVert"*/37:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_FLIP_VERT, getXmlReader().getValueAsBool());
                    break;
                case /*"custFlipHor"*/38:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_FLIP_HOR, getXmlReader().getValueAsBool());
                    break;
                case /*"custSzX"*/39:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_SZ_X, getXmlReader().getValueAsInt());
                    break;
                case /*"custSzY"*/40:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_SZ_Y, getXmlReader().getValueAsInt());
                    break;
                case /*"custScaleX"*/41:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_SCALE_X, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custScaleY"*/42:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_SCALE_Y, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custT"*/43:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_T, getXmlReader().getValueAsBool());
                    break;
                case /*"custLinFactX"*/44:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_LIN_FACT_X, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custLinFactY"*/45:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_LIN_FACT_Y, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custLinFactNeighborX"*/46:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_X, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custLinFactNeighborY"*/47:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_Y, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custRadScaleRad"*/48:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_RAD_SCALE_RAD, fromPercent(getXmlReader().getValue()));
                    break;
                case /*"custRadScaleInc"*/49:
                    prSet.getPrSet().setProperty(DmlPropertySetAttr.CUST_RAD_SCALE_INC, fromPercent(getXmlReader().getValue()));
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        while (getXmlReader().readChild("prSet"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"presLayoutVars"*/50:
                    prSet.setLayoutVariablePropertySet(DmlDiagramComplexTypesReader.readLayoutVariableList(getXmlReader()));
                    break;
                case /*"style"*/51:
                    prSet.setStyle(DmlShapeStyleReader.read(getXmlReader(), mDocumentReader.getComplianceInfo()));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return prSet;
    }

    /// <summary>
    /// Converts string percent value in 1000th of percent, if no percent sign, or in whole percents with percent
    /// sign to integer value in 1000th of percent.
    /// </summary>
    private int fromPercent(String value)
    {
        double asFraction = DmlPercentageUtil.fromPercentOrDmlPercent(value, mDocumentReader.getComplianceInfo());
        return MathUtil.doubleToInt(DmlPercentageUtil.toDmlPercent(asFraction));
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ptLst",
		"cxnLst",
		"whole",
		"bg",
		"extLst",
		"noFill",
		"solidFill",
		"gradFill",
		"blipFill",
		"pattFill",
		"grpFill",
		"effectLst",
		"effectDag",
		"ln",
		"cxn",
		"pt",
		"AlternateContent",
		"Choice",
		"Fallback",
		"spPr",
		"t",
		"prSet",
		"presAssocID",
		"presName",
		"presStyleLbl",
		"presStyleIdx",
		"presStyleCnt",
		"loTypeId",
		"loCatId",
		"qsTypeId",
		"qsCatId",
		"csTypeId",
		"csCatId",
		"coherent3DOff",
		"phldrT",
		"phldr",
		"custAng",
		"custFlipVert",
		"custFlipHor",
		"custSzX",
		"custSzY",
		"custScaleX",
		"custScaleY",
		"custT",
		"custLinFactX",
		"custLinFactY",
		"custLinFactNeighborX",
		"custLinFactNeighborY",
		"custRadScaleRad",
		"custRadScaleInc",
		"presLayoutVars",
		"style"
	);

}

