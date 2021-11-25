package it.com.demo.Controller;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Quote;
import it.com.demo.service.QuoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allQuotes")
public class QuoteController {

    @Autowired
    QuoteService service;

    @PostMapping("/add")
    @ApiParam(name = "Quote", type = "Quote", value = "Quote Object as Json", required = true)
    @ApiOperation(value = "addQuote", notes = "Adds a quote and author to the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class),
            @ApiResponse(code = 400, message = "Quote not given correctly", response = QuoteException.class)
    })
    public ResponseEntity<Object> addQuote(@RequestBody Quote quote) throws QuoteException {
        return service.addQuote(quote);
    }

    @GetMapping()
    @ApiOperation(value = "getAllQuotes", notes = "Gets all Quotes and authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operation sucess", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Empty repository", response = QuoteException.class)})
    private ResponseEntity<Object> getAllQuotes() throws QuoteException {
        return service.getAllQuotes();
    }

    @GetMapping("/{authorId}")
    @ApiOperation(value = "getAllQuotes", notes = "Gets all Quotes and authors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operation sucess", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Empty repository", response = QuoteException.class)})
    private ResponseEntity<Object> getAllQuotesFromAuthor(@PathVariable("authorId") Long authorId) throws QuoteException {
        return service.getAllQuotesFromAuthor(authorId);
    }

    @PutMapping("/{id}/put")
    @ApiParam(name = "Quote", type = "Quote", value = "Quote Object as Json", required = true)
    @ApiOperation(value = "putQuote", notes = "Put operation on a Quote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Put Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> putQuote(@PathVariable("id") Long id, @RequestBody Quote quote) throws QuoteException {
        return service.putQuote(id, quote);
    }

    @PutMapping("/{id}/patch")
    @ApiParam(name = "Quote", type = "Quote", value = "Quote Object as Json", required = true)
    @ApiOperation(value = "patchQuote", notes = "Patch operation on a Quote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patch Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> patchQuote(@PathVariable("id") Long id, @RequestBody Quote quote) throws QuoteException {
        return service.patchQuote(id, quote);
    }

    @DeleteMapping("/{id}/delete")
    @ApiParam(name = "QuoteId", type = "Long", value = "Id of Quote", required = true)
    @ApiOperation(value = "delQuote", notes = "Deletes a quote from the Map")
    @ApiResponse(code = 101, message = "Element not found", response = QuoteException.class)
    public ResponseEntity<Object> delQuote(@PathVariable("id") Long id) throws QuoteException {
        return service.delQuote(id);
    }
}
