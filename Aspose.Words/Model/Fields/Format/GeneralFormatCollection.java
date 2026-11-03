// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.IntList;
import java.util.Iterator;
import com.aspose.EnumeratorWrapperPalGeneric;


/// <summary>
/// Represents a typed collection of general formats.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class GeneralFormatCollection implements Iterable<Integer>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="fieldCode"></param>
    GeneralFormatCollection(FieldCode fieldCode)
    {
        mFieldCode = fieldCode;
    }

    /// <summary>
    /// Adds a general format to the collection.
    /// </summary>
    /// <param name="item">A general format.</param>
    public void add(/*GeneralFormat*/int item) throws Exception
    {
        mFieldCode.setSwitch(FieldFormat.GENERAL_FORMAT_SWITCH, GeneralFormatUtil.generalFormatToString(item), getCount());
    }

    /// <summary>
    /// Removes all occurrences of the specified general format from the collection.
    /// </summary>
    /// <param name="item">A general format.</param>
    public void remove(/*GeneralFormat*/int item) throws Exception
    {
        IntList indices = new IntList();

        for (int i = 0; i < getCount(); i++)
        {
            if (this.get(i) == item)
                indices.add(i);
        }

        for (int i = indices.getCount() - 1; i >= 0; i--)
            removeAt(indices.get(i));
    }

    /// <summary>
    /// Removes a general format occurrence at the specified index.
    /// </summary>
    /// <param name="index"></param>
    public void removeAt(int index) throws Exception
    {
        mFieldCode.setSwitch(FieldFormat.GENERAL_FORMAT_SWITCH, null, index);
    }

    /// <summary>
    /// Gets the total number of the items in the collection.
    /// </summary>
    public int getCount()
    {
        int count = 0;

        for (/*GeneralFormat*/int format : (Iterable</*GeneralFormat*/Integer>) this)
            count++;

        return count;
    }

    /// <summary>
    /// Gets a general format at the specified index.
    /// </summary>
    /// <param name="index">The index of a general format.</param>
    /// <returns>A general format.</returns>
    public /*GeneralFormat*/int get(int index)
    {
        int switchIndex = 0;
        for (FieldSwitch fieldSwitch : mFieldCode.getSwitches())
        {
            if (isValidSwitch(fieldSwitch))
            {
                if (switchIndex == index)
                    return GeneralFormatUtil.stringToGeneralFormat(fieldSwitch.getArgument().getNormalizedText());

                switchIndex++;
            }
        }

        return GeneralFormat.NONE;
    }

    boolean hasFormat(/*GeneralFormat*/int format)
    {
        for (int i = 0; i < getCount(); i++)
        {
            if (this.get(i) == format)
                return true;
        }

        return false;
    }

    void addOrRemove(/*GeneralFormat*/int format, boolean isAdd) throws Exception
    {
        if (hasFormat(format) == isAdd)
            return;

        if (isAdd)
            add(format);
        else
            remove(format);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<Integer> iterator()
    {
        return new GeneralFormatEnumerator(mFieldCode.getSwitches().iterator());
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private static boolean isValidSwitch(FieldSwitch fieldSwitch)
    {
        return fieldSwitch.hasName(FieldFormat.GENERAL_FORMAT_SWITCH) && fieldSwitch.hasArgument();
    }

    /// <summary>
    /// Returns the first numeric format encountered.
    /// </summary>
    /// <returns></returns>
    /*GeneralFormat*/int getNumericFormat()
    {
        for (/*GeneralFormat*/int format : (Iterable</*GeneralFormat*/Integer>) this)
        {
            if (GeneralFormatUtil.isNumericFormat(format))
                return format;
        }

        return GeneralFormat.NONE;
    }

    /// <summary>
    /// Returns string representation of the first numeric format encountered.
    /// </summary>
    /// <returns></returns>
    String getNumericFormatString()
    {
        return GeneralFormatUtil.generalFormatToString(getNumericFormat());
    }

    private final static class GeneralFormatEnumerator implements Iterator<Integer>
    {
        public GeneralFormatEnumerator(Iterator<FieldSwitch> switchEnumerator)
        {
            mSwitchEnumerator = new EnumeratorWrapperPalGeneric<FieldSwitch>(switchEnumerator);
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean hasNext()
        {
            while (mSwitchEnumerator.moveNext())
            {
                FieldSwitch currentSwitch = mSwitchEnumerator.getCurrent();
                if (isValidSwitch(currentSwitch))
                    return true;
            }

            return false;
        }

        public void reset()
        {
            mSwitchEnumerator.reset();
        }

        
        public  /*GeneralFormat*/Integer next()
        {
        	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
        	try
        	{
            	FieldSwitch fieldSwitch = mSwitchEnumerator.getCurrent();
            	return GeneralFormatUtil.stringToGeneralFormat(fieldSwitch.getArgument().getNormalizedText());
        	}
        	catch (Exception e)
        	{
        		throw new RuntimeException(e);
        	}
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ EnumeratorWrapperPalGeneric<FieldSwitch> mSwitchEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private /*final*/ FieldCode mFieldCode;
}

