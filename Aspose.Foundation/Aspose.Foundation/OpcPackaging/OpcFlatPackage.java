// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.Convert;
import java.lang.NumberFormatException;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// Class reads/writes Flat Opc packages.
/// Keeps all parts of the package in memory at all times.
/// </summary>
public class OpcFlatPackage extends OpcPackageBase
{
    /// <summary>
    /// Creates a new empty package.
    /// </summary>
    public OpcFlatPackage() { }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    public OpcFlatPackage(String filename) throws Exception
    {
        Stream stream = new FileStream(filename, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
    	{
            loadCore(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    /// <param name="stream">It is the responsibility of the caller to close the stream.</param>
    public OpcFlatPackage(Stream stream) throws Exception
    {
        loadCore(stream);
    }

    /// <summary>
    /// Opens a package from a file and loads all parts into memory, reads content types and relationships.
    /// </summary>
    private void loadCore(Stream stream) throws Exception
    {
        loadParts(stream);
        loadRelationships();
    }

    /// <summary>
    /// Just loads all parts into the parts collection.
    /// When quits, content types are already resolved, but relationships are not read.
    /// </summary>
    private void loadParts(Stream stream) throws Exception
    {
        AnyXmlReader flatOpcReader = new AnyXmlReader(stream);
        while (flatOpcReader.readChild("package"))
        {
            // Child elements of package should be part elements.
            // If some other element is encountered, just ignore it.
            if (!"part".equals(flatOpcReader.getLocalName()))
            {
                flatOpcReader.ignoreElement();
                continue;
            }

            OpcPackagePart part = readFlatOpcPackagePart(flatOpcReader);
            getParts().add(part);
        }
    }

    /// <summary>
    /// Reads part from the Flat Opc package.
    /// </summary>
    private static OpcPackagePart readFlatOpcPackagePart(AnyXmlReader flatOpcReader) throws Exception
    {
        // Read name and content type attributes.
        String partName = flatOpcReader.readAttribute("name", "");
        String contentType = flatOpcReader.readAttribute("contentType", "");

        // Read compression attribute, it is present only when part contains binary data.
        // It is not used yet. I am not sure whether it is needed at all.
        flatOpcReader.readAttribute("compression", "store");

        OpcPackagePart part = new OpcPackagePart(partName, contentType);

        // Read part data. It can be XML data or binary data.
        flatOpcReader.readChild("part");
        switch (gStringSwitchMap.of(flatOpcReader.getLocalName()))
        {
            case /*"xmlData"*/0:
            {
                StreamWriter xmlDataWriter = new StreamWriter(part.getStream());
                flatOpcReader.readChild("xmlData");
                xmlDataWriter.write(flatOpcReader.readOuterXml());
                xmlDataWriter.flush();
                break;
            }
            case /*"binaryData"*/1:
            {
                String base64 = flatOpcReader.readString();
                byte[] binData;

                try
                {
                    binData = Convert.fromBase64String(base64);
                }
                catch (NumberFormatException e)
                {
                    // WORDSNET-27250 Invalid Base64 string, invalid spaces and length.
                    base64 = base64.replace(" ", "");

                    if (base64.length() % 4 == 1)
                        base64 = base64.substring((0), (0) + (base64.length() - 3));
                    else if (base64.length() % 4 != 0)
                        base64 = StringUtilPal.padRight(base64, base64.length() + (4 - base64.length() % 4), '=');

                    binData = Convert.fromBase64String(base64);
                }

                part.getStream().write(binData, 0, binData.length);
                break;
            }
            default:
            {
                // Ignore.
                break;
            }
        }

        part.getStream().setPosition(0);

        return part;
    }

    public /*override*/ void save(Stream stream) throws Exception
    {
        AnyXmlBuilder xmlBuilder = new AnyXmlBuilder(stream, true);

        xmlBuilder.startDocument(true);
        xmlBuilder.writeProcessingInstruction("mso-application", "progid=\"Word.Document\"");

        // Start document's root element - package.
        xmlBuilder.startElement("pkg:package");
        xmlBuilder.writeAttributeString("xmlns:pkg", "http://schemas.microsoft.com/office/2006/xmlPackage");

        for (OpcPackagePart part : getParts())
        {
            part.getStream().setPosition(0);

            xmlBuilder.startElement("pkg:part");
            xmlBuilder.writeAttributeString("pkg:name", part.getName());
            xmlBuilder.writeAttributeString("pkg:contentType", part.getContentType());

            // ContentType of package parts, which contain XML data normally ends with "+xml" or equals "application/xml".
            // So if ContentType does not end with "xml", we can assume this is binary data.
            if (!msString.endsWith(part.getContentType(), "xml", StringComparison.ORDINAL))
            {
                // If we write binaryData, we should also add compression attribute.
                xmlBuilder.writeAttributeString("pkg:compression", "store");

                xmlBuilder.startElement("pkg:binaryData");
                xmlBuilder.writeBase64(part.getStream());
                xmlBuilder.endElement();
            }
            else
            {
                xmlBuilder.startElement("pkg:xmlData");
                xmlBuilder.writeRaw(getXmlString(part.getStream()));
                xmlBuilder.endElement();
            }
            xmlBuilder.endElement();
        }

        xmlBuilder.endDocument();
    }

    /// <summary>
    /// Gets an XML string with no initial XML definition from the specified stream.
    /// </summary>
    private static String getXmlString(Stream xmlStringStream) throws Exception
    {
        final String OPENING = "<?xml";
        final String ENDING = "?>";
        final int NOT_FOUND = -1;

        String xmlString = new StreamReader(xmlStringStream).readToEnd();

        int openingPos = msString.indexOf(xmlString, OPENING, StringComparison.ORDINAL);
        if (openingPos == NOT_FOUND)
            return xmlString;

        int endingPos = msString.indexOf(xmlString, ENDING, openingPos + OPENING.length(), StringComparison.ORDINAL);
        if (endingPos == NOT_FOUND)
            return xmlString;

        // Remove the XML definition from the beginning of the XML string.
        // If the output XML document contains few XML definitions, it will be invalid.
        return xmlString.substring(endingPos + ENDING.length());
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"xmlData",
		"binaryData"
	);

}

