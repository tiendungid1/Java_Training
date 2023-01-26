package rxn.jdbc.common.util.logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
  private static final String LOG_FILE = Paths.get("logs/app.log").toAbsolutePath().toString();

  private FileLogger() {
    throw new AssertionError();
  }

  public static Logger getLogger(String className) throws SecurityException, IOException {
    Logger logger = Logger.getLogger(className);

    // Prevent log to console
    logger.setUseParentHandlers(false);

    logger.setLevel(Level.ALL);

    FileHandler fileHandler = new FileHandler(LOG_FILE);
    logger.addHandler(fileHandler);
    SimpleFormatter formatter = new SimpleFormatter();
    fileHandler.setFormatter(formatter);

    return logger;
  }
}
