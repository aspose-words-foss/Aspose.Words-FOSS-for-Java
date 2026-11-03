// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Iterator;
import com.aspose.words.ref.Ref;


/// <summary>
/// Designates a one-dimension area of table cells.
/// </summary>
class OneDimensionCellRange implements ICellRange
{
    private OneDimensionCellRange(CellPosition referencePosition, /*CellEnumerationDirection*/int direction)
    {
        mReferencePosition = referencePosition;
        mDirection = direction;
    }

    /// <summary>
    /// Attempts to parse the specified string to a <see cref="OneDimensionCellRange"/> object.
    /// Returns an erroneous constant if there was a problem during parse.
    /// </summary>
    static Constant tryParse(FieldContext context, String name, /*out*/Ref<ICellRange> range)
    {
        range.set(null);

        /*CellEnumerationDirection*/int direction;
        switch (gStringSwitchMap.of(name.toUpperCase()))
        {
            // Word reacts on these words regardless of the culture set.
            // English
            // German
            // Spanish
            case /*"ABOVE"*/0:
            case /*"ÜBER"*/1:
            case /*"ENCIMA"*/2:
                direction = CellEnumerationDirection.UP;
                break;
            case /*"LEFT"*/3:
            case /*"LINKS"*/4:
            case /*"IZQUIERDA"*/5:
                direction = CellEnumerationDirection.LEFT;
                break;
            case /*"BELOW"*/6:
            case /*"UNTER"*/7:
            case /*"DEBAJO"*/8:
                direction = CellEnumerationDirection.DOWN;
                break;
            case /*"RIGHT"*/9:
            case /*"RECHTS"*/10:
            case /*"DERECHA"*/11:
                direction = CellEnumerationDirection.RIGHT;
                break;
            default:
                // Failed to parse.
                return null;
        }

        if (context.getParentCell() == null)
            return new ErrorConstant("!The Formula Not In Table");

        CellPosition referencePosition = new CellPosition(context.getParentCell());

        switch (direction)
        {
            case CellEnumerationDirection.UP:
                if (referencePosition.isAtFirstRow())
                    return new ErrorConstant("!Table Index Cannot be Zero");
                break;
            case CellEnumerationDirection.DOWN:
                if (referencePosition.isAtLastRow())
                    return new DoubleConstant(0d);
                break;
            case CellEnumerationDirection.LEFT:
                if (referencePosition.isAtFirstColumn())
                    return new ErrorConstant("!Table Index Cannot be Zero");
                break;
            case CellEnumerationDirection.RIGHT:
                if (referencePosition.isAtLastColumn())
                    return new DoubleConstant(0d);
                break;
            default:
                throw new IllegalArgumentException();
        }

        range.set(new OneDimensionCellRange(referencePosition, direction));
        return null;
    }

    public Iterator<Cell> iterator()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	return OneDimensionCellRangeEnumerator.createInstance(this);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public boolean /*ICellRange.*/isRectangular() { return false; }

    public boolean /*ICellRange.*/isOneCell() { return false; }

    public CellPosition /*ICellRange.*/getStart() { return null; }

    public CellPosition /*ICellRange.*/getEnd() { return null; }

    public Constant /*ICellRange.*/getEmptyCellValue()
    {
        switch (getOrientation())
        {
            case CellEnumerationOrientation.HORIZONTAL:
                return NullConstant.INSTANCE;
            case CellEnumerationOrientation.VERTICAL:
                return new DoubleConstant(0d);
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean /*ICellRange.*/getAlwaysEvaluateCellText() { return false; }

    CellPosition getReferencePosition() { return mReferencePosition; };

    private CellPosition mReferencePosition;

    /*CellEnumerationDirection*/int getDirection() { return mDirection; };

    private /*CellEnumerationDirection*/int mDirection;

    private /*CellEnumerationOrientation*/int getOrientation()
    {
        switch (getDirection())
        {
            case CellEnumerationDirection.UP:
            case CellEnumerationDirection.DOWN:
                return CellEnumerationOrientation.VERTICAL;
            case CellEnumerationDirection.LEFT:
            case CellEnumerationDirection.RIGHT:
                return CellEnumerationOrientation.HORIZONTAL;
            default:
                throw new IllegalArgumentException();
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ABOVE",
		"ÜBER",
		"ENCIMA",
		"LEFT",
		"LINKS",
		"IZQUIERDA",
		"BELOW",
		"UNTER",
		"DEBAJO",
		"RIGHT",
		"RECHTS",
		"DERECHA"
	);

}

