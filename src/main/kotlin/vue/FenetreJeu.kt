package vue

import iut.info1.flip7.cartes.Carte
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.FileInputStream

data class InfoJoueurAffichage(
    val nom: String,
    val scoreTotal: Int,
    val scoreManche: Int,
    val statut: String,
    val cartes: List<Carte>
)

class FenetreJeu(private val texteRegles: String) : BorderPane() {

    private val panneauCentre = BorderPane()
    private val panneauDroit = VBox()
    private val panneauHaut = HBox()

    val bouton_regles = Button("REGLES")
    val bouton_quitter = Button("QUITTER")
    val boutonEncore = Button("ENCORE")
    val boutonStop = Button("STOP")

    private val statut = Label("Statut : ...")
    private val pioche1 = Label("Pioche\n(94 cartes)")
    private val defausse1 = Label("Défausse\n(0 cartes)")
    private val score = Label("Score total : ...")
    private val tour = Label("Au tour de : ...")

    private val cartesCentreNum = HBox()
    private val cartesBonus = HBox()
    private val cartesSpec = HBox()

    private val titreTableau = Label("Tableau des joueurs")

    init {

        val input = FileInputStream("images/LOGO_FLIP7.png")
        val vueImage = ImageView(Image(input)).apply { isPreserveRatio = true; fitWidth = 60.0 }

        statut.style = "-fx-font-size: 16px; -fx-font-weight: bold;"
        bouton_regles.style = "-fx-background-color: white; -fx-border-color: black; -fx-font-size: 12px;"
        bouton_quitter.style = "-fx-background-color: white; -fx-border-color: black; -fx-font-size: 12px;"

        val espaceG = Region(); HBox.setHgrow(espaceG, Priority.ALWAYS)
        val espaceD = Region(); HBox.setHgrow(espaceD, Priority.ALWAYS)

        panneauHaut.alignment = Pos.CENTER
        panneauHaut.padding = Insets(10.0, 15.0, 10.0, 15.0)
        panneauHaut.style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1px 1px 1px 1px;"
        panneauHaut.spacing = 15.0
        panneauHaut.children.addAll(vueImage, espaceG, statut, espaceD, bouton_regles, bouton_quitter)

        val centreHaut = HBox()
        pioche1.style = "-fx-border-color: black; -fx-padding: 5px; -fx-background-color: white; -fx-alignment: center; -fx-text-alignment: center; -fx-font-size: 11px;"
        pioche1.setPrefSize(70.0, 45.0)
        defausse1.style = "-fx-border-color: black; -fx-padding: 5px; -fx-background-color: white; -fx-alignment: center; -fx-text-alignment: center; -fx-font-size: 11px;"
        defausse1.setPrefSize(70.0, 45.0)
        score.style = "-fx-border-color: black; -fx-padding: 10px 20px; -fx-background-color: white; -fx-font-size: 14px; -fx-font-weight: bold;"

        val espaceM = Region(); HBox.setHgrow(espaceM, Priority.ALWAYS)
        centreHaut.children.addAll(pioche1, defausse1, espaceM, score)
        centreHaut.style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 0 0px 0 1px;"
        centreHaut.padding = Insets(15.0)
        centreHaut.spacing = 15.0
        centreHaut.alignment = Pos.CENTER_LEFT

        val centreCentre = VBox()
        tour.style = "-fx-font-size: 30px; -fx-font-weight: bold;"
        val mainActive = Label("Votre main active :").apply { style = "-fx-font-size: 16px; -fx-font-weight: bold;" }

        cartesCentreNum.alignment = Pos.CENTER
        cartesCentreNum.spacing = 5.0

        val cartesCentreSB = HBox()
        cartesCentreSB.alignment = Pos.CENTER
        cartesCentreSB.spacing = 10.0
        cartesCentreSB.children.addAll(cartesBonus, cartesSpec)

        val espaceC1 = Region(); VBox.setVgrow(espaceC1, Priority.ALWAYS)
        val espaceC2 = Region(); VBox.setVgrow(espaceC2, Priority.ALWAYS)

        centreCentre.children.addAll(espaceC1, tour, mainActive, cartesCentreNum, cartesCentreSB, espaceC2)
        centreCentre.style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 0 0px 0 1px;"
        centreCentre.spacing = 15.0
        centreCentre.alignment = Pos.CENTER

        val centreBottom = HBox()
        boutonEncore.style = "-fx-background-color: #A2E8A2; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;"
        boutonEncore.setPrefSize(140.0, 40.0)
        boutonStop.style = "-fx-background-color: #FFD4A2; -fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;"
        boutonStop.setPrefSize(140.0, 40.0)

        val espaceB1 = Region(); HBox.setHgrow(espaceB1, Priority.ALWAYS)
        val espaceB2 = Region(); HBox.setHgrow(espaceB2, Priority.ALWAYS)
        val espaceB3 = Region(); HBox.setHgrow(espaceB3, Priority.ALWAYS)

        centreBottom.children.addAll(espaceB1, boutonEncore, espaceB2, boutonStop, espaceB3)
        centreBottom.style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 0 0px 1px 1px;"
        centreBottom.padding = Insets(0.0, 0.0, 30.0, 0.0)
        centreBottom.alignment = Pos.CENTER

        panneauCentre.top = centreHaut
        panneauCentre.center = centreCentre
        panneauCentre.bottom = centreBottom

        panneauDroit.style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 0 1px 1px 1px;"
        panneauDroit.padding = Insets(15.0)
        panneauDroit.spacing = 15.0
        panneauDroit.setPrefWidth(380.0)
        titreTableau.style = "-fx-font-size: 18px; -fx-font-weight: bold;"
        panneauDroit.children.add(titreTableau)

        this.center = panneauCentre
        this.right = panneauDroit
        this.top = panneauHaut

        bouton_regles.setOnAction { afficherPopUpRegles(this.scene.window as Stage) }
        bouton_quitter.setOnAction { (this.scene.window as Stage).close() }
    }

