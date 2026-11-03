// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates available locations of task pane object.
/// </summary>
/// <dev>"top", "bottom", "fill" do not work. Add more available values upon request.</dev>
public /*enum*/ final class TaskPaneDockState
{
    private TaskPaneDockState(){}
    
    /// <summary>
    /// Dock the task pane on the right side of the document window.
    /// </summary>
    public static final int RIGHT = 0;
    /// <summary>
    /// Dock the task pane on the left side of the document window.
    /// </summary>
    public static final int LEFT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TaskPaneDockState value.
    /// </summary>
    public static String getName(/*TaskPaneDockState*/int taskPaneDockState)
    {
    	switch (taskPaneDockState)
    	{
    		case RIGHT: return "RIGHT";
    		case LEFT: return "LEFT";
    		default: return "Unknown TaskPaneDockState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TaskPaneDockState value.
    /// </summary>
    public static String toString(/*TaskPaneDockState*/int taskPaneDockState)
    {
    	switch (taskPaneDockState)
    	{
    		case RIGHT: return "Right";
    		case LEFT: return "Left";
    		default: return "Unknown TaskPaneDockState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TaskPaneDockState name.
    /// </summary>
    public static /*TaskPaneDockState*/int fromName(String taskPaneDockStateName)
    {
    	if ("RIGHT".equals(taskPaneDockStateName)) return RIGHT;
    	if ("LEFT".equals(taskPaneDockStateName)) return LEFT;
    	throw new IllegalArgumentException("Unknown TaskPaneDockState name.");
    }
    
    /// <summary>
    /// Returns array of TaskPaneDockState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RIGHT,
    		LEFT,
    	};
    }

    public static final int length = 2;
}


