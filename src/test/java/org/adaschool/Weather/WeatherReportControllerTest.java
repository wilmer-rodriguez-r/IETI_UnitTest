package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class WeatherReportControllerTest {

    final String BASE_URL = "/v1/api/";
    @MockBean
    private WeatherReportService weatherReportService;
    @Autowired
    private WeatherReportController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testWhenExistLocation() throws Exception {
        //Arrange
        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setHumidity(2.0);
        weatherReport.setTemperature(1.0);
        when(weatherReportService.getWeatherReport(1.0, 2.0)).thenReturn(weatherReport);
        //Act
        //Assert
        mockMvc.perform(get(BASE_URL + "weather-report?latitude=1.0&longitude=2.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", is(1.0)))
                .andExpect(jsonPath("$.humidity", is(2.0)));

        verify(weatherReportService, times(1)).getWeatherReport(1.0, 2.0);
    }
}
