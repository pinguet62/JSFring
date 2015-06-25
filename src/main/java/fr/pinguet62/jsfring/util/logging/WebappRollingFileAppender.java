package fr.pinguet62.jsfring.util.logging;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

/**
 * A {@link FileAppender} used to create file into web-app, in deploy folder.
 * <p>
 * It's a copy/paste of {@link RollingFileAppender}.
 */
@Plugin(name = "WebappRollingFile", category = "Core", elementType = "appender", printObject = true)
public final class WebappRollingFileAppender extends
        AbstractOutputStreamAppender<RollingFileManager> {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final long serialVersionUID = 1;

    private final String fileName;
    private final String filePattern;
    private Object advertisement;
    private final Advertiser advertiser;

    /**
     * Calculate the absolute path of target file.<br>
     * The absolute path of webapp is calculated from the {@link URL} of a
     * resource into the application.
     * 
     * @param filename The filename.
     * @return The absolute file path.
     * @see Class#getResource(String)
     */
    private static String getFilePath(String filename) {
        File classesFolder;
        try {
            classesFolder = new File(WebappRollingFileAppender.class
                    .getResource("/").toURI());
        } catch (URISyntaxException e) {
            LOGGER.error(e);
            return null;
        }
        File webinfFolder = classesFolder.getParentFile();
        File webappFolder = webinfFolder.getParentFile();
        return new File(webappFolder, filename).toString();
    }

    @Override
    public void stop() {
        super.stop();
        if (advertiser != null) {
            advertiser.unadvertise(advertisement);
        }
    }

    @Override
    public void append(final LogEvent event) {
        getManager().checkRollover(event);
        super.append(event);
    }

    private WebappRollingFileAppender(String name,
            Layout<? extends Serializable> layout, Filter filter,
            RollingFileManager manager, String fileName, String filePattern,
            boolean ignoreExceptions, boolean immediateFlush,
            Advertiser advertiser) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        if (advertiser != null) {
            final Map<String, String> configuration = new HashMap<String, String>(
                    layout.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", name);
            advertisement = advertiser.advertise(configuration);
        }
        this.fileName = fileName;
        this.filePattern = filePattern;
        this.advertiser = advertiser;
    }

    public String getFilePattern() {
        return filePattern;
    }

    /**
     * Create the {@link WebappRollingFileAppender}.
     * <p>
     * It's a copy/paste of {@link RollingFileAppender}, but with override of
     * filenames.
     * 
     * @param fileName The filename of the file that is actively written to.
     *            (required).<br>
     *            It must be an relative path, relatively to base folder of the
     *            application.
     * @param filePattern The pattern of the file name to use on rollover.
     *            (required).<br>
     *            It must be an relative path, relatively to base folder of the
     *            application.
     * @see #getFilePath(String)
     */
    @PluginFactory
    public static WebappRollingFileAppender createAppender(
            @PluginAttribute("fileName") String fileName,
            @PluginAttribute("filePattern") String filePattern,
            @PluginAttribute("append") String append,
            @PluginAttribute("name") String name,
            @PluginAttribute("bufferedIO") String bufferedIO,
            @PluginAttribute("bufferSize") String bufferSizeStr,
            @PluginAttribute("immediateFlush") String immediateFlush,
            @PluginElement("Policy") TriggeringPolicy policy,
            @PluginElement("Strategy") RolloverStrategy strategy,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") Filter filter,
            @PluginAttribute("ignoreExceptions") String ignore,
            @PluginAttribute("advertise") String advertise,
            @PluginAttribute("advertiseURI") String advertiseURI,
            @PluginConfiguration Configuration config) {
        boolean isAppend = Booleans.parseBoolean(append, true);
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        boolean isBuffered = Booleans.parseBoolean(bufferedIO, true);
        boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
        boolean isAdvertise = Boolean.parseBoolean(advertise);
        int bufferSize = Integers.parseInt(bufferSizeStr, DEFAULT_BUFFER_SIZE);

        if (!isBuffered && bufferSize > 0) {
            LOGGER.warn(
                    "The bufferSize is set to {} but bufferedIO is not true: {}",
                    bufferSize, bufferedIO);
        }
        if (name == null) {
            LOGGER.error("No name provided for FileAppender");
            return null;
        }

        if (fileName == null) {
            LOGGER.error("No filename was provided for FileAppender with name "
                    + name);
            return null;
        }
        fileName = getFilePath(fileName);

        if (filePattern == null) {
            LOGGER.error("No filename pattern provided for FileAppender with name "
                    + name);
            return null;
        }
        filePattern = getFilePath(filePattern);

        if (policy == null) {
            LOGGER.error("A TriggeringPolicy must be provided");
            return null;
        }

        if (strategy == null) {
            strategy = DefaultRolloverStrategy.createStrategy(null, null, null,
                    String.valueOf(Deflater.DEFAULT_COMPRESSION), config);
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        RollingFileManager manager = RollingFileManager.getFileManager(
                fileName, filePattern, isAppend, isBuffered, policy, strategy,
                advertiseURI, layout, bufferSize);
        if (manager == null) {
            return null;
        }

        return new WebappRollingFileAppender(name, layout, filter, manager,
                fileName, filePattern, ignoreExceptions, isFlush,
                isAdvertise ? config.getAdvertiser() : null);
    }

    public String getFilename() {
        return fileName;
    }

}