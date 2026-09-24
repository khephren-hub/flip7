package cartes

import iut.info1.flip7.cartes.*
import iut.info1.flip7.exceptions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class OutilsTestCarteTest {

    @Test
    fun calculScoreMainVide() {
        val mainVide = listOf<iut.info1.flip7.cartes.Carte>()
        val score = OutilsCarte().calculScore(mainVide)
        assertEquals(0, score)
    }

    @Test
    fun calculScoreCarteNumOnly() {
        val mainStandard = listOf<iut.info1.flip7.cartes.Carte>(CarteNum(2), CarteNum(5), CarteNum(7))
        assertTrue(mainStandard.all { it.estCarteNum() })
        val score = OutilsCarte().calculScore(mainStandard)
        assertEquals(14, score)
    }

    @Test
    fun calculScoreAvecCarteZero() {
        val mainAvecZero = listOf<Carte>(
            CarteNum(0),
            CarteNum(5),
            CarteBonusPlus(4)
        )
        val score = OutilsCarte().calculScore(mainAvecZero)
        assertEquals(9, score)
    }

    @Test
    fun calculScoreMultiplicateur() {
        val mainAvecMultiplicateur = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(4),
            CarteNum(6),
            iut.info1.flip7.cartes.CarteBonusMultiplie()
        )
        assertTrue(mainAvecMultiplicateur.any { it.estCarteBonusMultiplie() })
        val score = OutilsCarte().calculScore(mainAvecMultiplicateur)
        assertEquals(20, score)
    }

    @Test
    fun calculScoreBonusPlus() {
        val mainAvecBonusFixe = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(3),
            CarteNum(5),
            CarteBonusPlus(4)
        )
        assertTrue(mainAvecBonusFixe.any { it.estCarteBonusPlus() })
        val score = OutilsCarte().calculScore(mainAvecBonusFixe)
        assertEquals(12, score)
    }

    @Test
    fun calculScoreCarteStop() {
        val mainAvecStop = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1),
            iut.info1.flip7.cartes.CarteStop()
        )
        assertTrue(mainAvecStop.any { it.estCarteStop() })
        val score = OutilsCarte().calculScore(mainAvecStop)
        assertEquals(1, score)
    }

    @Test
    fun calculScoreCarte2ndeChance() {
        val mainAvec2ndeChance = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(5),
            Carte2ndeChance()
        )
        assertTrue(mainAvec2ndeChance.any { it.estCarte2ndeChance() })
        val score = OutilsCarte().calculScore(mainAvec2ndeChance)
        assertEquals(5, score)
    }

    @Test
    fun calculScoreCarte3aLaSuite() {
        val mainAvec3aLaSuite = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(8),
            Carte3aLaSuite()
        )
        assertTrue(mainAvec3aLaSuite.any { it.estCarte3aLaSuite() })
        val score = OutilsCarte().calculScore(mainAvec3aLaSuite)
        assertEquals(8, score)
    }

    @Test
    fun calculScoreBonusFlip7() {
        val mainFlip7 = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7)
        )
        assertTrue(mainFlip7.size == 7 && mainFlip7.all { it.estCarteNum() })
        val score = OutilsCarte().calculScore(mainFlip7)
        assertEquals(43, score)
    }

    @Test
    fun calculScoreMultiplesBonusPlus() {
        val mainMultiplesBonus = listOf<Carte>(
            CarteNum(5),
            CarteBonusPlus(4),
            CarteBonusPlus(6)
        )
        val score = OutilsCarte().calculScore(mainMultiplesBonus)
        assertEquals(15, score)
    }

    @Test
    fun calculScoreMultiplicateurSansNumerique() {
        val mainSpecialeUniquement = listOf<Carte>(
            CarteBonusMultiplie(),
            CarteBonusPlus(10)
        )
        val score = OutilsCarte().calculScore(mainSpecialeUniquement)
        assertEquals(10, score)
    }

    @Test
    fun calculScoreFlip7AvecMultiplicateurEtBonus() {
        val mainComboFlip7 = listOf<Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7),
            CarteBonusPlus(2),
            CarteBonusMultiplie()
        )
        val score = OutilsCarte().calculScore(mainComboFlip7)
        assertEquals(73, score)
    }

    @Test
    fun calculScoreBonusMultiplieAvantBonusPlus() {
        val mainOrdre = listOf<Carte>(
            CarteNum(5),
            CarteBonusPlus(2),
            CarteBonusMultiplie()
        )
        val score = OutilsCarte().calculScore(mainOrdre)
        assertEquals(12, score)
    }

    @Test
    fun calculScoreBonusMultiplieAvantBonusFlip7() {
        val mainOrdreFlip7 = listOf<Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7),
            CarteBonusMultiplie()
        )
        val score = OutilsCarte().calculScore(mainOrdreFlip7)
        assertEquals(71, score)
    }

    @Test
    fun estFlip7MainVide() {
        val mainVide = listOf<Carte>()
        val resultat = OutilsCarte().estFlip7(mainVide)
        assertEquals(false, resultat)
    }

    @Test
    fun estFlip7MoinsDeSeptCartes() {
        val mainCourte = listOf<iut.info1.flip7.cartes.Carte>(CarteNum(1), CarteNum(2), CarteNum(3))

        val resultat = OutilsCarte().estFlip7(mainCourte)
        assertEquals(false, resultat)
    }

    @Test
    fun estFlip7SeptCartesNumPasDistinctes() {
        val mainSeptNonDistinctes = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(2),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(12)
        )
        val resultat = OutilsCarte().estFlip7(mainSeptNonDistinctes)
        assertEquals(false, resultat)
    }

    @Test
    fun estFlip7SeptCartesNumEtSpecialeOuBonus() {
        val mainFauxFlip7 = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3), CarteNum(4), CarteNum(5),
            CarteBonusPlus(4),
            iut.info1.flip7.cartes.Carte3aLaSuite()
        )
        val resultat = OutilsCarte().estFlip7(mainFauxFlip7)
        assertEquals(false, resultat)
    }

    @Test
    fun estFlip7Valide() {
        val mainFlip7Pure = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7)
        )

        val resultat = OutilsCarte().estFlip7(mainFlip7Pure)
        assertEquals(true, resultat)
    }

    @Test
    fun estFlip7ValideAvecCarteZero() {
        val mainFlip7AvecZero = listOf<Carte>(
            CarteNum(0), CarteNum(1), CarteNum(2),
            CarteNum(3), CarteNum(4), CarteNum(5), CarteNum(6)
        )
        val resultat = OutilsCarte().estFlip7(mainFlip7AvecZero)
        assertTrue(resultat)

        assertEquals(36, OutilsCarte().calculScore(mainFlip7AvecZero))
    }

    @Test
    fun estFlip7SeptDistinctesAvecCarteSpeciale() {
        val mainFlip7AvecSpeciale = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7),
            iut.info1.flip7.cartes.CarteStop()
        )

        val resultat = OutilsCarte().estFlip7(mainFlip7AvecSpeciale)
        assertEquals(true, resultat)
    }

    @Test
    fun verifiePiocheInitialeVide() {
        val piocheVide = emptyList<iut.info1.flip7.cartes.Carte>()  // CORRIGÉ : emptylistOf → emptyList
        assertThrows<PiocheInvalideException> {
            OutilsCarte().verifiePiocheInitiale(piocheVide)
        }
    }

    @Test
    fun verifiePiocheInitialeTailleIncorrecte() {
        val piocheIncomplete = listOf<iut.info1.flip7.cartes.Carte>(CarteNum(1), CarteNum(2), CarteNum(3))
        assertThrows<PiocheInvalideException> {
            OutilsCarte().verifiePiocheInitiale(piocheIncomplete)
        }
    }

    @Test
    fun verifiePiocheInitialeProportionsCarteNumErronees() {

        val piocheMauvaiseRepartition = List(94) { iut.info1.flip7.cartes.CarteStop() }
        assertThrows<PiocheInvalideException> {
            OutilsCarte().verifiePiocheInitiale(piocheMauvaiseRepartition)
        }
    }

    @Test
    fun verifiePiocheInitialeProportionsCarteBonusErronees() {
        val piocheSansBonus = List(94) { CarteNum(7) }
        assertThrows<PiocheInvalideException> {
            OutilsCarte().verifiePiocheInitiale(piocheSansBonus)
        }
    }
    

    @Test
    fun verifiePiocheInitialeProportionsCarteActionErronees() {
        val piocheSansAction = List(50) { CarteNum(5) } + List(44) { CarteBonusPlus(4) }
        assertThrows<PiocheInvalideException> {
            OutilsCarte().verifiePiocheInitiale(piocheSansAction)
        }
    }

    @Test
    fun verifiePiocheInitialeValideMelangee() {
        val piocheValide = mutableListOf<iut.info1.flip7.cartes.Carte>()
        repeat(1)  { piocheValide.add(CarteNum(0)) }
        repeat(1)  { piocheValide.add(CarteNum(1)) }
        repeat(2)  { piocheValide.add(CarteNum(2)) }
        repeat(3)  { piocheValide.add(CarteNum(3)) }
        repeat(4)  { piocheValide.add(CarteNum(4)) }
        repeat(5)  { piocheValide.add(CarteNum(5)) }
        repeat(6)  { piocheValide.add(CarteNum(6)) }
        repeat(7)  { piocheValide.add(CarteNum(7)) }
        repeat(8)  { piocheValide.add(CarteNum(8)) }
        repeat(9)  { piocheValide.add(CarteNum(9)) }
        repeat(10) { piocheValide.add(CarteNum(10)) }
        repeat(11) { piocheValide.add(CarteNum(11)) }
        repeat(12) { piocheValide.add(CarteNum(12)) }
        piocheValide.add(iut.info1.flip7.cartes.CarteBonusMultiplie())
        piocheValide.add(CarteBonusPlus(2))
        piocheValide.add(CarteBonusPlus(4))
        piocheValide.add(CarteBonusPlus(6))
        piocheValide.add(CarteBonusPlus(8))
        piocheValide.add(CarteBonusPlus(10))
        repeat(3) { piocheValide.add(iut.info1.flip7.cartes.CarteStop()) }
        repeat(3) { piocheValide.add(iut.info1.flip7.cartes.Carte2ndeChance()) }
        repeat(3) { piocheValide.add(iut.info1.flip7.cartes.Carte3aLaSuite()) }
        piocheValide.shuffle()
        assertDoesNotThrow {
            OutilsCarte().verifiePiocheInitiale(piocheValide)
        }
    }

    @Test
    fun verifiePiocheInitialeValideOrdonnee() {
        val piocheOrdonnee = mutableListOf<iut.info1.flip7.cartes.Carte>()
        repeat(1)  { piocheOrdonnee.add(CarteNum(0)) }
        repeat(1)  { piocheOrdonnee.add(CarteNum(1)) }
        repeat(2)  { piocheOrdonnee.add(CarteNum(2)) }
        repeat(3)  { piocheOrdonnee.add(CarteNum(3)) }
        repeat(4)  { piocheOrdonnee.add(CarteNum(4)) }
        repeat(5)  { piocheOrdonnee.add(CarteNum(5)) }
        repeat(6)  { piocheOrdonnee.add(CarteNum(6)) }
        repeat(7)  { piocheOrdonnee.add(CarteNum(7)) }
        repeat(8)  { piocheOrdonnee.add(CarteNum(8)) }
        repeat(9)  { piocheOrdonnee.add(CarteNum(9)) }
        repeat(10) { piocheOrdonnee.add(CarteNum(10)) }
        repeat(11) { piocheOrdonnee.add(CarteNum(11)) }
        repeat(12) { piocheOrdonnee.add(CarteNum(12)) }
        piocheOrdonnee.add(iut.info1.flip7.cartes.CarteBonusMultiplie())
        piocheOrdonnee.add(CarteBonusPlus(2))
        piocheOrdonnee.add(CarteBonusPlus(4))
        piocheOrdonnee.add(CarteBonusPlus(6))
        piocheOrdonnee.add(CarteBonusPlus(8))
        piocheOrdonnee.add(CarteBonusPlus(10))
        repeat(3) { piocheOrdonnee.add(iut.info1.flip7.cartes.CarteStop()) }
        repeat(3) { piocheOrdonnee.add(iut.info1.flip7.cartes.Carte2ndeChance()) }
        repeat(3) { piocheOrdonnee.add(iut.info1.flip7.cartes.Carte3aLaSuite()) }
        assertDoesNotThrow {
            OutilsCarte().verifiePiocheInitiale(piocheOrdonnee)
        }
    }

    @Test
    fun verifieMainCorrecteAvecMainVide() {
        val mainVide = listOf<Carte>()
        assertDoesNotThrow {
            OutilsCarte().verifieMainCorrecte(mainVide)
        }
    }

    @Test
    fun verifieMainCorrecteContientDoublonEtSpeciale() {
        val carteUn = CarteNum(5)
        val carteDeux = CarteNum(5)
        val carteTrois = Carte2ndeChance()

        val mainAvecDoublonEtSpeciale = listOf<iut.info1.flip7.cartes.Carte>(carteUn, carteDeux,carteTrois)

        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecDoublonEtSpeciale )
        }
    }

    @Test
    fun verifieMainCorrecteContientMemeBonus() {
        val carteUn = CarteBonusPlus(2)
        val carteDeux = CarteBonusPlus(2)
        val mainContient2FoisMemeBonus = listOf<iut.info1.flip7.cartes.Carte>(carteUn, carteDeux)

        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainContient2FoisMemeBonus )
        }
    }


    @Test
    fun verifieMainCorrecteContientTousLesBonusDifferents() {
        val carteUn = CarteBonusPlus(2)
        val carteDeux = CarteBonusPlus(4)
        val carteTrois = CarteBonusPlus(6)
        val carteQuatre = CarteBonusPlus(8)
        val carteCinq = CarteBonusPlus(10)

        val mainCorrecteContientTousLesBonusDifferents = listOf<iut.info1.flip7.cartes.Carte>(carteUn, carteDeux, carteTrois, carteQuatre, carteCinq)

        assertDoesNotThrow { OutilsCarte().verifieMainCorrecte(mainCorrecteContientTousLesBonusDifferents) }
    }

    @Test
    fun verifieMainCorrecteContientPlusieursCarteBonusMultiplie() {
        val carteUn = CarteBonusMultiplie()
        val carteDeux = CarteBonusMultiplie()

        val mainAvecDeuxCarteBonusMultiplie = listOf<iut.info1.flip7.cartes.Carte>(carteUn, carteDeux)

        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecDeuxCarteBonusMultiplie )
        }
    }

    @Test
    fun verifieMainCorrecteContientDoublonEtSpeciale2() {
        val carteUn = CarteNum(5)
        val carteDeux = CarteNum(5)
        val carteTrois = CarteStop()

        val mainAvecDoublonEtSpeciale = listOf<iut.info1.flip7.cartes.Carte>(carteUn, carteDeux,carteTrois)

        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecDoublonEtSpeciale )
        }
    }

    @Test
    fun verifieMainCorrecteontientCarteNonAutorisee() {
        val mainAvecCarteInterdite = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(3),
            iut.info1.flip7.cartes.CarteStop(),
            iut.info1.flip7.cartes.CarteStop()
        )
        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecCarteInterdite)
        }
    }

    @Test
    fun verifieMainCorrecteontientPlusDe3CarteStop() {
        val mainAvecCartePlusieurs2nd = listOf<iut.info1.flip7.cartes.Carte>(
            iut.info1.flip7.cartes.CarteStop(),
            iut.info1.flip7.cartes.CarteStop(),
            iut.info1.flip7.cartes.CarteStop(),
            iut.info1.flip7.cartes.CarteStop()

        )
        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecCartePlusieurs2nd)
        }
    }

    @Test
    fun verifieMainCorrecteontientPlusDe3Carte2ndChance() {
        val mainAvecCartePlusieurs2nd = listOf<iut.info1.flip7.cartes.Carte>(
            iut.info1.flip7.cartes.Carte2ndeChance(),
            iut.info1.flip7.cartes.Carte2ndeChance(),
            iut.info1.flip7.cartes.Carte2ndeChance(),
            iut.info1.flip7.cartes.Carte2ndeChance()

        )
        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecCartePlusieurs2nd)
        }
    }

    @Test
    fun verifieMainCorrecteontientPlusDe3Carte3ALaSuite() {
        val mainAvecCarteInterdite = listOf<iut.info1.flip7.cartes.Carte>(
            iut.info1.flip7.cartes.Carte3aLaSuite(),
            iut.info1.flip7.cartes.Carte3aLaSuite(),
            iut.info1.flip7.cartes.Carte3aLaSuite(),
            iut.info1.flip7.cartes.Carte3aLaSuite()
        )
        assertThrows<MainInvalideException> {
            OutilsCarte().verifieMainCorrecte(mainAvecCarteInterdite)
        }
    }



    @Test
    fun verifieMainCorrecteValideStandard() {
        val mainStandard = listOf<iut.info1.flip7.cartes.Carte>(CarteNum(2), CarteBonusPlus(4), CarteNum(6))
        assertDoesNotThrow {
            OutilsCarte().verifieMainCorrecte(mainStandard)
        }
    }

    @Test
    fun verifieMainCorrecteValideMaximale() {
        val mainMaximale = listOf<iut.info1.flip7.cartes.Carte>(
            CarteNum(1), CarteNum(2), CarteNum(3),
            CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7)
        )
        assertDoesNotThrow {
            OutilsCarte().verifieMainCorrecte(mainMaximale)
        }
    }



}

