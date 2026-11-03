// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides static method for writing "Extended File Properties" package part
/// </summary>
/*static*/ class DocxExtentedPropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private DocxExtentedPropertiesWriter()
	{}

    /// <summary>
    /// Writes "Extended File Properties" part for the specified document.
    /// </summary>
    static void write(DocxWriter writer) throws Exception
    {
        DocxBuilder builder = writer.createPartAndBuilder("/docProps/app.xml", DocxContentType.EXTENDED_PROPERTIES, writer.getRelTypes().getExtendedProperties());
        builder.startExtendedPropertiesDocumentPart();

        Document doc = writer.getDocument();
        BuiltInDocumentProperties props = doc.getBuiltInDocumentProperties();

        builder.writeOptionalElement("Template", props.getTemplate());

        builder.writeElement("TotalTime", props.getTotalEditingTime());
        builder.writeElement("Pages", props.getPages());
        builder.writeElement("Words", props.getWords());
        builder.writeElement("Characters", props.getCharacters());
        builder.writeElement("Application", "Microsoft Office Word");
        builder.writeElement("DocSecurity", (int)props.getSecurity());
        builder.writeElement("Lines", props.getLines());
        builder.writeElement("Paragraphs", props.getParagraphs());
        builder.writeElement("ScaleCrop", "false");
        writeHeadingPairs(builder, props.getHeadingPairs());
        writeTitlesOfParts(builder, props.getTitlesOfParts());
        builder.writeOptionalElement("Manager", props.getManager());
        builder.writeElement("Company", props.getCompany());
        builder.writeElement("LinksUpToDate", "false");
        builder.writeElement("CharactersWithSpaces", props.getCharactersWithSpaces());
        builder.writeElement("SharedDoc", "false");
        //"HLinks" we do not support.
        builder.writeOptionalElement("HyperlinkBase", props.getHyperlinkBase());
        builder.writeElement("HyperlinksChanged", "false");

        // High 16 bits contains version number.
        /*MsWordVersionCore*/int version = (/*MsWordVersionCore*/int)(props.getVersion() >> 16);

        // WORDSNET-8723 MS Word 2007 SP2 does not like earlier versions written. If they fix it,
        // it could be a good idea to change this code back to writing props.Version.
        // WORDSNET-15407 Do not change AppVersion for MSW version higher then Word2007.
        builder.writeElement("AppVersion", (version <= MsWordVersionCore.WORD_2007)
            ? "12.0000"
            : NrxXmlUtil.versionNumberToXml(props.getVersion()));

        builder.endDocument();
    }

    private static void writeHeadingPairs(DocxBuilder builder, Object[] headingPairs)
    {
        if (headingPairs.length == 0)
            return;

        builder.startElement("HeadingPairs");
        builder.startElement("vt:vector");
        builder.writeAttribute("size", headingPairs.length);
        builder.writeAttribute("baseType", "variant");

        for (Object o : headingPairs)
        {
            builder.startElement("vt:variant");

            if (o instanceof String)
                builder.writeElement("vt:lpstr", (String)o);
            else if (o instanceof /*int*/Integer)
                builder.writeElement("vt:i4", (/*int*/Integer)o);
            else
                throw new IllegalStateException("Unexpected value type in heading pairs.");

            builder.endElement();   // vt:variant
        }

        builder.endElement();   // vt:vector
        builder.endElement();   // HeadingPairs
    }

    private static void writeTitlesOfParts(DocxBuilder builder, String[] titlesOfParts)
    {
        if (titlesOfParts.length == 0)
            return;

        builder.startElement("TitlesOfParts");
        builder.startElement("vt:vector");
        builder.writeAttribute("size", titlesOfParts.length);
        builder.writeAttribute("baseType", "lpstr");

        for (String s : titlesOfParts)
            builder.writeElement("vt:lpstr", s);

        builder.endElement();   // vt:vector
        builder.endElement();   // TitlesOfParts
    }
}

