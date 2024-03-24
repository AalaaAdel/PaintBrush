/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Aalaa Adel
 */


package paint_brush_project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Shape {

    public int x1, x2, y1, y2;// initialiaze the variables of any shape 
    public Color c; // c variable to set the color   
    public boolean isFilled;
    public boolean isDotted;
    public boolean isVisible = true;
    public int eraserSize;

    public abstract void draw(Graphics2D g2d); // Function that will draw the graphics

    //default constructor
    public Shape() {
    }

    //overloaded constructor
    public Shape(int _x1, int _y1, int _x2, int _y2, Color _c) {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
        c = _c;
    }

    //used in mouse drag to change x2 and y2
    public void changexy(int x, int y) {
    }

    //used in mouse drag to add new points to array
    public void addPoint(int x, int y) {
    }

    //constant variables
    public static final int FREE_DRAW = 0;
    public static final int LINE = 1;
    public static final int RECTANGLE = 2;
    public static final int OVAL = 3;
    public static final int ERASER = 4;
    public static final int CLEAR = 5;
    public static final int UNDO = 6;
    public static final int REDO = 7;

}

class Line extends Shape {

    public Line(int _x1, int _y1, int _x2, int _y2, Color _c, boolean _isDotted) {
        //overloaded constructor of the parent
        super(_x1, _y1, _x2, _y2, _c);
        isDotted = _isDotted;
    }

    @Override
    public void changexy(int x, int y) {
        x2 = x;
        y2 = y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}

class Rect extends Shape {

    private int smaller_x;
    private int smaller_y;
    private int width;
    private int height;

    public Rect(int _x1, int _y1, int _x2, int _y2, Color _c, boolean _isFilled, boolean _isDotted) {
        super(_x1, _y1, _x2, _y2, _c);
        isFilled = _isFilled;
        isDotted = _isDotted;
        //extract the upper left point coordinates
        //get smaller x (closer to origin):
        smaller_x = Math.min(_x1, _x2);
        //get smaller y (closer to origin):
        smaller_y = Math.min(_y1, _y2);
        //get distance between two points on the x axis
        width = Math.abs(_x2 - _x1);
        //get distance between two points on the y axis
        height = Math.abs(_y2 - _y1);
    }

    @Override
    public void changexy(int x, int y) {
        x2 = x;
        y2 = y;
        smaller_x = Math.min(x1, x2);
        smaller_y = Math.min(y1, y2);
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            //g2d.setColor(c);
            if (isFilled) {
                g2d.fillRect(smaller_x, smaller_y, width, height);
            } else {
                g2d.drawRect(smaller_x, smaller_y, width, height);
            }
        }
    }
}

class Oval extends Shape {

    private int smaller_x;
    private int smaller_y;
    private int width;
    private int height;

    public Oval(int _x1, int _y1, int _x2, int _y2, Color _c, boolean _isFilled, boolean _isDotted) {
        super(_x1, _y1, _x2, _y2, _c);
        isFilled = _isFilled;
        isDotted = _isDotted;
        smaller_x = Math.min(_x1, _x2);
        smaller_y = Math.min(_y1, _y2);
        width = Math.abs(_x2 - _x1);
        height = Math.abs(_y2 - _y1);
    }

    @Override
    public void changexy(int x, int y) {
        x2 = x;
        y2 = y;
        smaller_x = Math.min(x1, x2);
        smaller_y = Math.min(y1, y2);
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            //g2d.setColor(c);
            if (isFilled) {
                g2d.fillOval(smaller_x, smaller_y, width, height);
            } else {
                g2d.drawOval(smaller_x, smaller_y, width, height);
            }
        }
    }
}

class Eraser extends Shape {

    public ArrayList<Integer> xArray;
    public ArrayList<Integer> yArray;

    public Eraser(int _x1, int _y1, int _eraserSize) {
        xArray = new ArrayList<>();
        yArray = new ArrayList<>();
        xArray.add(_x1);
        yArray.add(_y1);
        eraserSize = _eraserSize;
    }

    @Override
    public void addPoint(int x, int y) {
        xArray.add(x);
        yArray.add(y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            for (int i = 0; i <= xArray.size() - 1; i++) {
                // clear a square area of size eraserSize*eraserSize cenetered around the mouse pointer
                g2d.clearRect(xArray.get(i) - eraserSize / 2, yArray.get(i) - eraserSize / 2, eraserSize, eraserSize);
            }
        }
    }
}

class FreeDraw extends Shape {

    public ArrayList<Integer> xArray;
    public ArrayList<Integer> yArray;

    public FreeDraw(int _x1, int _y1, Color _c) {
        xArray = new ArrayList<>();
        yArray = new ArrayList<>();
        xArray.add(_x1);
        yArray.add(_y1);
        c = _c;
    }

    @Override
    public void addPoint(int x, int y) {
        xArray.add(x);
        yArray.add(y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            for (int i = 1; i <= xArray.size() - 1; i++) {
                g2d.drawLine(xArray.get(i - 1), yArray.get(i - 1), xArray.get(i), yArray.get(i));
            }
            //if only one point is in the array -> draw a point
            if (xArray.size() == 1) {
                g2d.drawLine(xArray.get(0), yArray.get(0), xArray.get(0), yArray.get(0));
            }
        }
    }
}

class Clear extends Shape {

    public Clear(int w, int h) {
        x1 = w;
        y1 = h;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible) {
            g2d.clearRect(0, 0, x1, y1);
        }
    }
}
