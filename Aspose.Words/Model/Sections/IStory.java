// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents element that contains block-level nodes.
/// </summary>
interface IStory
{
    /// <summary>
    /// Returns the type of the story.
    /// </summary>
    public /*StoryType*/int getStoryType();

    /// <summary>
    /// Gets the first paragraph in the story.
    /// </summary>
    public Paragraph getFirstParagraph();

    /// <summary>
    /// Gets the last paragraph in the story.
    /// </summary>
    public Paragraph getLastParagraph();

    /// <summary>
    /// Gets a collection of paragraphs that are immediate children of the story.
    /// </summary>
    public ParagraphCollection getParagraphs();

    /// <summary>
    /// Gets a collection of tables that are immediate children of the story.
    /// </summary>
    public TableCollection getTables();
}

