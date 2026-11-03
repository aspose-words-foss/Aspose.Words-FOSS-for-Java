// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2008 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a form field.
/// In the documents it is known as FfData structure but I figured it is better to use an attribute collection.
/// </summary>
class FormFieldAttr
{
    /// <summary>
    /// bool. Specifies that the current contents of a form field shall be recalculated from their field
    /// codes when the contents of the parent form field is modified.
    /// Default false.
    /// </summary>
    static final int CALC_ON_EXIT = 21000;

    /// <summary>
    /// bool. Specifies the current state for a checkbox form field.
    /// 
    /// If this element is omitted, then the parent form field checkbox has no state,
    /// and its state shall be determined based on the value of the default element in the
    /// checkbox form field properties.
    /// 
    /// Default false.
    /// </summary>
    static final int CHECK_BOX_CHECKED = 21010;

    /// <summary>
    /// bool. Specifies the default checkbox state for the parent checkbox form field.
    /// Default false.
    /// </summary>
    static final int CHECK_BOX_DEFAULT = 21020;

    /// <summary>
    /// int. Specifies the exact size for the parent checkbox form field.
    /// Default 20.
    /// </summary>
    static final int CHECK_BOX_SIZE_HALF_POINTS = 21030;

    /// <summary>
    /// bool. Specifies that the parent checkbox form field shall be formatted using the
    /// point size which is applied to its field characters via the style hierarchy.
    /// Default true.
    /// </summary>
    static final int CHECK_BOX_SIZE_AUTO = 21040;

    /// <summary>
    /// int. Specifies the zero-based index of the default entry for the parent drop-down list form field.
    /// Default 0.
    /// </summary>
    static final int DROP_DOWN_DEFAULT = 21050;

    /// <summary>
    /// <see cref="DropDownItemCollection"/>. The list of drop down entries.
    /// Default null.
    /// </summary>
    static final int DROP_DOWN_ITEMS = 21060;

    /// <summary>
    /// int. Specifies the zero-based index of the currently selected entry
    /// for the parent drop-down list form field.
    /// Default 0.
    /// </summary>
    static final int DROP_DOWN_RESULT = 21070;

    /// <summary>
    /// bool. Specifies whether the parent form field shall behave as though
    /// it is enabled or disabled when it is displayed in the document.
    /// Default true.
    /// </summary>
    static final int ENABLED = 21080;

    /// <summary>
    /// string. Specifies a subroutine in a scripting language which should be executed
    /// when the when the run contents of the parent form field are entered.
    /// Default empty string.
    /// </summary>
    static final int ENTRY_MACRO = 21090;

    /// <summary>
    /// string. Specifies a subroutine in a scripting language which should be executed
    /// when the when the run contents of the parent form field are exited.
    /// Default empty string.
    /// </summary>
    static final int EXIT_MACRO = 21100;

    /// <summary>
    /// string. Specifies optional help text which shall be associated with the parent form field.
    /// Default empty string.
    /// </summary>
    static final int HELP_TEXT = 21110;

    /// <summary>
    /// bool. When true, <see cref="HelpText"/> contains the literal status text for the form field.
    /// When false, <see cref="HelpText"/> contains the name of a glossary document entry which contains
    /// the status text for the form field.
    /// Default false.
    /// </summary>
    static final int OWN_HELP_TEXT = 21112;

    /// <summary>
    /// string. This element specifies the name of the current form field.
    /// Default empty string.
    /// </summary>
    static final int NAME = 21120;

    /// <summary>
    /// string. Specifies optional status text which shall be associated with the parent form field.
    /// Default empty string.
    /// </summary>
    static final int STATUS_TEXT = 21130;

    /// <summary>
    /// bool. When true, <see cref="StatusText"/> contains the literal status text for the form field.
    /// When false, <see cref="StatusText"/> contains the name of a glossary document entry which contains
    /// the status text for the form field.
    /// Default false.
    /// </summary>
    static final int OWN_STATUS_TEXT = 21132;

    /// <summary>
    /// string. Specifies the default string or a calculation expression for the parent text box form field.
    /// Default empty string.
    /// </summary>
    static final int TEXT_INPUT_DEFAULT = 21140;

    /// <summary>
    /// string. Specifies the field formatting which shall be applied to the contents
    /// of the parent form field whenever those contents are modified.
    /// The type of formatting which is applied to the field depends on the value of text input type.
    /// Default empty string.
    /// </summary>
    static final int TEXT_INPUT_FORMAT = 21150;

    /// <summary>
    /// int. Specifies the maximum length of text which should be allowed within the parent text box form
    /// field before any formatting specified by the format element.
    /// Default 0 (means no limit).
    /// </summary>
    static final int TEXT_INPUT_MAX_LENGTH = 21160;

    /// <summary>
    /// <see cref="TextFormFieldType"/> Specifies the type of the contents of the current text box form field.
    /// Default is regular text.
    /// </summary>
    static final int TEXT_INPUT_TYPE = 21170;
}

