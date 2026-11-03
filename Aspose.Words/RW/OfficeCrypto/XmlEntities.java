// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToCharDictionary;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;


/// <summary>
/// Utility class that converts XML numeric and named character references into corresponding Unicode characters.
/// </summary>
/*static*/ class XmlEntities
{
	/* Simulation of static class by using private constructor */
	private XmlEntities()
	{}


    static String expand(String s)
    {
        if (s == null)
        {
            return null;
        }

        if (s.length() == 0)
        {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length());
        boolean inEntity = false;
        StringBuilder entity = new StringBuilder(10);

        for (int i = 0; i < s.length(); i++)
        {
            if (!inEntity)
            {
                switch (s.charAt(i))
                {
                    case '&':
                        inEntity = true;
                        break;
                    default:
                        sb.append(s.charAt(i));
                        break;
                }
            }
            else
            {
                switch (s.charAt(i))
                {
                    case ';':
                        if (entity.length() == 0)
                        {
                            msStringBuilder.append(sb, "&;");
                        }
                        else
                        {
                            if (entity.charAt(0) == '#')
                            {
                                // It must be a numeric character reference.
                                try
                                {
                                    if ((entity.charAt(1) == 'x') || (entity.charAt(1) == 'X'))
                                    {
                                        // It must be a hex number.
                                        String hex = entity.substring((2), (2) + (entity.length() - 2));
                                        int code = FormatterPal.parseHex(hex);
                                        sb.append((char)code);
                                    }
                                    else
                                    {
                                        // It must be a decimal number.
                                        String dec = entity.substring((1), (1) + (entity.length() - 1));
                                        int code = FormatterPal.parseInt(dec);
                                        sb.append((char)code);
                                    }
                                }
                                catch(Exception e)
                                {
                                    // This is executed in number cannot be parsed, just leave as is.
                                    msStringBuilder.append(sb.append('&'), entity).append(';');
                                }
                            }
                            else
                            {
                                // named entity?
                                char entityChar = G_NAMED_ENTITIES.get(entity.toString());
                                if (StringToCharDictionary.isNullSubstitute(entityChar))
                                {
                                    // nope
                                    msStringBuilder.append(sb.append('&'), entity).append(';');
                                }
                                else
                                {
                                    // we found one
                                    sb.append(entityChar);
                                }
                            }
                            entity.delete((0), (0) + (entity.length()));
                        }
                        inEntity = false;
                        break;

                    case '&':
                        // new entity start without end, it was not an entity...
                        msStringBuilder.append(sb.append('&'), entity);
                        entity.setLength(0);
                        break;

                    default:
                        entity.append(s.charAt(i));
                        final int MAX_ENTITY_SIZE = 8 + 1; // we add the # char
                        if (entity.length() > MAX_ENTITY_SIZE)
                        {
                            // unknown stuff, just don't touch it
                            inEntity = false;
                            msStringBuilder.append(sb.append('&'), entity);
                            entity.setLength(0);
                        }
                        break;
                }
            }
        }

        // finish the work
        if (inEntity)
        {
            msStringBuilder.append(sb.append('&'), entity);
        }

        return sb.toString();
    }

    private static /*final*/ StringToCharDictionary G_NAMED_ENTITIES;
    static/* XmlEntities()*/
    {
        G_NAMED_ENTITIES = new StringToCharDictionary();
        G_NAMED_ENTITIES.add("quot", '\"');
        G_NAMED_ENTITIES.add("amp", '\u0026');
        G_NAMED_ENTITIES.add("apos", '\'');
        G_NAMED_ENTITIES.add("lt", '\u003C');
        G_NAMED_ENTITIES.add("gt", '\u003E');
    }
}

