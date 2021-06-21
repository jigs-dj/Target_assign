package com.product.data.badwords;

import com.product.data.TrieNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatasetLoader {

    public static void build(Path path, TrieNode node) throws IOException {
        Files.lines(path).forEach(line -> addToTrie(node, line.toLowerCase(), 0));
    }

    /** Helper method for loading data in Trie node. */
    private static void addToTrie(TrieNode node, String line, int index) {
        if (line == null || index > line.length()) {
            return;
        }

        Character ch = line.charAt(index);
        if (!node.containsChild(ch)) {
            node.addChild(ch);
        }

        if (index == line.length() - 1) {
            node.getChild(ch).setLast(Boolean.TRUE);
            return;
        }

        addToTrie(node.getChild(ch), line, ++index);
    }
}
