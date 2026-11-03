// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// The token that represents a tag node of HTML markup.
/// </summary>
class HtmlTagToken extends HtmlToken
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="name">The name of the tag node.</param>
    /// <param name="isStart">A flag that determines whether this is a start (<c>true</c>) or an end (<c>false</c>) tag.</param>
    /// <param name="isSelfClosing">Whether this tag is self-closing.</param>
    /// <param name="attributes">The collection of attributes of this tag node.</param>
    HtmlTagToken(
        String name,
        boolean isStart,
        boolean isSelfClosing,
        HtmlAttributeCollection attributes)
    {
    	super(HtmlTokenType.TAG);
	
        Debug.assertTrue(StringUtil.hasChars(name));
        Debug.assertTrue(attributes != null);

        setName(name);
        isStart(isStart);
        isSelfClosing(isSelfClosing);
        setAttributes(attributes);
    }

    /// <summary>
    /// Gets the name of the tag node.
    /// </summary>
    String getName() { return mName; }; private void setName(String value) { mName = value; };

    private String mName;

    /// <summary>
    /// Gets the value indicating whether this is a start (<c>true</c>) or end (<c>false</c>) tag.
    /// </summary>
    boolean isStart() { return mIsStart; }; private void isStart(boolean value) { mIsStart = value; };

    private boolean mIsStart;

    /// <summary>
    /// Gets the 'self-closing' flag value set by the tokenizer.
    /// </summary>
    boolean isSelfClosing() { return mIsSelfClosing; }; private void isSelfClosing(boolean value) { mIsSelfClosing = value; };

    private boolean mIsSelfClosing;

    /// <summary>
    /// Gets the collection of attributes of this tag node.
    /// </summary>
    HtmlAttributeCollection getAttributes() { return mAttributes; }; private void setAttributes(HtmlAttributeCollection value) { mAttributes = value; };

    private HtmlAttributeCollection mAttributes;
}

