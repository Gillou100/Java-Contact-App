package isen.java_contact_app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import isen.java_contact_app.entities.Address;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.util.Category;

public class VcardService {
	 /*
	 * Export les données des personnes de la liste de personnes filtrées
	 * @param : directory -> chemin absolu vers le dossier où ajouter la fiche de la personne
	 * 			Person -> personne dont la fiche doit être créé
	 */
	public static final void export(File directory, Person person)
	{
		File file = new File(directory, person.getFirstName() + "_" + person.getLastName() + ".vcf");
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file.toString()), "UTF-8"))
		{
			writer.write(person.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Récupération des informations de la fiche d'une personne et retourne la personne créé
	 * @param : file -> fichier de la personne
	 */
	public static final Person importFile(File file) throws IOException
	{
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		Iterator<String> line = lines.iterator();
		
		if(!line.hasNext())
		{
			new IOException("The file " + file + " is empty.");
		}
		
		int id = -1;
		String lastname = null;
		String firstname = null;
		String nickname = null;
		String phone_number = null;
		Address address = null;
		String email_address = null;
		LocalDate birth_date = null;
		String picture = null;
		Category category = null;
		/*Hashtable<Category, Boolean> categories = new Hashtable<>(Category.values().length);
		for (Category categorie : Category.values())
		{
			categories.put(categorie, false);
		}*/
		
		while(line.hasNext())
		{
			String data = line.next();
			if(data.equals("BEGIN:VCARD") || data.regionMatches(0, "FN:", 0, 3))
			{
				continue;
			}
			else if(data.equals("END:VCARD"))
			{
				break;
			}
			
			else
			{
				String[] dataSeperate = data.split(":");
				switch (dataSeperate[0])
				{
					case "VERSION":
						if(!dataSeperate[1].equals("4.0"))
						{
							new IOException("The file " + file + " is in a wrong version.");
						}
						break;
					case "PHOTO":
						picture = dataSeperate[1] + ":" + dataSeperate[2] + ":" + dataSeperate[3];
						break;
					case "UID":
						id = Integer.parseInt(dataSeperate[1]);
						break;
					case "N":
						dataSeperate = dataSeperate[1].split(";");
						lastname = dataSeperate[0];
						firstname = dataSeperate[1];
						break;
					case "NICKNAME":
						nickname = dataSeperate[1];
						break;
					case "EMAIL":
						email_address = dataSeperate[1];
						break;
					case "BDAY":
						birth_date = LocalDate.parse(dataSeperate[1]);
						break;
					case "CATEGORIES":
						category = Category.valueOf(dataSeperate[1]);
						/*String[] categoriesString = dataSeperate[1].split(";");
						for(String category: categoriesString)
						{
							if(Category.valueOf(category) != null)
							{
								categories.put(Category.valueOf(category), true);
							}
						}*/
						break;
				}
				
				dataSeperate = data.split(";");
				switch (dataSeperate[0])
				{
					case "TEL":
						phone_number = dataSeperate[2].substring("VALUE=uri:tel:".length());
						break;
					case "ADR":
						String pays = "";
						int codePostal = -1;
						String regionEtatProvince = "";
						String ville = "";
						String rue = "";
						String adresseEtendue = "";
						String boitePostale = "";
						switch(dataSeperate.length)
						{
							case 9:
								pays = dataSeperate[8];
							case 8:
								codePostal = Integer.parseInt(dataSeperate[7]);
							case 7:
								regionEtatProvince = dataSeperate[6];
							case 6:
								ville = dataSeperate[5];
							case 5:
								rue = dataSeperate[4];
							case 4:
								adresseEtendue = dataSeperate[3];
							case 3:
								boitePostale = dataSeperate[2].split(":").length > 1 ? dataSeperate[2].split(":")[1] : "";
						}
						address = new Address(boitePostale, adresseEtendue, rue, ville, regionEtatProvince, codePostal, pays);
						break;
				}
			}
		}
		return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date, category, picture);
		//return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date, categories, picture);
	}
	
}

