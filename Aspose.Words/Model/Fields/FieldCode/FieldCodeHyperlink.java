// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.UriUtil;
import com.aspose.URI;


/// <summary>
/// Parses and composes the HYPERLINK field code.
/// </summary>
class FieldCodeHyperlink
{
    FieldCodeHyperlink()
    {
    }

    /// <summary>
    /// Creates a HYPERLINK field code from parts.
    /// </summary>
    FieldCodeHyperlink(
        String address,
        String subAddress,
        String screenTip,
        String target,
        String docLocation,
        boolean noHistory)
    {
        Debug.assertTrue(address != null);
        Debug.assertTrue(subAddress != null);
        Debug.assertTrue(screenTip != null);
        Debug.assertTrue(target != null);
        Debug.assertTrue(docLocation != null);

        setAddress(address.replace("\"", "%22"));
        setSubAddress(subAddress);
        setScreenTip(screenTip);
        setTarget(target);
        setDocLocation(docLocation);
        setNoHistory(noHistory);
    }

    /// <summary>
    /// Parses a HYPERLINK field code string into its parts.
    /// </summary>
    static FieldCodeHyperlink parse(String fieldCode)
    {
        FieldCodeHyperlink result = new FieldCodeHyperlink();

        FieldCode parser = new FieldCode(fieldCode, new FieldHyperlink());

        result.setAddress(parser.getArgumentAsString(FieldHyperlink.ADDRESS_ARGUMENT_INDEX, true, true));
        result.setSubAddress(parser.getSwitchArgumentAsString(FieldHyperlink.SUB_ADDRESS_SWITCH, true));

        if (parser.hasSwitch(FieldHyperlink.OPEN_IN_NEW_WINDOW_SWITCH))
            result.setTarget(BLANK_TARGET);

        result.setScreenTip(parser.getSwitchArgumentAsString(FieldHyperlink.SCREEN_TIP_SWITCH, true));
        result.setTarget(parser.getSwitchArgumentAsString(FieldHyperlink.TARGET_SWITCH, true));
        result.setDocLocation(parser.getSwitchArgumentAsString(FieldHyperlink.DOC_LOCATION_SWITCH, true));
        result.setNoHistory(parser.hasSwitch(FieldHyperlink.NO_HISTORY_SWITCH));

        return result;
    }

    /// <summary>
    /// Parses a HYPERLINK field code into its parts.
    /// </summary>
    static FieldCodeHyperlink parse(FieldBundle fieldBundle)
    {
        Debug.assertTrue(fieldBundle.getFieldType() == FieldType.FIELD_HYPERLINK);

        FieldHyperlink field = (FieldHyperlink)fieldBundle.getField();

        FieldCodeHyperlink result = new FieldCodeHyperlink();

        result.setAddress(field.getAddress());
        result.setSubAddress(field.getSubAddress());

        if (field.getOpenInNewWindow())
            result.setTarget(BLANK_TARGET);

        result.setScreenTip(field.getScreenTip());
        result.setTarget(field.getTarget());
        result.setDocLocation(field.getDocLocation());
        result.setNoHistory(field.getNoHistory());

        return result;
    }

    /// <summary>
    /// Builds a complete HYPERLINK field code.
    /// </summary>
    String toFieldCodeString()
    {
        StringBuilder fieldCode = new StringBuilder();
        msStringBuilder.append(fieldCode, " HYPERLINK ");

        if (!msString.isNullOrEmpty(getAddress()))
            msStringBuilder.appendFormat(fieldCode, "{0} ", WordUtil.encodeMSWordUri(getAddress()));

        appendSwitch(fieldCode, FieldHyperlink.SUB_ADDRESS_SWITCH, getSubAddress());
        appendSwitch(fieldCode, FieldHyperlink.SCREEN_TIP_SWITCH, getScreenTip());
        appendSwitch(fieldCode, FieldHyperlink.TARGET_SWITCH, getTarget());
        appendSwitch(fieldCode, FieldHyperlink.DOC_LOCATION_SWITCH, getDocLocation());

        if (getNoHistory())
            msStringBuilder.appendFormat(fieldCode, " {0} ", FieldHyperlink.NO_HISTORY_SWITCH);

        return fieldCode.toString();
    }

    private static void appendSwitch(StringBuilder fieldCode, String switchName, String switchValue)
    {
        if (msString.isNullOrEmpty(switchValue))
            return;

        msStringBuilder.appendFormat(fieldCode, "{0} {1} ", switchName, FieldCodeUpdater.encodeArgument(switchValue, true));
    }

    void normalizeAddressAndSubAddress()
    {
        if (msString.isNullOrEmpty(getAddress()))
            return;

        if (getAddress().indexOf('#') == -1)
            return;

        setSubAddress(UriUtil.getSubAddress(getAddress()));
        setAddress(UriUtil.getAddress(getAddress()));
    }

    /// <summary>
    /// Optional hyperlink address. Never null.
    /// </summary>
    String getAddress() { return mAddress; }
    void setAddress(String value) { mAddress = value == null ? "" : value; }

    /// <summary>
    /// Optional hyperlink sub address, specified in the \l switch. Never null.
    /// </summary>
    String getSubAddress() { return mSubAddress; }
    void setSubAddress(String value) { mSubAddress = value == null ? "" : value; }

    /// <summary>
    /// Optional screen type, specified in the \o switch. Never null.
    /// </summary>
    String getScreenTip() { return mScreenTip; }
    void setScreenTip(String value) { mScreenTip = value == null ? "" : value; }

    /// <summary>
    /// Optional target frame, specified in the \t switch. Never null.
    /// </summary>
    String getTarget() { return mTarget; }
    void setTarget(String value) { mTarget = value == null ? "" : value; }

    /// <summary>
    /// Address + "#" + SubAddress. Never null.
    /// </summary>
    URI getHRef() { return new URI(getAddress(), getSubAddress()); }

    /// <summary>
    /// Optional target location, specified in the \s switch. Never null.
    /// </summary>
    String getDocLocation() { return mDocLocation; }
    void setDocLocation(String value) { mDocLocation = (value == null) ? "" : value; }

    /// <summary>
    /// Optional flag, specified by the \h switch.
    /// </summary>
    boolean getNoHistory() { return mNoHistory; }; void setNoHistory(boolean value) { mNoHistory = value; };

    private boolean mNoHistory;

    private String mAddress = "";
    private String mSubAddress = "";
    private String mScreenTip = "";
    private String mTarget = "";
    private String mDocLocation = "";

    private static final String BLANK_TARGET = "_blank";
}

