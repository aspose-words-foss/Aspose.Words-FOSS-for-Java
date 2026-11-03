// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements the INCLUDETEXT field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts all or part of the text and graphics contained in another document.
/// </remarks>
public class FieldIncludeText extends Field implements IFieldCodeTokenInfoProvider, IFieldIncludeTextCode
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return FieldIncludeTextUpdater.update(this);
    }

    /// <summary>
    /// Gets or sets the location of the document using an IRI.
    /// </summary>
    public String getSourceFullName() { return getFieldCodeCache().getArgumentAsString(SOURCE_FULL_NAME_ARGUMENT_INDEX); }
    public void setSourceFullName(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_FULL_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the name of the bookmark in the document to include.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_INDEX, value); }

    /// <summary>
    /// Gets or sets whether to prevent fields in the included document from being updated.
    /// </summary>
    public boolean getLockFields() { return getFieldCodeCache().hasSwitch(LOCK_FIELDS_SWITCH); }
    public void setLockFields(boolean value) throws Exception { getFieldCodeCache().setSwitch(LOCK_FIELDS_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the text converter for the format of the included file.
    /// </summary>
    public String getTextConverter() { return getFieldCodeCache().getSwitchArgumentAsString(TEXT_CONVERTER_SWITCH); }
    public void setTextConverter(String value) throws Exception { getFieldCodeCache().setSwitch(TEXT_CONVERTER_SWITCH, value); }

    /// <summary>
    /// Gets or sets the namespace mappings for XPath queries.
    /// </summary>
    public String getNamespaceMappings() { return getFieldCodeCache().getSwitchArgumentAsString(NAMESPACE_MAPPINGS_SWITCH); }
    public void setNamespaceMappings(String value) throws Exception { getFieldCodeCache().setSwitch(NAMESPACE_MAPPINGS_SWITCH, value); }

    /// <summary>
    /// Gets or sets the location of XSL Transformation to format XML data.
    /// </summary>
    public String getXslTransformation() { return getFieldCodeCache().getSwitchArgumentAsString(XSL_TRANSFORMATION_SWITCH); }
    public void setXslTransformation(String value) throws Exception { getFieldCodeCache().setSwitch(XSL_TRANSFORMATION_SWITCH, value); }

    /// <summary>
    /// Gets or sets XPath for the desired portion of the XML file.
    /// </summary>
    public String getXPath() { return getFieldCodeCache().getSwitchArgumentAsString(X_PATH_SWITCH); }
    public void setXPath(String value) throws Exception { getFieldCodeCache().setSwitch(X_PATH_SWITCH, value); }

    /// <summary>
    /// Gets or sets the encoding applied to the data within the referenced file.
    /// </summary>
    public String getEncoding() { return getFieldCodeCache().getSwitchArgumentAsString(ENCODING_SWITCH); }
    public void setEncoding(String value) throws Exception { getFieldCodeCache().setSwitch(ENCODING_SWITCH, value); }

    /// <summary>
    /// Gets or sets the MIME type of the referenced file.
    /// </summary>
    public String getMimeType() { return getFieldCodeCache().getSwitchArgumentAsString(MIME_TYPE_SWITCH); }
    public void setMimeType(String value) throws Exception { getFieldCodeCache().setSwitch(MIME_TYPE_SWITCH, value); }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.SourceFullName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.BookmarkName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //boolean IFieldIncludeTextCode.LockFields

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.NamespaceMappings

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.XPath

    @ReservedForInternalUse @Deprecated
    public int /*IFieldIncludeTextCode.*/getSourceFullNameArgumentIndex() { return SOURCE_FULL_NAME_ARGUMENT_INDEX; }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        // DV It is interesting that ECMA has two more switches specified, \e and \m, but they seem to be not recognized
        // in Word and are never mentioned anywhere else.
        switch (gStringSwitchMap.of(switchName))
        {
            case /*LOCK_FIELDS_SWITCH*/0:
                {
                    return FieldSwitchType.FLAG;
                }
            case /*TEXT_CONVERTER_SWITCH*/1:
            case /*ENCODING_SWITCH*/2:
            case /*MIME_TYPE_SWITCH*/3:
            case /*NAMESPACE_MAPPINGS_SWITCH*/4:
            case /*XSL_TRANSFORMATION_SWITCH*/5:
            case /*X_PATH_SWITCH*/6:
                {
                    return FieldSwitchType.HAS_ARGUMENT;
                }
            default:
                {
                    return FieldSwitchType.UNKNOWN;
                }
        }
    }

    private static final int SOURCE_FULL_NAME_ARGUMENT_INDEX = 0;
    private static final int BOOKMARK_NAME_INDEX = 1;

    private static final String LOCK_FIELDS_SWITCH = "\\!";
    private static final String TEXT_CONVERTER_SWITCH = "\\c";
    private static final String ENCODING_SWITCH = "\\e";
    private static final String MIME_TYPE_SWITCH = "\\m";
    private static final String NAMESPACE_MAPPINGS_SWITCH = "\\n";
    private static final String XSL_TRANSFORMATION_SWITCH = "\\t";
    private static final String X_PATH_SWITCH = "\\x";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\!",
		"\\c",
		"\\e",
		"\\m",
		"\\n",
		"\\t",
		"\\x"
	);

}

