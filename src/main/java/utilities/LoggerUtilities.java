package utilities;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtilities {

    private LoggerUtilities(){

    }

    private static final Logger LOG = LoggerFactory.getLogger(LoggerUtilities.class);

    public static void addLog(final Exception exception, final String methodName){
        LOG.error("Error in method {}", methodName);
        LOG.error(ExceptionUtils.getStackTrace(exception));
    }

    public static void addInfoLog(final String info)
    {
        LOG.info(info);
    }

    public static void addErrorLog(final String error){
        LOG.error(error);
    }
}
