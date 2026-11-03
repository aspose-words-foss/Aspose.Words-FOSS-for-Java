package com.aspose;

import java.util.ArrayList;
import java.util.List;

public abstract class ChainBuilder<T extends ChainBuilder.ChainLink>
{

    protected ChainLink<T> buildFromChainResetItems()
    {
        if (items.isEmpty())
            throw new IllegalArgumentException("There are no items to create chain");

        ChainLink<T> head = createChain(items);
        items.clear();
        return head;
    }

    private static <T extends ChainLink> ChainLink<T> createChain(List<ChainLink<T>> items)
    {
        ChainLink<T> head = null;
        ChainLink<T> current = null;

        for (ChainLink<T> item : items)
        {
            if (item == null)
                continue;

            if (head == null)
            {
                head = item;
                current = head;
            }
            else
            {
                current.setNext(item);
                current = item;
            }
        }
        return head;
    }

    public static class ChainLink<T extends ChainLink>
    {

        ChainLink<T> getNext()
        {
            return next;
        }

        ChainLink<T> setNext(ChainLink<T> next)
        {
            this.next = next;
            return next;
        }
        protected Throwable previousException;
        protected ChainLink<T> next;
    }
    protected final List<ChainLink<T>> items = new ArrayList<ChainLink<T>>();

}
