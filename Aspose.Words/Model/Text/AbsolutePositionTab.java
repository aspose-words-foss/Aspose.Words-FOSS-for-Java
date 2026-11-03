// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/15/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// An absolute position tab is a character which is used to advance the position on 
/// the current line of text when displaying this WordprocessingML content.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
public class AbsolutePositionTab extends SpecialChar
{
    AbsolutePositionTab(DocumentBase doc, RunPr runPr)
    {
    	super(doc, ControlChar.TAB_CHAR, runPr);
	
    }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitAbsolutePositionTab"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitAbsolutePositionTab(this));
    }

    /// <summary>
    /// Specifies the location of the positional tab stop on the line, 
    /// as well as the alignment which shall be applied to text subsequent to this absolute position tab.
    /// </summary>
    /*AbsolutePositionTabAlignment*/int getAlignment() { return mAlignment; }
    void setAlignment(/*AbsolutePositionTabAlignment*/int value) { mAlignment = value; }

    /// <summary>
    /// Specifies the extents which shall be used to calculate the absolute positioning of this absolute position tab.
    /// </summary>
    /*AbsolutePositionTabPositioningBase*/int getPositioningBase() { return mPositioningBase; }
    void setPositioningBase(/*AbsolutePositionTabPositioningBase*/int value) { mPositioningBase = value; }

    /// <summary>
    /// Specifies the character which shall be used to fill in the space created by this absolute position tab.
    /// </summary>
    /*AbsolutePositionTabLeaderChar*/int getLeaderChar() { return mLeader; }
    void setLeaderChar(/*AbsolutePositionTabLeaderChar*/int value) { mLeader = value; }

    private /*AbsolutePositionTabAlignment*/int mAlignment = AbsolutePositionTabAlignment.DEFAULT;
    private /*AbsolutePositionTabLeaderChar*/int mLeader = AbsolutePositionTabLeaderChar.DEFAULT;
    private /*AbsolutePositionTabPositioningBase*/int mPositioningBase = AbsolutePositionTabPositioningBase.DEFAULT;
}

