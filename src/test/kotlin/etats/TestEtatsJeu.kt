package etats

import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.OutilsCarte
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import iut.info1.flip7.exceptions.EtatPartieInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TestEtatsJeu {

    class JoueurTest(val nom: String) : IJoueur {
        override fun donneNom(): String = nom
    }

    private fun createGame(cartesPreparees: List<Carte>, i: Int): Flip7 {
        val joueurs = listOf(JoueurTest("Noe"), JoueurTest("Myriam"))

        return Flip7(nbJoueurs = 2, joueurs = joueurs, cartes = cartesPreparees, debug = true, scoreFinPartie = i, outilsCarte = OutilsCarte())
    }

    //ETAT JOUEUR

    @Test
    fun test_EtatInitialJoueursJoueEncorePourTous() {
        val jeu = createGame(cartesPreparees = listOf(CarteNum(valeur = 4)), i = 50)

        for (idJoueur in 0 until jeu.nbJoueurs) {
            assertEquals(EtatJoueur.JOUE_ENCORE, jeu.etatJoueur[idJoueur])
        }
    }

    @Test
    fun test_joueurEliminePasseMainAuto() {
        val deck = listOf(CarteNum(4), CarteNum(8), CarteNum(4))
        val jeu = createGame(deck, 100)
        val j1 = jeu.joueurCourant

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.PERDU, jeu.etatJoueur[j1])
        assertTrue(jeu.joueurCourant != j1)
    }



    @Test
    fun test_JoueurMisAStop() {
        val jeu = createGame(cartesPreparees = listOf(CarteNum(valeur = 6)), i = 50)
        val idJoueurInitial = jeu.joueurCourant
        jeu.joueurCourantDitStop()

        val etatJoueur = jeu.etatJoueur[idJoueurInitial]
        assertEquals(EtatJoueur.STOP, etatJoueur)
    }

    @Test
    fun test_JoueurPiocheDoublon() {
        val deck = listOf(CarteNum(valeur = 5), CarteNum(valeur = 9), CarteNum(valeur = 5))
        val jeu = createGame(cartesPreparees = deck, i = 50)
        val idJoueur1 = jeu.joueurCourant

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        val etatJ1 = jeu.etatJoueur[idJoueur1]
        assertEquals(EtatJoueur.PERDU, etatJ1)
    }

    @Test
    fun test_JoueurCibleParCarteStop() {
        val carteStop = CarteStop()
        val jeu = createGame(cartesPreparees = listOf(carteStop, CarteNum(valeur = 3)), i = 50)
        val idJoueurCible = (jeu.joueurCourant + 1) % jeu.nbJoueurs

        val carteTiree = jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantCibleStop(carteTiree, idJoueurCible)

        val etatCible = jeu.etatJoueur[idJoueurCible]
        assertEquals(EtatJoueur.STOP, etatCible)
    }

    @Test
    fun test_JoueurStoppePlusJoueurCourant() {
        val jeu = createGame(cartesPreparees = listOf(CarteNum(valeur = 8)), i = 50)
        val idJoueurInitial = jeu.joueurCourant
        jeu.joueurCourantDitStop()

        val nouveauJoueurCourant = jeu.joueurCourant
        assertTrue(nouveauJoueurCourant != idJoueurInitial)
    }

    @Test
    fun test_EtatJoueurAJoueEncoreMancheSuivante() {
        val deck = listOf(CarteNum(valeur = 5), CarteNum(valeur = 9), CarteNum(valeur = 5))
        val jeu = createGame(cartesPreparees = deck, i = 50)
        val idJoueur1 = jeu.joueurCourant

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.PERDU, jeu.etatJoueur[idJoueur1])

        //  il faut que tous les autres joueurs encore actifs
        // terminent leur tour pour que la manche se termine réellement
        while (jeu.etatJoueur.values.any { it == EtatJoueur.JOUE_ENCORE }) {
            jeu.joueurCourantDitStop()
        }

        jeu.scoreManche()
        jeu.nouvelleManche()

        for (idJoueur in 0 until jeu.nbJoueurs) {
            assertEquals(EtatJoueur.JOUE_ENCORE, jeu.etatJoueur[idJoueur])
        }
    }

    @Test
    fun test_JoueurAvecCarte2ndeChancePiocheDoublon() {
        val deck = listOf(
            Carte2ndeChance(),
            CarteNum(valeur = 5),
            CarteNum(valeur = 5)
        )
        val jeu = createGame(cartesPreparees = deck, i = 50)
        val idJoueur1 = jeu.joueurCourant

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        val etatJ1 = jeu.etatJoueur[idJoueur1]
        assertEquals(EtatJoueur.JOUE_ENCORE, etatJ1)
    }


    @Test
    fun test_secondeChanceDonneeAutreJoueurSiDejaPossede() {
        val deck = listOf(Carte2ndeChance(), Carte2ndeChance())
        val jeu = createGame(deck, 100)
        val j1 = jeu.joueurCourant
        val j2 = (j1 + 1) % jeu.nbJoueurs

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(1, jeu.main[j1]!!.count { it is Carte2ndeChance })
        assertEquals(1, jeu.main[j2]!!.count { it is Carte2ndeChance })
    }


    @Test
    fun test_MainJoueurContientCarteApresPioche() {
        val jeu = createGame(cartesPreparees = listOf(CarteNum(valeur = 7)), i = 50)
        val idJoueurInitial = jeu.joueurCourant

        val cartePiochee = jeu.joueurCourantPiocheUneCarte()

        val mainJoueur = jeu.main[idJoueurInitial]
        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.contains(cartePiochee))
        assertEquals(EtatJoueur.JOUE_ENCORE, jeu.etatJoueur[idJoueurInitial])
    }

    // ETAT PARTIE

    @Test
    fun test_EtatInitialEstAttenteChoixJoueur() {
        val jeu = createGame(listOf(CarteNum(4)), 50)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_EtatResteAttenteChoixJoueurApresPioche() {
        val jeu = createGame(listOf(CarteNum(4), CarteNum(5)), 50)
        jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_PiocheCarteStop() {
        val jeu = createGame(listOf(CarteStop()), 50)
        jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
    }

    @Test
    fun test_CarteStopVersAttenteChoixJoueur() {
        val jeu = createGame(listOf(CarteStop(), CarteNum(3)), 50)
        val idCible = (jeu.joueurCourant + 1) % jeu.nbJoueurs
        val carte = jeu.joueurCourantPiocheUneCarte()

        jeu.joueurCourantCibleStop(carte, idCible)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_PiocheCarte3aLaSuite() {
        val jeu = createGame(listOf(Carte3aLaSuite()), 50)
        jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
    }

    @Test
    fun test_3aLaSuiteVersAttenteChoixJoueur() {
        val deck = listOf(Carte3aLaSuite(), CarteNum(2), CarteNum(3), CarteNum(4))
        val jeu = createGame(deck, 50)
        val idCible = (jeu.joueurCourant + 1) % jeu.nbJoueurs
        val carte = jeu.joueurCourantPiocheUneCarte()

        jeu.joueurCourantCible3aLaSuite(carte, idCible)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_TousLesJoueursStopDevienneMancheTerminee() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(3)), 50)
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
    }

    @Test
    fun test_DernierJoueurSauteDevientMancheTerminee() {
        val deck = listOf(CarteNum(4), CarteNum(5), CarteNum(4))
        val jeu = createGame(deck, 50)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
    }


    @Test
    fun test_MancheTermineeDevientNouvelleManche() {
        val jeu = createGame(listOf(CarteNum(4), CarteNum(2)), 50)
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        jeu.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE, jeu.etatPartie)
    }

    @Test
    fun test_NouvelleMancheRepasseEnAttenteChoixJoueur() {
        val jeu = createGame(listOf(CarteNum(4), CarteNum(2), CarteNum(5)), 50)
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        jeu.scoreManche()
        jeu.nouvelleManche()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_PiocheInterditeEnEtatMancheTermineeLeveException() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(3)), 50)
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertThrows<EtatPartieInvalideException> {
            jeu.joueurCourantPiocheUneCarte()
        }
    }

    @Test
    fun test_CiblageStopInterditEnEtatStandardLeveException() {
        val jeu = createGame(listOf(CarteNum(5)), 50)
        val idCible = (jeu.joueurCourant + 1) % jeu.nbJoueurs

        assertThrows<EtatPartieInvalideException> {
            jeu.joueurCourantCibleStop(CarteStop(), idCible)
        }
    }

    @Test
    fun test_AppelNouvelleMancheSansCalculScoreLeveException() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(3)), 50)
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertThrows<EtatPartieInvalideException> {
            jeu.nouvelleManche()
        }
    }



    // IN-GAME SITUATION

    @Test
    fun testSecondeChanceCumulInterditBasculeEtatAttenteCible() {
        val deck = listOf(Carte2ndeChance(), Carte2ndeChance())
        val jeu = createGame(deck, 50)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun testSecondeChanceDefausseAutomatique() {
        val deck = listOf(CarteNum(4), Carte2ndeChance(), Carte2ndeChance())
        val jeu = createGame(deck, 50)

        val idJ1 = jeu.joueurCourant
        val idJ2 = (idJ1 + 1) % jeu.nbJoueurs

        jeu.joueurCourantPiocheUneCarte()
        assertEquals(idJ2, jeu.joueurCourant)

        jeu.joueurCourantDitStop()
        assertEquals(idJ1, jeu.joueurCourant)

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun testTroisALaSuiteBonneDistribution() {
        val deck = listOf(Carte3aLaSuite(), CarteNum(2), CarteNum(4), CarteNum(6))
        val jeu = createGame(deck, 50)

        val idDonneur = jeu.joueurCourant
        val idCible = (idDonneur + 1) % jeu.nbJoueurs

        val carteAction = jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)

        jeu.joueurCourantCible3aLaSuite(carteAction, idCible)

        val mainCible = jeu.main[idCible]

        assertNotNull(mainCible)
        assertTrue(mainCible!!.any { it is CarteNum && it.valeur == 2 })
        assertTrue(mainCible.any { it is CarteNum && it.valeur == 4 })
        assertTrue(mainCible.any { it is CarteNum && it.valeur == 6 })

        assertTrue(jeu.etatPartie != EtatPartie.ATTENTE_CIBLE_3SUITE)
    }

    @Test
    fun testTroisALaSuiteInterruptionSiLeJoueurCibleSaute() {
        val deck = listOf(
            CarteNum(5),
            CarteNum(5),
            Carte3aLaSuite(),
            CarteNum(5),
            CarteNum(8)
        )

        val jeu = createGame(deck, 50)
        val idJ1 = jeu.joueurCourant
        val idJ2 = (idJ1 + 1) % jeu.nbJoueurs

        jeu.joueurCourantPiocheUneCarte()
        assertEquals(idJ2, jeu.joueurCourant)

        jeu.joueurCourantPiocheUneCarte()
        assertEquals(idJ1, jeu.joueurCourant)

        val carteSpeciale = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)

        jeu.joueurCourantCible3aLaSuite(carteSpeciale, idJ2)

        assertEquals(EtatJoueur.PERDU, jeu.etatJoueur[idJ2])

        val mainCible = jeu.main[idJ2]
        assertTrue(mainCible!!.none { it is CarteNum && it.valeur == 8 })
    }


    @Test
    fun testFlip7FinDeMancheImmediateEtBonusPoints() {
        val deck = listOf(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7)
        )

        val jeu = createGame(deck, 50)
        val idJoueur = jeu.joueurCourant
        val idAutre = (idJoueur + 1) % jeu.nbJoueurs

        jeu.joueurCourantPiocheUneCarte()
        assertEquals(idAutre, jeu.joueurCourant)

        jeu.joueurCourantDitStop()

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        jeu.scoreManche()

        assertEquals(43, jeu.score[idJoueur])
    }

    @Test
    fun testFinDePartieDetectionCorrecteDuGagnant() {
        val deck = listOf(
            CarteNum(12), CarteNum(11), CarteNum(10),
            CarteNum(9), CarteNum(8)
        )

        val jeu = createGame(deck, 50)
        val idJoueur1 = jeu.joueurCourant
        val idJoueur2 = (idJoueur1 + 1) % jeu.nbJoueurs

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        jeu.scoreManche()

        assertEquals(EtatPartie.NOUVELLE_MANCHE, jeu.etatPartie)

        jeu.nouvelleManche()

        val courantManche2 = jeu.joueurCourant

        if (courantManche2 == idJoueur1) {
            jeu.joueurCourantPiocheUneCarte()
            jeu.joueurCourantDitStop()
            jeu.joueurCourantPiocheUneCarte()
            jeu.joueurCourantDitStop()
        } else {
            jeu.joueurCourantDitStop()
            jeu.joueurCourantPiocheUneCarte()
            jeu.joueurCourantPiocheUneCarte()
            jeu.joueurCourantDitStop()
        }

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        jeu.scoreManche()

        assertEquals(EtatPartie.PARTIE_TERMINEE, jeu.etatPartie)

        val scoreJ1 = jeu.score[idJoueur1] ?: 0
        val scoreJ2 = jeu.score[idJoueur2] ?: 0

        assertTrue(scoreJ1 >= 50)
        assertTrue(scoreJ1 > scoreJ2)
    }
}