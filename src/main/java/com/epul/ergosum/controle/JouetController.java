package com.epul.ergosum.controle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class JouetController extends MultiActionController {

	private static final Logger logger = LoggerFactory
			.getLogger(JouetController.class);

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
	@RequestMapping(value = "/jouet/ajouterJouet.htm")
	public ModelAndView ajoutJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";

		try {
			// on passe les numéros de client et de vendeur
			request.setAttribute("jouet", new Jouet());
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
			request.setAttribute("catalogues", GestionCatalogue.lister());

			destinationPage = "/jouet/SaisieJouet";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
			destinationPage = "/Erreur";
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Modifier Jouet
	 */
	@RequestMapping(value = "/jouet/modifierJouet.htm")
	public ModelAndView modifierJouet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			String id = request.getParameter("id");
			
			Jouet unJouet = GestionJouet.rechercher(id);
			request.setAttribute("jouet", unJouet);
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
			destinationPage = "/jouet/SaisieJouet";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}

		return new ModelAndView(destinationPage);
	}

	/**
	 * Sauver jouet
	 */
	@RequestMapping(value = "/jouet/sauverJouet.htm")
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

			Jouet unJouet = null;
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
	@RequestMapping(value = "/jouet/effacerJouet.htm")
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

}
