package com.java.update;

import com.google.gson.Gson;
import com.java.update.api.SKExampleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SKExampleApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void modifySKExample_skExampleIdIsInsideRange_returnOk() throws Exception {
        Gson gson = new Gson();
        SKExampleRequest request = new SKExampleRequest(1, 1);
        this.mockMvc
                .perform(
                        post("/modify")
                                .content(gson.toJson(request))
                                .contentType(APPLICATION_JSON)
                ).andExpect(
                        status().isOk()
                );
    }

    @Test
    public void modifySKExample_skExampleIdIsOutsideRange_returnStatusIsIAmATeapot() throws Exception {
        Gson gson = new Gson();
        SKExampleRequest request = new SKExampleRequest(2, 1);
        this.mockMvc
                .perform(
                        post("/modify")
                                .content(gson.toJson(request))
                                .contentType(APPLICATION_JSON)
                ).andExpect(
                        status().isIAmATeapot()
                );
    }
}
