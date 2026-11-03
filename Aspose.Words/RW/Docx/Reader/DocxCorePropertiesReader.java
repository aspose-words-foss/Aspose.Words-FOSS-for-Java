// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.AnyXmlTextHandlingConsts;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Provides static method for reading the "Core File Properties" package part.
/// </summary>
/*static*/ class DocxCorePropertiesReader
{
	/* Simulation of static class by using private constructor */
	private DocxCorePropertiesReader()
	{}

    /// <summary>
    /// Reads "Core File Properties" part for the specified document.
    /// </summary>
    static void read(DocxReader reader) throws Exception
    {
        DocxXmlReader xmlReader = reader.createPackageChildPartReader(reader.getRelTypes().getCoreProperties());
        if (xmlReader == null)
            return;

        BuiltInDocumentProperties props = reader.getDocument().getBuiltInDocumentProperties();

        while (xmlReader.readChild("coreProperties"))    //cp:coreProperties
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"title"*/0: // dc:title
                    props.setTitle(xmlReader.readStringConvertSpecialCharacters(false));
                    break;
                case /*"subject"*/1: // dc:subject
                {
                    // FOSS
                    props.setSubject(xmlReader.readString());
                    break;
                }
                case /*"creator"*/2: // dc:creator
                    props.setAuthor(xmlReader.readString());
                    break;
                case /*"keywords"*/3: // cp:keywords
                    parseKeywords(props, xmlReader);
                    break;
                case /*"description"*/4: // dc:description
                    props.setComments(xmlReader.readStringConvertSpecialCharacters(false));
                    break;
                case /*"lastModifiedBy"*/5:    // cp:lastModifiedBy
                    props.setLastSavedBy(xmlReader.readString());
                    break;
                case /*"revision"*/6:        // cp:revision
                    props.setRevisionNumber(xmlReader.readStringAsInt());
                    break;
                case /*"lastPrinted"*/7:        // cp:lastPrinted
                    props.setLastPrintedInternal(FormatterPal.xmlToDateTime(xmlReader.readString()));
                    break;
                case /*"created"*/8:            // dcterms:created
                    props.setCreatedTimeInternal(FormatterPal.xmlToDateTime(xmlReader.readString()));
                    break;
                case /*"modified"*/9:        // dcterms:modified
                    props.setLastSavedTimeInternal(FormatterPal.xmlToDateTime(xmlReader.readString()));
                    break;
                case /*"category"*/10:        // cp:category
                    props.setCategory(xmlReader.readString());
                    break;
                case /*"contentStatus"*/11:    // cp:contentStatus
                    props.setContentStatus(xmlReader.readString());
                    break;
                case /*"contentType"*/12:        // cp:contentType
                    props.setContentType(xmlReader.readString());
                    break;
                case /*"language"*/13:        // dc:language
                    props.get(PropertyName.LANGUAGE).setValue(xmlReader.readString());
                    break;
                case /*"version"*/14:         // cp:version
                    props.get(PropertyName.DOC_VERSION).setValue(xmlReader.readString());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void parseKeywords(BuiltInDocumentProperties props, DocxXmlReader xmlReader)
    {
        StringBuilder keywords = new StringBuilder();
        // Read pre-ISO29500 style keywords represented as <keywords>key1, key2, key3</keywords>
        // as well as ISO29500 style keywords:
        // <keywords><value>key2</value><value>key3</value></keywords>
        while (xmlReader.readChildWithTextValues("keywords", AnyXmlTextHandlingConsts.TEXT_AND_SIGNIFICANT))
        {
            switch(gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"value"*/15:
                    {
                        // process ISO29500 styled keywords e.g. <value>key1</value>
                        xmlReader.getComplianceInfo().markAsIsoTransitional();
                        msStringBuilder.append(keywords, xmlReader.readString());
                        msStringBuilder.append(keywords, ", ");
                        break;
                    }
                default: // assume we deal with pre-ISO29500 keywords which are plain text.
                    props.setKeywords(xmlReader.getValue());
                    break;
            }
        }

        if (keywords.length() != 0) // although this case seems extra-care, combine new and old style keywords
        {
            props.setKeywords((props.getKeywords().length() > 0)
                ? keywords.toString() + props.getKeywords()
                : keywords.substring((0), (0) + (keywords.length() - 2)));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"title",
		"subject",
		"creator",
		"keywords",
		"description",
		"lastModifiedBy",
		"revision",
		"lastPrinted",
		"created",
		"modified",
		"category",
		"contentStatus",
		"contentType",
		"language",
		"version",
		"value"
	);

}

