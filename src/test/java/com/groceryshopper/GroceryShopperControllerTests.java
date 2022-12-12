package com.groceryshopper;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groceryshopper.model.Item;
import com.groceryshopper.repository.ItemRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GroceryShopperControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		itemRepository.deleteAll();
	}

	@Test
	public void givenItemObject_whenCreateItem_thenReturnSavedItem() throws Exception {
		Item item = Item.builder().title("Item test").notes("note test").purchased(false).build();
		ResultActions response = mockMvc.perform(post("/grocery-shopper/item").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(item)));
		response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is(item.getTitle())))
				.andExpect(jsonPath("$.notes", is(item.getNotes())))
				.andExpect(jsonPath("$.purchased", is(item.isPurchased())));

	}

	@Test
	public void givenListOfItemss_whenGetAllItems_thenReturnItemssList() throws Exception {
		// given - precondition or setup
		List<Item> listOfItems = new ArrayList<>();
		listOfItems.add(Item.builder().title("item2").notes("item2 notes").purchased(false).build());
		listOfItems.add(Item.builder().title("item3").notes("item3 notes").purchased(true).build());
		itemRepository.saveAll(listOfItems);
		ResultActions response = mockMvc.perform(get("/grocery-shopper/item"));
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfItems.size())));

	}

	@Test
	public void givenItemId_whenGetItemById_thenReturnItemObject() throws Exception {
		Item item = Item.builder().title("item4").notes("item4 notes").purchased(true).build();
		itemRepository.save(item);
		ResultActions response = mockMvc.perform(get("/grocery-shopper/item/{id}", item.getId()));
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.title", is(item.getTitle())))
				.andExpect(jsonPath("$.notes", is(item.getNotes())))
				.andExpect(jsonPath("$.purchased", is(item.isPurchased())));

	}

	@Test
	public void givenUpdatedItem_whenUpdateItem_thenReturnUpdateItemObject() throws Exception {
		Item savedItem = Item.builder().title("item5").notes("item5 notes").purchased(false).build();
		itemRepository.save(savedItem);
		Item updatedItem = Item.builder().title("item5").notes("item5 notes").purchased(false).build();
		ResultActions response = mockMvc.perform(put("/grocery-shopper/item/{id}", savedItem.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(savedItem)));
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.title", is(updatedItem.getTitle())))
				.andExpect(jsonPath("$.notes", is(updatedItem.getNotes())))
				.andExpect(jsonPath("$.purchased", is(updatedItem.isPurchased())));
	}

	@Test
	public void givenItemId_whenDeleteItem_thenReturn204() throws Exception {
		Item savedItem = Item.builder().title("item6").notes("item6 notes").purchased(false).build();
		itemRepository.save(savedItem);
		ResultActions response = mockMvc.perform(delete("/grocery-shopper/item/{id}", savedItem.getId()));
		response.andExpect(status().isNoContent()).andDo(print());
	}
}