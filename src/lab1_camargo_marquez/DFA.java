/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1_camargo_marquez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author JesusCamargo
 */
public class DFA {
    private SyntaxTree st;
    private String regex;
    private final ArrayList<Object[]> dStates;
    private final HashMap<String,ArrayList<String[]>> dTran;

    public DFA(String regex) {
        this.regex = regex;
        st = new SyntaxTree(this.regex);
        dStates = new ArrayList<>();
        dTran = new HashMap<>();
        this.generateDFA();
    }

    public SyntaxTree getSt() {
        return st;
    }

    public void setSt(SyntaxTree st) {
        this.st = st;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public ArrayList<Object[]> getdStates() {
        return dStates;
    }

    public HashMap<String,ArrayList<String[]>> getdTran() {
        return dTran;
    }
    
    public void generateDFA(){
        dStates.add(new Object[]{"s0",st.getRoot().getFirstPos()});
        int i = 0;
        int s = 1;
        while (i<dStates.size()){
            for (String str : st.getAlphabet()) {
                ArrayList<Integer> u = new ArrayList<Integer>(){//Search for functional operations
                    @Override
                    public String toString(){
                        String str = "{";
                        str = this.stream().map((i) -> i+",").reduce(str, String::concat);
                        return str.length() > 1 ? str.substring(0, str.length()-1)+"}" : str+"}";
                    }
                };
                if (!str.equals("&")) {
                    for (Integer id : ((ArrayList<Integer>)dStates.get(i)[1])) {
                        Node node = SyntaxTree.getNodeById(id, st.getRoot());
                        if (node.getStr().equals(str)) {
                            node.getFollowPos().stream().filter((follow) -> (!u.contains(follow))).forEachOrdered((follow) -> {
                                u.add(follow);
                            });
                        }
                    }
                    if (!u.isEmpty()) {
                        Collections.sort(u);
                        boolean newState=true;
                        Object[] temp = null;
                        for (Object[] dState : dStates) {
                            if (u.equals(((ArrayList)dState[1]))) {//contains all or equals?
                                newState=false;
                                temp = dState;
                                //System.out.println("temp: " + temp[0]);
                                break;
                            }
                        }
                        if (newState) {
                            temp = new Object[]{"s" + Integer.toString(s),u};
                            s++;
                            dStates.add(temp);
                        }
                        if (dTran.containsKey(dStates.get(i)[0].toString())) {
                            dTran.get(dStates.get(i)[0].toString()).add(new String[]{str,temp[0].toString()});
                            //System.out.println(str+temp[0].toString());
                        }else{
                            ArrayList<String[]> list = new ArrayList<>();
                            list.add(new String[]{str,temp[0].toString()});
                            dTran.put(dStates.get(i)[0].toString(),list);
                        }
                    }
                }
            }
            i++;
        }
        
    }
    
    public boolean generates(String str){
        boolean res = false;
        String state = "s0";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '&') {
                if (st.getAlphabet().contains(str.charAt(i)+"")) {
                        ArrayList<String[]> transitions = dTran.get(state);
                        if (transitions != null) {
                            boolean noTransition = true;
                            for (String[] transition : transitions) {
                                if (transition[0].equals(str.charAt(i)+"")) {
                                    state = transition[1];
                                    noTransition = false;
                                }
                            }
                            if (noTransition) {
                                return false;
                            }
                        }else{
                            return false;
                        }
                }else{
                    return false;
                }
            }
        }
        for (Object[] dState : dStates) {
            if (dState[0].equals(state)) {
                res = ((ArrayList)dState[1]).contains(st.getLeaves());
            }
        }
        return res;
    }
}
