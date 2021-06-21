package com.product.data.local;

import com.product.data.Dataset;
import com.product.data.TrieNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LocalDataset implements Dataset {

    private final TrieNode badWords;
}
