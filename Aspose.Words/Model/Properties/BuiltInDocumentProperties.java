// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2005 by Dmitry Vorobyev
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.StringToIntDictionary;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.StringToStringDictionary;


/// <summary>
/// A collection of built-in document properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-document-properties/">Work with Document Properties</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Provides access to <see cref="DocumentProperty"/> objects by their names (using an indexer) and
/// via a set of typed properties that return values of appropriate types.</para>
/// </remarks>
/// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.Common"]/*'/>
/// <seealso cref="Document"/>
/// <seealso cref="Document.BuiltInDocumentProperties"/>
/// <seealso cref="Document.CustomDocumentProperties"/>
public class BuiltInDocumentProperties extends DocumentPropertyCollection
{
    /// <summary>
    /// Ctor.
    /// </summary>
    BuiltInDocumentProperties()
    {
    }

    /// <overloads>Returns a <see cref="DocumentProperty"/> object.</overloads>
    /// <summary>
    /// Returns a <see cref="DocumentProperty"/> object by the name of the property.
    /// </summary>
    /// <remarks>
    /// <para>The string names of the properties correspond to the names of the typed
    /// properties available from <see cref="BuiltInDocumentProperties"/>.</para>
    ///
    /// <para>If you request a property that is not present in the document, but the name
    /// of the property is recognized as a valid built-in name, a new <see cref="DocumentProperty"/>
    /// is created, added to the collection and returned. The newly created property is assigned
    /// a default value (empty string, zero, <c>false</c> or DateTime.MinValue depending on the type
    /// of the built-in property).</para>
    ///
    /// <para>If you request a property that is not present in the document and the name
    /// is not recognized as a built-in name, a <c>null</c> is returned.</para>
    /// </remarks>
    /// <param name="name">The case-insensitive name of the property to retrieve.</param>
    public /*override*/ DocumentProperty get(String name)
    {
        ArgumentUtil.checkHasChars(name, "name");

        String newName = G_OLD_NAME_TO_NEW_NAME.get(name);

        // Use actual (new) name to access or create property. This is required for example if old property name is requested and property does not exist.
        // In this case if use requested name for creating property - it will not be created because gNameToPropertyType is mapped to new names.
        String actualName = (newName == null) ? name : newName;

        DocumentProperty prop = super.get(actualName);

        if (prop == null)
        {
            // A built in property was not found. This could be okay because it could really be missing
            // from the document, in this case create it on demand, but it must be a known name.
            // However, if an unknown built in property was requested, we will return null.
            int propType = G_NAME_TO_PROPERTY_TYPE.get(actualName);
            if (!StringToIntDictionary.isNullSubstitute(propType))
            {
                Object defaultValue = DocumentProperty.getDefaultValueForPropertyType((/*PropertyType*/int)propType);
                prop = add(actualName, defaultValue);
            }
        }

        return prop;
    }

    /// <summary>
    /// Gets or sets the name of the document's author.
    /// </summary>
    public String getAuthor() { return this.get(PropertyName.AUTHOR).toString(); }
    public void setAuthor(String value) { this.get(PropertyName.AUTHOR).fromString(value); }

    /// <summary>
    /// Represents an estimate of the number of bytes in the document.
    /// </summary>
    /// <remarks>
    /// <para>Microsoft Word does not always set this property.</para>
    ///
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public int getBytes() { return this.get(PropertyName.BYTES).toInt(); }
    public void setBytes(int value) { this.get(PropertyName.BYTES).fromInt(value); }

    /// <summary>
    /// Represents an estimate of the number of characters in the document.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public int getCharacters() { return this.get(PropertyName.CHARACTERS).toInt(); }
    public void setCharacters(int value) { this.get(PropertyName.CHARACTERS).fromInt(value); }

    /// <summary>
    /// Represents an estimate of the number of characters (including spaces) in the document.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public int getCharactersWithSpaces() { return this.get(PropertyName.CHARACTERS_WITH_SPACES).toInt(); }
    public void setCharactersWithSpaces(int value) { this.get(PropertyName.CHARACTERS_WITH_SPACES).fromInt(value); }

    /// <summary>
    /// Gets or sets the document comments.
    /// </summary>
    public String getComments() { return this.get(PropertyName.COMMENTS).toString(); }
    public void setComments(String value) { this.get(PropertyName.COMMENTS).fromString(value); }

    /// <summary>
    /// Gets or sets the category of the document.
    /// </summary>
    public String getCategory() { return this.get(PropertyName.CATEGORY).toString(); }
    public void setCategory(String value) { this.get(PropertyName.CATEGORY).fromString(value); }

