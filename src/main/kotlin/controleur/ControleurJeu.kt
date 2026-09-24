package fr.iut.flip7.controleur

import fr.iut.flip7.util.AlertManager
import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import vue.FenetreFinJeu
import vue.FenetreJeu
import vue.InfoJoueurAffichage

class ControleurJeu(
    private val vue: FenetreJeu,
    private val flip7: Flip7,
    private val stagePrincipal: Stage,
    private val surRetourMenu: () -> Unit,
    private val surRelancerPartie: () -> Unit
) {
    private val joueurs: List<IJoueur> = flip7.joueurs
    private var derniereCarte: Carte? = null

    init {
        vue.boutonEncore.setOnAction { gererEncore() }
        vue.boutonStop.setOnAction { gererStop() }
        rafraichir()
    }

    private fun gererEncore() {
        if (flip7.etatPartie != EtatPartie.ATTENTE_CHOIX_JOUEUR) return
        val joueurAvant = flip7.joueurCourant          // qui joue, AVANT la pioche
        derniereCarte = flip7.joueurCourantPiocheUneCarte()
        rafraichir()

        if (flip7.etatJoueur[joueurAvant] == EtatJoueur.PERDU) {
            vue.afficherDialogueDoublon(joueurs[joueurAvant].donneNom()) {
                Platform.runLater { traiterEtat() }
            }
        } else {
            traiterEtat()
        }
    }

    private fun gererStop() {
        if (flip7.etatPartie != EtatPartie.ATTENTE_CHOIX_JOUEUR) return
        flip7.joueurCourantDitStop()
        traiterEtat()
    }

    private fun traiterEtat() {
        rafraichir()
        when (flip7.etatPartie) {
            EtatPartie.ATTENTE_CIBLE_STOP -> demanderCibleStop()
            EtatPartie.ATTENTE_CIBLE_3SUITE -> demanderCible3Suite()
            EtatPartie.MANCHE_TERMINEE -> traiterFinManche()
            else -> { }
        }
    }

    private fun demanderCibleStop() {
        val cibles = ciblesPossibles()
        vue.afficherDialogueCiblage("Carte Stop !", "images/images_carte/stop.png", cibles.map { it.second }) { nomCible ->
            val idx = cibles.first { it.second == nomCible }.first
            try {
                flip7.joueurCourantCibleStop(derniereCarte!!, idx)
            } catch (e: Exception) {
                AlertManager.afficherAvertissement("Action impossible", e.message ?: "Cible invalide.")
            }
            Platform.runLater { traiterEtat() }
        }
    }

    private fun demanderCible3Suite() {
        val cibles = ciblesPossibles()
        vue.afficherDialogueCiblage("Carte 3 à la suite !", "images/images_carte/3alasuite.png", cibles.map { it.second }) { nomCible ->
            val idx = cibles.first { it.second == nomCible }.first
            try {
                flip7.joueurCourantCible3aLaSuite(derniereCarte!!, idx)
            } catch (e: Exception) {
                AlertManager.afficherAvertissement("Action impossible", e.message ?: "Cible invalide.")
            }
            Platform.runLater { traiterEtat() }
        }
    }

    private fun traiterFinManche() {
        vue.afficherDialogueFinManche("La manche est terminée") {
            flip7.scoreManche()
            Platform.runLater {
                when (flip7.etatPartie) {
                    EtatPartie.PARTIE_TERMINEE -> afficherClassement()
                    EtatPartie.NOUVELLE_MANCHE -> { flip7.nouvelleManche(); rafraichir() }
                    else -> rafraichir()
                }
            }
        }
    }

    private fun ciblesPossibles(): List<Pair<Int, String>> {
        return (0 until flip7.nbJoueurs)
            .filter { it != flip7.joueurCourant && flip7.etatJoueur[it] == EtatJoueur.JOUE_ENCORE }
            .map { it to joueurs[it].donneNom() }
    }

    private fun rafraichir() {
        val courant = flip7.joueurCourant
        vue.setPiocheDefausse(flip7.taillePioche, flip7.defausse.size)
        vue.setStatut(texteEtatPartie(flip7.etatPartie, courant))
        vue.afficherJoueurActif(infoDe(courant))
        val autres = (0 until flip7.nbJoueurs).filter { it != courant }.map { infoDe(it) }
        vue.afficherJoueursAttente(autres)
    }

    private fun infoDe(i: Int): InfoJoueurAffichage {
        val main = flip7.main[i] ?: emptyList()
        val etat = flip7.etatJoueur[i] ?: EtatJoueur.JOUE_ENCORE
        val scoreManche = if (etat == EtatJoueur.PERDU) 0
        else try { flip7.outilsCarte.calculScore(main) } catch (e: Exception) { 0 }
        return InfoJoueurAffichage(
            nom = joueurs[i].donneNom(),
            scoreTotal = flip7.score[i] ?: 0,
            scoreManche = scoreManche,
            statut = statutTexte(etat),
            cartes = main
        )
    }

    private fun statutTexte(e: EtatJoueur): String = when (e) {
        EtatJoueur.JOUE_ENCORE -> "EN JEU"
        EtatJoueur.STOP -> "STOP"
        EtatJoueur.PERDU -> "PERDU"
    }

    private fun texteEtatPartie(e: EtatPartie, courant: Int): String = when (e) {
        EtatPartie.ATTENTE_CHOIX_JOUEUR -> "Au tour de ${joueurs[courant].donneNom()}"
        EtatPartie.ATTENTE_CIBLE_STOP -> "Choisissez une cible (Stop)"
        EtatPartie.ATTENTE_CIBLE_3SUITE -> "Choisissez une cible (3 à la suite)"
        EtatPartie.MANCHE_TERMINEE -> "Manche terminée"
        else -> "..."
    }

    private fun afficherClassement() {
        val classement = (0 until flip7.nbJoueurs)
            .map { joueurs[it].donneNom() to (flip7.score[it] ?: 0) }
            .sortedByDescending { it.second }
        val fenetreFin = FenetreFinJeu(classement, classement.first().first, false)
        fenetreFin.boutonRetourMenu.setOnAction { surRetourMenu() }
        fenetreFin.boutonRelancerPartie.setOnAction { surRelancerPartie() }
        stagePrincipal.scene = Scene(fenetreFin, 1000.0, 700.0)
    }
}