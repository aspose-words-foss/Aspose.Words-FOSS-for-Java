// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2015 by Vadim Polienko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the MERGEBARCODE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Mail merge a barcode.
/// </remarks>
public class FieldMergeBarcode extends Field implements IFieldCodeTokenInfoProvider, IMergeFieldSurrogate
{
    /// <summary>
    /// Gets or sets the barcode value.
    /// </summary>
    public String getBarcodeValue() { return getFieldCodeCache().getArgumentAsString(FieldBarcodeUtil.BARCODE_VALUE_ARGUMENT_INDEX); }
    public void setBarcodeValue(String value) throws Exception { getFieldCodeCache().setArgument(FieldBarcodeUtil.BARCODE_VALUE_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the barcode type (QR, etc.)
    /// </summary>
    public String getBarcodeType() { return getFieldCodeCache().getArgumentAsString(FieldBarcodeUtil.BARCODE_TYPE_ARGUMENT_INDEX); }
    public void setBarcodeType(String value) throws Exception { getFieldCodeCache().setArgument(FieldBarcodeUtil.BARCODE_TYPE_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the height of the symbol. The units are in TWIPS (1/1440 inch).
    /// </summary>
    public String getSymbolHeight() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.SYMBOL_HEIGHT_SWITCH); }
    public void setSymbolHeight(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.SYMBOL_HEIGHT_SWITCH, value); }

    /// <summary>
    /// Gets or sets the rotation of the barcode symbol. Valid values are [0, 3]
    /// </summary>
    public String getSymbolRotation() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.SYMBOL_ROTATION_SWITCH); }
    public void setSymbolRotation(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.SYMBOL_ROTATION_SWITCH, value); }

    /// <summary>
    /// Gets or sets a scaling factor for the symbol. The value is in whole percentage points and the valid values are [10, 1000]
    /// </summary>
    public String getScalingFactor() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.SCALING_FACTOR_SWITCH); }
    public void setScalingFactor(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.SCALING_FACTOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the foreground color of the barcode symbol. Valid values are in the range [0, 0xFFFFFF]
    /// </summary>
    public String getForegroundColor() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.FOREGROUND_COLOR_SWITCH); }
    public void setForegroundColor(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.FOREGROUND_COLOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the background color of the barcode symbol. Valid values are in the range [0, 0xFFFFFF]
    /// </summary>
    public String getBackgroundColor() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.BACKGROUND_COLOR_SWITCH); }
    public void setBackgroundColor(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.BACKGROUND_COLOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the style of a Point of Sale barcode (barcode types UPCA|UPCE|EAN13|EAN8). The valid values (case insensitive) are [STD|SUP2|SUP5|CASE].
    /// </summary>
    public String getPosCodeStyle() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.POS_CODE_STYLE_SWITCH); }
    public void setPosCodeStyle(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.POS_CODE_STYLE_SWITCH, value); }

    /// <summary>
    /// Gets or sets the style of a Case Code for barcode type ITF14. The valid values are [STD|EXT|ADD]
    /// </summary>
    public String getCaseCodeStyle() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.CASE_CODE_STYLE_SWITCH); }
    public void setCaseCodeStyle(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.CASE_CODE_STYLE_SWITCH, value); }

    /// <summary>
    /// Gets or sets an error correction level of QR Code. Valid values are [0, 3].
    /// </summary>
    public String getErrorCorrectionLevel() { return getFieldCodeCache().getSwitchArgumentAsString(FieldBarcodeUtil.ERROR_CORRECTION_LEVEL_SWITCH); }
    public void setErrorCorrectionLevel(String value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.ERROR_CORRECTION_LEVEL_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to display barcode data (text) along with image.
    /// </summary>
    public boolean getDisplayText() { return getFieldCodeCache().hasSwitch(FieldBarcodeUtil.DISPLAY_TEXT_SWITCH); }
    public void setDisplayText(boolean value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.DISPLAY_TEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to add Start/Stop characters for barcode types NW7 and CODE39.
    /// </summary>
    public boolean getAddStartStopChar() { return getFieldCodeCache().hasSwitch(FieldBarcodeUtil.ADD_START_STOP_CHAR_SWITCH); }
    public void setAddStartStopChar(boolean value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.ADD_START_STOP_CHAR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to fix the check digit if it’s invalid.
    /// </summary>
    public boolean getFixCheckDigit() { return getFieldCodeCache().hasSwitch(FieldBarcodeUtil.FIX_CHECK_DIGIT_SWITCH); }
    public void setFixCheckDigit(boolean value) throws Exception { getFieldCodeCache().setSwitch(FieldBarcodeUtil.FIX_CHECK_DIGIT_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        return FieldBarcodeUtil.getSwitchType(switchName);
    }

    @ReservedForInternalUse @Deprecated
    public String /*IMergeFieldSurrogate.*/getMergeFieldName()
    {
        return getBarcodeValue();
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/canWorkAsMergeField()
    {
        return true;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/isMergeValueRequired()
    {
        return true;
    }
}

