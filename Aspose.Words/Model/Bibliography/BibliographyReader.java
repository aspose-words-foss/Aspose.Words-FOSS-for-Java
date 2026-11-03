// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlReader;


/// <summary>
/// <see cref="Source"/> xml deserializer.
/// </summary>
/*static*/ class BibliographyReader
{
	/* Simulation of static class by using private constructor */
	private BibliographyReader()
	{}

    static CustomXmlPart read(Bibliography bibliography, Document document) throws Exception
    {
        CustomXmlPart result = null;

        for (CustomXmlPart customXmlPart : document.getCustomXmlParts())
        {
            if (customXmlPart.getData().length == 0)
                continue;

            if (readXmlPartSafe(bibliography, customXmlPart))
                result = customXmlPart;
        }

        return result;
    }

    private static boolean readXmlPartSafe(Bibliography bibliography, CustomXmlPart customXmlPart) throws Exception
    {
        try
        {
            return readXmlPartData(bibliography, customXmlPart.getData());
        }
        catch(Exception e)
        {
            return false;
        }
    }

    private static boolean readXmlPartData(Bibliography bibliography, byte[] data) throws Exception
    {
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            AnyXmlReader reader = new AnyXmlReader(stream, true);
            return readXml(bibliography, reader);
        }
        finally { if (stream != null) stream.close(); }
    }

    private static boolean readXml(Bibliography bibliography, AnyXmlReader reader)
    {
        if (!"Sources".equals(reader.getLocalName()))
            return false;

        readBibliography(bibliography, reader);
        readSources(bibliography, reader);

        return true;
    }

    private static void readBibliography(Bibliography bibliography, AnyXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"SelectedStyle"*/0:
                    bibliography.setBibliographyStyle(reader.getValue());
                    break;
                case /*"StyleName"*/1:
                    bibliography.setStyleName(reader.getValue());
                    break;
                case /*"Version"*/2:
                    bibliography.setVersion(reader.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    private static void readSources(Bibliography bibliography, AnyXmlReader reader)
    {
        // Subsequent sources overrides any previous one.
        bibliography.getSources().clear();

        if (!reader.readChild("Sources"))
            return;

        while (!reader.isEndElement("Sources"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Source"*/3:
                    String xml = reader.readOuterXml();
                    Source source = new Source(xml);
                    bibliography.getSources().add(source);
                    break;
                default:
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"SelectedStyle",
		"StyleName",
		"Version",
		"Source"
	);

}

