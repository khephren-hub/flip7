package fr.iut.flip7

import fr.iut.flip7.controleur.*
import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.util.Regles
import fr.iut.flip7.vue.FenetreJoueur
import vue.FenetreAccueil
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    private lateinit var stagePrincipal: Stage
    private val gestionJoueur = GestionJoueur()

    override fun start(stagePrincipal: Stage) {
        this.stagePrincipal = stagePrincipal
        afficherAccueil()
        stagePrincipal.title = "Flip 7 - Bienvenue"
        stagePrincipal.show()
    }

    // ----- Écran d'accueil -----
    fun afficherAccueil() {
        val fenetreAccueil = FenetreAccueil(Regles.TEXTE)

        fenetreAccueil.buttonLancerJeu.setOnAction { afficherConfigurationJoueurs() }
        fenetreAccueil.buttonQuitter.setOnAction { stagePrincipal.close() }

        stagePrincipal.scene = Scene(fenetreAccueil, 800.0, 600.0)
        stagePrincipal.title = "Flip 7 - Bienvenue"
    }

    // ----- Écran de configuration des joueurs -----
    fun afficherConfigurationJoueurs() {
        val fenetreJoueur = FenetreJoueur()

        val ctrlAjouter = ControleurAjouterJoueur(fenetreJoueur, gestionJoueur, stagePrincipal)
        val ctrlSupprimer = ControleurSupprimerJoueur(fenetreJoueur, gestionJoueur)
        val ctrlDroite = ControleurDeplacerDroite(fenetreJoueur, gestionJoueur)
        val ctrlGauche = ControleurDeplacerGauche(fenetreJoueur, gestionJoueur)
        val ctrlLancer = ControleurLancerPartie(
            gestionJoueur,
            stagePrincipal,
            { afficherAccueil() },                 // Retour au menu
            { afficherConfigurationJoueurs() }     // Relancer une partie
        )

        fenetreJoueur.boutonAjouter.setOnAction { ctrlAjouter.ajouterJoueur() }
        fenetreJoueur.boutonSupprimer.setOnAction { ctrlSupprimer.supprimerJoueur() }
        fenetreJoueur.boutonDroit.setOnAction { ctrlDroite.deplacerDroite() }
        fenetreJoueur.boutonGauche.setOnAction { ctrlGauche.deplacerGauche() }
        fenetreJoueur.boutonLancer.setOnAction {
            ctrlLancer.lancerPartie(fenetreJoueur.getScoreAAtteindre())
        }
        fenetreJoueur.update(gestionJoueur.joueursAjoutes, gestionJoueur.joueursSelectionnes)

        stagePrincipal.scene = Scene(fenetreJoueur, 700.0, 500.0)
        stagePrincipal.title = "Flip 7 - Configuration de la partie"
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}