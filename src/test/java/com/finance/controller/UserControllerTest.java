package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.UserFullDTO;
import com.finance.dto.response.UserDTO;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import com.finance.model.user.User;
import com.finance.service.OfferService;
import com.finance.service.RequestService;
import com.finance.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(value = UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private OfferService offerService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private JacksonTester<UserDTO> jsonUser;

    @Autowired
    private JacksonTester<UserFullDTO> jsonRequestUser;

    @Autowired
    private JacksonTester<Page<UserDTO>> jsonUserPage;

    @Autowired
    private JacksonTester<List<FilterDTO>> jsonFilter;

    @Test
    void getAll() throws Exception {
        Pageable pageable = PageRequest.of(0, 100);
        Page<User> page = new PageImpl<>(
                Arrays.asList(new User[] {new User(0L, "name", "email", "digest",
                        true, true, null, null, null) } )
        );
        Page<UserDTO> pageDto = new PageImpl<>(
                Arrays.asList(new UserDTO[] {new UserDTO(0L, "name", "email",
                        true, true, null) } )
        );

        given(userService.list(pageable))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonUserPage.write(pageDto).getJson()
        );
    }

    @Test
    void getOne() throws Exception {
        given(userService.getOne(0L))
                .willReturn(Optional.of(new User(0L, "name", "email", "password_digest",
                        true, true, null, null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/user/0")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonUser.write(new UserDTO(0L, "name", "email",
                        true, true, null)).getJson()
        );
    }

    @Test
    void deleteT() throws Exception {
        given(userService.getOne(0L))
                .willReturn(Optional.of(new User(0L, "name", "email", "password_digest",
                        true, true, null, new ArrayList<Request>(), new ArrayList<Offer>()))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/user/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void filter() throws Exception {
        Pageable pageable = PageRequest.of(0, 100);
        Page<User> page = new PageImpl<>(
                Arrays.asList(new User[] {new User(0L, "name", "email", "digest",
                        true, true, null, null, null) } )
        );
        Page<UserDTO> pageDto = new PageImpl<>(
                Arrays.asList(new UserDTO[] {new UserDTO(0L, "name", "email",
                        true, true, null) } )
        );

        List<FilterDTO> filters = Arrays.asList(new FilterDTO("userId", "=", Arrays.asList( new String[] {"0"} )));

        String payload = jsonFilter.write(filters).getJson();

        given(userService.list(filters, pageable))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonUserPage.write(pageDto).getJson()
        );
    }

    @Test
    void create() throws Exception {
        given(userService.getOne(0L))
                .willReturn(Optional.of(new User(0L, "name", "email", "password_digest",
                        true, true, null, null, null))
                );

        UserFullDTO user = new UserFullDTO(0L, "name 2", "email 2",
                "digest", true, true);

        UserDTO userDTO = new UserDTO(0L, "name 2", "email 2",
                true, true, null);

        String payload = jsonRequestUser.write(user).getJson();

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonUser.write(userDTO).getJson()
        );
    }
}