    fun setStatut(texte: String) { statut.text = "Statut : $texte" }

    fun setPiocheDefausse(nbPioche: Int, nbDefausse: Int) {
        pioche1.text = "Pioche\n($nbPioche cartes)"
        defausse1.text = "Défausse\n($nbDefausse cartes)"
    }

    fun afficherJoueurActif(info: InfoJoueurAffichage) {
        tour.text = "Au tour de : ${info.nom}"
        score.text = "Score total : ${info.scoreTotal}    |    Manche : ${info.scoreManche}"
        cartesCentreNum.children.clear()
        cartesBonus.children.clear()
        cartesSpec.children.clear()
        for (carte in info.cartes) {
            val vc = vueCarte(carte, 60.0)
            when (zoneDe(carte)) {
                0 -> cartesCentreNum.children.add(vc)
                1 -> cartesBonus.children.add(vc)
                else -> cartesSpec.children.add(vc)
            }
        }
    }

    fun afficherJoueursAttente(joueurs: List<InfoJoueurAffichage>) {
        panneauDroit.children.setAll(titreTableau)
        for (info in joueurs) {
            panneauDroit.children.add(creerBoiteJoueur(info))
        }
    }

    private fun couleurPourStatut(statut: String): String = when (statut) {
        "PERDU" -> "#FFD2D2"
        "STOP" -> "#FFE6C2"
        else -> "white"
    }

