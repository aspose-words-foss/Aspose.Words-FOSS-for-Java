// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ArgumentUtil;


/// <summary>
/// Provides data for a custom replace operation.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/find-and-replace/">Find and Replace</a> documentation article.</para>
/// </summary>
/// <seealso cref="IReplacingCallback"/>
/// <seealso cref="Range"/>
/// <seealso cref="Range.Replace(string, string, FindReplaceOptions)"/>
public class ReplacingArgs
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ReplacingArgs(Match match, int matchOffset, Node matchStartNode, Node matchEndNode, String replacement)
    {
        mMatch = match;
        mMatchNode = matchStartNode;
        mMatchEndNode = matchEndNode;
        mMatchOffset = matchOffset;
        mReplacement = replacement;
    }

    /// <summary>
    /// The <see cref="System.Text.RegularExpressions.Match"/> resulting from a single regular
    /// expression match during a <b>Replace</b>.
    /// </summary>
    /// <remarks>
    /// <p><ms><b>Match.Index"</b></ms><java><tt>Matcher.start()</tt></java><cpp><b>Match.Index"</b></cpp> gets the zero-based starting
    /// position of the match from the start of the find and replace range.</p>
    /// </remarks>
    //JAVA: property accessor is internalized
	Match getMatchInternal() { return mMatch; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.regex.Matcher getMatch() { return Match.toJava(getMatchInternal()); }

    /// <summary>
    /// Gets the node that contains the beginning of the match.
    /// </summary>
    public Node getMatchNode() { return mMatchNode; };

    private Node mMatchNode;

    /// <summary>
    /// Gets the node that contains the end of the match.
    /// </summary>
    public Node getMatchEndNode() { return mMatchEndNode; };

    private Node mMatchEndNode;

    /// <summary>
    /// Gets the zero-based starting position of the match from the start of
    /// the node that contains the beginning of the match.
    /// </summary>
    public int getMatchOffset() { return mMatchOffset; }

    /// <summary>
    /// Gets or sets the replacement string.
    /// </summary>
    public String getReplacement() { return mReplacement; }
    public void setReplacement(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mReplacement = value;
    }
    private String mGroupName;

    /// <summary>
    /// Identifies, by index, a captured group in the <see cref="Match"/>
    /// that is to be replaced with the <see cref="Replacement"/> string.
    /// </summary>
    /// <remarks>
    /// <ms><p><see cref="GroupIndex"/> has effect only when <see cref="GroupName"/> is <c>null</c>.</p></ms>
    /// <cpp><p><see cref="GroupIndex"/> has effect only when <see cref="GroupName"/> is <c>null</c>.</p></cpp>
    /// <p>Default is zero.</p>
    /// </remarks>
    public int getGroupIndex() { return mGroupIndex; }
    public void setGroupIndex(int value) { mGroupIndex = value; }

    private /*final*/ Match mMatch;
    private /*final*/ int mMatchOffset;
    private String mReplacement;
    private int mGroupIndex;
}

