package com.devjan.vartabatterytoinfluxdb.VartaInterface;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class VartaMeasurementModel {
  private VartaInverter inverter;

  public VartaInverter getInverter() {
    return inverter;
  }
  public void setInverter(VartaInverter inverter) {
    this.inverter = inverter;
  }

  @Override
  public String toString() {
    return "VartaMeasurementModel{" +
      "inverter=" + inverter +
      '}';
  }
}
