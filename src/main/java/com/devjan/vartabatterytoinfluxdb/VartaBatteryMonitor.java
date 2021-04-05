package com.devjan.vartabatterytoinfluxdb;

import com.devjan.vartabatterytoinfluxdb.VartaInterface.VartaMeasurementEndpoint;
import com.devjan.vartabatterytoinfluxdb.VartaInterface.VartaMeasurementModel;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class VartaBatteryMonitor {
  private static final Logger LOG = Logger.getLogger(VartaBatteryMonitor.class.getCanonicalName());
  private String baseUrl;
  private String influxDbUrl;
  private String influxDbBucket;
  private String measurementKey;
  private String influxDbUsername;
  private String influxDbPassword;

  public VartaMeasurementModel getMeasurementFrom() throws IOException {
    VartaMeasurementEndpoint endpoint = new VartaMeasurementEndpoint();
    return endpoint.getMeasurements(this.baseUrl).block();
  }

  public void sendMeasurementToInfluxDb(VartaMeasurementModel measurement) {
    InfluxDB influxDB = InfluxDBFactory.connect(this.influxDbUrl, this.influxDbUsername, this.influxDbPassword);
    influxDB.setDatabase(this.influxDbBucket);

    Point.Builder pointBuilder = Point.measurement(this.measurementKey)
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

    measurement.getInverter().variables.stream()
      .peek(System.out::println)
      .forEach(variable -> pointBuilder.addField(variable.getName(), variable.getValue()));

    influxDB.write(pointBuilder.build());
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public VartaBatteryMonitor withBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  public VartaBatteryMonitor withInfluxDbUrl(String influxDbUrl) {
    this.influxDbUrl = influxDbUrl;
    return this;
  }
  public VartaBatteryMonitor withInfluxDbBucket(String influxDbBucket) {
    this.influxDbBucket = influxDbBucket;
    return this;
  }
  public VartaBatteryMonitor withInfluxDbUser(String influxDbUsername) {
    this.influxDbUsername = influxDbUsername;
    return this;
  }
  public VartaBatteryMonitor withInfluxDbPassword(String influxDbPassword) {
    this.influxDbPassword = influxDbPassword;
    return this;
  }
  public VartaBatteryMonitor withMeasurementKey(String measurementKey) {
    this.measurementKey = measurementKey;
    return this;
  }
}
