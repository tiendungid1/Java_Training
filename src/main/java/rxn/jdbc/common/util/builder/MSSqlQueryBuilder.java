package rxn.jdbc.common.util.builder;

import java.util.Objects;

public class MSSqlQueryBuilder extends QueryBuilder {
  private static MSSqlQueryBuilder instance;

  private MSSqlQueryBuilder() {}

  public static MSSqlQueryBuilder getInstance() {
    if (Objects.isNull(instance)) {
      instance = new MSSqlQueryBuilder();
    }
    return instance;
  }
}