    private fun creerBoiteJoueur(info: InfoJoueurAffichage): VBox {
        val boite = VBox().apply {
            spacing = 10.0
            style = "-fx-background-color: ${couleurPourStatut(info.statut)}; -fx-border-color: black; -fx-padding: 10px;"
        }
        val infoHBox = HBox().apply {
            alignment = Pos.CENTER_LEFT
            val nom = Label(info.nom).apply { style = "-fx-font-weight: bold; -fx-font-size: 11px;" }
            val scoreT = Label("Score total : ${info.scoreTotal}").apply { style = "-fx-font-size: 10px;" }
            val scoreM = Label("Score manche : ${info.scoreManche}").apply { style = "-fx-font-size: 10px;" }
            val statutJ = Button(info.statut).apply { style = "-fx-background-color: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 10px;" }
            val e1 = Region().apply { HBox.setHgrow(this, Priority.ALWAYS) }
            val e2 = Region().apply { HBox.setHgrow(this, Priority.ALWAYS) }
            val e3 = Region().apply { HBox.setHgrow(this, Priority.ALWAYS) }
            children.addAll(nom, e1, scoreT, e2, scoreM, e3, statutJ)
        }
        val fpNum = FlowPane().apply { hgap = 2.0; vgap = 2.0 }
        val fpBonus = FlowPane().apply { hgap = 2.0; vgap = 2.0 }
        val fpSpec = FlowPane().apply { hgap = 2.0; vgap = 2.0 }
        for (carte in info.cartes) {
            val vc = vueCarte(carte, 26.0)
            when (zoneDe(carte)) {
                0 -> fpNum.children.add(vc)
                1 -> fpBonus.children.add(vc)
                else -> fpSpec.children.add(vc)
            }
        }
        boite.children.addAll(infoHBox, HBox(10.0, fpNum, fpBonus, fpSpec))
        return boite
    }

    // 0 = ligne numéro, 1 = bonus, 2 = spéciales. On lit le type via les méthodes du prof.
    private fun zoneDe(c: Carte): Int = when {
        c.estCarteNum() -> 0
        c.estCarteBonusPlus() || c.estCarteBonusMultiplie() -> 1
        else -> 2
    }

    private fun cheminImage(c: Carte): String = when {
        c.estCarteNum() -> "images/images_carte/${c.valeur}.png"
        c.estCarteBonusPlus() -> "images/images_carte/+${c.valeur}.png"
        c.estCarteBonusMultiplie() -> "images/images_carte/x2.png"
        c.estCarteStop() -> "images/images_carte/stop.png"
        c.estCarte2ndeChance() -> "images/images_carte/2emechance.png"
        c.estCarte3aLaSuite() -> "images/images_carte/3alasuite.png"
        else -> ""
    }

    private fun texteCarte(c: Carte): String = when {
        c.estCarteNum() -> "${c.valeur}"
        c.estCarteBonusPlus() -> "+${c.valeur}"
        c.estCarteBonusMultiplie() -> "x2"
        c.estCarteStop() -> "STOP"
        c.estCarte2ndeChance() -> "2e chance"
        c.estCarte3aLaSuite() -> "3 à la suite"
        else -> "?"
    }

    private fun vueCarte(carte: Carte, largeur: Double): Node {
        return try {
            ImageView(Image(FileInputStream(cheminImage(carte)))).apply { isPreserveRatio = true; fitWidth = largeur }
        } catch (e: Exception) {
            Label(texteCarte(carte)).apply {
                setPrefSize(largeur, largeur * 1.4)
                alignment = Pos.CENTER
                style = "-fx-border-color: black; -fx-background-color: white; -fx-font-weight: bold; -fx-font-size: 11px;"
            }
        }
    }

