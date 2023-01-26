package rxn.jdbc.common.util.builder;

public class QueryBuilder {
  protected QueryBuilder() {}

  public MSSqlQueryBuilder mssql() {
    return MSSqlQueryBuilder.getInstance();
  }


}
