package com.waracle.cake.controller;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waracle.cake.entity.Cake;
import com.waracle.cake.service.CakeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CakeController {

	@Autowired
	private CakeService cakeService;

	/**
	 * API to get all cakes for the given page and size
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Operation(summary = "Get list of cakes ordered by name, you must specify page number and page size")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content) })
	@GetMapping(value = "/", produces = { "application/json" })
	public List<Cake> getCakes() {
		return cakeService.getAllCakes();
	}

	/**
	 * API to get all cakes for the given page and size
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws JsonProcessingException
	 */
	@Operation(summary = "Download all cakes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content) })
	@GetMapping(value = "/cakes", produces = { "application/json" })
	public ResponseEntity<Resource> getAllCakes() throws JsonProcessingException {
		List<Cake> allCakes = cakeService.getAllCakes();
		ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		String json = objectMapper.writeValueAsString(allCakes);
		byte[] stringtobytes = json.getBytes();
		String fileName = "cakes.json";
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(stringtobytes.length);
		respHeaders.setContentType(MediaType.TEXT_XML);
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(new ByteArrayResource(stringtobytes));
	}

	/**
	 * API to get a cake by it's id
	 * 
	 * @param cakeId
	 * @return
	 */
	@Operation(summary = "Get a cake by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the cake", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "cake not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content) })
	@GetMapping(value = "/cakes/{cakeId}", produces = { "application/json" })
	public Cake getCakeById(@Parameter(description = "id of cake to be retrieved") @PathVariable int cakeId) {
		return cakeService.getCake(cakeId);
	}

	/**
	 * API to create a cake
	 * 
	 * @param cake
	 * @return
	 */
	@Operation(summary = "Create a new cake")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "cake created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "cake not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate cake name", content = @Content) })
	@PostMapping(value = "/cakes")
	public ResponseEntity<Cake> createCake(@RequestBody Cake cake) {
		return new ResponseEntity<>(cakeService.createCake(cake), HttpStatus.CREATED);
	}

	/**
	 * API to update a cake by id
	 * 
	 * @param cakeId
	 * @param cake
	 * @return
	 */
	@Operation(summary = "Update a cake by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "cake updated", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "cake not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate cake name", content = @Content) })
	@PutMapping(value = "/cakes/{cakeId}", produces = { "application/json" })
	public ResponseEntity<Cake> updateCake(
			@Parameter(description = "id of cake to be updated") @PathVariable int cakeId,
			@Parameter(description = "cake updated information") @RequestBody Cake cake) {
		return new ResponseEntity<>(cakeService.updateCake(cakeId, cake), HttpStatus.OK);
	}

	/**
	 * API to delete a cake by id
	 * 
	 * @param cakeId
	 * @return
	 */
	@Operation(summary = "Delete a cake by its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "cake deleted", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "cake not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error", content = @Content) })
	@DeleteMapping(value = "/cakes/{cakeId}")
	public ResponseEntity<Boolean> deleteCake(
			@Parameter(description = "id of cake to be deleted") @PathVariable int cakeId) {
		return new ResponseEntity<>(cakeService.deleteCake(cakeId), HttpStatus.ACCEPTED);
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, InvalidParameterException.class,
			MethodArgumentTypeMismatchException.class })
	public ResponseEntity<String> handleUserInputException() {
		return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
	}

	// Handling runtime exception
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<String> handleRuntimeException() {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<String> handleDuplicateException() {
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}
}