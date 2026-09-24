package fr.iut.flip7.modele


import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop

object ModeleFlip7 {

    fun construirePaquet(): List<Carte> {
        val cartes = mutableListOf<Carte>()

        cartes.add(CarteNum(0))
        for (n in 1..12) {
            repeat(n) { cartes.add(CarteNum(n)) }
        }

        for (b in listOf(2, 4, 6, 8, 10)) {
            cartes.add(CarteBonusPlus(b))
        }
        cartes.add(CarteBonusMultiplie())

        repeat(3) { cartes.add(CarteStop()) }
        repeat(3) { cartes.add(Carte3aLaSuite()) }
        repeat(3) { cartes.add(Carte2ndeChance()) }

        return cartes
    }

    fun creerPartie(joueurs: List<IJoueur>, scoreFinPartie: Int): Flip7 {
        return Flip7(
            nbJoueurs = joueurs.size,
            joueurs = joueurs,
            cartes = construirePaquet(),
            scoreFinPartie = scoreFinPartie
        )
    }
}