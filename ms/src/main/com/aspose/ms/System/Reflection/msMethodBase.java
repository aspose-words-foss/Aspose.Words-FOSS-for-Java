// 30/09/2014 by Konstantin Sidorenko

package com.aspose.ms.System.Reflection;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * Extension methods for porting System.Reflection.MethodBase.
 */
public class msMethodBase
{
    public static boolean isSealed(Member member)
    {
        return Modifier.isFinal(member.getModifiers());
    }

    public static boolean isStatic(Member member)
    {
        return Modifier.isStatic(member.getModifiers());
    }

    public static boolean isPublic(Member member)
    {
        return Modifier.isPublic(member.getModifiers());
    }
}
