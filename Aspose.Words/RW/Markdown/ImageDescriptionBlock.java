// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a markdown ImageDescription block.
/// </summary>
class ImageDescriptionBlock extends ReferenceBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return false;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        Debug.assertTrue(isOpened());

        add(block);
        return true;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        // Normally, there cannot be ImageDescription without corresponding LinkDestination.
        Debug.assertTrue(getLinkDestinationBlock() != null);

        // FOSS Storing an SVG image the way OOXML does requires a rasterized PNG fallback,
        // which depends on the removed rendering subsystem. Rather than throw while trying to
        // rasterize on load, skip SVG images entirely so they are not added to the model.
        // Full SVG support for Markdown is left as a later feature.
        if (getLinkDestinationBlock().isSvgDataUrl())
            return;

        Document doc = context.getBuilder().getDocument();
        Shape shape = new Shape(doc, ShapeType.IMAGE);
        shape.setWrapType(WrapType.INLINE);
        shape.setShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION, getText());
        shape.getImageData().setSourceFullName(getLinkDestinationBlock().getUri());
        if (!"".equals(getLinkDestinationBlock().getTitle()))
            shape.setShapeAttrInternal(ShapeAttr.IMAGE_TITLE, getLinkDestinationBlock().getTitle());
        shape.setName(getDefinitionLabel());

        // WORDSNET-21685 Update shape size from the actual image bytes.
        HtmlResourceLoader resourceLoader = new HtmlResourceLoader(doc.getResourceLoadingCallback(), doc.getWarningCallback());
        resourceLoader.updateShape(shape);

        context.getBuilder().insertNode(shape);
    }

    /// <summary>
    /// Returns true, if specified delimiters can constitute a valid visible text of the link.
    /// </summary>
    static boolean isValid(ImageDescriptionOpeningDelimiter opening, Delimiter closing)
    {
        Debug.assertTrue((opening != null) && (closing != null));
        Debug.assertTrue(opening.isBefore(closing));

        LinkTextOpeningDelimiter linkTextOpening = opening.getLinkTextOpening();
        if (linkTextOpening == null)
            return false;

        return LinkTextBlock.isValid(linkTextOpening, closing);
    }

    /// <summary>
    /// A type of the block.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.IMAGE_DESCRIPTION; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Opening string delimiter.
    /// </summary>
    protected /*override*/ String getOpeningStringDelimiter() { return "!["; }

    /// <summary>
    /// The Opening delimiter.
    /// </summary>
    static final String OPENING_DELIMITER = "![";

    /// <summary>
    /// The Closing delimiter.
    /// </summary>
    static final String CLOSING_DELIMITER = "]";
}

