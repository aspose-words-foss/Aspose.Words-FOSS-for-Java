// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 05.11.2013 by Konstantin Sidorenko

package com.aspose.ms.System.Collections;

import java.util.Iterator;
import java.util.Stack;

/**
 * .Net System.Collections.Stack is almost equal to java.util.Stack.
 * The main ugly difference - iteration in java is performed in reverse order.
 * This class reverses the iteration back in .Net order.
 */
public class msStack<E> extends Stack<E>
{
    public msStack()
    {
        super();
    }

    public msStack(Iterable<E> collection)
    {
        super();
        for(E element: collection)
        {
            this.push(element);
        }
    }

    public int getCount()
    {
        return size();
    }

    public Iterator<E> iterator()
    {
        return new ReverseIterator(this);
    }

    public class ReverseIterator implements Iterator<E>
    {
        public ReverseIterator(Stack<E> stack)
        {
            mStack = stack;
            mCurrent = mStack.size();
        }

        public boolean hasNext()
        {
            return mCurrent > 0;
        }

        public E next()
        {
            return mStack.elementAt(--mCurrent);
        }

        public void remove()
        {
            mStack.remove(mCurrent);
        }

        private Stack<E> mStack;
        private int mCurrent;
    }
}
