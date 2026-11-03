// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.CharCase;
import com.aspose.StringUtil;
import com.aspose.NumberFormattingOptions;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.DateTime;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents a single form field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-form-fields/">Working with Form Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Microsoft Word provides the following form fields: checkbox, text input and dropdown (combobox).</p>
///
/// <p><see cref="FormField"/> is an inline-node and can only be a child of <see cref="Paragraph"/>.</p>
///
/// <p><see cref="FormField"/> is represented in a document by a special character and
/// positioned as a character within a line of text.</p>
///
/// <p>A complete form field in a Word document is a complex structure represented by several
/// nodes: field start, field code such as FORMTEXT, form field data, field separator,
/// field result, field end and a bookmark. To programmatically create form fields in a Word document use
/// <see cref="DocumentBuilder.InsertCheckBox(string,bool,int)"/>,
/// <see cref="DocumentBuilder.InsertTextInput"/> and
/// <see cref="DocumentBuilder.InsertComboBox"/> which
/// make sure all of the form field nodes are created in a correct order and in a suitable state.</p>
/// </remarks>
public class FormField extends SpecialChar implements IRunAttrSource
{
    /// <summary>
    /// This is how the form field is represented in the model:
    /// FieldChar - start
    /// BookmarkStart
    /// Run - field code such as " FORMTEXT "
    /// FormField - contains ffdata
    /// FieldChar - separator
    /// Run - could be several, contain field value
    /// FieldChar - field end
    /// BookmarkEnd
    ///
    /// I also find this layout in some cases:
    /// BookmarkStart
    /// FieldChar - start
    /// Run - field code
    /// FormField
    /// FieldChar - separator
    /// Run - field value
    /// FieldChar - field end
    /// BookmarkEnd
    ///
    /// A text input form field that has a formular inside it (calculation) looks like this:
    /// FieldStart - form field
    /// Run - " FORMTEXT "
    ///     FieldStart - formula
    ///     Run - "=100+1000"
    ///     FieldSeparator - formula
    ///     Run - "1100"    - this is not formatted
    ///     FieldEnd - formula
    /// FormField - ffdata
    /// FieldChar - form field
    /// Run - "1,100" - formatted according to the form field format.
    /// FieldEnd - form field
    ///
    /// I could probably auto correct this and make it so bookmark always encompasses all field for example.
    /// </summary>
    FormField(DocumentBase doc, FormFieldPr formFieldPr, RunPr runPr)
    {
    	super(doc, ControlChar.PICTURE_CHAR, runPr);
	
        setFormFieldPr(formFieldPr);
    }

    /// <summary>
    /// Sets run attribute.
    /// </summary>
    /// <remarks>
    /// WORDSNET-522 We re-declare the <see cref="IRunAttrSource.SetRunAttr"/> method to provide the ability
    /// to set the same properties for the FormField, as well as for all field value Inlines.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        getRunPr().setAttr(key, value);

        if (getField() == null)
            return;

