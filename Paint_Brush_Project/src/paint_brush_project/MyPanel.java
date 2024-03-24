/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package paint_brush_project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

    int x1, x2, y1, y2;

    // declare shapes buttons
    public JButton lineButton, freeDrawButton, rectButton, ovalButton, eraserButton, clearButton, undoButton, redoButton;

    //current color variable
    public Color currentColor = Color.BLACK; // Default color for ink
    public JComboBox colorsCB;
    public String colorList[] = {"Black", "White", "Blue", "Red", "Green", "Yellow"};
    public Color colorList_colors[] = {Color.BLACK, Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};


    // current eraser size variable
    public int eraserSize = 10;
    public JComboBox eraserSizeCB;
    public String eraserSizeList[] = {"Small", "Medium", "Large"};
    public int eraserSizeList_int[] = {10, 20, 40};

    // current shape variable
    public int currentShape;
    //check boxes
    JCheckBox isDottedCheckbox, isFilledCheckbox;
    boolean isFilled = false;
    boolean isDotted = false;
    
    // Declare array to Store the drawn shapes
    public ArrayList<Shape> shapes;

    //Constructor
    public MyPanel() {
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        //dynamic allocation for the shapes array (list)
        shapes = new ArrayList<>();

////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////Buttons, Combo boxes & Check boxes//////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
        //combobox(drop down list) for colors
        colorsCB = new JComboBox(colorList);
        colorsCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = colorsCB.getSelectedIndex();
                currentColor = colorList_colors[i];
            }
        });
        this.add(colorsCB);

        freeDrawButton = new JButton("Free Draw");
        freeDrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.FREE_DRAW;
            }
        });
        this.add(freeDrawButton);

        //line button 
        lineButton = new JButton("Line");
        //annonymous inner class which implements from actionlistener
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.LINE;
            }
        });
        this.add(lineButton);//this is mypanel and line Button is the button on mypanel

        rectButton = new JButton("Rectangle");
        rectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.RECTANGLE;
            }
        });
        this.add(rectButton);

        ovalButton = new JButton("Oval");
        ovalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.OVAL;
            }
        });
        this.add(ovalButton);

        isDottedCheckbox = new JCheckBox("Dotted");
        isDottedCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDotted = isDottedCheckbox.isSelected();
            }
        });
        this.add(isDottedCheckbox);
        
        isFilledCheckbox = new JCheckBox("Filled");
        isFilledCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFilled = isFilledCheckbox.isSelected();
            }
        });
        this.add(isFilledCheckbox);

        eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.ERASER;
            }
        });
        this.add(eraserButton);

        //combobox(drop down list) for Eraser Size
        eraserSizeCB = new JComboBox(eraserSizeList);
        eraserSizeCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = eraserSizeCB.getSelectedIndex();
                eraserSize = eraserSizeList_int[i];
            }
        });
        this.add(eraserSizeCB);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.CLEAR;
                //no need for a mouse event for clear
                shapes.add(new Clear(10000, 10000));
                repaint();
            }
        });
        this.add(clearButton);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.UNDO;
                for (int x = shapes.size() - 1; x >= 0; x--) {
                    if (shapes.get(x).isVisible) {
                        shapes.get(x).isVisible = false;
                        break;
                    }
                }
                repaint();
            }
        });
        this.add(undoButton);

        redoButton = new JButton("Redo");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShape = Shape.REDO;
                if (shapes.getLast().isVisible == false) {
                    int x;
                    for (x = shapes.size() - 2; x >= 0; x--) {
                        if (shapes.get(x).isVisible == true) {
                            break;
                        }
                    }
                    shapes.get(x + 1).isVisible = true;
                }

                repaint();
            }
        });
        this.add(redoButton);

////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
//////////////////////////Action Listeners//////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("CLICKED");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                switch (currentShape){
                    case Shape.LINE:
                        shapes.add(new Line(x1, y1, x1, y1, currentColor, isDotted));
                        break;
                    case Shape.RECTANGLE:
                        shapes.add(new Rect(x1, y1, x1, y1, currentColor, isFilled, isDotted));
                        break;
                    case Shape.OVAL:
                        shapes.add(new Oval(x1, y1, x1, y1, currentColor, isFilled, isDotted));
                        break;
                    case Shape.FREE_DRAW:
                        shapes.add(new FreeDraw(x1, y1, currentColor));
                        break;
                    case Shape.ERASER:
                        shapes.add(new Eraser(x1, y1, eraserSize));
                        break;
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("RELEASED");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println("ENTERED");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //System.out.println("EXITED");
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                   
                switch (currentShape){
                    case Shape.LINE:
                        shapes.getLast().changexy(x2, y2);
                        break;
                    case Shape.RECTANGLE:
                        shapes.getLast().changexy(x2, y2);
                        break;
                    case Shape.OVAL:
                        shapes.getLast().changexy(x2, y2);
                        break;
                    case Shape.FREE_DRAW:
                        shapes.getLast().addPoint(x2, y2);
                        break;
                    case Shape.ERASER:
                        shapes.getLast().addPoint(x2, y2);
                        break;
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("MOVED");
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw all stored shapes
        // loop on the shapes list
        for (Shape shape : shapes) {
            // Set the color
            g2d.setColor(shape.c);
            if (shape.isDotted == false) {
                g2d.setStroke(new BasicStroke(2)); // Solid line
            } else {
                float[] dashPattern = {5, 5}; // dash pattern, adjust as needed
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
            }
            shape.draw(g2d);
        }
        //revert the graphics to solid to fix buttons becoming dotted
        g2d.setStroke(new BasicStroke(2)); 
    }

}
