package com.product.data;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ToString
@NoArgsConstructor
public class TrieNode implements Serializable {

    private Map<Character, TrieNode> node;

    private Boolean last;

    /** To add child node into dataset. */
    public void addChild(Character character) {
        if (this.node == null) {
            this.node = new HashMap<>();
        }
        this.node.put(character, new TrieNode());
    }

    /** To get child node from dataset. */
    public TrieNode getChild(Character character) {
        if (this.node == null) {
            return null;
        }
        return this.node.get(character);
    }

    /** To check the child exists or not. */
    public boolean containsChild(Character character) {
        if (this.node == null) {
            return false;
        }
        return this.node.containsKey(character);
    }

    /** To set the last pointer for child. */
    public void setLast(Boolean last) {
        this.last = last;
    }

    /** To get the last pointer for child. */
    public Boolean getLast() {
        return this.last == null ? Boolean.FALSE : this.last;
    }
}
