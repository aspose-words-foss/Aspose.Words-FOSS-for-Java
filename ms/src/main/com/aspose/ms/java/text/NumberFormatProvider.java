package com.aspose.ms.java.text;

import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.util.JavaUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class takes care of creating and reusing objects created with NumberFormat.get[Currency|Ingeger|Percent|etc]Instance() methods.
 * It caches NumberFormat objects per requested locale then clone the instance and return to the user.
 * <p>
 * This class provides different sets of methods:
 * <ul>
 * <li>
 * <code>new*Instance()</code> methods - they return cloned version of NumberFormat instance (and cache it if there is no such NumberFormat instance).
 * This methods MUST be used when you change the NumberFormat (applyPattern(), set*(), etc) or call <code>format()</code> or <code>parse()</code> methods.
 * <p>
 * </li>
 * <li><code>get*Instance()</code> methods - they return the original NumberFormat to read data from it. e.g. when you would like to retrieve some symbols etc</li>
 * </ul>
 * This class is thread-safe but not forget use <code>new*Instance()</code> methods in case if you going to change NumberFormat.
 */
public class NumberFormatProvider
{

// ############################################################

    /**
     * @return new instance of NumberFormat.getInstance() call for the default Thread's locale
     */
    public static DecimalFormat newInstance()
    {
        return newInstance(CurrentThread.getCurrentCulture().getLocale());
    }

    /**
     * @return new instance of NumberFormat.getInstance() call for the given locale
     */
    public static DecimalFormat newInstance(Locale locale)
    {
        return gDecimalFormatInstances.clone(locale);
    }

// ############################################################

    /**
     * @return new instance of NumberFormat.getInstance() which has been updated with .NET symbols for the default Thread's locale
     */
    public static DecimalFormat newFixedInstance()
    {
        return gFixedDecimalFormatsCache.clone(CurrentThread.getCurrentCulture().getLocale());
    }

    /**
     * @return new instance of NumberFormat.getInstance() which has been updated with .NET symbols for the the given locale
     */
    public static DecimalFormat newFixedInstance(Locale locale)
    {
        return gFixedDecimalFormatsCache.clone(locale);
    }

    /**
     * @return the existing in cache NumberFormat instance which has been updated with .NET symbols for the the given locale
     */
    public static DecimalFormat getFixedInstance(Locale locale)
    {
        return gFixedDecimalFormatsCache.get(locale);
    }

// ############################################################

    public static DecimalFormat newNumberInstance()
    {
        return newNumberInstance(CurrentThread.getCurrentCulture().getLocale());
    }

    public static DecimalFormat newNumberInstance(Locale locale)
    {
        return gNumberInstances.clone(locale);
    }

// ############################################################

    public static NumberFormat newCurrencyInstance(Locale locale)
    {
        return gCurrencyInstances.clone(locale);
    }

    public static DecimalFormat newFixedCurrencyInstance()
    {
        return gFixedCurrencyInstances.clone(CurrentThread.getCurrentCulture().getLocale());
    }

// ############################################################

    public static DecimalFormat newIntegerInstance(Locale locale)
    {
        return gIntegerInstances.clone(locale);
    }

// ############################################################

    public static NumberFormat newPercentInstance(Locale locale)
    {
        return gPercentInstances.clone(locale);
    }

// ############################################################

    /***
     * @return the original DecimalFormatSymbols object. If you change something in it other users will get changed version too.
     * Call getDecimalFormatSymbols().clone() if you need to change it or add a new method here.
     */
    public static DecimalFormatSymbols getDecimalFormatSymbols()
    {
        return getDecimalFormatSymbols(CurrentThread.getCurrentCulture().getLocale());
    }

    /***
     * @return the original DecimalFormatSymbols object. If you change something in it other users will get changed version too.
     * Call getDecimalFormatSymbols().clone() if you need to change it or add a new method here.
     */
    public static DecimalFormatSymbols getDecimalFormatSymbols(Locale locale)
    {
        return getFixedInstance(locale).getDecimalFormatSymbols();
    }

    public static String getCurrencySymbol()
    {
        return getDecimalFormatSymbols(CurrentThread.getCurrentCulture().getLocale()).getCurrencySymbol();
    }

    public static void setCurrencySymbol(String currency)
    {
        getDecimalFormatSymbols(CurrentThread.getCurrentCulture().getLocale()).setCurrencySymbol(currency);
    }

    public static char getDecimalSeparator()
    {
        return getDecimalSeparator(CurrentThread.getCurrentCulture().getLocale());
    }

    public static char getDecimalSeparator(Locale locale)
    {
        return getDecimalFormatSymbols(locale).getDecimalSeparator();
    }

    public static char getGroupingSeparator()
    {
        return getGroupingSeparator(CurrentThread.getCurrentCulture().getLocale());
    }

    public static char getGroupingSeparator(Locale locale)
    {
        return getDecimalFormatSymbols(locale).getGroupingSeparator();
    }

    public static int getGroupingSize()
    {
        return getFixedInstance(CurrentThread.getCurrentCulture().getLocale()).getGroupingSize();
    }

// ##############################

