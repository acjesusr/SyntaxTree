/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1_camargo_marquez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author JesusCamargo
 */
public class SyntaxTree {
    private String regex;
    private Node root;
    private int leaves = 0;
    private int nodes = 0;
    public int levels;
    private final ArrayList<String> alphabet = new ArrayList<String>(){//Search for functional operations
        @Override
        public String toString(){
            String str = "{";
            str = this.stream().map((i) -> i+",").reduce(str, String::concat);
            return str.length() > 1 ? str.substring(0, str.length()-1)+"}" : str+"}";
            //return "{" + this.stream().map((i) -> i+",").reduce(str, String::concat).substring(0, str.length()-1)+"}";
        }
    };
    private static final Map<Character, Integer> PRECEDENCE_MAP;
    static //<editor-fold defaultstate="collapsed" desc="Initialize PRECEDENCE_MAP">
    {
            Map<Character, Integer> map = new HashMap<>();
            /*map.put('(', 1);
            map.put('|', 2);
            map.put('.', 3);
            map.put('?', 4);
            map.put('*', 4);
            map.put('+', 4);*/
            /*
                       *
                       +
                       .
                       ?
                       |
            a.b?.#
            */
            map.put('(', 1);
            map.put('|', 2);
            map.put('.', 3);
            map.put('*', 4);
            map.put('+', 4);
            map.put('?', 4);
            
            PRECEDENCE_MAP = Collections.unmodifiableMap(map);
    };//</editor-fold>
    
    private static Integer getPrecedence(Character c) {
            Integer precedence = PRECEDENCE_MAP.get(c);
            return precedence == null ? 5 : precedence;
    }

    public SyntaxTree(String regex) {
        this.regex = regex;
        root = this.createTree();
        levels = this.getTreeLevels();
    }
    
    private Node createTree() {
       String regex = regexWithConcat("("+this.regex + ")#");
        //System.out.println("New regex: " + regex);
       Stack<Node> operand = new Stack<>();
       Stack<Character> operator = new Stack<>();
       int i = 0;
       while(i < regex.length()){
           if (regex.charAt(i) != '+' && regex.charAt(i) != '?' && regex.charAt(i) != '*' && regex.charAt(i) != '|'
                   && regex.charAt(i) != ')' && regex.charAt(i) != '(' && regex.charAt(i) != '.'){
               
               Node t = new Node(regex.charAt(i)+"");
               if (t.getStr().equals("&")) {
                   t.setNullable(true);
               }else{
                   leaves++;
                   t.setNullable(false);
                   t.setId(leaves);
                   t.getFirstPos().add(t.getId());
                   //t.getFollowPos().add(t.getId());
                   t.getLastPos().add(t.getId());
               }
               if (!alphabet.contains(t.toString()) && !t.toString().equals("#")) {
                   if (t.toString().equals("&")) {
                       alphabet.add(0, "&");
                   }else{
                       alphabet.add(t.toString());
                   }
                }
               nodes++;
               operand.add(t);
           }else{
               if (regex.charAt(i) == '(' ) {
                   operator.add(regex.charAt(i));
               }else{
                   if (operator.isEmpty() || operator.peek() == '(') {
                       operator.add(regex.charAt(i));
                   }else{
                       if (regex.charAt(i) == ')') {
                            while(operator.peek() != '('){
                                operand = updateStack(operator.pop(), operand);
                            }
                            //System.out.println("Popped: " + operator.pop()); //<------
                            operator.pop();
                        }else{
                            if (getPrecedence(operator.peek()) < getPrecedence(regex.charAt(i))) {
                                operator.add(regex.charAt(i));
                            }else{
                                if (getPrecedence(regex.charAt(i)) <= getPrecedence(operator.peek())) {
                                     while(!operator.empty() && getPrecedence(regex.charAt(i)) <= getPrecedence(operator.peek())){
                                         operand = updateStack(operator.pop(),operand);
                                     }
                                     operator.add(regex.charAt(i));
                                }
                            }
                       }
                   }
               }
           }
           i++;
       }
       while(!operator.empty()){
           operand = updateStack(operator.pop(),operand);
       }
       return operand.pop();
    }
    
