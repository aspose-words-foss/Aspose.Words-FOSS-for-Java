// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2018 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies the conversion scenario (load format and save format) for a unified test.
/// Also specifies the flag whether the gold verification should take place.
/// </summary>
public /*enum*/ final class UnifiedScenario
{
    private UnifiedScenario(){}
    
    /// <summary>
    /// LoadFormat value occupies the bits 0..7.
    /// </summary>
    public static final int LOAD_FORMAT_MASK = 0x000000ff;
    /// <summary>
    /// SaveFormat value occupies the bits 8..15.
    /// </summary>
    public static final int SAVE_FORMAT_MASK = 0x0000ff00;

    /// <summary>
    /// When this bit is set, NO gold verification will take place.
    /// </summary>
    public static final int NO_GOLD = 0x00010000;

    /// <summary>
    /// When this bit is set, ExportOnly gold verification will take place.
    /// </summary>
    public static final int EXPORT_ONLY = 0x00020000;

    /// <summary>
    /// Part of the standard unified scenarios.
    /// The verify gold is requested, but currently there is no gold verification for this binary scenario.
    /// Could be a good idea to create another module that will read DOC and dump as TXT so we can create golds for these.
    /// </summary>
    public static final int DOC_2_DOC = LoadFormat.DOC | (SaveFormat.DOC << 8);
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int DOC_2_DOCX = LoadFormat.DOC | (SaveFormat.DOCX << 8);
    /// <summary>
    /// Part of the standard unified scenarios, used instead of Doc2Docx for DOCM files with macros.
    /// </summary>
    public static final int DOC_2_DOCM = LoadFormat.DOC | (SaveFormat.DOCM << 8);
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int DOC_2_RTF = LoadFormat.DOC | (SaveFormat.RTF << 8);
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int DOC_2_WML = LoadFormat.DOC | (SaveFormat.WORD_ML << 8);
    /// <summary>
    /// Part of the standard unified scenarios.
    /// Open as OOXML with DrawingML and save as OOXML with DrawingML.
    /// This scenario is not for all files, but only for those that have graphics and shapes.
    /// </summary>
    public static final int DOCX_DML_2_DOCX_DML = LoadFormatTest.TEST_DOCX_DML | (SaveFormat.DOCX << 8);
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int DOCX_2_DOCX_NO_GOLD = LoadFormat.DOCX | (SaveFormat.DOCX << 8) | NO_GOLD;
    /// <summary>
    /// Part of the standard unified scenarios. Used instead of Docx2DocxNoGold for DOCM documents containing macros.
    /// </summary>
    public static final int DOCM_2_DOCM_NO_GOLD = LoadFormat.DOCM | (SaveFormat.DOCM << 8) | NO_GOLD;
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int RTF_2_RTF_NO_GOLD = LoadFormat.RTF | (SaveFormat.RTF << 8) | NO_GOLD;
    /// <summary>
    /// Part of the standard unified scenarios.
    /// </summary>
    public static final int WML_2_WML_NO_GOLD = LoadFormat.WORD_ML | (SaveFormat.WORD_ML << 8) | NO_GOLD;

    public static final int DOCX_2_DOCX = LoadFormat.DOCX | (SaveFormat.DOCX << 8);
    public static final int DOCX_2_RTF = LoadFormat.DOCX | (SaveFormat.RTF << 8);
    public static final int DOCX_2_WML = LoadFormat.DOCX | (SaveFormat.WORD_ML << 8);
    public static final int DOCX_2_MD = LoadFormat.DOCX | (SaveFormat.MARKDOWN << 8);

    public static final int RTF_2_RTF = LoadFormat.RTF | (SaveFormat.RTF << 8);
    public static final int RTF_2_DOCX = LoadFormat.RTF | (SaveFormat.DOCX << 8);

    public static final int WML_2_WML = LoadFormat.WORD_ML | (SaveFormat.WORD_ML << 8);

    public static final int MD_2_MD = LoadFormat.MARKDOWN | (SaveFormat.MARKDOWN << 8);
    public static final int MD_2_DOCX = LoadFormat.MARKDOWN | (SaveFormat.DOCX << 8);
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer UnifiedScenario value.
    /// </summary>
    public static String getName(/*UnifiedScenario*/int unifiedScenario)
    {
    	switch (unifiedScenario)
    	{
    		case LOAD_FORMAT_MASK: return "LOAD_FORMAT_MASK";
    		case SAVE_FORMAT_MASK: return "SAVE_FORMAT_MASK";
    		case NO_GOLD: return "NO_GOLD";
    		case EXPORT_ONLY: return "EXPORT_ONLY";
    		case DOC_2_DOC: return "DOC_2_DOC";
    		case DOC_2_DOCX: return "DOC_2_DOCX";
    		case DOC_2_DOCM: return "DOC_2_DOCM";
    		case DOC_2_RTF: return "DOC_2_RTF";
    		case DOC_2_WML: return "DOC_2_WML";
    		case DOCX_DML_2_DOCX_DML: return "DOCX_DML_2_DOCX_DML";
    		case DOCX_2_DOCX_NO_GOLD: return "DOCX_2_DOCX_NO_GOLD";
    		case DOCM_2_DOCM_NO_GOLD: return "DOCM_2_DOCM_NO_GOLD";
    		case RTF_2_RTF_NO_GOLD: return "RTF_2_RTF_NO_GOLD";
    		case WML_2_WML_NO_GOLD: return "WML_2_WML_NO_GOLD";
    		case DOCX_2_DOCX: return "DOCX_2_DOCX";
    		case DOCX_2_RTF: return "DOCX_2_RTF";
    		case DOCX_2_WML: return "DOCX_2_WML";
    		case DOCX_2_MD: return "DOCX_2_MD";
    		case RTF_2_RTF: return "RTF_2_RTF";
    		case RTF_2_DOCX: return "RTF_2_DOCX";
    		case WML_2_WML: return "WML_2_WML";
    		case MD_2_MD: return "MD_2_MD";
    		case MD_2_DOCX: return "MD_2_DOCX";
    		default: return "Unknown UnifiedScenario value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer UnifiedScenario value.
    /// </summary>
    public static Set<String> getNames(/*UnifiedScenario*/int unifiedScenario)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((LOAD_FORMAT_MASK & unifiedScenario) == LOAD_FORMAT_MASK) names.add("LOAD_FORMAT_MASK");
    	if ((SAVE_FORMAT_MASK & unifiedScenario) == SAVE_FORMAT_MASK) names.add("SAVE_FORMAT_MASK");
    	if ((NO_GOLD & unifiedScenario) == NO_GOLD) names.add("NO_GOLD");
    	if ((EXPORT_ONLY & unifiedScenario) == EXPORT_ONLY) names.add("EXPORT_ONLY");
    	if ((DOC_2_DOC & unifiedScenario) == DOC_2_DOC) names.add("DOC_2_DOC");
    	if ((DOC_2_DOCX & unifiedScenario) == DOC_2_DOCX) names.add("DOC_2_DOCX");
    	if ((DOC_2_DOCM & unifiedScenario) == DOC_2_DOCM) names.add("DOC_2_DOCM");
    	if ((DOC_2_RTF & unifiedScenario) == DOC_2_RTF) names.add("DOC_2_RTF");
    	if ((DOC_2_WML & unifiedScenario) == DOC_2_WML) names.add("DOC_2_WML");
    	if ((DOCX_DML_2_DOCX_DML & unifiedScenario) == DOCX_DML_2_DOCX_DML) names.add("DOCX_DML_2_DOCX_DML");
    	if ((DOCX_2_DOCX_NO_GOLD & unifiedScenario) == DOCX_2_DOCX_NO_GOLD) names.add("DOCX_2_DOCX_NO_GOLD");
    	if ((DOCM_2_DOCM_NO_GOLD & unifiedScenario) == DOCM_2_DOCM_NO_GOLD) names.add("DOCM_2_DOCM_NO_GOLD");
    	if ((RTF_2_RTF_NO_GOLD & unifiedScenario) == RTF_2_RTF_NO_GOLD) names.add("RTF_2_RTF_NO_GOLD");
    	if ((WML_2_WML_NO_GOLD & unifiedScenario) == WML_2_WML_NO_GOLD) names.add("WML_2_WML_NO_GOLD");
    	if ((DOCX_2_DOCX & unifiedScenario) == DOCX_2_DOCX) names.add("DOCX_2_DOCX");
    	if ((DOCX_2_RTF & unifiedScenario) == DOCX_2_RTF) names.add("DOCX_2_RTF");
    	if ((DOCX_2_WML & unifiedScenario) == DOCX_2_WML) names.add("DOCX_2_WML");
    	if ((DOCX_2_MD & unifiedScenario) == DOCX_2_MD) names.add("DOCX_2_MD");
    	if ((RTF_2_RTF & unifiedScenario) == RTF_2_RTF) names.add("RTF_2_RTF");
    	if ((RTF_2_DOCX & unifiedScenario) == RTF_2_DOCX) names.add("RTF_2_DOCX");
    	if ((WML_2_WML & unifiedScenario) == WML_2_WML) names.add("WML_2_WML");
    	if ((MD_2_MD & unifiedScenario) == MD_2_MD) names.add("MD_2_MD");
    	if ((MD_2_DOCX & unifiedScenario) == MD_2_DOCX) names.add("MD_2_DOCX");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer UnifiedScenario value.
    /// </summary>
    public static String toString(/*UnifiedScenario*/int unifiedScenario)
    {
    	switch (unifiedScenario)
    	{
    		case LOAD_FORMAT_MASK: return "LoadFormatMask";
    		case SAVE_FORMAT_MASK: return "SaveFormatMask";
    		case NO_GOLD: return "NoGold";
    		case EXPORT_ONLY: return "ExportOnly";
    		case DOC_2_DOC: return "Doc2Doc";
    		case DOC_2_DOCX: return "Doc2Docx";
    		case DOC_2_DOCM: return "Doc2Docm";
    		case DOC_2_RTF: return "Doc2Rtf";
    		case DOC_2_WML: return "Doc2Wml";
    		case DOCX_DML_2_DOCX_DML: return "DocxDml2DocxDml";
    		case DOCX_2_DOCX_NO_GOLD: return "Docx2DocxNoGold";
    		case DOCM_2_DOCM_NO_GOLD: return "Docm2DocmNoGold";
    		case RTF_2_RTF_NO_GOLD: return "Rtf2RtfNoGold";
    		case WML_2_WML_NO_GOLD: return "Wml2WmlNoGold";
    		case DOCX_2_DOCX: return "Docx2Docx";
    		case DOCX_2_RTF: return "Docx2Rtf";
    		case DOCX_2_WML: return "Docx2Wml";
    		case DOCX_2_MD: return "Docx2Md";
    		case RTF_2_RTF: return "Rtf2Rtf";
    		case RTF_2_DOCX: return "Rtf2Docx";
    		case WML_2_WML: return "Wml2Wml";
    		case MD_2_MD: return "Md2Md";
    		case MD_2_DOCX: return "Md2Docx";
    		default: return toStringSet(unifiedScenario);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer UnifiedScenario value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by UnifiedScenario name.
    /// </summary>
    public static /*UnifiedScenario*/int fromName(String unifiedScenarioName)
    {
    	if ("LOAD_FORMAT_MASK".equals(unifiedScenarioName)) return LOAD_FORMAT_MASK;
    	if ("SAVE_FORMAT_MASK".equals(unifiedScenarioName)) return SAVE_FORMAT_MASK;
    	if ("NO_GOLD".equals(unifiedScenarioName)) return NO_GOLD;
    	if ("EXPORT_ONLY".equals(unifiedScenarioName)) return EXPORT_ONLY;
    	if ("DOC_2_DOC".equals(unifiedScenarioName)) return DOC_2_DOC;
    	if ("DOC_2_DOCX".equals(unifiedScenarioName)) return DOC_2_DOCX;
    	if ("DOC_2_DOCM".equals(unifiedScenarioName)) return DOC_2_DOCM;
    	if ("DOC_2_RTF".equals(unifiedScenarioName)) return DOC_2_RTF;
    	if ("DOC_2_WML".equals(unifiedScenarioName)) return DOC_2_WML;
    	if ("DOCX_DML_2_DOCX_DML".equals(unifiedScenarioName)) return DOCX_DML_2_DOCX_DML;
    	if ("DOCX_2_DOCX_NO_GOLD".equals(unifiedScenarioName)) return DOCX_2_DOCX_NO_GOLD;
    	if ("DOCM_2_DOCM_NO_GOLD".equals(unifiedScenarioName)) return DOCM_2_DOCM_NO_GOLD;
    	if ("RTF_2_RTF_NO_GOLD".equals(unifiedScenarioName)) return RTF_2_RTF_NO_GOLD;
    	if ("WML_2_WML_NO_GOLD".equals(unifiedScenarioName)) return WML_2_WML_NO_GOLD;
    	if ("DOCX_2_DOCX".equals(unifiedScenarioName)) return DOCX_2_DOCX;
    	if ("DOCX_2_RTF".equals(unifiedScenarioName)) return DOCX_2_RTF;
    	if ("DOCX_2_WML".equals(unifiedScenarioName)) return DOCX_2_WML;
    	if ("DOCX_2_MD".equals(unifiedScenarioName)) return DOCX_2_MD;
    	if ("RTF_2_RTF".equals(unifiedScenarioName)) return RTF_2_RTF;
    	if ("RTF_2_DOCX".equals(unifiedScenarioName)) return RTF_2_DOCX;
    	if ("WML_2_WML".equals(unifiedScenarioName)) return WML_2_WML;
    	if ("MD_2_MD".equals(unifiedScenarioName)) return MD_2_MD;
    	if ("MD_2_DOCX".equals(unifiedScenarioName)) return MD_2_DOCX;
    	throw new IllegalArgumentException("Unknown UnifiedScenario name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by UnifiedScenario names.
    /// </summary>
    public static /*UnifiedScenario*/int fromNames(Set<String> unifiedScenarioNames)
    {
    	/*UnifiedScenario*/int result = 0;
    	for (String name : unifiedScenarioNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of UnifiedScenario values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LOAD_FORMAT_MASK,
    		SAVE_FORMAT_MASK,
    		NO_GOLD,
    		EXPORT_ONLY,
    		DOC_2_DOC,
    		DOC_2_DOCX,
    		DOC_2_DOCM,
    		DOC_2_RTF,
    		DOC_2_WML,
    		DOCX_DML_2_DOCX_DML,
    		DOCX_2_DOCX_NO_GOLD,
    		DOCM_2_DOCM_NO_GOLD,
    		RTF_2_RTF_NO_GOLD,
    		WML_2_WML_NO_GOLD,
    		DOCX_2_DOCX,
    		DOCX_2_RTF,
    		DOCX_2_WML,
    		DOCX_2_MD,
    		RTF_2_RTF,
    		RTF_2_DOCX,
    		WML_2_WML,
    		MD_2_MD,
    		MD_2_DOCX,
    	};
    }

    public static final int length = 23;
}

