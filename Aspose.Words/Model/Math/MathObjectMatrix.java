// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Matrix object, consisting of one or more elements laid out in one or more rows and
/// one or more columns. 
/// </summary>
/// <remarks>
/// Matrices do not have built in delimiters. Like other math elements, matrices can be contained in a delimiter object 
/// <see cref="MathObjectDelimiter"/> when delimiters are desired. 
/// </remarks>
class MathObjectMatrix extends MathObject
{
    /// <summary>
    /// Can only host element m:mr (math matrix row).
    /// </summary>
    /*override*/ boolean canInsert(Node node)
    {
        return ((node.getNodeType() == NodeType.OFFICE_MATH) && 
                ((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.MATRIX_ROW);
    }

    /*override*/ AttrCollection cloneCore()
    {
        MathObjectMatrix lhs = (MathObjectMatrix)super.cloneCore();
        lhs.mColumnPrCollection = mColumnPrCollection.deepClone();
        return lhs;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.MATRIX; }

    /// <summary>
    /// Specifies the vertical justification of the array relative to the text outside of the array.
    /// Default: <see cref="MathBaseJustification.Default"/>
    /// </summary>
    /// <remarks>
    /// The standard does not specify in detail how to align the matrix with the surrounding content.
    /// MS Word uses baseline alignment to align the matrix with the surrounding content. 
    /// </remarks>
    /*MathBaseJustification*/int getBaseJustification() { return (/*MathBaseJustification*/Integer)fetchAttr(MathAttr.BASE_JUSTIFICATION); }
    void setBaseJustification(/*MathBaseJustification*/int value) { setAttr(MathAttr.BASE_JUSTIFICATION, value, value != MathBaseJustification.DEFAULT); }

    /// <summary>
    /// This element specifies spacing between columns of matrix; it is used only when 
    /// <see cref="ColumnSpacingRule"/> is set to <see cref="MathSpacingRule.Exactly"/> or
    /// <see cref="MathSpacingRule.Multiple"/>.
    /// Default: 0.
    /// </summary>
    int getColumnGap() { return (/*int*/Integer)fetchAttr(MathAttr.COLUMN_GAP); }
    void setColumnGap(int value) { setAttr(MathAttr.COLUMN_GAP, value, value != 0); }
    
    /// <summary>
    /// Allows to define gaps between matrix columns.
    /// Default: <see cref="MathSpacingRule.Default"/>
    /// </summary>
    /*MathSpacingRule*/int getColumnSpacingRule() { return (/*MathSpacingRule*/Integer)fetchAttr(MathAttr.COLUMN_GAP_RULE); }
    void setColumnSpacingRule(/*MathSpacingRule*/int value) { setAttr(MathAttr.COLUMN_GAP_RULE, value, value != MathSpacingRule.DEFAULT); }
    
    /// <summary>
    /// Specifies the minimum column width of a matrix. The actual column width of a matrix will be the
    /// greater of either the width of the column’s widest argument or this property value.
    /// Interpreted as twips.
    /// Default: 0
    /// </summary>
    int getMinimumColumnWidth() { return (/*int*/Integer)fetchAttr(MathAttr.MIN_COLUMN_WIDTH); }
    void setMinimumColumnWidth(int value)
    {
        if (value > CEILING_FOR_MINIMUM_COLUMN_WIDTH)
            value = CEILING_FOR_MINIMUM_COLUMN_WIDTH;
            
        setAttr(MathAttr.MIN_COLUMN_WIDTH, value, value != 0);
    }

    /// <summary>
    /// specifies the Hide Placeholders property on a matrix m. 
    /// When this property is true, placeholders do not appear in the matrix.
    /// Default: false.
    /// </summary>
    boolean isHidePlaceholders() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_HIDE_PLACEHOLDERS); }
    void isHidePlaceholders(boolean value) { setAttr(MathAttr.IS_HIDE_PLACEHOLDERS, value, value); }
    
    /// <summary>
    /// Specifies the type of vertical spacing between columns in a matrix. 
    /// Default: <see cref="MathSpacingRule.Default"/>
    /// </summary>
    /*MathSpacingRule*/int getRowSpacingRule() { return (/*MathSpacingRule*/Integer)fetchAttr(MathAttr.ROW_SPACING_RULE); }
    void setRowSpacingRule(/*MathSpacingRule*/int value) { setAttr(MathAttr.ROW_SPACING_RULE, value, value != MathSpacingRule.DEFAULT); }
    
    /// <summary>
    /// This element specifies spacing between rows of this matrix; it is used only when 
    /// <see cref="RowSpacingRule"/> is set to <see cref="MathSpacingRule.Exactly"/> or
    /// <see cref="MathSpacingRule.Multiple"/>.
    /// Default: 0.
    /// </summary>
    int getRowSpacing() { return (/*int*/Integer)fetchAttr(MathAttr.ROW_SPACING); }
    void setRowSpacing(int value) { setAttr(MathAttr.ROW_SPACING, value, value != 0); }
    
    /// <summary>
    /// Contains a number of <see cref="MathMatrixColumnPr"/> object equal to number of columns in the matrix.
    /// Provide access to individual formatting properties of each matrix column.
    /// </summary>
    MathMatrixColumnPrCollection getColumnPrCollection() { return mColumnPrCollection; }

    private MathMatrixColumnPrCollection mColumnPrCollection = new MathMatrixColumnPrCollection();


    /// <summary>
    /// Office restricts the value of Minimum Matrix Column Width attribute to be at most 31680 (22").
    /// </summary>
    private static final int CEILING_FOR_MINIMUM_COLUMN_WIDTH = 31680;

    /// <summary>
    /// Indicates whether the matrix is converted from EQ-array.
    /// </summary>
    boolean isEqArray() { return mIsEqArray; }; void isEqArray(boolean value) { mIsEqArray = value; };

    private boolean mIsEqArray;
}

