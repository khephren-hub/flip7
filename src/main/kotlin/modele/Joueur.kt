package fr.iut.flip7.modele

import iut.info1.flip7.IJoueur

data class Joueur(
    val nom: String,
    val score: Int = 0
) : IJoueur {
    override fun donneNom(): String = nom
}