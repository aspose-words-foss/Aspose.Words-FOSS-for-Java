// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Represents on/off options for the <see cref="NodeCopier"/> class.
/// </summary>
/*enum*/ final class NodeCopierOptions
{
    private NodeCopierOptions(){}
    
    /// <summary>
    /// No defined options.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Indicates whether to replace every destination start node ancestor's properties with the corresponding
    /// source ones in case when any ancestor of the same type can be met while a source range traversal.
    /// </summary>
    public static final int USE_SOURCE_START_ANCESTOR_PR = 1;
    /// <summary>
    /// Specifies whether cross structure annotation nodes should be copied.
    /// See <see cref="NodeUtil.IsCrossStructureAnnotation(Node)"/> for details.
    /// </summary>
    public static final int SKIP_CROSS_STRUCTURE_ANNOTATIONS = 2;
    /// <summary>
    /// When defined, then node need to be cloned while extracting it within the enumerated range.
    /// </summary>
    public static final int CLONE_NODE = 4;
    /// <summary>
    /// If set, when the range has block level annotation nodes at bounds, copying gives same result as when
    /// the annotation nodes are moved to inline level.
    /// </summary>
    public static final int PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE = 8;
    /// <summary>
    /// Specifies whether the range start headers and footers should be prolongated (cloned) during reference node separation.
    /// </summary>
    public static final int PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeCopierOptions value.
    /// </summary>
    public static String getName(/*NodeCopierOptions*/int nodeCopierOptions)
    {
    	switch (nodeCopierOptions)
    	{
    		case NONE: return "NONE";
    		case USE_SOURCE_START_ANCESTOR_PR: return "USE_SOURCE_START_ANCESTOR_PR";
    		case SKIP_CROSS_STRUCTURE_ANNOTATIONS: return "SKIP_CROSS_STRUCTURE_ANNOTATIONS";
    		case CLONE_NODE: return "CLONE_NODE";
    		case PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE: return "PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE";
    		case PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS: return "PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS";
    		default: return "Unknown NodeCopierOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer NodeCopierOptions value.
    /// </summary>
    public static Set<String> getNames(/*NodeCopierOptions*/int nodeCopierOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & nodeCopierOptions) == NONE) names.add("NONE");
    	if ((USE_SOURCE_START_ANCESTOR_PR & nodeCopierOptions) == USE_SOURCE_START_ANCESTOR_PR) names.add("USE_SOURCE_START_ANCESTOR_PR");
    	if ((SKIP_CROSS_STRUCTURE_ANNOTATIONS & nodeCopierOptions) == SKIP_CROSS_STRUCTURE_ANNOTATIONS) names.add("SKIP_CROSS_STRUCTURE_ANNOTATIONS");
    	if ((CLONE_NODE & nodeCopierOptions) == CLONE_NODE) names.add("CLONE_NODE");
    	if ((PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE & nodeCopierOptions) == PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE) names.add("PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE");
    	if ((PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS & nodeCopierOptions) == PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS) names.add("PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeCopierOptions value.
    /// </summary>
    public static String toString(/*NodeCopierOptions*/int nodeCopierOptions)
    {
    	switch (nodeCopierOptions)
    	{
    		case NONE: return "None";
    		case USE_SOURCE_START_ANCESTOR_PR: return "UseSourceStartAncestorPr";
    		case SKIP_CROSS_STRUCTURE_ANNOTATIONS: return "SkipCrossStructureAnnotations";
    		case CLONE_NODE: return "CloneNode";
    		case PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE: return "ProcessBoundBlockAnnotationAsInline";
    		case PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS: return "ProlongRangeStartSectionHeadersFooters";
    		default: return toStringSet(nodeCopierOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeCopierOptions value.
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
    /// Returns integer representation by NodeCopierOptions name.
    /// </summary>
    public static /*NodeCopierOptions*/int fromName(String nodeCopierOptionsName)
    {
    	if ("NONE".equals(nodeCopierOptionsName)) return NONE;
    	if ("USE_SOURCE_START_ANCESTOR_PR".equals(nodeCopierOptionsName)) return USE_SOURCE_START_ANCESTOR_PR;
    	if ("SKIP_CROSS_STRUCTURE_ANNOTATIONS".equals(nodeCopierOptionsName)) return SKIP_CROSS_STRUCTURE_ANNOTATIONS;
    	if ("CLONE_NODE".equals(nodeCopierOptionsName)) return CLONE_NODE;
    	if ("PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE".equals(nodeCopierOptionsName)) return PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE;
    	if ("PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS".equals(nodeCopierOptionsName)) return PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS;
    	throw new IllegalArgumentException("Unknown NodeCopierOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by NodeCopierOptions names.
    /// </summary>
    public static /*NodeCopierOptions*/int fromNames(Set<String> nodeCopierOptionsNames)
    {
    	/*NodeCopierOptions*/int result = 0;
    	for (String name : nodeCopierOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of NodeCopierOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		USE_SOURCE_START_ANCESTOR_PR,
    		SKIP_CROSS_STRUCTURE_ANNOTATIONS,
    		CLONE_NODE,
    		PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE,
    		PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS,
    	};
    }

    public static final int length = 6;
}

