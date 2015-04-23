package com.epul.ergosum.controle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epul.ergosum.meserreurs.MonException;
import com.epul.ergosum.metier.Catalogue;
import com.epul.ergosum.metier.Categorie;
import com.epul.ergosum.metier.Comporte;
import com.epul.ergosum.metier.ComporteId;
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

	/**
	 * Affichage de tous les jouets
	 */
	@RequestMapping(value = "/jouet/afficherJouets.htm")
	public ModelAndView afficherLesJouets(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String destinationPage = "";
		try {
			List<Jouet> jouets = new ArrayList<Jouet>();

			String numero = request.getParameter("numero");
			Jouet j = null;
			if (numero != null && !numero.trim().isEmpty()) 
			{
				request.setAttribute("numeroSearch", numero);
				j = GestionJouet.rechercher(numero);
	
				if (j == null)
				{
					request.setAttribute("messWarning", "Le jouet "+numero+" n'existe pas !");
				} else {
					jouets.add(j);
				}
			} else {
				//Categorie tranche age
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
				
				jouets = GestionJouet.lister(categorieCode, trancheCode);
			}
			
			request.setAttribute("mesJouets", jouets);
		}

		catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
			System.out.println("ERREUR ="+e.getMessage());
		}
		destinationPage = "/jouet/ListeJouets";

		return new ModelAndView(destinationPage);
	}

	/**
	 *  Recherche avancé
	 */
	@RequestMapping(value = "/jouet/rechercheAvancee.htm")
	public ModelAndView rechercheAvancee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String destinationPage = "";
		
		try {
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());

			destinationPage = "/jouet/RechercheAvancee";
		} catch (MonException e) {
			request.setAttribute("MesErreurs", e.getMessage());
			destinationPage = "/Erreur";
		}
		
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
			request.setAttribute("type", "ajout");
			
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
			request.setAttribute("type", "modif");
			
			String id = request.getParameter("id");
			
			Jouet unJouet = GestionJouet.rechercher(id);
			request.setAttribute("jouet", unJouet);
			
			request.setAttribute("categories", GestionCategorie.lister());
			request.setAttribute("tranches", GestionTrancheAge.lister());
			request.setAttribute("catalogues", GestionCatalogue.lister());
			
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

			Jouet unJouet = null;
			if (request.getParameter("type").equals("ajout"))
				unJouet = new Jouet();
			else { // on récupère le jouet courant
				unJouet = GestionJouet.rechercher(id);
			}
			
			unJouet.setNumero(request.getParameter("id"));
			unJouet.setLibelle(request.getParameter("libelle"));

			Categorie uneCateg = GestionCategorie.rechercher(request.getParameter("categorie"));
			unJouet.setCategorie(uneCateg);

			Trancheage uneTranche = GestionTrancheAge.rechercher(request.getParameter("trancheage"));
			unJouet.setTrancheage(uneTranche);
			
			//Quantite
			String strQuantite = request.getParameter("quantite");
			if (strQuantite != null)
			{
				int quantite = Integer.valueOf(strQuantite);
				
				Catalogue leCatalogue = GestionCatalogue.rechercher(request.getParameter("catalogue"));
			
			
				//create "comporte" or modif if exists
				ComporteId cid = new ComporteId(leCatalogue.getAnnee(), unJouet.getNumero());
				Comporte c = new Comporte(cid, unJouet, leCatalogue);
				c.setQuantite(quantite);
				
				Iterator<Comporte> it = unJouet.getComportes().iterator();
				 while(it.hasNext())
				 {
					 Comporte cexit = it.next();
					 if (cexit.equals(c))
					 {
						 quantite -= cexit.getQuantite();
						 break;
					 }
				 }
		
				unJouet.addComporte(c);
				 
				//update catalogue
				leCatalogue.setQuantiteDistribuee(leCatalogue.getQuantiteDistribuee()+quantite);
				GestionCatalogue.modifier(leCatalogue);
			}

			// sauvegarde du jouet
			if (request.getParameter("type").equals("modif")) {
				GestionJouet.modifier(unJouet);
				request.setAttribute("messSuccess", "Le jouet "+unJouet.getNumero()+" a bien été modifié!");
			} else {
				GestionJouet.ajouter(unJouet);
				request.setAttribute("messSuccess", "Le jouet "+unJouet.getNumero()+" a bien été ajouté!");
			}
			
			try {
				request.setAttribute("mesJouets", GestionJouet.lister());
				destinationPage = "/jouet/ListeJouets";
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
			// recuperation de la liste des id a effacer
			String[] ids = request.getParameterValues("id");
			
			// effacement de la liste des id
			try {
				if (ids != null) {
					//Enlever les quantités dans catalogues
					for (String id : ids)
					{
						Jouet j = GestionJouet.rechercher(id);
						Iterator<Comporte> it = j.getComportes().iterator();
						
						while(it.hasNext())
						{
							Comporte c = it.next();
							
							//update catalogue
							Catalogue leCatalogue = GestionCatalogue.rechercher(String.valueOf(c.getId().getAnnee()));
							leCatalogue.setQuantiteDistribuee(leCatalogue.getQuantiteDistribuee()-c.getQuantite());
							GestionCatalogue.modifier(leCatalogue);
						}
					}
					
					GestionJouet.effacer(ids);
					
					request.setAttribute("messSuccess", "Les jouets ont bien été supprimés.");
				}
				else 
				{
					request.setAttribute("messWarning", "Aucun jouet n'a été sélectionné.");
				}
				// preparation de la liste
				request.setAttribute("mesJouets", GestionJouet.lister());
			}

			catch (MonException e) {
				destinationPage = "/Erreur";
				request.setAttribute("MesErreurs", e.getMessage());
			}

			destinationPage = "/jouet/ListeJouets";
			
		} catch (Exception e) {
			destinationPage = "/Erreur";
			request.setAttribute("MesErreurs", e.getMessage());
		}
		return new ModelAndView(destinationPage);
	}

}
