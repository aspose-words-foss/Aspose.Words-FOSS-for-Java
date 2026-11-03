//31.03.09 by Konstantin Sidorenko

package com.aspose.words;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used by Aspose.JavaDoc.
 * Autoporter annotates by this tag public members that should not be in public API but they is.
 * Aspose.JavaDoc makes summary for such members with text "Reserved for internal use." for clarity.
 * Placed in public api with purpose that JavaDoc can access it.
 */
@Retention(RetentionPolicy.RUNTIME)//RUNTIME retention needed so JavaSpy can get this annotation via reflection. 
public @interface ReservedForInternalUse
{
	public boolean isExplicitInerfaceImplementation() default false;
}
