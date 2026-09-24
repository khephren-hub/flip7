package vue

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.FileInputStream

class FenetreAccueil(private val texteRegles: String) : BorderPane(){
    private val panneauHaut : Label
    private val panneauCentre : VBox
    val buttonLancerJeu: Button
    val buttonRegleJeu: Button
    val buttonQuitter: Button

    init {
        this.panneauHaut = Label()
        this.panneauCentre = VBox(8.0)

        val input = FileInputStream("images/LOGO_FLIP7.png")
        val image = Image(input)
        val vueImage = ImageView(image)
        vueImage.isPreserveRatio = true

        vueImage.fitWidthProperty().bind(this.widthProperty().multiply(0.5))

        panneauHaut.graphic = vueImage

        buttonLancerJeu = Button("Lancer le jeu").apply {
            style = "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 0;" +
                    "-fx-cursor: hand;" +
                    "-fx-background-color: #FFDD43;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 1px;"
            prefWidthProperty().bind(this@FenetreAccueil.widthProperty().multiply(0.45))
            prefHeightProperty().bind(this@FenetreAccueil.heightProperty().multiply(0.08))
            maxHeight = Double.MAX_VALUE
            maxWidth = Double.MAX_VALUE
        }
        buttonRegleJeu = Button("Règles du jeu").apply {
            style = "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 0;" +
                    "-fx-cursor: hand;" +
                    "-fx-background-color: #FFDD43;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 1px;"
            prefWidthProperty().bind(this@FenetreAccueil.widthProperty().multiply(0.45))
            prefHeightProperty().bind(this@FenetreAccueil.heightProperty().multiply(0.08))
            maxHeight = Double.MAX_VALUE
            maxWidth = Double.MAX_VALUE
        }
        buttonQuitter = Button("Quitter").apply {
            style = "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;" +
                    "-fx-background-color: transparent;"
            prefWidthProperty().bind(this@FenetreAccueil.widthProperty().multiply(0.45))
            prefHeightProperty().bind(this@FenetreAccueil.heightProperty().multiply(0.08))
            maxHeight = Double.MAX_VALUE
            maxWidth = Double.MAX_VALUE
        }

        this.padding = Insets(30.0)
        this.style = "-fx-background-color: #FCF8E3;"
        panneauCentre.alignment = Pos.CENTER
        panneauCentre.padding = Insets(25.0)
        panneauCentre.maxWidth= Double.MAX_VALUE

        panneauCentre.children.addAll(buttonLancerJeu, buttonRegleJeu, buttonQuitter)
        this.top = panneauHaut
        BorderPane.setAlignment(panneauHaut, Pos.CENTER)
        this.center = panneauCentre

        buttonRegleJeu.setOnAction {
            val proprietaire = this.scene.window as Stage
            afficherPopUpRegles(proprietaire)
        }
    }

    private fun afficherPopUpRegles(owner: Stage) {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(owner)
        dialog.title = "Règles du Jeu"

        val conteneur = VBox(20.0).apply {
            padding = Insets(25.0)
            alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1.5;"
        }

        val logoLabel = Label()
        val input = FileInputStream("images/LOGO_FLIP7.png")
        val vueImage = ImageView(Image(input)).apply {
            isPreserveRatio = true
            fitWidth = 250.0
        }
        logoLabel.graphic = vueImage

        val labelTitre = Label("RÈGLES DU JEU").apply {
            font = Font.font("Arial", FontWeight.BOLD, 18.0)
            padding = Insets(8.0, 25.0, 8.0, 25.0)
            style = "-fx-border-color: black; -fx-background-color: white; -fx-font-weight: bold;"
        }

        val zoneTexte = TextArea(texteRegles).apply {
            isEditable = false
            isWrapText = true
            setPrefSize(400.0, 350.0)
            style = """
                -fx-control-inner-background: #FFF9E5; 
                -fx-text-fill: #1A1A1A;
                -fx-font-family: 'Courier New';
                -fx-font-size: 13px;
            """.trimIndent()
        }

        val btnOk = Button("OK").apply {
            style = "-fx-background-color: #D6C7E8; -fx-border-color: black; -fx-border-width: 1; -fx-cursor: hand;"
            font = Font.font("Arial", FontWeight.BOLD, 14.0)
            setPrefSize(150.0, 35.0)
            setOnAction { dialog.close() }
            setOnMouseEntered { style = "-fx-background-color: #BDA7DE; -fx-border-color: black; -fx-border-width: 1.2; -fx-cursor: hand;" }
            setOnMouseExited { style = "-fx-background-color: #D6C7E8; -fx-border-color: black; -fx-border-width: 1; -fx-cursor: hand;" }
        }

        conteneur.children.addAll(logoLabel, labelTitre, zoneTexte, btnOk)
        dialog.scene = Scene(conteneur, 550.0, 600.0)
        dialog.showAndWait()
    }
}