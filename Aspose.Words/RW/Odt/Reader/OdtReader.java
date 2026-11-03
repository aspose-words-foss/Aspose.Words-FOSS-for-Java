// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// FOSS reduction: only the format-detection statics survive. The full ODT reader
/// (IDocumentReader implementation, OdtPackage, OfficeCrypto, ~1400 lines) is gone.
/// </summary>
/*static*/ class OdtReader
{
	/* Simulation of static class by using private constructor */
	private OdtReader()
	{}

    /// <summary>
    /// Returns true if the ODT document has a digital signature.
    /// You need to provide the META-INF/documentsignatures.xml stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static boolean isDigitalSignaturePresent(Stream documentSignaturesStream) throws Exception
    {
        OdtPartReader partReader = new OdtPartReader(documentSignaturesStream);

        while (partReader.readChild("document-signatures"))
        {
            if ("Signature".equals(partReader.getLocalName()))
                return true;

            partReader.ignoreElement();
        }

        return false;
    }

    /// <summary>
    /// Returns true if the ODT document is encrypted.
    /// You need to provide the META-INF/manifest.xml stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static boolean isEncrypted(Stream manifestStream) throws Exception
    {
        OdtPartReader partReader = new OdtPartReader(manifestStream);

        while (partReader.readChild("manifest"))
        {
            if ("file-entry".equals(partReader.getLocalName()))
            {
                while (partReader.readChild("file-entry"))
                {
                    if ("encryption-data".equals(partReader.getLocalName()))
                        return true;

                    partReader.ignoreElement();
                }
            }
            else
            {
                partReader.ignoreElement();
            }
        }

        return false;
    }

    /// <summary>
    /// Detect load format by "manifest.xml". For ODT document without "mimetype" (WORDSNET-4985).
    /// </summary>
    static /*LoadFormat*/int detectLoadFormatByManifest(Stream manifestStream) throws Exception
    {
        OdtPartReader partReader = new OdtPartReader(manifestStream);

        while (partReader.readChild("manifest"))
        {
            if ("file-entry".equals(partReader.getLocalName()))
            {
                String mediaType = "";
                String fullPath = "";

                while (partReader.moveToNextAttribute())
                {
                    if ("full-path".equals(partReader.getLocalName()))
                        fullPath = partReader.getValue();
                    if ("media-type".equals(partReader.getLocalName()))
                        mediaType = partReader.getValue();
                }

                if ("/".equals(fullPath))
                    return detectLoadFormat(mediaType);
            }
            else
            {
                partReader.ignoreElement();
            }
        }

        return LoadFormat.UNKNOWN;
    }

    /// <summary>
    /// You need to pass the "mimetype" stream positioned at the beginning.
    /// Does NOT preserve the stream position.
    /// </summary>
    static /*LoadFormat*/int detectLoadFormat(Stream mimeTypeStream) throws Exception
    {
        StreamReader reader = new StreamReader(mimeTypeStream);

        String mimeType = reader.readLine();

        return detectLoadFormat(mimeType);
    }

    private static /*LoadFormat*/int detectLoadFormat(String mimeType)
    {
        switch (gStringSwitchMap.of(mimeType))
        {
            case/*OdtContentTypes.OPEN_DOCUMENT_TEXT*/0:
            // WORDSNET-23560 Added 'OpenDocument Master Document' mime type.
            case
        /*OdtContentTypes.OPEN_DOCUMENT_MASTER_DOCUMENT*/1:
                return LoadFormat.ODT;
            case /*OdtContentTypes.OPEN_DOCUMENT_TEXT_TEMPLATE*/2:
                return LoadFormat.OTT;
            default:
                return LoadFormat.UNKNOWN;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"application/vnd.oasis.opendocument.text",
		"application/vnd.oasis.opendocument.text-master",
		"application/vnd.oasis.opendocument.text-template"
	);

}

