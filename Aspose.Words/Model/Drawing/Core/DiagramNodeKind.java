// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies kind of node in a diagram. 
/// Taken from RTF specification. No guarantee this is correct.
/// </summary>
/*enum*/ final class DiagramNodeKind
{
    private DiagramNodeKind(){}
    
    /// <summary>
    /// Used for normal subordinates? Why not Subordinate?
    /// </summary>
    public static final int NODE = 0;
    /// <summary>
    /// Seen, looks correct.
    /// </summary>
    public static final int ROOT = 1; 
    /// <summary>
    /// Seen, looks correct.
    /// </summary>
    public static final int ASSISTANT = 2;
    public static final int CO_WORKER = 3;
    public static final int SUBORDINATE = 4;
    public static final int AUXILIARY = 5;
    /// <summary>
    /// Strange, not seen.
    /// </summary>
    public static final int DEFAULT = 6;
    /// <summary>
    /// Seen in TestDiagram.doc, not sure what it is for.
    /// </summary>
    public static final int UNDOCUMENTED_65535 = 65535;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DiagramNodeKind value.
    /// </summary>
    public static String getName(/*DiagramNodeKind*/int diagramNodeKind)
    {
    	switch (diagramNodeKind)
    	{
    		case NODE: return "NODE";
    		case ROOT: return "ROOT";
    		case ASSISTANT: return "ASSISTANT";
    		case CO_WORKER: return "CO_WORKER";
    		case SUBORDINATE: return "SUBORDINATE";
    		case AUXILIARY: return "AUXILIARY";
    		case DEFAULT: return "DEFAULT";
    		case UNDOCUMENTED_65535: return "UNDOCUMENTED_65535";
    		default: return "Unknown DiagramNodeKind value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DiagramNodeKind value.
    /// </summary>
    public static String toString(/*DiagramNodeKind*/int diagramNodeKind)
    {
    	switch (diagramNodeKind)
    	{
    		case NODE: return "Node";
    		case ROOT: return "Root";
    		case ASSISTANT: return "Assistant";
    		case CO_WORKER: return "CoWorker";
    		case SUBORDINATE: return "Subordinate";
    		case AUXILIARY: return "Auxiliary";
    		case DEFAULT: return "Default";
    		case UNDOCUMENTED_65535: return "Undocumented65535";
    		default: return "Unknown DiagramNodeKind value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DiagramNodeKind name.
    /// </summary>
    public static /*DiagramNodeKind*/int fromName(String diagramNodeKindName)
    {
    	if ("NODE".equals(diagramNodeKindName)) return NODE;
    	if ("ROOT".equals(diagramNodeKindName)) return ROOT;
    	if ("ASSISTANT".equals(diagramNodeKindName)) return ASSISTANT;
    	if ("CO_WORKER".equals(diagramNodeKindName)) return CO_WORKER;
    	if ("SUBORDINATE".equals(diagramNodeKindName)) return SUBORDINATE;
    	if ("AUXILIARY".equals(diagramNodeKindName)) return AUXILIARY;
    	if ("DEFAULT".equals(diagramNodeKindName)) return DEFAULT;
    	if ("UNDOCUMENTED_65535".equals(diagramNodeKindName)) return UNDOCUMENTED_65535;
    	throw new IllegalArgumentException("Unknown DiagramNodeKind name.");
    }
    
    /// <summary>
    /// Returns array of DiagramNodeKind values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NODE,
    		ROOT,
    		ASSISTANT,
    		CO_WORKER,
    		SUBORDINATE,
    		AUXILIARY,
    		DEFAULT,
    		UNDOCUMENTED_65535,
    	};
    }

    public static final int length = 8;
}

