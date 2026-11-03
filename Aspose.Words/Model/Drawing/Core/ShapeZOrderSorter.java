// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.ms.System.msInt;


/// <summary>
/// Compares shapes by their z-order. Note, that only top level shapes have ZOrder.
/// </summary>
class ShapeZOrderSorter<T extends IShape> implements Comparator<T>
{
    static <T extends IShape> void sort(ArrayList<T> zOrderList)
    {
        ArrayList<IShape> originalOrderClone = new ArrayList<IShape>();
        for (T shape : zOrderList)
            msArrayList.add(originalOrderClone, shape);
        Collections.sort(zOrderList, new ShapeZOrderSorter<T>(originalOrderClone));
    }

    private ShapeZOrderSorter(ArrayList<IShape> originalOrder)
    {
        mOriginalOrder = originalOrder;
    }

    public int compare(T x, T y)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// WORDSCPP-2248 C++ error (cannot access ZOrder_IShape property)
        	int zorder1 = x.getZOrder_IShape();
        	int zorder2 = y.getZOrder_IShape();
        	
        	int result = msInt.compareTo(zorder1, zorder2);

        	// RK In .NET 1.1 (and also in Java) the Array.Sort algorithm places objects with equal keys differently.
        	// This is not good for gold files.

        	// In case of equal z-order shapes should be sorted by their appearance.
        	// AM. I think best way is compare shape indexes in the original array because
        	// they are added in order by appearance.
        	if (result == 0)
        	{
            	int index1 = mOriginalOrder.indexOf(x);
            	int index2 = mOriginalOrder.indexOf(y);

            	result = msInt.compareTo(index1, index2);
        	}

        	return result;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private /*final*/ ArrayList<IShape> mOriginalOrder;

}

