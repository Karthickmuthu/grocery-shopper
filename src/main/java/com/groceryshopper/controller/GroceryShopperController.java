package com.groceryshopper.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groceryshopper.model.Item;
import com.groceryshopper.service.GroceryShopperAPIService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${crossorigin.value}")
@RestController
@RequestMapping("/grocery-shopper")
@RequiredArgsConstructor
public class GroceryShopperController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroceryShopperController.class);

	private final GroceryShopperAPIService groceryShopperAPIService;

	@PostMapping("/item")
	public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
		LOGGER.info("createItem() started....");
		LOGGER.info("Request body: {}", item);
		try {
			Item entity = groceryShopperAPIService.createItem(item);
			LOGGER.info("Item created successfully....");
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item")
	public ResponseEntity<List<Item>> getItems() {
		LOGGER.info("getItems() started....");
		try {
			List<Item> items = groceryShopperAPIService.getItems();
			if (items.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(items, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item/{id}")
	public ResponseEntity<Item> getItemById(
			@PathVariable(value = "id", required = true) @NotNull(message = "Please provide a valida id") long id) {
		LOGGER.info("getItemById() started....Id: {}", id);
		try {
			Optional<Item> itemData = groceryShopperAPIService.getItemById(id);
			if (itemData.isPresent()) {
				return new ResponseEntity<>(itemData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/item/{id}")
	public ResponseEntity<Item> updateItem(
			@PathVariable(value = "id", required = true) @NotNull(message = "Please provide a valida id") long id,
			@Valid @RequestBody Item item) {
		LOGGER.info("updateItem() started....Id: {}", id);
		LOGGER.info("Request body: {}", item);
		try {
			Item entity = groceryShopperAPIService.updateItem(id, item);
			if (entity != null) {
				return new ResponseEntity<>(entity, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/item/{id}")
	public ResponseEntity<HttpStatus> deleteItem(
			@PathVariable(value = "id", required = true) @NotNull(message = "Please provide a valida id") long id) {
		LOGGER.info("deleteItem() started....Id: {}", id);
		try {
			groceryShopperAPIService.deleteItem(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
