// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2008 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/*static*/ class DocxFfDataReader
{
	/* Simulation of static class by using private constructor */
	private DocxFfDataReader()
	{}

    /// <summary>
    /// If 'fldChar' has 'ffData' defined then we should memorize this data and use it later,
    /// when type of this field will become known to us.
    /// Make sure reader is positioned to element start.
    /// </summary>
    static void read(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        FormFieldPr formFieldPr = new FormFieldPr();
        while (xmlReader.readChild("ffData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"name"*/0:
                    formFieldPr.setName(xmlReader.readVal());
                    break;
                case /*"enabled"*/1:
                    formFieldPr.setEnabled(xmlReader.readBoolVal());
                    break;
                case /*"calcOnExit"*/2:
                    formFieldPr.setCalcOnExit(xmlReader.readBoolVal());
                    break;
                case /*"entryMacro"*/3:
                    formFieldPr.setEntryMacro(xmlReader.readVal());
                    break;
                case /*"exitMacro"*/4:
                    formFieldPr.setExitMacro(xmlReader.readVal());
                    break;
                case /*"helpText"*/5:
                    formFieldPr.setOwnHelpText(true); // This is a default if type is missing.
                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"type"*/10:
                                formFieldPr.setOwnHelpText(("text".equals(xmlReader.getValue())));
                                break;
                            case /*"val"*/11:
                                formFieldPr.setHelpText(xmlReader.getValue());
                                break;
                            default:
                                Debug.fail(xmlReader.getLocalName());
                                break;
                        }
                    }
                    break;
                case /*"statusText"*/6:
                    formFieldPr.setOwnStatusText(true); // This is a default if type is missing.
                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"type"*/10:
                                formFieldPr.setOwnStatusText(("text".equals(xmlReader.getValue())));
                                break;
                            case /*"val"*/11:
                                formFieldPr.setStatusText(xmlReader.getValue());
                                break;
                            default:
                                Debug.fail(xmlReader.getLocalName());
                                break;
                        }
                    }
                    break;
                case /*"textInput"*/7:
                    readTextInput(xmlReader, formFieldPr);
                    break;
                case /*"checkBox"*/8:
                    readCheckBox(xmlReader, formFieldPr, complianceInfo);
                    break;
                case /*"ddList"*/9:
                    readDropDownList(xmlReader, formFieldPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        // Store formFieldPr on the stack for further processing.
        xmlReader.pushFormFieldPr(formFieldPr);
    }

    private static void readTextInput(NrxXmlReader partReader, FormFieldPr formFieldPr)
    {
        while (partReader.readChild("textInput"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"maxLength"*/12:
                    formFieldPr.setTextInputMaxLength(partReader.readIntVal());
                    break;
                case /*"type"*/10:
                    formFieldPr.setTextInputType(DocxEnum.docxToTextFormFieldType(partReader.readVal()));
                    break;
                case /*"default"*/13:
                    formFieldPr.setTextInputDefault(partReader.readVal());
                    break;
                case /*"format"*/14:
                    formFieldPr.setTextInputFormat(partReader.readVal());
                    break;
                default:
                    partReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readCheckBox(NrxXmlReader partReader, FormFieldPr formFieldPr, 
        OoxmlComplianceInfo complianceInfo)
    {
        while (partReader.readChild("checkBox"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"size"*/15:
                    formFieldPr.setCheckBoxSizeAuto(false);
                    formFieldPr.setCheckBoxSizeHalfPoints(partReader.readValAsHalfPoints(complianceInfo));
                    break;
                case /*"sizeAuto"*/16:
                    formFieldPr.setCheckBoxSizeAuto(partReader.readBoolVal());
                    break;
                case /*"default"*/13:
                    formFieldPr.setCheckBoxDefault(partReader.readBoolVal());
                    break;
                case /*"checked"*/17:
                    formFieldPr.setCheckBoxChecked(partReader.readBoolVal());
                    break;
                default:
                    partReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readDropDownList(NrxXmlReader partReader, FormFieldPr formFieldPr)
    {
        while (partReader.readChild("ddList"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"default"*/13:
                    formFieldPr.setDropDownDefault(partReader.readIntVal());
                    break;
                case /*"result"*/18:
                    formFieldPr.setDropDownResult(partReader.readIntVal());
                    break;
                case /*"listEntry"*/19:
                    formFieldPr.getDropDownItems().add(partReader.readVal());
                    break;
                default:
                    partReader.ignoreElement();
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"name",
		"enabled",
		"calcOnExit",
		"entryMacro",
		"exitMacro",
		"helpText",
		"statusText",
		"textInput",
		"checkBox",
		"ddList",
		"type",
		"val",
		"maxLength",
		"default",
		"format",
		"size",
		"sizeAuto",
		"checked",
		"result",
		"listEntry"
	);

}

