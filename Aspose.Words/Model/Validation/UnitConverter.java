// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msMath;


/// <summary>
/// Updates <see cref="ParaPr.SpaceBefore" />, <see cref="ParaPr.SpaceAfter" /> and
/// <see cref="ParaPr.LeftIndent" />, <see cref="ParaPr.RightIndent" />, <see cref="ParaPr.FirstLineIndent" />
/// from corresponding unit values if specified.
/// </summary>
/// <remarks>
/// There are several reasons for this code:
///  - line/character units are not supported by layout.
///  - users have no public API support for line/character units.
///  - there are few files in TestData (TestJira4880 for example)
///    which have invalid "normal" indents but valid character unit indents.
///
/// So in summary the following behavior is implemented:
///   We read/write line/characters units during roundtrip. This allows Word to display indent/spacing as in original file.
///   We update "normal" spacing/indents upon load and before save (Normal style can be changed).
///   We remove line/character units once user sets "normal" spacing/indents.
/// </remarks>
class UnitConverter extends DocumentVisitor
{
    /// <summary>
    /// Called when enumeration of the document has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitDocumentStart(Document doc)
    {
        // WORDSNET-28431 Update indents from characters units in styles.
        for (Style style : doc.getStyles())
            updateTwipsFromUnits(style);

        mTwipsPerCharacterUnit = getTwipsPerCharacterUnit(doc);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Returns twips per character unit of the document.
    /// This is used to compute paragraph left/right indents in twips when they are expressed in character units.
    /// The trick is that first line indent cannot be converted using it because it depends on font size of the first run of the paragraph instead.
    /// </summary>
    private static int getTwipsPerCharacterUnit(Document doc)
    {
        // Character unit is Normal style font size.
        // Do not modify style collection to avoid side effects.
        Style styleNormal = doc.getStyles().getBySti(StyleIdentifier.NORMAL, false);

        int fontSize = (styleNormal != null)
            ? (/*int*/Integer)styleNormal.getFontAttr(FontAttr.SIZE, true)
            : (/*int*/Integer)RunPr.fetchDefaultAttr(FontAttr.SIZE);

        // Font size is half points, hence divide by 2.
        return ConvertUtilCore.pointToTwip(fontSize) / 2;
    }

    public /*override*/ /*VisitorAction*/int visitBodyStart(Body body)
    {
        /*SectionLayoutMode*/int gridType = body.getParentSection().getSectPr().getGridType();

        // If document uses "document grid" feature line unit size is specified by LinePitch section property.
        // Otherwise, line unit size is 12 pt.
        mTwipsPerLineUnit = (gridType == SectionLayoutMode.DEFAULT)
            ? ConvertUtilCore.pointToTwip(DEFAULT_TWIPS_PER_LINE)
            : body.getParentSection().getSectPr().getLinePitch();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter)
    {
        // WORDSNET-26551 Word always uses default value for header/footer content.
        mTwipsPerLineUnit = ConvertUtilCore.pointToTwip(DEFAULT_TWIPS_PER_LINE);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para)
    {
        updateTwipsFromUnits(para, ParaAttr.SPACE_BEFORE_UNITS);
        updateTwipsFromUnits(para, ParaAttr.SPACE_AFTER_UNITS);
        updateTwipsFromUnits(para, ParaAttr.RIGHT_INDENT_UNITS);

        boolean leftUpdated = updateTwipsFromUnits(para, ParaAttr.LEFT_INDENT_UNITS);
        boolean firstLineUpdated = updateTwipsFromUnits(para, ParaAttr.FIRST_LINE_INDENT_UNITS);

        // WORDSNET-19909 Update left indent using hanging even if "FirstLineIndentUnits" is not specified.
        if (firstLineUpdated || leftUpdated)
            applyHangingToLeftIndent(para.getParaPr(), (/*int*/Integer)((IParaAttrSource)para).fetchParaAttr(ParaAttr.FIRST_LINE_INDENT));

        return VisitorAction.CONTINUE;
    }

    private void updateTwipsFromUnits(Style style)
    {
        for (int key : G_UNIT_KEYS)
        {
            int twipKey = getTwipsKey(key);

            if (style.getParaPr().containsKey(twipKey))
                continue;

            updateTwipsFromUnits(style, style.getParaPr(), null, key);

            // AM. Found strange MS Word logic.
            // It removes inherited value for styles after indent calculation, at for FirstLineIndent.
            // Will try to test it deeper during next issues analysis.
            if (key == ParaAttr.FIRST_LINE_INDENT_UNITS)
            {
                Object inhValue = getInheritedValueSafe(style, ParaAttr.FIRST_LINE_INDENT);

                if(msObject.equals(inhValue, style.getParaPr().get(ParaAttr.FIRST_LINE_INDENT)))
                    style.getParaPr().remove(ParaAttr.FIRST_LINE_INDENT);
            }
        }
    }

    private boolean updateTwipsFromUnits(Paragraph para, int unitsKey)
    {
        return updateTwipsFromUnits(para, para.getParaPr(), para.getChildNodes(NodeType.ANY, false), unitsKey);
    }

    private static int getTwipsKey(int unitsKey)
    {
        switch (unitsKey)
        {
            case ParaAttr.SPACE_BEFORE_UNITS: return ParaAttr.SPACE_BEFORE;
            case ParaAttr.SPACE_AFTER_UNITS: return ParaAttr.SPACE_AFTER;
            case ParaAttr.RIGHT_INDENT_UNITS: return ParaAttr.RIGHT_INDENT;
            case ParaAttr.LEFT_INDENT_UNITS: return ParaAttr.LEFT_INDENT;
            case ParaAttr.FIRST_LINE_INDENT_UNITS: return ParaAttr.FIRST_LINE_INDENT;

            default:
                throw new IllegalStateException("Unexpected key value.");
        }
    }

    /// <summary>
    /// Updates single property in ParaPr.
    /// </summary>
    private boolean updateTwipsFromUnits(IRunAttrSource runAttrSource, ParaPr paraPr, NodeCollection childNodes, int unitsKey)
    {
        int twipsKey = getTwipsKey(unitsKey);

        Object val = paraPr.get(unitsKey);

        // WORDSNET-27416 Spacing attributes are not updated from units because of inherited spacing presence.
        // Actually we need to look if any inheritance presents but lets simplify solution for a while.
        if ((paraPr.getLineSpacingRule() == LineSpacingRule.EXACTLY) &&
            (((unitsKey == ParaAttr.SPACE_BEFORE_UNITS) && (paraPr.getSpaceBefore() > 0)) ||
             ((unitsKey == ParaAttr.SPACE_AFTER_UNITS) && (paraPr.getSpaceAfter() > 0)))
           )
            return false;

        if (val == null)
            return false;

        int units = (/*int*/Integer) val;

        // It seems that Word ignores 0 value for paragraphs.
        if (units == 0 && runAttrSource instanceof Paragraph)
            return false;

        // twips per unit.
        int tpu = 0;

        switch (unitsKey)
        {
            case ParaAttr.SPACE_BEFORE_UNITS:
            case ParaAttr.SPACE_AFTER_UNITS:
                tpu = mTwipsPerLineUnit;
                break;

            case ParaAttr.RIGHT_INDENT_UNITS:
            case ParaAttr.LEFT_INDENT_UNITS:
                tpu = mTwipsPerCharacterUnit;
                break;

            case ParaAttr.FIRST_LINE_INDENT_UNITS:
            {
                // Start with paragraph break size for empty paragraph case.
                tpu = (/*int*/Integer)InlineHelper.fetchAttr(runAttrSource, FontAttr.SIZE) * 10;

                if (childNodes == null)
                    break;

                for (Node node : (Iterable<Node>) childNodes)
                {
                    IInline inline = ms.as(node, IInline.class);

                    if (inline != null)
                    {
                        // This is not quite correct as Scale/Spacing must be taken into account here. See WORDSNET-19367 for details.
                        // Thus, FirstLineIndent and/or LeftIndent attributes can be calculated incorrectly (Layout code will not use attributes FirstLineIndent/LeftIndent calculated here).
                        tpu = (/*int*/Integer)InlineHelper.fetchAttr(inline, FontAttr.SIZE) * 10;
                        break;
                    }
                }

                break;
            }

            default:
                break;
        }

        int twips = (int)msMath.round(((double)units) / 100.0 * tpu);
        paraPr.setAttr(twipsKey, twips);

        return true;
    }

    /// <summary>
    /// Applies hanging to current left indent value.
    /// </summary>
    private static void applyHangingToLeftIndent(ParaPr paraPr, int firstLineIndent)
    {
        // Note that LeftIndent must be updated before FirstLineIndent update.
        if (firstLineIndent < 0)
        {
            // It seems that Word ignores 0 value.
            boolean hasLeftIndentUnits = (/*int*/Integer)paraPr.fetchAttr(ParaAttr.LEFT_INDENT_UNITS) != 0;

            // If there is no left indent units then left indent twips was not updated.
            int leftIndent = hasLeftIndentUnits ? paraPr.getLeftIndent() : 0;
            paraPr.setAttr(ParaAttr.LEFT_INDENT, leftIndent + (-firstLineIndent));
        }
    }

    /// <summary>
    /// Safely gets inherited value by the key.
    /// </summary>
    /// <remarks>
    /// At the moment when we need inherited value we might have invalid style hierarchy.
    /// </remarks>
    private static Object getInheritedValueSafe(IParaAttrSource paraAttrSource, int key)
    {
        Object val = null;
        try
        {
            val = paraAttrSource.fetchInheritedParaAttr(key);
        }
        catch(Exception e)
        {
            // Do nothing.
        }

        return val;
    }

    /// <summary>
    /// TODO this code never used.
    /// Twips per line unit in the Section being processed.
    /// </summary>
    private int mTwipsPerLineUnit = ConvertUtilCore.pointToTwip(DEFAULT_TWIPS_PER_LINE);

    /// <summary>
    /// Twips per character unit in the Document.
    /// </summary>
    /// <remarks>
    /// It looks that styles use this constant value.
    /// After styles are calculated this value is updated from document parts properties.
    /// </remarks>
    private int mTwipsPerCharacterUnit = 100;

    /// <summary>
    /// Twips per line unit if "document grid" is not used.
    /// </summary>
    private static final int DEFAULT_TWIPS_PER_LINE = 12;

    /// <summary>
    /// All keys for character units indents.
    /// </summary>
    private static /*final*/ int[] G_UNIT_KEYS = new int[]
    {
        ParaAttr.SPACE_BEFORE_UNITS, ParaAttr.SPACE_AFTER_UNITS, ParaAttr.RIGHT_INDENT_UNITS,
        ParaAttr.LEFT_INDENT_UNITS, ParaAttr.FIRST_LINE_INDENT_UNITS
    };

}

