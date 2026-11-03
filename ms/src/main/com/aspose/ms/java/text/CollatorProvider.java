package com.aspose.ms.java.text;

import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.logging.Log;

import java.lang.ref.SoftReference;
import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This functionality is related to WORDSJAVA-1469, WORDSJAVA-1386, WORDSJAVA-1205 (partly) tasks.
 *
 * The .NET Framework uses three distinct ways of sorting: word sort, string sort, and ordinal sort.
 * This class provides ready-to-use {@link Collator}s according to chosen sorting algorithm.
 *
 * For more details see https://msdn.microsoft.com/en-us/library/system.globalization.compareoptions(v=vs.110).aspx
 */
public class CollatorProvider
{
    /**
     * Returns a collator instance which used "word-sort" algorithm.
     * This is default Java behaviour.
     *
     * Word sort performs a culture-sensitive comparison of strings.
     * Certain nonalphanumeric characters might have special weights assigned to them.
     * For example, the hyphen ("-") might have a very small weight assigned to it so that "coop" and "co-op" appear next to each other in a sorted list.
     *
     * @return cached {@link Collator} instance
     */
    public static Collator getWordSortCollator()
    {
        return getWordSortCollator(CurrentThread.getCurrentCulture().getLocale());
    }

    public static Collator getWordSortIgnoreCaseCollator()
    {
        Locale locale = CurrentThread.getCurrentCulture().getLocale();
        return getWordSortIgnoreCaseCollator(locale);
    }

    public static Collator getInvariantCollator()
    {
        return gInvariantCollator;
    }

    public static Collator getInvariantIgnoreCaseCollator()
    {
        return gInvariantIgnoreCaseCollator;
    }

    /**
     * Returns a collator instance which used the "word-sort" algorithm.
     * This is default Java behaviour.
     *
     * Word sort performs a culture-sensitive comparison of strings.
     * Certain nonalphanumeric characters might have special weights assigned to them.
     * For example, the hyphen ("-") might have a very small weight assigned to it so that "coop" and "co-op" appear next to each other in a sorted list.
     *
     * @return cached {@link Collator} instance
     */
    public static Collator getWordSortCollator(Locale locale)
    {
        if (locale == Locale.ROOT)
            return getInvariantCollator();

        Collator result = gWordSortCollatorCache.get(locale);
        if (result == null)
        {
            if (gStandardCulture.equals(locale))
                result = createEnNzCollator();
            else
                result = (Collator) Collator.getInstance(locale);

            gWordSortCollatorCache.putIfAbsent(locale, result);
        }

        return result;
    }

    public static Collator getWordSortIgnoreCaseCollator(Locale locale)
    {
        if (locale == Locale.ROOT)
            return getInvariantIgnoreCaseCollator();

        Collator result = gIgnoreCaseCollatorCache.get(locale);
        if (result == null)
        {
            result = (Collator) Collator.getInstance(locale);
            result.setStrength(Collator.SECONDARY);
            gIgnoreCaseCollatorCache.putIfAbsent(locale, result);
        }

        return result;
    }

    /**
     * Returns a collator instance that use the "string-sort" algorithm.
     * In a string sort, the hyphen and the apostrophe, as well as other nonalphanumeric symbols,
     * come before alphanumeric characters.
     *
     * @return cached {@link Collator} instance
     */
    public static Collator getStringSortCollator()
    {
        return getStringSortCollator(CurrentThread.getCurrentCulture().getLocale());
    }

    /**
     * Returns a collator instance that use the string sort algorithm.
     * In a string sort, the hyphen and the apostrophe, as well as other nonalphanumeric symbols,
     * come before alphanumeric characters.
     *
     * @param locale which is used to create new Collator's instance.
     * @return cached {@link Collator} instance
     */
    public static Collator getStringSortCollator(Locale locale)
    {
        SoftReference<Collator> ref = gStringSortCache.get(locale);
        Collator result = (ref != null) ? ref.get() : null;
        if (result == null)
        {
            // WORDSJAVA-1386 users may register custom Collator's implementation as default.
            // So we should check before casting to the RuleBasedCollator.
            result = Collator.getInstance(locale); //CultureInfo.CurrentCulture. see .NET
            if (result instanceof RuleBasedCollator)  // Only our custom RuleBasedCollators will be cashed.
            {
                result = createNETStringSortCollator((RuleBasedCollator) result);
                while (true)
                {
                    if (ref != null)  // Remove the empty SoftReference if any
                        gStringSortCache.remove(locale, ref);

                    // cache it
                    ref = gStringSortCache.putIfAbsent(locale, new SoftReference<Collator>(result));
                    if (ref == null) // the new collator instance has been added
                        break;

                    Collator cachedColl = ref.get(); // existing collotar's instance will be returned
                    if (cachedColl != null)
                    {
                        result = cachedColl;
                        break;
                    }
                }
            }
        }
        return result; // At the moment there is no need to do .clone()
    }

