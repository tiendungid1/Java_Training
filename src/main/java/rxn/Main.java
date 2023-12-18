package rxn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Animal {
  private String name;
}

public class Main {
  public static void main(String[] args) {
    Animal animal = new Animal(StringUtils.capitalize("doggge"));
    System.out.println(animal.getName());
  }
}
