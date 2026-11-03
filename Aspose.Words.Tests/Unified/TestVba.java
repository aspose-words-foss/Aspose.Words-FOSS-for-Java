// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for macros in documents.
/// </summary>
@Test
public class TestVba extends UnifiedTestsBase
{
    /// <summary>
    /// This is needed because we have to deal with DOCM files instead of DOCX.
    /// </summary>
    private static /*UnifiedScenario*/int getDocxMacroAdjustedScenario(/*LoadFormat*/int lf, /*SaveFormat*/int sf)
    {
        return TestUtil.getUnifiedScenario(
            (lf == LoadFormat.DOCX) ? LoadFormat.DOCM : lf,
            (sf == SaveFormat.DOCX) ? SaveFormat.DOCM : sf);
    }



    /// <summary>
    /// Performs a check for a digital signature to be preserved.
    /// </summary>
    private static void testDigitalSignatureCore(
        /*LoadFormat*/int lf,
        /*SaveFormat*/int sf,
        String shortFileNameWithoutExtension,
        int sigLength,
        int sig0,
        int sig1,
        int sig8,
        int sig9) throws Exception
    {
        /*UnifiedScenario*/int scenario = getDocxMacroAdjustedScenario(lf, sf);

        Document doc = TestUtil.openSaveOpen(shortFileNameWithoutExtension, scenario);

        // RK This switch is needed because RTF format does not have VBA.
        switch (scenario)
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCM:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.DOCM_2_DOCM_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                msAssert.isNotNull(doc.getVbaProject());
                byte[] vbaSignature = doc.getVbaProject().getSignature();
                msAssert.isNotNull(vbaSignature);
                // This is the length of the signature with the 8 byte header.
                msAssert.areEqual(sigLength, vbaSignature.length);
                // Start of the header.
                msAssert.areEqual(sig0, vbaSignature[0]);
                msAssert.areEqual(sig1, vbaSignature[1]);
                // Start of the signature.
                msAssert.areEqual(sig8, vbaSignature[8]);
                msAssert.areEqual(sig9, vbaSignature[9]);
                break;
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                msAssert.isNull(doc.getVbaProject());
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

    /// <summary>
    /// Checks how macro names displayed in the Macros window are preserved.
    /// Also checks the document events.
    /// Also checks how macros can be programmatically removed.
    ///
    /// WORDSNET-829 Document OnOpen event no longer fires when opened and saved.
    /// Really obscure and undocumented behavior from MS Word, cannot solve quickly.
    ///
    /// WORDSNET-9658 Macros are no longer visible in the Macros window.
    /// The problem was that vbaData.xml was not processed.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestMacroNames(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = getDocxMacroAdjustedScenario(lf, sf);

        Document doc = TestUtil.openSaveOpen("Model\\Vba\\TestMacroNames", scenario);

        // RK This switch is needed because RTF format does not have VBA.
        switch (scenario)
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCM:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.DOCM_2_DOCM_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            {
                msAssert.isNotNull(doc.getVbaProject());
                ArrayList<String> vbaMacroNames = doc.getVbaProject().getMacroNames();
                msAssert.areEqual(1, vbaMacroNames.size());
                msAssert.areEqual("Project.Module1.MyTestMacro", vbaMacroNames.get(0));

                msAssert.areEqual(VbaDocumentEvents.OPEN | VbaDocumentEvents.XML_AFTER_INSERT, doc.getVbaDocumentEvents());
                break;
            }
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            {
                msAssert.isNull(doc.getVbaProject());
                msAssert.areEqual(0, (int)doc.getVbaDocumentEvents());
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }

        doc.removeMacros();

        // When you open this document in MS Word, press Alt+F11 and check there is no vba project.
        // There is no Macros in this document now, it should be possible to save as DOCX, not DOCM.
        TestUtil.saveOpen(doc, "Model\\Vba\\TestRemoveVba", TestUtil.getUnifiedScenario(lf, sf));

        // Check the macros and digital signature were removed.
        msAssert.isFalse(doc.hasMacros());
        msAssert.isNull(doc.getVbaProject());
        msAssert.areEqual(0, (int)doc.getVbaDocumentEvents());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestMacroNamesSourceDataProvider")
	public void unifiedTestMacroNamesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestMacroNames(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestMacroNamesSourceDataProvider")
	Object[][] unifiedTestMacroNamesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    private static void checkAcd(Document doc, int acdIndex, /*FixedCommandIdentifier*/int fci, Object arg)
    {
        AllocatedCommand acd = (AllocatedCommand)doc.getAllocatedCommands().get(acdIndex);
        msAssert.areEqual(fci, acd.FciBasedOn);

        if (arg instanceof byte[])
            ArrayUtil.checkArraysEqual((byte[])arg, acd.ArgValue);
        else if (arg instanceof String)
            msAssert.areEqual(arg, Encoding.getUnicode().getString(acd.ArgValue));
    }

    /// <summary>
    /// Tests how keymap customizations work.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestKeyMap(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        /*UnifiedScenario*/int scenario = getDocxMacroAdjustedScenario(lf, sf);

        Document doc = TestUtil.openSaveOpen("Model\\Vba\\TestKeyMap", scenario);

        // RK This switch is needed because RTF format does not have customizations.
        switch (scenario)
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCM:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.DOCM_2_DOCM_NO_GOLD:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            {
                msAssert.areEqual(null, doc.getAttachedToolbars());

                msAssert.areEqual(6, doc.getKeyMaps().size());
                checkKeyMap(doc, 0, KeyMapType.MASK, 0x0241, 0, 0, 0, 0, null);
                checkKeyMap(doc, 1, KeyMapType.ALLOCATED_COMMAND, 0x0242, 0, 0, 0, 0, null);
                checkKeyMap(doc, 2, KeyMapType.ALLOCATED_COMMAND, 0x0248, 0x0031, 1, 0, 0, null);
                checkKeyMap(doc, 3, KeyMapType.MACRO, 0x024d, 0, 0, 0, 0, "PROJECT.NEWMACROS.MACRO1");
                checkKeyMap(doc, 4, KeyMapType.INSERT_CHARACTER, 0x0254, 0x004d, 0, 0x2122, 0, null);
                checkKeyMap(doc, 5, KeyMapType.FIXED_COMMAND, 0x0341, 0, 0, 0, 0x00ed, null);

                // Check acds
                msAssert.areEqual(2, doc.getAllocatedCommands().size());
                checkAcd(doc, 0, FixedCommandIdentifier.APPLY_AUTO_TEXT_NAME, "Best regards,");
                checkAcd(doc, 1, FixedCommandIdentifier.APPLY_STYLE_NAME, new byte[] { (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0 }); // Heading 1
                break;
            }
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            {
                // Check RTF has nothing of that.
                msAssert.isNull(doc.getAttachedToolbars());
                msAssert.isNull(doc.getAllocatedCommands());
                msAssert.isNull(doc.getVbaProject());
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestKeyMapSourceDataProvider")
	public void unifiedTestKeyMapSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestKeyMap(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestKeyMapSourceDataProvider")
	Object[][] unifiedTestKeyMapSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Verifies ability to modify macro source code.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestModifyMacroSourceCode(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String NEW_SOURCE_CODE = "Test change source code";

        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            return;

        /*LoadFormat*/int loadFormat = (lf == LoadFormat.DOCX) ? LoadFormat.DOCM : lf;

        Document doc = TestUtil.open("Model\\Vba\\TestMacroNames", loadFormat);
        VbaProject project = doc.getVbaProject();

        msAssert.areEqual(2, project.getModules().getCount());
        msAssert.areEqual("ThisDocument", project.getModules().get(0).getName());
        msAssert.areEqual("Module1", project.getModules().get(1).getName());

        // Verify initial VbaProject.
        String m0SourceCode = project.getModules().get(0).getSourceCode();
        String m1SourceCode = project.getModules().get(1).getSourceCode();
        msAssert.isTrue(m0SourceCode.contains("Attribute VB_GlobalNameSpace"));
        msAssert.isTrue(m1SourceCode.contains("Hello from normal macro!"));

        // Set a new source code.
        project.getModules().get(1).setSourceCode(NEW_SOURCE_CODE);

        /*UnifiedScenario*/int scenario = getDocxMacroAdjustedScenario(lf, sf) | UnifiedScenario.NO_GOLD;
        doc = TestUtil.saveOpen(doc, "Model\\Vba\\TestModifyMacro", scenario);
        project = doc.getVbaProject();

        // Verify modified source code was changed, whereas other project parts remains the same.
        msAssert.areEqual(2, project.getModules().getCount());
        msAssert.areEqual("ThisDocument", project.getModules().get(0).getName());
        msAssert.areEqual("Module1", project.getModules().get(1).getName());
        msAssert.areEqual(m0SourceCode, project.getModules().get(0).getSourceCode());

        msAssert.areEqual(NEW_SOURCE_CODE, project.getModules().get(1).getSourceCode());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestModifyMacroSourceCodeSourceDataProvider")
	public void unifiedTestModifyMacroSourceCodeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestModifyMacroSourceCode(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestModifyMacroSourceCodeSourceDataProvider")
	Object[][] unifiedTestModifyMacroSourceCodeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkKeyMap(
        Document doc,
        int keyMapIndex,
        /*KeyMapType*/int type,
        int kcmPrimary,
        int kcmSecondary,
        int acdIndex,
        int characterCode,
        int fixedCommandIdentifier,
        String macroName)
    {
        KeyMap keyMap = (KeyMap)doc.getKeyMaps().get(keyMapIndex);
        msAssert.areEqual(type, keyMap.KeymapType);
        msAssert.areEqual(kcmPrimary, keyMap.KcmPrimary);
        msAssert.areEqual(kcmSecondary, keyMap.KcmSecondary);
        msAssert.areEqual(acdIndex, keyMap.AllocatedCommandIndex);
        msAssert.areEqual(characterCode, keyMap.CharacterCode);
        msAssert.areEqual(fixedCommandIdentifier, (int)keyMap.FixedCommandIdentifier);
        msAssert.areEqual(macroName, keyMap.MacroName);
    }

    /// <summary>
    /// WORDSNET-19530 NullReferenceException occurs during saving document to DOC/DOCX
    /// wne:argValue is not specified in the DOCX file and leaves the value null and then that crashes on write to DOC or DOCX.
    /// Fixed code to leave empty byte array instead of null.
    /// </summary>
    @Test
    public void testAllocatedCommandCtor()
    {
        AllocatedCommand cmd = new AllocatedCommand();
        msAssert.areEqual(0, cmd.ArgValue.length);    // This must not be null.
    }

    /// <summary>
    /// WORDSNET-3714 Consider adding an ability to read macros from documents.
    /// Added basic VBA project reader.
    /// </summary>
    @Test
    public void testVbaProject() throws Exception
    {
        Document doc = TestUtil.open("Model\\Vba\\Test3714.docm");

        VbaProject project = doc.getVbaProject();

        msAssert.areEqual(3, project.getModules().getCount());

        msAssert.areEqual("ThisDocument", project.getModules().get(0).getName());
        msAssert.areEqual("NewMacros", project.getModules().get(1).getName());
        msAssert.areEqual("Module1", project.getModules().get(2).getName());

        msAssert.isTrue(project.getModules().get(0).getSourceCode().contains("Attribute VB_GlobalNameSpace"));
        msAssert.isTrue(project.getModules().get(1).getSourceCode().contains("Selection.HomeKey Unit:=wdLine, Extend:=wdExtend"));
        msAssert.isTrue(project.getModules().get(2).getSourceCode().contains("MacroInModule11 Macro"));
    }

    /// <summary>
    /// [MS-OVBA] 3.2.1 No Compression Example.
    /// </summary>
    @Test
    public void testNoCompressionDecompression() throws Exception
    {
        final String INPUT_TEXT = "abcdefghijklmnopqrstuv.";
        final String EXPECTED_COMPRESSED_OUTPUT =
        "01 19 B0 00 61 62 63 64 65 66 67 68 00 69 6A 6B 6C 6D 6E 6F 70 00 71 72 73 74 75 76 2E";
        final String EXPECTED_DECOMPRESSED_OUTPUT =
        "61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F 70 71 72 73 74 75 76 2E";

        testCompressionDecompression(INPUT_TEXT, EXPECTED_COMPRESSED_OUTPUT, EXPECTED_DECOMPRESSED_OUTPUT);
    }

    /// <summary>
    /// [MS-OVBA] 3.2.2 Normal Compression Example.
    /// </summary>
    @Test
    public void testNormalCompressionDecompression() throws Exception
    {
        final String INPUT_TEXT = "#aaabcdefaaaaghijaaaaaklaaamnopqaaaaaaaaaaaarstuvwxyzaaa";
        final String EXPECTED_COMPRESSED_OUTPUT =
        "01 2F B0 00 23 61 61 61 62 63 64 65 82 66 00 70 61 67 68 69 6A 01 38 08 61 6B 6C 00 20 6D 6E 6F" +
        "70 06 71 02 70 04 00 72 73 74 75 76 10 77 78 79 7A 00 2C";
        final String EXPECTED_DECOMPRESSED_OUTPUT =
        "23 61 61 61 62 63 64 65 66 61 61 61 61 67 68 69 6A 61 61 61 61 61 6B 6C 61 61 61 6D 6E 6F 70 71" +
        "61 61 61 61 61 61 61 61 61 61 61 61 72 73 74 75 76 77 78 79 7A 61 61 61";

        testCompressionDecompression(INPUT_TEXT, EXPECTED_COMPRESSED_OUTPUT, EXPECTED_DECOMPRESSED_OUTPUT);
    }

    /// <summary>
    /// [MS-OVBA] 3.2.3 Maximum Compression Example.
    /// </summary>
    @Test
    public void testMaximumCompressionDecompression() throws Exception
    {
        final String INPUT_TEXT = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        final String EXPECTED_COMPRESSED_OUTPUT = "01 03 B0 02 61 45 00";
        final String EXPECTED_DECOMPRESSED_OUTPUT =
        "61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 " +
        "61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 " +
        "61 61 61 61 61 61 61 61 61";

        testCompressionDecompression(INPUT_TEXT, EXPECTED_COMPRESSED_OUTPUT, EXPECTED_DECOMPRESSED_OUTPUT);
    }

    /// <summary>
    ///  WORDSNET-19153 Improve a VBAProject class to be able to read all project content
    ///  into the document model.
    /// </summary>
    @Test
    public void test19153() throws Exception
    {
        String testName = "Model\\Vba\\Test3714";
        Document doc = TestUtil.open(testName+ ".docm");

        verifyVba3714(doc);

        doc.getVbaProject().markModified();
        doc = TestUtil.saveOpen(doc, testName,
            TestUtil.getUnifiedScenario(LoadFormat.DOCM, SaveFormat.DOCM) | UnifiedScenario.NO_GOLD);

        verifyVba3714(doc);
    }

    /// <summary>
    /// Verifies creation, export and import of a new empty VbaProject.
    /// </summary>
    @Test
    public void test19153_CreateNewEmptyProject() throws Exception
    {
        Document doc = new Document();

        VbaProject project = new VbaProject();
        doc.setVbaProject(project);

        verifyNewEmptyProject(doc);

        String testName = "Model\\Vba\\TestEmptyNewProject";
        doc = TestUtil.saveOpen(doc, testName,
            TestUtil.getUnifiedScenario(LoadFormat.DOCM, SaveFormat.DOCM) | UnifiedScenario.NO_GOLD);

        verifyNewEmptyProject(doc);
    }

    /// <summary>
    /// Verifies creation, export and import of a new VbaProject with adding a model.
    /// </summary>
    @Test
    public void test19153_CreateNewProject() throws Exception
    {
        Document doc = new Document();

        VbaProject project = new VbaProject();
        project.setName("AsposeProject");
        doc.setVbaProject(project);

        VbaModule module = new VbaModule("AsposeModule");
        module.setSourceCode("New source code");

        doc.getVbaProject().getModules().add(module);

        verifyCreatedNewProject(doc);

        String testName = "Model\\Vba\\TestNewProject";
        doc = TestUtil.saveOpen(doc, testName,
            TestUtil.getUnifiedScenario(LoadFormat.DOCM, SaveFormat.DOCM) | UnifiedScenario.NO_GOLD);

        verifyCreatedNewProject(doc);
    }

    /// <summary>
    /// Verifies remove module from the VbaModuleCollection.
    /// </summary>
    @Test
    public void test19153_ModuleCollectionRemove() throws Exception
    {
        Document doc = new Document();

        VbaProject project = new VbaProject();
        project.setName("AsposeProject");
        doc.setVbaProject(project);

        VbaModule module = new VbaModule("AsposeModule");

        doc.getVbaProject().getModules().add(module);
        msAssert.areEqual(2, project.getModules().getCount());
        msAssert.isNotNull(project.getModules().get("AsposeModule"));

        doc.getVbaProject().getModules().remove(module);
        msAssert.areEqual(1, project.getModules().getCount());
        msAssert.isNull(project.getModules().get("AsposeModule"));
    }

    private void verifyNewEmptyProject(Document doc)
    {
        VbaProject p = doc.getVbaProject();
        msAssert.areEqual("Project", p.getName());

        msAssert.areEqual(1251, p.getCodePage());
        msAssert.areEqual(1, p.getSysKind());

        msAssert.areEqual(0x00000409, p.getLcid());
        msAssert.areEqual(0x00000409, p.getLcidInvoke());

        msAssert.areEqual("", p.getDescription());

        msAssert.areEqual(1, p.getVersionMajor());
        msAssert.areEqual(1, p.getVersionMinor());

        VbaModuleCollection modules = p.getModules();
        msAssert.areEqual(1, modules.getCount());

        VbaModule module = modules.get(0);

        msAssert.areEqual("ThisDocument", module.getName());
        msAssert.areEqual(VbaModuleType.DOCUMENT_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());
    }

    private void verifyCreatedNewProject(Document doc)
    {
        VbaProject p = doc.getVbaProject();
        msAssert.areEqual("AsposeProject", p.getName());

        msAssert.areEqual(1251, p.getCodePage());
        msAssert.areEqual(1, p.getSysKind());

        msAssert.areEqual(0x00000409, p.getLcid());
        msAssert.areEqual(0x00000409, p.getLcidInvoke());

        msAssert.areEqual("", p.getDescription());

        msAssert.areEqual(1, p.getVersionMajor());
        msAssert.areEqual(1, p.getVersionMinor());

        VbaModuleCollection modules = p.getModules();
        msAssert.areEqual(2, modules.getCount());

        VbaModule module = modules.get(0);

        msAssert.areEqual("ThisDocument", module.getName());
        msAssert.areEqual(VbaModuleType.DOCUMENT_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());

        module = modules.get(1);
        msAssert.areEqual("AsposeModule", module.getName());
        msAssert.areEqual("New source code", module.getSourceCode());
        msAssert.areEqual(VbaModuleType.PROCEDURAL_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());
    }


    /// <summary>
    /// WORDSNET-19556 Added a feature to clone VbaPorject.
    /// </summary>
    @Test
    public void test19556() throws Exception
    {
        Document sourceDoc = TestUtil.open("Model\\Vba\\Test3714.docm");

        VbaProject sourceProject = sourceDoc.getVbaProject();

        Document destDoc = new Document();
        VbaProject destProject = sourceProject.deepClone();
        destDoc.setVbaProject(destProject);

        msAssert.isFalse(msObject.referenceEquals(sourceProject, destProject));
        msAssert.isFalse(msObject.referenceEquals(sourceProject.getModules(), destProject.getModules()));
        msAssert.isFalse(msObject.referenceEquals(sourceProject.getReferences(), destProject.getReferences()));

        destDoc = TestUtil.saveOpen(destDoc, "Model\\Vba\\Test19556",
            TestUtil.getUnifiedScenario(LoadFormat.DOCM, SaveFormat.DOCM) | UnifiedScenario.NO_GOLD);

        verifyVba3714(destDoc);
    }

    /// <summary>
    /// WORDSNET-15697 VBAProject password protection was lost after changes in this project.
    /// We must preserve several project parameters in a stream "PROJECT" to preserve password protection.
    /// </summary>
    @Test
    public void test15697() throws Exception
    {
        Document doc = TestUtil.open("Model\\Vba\\Test15697.docm");

        VbaModule module = doc.getVbaProject().getModules().get(1);
        module.setSourceCode(module.getSourceCode().replace("Hello", "Test15697"));

        doc.getVbaProject().markModified();

        doc = TestUtil.saveOpen(doc, "Model\\Vba\\Test15697", TestUtil.getUnifiedScenario(LoadFormat.DOCM, SaveFormat.DOCM));

        VbaProject project = doc.getVbaProject();

        // Get a "PROJECT" stream as a text to verify the needed parameters.
        MemoryStream stream = project.getStorage().fetchStream(VbaRecord.PROJECT_STREAM_NAME);
        String stringProj = Encoding.getEncoding(project.getCodePage()).getString(stream.toArray());

        // To preserve password protection these four parameters must be preserved:
        msAssert.isTrue(stringProj.contains("ID=\"{00000000-0000-0000-0000-000000000000}\""));
        msAssert.isTrue(stringProj.contains("CMG=\"848628B6283E2C3E2C3A303A30\""));
        msAssert.isTrue(stringProj.contains("DPB=\"080AA4ABC1ABC1543FACC1E396A1EBDAC59C863691AD483DB71A1082E7C25048C7C73E2E\""));
        msAssert.isTrue(stringProj.contains("GC=\"8C8E20BE204321432143\""));

        // Lets check modified source code.
        msAssert.isTrue(project.getModules().get(1).getSourceCode().contains("Test15697"));
    }




    /// <summary>
    /// WORDSNET-12444 Support an API to check if VBAcode is signed.
    /// <see cref="VbaProject.IsSigned"/> was implemented.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestVbaProjectIsSigned(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        String testName = "Model\\Vba\\Test12444";
        if ((lf == LoadFormat.RTF) || (sf == SaveFormat.RTF))
            return;
        /*LoadFormat*/int loadFormat = (lf == LoadFormat.DOCX) ? LoadFormat.DOCM : lf;
        /*SaveFormat*/int saveFormat = (sf == SaveFormat.DOCX) ? SaveFormat.DOCM : sf;

        Document doc = TestUtil.open(testName, loadFormat);

        // Load signed document, verify project is signed.
        VbaProject project = doc.getVbaProject();
        msAssert.isTrue(project.isSigned());

        // Modify project property and verify project is not signed anymore.
        project.setName("New Name");
        msAssert.isFalse(project.isSigned());

        doc = TestUtil.saveOpen(doc, testName, TestUtil.getUnifiedScenario(loadFormat, saveFormat) | UnifiedScenario.NO_GOLD);
        msAssert.isFalse(doc.getVbaProject().isSigned());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestVbaProjectIsSignedSourceDataProvider")
	public void unifiedTestVbaProjectIsSignedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestVbaProjectIsSigned(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestVbaProjectIsSignedSourceDataProvider")
	Object[][] unifiedTestVbaProjectIsSignedSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-25332 Accents are lost when VBA source code is written to document.
    /// Added setter for VbaProject.CodePage property.
    /// </summary>
    @Test (dataProvider = "test25332DataProvider")
    public void test25332(String text, int codePage) throws Exception
    {
        String sourceCode= msString.format(
            "Private Sub Test()\r\n    Dim test As String\r\n    Set test = \"{0}\"\r\nEnd Sub", text);

        Document doc = new Document();
        doc.setVbaProject(new VbaProject());

        // Explicitly set Portuguese code page.
        doc.getVbaProject().setCodePage(codePage);

        VbaModule module =  new VbaModule();
        module.setName("Test");
        doc.getVbaProject().getModules().add(module);
        module.setSourceCode(sourceCode);

        doc = TestUtil.saveOpen(doc, SaveFormat.DOCM);

        msAssert.areEqual(sourceCode, doc.getVbaProject().getModules().get("Test").getSourceCode());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25332DataProvider")
	public static Object[][] test25332DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ãç",  860},
			{"фй",  1251},
		};
	}

    /// <summary>
    /// #WORDSNET-26750 Investigate whether it is possible to check if the document has macros without loading document into the model.
    /// Added macros presence detection to FileFormatUtil class.
    /// </summary>
    @Test (dataProvider = "test26750DataProvider")
    public void test26750(String fileName, boolean hasMacros) throws Exception
    {
        FileFormatInfo fi = FileFormatUtil.detectFileFormat(TestUtil.buildTestFileName(fileName));
        msAssert.areEqual(hasMacros, fi.hasMacros());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26750DataProvider")
	public static Object[][] test26750DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Vba\\Test12444.doc",  true},
			{"Model\\Vba\\Test3714.docm",  true},
			{"ImportDocx\\Test18712.docx",  false},
		};
	}

    /// <summary>
    /// WORDSNET-26289 Consider providing VbaProject.IsProtected property.
    /// Added public property VbaProject.IsProtected.
    /// </summary>
    @Test (dataProvider = "test26289DataProvider")
    public void test26289(String fileName, boolean isProtected) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        msAssert.areEqual(isProtected, doc.getVbaProject().isProtected());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26289DataProvider")
	public static Object[][] test26289DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Vba\\Test15697.docm",  true},
		};
	}


    /// <summary>
    /// Compression and decompression of [MS-OVBA] examples.
    /// Core test for WORDSNET-18697
    /// </summary>
    private static void testCompressionDecompression(String inputText, String expectedCompressedOutput, String expectedDecompressedOutput) throws Exception
    {
        // Compression.
        MemoryStream inputStream = new MemoryStream(Encoding.getASCII().getBytes(inputText));
        MemoryStream outputStream = new MemoryStream();

        RleCompressor.compress(inputStream, outputStream);

        msAssert.areEqual(expectedCompressedOutput.replace(" ", ""), StringUtil.bytesToHex(outputStream.toArray()));

        // Decompression.
        outputStream.setPosition(0);
        MemoryStream decompressedStream = new MemoryStream();

        RleCompressor.decompress(outputStream, decompressedStream);

        msAssert.areEqual(expectedDecompressedOutput.replace(" ", ""), StringUtil.bytesToHex(decompressedStream.toArray()));
        msAssert.areEqual(inputText, Encoding.getASCII().getString(decompressedStream.toArray()));
    }

    private static void verifyVba3714(Document doc)
    {
        VbaProject p = doc.getVbaProject();
        msAssert.areEqual("MyProject", p.getName());
        msAssert.areEqual(1251, p.getCodePage());
        msAssert.areEqual(1, p.getSysKind());

        msAssert.areEqual(1033, p.getLcid());
        msAssert.areEqual(1033, p.getLcidInvoke());

        msAssert.areEqual("Описание проекта", p.getDescription());
        msAssert.areEqual("", p.getHelpFilePath1());
        msAssert.areEqual("", p.getHelpFilePath2());

        msAssert.areEqual(666, p.getHelpContext());
        msAssert.areEqual(0, p.getLibFlags());
        msAssert.areEqual(1588943535, p.getVersionMajor());
        msAssert.areEqual(6, p.getVersionMinor());

        msAssert.areEqual("", p.getConstants());

        VbaReferenceCollection references = p.getReferences();
        msAssert.areEqual(3, references.getCount());

        msAssert.areEqual(VbaReferenceType.REGISTERED, references.get(0).getType());
        msAssert.areEqual("stdole", references.get(0).getName());
        msAssert.areEqual("*\\G{00020430-0000-0000-C000-000000000046}#2.0#0#C:\\Windows\\SysWOW64\\"+
                "stdole2.tlb#OLE Automation", ((VbaReferenceRegistered)references.get(0)).getLibId());

        msAssert.areEqual(VbaReferenceType.PROJECT, references.get(1).getType());
        msAssert.areEqual("Normal", references.get(1).getName());
        VbaReferenceProject referenceProject = (VbaReferenceProject)references.get(1);
        msAssert.areEqual("*\\CNormal", referenceProject.getLibIdAbsolute());
        msAssert.areEqual("*\\CNormal", referenceProject.getLibIdRelative());
        msAssert.areEqual(1588889804, referenceProject.getMajorVersion());
        msAssert.areEqual(13, referenceProject.getMinorVersion());

        msAssert.areEqual("Office", references.get(2).getName());
        msAssert.areEqual(VbaReferenceType.REGISTERED, references.get(2).getType());
        msAssert.areEqual("*\\G{2DF8D04C-5BFA-101B-BDE5-00AA0044DE52}#2.0#0#C:\\Program Files (x86)\\Common "+
                            "Files\\Microsoft Shared\\OFFICE15\\MSO.DLL#Microsoft Office 15.0 Object Library", ((VbaReferenceRegistered)references.get(2)).getLibId());

        VbaModuleCollection modules = p.getModules();
        msAssert.areEqual(3, modules.getCount());

        VbaModule module = modules.get(0);
        msAssert.areEqual("ThisDocument", module.getName());
        msAssert.areEqual("Attribute VB_Name = \"ThisDocument\"\r\n" +
                "Attribute VB_Base = \"1Normal.ThisDocument\"\r\n" +
                "Attribute VB_GlobalNameSpace = False\r\n" +
                "Attribute VB_Creatable = False\r\n" +
                "Attribute VB_PredeclaredId = True\r\n" +
                "Attribute VB_Exposed = True\r\n" +
                "Attribute VB_TemplateDerived = True\r\n" +
                "Attribute VB_Customizable = True\r\n", module.getSourceCode());
        msAssert.areEqual(VbaModuleType.DOCUMENT_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());

        module = modules.get(1);
        msAssert.areEqual("NewMacros", module.getName());
        msAssert.areEqual("Attribute VB_Name = \"NewMacros\"\r\n" +
                "Sub Macro1()\r\n'\r\n" +
                "' Macro1 Macro\r\n" +
                "    Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n\r\n" +
                "    Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n" +
                "    \r\n    \r\n" +
                "        Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n    \r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n\r\n" +
                "    Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n    \r\n    \r\n" +
                "        Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n    \r\n" +
                "        Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n    \r\n" +
                "        Selection.TypeText Text:=\"test\"\r\n" +
                "    Selection.HomeKey Unit:=wdLine, Extend:=wdExtend\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.UnderlineColor = wdColorAutomatic\r\n" +
                "    Selection.Font.Underline = wdUnderlineSingle\r\n\r\nEnd Sub\r\n", module.getSourceCode());

        msAssert.areEqual(VbaModuleType.PROCEDURAL_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());

        module = modules.get(2);
        msAssert.areEqual("Module1", module.getName());
        msAssert.areEqual("Attribute VB_Name = \"Module1\"\r\n" +
                "Sub MacroInModule11()\r\n'\r\n" +
                "' MacroInModule11 Macro\r\n" +
                "    Selection.TypeText Text:=\"Module1\"\r\n\r\n" +
                "End Sub\r\n", module.getSourceCode());
        msAssert.areEqual(VbaModuleType.PROCEDURAL_MODULE, module.getType());
        msAssert.areEqual(0, module.getHelpContext());
        msAssert.areEqual("", module.getDocString());
    }
}

