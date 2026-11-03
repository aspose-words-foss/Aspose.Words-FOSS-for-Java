// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an add-in task pane object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <dev>2.2.8 CT_OsfTaskpane. This essence has public constructor.</dev>
public class TaskPane
{
    /// <summary>
    /// Specifies the index, enumerating from the outside to the inside, of this task pane among other persisted
    /// task panes docked in the same default location.
    /// </summary>
    public int getRow() { return mRow; }; public void setRow(int value) { mRow = value; };

    private int mRow;

    /// <summary>
    /// Specifies the default width value for this task pane instance.
    /// </summary>
    public double getWidth() { return mWidth; }; public void setWidth(double value) { mWidth = value; };

    private double mWidth;

    /// <summary>
    /// Specifies whether the task pane is locked to the document in the UI and cannot be closed by the user.
    /// </summary>
    public boolean isLocked() { return mIsLocked; }; public void isLocked(boolean value) { mIsLocked = value; };

    private boolean mIsLocked;

    /// <summary>
    /// Specifies whether the task pane shows as visible by default when the document opens.
    /// </summary>
    public boolean isVisible() { return mIsVisible; }; public void isVisible(boolean value) { mIsVisible = value; };

    private boolean mIsVisible;

    /// <summary>
    /// Specifies the last-docked location of this task pane object.
    /// </summary>
    /// <dev>In the spec this value represents as string.</dev>
    public /*TaskPaneDockState*/int getDockState() { return mDockState; }; public void setDockState(/*TaskPaneDockState*/int value) { mDockState = value; };

    private /*TaskPaneDockState*/int mDockState;

    /// <summary>
    /// Represents an web extension object.
    /// </summary>
    public WebExtension getWebExtension() { return mExtension; }

    private /*final*/ WebExtension mExtension = new WebExtension();
}

