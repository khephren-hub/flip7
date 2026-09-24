package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteNum as CarteNum // Alias modifié ici
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.Carte3aLaSuite

class TestCarteNum {

    // Instance partagée réinitialisée avant chaque test avec le nouveau nom
    private lateinit var c: CarteNum

    @BeforeEach
    fun setUp() {
        c = CarteNum(6)
    }

    // --- TESTS DU CONSTRUCTEUR ---

    @Test
    fun testConstructeurValeurNegative() {
        assertThrows(IllegalArgumentException::class.java) {
            CarteNum(-1)
        }
    }

    @Test
    fun testConstructeurBorneInf() {
        val cBorneInf = CarteNum(0)
        assertEquals(0, cBorneInf.valeur)
    }

    @Test
    fun testConstructeurValide() {
        assertEquals(6, c.valeur)
    }

    @Test
    fun testConstructeurBorneSup() {
        val cBorneSup = CarteNum(12)
        assertEquals(12, cBorneSup.valeur)
    }

    @Test
    fun testConstructeurValeurTropGrande() {
        assertThrows(IllegalArgumentException::class.java) {
            CarteNum(13)
        }
    }

    // --- TESTS DE LA METHODE equals ---

    @Test
    fun testEqualsNull() {
        assertFalse(c.equals(null))
    }

    @Test
    fun testEqualsTypeDifferent() {
        assertFalse(c.equals("String"))
        assertFalse(c.equals(CarteStop()))
    }

    @Test
    fun testEqualsValeurDifferente() {
        val c2 = CarteNum(5)
        assertFalse(c.equals(c2))
    }

    @Test
    fun testEqualsValeurIdentique() {
        val c2 = CarteNum(6)
        assertTrue(c.equals(c2))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo ---

    @Test
    fun testCompareToValeurInferieure() {
        val c2 = CarteNum(5)
        assertEquals(1, c.compareTo(c2))
    }

    @Test
    fun testCompareToValeurEgale() {
        val c2 = CarteNum(6)
        assertEquals(0, c.compareTo(c2))
    }

    @Test
    fun testCompareToValeurSuperieure() {
        val c2 = CarteNum(7)
        assertEquals(-1, c.compareTo(c2))
    }

    @Test
    fun testCompareToTypeSuperieur() {
        val c2 = CarteBonusMultiplie()
        assertEquals(-1, c.compareTo(c2))
    }

    @Test
    fun testCompareToTypeMaximum() {
        val c2 = CarteStop()
        assertEquals(-1, c.compareTo(c2))
    }

    // --- TESTS DE LA METHODE toString ---

    @Test
    fun testToString() {
        val c5 = CarteNum(5)
        assertEquals("[Carte n°5]", c5.toString())
    }

    // --- TESTS DE LA METHODE estCarteNum ---

    @Test
    fun testEstCarteNumTrue() {
        assertTrue(c.estCarteNum())
    }

    @Test
    fun testEstCarteNumFalsePourStop() {
        val cStop = CarteStop()
        assertFalse(cStop.estCarteNum())
    }

    @Test
    fun testEstCarteNumFalsePourBonus() {
        val cBonus = CarteBonusPlus(4)
        assertFalse(cBonus.estCarteNum())
    }

    @Test
    fun testEstCarteNumFalsePour3aLaSuite() {
        val c3 = Carte3aLaSuite()
        assertFalse(c3.estCarteNum())
    }
}