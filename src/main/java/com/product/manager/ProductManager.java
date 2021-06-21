package com.product.manager;

import com.product.commons.Preconditions;
import com.product.entry.CommentEntry;
import com.product.exception.ErrorType;
import com.product.exception.ServiceException;
import com.product.handler.ProductCommentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductManager {

    private final ProductCommentHandler commentHandler;

    @Autowired
    public ProductManager(ProductCommentHandler commentHandler) {
        this.commentHandler = commentHandler;
    }


    public Optional<CommentEntry> postComment(String productId, CommentEntry entry) throws ServiceException {
        try {
            Preconditions.checkNotNull(entry, ServiceException.build(1001, ErrorType.INPUT, "invalid comment data"));
            Preconditions.checkNotNull(productId, ServiceException.build(1002, ErrorType.INPUT, "invalid product id"));
            Preconditions.checkNotNull(entry.getComment(), ServiceException.build(1003, ErrorType.INPUT, "comment not exists in input json"));

            Boolean objectionable = this.commentHandler.isObjectionableContent(entry);

            entry.setProductId(productId);
            entry.setObjectionable(objectionable);

            return Optional.of(entry);
        } catch (Exception ex) {
            throw ServiceException.build(1004, ErrorType.UNKNOWN, ex.getMessage());
        }
    }
}
