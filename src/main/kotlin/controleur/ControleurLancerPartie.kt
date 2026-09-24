package fr.iut.flip7.controleur

import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.modele.ModeleFlip7
import fr.iut.flip7.util.AlertManager
import fr.iut.flip7.util.Regles
import javafx.scene.Scene
import javafx.stage.Stage
import vue.FenetreJeu

class ControleurLancerPartie(
    private val modele: GestionJoueur,
    private val stagePrincipal: Stage,
    private val surRetourMenu: () -> Unit,
    private val surRelancerPartie: () -> Unit
) {
    fun lancerPartie(scoreAAtteindre: Int) {
        if (!modele.peutLancerPartie()) {
            AlertManager.afficherAvertissement(
                "Nombre de joueurs invalide",
                "Il faut entre 2 et 4 joueurs sélectionnés pour lancer la partie."
            )
            return
        }

        val joueursPartie = modele.getJoueursPartie()

        val flip7 = try {
            ModeleFlip7.creerPartie(joueursPartie, scoreAAtteindre)
        } catch (e: Exception) {
            AlertManager.afficherAvertissement("Impossible de lancer", e.message ?: "Erreur du modèle.")
            return
        }

        val fenetreJeu = FenetreJeu(Regles.TEXTE)
        ControleurJeu(fenetreJeu, flip7, stagePrincipal, surRetourMenu, surRelancerPartie)
        stagePrincipal.scene = Scene(fenetreJeu, 1000.0, 700.0)
    }
}