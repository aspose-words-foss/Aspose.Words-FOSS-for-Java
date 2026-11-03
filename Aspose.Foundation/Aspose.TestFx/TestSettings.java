// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2013 by Michael Morozoff

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.StreamReader;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Comparator;
import com.aspose.ms.System.msInt;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Provides test run wide settings used by the verification UI.
/// </summary>
public /*static*/ class TestSettings
{
	/* Simulation of static class by using private constructor */
	private TestSettings()
	{}

    /// <summary>
    /// RK We need this overload because CPPPORTER does not yet support default parameter values.
    /// </summary>
    public static void set(String id, Object value) throws Exception
    {
        set(-1, id, value, null);
    }

    /// <summary>
    /// Sets value of the setting.
    /// This is used to set default values, or change value at runtime.
    /// </summary>
    /// <param name="index">This is used when value is created for the first time.
    /// The index affects placement of value in the test settings UI.
    /// It is not used when key was already used to create a setting</param>
    /// <param name="key">A key of the setting.</param>
    /// <param name="value">A value. This must be of actual type: bool, float, string or string[].
    /// When setting is created this type determines what <see cref="TestSetting.Kind"/> would be.</param>
    /// <param name="attached">All the text associated with the setting. In the configuraton file
    /// this are all comment lines before the key=value.</param>
    private static void set(int index, String key, Object value, /*params*/ String... attached) throws Exception
    {
        synchronized (gValuesLock)
        {
            ArgumentUtil.checkHasChars(key, "key");
            init();

            TestSetting v = DictionaryExtension.getValueOrNull(gValues, key.toLowerCase());
            if (v == null)
            {
                // attached values as stored as-is in file thus we want to add ";" at start
                for (int i = 0; i < attached.length; i++)
                {
                    // null and empty values are stored as empty strings
                    if (attached[i] == null)
                        attached[i] = "";
                    if (attached[i].length() > 0)
                        attached[i] = ";" + attached[i];
                }

                v = TestSetting.create(index, key, value, attached);
            }
            else
            {
                v.updateValue(value);
            }

            gValues.put(key.toLowerCase(), v);
        }
    }

    /// <summary>
    /// Gets a test setting.
    /// If at the time this method is called the Alt+Shift combination is pressed,
    /// this method displays the dialog box where the user can see and set the test settings.
    /// </summary>
    private static TestSetting get(String id) throws Exception
    {
        synchronized (gValuesLock)
        {
            ArgumentUtil.checkHasChars(id, "id");
            init();
            TestSetting v = DictionaryExtension.getValueOrNull(gValues, id);
            return v;
        }
    }

    /// <summary>
    /// Returns True if specified settings is set to True.
    /// </summary>
    public static boolean is(String id) throws Exception
    {
        return get(id).getAsBool();
    }

    /// <summary>
    /// Provides access to all of the test settings.
    /// The key is the setting name, the value is the <see cref="TestSetting"/> object.
    /// </summary>
    public static Map<String, TestSetting> getAllSettings() throws Exception
    {
        synchronized (gValuesLock)
        {
            init();
            return new HashMap<String, TestSetting>(gValues);
        }
    }

    /// <summary>
    /// Returns True if comparing xps documents shall use rounding for points.
    /// This is useful for layout tests to ignore minor differencies in layout.
    /// Its much like ignoring minor pixel differencies.
    ///
    /// You should only use this when many tests are failing and you want to check if its's because of rounding issues.
    /// If using this option test passes you a likely safe to accept new gold.
    /// </summary>
    public static boolean getRoundXpsPoints() throws Exception { return is(B_ROUNDXPS); }

    /// <summary>
    /// Whether test passes or fails out must be accepted as new gold.
    /// This is used when many golds fail because of the Windows update or other
    /// external factor wher AW code has not changed since all tests passed before.
    /// </summary>
    public static boolean getAcceptGoldAlways() throws Exception { return I_AUTOACCEPT_ALWAYS.equals(get(A_AUTOACCEPT).getAsArray()[0]); }

    /// <summary>
    /// Accept out as new gold when test passed and there was a difference.
    /// This happens when out does not match gold but test passed due to comparer result or other decision.
    /// </summary>
    public static boolean getAcceptGoldWhenPass() throws Exception { return I_AUTOACCEPT_PASS.equals(get(A_AUTOACCEPT).getAsArray()[0]); }

    /// <summary>
    /// Returns image difference labels which shall pass the test
    /// </summary>
    public static String[] getLabels() throws Exception { return msString.split(get(S_LABELS).getAsString(), new char[] { ',' }, StringSplitOptions.REMOVE_EMPTY_ENTRIES); }
    public static void setLabels(String[] value) throws Exception { set(S_LABELS, value); }

    public static boolean getCompareLogical() throws Exception { return is(B_COMPARELOGICAL); }

    public static boolean getCompareImages() throws Exception { return !I_COMPAREIMAGES_DONT.equals(get(A_COMPAREIMAGES).getAsArray()[0]); }

    /// <summary>
    /// True if image difference label learning more is activated.
    /// In this mode when compare fails and difference label does not match
    /// the form will have buttons to manually select difference label.
    /// </summary>
    public static boolean getLearning() throws Exception { return is(B_LEARN) && getPassWhenImagesDifferenceLabelsMatch(); }


    /// <summary>
    /// True if test shall pass when out and gold compared as images are binary equal.
    /// </summary>
    public static boolean getPassWhenImagesBinaryEqual() throws Exception { return !I_COMPAREIMAGES_DONT.equals(get(A_COMPAREIMAGES).getAsArray()[0]); }

    /// <summary>
    /// True if test shall pass when out and gold compared as images have the same pixels.
    /// </summary>
    public static boolean getPassWhenImagesPixelsMatch() throws Exception
    {
        switch (gStringSwitchMap.of(get(A_COMPAREIMAGES).getAsArray()[0]))
        {
            case /*I_COMPAREIMAGES_DONT*/0:
            case /*I_COMPAREIMAGES_PASS_BINARY*/1:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// True if test shall pass when out and gold compared as images have the same pixels.
    /// </summary>
    public static boolean getPassWhenImagesDifferenceLabelsMatch() throws Exception
    {
        switch (gStringSwitchMap.of(get(A_COMPAREIMAGES).getAsArray()[0]))
        {
            case /*I_COMPAREIMAGES_DONT*/0:
            case /*I_COMPAREIMAGES_PASS_BINARY*/1:
            case /*I_COMPAREIMAGES_PASS_PIXEL*/2:
                return false;
            case /*I_COMPAREIMAGES_PASS_LABEL*/3:
            case /*I_COMPAREIMAGES_PASS_EXPLICIT*/4:
                // we cannot pass by matching to undefined labels
                return getLabels().length > 0;
            default:
                throw new Exception("PassWhenImagesDifferenceLabelsMatch");
        }
    }

    public static boolean getPassExplicitLabel() throws Exception
    {
        return I_COMPAREIMAGES_PASS_EXPLICIT.equals(get(A_COMPAREIMAGES).getAsArray()[0]) && getLabels().length > 0;
    }


    public static boolean getCloseWordDocument() throws Exception { return is(B_CLOSEDOC); }
    public static void setCloseWordDocument(boolean value) throws Exception { set(B_CLOSEDOC, value); }

    /// <summary>
    /// Returns True if failed tests shall throw and do not display the compare form.
    /// ScrollLock on overrides behavior and means display the compare form.
    /// </summary>
    public static boolean getDontShowCompareForm() throws Exception
    {
        if (TestUtilPal.isScrollLockOn())
            return false;

        return !is(B_SHOWCOMPARE);
    }
    public static void setDontShowCompareForm(boolean value) throws Exception { set(B_SHOWCOMPARE, value); }

    /// <summary>
    /// Returns True if compare form shall be displayed regardless of whether out and gold are different or not.
    /// </summary>
    public static boolean getShowCompareAlways() throws Exception { return is(B_SHOWCOMPARE_ALWAYS); }
    public static void setShowCompareAlways(boolean value) throws Exception { set(B_SHOWCOMPARE_ALWAYS, value); }

    /// <summary>
    /// Returns True if local golds shall be used instead of versioned golds.
    /// </summary>
    public static boolean getUseLocalGolds() throws Exception { return is(B_USELOCALGOLDS); }

    /// <summary>
    /// Returns True if proxy is used to access resources on the web and cached values are returned.
    /// </summary>
    public static boolean getProxyWebTests() throws Exception { return is(B_PROXYWEB); }

    public static boolean getIgnoreWebTests() throws Exception { return is(B_NOWEB); }

    /// <summary>
    /// WORDSNET-21914 Enable PDF validation on CI server.
    /// Indicates whether the config is loaded from %USERPROFILE%\Aspose folder (global for all test assemblies)
    /// or from current test assembly output folder (unique for any test assembly).
    /// </summary>
    public static boolean isGlobalConfigFile() { return mIsGlobalConfigFile; }; public static void isGlobalConfigFile(boolean value) { mIsGlobalConfigFile = value; };

    private static boolean mIsGlobalConfigFile;


    /// <summary>
    /// Loads the configuration settings from the file but only if they have not been loaded yet.
    /// </summary>
    private static void init() throws Exception
    {
        if (gValues == null)
            loadConfig();
    }

    /// <summary>
    /// Resets and loads all the configuration settings.
    /// </summary>
    private static void loadConfig() throws Exception
    {
        loadDefaults();

        String configFileName = getConfigFileName();
        if (!File.exists(configFileName))
        {
            configFileName = getGlobalConfigFileName();
            if (!File.exists(configFileName))
                return;
            isGlobalConfigFile(true);
        }

        // I wanted simplest possible format of the file and to not use any 3rd party libraries
        // Simplest format is key=value pair, similar to ini files but even simpler
        // However, I need a few extras:
        // - store description for the value, so that it can be used in UI to describe what this option is
        // - file must support comments, so that it is easy to edit by hand, but these comments are not needed in UI,
        //
        // Originally format was like this:
        // ; at start of line designates comment line
        // key=value is the value stored
        // ; right above the key=value is description used in UI, any further is just comment
        // all values are bools, but can be parsed from y/yes/true/1/n/no/false/0
        // in the UI all the values were sorted by key
        //
        // Now the new version has this on top of the above:
        // - values are not sorted in the UI, they appear in the file order,
        // - when file is read/written the layout is preserved, values are updates in their respective lines,
        // - blank lines designate a divider in the UI, so that controls can be grouped
        // - values are read as strings and not parsed, instead methods AsBool, AsString, AsList, etc
        // - where same key appears multiple times in the file it is considered as array of values
        // - where value is array the UI will default to first value found in file, and values are not sorted
        // - where in UI list value changes, it will swap lines in file with the new value selected

        // Read config file if present. It may replace defaults.
        StreamReader sr = File.openText(configFileName);
        try /*JAVA: was using*/
        {
            String line = "";
            int index = 0;
            ArrayList<String> attached = new ArrayList<String>();
            while (true)
            {
                line = sr.readLine();
                if (line == null)
                    break;

                line = msString.trim(line);

                int p = line.indexOf('=');

                // lines not in key=value format, or where key is empty are ignored
                if (p <= 0)
                {
                    msArrayList.add(attached, line);
                    continue;
                }

                String key = line.substring((0), (0) + (p));
                String val = msString.trim(line.substring(p + 1));

                try
                {
                    TestSetting v = TestSetting.load(index++, key, val, msArrayList.toArray(attached, new String[0]));

                    // "Autoaccept always" shall not be read from config, use default value instead
                    if (!A_AUTOACCEPT.equals(v.getKey()) || !I_AUTOACCEPT_ALWAYS.equals(v.getAsArray()[0]))
                        gValues.put(key, v);       // Value of the last matching key wins
                }
                catch(Exception e)
                {
                    // Discard invalid value in file, use default instead
                }
                finally
                {
                    attached.clear();
                }
            }
        }
        finally { if (sr != null) sr.close(); }
    }

    private static class TestSettingIndexComparer implements Comparator<Map.Entry<String, TestSetting>>
    {
        public int compare(Map.Entry<String, TestSetting> x, Map.Entry<String, TestSetting> y)
        {
            return msInt.compareTo(x.getValue().getIndex(), y.getValue().getIndex());
        }
    }

    private static void loadDefaults() throws Exception
    {
        gValues = new HashMap<String, TestSetting>();

        int index = 0;
        set(index++, B_NOPDFVALIDATION, true, "Do not validate PDF output");
        set(index++, B_NOPDF, false, "Do not verify PDF output");
        set(index++, B_NOGDI, false, "Do not verify GDI output");
        set(index++, B_NOXPS, false, "Do not verify XPS output");
        set(index++, B_NOXAML, false, "Do not verify XAML output");
        set(index++, B_NOSVG, false, "Do not verify SVG output");
        set(index++, B_NOHTML, false, "Do not verify FixedPage HTML output");
        set(index++, B_NOPS, false, "Do not verify PostScript output");
        set(index++, B_NOEPS, false, "Do not verify Encapsulated PostScript output");
        set(index++, B_NOEMF, false, "Do not verify EMF output");

        set(index++, B_SHOWCOMPARE,
        false,
        "", "Show compare dialog when test fails");

        set(index++, B_SHOWCOMPARE_ALWAYS, false, "Show compare dialog always, even if test passes");

        // TODO We should normally have list here: ignore test, use proxy, neither

        set(index++, B_NOWEB, false, "", "Ignore tests which access internet");
        set(index++, B_PROXYWEB, false, "Use proxy to get web resources");
        set(index++, S_PROXYWEBSERVER, "X:/awnet/Aspose.Foundation/Tools/DirectoryBrowser/bin/Release/net8.0/DirectoryBrowser.exe", "A full path to the local image proxy server");
        set(index++, S_PROXYWEBURI, "http://aspose-web-resources:5555/", "A full url to the local image proxy server");

        set(index++, B_USELOCALGOLDS, false, "", "Use local set of gold files like in Java builds");
        set(index++, B_USESHAREDOUT, false, "Use shared folder to keep out files");
        set(index++, B_CLOSEDOC, false, "Close test document in Word before running test (under debugger only)");
        set(index++, B_ROUNDXPS, false, "Round xps points");
        set(index++, B_ENABLEPROGRESSESTIMATION, false, "Record layout progress in CSV files");

        set(index++, B_COMPARELOGICAL, false, "", "Compare logical difference");
        set(index++, A_COMPAREIMAGES, new String[]
            { I_COMPAREIMAGES_DONT, I_COMPAREIMAGES_PASS_BINARY, I_COMPAREIMAGES_PASS_PIXEL, I_COMPAREIMAGES_PASS_LABEL, I_COMPAREIMAGES_PASS_EXPLICIT },
            "Compare image difference");

        set(index++, B_LEARN, false, "Enable manual image difference labels tagging");
        set(index++, S_LABELS, "", "Compare image difference label with the following labels");
        set(index++, A_AUTOACCEPT, new String[]
            { I_AUTOACCEPT_DONT,I_AUTOACCEPT_PASS, I_AUTOACCEPT_ALWAYS },
            null, "Accept out as new gold when different");
    }

    private static final String A_AUTOACCEPT = "autoaccept";
    private static final String I_AUTOACCEPT_DONT = "Do not accept";
    private static final String I_AUTOACCEPT_PASS = "Accept when test passed with a difference";
    private static final String I_AUTOACCEPT_ALWAYS = "Accept always, even when test failed";

    private static final String B_USELOCALGOLDS = "uselocalgolds";
    private static final String B_CLOSEDOC = "closedoc";

    private static final String B_ENABLEPROGRESSESTIMATION = "enableprogressestimation";

    private static final String B_ROUNDXPS = "roundxps";
    private static final String B_PROXYWEB = "proxyweb";
    private static final String S_PROXYWEBSERVER = "proxyweb-server";
    private static final String S_PROXYWEBURI = "proxyweb-uri";
    private static final String B_NOWEB = "noweb";
    private static final String B_NOEMF = "noemf";
    private static final String B_NOEPS = "noeps";
    private static final String B_NOPS = "nops";
    private static final String B_NOHTML = "nohtml";
    private static final String B_NOSVG = "nosvg";
    private static final String B_NOXAML = "noxaml";
    private static final String B_NOPDFVALIDATION = "nopdfvalidation";
    private static final String B_NOPDF = "nopdf";
    private static final String B_NOXPS = "noxps";
    private static final String B_NOGDI = "nogdi";
    private static final String B_USESHAREDOUT = "usesharedout";
    private static final String LABEL_ENABLED = "label-enabled";
    private static final String LABEL_PASS_MINOR = "label-pass-minor";
    private static final String LABEL_PASS_NONE = "label-pass-none";
    private static final String LABEL_PASS_NOISE = "label-pass-noise";
    private static final String LABEL_PASS_CLIP = "label-pass-clip";

    private static final String B_COMPARELOGICAL = "comparelogical";

    private static final String A_COMPAREIMAGES = "compareimages";
    private static final String I_COMPAREIMAGES_DONT = "Do not compare";
    private static final String I_COMPAREIMAGES_PASS_BINARY = "Compare and pass if images are binary equal";
    private static final String I_COMPAREIMAGES_PASS_PIXEL = "Compare and pass if images have matching pixels";
    private static final String I_COMPAREIMAGES_PASS_LABEL = "Compare and pass if images difference label matches";
    private static final String I_COMPAREIMAGES_PASS_EXPLICIT = "Compare and pass applying first label to the difference";

    private static final String B_LEARN = "learn";
    private static final String S_LABELS = "labels";

    private static final String B_SHOWCOMPARE = "showbinary";
    private static final String B_SHOWCOMPARE_ALWAYS = "showalways";

    private static String getConfigFileName() throws Exception
    {
        return TestFxUtil.correctPath(Path.combine(TestUtilPal.getExecutingAssemblyPath(), TEST_CONFIG_FILE_NAME));
    }

    /// <summary>
    /// Gets global config file name stored in %USERPROFILE% folder.
    /// Currently helps to adjust specific test setting for CI (Jenkins) builds.
    /// </summary>
    private static String getGlobalConfigFileName() throws Exception
    {
        String userProfileDir = getUserProfileDir();
        userProfileDir = Path.combine(userProfileDir, "Aspose");
        return TestFxUtil.correctPath(Path.combine(userProfileDir, TEST_CONFIG_FILE_NAME));
    }

    /// <summary>
    /// C++ implementation of Environment.GetFolderPath throws NotImplementedException for Environment.SpecialFolder.UserProfile.
    /// </summary>
    private static String getUserProfileDir()
    {
        // Use Java system property to get the home directory
        return System.getProperty("user.home");
    }

    private static HashMap<String, TestSetting> gValues;
    private static Object gValuesLock = new Object();

    private static final String TEST_CONFIG_FILE_NAME = "TestSettings.config";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Do not compare",
		"Compare and pass if images are binary equal",
		"Compare and pass if images have matching pixels",
		"Compare and pass if images difference label matches",
		"Compare and pass applying first label to the difference"
	);

}

