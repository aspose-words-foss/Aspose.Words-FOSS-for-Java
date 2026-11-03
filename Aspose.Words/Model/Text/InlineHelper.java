// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DrColor;
import com.aspose.Debug;


/// <summary>
/// This class provides implementation for inline-content specific functions
/// that must be available in the classes that are base classes for inline nodes:
/// <see cref="Inline"/>, <see cref="InlineStory"/> and <see cref="Aspose.Words.Drawing.ShapeBase"/>.
/// These functions are needed in all of the above classes, yet these classes have no suitable
/// base class where I can put these functions. Therefore the common functions are here.
/// </summary>
/*static*/ class InlineHelper
{
	/* Simulation of static class by using private constructor */
	private InlineHelper()
	{}

    /// <summary>
    /// Gets character style for inline.
    /// </summary>
    /// <returns> Returns DefaultParagraphFont style if inline doesn't have character style assigned. </returns>
    static Style getCharacterStyle(IInline inlineNode)
    {
        return getCharacterStyle(inlineNode, RunPrExpandFlags.NORMAL, true);
    }

    /// <summary>
    /// Creates a fully expanded version of the RunPr object.
    /// </summary>
    static RunPr getExpandedRunPr(IInline node, /*RunPrExpandFlags*/int flags)
    {
        RunPr dstRunPr = new RunPr();
        expandRunPr(node, dstRunPr, flags);
        return dstRunPr;
    }

    /// <summary>
    /// Fully expands font properties of inline into direct formatting.
    /// </summary>
    static void expandRunPr(IInline inline, RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        expandRunPr(inline.getDocument_IInline(), inline.getParentParagraph_IInline(), inline.getRunPr_IInline(), dstRunPr, flags);
    }

    /// <summary>
    /// Fully expands font properties of paragraph break into direct formatting.
    /// </summary>
    static void expandRunPr(Paragraph para, RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        expandRunPr(para.getDocument(), para, para.getParagraphBreakRunPr(), dstRunPr, flags);
    }

    /// <summary>
    /// Fully expands font properties of this object into direct formatting.
    /// </summary>
    static void expandRunPr(DocumentBase doc, Paragraph parentPara, RunPr runPr, RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        StyleCollection styles = doc.getStyles();

        Style charStyle = getCharacterStyle(doc, runPr, flags, false);

        boolean isExpandParaStyle = (flags & RunPrExpandFlags.NO_PARA_STYLE) == 0;
        Style paraStyle = isExpandParaStyle ? getParaStyle(parentPara, flags) : styles.getByIstd(StyleIndex.NORMAL, false);

        Style tableStyle = getTableStyle(parentPara);

        RunPr directRunPr = runPr.getSourceRunPr(flags);

        // when layout renders revisions it needs to know if run has them
        // however the GetSourceRunPr will accept revision and remove format revision from directRunPr
        // we need to keep this attribute in the result collection so that layout knows this run has one
        if ((flags & RunPrExpandFlags.REVISED) != 0 && runPr.hasFormatRevision())
            dstRunPr.setFormatRevision((FormatRevision)((IComplexAttr)runPr.getFormatRevision()).deepCloneComplexAttr());

        boolean containsFontSizeNotInGlobalDefaults = dstRunPr.containsAnyKey(FontAttr.SIZE, FontAttr.SIZE_BI);
        boolean directFormatting = ((flags & RunPrExpandFlags.NO_DIRECT_FORMATTING) == 0);

        // Reset flag immediately as we need to ignore direct formatting only for this entity.
        flags &= ~RunPrExpandFlags.NO_DIRECT_FORMATTING;

        boolean isExpandDocumentDefaults = ((flags & RunPrExpandFlags.DOCUMENT_DEFAULTS) != 0);
        RunPr defaults = isExpandDocumentDefaults ? styles.getDefaultRunPr() : new RunPr();

        containsFontSizeNotInGlobalDefaults |= defaults.containsAnyKey(FontAttr.SIZE, FontAttr.SIZE_BI);

        boolean isExpandGlobalDefaults = ((flags & RunPrExpandFlags.GLOBAL_DEFAULTS) != 0);
        defaults.expandTo(dstRunPr, isExpandGlobalDefaults);

        // Expand font formatting from the table style if needed.
        if ((flags & RunPrExpandFlags.NO_TABLE_STYLE) == 0)
        {
            if (parentPara != null)
            {
                Cell cell = parentPara.getParentCell();
                if (cell != null)
                {
                    Table table = cell.getParentTable();
                    if (table != null)
                    {
                        tableStyle = ms.as(tableStyle, TableStyle.class);
                        if (tableStyle != null)
                        {
                            ((TableStyle)tableStyle).expandRunPr(cell, dstRunPr);
                            containsFontSizeNotInGlobalDefaults |= tableStyle.getRunPr().containsAnyKey(FontAttr.SIZE, FontAttr.SIZE_BI);
                        }
                    }
                }
            }
        }

        if (paraStyle != null)
        {
            // WORDSNET-5894 Use Normal style in case Character style is applied to paragraph.
            if (paraStyle.getType() == StyleType.CHARACTER)
                paraStyle = styles.getBySti(StyleIdentifier.NORMAL, false);

            if (containsFontSizeNotInGlobalDefaults && isIgnoreNormalFontSize(doc, ms.as(tableStyle, TableStyle.class)))
                flags |= RunPrExpandFlags.IGNORE_NORMAL_FONT_SIZE;

            // IN. The defaults have been expanded already and it might been overridden with table style above.
            // For sure, we do not want to override it again with the defaults during paragraph style expanding.
            // So, clear corresponding flags.
            /*RunPrExpandFlags*/int paraFlags = flags & (~RunPrExpandFlags.DOCUMENT_DEFAULTS & ~RunPrExpandFlags.GLOBAL_DEFAULTS);
            paraStyle.expandRunPr(dstRunPr, paraFlags);
        }

        // WORDSNET-6033 Paragraph style (part of linked style) assigned to run. Ignore such style.
        if ((charStyle != null) && (charStyle.getType() != StyleType.PARAGRAPH))
        {
            boolean isIgnoreHyperlinkCharStyle = ((flags & RunPrExpandFlags.IGNORE_HYPERLINK_CHAR_STYLE) != 0);
            boolean isIgnoreCharStyle = (isIgnoreHyperlinkCharStyle && (charStyle.getStyleIdentifier() == StyleIdentifier.HYPERLINK));
            if (!isIgnoreCharStyle)
            {
                // WORDSNET-25424 Expand style revised properties if requested.
                boolean isRevised = (flags & RunPrExpandFlags.REVISED) != 0;
                charStyle.expandRunPr(dstRunPr, isRevised ? RunPrExpandFlags.REVISED : RunPrExpandFlags.NORMAL);
            }
            else
            {
                // WORDSNET-14332 Most likely not all hyperlink char style attributes have to be ignored.
                // Mimic MSW and set destination AllCaps to true if AllCaps or SmallCaps of hyperlink style are equals true.
                if (!dstRunPr.contains(FontAttr.ALL_CAPS) && charStyle.getFont().getAllCaps() ||
                    (!dstRunPr.contains(FontAttr.SMALL_CAPS) && charStyle.getFont().getSmallCaps()))
                    dstRunPr.setAttr(FontAttr.ALL_CAPS, AttrBoolEx.TRUE);

                // WORDSNET-24506 Font family of the Hyperlink style shouldn't be ignored
                for (int attribute : RunPr.FONT_NAME_ATTRIBUTES)
                {
                    Object value = charStyle.getFontAttr(attribute, false);
                    if (value != null)
                        dstRunPr.setAttr(attribute, value);
                }
            }
        }

        // Most likely format revision may reference different style, etc. and thus we must do more magic in the lines above.
        // This code works only for paragraph nodes when we need to know formatting of the paragraph break.
        // For inline nodes caller has already removed format revision (but we already recorded it in dstRunPr)
        //
        // IN. If NoDirectFormatting flag is set, then do not expand direct attributes.
        if (directFormatting)
        {
            if (directRunPr.hasFormatRevision() && (flags & RunPrExpandFlags.REVISED) != 0)
            {
                RunPr src = AttrCollectionExtension.deepClone(directRunPr);
                src.acceptFormatRevision();
                src.expandTo(dstRunPr);

                // We must copy insert/delete/format revision attributes to the destination
                // because code needs to know if run must be rendered accordingly.
                if (directRunPr.hasDeleteRevision())
                    dstRunPr.setAttr(RevisionAttr.DELETE_REVISION, ((IComplexAttr)directRunPr.getDeleteRevision()).deepCloneComplexAttr());
                if (directRunPr.hasInsertRevision())
                    dstRunPr.setAttr(RevisionAttr.INSERT_REVISION, ((IComplexAttr)directRunPr.getInsertRevision()).deepCloneComplexAttr());
                if (directRunPr.hasFormatRevision())
                    dstRunPr.setAttr(RevisionAttr.FORMAT_REVISION, ((IComplexAttr)directRunPr.getFormatRevision()).deepCloneComplexAttr());
                if (directRunPr.hasMoveFromRevision())
                    dstRunPr.setAttr(RevisionAttr.MOVE_FROM_REVISION, ((IComplexAttr)directRunPr.getMoveFromRevision()).deepCloneComplexAttr());
                if (directRunPr.hasMoveToRevision())
                    dstRunPr.setAttr(RevisionAttr.MOVE_TO_REVISION, ((IComplexAttr)directRunPr.getMoveToRevision()).deepCloneComplexAttr());
            }
            else
            {
                directRunPr.expandTo(dstRunPr);

                directRunPr.themeColorInheritanceHack(dstRunPr);
            }
        }

        excludeSolidTextFillInheritance(dstRunPr, directRunPr);

        // Special handling for run in comments.
        boolean isChangeCommentsSize = (flags & RunPrExpandFlags.NO_CHANGE_COMMENT_SIZE) == 0;
        if (isChangeCommentsSize && ((parentPara != null) && (parentPara.getAncestor(NodeType.COMMENT) != null)))
        {
            Style commentStyle = styles.getBySti(StyleIdentifier.BALLOON_TEXT, false);
            if (commentStyle != null)
            {
                commentStyle.getRunPr().mirrorTo(dstRunPr, FontAttr.SIZE);
                commentStyle.getRunPr().mirrorTo(dstRunPr, FontAttr.SIZE_BI);
            }
        }
    }

    // WORDSNET-23840
    // It appears that MS Word has some illogical exception regarding attribute inheritance:
    // TextFill effect with solid color should not be inherited if the local color is specified (including auto value).
    // Local TextFill effect still overrides a local color.
    private static void excludeSolidTextFillInheritance(RunPr dstRunPr, RunPr directRunPr)
    {
        if (dstRunPr.containsKey(FontAttr.EFFECT_FILL) &&
            directRunPr.containsKey(FontAttr.COLOR) &&
            !directRunPr.containsKey(FontAttr.EFFECT_FILL))
        {
            DmlSolidFill solidTextFill = ms.as(dstRunPr.get(FontAttr.EFFECT_FILL), DmlSolidFill.class);
            if (solidTextFill != null)
                dstRunPr.remove(FontAttr.EFFECT_FILL);
        }
    }

    /// <summary>
    /// Returns either paragraph style or revised paragraph style.
    /// </summary>
    static Style getParaStyle(Paragraph para, /*RunPrExpandFlags*/int flags)
    {
        if (para == null)
            return null;

        // In order to get revised run properties we must use revised paragraph style for it.
        // WORDSNET-24396 Handle also AfterChanges flag to get proper style while calculating positive difference.
        /*RevisionsView*/int revisionsView = ((flags & RunPrExpandFlags.REVISED) != 0) ||
                                      ((flags & RunPrExpandFlags.AFTER_CHANGES) != 0)
            ? RevisionsView.FINAL
            : RevisionsView.ORIGINAL;

        return para.getParagraphStyle(revisionsView);
    }

    /// <summary>
    /// Returns table style.
    /// </summary>
    static Style getTableStyle(Paragraph para)
    {
        if (para == null)
            return null;

        return (para.isInCell() &&
               // WORDSNET-11447 IsInCell is true, but ParentTable is null when row or cell is cloned.
               (para.getParentTable() != null))
                ? para.getParentTable().getStyle()
                : null;
    }

    /// <summary>
    /// Helps to calculate the full value of a single character formatting attribute.
    /// </summary>
    static Object fetchInheritedAttr(IInline inline, int fontAttr)
    {
        // This method is called when a direct value for this attribute is not specified.

        // Try to find the value specified in the character style.
        Style charStyle = getCharacterStyle(inline, RunPrExpandFlags.NORMAL, false);

        // WORDSNET-19994 Do not consider a character style whose type isn't equal to 'StyleType.Character'.
        Object value = ((charStyle != null) && (charStyle.getType() == StyleType.CHARACTER))
            ? charStyle.getFontAttr(fontAttr, false)
            : null;

        if (value != null)
        {
            if (value instanceof AttrBoolEx)
            {
                // WORDSNET-27952 Toggle attributes not resolved correctly when defined in Character style and Paragraph style.
                // DM This is a *partial* fix to resolve between toggle attributes in Character style and Paragraph style
                // according to ECMA-376 17.7.3. Table styles are ignored.
                value = resolveToggleAttribute(inline, fontAttr, (AttrBoolEx)value);
            }
        }
        else
        {
            // Next, get the value from the paragraph style.
            value = getAttrFromParagraphStyle(inline, fontAttr);

            // WORDSNET-11068 After all get value from table style.
            if ((value == null) && isInTable(inline))
                value = getAttrFromTableStyle(inline, fontAttr);
        }

        // Get the value from document defaults defined in styles collection.
        if (value == null)
            value = inline.getDocument_IInline().getStyles().getDefaultRunPr().getDirectAttr(fontAttr);

        // WORDSNET-25882 Get the value from shape style.
        if ((value == null) && (fontAttr == FontAttr.COLOR) && isInShape(inline))
            value = getFontColorFromShapeStyle(inline);

        // Return the value taken from global application defaults if it's null.
        return (value != null)
            ? value
            : RunPr.getDefaultAttr(fontAttr);
    }

    /// <summary>
    /// Gets font color defined in shape style.
    /// </summary>
    private static DrColor getFontColorFromShapeStyle(IInline inline)
    {
        Shape shape = (Shape)inline.getParentParagraph_IInline().getFirstNonMarkupParentNode();
        DmlShape dmlShape = ms.as(shape.getDmlNode(), DmlShape.class);
        if ((dmlShape == null) || (dmlShape.getStyle() == null) || dmlShape.getStyle().getFontReference().isEmpty())
            return null;

        DocumentBase doc = inline.getDocument_IInline();
        return dmlShape.getStyle().getFontReference().getColor().createDrColor(doc.getThemeInternal(), null);
    }

    /// <summary>
    /// Gets character style for inline or paragraph break.
    /// </summary>
    /// <param name="inline">Inline</param>
    /// <param name="flags">Should be used to get either revised or not character style.</param>
    /// <param name="fetchDefault">If this is set true method returns DefaultParagraphFont in case inline doesn't have character style assigned.</param>
    /// <returns></returns>
    private static Style getCharacterStyle(IInline inline, /*RunPrExpandFlags*/int flags, boolean fetchDefault)
    {
        Style charStyle = null;
        RunPr runPr = inline.getRunPr_IInline();
        DocumentBase doc = inline.getDocument_IInline();

        if (fetchDefault)
        {
            int istd = (/*int*/Integer)runPr.fetchAttr(FontAttr.ISTD, flags);
            charStyle = doc.getStyles().fetchByIstd(istd, StyleIndex.DEFAULT_PARAGRAPH_FONT);
        }
        else
        {
            Object istdVal = runPr.getDirectAttr(FontAttr.ISTD, flags);
            if (null != istdVal)
                charStyle = doc.getStyles().getByIstd((/*int*/Integer)istdVal, false);
        }

        return charStyle;
    }

    private static Style getCharacterStyle(DocumentBase doc, RunPr runPr, /*RunPrExpandFlags*/int flags, boolean fetchDefault)
    {
        Style charStyle = null;

        if (fetchDefault)
        {
            int istd = (/*int*/Integer)runPr.fetchAttr(FontAttr.ISTD, flags);
            charStyle = doc.getStyles().fetchByIstd(istd, StyleIndex.DEFAULT_PARAGRAPH_FONT);
        }
        else
        {
            Object istdVal = runPr.getDirectAttr(FontAttr.ISTD, flags);
            if (null != istdVal)
                charStyle = doc.getStyles().getByIstd((/*int*/Integer)istdVal, false);
        }

        return charStyle;
    }

    private static boolean isInTable(IInline inline)
    {
        Paragraph parentPara = inline.getParentParagraph_IInline();
        return (parentPara != null) && (parentPara.getParentTable() != null);
    }

    /// <summary>
    /// Returns a value indicating whether the specified inline node is located in a shape.
    /// </summary>
    private static boolean isInShape(IInline inline)
    {
        Paragraph parentPara = inline.getParentParagraph_IInline();
        return (parentPara != null) && parentPara.isInShape();
    }

    /// <summary>
    /// Resolves toggle attributes defined at multiple levels of styles hierarchy.
    /// </summary>
    /// <remarks>Currently, table styles are not supported.</remarks>
    /// <param name="inline"></param>
    /// <param name="fontAttr"></param>
    /// <param name="charStyleAttr"></param>
    /// <returns></returns>
    private static AttrBoolEx resolveToggleAttribute(IInline inline, int fontAttr, AttrBoolEx charStyleAttr)
    {
        Debug.assertTrue(charStyleAttr != null);

        AttrBoolEx resolvedAttr = charStyleAttr;
        AttrBoolEx paraStyleAttr = (AttrBoolEx)getAttrFromParagraphStyle(inline, fontAttr);

        // ECMA-376 17.7.3: If the value of the toggle property appears at multiple levels of the style hierarchy...
        // This means attribute is defined both in char style and in paragraph style.
        // Note: We do not support attributes from table styles.
        if ((paraStyleAttr != null) && (charStyleAttr != null))
        {
            AttrBoolEx defaultAttr = (AttrBoolEx)getDocumentDefaultAttr(inline, fontAttr);
            if (defaultAttr == AttrBoolEx.TRUE)
            {
                // ECMA-376 17.7.3: ...If the value specified by the document defaults is true, the effective value is true...
                resolvedAttr = defaultAttr;
            }
            else
            {
                // ECMA-376 17.7.3: ...Otherwise, the values are combined by a Boolean XOR...
                // Note: We do not support attributes from table styles, so XOR char and para styles only.
                resolvedAttr = (charStyleAttr == paraStyleAttr)
                    ? AttrBoolEx.FALSE
                    : AttrBoolEx.TRUE;
            }
        }

        return resolvedAttr;
    }

    /// <summary>
    /// Gets the attribute from the parent paragraph style inheritance tree.
    /// </summary>
    /// <param name="inline"></param>
    /// <param name="fontAttr"></param>
    /// <returns>Returns null if the attribute is not defined at paragraph style level.</returns>
    private static Object getAttrFromParagraphStyle(IInline inline, int fontAttr)
    {
        Paragraph parentPara = inline.getParentParagraph_IInline();

        if (parentPara == null)
            return null;

        Style style = parentPara.getParagraphStyle();
        if ((style.getStyleIdentifier() == StyleIdentifier.NORMAL) && isSizeKey(fontAttr))
        {
            Object value = getTableOrDefaultFontSize(parentPara, fontAttr);
            if (value != null)
                return value;
        }

        return style.getFontAttr(fontAttr, false);
    }

    private static boolean isSizeKey(int key)
    {
        return (key == FontAttr.SIZE) || (key == FontAttr.SIZE_BI);
    }

    /// <summary>
    /// Returns font size considering 'ignore default font size' feature or null if option should not be applied.
    /// </summary>
    // See https://msdn.microsoft.com/en-us/library/dd944611%28v=office.12%29.aspx?f=255&MSPPError=-2147217396
    private static Object getTableOrDefaultFontSize(Paragraph para, int key)
    {
        DocumentBase doc = para.getDocument();

        Table table = para.getParentTable();
        if (table == null)
            return null;

        Style paraStyle = para.getParagraphStyle();

        Object value = null;
        TableStyle tableStyle = ms.as(doc.getStyles().getByIstd(table.getIstd(), false), TableStyle.class);
        if (tableStyle == null)
            return null;

        // if Normal font size should be ignored,
        // try to get it from table style then from document defaults and finally from paragraph style.
        if (isIgnoreNormalFontSize(doc, tableStyle) && (paraStyle.getRunPr().contains(key) && ((/*int*/Integer)paraStyle.getRunPr().get(key) == 24)))
        {
            value = ((IRunAttrSource)tableStyle).getDirectRunAttr(key);

            if (value == null)
                value = tableStyle.getInheritedFontAttr(key, false);

            if (value == null)
                value = doc.getStyles().getDefaultRunPr().get(key);

            if (isSizeKey(key) && (value != null) && ((/*int*/Integer)value == 20))
                value = null;
        }

        return value;
    }

    /// <summary>
    /// Gets attribute value from table style.
    /// </summary>
    private static Object getAttrFromTableStyle(IInline inline, int key)
    {
        Table parentTable = inline.getParentParagraph_IInline().getParentTable();

        Object value = null;
        if (inline.getDocument_IInline().getStyles().getByIstd(parentTable.getIstd(), false) != null)
        {
            TableStyle tableStyle = ms.as(parentTable.getStyle(), TableStyle.class);
            Cell cell = inline.getParentParagraph_IInline().getParentCell();

            if ((tableStyle != null) && (cell != null))
                value = tableStyle.getRunAttr(key, cell);
        }

        return value;
    }

    /// <summary>
    /// Gets the default attribute value.
    /// The value is taken from the document defaults defined in the styles collection, or from global defaults.
    /// </summary>
    private static Object getDocumentDefaultAttr(IInline inline, int fontAttr)
    {
        // First, get from document defaults defined in styles collection.
        Object attrValue = inline.getDocument_IInline().getStyles().getDefaultRunPr().getDirectAttr(fontAttr);

        if (attrValue == null)
        {
            // Get from the global application defaults.
            attrValue = RunPr.getDefaultAttr(fontAttr);
        }

        return attrValue;
    }

    /// <summary>
    /// Checks if font size specified on Normal paragraph style should be ignored.
    /// </summary>
    static boolean isIgnoreNormalFontSize(DocumentBase doc, TableStyle tableStyle)
    {
        if (tableStyle == null)
            return false;

        if (tableStyle.getIstd() == StyleIndex.TABLE_NORMAL)
            return false;

        // Accordingly to the spec for 'overrideTableStyleFontSizeAndJustification' option
        // in https://msdn.microsoft.com/en-us/library/dd944611%28v=office.12%29.aspx?f=255&MSPPError=-2147217396
        // if the default paragraph style (as specified in [ISO/IEC29500-1:2012] section 17.7.4.17)
        // specifies a font size of 11pt or 12pt, then that setting will not override
        // the font size specified by the table style for paragraphs in tables.
        // Although spec says about the 11pt font size, it seems that Word applies this rule only for the font size 12pt.
        CustomCompatibilitySetting overrideTableStyle = doc.getDocPr().CompatibilityOptions.getCustomCompatibilitySettings().get("overrideTableStyleFontSizeAndJustification");
        // By default this setting is false.
        return ((overrideTableStyle == null) || ("0".equals(overrideTableStyle.getValue())));
    }

    static boolean isInsertRevision(IInline inline)
    {
        return (inline.getRunPr_IInline().hasInsertRevision());
    }

    static boolean isDeleteRevision(IInline inline)
    {
        return (inline.getRunPr_IInline().hasDeleteRevision());
    }

    static boolean isMoveFromRevision(IInline inline)
    {
        return (inline.getRunPr_IInline().hasMoveFromRevision());
    }

    static boolean isMoveToRevision(IInline inline)
    {
        return (inline.getRunPr_IInline().hasMoveToRevision());
    }

    static boolean isFormatRevision(IInline inline)
    {
        return (inline.getRunPr_IInline().hasFormatRevision());
    }

    /// <summary>
    /// Returns resolved value of the specified attribute. This is used by AE to get Hidden attr value.
    /// </summary>
    static Object fetchAttr(IRunAttrSource src, int key, /*RevisionsView*/int revisionsView)
    {
        // This happens if we try to get value from the node which is not IRunAttrSource.
        if (null == src)
            return null;

        Object value = src.getDirectRunAttr(key, revisionsView);

        // The value is not specified directly, obtain it from the parent.
        if (value == null)
            return src.fetchInheritedRunAttr(key);

        // The value is specified directly, but might need to be resolved using parent values.
        if (value instanceof AttrBoolEx)
            return ((AttrBoolEx)value).resolveFetchInheritedRunAttr(src, key);

        //The value is specified and does not need any special handling.
        return value;
    }

    /// <summary>
    /// Returns resolved value of the specified attribute. This is used by AE to get Hidden attr value.
    /// </summary>
    static Object fetchAttr(IRunAttrSource src, int key)
    {
        return fetchAttr(src, key, RevisionsView.ORIGINAL);
    }

    /// <summary>
    /// Returns a resolved value of the specified AttrBoolEx attribute.
    /// </summary>
    /// <remarks>
    /// It will throw on invalid cast if the attribute value is not AttrBoolEx.
    /// </remarks>
    static boolean getBool(IRunAttrSource src, int key)
    {
        return ((AttrBoolEx)fetchAttr(src, key)).toBool();
    }

    /// <summary>
    /// Indicates that inline has character category hint set.
    /// </summary>
    static boolean hasCharacterCategoryHint(IRunAttrSource src)
    {
        return src.getDirectRunAttr(FontAttr.CHARACTER_CATEGORY_HINT) != null;
    }

    /// <summary>
    /// Returns top visible shading in hierarchy of properties.
    /// </summary>
    static Shading topVisibleShading(IRunAttrSource src)
    {
        // 1. Check shading for the run text.
        Shading shading = (Shading)fetchAttr(src, FontAttr.SHADING);
        if (shading.isVisible())
            return shading;

        // This can be either inline or style.
        if (src instanceof IParaAttrSource)
        {
            // For style we should process it's IParaAttrSource.
            IParaAttrSource paraAttrs = (IParaAttrSource)src;

            shading = (Shading)paraAttrs.fetchParaAttr(ParaAttr.SHADING);
            if (shading.isVisible())
                return shading;
        }
        else if (src instanceof IInline)
        {
            // For inline continue with parent paragraph.
            Paragraph parentPara = ((IInline)src).getParentParagraph_IInline();

            // 2. If text has no shading, then try to get it from the parent paragraph.
            if (parentPara != null)
            {
                shading = (Shading)((IParaAttrSource)parentPara).fetchParaAttr(ParaAttr.SHADING);
                if (shading.isVisible())
                    return shading;

                // WORDSNET-27432 Try to get shading from the parent Shape, if any.
                Shape parentShape = ms.as(parentPara.getFirstNonMarkupParentNode(), Shape.class);
                if ((parentShape != null) && (parentShape.getMarkupLanguage() == ShapeMarkupLanguage.DML))
                {
                    throw new UnsupportedOperationException("FOSS");
                }

                // 3. If parent paragraph has no shading, then try to get it from the parent cell, if any.
                if (parentPara.isInCell())
                {
                    shading = (Shading)((ICellAttrSource)parentPara.getParentCell()).fetchCellAttr(CellAttr.SHADING);
                    if (shading.isVisible())
                        return shading;
                }

                // 4. At last if document has page background then try to get shading from it.
                if (parentPara.getDocument().getBackgroundShape() != null)
                {
                    throw new UnsupportedOperationException("FOSS");
                }
            }
        }

        return null;
    }
}

