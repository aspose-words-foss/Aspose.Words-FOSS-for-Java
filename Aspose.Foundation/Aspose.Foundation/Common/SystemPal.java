// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 24/06/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msUri;
import com.aspose.ms.java.util.Base64;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.util.ResourceManager;
import com.aspose.ms.java.windows.WindowsRegistry;
import com.aspose.words.net.System.Globalization.CultureNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;


/// <summary>
/// This class is to be ported manually to Java.
/// </summary>
public /*static*/ class SystemPal
{
    /* Simulation of static class by using private constructor */
    private SystemPal()
    {
    }

    /// <summary>
    /// <para>Sets Test flag. This flag is used to make behavior of some methods deterministic for test purposes.
    /// Call this method before running any tests which can be connected to methods of this class.</para>
    /// <para>For now, it's used by <see cref="SetCulture(string)"/> only
    /// to ensure msCultureInfo ignores user overrides in Windows Control Panel.</para>
    /// </summary>
    public static void setTestMode(boolean testMode)
    {
        gTestMode = testMode;
    }

    /// <summary>
    /// Returns a resource's Java's input stream in the specified assembly or throws if the resource cannot be found.
    /// </summary>
    /// <param name="fullResourceName">Full resource name.</param>
    /// <param name="assembly">A Type (class) thet belongs to Assembly where the resource is located.</param>
    public static InputStream getResourceAsStream(String fullResourceName, Class assembly)
    {
        return ResourceManager.getResourceAsStream(fullResourceName, assembly);
    }

    /// <summary>
    /// Returns a resource stream in the specified assembly or throws if the resource cannot be found.
    /// </summary>
    /// <param name="fullResourceName">Full resource name.</param>
    /// <param name="assembly">A Type (class) thet belongs to Assembly where the resource is located.</param>
    public static Stream fetchResourceStream(String fullResourceName, Class assembly)
    {
        return ResourceManager.fetchResourceStream(fullResourceName, assembly);
    }

    /// <summary>
    /// Returns a resource stream in Aspose.Foundation.dll assembly or throws if the resource cannot be found.
    /// Note: after merging the Aspose.Foundation.dll assembly becomes Aspose.Words.dll.
    /// </summary>
    /// <param name="fullResourceName">Full resource name.</param>
    static Stream fetchResourceStream(String fullResourceName)
    {
        return fetchResourceStream(fullResourceName, ArrayUtil.class);
    }

    /// <summary>
    /// Opens a stream from a URI or from a path. If it is a URI with a scheme,
    /// then the content is copied into a memory stream and the memory stream is returned.
    /// Uses default timeout 100000 milliseconds (100 seconds).
    /// </summary>
    public static Stream openStreamFromHref(String href) throws Exception
    {
        return openStreamFromHref(href, DEFAULT_TIMEOUT);
    }

    /// <summary>
    /// Opens a stream from a URI or from a path. If it is a URI with a scheme,
    /// then the content is copied into a memory stream and the memory stream is returned.
    /// </summary>
    /// <param name="href">Request URI.</param>
    /// <param name="timeout">Web request timeout, measured in milliseconds.</param>
    /// <returns></returns>
    public static Stream openStreamFromHref(String href, final int timeout) throws Exception
    {
        //This handles http://, file:// and probably ftp://.
        //In java this can handle almost any URL, include http, ftp, jar, zip, file etc.
        if (UriUtil.isHrefWithScheme(href))
        {
            // FIX 12738 - Newline characters inside URLs prevent HTML import from loading resources.
            href = href.replace("\n", "");

            final URL url = new msUri(href).toJavaUrl();
            InputStream responseStream = null;

            // FIX: Do not specify capacity since the length of the response is sometimes not known.
            MemoryStream dstStream = new MemoryStream();
            ExecutorService executorService = null;
            try
            {
                // WORDSJAVA-1042 @nixspirit. According to WORDSNET-11514 user wants to limit the max time of waiting of request.

                // So lets create thread pool which has just one thread.
                executorService = Executors.newFixedThreadPool(1);

                // create a task to be called asynchronously
                Callable<InputStream> asyncCallToResource = new Callable<InputStream>()
                {
                    @Override
                    public InputStream call() throws Exception
                    {
                        // Host resolving operation goes first.
                        // It doesn't take into account URLConnection.setConnectTimeout and setReadTimeout
                        // So we check if URL can be resolved manually.
                        if (isUnresolved(url))
                            throw new java.net.UnknownHostException();

                        URLConnection conn = createConnection(url, timeout);
                        return (conn instanceof HttpURLConnection) ? getInputStreamRecursive((HttpURLConnection) conn) : conn.getInputStream();
                    }
                };

                // add new task into the pool
                Future<InputStream> futureTask = executorService.submit(asyncCallToResource);

                // call asyncCallToResource#call method so this will make the current thread wait for timeout
                responseStream = futureTask.get(timeout, TimeUnit.MILLISECONDS);
                if (responseStream != null)
                    JavaOnlyStreamUtil.copyStream(responseStream, dstStream);
            }
            catch (Exception e)
            {
                Log.warn(e);

                // WORDSJAVA-1936. Both c# and java have to raise an exception if data couldn't be read from the Internet
                throw e;
            }
            finally
            {
                // terminate all tasks and mark executorService available for GC
                if (executorService != null)
                    executorService.shutdownNow();

                if (responseStream != null)
                    responseStream.close();
            }

            dstStream.setPosition(0);
            return dstStream;
        }
        else
        {
            return File.openRead(href);
        }
    }

    private static URLConnection createConnection(HttpURLConnection connection, URL url) throws IOException
    {
        return createConnection(connection, url, DEFAULT_TIMEOUT);
    }

    private static URLConnection createConnection(URL url, int timeout) throws IOException
    {
        return createConnection(null, url, timeout);
    }

    /**
     * Creates a new connection from the given URL and all the request properties from the connection.
     *
     * @param connection original connection
     * @param url        new url
     * @param timeout    default timeout
     * @return new connection to the url. All request properties are kept
     * @throws IOException
     */
    private static URLConnection createConnection(HttpURLConnection connection, URL url, int timeout) throws IOException
    {
        Map<String, List<String>> requestProperties = null;
        if (connection != null)
        {
            connection.disconnect();
            requestProperties = connection.getRequestProperties();
        }

        URLConnection conn = WebRequestHelper.createRequestFromUrl(url);

        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        if (requestProperties != null)
        {
            // WORDSJAVA-1787 restore all the properties
            for (Map.Entry<String, List<String>> property : requestProperties.entrySet())
            {
                String value = msString.join(",", property.getValue().toArray(new String[property.getValue().size()]));
                if (!msString.isNullOrEmpty(value))
                    conn.setRequestProperty(property.getKey(), value);
            }
        }

        conn.connect();
        return conn;
    }

    // Java doesn't automatically redirect between different protocols, for instance from
    // http to https because of security: "it is unsafe to follow the redirect without user
    // approval". See:
    // http://stackoverflow.com/questions/14951696/java-urlconnection-get-the-final-redirected-url
    // http://stackoverflow.com/questions/1884230/urlconnection-doesnt-follow-redirect.
    private static InputStream getInputStreamRecursive(HttpURLConnection conn) throws IOException
    {
        int responseCode = conn.getResponseCode();

        // I saw following Response Codes for redirection: 301, 302, 303, 307.
        // HttpURLConnection doesn't contain constant for 307.
        if (responseCode >= 300 && responseCode < 400)
        {
            // get redirect url from "location" header field
            String location = conn.getHeaderField("Location");
            if (StringUtil.hasChars(location))
            {
                URL newUrl = new URL(location);

                // open the new connnection again
                HttpURLConnection newConn = (HttpURLConnection) createConnection(conn, newUrl);
                return getInputStreamRecursive(newConn);
            }
        }

        // All other statuses are ignored for resilience.
        InputStream inputStream = conn.getInputStream();

        // It's necessary to check the encoding of the content and return the corresponding stream.
        String contentEncoding = conn.getContentEncoding();
        if("deflate".equals(contentEncoding))
        {
            inputStream = new InflaterInputStream(inputStream);
        }
        else if ("gzip".equals(contentEncoding))
        {
            inputStream = new GZIPInputStream(inputStream);
        }

        return inputStream;
    }

    /// <summary>
    /// Gets all font file names registered in Windows and stores them in the specified hashtable.
    /// The key and the value is the full file name of the font.
    /// <param name="fileNames">Hashtable to store filenames.</param>
    /// </summary>
    public static void getFontFileNamesFromRegistry(StringToObjDictionary<String> fileNames)
    {
        // FIX WORDSJAVA-419:
        // Some fonts can be installed as shortcuts and not located in %WINDIR%\Fonts folder.
        // They can only be found in Windows registry
        try
        {
            Map<String, String> values = new WindowsRegistry().readRegistryStringValues(
                    WindowsRegistry.HKEY_LOCAL_MACHINE,
                    "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts");
            getFontFilesForKey(fileNames, values);

            // FIX 19531: On Win10 MW also uses current user registry for fonts.
            values = new WindowsRegistry().readRegistryStringValues(
                    WindowsRegistry.HKEY_CURRENT_USER,
                    "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Fonts");
            getFontFilesForKey(fileNames, values);
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
    }

    private static void getFontFilesForKey(StringToObjDictionary<String> fileNames, Map<String, String> values)
    {
        for (Map.Entry<String, String> entry : values.entrySet())
        {
            String path = entry.getValue();
            // Add only entries with absolute path
            // (others should be discovered
            // in standard way enumerating %WINDIR%\Fonts folder)
            if (path != null && new java.io.File(path).isAbsolute() && !fileNames.containsKey(path))
                fileNames.add(path, path);
        }
    }

    public static String getWindowsFontsFolder()
    {
        try
        {
            String windir = System.getenv("WINDIR");
            return StringUtil.hasChars(windir) ? Path.combine(windir, "Fonts") : "";
        }
        catch (Exception e)
        {
            // Fix Jira4912.
            // Consider there is no system fonts folder if access to environment variables is denied.
            return "";
        }
    }

    /// <summary>
    /// This method for java. Probably doesn't needed in .Net. Doesn't tested under .Net.
    /// </summary>
    public static String[] getLinuxFontFolders()
    {
        return new String[]{getLinuxUserFontFolder(), // user's local fonts' directory ~/.fonts
                getLinuxUserFontFolder2(), // user's local fonts' directory ~/.local/share/fonts
                "/usr/share/fonts", // Ubuntu, openSUSE
                "/usr/local/share/fonts", // Fedora?
                "/usr/X11R6/lib/X11/fonts", // RHEL?
                getJavaFontsFolder(),};
    }

    /// <summary>
    /// Returns the linux local user font path.
    /// </summary>
    public static String getLinuxUserFontFolder()
    {
        String userHome = System.getProperty("HOME");
        return StringUtil.hasChars(userHome) ? Path.combine(userHome, ".fonts") : "";
    }

    /// <summary>
    /// Returns the linux local user font path.
    /// </summary>
    public static String getLinuxUserFontFolder2()
    {
        String userHome = System.getProperty("user.home");
        return StringUtil.hasChars(userHome) ? Path.combine(userHome, ".local/share/fonts") : "";
    }

    public static String[] getMacOSFontsFolder() throws Exception
    {
        return new String[]
                {
                        getMacOSUserFontFolder(),
                        "/Library/Fonts",
                        "/System/Library/Fonts/"
                };
    }

    private static String getMacOSUserFontFolder() throws Exception
    {
        String userHome = System.getenv("HOME");
        return StringUtil.hasChars(userHome) ? Path.combine(Path.combine(userHome, "Library"), "Fonts") : "";
    }

    public static String[] getIosFontsFolder() throws Exception
    {
        // https://support.apple.com/en-us/HT201722
        return new String[]{getIosUserFontFolder(), "/Library/Fonts", "/System/Library/Fonts/", "/System Folder/Fonts/", "/Network/Library/Fonts/",};
    }

    private static String getIosUserFontFolder() throws Exception
    {
        String userHome = System.getenv("user.home");
        return StringUtil.hasChars(userHome) ? Path.combine(Path.combine(userHome, "Library"), "Fonts") : "";
    }

    /// <summary>
    /// This just returns some tick count in milliseconds. It can be current time or the time since the system started.
    /// </summary>
    public static int getTickCount()
    {
        return (int) System.currentTimeMillis();
    }

    /// <summary>
    /// Gets specific culture by it name.
    /// </summary>
    public static msCultureInfo getCulture(String culture)
    {
        return new msCultureInfo(culture);
    }

    /// <summary>
    /// Gets specific culture by it id.
    /// </summary>
    public static msCultureInfo getCulture(int lcid)
    {
        return new msCultureInfo(lcid, !gTestMode);
    }

    /// <summary>
    /// Gets specific culture by it name.
    /// </summary>
    public static msCultureInfo tryGetCulture(String cultureName)
    {
        try
        {
            return getCulture(cultureName);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
        catch (CultureNotFoundException e)
        {
            return null;
        }
    }

    /// <summary>
    /// Gets specific culture by it id.
    /// </summary>
    public static msCultureInfo tryGetCulture(int lcid)
    {
        try
        {
            return getCulture(lcid);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    //JAVA-added
    public static Locale getLocale(String culture)
    {
        //Java requires language and country in separate strings, i.e. "en-NZ" -> "en", "NZ".
        final String[] cultureParts = culture.split("-");

        if (cultureParts.length == 2)
        {
            //both language and country exist
            return new Locale(cultureParts[0], cultureParts[1]);
        } else if (cultureParts.length == 1)
        {
            //only language
            return new Locale(cultureParts[0]);
        } else
        {
            //just try given string
            return new Locale(culture);
        }
    }

    /// <summary>
    /// Gets the culture name of the current thread culture in the "language2-country2" format.
    /// </summary>
    public static String getCurrentCultureName()
    {
        return CurrentThread.getCurrentCulture().getName();
    }

    /// <summary>
    /// Gets the current thread culture.
    /// </summary>
    public static msCultureInfo getCurrentCulture()
    {
        return CurrentThread.getCurrentCulture();
    }

    /// <summary>
    /// Gets the culture name of the current thread culture in the ISO 639-1 two-letter code format.
    /// </summary>
    public static String getCurrentCultureTwoLetterName()
    {
        return CurrentThread.getCurrentCulture().getLocale().getLanguage();
    }

    /// <summary>
    /// Gets the system default LCID from Windows registry. Returns 0 if the value cannot be obtained.
    /// </summary>
    public static int getSystemDefaultLcid()
    {
        if (gSystemLcid != 0)
            return gSystemLcid;

        try
        {
            Map<String, String> fontsKey = new WindowsRegistry().readRegistryStringValues(
            WindowsRegistry.HKEY_LOCAL_MACHINE,
                "SYSTEM\\CurrentControlSet\\Control\\Nls\\Language");
            if (fontsKey == null)
                return 0;

            String value = fontsKey.get("Default");
            gSystemLcid = FormatterPal.parseHex(value);

            return gSystemLcid;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /// <summary>
    /// Sets the culture for the current thread e.g. "ru-RU".
    /// </summary>
    public static msCultureInfo setCulture(String culture)
    {
        msCultureInfo cultureInfo = getCulture(culture);
        return setCulture(cultureInfo);
    }

    public static msCultureInfo setCulture(msCultureInfo cultureInfo)
    {
        CurrentThread.setCurrentCulture(cultureInfo);
        return cultureInfo;
    }

    /// <summary>
    /// Sets the "standard development" culture, which is "en-nz".
    /// </summary>TestSaveToHtmlFixed
    /// <remarks>
    /// For test purposes only.
    /// </remarks>
    public static void setStandardCulture()
    {
        CurrentThread.setCurrentCulture(G_STANDARD_CULTURE);
    }


    /// <summary>
    /// Saves currect culture to a ThreadStatic field. Use <see cref="RestoreCulture"/>to restore the saved culture.
    /// </summary>
    public static void saveCulture()
    {
        gOldCultures.get().push(getCurrentCulture());
    }

    /// <summary>
    /// Restores the culture that was saved with <see cref="SaveCulture"/>.
    /// </summary>
    public static void restoreCulture()
    {
        Debug.assertTrue(gOldCultures.get().getCount() != 0);
        setCulture(gOldCultures.get().pop());
    }

    // [ThreadStatic] in .NET. It has to be per thread here as well: tests run in parallel, and with one shared stack
    // a save/restore pair of one test interleaves with another's, after which every later restoreCulture() asserts.
    private static final ThreadLocal<msStack<msCultureInfo>> gOldCultures = new ThreadLocal<msStack<msCultureInfo>>()
    {
        @Override
        protected msStack<msCultureInfo> initialValue()
        {
            return new msStack<msCultureInfo>();
        }
    };

    //JAVA-added:

    /// <summary>
    /// This is the most "human" culture. It uses numbers like 123.45 (decimal dot) and dates like dd/mm/yyyy.
    /// </summary>
    private static final msCultureInfo G_STANDARD_CULTURE = msCultureInfo.getCultureInfo("en-NZ");

    /// <summary>
    /// LCID cached value from Windows Registry.
    /// </summary>
    private static int gSystemLcid;

    public static String[] getAndroidFontFolders()
    {
        return new String[]{"/system/fonts", "/system/font", "/data/fonts",};
    }

    /// <summary>
    /// Returns the path to JRE fonts folder. e.g. X:\Program Files\Java\jdk1.8.0_101\jre\lib\fonts
    /// Fonts from this directory will be used in Unix-based systems.
    /// This method uses "java.home" property. It seems to be working on any JDK.
    /// See this https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
    /// </summary>
    public static String getJavaFontsFolder()
    {
        String javaHome = System.getProperties().getProperty("java.home");
        if (!StringUtil.hasChars(javaHome))
            return msString.Empty;

        String libFolder = Path.combine(javaHome, "lib");
        return Path.combine(libFolder, "fonts");
    }

    private static boolean gTestMode = false;

    public static MemoryStream executeWebRequest(String requestUri, String username, String password)
    {
        InputStream response = null;
        try
        {
            URLConnection connection = WebRequestHelper.createRequestFromUri(requestUri);
            connection.setDoOutput(true); // POST
            connection.setRequestProperty("Accept-Charset", REQUEST_CHARSET);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + REQUEST_CHARSET);

            if (StringUtil.hasChars(username))
            {
                //webRequest.Credentials = new NetworkCredential(userName, password);
                String userpass = username + ":" + password;
                String basicAuth = "Basic " + Base64.encodeToString(userpass.getBytes(), false);
                connection.setRequestProperty("Authorization", basicAuth);
            }

            if (connection instanceof HttpURLConnection)
                response = getInputStreamRecursive((HttpURLConnection) connection);
            else
                response = connection.getInputStream();

            MemoryStream responseStream = JavaOnlyStreamUtil.copyToMemoryStream(response);
            responseStream.setPosition(0);
            return responseStream;
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
        finally
        {
            try
            {
                if (response != null)
                    response.close();
            }
            catch (Exception ex)
            {
                Log.warn(ex);
            }

        }
        return null;
    }

    //JAVA-added

    /**
     * Tries to resolve url.
     *
     * @return <code>true</code> if host was not been found.
     */
    private static boolean isUnresolved(URL url)
    {
        if (url.getPort() < 0 && url.getDefaultPort() < 0)
            return false;

        int port = url.getPort() > 0 ? url.getPort() : url.getDefaultPort();
        return new InetSocketAddress(url.getHost(), port).isUnresolved();
    }

    //JAVA-added
    private static final String REQUEST_CHARSET = "UTF-8";
    private/*final*/ static final int DEFAULT_TIMEOUT = 100000;
}

