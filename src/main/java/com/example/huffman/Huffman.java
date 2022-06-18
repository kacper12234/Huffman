package com.example.huffman;

import java.util.*;
import java.util.stream.Collectors;

public class Huffman {

    private void encodeChar(Node root, String str,
                           Map<Character, String> huffmanCode)
    {
        if (root == null) {
            return;
        }

        // Found a leaf node
        if (isLeaf(root)) {
            huffmanCode.put(root.getCharacter(), str.length() > 0 ? str : "1");
        }

        encodeChar(root.getLeft(), str + '0', huffmanCode);
        encodeChar(root.getRight(), str + '1', huffmanCode);
    }

    private int decodeChar(Node root, int index, StringBuilder sb)
    {
        if (root == null) {
            return index;
        }

        // Found a leaf node
        if (isLeaf(root))
        {
            System.out.print(root.getCharacter());
            return index;
        }

        index++;

        root = (sb.charAt(index) == '0') ? root.getLeft() : root.getRight();
        index = decodeChar(root, index, sb);
        return index;
    }

    private boolean isLeaf(Node root) {
        return root.getLeft() == null && root.getRight() == null;
    }

    public String encode(Node root, String text){
        if(root != null){
            Map<Character,String> huffmanCode = new HashMap<>();
            encodeChar(root, "", huffmanCode);
            return text.chars().mapToObj(c->huffmanCode.get((char)c)).collect(Collectors.joining());
        }
        return "";
    }

    public Node buildTree(String text){

        if(text == null || text.isBlank()){
            return null;
        }
        Map<Character, Integer> freq = new HashMap<>();
        for(char c: text.toCharArray()){
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getFreq));
        freq.forEach((key, value) -> priorityQueue.add(new Node(key, value)));

        while(priorityQueue.size() != 1)
        {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            int sum = left.getFreq() + right.getFreq();
            priorityQueue.add(new Node(null, sum, left, right));
        }

        Node root = priorityQueue.peek();
        Map<Character,String> huffmanCode = new HashMap<>();
        encodeChar(root, "", huffmanCode);

        System.out.println("Huffman codes are: " + huffmanCode);
        System.out.println("The original string is: " + text);

        double m=0;
        double h=0;
        for(Character c: freq.keySet()){
            int f = freq.get(c);
            double p=(double)f/text.length();
            h+=p*Math.log(1/p)/Math.log(2);
            String code = huffmanCode.get(c);
            m+=p*code.length();
        }
        System.out.println(h);
        System.out.println(m);

        StringBuilder sb = new StringBuilder();
        for(char c: text.toCharArray()){
            sb.append(huffmanCode.get(c));
        }
        System.out.println("The encoded string is: " + sb);
        System.out.print("The decoded string is: ");
        if(isLeaf(root)) {
            root.setFreq(root.getFreq()-1);
            while (root.getFreq() > 0){
                System.out.println(root.getCharacter());
                root.setFreq(root.getFreq()-1);
            }
        } else {
            int index = -1;
            while (index < sb.length() - 1) {
                index = decodeChar(root, index, sb);
            }
        }
        return root;
    }
}
