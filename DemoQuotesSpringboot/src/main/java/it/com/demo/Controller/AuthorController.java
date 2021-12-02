package it.com.demo.Controller;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allAuthors")
public class AuthorController {

    private static final Logger logger = LogManager.getLogger(AuthorController.class);

    @Autowired
    AuthorService service;

    @GetMapping()
    @ApiOperation(value = "getAllAuthors", notes = "Gets all Authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operation sucess", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Empty repository", response = QuoteException.class)})
    public ResponseEntity<Object> getAllAuthors(@RequestParam(name = "name", defaultValue = "")String name,@RequestParam(name = "surname", defaultValue = "")String surname ) throws QuoteException {
        logger.info("Inside GET method");
        return service.getAllAuthors(name, surname);

    }

    @PatchMapping("/{id}/patch")
    @ApiOperation(value = "getAllQuotes", notes = "Gets all Quotes and authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patch Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Author not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> patchAuthor(@PathVariable("id") Long authorId, @RequestBody Author author) throws QuoteException {
        logger.info("Inside PATCH method");
        return service.patchAuthor(authorId, author);
    }

    @DeleteMapping("/{id}/delete")
    @ApiParam(name = "authorId", type = "Long", value = "Id of author", required = true)
    @ApiOperation(value = "delAuthor", notes = "Deletes an author from the DB")
    @ApiResponse(code = 101, message = "Element not found", response = QuoteException.class)
    public ResponseEntity<Object> delAuthor(@PathVariable("id") Long id) throws QuoteException {
        logger.info("Inside DEL method");
        return service.delAuthor(id);
    }
}
