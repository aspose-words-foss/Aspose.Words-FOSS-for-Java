// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements writing the People document part.
/// </summary>
/*static*/ class DocxPeopleWriter
{
	/* Simulation of static class by using private constructor */
	private DocxPeopleWriter()
	{}

    /// <summary>
    /// Writes the People document part with using the specified document writer.
    /// </summary>
    static void write(DocxDocumentWriterBase writer) throws Exception
    {
        DocumentBase doc = writer.getDocument();
        if (doc.getPeople().getCount() == 0 || writer.getCompliance() == OoxmlComplianceCore.ECMA_376)
            return;

        DocxBuilder builder = writer.createChildPartAndBuilder("people.xml", DocxContentType.PEOPLE,
            writer.getRelTypes().getPeople());
        writer.pushBuilder(builder);

        builder.startDocumentWithStandardNamespaces("w15:people");

        for (PersonInternal person : doc.getPeople())
            writePerson(person, writer);

        builder.endDocument(); //w15:people
        writer.popBuilder();
    }

    /// <summary>
    /// Writes the person element of 2.5.3.5 CT_Person [MS-DOCX] complex type.
    /// </summary>
    private static void writePerson(PersonInternal person, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("w15:person");

        builder.writeAttribute("w15:author", person.getAuthor());

        builder.startElement("w15:presenceInfo");

        builder.writeAttribute("w15:providerId", DocxEnum.contactIdentityProviderToDocx(person.getIdentityProvider()));

        // WORDSNET-16147 Always write userId, even it is empty. It is required in scheme
        // https://msdn.microsoft.com/en-us/library/documentformat.openxml.office2013.word.presenceinfo.aspx
        builder.writeAttributeString("w15:userId", person.getUserId());

        builder.endElement(); //w15:presenceInfo

        builder.endElement(); //w15:person
    }
}

