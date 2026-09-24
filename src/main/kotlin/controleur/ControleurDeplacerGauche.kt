package fr.iut.flip7.controleur

import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.vue.FenetreJoueur
import fr.iut.flip7.util.AlertManager

class ControleurDeplacerGauche(
    private val vue: FenetreJoueur,
    private val modele: GestionJoueur
) {
    fun deplacerGauche() {
        val joueur = vue.getJoueurSelectionneADroite()
        if (joueur == null) {
            AlertManager.afficherAvertissement("Aucune sélection", "Veuillez sélectionner un joueur à droite.")
            return
        }

        if (modele.deplacerGauche(joueur)) {
            vue.update(modele.joueursAjoutes, modele.joueursSelectionnes)
        }
    }
}