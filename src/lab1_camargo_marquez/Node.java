/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1_camargo_marquez;

import java.util.ArrayList;

/**
 *
 * @author JesusCamargo
 */
public class Node {
    private String str;
    private int id = -1;
    private Node left;
    private Node right;
    private boolean nullable;
    private ArrayList<Integer> firstPos = new ArrayList<Integer>(){//Search for functional operations
        @Override
        public String toString(){
            String str = "{";
            str = this.stream().map((i) -> i+",").reduce(str, String::concat);
            return str.length() > 1 ? str.substring(0, str.length()-1)+"}" : str+"}";
            //return "{" + this.stream().map((i) -> i+",").reduce(str, String::concat).substring(0, str.length()-1)+"}";
        }
    };
    private ArrayList<Integer> followPos = new ArrayList<Integer>(){
        @Override
        public String toString(){
            String str = "{";
            for (Integer i : this) {
                str+=i+",";
            }
            return str.length() > 1 ? str.substring(0, str.length()-1)+"}" : str+"}";
        }
    };
    private ArrayList<Integer> lastPos = new ArrayList<Integer>(){
        @Override
        public String toString(){
            String str = "{";
            for (Integer i : this) {
                str+=i+",";
            }
            return str.length() > 1 ? str.substring(0, str.length()-1)+"}" : str+"}";
        }
    };

    public Node(String str) {
        this.str = str;
        this.left = null;
        this.right = null;
    }

    public Node(String str, Node left, Node right) {
        this.str = str;
        this.left = left;
        this.right = right;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isNullable() {
        return nullable;
    }

    public ArrayList<Integer> getFirstPos() {
        return firstPos;
    }

    public ArrayList<Integer> getFollowPos() {
        return followPos;
    }

    public ArrayList<Integer> getLastPos() {
        return lastPos;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setFirstPos(ArrayList<Integer> firstPos) {
        this.firstPos = firstPos;
    }

    public void setFollowPos(ArrayList<Integer> followPos) {
        this.followPos = followPos;
    }

    public void setLastPos(ArrayList<Integer> lastPos) {
        this.lastPos = lastPos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
    
    @Override
    public String toString(){
        return this.str;
    }
}
    
 
