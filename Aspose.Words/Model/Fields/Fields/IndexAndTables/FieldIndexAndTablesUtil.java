// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.DrFont;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Provides utility methods shared between INDEX, TOC and TOA fields.
/// </summary>
class FieldIndexAndTablesUtil
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private FieldIndexAndTablesUtil()
    {
        // Hide from using.
    }

    /// <summary>
    /// Prepares and returns a <see cref="DocumentBuilder"/> instance used to write an INDEX, TOC or TOA field result.
    /// </summary>
    static DocumentBuilder getDocumentBuilder(Field field)
    {
        DocumentBuilder builder = new DocumentBuilder(field.fetchDocument());

        moveOutInlineSdt(field);

        if (requiresSplitResultFromTrailingNodes(field) && !isFirstNonZeroLengthChild(field))
        {
            // WORDSNET-9673 If field occupies more than one paragraphs, insert paragraph with attributes from first one.
            if (field.getSeparator().getParentNode() != field.getEnd().getParentNode())
                builder.moveTo(field.getSeparator().getParentParagraph());
            else
                builder.moveTo(field.getEnd());

            convertOuterInlineSdtToBlock(field);

            builder.writeln();
        }

        builder.moveTo(field.getEnd());

        return builder;
    }

    private static void moveOutInlineSdt(Field field)
    {
        FieldStart start = field.getStart();
        StructuredDocumentTag sdt = ms.as(start.getParentNode(), StructuredDocumentTag.class);
        if (sdt == null)
            return;

        Debug.assertTrue(sdt.getLevel() == MarkupLevel.INLINE);

        if (sdt.isFirstNonZeroLengthChild())
            return;

        sdt.insertNext(field.getStart(), null);
    }

    static boolean isFirstNonZeroLengthChild(Field field)
    {
        FieldStart start = field.getStart();
        if (!start.isFirstNonZeroLengthChild())
            return false;

        StructuredDocumentTag sdt = ms.as(start.getParentNode(), StructuredDocumentTag.class);
        if (sdt == null)
            return true;

        Debug.assertTrue(sdt.getLevel() == MarkupLevel.INLINE);

        return sdt.isFirstNonZeroLengthChild();
    }

    static void convertOuterInlineSdtToBlock(Field field)
    {
        StructuredDocumentTag sdt = (StructuredDocumentTag)field.getStart().getAncestor(StructuredDocumentTag.class);

        if (sdt == null)
            return;

        if (sdt.getLevel() != MarkupLevel.INLINE)
            return;

        sdt.convertToBlock();
    }

    private static boolean requiresSplitResultFromTrailingNodes(Field field)
    {
        switch (field.getType())
        {
            case FieldType.FIELD_INDEX:
            case FieldType.FIELD_TOC:
                return true;
            case FieldType.FIELD_TOA:
                return false;
            default:
                throw new IllegalArgumentException();
        }
    }

    /// <summary>
    /// Creates a paragraph to write an INDEX, TOC or TOA entry and sets its properties.
    /// </summary>
    static void setUpEntryParagraph(Field field, DocumentBuilder builder, /*StyleIdentifier*/int styleIdentifier)
    {
        // One entry - one paragraph. Also we separate the entry paragraph from the End's parent.
        builder.moveTo(field.getEnd());
        builder.writeln();
        builder.moveTo(builder.getCurrentParagraph().getPreviousNonAnnotationSibling());

        ParaPr fieldStartParaPr =  AttrCollectionExtension.deepClone(field.getStart().getParentParagraph().getParaPr());

        boolean isBidiTextSupported = builder.getDocument().getFieldOptions().isBidiTextSupportedOnUpdate();
        boolean isBidiParagraph = isBidiTextSupported && builder.getParagraphFormat().getBidi();

        // Clear paragraph format but restore BIDI.
        builder.getParagraphFormat().clearFormatting();
        builder.getParagraphFormat().setStyleIdentifier(styleIdentifier);
        if (isBidiParagraph)
            builder.getParagraphFormat().setBidi(true);

        boolean isBidiParagraphBreak = isBidiTextSupported && builder.getCurrentParagraph().getParagraphBreakFont().getBidi();

        // Clear paragraph break format but restore BIDI.
        builder.getCurrentParagraph().getParagraphBreakFont().clearFormatting();
        if (isBidiParagraphBreak)
            builder.getCurrentParagraph().getParagraphBreakFont().setBidi(true);

        // andrnosk: WORDSNET-8500 Copy frame attributes to TOC entry paragraph,
        // we have to preserve frame related attributes.
        fieldStartParaPr.mirrorTo(builder.getCurrentParagraph().getParaPr(), G_FRAME_ATTRS);
    }

    /// <summary>
    /// Sets font properties for an INDEX or TOC entry's paragraph break or tab stop.
    /// </summary>
    static void setFontForTabStopOrParagraphBreak(Document document, IRunAttrSource runAttrSource)
    {
        boolean isBidiTextSupported = document.getFieldOptions().isBidiTextSupportedOnUpdate();

        if (document.getThemeInternal() != null)
        {
            runAttrSource.setRunAttr(FontAttr.NAME_ASCII, ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
            runAttrSource.setRunAttr(FontAttr.NAME_OTHER, ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));

            if (isBidiTextSupported)
            {
                runAttrSource.setRunAttr(FontAttr.NAME_BI, ComplexFontName.fromTheme(ThemeFontCore.MINOR_BIDI));
                runAttrSource.setRunAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromTheme(ThemeFontCore.MINOR_EAST_ASIA));
            }
        }
        else
        {
            runAttrSource.setRunAttr(FontAttr.NAME_ASCII, ComplexFontName.fromName(DEFAULT_ENTRY_FONT_NAME));
            runAttrSource.setRunAttr(FontAttr.NAME_OTHER, ComplexFontName.fromName(DEFAULT_ENTRY_FONT_NAME));

            if (isBidiTextSupported)
            {
                runAttrSource.setRunAttr(FontAttr.NAME_BI, ComplexFontName.fromName(DEFAULT_ENTRY_FONT_NAME_BI));
                runAttrSource.setRunAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromName(DEFAULT_ENTRY_FONT_NAME));
            }
        }

        runAttrSource.setRunAttr(FontAttr.SIZE, DEFAULT_ENTRY_FONT_SIZE);
        if (isBidiTextSupported)
            runAttrSource.setRunAttr(FontAttr.SIZE_BI, DEFAULT_ENTRY_FONT_SIZE);

        runAttrSource.setRunAttr(FontAttr.NO_PROOFING, AttrBoolEx.TRUE);
    }

    /// <summary>
    /// Checks whether the current paragraph of the specified document builder has a predefined tab stop
    /// for an INDEX, TOC or TOA entry paragraph and appends one if it has not.
    /// </summary>
    static void ensureEntryParagraphTabStop(DocumentBuilder builder, Paragraph sourceParagraph)
    {
        if (isInsideFloatingTable(builder.getCurrentParagraph()))
            return;

        if (extractRightTabStop(builder.getCurrentParagraph()) != null)
            return;

        Section section = builder.getCurrentSection();
        int columnsCount = section.getSectPr().getColumnsCount();

        // WORDSNET-8152 Use ContentWidth instead of PageWidth - (LeftMargin + RightMargin)
        // because it takes into account a Gutter.
        double contentWidth = section.getPageSetup().getContentWidth();
        double tabStopPosition = contentWidth;
        if (columnsCount > 1)
        {
            // WORDSNET-8457 If the parent section has multiple columns, we need to consider spaces between them and
            // distribute available content width between the columns equally.
            double columnsSpacing = ConvertUtilCore.twipToPoint(section.getSectPr().getColumnsSpacing());
            tabStopPosition = (contentWidth - columnsSpacing*(columnsCount - 1))/columnsCount;
        }

        // WORDSNET-12456 The first paragraph`s tab leader determines TOC's tab leaders.
        TabStop stop = extractRightTabStop(sourceParagraph);
        /*TabLeader*/int leader = (stop != null) ? stop.getLeader() : TabLeader.DOTS;

        tabStopPosition -= 0.5; // MS Word does it.

        addTabStopToEntryParagraph(builder.getCurrentParagraph(), tabStopPosition, TabAlignment.RIGHT, leader);
    }

    private static boolean isInsideFloatingTable(Paragraph paragraph)
    {
        Table table = paragraph.getParentTable();
        if (table == null)
            return false;

        return table.isFloating();
    }

    private static TabStop extractRightTabStop(Paragraph paragraph)
    {
        if (paragraph == null)
            return null;

        ParaPr paraPr = paragraph.getExpandedParaPr(ParaPrExpandFlags.NORMAL);
        TabStopCollection stops = paraPr.getTabStops();

        if (stops == null)
            return null;

        if (stops.getCount() == 0)
            return null;

        if (stops.get(stops.getCount() - 1).getAlignment() != TabAlignment.RIGHT)
            return null;

        return stops.get(stops.getCount() - 1);
    }

    /// <summary>
    /// Creates and appends tab stop with the given properties to the INDEX, TOC or TOA entry paragraph's collection
    /// if a tab stop with the specified position has not been added to it yet.
    /// </summary>
    private static void addTabStopToEntryParagraph(
        Paragraph paragraph,
        double position,
        /*TabAlignment*/int alignment,
        /*TabLeader*/int leader)
    {
        // All TOCN styles are auto redefined, but AW does not support this feature at the moment.
        // We will do that ourselves if needed.
        ParagraphFormat entryParagraphFormat = paragraph.getParagraphFormat();
        TabStop tabStop = entryParagraphFormat.getStyle().getParagraphFormat().getTabStops().get(position);
        if (tabStop == null || tabStop.isClear())
            entryParagraphFormat.getTabStops().add(position, alignment, leader);
    }

    /// <summary>
    /// Creates and inserts a dummy (i.e. temporary) reference node to an INDEX, TOC or TOA entry paragraph.
    /// </summary>
    static Node createAndInsertDummyRefNode(DocumentBuilder builder)
    {
        Node dummyRefNode = new Run(builder.getDocument());
        builder.getCurrentParagraph().appendChild(dummyRefNode);
        return dummyRefNode;
    }

    /// <summary>
    /// Gets an internal representation of the given entry type.
    /// </summary>
    private static int getEntryType(String entryType)
    {
        if (entryType == null)
            return NULL_ENTRY_TYPE;

        // We should be able to distinguish null and empty strings as they are processed differently by MS Word.
        if (entryType.length() == 0)
            return EMPTY_ENTRY_TYPE;

        // MS Word considers only the first character of an entry type string.
        return msChar.toUpperInvariant(entryType.charAt(0));
    }

    /// <summary>
    /// Gets an internal representation of the given TOC entry type.
    /// </summary>
    static int getTocEntryType(FieldCode fieldCode, String switchName)
    {
        if (!fieldCode.hasSwitch(switchName))
            return MISSING_ENTRY_TYPE;

        String entryType = fieldCode.getSwitchArgumentAsString(switchName);

        return getEntryType(entryType);
    }

    /// <summary>
    /// Gets an internal representation of the given INDEX entry type.
    /// </summary>
    static int getIndexEntryType(String entryType)
    {
        return getEntryType(entryType);
    }

    static void ensureTocEntryTabStop(Run run, int entryLevel, boolean isTableOfFigures)
    {
        Debug.assertTrue(msString.equals(run.getText(), ControlChar.TAB));
        NodeRange range = new NodeRange(run.getParentParagraph().getFirstChild(), true, run, false);
        String text = NodeTextCollector.getText(range, true);

        double tabStopPosition = getTabStopPosition(run.fetchDocument(), run.getFont(), text, entryLevel, isTableOfFigures);

        if (!MathUtil.isMinValue(tabStopPosition))
            addTabStopToEntryParagraph(run.getParentParagraph(), tabStopPosition, TabAlignment.LEFT, TabLeader.NONE);

        setFontForTabStopOrParagraphBreak(run.fetchDocument(), run);
    }

    private static double getTabStopPosition(Document document, Font font, String text, int level, boolean isTableOfFigures)
    {
        final double MIN_INDENT_PADDING_WIDTH = 11d;
        final double MIN_HANGING_PADDING_WIDTH = 4d;

        DrFont drawingFont = document.getFontProvider().fetchDrFont(font.getName(), (float)font.getSize(), font.getFontStyle());

        double listLabelWidth = drawingFont.getTextWidthPoints(text);

        ParagraphFormat paragraphFormat = getParagraphFormatForLevel(level, document, isTableOfFigures);

        double firstLineIndent = paragraphFormat.getFirstLineIndent();
        double leftIndent = paragraphFormat.getLeftIndent();
        double paddingPosition = leftIndent + firstLineIndent + listLabelWidth;

        boolean isHanging = firstLineIndent < 0;

        double tabStopPosition = getTabStopPositionFromTocStyle(paddingPosition, paragraphFormat);

        if (MathUtil.isMinValue(tabStopPosition))
        {
            paddingPosition += !isHanging
                ? MIN_INDENT_PADDING_WIDTH
                : MIN_HANGING_PADDING_WIDTH;

            tabStopPosition = getDefaultTabStopPosition(level, paddingPosition, document);
        }

        // WORDSNET-18715 Paragraph hanging aware
        if (isHanging && MathUtil.isLessOrEqual(tabStopPosition, -firstLineIndent))
            return -Double.MAX_VALUE;

        return tabStopPosition;
    }

    private static double getTabStopPositionFromTocStyle(double paddingPosition, ParagraphFormat paragraphFormat)
    {
        double firstLineIndent = paragraphFormat.getFirstLineIndent();
        double leftIndent = paragraphFormat.getLeftIndent();

        double paddingWidth2 = firstLineIndent < 0 ? leftIndent - paddingPosition : 0.0;

        TabStopCollection paraTabStops = paragraphFormat.getTabStops();
        TabStop tabStop = paraTabStops.after(paddingPosition);

        boolean considerUseTocStyleTabStop = (tabStop != null) && (tabStop.getAlignment() == TabAlignment.LEFT);

        if (considerUseTocStyleTabStop)
        {
            double paddingWidth = tabStop.getPosition() - paddingPosition;

            if ((paddingWidth2 > 0) && (paddingWidth2 < paddingWidth))
                paddingWidth = paddingWidth2;

            if (paddingWidth > 0)
                return paddingPosition + paddingWidth;
        }

        return -Double.MAX_VALUE;
    }

    private static double getDefaultTabStopPosition(int entryLevel, double paddingPosition, Document document)
    {
        final int MAX_ENTRY_LEVEL = 9;

        ParagraphFormat paragraphFormat = getParagraphFormatForLevel(entryLevel, document, false);

        double firstLineIndent = paragraphFormat.getFirstLineIndent();
        double leftIndent = paragraphFormat.getLeftIndent();

        double paddingWidth2 = firstLineIndent < 0 ? leftIndent - paddingPosition : 0.0;

        double paddingWidth = 0.0;
        if (paddingWidth2 > 0)
            paddingWidth = paddingWidth2;

        if (paddingWidth > 0)
            return paddingPosition + paddingWidth;

        if (entryLevel < MAX_ENTRY_LEVEL)
        {
            ParagraphFormat nextLevelParagraphFormat = getParagraphFormatForLevel(entryLevel + 1, document, false);
            double nextLevelIndent = nextLevelParagraphFormat.getLeftIndent();
            if (nextLevelIndent - paddingPosition > 0)
                return nextLevelIndent;

            return getDefaultTabStopPosition(entryLevel + 1, paddingPosition, document);
        }

        return paddingPosition;
    }

    private static ParagraphFormat getParagraphFormatForLevel(int entryLevel, Document document, boolean isTableOfFigures)
    {
        final double DEFAULT_TOC_ENTRY_LEFT_INDENT_STEP = 11d;

        /*StyleIdentifier*/int styleIdentifier = FieldToc.getStyleIdentifierForLevel(entryLevel, isTableOfFigures);
        Style style = document.getStyles().getBySti(styleIdentifier, false);

        if (style != null)
        {
            // WORDSNET-16347 Take into account base styles.
            ParaPr paraPr = style.getExpandedParaPr(ParaPrExpandFlags.NORMAL);
            // Bind lifetime of paraPr for C++ porting. Otherwise NullReferenceException will be thrown,
            // since paraPr will becode out of scope.
            return new ParagraphFormat(paraPr, document.getStyles());
        }

        // Correct built-in TOC styles to make the TOC look like updated by Word 2010.
        style = document.getStyles().getBuiltInStyles().getByStyleIdentifier(styleIdentifier);
        IParaAttrSource styleParaPr = AttrCollectionExtension.deepClone(style.getParaPr());
        // Bind lifetime of styleParaPr for C++ porting. Otherwise NullReferenceException will be thrown,
        // since styleParaPr will becode out of scope.
        ParagraphFormat paragraphFormat = new ParagraphFormat(styleParaPr, document.getStyles());
        paragraphFormat.setLeftIndent((entryLevel - 1) * DEFAULT_TOC_ENTRY_LEFT_INDENT_STEP);

        return paragraphFormat;
    }

    static Document openRefDocument(FieldRD field) throws Exception
    {
        String fileName = getRefDocFilePath(field);
        if (msString.isNullOrEmpty(fileName))
            return null;

        Stream stream = FieldUtil.openStream(fileName, field.getDocument().getResourceLoadingCallback());
        try /*JAVA: was using*/
        {
            return stream != null
                ? new Document(stream, null, false)
                : null;
        }
        finally { if (stream != null) stream.close(); }
    }

    private static String getRefDocFilePath(FieldRD field) throws Exception
    {
        String fileName = field.getFileName();
        if (msString.isNullOrEmpty(fileName))
            return null;

        Document document = field.fetchDocument();

        if (field.isPathRelative() && !msString.isNullOrEmpty(document.getOriginalFileName()))
            return Path.combine(Path.getDirectoryName(document.getOriginalFileName()), fileName);

        return fileName;
    }

    static final int MISSING_ENTRY_TYPE = -1;
    static final int NULL_ENTRY_TYPE = -2;
    private static final int EMPTY_ENTRY_TYPE = -3;

    // Most likely these values are taken from some source like a standart TOCN style or a TOCN style from template.
    // Need to investigate further.
    private static final String DEFAULT_ENTRY_FONT_NAME = "Calibri";
    private static final String DEFAULT_ENTRY_FONT_NAME_BI = "Arial";
    private static final int DEFAULT_ENTRY_FONT_SIZE = 22;

    private static /*final*/ int[] G_FRAME_ATTRS =
    {
        ParaAttr.FRAME_LEFT,
        ParaAttr.FRAME_TOP,
        ParaAttr.FRAME_WRAP_TYPE,
        ParaAttr.FRAME_WIDTH,
        ParaAttr.FRAME_HEIGHT,
        ParaAttr.FRAME_LOCK_ANCHOR,
        ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT,
        ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT,
        ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION,
        ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION,
        ParaAttr.FRAME_HORIZONTAL_ALIGNMENT,
        ParaAttr.FRAME_VERTICAL_ALIGNMENT,
        ParaAttr.FRAME_TEXT_ORIENTATION,
        ParaAttr.FRAME_SUPPRESS_OVERLAP
    };
}