    /// <summary>
    /// Gets or sets the company property.
    /// </summary>
    public String getCompany() { return this.get(PropertyName.COMPANY).toString(); }
    public void setCompany(String value) { this.get(PropertyName.COMPANY).fromString(value); }

    /// <summary>
    /// Gets or sets date of the document creation in UTC.
    /// </summary>
    /// <remarks>
    /// <para>For documents originated from RTF format this property returns local time of the author's machine at the moment of document creation.</para>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getCreatedTimeInternal() { return this.get(PropertyName.CREATE_TIME).toDateTimeInternal(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getCreatedTime() { return DateTime.toJava(getCreatedTimeInternal()); }
    //JAVA: property accessor is internalized
	void setCreatedTimeInternal(DateTime value) { this.get(PropertyName.CREATE_TIME).fromDateTime(value); }
	//JAVA-added public wrapper for internalized property accessor
	public void setCreatedTime(java.util.Date value) { setCreatedTimeInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Specifies the base string used for evaluating relative hyperlinks in this document.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not use this property.</para>
    /// </remarks>
    public String getHyperlinkBase() { return this.get(PropertyName.HYPERLINK_BASE).toString(); }
    public void setHyperlinkBase(String value) { this.get(PropertyName.HYPERLINK_BASE).fromString(value); }

    /// <summary>
    /// Gets or sets the document keywords.
    /// </summary>
    public String getKeywords() { return this.get(PropertyName.KEYWORDS).toString(); }
    public void setKeywords(String value) { this.get(PropertyName.KEYWORDS).fromString(value); }

    /// <summary>
    /// Gets or sets the date when the document was last printed in UTC.
    /// </summary>
    /// <remarks>
    /// <para>For documents originated from RTF format this property returns the local time of last print operation.</para>
    /// <para>If the document was never printed, this property will return DateTime.MinValue.</para>
    ///
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getLastPrintedInternal() { return this.get(PropertyName.LAST_PRINTED).toDateTimeInternal(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getLastPrinted() { return DateTime.toJava(getLastPrintedInternal()); }
    //JAVA: property accessor is internalized
	void setLastPrintedInternal(DateTime value) { this.get(PropertyName.LAST_PRINTED).fromDateTime(value); }
	//JAVA-added public wrapper for internalized property accessor
	public void setLastPrinted(java.util.Date value) { setLastPrintedInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Gets or sets the name of the last author.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public String getLastSavedBy() { return this.get(PropertyName.LAST_SAVED_BY).toString(); }
    public void setLastSavedBy(String value) { this.get(PropertyName.LAST_SAVED_BY).fromString(value); }

    /// <summary>
    /// Gets or sets the time of the last save in UTC.
    /// </summary>
    /// <remarks>
    /// <para>For documents originated from RTF format this property returns the local time of last save operation.</para>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getLastSavedTimeInternal() { return this.get(PropertyName.LAST_SAVED_TIME).toDateTimeInternal(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getLastSavedTime() { return DateTime.toJava(getLastSavedTimeInternal()); }
    //JAVA: property accessor is internalized
	void setLastSavedTimeInternal(DateTime value) { this.get(PropertyName.LAST_SAVED_TIME).fromDateTime(value); }
	//JAVA-added public wrapper for internalized property accessor
	public void setLastSavedTime(java.util.Date value) { setLastSavedTimeInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Represents an estimate of the number of lines in the document.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public int getLines() { return this.get(PropertyName.LINES).toInt(); }
    public void setLines(int value) { this.get(PropertyName.LINES).fromInt(value); }

    /// <summary>
    /// Indicates whether hyperlinks in a document are up-to-date.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public boolean getLinksUpToDate() { return this.get(PropertyName.LINKS_UP_TO_DATE).toBool(); }
    public void setLinksUpToDate(boolean value) { this.get(PropertyName.LINKS_UP_TO_DATE).fromBool(value); }

    /// <summary>
    /// Indicates whether document thumbnail is cropped or scaled to fit the display.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public boolean getScaleCrop() { return this.get(PropertyName.SCALE_CROP).toBool(); }

    /// <summary>
    /// Indicates whether the document is a shared document.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public boolean getSharedDocument() { return this.get(PropertyName.SHARED_DOC).toBool(); }

    /// <summary>
    /// Indicates whether hyperlinks in a document were changed.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public boolean getHyperlinksChanged() { return this.get(PropertyName.HYPERLINKS_CHANGED).toBool(); }

    /// <summary>
    /// Gets or sets the manager property.
    /// </summary>
    public String getManager() { return this.get(PropertyName.MANAGER).toString(); }
    public void setManager(String value) { this.get(PropertyName.MANAGER).fromString(value); }

    /// <summary>
    /// Gets or sets the name of the application.
    /// </summary>
    public String getNameOfApplication() { return this.get(PropertyName.NAME_OF_APPLICATION).toString(); }
    public void setNameOfApplication(String value) { this.get(PropertyName.NAME_OF_APPLICATION).fromString(value); }

    /// <summary>
    /// Represents an estimate of the number of pages in the document.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words FOSS never updates this property.</para>
    /// </remarks>
    public int getPages() { return this.get(PropertyName.PAGES).toInt(); }
    public void setPages(int value) { this.get(PropertyName.PAGES).fromInt(value); }

    /// <summary>
    /// Represents an estimate of the number of paragraphs in the document.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public int getParagraphs() { return this.get(PropertyName.PARAGRAPHS).toInt(); }
    public void setParagraphs(int value) { this.get(PropertyName.PARAGRAPHS).fromInt(value); }

    /// <summary>
    /// Gets or sets the document revision number.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    /// </remarks>
    public int getRevisionNumber() { return this.get(PropertyName.REVISION_NUMBER).toInt(); }
    public void setRevisionNumber(int value) { this.get(PropertyName.REVISION_NUMBER).fromInt(value); }

    /// <summary>
    /// Specifies the security level of a document as a numeric value.
    /// </summary>
    /// <remarks>
    /// <para>Use this property for informational purposes only because Microsoft Word does not always
    /// set this property. This property is available in DOC and OOXML documents only.</para>
    ///
    /// <para>To protect or unprotect a document use the
    /// <see cref="Document.Protect(ProtectionType, string)"/> and <see cref="Document.Unprotect()"/> methods.</para>
    ///
    /// <para>Aspose.Words updates this property to a correct value before saving a document.</para>
    /// </remarks>
    public /*DocumentSecurity*/int getSecurity() { return (/*DocumentSecurity*/int)this.get(PropertyName.SECURITY).toInt(); }
    public void setSecurity(/*DocumentSecurity*/int value) { this.get(PropertyName.SECURITY).fromInt((int)value); }

    /// <summary>
    /// Gets or sets the subject of the document.
    /// </summary>
    public String getSubject() { return this.get(PropertyName.SUBJECT).toString(); }
    public void setSubject(String value) { this.get(PropertyName.SUBJECT).fromString(value); }

    /// <summary>
    /// Gets or sets the informational name of the document template.
    /// </summary>
    /// <remarks>
    /// <para>In Microsoft Word, this property is for informational purposes only and
    /// usually contains only the file name of the template without the path.</para>
    ///
    /// <para>Empty string means the document is attached to the Normal template.</para>
    ///
    /// <para>To get or set the actual name of the attached template, use the
    /// <see cref="Document.AttachedTemplate"/> property.</para>
    ///
    /// <seealso cref="Document.AttachedTemplate"/>
    /// </remarks>
    public String getTemplate() { return this.get(PropertyName.TEMPLATE).toString(); }
    public void setTemplate(String value) { this.get(PropertyName.TEMPLATE).fromString(value); }

    /// <summary>
    /// <para>Gets or sets the thumbnail of the document.</para>
    /// </summary>
    /// <remarks>
    /// <para>For now this property is used only when a document is being exported to ePub,
    /// it's not read from and written to other document formats.</para>
    ///
    /// <para>Image of arbitrary format can be set to this property, but the format is checked during export.</para>
    ///
    /// <para>Only gif, jpeg and png images can be used for ePub publication.</para>
    /// </remarks>
    /// <exception cref="InvalidOperationException"> Thrown if the image is invalid or its format is unsupported for specific format of document.</exception>
    
    public byte[] getThumbnail() { return this.get(PropertyName.THUMBNAIL).toByteArray(); }
    
    public void setThumbnail(byte[] value) { this.get(PropertyName.THUMBNAIL).fromByteArray(value); }

    /// <summary>
    /// Gets or sets the title of the document.
    /// </summary>
    public String getTitle() { return this.get(PropertyName.TITLE).toString(); }
    public void setTitle(String value) { this.get(PropertyName.TITLE).fromString(value); }

    /// <summary>
    /// Gets or sets the total editing time in minutes.
    /// </summary>
    public int getTotalEditingTime() { return this.get(PropertyName.TOTAL_EDITING_TIME).toInt(); }
    public void setTotalEditingTime(int value) { this.get(PropertyName.TOTAL_EDITING_TIME).fromInt(value); }

    /// <summary>
    /// Gets or sets the content type of the document.
    /// </summary>
    public String getContentType() { return this.get(PropertyName.CONTENT_TYPE).toString(); }
    public void setContentType(String value) { this.get(PropertyName.CONTENT_TYPE).fromString(value); }

    /// <summary>
    /// Gets or sets the content status of the document.
    /// </summary>
    public String getContentStatus() { return this.get(PropertyName.CONTENT_STATUS).toString(); }
    public void setContentStatus(String value) { this.get(PropertyName.CONTENT_STATUS).fromString(value); }

    /// <summary>
    /// Sets the total editing time in minutes to the value if it is in the valid range, otherwise sets it to 0.
    /// </summary>
    void setTotalEditingTimeSafe(int value)
    {
        setTotalEditingTime(((value > 0) && (value < Integer.MAX_VALUE)) ? value : 0);
    }

    /// <summary>
    /// Represents the version number of the application that created the document.
    /// </summary>
    /// <remarks>
    /// <para>When a document was created by Microsoft Word, then high 16 bit represent
    /// the major version and low 16 bit represent the build number.</para>
    /// </remarks>
    public int getVersion() { return this.get(PropertyName.VERSION).toInt(); }
    public void setVersion(int value) { this.get(PropertyName.VERSION).fromInt(value); }

    /// <summary>
    /// Represents an estimate of the number of words in the document.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public int getWords() { return this.get(PropertyName.WORDS).toInt(); }
    public void setWords(int value) { this.get(PropertyName.WORDS).fromInt(value); }

    /// <summary>
    /// Specifies document headings and their names.
    /// </summary>
    /// <remarks>
    /// <para>Every heading pair occupies two elements in this array.</para>
    ///
    /// <para>The first element of the pair is a <see cref="string"/> and specifies the heading name.
    /// The second element of the pair is an <see cref="int"/> and specifies the count of document
    /// parts for this heading in the <see cref="TitlesOfParts"/> property.</para>
    ///
    /// <para>The total sum of counts for all heading pairs in this property must be equal to the
    /// number of elements in the <see cref="TitlesOfParts"/> property.</para>
    ///
    /// <para>Aspose.Words does not update this property.</para>
    ///
    /// <seealso cref="TitlesOfParts"/>
    /// </remarks>
    
    public Object[] getHeadingPairs() { return (Object[])this.get(PropertyName.HEADING_PAIRS).getValueInternal(); }
    
    public void setHeadingPairs(Object[] value) { this.get(PropertyName.HEADING_PAIRS).setValueInternal(value); }

    /// <summary>
    /// Each string in the array specifies the name of a part in the document.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not update this property.</para>
    ///
    /// <seealso cref="HeadingPairs"/>
    /// </remarks>
    
    public String[] getTitlesOfParts() { return (String[])this.get(PropertyName.TITLES_OF_PARTS).getValueInternal(); }
    
    public void setTitlesOfParts(String[] value) { this.get(PropertyName.TITLES_OF_PARTS).setValueInternal(value); }

    /// <summary>
    /// Gets a flag indicating that current document is created in MS Word 2007 or in lower version.
    /// </summary>
    boolean isWord2007OrLower()
    {
        return (("Microsoft Office Word".equals(getNameOfApplication())) || (AssemblyConstants.FAMILY.equals(getNameOfApplication()))) &&
            ((/*MsWordVersionCore*/int)(getVersion() >> 16) <= MsWordVersionCore.WORD_2007);
    }

    /// <summary>
    /// RK MS Word 2007 seems to trim spaces for string properties when saving to most
    /// of the formats (it looks like does not trim in DOC only).
    /// To make all gold ExportImport tests work (and to look more like MS Word) we trim spaces too.
    /// </summary>
    void trimSpaces()
    {
        for (DocumentProperty prop : (Iterable<DocumentProperty>) this)
        {
            switch (prop.getType())
            {
                case PropertyType.STRING:
                {
                    prop.fromString(msString.trim(prop.toString()));
                    break;
                }
                case PropertyType.STRING_ARRAY:
                {
                    String[] values = (String[])prop.getValueInternal();
                    for (int i = 0; i < values.length; i++)
                        values[i] = makeEmptyStringIfAllSpaces(values[i]);
                    break;
                }
                case PropertyType.OBJECT_ARRAY:
                {
                    Object[] values = (Object[])prop.getValueInternal();
                    for (int i = 0; i < values.length; i++)
                    {
                        if (values[i] instanceof String)
                            values[i] = makeEmptyStringIfAllSpaces((String)values[i]);
                    }
                    break;
                }
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    /// <summary>
    /// Internal boolean property value setter.
    /// </summary>
    void setProperty(String propertyName, boolean value)
    {
        this.get(propertyName).fromBool(value);
    }

    private static String makeEmptyStringIfAllSpaces(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
            if (value.charAt(i) != ' ')
                return value;
        }
        return "";
    }

    /*override*/ DocumentPropertyCollection create()
    {
        return new BuiltInDocumentProperties();
    }

    // Pending hyperlinks. See _PID_HLINKS property.
    Hlinks Hlinks;

    /// <summary>
    /// Stores pairs of "old" property names (used by Aspose.Words early versions) and "new" property names (used by MS Word).
    /// Case-insensitive.
    /// </summary>
    private static /*final*/ StringToStringDictionary G_OLD_NAME_TO_NEW_NAME;
    /// <summary>
    /// Stores names of all known built in properties and their data types.
    /// Case-insensitive.
    /// </summary>
    private static /*final*/ StringToIntDictionary G_NAME_TO_PROPERTY_TYPE;

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* BuiltInDocumentProperties()*/
    {
        G_OLD_NAME_TO_NEW_NAME = new StringToStringDictionary(false);
        G_OLD_NAME_TO_NEW_NAME.add("Last Author", PropertyName.LAST_SAVED_BY);
        G_OLD_NAME_TO_NEW_NAME.add("Revision Number", PropertyName.REVISION_NUMBER);
        G_OLD_NAME_TO_NEW_NAME.add("Total Editing Time", PropertyName.TOTAL_EDITING_TIME);
        G_OLD_NAME_TO_NEW_NAME.add("Last Print Date", PropertyName.LAST_PRINTED);
        G_OLD_NAME_TO_NEW_NAME.add("Creation Date", PropertyName.CREATE_TIME);
        G_OLD_NAME_TO_NEW_NAME.add("Last Save Time", PropertyName.LAST_SAVED_TIME);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Pages", PropertyName.PAGES);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Words", PropertyName.WORDS);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Characters", PropertyName.CHARACTERS);
        G_OLD_NAME_TO_NEW_NAME.add("Application Name", PropertyName.NAME_OF_APPLICATION);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Bytes", PropertyName.BYTES);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Lines", PropertyName.LINES);
        G_OLD_NAME_TO_NEW_NAME.add("Number of Paragraphs", PropertyName.PARAGRAPHS);

        G_NAME_TO_PROPERTY_TYPE = new StringToIntDictionary(false);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.TITLE, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.SUBJECT, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.AUTHOR, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.KEYWORDS, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.COMMENTS, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.TEMPLATE, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LAST_SAVED_BY, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.REVISION_NUMBER, (int)PropertyType.NUMBER);    // This is string in OLE storage, but int in the model.
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.TOTAL_EDITING_TIME, (int)PropertyType.NUMBER);  // This is date time in OLE storage, but int minutes in the model.
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LAST_PRINTED, (int)PropertyType.DATE_TIME);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CREATE_TIME, (int)PropertyType.DATE_TIME);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LAST_SAVED_TIME, (int)PropertyType.DATE_TIME);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.PAGES, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.WORDS, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CHARACTERS, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.SECURITY, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.NAME_OF_APPLICATION, (int)PropertyType.STRING);

        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CATEGORY, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.BYTES, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LINES, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.PARAGRAPHS, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.HEADING_PAIRS, (int)PropertyType.OBJECT_ARRAY);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.TITLES_OF_PARTS, (int)PropertyType.STRING_ARRAY);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.MANAGER, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.COMPANY, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LINKS_UP_TO_DATE, (int)PropertyType.BOOLEAN);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.SCALE_CROP, (int)PropertyType.BOOLEAN);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.SHARED_DOC, (int)PropertyType.BOOLEAN);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.HYPERLINKS_CHANGED, (int)PropertyType.BOOLEAN);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CHARACTERS_WITH_SPACES, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.HYPERLINK_BASE, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.VERSION, (int)PropertyType.NUMBER);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CONTENT_STATUS, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.CONTENT_TYPE, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.DOC_VERSION, (int)PropertyType.STRING);
        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.LANGUAGE, (int)PropertyType.STRING);

        G_NAME_TO_PROPERTY_TYPE.add(PropertyName.THUMBNAIL, (int)PropertyType.BYTE_ARRAY);
    }
}

