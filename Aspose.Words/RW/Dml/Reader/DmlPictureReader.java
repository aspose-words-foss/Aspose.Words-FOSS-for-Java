// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Reads "pic" tag.
/// </summary>
class DmlPictureReader extends DmlReaderBase
{
    private DmlPictureReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static Shape read(DocxDocumentReaderBase reader)
    {
        DmlPictureReader pictureBuilder = new DmlPictureReader(reader);
        return pictureBuilder.readCore();
    }

    private Shape readCore()
    {
        try
        {
            if (!"pic".equals(getXmlReader().getLocalName()))
                return null;
            readPicture();
            return mPicture;
        }
        catch (UnsupportedOperationException e)
        {
            // Not a broken picture: the caller asked for something this build cannot do.
            throw e;
        }
        catch(Exception e)
        {
            // We are failed to read shape from xml.
            // In the case null will be returned to a client.
            return null;
        }
    }

    /// <summary>
    /// Reads the picture.
    /// </summary>
    private void readPicture() throws Exception
    {
        mPicture = new Shape(mDocumentReader.getDocument(), ShapeMarkupLanguage.DML);
        mDocumentReader.addAndPushContainer(mPicture);
        DmlPicture dmlPicture = new DmlPicture();
        mPicture.setDmlNode(dmlPicture);
        String shapeTag = getXmlReader().getLocalName();
        while (getXmlReader().readChild(shapeTag))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blipFill"*/0:
                    dmlPicture.setBlipFill((DmlBlipFill)DmlFillReader.read(mDocumentReader));
                    break;
                case /*"spPr"*/1:
                    DmlNodePropertiesReader.readShapeProperties(mDocumentReader, dmlPicture);
                    break;
                case /*"style"*/2:
                    dmlPicture.setStyle(DmlShapeStyleReader.read(getXmlReader(), mDocumentReader.getComplianceInfo()));
                    break;
                case /*"nvPicPr"*/3:
                    dmlPicture.setNonVisualPr(new DmlNvPrPicture());
                    DmlNodePropertiesReader.readNonVisualProperties(dmlPicture.getNonVisualPr(), mDocumentReader);
                    break;
                case /*"extLst"*/4:
                    dmlPicture.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                case /*"AlternateContent"*/5:
                    readAlternateContent(dmlPicture);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        mDocumentReader.popContainer(NodeType.SHAPE);
    }

    /// <summary>
    /// An alternate content block allows for an alternative representation of information.
    /// </summary>
    private void readAlternateContent(DmlShapeBase shape) throws Exception
    {
        while (getXmlReader().readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"Choice"*/6:
                    // andrnosk: - Ignore it for now.
                    // The Requires attribute specifies a set of space-delimited namespaces
                    // that must be understood in order to select that choice. If there is a match
                    // between required namespaces and what the consumer understands, the appropriate Choice is
                    // returned. If there are multiple possible matches, only the first match is returned.
                    warnNotSupportedAndIgnoreElement(getXmlReader());
                    break;
                case /*"Fallback"*/7:
                    // andrnosk: WORDSNET-5024 (part 2)
                    // An optional Fallback element can be used, and is utilized as a default when no match with Choice occurs.
                    // Always take Fallback for rendering, because it is appropriate alternative representation of Choice.
                    readFallback(shape);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// An optional Fallback element can be used, and is utilized as a default when no match with Choice occurs.
    /// </summary>
    private void readFallback(DmlShapeBase shape) throws Exception
    {
        while (getXmlReader().readChild("Fallback"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blipFill"*/0:
                    ((DmlPicture)shape).setBlipFill((DmlBlipFill)DmlFillReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private Shape mPicture;
    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"blipFill",
		"spPr",
		"style",
		"nvPicPr",
		"extLst",
		"AlternateContent",
		"Choice",
		"Fallback"
	);

}

