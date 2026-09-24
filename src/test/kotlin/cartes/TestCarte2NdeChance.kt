package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop

class TestCarte2NdeChance {

    private lateinit var c: Carte2ndeChance

    @BeforeEach
    fun setUp() {
        c = Carte2ndeChance()
    }

    @Test
    fun testConstructeur() {
        assertEquals(0, c.valeur)
    }

    // --- TESTS DE LA METHODE equals ---

    @Test
    fun testEqualsNull() {
        assertFalse(c.equals(null))
    }

    @Test
    fun testEqualsTypeDifferent() {
        assertFalse(c.equals("String"))
        assertFalse(c.equals(CarteNum(6)))
    }

    @Test
    fun testEqualsMemeType() {
        val c2 = Carte2ndeChance()
        assertTrue(c.equals(c2))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo ---

    @Test
    fun testCompareToTypeInferieur() {
        val c2 = CarteNum(6)
        assertEquals(1, c.compareTo(c2))
    }

    @Test
    fun testCompareToMemeType() {
        val c2 = Carte2ndeChance()
        assertEquals(0, c.compareTo(c2))
    }

    @Test
    fun testCompareToTypeSuperieur() {
        val c2 = CarteStop()
        assertEquals(-1, c.compareTo(c2))
    }

    // --- TESTS DE LA METHODE estCarte2ndeChance ---

    @Test
    fun testEstCarte2ndeChanceTrue() {
        assertTrue(c.estCarte2ndeChance())
    }

    @Test
    fun testEstCarte2ndeChanceFalse() {
        val cNum = CarteNum(6)
        assertFalse(cNum.estCarte2ndeChance())
    }

    // --- TESTS DE LA METHODE toString ---

    @Test
    fun testToString() {
        assertEquals("[Carte 2nde chance]", c.toString())
    }
}