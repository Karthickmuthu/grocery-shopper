package com.groceryshopper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.groceryshopper.model.Item;
import com.groceryshopper.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroceryShopperAPIService {

	private final ItemRepository itemRepository;

	public Item createItem(Item item) throws Exception {
		Item entity = itemRepository.save(new Item(item.getTitle(), item.getNotes(), false));
		return entity;
	}

	public List<Item> getItems() throws Exception {
		List<Item> items = itemRepository.findAll();
		return items;
	}

	public Optional<Item> getItemById(long id) throws Exception {
		Optional<Item> itemData = itemRepository.findById(id);
		return itemData;
	}

	public Item updateItem(long id, Item item) throws Exception {
		Optional<Item> itemData = itemRepository.findById(id);
		if (itemData.isPresent()) {
			Item entity = itemData.get();
			entity.setTitle(item.getTitle());
			entity.setNotes(item.getNotes());
			entity.setPurchased(item.isPurchased());
			entity = itemRepository.save(entity);
			return entity;
		} else {
			return null;
		}
	}

	public ResponseEntity<HttpStatus> deleteItem(long id) throws Exception {
		try {
			itemRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
