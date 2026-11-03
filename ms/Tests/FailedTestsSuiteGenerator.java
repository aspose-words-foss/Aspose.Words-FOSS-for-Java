package com.aspose.ms;

import org.testng.*;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * This listener creates xml files which contain failed tests,
 * Ant call target <code>runAllFailed</code> which uses those files.
 * The main difference is that these newly created failed.xml files will always be launched in a single thread.
 * Also the final emailable report will contain tests from those files only.
 */
public class FailedTestsSuiteGenerator implements ITestListener {

    public static final String OUTPUT_SUITE_NAME = "-aspose-testng-failed.xml";
    public static final String SUITE_CHARSET = "UTF8";
    // suite template
    private static MessageFormat gSuiteFormatter = new MessageFormat
            (
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n" +
                            "<suite configfailurepolicy=\"continue\" verbose=\"2\" name=\"Failed suite [{0}]\" parallel=\"false\" allow-return-values=\"true\">\n" +
                            "  <test name=\"{0}\" parallel=\"false\">\n" +
                            "    <classes>\n" +
                            "{1}" +
                            "    </classes>\n" +
                            "  </test>\n" +
                            "</suite>"
            );

    private static MessageFormat gClassItemFormat = new MessageFormat
            (
                    "       <class name=\"{0}\">\n" +
                            "           <methods>\n" +
                            "{1}" +
                            "           </methods>\n" +
                            "       </class>\n"
            );

    private static MessageFormat gIncludeItemFormat = new MessageFormat("               <include name=\"{0}\"/>\n");

    /**
     * This method wil lbe launched when all tests are finished.
     * */
    @Override
    public void onFinish(ITestContext context)
    {
        String suiteName = context.getName();
        String outputDirectory = context.getOutputDirectory();
        removeExistSuiteFile(suiteName + OUTPUT_SUITE_NAME, outputDirectory);

        Map<String, Set<String>> classBeforeMethodsMap = new HashMap<String, Set<String>>();
        Map<String, Set<String>> classAfterMethodsMap = new HashMap<String, Set<String>>();
        Map<String, Set<String>> classFailedMethodMap = new HashMap<String, Set<String>>();

        // group all results
        for(ITestNGMethod method : context.getFailedTests().getAllMethods())
            groupResults(method, classFailedMethodMap, classBeforeMethodsMap, classAfterMethodsMap);

        // create a string suite from the results and write it into a file
        String suiteText = createSuiteText(suiteName, classFailedMethodMap, classBeforeMethodsMap, classAfterMethodsMap);
        if (suiteText != null)
            writeXml(suiteName, outputDirectory, suiteText);
    }

    @Override
    public void onTestStart(ITestResult result) { /* empty */ }

    @Override
    public void onTestSuccess(ITestResult result) { /* empty */ }

    @Override
    public void onTestFailure(ITestResult result) { /* empty */ }

    @Override
    public void onTestSkipped(ITestResult result) { /* empty */ }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { /* empty */ }

    @Override
    public void onStart(ITestContext context) { /* empty */ }

    /**
     * Creates suite's text.
     * */
    private String createSuiteText(String suiteName, Map<String, Set<String>> failedMethods, Map<String, Set<String>> beforeMethods, Map<String, Set<String>> afterMethods)
    {
        StringBuilder classesItems = new StringBuilder();
        // We always create failed suite xml. Even if it is empty.
        // We do that because words-java.test.all.failed.xml has references to Parallel-aspose-testng-failed and Single Threaded-aspose-testng-failed xml files.
        //if (failedMethods.isEmpty())
         //   return null;

        for (Map.Entry<String, Set<String>> failedClass : failedMethods.entrySet())
        {
            StringBuilder methodItems = new StringBuilder();
            Set<String> before = beforeMethods.get(failedClass.getKey());
            if (before != null)
            {
                for (String method : before)
                    methodItems.append(gIncludeItemFormat.format(new Object[]{ method }));
            }
            Set<String> after = afterMethods.get(failedClass.getKey());
            if (after != null)
            {
                for (String method : after)
                    methodItems.append(gIncludeItemFormat.format(new Object[] { method }));
            }
            for (String failed : failedClass.getValue())
                methodItems.append(gIncludeItemFormat.format(new Object[]{ failed }));

            classesItems.append(gClassItemFormat.format(new Object[] {failedClass.getKey(), methodItems.toString()}));
        }

        return gSuiteFormatter.format(new Object[]{suiteName + "[Failed]", classesItems.toString()});
    }

    /**
     * Writes suite's text into <code>outputDir</code> directory with name <code>OUTPUT_SUITE_NAME</code>.
     * @param suiteName is not used at the moment
     * */
    private void writeXml(String suiteName, String outputDir, String suite) {

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        Writer writer = null;
        try
        {
            File outputDirectory = new File(outputDir);
            if (!outputDirectory.exists())
                outputDirectory.mkdirs();

            File out = new File(outputDirectory, suiteName + OUTPUT_SUITE_NAME);
            if (out.exists())
                out.delete();

            fileOutputStream = new FileOutputStream(out);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, SUITE_CHARSET);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write(suite);
            writer.flush();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null)
            {
                try
                {
                    outputStreamWriter.close();

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeExistSuiteFile(String suiteFileName, String outputDir)
    {
        File out = new File(outputDir, suiteFileName);
        if (out.exists())
            out.delete();
    }

    /**
     * Handy method to group methods by their classes.
     * */
    private void groupResults(ITestNGMethod method, Map<String, Set<String>> failed, Map<String, Set<String>> before, Map<String, Set<String>> after)
    {
        String failedMethod = method.getMethodName();

        ITestClass testClass = method.getTestClass();
        String instanceName = testClass.getName();

        if (!before.containsKey(instanceName))
            before.put(instanceName, new HashSet<String>());

        if (!after.containsKey(instanceName))
            after.put(instanceName, new HashSet<String>());

        if (!failed.containsKey(instanceName))
            failed.put(instanceName, new HashSet<String>());

        // add failed method
        failed.get(instanceName).add(failedMethod);

        // add before methods
        Set<String> beforeMethodsSet = before.get(instanceName);
        for (ITestNGMethod b : testClass.getBeforeTestMethods())
            beforeMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getBeforeClassMethods())
            beforeMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getBeforeGroupsMethods())
            beforeMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getBeforeSuiteMethods())
            beforeMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getBeforeTestConfigurationMethods())
            beforeMethodsSet.add(b.getMethodName());

        // add after methods
        Set<String> afterMethodsSet = after.get(instanceName);
        for (ITestNGMethod b : testClass.getAfterClassMethods())
            afterMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getAfterGroupsMethods())
            afterMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getAfterSuiteMethods())
            afterMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getAfterTestConfigurationMethods())
            afterMethodsSet.add(b.getMethodName());
        for (ITestNGMethod b : testClass.getAfterTestMethods())
            afterMethodsSet.add(b.getMethodName());
    }
}
