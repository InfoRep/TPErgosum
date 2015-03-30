package com.epul.ergosum.controle;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Catalogue;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Jouet;
import com.epul.ergosum.metier.Trancheage;
import com.epul.ergosum.metier.gestion.GestionCatalogue;
import com.epul.ergosum.metier.gestion.GestionCategorie;
import com.epul.ergosum.metier.gestion.GestionJouet;
import com.epul.ergosum.metier.gestion.GestionTrancheAge;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MultiController extends MultiActionController {

	private static final Logger logger = LoggerFactory
			.getLogger(MultiController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	private Jouet unJouet;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "/home";
	}

	/**
	 * Affichage de tous les jouets
	 */
	@RequestMapping(value = "/jouet/afficherJouets.htm")
	public ModelAndView afficherLesJouets(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			int categorieCode;
			int trancheCode;
			String categorie = request.getParameter("codecateg");
			String tranche = request.getParameter("codetranche");
			if (categorie == null && tranche == null) {
				categorieCode = 0;
				trancheCode = 0;
			} else {
				categorieCode = Integer.parseInt(categorie);
				trancheCode = Integer.parseInt(tranche);
			}

			request.setAttribute("mesJouets", GestionJouet.lister(categorieCode, trancheCode));
			
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
		}

		catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
			System.out.println("ERREUR ="+e.getMessage());
		}
		destinationPage = "/jouet/ListeJouets";

		return new ModelAndView(destinationPage);
	}

	/**
	 * Ajout d'un jouet
	 */
	@RequestMapping(value = "ajouterJouet.htm")
	public ModelAndView ajoutJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";

		try {
			// on passe les numéros de client et de vendeur
			request.setAttribute("jouet", new Jouet());
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
			request.setAttribute("catalogues", GestionCatalogue.lister());

			destinationPage = "/AjouterJouet";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Sélection d'une année par catégorie
	 */
	@RequestMapping(value = "selectionnerAnnee.htm")
	public ModelAndView selectionAnnee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";
		destinationPage = "/selectionAnneeCat";
		return new ModelAndView(destinationPage);
	}

	/**
	 * Sélection d'une année Ctagoriet
	 */
	@RequestMapping(value = "listerCatalogue.htm")
	public ModelAndView choixCatalogue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			request.setAttribute("catalogues", GestionCatalogue.lister());
			destinationPage = "/ChoixCatalogue";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Modifier Jouet
	 */
	@RequestMapping(value = "modifierJouet.htm")
	public ModelAndView modifierJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "Erreur";
		try {
			String id = request.getParameter("id");
			
			Jouet unJouet = GestionJouet.rechercher(id);
			request.setAttribute("jouet", unJouet);
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
			destinationPage = "/ModifierJouet";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Sauver jouet
	 */
	@RequestMapping(value = "sauverJouet.htm")
	public ModelAndView sauverJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			String id = request.getParameter("id");

			// fabrication du jouet à partir des paramètres de la requête
			// Si le jouet n'est pas à créer, il faut le récupérer de la
			// session
			// courante
			// Ensuite on peut modifier ses champs

			if (request.getParameter("type").equals("ajout"))
				unJouet = new Jouet();
			else { // on récupère le jouet courant

				unJouet = GestionJouet.rechercher(id);
			}
			unJouet.setNumero(request.getParameter("id"));
			unJouet.setLibelle(request.getParameter("libelle"));
			System.out.println("codecateg="
					+ request.getParameter("codecateg"));
			System.out.println("codetranche="
					+ request.getParameter("codetranche"));
			Categorie uneCateg = GestionCategorie.rechercher(request.getParameter("codecateg"));
			unJouet.setCategorie(uneCateg);

			Trancheage uneTranche = GestionTrancheAge.rechercher(request.getParameter("codetranche"));
			unJouet.setTrancheage(uneTranche);

			// sauvegarde du jouet
			if (request.getParameter("type").equals("modif")) {
				GestionJouet.modifier(unJouet);
			} else {

				Catalogue leCatalogue = GestionCatalogue.rechercher(request
								.getParameter("codecatalogue"));
				System.out.println("Je suis à la quantité ");
				;
				int quantiteDistribution = Integer.parseInt(request
						.getParameter("quantiteDistribution"));
				if (quantiteDistribution > 0) {
					leCatalogue
							.setQuantiteDistribuee(leCatalogue
									.getQuantiteDistribuee()
									+ quantiteDistribution);
					GestionCatalogue.modifier(leCatalogue);
				}
				GestionJouet.ajouter(unJouet);
			}
			try {
				request.setAttribute("mesJouets", GestionJouet.lister());
				destinationPage = "/ListeJouets";
			} catch (MonException e) {
				request.setAttribute("MesErreurs", e.getMessage());
			}

		} catch (Exception e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * effacer jouet
	 */
	@RequestMapping(value = "effacerJouet.htm")
	public ModelAndView effacerJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			String id = request.getParameter("id");
			
			// recuperation de la liste des id a effacer
			String[] ids = request.getParameterValues("id");
			// effacement de la liste des id
			try {
				if (ids != null) {
					GestionJouet.effacer(ids);
				}
				// preparation de la liste
				request.setAttribute("mesJouets", GestionJouet.lister());
			}

			catch (MonException e) {
				request.setAttribute("MesErreurs", e.getMessage());
			}

			destinationPage = "/ListeJouets";
			
		} catch (Exception e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}
		return new ModelAndView(destinationPage);
	}

	/**
	 * afficher Catalogue
	 */
	@RequestMapping(value = "afficherCatalogues.htm")
	public ModelAndView afficherCatalogue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			String id = request.getParameter("id");
			
			// preparation de la liste
			request.setAttribute("mesCataloguesQuantites", GestionCatalogue
					.listerCatalogueQuantites(Integer.parseInt(request.getParameter("anneeDebut")),
											  Integer.parseInt(request.getParameter("nbAnnees"))));
			destinationPage = "/AfficherCatalogues";
		}

		catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * afficher le Dictionnaire
	 */
	@RequestMapping(value = "dictionnaires.htm")
	public ModelAndView afficherDictionnaire(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			String annee = request.getParameter("annee"); //annee catalogue
			
			if (annee != null)
			{ //Recherche du dictionnaire pour le catalogue choisi
				HashMap<Categorie, Integer> hashCatInt = GestionCatalogue.rechercherDictionnaire(request.getParameter("annee"));
				request.setAttribute("dictionnaire", hashCatInt);
				request.setAttribute("annee", annee);	
			} else {
				//Vue de choix du catalogue
				request.setAttribute("catalogues", GestionCatalogue.lister());
			}
			
			destinationPage = "/Dictionnaires";
		}
		catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

}
