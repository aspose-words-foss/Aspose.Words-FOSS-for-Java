// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;
import com.aspose.URI;


/// <summary>
/// Implements the HYPERLINK field
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// When selected, causes control to jump to the location such as a bookmark or a URL.
/// </remarks>
public class FieldHyperlink extends Field implements IFieldCodeTokenInfoProvider, IFieldResultFormatProvider
{
    /*override*/ FieldUpdateAction updateCore()
    {
        if (hasResult() && !isErrorResult())
            return new FieldUpdateActionDoNothing(this);

        String title = getHyperlinkText();
        if (title != null)
            return new FieldUpdateActionApplyResult(this, title);

        return new FieldUpdateActionInsertErrorMessage(this, "Error! Hyperlink reference not valid.");
    }

    /*override*/ void beforeUnlink()
    {
        NodeRange range = getFieldResultRange();
        for (Node node : range)
        {
            Inline inline = ms.as(node, Inline.class);
            if ((inline != null) && (inline.getFont().getStyleIdentifier() == StyleIdentifier.HYPERLINK))
                inline.getRunPr().remove(FontAttr.ISTD);
        }
    }

    private boolean isErrorResult() { return msString.startsWith(getResult(), "Error!", StringComparison.ORDINAL); }

    private String getHyperlinkText()
    {
        String address = getAddressSchemeAware();
        String subAddress = getSubAddress();

        if (StringUtil.hasChars(address) && StringUtil.hasChars(subAddress))
            return msString.format("{0} - {1}", address, subAddress);

        if (StringUtil.hasChars(address))
            return address;

        if (StringUtil.hasChars(subAddress))
            return subAddress;

        return null;
    }

    protected /*override*/ boolean needStoreOldResultNodes()
    {
        return isDirectResultUpdate() || super.needStoreOldResultNodes();
    }

    /// <summary>
    /// Address + "#" + SubAddress. Never null.
    /// </summary>
    URI getHRef() { return new URI(getAddressSchemeAware(), getSubAddress()); }

    private String getAddressSchemeAware()
    {
        return trimAddressScheme(getAddress(), "url");
    }

    private static String trimAddressScheme(String address, String scheme)
    {
        if (msString.isNullOrEmpty(address))
            return address;

        if (!msString.startsWith(address, scheme + ":", StringComparison.ORDINAL))
            return address;

        return msString.remove(address, 0, scheme.length() + 1);
    }

    /// <summary>
    /// Gets or sets the target to which the link should be redirected.
    /// </summary>
    public String getTarget() { return getFieldCodeCache().getSwitchArgumentAsString(TARGET_SWITCH); }
    public void setTarget(String value) throws Exception { getFieldCodeCache().setSwitch(TARGET_SWITCH, value); }

