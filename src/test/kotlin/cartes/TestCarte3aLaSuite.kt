package cartes

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.Carte2ndeChance

class TestCarte3aLaSuite {

    private lateinit var c: Carte3aLaSuite

    @BeforeEach
    fun setUp() {
        c = Carte3aLaSuite()
    }

    // --- TESTS DU Constructeur ---

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
        val autre = Carte3aLaSuite()
        assertTrue(c.equals(autre))
    }

    @Test
    fun testEqualsMemeInstance() {
        assertTrue(c.equals(c))
    }

    // --- TESTS DE LA METHODE compareTo ---

    @Test
    fun testCompareToTypeInferieur() {
        assertEquals(1, c.compareTo(Carte2ndeChance()))
    }

    @Test
    fun testCompareToMemeType() {
        assertEquals(0, c.compareTo(Carte3aLaSuite()))
    }

    @Test
    fun testCompareToTypeSuperieur() {
        assertEquals(-1, c.compareTo(CarteStop()))
    }

    // --- TESTS DE LA METHODE estCarte3aLaSuite ---

    @Test
    fun testEstCarte3aLaSuiteTrue() {
        assertTrue(c.estCarte3aLaSuite())
    }

    @Test
    fun testEstCarte3aLaSuiteFalse() {
        assertFalse(CarteNum(6).estCarte3aLaSuite())
    }

    // --- TESTS DE LA METHODE toString ---
    
    @Test
    fun testToString() {
        assertEquals("[Carte 3 à la suite]", c.toString())
    }
}