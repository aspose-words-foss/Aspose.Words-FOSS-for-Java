// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements the IF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Compares the values designated by the expressions <see cref="LeftExpression"/> and <see cref="RightExpression"/>
/// in comparison using the operator designated by <see cref="ComparisonOperator"/>.</p>
/// <p>A field in the following format will be used as a mail merge source: { IF 0 = 0 "{PatientsNameFML}" "" \* MERGEFORMAT }</p>
/// </remarks>
public class FieldIf extends Field implements IMergeFieldSurrogate
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        ensureComparisonResult();

        FieldUpdateAction action = !mComparisonResult.isError()
            ? (FieldUpdateAction)new FieldUpdateActionApplyResult(this, getResultArgument())
            : new FieldUpdateActionInsertErrorMessage(this, mComparisonResult.getValueString());

        invalidateComparisonResult(); // Comparison result should not be used on next update if any.

        return action;
    }

    /*override*/ /*FieldUpdateStrategy*/int getChildFieldsUpdateStrategyInArgument(IFieldArgument argument) throws Exception
    {
        switch (getUpdateContext().getChildUpdateStage())
        {
            case FieldChildUpdateStage.PERMANENT:
                return (argument != getTrueResultArgument()) && (argument != getFalseResultArgument())
                    ? FieldUpdateStrategy.UPDATE
                    : FieldUpdateStrategy.DEFER;

            case FieldChildUpdateStage.CONDITIONAL:
                if ((argument != getTrueResultArgument()) && (argument != getFalseResultArgument()))
                    return FieldUpdateStrategy.DEFER;

                ensureComparisonResult();

                return !mComparisonResult.isError() && (argument == getResultArgument())
                    ? FieldUpdateStrategy.UPDATE
                    : FieldUpdateStrategy.REJECT;

            default:
                throw new IllegalArgumentException();
        }
    }

    /*override*/ void notifyChildFieldUpdated(IFieldArgument argument) throws Exception
    {
        FieldIfComparisonExpression comparisonExpression = getComparisonExpression();
        if ((argument == comparisonExpression.getLeftExpressionArgument()) || (argument == comparisonExpression.getRightExpressionArgument()))
            invalidateComparisonResult();

        super.notifyChildFieldUpdated(argument);
    }

    /*override*/ void parseFieldCode()
    {
        // WORDSNET-12193 Ensures separator before parse field code.
        ensureSeparator(true);

        super.parseFieldCode();
    }

    private void ensureComparisonResult() throws Exception
    {
        if (mComparisonResult != null)
            return;

        mComparisonResult = evaluateComparisonResult();
    }

    private void invalidateComparisonResult()
    {
        mComparisonResult = null;
    }

    private Constant evaluateComparisonResult() throws Exception
    {
        IComparisonExpression comparison = getComparisonExpression();
        return ComparisonEvaluator.evaluate(this, comparison);
    }

    private FieldIfComparisonExpression getComparisonExpression()
    {
        return new FieldIfComparisonExpression(getFieldCodeCache());
    }

    @ReservedForInternalUse @Deprecated
    public String /*IMergeFieldSurrogate.*/getMergeFieldName()
    {
        String fieldName = getTrueResultArgument().getNormalizedText();
        return msString.trimEnd(msString.trimStart(WordUtil.trimDoubleQuotes(fieldName), '{'), '}');
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/canWorkAsMergeField()
    {
        return false;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/isMergeValueRequired()
    {
        return false;
    }

    /*override*/ boolean getSupportsConditionalUpdate() { return !isInHeaderFooter(); }

    private FieldArgument getResultArgument()
    {
        Debug.assertTrue(!mComparisonResult.isError());

        return mComparisonResult.getValueBoolean() ? getTrueResultArgument() : getFalseResultArgument();
    }

    FieldArgument getTrueResultArgument()
    {
        FieldIfComparisonExpression comparisonExpression = getComparisonExpression();
        return comparisonExpression.getTrueResultArgument();
    }

    FieldArgument getFalseResultArgument()
    {
        FieldIfComparisonExpression comparisonExpression = getComparisonExpression();
        return comparisonExpression.getFalseResultArgument();
    }

    /// <summary>
    /// Gets or sets the left part of the comparison expression.
    /// </summary>
    public String getLeftExpression() { return getFieldCodeCache().getArgumentAsString(LEFT_EXPRESSION_ARGUMENT_INDEX, false); }
    public void setLeftExpression(String value) throws Exception { getFieldCodeCache().setArgument(LEFT_EXPRESSION_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the comparison operator.
    /// </summary>
    public String getComparisonOperator() { return getFieldCodeCache().getArgumentAsString(COMPARISON_OPERATOR_ARGUMENT_INDEX); }
    public void setComparisonOperator(String value) throws Exception { getFieldCodeCache().setArgument(COMPARISON_OPERATOR_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the right part of the comparison expression.
    /// </summary>
    public String getRightExpression() { return getFieldCodeCache().getArgumentAsString(RIGHT_EXPRESSION_ARGUMENT_INDEX, false); }
    public void setRightExpression(String value) throws Exception { getFieldCodeCache().setArgument(RIGHT_EXPRESSION_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the text displayed if the comparison expression is true.
    /// </summary>
    public String getTrueText() { return getFieldCodeCache().getArgumentAsString(TRUE_TEXT_ARGUMENT_INDEX); }
    public void setTrueText(String value) throws Exception { getFieldCodeCache().setArgument(TRUE_TEXT_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the text displayed if the comparison expression is <c>false</c>.
    /// </summary>
    public String getFalseText() { return getFieldCodeCache().getArgumentAsString(FALSE_TEXT_ARGUMENT_INDEX); }
    public void setFalseText(String value) throws Exception { getFieldCodeCache().setArgument(FALSE_TEXT_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Evaluates the condition.
    /// </summary>
    /// <returns>
    /// A <see cref="FieldIfComparisonResult"/> value that represents the result of the condition evaluation.
    /// </returns>
    public /*FieldIfComparisonResult*/int evaluateCondition() throws Exception
    {
        Constant result = evaluateComparisonResult();
        if (result.isError())
            return FieldIfComparisonResult.ERROR;
        return result.getValueBoolean()
            ? FieldIfComparisonResult.TRUE
            : FieldIfComparisonResult.FALSE;
    }

    /*override*/ INodeModifier getResultModifier()
    {
        return new FieldIfHyperlinkFormattingModifier(fetchDocument());
    }

    /// <summary>
    /// Represents result of comparison.
    /// </summary>
    private Constant mComparisonResult;
    /// <summary>
    /// Index of true result argument at field code.
    /// </summary>
    private static final int TRUE_RESULT_ARGUMENT_INDEX = 3;
    /// <summary>
    /// Index of false result argument at field code.
    /// </summary>
    private static final int FALSE_RESULT_ARGUMENT_INDEX = 4;

    private static final int LEFT_EXPRESSION_ARGUMENT_INDEX = 0;
    private static final int COMPARISON_OPERATOR_ARGUMENT_INDEX = 1;
    private static final int RIGHT_EXPRESSION_ARGUMENT_INDEX = 2;
    private static final int TRUE_TEXT_ARGUMENT_INDEX = 3;
    private static final int FALSE_TEXT_ARGUMENT_INDEX = 4;

    private static class FieldIfComparisonExpression implements IComparisonExpression
    {
        FieldIfComparisonExpression(FieldCode fieldCode)
        {
            FieldIfArgumentsBundle defaultArguments = new FieldIfArgumentsBundle(
                fieldCode.getArgument(LEFT_EXPRESSION_ARGUMENT_INDEX),
                fieldCode.getArgumentAsString(LEFT_EXPRESSION_ARGUMENT_INDEX, false),
                fieldCode.getArgumentAsString(COMPARISON_OPERATOR_ARGUMENT_INDEX, false),
                fieldCode.getArgument(RIGHT_EXPRESSION_ARGUMENT_INDEX),
                fieldCode.getArgumentAsString(RIGHT_EXPRESSION_ARGUMENT_INDEX, false),
                fieldCode.getArgument(TRUE_RESULT_ARGUMENT_INDEX),
                fieldCode.getArgument(FALSE_RESULT_ARGUMENT_INDEX));

            mArguments = ((tryMissedLeftExpression(fieldCode, defaultArguments) != null ? tryMissedLeftExpression(fieldCode, defaultArguments) : tryMissedOperatorAndRightExpression(fieldCode, defaultArguments)) != null ? (tryMissedLeftExpression(fieldCode, defaultArguments) != null ? tryMissedLeftExpression(fieldCode, defaultArguments) : tryMissedOperatorAndRightExpression(fieldCode, defaultArguments)) : defaultArguments);
        }

        public FieldArgument getLeftExpressionArgument() { return mArguments.getLeftExpressionArgument(); }

        public String getLeftExpression() { return mArguments.getLeftExpression(); }

        public String getComparisonOperator() { return mArguments.getComparisonOperator(); }

        public FieldArgument getRightExpressionArgument() { return mArguments.getRightExpressionArgument(); }

        public String getRightExpression() { return mArguments.getRightExpression(); }

        FieldArgument getTrueResultArgument() { return mArguments.getTrueResultArgument(); }

        FieldArgument getFalseResultArgument() { return mArguments.getFalseResultArgument(); }

        /// <summary>
        /// FieldIf with missed left expression (IF = "Right" "True Text (optional)" "False Text (optional)") update to (IF "" = "" "Right" "Right")
        /// </summary>
        private static FieldIfArgumentsBundle tryMissedLeftExpression(FieldCode fieldCode, FieldIfArgumentsBundle defaultArguments)
        {
            if (fieldCode.getArguments().size() < 2)
                return null;

            if (ComparisonEvaluator.isOperatorValid(defaultArguments.getComparisonOperator()))
                return null;

            if (!ComparisonEvaluator.isOperatorValid(defaultArguments.getLeftExpression()))
                return null;

            return new FieldIfArgumentsBundle(
                null,
                "",
                "=",
                null,
                "",
                fieldCode.getArgument(1),
                fieldCode.getArgument(1));
        }

        /// <summary>
        /// FieldIf with missed operator and right expression (IF "Right" "True Text" "False Text (optional)") update to (IF "" = "" "Right" "Right")
        /// </summary>
        private static FieldIfArgumentsBundle tryMissedOperatorAndRightExpression(FieldCode fieldCode, FieldIfArgumentsBundle defaultArguments)
        {
            if (fieldCode.getArguments().size() < 2)
                return null;

            if (ComparisonEvaluator.isOperatorValid(defaultArguments.getComparisonOperator()))
                return null;

            if (G_MATCH_NUMBER.isMatch(defaultArguments.getLeftExpression()))
                return null;

            return new FieldIfArgumentsBundle(
                null,
                "",
                "=",
                null,
                "",
                fieldCode.getArgument(1),
                fieldCode.getArgument(1));
        }

        private /*final*/ FieldIfArgumentsBundle mArguments;

        private static /*final*/ Regex G_MATCH_NUMBER = new Regex("^[0-9]+$", RegexOptions.COMPILED);

        private static class FieldIfArgumentsBundle
        {
            FieldIfArgumentsBundle(
                FieldArgument leftExpressionArgument,
                String leftExpression,
                String comparisonOperator,
                FieldArgument rightExpressionArgument,
                String rightExpression,
                FieldArgument trueResultArgument,
                FieldArgument falseResultArgument)
            {
                mLeftExpression = leftExpression;
                mComparisonOperator = comparisonOperator;
                mRightExpression = rightExpression;
                mTrueResultArgument = trueResultArgument;
                mFalseResultArgument = falseResultArgument;
                mLeftExpressionArgument = leftExpressionArgument;
                mRightExpressionArgument = rightExpressionArgument;
            }

            FieldArgument getLeftExpressionArgument() { return mLeftExpressionArgument; };

            private FieldArgument mLeftExpressionArgument;

            String getLeftExpression() { return mLeftExpression; };

            private String mLeftExpression;

            String getComparisonOperator() { return mComparisonOperator; };

            private String mComparisonOperator;

            FieldArgument getRightExpressionArgument() { return mRightExpressionArgument; };

            private FieldArgument mRightExpressionArgument;

            String getRightExpression() { return mRightExpression; };

            private String mRightExpression;

            FieldArgument getTrueResultArgument() { return mTrueResultArgument; };

            private FieldArgument mTrueResultArgument;

            FieldArgument getFalseResultArgument() { return mFalseResultArgument; };

            private FieldArgument mFalseResultArgument;
        }
    }
}

