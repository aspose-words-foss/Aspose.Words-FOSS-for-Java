// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2013 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Common constants for reader/writer classes.
/// </summary>
/*static*/ class HtmlConstants
{
	/* Simulation of static class by using private constructor */
	private HtmlConstants()
	{}

    /// <summary>
    /// Aspose Words vendor-specific extensions for CSS properties.
    /// </summary>
    static final String ASPOSE_VENDOR_CSS_PREFIX = "-aw-";

    static final String IGNORE_ON_ROUNDTRIP = ASPOSE_VENDOR_CSS_PREFIX + "ignore";
    static final String IGNORE_ON_ROUNDTRIP_TRUE = "true";

    // Prefixes used to generate names for comment and its cross-references.
    static final String COMMENT_REFERENCE_ID_PREFIX = "_cmntref";
    static final String COMMENT_HYPERLINK_HREF_PREFIX = "#_cmnt";
    static final String COMMENT_ID_PREFIX = "_cmnt";
    static final String COMMENT_REFERENCE_HYPERLINK_HREF_PREFIX = "#_cmntref";

    static final String COMMENT_AUTHOR = ASPOSE_VENDOR_CSS_PREFIX + "comment-author";
    static final String COMMENT_DATE_TIME = ASPOSE_VENDOR_CSS_PREFIX + "comment-datetime";
    static final String COMMENT_INITIAL = ASPOSE_VENDOR_CSS_PREFIX + "comment-initial";
    static final String COMMENT_PARENT_ID = ASPOSE_VENDOR_CSS_PREFIX + "comment-parent-id";
    static final String COMMENT_DONE = ASPOSE_VENDOR_CSS_PREFIX + "comment-done";
    static final String COMMENT_DONE_YES = "yes";

    // In case when comment is overlapped with other comment or comment’s boundaries are spread over
    // multiple paragraphs additional span element is used to preserve comment’s boundaries
    // (HTML doesn’t allow anchor tags to overlap each other or spread over multiple paragraphs).
    // These css properties can be applied to span element and store names of comments which they start or end.
    static final String COMMENT_START = ASPOSE_VENDOR_CSS_PREFIX + "comment-start";
    static final String COMMENT_END = ASPOSE_VENDOR_CSS_PREFIX + "comment-end";

    // Prefixes used to generate names for footnote and its cross-references.
    static final String FOOTNOTE_REFERENCE_ID_PREFIX = "_ftnref";
    static final String FOOTNOTE_HYPERLINK_HREF_PREFIX = "#_ftn";
    static final String FOOTNOTE_ID_PREFIX = "_ftn";
    static final String FOOTNOTE_REFERENCE_HYPERLINK_HREF_PREFIX = "#_ftnref";

    // Prefixes used to generate names for endnote and its cross-references.
    static final String ENDNOTE_REFERENCE_ID_PREFIX = "_ednref";
    static final String ENDNOTE_HYPERLINK_HREF_PREFIX = "#_edn";
    static final String ENDNOTE_ID_PREFIX = "_edn";
    static final String ENDNOTE_REFERENCE_HYPERLINK_HREF_PREFIX = "#_ednref";

    // All footnotes (endnotes) in a document share formatting. When exported to HTML footnotes
    // (endnotes) are separated from other document’s content with horizontal rule element.
    // These css properties can be applied to horizontal rule element and
    // store footnotes' (endnotes') formatting roundtrip information.
    static final String FOOTNOTE_TYPE = ASPOSE_VENDOR_CSS_PREFIX + "footnote-type";
    static final String FOOTNOTE_NUMBER_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "footnote-numberstyle";
    static final String FOOTNOTE_START_NUMBER = ASPOSE_VENDOR_CSS_PREFIX + "footnote-startnumber";

    static final String FOOTNOTE_IS_AUTO = ASPOSE_VENDOR_CSS_PREFIX + "footnote-isauto";

    static final String HEADER_FOOTER_TYPE = ASPOSE_VENDOR_CSS_PREFIX + "headerfooter-type";
    static final String HEADER_FOOTER_TYPE_HEADER_PRIMARY = "header-primary";
    static final String HEADER_FOOTER_TYPE_HEADER_FIRST = "header-first";
    static final String HEADER_FOOTER_TYPE_FOOTER_PRIMARY = "footer-primary";
    static final String HEADER_FOOTER_TYPE_FOOTER_FIRST = "footer-first";
    static final String HEADER_FOOTER_TYPE_LINKED = "linked";

    // In case when bookmark is overlapped with other bookmark or bookmark’s boundaries are spread over
    // multiple paragraphs additional span element is used to preserve bookmark’s boundaries
    // (HTML doesn’t allow anchor tags to overlap each other or spread over multiple paragraphs).
    // These css properties can be applied to span element and store names of bookmarks which they start or end.
    static final String BOOKMARK_START = ASPOSE_VENDOR_CSS_PREFIX + "bookmark-start";
    static final String BOOKMARK_END = ASPOSE_VENDOR_CSS_PREFIX + "bookmark-end";

    static final String HEADER_FOOTER_DIFFERENT_FIRST_PAGE = ASPOSE_VENDOR_CSS_PREFIX + "different-first-page";
    static final String HEADER_FOOTER_DIFFERENT_FIRST_PAGE_TRUE = "true";

    static final String TAB_STOP_ALIGNMENT = ASPOSE_VENDOR_CSS_PREFIX + "tabstop-align";
    static final String TAB_STOP_POSITION = ASPOSE_VENDOR_CSS_PREFIX + "tabstop-pos";
    static final String TAB_STOP_LEADER = ASPOSE_VENDOR_CSS_PREFIX + "tabstop-leader";
    static final String TAB_STOP_REPLACEMENT = "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u0020";

    static final String FIELD_CODE = ASPOSE_VENDOR_CSS_PREFIX + "field-code";
    static final String FIELD_START = ASPOSE_VENDOR_CSS_PREFIX + "field-start";
    static final String FIELD_START_TRUE = "true";
    static final String FIELD_SEPARATOR = ASPOSE_VENDOR_CSS_PREFIX + "field-separator";
    static final String FIELD_SEPARATOR_TRUE = "true";
    static final String FIELD_END = ASPOSE_VENDOR_CSS_PREFIX + "field-end";
    static final String FIELD_END_TRUE = "true";

    static final String IMAGE_WRAP_TYPE = ASPOSE_VENDOR_CSS_PREFIX + "wrap-type";
    static final String IMAGE_LEFT_POS = ASPOSE_VENDOR_CSS_PREFIX + "left-pos";
    static final String IMAGE_TOP_POS = ASPOSE_VENDOR_CSS_PREFIX + "top-pos";
    static final String IMAGE_RELATIVE_H_POS = ASPOSE_VENDOR_CSS_PREFIX + "rel-hpos";
    static final String IMAGE_RELATIVE_V_POS = ASPOSE_VENDOR_CSS_PREFIX + "rel-vpos";

    static final String REVISION_AUTHOR = ASPOSE_VENDOR_CSS_PREFIX + "revision-author";
    static final String REVISION_DATE_TIME = ASPOSE_VENDOR_CSS_PREFIX + "revision-datetime";

    // Every document element which is content control can be marked in
    // output HTML with these css properties to store some roundtrip information.
    static final String SDT_TITLE = ASPOSE_VENDOR_CSS_PREFIX + "sdt-title";
    static final String SDT_TAG = ASPOSE_VENDOR_CSS_PREFIX + "sdt-tag";
    static final String SDT_CONTENT = ASPOSE_VENDOR_CSS_PREFIX + "sdt-content";

    static final String SDT_CONTENT_PLACEHOLDER = "placeholder";

    static final String ORIGINAL_FONT_FAMILY = ASPOSE_VENDOR_CSS_PREFIX + "font-family";
    static final String LIST_LABEL_FONT_WEIGHT = ASPOSE_VENDOR_CSS_PREFIX + "font-weight";
    static final String LIST_LABEL_NUMBER_FORMAT = ASPOSE_VENDOR_CSS_PREFIX + "number-format";

    static final String LIST_LEVEL_NUMBER = ASPOSE_VENDOR_CSS_PREFIX + "list-level-number";
    static final String LIST_NUMBER_FORMAT = ASPOSE_VENDOR_CSS_PREFIX + "list-number-format";
    static final String LIST_NUMBER_STYLES = ASPOSE_VENDOR_CSS_PREFIX + "list-number-styles";
    static final String LIST_NUMBER_VALUES = ASPOSE_VENDOR_CSS_PREFIX + "list-number-values";
    static final String LIST_PADDING_SIMULATION = ASPOSE_VENDOR_CSS_PREFIX + "list-padding-sml";

    static final String IMPORT = ASPOSE_VENDOR_CSS_PREFIX + "import";

    static final String HEIGHT_RULE = ASPOSE_VENDOR_CSS_PREFIX + "height-rule";
    static final String HEIGHT_RULE_EXACTLY = "exactly";

    static final String STYLE_NAME = ASPOSE_VENDOR_CSS_PREFIX + "style-name";
    static final String STYLE_ALIASES = ASPOSE_VENDOR_CSS_PREFIX + "style-aliases";
    static final String STYLE_PARENT = ASPOSE_VENDOR_CSS_PREFIX + "style-parent";

    static final String OUTLINE_LEVEL = ASPOSE_VENDOR_CSS_PREFIX + "outline-level";

    // Obsolete data attributes which were used in previous versions of AW to store roundtrip information.
    // They have same meaning and application as their css counterparts.
    static final String COMMENT_AUTHOR_DATA_ATTRIBUTE = "data-comment-author";
    static final String COMMENT_DATE_TIME_DATA_ATTRIBUTE = "data-comment-datetime";
    static final String COMMENT_INITIAL_DATA_ATTRIBUTE = "data-comment-initial";

    static final String FOOTNOTE_TYPE_ATTRIBUTE = "data-footnote-type";
    static final String FOOTNOTE_NUMBER_STYLE_ATTRIBUTE = "data-footnote-numberstyle";
    static final String FOOTNOTE_START_NUMBER_ATTRIBUTE = "data-footnote-startnumber";

    static final String FOOTNOTE_IS_AUTO_ATTRIBUTE = "data-footnote-isauto";
    static final String HEADER_FOOTER_TYPE_ATTRIBUTE = "data-headerfooter-type";

    /// <summary>
    /// The minimum length between list label and beginning of item text in html (in Word can be zero).
    /// It varies from browser to browser. In IE and Firefox browsers it's equal to 5pt and isn't depended from
    /// a font size but it depends on the font size in Chrome.
    /// </summary>
    static final double LIST_LABEL_TO_TEXT_WIDTH = 5.0;

    /// <summary>
    /// When HtmlSaveOptions.PrettyFormat is on,
    /// in output HTML pseudo-element list item has additional space between list label and text.
    /// Currently it's a constant. And it's equal to <see cref="ListLabelToTextWidth"/>,
    /// because previously <see cref="ListLabelToTextWidth"/> was used (by mistake).
    /// So leave this as is for now.
    /// </summary>
    static final double FIRST_SPACE_WIDTH = LIST_LABEL_TO_TEXT_WIDTH;

    /// <summary>
    /// XML numeric character reference for U+00A0 (NO-BREAK SPACE).
    /// </summary>
    /// <remarks>
    /// We cannot use the '&amp;nbsp;' reference defined in HTML, because it is not valid in XML.
    /// This allows users to load our HTML into XmlDocument even when DTD validation is off. See WORDSNET-1011 
    /// </remarks>
    static final String NO_BREAK_SPACE_HTML_NUMERIC_REF = "&#xa0;";

    // Border widths from the document model may be changed during export to HTML.
    // The following CSS properties are used to preserve original border widths during roundtrip.
    static final String ASPOSE_BORDER_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-width";
    static final String ASPOSE_BORDER_TOP_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-top-width";
    static final String ASPOSE_BORDER_RIGHT_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-right-width";
    static final String ASPOSE_BORDER_BOTTOM_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-bottom-width";
    static final String ASPOSE_BORDER_LEFT_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-left-width";

    // Not all border line styles from the document model can be presented by CSS line styles.
    // The following CSS properties are used to preserve original border line styles during roundtrip.
    static final String ASPOSE_BORDER_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-style";
    static final String ASPOSE_BORDER_TOP_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-top-style";
    static final String ASPOSE_BORDER_RIGHT_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-right-style";
    static final String ASPOSE_BORDER_BOTTOM_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-bottom-style";
    static final String ASPOSE_BORDER_LEFT_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-left-style";

    static final String ASPOSE_BORDER_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-color";
    static final String ASPOSE_BORDER_TOP_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-top-color";
    static final String ASPOSE_BORDER_RIGHT_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-right-color";
    static final String ASPOSE_BORDER_BOTTOM_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-bottom-color";
    static final String ASPOSE_BORDER_LEFT_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-left-color";

    // Shorthand versions of the properties above.
    static final String ASPOSE_BORDER_TOP = ASPOSE_VENDOR_CSS_PREFIX + "border-top";
    static final String ASPOSE_BORDER_RIGHT = ASPOSE_VENDOR_CSS_PREFIX + "border-right";
    static final String ASPOSE_BORDER_BOTTOM = ASPOSE_VENDOR_CSS_PREFIX + "border-bottom";
    static final String ASPOSE_BORDER_LEFT = ASPOSE_VENDOR_CSS_PREFIX + "border-left";

    static final String ASPOSE_BORDER = ASPOSE_VENDOR_CSS_PREFIX + "border";

    static final String ASPOSE_PADDING_TOP = ASPOSE_VENDOR_CSS_PREFIX + "padding-top";
    static final String ASPOSE_PADDING_RIGHT = ASPOSE_VENDOR_CSS_PREFIX + "padding-right";
    static final String ASPOSE_PADDING_BOTTOM = ASPOSE_VENDOR_CSS_PREFIX + "padding-bottom";
    static final String ASPOSE_PADDING_LEFT = ASPOSE_VENDOR_CSS_PREFIX + "padding-left";

    static final String ASPOSE_INSIDE_H = ASPOSE_VENDOR_CSS_PREFIX + "border-insideh";
    static final String ASPOSE_INSIDE_V = ASPOSE_VENDOR_CSS_PREFIX + "border-insidev";

    static final String ASPOSE_BORDER_INSIDE_H_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-insideh-width";
    static final String ASPOSE_BORDER_INSIDE_V_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "border-insidev-width";
    static final String ASPOSE_BORDER_INSIDE_H_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-insideh-style";
    static final String ASPOSE_BORDER_INSIDE_V_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "border-insidev-style";
    static final String ASPOSE_BORDER_INSIDE_H_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-insideh-color";
    static final String ASPOSE_BORDER_INSIDE_V_COLOR = ASPOSE_VENDOR_CSS_PREFIX + "border-insidev-color";

    // Custom CSS properties that we use to preserve frame properties during HTML round trip.
    static final String FRAME_WRAP_TYPE = ASPOSE_VENDOR_CSS_PREFIX + "element-wrap";
    static final String FRAME_RELATIVE_VERTICAL_POSITION = ASPOSE_VENDOR_CSS_PREFIX + "element-anchor-vertical";
    static final String FRAME_RELATIVE_HORIZONTAL_POSITION = ASPOSE_VENDOR_CSS_PREFIX + "element-anchor-horizontal";
    static final String FRAME_HEIGHT_RULE = ASPOSE_VENDOR_CSS_PREFIX + "height-rule";
    static final String FRAME_WIDTH = ASPOSE_VENDOR_CSS_PREFIX + "frame-width";
    static final String FRAME_HEIGHT = ASPOSE_VENDOR_CSS_PREFIX + "frame-height";
    static final String FRAME_TOP = ASPOSE_VENDOR_CSS_PREFIX + "element-top";
    static final String FRAME_LEFT = ASPOSE_VENDOR_CSS_PREFIX + "element-left";
    static final String FRAME_HORIZONTAL_DISTANCE_FROM_TEXT = ASPOSE_VENDOR_CSS_PREFIX + "element-frame-hspace";
    static final String FRAME_VERTICAL_DISTANCE_FROM_TEXT = ASPOSE_VENDOR_CSS_PREFIX + "element-frame-vspace";
    static final String FRAME_LOCK_ANCHOR = ASPOSE_VENDOR_CSS_PREFIX + "element-anchor-lock";

    static final String FRAME_LOCK_ANCHOR_LOCKED_VALUE = "locked";

    // Custom CSS properties that we use to preserve page numbering properties.
    static final String PAGE_SETUP_NUMBER_STYLE = ASPOSE_VENDOR_CSS_PREFIX + "page-numbers-style";
    static final String PAGE_SETUP_CHAPTER_SEPARATOR = ASPOSE_VENDOR_CSS_PREFIX + "page-numbers-chapter-separator";
    static final String PAGE_SETUP_HEADING_LEVEL_FOR_CHAPTER = ASPOSE_VENDOR_CSS_PREFIX + "page-numbers-chapter-level";
    static final String PAGE_SETUP_STARTING_NUMBER = ASPOSE_VENDOR_CSS_PREFIX + "page-numbers-start";

    // Custom CSS properties that we use to preserve page header and footer distances.
    static final String PAGE_SETUP_HEADER_DISTANCE = ASPOSE_VENDOR_CSS_PREFIX + "header-distance";
    static final String PAGE_SETUP_FOOTER_DISTANCE = ASPOSE_VENDOR_CSS_PREFIX + "footer-distance";
}

