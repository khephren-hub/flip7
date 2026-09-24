package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteStop

class TestCarteBonusPlus {

    private lateinit var c: CarteBonusPlus

    @BeforeEach
    fun setUp() {
        c = CarteBonusPlus(6)
    }

    // --- TESTS DU Constructeur ---

    @Test
    fun testConstructeurValeurPaireInferieure() {
        assertThrows<IllegalArgumentException> { CarteBonusPlus(0) }
    }

    @Test
    fun testConstructeurValeurBorneInferieure() {
        assertEquals(2, CarteBonusPlus(2).valeur)
    }

    @Test
    fun testConstructeurValeurImpaireInvalide() {
        assertThrows<IllegalArgumentException> { CarteBonusPlus(3) }
    }

    @Test
    fun testConstructeurValeurMediane() {
        assertEquals(6, c.valeur)
    }

    @Test
    fun testConstructeurValeurBorneSuperieure() {
        assertEquals(10, CarteBonusPlus(10).valeur)
    }

    @Test
    fun testConstructeurValeurPaireSuperieure() {
        assertThrows<IllegalArgumentException> { CarteBonusPlus(12) }
    }

    // --- TESTS DE LA METHODE equals ---

    @Test
    fun testEqualsNull() {
        assertFalse(c.equals(null))
    }

    @Test
    fun testEqualsTypeDifferent() {
        assertFalse(c.equals(CarteStop()))
    }

    @Test
    fun testEqualsMemeTypeValeurDifferente() {
        assertFalse(c.equals(CarteBonusPlus(10)))
    }

    @Test
    fun testEqualsMemeTypeMemValeur() {
        assertTrue(c.equals(CarteBonusPlus(6)))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo

    @Test
    fun testCompareToTypeInferieur() {
        assertEquals(1, c.compareTo(CarteBonusMultiplie()))
    }

    @Test
    fun testCompareToMemeTypeValeurInferieure() {
        assertEquals(1, c.compareTo(CarteBonusPlus(2)))
    }

    @Test
    fun testCompareToMemeTypeValeurEgale() {
        assertEquals(0, c.compareTo(CarteBonusPlus(6)))
    }

    @Test
    fun testCompareToMemeTypeValeurSuperieure() {
        assertEquals(-1, c.compareTo(CarteBonusPlus(10)))
    }

    @Test
    fun testCompareToTypeSuperieur() {
        assertEquals(-1, c.compareTo(Carte2ndeChance()))
    }

    // --- TESTS DE LA METHODE estCarteBonusPlus ---

    @Test
    fun testEstCarteBonusPlusTrue() {
        assertTrue(c.estCarteBonusPlus())
    }

    @Test
    fun testEstCarteBonusPlusFalse() {
        assertFalse(CarteStop().estCarteBonusPlus())
    }

    // --- TESTS DE LA METHODE toString ---

    @Test
    fun testToString() {
        assertEquals("[Carte Bonus +4]", CarteBonusPlus(4).toString())
    }
}