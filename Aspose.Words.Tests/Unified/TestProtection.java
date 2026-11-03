// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Convert;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for document protection.
/// </summary>
@Test
public class TestProtection extends UnifiedTestsBase
{
    @Test
    public void testLegacyProtectionHashExample()
    {
        msAssert.areEqual(0x64ceed7e, DocumentProtection.calculateLegacyHash("Example"));
    }

    @Test
    public void testLegacyProtectionHashNull()
    {
        msAssert.areEqual(0, DocumentProtection.calculateLegacyHash(null));
    }

    @Test
    public void testLegacyProtectionHashEmpty()
    {
        msAssert.areEqual(0, DocumentProtection.calculateLegacyHash(""));
    }

    @Test
    public void testLegacyProtectionHashLong()
    {
        int hash1 = DocumentProtection.calculateLegacyHash("This is a very long password.");
        int hash2 = DocumentProtection.calculateLegacyHash("This is a very long password, but even longer than the previous one.");
        msAssert.areEqual(hash1, hash2);
        msAssert.areEqual(0x46ffeeff, hash1);
    }

    /// <summary>
    /// WORDSNET-9724 Document.Unprotect(String) doesn't remove protection from document.
    /// andrnosk: The problem occurs because CalculateDocumentProtectionHash method always uses default values for 
    /// algorithm, spin count and salt parameters, but in this case we have to use values specified inside 
    /// document to calculate correct hash.
    /// </summary>
    @Test
    public void testJira9724() throws Exception
    {
        Document doc = TestUtil.open("Model\\Protection\\TestJira9724.docx");
        msAssert.areEqual(doc.getProtectionType(), ProtectionType.ALLOW_ONLY_FORM_FIELDS);
        doc.unprotect("12345");
        msAssert.areEqual(doc.getProtectionType(), ProtectionType.NO_PROTECTION);
    }
    

    /// <summary>
    /// WORDSNET-7893 Consider adding a VerifiyPassword method.
    /// Implemented password validation for write-protected documents.
    /// Note, that Word 2013 stores hash with SHA-512 algorithm and other Word versions use SHA-1.
    /// </summary>
    @Test
    public void testJira7893() throws Exception
    {
        // Check Word2013 write-protected document.
        Document doc = TestUtil.open("Model\\Protection\\TestJira7893Word2013.docx");
        WriteProtection writeProtection = doc.getWriteProtection();
        msAssert.isTrue(writeProtection.validatePassword("!Qaz2wsx"));
        validateBadPasswords(writeProtection);

        // Check Word2010 write-protected document.
        doc = TestUtil.open("Model\\Protection\\TestJira7893Word2010.docx");
        writeProtection = doc.getWriteProtection();
        msAssert.isTrue(writeProtection.validatePassword("!Qaz2wsx"));
        validateBadPasswords(writeProtection);

        // Check unprotected document.
        doc = new Document();
        validateBadPasswords(doc.getWriteProtection());
    }



    /// <summary>
    /// The password is "test".
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCommentsOnly(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestProtectionCommentsOnly", lf, sf);

        msAssert.areEqual(ProtectionType.ALLOW_ONLY_COMMENTS, doc.getProtectionType());

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
                msAssert.areEqual(DocumentSecurity.READ_ONLY_EXCEPT_ANNOTATIONS, doc.getBuiltInDocumentProperties().getSecurity());
                break;
            default:
                msAssert.areEqual(DocumentSecurity.NONE, doc.getBuiltInDocumentProperties().getSecurity());
                break;
        }

