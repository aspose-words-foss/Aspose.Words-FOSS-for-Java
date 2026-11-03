// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class for all math objects that exist in Office Math.
/// Encapsulates differences in properties and semantics of all Office Math elements.
/// </summary>
/// <remarks>
/// Specific math properties are implemented through AttrCollection for two reasons:
/// - Use mechanisms of default/non-default attrs for writing(non-defaults only) into docx
/// - Use same AttrCollection pattern for consistency with other attr-bearing nodes.
/// - We might want to include math attr collections in later refactoring effort <see cref="Attr"/>.
/// </remarks>
abstract class MathObject extends AttrCollection
{
    /// <summary>
    /// Each math object has its own list of allowed children per spec.
    /// This method allows to maintain tree integrity.
    /// </summary>
    /*virtual*/ boolean canInsert(Node node)
    {
        boolean result;

        if (getNeedsMathObjectArgumentWrapper())
        {
            // this object needs every argument wrapped into special kind of MathObjects.
            result = (node.getNodeType() == NodeType.OFFICE_MATH) &&
                    (((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.ARGUMENT);
        }
        else
        {
            // this object allows arguments inserted as direct children.
            result = canBeMathArgument(node);
        }

        return result;
    }

    /// <summary>
    /// Returns true if a <see cref="Node"/> can be argument of an Office Math node.
    /// </summary>
    static boolean canBeMathArgument(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.OFFICE_MATH:
                return (((OfficeMath)node).getMathObject()).canBeArgument();

            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.EDITABLE_RANGE_START:
            case NodeType.EDITABLE_RANGE_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.COMMENT:
            case NodeType.SMART_TAG:
            case NodeType.RUN:
            // andrnosk: WORDSNET-6505 MathML contains MERGEFIELD.
            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            case NodeType.FIELD_END:
            // WORDSNET-12977 MathML contains FormField.
            case NodeType.FORM_FIELD:
            // These are not in the model, but dont forget to insert them here when needed.
            // NodeType.customXmlDelRangeStart - not supported.
            // NodeType.customXmlDelRangeEnd - not supported.
            // NodeType.customXmlInsRangeStart - not supported.
            // NodeType.customXmlInsRangeEnd - not supported.
            // NodeType.customXmlMoveFromRangeStart - not supported.
            // NodeType.customXmlMoveFromRangeEnd - not supported.
            // NodeType.customXmlMoveToRangeStart - not supported.
            // NodeType.customXmlMoveToRangeEnd - not supported.
            // WORDSNET-9221 It seems that Shape can be argument.
            case NodeType.SHAPE:
            // WORDSNET-25441 GroupShape can be argument.
            case NodeType.GROUP_SHAPE:
            // WORDSNET-10346 Footnote can be argument.
            case NodeType.SPECIAL_CHAR:
            case NodeType.FOOTNOTE:
                return true;

            // WORDSNET-20848 An inline-level SDT can be argument.
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return (((StructuredDocumentTag)node).getLevel() == MarkupLevel.INLINE);

            // Any other node can not be inserted as Math argument.
            default:
                return false;
        }
    }

    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    abstract /*MathObjectType*/int getMathObjectType();

    /// <summary>
    /// For most objects it is needed to have MathObjectArgumentBase as a wrapper for every argument.
    /// The only exclusions are <see cref="MathObjectArgumentBase"/> and <see cref="MathObjectOMath"/>
    /// </summary>
    /*virtual*/ boolean getNeedsMathObjectArgumentWrapper() { return true; }

    /// <summary>
    /// Returns true if a Math entity can act as an argument for other Math entity.
    /// More formally this means that this math element can be inside "e" element as described in Iso29500 part 1,
    /// chapter 22.1.2.32 e (Element (Argument))
    /// </summary>
    /*virtual*/ boolean canBeArgument() { return true; }

    /// <summary>
    /// Some character properties defined for MathObject descendants, e.g. <see cref="MathObjectNAry"/>
    /// can contain empty characters, since there is no such thing in .NET, we will emulate empty char by UNICODE symbol 0x0000
    /// </summary>
    static final char EMPTY_CHARACTER = '\u0000';

    private static /*final*/ AttrCollection G_DEFAULTS;

    static/* MathObject()*/
    {
        G_DEFAULTS = new MathObjectOMath();
        G_DEFAULTS.add(MathAttr.JUSTIFICATION, OfficeMathJustification.DEFAULT);
        G_DEFAULTS.add(MathAttr.POSITION, MathPosition.DEFAULT);
        G_DEFAULTS.add(MathAttr.ACCENT_CHARACTER, MathObjectAccent.DEFAULT_CHARACTER);
        G_DEFAULTS.add(MathAttr.HIDE_BOTTOM, false);
        G_DEFAULTS.add(MathAttr.HIDE_TOP, false);
        G_DEFAULTS.add(MathAttr.HIDE_LEFT, false);
        G_DEFAULTS.add(MathAttr.HIDE_RIGHT, false);
        G_DEFAULTS.add(MathAttr.STRIKE_BLTR, false);
        G_DEFAULTS.add(MathAttr.STRIKE_H, false);
        G_DEFAULTS.add(MathAttr.STRIKE_TLBR, false);
        G_DEFAULTS.add(MathAttr.STRIKE_V, false);
        G_DEFAULTS.add(MathAttr.IS_OP_EMU, false);
        G_DEFAULTS.add(MathAttr.IS_ALIGNMENT_POINT, false);
        G_DEFAULTS.add(MathAttr.IS_DIFFERENTIAL, false);
        G_DEFAULTS.add(MathAttr.NO_BREAKS, true);
        G_DEFAULTS.add(MathAttr.BEGIN_CHAR, MathObjectDelimiter.DEFAULT_BEGINNING_CHARACTER);
        G_DEFAULTS.add(MathAttr.END_CHAR, MathObjectDelimiter.DEFAULT_ENDING_CHARACTER);
        G_DEFAULTS.add(MathAttr.SEPARATOR_CHAR, MathObjectDelimiter.DEFAULT_SEPARATOR_CHARACTER);
        G_DEFAULTS.add(MathAttr.GROW_OPERAND, false);
        G_DEFAULTS.add(MathAttr.DELIMITER_SHAPE, MathDelimiterShape.DEFAULT);
        G_DEFAULTS.add(MathAttr.ROW_SPACING_RULE, MathSpacingRule.DEFAULT);
        G_DEFAULTS.add(MathAttr.ROW_SPACING, 0);
        G_DEFAULTS.add(MathAttr.BASE_JUSTIFICATION, MathBaseJustification.DEFAULT);
        G_DEFAULTS.add(MathAttr.MAX_DIST, false);
        G_DEFAULTS.add(MathAttr.OBJ_DIST, false);
        G_DEFAULTS.add(MathAttr.GROUP_CHAR_POSITION, MathPosition.DEFAULT);
        G_DEFAULTS.add(MathAttr.VERTICAL_JUSTIFICATION, MathVerticalJustification.DEFAULT);
        G_DEFAULTS.add(MathAttr.GROUP_CHAR, MathObjectGroupCharacter.DEFAULT_CHARACTER);
        G_DEFAULTS.add(MathAttr.IS_SHOWN, true);
        G_DEFAULTS.add(MathAttr.IS_TRANSPARENT, false);
        G_DEFAULTS.add(MathAttr.IS_ZERO_ASCENT, false);
        G_DEFAULTS.add(MathAttr.IS_ZERO_WIDTH, false);
        G_DEFAULTS.add(MathAttr.IS_ZERO_DESCENT, false);
        G_DEFAULTS.add(MathAttr.FRACTION_TYPE, MathFractionType.DEFAULT);
        G_DEFAULTS.add(MathAttr.IS_HIDE_PLACEHOLDERS, false);
        G_DEFAULTS.add(MathAttr.MIN_COLUMN_WIDTH, 0);
        G_DEFAULTS.add(MathAttr.COLUMN_GAP_RULE, MathSpacingRule.DEFAULT);
        G_DEFAULTS.add(MathAttr.COLUMN_GAP, 0);
        G_DEFAULTS.add(MathAttr.LIMIT_LOCATION, MathLimitLocation.UNDEFINED);
        G_DEFAULTS.add(MathAttr.IS_HIDE_SUBSCRIPT, false);
        G_DEFAULTS.add(MathAttr.IS_HIDE_SUPERSCRIPT, false);
        G_DEFAULTS.add(MathAttr.NARY_CHAR, MathObjectNAry.DEFAULT_CHARACTER);
        G_DEFAULTS.add(MathAttr.DEGREE_HIDE, false);
        G_DEFAULTS.add(MathAttr.IS_ALIGN_SCRIPTS, false);
    }
}

