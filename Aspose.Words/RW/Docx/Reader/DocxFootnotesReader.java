// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msString;
import java.util.HashMap;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Reads "Footnotes" and "Endnotes" parts.
/// </summary>
class DocxFootnotesReader
{
    /// <summary>
    /// Reads all footnotes and endnotes into hashtables.
    /// </summary>
    DocxFootnotesReader(DocxDocumentReaderBase reader) throws Exception
    {
        mReader = reader;
        read(FootnoteType.FOOTNOTE);
        read(FootnoteType.ENDNOTE);
    }

    /// <summary>
    /// Gets next not-used footnote or endnote by an id. Returns null if the node is not found. Raises an exception
    /// if all footnotes with the specified ID are already inserted into a document. Allows correctly process
    /// documents that has several footnotes with same ID: next not-used footnote/endnote is returned.
    /// </summary>
    Footnote getFootnoteById(/*FootnoteType*/int footnoteType, int id)
    {
        ArrayList<Footnote> footnoteList;
        switch (footnoteType)
        {
            case FootnoteType.ENDNOTE:
                footnoteList = DictionaryExtension.getValueOrNull(mEndnoteByIdTable, id);
                break;
            case FootnoteType.FOOTNOTE:
                footnoteList = DictionaryExtension.getValueOrNull(mFootnoteByIdTable, id);
                break;
            default:
                throw new IllegalStateException("Unknown footnote type.");
        }

        if (footnoteList == null)
            return null;


        Footnote lastFootnote = null;
        for (Footnote footnote : footnoteList)
        {
            lastFootnote = footnote;

            if (footnote.getParentNode() == null)
                return footnote;
        }

        mReader.warn(WarningType.DATA_LOSS, WarningSource.DOCX,
            msString.format(WarningStrings.FOOTNOTE_WITH_SEVERAL_REFERENCES, id));

        switch (mReader.getLoadOptions().getRecoveryMode())
        {
            case DocumentRecoveryMode.TRY_RECOVER:
                // Returns footnotes with empty content. This is MS Word behavior.
                return lastFootnote != null
                    ? (Footnote)lastFootnote.deepClone(false)
                    : null;
            default:
                throw new IllegalStateException(msString.format(WarningStrings.FOOTNOTE_WITH_SEVERAL_REFERENCES, id));
        }
    }

    /// <summary>
    /// Reads a footnotes or endnotes document part.
    /// </summary>
    private void read(/*FootnoteType*/int footnoteType) throws Exception
    {
        String relType;
        String elementName;
        HashMap<Integer, ArrayList<Footnote>> dictionary;
        boolean isEndnote;

        // Select appropriate values to read either footnotes or endnotes.
        switch (footnoteType)
        {
            case FootnoteType.FOOTNOTE:
            {
                relType = mReader.getRelTypes().getFootnotes();
                elementName = "footnote";
                dictionary = mFootnoteByIdTable;
                isEndnote = false;
                break;
            }
            case FootnoteType.ENDNOTE:
            {
                relType = mReader.getRelTypes().getEndnotes();
                elementName = "endnote";
                dictionary = mEndnoteByIdTable;
                isEndnote = true;
                break;
            }
            default:
                throw new IllegalStateException("Unknown footnote type.");
        }

        NrxXmlReader xmlReader = mReader.switchToPartReaderByRelType(relType);
        if (xmlReader == null)
            return;

        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            if (msString.equals(xmlReader.getLocalName(), elementName))
            {
                // Read attributes.
                boolean isNormalSeparatorType = true;
                /*FootnoteSeparatorType*/int separatorType = FootnoteSeparatorType.FOOTNOTE_SEPARATOR;
                String idStr = null;
                while (xmlReader.moveToNextAttribute())
                {
                    switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                    {
                        case /*"type"*/0:
                            separatorType = DocxEnum.docxToFootnoteSeparatorType(xmlReader.getValue(), isEndnote);
                            isNormalSeparatorType = ("normal".equals(xmlReader.getValue()));
                            break;
                        case /*"id"*/1:
                            idStr = xmlReader.getValue();
                            break;
                        default:
                            xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, xmlReader.getLocalName()));
                            break;
                    }
                }

                // Ignore footnotes without an id.
                if (!StringUtil.hasChars(idStr))
                    continue;

                if (isNormalSeparatorType)
                {
                    // Read the footnote and add to the hashtable for later insertion into the model.
                    Footnote footnote = new Footnote(mReader.getDocument(), footnoteType);
                    DocxReaderFactory.getStoryReader().read(mReader, footnote);

                    // MS Word correctly processes documents that have several footnotes with same ID. Do the same.
                    int id = FormatterPal.xmlToInt(idStr);
                    if (dictionary.containsKey(id))
                    {
                        msArrayList.add(dictionary.get(id), footnote);
                    }
                    else
                    {
                        ArrayList<Footnote> footnoteList = new ArrayList<Footnote>();
                        msArrayList.add(footnoteList, footnote);
                        dictionary.put(id, footnoteList);
                    }
                }
                else
                {
                    // Read a footnote separator.
                    FootnoteSeparator separator = new FootnoteSeparator(mReader.getDocument(), separatorType);
                    DocxReaderFactory.getStoryReader().read(mReader, separator);
                    // It is not ideal, but we don't follow the relationship from the source
                    // and add separator to the document straight away,
                    mReader.getDocument().getFootnoteSeparators().add(separator);
                }
            }
            else
            {
                xmlReader.ignoreElement();
            }
        }
        mReader.restorePartReader();
    }

    private /*final*/ DocxDocumentReaderBase mReader;
    /// <summary>
    /// All endnotes are read here before the main document is read.
    /// Key is endnote id, value is a Footnote object (with type Endnote).
    /// </summary>
    private /*final*/ HashMap<Integer, ArrayList<Footnote>> mEndnoteByIdTable = new HashMap<Integer, ArrayList<Footnote>>();
    /// <summary>
    /// All footnotes are read here before the main document is read.
    /// Key is footnote id, value is a Footnote object.
    /// </summary>
    private /*final*/ HashMap<Integer, ArrayList<Footnote>> mFootnoteByIdTable =
        new HashMap<Integer, ArrayList<Footnote>>();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"type",
		"id"
	);

}

