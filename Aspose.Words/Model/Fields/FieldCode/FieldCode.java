// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.NullableInt32;
import com.aspose.FormatterPal;
import com.aspose.Debug;


/// <summary>
/// Represents a field code parsed to field parts like field arguments or switches.
/// Parsing field code may be required during field update or in other cases (e.g. to read field format
/// when setting field result). That's why we have different ctors, and FieldUpdateContext may be null.
/// </summary>
class FieldCode implements IFieldCodeTokenInfoProvider, IFieldCode
{
    FieldCode(String fieldCode, IFieldCodeTokenInfoProvider tokenInfoProvider)
    {
        mTokenInfoProvider = tokenInfoProvider;
        mVerbatimArgumentIndex = FieldUtil.getVerbatimArgumentIndex(fieldCode);
        mIgnoreSwitchesInFieldType = FieldUtil.ignoreSwitchesInFieldType(fieldCode);

        FieldCodeParser parser = new FieldCodeParser(fieldCode, this);
        try /*JAVA: was using*/
    	{
            parse(parser);
    	}
        finally { if (parser != null) parser.dispose(); }
        mLanguageId = RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID;
    }

    FieldCode(Field field)
    {
        mField = field;
        mTokenInfoProvider = ms.as(field, IFieldCodeTokenInfoProvider.class);
        mVerbatimArgumentIndex = FieldUtil.getVerbatimArgumentIndex(field.getType());
        mIgnoreSwitchesInFieldType = FieldUtil.ignoreSwitchesInFieldType(field.getType());

        parseField();

        mUpdateSupported = true;
    }

    private void parseField()
    {
        mArguments.clear();
        mSwitches.clear();
        mElements.clear();

        FieldCodeParser parser = new FieldCodeParser(mField, this);
        try /*JAVA: was using*/
        {
            parse(parser);

            // Remember the field language.
            mLanguageId = parser.getLanguageId();
            setLanguageIdFarEast(parser.getLanguageIdFarEast());
            mLanguageIdBi = parser.getLanguageIdBi();
            mBidi = parser.getBidi();
            mLanguageNode = parser.getLanguageNode();
        }
        finally { if (parser != null) parser.dispose(); }
    }

    /// <summary>
    /// Parses field code into arguments and switches. I have noticed that arguments and switches (at least formatting
    /// switches) may be intermixed. For example, this construction works: IF 2 > 1  \* MERGEFORMAT True False
    /// Therefore we should collect arguments and switches independently.
    /// </summary>
    private void parse(FieldCodeParser parser)
    {
        parser.moveToFirstToken(mIgnoreSwitchesInFieldType);
        setFieldType(parser.getCurrentToken());

        String switchName = null;
        NodeRange switchRange = null;

        while (parser.moveToNextToken(isVerbatimArgument() && switchName == null, ignoreSymbolicQuotesArgument(switchName)))
        {
            if (parser.isSwitch())
            {
                if (switchName != null)
                {
                    if (FieldSwitch.isFormattingSwitch(switchName))
                        setParseErrorMessage("Error! Switch argument not specified.");

                    addSwitch(switchRange, switchName);
                    switchName = null;
                    switchRange = null;
                }

                if (getSwitchType(parser.getCurrentToken()) != FieldSwitchType.UNKNOWN)
                {
                    switchName = parser.getCurrentToken();
                    switchRange = parser.getCurrentNodeRange();

                    if (FieldSwitch.isPictureSwitch(switchName))
                    {
                        if (hasPictureSwitch())
                            setParseErrorMessage("Error! Too many picture switches defined.");
                        else if (hasFormattingSwitch())
                            setParseErrorMessage("Error! Picture switch must be first formatting switch.");
                    }
                }
            }
            else
            {
                boolean isFieldArgument = (switchName == null) || (getSwitchType(switchName) != FieldSwitchType.HAS_ARGUMENT);

                FieldArgument argument = new FieldArgument(
                    mField,
                    parser.getCurrentToken(),
                    parser.getCurrentRichToken(),
                    parser.getCurrentNodeRange(),
                    parser.getCompleteFieldNodeRange(),
                    parser.isCurrentTokenInDoubleQuotes());

                if (isFieldArgument)
                {
                    if (switchName != null)
                    {
                        addSwitch(switchRange, switchName);
                        switchName = null;
                        switchRange = null;
                    }

                    // This is a field argument.
                    addArgument(argument);
                }
                else
                {
                    // This is a switch argument.
                    addSwitch(switchRange, switchName, argument);
                    switchName = null;
                    switchRange = null;
                }

                // Notify field update context (if any) about that the argument is added.
                if ((mField != null) && mField.isUpdating())
                    mField.getUpdateContext().notifyArgumentAdded(argument);
            }
        }

        if (switchName != null)
            addSwitch(switchRange, switchName);
    }

