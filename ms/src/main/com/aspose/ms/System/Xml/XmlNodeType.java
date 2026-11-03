// ported 31/07/2008 by Konstantin Sidorenko

package com.aspose.ms.System.Xml;

import javax.xml.stream.XMLStreamConstants;

/**
 * Copycat of System.Xml.XmlNodeType.
 *
 * Since ms XmlReader is backed with StAX parser on Java, javax.xml.stream.XMLStreamConstants are
 * used for constants values.
 *
 * Note not mapped constants:
 * 1. Java hasn't analogs for XmlNodeType constants: None, DocumentFragment, SignificantWhitespace,
 *    EndEntity, XmlDeclaration.
 * 2. Java has "spare" constants: END_DOCUMENT, NAMESPACE.
 *
 * Note also important difference in .Net TEXT/WHITESPACE and java CHARACTERS/SPACE:
 * In java events are only reported as SPACE only in prolog/epilog because they are "truly ignorable white spaces".
 * All markup between tags are reported as CHARACTERS. As a result we got in Java CHARACTERS (Text) in places
 * when .Net reports Whitespace (SPACE).
 */
public final class XmlNodeType //JAVA was: enum
{
	public static final int NONE                   = 0;//no analog in XMLStreamConstants
	public static final int ELEMENT                = XMLStreamConstants.START_ELEMENT;//= 1,
	public static final int END_ELEMENT            = XMLStreamConstants.END_ELEMENT;//= 2,
	public static final int PROCESSING_INSTRUCTION = XMLStreamConstants.PROCESSING_INSTRUCTION;//= 3,
	public static final int TEXT                   = XMLStreamConstants.CHARACTERS;//= 4,
	public static final int COMMENT                = XMLStreamConstants.COMMENT;//= 5,
	public static final int WHITESPACE             = XMLStreamConstants.SPACE;//= 6,
	public static final int DOCUMENT               = XMLStreamConstants.START_DOCUMENT;//= 7,
	public static final int ENTITY_REFERENCE       = XMLStreamConstants.ENTITY_REFERENCE;//= 9,
	public static final int ATTRIBUTE              = XMLStreamConstants.ATTRIBUTE;//= 10,
	public static final int DOCUMENT_TYPE          = XMLStreamConstants.DTD;//= 11,
	public static final int CDATA                  = XMLStreamConstants.CDATA;//= 12,
	public static final int NOTATION               = XMLStreamConstants.NOTATION_DECLARATION;//= 14,
	public static final int ENTITY                 = XMLStreamConstants.ENTITY_DECLARATION;//= 15,
//	public static final int DOCUMENT_FRAGMENT      = XMLStreamConstants.;//= 11,
    // Stax parser reports significant whitespace as characters actually.
    // SIGNIFICANT_WHITESPACE not presented in XMLStreamConstants.
    // Original .NET code "14" is already used by XMLStreamConstants.NOTATION_DECLARATION,
    // so take other not used code
	public static final int SIGNIFICANT_WHITESPACE = 24;
//	public static final int END_ENTITY             = XMLStreamConstants.;//= 16,
	public static final int XML_DECLARATION        = 17;//XMLStreamConstants.;//= 17

	/**
	 * JAVA-added for tests. 
	 * Converts javax.xml.stream.XMLEvent int value to its name string.
	 */
	public static String toString(int xmlStreamConstant)
	{
		switch (xmlStreamConstant)
		{
			case XMLStreamConstants.START_ELEMENT:
				return "START_ELEMENT";
			case XMLStreamConstants.END_ELEMENT:
				return "END_ELEMENT";
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				return "PROCESSING_INSTRUCTION";
			case XMLStreamConstants.CHARACTERS:
				return "CHARACTERS";
			case XMLStreamConstants.COMMENT:
				return "COMMENT";
			case XMLStreamConstants.SPACE:
				return "SPACE";
			case XMLStreamConstants.START_DOCUMENT:
				return "START_DOCUMENT";
			case XMLStreamConstants.END_DOCUMENT:
				return "END_DOCUMENT";
			case XMLStreamConstants.ENTITY_REFERENCE:
				return "ENTITY_REFERENCE";
			case XMLStreamConstants.ATTRIBUTE:
				return "ATTRIBUTE";
			case XMLStreamConstants.DTD:
				return "DTD";
			case XMLStreamConstants.CDATA:
				return "CDATA";
			case XMLStreamConstants.NAMESPACE:
				return "NAMESPACE";
			case XMLStreamConstants.NOTATION_DECLARATION:
				return "NOTATION_DECLARATION";
			case XMLStreamConstants.ENTITY_DECLARATION:
				return "ENTITY_DECLARATION";
            // added to emulate .Net SIGNIFICANT_WHITESPACE
			case SIGNIFICANT_WHITESPACE:
				return "SPACE";
			default:
				return "Unknown constant!";
		}
	}
}
