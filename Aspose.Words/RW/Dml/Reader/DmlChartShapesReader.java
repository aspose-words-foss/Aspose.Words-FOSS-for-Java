// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.FormatterPal;


/// <summary>
/// Class for reading 5.7.2.221 userShapes (User Shapes).
/// </summary>
class DmlChartShapesReader extends DmlReaderBase
{
    private DmlChartShapesReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static void read(DocxDocumentReaderBase reader, String userShapesId) throws Exception
    {
        reader.switchToPartReaderByRelId(userShapesId);
        read(reader);
        reader.restorePartReader();
    }

    private static void read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlChartShapesReader chartShapesReader = new DmlChartShapesReader(reader);

        while (reader.getXmlReader().readChild("userShapes"))
            chartShapesReader.readUserShape();
    }

    private void readUserShape() throws Exception
    {
        String elementName = getXmlReader().getLocalName();
        ShapeBase shape = null;

        /*PointF*/long from = msPointF.Empty;
        /*PointF*/long to = msPointF.Empty;

        while (getXmlReader().readChild(elementName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"from"*/0:
                    from = readAnchorPoint();
                    break;
                case /*"to"*/1:
                case /*"ext"*/2:
                    to = readAnchorPoint();
                    break;
                case /*"cxnSp"*/3:
                case /*"sp"*/4:
                    shape = DmlShapeReader.read(mDocumentReader);
                    break;
                case /*"grpSp"*/5:
                    shape = DmlCompositeNodeReader.readGroup(mDocumentReader);
                    break;
                case /*"pic"*/6:
                    shape = DmlPictureReader.read(mDocumentReader);
                    break;
                case /*"graphicFrame"*/7:
                    // Attempts to create document with graphic frame in user shapes
                    // failed while resolving WORDSNET-14542, so currently skip this item.
                    warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        if (shape != null)
        {
            // WORDSNET-14006 Mimic MS Word behavior - remove empty group shape.
            if (shape.isGroup() && !shape.hasChildNodes())
            {
                shape.remove();
            }
            else
            {
                shape.setFrom(from);
                shape.setTo(to);
                shape.setAnchorType(getAnchorType(elementName));
            }
        }
    }

    private /*PointF*/long readAnchorPoint()
    {
        String elementName = getXmlReader().getLocalName();

        // WORDSNET-15569 cx cy might be attributes.
        float x = (float)getXmlReader().readDoubleAttribute("cx", 0.0);
        float y = (float)getXmlReader().readDoubleAttribute("cy", 0.0);

        while (getXmlReader().readChild(elementName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"x"*/8:
                case /*"cx"*/9:
                    x = (float)FormatterPal.parseDouble(getXmlReader().readString());
                    break;
                case /*"y"*/10:
                case /*"cy"*/11:
                    y = (float)FormatterPal.parseDouble(getXmlReader().readString());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return msPointF.ctor(x, y);
    }

    private static /*DmlChartUserShapeAnchorType*/int getAnchorType(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"absSizeAnchor"*/12:
                return DmlChartUserShapeAnchorType.ABSOLUTE;
            case /*"relSizeAnchor"*/13:
            default:
                return DmlChartUserShapeAnchorType.RELATIVE;
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"from",
		"to",
		"ext",
		"cxnSp",
		"sp",
		"grpSp",
		"pic",
		"graphicFrame",
		"x",
		"cx",
		"y",
		"cy",
		"absSizeAnchor",
		"relSizeAnchor"
	);

}

