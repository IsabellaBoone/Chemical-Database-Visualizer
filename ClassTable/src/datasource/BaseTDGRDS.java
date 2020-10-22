package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseException;
import database.DatabaseManager;

public class BaseTDGRDS implements BaseTDG {
  
  String sql = "SELECT * FROM Base INNER JOIN Chemical WHERE (Base.BaseId = Chemical.chemicalId)";
  private static BaseTDGRDS singleton;
  
  public BaseTDGRDS() {
    sql = "(SELECT * FROM Base INNER JOIN Chemical WHERE Base.BaseId = Chemical.chemicalId)";
  }
  
  public static BaseTDGRDS getSingleton() {
    if(singleton == null) {
      singleton = new BaseTDGRDS();
    }
    return singleton;
  }
  
  public BaseTDGRDS filterByName(String name) {
    sql +=  " AND (Chemical.name LIKE '%" + name + "%') ";
    return getSingleton();
  }

  @Override
  public BaseTDGRDS filterByInventory(double inventory) {
    sql += " AND (Chemical.inventory = " + inventory + ") ";
    return getSingleton();
  }

  @Override
  public BaseTDGRDS filterBySolute(int solute) {
    sql += " AND (Base.solute = " + solute + ") ";
    return getSingleton();
  }

  @Override
  public BaseTDGRDS filterByInventoryRange(double high, double low) {
    sql += " AND (Chemical.inventory BETWEEN " + low + " AND " + high + ") ";
    return getSingleton();
  }

  @Override
  public List<BaseDTO> executeQuery() throws DatabaseException {
    List<BaseDTO> listDTO = new ArrayList<>();
    try {
      PreparedStatement statement = DatabaseManager.getSingleton().getConnection().prepareStatement(this.sql + ";");
      try {
        ResultSet results = statement.executeQuery();

        while (results.next()) {
          int baseId = results.getInt("baseId");
          int soluteId = results.getInt("solute");
          String name = results.getString("name");
          double inventory = results.getDouble("inventory");
          BaseDTO base = new BaseDTO(baseId, soluteId, name, inventory);
          listDTO.add(base);
        }
        
        // Reset sql string
        sql = "SELECT * FROM Base INNER JOIN Chemical WHERE (Base.baseId = Chemical.chemicalId)";
      } catch (SQLException e) {
        throw new DatabaseException("Failed to convert query to DTO.", e);
      }
    } catch (SQLException e) {
      throw new DatabaseException("Failed to execute query.", e);
    }
    return listDTO;
  }

  @Override
  public BaseTDGRDS getAllBases() {
    sql = "SELECT * FROM Base INNER JOIN Chemical WHERE (Base.baseId = Chemical.chemicalId)";
    return getSingleton();
  }
}