package vue

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class FenetreFinJeu(
    classement: List<Pair<String, Int>>,
    nomGagnant: String,
    parFlip7: Boolean
) : BorderPane() {

    val boutonRetourMenu = Button("Retour au menu")
    val boutonRelancerPartie = Button("Relancer une partie")

    init {
        this.padding = Insets(30.0)
        this.style = "-fx-background-color: #FCF8E3;"

        // Partie haut
        val partieHaut = VBox(8.0)
        partieHaut.alignment = Pos.CENTER
        partieHaut.padding = Insets(0.0, 0.0, 25.0, 0.0)

        val labelFinDePartie = Label("Fin de Partie").apply {
            style = "-fx-font-size: 16px; -fx-text-fill: #555555; -fx-font-family: 'Arial';"
        }
        val labelTitre = Label("Félicitations !").apply {
            style = "-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #222222; -fx-font-family: 'Arial';"
        }
        val texteGagnant = if (parFlip7) "$nomGagnant a gagné avec un FLIP 7 !" else "$nomGagnant a gagné !"
        val labelJoueurGagnant = Label(texteGagnant).apply {
            style = "-fx-font-size: 18px; -fx-text-fill: #333333; -fx-font-family: 'Arial';"
        }

        partieHaut.children.addAll(labelFinDePartie, labelTitre, labelJoueurGagnant)
        this.top = partieHaut

        // Partie classement
        val grilleClassement = GridPane().apply {
            alignment = Pos.CENTER
            hgap = 0.0
            vgap = 12.0
        }

        val couleursFond = listOf("#FBC02D", "#CFD8DC", "#E65100", "#FFFFFF")
        val couleursTexte = listOf("#222222", "#37474F", "#FFFFFF", "#333333")

        for (i in classement.indices) {
            val (nom, score) = classement[i]
            val index = i.coerceAtMost(couleursFond.size - 1)
            val styleFond = couleursFond[index]
            val styleTexte = couleursTexte[index]
            val styleBordure = "-fx-border-color: #333333; -fx-border-width: 1px;"

            val labelNombreJoueur = Label("  ${i + 1}.  ").apply {
                style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: $styleFond; " +
                        "-fx-text-fill: $styleTexte; $styleBordure " +
                        "-fx-background-radius: 10px 0px 0px 10px; -fx-border-radius: 10px 0px 0px 10px;"
                alignment = Pos.CENTER
                setPrefSize(60.0, 40.0)
            }
            val labelNomJoueur = Label(nom).apply {
                style = "-fx-font-size: 16px; -fx-background-color: $styleFond; -fx-text-fill: $styleTexte; " +
                        "-fx-border-color: #333333; -fx-border-width: 1px 0px 1px 0px;"
                alignment = Pos.CENTER
                setPrefSize(240.0, 40.0)
            }
            val labelScoreJoueur = Label("score : $score  ").apply {
                style = "-fx-font-size: 16px; -fx-background-color: $styleFond; -fx-text-fill: $styleTexte; " +
                        "$styleBordure -fx-background-radius: 0px 10px 10px 0px; -fx-border-radius: 0px 10px 10px 0px;"
                alignment = Pos.CENTER_RIGHT
                setPrefSize(140.0, 40.0)
            }

            grilleClassement.add(labelNombreJoueur, 0, i)
            grilleClassement.add(labelNomJoueur, 1, i)
            grilleClassement.add(labelScoreJoueur, 2, i)
        }
        this.center = grilleClassement

        // Partie bas
        val partieBas = HBox(25.0)
        partieBas.alignment = Pos.CENTER
        partieBas.padding = Insets(25.0, 0.0, 10.0, 0.0)

        val styleDeBaseBouton = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;"

        boutonRetourMenu.apply {
            style = "$styleDeBaseBouton -fx-background-color: brown; -fx-text-fill: white;"
            setPrefSize(220.0, 45.0)
        }
        boutonRelancerPartie.apply {
            style = "$styleDeBaseBouton -fx-background-color: purple; -fx-text-fill: white;"
            setPrefSize(220.0, 45.0)
        }

        partieBas.children.addAll(boutonRetourMenu, boutonRelancerPartie)
        this.bottom = partieBas
    }
}