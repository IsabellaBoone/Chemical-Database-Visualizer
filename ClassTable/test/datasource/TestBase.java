package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Isabella Boone
 *
 */
class TestBase extends DatabaseTest {

  @Test
  static void testGetName() throws SQLException, DatabaseException {    
    BaseRDG
        base1 = new BaseRDGRDS(11), 
        base2 = new BaseRDGRDS(12),
        base3 = new BaseRDGRDS(13),
        base4 = new BaseRDGRDS(14);
    
    assertEquals("basename1", base1.getName());
    assertEquals("basename2", base2.getName());
    assertEquals("basename3", base3.getName());
    assertEquals("basename4", base4.getName());
  }
  
  @Test
  static void testGetInhabits() throws SQLException, DatabaseException {
    BaseRDG 
        base1 = new BaseRDGRDS(11), 
        base2 = new BaseRDGRDS(12),
        base3 = new BaseRDGRDS(13),
        base4 = new BaseRDGRDS(14);

    assertEquals("baseinhabits1", base1.getInhabits());
    assertEquals("baseinhabits2", base2.getInhabits());
    assertEquals("baseinhabits3", base3.getInhabits());
    assertEquals("baseinhabits4", base4.getInhabits());
  }
  
  @Test
  static void testGetSolute() throws SQLException, DatabaseException {
    BaseRDG 
        base1 = new BaseRDGRDS(11), 
        base2 = new BaseRDGRDS(12),
        base3 = new BaseRDGRDS(13),
        base4 = new BaseRDGRDS(14);

    assertEquals(51, base1.getSolute());
    assertEquals(52, base2.getSolute());
    assertEquals(53, base3.getSolute());
    assertEquals(54, base4.getSolute());
  }
  
  @Test
  static void testUpdate() throws SQLException, DatabaseException {
    BaseRDG base_setter = new BaseRDGRDS(19, 59, "basename9", "baseinhabits9"),
        base_getter = new BaseRDGRDS(19);
    
    assertEquals("basename9", base_getter.getName());
    assertEquals("baseinhabits9", base_getter.getInhabits());
    assertEquals(59, base_getter.getSolute());
    
    base_setter.setName("basename6");
    base_setter.setInhabits("baseinhabits6");
    base_setter.setSolute(56);
    base_setter.update();
    
    base_getter = new BaseRDGRDS(19); 
    
    assertEquals("basename6", base_getter.getName());
    assertEquals("baseinhabits6", base_getter.getInhabits());
    assertEquals(56, base_getter.getSolute());
    
    base_getter.delete();
  }
  
  @Test
  static void testDelete() {
    BaseRDG base = new BaseRDGRDS(19, 59, "basename9", "baseinhabits9");
    
    assertEquals("basename9", base.getName());
    assertEquals("baseinhabits9", base.getInhabits());
    
    base.delete();
    
    try { 
      base = new BaseRDGRDS(1);
      fail("");
    } catch(DatabaseException | SQLException e) {
      assertTrue(true); 
    }
  }
  
  @Test
  static void testGetSet() {
    BaseRDG getter = new BaseRDGRDS();
    List<BaseRDGRDS> baseGet = getter.findSet(55);
    
    assertEquals("basename5", baseGet.get(0).getName());
    assertEquals("basename6", baseGet.get(1).getName());    
  }
  
  static void testAll() {
    try {
      insertBases();
      testGetName();
      testGetInhabits();
      testGetSolute();
      testDelete();
      testUpdate();
      testGetSet(); 
    } catch (SQLException | DatabaseException e) {
      e.printStackTrace();
    }
  }
  
  private static void insertBases() {
    BaseRDG base = new BaseRDGRDS(11, 51, "basename1", "baseinhabits1");
    base = new BaseRDGRDS(12, 52, "basename2", "baseinhabits2");
    base = new BaseRDGRDS(13, 53, "basename3", "baseinhabits3");
    base = new BaseRDGRDS(14, 54, "basename4", "baseinhabits4");
    base = new BaseRDGRDS(15, 55, "basename5", "baseinhabits5");
    base = new BaseRDGRDS(16, 55, "basename6", "baseinhabits6");
    
  }
}
