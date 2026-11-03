// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.AnyXmlTextHandlingConsts;


/// <summary>
/// Provides static method for reading "Extented File Properties" package part
/// </summary>
/*static*/ class DocxExtentedPropertiesReader
{
	/* Simulation of static class by using private constructor */
	private DocxExtentedPropertiesReader()
	{}

    /// <summary>
    /// Reads "Extented File Properties" part for the specified document.
    /// </summary>
    static void read(DocxReader reader) throws Exception
    {
        DocxXmlReader xmlReader = reader.createPackageChildPartReader(reader.getRelTypes().getExtendedProperties());
        if (xmlReader == null)
            return;

        BuiltInDocumentProperties props = reader.getDocument().getBuiltInDocumentProperties();

        while (xmlReader.readChild("Properties"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Template"*/0:
                    props.setTemplate(xmlReader.readString());
                    break;
                case /*"TotalTime"*/1:
                    props.setTotalEditingTimeSafe(xmlReader.readStringAsInt());
                    break;
                case /*"Pages"*/2:
                    props.setPages(xmlReader.readStringAsInt());
                    break;
                case /*"Words"*/3:
                    props.setWords(xmlReader.readStringAsInt());
                    break;
                case /*"Characters"*/4:
                    props.setCharacters(xmlReader.readStringAsInt());
                    break;
                case /*"Application"*/5:
                    props.setNameOfApplication(xmlReader.readString());
                    break;
                case /*"DocSecurity"*/6:
                    props.setSecurity((/*DocumentSecurity*/int)xmlReader.readStringAsInt());
                    break;
                case /*"Lines"*/7:
                    props.setLines(xmlReader.readStringAsInt());
                    break;
                case /*"Paragraphs"*/8:
                    props.setParagraphs(xmlReader.readStringAsInt());
                    break;
                case /*"HeadingPairs"*/9:
                    props.setHeadingPairs(readHeadingPairs(xmlReader));
                    break;
                case /*"TitlesOfParts"*/10:
                    props.setTitlesOfParts(readTitlesOfParts(xmlReader));
                    break;
                case /*"Manager"*/11:
                    props.setManager(xmlReader.readString());
                    break;
                case /*"Company"*/12:
                    props.setCompany(xmlReader.readString());
                    break;
                case /*"LinksUpToDate"*/13:
                    // Not in model. Ignored.
                    break;
                case /*"CharactersWithSpaces"*/14:
                    props.setCharactersWithSpaces(xmlReader.readStringAsInt());
                    break;
                case /*"HLinks"*/15:
                    // Not in model. Ignored.
                    break;
                case /*"HyperlinkBase"*/16:
                    props.setHyperlinkBase(xmlReader.readString());
                    break;
                case /*"AppVersion"*/17:
                    props.setVersion(NrxXmlUtil.xmlToVersionNumber(xmlReader.readString()));
                    break;
                case /*"DigSig"*/18:
                    // Ignore because this is only used in the binary Excel documents.
                    break;
                case
                    /*PropertyName.SCALE_CROP*/19:
                case/*PropertyName.SHARED_DOC*/20:
                case/*PropertyName.HYPERLINKS_CHANGED*/21:
                    // WORDSNET-27690 Extended document properties are written as int rather than as bool.
                    String val = xmlReader.readString();
                    if(FormatterPal.isBoolTrueFalse(val))
                        props.setProperty(xmlReader.getLocalName(), FormatterPal.parseBoolTrueFalse(val));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads a heading pairs property from an OOXML document.
    /// If there is any problem, return a zero length array, not null.
    /// </summary>
    private static Object[] readHeadingPairs(DocxXmlReader xmlReader)
    {
        while (xmlReader.readChild("HeadingPairs"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"vector"*/22:
                    return readVector(xmlReader);
                default:
                    break;
            }
        }
        return new Object[0];
    }

    /// <summary>
    /// Reads a titles of parts property from an OOXML document.
    /// If there is any problem, return a zero length array, not null.
    /// </summary>
    private static String[] readTitlesOfParts(DocxXmlReader xmlReader)
    {
        while (xmlReader.readChild("TitlesOfParts"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"vector"*/22:
                {
                    // Convert from an object array to a string array.
                    Object[] objects = readVector(xmlReader);
                    String[] result = new String[objects.length];
                    System.arraycopy(objects, 0, result, 0, objects.length);
                    return result;
                }
                default:
                    break;
            }
        }
        return new String[0];
    }

    /// <summary>
    /// Reads a vt:vector from an OOXML document.
    /// Returns an object array of zero length if cannot parse.
    /// </summary>
    private static Object[] readVector(DocxXmlReader xmlReader)
    {
        int length = 0;
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"size"*/23:
                    length = xmlReader.getValueAsInt();
                    break;
                case /*"baseType"*/24:
                    // Do nothing. We read any as array objects.
                    break;
                default:
                    break;
            }
        }

        Object[] result = new Object[length];
        int index = 0;
        while (xmlReader.readChildWithTextValues("vector", AnyXmlTextHandlingConsts.TEXT_AND_SIGNIFICANT))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"variant"*/25:
                    result[index] = readVariant(xmlReader);
                    index++;
                    break;
                case /*"lpstr"*/26:
                    result[index] = xmlReader.readString();
                    index++;
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    /// <summary>
    /// Reads a vt:variant from an OOXML document. At the moment reads only limited number of types.
    /// Returns null if cannot parse the value.
    /// </summary>
    private static Object readVariant(DocxXmlReader xmlReader)
    {
        while (xmlReader.readChild("variant"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"lpstr"*/26:
                    return xmlReader.readString();
                case /*"i4"*/27:
                    return xmlReader.readStringAsInt();
                default:
                    break;
            }
        }
        return null;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Template",
		"TotalTime",
		"Pages",
		"Words",
		"Characters",
		"Application",
		"DocSecurity",
		"Lines",
		"Paragraphs",
		"HeadingPairs",
		"TitlesOfParts",
		"Manager",
		"Company",
		"LinksUpToDate",
		"CharactersWithSpaces",
		"HLinks",
		"HyperlinkBase",
		"AppVersion",
		"DigSig",
		"ScaleCrop",
		"SharedDoc",
		"HyperlinksChanged",
		"vector",
		"size",
		"baseType",
		"variant",
		"lpstr",
		"i4"
	);

}

