package rxn.io.classic.stream.serialization;

import java.io.Serializable;

public class Employee implements Serializable {
  private static final long serialVersionUID = 1517331364702470316L;
  private final String name;
  private final int age;
  //  private final int year = 2019;

  public Employee(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }
}
