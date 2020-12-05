package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseException;
import datasource.BaseDTO;
import datasource.BaseRDG;
import datasource.BaseRDGRDS;
import datasource.BaseTDGRDS;

/**
 * Maps BaseDataMapperInterface functions to class table implementation.
 * 
 * @author Isabella Boone, Kim O'Neill
 *
 */
public class BaseDataMapper implements BaseDataMapperInterface {
  @Override
  public Base create(String name, double inventory, Chemical solute) throws DomainModelException {
    BaseRDG row = new BaseRDGRDS(solute.getID(), name, inventory, solute.getName());
    // Use BaseRDG to create base

    return convertFromDTO(row.getBase());
    // Convert DTO to Base and return it
  }

  @Override
  public Base read(int id) throws DomainModelException {
    try {
      BaseRDG row = new BaseRDGRDS(id);
      // Use BaseRDG to fetch base

      return convertFromDTO(row.getBase());
      // Convert DTO to Base and return it
    } catch (SQLException | DatabaseException e) {
      throw new DomainModelException("Failed to fetch Base with id = " + id, e);
    }
  }

  @Override
  public void update(Base base) {
    try {
      BaseRDG row = new BaseRDGRDS(base.getID());
      // Use BaseRDG to fetch Base

      // Set new values with setters
      row.setName(base.getName());
      row.setInventory(base.getInventory());
      row.setSolute(base.getSolute().getID());
      row.setSoluteType(base.getSolute().getName());

      // Update
      row.update();
    } catch (SQLException | DatabaseException e) {
      System.out.println("Failed to update");
      e.printStackTrace();
    }

  }

  @Override
  public void delete(Base base) {
    try {
      BaseRDG row = new BaseRDGRDS(base.getID());
      // Use BaseRDG to fetch Base

      row.delete();
      // Delete it
    } catch (SQLException | DatabaseException e) {
      e.printStackTrace();
    }

  }

  @Override
  public List<Base> getAll() {
    ArrayList<Base> base = new ArrayList<>();
    try {
      // Get all bases
      List<BaseDTO> dtos = BaseTDGRDS.getSingleton().executeQuery();

      // For every basedto, convert it to a base and add it to list of bases
      for (BaseDTO b : dtos) {
        base.add(convertFromDTO(b));
      }
    } catch (DatabaseException e) {
      // Problem with executeQuery()
      e.printStackTrace();
    }

    return base;
  }

  @Override
  public List<Base> filterByNameLike(String wildCardName) {
    ArrayList<Base> base = new ArrayList<>();
    try {
      // Get all bases with specific name
      List<BaseDTO> dtos = BaseTDGRDS.getSingleton().filterByName(wildCardName).executeQuery();

      // For every BaseDTO, convert it to a base and add it to the list of bases
      for (BaseDTO b : dtos) {
        base.add(convertFromDTO(b));
      }
    } catch (DatabaseException e) {
      // Problem with executeQuery
      e.printStackTrace();
    }

    return base;
  }

  @Override
  public List<Base> filterByInventory(double inventory) {
    ArrayList<Base> base = new ArrayList<>();
    try {
      // Get all bases with speicfic inventory amount
      List<BaseDTO> dtos = BaseTDGRDS.getSingleton().filterByInventory(inventory).executeQuery();

      // For every BaseDTO, convert it to a base and add it to the list of bases
      for (BaseDTO b : dtos) {
        base.add(convertFromDTO(b));
      }
    } catch (DatabaseException e) {
      // Problem with executeQuery
      e.printStackTrace();
    }

    return base;
  }

  @Override
  public List<Base> filterByInventoryBetween(double min, double max) {
    ArrayList<Base> base = new ArrayList<>();
    try {
      // Get all bases with a specific inventory range
      List<BaseDTO> dtos = BaseTDGRDS.getSingleton().filterByInventoryRange(max, min).executeQuery();

      // For every BaseDTO, convert it to a base and add it to the list of bases
      for (BaseDTO b : dtos) {
        base.add(convertFromDTO(b));
      }
    } catch (DatabaseException e) {
      // Problem with executeQuery
      e.printStackTrace();
    }

    return base;
  }

  @Override
  public List<Base> filterBySolute(int chemicalID) {
    List<Base> base = new ArrayList<>();
    try {
      // Get all bases with specific solute id
      List<BaseDTO> dtos = BaseTDGRDS.getSingleton().filterBySolute(chemicalID).executeQuery();

      // For every BaseDTO, convert it to a base and add it to the list of bases
      for (BaseDTO b : dtos) {
        base.add(convertFromDTO(b));
      }
    } catch (DatabaseException e) {
      // Problem with executeQuery
      e.printStackTrace();
    }

    return base;
  }

  /**
   * Fetches a solute by it's type
   * 
   * @param s Solute Type
   * @param i ID
   * @return Solute
   */
  private Chemical soluteType(String s, int i) {
    System.out.println(s + " " + i);
    // very possible there is infinite loading shenanigans
    try {
      if (s.toLowerCase().contains("acid")) {
        AcidDataMapper m = new AcidDataMapper();
        return m.read(i);
      } else if (s.toLowerCase().contains("base")) {
        BaseDataMapper m = new BaseDataMapper();
        return m.read(i);
      } else if (s.toLowerCase().contains("compound")) {
        CompoundDataMapper m = new CompoundDataMapper();
        return m.read(i);
      } else if (s.toLowerCase().contains("element")) {
        ElementDataMapper m = new ElementDataMapper();
        return m.read(i);
      } else if (s.toLowerCase().contains("metal")) {
        BaseDataMapper m = new BaseDataMapper();
        return m.read(i);
      } else {
        return null; 
      }

    } catch (DomainModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Convert a dto to a base
   * 
   * @param dto to convert
   * @return converted Base
   */
  private Base convertFromDTO(BaseDTO dto) {
    return new Base(dto.getBaseId(), dto.getName(), dto.getInventory(), soluteType(dto.getSoluteType(), dto.getSoluteId()));
  }

  @Override
  public List<Base> filterByLowInventory() throws DomainModelException {
    return null;
  }

}
