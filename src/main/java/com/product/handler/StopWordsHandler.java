package com.product.handler;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class StopWordsHandler {

    private static final String STOPWORDS_FILE = "data/stop-words.txt";

    private final List<String> stopWords;

    /** To initialize the stop words. */
    public void init() throws IOException, URISyntaxException {
        stopWords.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(STOPWORDS_FILE).toURI()))
                .collect(Collectors.toList()));
    }

    /** To remove the stop words from the input. */
    void removeAllStopWords(List<String> words) {
        words.removeAll(this.stopWords);
    }
}
