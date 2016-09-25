package alexeychurchill.github.io.bresenhamlines.graphics.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Primitive;

/**
 * Parses Drawings from file
 */

public class FileParser {
    private File file;

    public FileParser(String filename) {
        this.file = new File(filename);
    }

    public FileParser(File file) {
        this.file = file;
    }

    public Drawing parse() {
        if (file == null) {
            return null;
        }
        Drawing drawing = new Drawing();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String source = scanner.nextLine();
                Primitive parsedPrimitive = DrawingParser.parsePrimitive(source);
                if (parsedPrimitive != null) {
                    drawing.getPrimitives().add(parsedPrimitive);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return drawing;
    }
}
