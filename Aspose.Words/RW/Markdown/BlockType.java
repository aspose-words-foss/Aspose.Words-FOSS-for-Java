// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible types of markdown blocks.
/// </summary>
/*enum*/ final class BlockType /*: byte*/
{
    private BlockType(){}
    
    public static final byte DOCUMENT = (byte)0;

    public static final byte INLINE = (byte)1;
    public static final byte BOLD_INLINE = (byte)2;
    public static final byte ITALIC_INLINE = (byte)3;
    public static final byte INLINE_CODE = (byte)4;
    public static final byte STRIKETHROUGH = (byte)5;
    public static final byte UNDERLINE = (byte)6;
    public static final byte AUTOLINK = (byte)7;
    public static final byte IMAGE_DESCRIPTION = (byte)8;
    public static final byte LINK_TEXT = (byte)9;
    public static final byte LINK_DESTINATION = (byte)10;
    public static final byte FOOTNOTE_REFERENCE = (byte)11;

    public static final byte BLANK_LINE = (byte)12;
    public static final byte END_OF_LINE = (byte)13;

    public static final byte PARAGRAPH = (byte)14;
    public static final byte HORIZONTAL_RULE = (byte)15;
    public static final byte ATX_HEADING = (byte)16;
    public static final byte SETEXT_HEADING = (byte)17;
    public static final byte QUOTE = (byte)18;
    public static final byte CELL = (byte)19;
    public static final byte ROW = (byte)20;
    public static final byte TABLE = (byte)21;
    public static final byte FOOTNOTE_DEFINITION = (byte)22;
    public static final byte LINK_DEFINITION = (byte)23;

    public static final byte INDENTED_CODE = (byte)24;
    public static final byte FENCED_CODE = (byte)25;

    public static final byte BULLET_LIST_ITEM = (byte)26;
    public static final byte ORDERED_LIST_ITEM = (byte)27;

    public static final byte DELIMITER_RUN = (byte)28;

    public static final byte HTML_TAG = (byte)29;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BlockType value.
    /// </summary>
    public static String getName(/*BlockType*/byte blockType)
    {
    	if (DOCUMENT == blockType) return "DOCUMENT";
    	if (INLINE == blockType) return "INLINE";
    	if (BOLD_INLINE == blockType) return "BOLD_INLINE";
    	if (ITALIC_INLINE == blockType) return "ITALIC_INLINE";
    	if (INLINE_CODE == blockType) return "INLINE_CODE";
    	if (STRIKETHROUGH == blockType) return "STRIKETHROUGH";
    	if (UNDERLINE == blockType) return "UNDERLINE";
    	if (AUTOLINK == blockType) return "AUTOLINK";
    	if (IMAGE_DESCRIPTION == blockType) return "IMAGE_DESCRIPTION";
    	if (LINK_TEXT == blockType) return "LINK_TEXT";
    	if (LINK_DESTINATION == blockType) return "LINK_DESTINATION";
    	if (FOOTNOTE_REFERENCE == blockType) return "FOOTNOTE_REFERENCE";
    	if (BLANK_LINE == blockType) return "BLANK_LINE";
    	if (END_OF_LINE == blockType) return "END_OF_LINE";
    	if (PARAGRAPH == blockType) return "PARAGRAPH";
    	if (HORIZONTAL_RULE == blockType) return "HORIZONTAL_RULE";
    	if (ATX_HEADING == blockType) return "ATX_HEADING";
    	if (SETEXT_HEADING == blockType) return "SETEXT_HEADING";
    	if (QUOTE == blockType) return "QUOTE";
    	if (CELL == blockType) return "CELL";
    	if (ROW == blockType) return "ROW";
    	if (TABLE == blockType) return "TABLE";
    	if (FOOTNOTE_DEFINITION == blockType) return "FOOTNOTE_DEFINITION";
    	if (LINK_DEFINITION == blockType) return "LINK_DEFINITION";
    	if (INDENTED_CODE == blockType) return "INDENTED_CODE";
    	if (FENCED_CODE == blockType) return "FENCED_CODE";
    	if (BULLET_LIST_ITEM == blockType) return "BULLET_LIST_ITEM";
    	if (ORDERED_LIST_ITEM == blockType) return "ORDERED_LIST_ITEM";
    	if (DELIMITER_RUN == blockType) return "DELIMITER_RUN";
    	if (HTML_TAG == blockType) return "HTML_TAG";
    	return "Unknown BlockType value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BlockType value.
    /// </summary>
    public static String toString(/*BlockType*/byte blockType)
    {
    	if (DOCUMENT == blockType) return "Document";
    	if (INLINE == blockType) return "Inline";
    	if (BOLD_INLINE == blockType) return "BoldInline";
    	if (ITALIC_INLINE == blockType) return "ItalicInline";
    	if (INLINE_CODE == blockType) return "InlineCode";
    	if (STRIKETHROUGH == blockType) return "Strikethrough";
    	if (UNDERLINE == blockType) return "Underline";
    	if (AUTOLINK == blockType) return "Autolink";
    	if (IMAGE_DESCRIPTION == blockType) return "ImageDescription";
    	if (LINK_TEXT == blockType) return "LinkText";
    	if (LINK_DESTINATION == blockType) return "LinkDestination";
    	if (FOOTNOTE_REFERENCE == blockType) return "FootnoteReference";
    	if (BLANK_LINE == blockType) return "BlankLine";
    	if (END_OF_LINE == blockType) return "EndOfLine";
    	if (PARAGRAPH == blockType) return "Paragraph";
    	if (HORIZONTAL_RULE == blockType) return "HorizontalRule";
    	if (ATX_HEADING == blockType) return "AtxHeading";
    	if (SETEXT_HEADING == blockType) return "SetextHeading";
    	if (QUOTE == blockType) return "Quote";
    	if (CELL == blockType) return "Cell";
    	if (ROW == blockType) return "Row";
    	if (TABLE == blockType) return "Table";
    	if (FOOTNOTE_DEFINITION == blockType) return "FootnoteDefinition";
    	if (LINK_DEFINITION == blockType) return "LinkDefinition";
    	if (INDENTED_CODE == blockType) return "IndentedCode";
    	if (FENCED_CODE == blockType) return "FencedCode";
    	if (BULLET_LIST_ITEM == blockType) return "BulletListItem";
    	if (ORDERED_LIST_ITEM == blockType) return "OrderedListItem";
    	if (DELIMITER_RUN == blockType) return "DelimiterRun";
    	if (HTML_TAG == blockType) return "HtmlTag";
    	return "Unknown BlockType value.";
    }
    
    /// <summary>
    /// Returns integer representation by BlockType name.
    /// </summary>
    public static /*BlockType*/byte fromName(String blockTypeName)
    {
    	if ("DOCUMENT".equals(blockTypeName)) return DOCUMENT;
    	if ("INLINE".equals(blockTypeName)) return INLINE;
    	if ("BOLD_INLINE".equals(blockTypeName)) return BOLD_INLINE;
    	if ("ITALIC_INLINE".equals(blockTypeName)) return ITALIC_INLINE;
    	if ("INLINE_CODE".equals(blockTypeName)) return INLINE_CODE;
    	if ("STRIKETHROUGH".equals(blockTypeName)) return STRIKETHROUGH;
    	if ("UNDERLINE".equals(blockTypeName)) return UNDERLINE;
    	if ("AUTOLINK".equals(blockTypeName)) return AUTOLINK;
    	if ("IMAGE_DESCRIPTION".equals(blockTypeName)) return IMAGE_DESCRIPTION;
    	if ("LINK_TEXT".equals(blockTypeName)) return LINK_TEXT;
    	if ("LINK_DESTINATION".equals(blockTypeName)) return LINK_DESTINATION;
    	if ("FOOTNOTE_REFERENCE".equals(blockTypeName)) return FOOTNOTE_REFERENCE;
    	if ("BLANK_LINE".equals(blockTypeName)) return BLANK_LINE;
    	if ("END_OF_LINE".equals(blockTypeName)) return END_OF_LINE;
    	if ("PARAGRAPH".equals(blockTypeName)) return PARAGRAPH;
    	if ("HORIZONTAL_RULE".equals(blockTypeName)) return HORIZONTAL_RULE;
    	if ("ATX_HEADING".equals(blockTypeName)) return ATX_HEADING;
    	if ("SETEXT_HEADING".equals(blockTypeName)) return SETEXT_HEADING;
    	if ("QUOTE".equals(blockTypeName)) return QUOTE;
    	if ("CELL".equals(blockTypeName)) return CELL;
    	if ("ROW".equals(blockTypeName)) return ROW;
    	if ("TABLE".equals(blockTypeName)) return TABLE;
    	if ("FOOTNOTE_DEFINITION".equals(blockTypeName)) return FOOTNOTE_DEFINITION;
    	if ("LINK_DEFINITION".equals(blockTypeName)) return LINK_DEFINITION;
    	if ("INDENTED_CODE".equals(blockTypeName)) return INDENTED_CODE;
    	if ("FENCED_CODE".equals(blockTypeName)) return FENCED_CODE;
    	if ("BULLET_LIST_ITEM".equals(blockTypeName)) return BULLET_LIST_ITEM;
    	if ("ORDERED_LIST_ITEM".equals(blockTypeName)) return ORDERED_LIST_ITEM;
    	if ("DELIMITER_RUN".equals(blockTypeName)) return DELIMITER_RUN;
    	if ("HTML_TAG".equals(blockTypeName)) return HTML_TAG;
    	throw new IllegalArgumentException("Unknown BlockType name.");
    }
    
    /// <summary>
    /// Returns array of BlockType values.
    /// </summary>
    public static byte[] getValues()
    {
    	return new byte[]
    	{
    		DOCUMENT,
    		INLINE,
    		BOLD_INLINE,
    		ITALIC_INLINE,
    		INLINE_CODE,
    		STRIKETHROUGH,
    		UNDERLINE,
    		AUTOLINK,
    		IMAGE_DESCRIPTION,
    		LINK_TEXT,
    		LINK_DESTINATION,
    		FOOTNOTE_REFERENCE,
    		BLANK_LINE,
    		END_OF_LINE,
    		PARAGRAPH,
    		HORIZONTAL_RULE,
    		ATX_HEADING,
    		SETEXT_HEADING,
    		QUOTE,
    		CELL,
    		ROW,
    		TABLE,
    		FOOTNOTE_DEFINITION,
    		LINK_DEFINITION,
    		INDENTED_CODE,
    		FENCED_CODE,
    		BULLET_LIST_ITEM,
    		ORDERED_LIST_ITEM,
    		DELIMITER_RUN,
    		HTML_TAG,
    	};
    }

    public static final int length = 30;
}

