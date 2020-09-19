package datasource;

import java.sql.Statement;

public class ElementRowDataGateway {
  public void createTableElement() {
    String dropTable = "DROP TABLE IF EXISTS Element;";
    String createTable = "CREATE TABLE Element" + "(" 
        + "elementId INT NOT NULL, "
        + "atomicNumber INT, " 
        + "atomicMass DOUBLE, " 
        + "FOREIGN KEY(elementId) REFERENCES Chemical(chemicalId)"
        + ");";
    
    try {
      Statement statement = DatabaseManager.getSingleton().getConnection().createStatement();
      // Drop the table if exists first
      statement.executeUpdate(dropTable);
      // Create new Monitorings Table
      statement.executeUpdate(createTable);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}