    fun afficherDialogueCiblage(
        titre: String,
        cheminImageCarte: String,
        joueursCiblables: List<String>,
        surChoix: (String) -> Unit
    ) {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(this.scene.window)
        dialog.title = titre

        val conteneur = VBox(15.0).apply {
            padding = Insets(25.0); alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1.5;"
        }
        conteneur.children.add(Label(titre).apply { font = Font.font("Arial", FontWeight.BOLD, 20.0) })

        try {
            val img = ImageView(Image(FileInputStream(cheminImageCarte))).apply { isPreserveRatio = true; fitWidth = 80.0 }
            conteneur.children.add(img)
        } catch (e: Exception) { }

        conteneur.children.add(Label("Sélectionnez un joueur :").apply { style = "-fx-font-size: 13px;" })

        val ligneJoueurs = HBox(10.0).apply { alignment = Pos.CENTER }
        for (nom in joueursCiblables) {
            ligneJoueurs.children.add(Button(nom).apply {
                style = "-fx-background-color: white; -fx-border-color: black; -fx-cursor: hand;"
                setOnAction { dialog.close(); surChoix(nom) }
            })
        }
        conteneur.children.add(ligneJoueurs)

        dialog.scene = Scene(conteneur, 420.0, 280.0)
        dialog.showAndWait()
    }

    fun afficherDialogueFinManche(message: String, surSuivant: () -> Unit) {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(this.scene.window)
        dialog.title = "Fin de la manche"

        val conteneur = VBox(20.0).apply {
            padding = Insets(25.0); alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1.5;"
        }
        try {
            conteneur.children.add(ImageView(Image(FileInputStream("images/LOGO_FLIP7.png"))).apply { isPreserveRatio = true; fitWidth = 200.0 })
        } catch (e: Exception) { }

        conteneur.children.add(Label(message).apply { font = Font.font("Arial", FontWeight.BOLD, 22.0) })
        conteneur.children.add(Button("Manche suivante").apply {
            style = "-fx-background-color: #D6C7E8; -fx-border-color: black; -fx-cursor: hand;"
            font = Font.font("Arial", FontWeight.BOLD, 14.0)
            setPrefSize(180.0, 38.0)
            setOnAction { dialog.close() }
        })

        dialog.setOnHidden { surSuivant() }

        dialog.scene = Scene(conteneur, 420.0, 320.0)
        dialog.width = 420.0
        dialog.height = 320.0
        dialog.isResizable = false
        dialog.showAndWait()
    }

    fun afficherDialogueDoublon(nomJoueur: String, surOk: () -> Unit) {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(this.scene.window)
        dialog.title = "Manche perdue"

        val conteneur = VBox(20.0).apply {
            padding = Insets(25.0); alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1.5;"
        }
        val message = Label("$nomJoueur a pioché un doublon.\nIl perd cette manche !").apply {
            font = Font.font("Arial", FontWeight.BOLD, 16.0)
            style = "-fx-text-alignment: center;"
        }
        val btnOk = Button("OK").apply {
            style = "-fx-background-color: #FFD2D2; -fx-border-color: black; -fx-border-width: 1; -fx-cursor: hand;"
            font = Font.font("Arial", FontWeight.BOLD, 14.0)
            setPrefSize(150.0, 35.0)
            setOnAction { dialog.close() }
        }
        dialog.setOnHidden { surOk() }

        conteneur.children.addAll(message, btnOk)
        dialog.scene = Scene(conteneur, 380.0, 200.0)
        dialog.width = 380.0
        dialog.height = 200.0
        dialog.isResizable = false
        dialog.showAndWait()
    }

    private fun afficherPopUpRegles(owner: Stage) {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(owner)
        dialog.title = "Règles du Jeu"

        val conteneur = VBox(20.0).apply {
            padding = Insets(25.0); alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3; -fx-border-color: black; -fx-border-width: 1.5;"
        }

        val logoLabel = Label()
        val input = FileInputStream("images/LOGO_FLIP7.png")
        logoLabel.graphic = ImageView(Image(input)).apply { isPreserveRatio = true; fitWidth = 250.0 }

        val labelTitre = Label("RÈGLES DU JEU").apply {
            font = Font.font("Arial", FontWeight.BOLD, 18.0)
            padding = Insets(8.0, 25.0, 8.0, 25.0)
            style = "-fx-border-color: black; -fx-background-color: white; -fx-font-weight: bold;"
        }

        val zoneTexte = TextArea(texteRegles).apply {
            isEditable = false; isWrapText = true
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