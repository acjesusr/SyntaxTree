
import java.util.Scanner;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JesusCamargo
 */
public class JTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Regex: ");
        String regex = sc.next();
        /*Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();*/
        sc = new Scanner(regex);
        while(sc.hasNext()){
           System.out.println(sc.next());
        }
        /*regex = regexWithConcat(regex);
        System.out.println(regex);*/
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
}
