// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;


/// <summary>
/// Compares two expressions. Used when evaluating the IF and COMPARE fields.
/// Comparison expressions seem to be handled differently in these fields than those in formula fields.
/// For example, they can include strings enclosed in double quotes.
/// </summary>
class ComparisonEvaluator
{
    private ComparisonEvaluator(Field field, ComparisonExpressionBag expression)
    {
        mExpression = expression;
        mFieldContext = new FieldContext(field);
    }

    static Constant evaluate(Field field, IComparisonExpression expression) throws Exception
    {
        IComparisonExpressionEvaluator customEvaluator = field.fetchDocument().getFieldOptions().getComparisonExpressionEvaluator();
        if (customEvaluator != null)
        {
            ComparisonEvaluationResult result = customEvaluator.evaluate(
                field,
                new ComparisonExpression(
                    expression.getLeftExpression(),
                    expression.getComparisonOperator(),
                    expression.getRightExpression()));

            if (result != null)
            {
                return msString.isNullOrEmpty(result.getErrorMessage())
                    ? (Constant)new BooleanConstant(result.getResult())
                    : new ErrorConstant(result.getErrorMessage());
            }
        }

        ComparisonEvaluator comparisonEvaluator = new ComparisonEvaluator(
            field,
            new ComparisonExpressionBag(expression));

        return comparisonEvaluator.evaluateCore();
    }

    private Constant evaluateCore() throws Exception
    {
        if (mExpression.getLeftExpression() == null)
            return new ErrorConstant("Error! Missing test condition.");

        if (mExpression.getComparisonOperator() == null)
            return new BooleanConstant(true);

        if (!isOperatorValid(mExpression.getComparisonOperator()))
            return new ErrorConstant("Error! Unknown op code for conditional.");

        if (mExpression.getRightExpression() == null)
            return new ErrorConstant("Error! Missing second part of test condition.");

        Constant rightResult = null;
        Constant leftResult = null;

        if (mExpression.isLeftExpressionInQuotationMarks() && mExpression.isRightExpressionInQuotationMarks())
        {
            leftResult = new StringConstant(mExpression.getLeftExpression());
            rightResult = new StringConstant(mExpression.getRightExpression());
        }
        else if (mExpression.isLeftExpressionInQuotationMarks())
        {
            leftResult = new StringConstant(mExpression.getLeftExpression());
            rightResult = resolveBookmark(mExpression.getRightExpression());
        }
        else if (mExpression.isRightExpressionInQuotationMarks())
        {
            leftResult = resolveBookmark(mExpression.getLeftExpression());
            if (leftResult != null)
            {
                rightResult = new StringConstant(mExpression.getRightExpression());
            }
            else
            {
                leftResult = ExpressionEvaluator.evaluateReferenceExpression(
                    mFieldContext,
                    mExpression.getLeftExpression(),
                    false);

                rightResult = ExpressionEvaluator.evaluateReferenceExpression(
                    mFieldContext,
                    mExpression.getRightExpression());
            }
        }
        else
        {
            ReferenceExpressionResult leftResultInfo =
                resolveBookmarkOrEvaluateReferenceExpression(mExpression.getLeftExpression());
            ReferenceExpressionResult rightResultInfo =
                resolveBookmarkOrEvaluateReferenceExpression(mExpression.getRightExpression());

            if ((leftResultInfo == null) || !leftResultInfo.hasLeadingMinusOperator() ||
                (rightResultInfo == null) || !rightResultInfo.hasLeadingMinusOperator())
            {
                if (leftResultInfo != null)
                    leftResult = leftResultInfo.getResult();

                if (rightResultInfo != null)
                    rightResult = rightResultInfo.getResult();
            }
        }

        leftResult = validateResult(leftResult, mExpression.getLeftExpression());
        rightResult = validateResult(rightResult, mExpression.getRightExpression());

        boolean isLeftResultStringConstant = leftResult instanceof StringConstant;
        boolean isRightResultStringConstant = rightResult instanceof StringConstant;
        if (isLeftResultStringConstant || isRightResultStringConstant)
        {
            String leftOperand = isLeftResultStringConstant ? leftResult.getValueString() : mExpression.getLeftExpression();
            String rightOperand = isRightResultStringConstant ? rightResult.getValueString() : mExpression.getRightExpression();
            boolean areWildcardsAllowed = mExpression.isRightExpressionInQuotationMarks() ||
                                       msString.isNullOrEmpty(rightOperand) ||
                                       !Character.isDigit(rightOperand.charAt(0));
            return new BooleanConstant(compareStrings(leftOperand, rightOperand, areWildcardsAllowed));
        }

        return new BooleanConstant(compareDoubles(leftResult.getValueDouble(), rightResult.getValueDouble()));
    }

