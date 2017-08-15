package com.nowcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by wang on 2017/7/10.
 */
public class SensitiveTest {
    private class TrieNode{
        private boolean end = false;
        private HashMap<Character,TrieNode> subNodes = new HashMap<>();
        void addTrieNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }
        TrieNode getTrieNode(Character key){
            return subNodes.get(key);
        }
        boolean isKeyWordEnd(){
            return end;
        }
        void setKeyWordEnd(boolean end){
            this.end=end;
        }
        public int getSubNodeCount() {
            return subNodes.size();
        }
    }
    TrieNode rootNode = new TrieNode();
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }
    private void addwords(String LineTxt){
        TrieNode tempNode = rootNode;

        for(int i=0;i<LineTxt.length();i++){
            char c = LineTxt.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getTrieNode(c);
            if(node == null){
                node = new TrieNode();
                tempNode.addTrieNode(c,node);
            }
            tempNode = node;
            if(i == LineTxt.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }

    }
    private String filter(String text){
        int begin = 0;
        int position = 0;
        StringBuilder result = new StringBuilder();
        TrieNode tempNode = rootNode;
        while(position<text.length()){
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode == rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getTrieNode(c);
            if(tempNode == null){
                result.append(text.charAt(begin));
                position = begin+1;
                begin = position;
                tempNode=rootNode;
            }else if (tempNode.isKeyWordEnd()){
                result.append("**");
                position = position+1;
                begin = position;
                tempNode = rootNode;
            }else{
                position++;
            }
        }
        result.append(text.substring(begin));
        return  result.toString();
    }
    public static void main(String[] args){
        System.out.println(new SensitiveTest().isSymbol(' '));
    }
}