        for (Node node : getField().getFieldResultRange())
        {
            Debug.assertTrue(node != this);

            if (node == this)
                continue;

            IRunAttrSource runAttrSource = ms.as(node, IRunAttrSource.class);
            if (runAttrSource == null)
                continue;

            runAttrSource.setRunAttr(key, value);
        }
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        FormField lhs = (FormField)super.deepClone(isCloneChildren, cloningListener);
        lhs.setFormFieldPr(AttrCollectionExtension.deepClone(getFormFieldPr()));
        lhs.mFieldCache = null;
        return lhs;
    }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitFormField"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitFormField(this));
    }

    /// <summary>
    /// Removes the complete form field, not just the form field special character.
    /// </summary>
    /// <remarks>
    /// If there is a bookmark associated with the form field, the bookmark is not removed.
    /// </remarks>
    public void removeField() throws Exception
    {
        // If bookmark start is after the field start, then move the bookmark start to be before
        // the field start. This is to avoid deleting the bookmark start when deleting the field.
        if (getField() == null)
            return;

        BookmarkStart bookmarkStart = ms.as(getField().getStart().getNextSibling(), BookmarkStart.class);
        if (bookmarkStart != null)
        {
            bookmarkStart.remove();
            // because per OOXML spec one type of field (fldSimple) can be child of Sdt.
            getField().getStart().getFirstNonMarkupParentNode().insertBefore(bookmarkStart, getField().getStart());
        }

        getField().remove();
    }

    /// <summary>
    /// Applies the text format specified in <see cref="TextInputFormat"/> and stores the value in <see cref="Result"/>.
    /// </summary>
    /// <param name="newValue">Can be a string, number or a <b>DateTime</b> object.</param>
    /// <remarks>The <see cref="TextInputDefault"/> value is applied if <paramref name="newValue"/> is <c>null</c>.</remarks>
    public void setTextInputValue(Object newValue) throws Exception
    {
        if (getType() != FieldType.FIELD_FORM_TEXT_INPUT)
            throw new IllegalStateException("The form field is not a text form field.");

        if (setNullTextFormFieldResult(newValue))
            return;

        switch (getTextInputType())
        {
            case TextFormFieldType.REGULAR:
            {
                /*CharCase*/int charCase = stringToCharCase(getTextInputFormat());
                setResult(StringUtil.formatCharCase((String)newValue, charCase));
                break;
            }
            case TextFormFieldType.NUMBER:
            {
                FieldOptions fieldOptions = fetchDocument().getFieldOptions();
                /*NumberFormattingOptions*/int formattingOptions =
                    fieldOptions.getNumberFormattingOptions() |
                    NumberFormattingOptions.IGNORE_UNMATCHED_DIGIT_PLACEHOLDER |
                    NumberFormattingOptions.IS_MULTIPLY_PERCENT;
                setResult(FormatterPal.numberToStrMSWord(Convert.toDouble(newValue), getTextInputFormat(), formattingOptions));
                break;
            }
            case TextFormFieldType.DATE:
            {
                setResult(WordUtil.formatDateTime((DateTime)newValue, getTextInputFormat()));
                break;
            }
            case TextFormFieldType.CURRENT_DATE:
            case TextFormFieldType.CURRENT_TIME:
            case TextFormFieldType.CALCULATED:
                throw new IllegalStateException("Cannot set the form field value because it is calculated.");
            default:
                throw new IllegalStateException(UNKNOWN_FORM_FIELD_TYPE);
        }
    }

    private boolean setNullTextFormFieldResult(Object newValue) throws Exception
    {
        if (newValue != null)
            return false;

        switch (getTextInputType())
        {
            case TextFormFieldType.REGULAR:
            case TextFormFieldType.NUMBER:
            case TextFormFieldType.DATE:
                setResult(null);
                return true;
            case TextFormFieldType.CURRENT_DATE:
            case TextFormFieldType.CURRENT_TIME:
            case TextFormFieldType.CALCULATED:
                return false;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static /*CharCase*/int stringToCharCase(String charCase)
    {
        charCase = charCase.toLowerCase();
        switch (gStringSwitchMap.of(charCase))
        {
            case /*"uppercase"*/0:
                return CharCase.UPPER;
            case /*"lowercase"*/1:
                return CharCase.LOWER;
            case /*"first capital"*/2:
                return CharCase.FIRST_CAP;
            case /*"title case"*/3:
                return CharCase.CAPS;
            default:
                return CharCase.DEFAULT;
        }
    }

    /// <summary>
    /// Gets the start of a field that contains this form field node.
    /// </summary>
    private FieldStart getFieldStart()
    {
        // The implementation of this method should be in sync with FieldStart.FormField implementation.

        int level = 0;
        DocumentPosition position = DocumentPosition.createPositionBefore(this);

        while (true)
        {
            Node node = position.getNode();

            switch (node.getNodeType())
            {
                case NodeType.FIELD_START:
                    if (level == 0)
                        return (FieldStart)node;

                    level--;
                    break;

                case NodeType.FIELD_END:
                    level++;
                    break;

                default:
                    // Do nothing.
                    break;
            }

            if (!position.move(null, false, true, true, false, false))
                return null;
        }
    }

    /// <summary>
    /// Copies the properties from the old field value <see cref="Inline"/> to the <see cref="FormField"/> and to the all field value Inlines.
    /// </summary>
    /// <remarks>
    /// WORDSNET-522 We need to copy the properties from the old field value <see cref="Inline"/> to the <see cref="FormField"/>
    /// and to the new field value Inlines, because the old field value Inlines was excluded from the node tree.
    /// </remarks>
    private void copyPropertiesToFormField(Inline oldFieldValueInline)
    {
        if (oldFieldValueInline == null)
            return;

        setRunPr(AttrCollectionExtension.deepClone(oldFieldValueInline.getRunPr()));

        if (getField() == null)
            return;

        for (Node node : getField().getFieldResultRange())
        {
            Debug.assertTrue(node != this);

            if (node == this)
                continue;

            Inline inline = ms.as(node, Inline.class);
            if (inline == null)
                continue;

            inline.setRunPr(AttrCollectionExtension.deepClone(oldFieldValueInline.getRunPr()));
        }
    }

    /// <summary>
    /// Returns <see cref="NodeType.FormField"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.FORM_FIELD; }

    /// <summary>
    /// Gets or sets the form field name.
    /// </summary>
    /// <remarks>
    /// Microsoft Word allows strings with at most 20 characters.
    /// </remarks>
    public String getName() { return getFormFieldPr().getName(); }
    public void setName(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        getFormFieldPr().setName(value);

        BookmarkStart bmkStart = getBookmarkStart();
        if (bmkStart != null)
        {
            BookmarkEnd bmkEnd =
                BookmarkFinder.findBookmarkEnd(bmkStart.getTopmostAncestor(), bmkStart.getName(), bmkStart);
            if (bmkEnd != null)
                bmkStart.getBookmark().setName(getFormFieldPr().getName());
        }
    }

    /// <summary>
    /// Returns the form field type.
    /// </summary>
    
    public /*FieldType*/int getType()
    {
        return (getField() != null) ? getField().getType() : FieldType.FIELD_NONE;
    }

    /// <summary>
    /// Gets the form field type that we use for writing into some formats.
    /// </summary>
    /*FormFieldType*/int getFormFieldType()
    {
        switch (getType())
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
                return FormFieldType.TEXT_INPUT;
            case FieldType.FIELD_FORM_CHECK_BOX:
                return FormFieldType.CHECK_BOX;
            case FieldType.FIELD_FORM_DROP_DOWN:
                return FormFieldType.DROP_DOWN;
            default:
                throw new IllegalStateException(UNKNOWN_FORM_FIELD_TYPE);
        }
    }

    /// <summary>
    /// Gets the field object that contains this form field.
    /// </summary>
    Field getField()
    {
        if (mFieldCache == null)
        {
            FieldStart fieldStart = getFieldStart();
            if (fieldStart != null)
                mFieldCache = fieldStart.getField();
        }

        return mFieldCache;
    }

    /// <summary>
    /// Gets or sets a string that represents the result of this form field.
    /// </summary>
    /// <remarks>
    /// <p>For a text form field the result is the text that is in the field.</p>
    /// <p>For a checkbox form field the result can be "1" or "0" to indicate checked or unchecked.</p>
    /// <p>For a dropdown form field the result is the string selected in the dropdown.</p>
    ///
    /// <p>Setting <see cref="Result"/> for a text form field does not apply the text format
    /// specified in <see cref="TextInputFormat"/>. If you want to set a value and apply the
    /// format, use the <see cref="SetTextInputValue"/> method.</p>
    /// <p>For a text form field the <see cref="TextInputDefault"/> value is applied
    /// if <paramref name="value"/> is <c>null</c>.</p>
    /// </remarks>
    public String getResult()
    {
        // Must do different things depending on the form field type.
        switch (getType())
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
            {
                if (getField() == null)
                    return "";

                String result = getField().getResult(true);
                return (msString.equals(result, DEFAULT_TEXT_INPUT_VALUE)) ? "" : result;
            }
            case FieldType.FIELD_FORM_CHECK_BOX:
                return getChecked() ? "1" : "0";
            case FieldType.FIELD_FORM_DROP_DOWN:
                return getDropDownValue();
            default:
                throw new IllegalStateException(UNKNOWN_FORM_FIELD_TYPE);
        }
    }
    public void setResult(String value) throws Exception
    {
        switch (getType())
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
            {
                // WORDSNET-522 We need to remember the old field value first Inline, because it will be excluded
                // from the node tree and the new field value Inlines will be inserted.
                Inline oldFieldValueFirstInline = getFieldValueFirstInline();

                getField().setResult((value != null ? value : getTextInputDefault()));

                // WORDSNET-522 We need to copy the properties from the old field value first Inline
                // to the FormField and to the new field value Inlines.
                if (oldFieldValueFirstInline != null)
                    copyPropertiesToFormField(oldFieldValueFirstInline);

                break;
            }
            case FieldType.FIELD_FORM_CHECK_BOX:
                ArgumentUtil.checkNotNull(value, "value");

                // RK It is okay to parse like this because we want to throw an exception to the user if he gives us some rubbish.
                setChecked((FormatterPal.parseInt(value) != 0));
                break;
            case FieldType.FIELD_FORM_DROP_DOWN:
                ArgumentUtil.checkNotNull(value, "value");

                setDropDownValue(value);
                break;
            default:
                throw new IllegalStateException(UNKNOWN_FORM_FIELD_TYPE);
        }
    }

    /// <summary>
    /// Returns or sets the text that's displayed in the status bar when a form field has the focus.
    /// </summary>
    /// <remarks>
    /// <p>If the <see cref="OwnStatus"/> property is set to <c>true</c>, the <see cref="StatusText"/> property specifies the status bar text.
    /// If the <see cref="OwnStatus"/> property is set to <c>false</c>, the <see cref="StatusText"/> property specifies the name of an AutoText
    /// entry that contains status bar text for the form field.</p>
    /// <p> Microsoft Word allows strings with at most 138 characters.</p>
    /// </remarks>
    public String getStatusText() { return getFormFieldPr().getStatusText(); }
    public void setStatusText(String value) { getFormFieldPr().setStatusText(value); }

    /// <summary>
    /// Specifies the source of the text that's displayed in the status bar when a form field has the focus.
    /// </summary>
    /// <remarks>
    /// <p>If <c>true</c>, the text specified by the <see cref="StatusText"/> property is displayed.
    /// If <c>false</c>, the text of the AutoText entry specified by the <see cref="StatusText"/> property is displayed.</p>
    /// </remarks>
    public boolean getOwnStatus() { return getFormFieldPr().getOwnStatusText(); }
    public void setOwnStatus(boolean value) { getFormFieldPr().setOwnStatusText(value); }

    /// <summary>
    /// Returns or sets the text that's displayed in a message box when the form field has the focus and the user presses F1.
    /// </summary>
    /// <remarks>
    /// <p>If the <see cref="OwnHelp"/> property is set to <c>true</c>, <see cref="HelpText"/> specifies the text string value.
    /// If <see cref="OwnHelp"/> is set to <c>false</c>, <see cref="HelpText"/> specifies the name of an AutoText entry that contains help
    /// text for the form field.</p>
    /// <p>Microsoft Word allows strings with at most 255 characters.</p>
    /// </remarks>
    public String getHelpText() { return getFormFieldPr().getHelpText(); }
    public void setHelpText(String value) { getFormFieldPr().setHelpText(value); }

    /// <summary>
    /// Specifies the source of the text that's displayed in a message box when a form field has the focus and the user presses F1.
    /// </summary>
    /// <remarks>
    /// <p>If <c>true</c>, the text specified by the <see cref="HelpText"/> property is displayed.
    /// If <c>false</c>, the text in the AutoText entry specified by the <see cref="HelpText"/> property is displayed.</p>
    /// </remarks>
    public boolean getOwnHelp() { return getFormFieldPr().getOwnHelpText(); }
    public void setOwnHelp(boolean value) { getFormFieldPr().setOwnHelpText(value); }

    /// <summary>
    /// True if references to the specified form field are automatically updated whenever the field is exited.
    /// </summary>
    /// <remarks>
    /// <p>Setting <see cref="CalculateOnExit"/> only affects the behavior of the form field when
    /// the document is opened in Microsoft Word. Aspose.Words never updates references
    /// to the form field.</p>
    /// </remarks>
    public boolean getCalculateOnExit() { return getFormFieldPr().getCalcOnExit(); }
    public void setCalculateOnExit(boolean value) { getFormFieldPr().setCalcOnExit(value); }

    /// <summary>
    /// Returns or sets an entry macro name for the form field.
    /// </summary>
    /// <remarks>
    /// <p>The entry macro runs when the form field gets the focus in Microsoft Word.</p>
    /// <p>Microsoft Word allows strings with at most 32 characters.</p>
    /// </remarks>
    public String getEntryMacro() { return getFormFieldPr().getEntryMacro(); }
    public void setEntryMacro(String value) { getFormFieldPr().setEntryMacro(value); }

    /// <summary>
    /// Returns or sets an exit macro name for the form field.
    /// </summary>
    /// <remarks>
    /// <p>The exit macro runs when the form field loses the focus in Microsoft Word.</p>
    /// <p>Microsoft Word allows strings with at most 32 characters.</p>
    /// </remarks>
    public String getExitMacro() { return getFormFieldPr().getExitMacro(); }
    public void setExitMacro(String value) { getFormFieldPr().setExitMacro(value); }

    /// <summary>
    /// True if a form field is enabled.
    /// </summary>
    /// <remarks>
    /// <p>If a form field is enabled, its contents can be changed as the form is filled in.</p>
    /// </remarks>
    public boolean getEnabled() { return getFormFieldPr().getEnabled(); }
    public void setEnabled(boolean value) { getFormFieldPr().setEnabled(value); }

    /// <summary>
    /// Returns or sets the text formatting for a text form field.
    /// </summary>
    /// <remarks>
    /// <p>If the text form field contains regular text, then valid format strings are
    /// "", "UPPERCASE", "LOWERCASE", "FIRST CAPITAL" and "TITLE CASE". The strings
    /// are case-insensitive.</p>
    /// <p>If the text form field contains a number or a date/time value, then valid
    /// format strings are number or date and time format strings.</p>
    /// <p>Microsoft Word allows strings with at most 64 characters.</p>
    /// </remarks>
    public String getTextInputFormat() { return getFormFieldPr().getTextInputFormat(); }
    public void setTextInputFormat(String value) { getFormFieldPr().setTextInputFormat(value); }

    /// <summary>
    /// Gets or sets the type of a text form field.
    /// </summary>
    public /*TextFormFieldType*/int getTextInputType() { return getFormFieldPr().getTextInputType(); }
    public void setTextInputType(/*TextFormFieldType*/int value) { getFormFieldPr().setTextInputType(value); }

    /// <summary>
    /// Gets or sets the default string or a calculation expression of a text form field.
    /// </summary>
    /// <remarks>
    ///
    /// <para>The meaning of this property depends on the value of the <see cref="TextInputType"/> property.</para>
    ///
    /// <para>When <see cref="TextInputType"/> is <see cref="TextFormFieldType.Regular"/> or
    /// <see cref="TextFormFieldType.Number"/>, this string specifies the default string for the text form field.
    /// This string is the content that Microsoft Word will display in the document when the form field is empty.</para>
    ///
    /// <para>When <see cref="TextInputType"/> is <see cref="TextFormFieldType.Calculated"/>, then this string holds
    /// the expression to be calculated. The expression needs to be a formula valid according to Microsoft Word formula field
    /// requirements. When you set a new expression using this property, Aspose.Words calculates the formula result
    /// automatically and inserts it into the form field.</para>
    ///
    /// <para>Microsoft Word allows strings with at most 255 characters.</para>
    /// </remarks>
    public String getTextInputDefault() { return getFormFieldPr().getTextInputDefault(); }
    public void setTextInputDefault(String value) throws Exception
    {
        getFormFieldPr().setTextInputDefault(value);

        if (getTextInputType() == TextFormFieldType.CALCULATED)
        {
            // We are dealing with something like this and we need to remove and insert a completely new inner formula field.
            //
            // FieldStart - form field
            // BookmarkStart - optional
            // Run - " FORMTEXT "
            //     FieldStart - formula
            //     Run - "=100+1000"
            //     FieldSeparator - formula
            //     Run - "1100"    - this is not formatted
            //     FieldEnd - formula
            // FormField - ffdata (this)
            // FieldChar - form field
            // Run - "1,100" - formatted according to the form field format.
            // FieldEnd - form field

            // Jump to the form field code so we can have the range of the formula field and delete it.
            Node startNode = getField().getStart().nextSiblingOfType(NodeType.RUN);
            NodeRemover.remove(startNode, false, this, false);

            // Insert the new inner formula field, no need to update just yet.
            DocumentBuilder builder = new DocumentBuilder(fetchDocument());
            builder.moveTo(this);
            builder.insertField(value, "");

            // Update this field, it will update the inner formula first.
            getField().update();
        }
    }

    /// <summary>
    /// Maximum length for the text field. Zero when the length is not limited.
    /// </summary>
    public int getMaxLength() { return getFormFieldPr().getTextInputMaxLength(); }
    public void setMaxLength(int value) { getFormFieldPr().setTextInputMaxLength(value); }

    /// <summary>
    /// Provides access to the items of a dropdown form field.
    /// </summary>
    /// <remarks>
    /// <p>Microsoft Word allows maximum 25 items in a dropdown form field.</p>
    /// </remarks>
    public DropDownItemCollection getDropDownItems() { return getFormFieldPr().getDropDownItems(); }

    /// <summary>
    /// Gets or sets the index specifying the currently selected item in a dropdown form field.
    /// </summary>
    public int getDropDownSelectedIndex()
    {
        // RK This resolves to default value if needed.
        if (getFormFieldPr().hasDropDownResult())
            return getFormFieldPr().getDropDownResult();
        else
            return getFormFieldPr().getDropDownDefault();
    }
    public void setDropDownSelectedIndex(int value)
    {
        getFormFieldPr().setDropDownResult(value);
    }

    /// <summary>
    /// Gets/sets string selected in the dropdown.
    /// If you specify a string that is not in the dropdown items, nothing happens.
    /// The comparison is case insensitive.
    /// </summary>
    String getDropDownValue()
    {
        int index = getDropDownSelectedIndex();
        if ((index < 0) || (index >= getDropDownItems().getCount()))
            return "";

        return getDropDownItems().get(index);
    }
    void setDropDownValue(String value)
    {
        for (int i = 0; i < getDropDownItems().getCount(); ++i)
        {
            if (StringUtil.equalsIgnoreCase(getDropDownItems().get(i), value))
            {
                setDropDownSelectedIndex(i);
                return;
            }
        }
    }

    /// <summary>
    /// Gets or sets the checked status of the check box form field.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <p>Applicable for a check box form field only.</p>
    /// </remarks>
    public boolean getChecked()
    {
        // RK This resolves to default value if needed.
        if (getFormFieldPr().hasCheckBoxChecked())
            return getFormFieldPr().getCheckBoxChecked();
        else
            return getFormFieldPr().getCheckBoxDefault();
    }
    public void setChecked(boolean value)
    {
        getFormFieldPr().setCheckBoxChecked(value);
    }

    /// <summary>
    /// Gets or sets the default value of the check box form field.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <p>Applicable for a check box form field only.</p>
    /// </remarks>
    public boolean getDefault()
    {
        return getFormFieldPr().getCheckBoxDefault();
    }
    public void setDefault(boolean value)
    {
        getFormFieldPr().setCheckBoxDefault(value);
    }

    /// <summary>
    /// Gets or sets the boolean value that indicates whether the size of the textbox is automatic or specified explicitly.
    /// </summary>
    /// <remarks>
    /// <p>Applicable for a check box form field only.</p>
    /// <seealso cref="CheckBoxSize"/>
    /// </remarks>
    public boolean isCheckBoxExactSize() { return !getFormFieldPr().getCheckBoxSizeAuto(); }
    public void isCheckBoxExactSize(boolean value) { getFormFieldPr().setCheckBoxSizeAuto(!value); }

    /// <summary>
    /// Gets or sets the size of the checkbox in points. Has effect only when <see cref="IsCheckBoxExactSize"/> is <c>true</c>.
    /// </summary>
    /// <remarks>
    /// <p>Applicable for a check box form field only.</p>
    /// <seealso cref="IsCheckBoxExactSize"/>
    /// </remarks>
    public double getCheckBoxSize() { return ConvertUtilCore.halfPointToPoint(getFormFieldPr().getCheckBoxSizeHalfPoints()); }
    public void setCheckBoxSize(double value) { getFormFieldPr().setCheckBoxSizeHalfPoints(ConvertUtilCore.pointToHalfPoint(value)); }

    /// <summary>
    /// Gets the bookmark start associated with this form field.
    /// </summary>
    BookmarkStart getBookmarkStart()
    {
        if (getField() == null)
            return null;

        // Normally bookmark start is immediately after field start.
        BookmarkStart bookmarkStart = ms.as(getField().getStart().getNextSibling(), BookmarkStart.class);
        if (bookmarkStart != null)
            return bookmarkStart;

        // But sometimes it can be immediately before ...
        bookmarkStart = ms.as(getField().getStart().getPreviousSibling(), BookmarkStart.class);
        if (bookmarkStart != null)
            return bookmarkStart;

        // ... or immediately before and above.
        return resolveBookmarkStartBeforeParent(getField().getStart());
    }

    private Inline getFieldValueFirstInline()
    {
        if (getField() == null)
            return null;

        for (Node node : getField().getFieldResultRange())
        {
            Debug.assertTrue(node != this);

            Inline inline = ms.as(node, Inline.class);
            if (inline != null)
                return inline;
        }

        return null;
    }

    private static BookmarkStart resolveBookmarkStartBeforeParent(FieldStart fieldStart)
    {
        if (fieldStart.getParentNode() == null)
            return null;

        if (!fieldStart.isFirstChild())
            return null;

        return ms.as(fieldStart.getParentNode().getPreviousSibling(), BookmarkStart.class);
    }

    /// <summary>
    /// Provides direct access to the form field properties (ffData).
    /// </summary>
    FormFieldPr getFormFieldPr() { return mFormFieldPr; }; private void setFormFieldPr(FormFieldPr value) { mFormFieldPr = value; };

    private FormFieldPr mFormFieldPr;

    private Field mFieldCache;

    /// <summary>
    /// When there is no default there will be maximum 5 circles displayed. Just mimic what MS Word is doing.
    /// </summary>
    static /*final*/ String DEFAULT_TEXT_INPUT_VALUE = msString.newString(ControlChar.DEFAULT_TEXT_INPUT_CHAR, 5);

    private static final String UNKNOWN_FORM_FIELD_TYPE = "Unknown form field type.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"uppercase",
		"lowercase",
		"first capital",
		"title case"
	);

}

