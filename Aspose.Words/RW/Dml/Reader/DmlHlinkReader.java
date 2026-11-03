// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Reader for DrawingML click/mouse-over hyperlink.
/// </summary>
class DmlHlinkReader extends DmlReaderBase
{
    private DmlHlinkReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
        mXmlReader = reader.getXmlReader();
    }

    /// <summary>
    /// Reads click/mouse-over Hyperlink.
    /// 5.1.5.3.5 hlinkClick (Click Hyperlink)
    /// 5.1.2.1.23 hlinkHover (Hyperlink for Hover)
    /// 21.1.2.3.5 hlinkClick (Click Hyperlink)
    /// 21.1.2.3.6 hlinkMouseOver (Mouse-Over Hyperlink)
    /// Specifies the on-click/mouse-over hyperlink information to be applied to a run of text.
    /// When the hyperlink text is clicked the link will be fetched.
    /// </summary>
    static DmlHlink read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlHlinkReader hlinkReader = new DmlHlinkReader(reader);
        return hlinkReader.readCore();
    }

    private DmlHlink readCore() throws Exception
    {
        String tagName = mXmlReader.getLocalName();

        DmlHlink hlink = new DmlHlink();

        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"id"*/0:
                    hlink.setId(mDocumentReader.getRelationshipTarget(mXmlReader.getValue()));
                    break;
                case /*"tgtFrame"*/1:
                    hlink.setTargetFrame(mXmlReader.getValue());
                    break;
                case /*"tooltip"*/2:
                    hlink.setTooltip(mXmlReader.getValue());
                    break;
                case /*"action"*/3:
                case /*"endSnd"*/4:
                case /*"highlightClick"*/5:
                case /*"history"*/6:
                case /*"invalidUrl"*/7:
                default:
                    warnUnexpected(mXmlReader);
                    break;
            }
        }

        // Read elements.
        while (mXmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"extLst"*/8:
                    hlink.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                case /*"snd"*/9:
                default:
                    warnNotSupportedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return hlink;
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;
    private /*final*/ NrxXmlReader mXmlReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"tgtFrame",
		"tooltip",
		"action",
		"endSnd",
		"highlightClick",
		"history",
		"invalidUrl",
		"extLst",
		"snd"
	);

}

