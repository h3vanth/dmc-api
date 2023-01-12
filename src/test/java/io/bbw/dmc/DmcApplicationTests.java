package io.bbw.dmc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import io.bbw.dmc.pojo.Product;
import io.bbw.dmc.pojo.User;
import io.bbw.dmc.repository.ProductRepository;
import io.bbw.dmc.util.JwtUtils;

@SpringBootTest
@AutoConfigureMockMvc
class DmcApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private static HttpHeaders httpHeaders;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	static void setAuthToken() {
		var httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(JwtUtils.generateToken(new User("1", "hr@gmail.com",
				"12345678")));
		DmcApplicationTests.httpHeaders = httpHeaders;
	}

	@Test
	void getProductsTest() throws Exception {
	when(productRepository.findAll()).thenReturn(Arrays.asList(new Product("123", "1", "Some product", new BigDecimal(100), false, "", 0)));

	RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products").headers(httpHeaders);
	mockMvc.perform(request).andExpect(status().isOk())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	.andExpect(jsonPath("$").isArray())
	.andExpect(jsonPath("$.size()").value(1))
	.andExpect(jsonPath("$.[?(@.productId == \"123\" && @.productName == \"Some product\" && @.price== 100 && @.isAvailable == false)]")
	.exists());
	}

	@Test
	void getProductByProductIdTest() throws Exception {
	when(productRepository.findById("123")).thenReturn(Optional.of(new
	Product("123", "1", "Some product", new BigDecimal(100), false, "", 0)));

	RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products/123").headers(httpHeaders);
	mockMvc.perform(request)
	.andExpect(status().isOk())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	.andExpect(jsonPath("$").isMap())
	.andExpect(jsonPath("$.productName").value("Some product"));
	}

	@Test
	void getProductByProductId_InvalidProductIdTest() throws Exception {
	when(productRepository.findById("234")).thenReturn(Optional.empty());

	RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products/234").headers(httpHeaders);
	mockMvc.perform(request)
	.andExpect(status().isNotFound());
	}

	@Test
	void addProductTest() throws Exception {
		var product = new Product("234", "1", "Biryani", new BigDecimal(150), true, "",
				0);

		when(productRepository.save(product)).thenReturn(product);

		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/products").headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product));
		mockMvc.perform(request)
				.andExpect(status().isCreated());
	}

	@Test
	void addProduct_ProductNotValidTest() throws Exception {
		var product = new Product("234", "1", "", new BigDecimal(-1), true, "", 0);

		when(productRepository.save(product)).thenReturn(product);

		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/products").headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product));
		mockMvc.perform(request)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessages.length()").value(2));
	}

}
