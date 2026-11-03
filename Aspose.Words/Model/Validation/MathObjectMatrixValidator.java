// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/03/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ObjToIntDictionary;
import com.aspose.Debug;


/// <summary>
/// Performs validation of Office Math element m:m (matrix element) by:
/// - making number of columns in <see cref="MathMatrixColumnPrCollection"/> equal to max number of row elements.
/// - adding empty Arguments to the rows that contain less than max number of elements, thus making
///   each row to contain equal number of arguments.
/// </summary>
class MathObjectMatrixValidator
{
    MathObjectMatrixValidator(boolean reconstructMissingArguments, DocumentBase doc)
    {
        mDoc = doc;
        mReconstructMissingArguments = reconstructMissingArguments;
        if (mReconstructMissingArguments)
            mMatrixRowsCache = new ObjToIntDictionary<OfficeMath>();
    }
    
    static boolean needToValidateNode(OfficeMath officeMath)
    {
        /*MathObjectType*/int type = officeMath.getMathObject().getMathObjectType();
        return ((type == MathObjectType.MATRIX_ROW) || (type == MathObjectType.ARGUMENT));
    }

    /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath)
    {
        switch (mStatus)
        {
            case ValidatorStatus.INSIDE_MATRIX:
                {
                    if (officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX_ROW)
                    {
                        mStatus = ValidatorStatus.INSIDE_ROW;
                        mColumnCount = 0;
                    }
                    break;
                }
            case ValidatorStatus.INSIDE_ROW:
                {
                    if (officeMath.getMathObject().getMathObjectType() == MathObjectType.ARGUMENT)
                    {
                        mColumnCount++;
                        mArgumentCounter = 1; // first argument belongs to matrix cell itself.
                        mStatus = ValidatorStatus.INSIDE_CELL;
                    }
                    break;
                }
            case ValidatorStatus.INSIDE_CELL:
                    {
                        if (officeMath.getMathObject().getMathObjectType() == MathObjectType.ARGUMENT)
                        {
                            // while parsing throught the cell content, we've found one more nested argument
                            mArgumentCounter++;
                        }
                        break;
                    }
            default:
                {
                    throw new IllegalStateException("Please report exception");
                }        
        }
        
        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitOfficeMathEnd(OfficeMath officeMath)
    {
        switch (mStatus)
        {
            case ValidatorStatus.INSIDE_MATRIX:
                {                
                    // processed all rows, leaving cur nested matrix.
                    if (officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX)
                    {
                        // now when we know number of columns per se, pass it to ColumnPrCollection for validation.
                        ((MathObjectMatrix)officeMath.getMathObject()).getColumnPrCollection().validateCollection(mMaxColumns);

                        if (mReconstructMissingArguments)
                            createMissingElements();
                    }
                    break;
                }
            case ValidatorStatus.INSIDE_ROW:
                {
                    if (officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX_ROW)
                    {
                        mStatus = ValidatorStatus.INSIDE_MATRIX;
                        if (mMaxColumns < mColumnCount)
                            mMaxColumns = mColumnCount;

                        if (mReconstructMissingArguments)
                            mMatrixRowsCache.add(officeMath, mColumnCount);
                    }
                    break;
                }
            case ValidatorStatus.INSIDE_CELL:
                {
                    if (officeMath.getMathObject().getMathObjectType() == MathObjectType.ARGUMENT)
                    {
                        mArgumentCounter--; // we've returned from nested level of one more argument.
                        
                        if (mArgumentCounter == 0) // means we have come to the original argument, denoting the cur cell.
                            mStatus = ValidatorStatus.INSIDE_ROW;
                    }
                    break;
                }
            default:
                {
                    throw new IllegalStateException("Please report exception");
                }        
        }
         
        return VisitorAction.CONTINUE;
    }
    
    /// <summary>
    /// Populate rows with empty arguments if rows have too few elements.
    /// </summary>
    private void createMissingElements()
    {
        ObjToIntDictionary<OfficeMath>.Enumerator enumerator = mMatrixRowsCache.iterator();
        while (enumerator.moveNext())
        {
            OfficeMath officeMath = enumerator.getCurrentKey();
            int columnsToAdd = mMaxColumns - enumerator.getCurrentValue();

            for (int iColumn = 0; iColumn < columnsToAdd; iColumn++)
            {
                Debug.assertTrue(officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX_ROW);
                
                // mimic MS Word behavior, which prepends empty arguments in case if matrix has rows with fewer els than needed.
                officeMath.prependChild(new OfficeMath(mDoc, new MathObjectArgumentBase(MathObjectType.ARGUMENT)));
            }
        }
    }
    
    private /*enum*/ final class ValidatorStatus
    {
        private ValidatorStatus(){}
        
        public static final int INSIDE_MATRIX = 0;
        
        public static final int INSIDE_ROW = 1;
        
        public static final int INSIDE_CELL = 2;

        public static final int length = 3;
    }
    
    private /*ValidatorStatus*/int mStatus = ValidatorStatus.INSIDE_MATRIX;
    
    /// <summary>
    /// Count of columns for the current row
    /// </summary>
    private int mColumnCount;

    /// <summary>
    /// We can have multiple arguments inside a matrix cell, and only topmost one means that we on cell level. So whenever
    /// cell contains complex content with multiple n of arguments, we keep track of their nestedness by using this counter.
    /// </summary>
    private int mArgumentCounter;
    
    /// <summary>
    /// Number of columns in matrix. We are assuming that every row can contain variable n of elements, so we use max n.
    /// </summary>
    private int mMaxColumns;

    /// <summary>
    /// If set to true, then this validator will insert empty arguments to the rows that have number of argument smaller than
    /// maximum number of columns across the table. This means that all rows of the table will containt the same n of elements.
    /// </summary>
    private /*final*/ boolean mReconstructMissingArguments;

    /// <summary>
    /// Every node that we pass will be stored here along with number of elements it contains.
    /// </summary>
    private /*final*/ ObjToIntDictionary<OfficeMath> mMatrixRowsCache;

    /// <summary>
    /// will need this when we insert empty arguments into the matrix.
    /// </summary>
    private /*final*/ DocumentBase mDoc;
}

