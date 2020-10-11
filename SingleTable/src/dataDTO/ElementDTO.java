package dataDTO;

public final class ElementDTO {

  private final int chemicalID;
  private final int type;
  private final String name;
  private final double inventory;
  private final int atomicNumber;
  private final double atomicMass;

  /**
   * Create a chemical DTO
   * 
   * @param chemicalID   the ID of the Chemical.
   * @param type         the type of the Chemical.
   * @param name         the name of the Chemical.
   * @param inventory    the inventory of the Chemical.
   * @param atomicNumber the atomicNumber of the Chemical.
   * @param atomicMass   the atomicMass of the Chemical.
   */
  public ElementDTO(int chemicalID, int type, String name, double inventory, int atomicNumber, double atomicMass) {
    this.chemicalID = chemicalID;
    this.type = type;
    this.name = name;
    this.inventory = inventory;
    this.atomicNumber = atomicNumber;
    this.atomicMass = atomicMass;
  }

  /**
   * @return the chemicalID
   */
  public int getChemicalID() {
    return chemicalID;
  }

  /**
   * @return the type
   */
  public int getType() {
    return type;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the inventory
   */
  public double getInventory() {
    return inventory;
  }

  /**
   * @return the atomicNumber
   */
  public int getAtomicNumber() {
    return atomicNumber;
  }

  /**
   * @return the atomicMass
   */
  public double getAtomicMass() {
    return atomicMass;
  }

}
