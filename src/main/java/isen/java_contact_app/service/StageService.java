package isen.java_contact_app.service;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StageService {

	/*
	 * Initialisation de la vue du mainlayout
	 */
	private StageService() {
		mainLayout = ViewService.getView("MainLayout");
	}

	/*
	 * Création de la fenêtre
	 */
	private static class StageServiceHolder {
		private static final StageService INSTANCE = new StageService();
	}
	
	private Stage primaryStage;

	private BorderPane mainLayout;

	/*
	 * Retourne l'instance du mainLayout
	 */
	public static BorderPane getMainLayoutBorderPane() {
		return StageServiceHolder.INSTANCE.mainLayout;
	}
	
	/*
	 * Retourne l'instance de la fenêtre
	 */
	public static Stage getPrimaryStage() {
		return StageServiceHolder.INSTANCE.primaryStage;
	}

	/*
	 * Initialise le contenu de la fenêtre
	 */
	public static void initPrimaryStage(Stage primaryStage) {
		primaryStage.setTitle("Contact App");
		primaryStage.setScene(new Scene(StageServiceHolder.INSTANCE.mainLayout));
		primaryStage.setResizable(false);
		primaryStage.show();

		StageServiceHolder.INSTANCE.primaryStage = primaryStage;
	}

	/*
	 * Actualiser la vue centrale visible sur la fenêtre
	 */
	public static void showView(Node rootElement) {
		StageServiceHolder.INSTANCE.mainLayout.setCenter(rootElement);
	}

	/*
	 * Fermer la fenêtre
	 */
	public static void closeStage() {
		StageServiceHolder.INSTANCE.primaryStage
				.fireEvent(new WindowEvent(StageServiceHolder.INSTANCE.primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));

	}

}