        DocumentProtection prot = doc.getDocPr().DocumentProtection;
        PasswordHash hash = prot.getPasswordHash();
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.DOC_2_WML:
                // RK In these cases only "old" password hash exists.
                msAssert.areEqual(0x1FC6CBEB, prot.getLegacyHash());
                msAssert.areEqual(null, hash.Hash);
                msAssert.areEqual(null, hash.Salt);
                msAssert.areEqual(0, hash.CryptAlgorithmSid);
                msAssert.areEqual(0, hash.CryptSpinCount);
                break;
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // RK In these cases only "new" password hash exists.
                msAssert.areEqual(0, prot.getLegacyHash());
                msAssert.areEqual(20, hash.Hash.length);
                msAssert.areEqual(16, hash.Salt.length);
                msAssert.areEqual(4, hash.CryptAlgorithmSid);
                // "ms" files created using Word 2007 SP2 have a higher spin count value.
                msAssert.areEqual((lf == LoadFormat.DOC) ? 50000 : 100000, hash.CryptSpinCount);
                break;
            case UnifiedScenario.DOC_2_RTF:
                // RK Our RTF save-open supports both old and new password hashes.
                msAssert.areEqual(0x1FC6CBEB, prot.getLegacyHash());
                msAssert.areEqual(20, hash.Hash.length);
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCommentsOnlySourceDataProvider")
	public void unifiedTestCommentsOnlySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCommentsOnly(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCommentsOnlySourceDataProvider")
	Object[][] unifiedTestCommentsOnlySourceDataProvider() throws Exception
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


    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestNoProtection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestProtectionNone", lf, sf);
        msAssert.areEqual(ProtectionType.NO_PROTECTION, doc.getProtectionType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestNoProtectionSourceDataProvider")
	public void unifiedTestNoProtectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestNoProtection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestNoProtectionSourceDataProvider")
	Object[][] unifiedTestNoProtectionSourceDataProvider() throws Exception
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
    /// No password on this document.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestProtectionFormFields(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestProtectionFormFieldsOnly", lf, sf);
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, doc.getProtectionType());
        msAssert.areEqual(DocumentSecurity.NONE, doc.getBuiltInDocumentProperties().getSecurity());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectionFormFieldsSourceDataProvider")
	public void unifiedTestProtectionFormFieldsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtectionFormFields(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectionFormFieldsSourceDataProvider")
	Object[][] unifiedTestProtectionFormFieldsSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestProtectGeneratePassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestProtectionNone", lf, sf);

        // This is the "legacy" way of protecting with an unknown password because we didn't know the hash algorithm.
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS);

        doc = TestUtil.saveOpen(doc, "Model\\Protection\\TestProtectGeneratePassword", lf, sf);

        // Check the protection is now set with a password (albeit unknown).
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, doc.getProtectionType());
        msAssert.areEqual(DocumentSecurity.NONE, doc.getBuiltInDocumentProperties().getSecurity());

        DocumentProtection prot = doc.getDocPr().DocumentProtection;
        PasswordHash hash = prot.getPasswordHash();
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                // RK When document is opened/saved in these formats, only "old" password hash exists.
                msAssert.isTrue(prot.getLegacyHash() == 0x56AE3201);
                break;
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // RK If the file is opened/save in these formats, only "new" password hash exists.
                msAssert.areEqual(20, hash.Hash.length);
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectGeneratePasswordSourceDataProvider")
	public void unifiedTestProtectGeneratePasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtectGeneratePassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectGeneratePasswordSourceDataProvider")
	Object[][] unifiedTestProtectGeneratePasswordSourceDataProvider() throws Exception
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
    /// Changing protection type should not change existing protection password.
    /// The password is "test".
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestProtectKeepPassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestProtectionCommentsOnly", lf, sf);

