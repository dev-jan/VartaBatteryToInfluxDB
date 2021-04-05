package com.devjan.vartabatterytoinfluxdb.VartaInterface;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "inverter")
public class VartaInverter {
  @XmlElement(name = "var")
  public Collection<VartaVariable> variables;

  @Override
  public String toString() {
    return "Inverter{" +
      "variables=" + variables +
      '}';
  }

}
