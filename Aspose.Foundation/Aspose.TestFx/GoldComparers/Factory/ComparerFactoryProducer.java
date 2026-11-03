// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/*static*/ class ComparerFactoryProducer
{
	/* Simulation of static class by using private constructor */
	private ComparerFactoryProducer()
	{}

    private static /*final*/ AbstractComparerFactory G_WIN_FACTORY = new WindowsComparersFactory();
    private static /*final*/ AbstractComparerFactory G_REMOTE_FACTORY = new RemoteComparersFactory();

    public static AbstractComparerFactory getFactory() throws Exception
    {
        // Use remote comparer for Unix like environments (Mac, Android, iOS, Ubuntu etc).
        return PlatformUtilPal.isUnixLike() ? G_REMOTE_FACTORY : G_WIN_FACTORY;
    }
}