    /// <summary>
    /// Gets or sets a location where this hyperlink jumps.
    /// </summary>
    public String getAddress() { return getFieldCodeCache().getArgumentAsString(ADDRESS_ARGUMENT_INDEX); }
    public void setAddress(String value) throws Exception { getFieldCodeCache().setArgument(ADDRESS_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets a location in the file, such as a bookmark, where this hyperlink jumps.
    /// </summary>
    public String getSubAddress() { return getFieldCodeCache().getSwitchArgumentAsString(SUB_ADDRESS_SWITCH); }
    public void setSubAddress(String value) throws Exception { getFieldCodeCache().setSwitch(SUB_ADDRESS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to append coordinates to the hyperlink for a server-side image map.
    /// </summary>
    public boolean isImageMap() { return getFieldCodeCache().hasSwitch(IS_IMAGE_MAP_SWITCH); }
    public void isImageMap(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_IMAGE_MAP_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to open the destination site in a new web browser window.
    /// </summary>
    public boolean getOpenInNewWindow() { return getFieldCodeCache().hasSwitch(OPEN_IN_NEW_WINDOW_SWITCH); }
    public void setOpenInNewWindow(boolean value) throws Exception { getFieldCodeCache().setSwitch(OPEN_IN_NEW_WINDOW_SWITCH, value); }

    /// <summary>
    /// Gets or sets the ScreenTip text for the hyperlink.
    /// </summary>
    public String getScreenTip() { return getFieldCodeCache().getSwitchArgumentAsString(SCREEN_TIP_SWITCH); }
    public void setScreenTip(String value) throws Exception { getFieldCodeCache().setSwitch(SCREEN_TIP_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the target of the hyperlink should not be added to a list of viewed hyperlinks when the hyperlink is invoked.
    /// </summary>
    /// <remarks>
    /// [MS-OI29500] 2.1.477 Part 1 Section 17.16.5.25, HYPERLINK. -> a.
    /// </remarks>
    boolean getNoHistory() { return getFieldCodeCache().hasSwitch(NO_HISTORY_SWITCH); }
    void setNoHistory(boolean value) throws Exception { getFieldCodeCache().setSwitch(NO_HISTORY_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether a location in the target of the hyperlink that has no bookmarks.
    /// </summary>
    /// <remarks>
    /// [MS-OI29500] 2.1.477 Part 1 Section 17.16.5.25, HYPERLINK. -> b.
    /// </remarks>
    String getDocLocation() { return getFieldCodeCache().getSwitchArgumentAsString(DOC_LOCATION_SWITCH); }
    void setDocLocation(String value) throws Exception { getFieldCodeCache().setSwitch(DOC_LOCATION_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_IMAGE_MAP_SWITCH*/0:
            case /*OPEN_IN_NEW_WINDOW_SWITCH*/1:
            case /*NO_HISTORY_SWITCH*/2:
                return FieldSwitchType.FLAG;
            case /*SUB_ADDRESS_SWITCH*/3:
            case /*SCREEN_TIP_SWITCH*/4:
            case /*TARGET_SWITCH*/5:
            case /*DOC_LOCATION_SWITCH*/6:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    @ReservedForInternalUse @Deprecated
    public Inline /*IFieldResultFormatProvider.*/getSourceNode()
    {
        return null;
    }

    @ReservedForInternalUse @Deprecated
    public IFieldResultFormatApplier /*IFieldResultFormatProvider.*/getFormatApplier()
    {
        // Retain original formatting from last inline node, when the field result is changed through the Field.Result property.
        // Mimic MS Word "Edit Hyperlink" dialog behaviour.
        if (isDirectResultUpdate())
            return RetainingFormatApplier.getFormatApplier(this);

        // Otherwise, apply Hyperlink style.
        return HyperlinkStyleFormatApplier.INSTANCE;
    }

    /// <summary>
    /// Specifies whether the field result is changed through the Field.Result property or not.
    /// </summary>
    private boolean isDirectResultUpdate() { return getUpdateContext() == null; }

    static final int ADDRESS_ARGUMENT_INDEX = 0;

    static final String SUB_ADDRESS_SWITCH = "\\l";
    static final String IS_IMAGE_MAP_SWITCH = "\\m";
    static final String OPEN_IN_NEW_WINDOW_SWITCH = "\\n";
    static final String SCREEN_TIP_SWITCH = "\\o";
    static final String TARGET_SWITCH = "\\t";
    static final String NO_HISTORY_SWITCH = "\\h";
    static final String DOC_LOCATION_SWITCH = "\\s";

    private static class HyperlinkStyleFormatApplier implements IFieldResultFormatApplier
    {
        private HyperlinkStyleFormatApplier()
        {
        }

        public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result)
        {
            for (Node node : result)
            {
                Inline inline = ms.as(node, Inline.class);
                if (inline == null)
                    continue;

                inline.getFont().clearFormatting();
                inline.getFont().setStyleIdentifier(StyleIdentifier.HYPERLINK);
            }
        }

        static /*final*/ HyperlinkStyleFormatApplier INSTANCE = new HyperlinkStyleFormatApplier();
    }

    private static class RetainingFormatApplier implements IFieldResultFormatApplier
    {
        static RetainingFormatApplier getFormatApplier(Field field)
        {
            NodeRange range = field.getFieldResultRange();
            RunPr runPr = null;
            for (Node node : range)
            {
                Inline inline = ms.as(node, Inline.class);
                if (inline == null)
                    continue;

                runPr = inline.getRunPr();
            }

            return new RetainingFormatApplier(runPr);
        }

        private RetainingFormatApplier(RunPr runPr)
        {
            mRunPr = runPr;
        }

        public void applyFormat(NodeRange result)
        {
            if (mRunPr == null)
                return;

            for (Node node : result)
            {
                Inline inline = ms.as(node, Inline.class);
                if (inline == null)
                    continue;

                inline.setRunPr(AttrCollectionExtension.deepClone(mRunPr));
            }
        }

        private /*final*/ RunPr mRunPr;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\m",
		"\\n",
		"\\h",
		"\\l",
		"\\o",
		"\\t",
		"\\s"
	);

}