    private Stack<Node> updateStack(char c, Stack<Node> stack){
        Node r;
        Node l;
        Node t;
        nodes++;
        switch (c) {
            case '*':
                l = stack.pop();
                t=new Node("*", l,null);
                t.setNullable(true);
                t.getFirstPos().addAll(l.getFirstPos());
                t.getLastPos().addAll(l.getLastPos());
                Collections.sort(t.getFirstPos());
                Collections.sort(t.getLastPos());
                t.getLastPos().forEach((i) -> {
                    SyntaxTree.getNodeById(i, t).getFollowPos().addAll(t.getFirstPos());
                    Collections.sort(SyntaxTree.getNodeById(i, t).getFollowPos());
                });
                if (!alphabet.contains("&")) {
                    alphabet.add(0, "&");
                }
                stack.add(t);
                break;
            case '+':
                l = stack.pop();
                t=new Node("+", l,null);
                t.setNullable(l.isNullable());
                t.getFirstPos().addAll(l.getFirstPos());
                t.getLastPos().addAll(l.getLastPos());
                t.getLastPos().forEach((i) -> {
                    SyntaxTree.getNodeById(i, t).getFollowPos().addAll(t.getFirstPos());
                    Collections.sort(SyntaxTree.getNodeById(i, t).getFollowPos());
                });
                Collections.sort(t.getFirstPos());
                Collections.sort(t.getLastPos());
                stack.add(t);
                break;
            case '?':
                l = stack.pop();
                t=new Node("?", l,null);
                t.setNullable(true);
                t.getFirstPos().addAll(l.getFirstPos());
                t.getLastPos().addAll(l.getLastPos());
                if (!alphabet.contains("&")) {
                    alphabet.add(0, "&");
                }
                Collections.sort(t.getFirstPos());
                Collections.sort(t.getLastPos());
                stack.add(t);
                break;
            case '|':
                r = stack.pop();
                l = stack.pop();
                t=new Node("|",l,r);
                t.setNullable(r.isNullable() || l.isNullable());
                t.getFirstPos().addAll(l.getFirstPos());
                t.getFirstPos().addAll(r.getFirstPos());
                t.getLastPos().addAll(l.getLastPos());
                t.getLastPos().addAll(r.getLastPos());
                Collections.sort(t.getFirstPos());
                Collections.sort(t.getLastPos());
                stack.add(t);
                break;
            case '.':
                r = stack.pop();
                l = stack.pop();
                t=new Node(".",l,r);
                t.setNullable(l.isNullable() && r.isNullable());
                if (l.isNullable()) {
                    t.getFirstPos().addAll(l.getFirstPos());
                    t.getFirstPos().addAll(r.getFirstPos());
                }else{
                    t.getFirstPos().addAll(l.getFirstPos());
                }
                if (r.isNullable()) {
                    t.getLastPos().addAll(l.getLastPos());
                    t.getLastPos().addAll(r.getLastPos());
                }else{
                    t.getLastPos().addAll(r.getLastPos());
                }
                
                t.getLeft().getLastPos().forEach((i) -> {
                    SyntaxTree.getNodeById(i, t).getFollowPos().addAll(t.getRight().getFirstPos());
                    Collections.sort(SyntaxTree.getNodeById(i, t).getFollowPos());
                });
                Collections.sort(t.getFirstPos());
                Collections.sort(t.getLastPos());
                stack.add(t);
                break;

            default:
                //System.out.println("Default case: " + c);
                nodes--;
        }
        /*System.out.println("Node: " + t.getStr());
        System.out.println("First position: " + t.getFirstPos());
        System.out.println("Last position: " + t.getLastPos());*/
        return stack;
    }
    
    private int getTreeLevels(){
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        int levels = 0;
        int level = 1;
        int nlevel = 0;
        while(!nodes.isEmpty()){
            Node t = nodes.poll();
            if (t.getRight() != null) {
                nodes.add(t.getLeft());
                nodes.add(t.getRight());
                nlevel+=2;
            }else{
                if (t.getLeft() != null) {
                    nodes.add(t.getLeft());
                    nlevel++;
                }
            }
            level--;
            if (level == 0) {
                levels++;
                level = nlevel;
                nlevel = 0;
            }
        }
        return levels;
    }
    
    public static String regexWithConcat(String regex){
        String newRegex = new String();
        for (int i = 0; i < regex.length(); i++) {
           if(regex.charAt(i) != '+' && regex.charAt(i) != '?' && regex.charAt(i) != '*' && regex.charAt(i) != '|'
                   && regex.charAt(i) != ')' && i!= 0 && regex.charAt(i-1) != '(' && regex.charAt(i-1)!= '|'){
                    newRegex = newRegex+'.'+regex.charAt(i);
            }else{
                 newRegex += regex.charAt(i);
            }
        }
        return newRegex;
    }
    
    public static Node getNodeById(int id, Node root){
        if (root == null || root.getId() == id) {
            return root;
        }else{
            Node t = SyntaxTree.getNodeById(id, root.getRight());
            return t == null? getNodeById(id, root.getLeft()) : t;
        }
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
        this.root = this.createTree();
    }

    public Node getRoot() {
        return root;
    }

    public int getLeaves() {
        return leaves;
    }

    public int getNodes() {
        return nodes;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }
   
}
