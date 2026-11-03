// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.Stream;
import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.StackExtension;


/// <summary>
/// Provides methods useful for reading MS Word XML files (DOCX and WordML).
/// Put only MS Word specific methods here.
/// </summary>
class NrxXmlReader extends AnyXmlReader
{
    /// <summary>
    /// Ctor to read from a stream.
    /// </summary>
    NrxXmlReader(Stream stream) throws Exception
    {
    	this(stream, null, WarningSource.NRX);
	
    }

    /// <summary>
    /// Ctor to read from a stream and specifies WarningCallback.
    /// </summary>
    NrxXmlReader(Stream stream, IWarningCallback warningCallback, /*WarningSource*/int warningSource) throws Exception
    {
    	super(stream);
	
        mWarningCallback = warningCallback;
        mWarningSource = warningSource;
    }

    /// <summary>
    /// Ctor to read from a string, possibly a fragment.
    /// </summary>
    /// <param name="xml">This can be an XML document or fragment.</param>
    /// <param name="namespaces">Key is prefix, value is namespace uri. Needed to properly load
    /// fragments that contain elements with prefixes. Optional, can be null.</param>
    NrxXmlReader(String xml, Map<String, String> namespaces)
    {
    	this(xml, namespaces, null, WarningSource.NRX);
	
    }

    /// <summary>
    /// Ctor to read from a string, possibly a fragment and register warnings.
    /// </summary>
    /// <param name="xml">This can be an XML document or fragment.</param>
    /// <param name="namespaces">Key is prefix, value is namespace uri.</param>
    /// <param name="warningCallback">"Callback" for register warning.</param>
    NrxXmlReader(String xml, HashMap<String, String> namespaces, IWarningCallback warningCallback)
    {
    	this(xml, namespaces, warningCallback, WarningSource.NRX);
	

    }

    /// <summary>
    /// Ctor to read from a string, possibly a fragment and specifies WarningCallback.
    /// </summary>
    private NrxXmlReader(String xml, Map<String, String> namespaces, IWarningCallback warningCallback, /*WarningSource*/int warningSource)
    {
    	super(xml, namespaces);
	
        mWarningCallback = warningCallback;
        mWarningSource = warningSource;
    }

    /// <summary>
    /// Skips the current element content and positions reader to the element's end.
    /// Reader should already be positioned on the specified element
    /// (or on an attribute of the element).
    ///
    /// This method is different from XmlTextReader.Skip in the way it positions the cursor.
    /// Skip positions to start of next element, but this method positions to the end of the skipped element.
    /// This is needed because we read elements using ReadChild and it will read next node when called.
    ///
    /// Warning will be produced to the user-provided warning callback.
    /// </summary>
    public /*override*/ void ignoreElement()
    {
        // Create warning message.
        String message = mWarningCallback != null
            ? msString.format("Import of element '{0}' is not supported in {1} format by Aspose.Words.", getLocalName(),
                WarningInfo.warningSourceToString(mWarningSource))
            : null;
        ignoreElement(WarningType.MINOR_FORMATTING_LOSS, mWarningSource, message);
    }

    /// <summary>
    /// Ignore element and warn user with specified warning information.
    /// </summary>
    /// <param name="warningType">Type of a warning that is issued by Aspose.Words during document loading.</param>
    /// <param name="warningSource">Module that produces a warning during document loading.</param>
    /// <param name="description">Warning description.</param>
    void ignoreElement(/*WarningType*/int warningType, /*WarningSource*/int warningSource, String description)
    {
        super.ignoreElement();
        warn(warningType, warningSource, description);
    }

    void ignoreElementUnexpected(boolean isDocx)
    {
        super.ignoreElement();
        warn(WarningType.UNEXPECTED_CONTENT, isDocx ? WarningSource.DOCX : WarningSource.WORD_ML,
            mWarningCallback != null ? msString.format(WarningStrings.UNEXPECTED_ELEMENT, getLocalName()) : null);
    }

    /// <summary>
    /// Ignore element and do no generate warning.
    /// </summary>
    void ignoreElementNoWarn()
    {
        super.ignoreElement();
    }

