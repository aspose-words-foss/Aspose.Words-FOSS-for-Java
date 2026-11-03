// mf

package com.aspose.ms.System;

/**
 * Java event model sligtly differs from .Net one.
 * Formally java.util.EventObject corrsponds to .Net System.EventArgs but
 * it is cheeper to use ours own EventArgs in java due the difference
 * between EventObject and EventArgs:
 * java.util.EventObject should contain an object instance that raises the event
 * - but System.EventArgs should not: the object passed directly to EventHandler delegate.
 */
public class EventArgs
{
    public static final EventArgs Empty = new EventArgs();
}
