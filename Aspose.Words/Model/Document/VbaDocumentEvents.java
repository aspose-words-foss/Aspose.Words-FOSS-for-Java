// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2010 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies what document events are active (turned on) for a document.
/// </summary>
/// <dev>Do not renumber these because they match the binary DOC values.</dev>
/*enum*/ final class VbaDocumentEvents
{
    private VbaDocumentEvents(){}
    
    public static final int NEW = 0x0001;
    public static final int OPEN = 0x0002;
    public static final int CLOSE = 0x0004;
    public static final int SYNC = 0x0008;
    public static final int XML_AFTER_INSERT = 0x0010;
    public static final int XML_BEFORE_DELETE = 0x0020;
    public static final int CONTENT_CONTROL_AFTER_INSERT = 0x0100;
    public static final int CONTENT_CONTROL_BEFORE_DELETE = 0x0200;
    public static final int CONTENT_CONTROL_ON_EXIT = 0x0400;
    public static final int CONTENT_CONTROL_ON_ENTER = 0x0800;
    public static final int STORE_UPDATE = 0x1000;
    public static final int CONTENT_CONTROL_CONTENT_UPDATE = 0x2000;
    public static final int BUILDING_BLOCK_AFTER_INSERT = 0x4000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VbaDocumentEvents value.
    /// </summary>
    public static String getName(/*VbaDocumentEvents*/int vbaDocumentEvents)
    {
    	switch (vbaDocumentEvents)
    	{
    		case NEW: return "NEW";
    		case OPEN: return "OPEN";
    		case CLOSE: return "CLOSE";
    		case SYNC: return "SYNC";
    		case XML_AFTER_INSERT: return "XML_AFTER_INSERT";
    		case XML_BEFORE_DELETE: return "XML_BEFORE_DELETE";
    		case CONTENT_CONTROL_AFTER_INSERT: return "CONTENT_CONTROL_AFTER_INSERT";
    		case CONTENT_CONTROL_BEFORE_DELETE: return "CONTENT_CONTROL_BEFORE_DELETE";
    		case CONTENT_CONTROL_ON_EXIT: return "CONTENT_CONTROL_ON_EXIT";
    		case CONTENT_CONTROL_ON_ENTER: return "CONTENT_CONTROL_ON_ENTER";
    		case STORE_UPDATE: return "STORE_UPDATE";
    		case CONTENT_CONTROL_CONTENT_UPDATE: return "CONTENT_CONTROL_CONTENT_UPDATE";
    		case BUILDING_BLOCK_AFTER_INSERT: return "BUILDING_BLOCK_AFTER_INSERT";
    		default: return "Unknown VbaDocumentEvents value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer VbaDocumentEvents value.
    /// </summary>
    public static Set<String> getNames(/*VbaDocumentEvents*/int vbaDocumentEvents)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NEW & vbaDocumentEvents) == NEW) names.add("NEW");
    	if ((OPEN & vbaDocumentEvents) == OPEN) names.add("OPEN");
    	if ((CLOSE & vbaDocumentEvents) == CLOSE) names.add("CLOSE");
    	if ((SYNC & vbaDocumentEvents) == SYNC) names.add("SYNC");
    	if ((XML_AFTER_INSERT & vbaDocumentEvents) == XML_AFTER_INSERT) names.add("XML_AFTER_INSERT");
    	if ((XML_BEFORE_DELETE & vbaDocumentEvents) == XML_BEFORE_DELETE) names.add("XML_BEFORE_DELETE");
    	if ((CONTENT_CONTROL_AFTER_INSERT & vbaDocumentEvents) == CONTENT_CONTROL_AFTER_INSERT) names.add("CONTENT_CONTROL_AFTER_INSERT");
    	if ((CONTENT_CONTROL_BEFORE_DELETE & vbaDocumentEvents) == CONTENT_CONTROL_BEFORE_DELETE) names.add("CONTENT_CONTROL_BEFORE_DELETE");
    	if ((CONTENT_CONTROL_ON_EXIT & vbaDocumentEvents) == CONTENT_CONTROL_ON_EXIT) names.add("CONTENT_CONTROL_ON_EXIT");
    	if ((CONTENT_CONTROL_ON_ENTER & vbaDocumentEvents) == CONTENT_CONTROL_ON_ENTER) names.add("CONTENT_CONTROL_ON_ENTER");
    	if ((STORE_UPDATE & vbaDocumentEvents) == STORE_UPDATE) names.add("STORE_UPDATE");
    	if ((CONTENT_CONTROL_CONTENT_UPDATE & vbaDocumentEvents) == CONTENT_CONTROL_CONTENT_UPDATE) names.add("CONTENT_CONTROL_CONTENT_UPDATE");
    	if ((BUILDING_BLOCK_AFTER_INSERT & vbaDocumentEvents) == BUILDING_BLOCK_AFTER_INSERT) names.add("BUILDING_BLOCK_AFTER_INSERT");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VbaDocumentEvents value.
    /// </summary>
    public static String toString(/*VbaDocumentEvents*/int vbaDocumentEvents)
    {
    	switch (vbaDocumentEvents)
    	{
    		case NEW: return "New";
    		case OPEN: return "Open";
    		case CLOSE: return "Close";
    		case SYNC: return "Sync";
    		case XML_AFTER_INSERT: return "XmlAfterInsert";
    		case XML_BEFORE_DELETE: return "XmlBeforeDelete";
    		case CONTENT_CONTROL_AFTER_INSERT: return "ContentControlAfterInsert";
    		case CONTENT_CONTROL_BEFORE_DELETE: return "ContentControlBeforeDelete";
    		case CONTENT_CONTROL_ON_EXIT: return "ContentControlOnExit";
    		case CONTENT_CONTROL_ON_ENTER: return "ContentControlOnEnter";
    		case STORE_UPDATE: return "StoreUpdate";
    		case CONTENT_CONTROL_CONTENT_UPDATE: return "ContentControlContentUpdate";
    		case BUILDING_BLOCK_AFTER_INSERT: return "BuildingBlockAfterInsert";
    		default: return toStringSet(vbaDocumentEvents);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VbaDocumentEvents value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by VbaDocumentEvents name.
    /// </summary>
    public static /*VbaDocumentEvents*/int fromName(String vbaDocumentEventsName)
    {
    	if ("NEW".equals(vbaDocumentEventsName)) return NEW;
    	if ("OPEN".equals(vbaDocumentEventsName)) return OPEN;
    	if ("CLOSE".equals(vbaDocumentEventsName)) return CLOSE;
    	if ("SYNC".equals(vbaDocumentEventsName)) return SYNC;
    	if ("XML_AFTER_INSERT".equals(vbaDocumentEventsName)) return XML_AFTER_INSERT;
    	if ("XML_BEFORE_DELETE".equals(vbaDocumentEventsName)) return XML_BEFORE_DELETE;
    	if ("CONTENT_CONTROL_AFTER_INSERT".equals(vbaDocumentEventsName)) return CONTENT_CONTROL_AFTER_INSERT;
    	if ("CONTENT_CONTROL_BEFORE_DELETE".equals(vbaDocumentEventsName)) return CONTENT_CONTROL_BEFORE_DELETE;
    	if ("CONTENT_CONTROL_ON_EXIT".equals(vbaDocumentEventsName)) return CONTENT_CONTROL_ON_EXIT;
    	if ("CONTENT_CONTROL_ON_ENTER".equals(vbaDocumentEventsName)) return CONTENT_CONTROL_ON_ENTER;
    	if ("STORE_UPDATE".equals(vbaDocumentEventsName)) return STORE_UPDATE;
    	if ("CONTENT_CONTROL_CONTENT_UPDATE".equals(vbaDocumentEventsName)) return CONTENT_CONTROL_CONTENT_UPDATE;
    	if ("BUILDING_BLOCK_AFTER_INSERT".equals(vbaDocumentEventsName)) return BUILDING_BLOCK_AFTER_INSERT;
    	throw new IllegalArgumentException("Unknown VbaDocumentEvents name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by VbaDocumentEvents names.
    /// </summary>
    public static /*VbaDocumentEvents*/int fromNames(Set<String> vbaDocumentEventsNames)
    {
    	/*VbaDocumentEvents*/int result = 0;
    	for (String name : vbaDocumentEventsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of VbaDocumentEvents values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEW,
    		OPEN,
    		CLOSE,
    		SYNC,
    		XML_AFTER_INSERT,
    		XML_BEFORE_DELETE,
    		CONTENT_CONTROL_AFTER_INSERT,
    		CONTENT_CONTROL_BEFORE_DELETE,
    		CONTENT_CONTROL_ON_EXIT,
    		CONTENT_CONTROL_ON_ENTER,
    		STORE_UPDATE,
    		CONTENT_CONTROL_CONTENT_UPDATE,
    		BUILDING_BLOCK_AFTER_INSERT,
    	};
    }

    public static final int length = 13;
}

