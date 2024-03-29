/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1_camargo_marquez;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author JesusCamargo
 */
public class TreeView extends JPanel{
    private final SyntaxTree st;

    public TreeView(SyntaxTree st) {
        super();
        this.st = st;
        //this.setPreferredSize(new Dimension(((this.getWidth()*4/5)+st.levels*15)*st.levels,st.levels*50));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
        displayTree(g,st.getRoot(), st.levels-1,null, new Point((4*this.getWidth()/5),20));
    }
    
    private void displayTree(final Graphics g,Node root,int offset, Point p0, Point p1){
        if(root != null){
            g.setColor(Color.black);
            g.fillOval(p1.x-10, p1.y-10, 20, 20);
            
            if (p0 != null) {
                g.drawLine(p0.x, p0.y, p1.x, p1.y);
            }
            if (root.getRight() != null){
                displayTree(g,root.getLeft(),offset-1,new Point(p1.x, p1.y),new Point(p1.x-(st.levels*10-(st.levels-offset)*5),p1.y+50));
                displayTree(g,root.getRight(),offset-1,new Point(p1.x, p1.y),new Point(p1.x+(st.levels*10-(st.levels-offset)*8),p1.y+50));
            }else{
                displayTree(g,root.getLeft(),offset-1,new Point(p1.x, p1.y),new Point(p1.x,p1.y+50));
            }
            g.setColor(Color.white);
            g.drawString(root.getStr(), p1.x, p1.y+5);
            g.setColor(Color.blue);
            g.drawString("F: " + root.getFirstPos().toString(), p1.x, p1.y+20);
            g.drawString("L: " + root.getLastPos().toString(), p1.x, p1.y+35);
            if(root.getId() != -1){
                g.setColor(Color.red);
                g.drawString(Integer.toString(root.getId()), p1.x+10, p1.y+5);    
            }
        }
        
    }
}
