package alexeychurchill.github.io.bresenhamlines.graphics.parsers;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.ClosedPolyline;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Polyline;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Primitive;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.SingleLine;

/**
 * Drawings parser
 * Allows to parse drawings from string
 */

public class DrawingParser {
    public static final String SINGLE_LINE = "SINGLE_LINE";
    public static final String POLYLINE = "POLYLINE";
    public static final String CLOSED_POLYLINE = "CLOSED_POLYLINE";
    public static final String POINT = "POINT";
    public static final String LOG = "DrawingParser";

    public static Drawing parse(String source) {
        Drawing drawing = new Drawing();
        Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            Primitive primitive = parsePrimitive(scanner.nextLine());
            if (primitive != null) {
                drawing.getPrimitives().add(primitive);
            }
        }
        return drawing;
    }

    public static Primitive parsePrimitive(String source) {
        Primitive primitive = null;
        Scanner scanner = new Scanner(source);
        //Primitive type
        if (!scanner.hasNext("([a-zA-Z]|_)+")) {
            return null;
        }
        String primitiveType = scanner.next("([a-zA-Z]|_)+");
        switch (primitiveType.toUpperCase()) {
            case CLOSED_POLYLINE:
                primitive = scanClosedPolyline(scanner);
                break;
            case POLYLINE:
                primitive = scanPolyline(scanner);
                break;
            case SINGLE_LINE:
                primitive = scanSingleLine(scanner);
                break;
            case POINT:
                primitive = scanPoint(scanner);
                break;
        }
        return primitive;
    }

    private static Primitive scanPoint(Scanner scanner) {
        Point point = new Point();
        int color = scanColor(scanner);
        point.setColor(color);
        if (!scanner.hasNextInt()) { //X
            return null;
        }
        point.setX(scanner.nextInt());
        if (!scanner.hasNextInt()) { //Y
            return null;
        }
        point.setY(scanner.nextInt());
        return point;
    }

    private static Primitive scanSingleLine(Scanner scanner) {
        SingleLine singleLine = new SingleLine();
        int color = scanColor(scanner);
        singleLine.setColor(color);
        if (!scanner.hasNextInt()) { //Xbegin
            return null;
        }
        singleLine.getBegin().setY(scanner.nextInt());
        if (!scanner.hasNextInt()) { //Ybegin
            return null;
        }
        singleLine.getBegin().setY(scanner.nextInt());
        if (!scanner.hasNextInt()) { //Xend
            return null;
        }
        singleLine.getEnd().setX(scanner.nextInt());
        if (!scanner.hasNextInt()) { //Yend
            return null;
        }
        singleLine.getEnd().setY(scanner.nextInt());
        return singleLine;
    }

    private static Primitive scanPolyline(Scanner scanner) {
        Polyline polyline = new Polyline();
        int color = scanColor(scanner);
        polyline.setColor(color);
        List<Point> points = scanPolylinePoints(scanner);
        if (points != null) {
            polyline.getPoints().addAll(points);
        }
        return polyline;
    }

    private static Primitive scanClosedPolyline(Scanner scanner) {
        ClosedPolyline closedPolyline = new ClosedPolyline();
        int color = scanColor(scanner);
        closedPolyline.setColor(color);
        List<Point> points = scanPolylinePoints(scanner);
        if (points != null) {
            closedPolyline.getPoints().addAll(points);
        }
        return closedPolyline;
    }

    private static List<Point> scanPolylinePoints(Scanner scanner) {
        List<Point> points = new ArrayList<>();
        while (scanner.hasNext()) {
            Point point = new Point();
            if (!scanner.hasNextInt()) { //X
                return null;
            }
            point.setX(scanner.nextInt());
            if (!scanner.hasNextInt()) { //Y
                return null;
            }
            point.setY(scanner.nextInt());
            points.add(point);
        }
        return points;
    }

    private static int scanColor(Scanner scanner) {
        int r, g, b;
        if (!scanner.hasNextInt()) { //R
            return 0;
        }
        r = scanner.nextInt();
        if (!scanner.hasNextInt()) { //G
            return 0;
        }
        g = scanner.nextInt();
        if (!scanner.hasNextInt()) { //B
            return 0;
        }
        b = scanner.nextInt();
        return Color.rgb(r, g, b);
    }
}
