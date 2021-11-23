package com.quotes.Controller;

import com.quotes.exception.QuoteException;
import com.quotes.model.QuotesElement;
import com.quotes.service.QuotesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/")
public class QuotesController {

    @Autowired
    QuotesService service;

    @GetMapping("allquotes")
    @ApiOperation(value = "getAllQuotes", notes = "Gets all Quotes from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get operation sucess", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Empty repository", response = QuoteException.class)})
    private ResponseEntity<Object> getAllQuotes() throws QuoteException {
        return service.getAllQuotes();
    }

    @GetMapping("quote/{id}")
    @ApiParam(name = "QuoteId", type = "Long", value = "Id of Quote", required = true)
    @ApiOperation(value = "getQuote", notes = "Gets a single quote from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get Operation success", response = QuoteException.class),
            @ApiResponse(code = 101, message = "Element not found", response = QuoteException.class)})
    private ResponseEntity<Object> getQuote(@PathVariable Long id) throws QuoteException {
        return service.getQuote(id);
    }

    @GetMapping("allfrom")
    @ApiParam(name = "author", type = "String", value = "author filed of object QuotesElement", required = true)
    @ApiOperation(value = "getQuoteFrom", notes = "Gets all quotes from a single author")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get Operation success", response = QuoteException.class),
            @ApiResponse(code = 101, message = "author not found", response = QuoteException.class),
            @ApiResponse(code = 103, message = "author not given correctly", response = QuoteException.class)})
    private ResponseEntity<Object> getQuoteFrom(@RequestParam("author") String author) throws QuoteException {
        return service.getQuoteFrom(author);
    }

    @PostMapping("addquote")
    @ApiParam(name = "Quote", type = "QuotesElement", value = "QuotesElement Object as Json", required = true)
    @ApiOperation(value = "addQuote", notes = "Adds a quote to the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class),
            @ApiResponse(code = 400, message = "Quote not given correctly", response = QuoteException.class)
    })
    public ResponseEntity<Object> addQuote(@RequestBody QuotesElement quote) throws QuoteException {
        return service.addQuote(quote);
    }

    @PutMapping("changequote/{id}")
    @ApiParam(name = "Quote", type = "QuotesElement", value = "QuotesElement Object as Json", required = true)
    @ApiOperation(value = "putQuote", notes = "Put operation on a Quote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Put Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> putQuote(@PathVariable("id") Long id, @RequestBody QuotesElement quote) throws QuoteException {
        return service.putQuote(id, quote);
    }

    @PatchMapping("fixAuthor/{id}")
    @ApiParam(name = "QuoteId", type = "Long", value = "Id of Quote", required = true)
    @ApiOperation(value = "fixQuote", notes = "Patch operation on a quote in Map")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patch Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> fixAuthor(@PathVariable("id") Long id, @RequestBody String author) throws QuoteException {
        return service.fixAuthor(id, author);
    }

    @PatchMapping("fixQuote/{id}")
    @ApiParam(name = "QuoteId", type = "Long", value = "Id of Quote", required = true)
    @ApiOperation(value = "fixQuote", notes = "Patch operation on a quote in Map")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patch Operation success", response = QuoteException.class),
            @ApiResponse(code = 103, message = "Quote not given correctly", response = QuoteException.class)})
    public ResponseEntity<Object> fixQuote(@PathVariable("id") Long id, @RequestBody String quote) throws QuoteException {
        return service.fixQuote(id, quote);
    }


    @DeleteMapping("deletequote/{id}")
    @ApiParam(name = "QuoteId", type = "Long", value = "Id of Quote", required = true)
    @ApiOperation(value = "delQuote", notes = "Deletes a quote from the Map")
    @ApiResponse(code = 101, message = "Element not found", response = QuoteException.class)
    public ResponseEntity<Object> delQuote(@PathVariable("id") Long id) throws QuoteException {
        return service.delQuote(id);
    }
}
