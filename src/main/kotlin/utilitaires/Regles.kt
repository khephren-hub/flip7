package fr.iut.flip7.util

object Regles {
    val TEXTE = """
        FLIP 7 - RÈGLES DU JEU

        But du jeu :
        Marquer le plus de points possible.
        Le premier joueur à atteindre 200 points gagne la partie.

        À votre tour :
        - ENCORE : vous piochez UNE carte.
        - STOP : vous vous arrêtez et gardez vos points de la manche.
        Vous ne piochez qu'une carte par tour, ensuite c'est au joueur suivant.

        Les cartes :
        - NUMÉRO (0 à 12) : leur valeur s'ajoute à votre score de la manche.
        - DOUBLON : si vous piochez deux fois le même numéro, vous perdez
          TOUS vos points de la manche et vous êtes éliminé pour cette manche.
        - BONUS (+2, +4, ...) : s'ajoutent à votre score.
        - x2 : double la somme de vos cartes numéro.
        - 2e CHANCE : vous protège une fois d'un doublon.
        - FREEZE : choisissez un joueur, il est gelé et ne joue plus
          de la manche (il garde ses points).
        - 3 À LA SUITE : choisissez un joueur, il pioche 3 cartes d'affilée.

        Fin de la manche :
        Elle se termine quand tous les joueurs sont arrêtés, gelés ou éliminés.
        Les scores de manche sont ajoutés au score total, puis une nouvelle
        manche commence.
    """.trimIndent()
}