    private static Collator createNETStringSortCollator(RuleBasedCollator collator)
    {
        if (collator == null)
            throw new IllegalArgumentException("RuleBasedCollator must be present.");

        // RK This is my experiment with the rule-based collator. I do not fully understand it because the default
        // rule seems to specify the same order. The default rule specifies hyphen and apostrophe before digits and letters
        // and that's exactly what we need, but it still returns "-abc" greater than "0abc", where it should be less.
        // I modify the default rule by adding stuff at the end. It makes the tests pass, but probably the rule is incomplete.
        // We should also consider moving this into PalFormatterCore to cache the instance.
        final StringBuilder defaultRules = new StringBuilder(collator.getRules());
        makeMinusNotIgnorable(defaultRules);
        try
        {
            final RuleBasedCollator ruleBasedCollator = new RuleBasedCollator(defaultRules.toString());
            ruleBasedCollator.setDecomposition(collator.getDecomposition());
            ruleBasedCollator.setStrength(collator.getStrength());
            return ruleBasedCollator;
        }
        catch (ParseException ex)
        {
            Log.warn(ex);
        }
        return collator;
    }

    /**
     * This method moves hyphens,apostrophes and minuses from the ignorable section at the beginning of the main rule.
     *
     * e.g The default rule for en-NZ is
     * ⃟;⃠;⃡ ,'-';­;‐;‑;‒;–;—;―;−<'_'<¯<','<';'<':'<
     * This method will change it as follow:
     * ⃟;⃠;⃡ <'-';­;‐;‑;‒;–;—;―;−<'_'<¯<','<';'<':'<
     * So the "minuses" will be less then letters, digits and some other symbols.
     *
     * @param rules
     */
    private static void makeMinusNotIgnorable(StringBuilder rules)
    {
        if (rules == null || rules.length() == 0)
            return;

        List<String> minuses = new ArrayList<String>();
        boolean quoted = false;
        int i = 0;
        while (i < rules.length() - 1) // In reality it breaks when coming across <
        {
            char ch = rules.charAt(i);
            switch (ch)
            {
                case '-':
                case '\u00AD':
                case '‐':
                case '‑':
                case '‒':
                case '–':
                case '—':
                case '―':
                case '−':
                    if (quoted)
                    {
                        minuses.add("'" + ch + "'");
                        quoted = !quoted;

                        rules.delete(i - 1, i + 2); // remove '-'
                        i--;

                        // next goes either the relation ;,= or <
                        if (rules.charAt(i) != '<')
                            rules.deleteCharAt(i);
                    }
                    else // not quoted
                    {
                        minuses.add(String.valueOf(ch));
                        rules.deleteCharAt(i); // remove just -
                        // next goes either the relation ;,= or <
                        if (rules.charAt(i) != '<')
                            rules.deleteCharAt(i);
                    }
                    break;

                case '<': // rule has started
                    if (!quoted)
                    {
                        if (!minuses.isEmpty())
                        {
                            int newPosition = i - 1;
                            final char precedingChar = rules.charAt(newPosition);
                            if (precedingChar == ',' || precedingChar == ';' || precedingChar == '=')
                                rules.deleteCharAt(newPosition);

                            else
                                newPosition = i;

                            StringBuilder minusesRule = makeMinusRule(minuses);
                            rules.insert(newPosition, minusesRule);
                        }
                        return;
                    }
                    else
                        quoted = !quoted;

                case '\'':
                    quoted = !quoted;
                default:
                    i++;

            }
        }
    }

