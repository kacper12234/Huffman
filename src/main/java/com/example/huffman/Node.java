package com.example.huffman;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Node {
    private Character character;
    @Setter
    private Integer freq;
    private Node left, right;

    Node(Character character, Integer freq)
    {
        this.character = character;
        this.freq = freq;
    }

    public static int getHeight(Node root) {
        if (root == null)
            return 0;

        int heightLeft = getHeight(root.left);
        int heightRight = getHeight(root.right);

        return 1 + Math.max(heightLeft, heightRight);
    }
}
