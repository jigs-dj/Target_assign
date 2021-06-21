package com.product.resource;

import com.product.entry.CommentEntry;
import com.product.exception.ServiceException;
import com.product.manager.ProductManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/product")
public class ProductResource implements AbstractResource {

    private final ProductManager manager;

    @Autowired
    public ProductResource(ProductManager manager) {
        this.manager = manager;
    }

    /**
     * API to post product comment.
     */
    @POST
    @Path("/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postComment(@PathParam("id") String id, CommentEntry entry) throws ServiceException {
        try {
            return sendResponse(this.manager.postComment(id, entry));
        } catch (Exception ex) {
            logException("Error in postComment. entry: {} \n ex: {}", entry, ex.getMessage());
            throw ex;
        }
    }
}
