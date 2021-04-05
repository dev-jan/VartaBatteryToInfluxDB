package com.devjan.vartabatterytoinfluxdb;

import com.devjan.vartabatterytoinfluxdb.VartaInterface.VartaMeasurementModel;
import com.devjan.vartabatterytoinfluxdb.VartaInterface.VartaVariable;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class VartaBatteryMonitorTest {
  @Test
  public void testGetMeasurementFrom() throws IOException {
    // arrange
    MockWebServer mockWebServer = new MockWebServer();
    mockWebServer.enqueue(new MockResponse()
      .setResponseCode(200)
      .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
      .setBody("<root Timestamp='1616153873' id='125003033' ChargerCount='2' Description='VARTA'>\n" +
        "<inverter id='M396367'>\n" +
        "<var name='P' value='81'/>\n" +
        "<var name='SOC' value='99'/>\n" +
        "<var name='Capacity' value='8800'/>\n" +
        "<var name='State' value='1'/>\n" +
        "</inverter>\n" +
        "</root>\n")
    );
    VartaBatteryMonitor vartaBatteryMonitor = new VartaBatteryMonitor()
      .withBaseUrl(mockWebServer.url("/cgi/ems_data.xml").url().toString());

    // act
    VartaMeasurementModel result = vartaBatteryMonitor.getMeasurementFrom();

    // assert
    assertThat(result).isNotNull();

    VartaVariable varP = new VartaVariable("P", 81);
    VartaVariable varSoc = new VartaVariable("SOC", 99);
    VartaVariable varCapacity = new VartaVariable("Capacity", 8800);
    VartaVariable varState = new VartaVariable("State", 1);

    assertThat(result.getInverter().variables).containsExactly(varP, varSoc, varCapacity, varState);
  }
}
