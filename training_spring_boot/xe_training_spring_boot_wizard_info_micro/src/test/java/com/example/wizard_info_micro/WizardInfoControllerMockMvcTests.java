package com.example.wizard_info_micro;

import com.example.wizard_info_micro.controller.WizardInfoController;
import com.example.wizard_info_micro.dto.WizardInfoRequestDto;
import com.example.wizard_info_micro.dto.WizardInfoResponseDto;
import com.example.wizard_info_micro.entity.WizardInfo;
import com.example.wizard_info_micro.service.WizardInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example.wizard_info_micro")
@SpringBootTest(classes = {WizardInfoControllerMockMvcTests.class})
public class WizardInfoControllerMockMvcTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    // To test the methods
    @InjectMocks
    WizardInfoController wizardInfoController;

    // To test the function within the method
    @Mock
    WizardInfoService wizardInfoService;

    List<WizardInfo> allWizardInfo;

    WizardInfo wizardInfo;

    // Before starting each test method will run this first
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wizardInfoController).build();
    }

    @Test
    @Order(1)
    public void test_findAllWizardInfo() throws Exception {
        allWizardInfo = new ArrayList<WizardInfo>();
        allWizardInfo.add(new WizardInfo(UUID.randomUUID(), "wizard a", 19, String.valueOf(java.time.LocalDate.now()), true));
        allWizardInfo.add(new WizardInfo(UUID.randomUUID(), "wizard b", 29, String.valueOf(java.time.LocalDate.now()), true));
        List<WizardInfoResponseDto> expected = allWizardInfo.stream().map(wizardInfo -> modelMapper.map(wizardInfo, WizardInfoResponseDto.class)).collect(Collectors.toList());
        when(wizardInfoService.getAllWizardInfo()).thenReturn(expected);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/wizard-info/find-all")).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<WizardInfoResponseDto> wizardInfoResponseDtoList = Arrays.asList(new ObjectMapper().readValue(contentAsString, WizardInfoResponseDto[].class));
        assertThat(wizardInfoResponseDtoList).isEqualTo(expected);
    }

    @Test
    @Order(2)
    public void test_findWizardInfoById() throws Exception {
        UUID sampleWizardInfoId = UUID.randomUUID();
        wizardInfo = new WizardInfo(sampleWizardInfoId, "wizard c", 39, String.valueOf(java.time.LocalDate.now()), true);
        WizardInfoResponseDto expected = modelMapper.map(wizardInfo, WizardInfoResponseDto.class);
        when(wizardInfoService.getWizardInfoById(sampleWizardInfoId.toString())).thenReturn(expected);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/wizard-info/find-id/{id}", sampleWizardInfoId.toString())).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        WizardInfoResponseDto wizardInfoResponseDto = new ObjectMapper().readValue(contentAsString, WizardInfoResponseDto.class);
        assertThat(wizardInfoResponseDto).isEqualTo(expected);
    }

    @Test
    @Order(3)
    public void test_addWizardInfo() throws Exception {
        wizardInfo = new WizardInfo(UUID.randomUUID(), "wizard d", 49, String.valueOf(java.time.LocalDate.now()), true);
        WizardInfoRequestDto request = modelMapper.map(wizardInfo, WizardInfoRequestDto.class);
        WizardInfoResponseDto expected = modelMapper.map(wizardInfo, WizardInfoResponseDto.class);
        when(wizardInfoService.saveWizardInfo(request)).thenReturn(expected);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/wizard-info/add").content(jsonBody).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        WizardInfoResponseDto wizardInfoResponseDto = new ObjectMapper().readValue(contentAsString, WizardInfoResponseDto.class);
        assertThat(wizardInfoResponseDto).isEqualTo(expected);
    }

    @Test
    @Order(4)
    public void test_changeWizardInfoById() throws Exception {
        UUID sampleWizardInfoId = UUID.randomUUID();
        wizardInfo = new WizardInfo(sampleWizardInfoId, "wizard e", 59, String.valueOf(java.time.LocalDate.now()), true);
        WizardInfoRequestDto request = modelMapper.map(wizardInfo, WizardInfoRequestDto.class);
        WizardInfoResponseDto expected = modelMapper.map(wizardInfo, WizardInfoResponseDto.class);
        when(wizardInfoService.updateWizardInfoById(sampleWizardInfoId.toString(), request)).thenReturn(expected);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/wizard-info/update-id/{id}", sampleWizardInfoId.toString()).content(jsonBody).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        WizardInfoResponseDto wizardInfoResponseDto = new ObjectMapper().readValue(contentAsString, WizardInfoResponseDto.class);
        assertThat(wizardInfoResponseDto).isEqualTo(expected);
    }

    @Test
    @Order(5)
    public void test_removeWizardInfoById() throws Exception {
        UUID sampleWizardInfoId = UUID.randomUUID();
        wizardInfo = new WizardInfo(sampleWizardInfoId, "wizard f", 69, String.valueOf(java.time.LocalDate.now()), true);
        String expected = "Wizard info has been deleted successfully !\tId: " + sampleWizardInfoId.toString();
        when(wizardInfoService.deleteWizardInfo(sampleWizardInfoId.toString())).thenReturn(expected);
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/wizard-info/delete-id/{id}", sampleWizardInfoId.toString())).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(expected);
    }
}
