/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1_camargo_marquez;

import java.util.Scanner;

/**
 *
 * @author Henry
 */
public class Lab1_camargo_marquez {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Enter Regex: ");
        String regex = sc.next();
        SyntaxTree st = new SyntaxTree(regex);
        printTree(st.getRoot());*/
        /*ViewFrame mainWindow = new ViewFrame();
        mainWindow.setVisible(true);*/
        ViewFrame.main();
        //(1|2)*34?562+1+
    }
    
    public static void printTree(Node root){
        if(root != null){
            //System.out.println("Left");
            System.out.println("Root " + root.getStr());
            printTree(root.getLeft());
            //System.out.println("Right");
            printTree(root.getRight());
            
        }
    }
    
}
