// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxAnnotationReader
{
    DocxAnnotationReader(NrxAnnotationReader nrxAnnotationReader,
        DocxInlineReader inlineReader,
        DocxSectPrReader sectPrReader)
    {
        Debug.assertTrue(nrxAnnotationReader != null);
        Debug.assertTrue(inlineReader != null);
        Debug.assertTrue(sectPrReader != null);
        mNrxAnnotationReader = nrxAnnotationReader;
        mInlineReader = inlineReader;
        mSectPrReader = sectPrReader;
    }

    /// <summary>
    /// Reads an inline deletion revision.
    /// </summary>
    void readInlineDeletion(NrxDocumentReaderBase reader) throws Exception
    {
        // This is just common code that reads the attributes.
        NrxAnnotation annotation = new NrxAnnotation(reader.getXmlReader());

        // We create a deletion revision object and store it in the main reader.
        // Then we read the content of the revision and the main reader
        // applies the current deletion revision object to all created nodes.
        //
        // It is interesting to note that according to the DOCX spec, deletion and insertion
        // revisions can be recursive. I have not seen such and they will not work with this code.
        // I just presume they never occur.
        EditRevision deletion = new EditRevision(EditRevisionType.DELETION, annotation.getAuthor(), annotation.getDate());
        reader.StoryRevisionStack.push(deletion);

        // Content of the deletion revision seems to be mostly inline elements,
        // although according to the DOCX spec it can contain various Math elements.
        // For the time being, just recurse.
        mInlineReader.readChildren(reader);

        // Remove the revision object from the main reader so it is no longer applied to new nodes.
        reader.StoryRevisionStack.pop(deletion);
    }

    /// <summary>
    /// Reds an inline insertion revision. See comments above.
    /// </summary>
    void readInlineInsertion(NrxDocumentReaderBase reader) throws Exception
    {
        NrxAnnotation annotation = new NrxAnnotation(reader.getXmlReader());
        EditRevision insertion = new EditRevision(EditRevisionType.INSERTION, annotation.getAuthor(), annotation.getDate());
        reader.StoryRevisionStack.push(insertion);
        mInlineReader.readChildren(reader);
        reader.StoryRevisionStack.pop(insertion);
    }

    /// <summary>
    /// This is for "ins" inside "rPr", occurs in paragraph marks.
    /// </summary>
    static NrxAnnotation readRunPrInsertion(NrxXmlReader reader, RunPr runPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);
        runPr.setInsertRevision(new EditRevision(EditRevisionType.INSERTION, annotation.getAuthor(), annotation.getDate()));
        return annotation;
    }

    /// <summary>
    /// This is for "del" inside "rPr", occurs in paragraph marks.
    /// </summary>
    static NrxAnnotation readRunPrDeletion(NrxXmlReader reader, RunPr runPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);
        runPr.setDeleteRevision(new EditRevision(EditRevisionType.DELETION, annotation.getAuthor(), annotation.getDate()));
        return annotation;
    }

    /// <summary>
    /// This is for "moveFrom" inside "rPr", occurs in paragraph marks.
    /// </summary>
    static NrxAnnotation readRunPrMoveFrom(NrxXmlReader reader, RunPr runPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);
        runPr.setMoveFromRevision(new MoveRevision(MoveRevisionType.MOVE_FROM, annotation.getAuthor(), annotation.getDate()));
        return annotation;
    }

    /// <summary>
    /// This is for "moveTo" inside "rPr", occurs in paragraph marks.
    /// </summary>
    static NrxAnnotation readRunPrMoveTo(NrxXmlReader reader, RunPr runPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);
        runPr.setMoveToRevision(new MoveRevision(MoveRevisionType.MOVE_TO, annotation.getAuthor(), annotation.getDate()));
        return annotation;
    }

    void readParaPrRevision(NrxDocumentReaderBase reader, ParaPr paraPr, RunPr paragraphBreakRunPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        while (xmlReader.readChild("pPrChange"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pPr"*/0:
                    mNrxAnnotationReader.readParaPr(reader, paraPr, paragraphBreakRunPr, annotation);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    void readSectPrRevision(NrxDocumentReaderBase reader, SectPr sectPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        while (xmlReader.readChild("sectPrChange"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"sectPr"*/1:
                    // See comments inside ReadParaPrRevision method.
                    //
                    // But note, it looks we should not be excluding rsid attributes from collapse here.
                    // Probably because rsid section is specified directly on "sectPr", but
                    // rsid for paragraph is specified on "p", not on "pPr.
                    SectPr revPr = AttrCollectionExtension.deepClone(sectPr);
                    sectPr.clear();

                    // WORDSNET-25040 Try to copy Word behavior and handle certain revised attributes.
                    revPr.copyTo(sectPr, SectAttr.ORIENTATION);
                    revPr.copyTo(sectPr, SectAttr.PAGE_WIDTH);
                    revPr.copyTo(sectPr, SectAttr.PAGE_HEIGHT);
                    revPr.copyTo(sectPr, SectAttr.SECTION_START);

                    revPr.copyTo(sectPr, SectAttr.COLUMNS_SPACING);

                    mSectPrReader.read(reader, sectPr);
                    revPr.collapse(sectPr);
                    sectPr.setFormatRevision(new FormatRevision(revPr, annotation.getAuthor(), annotation.getDate()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads math del(§17.13.5.13) and ins(§17.13.5.16) run revisions.
    /// </summary>
    void readMathRunPrRevision(NrxDocumentReaderBase reader, RunPr runPr, boolean isDeleteMathPrRevision) throws Exception
    {
        String tagName = (isDeleteMathPrRevision) ? "del" : "ins";
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = (isDeleteMathPrRevision) ? readRunPrDeletion(xmlReader, runPr) : readRunPrInsertion(xmlReader, runPr);

        // RK I don't fully understand this. Why formatting revision tag name is "del" or "ins" here?
        readRevisionCore(reader, xmlReader, tagName, runPr, annotation);

        // DD: That is what MS Word seems to do. See AW Office Math Implementation notes for details.
        runPr.rejectFormatRevision();
    }

    void readRunPrRevision(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        readRevisionCore(reader, xmlReader, "rPrChange", runPr, annotation);
    }

    /// <summary>
    /// Read previous RunPr properties (see ISO 29500 17.3.2.27 rPr (Previous Run Properties))
    /// </summary>
    private void readRevisionCore(
        NrxDocumentReaderBase reader,
        NrxXmlReader xmlReader,
        String tagName,
        RunPr runPr,
        NrxAnnotation annotation) throws Exception
    {
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPr"*/2:
                    mNrxAnnotationReader.readRunPr(reader, runPr, annotation);
                    break;
                // WORDSNET-10596 <m:t> element inside <w:del> occurred.
                case /*"t"*/3:
                    DocxReaderFactory.getRunReader().readText(reader, runPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// This is the case where number formatting was added to a paragraph.
    /// </summary>
    static void readNumberingInsertion(NrxXmlReader reader, ParaPr paraPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);

        ParagraphNumberRevision numberRevision = new ParagraphNumberRevision();
        numberRevision.setAuthor(annotation.getAuthor());
        numberRevision.setDateTime(annotation.getDate());
        numberRevision.isInsertion(true);

        paraPr.setNumberRevision(numberRevision);
    }

    /// <summary>
    /// This covers the case where number formatting was accepted or removed
    /// or where the number was simply changed.
    /// </summary>
    static void readNumberingChange(NrxXmlReader reader, ParaPr paraPr)
    {
        NrxAnnotation annotation = new NrxAnnotation(reader);

        ParagraphNumberRevision numberRevision = new ParagraphNumberRevision();
        numberRevision.setAuthor(annotation.getAuthor());
        numberRevision.setDateTime(annotation.getDate());
        // RK Yes, this code is in WmlAnnnotationReader.
        NrxAnnotationReader.readOriginal(numberRevision, annotation.getOriginal());
        numberRevision.setWasNumbered(true);
        paraPr.setNumberRevision(numberRevision);
    }

    static void readTablePrFormatRevision(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        readTablePrFormatRevision(reader, tablePr, reader.getXmlReader().getLocalName());
    }

    static void readRowPrFormatRevision(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        readTablePrFormatRevision(reader, tablePr, reader.getXmlReader().getLocalName());
    }

    static void readCellPrRevision(NrxDocumentReaderBase reader, CellPr cellPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        while (xmlReader.readChild("tcPrChange"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tcPr"*/4:
                    {
                        // See comments inside ReadParaPrRevision method.
                        WordAttrCollection revPr = AttrCollectionExtension.deepClone(cellPr);
                        cellPr.clear();
                        DocxReaderFactory.getCellPrReader().read(reader, cellPr);
                        revPr.collapse(cellPr);
                        cellPr.setFormatRevision(new FormatRevision(revPr, annotation.getAuthor(), annotation.getDate()));
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read deletion/insertion revisions to specified <paramref name="attrCollection"/>.
    /// </summary>
    /// <remarks>
    /// Used for reading table, row (17.13.5.12, 17.13.5.17 and cell (17.13.5.1, 17.13.5.2) revisions.
    /// </remarks>
    static void readEditRevision(NrxDocumentReaderBase reader, WordAttrCollection attrCollection, /*EditRevisionType*/int revisionType)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation revision = new NrxAnnotation(xmlReader);

        switch (revisionType)
        {
            case EditRevisionType.DELETION:
                attrCollection.setDeleteRevision(new EditRevision(revisionType, revision.getAuthor(), revision.getDate()));
                break;
            case EditRevisionType.INSERTION:
                attrCollection.setInsertRevision(new EditRevision(revisionType, revision.getAuthor(), revision.getDate()));
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private static void readTablePrFormatRevision(NrxDocumentReaderBase reader, TablePr tablePr, String parentTag)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        NrxAnnotation annotation = new NrxAnnotation(xmlReader);

        while (xmlReader.readChild(parentTag))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tblPr"*/5:
                case /*"tblPrEx"*/6:
                    readTablePrFormatRevisionCore(reader, tablePr, annotation, true);
                    break;
                case /*"trPr"*/7:
                    readTablePrFormatRevisionCore(reader, tablePr, annotation, false);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read table, row and tblPrEx (17.4.61) revisions to TablePr bag.
    /// </summary>
    private static void readTablePrFormatRevisionCore(NrxDocumentReaderBase reader, TablePr tablePr, NrxAnnotation annotation, boolean isTblPr)
    {
        WordAttrCollection revPr =  AttrCollectionExtension.deepClone(tablePr);

        // WORDSNET-6086 andrnosk: RevPr can already have format revisions from Table, we need to preserve them.
        // That is why we need to accept current revisions and add new revisions to this collection using Collapse method (see below).
        if (revPr.hasFormatRevision())
            revPr.acceptFormatRevision();

        // Keep all attributes except revision.
        tablePr.remove(RevisionAttr.FORMAT_REVISION);

        // Read 'w:tblPr', 'w:tblPrEx' or 'w:trPr' element from the specified reader.
        if (isTblPr)
            // Dmatv: per WORDSNET-17857, I've changed the logic that decrements FrameLeft/FrameTop.
            // No excessive decrement will happen after reading twice into the same tablePr.
            DocxTablePrReader.readTblPr(reader, tablePr);
        else
            DocxRowPrReader.read(reader, tablePr);

        revPr.collapse(tablePr);
        tablePr.setFormatRevision(new FormatRevision(revPr, annotation.getAuthor(), annotation.getDate()));
    }

    /// <summary>
    /// Reads 'w:moveFromRangeStart' and 'w:moveToRangeStart' node depending on isMoveFrom flag.
    /// </summary>
    static void readMoveRangeStart(NrxDocumentReaderBase reader, boolean isMoveFrom)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        MoveRangeStart moveRangeStart;

        if (isMoveFrom)
            moveRangeStart = new MoveFromRangeStart(reader.getDocument(), annotation.getId(), annotation.getAuthor(), annotation.getName(), annotation.getDate(), annotation.getDisplacedBy());
        else
            moveRangeStart = new MoveToRangeStart(reader.getDocument(), annotation.getId(), annotation.getAuthor(), annotation.getName(), annotation.getDate(), annotation.getDisplacedBy());

        if (annotation.getColFirst() >= 0 && annotation.getColLast() >= 0)
        {
            moveRangeStart.setFirstColumn(annotation.getColFirst());
            moveRangeStart.setLastColumn(annotation.getColLast());
        }

        reader.addCrossStructureAnnotation(moveRangeStart);

        MoveRevision revision = new MoveRevision(isMoveFrom ? MoveRevisionType.MOVE_FROM : MoveRevisionType.MOVE_TO,
            annotation.getAuthor(), annotation.getDate());
        reader.StoryRevisionStack.push(revision, true);
    }

    /// <summary>
    /// Reads 'w:moveFromRangeEnd' and 'w:moveToRangeEnd' node depending on isMoveFrom flag.
    /// </summary>
    static void readMoveRangeEnd(NrxDocumentReaderBase reader, boolean isMoveFrom)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        NrxAnnotation annotation = new NrxAnnotation(xmlReader);
        if (!annotation.hasId())
            return;

        MoveRangeEnd moveRangeEnd;

        if (isMoveFrom)
            moveRangeEnd = new MoveFromRangeEnd(reader.getDocument(), annotation.getId(), annotation.getDisplacedBy());
        else
            moveRangeEnd = new MoveToRangeEnd(reader.getDocument(), annotation.getId(), annotation.getDisplacedBy());

        /*MoveRevisionType*/int revisionType = isMoveFrom ? MoveRevisionType.MOVE_FROM : MoveRevisionType.MOVE_TO;
        MoveRevision moveRevision = new MoveRevision(revisionType, annotation.getAuthor(), annotation.getDate());

        RevisionBase revision = reader.StoryRevisionStack.pop(moveRevision);
        if (revision != null)
            reader.addCrossStructureAnnotation(moveRangeEnd);
    }

    /// <summary>
    /// Reds an inline moveTo/moveFrom revisions.
    /// </summary>
    void readInlineMove(NrxDocumentReaderBase reader, boolean isMoveTo) throws Exception
    {
        NrxAnnotation annotation = new NrxAnnotation(reader.getXmlReader());
        /*MoveRevisionType*/int revType = (isMoveTo) ? MoveRevisionType.MOVE_TO : MoveRevisionType.MOVE_FROM;
        MoveRevision revision = new MoveRevision(revType, annotation.getAuthor(), annotation.getDate());

        reader.StoryRevisionStack.push(revision, false);
        mInlineReader.readChildren(reader);
        reader.StoryRevisionStack.pop(revision);
    }

    /// <summary>
    /// Reads customXml edit revision from the reader into
    /// <see cref="NrxDocumentReaderBase.CurrentCustomXmlRevision"/>.
    /// </summary>
    /// <remarks>
    /// 'Start' of revision range is read here and placed to the temporary storage in
    /// <see cref="NrxDocumentReaderBase.CurrentCustomXmlRevision"/>.
    /// So, when we read SDT itself, we know if some revision was started and therefore can apply it appropriately.
    /// </remarks>
    static void readCustomXmlEditRevision(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String revisionName = xmlReader.getLocalName();
        /*EditRevisionType*/int revisionType = ("customXmlDelRangeStart".equals(revisionName))
            ? EditRevisionType.DELETION
            : EditRevisionType.INSERTION;

        NrxAnnotation revision = new NrxAnnotation(xmlReader);

        // Set current customXml revision in reader to indicate that subsequent SDTs will have revisions.
        reader.setCurrentCustomXmlRevision(new EditRevision(revisionType, revision.getAuthor(), revision.getDate()));
    }

    /// <summary>
    /// Reads customXml move revision from the reader into
    /// <see cref="NrxDocumentReaderBase.CurrentCustomXmlRevision"/>.
    /// </summary>
    /// <remarks>
    /// 'Start' of revision range is read here and placed to the temporary storage in
    /// <see cref="NrxDocumentReaderBase.CurrentCustomXmlRevision"/>.
    /// So, when we read SDT itself, we know if some revision was started and therefore can apply it appropriately.
    /// </remarks>
    static void readCustomXmlMoveRevision(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String revisionName = xmlReader.getLocalName();
        /*MoveRevisionType*/int revisionType = ("customXmlMoveFromRangeStart".equals(revisionName))
            ? MoveRevisionType.MOVE_FROM
            : MoveRevisionType.MOVE_TO;

        NrxAnnotation revision = new NrxAnnotation(xmlReader);

        // Set current customXml revision in reader to indicate that subsequent SDTs will have revisions.
        reader.setCurrentCustomXmlRevision(new MoveRevision(revisionType, revision.getAuthor(), revision.getDate()));
    }

    /// <summary>
    /// Resets customXml revision in <see cref="NrxDocumentReaderBase.CurrentCustomXmlRevision"/>.
    /// </summary>
    /// <remarks>
    /// This method is called during reading of customXmlXXXRangeEnd elements.
    /// </remarks>
    static void clearCustomXmlRevision(NrxDocumentReaderBase reader)
    {
        reader.setCurrentCustomXmlRevision(null);
    }

    private /*final*/ NrxAnnotationReader mNrxAnnotationReader;
    private /*final*/ DocxInlineReader mInlineReader;
    private /*final*/ DocxSectPrReader mSectPrReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"pPr",
		"sectPr",
		"rPr",
		"t",
		"tcPr",
		"tblPr",
		"tblPrEx",
		"trPr"
	);

}

