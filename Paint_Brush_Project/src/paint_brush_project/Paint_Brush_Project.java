/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Aalaa Adel
 */

package paint_brush_project;

import javax.swing.JFrame;

//aalaa adel & yomna sayed --> QA track intake 44
public class Paint_Brush_Project {

    public static void main(String[] args) {
        // create a frame 
        JFrame f = new JFrame();
        //set the size
        f.setSize(1000, 800);
        //object mn mypanel (segada)
        MyPanel mp = new MyPanel();
        //B7ot el segada 3al frame
        f.setContentPane(mp);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
