package com.example.demo

import com.example.demo.persistance.Entity
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun contextLoads() {
		mockMvc.perform(get("/hello/{name}", "John"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(content().string("Entity(id=1, name=John)"))

		mockMvc.perform(get("/hello/{name}", "Bill"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(content().string("Entity(id=2, name=Bill)"))

		mockMvc.perform(get("/all"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(jsonPath("$", hasSize<Entity>(2)))
	}
}
