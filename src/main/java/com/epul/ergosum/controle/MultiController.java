package com.epul.ergosum.controle;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.epul.ergosum.metier.CatalogueQuantites;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.gestion.GestionCatalogue;
import com.epul.ergosum.metier.gestion.GestionCategorie;

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
	 * Sélection d'une année par catalogue et interval
	 */
	@RequestMapping(value = "/catalogue/selectionnerAnnee.htm")
	public ModelAndView selectionAnnee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "";
		try {
			request.setAttribute("catalogues", GestionCatalogue.lister());
			
			//aff cat select
			boolean cat = (request.getParameter("cat") != null);
			request.setAttribute("cat", cat);
			
			if (cat)
			{
				request.setAttribute("categories", GestionCategorie.lister());
			}
			
			destinationPage = "/catalogue/SelectionnerAnnee";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
		}	
		
		return new ModelAndView(destinationPage);
	}

	/**
	 * afficher Catalogue
	 */
	@RequestMapping(value = "/catalogue/afficherCatalogues.htm")
	public ModelAndView afficherCatalogue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String destinationPage = "/Erreur";
		try {
			int annee = Integer.valueOf(request.getParameter("annee"));
			int interval = Integer.valueOf(request.getParameter("interval"));
			
			List<CatalogueQuantites> list = null;
			if (interval > 0)
			{
				request.setAttribute("annee", annee);
				request.setAttribute("anneefin", annee+interval);
				request.setAttribute("interval", interval);
				
				list = GestionCatalogue.listerCatalogueQuantites(annee, interval, request.getParameter("categorie"));
				
				if (request.getParameter("categorie") != null)
				{
					request.setAttribute("categorie", GestionCategorie.rechercher(request.getParameter("categorie")));
				}
				request.setAttribute("catQuantites", list);
			} else {
				list = new ArrayList<CatalogueQuantites>();
				request.setAttribute("messErreur", "L'interval doit être positif !");
			}
						
			destinationPage = "/catalogue/AfficherCataloguesInterval";
		}
		catch (Exception e) {
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
