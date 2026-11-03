// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StackExtension;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Helps to translate HTML common borders (container borders) into AW paragraph's or table's borders.
/// </summary>
/// <remarks>
/// <para>
/// Common borders are the borders around a few paragraphs. Common paragraph borders representation differs fundamentally
/// in AW and HTML. They are represented as container CSS borders in HTML and as paragraph borders in AW.
/// This class helps to find the common paragraph borders saved in HTML and to translate them into AW paragraph borders
/// during HTML import.
/// </para>
/// <para>
/// Note that there is an alternative method of representing common borders - by using <see cref="HtmlBlock"/> nodes,
/// which is close to what MS Word does. When we use that alternative method we disable this resolver.
/// </para>
/// </remarks>
class HtmlCommonBorderResolver
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="disabled">
    /// Indicates whether the common border resolver is disabled. If it is <c>true</c> then common borders don't apply
    /// to paragraph's or table's borders and we use another method of representing common borders in the document model.
    /// </param>
    HtmlCommonBorderResolver(boolean disabled)
    {
        mDisabled = disabled;
    }

    /// <summary>
    /// Starts the container element processing.
    /// </summary>
    /// <param name="declarations">container element CSS declarations.</param>
    void startContainer(CssDeclarationCollection declarations)
    {
        CommonBorderContainer parentContainer = StackExtension.top(mContainers);
        BordersInfo borders = new BordersInfo(declarations);
        mContainers.push(new CommonBorderContainer(parentContainer, borders));
    }

    /// <summary>
    /// Ends the container element processing. Applies common border to the inner container's paragraphs if needed.
    /// </summary>
    void endContainer() throws Exception
    {
        Debug.assertTrue(mContainers.getCount() > 0);
        CommonBorderContainer container = mContainers.pop();
        if (!mDisabled)
            container.applyCommonBorderIfNeeded();
    }

    /// <summary>
    /// Processes and collects container's inner paragraph which doesn't have its own border.
    /// </summary>
    /// <param name="paragraph">Model's paragraph.</param>
    void collectParagraphWithoutBorder(Paragraph paragraph)
    {
        collectParagraphWithBorder(paragraph, CssDeclarationCollection.EMPTY);
    }

    /// <summary>
    /// Processes and collects container's inner paragraph which can have its own border.
    /// </summary>
    /// <param name="paragraph">Model's paragraph.</param>
    /// <param name="borderDeclarations">Paragraph's border CSS declarations.</param>
    void collectParagraphWithBorder(Paragraph paragraph, CssDeclarationCollection borderDeclarations)
    {
        if (mContainers.getCount() == 0)
            return;

        CommonBorderContainer container = mContainers.peek();
        container.add(paragraph, new BordersInfo(borderDeclarations));
    }

    void collectTable(Table table, CssDeclarationCollection borderDeclarations)
    {
        if (mContainers.getCount() == 0)
            return;

        CommonBorderContainer container = mContainers.peek();
        container.add(table, new BordersInfo(borderDeclarations));
    }

    /// <summary>
    /// A stack of currently opened common border containers.
    /// </summary>
    private /*final*/ msStack<CommonBorderContainer> mContainers = new msStack<CommonBorderContainer>();

    /// <summary>
    /// Indicates whether the common border resolver is disabled.
    /// </summary>
    /// <remarks>
    /// In order to simplify outer code we've introduced an "idle" mode for this module. When the module is disabled,
    /// it doesn't do anything useful but the outer code still uses it normally. In this way we avoid writing a lot of
    /// conditional expression in the calling code and keep it simpler.
    /// </remarks>
    private /*final*/ boolean mDisabled;
}