    /// <summary>
    /// Logs a warning of unexpected attribute to the user-provided warning callback.
    /// </summary>
    private void warnOfUnexpectedAttribute()
    {
        warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX,
            msString.format(WarningStrings.UNEXPECTED_ATTRIBUTE, getLocalName()));
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    void warn(/*WarningType*/int warningType, /*WarningSource*/int warningSource, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(warningType, warningSource, description));
    }

    /// <summary>
    /// Indicates that currently begin read element namespace is well known.
    /// </summary>
    /// <remarks>
    /// Used to distinguish between Microsoft document markup and custom xml markup.
    /// </remarks>
    /*virtual*/ boolean isWordNamespace() { throw new IllegalStateException("Abstract implementation is called."); }

    /// <summary>
    /// Reads the 'id' attribute of the current element.
    /// When the attribute is missing returns null.
    /// </summary>
    String readId()
    {
        return readAttribute("id", null);
    }

    /// <summary>
    /// Reads the 'val' attribute of the current element.
    /// When the attribute is missing returns null.
    /// </summary>
    String readVal()
    {
        return readAttribute("val", null);
    }

    /// <summary>
    /// Reads a 'val' attribute of the integer type.
    /// When the 'val' attribute is missing, returns 0.
    /// </summary>
    int readIntVal()
    {
        String val = readVal();

        if (StringUtil.hasChars(val))
            return FormatterPal.xmlToInt(val);
        else
            return 0;
    }

    /// <summary>
    /// Reads a 'val' attribute of the onOffType.
    /// When the 'val' attribute is missing, returns true.
    /// </summary>
    boolean readBoolVal()
    {
        String val = readVal();

        if (StringUtil.hasChars(val))
            return xmlToBool(val);
        else
            return true;
    }

    /// <summary>
    /// Reads the 'val' attribute of the current element.
    /// If the attribute is not found returns the default value.
    /// </summary>
    String readAttribute(String defaultValue)
    {
        return readAttribute("val", defaultValue);
    }

    /// <summary>
    /// Reads a 'val' attribute of the integer type.
    /// If the attribute is not found returns the default value.
    /// </summary>
    int readIntAttribute(int defaultValue)
    {
        return readIntAttribute("val", defaultValue);
    }

    /// <summary>
    /// Reads a 'val' attribute of the double type.
    /// If the attribute is not found returns the default value.
    /// </summary>
    double readDoubleAttribute(double defaultValue)
    {
        return readDoubleAttribute("val", defaultValue);
    }

    /// <summary>
    /// Reads a 'val' attribute of the onOffType.
    /// If the attribute is not found returns the default value.
    /// </summary>
    boolean readBoolAttribute(boolean defaultValue)
    {
        return readBoolAttribute("val", defaultValue);
    }

    /// <summary>
    /// Reads a 'val' attribute of the onOffType for an attribute that can be on/off or toggle.
    /// When the 'val' attribute is missing, return true.
    /// </summary>
    AttrBoolEx readBoolExVal()
    {
        String val = readVal();

        if (StringUtil.hasChars(val))
            return AttrBoolEx.fromBool(xmlToBool(val));
        else
            return AttrBoolEx.TRUE;
    }

    /// <summary>
    /// Reads contents of an element as a boolean "true", "t" or "false", "f".
    /// For other values the default value is returned.
    /// </summary>
    boolean readBoolString(boolean defaultValue)
    {
        switch (gStringSwitchMap.of(readString()))
        {
            case /*"true"*/0:
            case /*"t"*/1:
                return true;
            case /*"false"*/2:
            case /*"f"*/3:
                return false;
            default:
                return defaultValue;
        }
    }

    /// <summary>
    /// Reads a w:sym element. Returns the character read and also sets SymbolFonts in the specified runPr.
    /// </summary>
    char readSymbol(RunPr runPr)
    {
        // MS Word considers document to be invalid if 'w:font' or 'w:char' attributes not set.
        // We are resilient and will just import default values.
        char sym = '\uf0ff'; // 'empty square' sign
        String font = "Symbol";

        while (moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getLocalName()))
            {
                case /*"font"*/4:
                    font = getValue();
                    break;
                case /*"char"*/5:
                    sym = (char)NrxXmlUtil.hexToInt(getValue());
                    break;
                default:
                    warnOfUnexpectedAttribute();
                    break;
            }
        }

        runPr.setSymbolFonts(font);

        return sym;
    }

    Border readBorder()
    {
        Border border = new Border();
        boolean isAuto = true;

        while (moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getLocalName()))
            {
                case /*"val"*/6:
                    // This allows us to distinguish "nil" from "none" string because in
                    // the line style we only have None. I don't want Nil in the line style.
                    if ("nil".equals(getValue()))
                        return Border.createNilBorder();

                    border.setLineStyleInternal(readLineStyle());
                    break;
                case /*"sz"*/7:
                    border.setRawLineWidth(getValueAsInt());
                    break;
                case /*"space"*/8:
                    border.setRawDistanceFromText(getValueAsInt());
                    break;
                case /*"color"*/9:
                    isAuto = ("auto".equals(getValue()));
                    border.setColorInternal(NrxXmlUtil.xmlToColor(getValue()));
                    break;
                case /*"shadow"*/10:
                    border.setShadow(getValueAsBool());
                    break;
                case /*"frame"*/11:
                    border.setFrame(getValueAsBool());
                    break;
                case /*"themeColor"*/12:
                    border.setThemeColorInternal(getValue());
                    break;
                case /*"themeShade"*/13:
                    border.setThemeShade(getValue());
                    break;
                case /*"themeTint"*/14:
                    border.setThemeTint(getValue());
                    break;
                case /*"bdrwidth"*/15: // Not supported attribute of WML 2003 format
                    warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.NRX,
                        msString.format(WarningStrings.NOT_SUPPORTED_ATTRIBUTE, getLocalName()));
                    break;
                default:
                    warnOfUnexpectedAttribute();
                    break;
            }
        }

        if (border.getLineStyle() == LineStyle.NONE && !isAuto)
        {
            // Cell inherits table border only if color is 'auto' and both space and sz is 0. See TestJira8023A for example.
            border.isNil(true);
        }

        return border;
    }

    Shading readShading()
    {
        Shading shading = new Shading();

        while (moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getLocalName()))
            {
                case /*"val"*/6:
                    shading.setTexture(StyleConvertUtil.xmlToTextureIndex(getValue()));
                    break;
                case /*"color"*/9:
                    shading.setForegroundPatternColorInternal(NrxXmlUtil.xmlToColor(getValue()));
                    break;
                case /*"fill"*/16:
                    shading.setBackgroundPatternColorInternal(NrxXmlUtil.xmlToColor(getValue()));
                    break;
                case /*"themeColor"*/12:
                    shading.setThemeColor(getValue());
                    break;
                case /*"themeShade"*/13:
                    shading.setThemeShade(getValue());
                    break;
                case /*"themeTint"*/14:
                    shading.setThemeTint(getValue());
                    break;
                case /*"themeFill"*/17:
                    shading.setThemeFill(getValue());
                    break;
                case /*"themeFillShade"*/18:
                    shading.setThemeFillShade(getValue());
                    break;
                case /*"themeFillTint"*/19:
                    shading.setThemeFillTint(getValue());
                    break;
                default:
                    warnOfUnexpectedAttribute();
                    break;
            }
        }

        return shading;
    }

    /// <summary>
    /// Reads length value in twips.
    /// </summary>
    int readLengthInTwips(OoxmlComplianceInfo cInfo)
    {
        return readLength(cInfo).getValueRaw();
    }

    /// <summary>
    /// Reads length element.
    /// </summary>
    PreferredWidth readLength(OoxmlComplianceInfo cInfo)
    {
        // If w:w attribute is not specified MS Word defaults it to "0".
        int w = 0;

        // If w:type attribute is not specified and if w:w attribute has no unit MS Word defaults it to "auto".
        /*PreferredWidthType*/int type = PreferredWidthType.AUTO;

        boolean isPercentage = false;
        boolean isUniversalMeasure = false;
        boolean hasType = false;

        while (moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getLocalName()))
            {
                case /*"w"*/20:
                    {
                        isPercentage = isPercentage(getValue());
                        isUniversalMeasure = !isPercentage && isUniversalMeasure(getValue());
                        if (isUniversalMeasure)
                        {
                            // Conversion to twips
                            w = getValueAsTwips(cInfo);
                            if (cInfo != null)
                                cInfo.markAsIsoTransitional();
                        }
                        else if (isPercentage)
                        {
                            // Conversion to 50ths of percent
                            String num = getValue().substring((0), (0) + (getValue().length() - 1));
                            w = MathUtil.doubleToInt(Math.floor(FormatterPal.parseDouble(num)) * PreferredWidth.PERCENT_FACTOR);
                            if (cInfo != null)
                                cInfo.markAsIsoTransitional();
                        }
                        else
                        {
                            w = FormatterPal.xmlToShortInt(getValue());
                        }
                        break;
                    }
                case /*"type"*/21:
                    type = StyleConvertUtil.xmlToLengthType(getValue());
                    hasType = true;
                    break;
                default:
                    warnOfUnexpectedAttribute();
                    break;
            }
        }

        // If value of the 'type' attribute and actual measurement specified by the 'w' attribute are
        // contradictory, the type specified by the 'type' attribute shall be ignored.
        // MS Word uses the 'pt' unit in values of the 'w' attribute when the type attribute is 'auto'.
        // So, correct the type if needed.
        // WORDSNET-13442 In case when length value contains universal measure unit and type of measure unit is "nil"
        // then length can not be ignored, so type "nil"(0) updated to points (after re-savings MSW store length in points).
        // WORDSNET-19279 If the type is missing, then the type is a points.
        if (isPercentage)
            type = PreferredWidthType.PERCENT;
        else if (!hasType || (isUniversalMeasure && ((type == PreferredWidthType.PERCENT) || (type == 0))))
            type = PreferredWidthType.POINTS;

        return PreferredWidth.fromRawSafe(type, w);
    }

    /// <summary>
    /// Reads value as universal measure from XML and converts it into Half-points.
    /// </summary>
    int readValAsHalfPoints(OoxmlComplianceInfo complianceInfo)
    {
        return MathUtil.doubleToInt(convertUniversalMeasure(readVal(), NrxUnit.HALF_POINTS, complianceInfo));
    }

    /// <summary>
    /// Reads value as universal measure from XML and converts it into twips.
    /// </summary>
    int readValAsTwips(OoxmlComplianceInfo complianceInfo)
    {
        return MathUtil.doubleToInt(convertUniversalMeasure(readVal(), NrxUnit.TWIPS, complianceInfo));
    }

    /// <summary>
    /// Reads a value of the attribute with the specified name as universal measure.
    /// Converts value to English Metric Units.
    /// Returns the default value, if the attribute is not found.
    /// </summary>
    public double readAttributeAsEmus(String localName, double defaultValue, OoxmlComplianceInfo complianceInfo)
    {
        String strValue = readAttribute(localName, null);
        double result = tryConvertUniversalMeasureToEmus(strValue, complianceInfo);
        return (!Double.isNaN(result)) ? result : defaultValue;
    }

    /// <summary>
    /// Exposed mainly for testing. Not recommended for use.
    /// Instead use <see cref="ReadAttributeAsEmus"/>, <see cref="ReadValAsTwips"/> and <see cref="ReadValAsHalfPoints"/>
    /// to read measures from OOXML data.
    /// </summary>
    /// <remarks>WORDSNET-10435 Returns double instead of float, as it can be a very large values.</remarks>
    static double convertUniversalMeasure(String value, /*NrxUnit*/int targetUnit,
        OoxmlComplianceInfo complianceInfo)
    {
        double result = tryConvertUniversalMeasure(value, targetUnit, complianceInfo);
        return (!Double.isNaN(result)) ? result : 0.0;
    }

    static double convertUniversalMeasure(String p, /*NrxUnit*/int nrxUnit)
    {
        return convertUniversalMeasure(p, nrxUnit, null);
    }

    /// <summary>
    /// Tries to convert input string that may contain number or ST_UniversalMeasure to target units.
    /// If units are not specified in the input string, <paramref name="targetUnit"/> are expected.
    /// If the input string contains wrong value, Double.Nan is returned.
    /// </summary>
    /// <param name="value">Source string value.</param>
    /// <param name="targetUnit">Unit to which result value will be converted.</param>
    /// <param name="complianceInfo">Allows setting compliance info about the document.</param>
    /// <returns>Result value in <paramref name="targetUnit"/> or Double.NaN if the conversion has failed.</returns>
    private static double tryConvertUniversalMeasure(String value, /*NrxUnit*/int targetUnit,
        OoxmlComplianceInfo complianceInfo)
    {
        double result = Double.NaN;
        if (StringUtil.hasChars(value))
        {
            // The index of a character where number part ends and units part starts,
            // that is index of the last character of the unit (which is before digit).
            int splitIndex;
            for (splitIndex = value.length() - 1; splitIndex >= 0; splitIndex--)
            {
                if (value.charAt(splitIndex) >= '0' && value.charAt(splitIndex) <= '9')
                    break;
            }

            String numericPart = value.substring((0), (0) + (splitIndex + 1));
            if (!StringUtil.hasChars(numericPart))
                return Double.NaN;

            boolean valueHasUnitsPart = (splitIndex < value.length() - 1);

            double numericVal = FormatterPal.tryParseDoubleInvariant(numericPart);
            if (Double.isNaN(numericVal))
                return Double.NaN;

            // WORDSNET-11357 Should truncate values that have not units part.
            if (!valueHasUnitsPart)
                numericVal = MathUtil.castDoubleToInt(numericVal);

            /*NrxUnit*/int sourceUnit = unitFromString(
                value.substring((splitIndex + 1), (splitIndex + 1) + (value.length() - splitIndex - 1)), targetUnit);

            // WORDSNET-25829 Read zero value in case of invalid measurement.
            if (sourceUnit == NrxUnit.NONE)
                result = 0.0;
            else
            {
                result = NrxXmlUtil.convertMeasure(numericVal, sourceUnit, targetUnit);

                if (valueHasUnitsPart && complianceInfo != null)
                    complianceInfo.markAsIsoTransitional();
            }
        }
        return result;
    }

    /// <summary>
    /// Tries to convert input string that may contain number or ST_UniversalMeasure to English Metric Units.
    /// If units are not specified in the input string, English Metric Units are expected. If the input string
    /// contains wrong value, Double.Nan is returned.
    /// </summary>
    /// <param name="value">Source string value.</param>
    /// <param name="complianceInfo">Allows setting compliance info about the document.</param>
    /// <returns>Result value in EMUs or Double.NaN if the conversion has failed.</returns>
    static double tryConvertUniversalMeasureToEmus(String value, OoxmlComplianceInfo complianceInfo)
    {
        return tryConvertUniversalMeasure(value, NrxUnit.EMUS, complianceInfo);
    }

    /// <summary>
    /// Handles both decimal and percentage data types.
    /// This function returns int or rounded percent value as int.
    /// </summary>
    /// <remarks>
    /// Iso29500 introduces several types where a value can be percent value:
    /// ST_DecimalNumberOrPercent (this includes decimal number or percent),
    /// ST_Percentage, ST_FixedPercentage, ST_PositiveFixedPercentage.
    /// Note that these types stores values in 1000ths of percent if the percent sign is not specified. This method
    /// cannot be used for the types.
    /// The format, which is supported by this method, is like in <see cref="FormatterPal.ParseDouble"/> but
    /// followed by "%" sign.
    /// In AW model XmlToInt was used for ECMA-376 for lots of attributes into Model.
    /// Because of that AW DOM contains lots of int-valued attributes, whereas spec now allows two
    /// digits after floating point.
    /// We will still return rounded int, thus bringing small round-off error into the numeric data.
    /// </remarks>
    static int xmlToPercent(String val, OoxmlComplianceInfo cInfo)
    {
        if (isPercentage(val))
        {
            if (cInfo != null)
                cInfo.markAsIsoTransitional();

            return MathUtil.doubleToInt(FormatterPal.parseDouble(val.substring((0), (0) + (val.length() - 1))));
        }
        else
        {
            return FormatterPal.xmlToInt(val);
        }
    }

    /// <summary>
    /// Reads cell spacing. Returns 'null' if unit type is 'nil' or missed.
    /// </summary>
    PreferredWidth readCellSpacing(OoxmlComplianceInfo cInfo)
    {
        return !"nil".equals(readAttribute("type", "nil")) ? readLength(cInfo) : null;
    }

    protected /*virtual*/ /*LineStyle*/int readLineStyle()
    {
        return LineStyle.NONE;
    }

    private static boolean isPercentage(String val)
    {
        return (val.length() >= 1) && (val.charAt(val.length() - 1) == '%');
    }

    /// <summary>
    /// Returns true if the input value meets the demands of the ST_UniversalMeasure simple type.
    /// </summary>
    static boolean isUniversalMeasure(String value)
    {
        if (!StringUtil.hasChars(value) || StringUtil.isDigit(value.charAt(value.length() - 1)))
            return false;
        if (gUniversalMeasureRegex == null)
            gUniversalMeasureRegex = new Regex("-?[0-9]+(\\.[0-9]+)?(mm|cm|in|pt|pc|pi)", RegexOptions.COMPILED);
        return gUniversalMeasureRegex.isMatch(value);
    }

    /// <summary>
    /// Convert source string units into enum <see cref="NrxUnit"/>
    /// </summary>
    private static /*NrxUnit*/int unitFromString(String unit, /*NrxUnit*/int targetUnit)
    {
        /*NrxUnit*/int result;
        switch (gStringSwitchMap.of(unit))
        {
            case /*"in"*/22:
                result = NrxUnit.INCH;
                break;
            case /*"pt"*/23:
                result = NrxUnit.POINT;
                break;
            case /*"mm"*/24:
                result = NrxUnit.MILLIMETER;
                break;
            case /*"cm"*/25:
                result = NrxUnit.CENTIMETER;
                break;
            case /*"pc"*/26:
            case /*"pi"*/27:
                result = NrxUnit.PICA;
                break;
            case /*""*/28:
                // unitless values have target units.
                result = targetUnit;
                break;
            case /*"UL"*/29:
            case /*"\n"*/30:
                result = NrxUnit.NONE;
                break;
            default:
                // we don't expect other units, so throw
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + msString.format("Unknown universal measure {0}", unit));
        }

        return result;
    }

    /// <summary>
    /// This helps to properly build run nodes. In DOCX text of one run can consist from
    /// several elements such as "t", "tab" and so on. These all are joined here when possible.
    ///
    /// RK Used by DocxRunReader, should be moved there.
    /// </summary>
    StringBuilder getRunTextBuilder() { return mRunTextBuilder; }


    /// <summary>
    /// RK Used in DocxParaChildReader, maybe should be moved there.
    /// Remembers created field start and separator nodes. Pops on field end.
    /// </summary>
    msStack<FieldChar> getFieldNodesStack() { return mFieldNodesStack; }

    /// <summary>
    /// Used for DOCX only.
    /// RK Used only by DocxParaReader and DocxParaChildReader, so better be moved there.
    /// Should probably be refactored to stack to work correctly as paragraphs can be nested.
    /// But since RSIDs implementation in the model is incomplete anyway - I leave it as is for now.
    /// </summary>
    int getRsidRDefault() { return mRsidRDefault; }
    void setRsidRDefault(int value) { mRsidRDefault = value; }

    /// <summary>
    /// Used for DOCX only.
    /// </summary>
    FormFieldPr getCurrentFormFieldPr() { return StackExtension.top(mFormFieldPrStack); }

    /// <summary>
    /// Used for DOCX only.
    /// </summary>
    void pushFormFieldPr(FormFieldPr value)
    {
        mFormFieldPrStack.push(value);
    }

    /// <summary>
    /// Used for DOCX only.
    /// </summary>
    void popFormFieldPr()
    {
        mFormFieldPrStack.pop();
    }

    /// <summary>
    /// Returns current attribute value as universal measure from XML and converts it into twips.
    /// </summary>
    int getValueAsTwips(OoxmlComplianceInfo complianceInfo)
    {
        return MathUtil.doubleToInt(convertUniversalMeasure(getValue(), NrxUnit.TWIPS, complianceInfo));
    }

    /// <summary>
    /// Returns current attribute value as universal measure from XML and converts it into twips.
    /// Result is adjusted to fit in a short type range.
    /// </summary>
    int getValueAsTwipsAsShort(OoxmlComplianceInfo complianceInfo)
    {
        return MathUtil.doubleToShort(convertUniversalMeasure(getValue(), NrxUnit.TWIPS, complianceInfo));
    }

    /// <summary>
    /// Returns current attribute value as universal measure from XML and converts it into hundredths of a point.
    /// </summary>
    int getValueAs100thsOfPoint(OoxmlComplianceInfo complianceInfo)
    {
        return MathUtil.doubleToInt(convertUniversalMeasure(getValue(), NrxUnit.HUNDREDTHS_OF_POINT, complianceInfo));
    }

    /// <summary>
    /// Returns current attribute value as universal measure from XML and converts it into EMUs.
    /// </summary>
    double getValueAsEmus(OoxmlComplianceInfo complianceInfo)
    {
        return convertUniversalMeasure(getValue(), NrxUnit.EMUS, complianceInfo);
    }

    public /*override*/ boolean getValueAsBool() { return xmlToBool(getValue()); }

    /// <summary>
    /// Last read paragraph or block level cross-structure annotation. Used to bind block-level custom xml.
    /// </summary>
    Node getPossibleSdtEnd() { return mPossibleSdtEnd; }
    void setPossibleSdtEnd(Node value) { mPossibleSdtEnd = value; }

    /// <summary>
    /// Converts a value of an onOffType attribute into a boolean value.
    /// </summary>
    static boolean xmlToBool(String value)
    {
        if (("on".equals(value)) || ("1".equals(value)) || ("true".equals(value)) || ("t".equals(value)))
            return true;

        if (("off".equals(value)) || ("0".equals(value)) || ("false".equals(value)) || ("f".equals(value)))
            return false;

        // WORDSNET-28064 Invalid bool attribute value.
        if (("00".equals(value)))
            return false;

        throw new IllegalStateException(msString.format("Unknown value '{0}' for an 'onOffType' attribute.", value));
    }

    /// <summary>
    /// Warning callback.
    /// </summary>
    IWarningCallback getWarningCallback() { return mWarningCallback; }

    /// <summary>
    /// The [Shape, FillSource] pairs for deferred binding.
    /// </summary>
    HashMap<ShapeBase, String> getFillSourceMap() { return mFillSourceMap; }

    /// <summary>
    /// Used for WML only.
    /// Memorizes fieldData encountered when parsing field definitions.
    /// When type of the field becomes known this field data is used to set various field properties.
    /// Right now it is actually used only when reading form fields. In all other cases it is simply discarded.
    /// </summary>
    String FieldData;

    private /*final*/ StringBuilder mRunTextBuilder = new StringBuilder();
    private /*final*/ msStack<FieldChar> mFieldNodesStack = new msStack<FieldChar>();
    private int mRsidRDefault;

    private Node mPossibleSdtEnd;
    private /*final*/ IWarningCallback mWarningCallback;
    private /*final*/ /*WarningSource*/int mWarningSource;
    private /*final*/ msStack<FormFieldPr> mFormFieldPrStack = new msStack<FormFieldPr>();
    private /*final*/ HashMap<ShapeBase, String> mFillSourceMap = new HashMap<ShapeBase, String>();

    private static Regex gUniversalMeasureRegex;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"true",
		"t",
		"false",
		"f",
		"font",
		"char",
		"val",
		"sz",
		"space",
		"color",
		"shadow",
		"frame",
		"themeColor",
		"themeShade",
		"themeTint",
		"bdrwidth",
		"fill",
		"themeFill",
		"themeFillShade",
		"themeFillTint",
		"w",
		"type",
		"in",
		"pt",
		"mm",
		"cm",
		"pc",
		"pi",
		"",
		"UL",
		"\n"
	);

}

