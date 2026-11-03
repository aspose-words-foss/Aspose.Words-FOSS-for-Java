package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.msString;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.XmlUtils;
import com.aspose.words.ref.Ref;
import com.ctc.wstx.stax.WstxInputFactory;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.ri.Stax2ReaderAdapter;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CultureDataLoader
{
    private interface ICultureFinder
    {
        boolean canApply(CultureDataReader reader);

        boolean apply(CultureDataReader reader);
    }

    public static String LCIDToLocaleName(int cultureID)
    {
        if (cultureID < 1)
            return msString.Empty;

        final String lcid = String.valueOf(cultureID);
        final Ref<String> cultureName = new Ref<>(msString.Empty);

        findCultureByCriteria(new ICultureFinder()
        {
            @Override
            public boolean canApply(CultureDataReader reader)
            {
                return lcid.equals(reader.getAttribute("LCID"));
            }

            @Override
            public boolean apply(CultureDataReader reader)
            {
                cultureName.set(reader.getAttribute("Name"));
                return true;
            }
        }, true);

        return cultureName.get();
    }

    private static boolean findCultureByCriteria(ICultureFinder finder, boolean applyToFirst)
    {
        CultureDataReader sr = getReader();

        try
        {
            while (sr.hasNext())
            {
                if (sr.next() == XMLStreamConstants.START_ELEMENT && "Culture".equals(sr.getLocalName()) && finder.canApply(sr))
                {
                    if (finder.apply(sr) && applyToFirst)
                        return true;
                }
            }
        }
        catch (Exception e)
        {
            Log.error(e);
        }
        finally
        {
            sr.close();
        }

        return !applyToFirst;
    }

    private static CultureDataReader getReader()
    {
        try
        {
            XMLStreamReader r = factory.createXMLStreamReader(getCultureInfoStream());
            r = factory.createFilteredReader(r, gXmlFilter);
            return new CultureDataReader(Stax2ReaderAdapter.wrapIfNecessary(r));
        }
        catch (XMLStreamException e)
        {
            Log.error(e);
        }

        throw new IllegalStateException("Cannot read CultureInfo.xml");
    }

    /**
     * This method returns the CultureInfo.xml stream.
     * the CultureInfo.xml will be copied into the com/aspose/words/resources/ directory
     * But in the case of re-packaging this class will look for the xml file in the current directory and in the parent directory.
     * See the ResourceManager class.
     * We should make the ResourceManager available for any module.
     *
     * @return the CultureInfo.xml stream
     */
    private static InputStream getCultureInfoStream()
    {
        Class<CultureDataLoader> classCaller = CultureDataLoader.class;

        if (gCachedResourcePrefix != null)
            return classCaller.getResourceAsStream(gCachedResourcePrefix);

        // 1. the first attempt, dev environment or the production jar
        String candidate = "/com/aspose/words/resources/CultureInfo.xml";
        InputStream stream = classCaller.getResourceAsStream(candidate);
        if (stream != null)
        {
            gCachedResourcePrefix = candidate;
            return stream;
        }

        // 2. production jar i.e. (x.y.z.)com.aspose.words.internal.resources
        String classRootDir = classCaller.getPackage().getName().replaceAll("\\.", "/");
        candidate = classRootDir + "/resources/CultureInfo.xml";
        stream = classCaller.getResourceAsStream(candidate);
        if (stream != null)
        {
            gCachedResourcePrefix = candidate;
            return stream;
        }

        // 3. last resort try to find resources' folder in the parent folder
        // i.e. (x.y.z.)com.aspose.words.resources
        classRootDir = classRootDir.substring(0, classRootDir.lastIndexOf("/"));
        candidate = classRootDir + "/resources/CultureInfo.xml";
        stream = classCaller.getResourceAsStream(candidate);

        if (stream != null)
        {
            gCachedResourcePrefix = candidate;
            return stream;
        }

        throw new IllegalStateException("CultureInfo.xml cannot be found");
    }

    public static List<CultureData> getCultureNamesByType(final int types)
    {
        if (types <= 0)
            return Collections.emptyList();

        final List<CultureData> cultureDatas = new ArrayList<>(400);

        findCultureByCriteria(new ICultureFinder()
        {
            @Override
            public boolean canApply(CultureDataReader reader)
            {
                return (types & reader.getAttributeAsInt("Type")) != 0;
            }

            @Override
            public boolean apply(CultureDataReader reader)
            {
                CultureData cultureData = new CultureData();
                fillCultureInfo(reader, cultureData);
                cultureDatas.add(cultureData);
                return true;
            }
        }, false);

        return cultureDatas;
    }

    private static boolean fillCultureInfo(CultureDataReader sr, CultureData cultureData)
    {
        try
        {
            if (fillCulture(sr, cultureData))
            {
                if (sr.hasNext())
                    fillDateTimeFormat(sr.next(), sr, cultureData);

                return true;
            }
        }
        catch (Exception e)
        {
            Log.error(e);
        }
        return false;
    }

    private static boolean fillCulture(CultureDataReader reader, CultureData cultureData)
    {
        if (!"Culture".equals(reader.getLocalName()))
            return false;

        cultureData.setCultureType(reader.getAttributeAsInt("Type"));
        cultureData.setName(reader.getAttribute("Name"));
        cultureData.setEngDisplayName(reader.getAttribute("EnglishName"));
        cultureData.setNativeDisplayName(reader.getAttribute("NativeName"));
        cultureData.setLocalizedDisplayName(reader.getAttribute("DisplayName"));
        cultureData.setLanguage(reader.getAttributeAsInt("LCID"));
        cultureData.setDefaultCalendarId(reader.getAttributeAsInt("CalendarId"));
        cultureData.setCalendarIds(reader.getAttributeAsIntArray("OptionalCalendars"));
        cultureData.setNeutralCulture(reader.getAttributeAsBoolean("IsNeutralCulture"));
        cultureData.setISO639LangName(reader.getAttribute("TwoLetterISOLanguageName"));
        cultureData.setISO639LangName2(reader.getAttribute("ThreeLetterISOLanguageName"));
        cultureData.setAbbrevLangName(reader.getAttribute("ThreeLetterWindowsLanguageName"));
        cultureData.setListSeparator(reader.getAttribute("ListSeparator"));
        cultureData.setANSICodePage(reader.getAttributeAsInt("ANSICodePage"));
        return true;
    }

    private static void fillDateTimeFormat(int eventType, CultureDataReader reader, CultureData cultureData)
    {
        if (eventType != XMLStreamConstants.START_ELEMENT || !"DateTimeFormat".equals(reader.getLocalName()))
            return;

        CalendarData calendarData = new CalendarData();
        calendarData.setCalendarId(cultureData.getDefaultCalendar().getID());
        calendarData.setLongDatePattern(reader.getAttribute("LongDatePattern"));
        calendarData.setLongTimePattern(reader.getAttribute("LongTimePattern"));
        calendarData.setShortDatePattern(reader.getAttribute("ShortDatePattern"));
        calendarData.setShortTimePattern(reader.getAttribute("ShortTimePattern"));
        calendarData.setMonthDayPattern(reader.getAttribute("MonthDayPattern"));
        calendarData.setYearMonthPattern(reader.getAttribute("YearMonthPattern"));
        calendarData.setDateSeparator(reader.getAttribute("DateSeparator"));
        calendarData.setTimeSeparator(reader.getAttribute("TimeSeparator"));
        calendarData.setFullDateTimePattern(reader.getAttribute("FullDateTimePattern"));
        calendarData.setPMDesignator(reader.getAttribute("PMDesignator"));
        calendarData.setAMDesignator(reader.getAttribute("AMDesignator"));
        calendarData.setRFC1123Pattern(reader.getAttribute("RFC1123Pattern"));
        calendarData.setSortableDateTimePattern(reader.getAttribute("SortableDateTimePattern"));
        calendarData.setUniversalSortableDateTimePattern(reader.getAttribute("UniversalSortableDateTimePattern"));
        cultureData.setCalendarData(calendarData);
    }

    public static boolean loadCultureData(final CultureData cultureData)
    {
        final String realName = cultureData.getCultureName();

        if (msString.isNullOrEmpty(realName))
            return false;

        return findCultureByCriteria(new ICultureFinder()
        {
            @Override
            public boolean canApply(CultureDataReader reader)
            {
                return realName.equalsIgnoreCase(reader.getAttribute("Name"));
            }

            @Override
            public boolean apply(CultureDataReader reader)
            {
                return fillCultureInfo(reader, cultureData);
            }
        }, true);
    }

    private static final class CultureDataReader
    {
        public String getLocalName()
        {
            return reader.getLocalName();
        }

        public void close()
        {
            try
            {
                reader.close();
            }
            catch (XMLStreamException e)
            {
                Log.error(e);
            }
        }

        public boolean hasNext() throws XMLStreamException
        {
            return reader.hasNext();
        }

        public int next() throws XMLStreamException
        {
            return reader.next();
        }

        public boolean getAttributeAsBoolean(String attributeName)
        {
            return Boolean.parseBoolean(getAttribute(attributeName));
        }

        public String getAttribute(String attributeName)
        {
            return reader.getAttributeValue(msString.Empty, attributeName);
        }

        public int[] getAttributeAsIntArray(String attributeName)
        {
            String val = getAttribute(attributeName);
            String[] split = val.split(",");
            int[] result = new int[split.length];
            for (int i = 0; i < result.length; i++)
                result[i] = Integer.parseInt(split[i]);

            return result;
        }

        public int getAttributeAsInt(String attributeName)
        {
            return Integer.parseInt(getAttribute(attributeName));
        }

        CultureDataReader(XMLStreamReader2 reader)
        {
            this.reader = reader;
        }

        private final XMLStreamReader2 reader;
    }

    private static final WstxInputFactory factory = XmlUtils.newWstxInputFactory(false, true);
    private static final StreamFilter gXmlFilter = new StreamFilter()
    {
        @Override
        public boolean accept(XMLStreamReader reader)
        {
            return reader.isStartElement();
        }
    };
    private volatile static String gCachedResourcePrefix;

    private CultureDataLoader()
    {
        // intentionally left blank
    }
}
