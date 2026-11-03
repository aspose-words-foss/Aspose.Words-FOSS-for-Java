// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents a reference to a table cell.
/// </summary>
class CellReference implements IExecutionItem
{
    private CellReference(ICellRange range, FieldContext context, boolean isAggregateFunctionParameter)
    {
        mRange = range;
        mContext = context;
        mIsAggregateFunctionParameter = isAggregateFunctionParameter;
    }

    static IExecutionItem tryParse(FieldContext context, String referenceName) throws Exception
    {
        IExecutionItem reference = tryParseAsOneDimensionRange(context, referenceName);
        return reference != null
            ? reference
            : tryParseAsRectangularRange(context, referenceName, true);
    }

    /// <summary>
    /// Attempts to parse a cell reference represented by a one dimension range (ABOVE, BELOW, LEFT or RIGHT).
    /// </summary>
    /// <param name="context"></param>
    /// <param name="referenceName"></param>
    /// <returns></returns>
    private static IExecutionItem tryParseAsOneDimensionRange(FieldContext context, String referenceName)
    {
        ICellRange range = null;

        Ref<ICellRange> referenceToRange = new Ref<ICellRange>(range);
        Constant result = OneDimensionCellRange.tryParse(context, referenceName, /*out*/ referenceToRange);
        range = referenceToRange.get();
        if (result != null)
            return result;

        return range != null
            ? new CellReference(range, context, false)
            : null;
    }

    /// <summary>
    /// Attempts to parse a cell reference represented by a rectangular range (A1, A1:B2, C:C, 3:3 etc).
    /// </summary>
    static IExecutionItem tryParseAsRectangularRange(
        FieldContext context,
        String referenceName,
        boolean isAggregateFunctionParameter) throws Exception
    {
        ICellRange range = null;

        Ref<ICellRange> referenceToRange = new Ref<ICellRange>(range);
        Constant result = RectangularCellRange.tryParse(context, referenceName, isAggregateFunctionParameter, /*out*/ referenceToRange);
        range = referenceToRange.get();
        if (result != null)
            return result;

        return range != null
            ? new CellReference(range, context, isAggregateFunctionParameter)
            : null;
    }

    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack) throws Exception
    {
        return mRange.isOneCell()
            ? evaluateSingleCell()
            : evaluateMultipleCells();
    }

    private Constant evaluateSingleCell() throws Exception
    {
        if (mRange.getStart().getCell() != null)
        {
            DoubleConstant cellValue = getCellValue(mRange.getStart().getCell(), true).getValue();
            if (cellValue != null)
                return cellValue;

            return mIsAggregateFunctionParameter
                ? (Constant)NullConstant.INSTANCE
                : new DoubleConstant(0d);
        }

        if (mIsAggregateFunctionParameter)
            return NullCellReference.INSTANCE;

        return new ErrorConstant(msString.format("!{0} Is Not In Table", mRange.getStart()));
    }


    private Constant evaluateMultipleCells() throws Exception
    {
        EvaluationState state = new EvaluationState();
        AggregateConstant result = new AggregateConstant();

        for (Cell cell : mRange)
        {
            if (!tryEvaluateCellInRange(cell, result, state))
                break;
        }

        return result;
    }

    private boolean tryEvaluateCellInRange(Cell cell, AggregateConstant result, EvaluationState state) throws Exception
    {
        CellValue cellValue = getCellValue(cell, !state.isParsedConstantReached());
        state.isParsedConstantReached(state.isParsedConstantReached() || cellValue.isParsedConstant());

        if (cellValue.getValue() != null)
        {
            if (!state.isStarted())
            {
                result.getValues().add(cellValue.getValue());

                state.isStarted(true);
                state.startsWithEndNonDigits(cellValue.getValue().hasEndNonDigits());
                state.setPreviousValueHasAllDigits(!state.startsWithEndNonDigits());

                return true;
            }

            if (cellValue.getValue().hasEndNonDigits())
            {
                if (state.getPreviousValueHasAllDigits())
                    return false;

                result.getValues().add(cellValue.getValue());
            }
            else
            {
                result.getValues().add(cellValue.getValue());

                state.setPreviousValueHasAllDigits(true);
            }
        }
        else if (!mRange.isRectangular())
        {
            if (!state.isStarted() || state.startsWithEndNonDigits())
                result.getValues().add(mRange.getEmptyCellValue());
            else
                return false;
        }

        return true;
    }

    private CellValue getCellValue(Cell cell, boolean evaluateCellText) throws Exception
    {
        if (!cell.hasChildNodes())
            return CellValue.EMPTY;

        StringBuilder builder = new StringBuilder();
        NodeCollection cellParagraphs = cell.getChildNodes(NodeType.PARAGRAPH, true);
        for (Paragraph paragraph : (Iterable<Paragraph>) cellParagraphs)
        {
            if (!paragraph.hasChildNodes())
                continue;

            String paragraphText = NodeTextCollector.getText(paragraph.getFirstChild(), true, paragraph.getLastChild(), true, true);
            msStringBuilder.append(builder, paragraphText).append(' ');
        }

        String cellText = builder.toString();

        DoubleConstant cellValue = DoubleConstant.tryParse(cellText);
        if (cellValue != null)
            return new CellValue(cellValue, true);

        if (evaluateCellText || mRange.getAlwaysEvaluateCellText())
            cellValue = ExpressionEvaluator.evaluateReferenceExpression(mContext, cellText);

        return new CellValue(cellValue, false);
    }

    private /*final*/ ICellRange mRange;
    private /*final*/ FieldContext mContext;
    private /*final*/ boolean mIsAggregateFunctionParameter;

    private static class CellValue
    {
        static /*final*/ CellValue EMPTY = new CellValue(null, false);

        CellValue(DoubleConstant value, boolean isParsedConstant)
        {
            mValue = value;
            mIsParsedConstant = isParsedConstant;
        }

        DoubleConstant getValue() { return mValue; };

        private DoubleConstant mValue;

        boolean isParsedConstant() { return mIsParsedConstant; };

        private boolean mIsParsedConstant;
    }

    private static class EvaluationState
    {
        boolean isStarted() { return mIsStarted; }; void isStarted(boolean value) { mIsStarted = value; };

        private boolean mIsStarted;
        boolean startsWithEndNonDigits() { return mStartsWithEndNonDigits; }; void startsWithEndNonDigits(boolean value) { mStartsWithEndNonDigits = value; };

        private boolean mStartsWithEndNonDigits;
        boolean getPreviousValueHasAllDigits() { return mPreviousValueHasAllDigits; }; void setPreviousValueHasAllDigits(boolean value) { mPreviousValueHasAllDigits = value; };

        private boolean mPreviousValueHasAllDigits;
        boolean isParsedConstantReached() { return mIsParsedConstantReached; }; void isParsedConstantReached(boolean value) { mIsParsedConstantReached = value; };

        private boolean mIsParsedConstantReached;
    }
}

