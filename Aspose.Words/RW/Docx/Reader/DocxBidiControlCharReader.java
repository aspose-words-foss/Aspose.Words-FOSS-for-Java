// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2015 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.BidiControlCharUtil;
import com.aspose.BidiChars;


/// <summary>
/// Provides static method for reading 'w:dir' and 'w:bdo' elements.
/// </summary>
/*static*/ class DocxBidiControlCharReader
{
	/* Simulation of static class by using private constructor */
	private DocxBidiControlCharReader()
	{}

    /// <summary>
    /// Reads BiDi control character into a model.
    /// In the model it will be stored as a Run with a corresponding
    /// Unicode BiDi control character <see cref="BidiChars"/>.
    /// </summary>
    static void read(NrxDocumentReaderBase reader, DocxInlineReader inlineReader) throws Exception
    {
        // Reader should be positioned on either 'dir' or 'bdo' element.
        NrxXmlReader xmlReader = reader.getXmlReader();
        String bidiControlCharType = xmlReader.getLocalName();
        
        Debug.assertTrue(("dir".equals(bidiControlCharType)) || ("bdo".equals(bidiControlCharType)));

        // Read text direction attribute.
        String bidiTextDirection = xmlReader.readAttribute("ltr");
        char bidiChar = BidiControlCharUtil.toBidiChar(bidiControlCharType, bidiTextDirection);
        
        // Add run with BiDi control character.
        Run run = new Run(reader.getDocument(), Character.toString(bidiChar), new RunPr());
        reader.addChild(run);
        
        // Read children elements.
        xmlReader.moveToElement();
        while (xmlReader.readChild(bidiControlCharType))
            inlineReader.readChild(reader);

        // Close 'bdo' or 'dir' element.
        run = new Run(reader.getDocument(), Character.toString(BidiChars.PDF), new RunPr());
        reader.addChild(run);
    }
}

