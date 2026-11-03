// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.AnyXmlReader;
import com.aspose.StringUtil;
import com.aspose.Debug;


class DocxSmartTagReader
{
    /// <summary>
    /// Reads a 'w:smartTag' element from the specified reader.
    /// </summary>
    /// <param name="reader">DocxReader to read from. Should be positioned to element start.</param>
    void read(NrxDocumentReaderBase reader) throws Exception
    {
        SmartTag smartTag = new SmartTag(reader.getDocument());
        reader.addAndPushContainer(smartTag);

        // Read attributes.
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"element"*/0:
                    smartTag.setElement(xmlReader.getValue());
                    break;
                case /*"uri"*/1:
                    smartTag.setUri(xmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // Read elements.
        xmlReader.moveToElement();
        while (xmlReader.readChild("smartTag"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"smartTagPr"*/2:
                    readSmartTagPr(xmlReader, smartTag);
                    break;
                default:
                    mInlineReader.readChild(reader);
                    break;
            }
        }

        reader.popContainer(NodeType.SMART_TAG);
    }

    /// <summary>
    /// Reads a w:smartTagPr collection.
    /// </summary>
    private static void readSmartTagPr(AnyXmlReader partReader, SmartTag smartTag)
    {
        while (partReader.readChild("smartTagPr"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"attr"*/3:
                    readAttr(partReader, smartTag);
                    break;
                default:
                    partReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads a w:attr element of w:smartTagPr collection and adds to the smart tag properties.
    /// </summary>
    private static void readAttr(AnyXmlReader partReader, SmartTag smartTag)
    {
        String name = "";
        String uri = "";
        String val = "";

        while (partReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"name"*/4:
                    name = partReader.getValue();
                    break;
                case /*"uri"*/1:
                    uri = partReader.getValue();
                    break;
                case /*"val"*/5:
                    val = partReader.getValue();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        if (StringUtil.hasChars(name))
            smartTag.getProperties().addSafe(new CustomXmlProperty(name, uri, val));
    }

    void setInlineReader(DocxInlineReader inlineReader)
    {
        Debug.assertTrue(inlineReader != null);
        mInlineReader = inlineReader;
    }

    private DocxInlineReader mInlineReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"element",
		"uri",
		"smartTagPr",
		"attr",
		"name",
		"val"
	);

}

