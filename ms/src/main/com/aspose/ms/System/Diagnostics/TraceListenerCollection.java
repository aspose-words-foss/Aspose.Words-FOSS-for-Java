package com.aspose.ms.System.Diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TraceListenerCollection implements Iterable<TraceListener>
{
    @Override
    public Iterator<TraceListener> iterator()
    {
        return mListeners.iterator();
    }

    public void add(TraceListener traceWriter)
    {
        mListeners.add(traceWriter);
    }

    public void remove(TextWriterTraceListener traceWriter)
    {
        mListeners.remove(traceWriter);
    }

    public int getCount() { return mListeners.size();}

    public Object get(String name)
    {
        if (name == null || "".equals(name.trim()))
            return null;

        for (TraceListener l : mListeners)
            if (name.equals(l.getName()))
                return l;

        return null;
    }

    public void clear()
    {
        mListeners.clear();
    }

    private final List<TraceListener> mListeners = new ArrayList<TraceListener>();
}
