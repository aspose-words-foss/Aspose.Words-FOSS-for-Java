// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies an internal behavior for a <see cref="NodeEnumerator.ExtractCurrentNode"/> method call
/// relative to child nodes' involving and node cloning conditions.
/// </summary>
/// <remarks>
/// There is the alternative approach which is to use an INodeExtractBehavior interface with two
/// methods: NeedModifyChildren() and NeedClone() in conjunction with the strategy pattern.
/// 
/// However, it is useful to see all of the logic altogether concentrated in a single place,
/// which is the NodeEnumerator class at the moment.
/// 
/// It is useful simply because some of the alternative interface implementations' methods' result
/// combinations could be erroneous. For instance, it would be the mistake to let to clone a part
/// of nodes and to modify children at the same time, as it requires extra node binding of cloned
/// and non-cloned nodes to take place, which simply would not be right in every particular scenario.
/// 
/// But if all of the logic is located in a single place, it is easier to distinguish wrong and right.
/// 
/// However, if the number of different node extract behaviors (or their features) will grow, then
/// the alternative approach would be more preferable.
/// </remarks>
/*enum*/ final class NodeExtractBehavior
{
    private NodeExtractBehavior(){}
    
    /// <summary>
    /// Specifies that child nodes should be modified and all of the nodes should be cloned.
    /// This one should be used in conjunction with a <see cref="NodeTraverser.Traverse"/> method call.
    /// </summary>
    public static final int MODIFY_CHILDREN_CLONE_ALL = 0;
    /// <summary>
    /// Specifies that child nodes should not be modified and only runs should be cloned.
    /// This one should be used when all of the node range nodes (including children) are coherently encountered.
    /// </summary>
    public static final int DONT_MODIFY_CHILDREN_CLONE_RUNS = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeExtractBehavior value.
    /// </summary>
    public static String getName(/*NodeExtractBehavior*/int nodeExtractBehavior)
    {
    	switch (nodeExtractBehavior)
    	{
    		case MODIFY_CHILDREN_CLONE_ALL: return "MODIFY_CHILDREN_CLONE_ALL";
    		case DONT_MODIFY_CHILDREN_CLONE_RUNS: return "DONT_MODIFY_CHILDREN_CLONE_RUNS";
    		default: return "Unknown NodeExtractBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeExtractBehavior value.
    /// </summary>
    public static String toString(/*NodeExtractBehavior*/int nodeExtractBehavior)
    {
    	switch (nodeExtractBehavior)
    	{
    		case MODIFY_CHILDREN_CLONE_ALL: return "ModifyChildrenCloneAll";
    		case DONT_MODIFY_CHILDREN_CLONE_RUNS: return "DontModifyChildrenCloneRuns";
    		default: return "Unknown NodeExtractBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NodeExtractBehavior name.
    /// </summary>
    public static /*NodeExtractBehavior*/int fromName(String nodeExtractBehaviorName)
    {
    	if ("MODIFY_CHILDREN_CLONE_ALL".equals(nodeExtractBehaviorName)) return MODIFY_CHILDREN_CLONE_ALL;
    	if ("DONT_MODIFY_CHILDREN_CLONE_RUNS".equals(nodeExtractBehaviorName)) return DONT_MODIFY_CHILDREN_CLONE_RUNS;
    	throw new IllegalArgumentException("Unknown NodeExtractBehavior name.");
    }
    
    /// <summary>
    /// Returns array of NodeExtractBehavior values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MODIFY_CHILDREN_CLONE_ALL,
    		DONT_MODIFY_CHILDREN_CLONE_RUNS,
    	};
    }

    public static final int length = 2;
}

