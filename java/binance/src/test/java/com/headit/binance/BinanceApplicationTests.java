package com.headit.binance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BinanceApplicationTests {
    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testOne() {
        try {
            mvc.perform(get("/order")
                    .contentType("application/json")
                    .accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(mvcResult -> {
                        String content = mvcResult.getResponse().getContentAsString();
                        assert (content.contains("\"symbol:\""));
                        System.out.println(content);
                    });
        } catch (Exception e) {
            System.out.println("Exception While Test");
            e.printStackTrace();
        }
    }
}
