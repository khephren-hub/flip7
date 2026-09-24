package fr.iut.flip7.controleur

import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.vue.FenetreJoueur
import fr.iut.flip7.util.AlertManager

class ControleurDeplacerDroite(
    private val vue: FenetreJoueur,
    private val modele: GestionJoueur
) {
    fun deplacerDroite() {
        val joueur = vue.getJoueurSelectionneAGauche()
        if (joueur == null) {
            AlertManager.afficherAvertissement("Aucune sélection", "Veuillez sélectionner un joueur à gauche.")
            return
        }

        if (modele.deplacerDroite(joueur)) {
            vue.update(modele.joueursAjoutes, modele.joueursSelectionnes)
        }
    }
}