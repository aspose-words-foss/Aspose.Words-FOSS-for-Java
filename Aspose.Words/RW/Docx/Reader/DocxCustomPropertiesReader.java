// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;


/// <summary>
/// Provides static method for reading "Custom File Properties" package part
/// </summary>
/*static*/ class DocxCustomPropertiesReader
{
	/* Simulation of static class by using private constructor */
	private DocxCustomPropertiesReader()
	{}

    /// <summary>
    /// Reads "Custom File Properties" part for the specified document.
    /// </summary>
    static void read(DocxReader reader) throws Exception
    {
        DocxXmlReader xmlReader = reader.createPackageChildPartReader(reader.getRelTypes().getCustomProperties());
        // WORDSNET-27423 MS Word allows opening and reading custom properties if the document has a strict compliance,
        // but the custom.xml file has a schema url from a transitional compliance.
        // Lets try to create a reader with url from transitional compliance, if the reader is null.
        if ((xmlReader == null) && (reader.getDocument().getComplianceInfo().getCompliance() == OoxmlComplianceCore.ISO_STRICT))
            xmlReader = reader.createPackageChildPartReader(DocxRelationshipTypes.getType(DocxRelationshipType.CUSTOM_PROPERTIES, false));
        // If the reader still null, ignore the properties.
        if (xmlReader == null)
            return;

        CustomDocumentProperties props = reader.getDocument().getCustomDocumentProperties();

        while (xmlReader.readChild("Properties"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"property"*/0:
                    readProperty(props, xmlReader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readProperty(CustomDocumentProperties props, DocxXmlReader xmlReader)
    {
        // Example:
        //   <property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}" pid="4" name="_AuthorEmail">
        //       <vt:lpwstr>kpaul@boomerang.com</vt:lpwstr>
        //   </property>

        String name = null;
        String linkTarget = null;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fmtid"*/1:
                    // Can be ignored.
                    break;
                case /*"pid"*/2:
                    // RK It actually looks it is better to ignore property id and generate new one.
                    // This allows for "more" resiliency in case pid is not specified in DOCX.
                    break;
                case /*"name"*/3:
                    name = xmlReader.getValue();
                    break;
                case /*"linkTarget"*/4:
                    linkTarget = xmlReader.getValue();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // Property should have name.
        if (!StringUtil.hasChars(name))
            return;

        while (xmlReader.readChild("property"))
        {
            DocumentProperty prop = null;

            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bool"*/5:        // vt:bool
                    props.addSafe(name, xmlReader.readBoolString(false));
                    break;
                case /*"i4"*/6:            // vt:i4
                    prop = props.addSafe(name, xmlReader.readStringAsInt());
                    break;
                case /*"r8"*/7:            // vt:r8
                    prop = props.addSafe(name, FormatterPal.parseDouble(xmlReader.readString()));
                    break;
                case /*"lpwstr"*/8:        // vt:lpwstr
                {
                    // andrnosk: WORDSNET-9275/9830/11735 x000D and x000B is the hexadecimal equivalent of 13, which is the ascii value of 
                    // a carriage return, MS Word does not show this.
                    prop = props.addSafe(name, xmlReader.readStringConvertSpecialCharacters(true));
                    break;
                }
                case /*"filetime"*/9:    // vt:filetime
                    props.addSafe(name, FormatterPal.xmlToDateTime(xmlReader.readString()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }

            if (StringUtil.hasChars(linkTarget) && (prop != null))
                prop.setLinkTarget(linkTarget);
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"property",
		"fmtid",
		"pid",
		"name",
		"linkTarget",
		"bool",
		"i4",
		"r8",
		"lpwstr",
		"filetime"
	);

}

