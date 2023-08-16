package com.example.bookstore;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Book;
import org.openapitools.model.CreateBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookstoreApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateBook() throws Exception {

		JSONObject jo = new JSONObject();
		jo.put("author", "Author");
		jo.put("code", "123445");
		jo.put("name", "Book");
		jo.put("quantity", 12);
		jo.put("publisher", "Publisher");

		this.mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
				.andDo(print())
				.andExpect(status()
						.isCreated())
				.andExpect(jsonPath("$.name").value("Book"));
	}
}
