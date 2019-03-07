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
	private String boitePostale;
	private String adresseEtendue;
	private String rue;
	private String ville;
	private String regionEtatProvince;
	private int codePostal;
	private String pays;
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
	
	/**
	 * @param boitePostale the boitePostale to set
	 */
	public void setBoitePostale(String boitePostale)
	{
		this.boitePostale = boitePostale;
	}

	/**
	 * @param adresseEtendue the adresseEtendue to set
	 */
	public void setAdresseEtendue(String adresseEtendue)
	{
		this.adresseEtendue = adresseEtendue;
	}
	
	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue)
	{
		this.rue = rue;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville)
	{
		this.ville = ville;
	}

	/**
	 * @param regionEtatProvince the regionEtatProvince to set
	 */
	public void setRegionEtatProvince(String regionEtatProvince)
	{
		this.regionEtatProvince = regionEtatProvince;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(int codePostal)
	{
		this.codePostal = codePostal;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(String pays)
	{
		this.pays = pays;
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