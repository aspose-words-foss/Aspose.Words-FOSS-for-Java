// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;


/// <summary>
/// Provides methods for replacing of an individual field with its result and its completely removal
/// considering that it can be a part of a parent field's code.
/// </summary>
/*static*/ class FieldReplacer
{
	/* Simulation of static class by using private constructor */
	private FieldReplacer()
	{}

    /// <summary>
    /// Completely removes the specified field considering that it can be a part of a parent field's code.
    /// Returns a node right after the field. If the field's end is the last child of its parent node, returns
    /// its parent paragraph. If the field is already removed, returns <c>null</c>.
    /// </summary>
    static Node remove(Field field) throws Exception
    {
        if (field.isRemoved())
            return null;

        separateFieldCharsIfNeeded(field, RemainingFieldPart.NONE);

        // We can obtain a proper reference node only after the possible separation above is done.
        // Field start is used because of NodeJoinMode.JoinToPreviousSibling.
        Node refNode = (field.getEnd().getNextSibling() != null ? field.getEnd().getNextSibling() : field.getStart().getParentParagraph());

        NodeRemover.remove(field.getStart(), field.getEnd());

        notifyUpdaterIfNeeded(field, true);

        return refNode;
    }

    /// <summary>
    /// Replaces the specified field with its result considering that it can be a part of a parent field's code.
    /// </summary>
    static void replaceWithResult(Field field) throws Exception
    {
        replaceWithResult(field, EmptyFieldReplaceListener.INSTANCE, NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING);
    }

    /// <summary>
    /// Replaces the specified field with its result considering that it can be a part of a parent field's code.
    /// </summary>
    static void replaceWithResult(Field field, IFieldReplaceListener listener, /*NodeJoinMode*/int joinMode) throws Exception
    {
        if (field.isRemoved())
            return;

        if (FieldUtil.isFieldResultInvisible(field))
        {
            // WORDSNET-7552 Completely remove the field if its result is preserved from being viewed
            // after all of the dependent fields are updated.
            if (field.isUpdating() && FieldUtil.producesBookmarkAsResult(field.getType()))
            {
                field.getUpdater().deferRemove(field);
            }
            else
            {
                remove(field);
            }
        }
        else
        {
            NodeRange fakeResult = field.getFakeResult();

            boolean hasFakeResult = (fakeResult != null) && !fakeResult.isVoid() && !fakeResult.isEmpty();

            if (!field.hasSeparator() && !hasFakeResult)
            {
                // No result - remove the field completely.
                remove(field);
            }
            else
            {
                if (hasFakeResult)
                {
                    field.removeFieldResult();
                    field.ensureSeparator(true);
                    NodeCopier.copy(fakeResult, field.getEnd());
                }

                removeFieldCode(field, listener, joinMode);
            }
        }
    }

    private static void removeFieldCode(Field field, IFieldReplaceListener listener, /*NodeJoinMode*/int joinMode) throws Exception
    {
        listener.beforeReplaceWithResult();

        separateFieldCharsIfNeeded(field, RemainingFieldPart.RESULT);

        // Remove field start, field code and field separator.
        NodeRemover.remove(field.getStart(), true, field.getSeparator(), true, joinMode);
        // Remove field end.
        field.getEnd().remove();

        notifyUpdaterIfNeeded(field, false);
    }

    /// <summary>
    /// If the specified field is a separate token of a parent field's code (i.e. it is not double quoted) and its
    /// replacement leads its remainder to join with other tokens, separates the remainder with appropriate separators.
    /// </summary>
    private static void separateFieldCharsIfNeeded(Field field, /*RemainingFieldPart*/int part)
    {
        /*FieldCharSeparators*/int separators = defineFieldCharSeparators(field, part);
        if (separators == FieldCharSeparators.NONE)
            return;

        Inline fieldCharBeforeSeparator = getFieldCharBeforeSeparator(field, part, separators);
        insertSeparator(fieldCharBeforeSeparator, separators, true);

        Inline fieldCharAfterSeparator = getFieldCharAfterSeparator(field, part, separators);
        insertSeparator(fieldCharAfterSeparator, separators, false);
    }

    /// <summary>
    /// Returns appropriate separators to use while replacing of the specified field.
    /// </summary>
    private static /*FieldCharSeparators*/int defineFieldCharSeparators(Field field, /*RemainingFieldPart*/int part)
    {
        // At the moment this method works properly only for fields being updated, since we can not easily determine
        // whether the field is a part of a parent field's code as well as whether it is double quoted, when it is not
        // being updated (or is being updated selectively). But let's leave it as is until any related client request.
        if (!field.isUpdating())
            return FieldCharSeparators.NONE;

        // If the field is located in a place where it can be safely replaced (i.e. it is topmost, or double quoted, or
        // a part of another field's result), do not separate it.
        if (field.getUpdateContext().isSafeReplacingPossible())
            return FieldCharSeparators.NONE;

        // If the field is a part of a parent formula field's code, then its result must not be enclosed in double quotes.
        // Moreover, in this case the field is surrounded by operator symbols which act like separators themselves,
        // so we do not need to add extra whitespaces either. It is a bit hacky to handle this here, but it works.
        if (field.getUpdateContext().getParentContext().getField().getType() == FieldType.FIELD_FORMULA)
            return FieldCharSeparators.NONE;

        // If the field is to be completely removed (which is equal to it to be replaced with an empty result),
        // then it should be enclosed in double quotes.
        if (part == RemainingFieldPart.NONE)
            return FieldCharSeparators.DOUBLE_QUOTES;

        // If the field is to be replaced with an empty or multi-word (i.e. containing whitespaces) result,
        // then it should be enclosed in double quotes.
        String fieldResult = field.getResult();
        if (!StringUtil.hasChars(fieldResult) || StringUtil.containsAnyWhitespaces(fieldResult))
            return FieldCharSeparators.DOUBLE_QUOTES;

        // WORDSNET-7614, 7390 REWORKED.
        // If the field's result is to be joined with other parent field's code tokens, separate it with whitespaces.
        /*FieldCharSeparators*/int separators = FieldCharSeparators.NONE;
        if (!isSeparateFieldBoundary(field.getStart()))
            separators |= FieldCharSeparators.LEADING_WHITE_SPACE;

        if (!isSeparateFieldBoundary(field.getEnd()))
            separators |= FieldCharSeparators.TRAILING_WHITE_SPACE;

        return separators;
    }

    /// <summary>
    /// Returns a value indicating whether the specified field boundary (i.e. field start or field end) node is separated
    /// from other parent field's code tokens by any separator other than the boundary node itself.
    /// </summary>
    private static boolean isSeparateFieldBoundary(Node boundary)
    {
        // Go forward from an end boundary, go backward from a start boundary.
        boolean isForward = (boundary.getNodeType() == NodeType.FIELD_END);

        for (Node node = boundary.getNearestSibling(isForward); node != null; node = node.getNearestSibling(isForward))
        {
            // Skip zero-length nodes.
            if (NodeUtil.isZln(node))
                continue;

            switch (node.getNodeType())
            {
                case NodeType.RUN:
                {
                    String text = node.getText();

                    // Skip empty runs.
                    if (!StringUtil.hasChars(text))
                        continue;

                    // Check the first or the last character of the run depending on the movement direction.
                    int charIndexToCheck = isForward ? 0 : (text.length() - 1);
                    char charToCheck = text.charAt(charIndexToCheck);

                    // Only double quote and whitespace characters are usually considered as field code token separators.
                    return (FieldCodeParser.isDoubleQuote(charToCheck) || msChar.isWhiteSpace(charToCheck));
                }
                case NodeType.FIELD_START:
                case NodeType.FIELD_SEPARATOR:
                case NodeType.FIELD_END:
                    // Other fields' boundary nodes are considered as field code token separators either.
                    return true;
                default:
                    // Other nodes are considered as parts of field code tokens.
                    return false;
            }
        }

        // If we have reached a parent paragraph boundary, this means that the specified field boundary is separated
        // by a paragraph break.
        return true;
    }

    /// <summary>
    /// Returns a field char belonging to the given field which should be followed by a separator according to
    /// the specified <see cref="RemainingFieldPart"/>. Returns <c>null</c> if the separation is not required.
    /// </summary>
    private static Inline getFieldCharBeforeSeparator(Field field, /*RemainingFieldPart*/int part, /*FieldCharSeparators*/int separators)
    {
        switch (part)
        {
            case RemainingFieldPart.NONE:
                return hasTrailingFieldCharSeparator(separators) ? field.getEnd() : null;
            case RemainingFieldPart.RESULT:
                return hasLeadingFieldCharSeparator(separators) ? field.getSeparator() : null;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "part");
        }
    }

    /// <summary>
    /// Returns a field char belonging to the given field which should be preceded by a separator according to
    /// the specified <see cref="RemainingFieldPart"/>. Returns <c>null</c> if the separation is not required.
    /// </summary>
    private static Inline getFieldCharAfterSeparator(Field field, /*RemainingFieldPart*/int part, /*FieldCharSeparators*/int separators)
    {
        switch (part)
        {
            case RemainingFieldPart.NONE:
                return hasLeadingFieldCharSeparator(separators) ? field.getStart() : null;
            case RemainingFieldPart.RESULT:
                return hasTrailingFieldCharSeparator(separators) ? field.getEnd() : null;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "part");
        }
    }

    /// <summary>
    /// Inserts a separator run before or after the specified field char.
    /// </summary>
    private static void insertSeparator(Inline fieldChar, /*FieldCharSeparators*/int separators, boolean isAfter)
    {
        if (fieldChar == null)
            return;

        String separatorText = getFieldCharSeparatorText(separators);
        RunPr separatorRunPr = getFieldCharSeparatorRunPr(fieldChar, fieldChar.getNearestSibling(isAfter));
        Run separatorRun = new Run(fieldChar.getDocument(), separatorText, separatorRunPr);

        fieldChar.getParentNode().insert(separatorRun, fieldChar, isAfter);
    }

    private static RunPr getFieldCharSeparatorRunPr(Inline fieldChar, Node sibling)
    {
        Inline inline = (ms.as(sibling, Inline.class) != null ? ms.as(sibling,Inline.class) : fieldChar);
        return AttrCollectionExtension.deepClone(inline.getRunPr());
    }

    private static String getFieldCharSeparatorText(/*FieldCharSeparators*/int separators)
    {
        if (isDoubleQuotes(separators))
            return "\"";

        if (hasLeadingWhiteSpace(separators) || hasTrailingWhiteSpace(separators))
            return ControlChar.SPACE;

        throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "separators");
    }

    private static boolean hasLeadingFieldCharSeparator(/*FieldCharSeparators*/int separators)
    {
        return (isDoubleQuotes(separators) || hasLeadingWhiteSpace(separators));
    }

    private static boolean hasTrailingFieldCharSeparator(/*FieldCharSeparators*/int separators)
    {
        return (isDoubleQuotes(separators) || hasTrailingWhiteSpace(separators));
    }

    private static boolean isDoubleQuotes(/*FieldCharSeparators*/int separators)
    {
        return (separators == FieldCharSeparators.DOUBLE_QUOTES);
    }

    private static boolean hasLeadingWhiteSpace(/*FieldCharSeparators*/int separators)
    {
        return ((separators & FieldCharSeparators.LEADING_WHITE_SPACE) != 0);
    }

    private static boolean hasTrailingWhiteSpace(/*FieldCharSeparators*/int separators)
    {
        return ((separators & FieldCharSeparators.TRAILING_WHITE_SPACE) != 0);
    }

    private static void notifyUpdaterIfNeeded(Field field, boolean isFieldResultRemoved) throws Exception
    {
        if (field.isUpdating())
            field.getUpdater().notifyFieldReplaced(field, isFieldResultRemoved);
    }

    /// <summary>
    /// Specifies what characters should be used to separate a field remainder after replacing of
    /// the corresponding field.
    /// </summary>
    private /*enum*/ final class FieldCharSeparators
    {
        private FieldCharSeparators(){}
        
        public static final int NONE = 0;
        public static final int DOUBLE_QUOTES = 1;
        public static final int LEADING_WHITE_SPACE = 2;
        public static final int TRAILING_WHITE_SPACE = 4;

        public static final int length = 4;
    }

    /// <summary>
    /// Specifies a field part that should stay in a document instead of the corresponding field
    /// after its replacing.
    /// </summary>
    private /*enum*/ final class RemainingFieldPart
    {
        private RemainingFieldPart(){}
        
        public static final int NONE = 0;
        public static final int RESULT = 1;

        public static final int length = 2;
    }
}

