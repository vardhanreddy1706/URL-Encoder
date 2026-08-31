package com.vardhanreddy1706.URLEncoder.Controller;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Service.ShorturlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class ShorturlControllerTests {

    @Mock
    private ShorturlService shorturlService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new ShorturlController()).build();
    }

    @Test
    void createsShortUrlAndReturnsCreatedStatus() throws Exception {
        Shorturl savedShorturl = new Shorturl(
                "aB7kP2",
                "https://www.sivalabs.in/",
                null,
                false,
                null
        );
        savedShorturl.setId("mongo-id-1");

        when(shorturlService.createShorturl("https://www.sivalabs.in/"))
                .thenReturn(savedShorturl);

        mockMvc.perform(post("/api/v1/short-urls")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"originalUrl":"https://www.sivalabs.in/"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("mongo-id-1"))
                .andExpect(jsonPath("$.shortKey").value("aB7kP2"))
                .andExpect(jsonPath("$.originalUrl").value("https://www.sivalabs.in/"))
                .andExpect(jsonPath("$.clickCount").value(0));
    }
}
