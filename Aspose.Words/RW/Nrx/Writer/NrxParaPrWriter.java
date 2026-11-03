// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class NrxParaPrWriter
{
	/* Simulation of static class by using private constructor */
	private NrxParaPrWriter()
	{}

    static void write(ParaPr paraPr, INrxWriterContext writer) throws Exception
    {
        if (writeStart(paraPr, writer))
            writer.getBuilder().endElement();    //w:pPr
    }

    /// <summary>
    /// Writes paragraph formatting properties. NOTE does not close the pPr element!
    /// If return value is true, you need to close the pPr element.
    /// </summary>
    /// <returns>True if at least one paragraph property was written.</returns>
    static boolean writeStart(ParaPr paraPr, INrxWriterContext writer) throws Exception
    {
        if ((paraPr == null) || (paraPr.getCount() == 0))
            return false;

        NrxXmlBuilder builder = writer.getBuilder();

        if (paraPr.getFormatRevision() != null)
        {
            // "w:pPr" is written because format revision is present in paragraph properties.
            builder.startElement("w:pPr");

            // Calculate and write the AfterChanges attribute collection.
            WordAttrCollection afterChanges = AttrCollectionExtension.deepClone(paraPr);
            afterChanges.acceptFormatRevision();
            writeProps(afterChanges, false, writer, false);

            // Write the BeforeChanges attribute collection.
            builder.writeRevisionStart(paraPr.getFormatRevision(), "w:pPrChange", writer.getNextAnnotationId());
            builder.startElement("w:pPr");
            writeProps(paraPr, false, writer, true);
            builder.endElement("w:pPr");
            builder.writeRevisionEnd(); //w:pPrChange

            // If format revision is present in paragraph properties then for sure "w:pPr" was written.
            return true;
        }
        else
        {
            // This is normal course, no revisions.
            return writeProps(paraPr, true, writer, false);
        }
    }

    private static boolean writeProps(WordAttrCollection attrs, boolean doWriteParaPrStart, INrxWriterContext writer, boolean isFormattingRevision) throws Exception
    {
        // WORDSNET-27683 The customer asked not to write a framePr element, if it can be omitted, i.e. if a frame is
        // not applied.
        clearFramePropertiesIfNotFramed(attrs);

        boolean isDocx = writer.isDocx();
        boolean isIsoStrict = isDocx && (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        NrxXmlBuilder builder = writer.getBuilder();

        // Use counter to determine if there are any properties that need to be written in w:pPr declaration.
        // That is used to avoid writing empty w:pPr declarations.
        // Checking paraPr.Count does not help here because there can be attributes that should not be written in WordML.
        int writableAttributesCounter = 0;

        String styleId = null;
        Object keepNext = null;
        Object keepLines = null;
        Object pageBreakBefore = null;

        String frameDropCap = null;
        Object frameLines = null;
        Object frameW = null;
        Object frameH = null;
        Object frameVSpace = null;
        Object frameHSpace = null;
        String frameWrap = null;
        String frameHAnchor = null;
        String frameVAnchor = null;
        Object frameX = null;
        String frameXAlign = null;
        Object frameY = null;
        String frameYAlign = null;
        String frameHRule = null;
        Object frameAnchorLock = null;

        Object widowControl = null;

        Object listId = null;
        Object listLevel = null;

        Object supressLineNumbers = null;

        Border bdrTop = null;
        Border bdrLeft = null;
        Border bdrBottom = null;
        Border bdrRight = null;
        Border bdrBetween = null;
        Border bdrBar = null;

        Shading shading = null;

        TabStopCollection tabStops = null;

        Object suppressAutoHyphens = null;
        Object kinsoku = null;
        Object wordWrap = null;
        Object overflowPunct = null;
        Object topLinePunct = null;
        Object autoSpaceDE = null;
        Object autoSpaceDN = null;
        Object bidi = null;
        Object adjustRightInd = null;
        Object snapToGrid = null;

        Object spacingBefore = null;
        Object spacingBeforeLines = null;
        Object spacingBeforeAutospacing = null;
        Object spacingAfter = null;
        Object spacingAfterLines = null;
        Object spacingAfterAutospacing = null;
        Object spacingLine = null;
        String spacingLineRule = null;

        Object mirrorIndents = null;
        Object indLeft = null;
        Object indRight = null;
        Object indHanging = null;
        Object indFirstLine = null;

        Object indLeftChars = null;
        Object indRightChars = null;
        Object indHangingChars = null;
        Object indFirstLineChars = null;

        Object contextualSpacing = null;
        Object suppressOverlap = null;
        String jc = null;
        String textDirection = null;
        String textAlignment = null;
        Object outlineLvl = null;

        Object divId = null;
        Object tightWrap = null;
        Object collapsed = null;

        ParagraphNumberRevision numberRevision = null;

        // This is the main loop to collect the properties.
        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);
            writableAttributesCounter++;

            switch (key)
            {
                case ParaAttr.ISTD:
                {
                    int istd = (/*int*/Integer)value;
                    //pStyle
                    if ((istd != 0) && WordUtil.isValidIstd(istd))
                    {
                        styleId = writer.getStyleId(istd);
                    }
                    else
                    {
                        // We need to set it explicitly here to combine paraPr revisions correctly.
                        styleId = null;
                        writableAttributesCounter--;
                    }

                    if (styleId != null)
                    {
                        Style style = writer.getDocument().getStyles().getByIstd(istd, false);

                        // List style references are not written.
                        // Also style references are not written inside numbering and style definitions.
                        if ((style.getType() == StyleType.LIST) || builder.isStylesBuilder())
                        {
                            styleId = null;
                            writableAttributesCounter--;
                        }
                    }
                    break;
                }
                case ParaAttr.TEXTBOX_TIGHT_WRAP:
                    tightWrap = NrxParaEnum.textboxTightWrapToXml((/*TextboxTightWrap*/Integer)value);
                    break;
                case ParaAttr.HTML_BLOCK_ID:
                    divId = value;
                    break;
                case ParaAttr.KEEP_WITH_NEXT:
                    keepNext = value;
                    break;
                case ParaAttr.KEEP_TOGETHER:
                    keepLines = value;
                    break;
                case ParaAttr.PAGE_BREAK_BEFORE:
                    pageBreakBefore = value;
                    break;
                case ParaAttr.DROP_CAP_POSITION:
                    frameDropCap = NrxParaEnum.dropCapPositionToXml((/*DropCapPosition*/Integer)value);
                    break;
                case ParaAttr.DROP_CAP_LINES_TO_DROP:
                    frameLines = value;
                    break;
                case ParaAttr.FRAME_WIDTH:
                    frameW = value;
                    break;
                case ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT:
                    frameHSpace = value;
                    break;
                case ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT:
                    frameVSpace = value;
                    break;
                case ParaAttr.FRAME_WRAP_TYPE:
                    frameWrap = NrxParaEnum.textFrameWrapTypeToXml((/*WrapType*/Integer)value, isDocx);
                    break;
                case ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION:
                    frameHAnchor = NrxParaEnum.relativeHorizontalPositionToXml((/*RelativeHorizontalPosition*/Integer)value);
                    break;
                case ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION:
                    frameVAnchor = NrxParaEnum.relativeVerticalPositionToXml((/*RelativeVerticalPosition*/Integer)value);
                    break;
                case ParaAttr.FRAME_LEFT:
                    frameX = ((value != null) && ((/*int*/Integer)value == 0)) ? null : value;
                    break;
                case ParaAttr.FRAME_HORIZONTAL_ALIGNMENT:
                    frameXAlign = NrxParaEnum.horizontalAlignmentToXml((/*HorizontalAlignment*/Integer)value);
                    break;
                case ParaAttr.FRAME_TOP:
                    frameY = ((value != null) && ((/*int*/Integer)value == 0)) ? null : value;
                    break;
                case ParaAttr.FRAME_VERTICAL_ALIGNMENT:
                    frameYAlign = NrxParaEnum.verticalAlignmentToXml((/*VerticalAlignment*/Integer)value);
                    break;
                case ParaAttr.FRAME_HEIGHT:
                    Height height = (Height)value;
                    frameHRule = NrxParaEnum.heightRuleToXml(height.getRule(), isDocx);
                    frameH = height.getValue();
                    break;
                case ParaAttr.FRAME_LOCK_ANCHOR:
                    frameAnchorLock = value;
                    break;
                case ParaAttr.WIDOW_CONTROL:
                    widowControl = value;
                    break;
                case ParaAttr.LIST_ID:
                    listId = value;
                    break;
                case ParaAttr.LIST_LEVEL:
                    listLevel = value;
                    break;
                case ParaAttr.SUPPRESS_LINE_NUMBERS:
                    supressLineNumbers = value;
                    break;
                case ParaAttr.BORDER_BAR:
                    bdrBar = (Border)value;
                    if (bdrBar.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.BORDER_BETWEEN:
                    bdrBetween = (Border)value;
                    if (bdrBetween.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.BORDER_BOTTOM:
                    bdrBottom = (Border)value;
                    if (bdrBottom.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.BORDER_LEFT:
                    bdrLeft = (Border)value;
                    if (bdrLeft.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.BORDER_RIGHT:
                    bdrRight = (Border)value;
                    if (bdrRight.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.BORDER_TOP:
                    bdrTop = (Border)value;
                    if (bdrTop.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.SHADING:
                    shading = (Shading)value;
                    if (shading.isInherited())
                        writableAttributesCounter--;
                    break;
                case ParaAttr.TAB_STOPS:
                    tabStops = (TabStopCollection)value;
                    break;
                case ParaAttr.SUPPRESS_AUTO_HYPHENS:
                    suppressAutoHyphens = value;
                    break;
                case ParaAttr.FAR_EAST_LINE_BREAK_CONTROL:
                    kinsoku = value;
                    break;
                case ParaAttr.WORD_WRAP:
                    wordWrap = value;
                    break;
                case ParaAttr.HANGING_PUNCTUATION:
                    overflowPunct = value;
                    break;
                case ParaAttr.TOP_LINE_PUNCTUATION:
                    topLinePunct = value;
                    break;
                case ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA:
                    autoSpaceDE = value;
                    break;
                case ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT:
                    autoSpaceDN = value;
                    break;
                case ParaAttr.BIDI:
                    bidi = value;
                    break;
                case ParaAttr.AUTO_ADJUST_RIGHT_INDENT:
                    adjustRightInd = value;
                    break;
                case ParaAttr.SNAP_TO_GRID:
                    snapToGrid = value;
                    break;
                case ParaAttr.SPACE_AFTER:
                    spacingAfter = value;
                    break;
                case ParaAttr.SPACE_AFTER_UNITS:
                    spacingAfterLines = value;
                    break;
                case ParaAttr.SPACE_AFTER_AUTO:
                    spacingAfterAutospacing = value;
                    break;
                case ParaAttr.SPACE_BEFORE:
                    spacingBefore = value;
                    break;
                case ParaAttr.SPACE_BEFORE_UNITS:
                    spacingBeforeLines = value;
                    break;
                case ParaAttr.SPACE_BEFORE_AUTO:
                    spacingBeforeAutospacing = value;
                    break;
                case ParaAttr.LINE_SPACING:
                {
                    LineSpacing lineSpacing = (LineSpacing)value;

                    spacingLine = lineSpacing.getValue();
                    spacingLineRule = NrxParaEnum.lineSpacingRuleToXml(lineSpacing.getRule(), isDocx);
                    break;
                }
                case ParaAttr.MIRROR_INDENTS:
                    mirrorIndents = value;
                    break;
                case ParaAttr.FIRST_LINE_INDENT:
                    // if this attribute is negative then it shall be written as "w:hanging",
                    // otherwise as "w:first-line"
                    if ((/*int*/Integer)value < 0)
                        indHanging = -(/*int*/Integer)value;
                    else
                        indFirstLine = value;
                    break;
                case ParaAttr.LEFT_INDENT:
                    indLeft = value;
                    break;
                case ParaAttr.RIGHT_INDENT:
                    indRight = value;
                    break;
                case ParaAttr.FIRST_LINE_INDENT_UNITS:
                    // if this attribute is negative then it shall be written as "w:hanging",
                    // otherwise as "w:first-line"
                    if ((/*int*/Integer)value < 0)
                        indHangingChars = -(/*int*/Integer)value;
                    else
                        indFirstLineChars = value;
                    break;
                case ParaAttr.LEFT_INDENT_UNITS:
                    indLeftChars = value;
                    break;
                case ParaAttr.RIGHT_INDENT_UNITS:
                    indRightChars = value;
                    break;
                case ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE:
                    contextualSpacing = value;
                    break;
                case ParaAttr.FRAME_SUPPRESS_OVERLAP:
                    suppressOverlap = value;
                    break;
                case ParaAttr.ALIGNMENT:
                    jc = NrxParaEnum.paragraphAlignmentToXml((/*ParagraphAlignment*/Integer)value, isDocx, writer.getCompliance());
                    break;
                case ParaAttr.FRAME_TEXT_ORIENTATION:
                    textDirection = StyleConvertUtil.textOrientationToXml((/*TextOrientation*/Integer)value, isDocx,
                        writer.getCompliance());
                    break;
                case ParaAttr.BASELINE_ALIGNMENT:
                    textAlignment = NrxParaEnum.baselineAlignmentToXml((/*BaselineAlignment*/Integer)value);
                    break;
                case ParaAttr.OUTLINE_LEVEL:
                    outlineLvl = (/*int*/Integer)value;
                    break;
                case ParaAttr.COLLAPSED:
                    collapsed = (/*boolean*/Boolean)value;
                    break;
                case RevisionAttr.NUMBER_REVISION:
                {
                    // Write number revision only if we are not writing a formatting revision right now.
                    if (!isFormattingRevision)
                    {
                        numberRevision = (ParagraphNumberRevision)value;
                        if (!numberRevision.isActive())
                        {
                            numberRevision = null;
                            writableAttributesCounter--;
                        }
                    }
                    break;
                }
                case RevisionAttr.FORMAT_REVISION:
                    // This is written elsewhere.
                    writableAttributesCounter--;
                    break;
                default:
                    writableAttributesCounter--;
                    break;
            }
        }

        if (writableAttributesCounter == 0)
            return false;

        if (doWriteParaPrStart)
            builder.startElement("w:pPr");

        builder.writeVal("w:pStyle", styleId);
        builder.writeVal("w:textboxTightWrap", tightWrap);
        builder.writeVal("w:divId", divId);
        builder.writeVal("w:keepNext", keepNext);
        builder.writeVal("w:keepLines", keepLines);
        builder.writeVal("w:pageBreakBefore", pageBreakBefore);

        builder.writeElementWithAttributes(
            "w:framePr",
            isDocx ? "w:dropCap" : "w:drop-cap", frameDropCap,
            "w:lines", frameLines,
            "w:w", frameW,
            "w:h", frameH,
            isDocx ? "w:hRule" : "w:h-rule", frameHRule,
            isDocx ? "w:hSpace" : "w:hspace", frameHSpace,
            isDocx ? "w:vSpace" : "w:vspace", frameVSpace,
            "w:wrap", frameWrap,
            isDocx ? "w:vAnchor" : "w:vanchor", frameVAnchor,
            isDocx ? "w:hAnchor" : "w:hanchor", frameHAnchor,
            "w:x", frameX,
            isDocx ? "w:xAlign" : "w:x-align", frameXAlign,
            "w:y", frameY,
            isDocx ? "w:yAlign" : "w:y-align", frameYAlign,
            isDocx ? "w:anchorLock" : "w:anchor-lock", frameAnchorLock);

        builder.writeVal("w:widowControl", widowControl);

        boolean hasListLevel = (listLevel != null);
        boolean hasListId = (listId != null);
        // AS ListLabelString and ListLabelFontName are not used for DOCX as list properties.
        String listLabelString = isDocx ? null : writer.getCurrentParagraphListLabelString();
        String listLabelFontName = isDocx ? null : writer.getCurrentParagraphListLabelFontName();
        boolean hasNumberRevision = (numberRevision != null) && (numberRevision.isActive());

        // We write w:listPr element if we are going to write at least one of these.
        // AS The case is possible when (hasListId == false) but (listLabelString != null) or (listLabelFontName != null)
        // if the paragraph inherits list item property.
        if (hasListLevel || hasListId || hasNumberRevision || (listLabelString != null) || (listLabelFontName != null))
        {
            builder.startElement(isDocx ? "w:numPr" : "w:listPr");

            if (hasListLevel)
                builder.writeVal("w:ilvl", listLevel);

            if (hasListId)
                builder.writeVal(isDocx ? "w:numId" : "w:ilfo", listId);

            if (listLabelString != null)
            {
                builder.writeVal("wx:t", listLabelString);
            }

            if (listLabelFontName != null)
            {
                builder.writeVal("wx:font", listLabelFontName);
            }

            if (hasNumberRevision)
                builder.writeRevision(numberRevision, writer.getNextAnnotationId());

            builder.endElement(); //w:listPr
        }

        // Funny that in WordML it was called "supressLineNumbers" (with one p).
        // "Suppress" is actually the right spelling for this word.
        builder.writeVal(isDocx ? "w:suppressLineNumbers" : "w:supressLineNumbers", supressLineNumbers);

        builder.writeBorders(
            "w:pBdr",
            "w:top", bdrTop,
            "w:left", bdrLeft,
            "w:bottom", bdrBottom,
            "w:right", bdrRight,
            "w:between", bdrBetween,
            "w:bar", bdrBar);

        builder.writeShd(shading);

        writeTabs(tabStops, writer);

        builder.writeVal("w:suppressAutoHyphens", suppressAutoHyphens);
        builder.writeVal("w:kinsoku", kinsoku);
        builder.writeVal("w:wordWrap", wordWrap);
        builder.writeVal("w:overflowPunct", overflowPunct);
        builder.writeVal("w:topLinePunct", topLinePunct);
        builder.writeVal("w:autoSpaceDE", autoSpaceDE);
        builder.writeVal("w:autoSpaceDN", autoSpaceDN);
        builder.writeVal("w:bidi", bidi);
        builder.writeVal("w:adjustRightInd", adjustRightInd);
        builder.writeVal("w:snapToGrid", snapToGrid);

        // Word writes either "w:before", "w:after" or "w:before-lines", "w:after-lines" attributes.
        // I don't know how to decide which one should be written, so I will always write "w:before", "w:after".
        builder.writeElementWithAttributes(
            "w:spacing",
            "w:before", spacingBefore,
            isDocx ? "w:beforeLines" : "w:before-lines", spacingBeforeLines,
            isDocx ? "w:beforeAutospacing" : "w:before-autospacing", spacingBeforeAutospacing,
            "w:after", spacingAfter,
            isDocx ? "w:afterLines" : "w:after-lines", spacingAfterLines,
            isDocx ? "w:afterAutospacing" : "w:after-autospacing", spacingAfterAutospacing,
            "w:line", spacingLine,
            isDocx ? "w:lineRule" : "w:line-rule", spacingLineRule);

        //Word writes either "w:left", "w:right", "w:hanging", "w:first-line" or "w:left-chars", "w:right-chars", "w:hanging-chars", "w:first-line-chars" attributes.
        //I don't know how to decide which one should be written, so I will always write "w:left", "w:right", "w:hanging", "w:first-line".
        builder.writeElementWithAttributes(
            "w:ind",
            isIsoStrict ? "w:start" : "w:left", indLeft,
            isIsoStrict ? "w:end" : "w:right", indRight,
            "w:hanging", indHanging,
            isDocx ? "w:firstLine" : "w:first-line", indFirstLine,
            // line/char
            isDocx ? "w:leftChars" : "w:left-chars", indLeftChars,
            isDocx ? "w:rightChars" : "w:right-chars", indRightChars,
            isDocx ? "w:hangingChars" : "w:hanging-chars", indHangingChars,
            isDocx ? "w:firstLineChars" : "w:first-line-chars", indFirstLineChars);

        builder.writeVal("w:contextualSpacing", contextualSpacing);
        builder.writeVal("w:suppressOverlap", suppressOverlap);
        builder.writeVal("w:jc", jc);
        builder.writeVal("w:textDirection", textDirection);
        builder.writeVal("w:textAlignment", textAlignment);
        builder.writeVal("w:outlineLvl", outlineLvl);

        // andrnosk: MirrorIndents is not supported in WordML. 
        if (isDocx)
            builder.writeVal("w:mirrorIndents", mirrorIndents);
        else if (mirrorIndents != null)
            writer.warn(WarningType.MINOR_FORMATTING_LOSS, "MirrorIndents is not supported in WordML.");

        // Collapsed is not supported in WordML. 
        if ((collapsed != null) && (/*boolean*/Boolean)collapsed &&
            isDocx && (writer.getCompliance() != OoxmlComplianceCore.ECMA_376))
            builder.writeEmptyElement("w15:collapsed");

        return true;
    }

    /// <summary>
    /// Clears the frame attributes, if a frame is not applied, to avoid writing a meaningless 'framePr' element.
    /// </summary>
    private static void clearFramePropertiesIfNotFramed(WordAttrCollection attrs)
    {
        // If the ParaAttr.FrameWrapType attribute has the 'auto' value and the other frame attributes are null,
        // the frame is not applied and the ParaAttr.FrameWrapType attribute can be cleared.

        if ((attrs.get(ParaAttr.FRAME_WRAP_TYPE) == null) || ((/*WrapType*/Integer)attrs.get(ParaAttr.FRAME_WRAP_TYPE) != WrapType.INLINE))
            return;

        for (int key : gFramePrAttributesExceptWrap)
        {
            if (attrs.get(key) != null)
                return;
        }

        attrs.remove(ParaAttr.FRAME_WRAP_TYPE);
    }

    /// <summary>
    /// Writes w:tabs element using specified TabStops.
    /// </summary>
    private static void writeTabs(TabStopCollection tabs, INrxWriterContext writer)
    {
        if ((tabs == null) || (tabs.getCount() == 0))
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("w:tabs");

        for (int i = 0; i < tabs.getCount(); i++)
        {
            TabStop tabStop = tabs.get(i);
            String leader = NrxParaEnum.tabLeaderToXml(tabStop.getLeader(), writer.isDocx());
            int pos = Math.max(tabStop.getPositionTwips(), MIN_TAB);

            builder.writeElementWithAttributes(
                "w:tab",
                "w:val", NrxParaEnum.tabAlignmentToXml(tabStop.getAlignment(), writer.isDocx(),
                    writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT),
                // do not write w:leader if its value is 'none'
                "w:leader", (!"none".equals(leader)) ? leader : null,
                "w:pos", pos);
        }

        builder.endElement(); //w:tabs
    }

    private static int[] gFramePrAttributesExceptWrap = new int[]
    {
        ParaAttr.DROP_CAP_POSITION, ParaAttr.DROP_CAP_LINES_TO_DROP, ParaAttr.FRAME_WIDTH, ParaAttr.FRAME_HEIGHT,
        ParaAttr.FRAME_HEIGHT, ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT,
        ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, ParaAttr.FRAME_LEFT,
        ParaAttr.FRAME_HORIZONTAL_ALIGNMENT, ParaAttr.FRAME_TOP, ParaAttr.FRAME_VERTICAL_ALIGNMENT, ParaAttr.FRAME_LOCK_ANCHOR
    };

    private static final int MIN_TAB = -31680;
}