    private ReferenceExpressionResult resolveBookmarkOrEvaluateReferenceExpression(String expression) throws Exception
    {
        StringConstant bookmark = resolveBookmark(expression);
        if (bookmark != null)
            expression = bookmark.getValueString();

        ReferenceExpressionResult result = ExpressionEvaluator.evaluateReferenceExpressionWithInfo(
            mFieldContext,
            expression,
            true);

        if (result != null)
            return result;

        if (bookmark != null)
            return new ReferenceExpressionResult(bookmark, false);

        return null;
    }

    private static Constant validateResult(Constant constant, String expression)
    {
        return isValidConstant(constant)
            ? constant
            : new StringConstant(expression);
    }

    private static boolean isValidConstant(Constant constant)
    {
        if (constant == null)
            return false;

        if (constant.isError())
            return false;

        if (constant instanceof BooleanConstant)
            return false;

        return true;
    }

    private StringConstant resolveBookmark(String bookmarkName) throws Exception
    {
        Bookmark bookmark = FieldUtil.getCachedBookmark(mFieldContext.getField(), bookmarkName);
        return bookmark != null
            ? new StringConstant(bookmark.getText(true))
            : null;
    }

    private boolean compareStrings(String leftOperand, String rightOperand, boolean areWildcardsAllowed)
    {
        // The comparison operator should be '=' or '<>' in order to use wildcards.
        areWildcardsAllowed = areWildcardsAllowed && (isEqualityOperator() || isInequalityOperator());

        // OOXML states that wildcards are only allowed in the left-hand expression, but Word (as well as its help)
        // shows only the right-hand expression may have them.
        boolean hasWildcards = (rightOperand.indexOf(WORD_SINGLE_WILDCARD) != -1) || (rightOperand.indexOf(WORD_WILDCARD) != -1);

        if (!areWildcardsAllowed || !hasWildcards)
        {
            // WORDSNET-4640 Use the StringSort order for comparing.
            return compareDoubles(StringUtil.compareStringSort(leftOperand, rightOperand), 0.0);
        }

        // WORDSNET-5692 Replacing self-written wildcard search algorithm with Regex-based one.
        // We don't use static Regex method here as we don't want this Regex to be cached because its pattern is dynamic.
        Regex regex = new Regex(getRegexPattern(rightOperand, leftOperand));
        return regex.isMatch(leftOperand) == isEqualityOperator();
    }

