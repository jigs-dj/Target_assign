package com.product.config;

import com.product.data.Dataset;
import com.product.data.TrieNode;
import com.product.data.badwords.DatasetLoader;
import com.product.data.local.LocalDataset;
import com.product.data.redis.RedisDataset;
import com.product.exception.ServiceExceptionMapper;
import com.product.handler.StopWordsHandler;
import com.product.resource.ProductResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Configuration
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    private final String badWordsFile;
    private final Boolean isLocalStorageMode;

    public ApplicationConfig(@Value("${data.bad-words-file:data/bad-words.txt}") String badWordsFile,
                             @Value("${data.is-local-storage-mode:true}") Boolean isLocalStorageMode) {
        this.badWordsFile = badWordsFile;
        this.isLocalStorageMode = isLocalStorageMode;
    }

    @Bean
    public ResourceConfig resourceConfig() {
        return new ResourceConfig().register(ProductResource.class)
                .register(ServiceExceptionMapper.class);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public Dataset getDataset() throws IOException, URISyntaxException {
        try {
            if (Boolean.TRUE.equals(this.isLocalStorageMode)) {
                TrieNode node = new TrieNode();

                Path filePath = Paths.get(this.badWordsFile).toFile().exists() ? Paths.get(this.badWordsFile) :
                        Paths.get(getClass().getClassLoader().getResource("data/bad-words.txt").toURI());
                DatasetLoader.build(filePath, node);
                return new LocalDataset(node);
            }

            return new RedisDataset();
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error("Error initializing application config - Dataset", ex);
            throw ex;
        }
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public StopWordsHandler stopWordsHandler() throws IOException, URISyntaxException {
        try {
            StopWordsHandler handler = new StopWordsHandler(new ArrayList<>());
            handler.init();
            return handler;
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error("Error initializing application config - StopWordsHandler", ex);
            throw ex;
        }
    }
}
