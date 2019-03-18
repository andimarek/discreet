import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class ReadFile {


    public static String readUtf8File(Path path) {
        requireNonNull(path);
        try {
            return new String(Files.readAllBytes(path), "UTF-8");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readUtf8File(File file) {
        requireNonNull(file);
        try {
            return new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}