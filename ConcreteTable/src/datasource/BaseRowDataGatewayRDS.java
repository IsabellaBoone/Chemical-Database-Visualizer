package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * Row Data Gateway for Base.
 * @author Chase
 *
 */
public class BaseRowDataGatewayRDS implements BaseRowDataGateway{
  
  /**
   * Creates the table in the database. Drops the table if it already exists.
   * @throws DatabaseException
   */
	public static void createTable() throws DatabaseException{
		String drop = "DROP TABLE IF EXISTS Base";
		String create = "CREATE TABLE Base (" + 
				"baseID INT NOT NULL, " + 
				"name VARCHAR(30) NOT NULL, " +                      
				"inhabits VARCHAR(30), " +
				"solute VARCHAR(30), " + 
				"UNIQUE(name), " +
				"PRIMARY KEY(baseID)) ;";
		
		Connection conn = DatabaseManager.getSingleton().getConnection();

		try
		{
			// drop table
			PreparedStatement stmt;
			stmt = DatabaseManager.getSingleton().getConnection().prepareStatement(drop);
			stmt.execute();
			stmt.close();

			// create table
			stmt = DatabaseManager.getSingleton().getConnection().prepareStatement(create);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new DatabaseException("Unable to create Base table", e);
		}
	}
	

	private Connection conn;
	
	private int baseID;
	private String name;
	private String inhabits;
	private String solute;
	
	/**
	 * Constructs Base Row Data Gateway based off of existing row by ID.
	 * @param id
	 * @throws DatabaseException
	 */
	public BaseRowDataGatewayRDS(int id) throws DatabaseException {
		conn = DatabaseManager.getSingleton().getConnection();
		this.baseID = id;
		findByID(id);
	}
	
	/**
	 * Finds existing row by ID.
	 * @param id
	 * @throws DatabaseException
	 */
	private void findByID(int id) throws DatabaseException {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Base WHERE baseID = " + id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			name = rs.getString("name");
			inhabits = rs.getString("inhabits");
			solute = rs.getString("solute");
		} catch (SQLException e) {
			throw new DatabaseException("Couldn't find Base with that name", e);
		}
	}
	
	/**
	 * Constructs Base Row Data Gateway based off of existing row by name.
	 * @param name
	 * @throws DatabaseException
	 */
	public BaseRowDataGatewayRDS(String name) throws DatabaseException{
		conn = DatabaseManager.getSingleton().getConnection();
		this.name = name;
		findByName(name);
	}
	
	/**
	 * Finds existing row by Name.
	 * @param name
	 * @throws DatabaseException
	 */
	private void findByName(String name) throws DatabaseException{
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Base WHERE name = '" + name + "'");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			baseID = rs.getInt("baseID");
			inhabits = rs.getString("inhabits");
			solute = rs.getString("solute");
		} catch (SQLException e) {
			throw new DatabaseException("Couldn't find Base with that name", e);
		}
	}
	
	/**
	 * Constructs new Base Row Data Gateway from given parameters.
	 * @param id
	 * @param name
	 * @param inhabits
	 * @param solute
	 * @throws DatabaseException
	 */
	public BaseRowDataGatewayRDS(int id, String name, String inhabits, String solute) throws DatabaseException {
		baseID = id;
		this.name = name;
		this.inhabits = inhabits;
		this.solute = solute;
		conn = DatabaseManager.getSingleton().getConnection();
		insert();
	}

  @Override
  public int getBaseID() {
    return this.baseID;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getInhabits() {
    return this.inhabits;
  }

  @Override
  public String getSolute() {
    return this.solute;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setInhabits(String inhabits) {
    this.inhabits = inhabits;
  }

  @Override
  public void setSolute(String solute) {
    this.solute = solute;
  }
  
  /**
   * Updates the information in the database to reflect changes made.
   * @return boolean
   */
  public boolean persist() {
	  try {
  		PreparedStatement stmt = conn.prepareStatement("UPDATE Base SET"
  				+ " name = '" + name
  				+ "', inhabits = '" + inhabits
  				+ "', solute = '" + solute
  				+ "' WHERE baseID = " + baseID);
  		stmt.executeUpdate();
  		return true;
	  } catch (SQLException e) {
  		new DatabaseException("Couldn't update Base table");
  		return false;
	  }
  }
  
  /**
   * Deletes row from database.
   * @return boolean
   */
  public boolean delete() {
    try {
      PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Base WHERE baseID = " + baseID);
      stmt1.execute();
      return true;
    } catch (SQLException e) {
      new DatabaseException("could not delete acid");
      return false;
    }
  }
  
  /**
   * Inserts new row into database.
   */
  private void insert() {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Base(baseID, name, inhabits, solute) VALUES (" + baseID + ", '" + name + "', '" + inhabits + "', '" + solute + "');");
			stmt.execute();
		} catch(SQLException e) {
			new DatabaseException("could not insert into base table");
		}
	}
}

  