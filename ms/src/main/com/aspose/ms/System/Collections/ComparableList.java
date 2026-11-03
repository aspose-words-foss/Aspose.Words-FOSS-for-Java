package com.aspose.ms.System.Collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * This class implements ArrayList with comparator
 */
public class ComparableList<K> extends ArrayList<K>
{
    private Comparator<K> comparer = null;

    public ComparableList(Collection<K> c, Comparator<K> comparer)
    {
        super(c);
        this.comparer = comparer;
    }

    @Override
    public int indexOf(Object o)
    {
        int size = this.size();

        if (o == null)
        {
            for (int i = 0; i < size; i++)
                if (this.get(i)==null)
                    return i;
        }
        else
        {
            for (int i = 0; i < size; i++)
                if (this.comparer.compare((K)o, this.get(i)) == 0)
                    return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        int size = this.size();

        if (o == null)
        {
            for (int i = size-1; i >= 0; i--)
                if (this.get(i)==null)
                    return i;
        }
        else
        {
            for (int i = size-1; i >= 0; i--)
                if (this.comparer.compare((K)o, this.get(i)) == 0)
                    return i;
        }

        return -1;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
        {
            return super.remove(o);
        }
        else
        {
            int index = this.indexOf(o);
            if (index >= 0)
            {
                this.remove(index);
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
