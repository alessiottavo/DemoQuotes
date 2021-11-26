package it.com.demo.Controller;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allAuthors")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping()
    @ApiOperation(value = "getAllAuthors", notes = "Gets all Authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operation sucess", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Empty repository", response = QuoteException.class)})
    private ResponseEntity<Object> getAllQuotes() throws QuoteException {
        return service.getAllAuthors();
    }

    @PatchMapping("/{id}/patch")
    @ApiOperation(value = "getAllQuotes", notes = "Gets all Quotes and authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patch Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Author not given correctly", response = QuoteException.class)})
    private ResponseEntity<Object> patchAuthor(@PathVariable("id") Long authorId, @RequestBody Author author) throws QuoteException {
        return service.patchAuthor(authorId, author);
    }

    @DeleteMapping("/{id}/delete")
    @ApiParam(name = "authorId", type = "Long", value = "Id of author", required = true)
    @ApiOperation(value = "delAuthor", notes = "Deletes an author from the DB")
    @ApiResponse(code = 101, message = "Element not found", response = QuoteException.class)
    public ResponseEntity<Object> delAuthor(@PathVariable("id") Long id) throws QuoteException {
        return service.delAuthor(id);
    }
}
