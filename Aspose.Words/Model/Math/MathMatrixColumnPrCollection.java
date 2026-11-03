// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// In Office Math a matrix object has collection of column properties.
/// This collection provide access to individual properties of each column <see cref="MathMatrixColumnPr"/>.
/// </summary>
class MathMatrixColumnPrCollection
{
    MathMatrixColumnPrCollection deepClone()
    {
        MathMatrixColumnPrCollection lhs = new MathMatrixColumnPrCollection();    
        
        for (int i = 0; i < getCount(); i++)
            lhs.add(this.get(i).deepClone());
        
        return lhs;
    }
    
    void add(MathMatrixColumnPr matrixColumnPr)
    {
        add(matrixColumnPr, 1);
    }
    
    void add(MathMatrixColumnPr matrixColumnPr, int count)
    {
        for (int i = 0; i < count; i++)
            msArrayList.add(mChildren, matrixColumnPr.deepClone());
    }
    
    void removeAt(int index)
    {
        mChildren.remove(index);
    }
    
    void removeFromTheEnd()
    {
        removeAt(getCount() - 1);
    }
    
    /// <summary>
    /// Checks that number of columns (<see cref="Count"/>) in this collection 
    /// matches the number passed as nColumns parameter.
    /// - If the number is greater than parameter, then removes some of the columnPr items starting from the end. 
    /// - If the number is smaller than parameter, then populates collection with some default columnPr, starting from the end.
    /// </summary>
    void validateCollection(int nColumns)
    {
        int difference = getCount() - nColumns;
        if (difference > 0)
        {
            for (int i = 0; i < difference; i++)
                removeFromTheEnd();
        }
        else if (difference < 0)
        {
            for (int i = difference; i < 0; i++)
                add(new MathMatrixColumnPr());
        }
    }
    
    MathMatrixColumnPr get(int i) { return mChildren.get(i); }
    
    int getCount() { return mChildren.size(); }
    
    private /*final*/ ArrayList<MathMatrixColumnPr> mChildren = new ArrayList<MathMatrixColumnPr>();
}

