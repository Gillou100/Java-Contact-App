/**
 * 
 */
package isen.java_contact_app.model;

/**
 * @author HUBERT Gilles
 *
 */
public final class Address
{
	private final String boitePostale;
	private final String adresseEtendue;
	private final String rue;
	private final String ville;
	private final String regionEtatProvince;
	private final int codePostal;
	private final String pays;
	/**
	 * 
	 */
	public Address()
	{
		boitePostale = "";
		adresseEtendue = "";
		rue = "";
		ville = "";
		regionEtatProvince = "";
		codePostal = -1;
		pays = "";
	}
	
	public Address(String boitePostale, String adresseEtendue, String rue, String ville, String regionEtatProvince, int codePostal, String pays)
	{
		this.boitePostale = boitePostale;
		this.adresseEtendue = adresseEtendue;
		this.rue = rue;
		this.ville = ville;
		this.regionEtatProvince = regionEtatProvince;
		this.codePostal = codePostal;
		this.pays = pays;
	}
	
	/**
	 * @return the boitePostale
	 */
	public final String getBoitePostale()
	{
		return this.boitePostale;
	}
	
	/**
	 * @return the adresseEtendue
	 */
	public final String getAdresseEtendue()
	{
		return this.adresseEtendue;
	}
	
	/**
	 * @return the rue
	 */
	public final String getRue()
	{
		return this.rue;
	}
	
	/**
	 * @return the ville
	 */
	public final String getVille()
	{
		return this.ville;
	}
	
	/**
	 * @return the regionEtatProvince
	 */
	public final String getRegionEtatProvince()
	{
		return this.regionEtatProvince;
	}
	
	/**
	 * @return the codePostal
	 */
	public final int getCodePostal()
	{
		return this.codePostal;
	}
	
	/**
	 * @return the pays
	 */
	public final String getPays()
	{
		return this.pays;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString()
	{
		String string = "";
		string += getRue() + "\\n";
		string += getVille() + ", " + getRegionEtatProvince() + " " + getCodePostal() + "\\n";
		string += getPays();
		return string;
	}
}