        PasswordHash hashBefore = doc.getDocPr().DocumentProtection.getPasswordHash();
        
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS);

        doc = TestUtil.saveOpen(doc, "Model\\Protection\\TestProtectKeepPassword", lf, sf);
        
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, doc.getProtectionType());
        msAssert.areEqual(DocumentSecurity.NONE, doc.getBuiltInDocumentProperties().getSecurity());

        // Check the original password was kept.
        DocumentProtection prot = doc.getDocPr().DocumentProtection;
        PasswordHash hash = prot.getPasswordHash();
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.DOC_2_WML:
                // RK We have the legacy hash only, check it.
                msAssert.areEqual(0x1fc6cbeb, prot.getLegacyHash());
                msAssert.areEqual(null, hash.Hash);
                break;
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // RK Check the ooxml hash.
                msAssert.areEqual(4, hash.CryptAlgorithmSid);
                // "ms" files created using Word 2007 SP2 have a higher spin count value.
                msAssert.areEqual((lf == LoadFormat.DOC) ? 50000 : 100000, hash.CryptSpinCount);  
                msAssert.isTrue(hash != hashBefore);
                ArrayUtil.checkArraysEqual(hashBefore.Salt, hash.Salt);
                ArrayUtil.checkArraysEqual(hashBefore.Hash, hash.Hash);
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectKeepPasswordSourceDataProvider")
	public void unifiedTestProtectKeepPasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtectKeepPassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectKeepPasswordSourceDataProvider")
	Object[][] unifiedTestProtectKeepPasswordSourceDataProvider() throws Exception
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
    /// WORDSNET-4278 Support protection password export in RTF.
    /// WORDSNET-3613 Support proper protection password export in DOCX.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestProtectSetPassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS, "test");
        doc = TestUtil.saveOpen(doc, "Model\\Protection\\TestProtectSetPassword", lf, sf);

        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, doc.getProtectionType());

        // Check the password was set.
        DocumentProtection prot = doc.getDocPr().DocumentProtection;
        PasswordHash hash = prot.getPasswordHash();
        switch (sf)
        {
            case SaveFormat.DOC:
            case SaveFormat.WORD_ML:
                // RK Check the legacy hash.
                msAssert.areEqual(0x1fc6cbeb, prot.getLegacyHash());
                msAssert.areEqual(null, hash.Hash);
                break;
            case SaveFormat.DOCX:
            case SaveFormat.RTF:
                // RK Check the new hash.
                msAssert.areEqual(4, hash.CryptAlgorithmSid);
                msAssert.areEqual(50000, hash.CryptSpinCount);
                msAssert.areEqual("wZko1pqoSiQdGEuyf0evzPwm9AE=", Convert.toBase64String(hash.Hash));
                msAssert.areEqual("hIqOl99kIr/+N0hyovEq7Q==", Convert.toBase64String(hash.Salt));
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectSetPasswordSourceDataProvider")
	public void unifiedTestProtectSetPasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtectSetPassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectSetPasswordSourceDataProvider")
	Object[][] unifiedTestProtectSetPasswordSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestProtectNoPassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        
        // Verify we allow empty string.
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS, "");
        msAssert.areEqual(0, doc.getDocPr().DocumentProtection.getLegacyHash());
        msAssert.areEqual(null, doc.getDocPr().DocumentProtection.getPasswordHash().Hash);

        // Verify we allow null.
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS, null);
        msAssert.areEqual(0, doc.getDocPr().DocumentProtection.getLegacyHash());
        msAssert.areEqual(null, doc.getDocPr().DocumentProtection.getPasswordHash().Hash);

        // Check after save open.
        doc = TestUtil.saveOpen(doc, "Model\\Protection\\TestProtectNoPassword", lf, sf);
        msAssert.areEqual(0, doc.getDocPr().DocumentProtection.getLegacyHash());
        msAssert.areEqual(null, doc.getDocPr().DocumentProtection.getPasswordHash().Hash);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestProtectNoPasswordSourceDataProvider")
	public void unifiedTestProtectNoPasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestProtectNoPassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestProtectNoPasswordSourceDataProvider")
	Object[][] unifiedTestProtectNoPasswordSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestReadOnlyRecommended(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual(false, doc.getDocPr().WriteProtection.getReadOnlyRecommended());

        doc = TestUtil.openSaveOpen("Model\\Protection\\TestReadOnlyRecommended", lf, sf);

        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                // RK For RTF although the property is documented and written, MS Word seems to ignore it.
                msAssert.areEqual(true, doc.getDocPr().WriteProtection.getReadOnlyRecommended());
                break;
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                // RK WordML does not have this property.
                msAssert.areEqual(false, doc.getDocPr().WriteProtection.getReadOnlyRecommended());
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestReadOnlyRecommendedSourceDataProvider")
	public void unifiedTestReadOnlyRecommendedSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestReadOnlyRecommended(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestReadOnlyRecommendedSourceDataProvider")
	Object[][] unifiedTestReadOnlyRecommendedSourceDataProvider() throws Exception
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
    /// Also known as write protection password.
    /// The password is "test".
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSharingPassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestSharingPassword", lf, sf);

        WriteProtection prot = doc.getDocPr().WriteProtection;
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
                // RK This password is actually stored in DOC.
                msAssert.areEqual("test", prot.getPassword());
                msAssert.isTrue(prot.getPasswordHash().isEmpty());
                break;
            case UnifiedScenario.DOC_2_DOCX:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                msAssert.areEqual("", prot.getPassword());
                msAssert.isFalse(prot.getPasswordHash().isEmpty());
                break;
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
                // RK WordML does not have this feature.
                msAssert.areEqual("", prot.getPassword());
                msAssert.isTrue(prot.getPasswordHash().isEmpty());
                break;
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSharingPasswordSourceDataProvider")
	public void unifiedTestSharingPasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSharingPassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSharingPasswordSourceDataProvider")
	Object[][] unifiedTestSharingPasswordSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestUnprotectWithPassword(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String CORRECT_PASSWORD = "MyPassword1234567890";
        final String WRONG_PASSWORD = "ThisIsIncorrectPassword";

        Document doc = new Document();

        // Protect document using correct password.
        doc.protect(ProtectionType.READ_ONLY, CORRECT_PASSWORD);
        msAssert.areEqual(ProtectionType.READ_ONLY, doc.getProtectionType());

        // Try unprotecting with wrong password.
        msAssert.isFalse(doc.unprotect(WRONG_PASSWORD));
        msAssert.areEqual(ProtectionType.READ_ONLY, doc.getProtectionType());

        // Try unprotecting with correct password.
        msAssert.isTrue(doc.unprotect(CORRECT_PASSWORD));
        msAssert.areEqual(ProtectionType.NO_PROTECTION, doc.getProtectionType());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestUnprotectWithPasswordSourceDataProvider")
	public void unifiedTestUnprotectWithPasswordSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestUnprotectWithPassword(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestUnprotectWithPasswordSourceDataProvider")
	Object[][] unifiedTestUnprotectWithPasswordSourceDataProvider() throws Exception
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
    /// WORDSNET-20831 Section.ProtectedForForms returns true for all sections in DOCX format even when a section is not protected.
    /// SectAttr.Unlocked is true when the section is not protected with forms,
    /// but in DOCX/WML formProt = 0 (false) when the section is not protected (see 2.6.6 in OOXML spec). 
    /// So the correct value of SectAttr.Unlocked should be !formProt.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect20831(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Protection\\TestDefect20831", lf, sf);

        // The document is protect with AllowOnlyFormFields protection type.
        msAssert.areEqual(ProtectionType.ALLOW_ONLY_FORM_FIELDS, doc.getProtectionType());

        // There are 3 sections in the document, the second is not protected.
        msAssert.isTrue(doc.getSections().get(0).getProtectedForForms());
        msAssert.isFalse(doc.getSections().get(1).getProtectedForForms());
        msAssert.isTrue(doc.getSections().get(2).getProtectedForForms());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect20831SourceDataProvider")
	public void unifiedTestDefect20831Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect20831(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect20831SourceDataProvider")
	Object[][] unifiedTestDefect20831SourceDataProvider() throws Exception
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
    /// Validates write-protection password against obviously wrong, 'empty' and 'null' password strings.
    /// </summary>
    private static void validateBadPasswords(WriteProtection writeProtection)
    {
        msAssert.isFalse(writeProtection.validatePassword("wrong_password"));
        msAssert.isFalse(writeProtection.validatePassword(""));
        msAssert.isFalse(writeProtection.validatePassword(null));
    }

    // FOSS: TestJira12137 removed — it validated a legacy plain (unhashed) write-protection password
    // in a binary .doc; DOC/DOT load and that legacy password path are gone.

    /// <summary>
    /// WORDSNET-12495 Password protected document cannot be unlocked anymore after editing with aspose
    /// RsaAES should be written in cryptProviderType element for SHA-512 protection.
    /// </summary>
    @Test
    public void testJira12495() throws Exception
    {
        final String TEST_FILE = "Model\\Protection\\TestJira12495.docx";

        Document doc = TestUtil.open(TEST_FILE);
        String outFile = TestUtil.save(doc, TEST_FILE);

        msAssert.isTrue(TestUtil.isWrittenToOpcPackage(outFile, "/word/settings.xml", "rsaAES"));
    }





    /// <summary>
    /// WORDSNET-22989 Document can be unprotected with an empty password.
    /// An empty password shouldn't unprotect the document.
    /// </summary>
    @Test
    public void test26170() throws Exception
    {
        Document doc = TestUtil.open("Model\\Protection\\Test26170.docx");

        // Trying to unprotect with an empty password.
        msAssert.areEqual(false, doc.unprotect(""));
        msAssert.areEqual(doc.getProtectionType(), ProtectionType.ALLOW_ONLY_FORM_FIELDS);

        // Trying to unprotect with correct password.
        msAssert.areEqual(true, doc.unprotect("test"));
        msAssert.areEqual(doc.getProtectionType(), ProtectionType.NO_PROTECTION);
    }
}

