// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Form field properties (ffData).
/// Handles all form field types.
/// </summary>
class FormFieldPr extends AttrCollection
{
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    String getName() { return (String)fetchAttr(FormFieldAttr.NAME); }
    void setName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.NAME, truncate(value, MAX_NAME_LENGTH));
    }

    boolean getEnabled() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.ENABLED); }
    void setEnabled(boolean value) { setAttr(FormFieldAttr.ENABLED, value); }

    boolean getCalcOnExit() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.CALC_ON_EXIT); }
    void setCalcOnExit(boolean value) { setAttr(FormFieldAttr.CALC_ON_EXIT, value); }

    String getEntryMacro() { return (String)fetchAttr(FormFieldAttr.ENTRY_MACRO); }
    void setEntryMacro(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.ENTRY_MACRO, truncate(value, MAX_MACRO_NAME_LENGTH));
    }

    String getExitMacro() { return (String)fetchAttr(FormFieldAttr.EXIT_MACRO); }
    void setExitMacro(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.EXIT_MACRO, truncate(value, MAX_MACRO_NAME_LENGTH));
    }

    boolean getOwnHelpText() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.OWN_HELP_TEXT); }
    void setOwnHelpText(boolean value) { setAttr(FormFieldAttr.OWN_HELP_TEXT, value); }

    String getHelpText() { return (String)fetchAttr(FormFieldAttr.HELP_TEXT); }
    void setHelpText(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.HELP_TEXT, truncate(value, MAX_HELP_TEXT_LENGTH));
    }

    boolean getOwnStatusText() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.OWN_STATUS_TEXT); }
    void setOwnStatusText(boolean value) { setAttr(FormFieldAttr.OWN_STATUS_TEXT, value); }

    String getStatusText() { return (String)fetchAttr(FormFieldAttr.STATUS_TEXT); }
    void setStatusText(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.STATUS_TEXT, truncate(value, MAX_STATUS_TEXT_LENGTH));
    }

    int getTextInputMaxLength() { return (/*int*/Integer)fetchAttr(FormFieldAttr.TEXT_INPUT_MAX_LENGTH); }
    void setTextInputMaxLength(int value) { setAttr(FormFieldAttr.TEXT_INPUT_MAX_LENGTH, value); }

    /*TextFormFieldType*/int getTextInputType() { return (/*TextFormFieldType*/Integer)fetchAttr(FormFieldAttr.TEXT_INPUT_TYPE); }
    void setTextInputType(/*TextFormFieldType*/int value) { setAttr(FormFieldAttr.TEXT_INPUT_TYPE, value); }

    String getTextInputDefault() { return (String)fetchAttr(FormFieldAttr.TEXT_INPUT_DEFAULT); }
    void setTextInputDefault(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.TEXT_INPUT_DEFAULT, truncate(value, MAX_TEXT_INPUT_DEFAULT_LENGTH));
    }

    String getTextInputFormat() { return (String)fetchAttr(FormFieldAttr.TEXT_INPUT_FORMAT); }
    void setTextInputFormat(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(FormFieldAttr.TEXT_INPUT_FORMAT, truncate(value, MAX_TEXT_INPUT_FORMAT_LENGTH));
    }

    boolean getCheckBoxSizeAuto() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.CHECK_BOX_SIZE_AUTO); }
    void setCheckBoxSizeAuto(boolean value) { setAttr(FormFieldAttr.CHECK_BOX_SIZE_AUTO, value); }

    int getCheckBoxSizeHalfPoints() { return (/*int*/Integer)fetchAttr(FormFieldAttr.CHECK_BOX_SIZE_HALF_POINTS); }
    void setCheckBoxSizeHalfPoints(int value) { setAttr(FormFieldAttr.CHECK_BOX_SIZE_HALF_POINTS, value); }

    boolean getCheckBoxDefault() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.CHECK_BOX_DEFAULT); }
    void setCheckBoxDefault(boolean value) { setAttr(FormFieldAttr.CHECK_BOX_DEFAULT, value); }

    /// <summary>
    /// Gets the explicit checked value.
    /// </summary>
    boolean getCheckBoxChecked() { return (/*boolean*/Boolean)fetchAttr(FormFieldAttr.CHECK_BOX_CHECKED); }
    void setCheckBoxChecked(boolean value) { setAttr(FormFieldAttr.CHECK_BOX_CHECKED, value); }

    boolean hasCheckBoxChecked() { return contains(FormFieldAttr.CHECK_BOX_CHECKED); }

    int getDropDownDefault() { return (/*int*/Integer)fetchAttr(FormFieldAttr.DROP_DOWN_DEFAULT); }
    void setDropDownDefault(int value) { setAttr(FormFieldAttr.DROP_DOWN_DEFAULT, value); }

    int getDropDownResult() { return (/*int*/Integer)fetchAttr(FormFieldAttr.DROP_DOWN_RESULT); }
    void setDropDownResult(int value) { setAttr(FormFieldAttr.DROP_DOWN_RESULT, value); }

    boolean hasDropDownResult() { return contains(FormFieldAttr.DROP_DOWN_RESULT); }

    /// <summary>
    /// Gets a collection of drop down items.
    /// Automatically creates an empty collection if it does not exist.
    /// </summary>
    DropDownItemCollection getDropDownItems()
    {
        DropDownItemCollection items = (DropDownItemCollection)fetchAttr(FormFieldAttr.DROP_DOWN_ITEMS);
        if (items == null)
        {
            items = new DropDownItemCollection();
            setAttr(FormFieldAttr.DROP_DOWN_ITEMS, items);
        }

        return items;
    }

    /// <summary>
    /// RK Word earlier than 2003 crashes if we specify a string longer than allowed so lets silently truncate.
    /// </summary>
    private static String truncate(String value, int maxLength)
    {
        if (value.length() > maxLength)
            return value.substring((0), (0) + (maxLength));
        else
            return value;
    }

    private static /*final*/ FormFieldPr G_DEFAULTS = buildDefaults();

    private static FormFieldPr buildDefaults()
    {
        FormFieldPr defaults = new FormFieldPr();

        defaults.add(FormFieldAttr.CALC_ON_EXIT, false);
        defaults.add(FormFieldAttr.CHECK_BOX_CHECKED, false);
        defaults.add(FormFieldAttr.CHECK_BOX_DEFAULT, false);
        defaults.add(FormFieldAttr.CHECK_BOX_SIZE_HALF_POINTS, 20);
        defaults.add(FormFieldAttr.CHECK_BOX_SIZE_AUTO, true);
        defaults.add(FormFieldAttr.DROP_DOWN_DEFAULT, 0);
        defaults.add(FormFieldAttr.DROP_DOWN_ITEMS, null);
        defaults.add(FormFieldAttr.DROP_DOWN_RESULT, 0);
        defaults.add(FormFieldAttr.ENABLED, true);
        defaults.add(FormFieldAttr.ENTRY_MACRO, "");
        defaults.add(FormFieldAttr.EXIT_MACRO, "");
        defaults.add(FormFieldAttr.HELP_TEXT, "");
        defaults.add(FormFieldAttr.OWN_HELP_TEXT, false);
        defaults.add(FormFieldAttr.NAME, "");
        defaults.add(FormFieldAttr.STATUS_TEXT, "");
        defaults.add(FormFieldAttr.OWN_STATUS_TEXT, false);
        defaults.add(FormFieldAttr.TEXT_INPUT_DEFAULT, "");
        defaults.add(FormFieldAttr.TEXT_INPUT_FORMAT, "");
        defaults.add(FormFieldAttr.TEXT_INPUT_MAX_LENGTH, 0);
        defaults.add(FormFieldAttr.TEXT_INPUT_TYPE, TextFormFieldType.REGULAR);

        return defaults;
    }

    static final int MAX_NAME_LENGTH = 20;
    static final int MAX_TEXT_INPUT_DEFAULT_LENGTH = 255;
    static final int MAX_TEXT_INPUT_FORMAT_LENGTH = 64;
    static final int MAX_STATUS_TEXT_LENGTH = 138;
    static final int MAX_HELP_TEXT_LENGTH = 255;
    static final int MAX_MACRO_NAME_LENGTH = 32;
}

