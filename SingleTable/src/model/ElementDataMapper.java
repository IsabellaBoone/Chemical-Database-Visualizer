package model;

import java.util.ArrayList;
import java.util.List;

import dataDTO.ChemicalDTO;
import dataENUM.ChemicalEnum;
import datasource.ChemicalRowDataGatewayRDS;
import datasource.ChemicalTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.ElementCompoundTableDataGatewayRDS;

/**
 * A mapper for Element objects.
 * 
 * @author andrewjanuszko
 *
 */
public class ElementDataMapper implements ElementDataMapperInterface {

  private ChemicalTableDataGatewayRDS chemicalTableDataGateway;
  private ElementCompoundTableDataGatewayRDS ecTableDataGateway;

  /**
   * An empty Constructor for ElementDataMapper.
   */
  public ElementDataMapper() {
    // EMPTY
  }

  /**
   * @see model.ElementDataMapperInterface#create(String, double, int, double).
   */
  @Override
  public Element create(String name, double inventory, int atomicNumber, double atomicMass)
      throws DomainModelException {
    try {
      ChemicalRowDataGatewayRDS row = new ChemicalRowDataGatewayRDS(ChemicalEnum.ELEMENT.getIntValue(), name, inventory,
          atomicNumber, atomicMass, 0, 0, 0);
      return new Element(row.getID(), name, inventory, atomicNumber, atomicMass);
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to create an Element.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#read(int).
   */
  @Override
  public Element read(int id) throws DomainModelException {
    try {
      ChemicalRowDataGatewayRDS row = new ChemicalRowDataGatewayRDS(id);
      return new Element(row.getID(), row.getName(), row.getInventory(), row.getAtomicNumber(), row.getAtomicMass());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to find an Element with ID '" + id + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#update(Element).
   */
  @Override
  public void update(Element element) throws DomainModelException {
    try {
      ChemicalRowDataGatewayRDS row = new ChemicalRowDataGatewayRDS(element.getID());
      row.setName(element.getName());
      row.setInventory(element.getInventory());
      row.setAtomicNumber(element.getAtomicNumber());
      row.setAtomicMass(element.getAtomicMass());
      row.update();
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to update an Element with ID '" + element.getID() + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#delete(Element).
   */
  @Override
  public void delete(Element element) throws DomainModelException {
    try {
      ChemicalRowDataGatewayRDS row = new ChemicalRowDataGatewayRDS(element.getID());
      row.delete();
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to delete an Element with ID '" + element.getID() + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#getAll().
   */
  @Override
  public List<Element> getAll() throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Elements.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByWildCardName(String).
   */
  @Override
  public List<Element> filterByNameLike(String nameLike) throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().filterByNameLike(nameLike).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Elements with a partial name match of '" + nameLike + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByInventory(double).
   */
  @Override
  public List<Element> filterByInventory(double inventory) throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().filterByInventory(inventory).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Elements with an inventory value of '" + inventory + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByInventoryRange(double, double).
   */
  @Override
  public List<Element> filterByInventoryBetween(double min, double max) throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().filterByInventoryBetween(min, max).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException(
          "Failed to get all Elements with inventory between '" + min + "' < x < '" + max + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByAtomicNumber(int).
   */
  @Override
  public List<Element> filterByAtomicNumber(int atomicNumber) throws DomainModelException {
    try {
      return convertToElement(
          chemicalTableDataGateway.getElements().filterByAtomicNumber(atomicNumber).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Elements with atomic number of '" + atomicNumber + "'.", e);
    }
  }
  
  /**
   * 
   */
  @Override
  public List<Element> filterByAtomicNumberBetween(int min, int max) throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().filterByAtomicNumberBetween(min, max).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Element with atomic number between '" + min + "' < x < '" + max + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByAtomicMass(double).
   */
  @Override
  public List<Element> filterByAtomicMass(double atomicMass) throws DomainModelException {
    try {
      return convertToElement(
          chemicalTableDataGateway.getElements().filterByAtomicMass(atomicMass).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get all Elements with atomic mass of '" + atomicMass + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByAtomicMassRange(double,
   *      double).
   */
  @Override
  public List<Element> filterByAtomicMassBetween(double min, double max) throws DomainModelException {
    try {
      return convertToElement(chemicalTableDataGateway.getElements().filterByAtomicMassBetween(min, max).executeQuery());
    } catch (DatabaseException e) {
      throw new DomainModelException(
          "Failed to get all Element with atomic mass between '" + min + "' < x < '" + max + "'.", e);
    }
  }

  /**
   * @see model.ElementDataMapperInterface#filterByPartOfCompound(int).
   */
  @Override
  public List<Element> filterByPartOfCompound(int compoundID) throws DomainModelException {
    try {
      return convertToElement(ecTableDataGateway.findElementsByCompoundID(compoundID).getRelations());
    } catch (DatabaseException e) {
      throw new DomainModelException("Failed to get Elements in Compound with ID '" + compoundID + "'.", e);
    }
  }

  /**
   * Converts ChemicalDTOs to a List of Elements.
   * 
   * @param chemicals the ChemicalDTOs.
   * @return a List of Elements.
   * @throws DomainModelException when things go wrong.
   */
  private List<Element> convertToElement(List<ChemicalDTO> chemicals) throws DomainModelException {
    List<Element> elements = new ArrayList<>();
    for (ChemicalDTO dto : chemicals) {
      elements
          .add(new Element(dto.getID(), dto.getName(), dto.getInventory(), dto.getAtomicNumber(), dto.getAtomicMass()));
    }
    return elements;
  }

}
