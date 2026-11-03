// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests the <see cref="DocumentBase.People"/> collection and the <see cref="PersonInternal"/> type which objects
/// it stores.
/// </summary>
@Test
public class TestPeople
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests cloning of <see cref="PersonCollectionInternal"/>.
    /// </summary>
    @Test
    public void testCloning() throws Exception
    {
        Document source = new Document();
        PersonInternal person = source.getPeople().add("John", ContactIdentityProvider.ACTIVE_DIRECTORY, "1");

        Document cloned = source.deepClone();
        msAssert.areEqual(1, cloned.getPeople().getCount());

        // Change object of the source document and check that new values are not applied to
        // the object of the cloned document.
        person.setAuthor("Jack");
        person.setIdentityProvider(ContactIdentityProvider.WINDOWS_LIVE_ID);
        person.setUserId("000");

        PersonInternal clonedPerson = cloned.getPeople().get(0);
        msAssert.areEqual("John", clonedPerson.getAuthor());
        msAssert.areEqual(ContactIdentityProvider.ACTIVE_DIRECTORY, clonedPerson.getIdentityProvider());
        msAssert.areEqual("1", clonedPerson.getUserId());
    }


    /// <summary>
    /// Checks properties of the <see cref="PersonInternal"/> object.
    /// </summary>
    private static void checkPerson(PersonInternal person, String expectedAuthor, /*ContactIdentityProvider*/int expectedProvider,
        String expectedId)
    {
        msAssert.areEqual(expectedAuthor, person.getAuthor());
        msAssert.areEqual(expectedProvider, person.getIdentityProvider());
        msAssert.areEqual(expectedId, person.getUserId());
    }

    // FOSS: TestWarningOnSavingToOldFormat removed. It asserted the "people part not supported" data-loss
    // warning produced when saving to ECMA-375/DOC/RTF/WML - all removed formats. Markdown/Text don't
    // raise that particular warning, so the scenario can't be reproduced in FOSS.

}

