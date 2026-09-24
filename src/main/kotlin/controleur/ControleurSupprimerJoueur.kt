package fr.iut.flip7.controleur

import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.vue.FenetreJoueur
import fr.iut.flip7.util.AlertManager

class ControleurSupprimerJoueur(
    private val vue: FenetreJoueur,
    private val modele: GestionJoueur
) {
    fun supprimerJoueur() {
        val joueur = vue.getJoueurSelectionneAGauche()
        if (joueur == null) {
            AlertManager.afficherAvertissement("Aucune sélection", "Veuillez sélectionner un joueur dans la liste de gauche.")
            return
        }

        if (AlertManager.confirmerSuppression(joueur.nom)) {
            if (modele.supprimerJoueur(joueur)) {
                vue.update(modele.joueursAjoutes, modele.joueursSelectionnes)
            }
        }
    }
}