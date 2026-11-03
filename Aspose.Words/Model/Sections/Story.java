// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Base class for elements that contain block-level nodes <see cref="Paragraph"/> and <see cref="Table"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/logical-levels-of-nodes-in-a-document/">Logical Levels of Nodes in a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Text of a Word document is said to consist of several stories.
/// The main text is stored in the main text story represented by <see cref="Body"/>,
/// each header and footer is stored in a separate story represented by <see cref="HeaderFooter"/>.</p>
/// </remarks>
public abstract class Story extends CompositeNode<Node> implements IStory
{
    Story(DocumentBase doc, /*StoryType*/int storyType)
    {
    	super(doc);
	
        mStoryType = storyType;
    }

    /// <summary>
    /// Gets the type of this story.
    /// </summary>
    public /*StoryType*/int getStoryType() { return mStoryType; };

    private /*StoryType*/int mStoryType;

    /// <summary>
    /// Gets the first paragraph in the story.
    /// </summary>
    public Paragraph getFirstParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, 0, false); }

    /// <summary>
    /// Gets the last paragraph in the story.
    /// </summary>
    public Paragraph getLastParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, -1, false); }

    /// <summary>
    /// Gets a collection of paragraphs that are immediate children of the story.
    /// </summary>
    public ParagraphCollection getParagraphs() { return (mParagraphs != null ? mParagraphs : (mParagraphs = new ParagraphCollection(this))); }

    /// <summary>
    /// Gets a collection of tables that are immediate children of the story.
    /// </summary>
    public TableCollection getTables() { return (mTables != null ? mTables : (mTables = new TableCollection(this))); }

    /// <summary>
    /// Gets a collection of shapes that are immediate children of the story.
    /// </summary>
    ShapeCollection getShapes() { return (mShapes != null ? mShapes : (mShapes = new ShapeCollection(this))); }

    /// <summary>
    /// Gets a collection of structured document tags that are children of the story.
    /// </summary>
    StructuredDocumentTagCollection getStructuredDocumentTags()
    {
        return (mStructuredDocumentTags != null ? mStructuredDocumentTags : (mStructuredDocumentTags = new StructuredDocumentTagCollection(this)));
    }

    /// <summary>
    /// Returns true if the section contains only a single empty paragraph with a section break.
    /// </summary>
    boolean isSectionBreak() { return hasOneChildOnly() && (msString.equals(getLastChild().getText(), ControlChar.SECTION_BREAK)); }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Story lhs = (Story)super.deepClone(isCloneChildren, cloningListener);
        lhs.mParagraphs = null;
        lhs.mTables = null;
        lhs.mShapes = null;
        return lhs;
    }

    /// <summary>
    /// Deletes all shapes from the text of this story.
    /// </summary>
    public void deleteShapes()
    {
        NodeCollection groupShapes = getChildNodes(NodeType.GROUP_SHAPE, true);
        groupShapes.clear();

        NodeCollection shapes = getChildNodes(NodeType.SHAPE, true);
        shapes.clear();
    }

    /// <summary>
    /// Can insert only block level elements into the story.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.isBlockLevelNode(newChild);
    }

    /// <summary>
    /// A shortcut method that creates a <see cref="Paragraph"/> object with optional text and appends it to the end of this object.
    /// </summary>
    /// <param name="text">The text for the paragraph. Can be <c>null</c> or empty string.</param>
    /// <returns>The newly created and appended paragraph.</returns>
    public Paragraph appendParagraph(String text)
    {
        Paragraph para = new Paragraph(getDocument());
        appendChild(para);

        if (StringUtil.hasChars(text))
            para.appendChild(new Run(getDocument(), text));

        return para;
    }

    private ParagraphCollection mParagraphs;
    private TableCollection mTables;
    private ShapeCollection mShapes;
    private StructuredDocumentTagCollection mStructuredDocumentTags;
}