    /// WORDSJAVA-1860. .NET has weird logic of comparing letters and apostrophe.
    /// Comparison rules are: ab < a'b, but ab > a' b. Here we generate root-locale based
    /// collator which uses these rules.
    private static Collator createRootCollator()
    {
        // Collator.getInstance(Locale.ROOT) was created with the following rule (see sources)
        // RuleBasedCollator("='\u200b'=\u200c=\u200d=\u200e=\u200f=\u0000 =\u0001 =\u0002 =\u0003 =\u0004=\u0005 =\u0006 =\u0007 =\b ='\t'='\u000b' =\u000e=\u000f ='\u0010' =\u0011 =\u0012 =\u0013=\u0014 =\u0015 =\u0016 =\u0017 =\u0018=\u0019 =\u001a =\u001b =\u001c =\u001d=\u001e =\u001f =\u007f=\u0080 =\u0081 =\u0082 =\u0083 =\u0084 =\u0085=\u0086 =\u0087 =\u0088 =\u0089 =\u008a =\u008b=\u008c =\u008d =\u008e =\u008f =\u0090 =\u0091=\u0092 =\u0093 =\u0094 =\u0095 =\u0096 =\u0097=\u0098 =\u0099 =\u009a =\u009b =\u009c =\u009d=\u009e =\u009f;' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';'　';'\ufeff';'\r' ;'\t' ;'\n';'\f';'\u000b';́;̀;̆;̂;̌;̊;̍;̈;̋;̃;̇;̄;̷;̧;̨;̣;̲;̅;̉;̎;̏;̐;̑;̒;̓;̔;̕;̖;̗;̘;̙;̚;̛;̜;̝;̞;̟;̠;̡;̢;̤;̥;̦;̩;̪;̫;̬;̭;̮;̯;̰;̱;̳;̴;̵;̶;̸;̹;̺;̻;̼;̽;̾;̿;͂;̈́;ͅ;͠;͡;҃;҄;҅;҆;⃐;⃑;⃒;⃓;⃔;⃕;⃖;⃗;⃘;⃙;⃚;⃛;⃜;⃝;⃞;⃟;⃠;⃡,'-';\u00ad;‐;‑;‒;–;—;―;−<'_'<¯<','<';'<':'<'!'<¡<'?'<¿<'/'<'.'<´<'`'<'^'<¨<'~'<·<¸<'''<'\"'<«<»<'('<')'<'['<']'<'{'<'}'<§<¶<©<®<'@'<¤<฿<¢<₡<₢<'$'<₫<€<₣<₤<₥<₦<₧<£<₨<₪<₩<¥<'*'<'\\'<'&'<'#'<'%'<'+'<±<÷<×<'<'<'='<'>'<¬<'|'<¦<°<µ<0<1<2<3<4<5<6<7<8<9<¼<½<¾<a,A<b,B<c,C<d,D<ð,Ð<e,E<f,F<g,G<h,H<i,I<j,J<k,K<l,L<m,M<n,N<o,O<p,P<q,Q<r,R<s, S & SS,ß<t,T& TH, Þ &TH, þ <u,U<v,V<w,W<x,X<y,Y<z,Z&AE,Æ&AE,æ&OE,Œ&OE,œ" + var3);
        // To implement required rule we have to change a,A < b,B <... sequence to a,A < '''a,'''A < b,B < '''b,'''B < ...
        return createRuleBaseCollator("='\u200b'=\u200c=\u200d=\u200e=\u200f=\u0000 =\u0001 =\u0002 =\u0003 =\u0004=\u0005 =\u0006 =\u0007 =\b ='\t'='\u000b' =\u000e=\u000f ='\u0010' =\u0011 =\u0012 =\u0013=\u0014 =\u0015 =\u0016 =\u0017 =\u0018=\u0019 =\u001a =\u001b =\u001c =\u001d=\u001e =\u001f =\u007f=\u0080 =\u0081 =\u0082 =\u0083 =\u0084 =\u0085=\u0086 =\u0087 =\u0088 =\u0089 =\u008a =\u008b=\u008c =\u008d =\u008e =\u008f =\u0090 =\u0091=\u0092 =\u0093 =\u0094 =\u0095 =\u0096 =\u0097=\u0098 =\u0099 =\u009a =\u009b =\u009c =\u009d=\u009e =\u009f;' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';' ';'　';'\ufeff';'\r' ;'\t' ;'\n';'\f';'\u000b';́;̀;̆;̂;̌;̊;̍;̈;̋;̃;̇;̄;̷;̧;̨;̣;̲;̅;̉;̎;̏;̐;̑;̒;̓;̔;̕;̖;̗;̘;̙;̚;̛;̜;̝;̞;̟;̠;̡;̢;̤;̥;̦;̩;̪;̫;̬;̭;̮;̯;̰;̱;̳;̴;̵;̶;̸;̹;̺;̻;̼;̽;̾;̿;͂;̈́;ͅ;͠;͡;҃;҄;҅;҆;⃐;⃑;⃒;⃓;⃔;⃕;⃖;⃗;⃘;⃙;⃚;⃛;⃜;⃝;⃞;⃟;⃠;⃡,'-';\u00ad;‐;‑;‒;–;—;―;−<'_'<¯<','<';'<':'<'!'<¡<'?'<¿<'/'<'.'<´<'`'<'^'<¨<'~'<·<¸<'''<'\"'<«<»<'('<')'<'['<']'<'{'<'}'<§<¶<©<®<'@'<¤<฿<¢<₡<₢<'$'<₫<€<₣<₤<₥<₦<₧<£<₨<₪<₩<¥<'*'<'\\'<'&'<'#'<'%'<'+'<±<÷<×<'<'<'='<'>'<¬<'|'<¦<°<µ<0<1<2<3<4<5<6<7<8<9<¼<½<¾<a,A<'''a,'''A<b,B<'''b,'''B<c,C<'''c,'''C<d,D<'''d,'''D<ð,Ð<e,E<'''e,'''E<f,F<'''f,'''F<g,G<'''g,'''G<h,H<'''h,'''H<i,I<'''i,'''I<j,J<'''j,'''J<k,K<'''k,'''K<l,L<'''l,'''L<m,M<'''m,'''M<n,N<'''n,'''N<o,O<'''o,'''O<p,P<'''p,'''P<q,Q<'''q,'''Q<r,R<'''r,'''R<s, S & SS,ß<'''s,ß=ss,'''S<t,T& TH, Þ &TH, þ<'''t,'''T<u,U<'''u,'''U<v,V<'''v,'''V<w,W<'''w,'''W<x,X<'''x,'''X<y,Y<'''y,'''Y<z,Z&AE,Æ&AE,æ&OE,Œ&OE,œ<'''z,'''Z");
    }

