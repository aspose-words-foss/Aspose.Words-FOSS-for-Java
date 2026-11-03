// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements reading data of the People document part.
/// </summary>
/*static*/ class DocxPeopleReader
{
	/* Simulation of static class by using private constructor */
	private DocxPeopleReader()
	{}

    /// <summary>
    /// Reads the People document part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getPeople());
        if (xmlReader == null)
            return;

        reader.getComplianceInfo().isDocxExtensions(true);
        
        while (xmlReader.readChild("people"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"person"*/0:
                    readPerson(reader);
                    break;
                default:
                {
                    warnUnexpected(xmlReader);
                    xmlReader.ignoreElement();
                    break;
                }
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Reads the person element of the 2.5.3.5 CT_Person type [MS-DOCX].
    /// </summary>
    private static void readPerson(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        PersonInternal person = new PersonInternal();

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"author"*/1:
                    person.setAuthor(xmlReader.getValue());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        // Read elements.
        xmlReader.moveToElement();
        while (xmlReader.readChild("person"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"presenceInfo"*/2:
                    readPresenceInfo(person, xmlReader);
                    break;
                default:
                {
                    warnUnexpected(xmlReader);
                    xmlReader.ignoreElement();
                    break;
                }
            }
        }

        reader.getDocument().getPeople().add(person);
    }

    /// <summary>
    /// Reads the presenceInfo element of the 2.5.3.6 CT_PresenceInfo type [MS-DOCX].
    /// </summary>
    private static void readPresenceInfo(PersonInternal person, NrxXmlReader xmlReader)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"providerId"*/3:
                    person.setIdentityProvider(DocxEnum.docxToContactIdentityProvider(xmlReader.getValue()));
                    break;
                case /*"userId"*/4:
                    person.setUserId(xmlReader.getValue());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Generates a warning that current element or attribute of XML reader is unexpected.
    /// </summary>
    private static void warnUnexpected(NrxXmlReader xmlReader)
    {
        xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX,
            msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, xmlReader.getLocalName()));
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"person",
		"author",
		"presenceInfo",
		"providerId",
		"userId"
	);

}

