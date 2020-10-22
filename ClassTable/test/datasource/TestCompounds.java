package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author kimberlyoneill
 *
 */
class TestCompoundsMadeOf extends DatabaseTest {

  /**
   * Test that the getName function of compoundTDGRDS works
   */
  @Test
  static void testGetName() {
    // Fetch compounds
    CompoundRDG compound1 = new CompoundRDGRDS(29);
    CompoundRDG compound2 = new CompoundRDGRDS(30);

    // Test
    assertEquals("compoundname1", compound1.getCompound().getName());
    assertEquals("compoundname2", compound2.getCompound().getName());
  }

  /**
   * Test that the getInventory function of compoundTDGRDS works
   */
  @Test
  static void testGetInventory() {
    // Fetch compounds
    CompoundRDG compound1 = new CompoundRDGRDS(29);
    CompoundRDG compound2 = new CompoundRDGRDS(30);

    // Tests
    assertEquals(1.1, compound1.getCompound().getInventory(), 0.1);
    assertEquals(1.2, compound2.getCompound().getInventory(), 0.1);
  }

  /**
   * Run all tests in TestCompounds
   */
  static void testAll() {
    insertCompounds();
    testGetName();
    testGetInventory();
  }

  /**
   * Insert data into database to modify
   */
  private static void insertCompounds() {
    List<Integer> madeOf1 = new ArrayList<Integer>(), madeOf2 = new ArrayList<Integer>();
    madeOf1.add(24);
    madeOf1.add(25);
    madeOf2.add(26);
    madeOf2.add(27);

    CompoundRDG RDG1 = new CompoundRDGRDS(madeOf1, "compoundname1", 1.1); 
    CompoundRDG RDG2 = new CompoundRDGRDS(madeOf2, "compoundname2", 1.2); 
  }
  
  
}