    private static void fixDecimalFormat(DecimalFormat decimalFormat)
    {
        // FIX WORDSJAVA-1975: New grivna (hryvnia) currency suffix.
        if (UKRAINIAN_CULTURE.equals(getCurrentCultureName()))
        {
            DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
            if (!UKRAINIAN_CURRENCY.equals(symbols.getCurrencySymbol()))
            {
                symbols.setCurrencySymbol(UKRAINIAN_CURRENCY);
                // We can't simply change properties without setting the whole
                // symbols object, because each getDecimalFormatSymbols()
                // returns cloned object instance
                decimalFormat.setDecimalFormatSymbols(symbols);
            }
        }
        else if (AUSTRIAN_CULTURE.equals(getCurrentCultureName()) || SETSWANA_CULTURE.equals(getCurrentCultureName()))
        {
            DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
            if(NBSP_NUMBER_GROUPING_SEPARATOR != symbols.getGroupingSeparator())
            {
                symbols.setGroupingSeparator(NBSP_NUMBER_GROUPING_SEPARATOR);
                decimalFormat.setDecimalFormatSymbols(symbols);
            }
        }
    }

    private static String getCurrentCultureName()
    {
        return CurrentThread.getCurrentCulture().toString();
    }

    /***
     * The main purpose of this class is to cache NumberFormat instances.
     * It can return the original NumberFormat instance for the given locale or its cloned version to be changed.
     *
     * @param <V> NumberFormat and its descendants
     */
    private abstract static class NumberFormatCache<V extends NumberFormat>
    {
        public V clone(Locale locale)
        {
            return (V) get(locale).clone(); // clone() works faster then NumberFormat.get*Instance()
        }

        public V get(Locale locale)
        {
            if (mCache.containsKey(locale))
                return mCache.get(locale);

            // WORDSJAVA-1237. I don't use locks here.
            // Yes it could lead to unnecessary get*Instance() calls.
            // Nevertheless the cache will stay correct and it almost won't affect the speed.
            V decimalFormat = (V) getNumberFormatInstance(locale);
            mCache.putIfAbsent(locale, decimalFormat);
            return decimalFormat;
        }

        public abstract NumberFormat getNumberFormatInstance(Locale locale);

        private final ConcurrentHashMap<Locale, V> mCache = new ConcurrentHashMap<Locale, V>();
    }

    private static final String UKRAINIAN_CULTURE = "uk-UA";
    private static final String AUSTRIAN_CULTURE = "de-AT";
    private static final String SETSWANA_CULTURE = "tn-ZA";
    private static final String UKRAINIAN_CURRENCY = "₴";
    private static final char NBSP_NUMBER_GROUPING_SEPARATOR = '\u00A0';

    // FIX WORDSJAVA-1134: ExecuteWithRegions call significantly slower after Aspose.Words upgrade (15.7.0)
    // Pre-calculate this value because JavaUtil.isJdkOlderThan() can be called many times.
    private static final boolean JDK_OLDER_THAN_1_6_0_25 = JavaUtil.isJdkOlderThan("1.6.0_25");

    // WORDSJAVA-1237. In order to avoid calling NumberFormat.get*Instance() methods we cache the retrieved formats by their types.

    // NumberFormat.getInstance(locale) with .NET symbols
    private final static NumberFormatCache<DecimalFormat> gFixedDecimalFormatsCache =
            new NumberFormatCache<DecimalFormat>()
            {
                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    DecimalFormat fmt = (DecimalFormat) NumberFormat.getInstance(locale);
                    fixDecimalFormat(fmt);
                    return fmt;
                }
            };

    // NumberFormat.getInstance(locale)
    private final static NumberFormatCache<DecimalFormat> gDecimalFormatInstances =
            new NumberFormatCache<DecimalFormat>()
            {
                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    return (DecimalFormat) NumberFormat.getInstance(locale);
                }
            };

    // NumberFormat.getCurrencyInstance(locale)  with .NET symbols
    private final static NumberFormatCache<DecimalFormat> gFixedCurrencyInstances =
            new NumberFormatCache<DecimalFormat>()
            {
                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    DecimalFormat fmt = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
                    fixDecimalFormat(fmt);
                    return fmt;
                }
            };

    // NumberFormat.getCurrencyInstance(locale)
    private final static NumberFormatCache<DecimalFormat> gCurrencyInstances =
            new NumberFormatCache<DecimalFormat>()
            {
                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    return (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
                }
            };

    // NumberFormat.getNumberInstance(locale)
    private final static NumberFormatCache<DecimalFormat> gNumberInstances =
            new NumberFormatCache<DecimalFormat>()
            {
                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    return (DecimalFormat) NumberFormat.getNumberInstance(locale);
                }
            };

    // NumberFormat.getIntegerInstance(locale)
    private static NumberFormatCache<DecimalFormat> gIntegerInstances =
            new NumberFormatCache<DecimalFormat>()
            {

                @Override
                public DecimalFormat getNumberFormatInstance(Locale locale)
                {
                    return (DecimalFormat) NumberFormat.getIntegerInstance(locale);
                }
            };

    // NumberFormat.getPercentInstance(locale)
    private final static NumberFormatCache<NumberFormat> gPercentInstances =
            new NumberFormatCache<NumberFormat>()
            {
                @Override
                public NumberFormat getNumberFormatInstance(Locale locale)
                {
                    return NumberFormat.getPercentInstance(locale);
                }
            };
}
