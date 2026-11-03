// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests related to DOCX encryption.
/// </summary>
@Test
public class TestDocxEncryption
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testEncrypted() throws Exception
    {
        String fileName = TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncrypted.docx");

        FileFormatInfo info = FileFormatUtil.detectFileFormat(fileName);
        msAssert.areEqual(LoadFormat.DOCX, info.getLoadFormat());
        msAssert.areEqual(true, info.isEncrypted());

        Document doc = new Document(fileName, new LoadOptions("workshare"));

        msAssert.areEqual("Hello World from an encrypted document!\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-8935 “FileCorruptedException” exception occurs when try to open encrypted DOCX file.
    /// Fixed to throw a proper exception.
    /// </summary>
    @Test (expectedExceptions = IncorrectPasswordException.class)
    public void testEncryptedWrongPassword() throws Exception
    {
        new Document(TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncrypted.docx"), new LoadOptions(msString.newString('A', 100)));
    }

    @Test (expectedExceptions = IncorrectPasswordException.class)
    public void testEncryptedEmptyPassword() throws Exception
    {
        new Document(TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncrypted.docx"), new LoadOptions(""));
    }

    @Test (expectedExceptions = IncorrectPasswordException.class)
    public void testEncryptedNullPassword() throws Exception
    {
        new Document(TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncrypted.docx"), new LoadOptions((String)null));
    }

    /// <summary>
    /// Although the user specifies a wrong load format we still load the document okay.
    /// </summary>
    @Test
    public void testEncryptedWrongLoadFormat() throws Exception
    {
        Document doc = new Document(
            TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncrypted.docx"), new LoadOptions("workshare"));

        msAssert.areEqual(LoadFormat.DOCX, doc.getOriginalLoadFormat());
        msAssert.areEqual("Hello World from an encrypted document!\f", doc.getText());
    }

    /// <summary>
    /// The document is an encrypted DOCX, but contains a stream of a DOC document that was
    /// causing wrong detection. Fixed.
    /// Passwords to open it are:
    /// pass1: Libertybell
    /// pass2: GreekTragedy
    /// </summary>
    @Test
    public void testEncryptedWithWordDocumentStream() throws Exception
    {
        Document doc = new Document(
            TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestEncryptedWithWordDocumentStream.docx"),
            new LoadOptions("Libertybell"));

        msAssert.areEqual(57, doc.getChildNodes(NodeType.PARAGRAPH, true).getCount());
    }

    @Test
    public void testAgileEncryption() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Encryption\\TestAgileEncryption.docx", new LoadOptions("123"));
        // insane check
        msAssert.areEqual("test\f", doc.getText());

        TestUtil.open("ImportDocx\\Encryption\\TestAgileEncryptionBigger.docx", new LoadOptions("password"));
    }

    @Test (expectedExceptions = IncorrectPasswordException.class)
    public void testAgileEncryptionBadPassword() throws Exception
    {
        TestUtil.open("ImportDocx\\Encryption\\TestAgileEncryption.docx", new LoadOptions("1234"));
    }

    @Test (expectedExceptions = FileCorruptedException.class)
    public void testAgileEncryptionIntegrity() throws Exception
    {
        TestUtil.open("ImportDocx\\Encryption\\TestAgileEncryption Modified.docx", new LoadOptions("123"));
    }

    /// <summary>
    /// Encrypt with password and open with wrong password.
    /// </summary>
    @Test (expectedExceptions = IncorrectPasswordException.class)
    public void testEncryptionWrongPassword() throws Exception
    {
        Document doc = TestUtil.open(BASE_TEST_FILE, new LoadOptions("workshare"));
        saveOpen(doc, BASE_TEST_FILE, "password", "wrongpassword", SaveFormat.DOCX);
    }

    /// <summary>
    /// Encrypt with password and with the same password.
    /// </summary>
    @Test
    public void testEncryptionTheSamePassword() throws Exception
    {
        Document doc = TestUtil.open(BASE_TEST_FILE, new LoadOptions("workshare"));
        doc = saveOpen(doc, BASE_TEST_FILE, "amorozov", "amorozov", SaveFormat.DOCX);
        msAssert.areEqual("Hello World from an encrypted document!\f", doc.getText());
    }

    /// <summary>
    /// Encrypt with password and with the same password bigger file.
    /// </summary>
    @Test
    public void testEncryptionAgileToStandart() throws Exception
    {
        final String TESTFILE = "ImportDocx\\Encryption\\TestAgileEncryptionBigger.docx";

        Document doc = TestUtil.open(TESTFILE, new LoadOptions("password"));
        doc = saveOpen(doc, TESTFILE, "amorozov", "amorozov", SaveFormat.DOCX);
        msAssert.isTrue(doc.getNodeById("1.0.0").getText().startsWith("Штаты, небольшой городок"));
    }

    /// <summary>
    /// Encrypt with password and with the same password bigger file.
    /// </summary>
    @Test
    public void testEncryptionFlatOpc() throws Exception
    {
        final String TESTFILE = "ImportDocx\\Encryption\\TestFlatOpc.xml";

        Document doc = TestUtil.open(TESTFILE, new LoadOptions("test"));
        doc = saveOpen(doc, TESTFILE, "amorozov", "amorozov", SaveFormat.FLAT_OPC);
        msAssert.areEqual("Test Encrypted FlatOPC.\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-7901 FileFormatInfo.LoadFormat return Docx for password protected docm.
    /// andrnosk: The reason of the problem is recursion which we are using to read encrypted documents.
    /// On the first stage when an OOXML document is encrypted, it is not possible to ascertained whether it is an Excel,
    /// Word or PowerPoint document without decrypting it first so we just return DOCX.
    /// But on the next stage if LoadPassword is specified we can get the original format but in current code
    /// we just replace this real format with Docx.
    /// Fixed by removing the code which ovveride LoadFormat.
    /// </summary>
    @Test
    public void testJira7901() throws Exception
    {
        String fileName = TestUtil.buildTestFileName("ImportDocx\\Encryption\\TestJira7901.docm");

        // While an OOXML document is encrypted, it is not possible to ascertained whether it is
        // an Excel, Word Docx, Docm or PowerPoint document without decrypting it first so we just return DOCX.
        FileFormatInfo fileFormatInfo = FileFormatUtil.detectFileFormat(fileName);
        msAssert.isTrue(fileFormatInfo.isEncrypted());
        msAssert.areEqual(LoadFormat.DOCX, fileFormatInfo.getLoadFormat());

        LoadOptions loadOption = new LoadOptions();
        loadOption.setPassword("test");

        Document doc = TestUtil.open(fileName, loadOption);

        // Now the document is decrypted and we can get the correct original Load Format.
        msAssert.areEqual(LoadFormat.DOCM, doc.getOriginalLoadFormat());
    }

    /// <summary>
    /// WORDSNET-7964 Load Office 2013 encrypted document into Aspose.Words DOM
    /// Added support for SHA512 hash algorithm used in Office2013.
    ///
    /// On Java this requires Java Cryptography Extensions (JCE) in order to pass.
    /// You need to download it from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#jce_policy-1.5.0-oth-JPR
    /// At the moment we are running unit tests on Java 5 and therefore JCE should be for Java 5.
    ///
    /// You need then to copy the JAR files into C:\Program Files (x86)\Java\jdk1.5.0_22\jre\lib\security
    /// </summary>
    @Test
    public void testJira7964() throws Exception
    {
        LoadOptions lo = new LoadOptions("open");
        Document doc = TestUtil.open("ImportDocx\\Encryption\\TestJira7964.docx", lo);

        msAssert.areEqual("test\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-9654 Aspose.Words.FileCorruptedException is thrown while loading MS Word 2013 document
    /// PKE hash algorithm is different.
    /// </summary>
    @Test
    public void testJira9654() throws Exception
    {
        LoadOptions lo = new LoadOptions("pass");
        Document doc = TestUtil.open("ImportDocx\\Encryption\\TestJira9654.docx", lo);

        msAssert.areEqual(4, doc.getFirstSection().getBody().getParagraphs().getCount());
    }


    /// <summary>
    /// WORDSNET-17059 Aspose.Words.UnsupportedFileFormatException is thrown while loading DOCX
    /// Document is encrypted with ECMA376 encryption but DataSpaces stream is missing.
    /// </summary>
    @Test
    public void testJira17059() throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setPassword("123456");

        Document doc = TestUtil.open("ImportDocx\\Encryption\\TestJira17059.docx", lo);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// WORDSNET-19272 Aspose.Words throws an error upon loading Apple Pages Documents
    /// "Primary" stream instead of "\0x006Primary".
    /// </summary>
    @Test
    public void test19272() throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setPassword("test123");

        Document doc = TestUtil.open("ImportDocx\\Encryption\\Test19272.docx", lo);
        msAssert.areEqual("This is a test Word document from Pages app", doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// WORDSNET-22837 Loading encrypted word File Corrupted Exception
    /// Version stream is missing and Primary stream has invalid transform values.
    /// It seems that Word does not verify these fields at all.
    /// </summary>
    @Test
    public void test22837() throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setLoadFormat(LoadFormat.DOCX);
        lo.setPassword("112233");

        Document doc = TestUtil.open("ImportDocx\\Encryption\\Test22837.docx", lo);
        msAssert.areEqual("37040619890809102X开房记录\f", doc.getText());
    }

    /// <summary>
    /// Saves file encrypted with given password and opens it again using the same password.
    /// </summary>
    private static Document saveOpen(Document doc, String filename, String savePassword, String loadPassword, /*SaveFormat*/int sf) throws Exception
    {
        String outfile = TestUtil.buildOutFileName(TestUtil.buildTestFileName(filename), "", sf);

        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        saveOptions.setPassword(savePassword);
        TestUtil.save(doc, outfile, saveOptions, false);
        return TestUtil.open(outfile, new LoadOptions(loadPassword));
    }

    private static final String BASE_TEST_FILE = "ImportDocx\\Encryption\\TestEncrypted.docx";

}

