/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 *
 * @author Henry
 */
public class Edge {
    private Vertex vi;
    private Vertex vf;
    private static int count = 0;
    private int id;

    public Edge(Vertex vi, Vertex vf) {
        this.vi = vi;
        this.vf = vf;
        id = count;
        count++;
    }
    
    
    
}
