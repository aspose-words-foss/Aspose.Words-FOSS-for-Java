// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements a Tab of the TabStripControl.
/// </summary>
class TabStripTab
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TabStripTab(String caption)
    {
        mCaption = caption;
    }

    /// <summary>
    /// Gets or sets a caption of the tab.
    /// </summary>
    String getCaption() { return mCaption; }
    void setCaption(String value) { mCaption = value; }

    /// <summary>
    /// Gets or sets a tooltip of the tab.
    /// </summary>
    String getTip() { return mTip; }
    void setTip(String value) { mTip = value; }

    /// <summary>
    /// Gets or sets a name of the tab.
    /// </summary>
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Gets or sets a tag of the tab.
    /// </summary>
    String getTag() { return mTag; }
    void setTag(String value) { mTag = value; }

    /// <summary>
    /// Gets or sets an accelerator of the tab.
    /// </summary>
    char getAccelerator() { return mAccelerator; }
    void setAccelerator(char value) { mAccelerator = value; }

    /// <summary>
    /// Gets or sets a boolean value that specifies whether the tab is visible.
    /// </summary>
    boolean getVisible() { return mVisible; }
    void setVisible(boolean value) { mVisible = value; }
    
    /// <summary>
    /// Gets or sets a boolean value that specifies whether the tab is enabled.
    /// </summary>
    boolean getEnabled() { return mEnabled; }
    void setEnabled(boolean value) { mEnabled = value; }

    /// <summary>
    /// Gets or sets a TabFlag value, that combines visible and enabled properties.
    /// </summary>
    /*TabFlag*/int getTabFlag()
    {
        /*TabFlag*/int tabFlag = TabFlag.NONE;

        if (getVisible())
            tabFlag |= TabFlag.VISIBLE;
        if (getEnabled())
            tabFlag |= TabFlag.ENABLED;

        return tabFlag;
    }
    void setTabFlag(/*TabFlag*/int value)
    {
        setVisible(((value & TabFlag.VISIBLE) != 0));
        setEnabled(((value & TabFlag.ENABLED) != 0));
    }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.9.7 TabStripTabFlag.
    /// </summary>
    private boolean mVisible = true;
    private boolean mEnabled = true;

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.9.4 TabStripExtraDataBloc.
    /// </summary>
    private String mCaption;
    private String mTip = "";
    private String mName = "";
    private String mTag = "";
    private char mAccelerator = '\u0000';
}

