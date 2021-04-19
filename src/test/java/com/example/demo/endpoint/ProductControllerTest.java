package com.example.demo.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allProductTest() throws Exception {
        mockMvc.perform(get("/allProducts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/product/{id}", 37)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveProductTest() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("id", 20);
        objectNode.put("name", "botas");
        objectNode.put("description", "sdhfg");
        objectNode.put("price", 1500);

        mockMvc.perform(post("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductTest() throws Exception{
        mockMvc.perform(delete("/delete/product/{id}", 16)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductTest() throws Exception{
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "fafa");
        objectNode.put("description", "efdwds");
        objectNode.put("price", 4000);

        mockMvc.perform(put("/product/update/14")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

}