    private boolean hasFormattingSwitch()
    {
        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (fieldSwitch.isFormatting())
                return true;
        }

        return false;
    }

    private boolean hasPictureSwitch()
    {
        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (fieldSwitch.isPicture())
                return true;
        }

        return false;
    }

    private void addSwitch(NodeRange range, String switchName)
    {
        addSwitch(range, switchName, null);
    }

    private void addSwitch(NodeRange range, String switchName, FieldArgument switchArgument)
    {
        FieldSwitch fieldSwitch = new FieldSwitch(range, switchName, switchArgument);
        msArrayList.add(mSwitches, fieldSwitch);
        msArrayList.add(mElements, fieldSwitch);
    }

    private void addArgument(FieldArgument argument)
    {
        msArrayList.add(mArguments, argument);
        msArrayList.add(mElements, argument);
    }

    private void updateSwitch(int elementIndex, FieldSwitch fieldSwitch)
    {
        int index = mSwitches.indexOf((FieldSwitch)mElements.get(elementIndex));
        mSwitches.set(index, fieldSwitch);
        mElements.set(elementIndex, fieldSwitch);
    }

    private void updateArgument(int elementIndex, FieldArgument fieldArgument)
    {
        int index = mArguments.indexOf((FieldArgument)mElements.get(elementIndex));
        mArguments.set(index, fieldArgument);
        mElements.set(elementIndex, fieldArgument);
    }

    /// <summary>
    /// Returns <c>true</c> if the switch was found in the field code.
    /// </summary>
    public boolean hasSwitch(String switchName)
    {
        return getSwitch(switchName) != null;
    }

    /// <summary>
    /// Returns the argument of the specified switch or null if the switch was not found in field code
    /// or is missing the argument.
    /// </summary>
    FieldArgument getSwitchArgument(String switchName)
    {
        FieldSwitch fieldSwitch = getSwitch(switchName);
        return fieldSwitch != null
            ? fieldSwitch.getArgument()
            : null;
    }

    /// <summary>
    /// Returns all arguments of the specified switch that were found in field code.
    /// </summary>
    Iterable<FieldArgument> getSwitchArguments(String switchName)
    {
        ArrayList<FieldArgument> result = new ArrayList<FieldArgument>();

        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (fieldSwitch.hasName(switchName) && fieldSwitch.hasArgument())
                msArrayList.add(result, fieldSwitch.getArgument());
        }

        return result;
    }

    /// <summary>
    /// Gets the range for the argument of a switch with the specified name.
    /// </summary>
    /// <remarks>
    /// Creates a fake empty range using parentDocument if field argument is missing.
    /// A field with an empty field argument shall be saved when converting to RTF to mimic Word.
    /// </remarks>
    NodeRange getSwitchArgumentRange(String switchName)
    {
        return getArgumentRange(getSwitchArgument(switchName));
    }

    /// <summary>
    /// Returns the argument of the specified switch as a text string or null
    /// if the switch was not found in field code or is missing the argument.
    /// </summary>
    String getSwitchArgumentAsString(String switchName)
    {
        return getSwitchArgumentAsString(switchName, false);
    }

    /// <summary>
    /// Returns the argument of the specified switch as a text string or null or empty string
    /// if the switch was not found in field code or is missing the argument.
    /// </summary>
    String getSwitchArgumentAsString(String switchName, boolean replaceNullWithEmptyString)
    {
        FieldArgument argument = getSwitchArgument(switchName);
        String result = argument != null
            ? argument.getNormalizedText()
            : null;

        if (result != null)
            return result;

        return replaceNullWithEmptyString
            ? ""
            : null;
    }

    RichString getSwitchArgumentAsRichString(String switchName)
    {
        return getSwitchArgumentAsRichString(switchName, false);
    }

    private RichString getSwitchArgumentAsRichString(String switchName, boolean replaceNullWithEmptyString)
    {
        FieldArgument argument = getSwitchArgument(switchName);
        RichString result = argument != null
            ? argument.getNormalizedRichText()
            : null;

        if (result != null)
            return result;

        return replaceNullWithEmptyString
            ? RichString.EMPTY
            : null;
    }

    /// <summary>
    /// Returns all arguments of the specified switch that were found in field code as a text string.
    /// </summary>
    ArrayList<String> getSwitchArgumentsAsStrings(String switchName)
    {
        ArrayList<String> result = new ArrayList<String>();
        for (FieldArgument argument : getSwitchArguments(switchName))
        {
            String argumentText = argument.getNormalizedText();
            if (argumentText != null)
                msArrayList.add(result, argumentText);
        }

        return result;
    }

    /// <summary>
    /// Returns the argument of the specified switch as an integer number.
    /// Throws an exception if the switch was not found in field code.
    /// Returns null if the switch argument is missed or non-numeric.
    /// </summary>
    NullableInt32 getSwitchArgumentAsInt32(String switchName)
    {
        if (!hasSwitch(switchName))
            throw new IllegalStateException();

        return FormatterPal.parseNullableInt(getSwitchArgumentAsString(switchName));
    }

    /// <summary>
    /// Returns the argument of the specified switch as a floating point number.
    /// Returns -1 if the switch was not found in field code, is missing the argument or contains a non-numeric argument.
    /// </summary>
    double getSwitchArgumentAsDouble(String switchName)
    {
        if (!hasSwitch(switchName))
            return -1d;

        double value = FormatterPal.tryParseDoubleCurrent(getSwitchArgumentAsString(switchName));
        return !Double.isNaN(value) ? value : -1d;
    }

    /// <summary>
    /// Returns a value indicating whether a field argument with the specified index exists.
    /// </summary>
    boolean hasArgument(int index)
    {
        return (index >= 0) && (index < mArguments.size());
    }

    /// <summary>
    /// Returns a field argument at the specified index or null if the argument is omitted.
    /// </summary>
    FieldArgument getArgument(int index)
    {
        return hasArgument(index) ? mArguments.get(index) : null;
    }

    FieldSwitch getSwitch(int index)
    {
        return (index >= 0) && (index < mSwitches.size())
                   ? mSwitches.get(index)
                   : null;
    }

    /// <summary>
    /// Gets the range for the field argument.
    /// </summary>
    /// <remarks>
    /// Creates a fake empty range using parentDocument if field argument is missing.
    /// A field with an empty field argument shall be saved when converting to RTF to mimic Word.
    /// </remarks>
    NodeRange getArgumentRange(int argumentIndex)
    {
        return getArgumentRange(getArgument(argumentIndex));
    }

    private NodeRange getArgumentRange(FieldArgument argument)
    {
        // WORDSNET-22419 NullReferenceException occurs during UpdateFields.
        // Field argument may be missing, though such field is incorrect.
        if (argument != null)
            return argument.getRange();

        Run fakeRun = new Run(mField.getDocument());
        return new NodeRange(fakeRun, true, fakeRun, false);
    }

    /// <summary>
    /// Adds (or replaces if present) the specified argument at the specified position in the field code.
    /// If the position specified is too big, adds a number of empty arguments ("") to fill the gap.
    /// </summary>
    void setArgument(int argumentIndex, String argumentText) throws Exception
    {
        initializeUpdate();

        Debug.assertTrue(argumentIndex >= 0);

        boolean isVerbatimArgument = FieldUtil.getVerbatimArgumentIndex(mField.getType()) == argumentIndex;

        FieldArgument argument = getArgument(argumentIndex);
        if (argument != null)
        {
            if (argumentText == null && argumentIndex == mArguments.size() - 1)
                FieldCodeUpdater.removeArgument(argument);
            else
                FieldCodeUpdater.updateArgument(this, argument, argumentText, isVerbatimArgument);

            finalizeUpdate();
        }
        else if (argumentText != null)
        {
            String[] arguments = new String[argumentIndex - mArguments.size() + 1];
            arguments[arguments.length - 1] = argumentText;
            FieldCodeUpdater.appendArguments(this, arguments, isVerbatimArgument);

            finalizeUpdate();
        }
    }

    /// <summary>
    /// Adds (or replaces) or removes a switch with the specified name to/from the field code.
    /// </summary>
    void setSwitch(String switchName, boolean set) throws Exception
    {
        initializeUpdate();

        Debug.assertTrue(switchName != null);

        boolean switchRemoved = false;

        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (!fieldSwitch.hasName(switchName))
                continue;

            if (set)
                return;

            FieldCodeUpdater.removeSwitch(fieldSwitch);
            switchRemoved = true;

            // continue loop to remove all switches
        }

        if (switchRemoved)
        {
            finalizeUpdate();
        }
        else if (set)
        {
            FieldCodeUpdater.appendSwitch(this, switchName, null);
            finalizeUpdate();
        }

    }

    /// <summary>
    /// Adds (or replaces) or removes a switch with the specified name and argument to/from the field code.
    /// </summary>
    void setSwitch(String switchName, int switchArgument) throws Exception
    {
        setSwitch(switchName, Integer.toString(switchArgument));
    }

    /// <summary>
    /// Adds (or replaces) or removes a switch with the specified name and argument to/from the field code.
    /// </summary>
    void setSwitch(String switchName, double switchArgument) throws Exception
    {
        setSwitch(switchName, FormatterPal.doubleToStr(switchArgument));
    }

    /// <summary>
    /// Adds (or replaces) or removes a switch with the specified name and argument to/from the field code.
    /// </summary>
    void setSwitch(String switchName, String switchArgument) throws Exception
    {
        setSwitchInternal(switchName, switchArgument, -1);
    }

    void setSwitchAsInt32(String switchName, String switchArgument) throws Exception
    {
        int val = FormatterPal.tryParseInt(switchArgument);
        setSwitch(switchName, val);
    }

    void setSwitchAsDouble(String switchName, String switchArgument) throws Exception
    {
        double val = FormatterPal.tryParseDoubleInvariant(switchArgument);
        setSwitch(switchName, val);
    }

    /// <summary>
    /// Adds (or replaces) or removes a switch with the specified name and argument located at the specified position
    /// to/from the field code.
    /// </summary>
    void setSwitch(String switchName, String switchArgument, int switchIndex) throws Exception
    {
        Debug.assertTrue(switchName != null);
        Debug.assertTrue(switchIndex >= 0);

        setSwitchInternal(switchName, switchArgument, switchIndex);
    }

    private void setSwitchInternal(String switchName, String switchArgument, int switchIndex) throws Exception
    {
        Debug.assertTrue(switchName != null);
        Debug.assertTrue(switchIndex >= -1);

        initializeUpdate();

        boolean setSingleSwitch = switchIndex != -1;
        boolean switchFound = false;

        int index = 0;
        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (!fieldSwitch.hasName(switchName))
                continue;

            if (setSingleSwitch && switchIndex != index)
            {
                index++;
                continue;
            }

            if (switchArgument != null)
                FieldCodeUpdater.updateArgument(this, fieldSwitch.getArgument(), switchArgument, false);
            else
                FieldCodeUpdater.removeSwitch(fieldSwitch);

            switchFound = true;

            if (setSingleSwitch)
                break;
        }

        if (switchFound)
        {
            finalizeUpdate();
        }
        else if (switchArgument != null)
        {
            FieldCodeUpdater.appendSwitch(this, switchName, switchArgument);
            finalizeUpdate();
        }
    }

    /// <summary>
    /// Gets an argument as a plain text at the specified index.
    /// </summary>
    public String getArgumentAsString(int index)
    {
        return getArgumentAsString(index, true);
    }

    /// <summary>
    /// Gets an argument as a plain text at the specified index.
    /// </summary>
    String getArgumentAsString(int index, boolean trimDoubleQuotes)
    {
        return getArgumentAsString(index, trimDoubleQuotes, false);
    }

    /// <summary>
    /// Gets an argument as a plain text at the specified index.
    /// </summary>
    /// <param name="index"></param>
    /// <param name="trimDoubleQuotes">Double quotes should be trimmed in most cases except of in comparison
    /// expression operands because they are used there to determine whether to compare the operands as strings.</param>
    /// <param name="replaceNullWithEmptyString">Null arguments equal to empty strings in most cases except of
    /// in comparison fields where there is a need to know what arguments are omitted.</param>
    /// <returns></returns>
    String getArgumentAsString(int index, boolean trimDoubleQuotes, boolean replaceNullWithEmptyString)
    {
        FieldArgument argument = getArgument(index);

        if (argument == null)
            return replaceNullWithEmptyString ? "" : null;

        return argument.getNormalizedText(trimDoubleQuotes);
    }

    private FieldSwitch getSwitch(String switchName)
    {
        // WORDSNET-3786 Page numbers lost in TOC when multiple \n switches are present.
        // Get the last switch with the given name.
        FieldSwitch result = null;

        for (FieldSwitch fieldSwitch : mSwitches)
        {
            if (fieldSwitch.hasName(switchName))
                result = fieldSwitch;
        }

        return result;
    }

    public /*FieldSwitchType*/int getSwitchType(String switchName)
    {
        if (FieldSwitch.isFormattingSwitch(switchName))
        {
            // The three formatting switches are common for all fields and may appear in any of them.
            return FieldSwitchType.HAS_ARGUMENT;
        }

        return (mTokenInfoProvider != null)
            ? mTokenInfoProvider.getSwitchType(switchName.toLowerCase())
            : FieldSwitchType.UNKNOWN;
    }

    /// <summary>
    /// Gets all collected field arguments, preserving the order.
    /// </summary>
    ArrayList<FieldArgument> getArguments() { return mArguments; }

    /// <summary>
    /// Gets all collected switches.
    /// </summary>
    ArrayList<FieldSwitch> getSwitches() { return mSwitches; }

    /// <summary>
    /// Gets all collected arguments and switches.
    /// </summary>
    ArrayList<Object> getElements() { return mElements; }

    String getFieldType() { return mFieldType; }; private void setFieldType(String value) { mFieldType = value; };

    private String mFieldType;

    Field getField() { return mField; }

    /// <summary>
    /// Gets the field language Id specified via field code formatting properties.
    /// </summary>
    int getLanguageId() { return mLanguageId; }
    void setLanguageId(int value)
    {
        if (mLanguageNode == null)
            return;

        mLanguageId = value;
        mLanguageNode.getFont().setLocaleId(value);
    }

    /// <summary>
    /// Gets the field East Asian language Id specified via field code formatting properties.
    /// </summary>
    int getLanguageIdFarEast() { return mLanguageIdFarEast; }; private void setLanguageIdFarEast(int value) { mLanguageIdFarEast = value; };

    private int mLanguageIdFarEast;

    /// <summary>
    /// Gets the field RTL language Id specified via field code formatting properties.
    /// </summary>
    int getLanguageIdBi() { return mLanguageIdBi; }
    void setLanguageIdBi(int value)
    {
        if (mLanguageNode == null)
            return;

        mLanguageIdBi = value;
        mLanguageNode.getFont().setLocaleIdBi(value);
    }

    /// <summary>
    /// A boolean value indicating if RTL is specified via field code formatting properties.
    /// </summary>
    boolean getBidi() { return mBidi; }
    void setBidi(boolean value)
    {
        if (mLanguageNode == null)
            return;

        mBidi = value;
        mLanguageNode.getFont().setBidi(value);
    }

    private boolean isVerbatimArgument() { return mArguments.size() == mVerbatimArgumentIndex; }

    private boolean ignoreSymbolicQuotesArgument(String switchName)
    {
        /*FieldType*/int fieldType = FieldUtil.getFieldType(getFieldType());
        return switchName != null
            ? FieldUtil.ignoreSymbolicQuotes(fieldType, switchName)
            : FieldUtil.ignoreSymbolicQuotes(fieldType, mArguments.size());
    }

    String getParseErrorMessage() { return mParseErrorMessage; }; private void setParseErrorMessage(String value) { mParseErrorMessage = value; };

    private String mParseErrorMessage;

    boolean hasParseError() { return getParseErrorMessage() != null; }

    private void initializeUpdate()
    {
        Debug.assertTrue(mUpdateSupported);

        isolateElements();
    }

    private void finalizeUpdate()
    {
        parseField();

        ensureTrailingSpaceIfNeeded();
    }

    /// <summary>
    /// Ad-hoc solution to workaround MS-Word bug: it updates hyperlink-like fields (REF and PAGEREF with \h switch)
    /// with error if field has general format switch (i.e. \* lower) in last position
    /// and there is no trailing whitespace after it.
    /// </summary>
    private void ensureTrailingSpaceIfNeeded()
    {
        if (!isHyperlinkLikeField())
            return;

        FieldSwitch lastSwitch = mSwitches.get(mSwitches.size() - 1);
        if (!lastSwitch.hasName(FieldFormat.GENERAL_FORMAT_SWITCH))
            return;

        if (!lastSwitch.hasArgument())
            return;

        FieldChar fieldCodeEnd = mField.getFieldCodeEnd();
        if (lastSwitch.getArgument().getRange().getEnd().getNode() != fieldCodeEnd)
            return;

        FieldCodeUpdater.appendTrailingSpace(this);
    }

    private boolean isHyperlinkLikeField()
    {
        if (mField == null)
            return false;

        if (mSwitches.size() == 0)
            return false;

        switch (mField.getType())
        {
            case com.aspose.words.FieldType.FIELD_REF:
                return hasSwitch(FieldRef.INSERT_HYPERLINK_SWITCH);
            case com.aspose.words.FieldType.FIELD_PAGE_REF:
                return hasSwitch(FieldPageRef.INSERT_HYPERLINK_SWITCH);
            default:
                return false;
        }
    }

    /// <summary>
    /// Isolates elements into complete nodes.
    /// </summary>
    void isolateElements()
    {
        FieldCodeIsolator isolator = new FieldCodeIsolator(this);
        isolator.isolateElements();
    }

    private /*final*/ Field mField;
    private /*final*/ IFieldCodeTokenInfoProvider mTokenInfoProvider;
    private /*final*/ int mVerbatimArgumentIndex;
    private /*final*/ boolean mIgnoreSwitchesInFieldType;
    private /*final*/ ArrayList<FieldArgument> mArguments = new ArrayList<FieldArgument>();
    private /*final*/ ArrayList<FieldSwitch> mSwitches = new ArrayList<FieldSwitch>();
    private /*final*/ ArrayList<Object> mElements = new ArrayList<Object>();

    private int mLanguageId;
    private int mLanguageIdBi;
    private boolean mBidi;
    private Inline mLanguageNode;
    private /*final*/ boolean mUpdateSupported;

    private static class FieldCodeIsolator
    {
        FieldCodeIsolator(FieldCode fieldCode)
        {
            mFieldCode = fieldCode;
        }

        void isolateElements()
        {
            if (mFieldCode.getField().isUpdating())
            {
                for (FieldArgument fieldArgument : mFieldCode.getArguments())
                    fieldArgument.ensureText();

                for (FieldSwitch fieldSwitch : mFieldCode.getSwitches())
                    if (fieldSwitch.hasArgument())
                        fieldSwitch.getArgument().ensureText();
            }

            // Isolate elements in backward direction, so we need care about only range boundaries nodes changed, but offset remains unchanged.
            for (int elementIndex = mFieldCode.mElements.size() - 1; elementIndex >= 0; elementIndex--)
            {
                tryIsolateSwitchArgument(elementIndex);
                tryIsolateSwitch(elementIndex);
                tryIsolateArgument(elementIndex);
            }
        }

        private void tryIsolateSwitchArgument(int elementIndex)
        {
            FieldSwitch fieldSwitch = ms.as(mFieldCode.mElements.get(elementIndex), FieldSwitch.class);
            if (fieldSwitch == null || !fieldSwitch.hasArgument())
                return;

            isolateRange(fieldSwitch.getArgument().getRange(), elementIndex, true);
        }

        private void tryIsolateSwitch(int elementIndex)
        {
            FieldSwitch fieldSwitch = ms.as(mFieldCode.mElements.get(elementIndex), FieldSwitch.class);
            if (fieldSwitch == null)
                return;

            isolateRange(fieldSwitch.getRange(), elementIndex, false);
        }

        private void tryIsolateArgument(int elementIndex)
        {
            FieldArgument fieldArgument = ms.as(mFieldCode.mElements.get(elementIndex), FieldArgument.class);
            if (fieldArgument == null)
                return;

            isolateRange(fieldArgument.getRange(), elementIndex, false);
        }

        private void isolateRange(NodeRange isolatedRange, int isolatedElementIndex, boolean isSwitchArgument)
        {
            Affects affects = findAffects(isolatedElementIndex, isolatedRange, isSwitchArgument);

            Node node = isolatedRange.isolate();
            if (node == null)
                return;

            recoverAffects(affects, isolatedElementIndex, node);
        }

        private Affects findAffects(int isolatedElementIndex, NodeRange isolatedRange, boolean isSwitchArgument)
        {
            Affects result = new Affects(isolatedElementIndex);

            // If switch argument range is isolated, switch range may affected to.
            if (isSwitchArgument)
            {
                FieldSwitch fieldSwitch = (FieldSwitch)mFieldCode.mElements.get(isolatedElementIndex);
                result.setCurrentSwitchAffect(getRangeAffect(fieldSwitch.getRange(), isolatedRange));
            }

            for (int i = 0; i < isolatedElementIndex; i++)
            {
                result.getElementsAffect()[i] = new ElementAffect();

                FieldSwitch leadingSwitch = ms.as(mFieldCode.mElements.get(i), FieldSwitch.class);
                if (leadingSwitch != null)
                {
                    result.getElementsAffect()[i].setSwitchAffect(getRangeAffect(leadingSwitch.getRange(), isolatedRange));

                    if (leadingSwitch.hasArgument())
                    {
                        result.getElementsAffect()[i].setSwitchArgumentAffect(getRangeAffect(leadingSwitch.getArgument().getRange(), isolatedRange));
                    }
                }

                FieldArgument leadingArgument = ms.as(mFieldCode.mElements.get(i), FieldArgument.class);
                if (leadingArgument != null)
                {
                    result.getElementsAffect()[i].setArgumentAffect(getRangeAffect(leadingArgument.getRange(), isolatedRange));
                }
            }

            return result;
        }

        private static RangeAffect getRangeAffect(NodeRange leadingRange, NodeRange isolatedRange)
        {
            boolean isStartAffected = leadingRange.getStart().getNode() == isolatedRange.getStart().getNode();
            boolean isEndAffected = leadingRange.getEnd().getNode() == isolatedRange.getStart().getNode();
            return new RangeAffect(isStartAffected, isEndAffected);
        }

        private void recoverAffects(Affects affects, int isolatedElementIndex, Node node)
        {
            if (affects.getCurrentSwitchAffect().isAffected())
            {
                FieldSwitch fieldSwitch = (FieldSwitch)mFieldCode.mElements.get(isolatedElementIndex);
                NodeRange recoveredRange = recoverRange(fieldSwitch.getRange(), affects.getCurrentSwitchAffect(), node);
                mFieldCode.updateSwitch(isolatedElementIndex, fieldSwitch.deepClone(recoveredRange));
            }

            for (int j = 0; j < isolatedElementIndex; j++)
            {
                ElementAffect elementAffect = affects.getElementsAffect()[j];

                if (elementAffect.getSwitchArgumentAffect().isAffected())
                {
                    FieldSwitch fieldSwitch = (FieldSwitch)mFieldCode.mElements.get(j);
                    NodeRange recoveredRange = recoverRange(fieldSwitch.getArgument().getRange(), elementAffect.getSwitchArgumentAffect(), node);
                    FieldArgument recoveredSwitchArgument = fieldSwitch.getArgument().deepClone(recoveredRange);
                    mFieldCode.updateSwitch(j, fieldSwitch.deepClone(recoveredSwitchArgument));
                }

                if (elementAffect.getSwitchAffect().isAffected())
                {
                    FieldSwitch fieldSwitch = (FieldSwitch)mFieldCode.mElements.get(j);
                    NodeRange recoveredRange = recoverRange(fieldSwitch.getRange(), elementAffect.getSwitchAffect(), node);
                    mFieldCode.updateSwitch(j, fieldSwitch.deepClone(recoveredRange));
                }

                if (elementAffect.getArgumentAffect().isAffected())
                {
                    FieldArgument fieldArgument = (FieldArgument)mFieldCode.mElements.get(j);
                    NodeRange recoveredRange = recoverRange(fieldArgument.getRange(), elementAffect.getArgumentAffect(), node);
                    mFieldCode.updateArgument(j, fieldArgument.deepClone(recoveredRange));
                }
            }
        }

        private static NodeRange recoverRange(NodeRange range, RangeAffect rangeAffect, Node node)
        {
            return new NodeRange(
                rangeAffect.isStartAffected() ? new DocumentPosition(node, range.getStart().getOffset()) : range.getStart(),
                rangeAffect.isEndAffected() ? new DocumentPosition(node, range.getEnd().getOffset()) : range.getEnd());
        }

        private /*final*/ FieldCode mFieldCode;

        private static class Affects
        {
            Affects(int elementsCount)
            {
                mElementsAffect = new ElementAffect[elementsCount];
                setCurrentSwitchAffect(RangeAffect.UN_AFFECTED);
            }

            RangeAffect getCurrentSwitchAffect() { return mCurrentSwitchAffect; }; void setCurrentSwitchAffect(RangeAffect value) { mCurrentSwitchAffect = value; };

            private RangeAffect mCurrentSwitchAffect;

            ElementAffect[] getElementsAffect() { return mElementsAffect; };

            private ElementAffect[] mElementsAffect;
        }

        private static class ElementAffect
        {
            ElementAffect()
            {
                setArgumentAffect(RangeAffect.UN_AFFECTED);
                setSwitchArgumentAffect(RangeAffect.UN_AFFECTED);
                setSwitchAffect(RangeAffect.UN_AFFECTED);
            }

            RangeAffect getSwitchAffect() { return mSwitchAffect; }; void setSwitchAffect(RangeAffect value) { mSwitchAffect = value; };

            private RangeAffect mSwitchAffect;

            RangeAffect getSwitchArgumentAffect() { return mSwitchArgumentAffect; }; void setSwitchArgumentAffect(RangeAffect value) { mSwitchArgumentAffect = value; };

            private RangeAffect mSwitchArgumentAffect;

            RangeAffect getArgumentAffect() { return mArgumentAffect; }; void setArgumentAffect(RangeAffect value) { mArgumentAffect = value; };

            private RangeAffect mArgumentAffect;
        }

        private static class RangeAffect
        {
            RangeAffect(boolean isStartAffected, boolean isEndAffected)
            {
                mIsStartAffected = isStartAffected;
                mIsEndAffected = isEndAffected;
            }

            boolean isStartAffected() { return mIsStartAffected; };

            private boolean mIsStartAffected;

            boolean isEndAffected() { return mIsEndAffected; };

            private boolean mIsEndAffected;

            boolean isAffected() { return isStartAffected() || isEndAffected(); }

            static /*final*/ RangeAffect UN_AFFECTED = new RangeAffect(false, false);
        }
    }
}

