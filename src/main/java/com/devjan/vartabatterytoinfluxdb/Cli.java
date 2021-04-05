package com.devjan.vartabatterytoinfluxdb;

import com.devjan.vartabatterytoinfluxdb.VartaInterface.VartaMeasurementModel;
import org.apache.commons.cli.*;
import org.influxdb.InfluxDBException;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class Cli {
  private static final Logger LOG = Logger.getLogger(Cli.class.getName());
  public static void main(String[] args) throws ParseException, InterruptedException {
    Options options = new Options();
    options.addOption("c", "continuous", false, "Run in continuous mode");
    CommandLineParser parser = new DefaultParser();

    CommandLine cmd = parser.parse(options, args);
    if (cmd.hasOption("c")) {
      while (true) {
        String frequency = Optional.of(System.getenv("MEASUREMENT_FREQUENCY_IN_SECONDS")).orElse("10");
        int measurementFrequency = Integer.parseInt(frequency) * 1000;
        LOG.info("Run measurement in continuous mode");
        runMeasurement();
        Thread.sleep(measurementFrequency);
      }
    } else {
      runMeasurement();
    }
  }

  private static void runMeasurement() {
    LOG.info("Start measurement...");

    VartaBatteryMonitor vartaBattery = new VartaBatteryMonitor()
      .withBaseUrl(Optional.ofNullable(System.getenv("VARTA_BASE_URL")).orElse("http://localhost:8000/"))
      .withInfluxDbUrl(Optional.ofNullable(System.getenv("INFLUXDB_URL")).orElse("http://localhost:8086/"))
      .withInfluxDbBucket(Optional.ofNullable(System.getenv("INFLUXDB_BUCKET")).orElse("varta"))
      .withInfluxDbUser(Optional.ofNullable(System.getenv("INFLUXDB_USER")).orElse("admin"))
      .withInfluxDbPassword(Optional.ofNullable(System.getenv("INFLUXDB_PASSWORD")).orElse(""))
      .withMeasurementKey(Optional.ofNullable(System.getenv("MEASUREMENT_KEY")).orElse("battery"));

    try {
      VartaMeasurementModel measurement = vartaBattery.getMeasurementFrom();
      vartaBattery.sendMeasurementToInfluxDb(measurement);
      LOG.info("Measurement successfully send to InfluxDB");
    } catch (IOException | InfluxDBException e) {
      LOG.severe("Exception while creating measurement! " + e.getMessage());
    }
  }
}
