package fr.iut.flip7.vue

import fr.iut.flip7.modele.Joueur
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.*

class FenetreJoueur : BorderPane() {

    val labelTitre      = Label("Gestion des Joueurs")
    val listeGaucheView = VBox(5.0)
    val listeDroiteView = VBox(5.0)
    val boutonAjouter  = Button("Ajouter joueur")
    val boutonSupprimer = Button("Supprimer joueur")
    val boutonDroit    = Button(">")
    val boutonGauche   = Button("<")
    val boutonLancer   = Button("Lancer la partie")

    private val sliderScore = Slider(50.0, 200.0, 100.0)

    private val labelT1 = Label("Joueurs ajoutés")
    private val labelT2 = Label("Joueurs sélectionnés")

    private val gridMenus  = GridPane()
    private val gridBoutons = GridPane()

    init {

        this.style = "-fx-padding: 15px; -fx-background-color: #FCF8E3;"
        val styleTitre   = "-fx-text-fill: #000000; -fx-font-size: 24px; -fx-font-weight: bold;"
        val styleBloc    = "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px; " +
                "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 15px; " +
                "-fx-min-width: 150px; -fx-min-height: 200px;"
        val styleBaseBtn = "-fx-text-fill: white; -fx-font-weight: bold; -fx-border-width: 2px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"
        val styleVert    = "-fx-background-color: #2e8b57; -fx-border-color: black; $styleBaseBtn"
        val styleRouge   = "-fx-background-color: #cd5c5c; -fx-border-color: black; $styleBaseBtn"
        val styleOrange  = "-fx-background-color: #ff8c00; -fx-border-color: black; $styleBaseBtn"
        val styleNav     = "-fx-background-color: #FFDD43; -fx-border-color: black; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"

        listOf(
            boutonAjouter  to styleVert,
            boutonSupprimer to styleRouge,
            boutonLancer   to styleOrange,
            boutonDroit    to styleNav,
            boutonGauche   to styleNav
        ).forEach { (btn, styleDeBase) ->
            btn.style = styleDeBase
            btn.setOnMouseEntered { btn.style = styleDeBase + "; -fx-border-color: white;" }
            btn.setOnMouseExited { btn.style = styleDeBase }
        }

        // TOP
        labelTitre.style = styleTitre

        val labelScore = Label("Score à atteindre : 100").apply {
            style = "-fx-text-fill: #000000; -fx-font-weight: bold; -fx-font-size: 14px;"
        }
        sliderScore.apply {
            isShowTickLabels = true
            majorTickUnit = 50.0
            minorTickCount = 0
            maxWidth = 300.0
        }
        sliderScore.valueProperty().addListener { _, _, v ->
            labelScore.text = "Score à atteindre : ${v.toInt()}"
        }
        val blocScore = VBox(5.0, labelScore, sliderScore).apply { alignment = Pos.CENTER }

        val conteneurHaut = VBox(10.0, labelTitre, blocScore).apply {
            alignment = Pos.CENTER
            style = "-fx-padding: 0 0 15 0;"
        }
        this.top = conteneurHaut

        // CENTER
        gridMenus.alignment = Pos.CENTER
        gridMenus.hgap = 20.0
        gridMenus.vgap = 10.0

        labelT1.style = "-fx-text-fill: #000000; -fx-font-weight: bold; -fx-font-size: 14px;"
        listeGaucheView.style = styleBloc
        GridPane.setConstraints(labelT1,         0, 0, 2, 1)
        GridPane.setConstraints(listeGaucheView, 0, 1, 2, 6)

        labelT2.style = "-fx-text-fill: #000000; -fx-font-weight: bold; -fx-font-size: 14px;"
        listeDroiteView.style = styleBloc
        GridPane.setConstraints(labelT2,         3, 0, 2, 1)
        GridPane.setConstraints(listeDroiteView, 3, 1, 2, 6)

        boutonDroit.maxWidth  = 50.0
        boutonGauche.maxWidth = 50.0
        GridPane.setConstraints(boutonDroit,  2, 2)
        GridPane.setConstraints(boutonGauche, 2, 3)

        gridMenus.children.addAll(
            labelT1, listeGaucheView,
            labelT2, listeDroiteView,
            boutonDroit, boutonGauche
        )
        this.center = gridMenus

        // BOTTOM
        gridBoutons.alignment = Pos.CENTER
        val col = ColumnConstraints()
        col.percentWidth = 25.0
        gridBoutons.columnConstraints.addAll(col, col, col, col)

        val contAjouter  = HBox(boutonAjouter).apply  { alignment = Pos.CENTER_RIGHT }
        val contSupprimer = HBox(boutonSupprimer).apply { alignment = Pos.CENTER_LEFT  }
        val contLancer   = HBox(boutonLancer).apply   { alignment = Pos.CENTER        }

        GridPane.setMargin(contAjouter,   javafx.geometry.Insets(0.0, 10.0, 0.0, 0.0))
        GridPane.setMargin(contSupprimer, javafx.geometry.Insets(0.0, 0.0, 0.0, 10.0))
        GridPane.setMargin(contLancer,    javafx.geometry.Insets(10.0, 0.0, 0.0, 0.0))

        GridPane.setConstraints(contAjouter,   0, 0, 2, 1)
        GridPane.setConstraints(contSupprimer, 2, 0, 2, 1)
        GridPane.setConstraints(contLancer,    1, 1, 2, 1)

        gridBoutons.children.addAll(contAjouter, contSupprimer, contLancer)
        this.bottom = gridBoutons
    }

    fun getScoreAAtteindre(): Int = sliderScore.value.toInt()

    private var dernierLabelSelectionne: Label? = null

    fun getJoueurSelectionneAGauche(): Joueur? {
        return if (dernierLabelSelectionne?.parent == listeGaucheView) {
            dernierLabelSelectionne?.userData as? Joueur
        } else null
    }

    fun getJoueurSelectionneADroite(): Joueur? {
        return if (dernierLabelSelectionne?.parent == listeDroiteView) {
            dernierLabelSelectionne?.userData as? Joueur
        } else null
    }

    fun update(disponibles: List<Joueur>, selecs: List<Joueur>) {
        listeGaucheView.children.clear()
        listeDroiteView.children.clear()
        dernierLabelSelectionne = null
        disponibles.forEach { listeGaucheView.children.add(creerLabelJoueur(it)) }
        selecs.forEach { listeDroiteView.children.add(creerLabelJoueur(it)) }
    }

    private fun creerLabelJoueur(joueur: Joueur): Label {
        val label = Label(joueur.nom)
        label.userData = joueur
        label.setOnMouseClicked {
            dernierLabelSelectionne?.style = ""
            dernierLabelSelectionne = label
            label.style = "-fx-background-color: #FCF8E3; -fx-text-fill: white;"
        }
        return label
    }
}