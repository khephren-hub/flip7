package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteNum

class TestCarteStop {

    private lateinit var c: CarteStop

    @BeforeEach
    fun setUp() {
        c = CarteStop()
    }

    // --- TESTS DU Constructeur ---

    // Pas de paramètre donc val par défaut à 0
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
        val autre = CarteStop()
        assertTrue(c.equals(autre))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo ---

    @Test
    fun testCompareToTypeInferieurImmédiat() {
        assertEquals(1, c.compareTo(Carte3aLaSuite()))
    }

    @Test
    fun testCompareToTypeInferieurEloigne() {
        assertEquals(1, c.compareTo(Carte2ndeChance()))
    }

    @Test
    fun testCompareToMemeType() {
        assertEquals(0, c.compareTo(CarteStop()))
    }

    // --- TESTS DE LA METHODE estCarteStop ---

    @Test
    fun testEstCarteStopTrue() {
        assertTrue(c.estCarteStop())
    }

    @Test
    fun testEstCarteStopFalse() {
        assertFalse(CarteNum(6).estCarteStop())
    }

    // --- TESTS DE LA METHODE toString ---

    @Test
    fun testToString() {
        assertEquals("[Carte Stop]", c.toString())
    }
}