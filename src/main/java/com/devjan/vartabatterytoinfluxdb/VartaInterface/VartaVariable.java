package com.devjan.vartabatterytoinfluxdb.VartaInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "var")
@XmlAccessorType(XmlAccessType.NONE)
public class VartaVariable {
  @XmlAttribute(name = "name")
  private String name;
  @XmlAttribute(name = "value")
  private int value;

  public VartaVariable() {}

  public VartaVariable(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Variable{" +
      "name='" + name + '\'' +
      ", value=" + value +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VartaVariable that = (VartaVariable) o;
    return value == that.value && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }
}
