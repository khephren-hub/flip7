package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum

class TestCarteBonusMultiplie {

    private lateinit var c: CarteBonusMultiplie

    @BeforeEach
    fun setUp() {
        c = CarteBonusMultiplie()
    }

    // --- TESTS DU constructeur ---

    // Pas de paramètre mais par défaut elle doit valoir 2
    @Test
    fun testConstructeur() {
        assertEquals(2, c.valeur)
    }

    // --- TESTS DE LA METHODE equals --

    @Test
    fun testEqualsNull() {
        assertFalse(c.equals(null))
    }

    @Test
    fun testEqualsTypeDifferent() {
        assertFalse(c.equals(CarteNum(6)))
    }

    @Test
    fun testEqualsMemeType() {
        val autre = CarteBonusMultiplie()
        assertTrue(c.equals(autre))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo ---

    @Test
    fun testCompareToTypeInferieur() {
        assertEquals(1, c.compareTo(CarteNum(6)))
    }

    @Test
    fun testCompareToMemeType() {
        assertEquals(0, c.compareTo(CarteBonusMultiplie()))
    }

    @Test
    fun testCompareToTypeSuperieur() {
        assertEquals(-1, c.compareTo(CarteBonusPlus(2)))
    }

    // --- TESTS DE LA METHODE estCarteBonusMultiplie ---

    @Test
    fun testEstCarteBonusMultiplieTrue() {
        assertTrue(c.estCarteBonusMultiplie())
    }

    @Test
    fun testEstCarteBonusMultiplieFalse() {
        assertFalse(CarteNum(6).estCarteBonusMultiplie())
    }

    // --- TESTS DE LA METHODE toString ---

    @Test
    fun testToString() {
        assertEquals("[Carte Bonus x2]", c.toString())
    }
}