    /// <summary>
    /// Gets Regex pattern by word wildcard search pattern.
    /// </summary>
    /// <param name="wordPattern">word wildcard search pattern</param>
    /// <param name="leftOperand"></param>
    /// <returns>Regex pattern</returns>
    private static String getRegexPattern(String wordPattern, String leftOperand)
    {
        StringBuilder builder = new StringBuilder(REGEX_STRING_START_DESIGNATOR);
        String workWordPattern = wordPattern;
        boolean needLeaveLoop = false;
        int loopCount = 0;
        boolean isFirstWildcard = true;
        int wordPatternLength = wordPattern.length();
        int firstWildcardIndex = workWordPattern.indexOf(WORD_WILDCARD);

        // If firstWildcard is in the center of the word (not first or last symbol)
        if ((firstWildcardIndex > 0) && (firstWildcardIndex < wordPatternLength - 1))
        {
            // If after * there is any symbol and length of the leftOperand equal length of the part wordPattern before * then add symbol ?
            // that regex always will be false
            if (leftOperand.length() == firstWildcardIndex)
            {
                workWordPattern = msString.replace(workWordPattern, WORD_WILDCARD, WORD_SINGLE_WILDCARD);
            }
            // Word ignores only one symbol after Wildcard - equal simple *
            else if (firstWildcardIndex == wordPatternLength - 2)
            {
                workWordPattern = msString.remove(workWordPattern, wordPatternLength - 1);
            }
            // WildcardIndex and index of last symbol leftOperand are the same
            else if (firstWildcardIndex == leftOperand.length() - 1)
            {
                // All symbols after Wildcard Word ignores in this case - remove these symbols for correct regex
                workWordPattern = msString.remove(workWordPattern, firstWildcardIndex + 1);
            }
            // Create specific regex
            else
            {
                // If WildcardIndex > 0 need specific logic
                if (firstWildcardIndex > 1)
                {
                    // Word ignores some symbols after *. Symbols count is count of symbols before *
                    // Example cvb*dfd equal cvb* or cvb*dfhgjkl equal cvb*gjkl
                    int removeCount = workWordPattern.length() - 1 - firstWildcardIndex;
                    if (removeCount > firstWildcardIndex)
                        removeCount = firstWildcardIndex;

                    workWordPattern = msString.remove(workWordPattern, firstWildcardIndex + 1, removeCount);
                }
                else
                {
                    int leftOperandSymbolsAfterWildcardCount = leftOperand.length() - firstWildcardIndex - 1;
                    int wordPatternSymbolsAfterWildcardCount = workWordPattern.length() - firstWildcardIndex - 1;
                    // Get length of the part wordPattern after * - length of the part wordPattern after * position
                    int removeCount = wordPatternSymbolsAfterWildcardCount - leftOperandSymbolsAfterWildcardCount;

                    // First symbol after * always doesn't matter
                    if (removeCount <= 0)
                        removeCount = 1;

                    // Create strange regex
                    workWordPattern = msString.remove(workWordPattern, firstWildcardIndex + 1, removeCount);
                }
            }

        }

        // Prepare Regex
        for (int i = 0; i < workWordPattern.length(); i++)
        {
        	char c = workWordPattern.charAt(i);
            switch (c)
            {
                case WORD_WILDCARD:
                    // Second and other "*" is simple symbols (not Wildcard)
                    if (isFirstWildcard)
                    {
                        msStringBuilder.append(builder, REGEX_WILDCARD);
                        isFirstWildcard = false;
                    }
                    else
                    {
                        msStringBuilder.append(builder, REGEX_UNICODE_DESIGNATOR);
                        msStringBuilder.append(builder, FormatterPal.intToStrX4(c));
                    }

                    // Only if wildcard at the end of string need leave loop
                    needLeaveLoop = loopCount == wordPatternLength - 1;
                    break;
                case WORD_SINGLE_WILDCARD:
                    builder.append(REGEX_SINGLE_WILDCARD);
                    break;
                default:
                    msStringBuilder.append(builder, REGEX_UNICODE_DESIGNATOR);
                    msStringBuilder.append(builder, FormatterPal.intToStrX4(c));
                    break;
            }
            loopCount++;
            if (needLeaveLoop)
                break;
        }

        msStringBuilder.append(builder, REGEX_STRING_END_DESIGNATOR);
        return builder.toString();
    }

    private boolean compareDoubles(double leftOperand, double rightOperand)
    {
        switch (gStringSwitchMap.of(mExpression.getComparisonOperator()))
        {
            case /*"="*/0:
                return (leftOperand == rightOperand);
            case /*"<>"*/1:
                return (leftOperand != rightOperand);
            case /*">"*/2:
                return (leftOperand > rightOperand);
            case /*"<"*/3:
                return (leftOperand < rightOperand);
            case /*">="*/4:
                return (leftOperand >= rightOperand);
            case /*"<="*/5:
                return (leftOperand <= rightOperand);
            default:
                throw new IllegalArgumentException();
        }
    }

