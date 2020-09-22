package datasource;

/**
 * Interface for a ChemicalRowDataGateway
 * @author andrewjanuszko
 *
 */
public interface ChemicalRowDataGateway {
	
	public void deleteChemical() throws DatabaseException;
	
	public void updateChemical() throws DatabaseException;
	
	public int getType();
	
	public String getName();
	
	public String getInhabits();
	
	public int getAtomicNumber();
	
	public double getAtomicMass();
	
	public int getDissolvedBy();
	
	public int getSolute();
	
	public void setType(int type);
	
	public void setName(String name);
	
	public void setInhabits(String inhabits);
	
	public void setAtomicNumber(int atomicNumber);
	
	public void setAtomicMass(double atomicMass);

	public void setDissolvedBy(int dissolvedBy) throws DatabaseException;
	
	public void setSolute(int solute);
	
}