    /// WORDSJAVA-2383. .NET has a slightly different collation rules for the EN-NZ culture.
    private static Collator createEnNzCollator()
    {
        Collator instance = Collator.getInstance(gStandardCulture);
        if (!(instance instanceof RuleBasedCollator))
            return instance;

        String enNzNetRule = ((RuleBasedCollator) instance).getRules();
        enNzNetRule = enNzNetRule
                .replace("ß<t,T& TH,", "ß<t,T& TH,Ø<t,T& TH,") // make Ø less than t
                .replace("<'''", "").replace("<'_'", ";'''<'_'"); // ignore '
        return createRuleBaseCollator(enNzNetRule);
    }

    private static Collator createRuleBaseCollator(String rule)
    {
        Collator collator;
        try
        {
            collator = new RuleBasedCollator(rule);
        }
        catch (Exception e)
        {
            collator = Collator.getInstance(Locale.ROOT);
            Log.warn("Couldn't create root collator: ", e);
        }

        return collator;
    }

    private static StringBuilder makeMinusRule(List<String> minuses)
    {
        StringBuilder minusesRule = new StringBuilder("<");
        for (int minusIdx = 0; minusIdx < minuses.size(); minusIdx++)
        {
            minusesRule.append(minuses.get(minusIdx));
            if (minusIdx < minuses.size() - 1)
                minusesRule.append(";"); // all minuses are equal in this case
        }
        return minusesRule;
    }

    private static final ConcurrentMap<Locale, SoftReference<Collator>> gStringSortCache
            = new ConcurrentHashMap<Locale, SoftReference<Collator>>();
    private static final ConcurrentMap<Locale, Collator> gIgnoreCaseCollatorCache
            = new ConcurrentHashMap<Locale, Collator>();
    private static final ConcurrentMap<Locale, Collator> gWordSortCollatorCache
            = new ConcurrentHashMap<Locale, Collator>();

    private static final Collator gInvariantCollator;
    private static final Collator gInvariantIgnoreCaseCollator;
    private static final Locale gStandardCulture = new Locale("en", "NZ");

    static
    {
        // invariant collator
        gInvariantCollator = CollatorProvider.createRootCollator();

        // invariant ignore-case collator
        gInvariantIgnoreCaseCollator = CollatorProvider.createRootCollator();
        gInvariantIgnoreCaseCollator.setStrength(Collator.SECONDARY);
    }
}
