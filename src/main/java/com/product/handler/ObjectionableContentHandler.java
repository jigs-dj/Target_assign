package com.product.handler;

import com.product.data.Dataset;
import com.product.data.TrieNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectionableContentHandler {

    private final Dataset dataset;

    @Autowired
    public ObjectionableContentHandler(Dataset dataset) {
        this.dataset = dataset;
    }


    boolean isObjectionableContent(String processedInput) {
        for (int i = 0; i < processedInput.length(); i++) {
            boolean isObjectionable = validateWithDataSet(processedInput, i, this.dataset.getBadWords());

            if (isObjectionable) {
                return true;
            }
        }
        return false;
    }

    /** Helper function for identifying the objectionable content  */
    private static boolean validateWithDataSet(String input, int index, TrieNode node) {
        if (input == null || index > input.length() - 1) {
            return false;
        }

        Character ch = input.charAt(index);
        if (node.containsChild(ch)) {

            node = node.getChild(ch);
            if (Boolean.TRUE.equals(node.getLast())) {
                return true;
            }
            return validateWithDataSet(input, ++index, node);
        }
        return false;
    }
}
