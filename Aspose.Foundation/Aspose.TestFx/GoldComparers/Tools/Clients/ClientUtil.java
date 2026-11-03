// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2025 by Alexey Maslov

//
// NOTE This source file is included by link from
// X:/awnet/Aspose.Foundation/Tools/XpsToPng
// X:/awnet/Aspose.Foundation/Tools/WebToPng
//
// DO NOT add using directives for Aspose namespaces
//

package com.aspose;

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.StringComparison;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;


/// <summary>
/// Implements client logic for posting a job to server.
/// This class is used by server as well since server can act as client.
/// </summary>
/*static*/ class ClientUtil
{
	/* Simulation of static class by using private constructor */
	private ClientUtil()
	{}

    /// <summary>
    /// Implements client logic for posting a job to server
    /// </summary>
    /// <param name="mutex">Server mutex name</param>
    /// <param name="pipe">Server pipe name</param>
    /// <param name="serverPath">Deafult path to server executable</param>
    /// <param name="envVariable">Name of environment variable used to get server path</param>
    /// <param name="parameters">Parameters passed to the server</param>
    /// <returns>Server command output</returns>
    static String postServerJobGetResults(String mutex, String pipe, String serverPath, String envVariable, String[] parameters) throws Exception
    {
        ensureServerRunning(mutex, (System.getenv(envVariable) != null ? System.getenv(envVariable) : serverPath));

        // Send request and get unique pipe name in response where to get results later
        // Use short timeout since it takes little time for the server to schedule the job
        StringBuilder sb = new StringBuilder();
        for (String p : parameters)
        {
            if (msString.isNullOrEmpty(p))
                continue;
            if (sb.length() > 0)
                sb.append(' ');
            msStringBuilder.append(sb, "\"" + fixSlashAtEnd(p) + "\"");
        }

        String result = pipeChat(pipe, sb.toString(), 10);
        Thread.sleep(2000);

        // If server already knows there is an error it will tell us straight away
        // This typically happens where passed arguments are invalid
        if (msString.isNullOrWhiteSpace(result))
            throw new Exception("server replied with empty response");  // we waited for pipe name, but nothing came back
        if (!Character.isLetter(result.charAt(0)))  // if there is an error the results will start with non-letter (by design)
            throw new Exception("server replied with error --->\r\n"+ result.substring(1));

        // Now wait for a response over the dedicated pipe
        // Set longer timeout to allow time for the server to generate results
        result = pipeChat(result, null, 60);

        if (!msString.isNullOrEmpty(result) && msString.startsWith(result, "$", StringComparison.ORDINAL_IGNORE_CASE))
            throw new Exception("server replied with error --->\r\n"+ result.substring(1));

        return result;
    }

    static String[] postServerJobGetResults(String mutex, String pipe, String serverPath, String envVariable, String[][] parameters) throws Exception
    {
        if (parameters.length <= 0)
            throw new IllegalArgumentException("parameters");

        if (parameters.length == 1)
            return new String[] { postServerJobGetResults(mutex, pipe, serverPath, envVariable, parameters[0]) };

        ExecutorService executor = Executors.newFixedThreadPool(parameters.length);
        Map<Integer, Object> results = new ConcurrentHashMap<>();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++)
        {
            final int index = i;
            futures.add(executor.submit(() ->
            {
                try
                {
                    String result = postServerJobGetResults(mutex, pipe, serverPath, envVariable, parameters[index]);
                    results.put(index, result);
                }
                catch (Exception e)
                {
                    results.put(index, e);
                }
            }));
        }

        for (Future<?> future : futures) future.get();
        executor.shutdown();

        List<Exception> exceptions = new ArrayList<>();
        String[] output = new String[parameters.length];
        for (Map.Entry<Integer, Object> entry : results.entrySet())
        {
            if (entry.getValue() instanceof Exception)
            {
                exceptions.add((Exception) entry.getValue());
            } else
            {
                output[entry.getKey()] = (String) entry.getValue();
            }
        }

        if (!exceptions.isEmpty())
            throw new AggregateException(exceptions);

        return output;
    }

    /// <summary>
    /// If I want to pass \ at the end of the path with spaces inside then I need quotes around it
    /// but the windows command line parser will treat it as if I wanted to escape the quote at the end
    /// yet I wanted to include \ at the end and keep the quotes. ugly windows.
    /// </summary>
    private static String fixSlashAtEnd(String v)
    {
        return (msString.endsWith(v, "\\", StringComparison.ORDINAL_IGNORE_CASE) == true) ? v + '\\' : v;
    }

    private static void ensureServerRunning(String mutexName, String serverPath)
    {
        for (int i = 0; i < MAX_ATTEMPTS; i++)
        {
            WinNT.HANDLE mutex = null;
            try
            {
                mutex = Kernel32.INSTANCE.CreateMutex(
                        null,
                        true,
                        mutexName
                );

                int lastError = Kernel32.INSTANCE.GetLastError();

                if (lastError == ALREADY_EXISTS_ERRCODE)
                    break;

                if (mutex != null && !WinNT.INVALID_HANDLE_VALUE.equals(mutex))
                    Kernel32.INSTANCE.ReleaseMutex(mutex);

                new ProcessBuilder()
                        .command(serverPath, "-s")
                        .redirectError(ProcessBuilder.Redirect.to(new File("NUL")))
                        .redirectOutput(ProcessBuilder.Redirect.to(new File("NUL")))
                        .start();

                break;

            }
            catch (IOException e)
            {
                if (i == MAX_ATTEMPTS - 1)
                {
                    throw new RuntimeException("Server start failed after " + MAX_ATTEMPTS + " attempts", e);
                }

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt();
                }
            }
            finally
            {
                if (mutex != null && !WinNT.INVALID_HANDLE_VALUE.equals(mutex))
                {
                    Kernel32.INSTANCE.CloseHandle(mutex);
                }
            }
        }
    }

    public static String pipeChat(String serverPath, String request) throws IOException
    {
        return pipeChat(serverPath, request, 10);
    }

    public static String pipeChat(String pipeName, String request, int timeoutSeconds) throws IOException
    {
        // Channel direction.
        int desiredAccess = (request != null)
                ? WinNT.GENERIC_READ | WinNT.GENERIC_WRITE
                : WinNT.GENERIC_READ;

        String fullPipeName = "\\\\.\\pipe\\" + pipeName;
        WinNT.HANDLE hPipe = null;

        try
        {
            if (desiredAccess != WinNT.GENERIC_READ)
            {
                for (int i = 0; i < MAX_ATTEMPTS; i++)
                {
                    if (!Kernel32.INSTANCE.WaitNamedPipe(fullPipeName, timeoutSeconds * 1000))
                    {
                        if(i == MAX_ATTEMPTS - 1)
                            throw new IOException("Channel timeout error: " + Kernel32.INSTANCE.GetLastError());
                        else
                            Thread.sleep(1000);
                    }
                    else
                        break;
                }
            }

            for(int i = 0; i < MAX_ATTEMPTS; i++)
            {
                // Channel connection
                hPipe = Kernel32.INSTANCE.CreateFile(
                        fullPipeName,
                        desiredAccess,
                        0,
                        null,
                        WinNT.OPEN_EXISTING,
                        0,
                        null
                );

                if (hPipe != WinBase.INVALID_HANDLE_VALUE)
                {
                    break;
                }
                else
                {
                    if(i == MAX_ATTEMPTS - 1)
                        throw new IOException("Can't connect to the channel " + pipeName);
                    else
                        Thread.sleep(1000);
                }
            }

            if(request != null)
                writePipeRequest(hPipe, request);

            return readPipeResponce(hPipe);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally
        {
            if (hPipe != null && !hPipe.equals(WinBase.INVALID_HANDLE_VALUE))
                Kernel32.INSTANCE.CloseHandle(hPipe);
        }
    }

    private static void writePipeRequest(WinNT.HANDLE hPipe, String request) throws IOException
    {
        byte[] data = (request + "\n").getBytes(StandardCharsets.UTF_8);
        IntByReference bytesWritten = new IntByReference();

        if (!Kernel32.INSTANCE.WriteFile(
                hPipe,
                data,
                data.length,
                bytesWritten,
                null))
        {
            throw new IOException("Channel write error");
        }
    }

    private static String readPipeResponce(WinNT.HANDLE hPipe) throws IOException
    {
        StringBuilder response = new StringBuilder();
        byte[] buffer = new byte[2048];
        IntByReference bytesRead = new IntByReference();

        while (true)
        {
            if (!Kernel32.INSTANCE.ReadFile(
                    hPipe,
                    buffer,
                    buffer.length,
                    bytesRead,
                    null))
            {
                throw new IOException("Channel read error: " + Kernel32.INSTANCE.GetLastError());
            }

            String chunk = new String(
                    buffer,
                    0,
                    bytesRead.getValue(),
                    StandardCharsets.UTF_8
            );

            // Java reads bytes woth BOM symbol. Remove it.
            if(chunk.charAt(0) == '\ufeff')
                chunk = chunk.substring(1);

            response.append(chunk);

            if (chunk.contains("\n"))
            {
                break;
            }
        }

        return response.toString().trim();
    }

    public static class AggregateException extends Exception
    {
        private final List<Exception> exceptions;

        public AggregateException(List<Exception> exceptions)
        {
            super("Multiple exceptions occurred");
            this.exceptions = exceptions;
        }

        public List<Exception> getExceptions()
        {
            return exceptions;
        }
    }

    private static final int MAX_ATTEMPTS = 10;
    private static final int ALREADY_EXISTS_ERRCODE = 183;

}