    static boolean isOperatorValid(String comparisonOperator)
    {
        switch (gStringSwitchMap.of(comparisonOperator))
            {
                case /*"="*/0:
                case /*"<>"*/1:
                case /*">"*/2:
                case /*"<"*/3:
                case /*">="*/4:
                case /*"<="*/5:
                    return true;
                default:
                    return false;
            }
    }

    private boolean isEqualityOperator() { return "=".equals(mExpression.getComparisonOperator()); }

    private boolean isInequalityOperator() { return "<>".equals(mExpression.getComparisonOperator()); }

    private /*final*/ ComparisonExpressionBag mExpression;
    private /*final*/ FieldContext mFieldContext;

    /// <summary>
    /// Word zero or more characters wildcard.
    /// </summary>
    private static final char WORD_WILDCARD = '*';

    /// <summary>
    /// Word single character wildcard.
    /// </summary>
    private static final char WORD_SINGLE_WILDCARD = '?';

    /// <summary>
    /// Regex zero or more characters wildcard.
    /// </summary>
    private static final String REGEX_WILDCARD = ".*";

    /// <summary>
    /// Regex single character wildcard.
    /// </summary>
    private static final char REGEX_SINGLE_WILDCARD = '.';

    /// <summary>
    /// Regex unicode character code designator.
    /// </summary>
    private static final String REGEX_UNICODE_DESIGNATOR = "\\u";

    /// <summary>
    /// Regex "must occur on string start" assertion.
    /// </summary>
    private static final String REGEX_STRING_START_DESIGNATOR = "\\A";

    /// <summary>
    /// Regex "must occur on string end" assertion.
    /// </summary>
    private static final String REGEX_STRING_END_DESIGNATOR = "\\z";

    private static class ComparisonExpressionBag
    {
        ComparisonExpressionBag(IComparisonExpression arg) throws Exception
        {
            FieldArgument leftArgument = arg.getLeftExpressionArgument();
            if (leftArgument != null)
            {
                mLeftExpression = leftArgument.getNormalizedText(true);
                mIsLeftExpressionInQuotationMarks = isInQuotationMarks(
                    arg.getLeftExpression(),
                    arg.getLeftExpressionArgument());
            }
            else
            {
                // Left expression argument may be missed, i.e. IF = "right" "true" "false".
                mLeftExpression = arg.getLeftExpression();
            }

            mComparisonOperator = arg.getComparisonOperator();

            FieldArgument rightArgument = arg.getRightExpressionArgument();
            if (rightArgument != null)
            {
                mRightExpression = rightArgument.getNormalizedText(true);
                mIsRightExpressionInQuotationMarks = isInQuotationMarks(
                    arg.getRightExpression(),
                    arg.getRightExpressionArgument());
            }
            else
            {
                // Left expression argument may be missed, i.e. IF "right" "true" "false".
                mRightExpression = arg.getRightExpression();
            }
        }

        private static boolean isInQuotationMarks(String expression, FieldArgument argument)
        {
            if (!WordUtil.isInQuotationMarks(expression))
                return false;

            if (FieldUtil.endsWithFieldEnd(argument.getRange()))
                return false;

            return true;
        }

        String getLeftExpression() { return mLeftExpression; };

        private String mLeftExpression;
        boolean isLeftExpressionInQuotationMarks() { return mIsLeftExpressionInQuotationMarks; };

        private boolean mIsLeftExpressionInQuotationMarks;
        String getComparisonOperator() { return mComparisonOperator; };

        private String mComparisonOperator;
        String getRightExpression() { return mRightExpression; };

        private String mRightExpression;
        boolean isRightExpressionInQuotationMarks() { return mIsRightExpressionInQuotationMarks; };

        private boolean mIsRightExpressionInQuotationMarks;

    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"=",
		"<>",
		">",
		"<",
		">=",
		"<="
	);

}

