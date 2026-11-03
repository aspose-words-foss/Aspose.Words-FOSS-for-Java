// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2020 by Vyacheslav Durin
package com.aspose.ms.java.util;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.debug.JavaDebug;

import java.io.InputStream;

/**
 * WORDSJAVA-2219.
 * We should remember that our library can be re-packed in other projects or by users with tools like JarJar.
 * So we shouldn't use the methods Class.getResourceAsStream() and Class.getResource() directly.
 * <p>
 * This class provides methods to retrieve resources by their names and their caller classes.
 * For development we always use '/com/aspose/words/resources' so we always return this path.
 * <p>
 * For production we have a root package which is 'com.aspose.words' and a subpackage which is 'com.aspose.words.internal'.
 * <p>
 * Use SystemPal.fetchResource() methods with
 * com.aspose.words.internal.ArrayUtil.class
 * or com.aspose.words.Document.class
 * <p>
 * so in spite of re-packaging all the calls will be performed from these two packages.
 * <p>
 * NOTE: this class needs to be updated if the release jar structure changes.
 */
public final class ResourceManager
{
    private ResourceManager()
    {
        // blank
    }

    public static Stream fetchResourceStream(String fullResourceName, Class assembly)
    {
        /*JAVA-added try/catch to wrap a checked exception into unchecked one*/
        try
        {
            InputStream inputStream = getResourceAsStream(fullResourceName, assembly);

            //JAVA-added copy java InputStream to our Stream.
            MemoryStream stream = new MemoryStream();
            try
            {
                JavaOnlyStreamUtil.copyStream(inputStream, stream);
            }
            finally
            {
                inputStream.close();
            }

            stream.setPosition(0);
            return stream;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method tries to find the requested resource as follows:
     * 1. Convert the resource's name to Java.
     * <p>
     * 2. Try to load it from "/com/aspose/words/resources/"
     * In case of re-packaging e.g. ("/com/groupdocs/viewer/internal/c/a/w/resources") the decision is based on the Caller's class.
     * <p>
     * 3. If the resource is not found then try to step up and load it from the parents packages
     * e.g. the caller is in "/com/aspose/words/internal" or "/com/groupdocs/viewer/internal/c/a/w/internal".
     * <p>
     * 4. If there is still no luck then try the second name format.
     * <p>
     * 5. Repeat 2 and 3.
     * <p>
     * 6. Cache it to avoid strings' routine.
     *
     * 7. Throws if found nothing.
     */
    public static InputStream getResourceAsStream(String fullResourceName, Class assembly)
    {
        String resourceName = convertResourceNameToJava(fullResourceName);
        InputStream inputStream = getResourceByCallerAsStream(resourceName, assembly);
        if (inputStream == null)
        {
            resourceName = convertResourceNameSecondChance(resourceName);
            inputStream = getResourceByCallerAsStream(resourceName, ResourceManager.class);

            if (inputStream == null)
                throw new IllegalStateException(new String(new byte[]{82, 101, 115, 117, 114, 99, 101, 32, 110, 111, 116, 32, 102, 111, 117, 110, 100, 32}) + fullResourceName + "."); // Resource not found
        }

        return inputStream;
    }

    /**
     * WORDSJAVA-2219.
     * Since Aspose.Words for Java is used in other products we should remember that it can be re-packed by JarJar or similar tools.
     * We shouldn't count on the hardcoded resources directory path in this case.
     * <p>
     * Development mode:
     * Each module, Aspose.Foundation and Aspose.Words, has its own set of resources located at /com/aspose/words/resources/.
     * Resources can be requested by "com.aspose" from Foundation and Reporting and by "com.aspose.words" from Words.
     * <p>
     * Release mode:
     * For production there is a single set of resources located at /com/aspose/words/resources/.
     * We move all the classes into "com.aspose.words" and its sub-packages so we shouldn't worry about "/com/aspose/" package.
     * All public classes are moved into "com.aspose.words which become the root package and other modules are moved into "com.aspose.words.internal" package.
     * Hence, all calls will be performed from "com.aspose.words" and its subpackages.
     * <p>
     * So it is sufficient to check the resources with respect to the given Assembly first.
     * If we cannot find the requested file we fallback to the parent packages and look for resources there.
     * <p>
     * NOTE: in case of changing the release jar's structure this method will need to be improved.
     */
    private static InputStream getResourceByCallerAsStream(String resourceName, Class assembly)
    {
        // At the moment we have a just one resource folder so we cache just this single folder
        // Later when we have different modules we will have a cache like Map<Class,String>
        if (gCachedResourcesDir != null)
            return assembly.getResourceAsStream(gCachedResourcesDir + resourceName);

        // The following code will be performed just once.
        InputStream inputStream;
        ClassPackage classPackage = new ClassPackage(assembly.getPackage().getName());
        ClassPackageNode currentCallerPackage = classPackage.getPackage();

        // move up till we find the request resources.
        // try 'com.aspose.words.internal' then 'com.aspose.words'
        String path;
        while (currentCallerPackage != null)
        {
            path = currentCallerPackage.getPath() + new String(new byte[]{47, 114, 101, 115, 111, 117, 114, 99, 101, 115, 47}); // /resources/
            inputStream = assembly.getResourceAsStream(path + resourceName);
            if (inputStream != null)
            {
                // cache the folder.
                gCachedResourcesDir = path;
                return inputStream;
            }

            // Try next location 'com.aspose.words.internal' => 'com.aspose.words'
            currentCallerPackage = currentCallerPackage.getParent();
        }

        return null;
    }

    /**
     * Converts strings like this:
     * "Aspose.Resources.NoImage.png" -> "/com/aspose/words/resources/NoImage.png"
     * "Aspose.Words.Resources.Blank.doc" -> "/com/aspose/words/resources/Blank.doc"
     */
    private static String convertResourceNameToJava(String fullResourceName)
    {
        final String Resources = new String(new byte[]{46, 82, 101, 115, 111, 117, 114, 99, 101, 115, 46}); //".Resources.";
        int pos = fullResourceName.indexOf(Resources);
        if (pos > 0)
            fullResourceName = fullResourceName.substring(pos + Resources.length());

        return fullResourceName;
    }

    /**
     * "Aspose.Words.Resources.PageBorderArt.Definitions.xml" resource name can be converted
     * from .Net using two steps:
     * 1. "PageBorderArt.Definitions.xml" - file with dots in name - method above does this.
     * 2. "PageBorderArt/Definitions.xml" - file inside a folder - this method does this i.e.
     * "/resources/PageBorderArt.Definitions.xml" (produced by the first method) will
     * be converted to "/resources/PageBorderArt/Definitions.xml".
     */
    private static String convertResourceNameSecondChance(String firstName)
    {
        int ext = firstName.lastIndexOf('.');// leave last dot for extension.
        int dotInName = firstName.lastIndexOf('.', ext - 1);
        if (ext < 0 || dotInName < 0)
            return null;

        String name = firstName.substring(0, ext);
        name = msString.replace(name, '.', '/');
        return name + firstName.substring(ext);
    }

    private static final char gDirSeparator = '/';
    // Atm it is ok to cache a single folder because all resources are located there
    // But if we had different sets of resources for different modules
    // we would cache it based on a caller's class like Map<Class,String>
    private volatile static String gCachedResourcesDir;

    private static class ClassPackage
    {
        public ClassPackage(String classPackage)
        {
            // Development mode only. It will be removed on production
            if (JavaDebug.ON && "com.aspose".equals(classPackage)) classPackage += ".words";

            if (msString.isNullOrEmpty(classPackage))
                mHead = new ClassPackageNode("");
            else
            {
                String[] packages = classPackage.split("\\.");
                mHead = new ClassPackageNode(packages[packages.length - 1]);
                ClassPackageNode current = mHead;
                // Create a list like this
                // 'com.aspose.words.internal' => ['internal', 'words', 'aspose', 'com']
                for (int i = packages.length - 2; i >= 0; i--)
                {
                    current.mNext = new ClassPackageNode(packages[i]);
                    current = current.mNext;
                }
            }
        }

        /**
         * @return the current package. e.g. it will return 'com.aspose.words.internal' for some com.aspose.words.internal.Zzz class
         */
        public ClassPackageNode getPackage()
        {
            return mHead;
        }

        private ClassPackageNode mHead;
    }

    private static class ClassPackageNode
    {
        /**
         * @return a parent package,
         * e.g. in case of 'com.aspose.words.internal' it will return 'com.aspose.words'
         */
        public ClassPackageNode getParent()
        {
            return mNext;
        }

        public ClassPackageNode(String packageName)
        {
            this.mName = packageName;
        }

        private ClassPackageNode mNext;
        private String mName;

        /**
         * Recursively builds a path from the parts of the package.
         * Nodes ['words','aspose','com'] will become a path '/com/aspose/words'
         */
        private String getPath()
        {
            // ['words' -> 'aspose' -> 'com'] => "/com/aspose/words
            return mNext == null
                    ? gDirSeparator + mName
                    : mNext.getPath() + gDirSeparator + mName;
        }
    }
}
