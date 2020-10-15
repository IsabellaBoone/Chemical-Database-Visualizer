package datasource;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseException;

public interface AcidTDG {

  public void getAllAcids();
  
  public void filterByName(String name);
  
  public void filterByInventory(double inventory); 
  
  public void filterBySolute(int solute);
  
  public void filterByInventoryRange(double high, double low);
  
  public List<AcidDTO> executeQuery() throws DatabaseException;
  
}
