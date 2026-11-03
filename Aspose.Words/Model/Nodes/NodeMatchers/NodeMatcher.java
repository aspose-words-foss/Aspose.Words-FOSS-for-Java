// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2012 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Used to find nodes matching some pattern.
/// Override to implement various matching patterns according to your needs.
/// </summary>
/// <remarks>
/// DD: Be careful of node matcher/live collections combination. Live collection only tracks tree changes e.g. added/removed nodes.
/// But if your node matcher uses more sophisticated criteria then collection will not update automatically.
/// </remarks>
abstract class NodeMatcher
{
    /// <summary>
    /// Returns true if the specified node is matching the internal pattern.
    /// </summary>
    abstract boolean isMatch(Node node) throws Exception;

    /// <summary>
    /// Returns true if markup nodes should be skipped.
    /// </summary>
    abstract boolean isSkipMarkupNodes();
}

