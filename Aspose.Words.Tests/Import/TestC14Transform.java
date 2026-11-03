// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/10/2017 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.File;


/// <summary>
/// C14Transform tests.
/// </summary>
/// <remarks>
/// https://www.w3.org/TR/2001/REC-xml-c14n-20010315
/// </remarks>
@Test
public class TestC14Transform
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests specification XML example - 3.1 PIs, Comments, and Outside of Document Element.
    /// </summary>
    @Test
    public void testSpecExample1() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example1.xml");
    }

    /// <summary>
    /// Relates to TestTransformExample1().
    /// The input XML is slightly changed to check line breaks when element node is at the very start and
    /// also when there are two consequent PIs (processing instructions) in XML.
    /// </summary>
    @Test
    public void testSpecExample1A() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example1A.xml");
    }

    /// <summary>
    /// Relates to TestTransformExample1() 
    /// The difference is prefixes added to XML element nodes and also made one nested XML element with name prefix.
    /// </summary>
    @Test
    public void testSpecExample1B() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example1B.xml");
    }

    /// <summary>
    /// Tests specification XML example - 3.2 Whitespace in Document Content.
    /// </summary>
    @Test
    public void testSpecExample2() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example2.xml");
    }

    /// <summary>
    /// Tests specification XML example - 3.3 Start and End Tags.
    /// </summary>
    @Test (enabled = false, description = "Specification example file contains [<!ATTLIST e9 attr CDATA 'default'>] element that cannot be resolved correctly with AnyXmlReader, so skip it for a while")
    public void testSpecExample3() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example3.xml");
    }

    /// <summary>
    /// Relates to TestSpecExample3(), but it is without problematic !ATTLIST element. 
    /// </summary>
    @Test
    public void testSpecExample3A() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example3A.xml");
    }

    /// <summary>
    /// Tests specification XML example - 3.4 Character Modifications and Character References.
    /// </summary>
    @Test (enabled = false, description = "Character references are not canonized good now, so skip it for a while as it is not a frequent case.")
    public void testSpecExample4() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example4.xml");
    }

    /// <summary>
    /// Tests specification XML example - 3.5 Entity References.
    /// </summary>
    @Test
    public void testSpecExample5() throws Exception
    {
        checkTransform("OfficeCrypto\\c14Example5.xml");
    }

    /// <summary>
    /// Tests propagating namespace.
    /// </summary>
    @Test
    public void testPropagatingNamespace() throws Exception
    {
        checkTransform("OfficeCrypto\\c14PropagateNs.xml", "propagated namespace");
    }

    /// <summary>
    /// Checks that AW c14 canonical transformation equals to transformation produced with DotNet library.
    /// </summary>
    private static void checkTransform(String inputXml) throws Exception
    {
        checkTransform(inputXml, null);
    }

    /// <summary>
    /// Checks that AW c14 canonical transformation equals to transformation produced with DotNet library.
    /// Propagates specified namespace.
    /// </summary>
    private static void checkTransform(String inputXml, String propagatingNamespace) throws Exception
    {
        String testFileName = TestUtil.buildTestFileName(inputXml);
        Stream xml = new FileStream(testFileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            String outFileName = TestUtil.buildOutFileName(testFileName, "", SaveFormat.UNKNOWN);

            C14Transform c14Transform = new C14Transform();
            c14Transform.propagateNamespace(propagatingNamespace);

            TestUtil.ensureDirectoryForFileExists(outFileName);
            File.writeAllBytes(outFileName, c14Transform.apply(xml).toArray());

            String goldFileName = TestUtil.buildGoldFileName(testFileName, "", SaveFormat.UNKNOWN);
            TestUtil.verifyGoldText(testFileName, outFileName, goldFileName, null, GoldLevel.EXPORT_ONLY, null);
        }
        finally { if (xml != null) xml.close(); }
    }
}

