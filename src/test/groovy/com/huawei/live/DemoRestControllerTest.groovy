package com.huawei.live

import com.huawei.live.controller.DemoRestController
import com.huawei.live.entity.User
import com.huawei.live.repository.UserRepository
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class DemoRestControllerTest extends Specification {

    MockMvc mockMvc

    List<User> expectedList

    def setup() {
        expectedList = new ArrayList<User>()
        expectedList.add(new User(
                "World",
                18
        ))
        def mockRepo = mock(UserRepository.class)
        when(mockRepo.findByName("World")).thenReturn(expectedList)
        def controller = new DemoRestController(mockRepo)
        mockMvc = standaloneSetup(controller).build()
    }

    def "should put list returned from repository"() {
        when:
        def response = mockMvc.perform(get("/users/by_name"))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath('$[0].name').value('World'))
                .andExpect(jsonPath('$[0].age').value(18))
                .andDo(print())
    }
}
