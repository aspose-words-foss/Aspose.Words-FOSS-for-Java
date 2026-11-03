// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2011 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Class reads fldSimple from WML or DOCX document.
/// It is created as a result of refactoring WmlFldSimpleReader and DocxFldSimpleReader.
/// </summary>
class NrxFldSimpleReader
{
    NrxFldSimpleReader(NrxInlineReaderBase inlineReader)
    {
        Debug.assertTrue(inlineReader != null);
        mInlineReader = inlineReader;
    }

    /// <summary>
    /// Reads an fldSimple element.
    /// </summary>
    void read(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DocumentBase doc = reader.getDocument();

        String fieldCode = xmlReader.readAttribute("instr", "");
        String[] fieldCodeWords = msString.split(msString.trimStart(fieldCode, ' '), ' ');
        /*FieldType*/int fieldType = FieldUtil.getFieldType(fieldCodeWords[0]);
        FieldStart fieldStart = new FieldStart(doc, new RunPr(), fieldType);
        reader.addChild(fieldStart);
        reader.addChild(new Run(doc, fieldCode));
        reader.addChild(new FieldSeparator(doc, new RunPr(), fieldType));

        fieldStart.isLocked(NrxXmlReader.xmlToBool(xmlReader.readAttribute("fldLock", "off")));

        mInlineReader.readChildren(reader, runPr);

        // We need to set field end formatting to be the same as field value formatting
        // so that the merged data could be formatted correctly after merging.
        // (Currently, merged text takes formatting from merge field end).
        IInline lastInline = ms.as(reader.getCurContainer().getLastChild(), IInline.class);
        RunPr fieldEndRunPr;

        // WORDSNET-5397 andrnosk: Do not take formatting from lastInline if there is delete revision.
        if (lastInline != null && !lastInline.getRunPr_IInline().hasDeleteRevision())
            fieldEndRunPr = AttrCollectionExtension.deepClone(lastInline.getRunPr_IInline());
        else
            fieldEndRunPr = new RunPr();

        reader.addChild(new FieldEnd(doc, fieldEndRunPr, fieldType, true));
    }

    private /*final*/ NrxInlineReaderBase mInlineReader;
}

