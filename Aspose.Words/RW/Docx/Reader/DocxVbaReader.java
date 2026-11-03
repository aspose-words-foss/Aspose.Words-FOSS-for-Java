// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackagePart;
import com.aspose.FileSystem;
import com.aspose.Debug;
import com.aspose.ArrayUtil;
import com.aspose.StreamUtil;
import com.aspose.AnyXmlReader;


/// <summary>
/// Reads a VBA project and its related parts from OOXML.
/// </summary>
class DocxVbaReader
{
    static void read(DocxDocumentReader reader) throws Exception
    {
        OpcPackagePart vbaPart = readVbaProject(reader);
        if (vbaPart == null)
            return;

        if (reader.getDocument().getVbaProject() != null)
            reader.getDocument().getVbaProject().setSignature(readVbaSignature(reader, vbaPart));

        readVbaSupplementalData(reader, vbaPart);
    }

    /// <summary>
    /// Reads the 2.1.1.2 VBA Project part into the model. Also returns the part because we need it to find other related parts.
    /// </summary>
    private static OpcPackagePart readVbaProject(DocxDocumentReader reader) throws Exception
    {
        OpcPackagePart vbaPart = reader.getPartByRelationshipType(reader.getDocumentPart(), reader.getRelTypes().getVba());
        if (vbaPart == null)
            return null;

        // Load the VBA project into the model.
        FileSystem vbaFs = new FileSystem(vbaPart.getStream());
        reader.getDocument().readVbaProject(vbaFs.getRoot(), null);

        return vbaPart;
    }

    private static byte[] readVbaSignature(DocxDocumentReader reader, OpcPackagePart vbaPart) throws Exception
    {
        Debug.assertTrue(vbaPart != null);

        OpcPackagePart vbaSignaturePart = reader.getPartByRelationshipType(vbaPart, reader.getRelTypes().getVbaProjectSignature());
        if (vbaSignaturePart == null)
            return null;

        // Load the vba signature into the model and synthesize the 8 byte prefix.
        final int SIG_PREFIX_LENGTH = Document.VBA_SIGNATURE_PREFIX_LENGTH;
        int sigLength = (int)vbaSignaturePart.getStream().getLength();
        byte[] sigWithPrefix = new byte[SIG_PREFIX_LENGTH + sigLength];
        ArrayUtil.writeUInt32ToByteArray(sigLength, sigWithPrefix, 0);
        ArrayUtil.writeUInt32ToByteArray(SIG_PREFIX_LENGTH, sigWithPrefix, 4);
        StreamUtil.read(vbaSignaturePart.getStream(), sigWithPrefix, SIG_PREFIX_LENGTH, sigLength);
        return sigWithPrefix;
    }

