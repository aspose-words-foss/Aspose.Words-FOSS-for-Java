package com.aspose.ms.System;

//JAVA has full analog : AutoCloseable interface.
// The AutoCloseable.close() is called automatically when exiting a
// try-with-resources block for which the object has been declared in
// the resource specification header.
//
//

import java.io.IOException;

/**
 * Provides a mechanism for releasing unmanaged resources.
 *
 * JAVA has full analog : AutoCloseable interface. But it defined since 1.7 only.
 *
 * The AutoCloseable.close() is called automatically when exiting a
 * try-with-resources block for which the object has been declared in
 * the resource specification header.
 *
 * todo: Remove this and change the map to AutoCloseable after dropping support of 1.6.
 */
public interface IDisposable
{
    /**
     * Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
     */
    void dispose() throws Exception;
}
