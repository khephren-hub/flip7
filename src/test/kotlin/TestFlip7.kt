package flip7

import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.OutilsCarte
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import iut.info1.flip7.exceptions.CarteInvalideException
import iut.info1.flip7.exceptions.EtatPartieInvalideException
import iut.info1.flip7.exceptions.IndiceJoueurInvalideException
import iut.info1.flip7.exceptions.ListeJoueursInvalideException
import iut.info1.flip7.exceptions.NombreJoueursInvalideException
import iut.info1.flip7.exceptions.PiocheInvalideException
import iut.info1.flip7.exceptions.ScoreFinPartieInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TestFlip7 {

    class JoueurTest(val nom: String) : IJoueur {
        override fun donneNom(): String = nom
    }

    private fun createGame(cartesPreparees: List<Carte>, scoreFin:Int = 50): Flip7 {
        val joueurs = listOf(JoueurTest("Noe"), JoueurTest("Myriam"))

        return Flip7(nbJoueurs = 2, joueurs = joueurs, cartes = cartesPreparees, debug = true, scoreFinPartie = scoreFin, outilsCarte = OutilsCarte())
    }

    // ************ Test de Create ************


    // Construit une pioche complète et valide : 94 cartes (79 numéros + 6 bonus + 9 spéciales)
    private fun piocheValide(): List<Carte> {
        val pioche = mutableListOf<Carte>()
        pioche.add(CarteNum(0))
        pioche.add(CarteNum(1))
        repeat(2)  { pioche.add(CarteNum(2)) }
        repeat(3)  { pioche.add(CarteNum(3)) }
        repeat(4)  { pioche.add(CarteNum(4)) }
        repeat(5)  { pioche.add(CarteNum(5)) }
        repeat(6)  { pioche.add(CarteNum(6)) }
        repeat(7)  { pioche.add(CarteNum(7)) }
        repeat(8)  { pioche.add(CarteNum(8)) }
        repeat(9)  { pioche.add(CarteNum(9)) }
        repeat(10) { pioche.add(CarteNum(10)) }
        repeat(11) { pioche.add(CarteNum(11)) }
        repeat(12) { pioche.add(CarteNum(12)) }
        pioche.add(CarteBonusMultiplie())
        pioche.add(CarteBonusPlus(2))
        pioche.add(CarteBonusPlus(4))
        pioche.add(CarteBonusPlus(6))
        pioche.add(CarteBonusPlus(8))
        pioche.add(CarteBonusPlus(10))
        repeat(3) { pioche.add(CarteStop()) }
        repeat(3) { pioche.add(Carte2ndeChance()) }
        repeat(3) { pioche.add(Carte3aLaSuite()) }
        return pioche
    }

    private fun deuxJoueurs(): List<IJoueur> = listOf(JoueurTest("Noe"), JoueurTest("Kris"))
    private fun quatreJoueurs(): List<IJoueur> =
        listOf(JoueurTest("Noe"), JoueurTest("Kris"), JoueurTest("Tunar"), JoueurTest("Simon"))

    @Test
    fun test_CT1_constructeurNombreJoueursInvalideBorneBasse() {
        assertThrows<NombreJoueursInvalideException> {
            Flip7(1, listOf(JoueurTest("Noe")), piocheValide(), false, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT2_constructeurNombreJoueursInvalideBorneHaute() {
        val cinqJoueurs = quatreJoueurs() + JoueurTest("Khemara")
        assertThrows<NombreJoueursInvalideException> {
            Flip7(5, cinqJoueurs, piocheValide(), false, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT3_constructeurListeJoueursInvalideSousEffectif() {
        assertThrows<ListeJoueursInvalideException> {
            Flip7(2, listOf(JoueurTest("Noe")), piocheValide(), false, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT4_constructeurListeJoueursInvalideSurEffectif() {
        assertThrows<ListeJoueursInvalideException> {
            Flip7(2, listOf(JoueurTest("Noe"), JoueurTest("Myriam"), JoueurTest("Tunar")), piocheValide(), false, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT5_constructeurPiocheInvalide() {
        assertThrows<PiocheInvalideException> {
            Flip7(2, deuxJoueurs(), listOf(CarteNum(1), CarteNum(2)), false, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT6_constructeurPiocheImposeeEnDebug() {
        assertDoesNotThrow {
            Flip7(2, deuxJoueurs(), listOf(CarteNum(7)), true, 50, OutilsCarte())
        }
    }

    @Test
    fun test_CT7_constructeurScoreFinPartieInvalideBorneBasse() {
        assertThrows<ScoreFinPartieInvalideException> {
            Flip7(2, deuxJoueurs(), piocheValide(), false, 0, OutilsCarte())
        }
    }

    @Test
    fun test_CT8_constructeurScoreFinPartieInvalideBorneHaute() {
        assertThrows<ScoreFinPartieInvalideException> {
            Flip7(2, deuxJoueurs(), piocheValide(), false, 201, OutilsCarte())
        }
    }

    @Test
    fun test_CT9_constructeurValide() {
        val jeu = Flip7(
            nbJoueurs = 2,
            joueurs = deuxJoueurs(),
            cartes = piocheValide(),
            debug = false,
            scoreFinPartie = 50,
            outilsCarte = OutilsCarte()
        )
        assertNotNull(jeu)
        assertEquals(2, jeu.nbJoueurs)
    }

    @Test
    fun test_CT10_constructeurValideBornesHautes() {
        val jeu = Flip7(
            nbJoueurs = 4,
            joueurs = quatreJoueurs(),
            cartes = piocheValide(),
            debug = false,
            scoreFinPartie = 200,
            outilsCarte = OutilsCarte()
        )
        assertEquals(4, jeu.nbJoueurs)
        assertEquals(200, jeu.scoreFinPartie)
    }

    @Test
    fun test_CT11_constructeurValeursParDefaut() {
        val jeu = Flip7(nbJoueurs = 2, joueurs = deuxJoueurs(), cartes = piocheValide())
        assertEquals(false, jeu.debug)
        assertEquals(200, jeu.scoreFinPartie)
    }

    // ************ Test de joueurCourantPiocheUneCarte ************

    //pioche normal test
    @Test
    fun test_CT2_joueurCourantPiocheNormal() {
        val jeu = createGame(listOf(CarteNum(7)))
        val idJoueurInitial = jeu.joueurCourant
        val cartePiochee = jeu.joueurCourantPiocheUneCarte()
        val mainJoueur = jeu.main[idJoueurInitial]
        val prochainJoueur = (idJoueurInitial + 1) % jeu.nbJoueurs

        assertEquals(7, cartePiochee.valeur)
        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.contains(cartePiochee))
        assertEquals(prochainJoueur, jeu.joueurCourant)
    }

    @Test
    fun test_CT7_joueurCourantPiocheBonus() {
        val jeu = createGame(listOf(CarteNum(7), CarteNum(2), CarteBonusMultiplie()))
        val idJoueurInitial = jeu.joueurCourant
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        val cartePiochee = jeu.joueurCourantPiocheUneCarte()
        val mainJoueur = jeu.main[idJoueurInitial]
        val prochainJoueur = (idJoueurInitial + 1) % jeu.nbJoueurs

        assertEquals(2, cartePiochee.valeur)
        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.contains(cartePiochee))
        assertEquals(prochainJoueur, jeu.joueurCourant)
    }

    @Test
    fun test_CT7_joueurCourantPiocheBonus2() {
        val jeu = createGame(listOf(CarteNum(7), CarteNum(2), CarteBonusPlus(4)))
        val idJoueurInitial = jeu.joueurCourant
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        val cartePiochee = jeu.joueurCourantPiocheUneCarte()
        val mainJoueur = jeu.main[idJoueurInitial]
        val prochainJoueur = (idJoueurInitial + 1) % jeu.nbJoueurs

        assertEquals(4, cartePiochee.valeur)
        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.contains(cartePiochee))
        assertEquals(prochainJoueur, jeu.joueurCourant)
    }

    // pioche doublons test
    @Test
    fun test_CT3_joueurCourantPiocheDoublons() {
        val jeu = createGame(listOf(CarteNum(5), CarteNum(9), CarteNum(5)))
        val idJoueur1 = jeu.joueurCourant

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()

        val etatJ1 = jeu.etatJoueur[idJoueur1]
        assertEquals(EtatJoueur.PERDU, etatJ1)
    }

    // Pioche d'une CarteStop
    @Test
    fun test_CT4_joueurCourantPiocheStop() {
        // Une carte Stop est au-dessus de la pile
        val jeu = createGame(listOf(CarteStop()))

        // On pioche la carte
        jeu.joueurCourantPiocheUneCarte()

        // L'état de la partie doit passer en attente d'une cible pour le Stop
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
    }

    //Pioche d'une Carte3aLaSuite
    @Test
    fun test_CT6_joueurCourantPioche3aLaSuite() {
        // Une carte 3ÀLaSuite est disponible
        val jeu = createGame(listOf(Carte3aLaSuite()))
        jeu.joueurCourantPiocheUneCarte()

        // Changement d'état de la partie vers la cible requise
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
    }

    //Test des Exceptions (EtatPartieInvalide)
    @Test
    fun test_CT1_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(Carte3aLaSuite(), CarteNum(2)))
        jeu.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Attente_cible_stop + normal -> etatInvalide
    @Test
    fun test_CT5_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteStop(), Carte2ndeChance()))
        jeu.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Manche_terminee + 2ndChance -> etatInvalide
    @Test
    fun test_CT10_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(5), Carte2ndeChance()))
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Manche_terminee + bonusPlus -> etatInvalide
    @Test
    fun test_CT12_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(5), CarteBonusPlus(4)))
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Manche_terminee + 2ndChance -> etatInvalide
    @Test
    fun test_CT12_joueurCourantEtatPartieInvalideException2() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(5), CarteBonusMultiplie()))
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Manche_terminee + doublons -> etatInvalide
    @Test
    fun test_CT8_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(5), CarteNum(2)))
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Nouvelle_manche + carteStop -> etatInvalide
    @Test
    fun test_CT9_joueurCourantEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteStop(), CarteNum(2), CarteNum(3)))
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        // Test etat = Manche_terminee + doublons -> etatInvalide
        assertEquals(EtatPartie.NOUVELLE_MANCHE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // Test etat = Partie_terminee + normal -> etatInvalide
    @Test
    fun test_CT1_joueurCourantEtatPartieInvalideException3() {
        val jeu = createGame(listOf(
            CarteNum(12), CarteNum(2),
            CarteNum(11), CarteNum(3),
            CarteNum(10), CarteNum(4),
            CarteNum(9), CarteNum(5),
            CarteNum(8), CarteNum(6),
            CarteNum(7), CarteNum(7)
            )
        )
        for (i in 0 until 12) {
            jeu.joueurCourantPiocheUneCarte()
        }
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        jeu.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantPiocheUneCarte()}
    }

    // ************ Test de joueurCourantCibleStop ************

    @Test
    fun test_CT1_joueurCourantCibleStopEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(5)))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCibleStop(CarteStop(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCibleStopEtatPartieInvalideException1() {
        val jeu = createGame(listOf(CarteBonusMultiplie()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCibleStop(CarteStop(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCibleStopEtatPartieInvalideException2() {
        val jeu = createGame(listOf(Carte3aLaSuite()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCibleStop(CarteStop(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCibleStopEtatPartieInvalideException3() {
        val jeu = createGame(listOf(Carte2ndeChance()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCibleStop(CarteStop(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCibleStopEtatPartieInvalideException4() {
        val jeu = createGame(listOf(CarteBonusPlus(4)))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCibleStop(CarteStop(), 1)}
    }

    @Test
    fun test_CT2_joueurCourantCibleStopNormal() {
        val jeu = createGame(listOf(CarteStop(), CarteNum(2)))
        val cP = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
        jeu.joueurCourantCibleStop(cP, 1)
        assertEquals(EtatJoueur.STOP, jeu.etatJoueur[1])
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_CT3_joueurCourantCibleStopCarteInvalideException() {
        val jeu = createGame(listOf(CarteStop()))
        jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
        assertThrows<CarteInvalideException> {jeu.joueurCourantCibleStop(CarteNum(5), 1)}
    }

    @Test
    fun test_CT4_joueurCourantCibleStopIndiceJoueurInvalideException() {
        val jeu = createGame(listOf(CarteStop()))
        val carteStop = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
        assertThrows<IndiceJoueurInvalideException> {jeu.joueurCourantCibleStop(carteStop, -1)}
    }

    @Test
    fun test_CT5_joueurCourantCibleStopIndiceJoueurInvalideException() {
        val jeu = createGame(listOf(CarteStop()))
        val carteStop = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, jeu.etatPartie)
        assertThrows<IndiceJoueurInvalideException> {jeu.joueurCourantCibleStop(carteStop, 5)}
    }

    // ************ Test de joueurCourantCible3aLaSuite ************

    @Test
    fun test_CT1_joueurCourantCible3aLaSuiteEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(5)))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCible3aLaSuite(Carte3aLaSuite(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCible3aLaSuiteEtatPartieInvalideException1() {
        val jeu = createGame(listOf(CarteBonusMultiplie()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCible3aLaSuite(Carte3aLaSuite(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCible3aLaSuiteEtatPartieInvalideException2() {
        val jeu = createGame(listOf(Carte3aLaSuite()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCible3aLaSuite(Carte3aLaSuite(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCible3aLaSuiteEtatPartieInvalideException3() {
        val jeu = createGame(listOf(Carte2ndeChance()))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCible3aLaSuite(Carte3aLaSuite(), 1)}
    }

    @Test
    fun test_CT1_joueurCourantCible3aLaSuiteEtatPartieInvalideException4() {
        val jeu = createGame(listOf(CarteBonusPlus(4)))
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantCible3aLaSuite(Carte3aLaSuite(), 1)}
    }

    @Test
    fun test_CT2_joueurCourantCible3aLaSuiteNormal() {
        val jeu = createGame(listOf(Carte3aLaSuite(), CarteNum(2),CarteNum(5), CarteNum(6), CarteNum(7)))
        val cP = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
        jeu.joueurCourantCible3aLaSuite(cP, 1)
        assertEquals(EtatJoueur.JOUE_ENCORE, jeu.etatJoueur[1])
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
    }

    @Test
    fun test_CT3_joueurCourantCible3aLaSuiteMauvaiseCarteInvalideException() {
        val jeu = createGame(listOf(Carte3aLaSuite()))
        jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
        assertThrows<CarteInvalideException> {jeu.joueurCourantCible3aLaSuite(CarteNum(5), 1)}
    }

    @Test
    fun test_CT4_joueurCourantCible3aLaSuiteIndiceJoueurInvalideException() {
        val jeu = createGame(listOf(Carte3aLaSuite(), CarteNum(2),CarteNum(5), CarteNum(6), CarteNum(7)))
        val carte3aLaSuite = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
        assertThrows<IndiceJoueurInvalideException> {jeu.joueurCourantCible3aLaSuite(carte3aLaSuite, -1)}
    }

    @Test
    fun test_CT5_joueurCourantCible3aLaSuiteIndiceJoueurInvalideException() {
        val jeu = createGame(listOf(Carte3aLaSuite(), CarteNum(2),CarteNum(5), CarteNum(6), CarteNum(7)))
        val carte3aLaSuite = jeu.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, jeu.etatPartie)
        assertThrows<IndiceJoueurInvalideException> {jeu.joueurCourantCible3aLaSuite(carte3aLaSuite, 5)}
    }

    // ************ Test de joueurCourantDitStop ************

    @Test
    fun test_CT1_joueurCourantDitStopEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteStop(), CarteNum(2), CarteNum(3)))
        jeu.joueurCourantPiocheUneCarte()
        assertNotEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantDitStop()}
    }

    @Test
    fun test_CT1_joueurCourantDitStopEtatPartieInvalideException2() {
        val jeu = createGame(listOf(Carte3aLaSuite(), CarteNum(2), CarteNum(3)))
        jeu.joueurCourantPiocheUneCarte()
        assertNotEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantDitStop()}
    }

    @Test
    fun test_CT1_joueurCourantDitStopEtatPartieInvalideException3() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(2), CarteNum(3)))
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        assertNotEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantDitStop()}
    }

    @Test
    fun test_CT1_joueurCourantDitStopEtatPartieInvalideException4() {
        val jeu = createGame(listOf(CarteNum(2), CarteNum(2), CarteNum(3)))
        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()
        jeu.scoreManche()
        assertNotEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantDitStop()}
    }

    @Test
    fun test_CT1_joueurCourantDitStopEtatPartieInvalideException5() {
        val jeu = createGame(listOf(
            CarteNum(12), CarteNum(2),
            CarteNum(11), CarteNum(3),
            CarteNum(10), CarteNum(4),
            CarteNum(9), CarteNum(5),
            CarteNum(8), CarteNum(6),
            CarteNum(7), CarteNum(1)))

        for (i in 0 until 12) {
            jeu.joueurCourantPiocheUneCarte()
        }

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)
        jeu.scoreManche()

        assertNotEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)
        assertThrows<EtatPartieInvalideException> {jeu.joueurCourantDitStop()}
    }

    @Test
    fun test_CT2_joueurCourantDitStopNormal() {
        val jeu = createGame(listOf(CarteNum(5), CarteNum(6)))
        val joueurQuiStop = jeu.joueurCourant
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, jeu.etatPartie)

        jeu.joueurCourantDitStop()
        assertEquals(EtatJoueur.STOP, jeu.etatJoueur[joueurQuiStop])
    }

    // ************ Test de scoreManche ************

    @Test
    fun test_CT1_scoreMancheEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(3)), scoreFin = 200)
        assertThrows<EtatPartieInvalideException> {jeu.scoreManche()}
    }

    @Test
    fun test_CT2_3_scoreMancheFinScoreAtteint() {
        val jeu = createGame(listOf(
            CarteNum(12), CarteNum(2),
            CarteNum(11), CarteNum(3),
            CarteNum(10), CarteNum(4),
            CarteNum(9),  CarteNum(5),
            CarteNum(8),  CarteNum(6),
            CarteNum(7),  CarteNum(1),
            CarteNum(2)
        ), scoreFin = 50)

        val idJoueur0 = 0
        val idJoueur1 = 1
        for (i in 0 until 12) {
            jeu.joueurCourantPiocheUneCarte()
        }
        jeu.joueurCourantDitStop()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        val scoresManche = jeu.scoreManche()

        assertEquals(EtatPartie.PARTIE_TERMINEE, jeu.etatPartie)
        assertEquals(57, scoresManche[idJoueur0])
        assertEquals(0, scoresManche[idJoueur1])
    }

    @Test
    fun test_CT4_5_scoreMancheScoreNonAtteint() {
        val jeu = createGame(listOf(
            CarteNum(5),
            CarteNum(3),
            CarteNum(3)
        ), scoreFin = 50)
        val idJoueur0 = 0
        val idJoueur1 = 1

        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantPiocheUneCarte()
        jeu.joueurCourantDitStop()
        jeu.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.MANCHE_TERMINEE, jeu.etatPartie)

        val scoresManche = jeu.scoreManche()

        assertEquals(5, scoresManche[idJoueur0])
        assertEquals(0, scoresManche[idJoueur1])
        assertNotEquals(EtatPartie.PARTIE_TERMINEE, jeu.etatPartie)
    }

    // ************ Test de nouvelleManche ************

    @Test
    fun test_CT1_nouvelleMancheNormal() {
        val jeu = createGame(listOf(CarteNum(5), CarteNum(4)))
        val idJoueurInitial = jeu.joueurCourant

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        val prochainJoueur = (idJoueurInitial + 1) % jeu.nbJoueurs

        jeu.scoreManche()
        jeu.nouvelleManche()

        val mainJoueur = jeu.main[idJoueurInitial]

        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.isEmpty())
        assertEquals(prochainJoueur, jeu.joueurCourant)
    }

    @Test
    fun test_CT2_nouvelleMancheNormalMainNonVide() {
        val jeu = createGame(listOf(CarteNum(5), CarteNum(4)))
        val idJoueurInitial = jeu.joueurCourant
        val cartePiochee = jeu.joueurCourantPiocheUneCarte()

        jeu.joueurCourantDitStop()
        jeu.joueurCourantDitStop()

        val prochainJoueur = (idJoueurInitial + 1) % jeu.nbJoueurs

        jeu.scoreManche()
        jeu.nouvelleManche()

        val mainJoueur = jeu.main[idJoueurInitial]

        assertEquals(5, cartePiochee.valeur)
        assertNotNull(mainJoueur)
        assertTrue(mainJoueur!!.isEmpty())
        assertEquals(prochainJoueur, jeu.joueurCourant)
    }

    @Test
    fun test_CT3_nouvelleMancheEtatPartieInvalideException() {
        val jeu = createGame(listOf(CarteNum(5), CarteNum(4)))
        assertThrows<EtatPartieInvalideException> {(jeu.nouvelleManche())}
    }


}
