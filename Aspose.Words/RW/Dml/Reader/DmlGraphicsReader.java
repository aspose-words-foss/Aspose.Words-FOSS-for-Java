// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/14/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.Debug;


/// <summary>
/// Class reads 'graphics' element of DrawingML and returns the corresponding <see cref="DmlNode"/>
/// </summary>
class DmlGraphicsReader
{
    private DmlGraphicsReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static ShapeBase read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlGraphicsReader graphicsReader = new DmlGraphicsReader(reader);
        return graphicsReader.readGraphic();
    }

    /// <summary>
    /// Reads 5.1.2.1.16 graphic (Graphic Object).
    /// </summary>
    private ShapeBase readGraphic() throws Exception
    {
        // Has elements only.
        while (getXmlReader().readChild("graphic"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"graphicData"*/0:
                    return readGraphicData();
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
        return null;
    }

    /// <summary>
    /// Reads 5.1.2.1.17 graphicData (Graphic Object Data).
    /// This element specifies the reference to a graphic object within the document.
    /// This graphic object is provided entirely by the document authors who choose to
    /// persist this data within the document.
    /// </summary>
    private ShapeBase readGraphicData() throws Exception
    {
        // Read attributes.
        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"uri"*/1:
                    // This attribute tells the consumer how to interpret the graphicData.
                    // Office supports a set of specific uri values:
                    // http://schemas.openxmlformats.org/drawingml/2006/chart
                    // http://schemas.openxmlformats.org/drawingml/2006/compatibility
                    // http://schemas.openxmlformats.org/drawingml/2006/diagram
                    // http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas
                    // http://schemas.openxmlformats.org/drawingml/2006/picture
                    // http://schemas.openxmlformats.org/drawingml/2006/table
                    // http://schemas.openxmlformats.org/drawingml/2006/ole
                    // http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas
                    // http://schemas.microsoft.com/office/word/2010/wordprocessingGroup
                    // http://schemas.microsoft.com/office/word/2010/wordprocessingShape
                    break;
                default:
                    getXmlReader().warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, getXmlReader().getLocalName()));
                    break;
            }
        }

        // Read elements.
        // The spec says vaguely that any element from any namespace can occur here. We only support some of course.
        while (getXmlReader().readChild("graphicData"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"lockedCanvas"*/2:
                case /*"wpc"*/3: // wpc seems to be analog of locked canvas in Word 2013, the only difference it is editable.
                    return DmlCompositeNodeReader.readLockedCanvas(mDocumentReader);
                case /*"pic"*/4:
                    return DmlPictureReader.read(mDocumentReader);
                case /*"chart"*/5:
                    return DmlChartReader.read(mDocumentReader);
                case /*"relIds"*/6:
                    return DmlDiagramReader.read(mDocumentReader);
                case /*"wsp"*/7:   //ISO 29500 extension Wordprocessing shape.
                case /*"sp"*/8:    // I never saw sp as direct child of graphicData, but if wsp can be its child sp can too.
                    return DmlShapeReader.read(mDocumentReader);
                case /*"wgp"*/9:   // ISO 29500 extension Wordprocessing group shape.
                case /*"grpSp"*/10: // I never saw grpSp as direct child of graphicData, but if wpg can be its child grpSp can too.
                    return DmlCompositeNodeReader.readGroup(mDocumentReader);
                case /*"contentPart"*/11: // ISO 29500 17.3.3.2 contentPart (Content Part)
                    return DmlContentPartReader.read(mDocumentReader);
                case /*"AlternateContent"*/12: // WORDSNET-11918 Read Choose from this AlternateContent.
                    return readAlternateContent();
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
        return null;
    }

    /// <summary>
    /// Reads M.7.3.4.2 AlternateContent (Alternate Content block).
    /// An alternate content block allows for an alternative representation of information.
    /// </summary>
    private Shape readAlternateContent() throws Exception
    {
        Shape shape = null;
        while (getXmlReader().readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"Choice"*/13:
                    shape = readAlternateContentCore();
                    break;
                case /*"Fallback"*/14:
                    getXmlReader().ignoreElement();
                    break;
                default:
                    Debug.fail(getXmlReader().getLocalName());
                    getXmlReader().ignoreElement();
                    break;
            }
        }

        return shape;
    }

    /// <summary>
    /// Reads M.7.3.4.2.1 AlternateContent Syntax
    /// </summary>
    private Shape readAlternateContentCore() throws Exception
    {
        String tagName = getXmlReader().getLocalName();

        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"contentPart"*/11:
                    return DmlContentPartReader.read(mDocumentReader);
                default:
                    Debug.fail(getXmlReader().getLocalName());
                    getXmlReader().ignoreElement();
                    break;
            }
        }

        return null;
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"graphicData",
		"uri",
		"lockedCanvas",
		"wpc",
		"pic",
		"chart",
		"relIds",
		"wsp",
		"sp",
		"wgp",
		"grpSp",
		"contentPart",
		"AlternateContent",
		"Choice",
		"Fallback"
	);

}