    /// <summary>
    /// Reads the 2.1.1.3 VBA Supplemental Data part into the model.
    /// </summary>
    private static void readVbaSupplementalData(DocxDocumentReader reader, OpcPackagePart vbaPart) throws Exception
    {
        Debug.assertTrue(vbaPart != null);

        OpcPackagePart vbaDataPart = reader.getPartByRelationshipType(vbaPart, reader.getRelTypes().getVbaData());
        if (vbaDataPart == null)
            return;

        NrxXmlReader xmlReader = new NrxXmlReader(vbaDataPart.getStream());
        while (xmlReader.readChild("vbaSuppData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"docEvents"*/0:
                    readDocEvents(xmlReader, reader.getDocument());
                    break;
                case /*"mcds"*/1:
                    readMcds(xmlReader, reader.getDocument());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the 2.1.4.3 CT_DocEvents which contains names of macros for document events.
    /// </summary>
    private static void readDocEvents(AnyXmlReader xmlReader, Document doc)
    {
        /*VbaDocumentEvents*/int events = 0;

        while (xmlReader.readChild("docEvents"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"eventDocNew"*/2:
                    events |= VbaDocumentEvents.NEW;
                    break;
                case /*"eventDocOpen"*/3:
                    events |= VbaDocumentEvents.OPEN;
                    break;
                case /*"eventDocClose"*/4:
                    events |= VbaDocumentEvents.CLOSE;
                    break;
                case /*"eventDocSync"*/5:
                    events |= VbaDocumentEvents.SYNC;
                    break;
                case /*"eventDocXmlAfterInsert"*/6:
                    events |= VbaDocumentEvents.XML_AFTER_INSERT;
                    break;
                case /*"eventDocXmlBeforeDelete"*/7:
                    events |= VbaDocumentEvents.XML_BEFORE_DELETE;
                    break;
                case /*"eventDocContentControlAfterInsert"*/8:
                    events |= VbaDocumentEvents.CONTENT_CONTROL_AFTER_INSERT;
                    break;
                case /*"eventDocContentControlBeforeDelete"*/9:
                    events |= VbaDocumentEvents.CONTENT_CONTROL_BEFORE_DELETE;
                    break;
                case /*"eventDocContentControlOnExit"*/10:
                    events |= VbaDocumentEvents.CONTENT_CONTROL_ON_EXIT;
                    break;
                case /*"eventDocContentControlOnEnter"*/11:
                    events |= VbaDocumentEvents.CONTENT_CONTROL_ON_ENTER;
                    break;
                case /*"eventDocStoreUpdate"*/12:
                    events |= VbaDocumentEvents.STORE_UPDATE;
                    break;
                case /*"eventDocContentControlContentUpdate"*/13:
                    events |= VbaDocumentEvents.CONTENT_CONTROL_CONTENT_UPDATE;
                    break;
                case /*"eventDocBuildingBlockAfterInsert"*/14:
                    events |= VbaDocumentEvents.BUILDING_BLOCK_AFTER_INSERT;
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        doc.setVbaDocumentEvents(events);
    }

    /// <summary>
    /// Reads the 2.1.4.2 CT_Mcds element which contains a list of macro names. These names appear in the Macros Window in MS Word.
    /// </summary>
    private static void readMcds(AnyXmlReader xmlReader, Document doc)
    {
        while (xmlReader.readChild("mcds"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"mcd"*/15:
                    readMcd(xmlReader, doc);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 2.1.4.1 CT_Mcd which specifies a macro name.
    /// </summary>
    private static void readMcd(AnyXmlReader xmlReader, Document doc)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"macroName"*/16:
                    // An ST_String ([ECMA-376] Part 4, Section 2.18.89) attribute that MUST equal 
                    // the name attribute with every character set to uppercase.
                    // RK Just ignore on read.
                    break;
                case /*"name"*/17:
                    // An ST_String ([ECMA-376] Part 4, Section 2.18.89) attribute that specifies 
                    // the name of the macro. name MUST NOT exceed 255 characters.
                    if (doc.getVbaProject() != null)
                        doc.getVbaProject().addMacroName(xmlReader.getValue());
                    break;
                case /*"menuHelp"*/18:
                    // According to the SPEC this MUST be ignored.
                    break;
                case /*"bEncrypt"*/19:
                    // According to the SPEC this MUST be 0. In fact it is "00" in the documents. Just ignore on read.
                    break;
                case /*"cmg"*/20:
                    // According to the SPEC this must be "56". Just ignore on read.
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
		"docEvents",
		"mcds",
		"eventDocNew",
		"eventDocOpen",
		"eventDocClose",
		"eventDocSync",
		"eventDocXmlAfterInsert",
		"eventDocXmlBeforeDelete",
		"eventDocContentControlAfterInsert",
		"eventDocContentControlBeforeDelete",
		"eventDocContentControlOnExit",
		"eventDocContentControlOnEnter",
		"eventDocStoreUpdate",
		"eventDocContentControlContentUpdate",
		"eventDocBuildingBlockAfterInsert",
		"mcd",
		"macroName",
		"name",
		"menuHelp",
		"bEncrypt",
		"cmg"
	);

}

