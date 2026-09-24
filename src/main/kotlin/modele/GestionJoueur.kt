package fr.iut.flip7.modele

class GestionJoueur {

    val joueursAjoutes: MutableList<Joueur> = mutableListOf()
    val joueursSelectionnes: MutableList<Joueur> = mutableListOf()


    fun getJoueursPartie(): List<Joueur> {
        return joueursSelectionnes.toList()
    }
    fun ajouterJoueur(nom: String): Boolean {
        val nomTrim = nom.trim()
        if (nomTrim.isBlank()) return false

        if (joueursAjoutes.any { it.nom == nomTrim } || joueursSelectionnes.any { it.nom == nomTrim }) return false

        joueursAjoutes.add(Joueur(nomTrim))
        return true
    }

    fun supprimerJoueur(joueur: Joueur): Boolean {
        return joueursAjoutes.remove(joueur)
    }

    fun deplacerDroite(joueur: Joueur): Boolean {
        if (!joueursAjoutes.contains(joueur)) return false
        joueursAjoutes.remove(joueur)
        joueursSelectionnes.add(joueur)
        return true
    }

    fun deplacerGauche(joueur: Joueur): Boolean {
        if (!joueursSelectionnes.contains(joueur)) return false
        joueursSelectionnes.remove(joueur)
        joueursAjoutes.add(joueur)
        return true
    }

    fun peutLancerPartie(): Boolean {
        return joueursSelectionnes.size in 2..4
    }
}