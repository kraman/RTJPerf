/*-------------------------------------------------------------------------*
 * $Id: RTJPerfArgs.java,v 1.2 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.util;

/**
 * This is an utility class that contains a list of the command line
 * argument that are used by RTJPerf benchmark applications.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class RTJPerfArgs {

    public static String THREAD_BOUND_OPT = "--threadBoundHandler";
    public static int   THREAD_BOUND_OPT_CODE = 0x0001;
    
    public static String NO_HEAP_OPT = "--noHeap";
    public static int   NO_HEAP_OPT_CODE = 0x0002;
    
    public static String HANDLER_PRIORITY_OPT = "--handlerPriority";
    public static int   HANDLER_PRIORITY_OPT_CODE = 0x0004;
    
    public static String FIRE_COUNT_OPT = "--fireCount";
    public static int FIRE_COUNT_OPT_CODE = 0x0008;
    
    public static String MEMORY_AREA_OPT = "--memoryArea";
    public static int   MEMORY_AREA_OPT_CODE = 0x0010;
    
    public static String OUT_DIR_OPT = "--outDir";
    public static int    OUT_DIR_OPT_CODE = 0x0020;
    
    public static String HELP_OPT = "--help";
    public static  int HELP_OPT_CODE = 0x0040;
    
    public static String MEM_PARAMS_OPTION = "--memParams";
    public static int    MEM_PARAMS_OPTION_CODE = 0x0080;
    
    public static String MEM_PROFILE_OPT = "--memProfiling";
    public static int MEM_PROFILE_OPT_CODE = 0x0100;

    public static String BACKGROUND_THREAD_NUMBER_OPT = "--backgroundThreadNumber";
    public static int  BACKGROUND_THREAD_NUMBER_OPT_CODE = 0x0200;

    public static String BACKGROUND_THREAD_PRIORITY_OPT = "--backgroundThreadPriority";
    public static int  BACKGROUND_THREAD_PRIORITY_OPT_CODE = 0x0400;

    public static String BACKGROUND_THREAD_TYPE_OPT = "--backgroundThreadType";
    public static int  BACKGROUND_THREAD_TYPE_OPT_CODE = 0x0800;

    public static String LP_ASYNC_HANDLER_NUMBER_OPT = "--lpAsyncHandlerNumber";
    public static int LP_ASYNC_HANDLER_NUMBER_OPT_CODE =  0x1000;

    public static String LP_ASYNC_HANDLER_PRIORITY_OPT = "--lpAsyncHandlerPriority";
    public static int  LP_ASYNC_HANDLER_PRIORITY_OPT_CODE =  0x2000;
    
}
