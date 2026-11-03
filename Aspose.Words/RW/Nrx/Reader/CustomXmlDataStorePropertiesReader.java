// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;


/*static*/ class CustomXmlDataStorePropertiesReader
{
	/* Simulation of static class by using private constructor */
	private CustomXmlDataStorePropertiesReader()
	{}

    /// <summary>
    /// Reads 22.5.2.1 datastoreItem (Custom XML Data Properties).
    /// </summary>
    static void read(Stream stream, CustomXmlPart customXmlPart, IWarningCallback warningCallback) throws Exception
    {
        // andrnosk: WORDSNET-6977 Check if stream is not empty.
        if (stream.getLength() == 0)
            return;

        try
        {
            NrxXmlReader xmlReader = new NrxXmlReader(stream);

            // Read attrs.
            while (xmlReader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"itemID"*/0:
                        // The SPEC specifies this to be a GUID, but MS Word allows any string so we do the same.
                        customXmlPart.setId(xmlReader.getValue());
                        break;
                    default:
                        // Do nothing.
                        break;
                }
            }

            // Read elements.
            xmlReader.moveToElement();
            while (xmlReader.readChild("datastoreItem"))
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"schemaRefs"*/1:
                        readSchemaRefs(xmlReader, customXmlPart);
                        break;
                    default:
                        xmlReader.ignoreElement();
                        break;
                }
            }
        }
        catch(Exception e)
        {
            // WORDSNET-10259 Resiliently catch any XML errors.
            WarningUtil.warnUnexpected(warningCallback, WarningSource.UNKNOWN, WarningStrings.CUSTOM_XML_ERROR);
        }
    }

    /// <summary>
    /// Reads 22.5.2.3 schemaRefs (Set of Associated XML Schemas)
    /// </summary>
    private static void readSchemaRefs(NrxXmlReader xmlReader, CustomXmlPart customXmlPart)
    {
        while (xmlReader.readChild("schemaRefs"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"schemaRef"*/2:
                    readSchemaRef(xmlReader, customXmlPart);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 22.5.2.2 schemaRef (Associated XML Schema)
    /// </summary>
    private static void readSchemaRef(NrxXmlReader xmlReader, CustomXmlPart customXmlPart)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"uri"*/3:
                    customXmlPart.getSchemas().add(xmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"itemID",
		"schemaRefs",
		"schemaRef",
		"uri"
	);

}

