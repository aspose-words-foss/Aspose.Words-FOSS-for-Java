// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Builds objects of DmlOutline class using xml.
/// </summary>
class DmlOutlineReader extends DmlReaderBase
{
    private DmlOutlineReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Builds an object of type DmlOutline using the provided reader.
    /// </summary>
    static DmlOutline read(DocxDocumentReaderBase reader) throws Exception
    {
        Debug.assertTrue(reader != null);

        DmlOutlineReader outlineReader = new DmlOutlineReader(reader);
        String tagName = reader.getXmlReader().getLocalName();

        if (!"ln".equals(tagName) && !"textOutline".equals(tagName) && !"uLn".equals(tagName) && !"hiddenLine".equals(tagName))
            return null;

        DmlOutline outline = new DmlOutline();
        outlineReader.readAttributes(outline);
        outlineReader.readChildTags(outline);
        return outline;
    }

    private void readChildTags(DmlOutline outline) throws Exception
    {
        boolean isText = ("textOutline".equals(getXmlReader().getLocalName()));

        String elementName = getXmlReader().getLocalName();
        while (getXmlReader().readChild(elementName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"gradFill"*/0:
                case /*"pattFill"*/1:
                case /*"noFill"*/2:
                case /*"solidFill"*/3:
                {
                    outline.setFill((isText)
                        ? DmlFillReader.readTextOutlineFill(mDocumentReader)
                        : DmlFillReader.read(mDocumentReader));
                    break;
                }
                case /*"prstDash"*/4:
                    outline.setDash(readPresetDash());
                    break;
                case /*"custDash"*/5:
                    outline.setDash(readCustomDash());
                    break;
                case /*"headEnd"*/6:
                    outline.setHeadLineEndStyle(new DmlHeadLineEndStyle());
                    readLineEndStyle(outline.getHeadLineEndStyle());
                    break;
                case /*"tailEnd"*/7:
                    outline.setTailLineEndStyle(new DmlTailLineEndStyle());
                    readLineEndStyle(outline.getTailLineEndStyle());
                    break;
                case /*"bevel"*/8:
                    outline.setLineJoinStyle(JoinStyle.BEVEL);
                    break;
                case /*"miter"*/9:
                    readMiter(outline);
                    break;
                case /*"round"*/10:
                    outline.setLineJoinStyle(JoinStyle.ROUND);
                    break;
                case /*"extLst"*/11:
                    outline.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 5.1.10.43 miter (Miter Line Join).
    /// This element specifies that a line join shall be mitered.
    /// </summary>
    private void readMiter(DmlOutline outline)
    {
        outline.setLineJoinStyle(JoinStyle.MITER);
        outline.setLineMiterLimit(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("lim", ""), getComplianceInfo()));
    }

    /// <summary>
    /// Reads 20.1.8.38 headEnd (Line Head/End Style)
    /// or 20.1.8.57 tailEnd (Tail line end style)
    /// </summary>
    private void readLineEndStyle(DmlLineEndStyle endStyle)
    {
        endStyle.setLength(readLineEndLength("len"));
        endStyle.setType(readLineEndType());
        endStyle.setWidth(readLineEndWidth("w"));
    }

    /// <summary>
    /// Reads values of the ST_LineEndLength simple type
    /// </summary>
    private /*ArrowLength*/int readLineEndLength(String attribureName)
    {
        String attrValue = getXmlReader().readAttribute(attribureName, "");
        return DmlEnum.dmlToArrowLength(attrValue);
    }

    /// <summary>
    /// Reads values of the ST_LineEndWidth simple type
    /// </summary>
    private /*ArrowWidth*/int readLineEndWidth(String attribureName)
    {
        String attrValue = getXmlReader().readAttribute(attribureName, "");
        return DmlEnum.dmlToArrowWidth(attrValue);
    }

    /// <summary>
    /// Reads a value of the ST_LineEndType simple type.
    /// </summary>
    private /*ArrowType*/int readLineEndType()
    {
        String attrValue = getXmlReader().readAttribute("type", "");
        return DmlEnum.dmlToArrowType(attrValue);
    }

    /// <summary>
    /// Reads 20.1.8.48 prstDash tag.
    /// </summary>
    private DmlDash readPresetDash()
    {
        DmlPresetDash dash = new DmlPresetDash();
        String val = getXmlReader().readAttribute("solid");
        dash.setPreset(DmlEnum.dmlToPresetDashType(val));
        return dash;
    }

    /// <summary>
    /// Reads 20.1.8.21 custDash (Custom Dash) tag.
    /// </summary>
    private DmlDash readCustomDash()
    {
        DmlCustomDash dash = new DmlCustomDash();
        while (getXmlReader().readChild("custDash"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"ds"*/12:
                    msArrayList.add(dash.getDashStops(), readDashStop());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return dash;
    }

    /// <summary>
    /// Reads 20.1.8.22 ds (Dash Stop) tag.
    /// </summary>
    private DmlDashStop readDashStop()
    {
        DmlDashStop dashStop = new DmlDashStop();
        dashStop.setDashLength(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("d", ""), getComplianceInfo()));
        dashStop.setSpaceLength(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("sp", ""), getComplianceInfo()));
        return dashStop;
    }


    private void readAttributes(DmlOutline outline)
    {
        readWidth(outline);
        readCompoundLineType(outline);
        readLineEndingCap(outline);
        readStrokeAlignment(outline);
    }

    /// <summary>
    /// Read values of the ST_PenAlignment simple type.
    /// </summary>
    private void readStrokeAlignment(DmlOutline outline)
    {
        String attrValue = getXmlReader().readAttribute("algn", "");

        if (StringUtil.hasChars(attrValue))
            outline.setStrokeAlignment(("in".equals(attrValue)));
    }

    /// <summary>
    /// Read values of the ST_LineCap simple type.
    /// </summary>
    private void readLineEndingCap(DmlOutline outline)
    {
        String attrValue = getXmlReader().readAttribute("cap", "");

        if (StringUtil.hasChars(attrValue))
            outline.setEndCap(DmlEnum.dmlToLineEndingCapType(attrValue));
    }

    /// <summary>
    /// Read values of the ST_CompoundLine simple type.
    /// </summary>
    private void readCompoundLineType(DmlOutline outline)
    {
        String attrValue = getXmlReader().readAttribute("cmpd", "");

        if (StringUtil.hasChars(attrValue))
            outline.setCompoundLineType(DmlEnum.dmlToCompoundLineType(attrValue));
    }

    private void readWidth(DmlOutline outline)
    {
        // If this attribute is omitted, then write nothing (then width from outline style will be used).
        double attrValue = getXmlReader().readDoubleAttribute("w", Double.NaN);

        if (!Double.isNaN(attrValue))
            outline.setWidthInEmus(attrValue);
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private OoxmlComplianceInfo getComplianceInfo() { return mDocumentReader.getComplianceInfo(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"gradFill",
		"pattFill",
		"noFill",
		"solidFill",
		"prstDash",
		"custDash",
		"headEnd",
		"tailEnd",
		"bevel",
		"miter",
		"round",
		"extLst",
		"ds"
	);

}

