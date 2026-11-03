// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the type of a Word document node.
/// </summary>
/// <dev>
/// <p>Enumerated node types are provided as a common denominator to ensure all functionality
/// is accessible to all clients (including COM applications that might not have full access
/// to the runtime type information).</p>
/// </dev>
public /*enum*/ final class NodeType
{
    private NodeType(){}
    
    /// <summary>
    /// Indicates all node types. Allows to select all children.
    /// </summary>
    public static final int ANY = 0;

    /// <summary>
    /// <p>A <see cref="Aspose.Words.Document"/> object that, as the root of the document tree,
    /// provides access to the entire Word document.</p>
    /// <p>A <see cref="Aspose.Words.Document"/> node can have <see cref="Aspose.Words.Section"/> nodes.</p>
    /// </summary>
    public static final int DOCUMENT = 1;

    /// <summary>
    /// <p>A <see cref="Aspose.Words.Section"/> object that corresponds to one section in a Word document.</p>
    /// <p>A <see cref="Aspose.Words.Section"/> node can have <see cref="Aspose.Words.Body"/> and <see cref="Aspose.Words.HeaderFooter"/> nodes.</p>
    /// </summary>
    public static final int SECTION = 2;

    /// <summary>
    /// <p>A <see cref="Aspose.Words.Body"/> object that contains the main text of a section (main text story).</p>
    /// <p>A <see cref="Aspose.Words.Body"/> node can have <see cref="Aspose.Words.Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int BODY = 3;

    /// <summary>
    /// <p>A <see cref="Aspose.Words.HeaderFooter"/> object that contains text of a particular header or footer inside a section.</p>
    /// <p>A <see cref="Aspose.Words.HeaderFooter"/> node can have <see cref="Aspose.Words.Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int HEADER_FOOTER = 4;

    /// <summary>
    /// <p>A <see cref="Aspose.Words.Tables.Table"/> object that represents a table in a Word document.</p>
    /// <p>A <see cref="Aspose.Words.Tables.Table"/> node can have <see cref="Aspose.Words.Tables.Row"/> nodes.</p>
    /// </summary>
    public static final int TABLE = 5;

    /// <summary>
    /// <p>A row of a table.</p>
    /// <p>A <see cref="Aspose.Words.Tables.Row"/> node can have <see cref="Aspose.Words.Tables.Cell"/> nodes.</p>
    /// </summary>
    public static final int ROW = 6;

    /// <summary>
    /// <p>A cell of a table row.</p>
    /// <p>A <see cref="Aspose.Words.Tables.Cell"/> node can have <see cref="Aspose.Words.Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int CELL = 7;

    /// <summary>
    /// <p>A paragraph of text.</p>
    /// <p>A <see cref="Aspose.Words.Paragraph"/> node is a container for inline level elements
    /// <see cref="Aspose.Words.Run"/>,
    /// <see cref="Aspose.Words.Fields.FieldStart"/>,
    /// <see cref="Aspose.Words.Fields.FieldSeparator"/>,
    /// <see cref="Aspose.Words.Fields.FieldEnd"/>,
    /// <see cref="Aspose.Words.Fields.FormField"/>,
    /// <see cref="Aspose.Words.Drawing.Shape"/>,
    /// <see cref="Aspose.Words.Drawing.GroupShape"/>,
    /// <see cref="Aspose.Words.Notes.Footnote"/>,
    /// <see cref="Aspose.Words.Comment"/>,
    /// <see cref="Aspose.Words.SpecialChar"/>,
    /// as well as <see cref="Aspose.Words.BookmarkStart"/> and <see cref="Aspose.Words.BookmarkEnd"/>.</p>
    /// </summary>
    public static final int PARAGRAPH = 8;

    /// <summary>
    /// <p>A beginning of a bookmark marker.</p>
    /// </summary>
    public static final int BOOKMARK_START = 9;

    /// <summary>
    /// <p>An end of a bookmark marker.</p>
    /// </summary>
    public static final int BOOKMARK_END = 10;

    /// <summary>
    /// <p>A beginning of an editable range.</p>
    /// </summary>
    public static final int EDITABLE_RANGE_START = 11;

    /// <summary>
    /// <p>An end of an editable range.</p>
    /// </summary>
    public static final int EDITABLE_RANGE_END = 12;

    /// <summary>
    /// <p>A beginning of an MoveFrom range.</p>
    /// </summary>
    public static final int MOVE_FROM_RANGE_START = 13;

    /// <summary>
    /// <p>An end of an MoveFrom range.</p>
    /// </summary>
    public static final int MOVE_FROM_RANGE_END = 14;

    /// <summary>
    /// <p>A beginning of an MoveTo range.</p>
    /// </summary>
    public static final int MOVE_TO_RANGE_START = 15;

    /// <summary>
    /// <p>An end of an MoveTo range.</p>
    /// </summary>
    public static final int MOVE_TO_RANGE_END = 16;

    /// <summary>
    /// <p>A group of shapes, images, OLE objects or other group shapes.</p>
    /// <p>A <see cref="Aspose.Words.Drawing.GroupShape"/> node can contain other
    /// <see cref="Aspose.Words.Drawing.Shape"/> and <see cref="Aspose.Words.Drawing.GroupShape"/> nodes.</p>
    /// </summary>
    public static final int GROUP_SHAPE = 17;

    /// <summary>
    /// <p>A drawing object, such as an OfficeArt shape, image or an OLE object.</p>
    /// <p>A <see cref="Aspose.Words.Drawing.Shape"/> node can contain <see cref="Aspose.Words.Paragraph"/>
    /// and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int SHAPE = 18;

    /// <summary>
    /// <p>A comment in a Word document.</p>
    /// <p>A <see cref="Aspose.Words.Comment"/> node can have <see cref="Aspose.Words.Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int COMMENT = 19;

    /// <summary>
    /// <p>A footnote or endnote in a Word document.</p>
    /// <p>A <see cref="Aspose.Words.Notes.Footnote"/> node can have <see cref="Aspose.Words.Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> nodes.</p>
    /// </summary>
    public static final int FOOTNOTE = 20;

    /// <summary>
    /// <p>A run of text.</p>
    /// </summary>
    public static final int RUN = 21;

    /// <summary>
    /// <p>A special character that designates the start of a Word field.</p>
    /// </summary>
    public static final int FIELD_START = 22;

    /// <summary>
    /// <p>A special character that separates the field code from the field result.</p>
    /// </summary>
    public static final int FIELD_SEPARATOR = 23;

    /// <summary>
    /// <p>A special character that designates the end of a Word field.</p>
    /// </summary>
    public static final int FIELD_END = 24;

    /// <summary>
    /// <p>A form field.</p>
    /// </summary>
    public static final int FORM_FIELD = 25;

    /// <summary>
    /// <p>A special character that is not one of the more specific special character types.</p>
    /// </summary>
    public static final int SPECIAL_CHAR = 26;

    /// <summary>
    /// <para>A smart tag around one or more inline structures (runs, images, fields,etc.) within a paragraph</para>
    /// </summary>
    public static final int SMART_TAG = 27;

    /// <summary>
    /// <para>Allows to define customer-specific information and its means of presentation.</para>
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG = 28;

    /// <summary>
    /// <para>A start of <b>ranged</b> structured document tag which accepts multi-sections content.</para>
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG_RANGE_START = 29;

    /// <summary>
    /// <para>A end of <b>ranged</b> structured document tag which accepts multi-sections content.</para>
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG_RANGE_END = 30;

    /// <summary>
    /// <para>A glossary document within the main document.</para>
    /// </summary>
    public static final int GLOSSARY_DOCUMENT = 31;

    /// <summary>
    /// <para>A building block within a glossary document (e.g. glossary document entry).</para>
    /// </summary>
    public static final int BUILDING_BLOCK = 32;

    /// <summary>
    /// A marker node that represents the start of a commented range.
    /// </summary>
    public static final int COMMENT_RANGE_START = 33;

    /// <summary>
    /// A marker node that represents the end of a commented range.
    /// </summary>
    public static final int COMMENT_RANGE_END = 34;

    /// <summary>
    /// <para> An Office Math object. Can be equation, function, matrix or one of other mathematical objects.
    /// Can be a collection of mathematical object and also can contain some non-mathematical objects such as runs of text.</para>
    /// </summary>
    public static final int OFFICE_MATH = 35; 

    /// <summary>
    /// A subdocument node which is a link to another document.
    /// </summary>
    public static final int SUB_DOCUMENT = 36;

    /// <summary>
    /// Reserved for internal use by Aspose.Words.
    /// </summary>
    public static final int SYSTEM = 37;

    /// <summary>
    /// Reserved for internal use by Aspose.Words.
    /// </summary>
    public static final int NULL = 38;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeType value.
    /// </summary>
    public static String getName(/*NodeType*/int nodeType)
    {
    	switch (nodeType)
    	{
    		case ANY: return "ANY";
    		case DOCUMENT: return "DOCUMENT";
    		case SECTION: return "SECTION";
    		case BODY: return "BODY";
    		case HEADER_FOOTER: return "HEADER_FOOTER";
    		case TABLE: return "TABLE";
    		case ROW: return "ROW";
    		case CELL: return "CELL";
    		case PARAGRAPH: return "PARAGRAPH";
    		case BOOKMARK_START: return "BOOKMARK_START";
    		case BOOKMARK_END: return "BOOKMARK_END";
    		case EDITABLE_RANGE_START: return "EDITABLE_RANGE_START";
    		case EDITABLE_RANGE_END: return "EDITABLE_RANGE_END";
    		case MOVE_FROM_RANGE_START: return "MOVE_FROM_RANGE_START";
    		case MOVE_FROM_RANGE_END: return "MOVE_FROM_RANGE_END";
    		case MOVE_TO_RANGE_START: return "MOVE_TO_RANGE_START";
    		case MOVE_TO_RANGE_END: return "MOVE_TO_RANGE_END";
    		case GROUP_SHAPE: return "GROUP_SHAPE";
    		case SHAPE: return "SHAPE";
    		case COMMENT: return "COMMENT";
    		case FOOTNOTE: return "FOOTNOTE";
    		case RUN: return "RUN";
    		case FIELD_START: return "FIELD_START";
    		case FIELD_SEPARATOR: return "FIELD_SEPARATOR";
    		case FIELD_END: return "FIELD_END";
    		case FORM_FIELD: return "FORM_FIELD";
    		case SPECIAL_CHAR: return "SPECIAL_CHAR";
    		case SMART_TAG: return "SMART_TAG";
    		case STRUCTURED_DOCUMENT_TAG: return "STRUCTURED_DOCUMENT_TAG";
    		case STRUCTURED_DOCUMENT_TAG_RANGE_START: return "STRUCTURED_DOCUMENT_TAG_RANGE_START";
    		case STRUCTURED_DOCUMENT_TAG_RANGE_END: return "STRUCTURED_DOCUMENT_TAG_RANGE_END";
    		case GLOSSARY_DOCUMENT: return "GLOSSARY_DOCUMENT";
    		case BUILDING_BLOCK: return "BUILDING_BLOCK";
    		case COMMENT_RANGE_START: return "COMMENT_RANGE_START";
    		case COMMENT_RANGE_END: return "COMMENT_RANGE_END";
    		case OFFICE_MATH: return "OFFICE_MATH";
    		case SUB_DOCUMENT: return "SUB_DOCUMENT";
    		case SYSTEM: return "SYSTEM";
    		case NULL: return "NULL";
    		default: return "Unknown NodeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeType value.
    /// </summary>
    public static String toString(/*NodeType*/int nodeType)
    {
    	switch (nodeType)
    	{
    		case ANY: return "Any";
    		case DOCUMENT: return "Document";
    		case SECTION: return "Section";
    		case BODY: return "Body";
    		case HEADER_FOOTER: return "HeaderFooter";
    		case TABLE: return "Table";
    		case ROW: return "Row";
    		case CELL: return "Cell";
    		case PARAGRAPH: return "Paragraph";
    		case BOOKMARK_START: return "BookmarkStart";
    		case BOOKMARK_END: return "BookmarkEnd";
    		case EDITABLE_RANGE_START: return "EditableRangeStart";
    		case EDITABLE_RANGE_END: return "EditableRangeEnd";
    		case MOVE_FROM_RANGE_START: return "MoveFromRangeStart";
    		case MOVE_FROM_RANGE_END: return "MoveFromRangeEnd";
    		case MOVE_TO_RANGE_START: return "MoveToRangeStart";
    		case MOVE_TO_RANGE_END: return "MoveToRangeEnd";
    		case GROUP_SHAPE: return "GroupShape";
    		case SHAPE: return "Shape";
    		case COMMENT: return "Comment";
    		case FOOTNOTE: return "Footnote";
    		case RUN: return "Run";
    		case FIELD_START: return "FieldStart";
    		case FIELD_SEPARATOR: return "FieldSeparator";
    		case FIELD_END: return "FieldEnd";
    		case FORM_FIELD: return "FormField";
    		case SPECIAL_CHAR: return "SpecialChar";
    		case SMART_TAG: return "SmartTag";
    		case STRUCTURED_DOCUMENT_TAG: return "StructuredDocumentTag";
    		case STRUCTURED_DOCUMENT_TAG_RANGE_START: return "StructuredDocumentTagRangeStart";
    		case STRUCTURED_DOCUMENT_TAG_RANGE_END: return "StructuredDocumentTagRangeEnd";
    		case GLOSSARY_DOCUMENT: return "GlossaryDocument";
    		case BUILDING_BLOCK: return "BuildingBlock";
    		case COMMENT_RANGE_START: return "CommentRangeStart";
    		case COMMENT_RANGE_END: return "CommentRangeEnd";
    		case OFFICE_MATH: return "OfficeMath";
    		case SUB_DOCUMENT: return "SubDocument";
    		case SYSTEM: return "System";
    		case NULL: return "Null";
    		default: return "Unknown NodeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NodeType name.
    /// </summary>
    public static /*NodeType*/int fromName(String nodeTypeName)
    {
    	if ("ANY".equals(nodeTypeName)) return ANY;
    	if ("DOCUMENT".equals(nodeTypeName)) return DOCUMENT;
    	if ("SECTION".equals(nodeTypeName)) return SECTION;
    	if ("BODY".equals(nodeTypeName)) return BODY;
    	if ("HEADER_FOOTER".equals(nodeTypeName)) return HEADER_FOOTER;
    	if ("TABLE".equals(nodeTypeName)) return TABLE;
    	if ("ROW".equals(nodeTypeName)) return ROW;
    	if ("CELL".equals(nodeTypeName)) return CELL;
    	if ("PARAGRAPH".equals(nodeTypeName)) return PARAGRAPH;
    	if ("BOOKMARK_START".equals(nodeTypeName)) return BOOKMARK_START;
    	if ("BOOKMARK_END".equals(nodeTypeName)) return BOOKMARK_END;
    	if ("EDITABLE_RANGE_START".equals(nodeTypeName)) return EDITABLE_RANGE_START;
    	if ("EDITABLE_RANGE_END".equals(nodeTypeName)) return EDITABLE_RANGE_END;
    	if ("MOVE_FROM_RANGE_START".equals(nodeTypeName)) return MOVE_FROM_RANGE_START;
    	if ("MOVE_FROM_RANGE_END".equals(nodeTypeName)) return MOVE_FROM_RANGE_END;
    	if ("MOVE_TO_RANGE_START".equals(nodeTypeName)) return MOVE_TO_RANGE_START;
    	if ("MOVE_TO_RANGE_END".equals(nodeTypeName)) return MOVE_TO_RANGE_END;
    	if ("GROUP_SHAPE".equals(nodeTypeName)) return GROUP_SHAPE;
    	if ("SHAPE".equals(nodeTypeName)) return SHAPE;
    	if ("COMMENT".equals(nodeTypeName)) return COMMENT;
    	if ("FOOTNOTE".equals(nodeTypeName)) return FOOTNOTE;
    	if ("RUN".equals(nodeTypeName)) return RUN;
    	if ("FIELD_START".equals(nodeTypeName)) return FIELD_START;
    	if ("FIELD_SEPARATOR".equals(nodeTypeName)) return FIELD_SEPARATOR;
    	if ("FIELD_END".equals(nodeTypeName)) return FIELD_END;
    	if ("FORM_FIELD".equals(nodeTypeName)) return FORM_FIELD;
    	if ("SPECIAL_CHAR".equals(nodeTypeName)) return SPECIAL_CHAR;
    	if ("SMART_TAG".equals(nodeTypeName)) return SMART_TAG;
    	if ("STRUCTURED_DOCUMENT_TAG".equals(nodeTypeName)) return STRUCTURED_DOCUMENT_TAG;
    	if ("STRUCTURED_DOCUMENT_TAG_RANGE_START".equals(nodeTypeName)) return STRUCTURED_DOCUMENT_TAG_RANGE_START;
    	if ("STRUCTURED_DOCUMENT_TAG_RANGE_END".equals(nodeTypeName)) return STRUCTURED_DOCUMENT_TAG_RANGE_END;
    	if ("GLOSSARY_DOCUMENT".equals(nodeTypeName)) return GLOSSARY_DOCUMENT;
    	if ("BUILDING_BLOCK".equals(nodeTypeName)) return BUILDING_BLOCK;
    	if ("COMMENT_RANGE_START".equals(nodeTypeName)) return COMMENT_RANGE_START;
    	if ("COMMENT_RANGE_END".equals(nodeTypeName)) return COMMENT_RANGE_END;
    	if ("OFFICE_MATH".equals(nodeTypeName)) return OFFICE_MATH;
    	if ("SUB_DOCUMENT".equals(nodeTypeName)) return SUB_DOCUMENT;
    	if ("SYSTEM".equals(nodeTypeName)) return SYSTEM;
    	if ("NULL".equals(nodeTypeName)) return NULL;
    	throw new IllegalArgumentException("Unknown NodeType name.");
    }
    
    /// <summary>
    /// Returns array of NodeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ANY,
    		DOCUMENT,
    		SECTION,
    		BODY,
    		HEADER_FOOTER,
    		TABLE,
    		ROW,
    		CELL,
    		PARAGRAPH,
    		BOOKMARK_START,
    		BOOKMARK_END,
    		EDITABLE_RANGE_START,
    		EDITABLE_RANGE_END,
    		MOVE_FROM_RANGE_START,
    		MOVE_FROM_RANGE_END,
    		MOVE_TO_RANGE_START,
    		MOVE_TO_RANGE_END,
    		GROUP_SHAPE,
    		SHAPE,
    		COMMENT,
    		FOOTNOTE,
    		RUN,
    		FIELD_START,
    		FIELD_SEPARATOR,
    		FIELD_END,
    		FORM_FIELD,
    		SPECIAL_CHAR,
    		SMART_TAG,
    		STRUCTURED_DOCUMENT_TAG,
    		STRUCTURED_DOCUMENT_TAG_RANGE_START,
    		STRUCTURED_DOCUMENT_TAG_RANGE_END,
    		GLOSSARY_DOCUMENT,
    		BUILDING_BLOCK,
    		COMMENT_RANGE_START,
    		COMMENT_RANGE_END,
    		OFFICE_MATH,
    		SUB_DOCUMENT,
    		SYSTEM,
    		NULL,
    	};
    }

    public static final int length = 39;
}

