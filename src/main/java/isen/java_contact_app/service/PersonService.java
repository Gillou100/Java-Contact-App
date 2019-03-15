package isen.java_contact_app.service;

import isen.java_contact_app.model.Address;
import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.UserService;
import isen.java_contact_app.util.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
//import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class PersonService{
	
	// Liste de personnes filtrés de l'utilisateur
	private ObservableList<Person> persons;

	// Liste de personnes entière de l'utilisateur
	private ObservableList<Person> personsDataBase;
	
	/*
	 * Initialisation des listes
	 */
	private PersonService() {
		personsDataBase = FXCollections.observableArrayList();
		persons = FXCollections.observableArrayList();
		/*Hashtable<Category, Boolean> categories1 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories2 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories3 = new Hashtable<>(Category.values().length);
		for (Category category : Category.values())
		{
			categories1.put(category, false);
			categories1.put(category, false);
			categories1.put(category, false);
		}
		categories1.put(Category.FAMILY, true);
		categories3.put(Category.WORK, true);*/
		for (int i = 0; i<1; i++) {
			personsDataBase.add(new Person(1, "lastname1", "firstname1", "nickname1", "0665487562", new Address("", "", "address1", "", "", -1, ""), "email_address_1", LocalDate.now(), Category.FAMILY/*categories1*/, null));
			personsDataBase.add(new Person(1, "lastname2", "firstname2", "nickname2", "0654423025", new Address("", "", "address2", "", "", -1, ""), "email_address_2", LocalDate.now(), null/*categories2*/, null));
			personsDataBase.add(new Person(1, "lastname3", "firstname3", "nickname3", "0744512325", new Address("", "", "address3", "", "", -1, ""), "email_address_3", LocalDate.now(), Category.WORK/*categories3*/, "C"));
		}
	}
	
	/*
	 * Mise à jour de la liste de personnes filtrées
	 * @param : textFilter -> texte entré comparé au surnom, nom et prénom de chaque utilisateur (+ combinaison nom/prénom dans les 2 sens)
	 * 			categoryFilter -> Categorie sélectionnée comparée à celle de chaque personne
	 */
	public static void filterPersons(String textFilter, Category categoryFilter) {
		if(PersonServiceHolder.INSTANCE.persons != null) PersonService.clearPersons();
		for (Person person : PersonServiceHolder.INSTANCE.personsDataBase) {
			if (textFilter == null || textFilter.isEmpty()
					|| person.getNickname().toLowerCase().contains(textFilter) || person.getLastName().toLowerCase().contains(textFilter) || person.getFirstName().toLowerCase().contains(textFilter)
					|| (person.getFirstName().toLowerCase() + " " + person.getLastName().toLowerCase()).contains(textFilter)
					|| (person.getLastName().toLowerCase() + " " + person.getFirstName().toLowerCase()).contains(textFilter)) {
				if (person.getCategory() != null && categoryFilter.equals(person.getCategory()) || categoryFilter.equals(Category.ALL)) {
					PersonService.addPerson(person, false);
				}
			}
		}
	}
	
	/*
	 * Retourne la liste des personnes filtrées
	 */
	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.persons;
	}
	
	/*
	 * Ajout d'une nouvelle personne à la liste
	 * @param : person -> la personne à ajouter
	 * 			newPerson -> Si la personne ajoutée l'est seulement pour la liste filtrée ou pour la database aussi
	 */
	public static void addPerson(Person person, boolean newPerson) {
		if(newPerson) PersonServiceHolder.INSTANCE.personsDataBase.add(person);
		PersonServiceHolder.INSTANCE.persons.add(person);
	}
	
	/*
	 * Supprimer une personne
	 * @param : person -> La personne a supprimé de la liste filtrée et de la database
	 */
	public static void deletePerson(Person person) {
		PersonServiceHolder.INSTANCE.personsDataBase.remove(person);
		PersonServiceHolder.INSTANCE.persons.remove(person);
	}
	
	/*
	 * Effacer la liste des personnes filtrées
	 */
	public static void clearPersons() {
		if(PersonServiceHolder.INSTANCE.persons != null) PersonServiceHolder.INSTANCE.persons.clear();
	}
	
	/*
	 * Création d'une nouvelle instance de personnes et la retourne
	 */
	public static PersonService newInstance() {
		return PersonServiceHolder.INSTANCE = new PersonService();
	}
	
	/*
	 * Changer l'instance selon l'utilisateur connectée
	 * @param : indexInstance -> Position de l'utilisateur identifiée dans la liste des utilisateurs
	 */
	public static void changeInstance(int indexInstance) {
		PersonServiceHolder.INSTANCE = UserService.getUsers().get(indexInstance).getPersonServiceInstance();
	}
	
	/*
	 * Création d'une sous-classe permettant l'utilisation d'instance
	 */
	private static class PersonServiceHolder {
		private static PersonService INSTANCE;
	}
	
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