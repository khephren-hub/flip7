package fr.iut.flip7.controleur

import fr.iut.flip7.modele.GestionJoueur
import fr.iut.flip7.vue.FenetreJoueur
import fr.iut.flip7.util.AlertManager
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

class ControleurAjouterJoueur(
    private val vue: FenetreJoueur,
    private val modele: GestionJoueur,
    private val stagePrincipal: Stage
) {
    fun ajouterJoueur() {
        val dialog = Stage()
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.initOwner(stagePrincipal)
        dialog.title = "Ajouter un joueur"

        val field = TextField().apply { promptText = "Nom du joueur" }
        val btnOk = Button("Ajouter").apply {
            style = "-fx-background-color: #2e8b57; -fx-text-fill: white; -fx-font-weight: bold;"
        }
        val btnAnnuler = Button("Annuler").apply {
            style = "-fx-background-color: #cd5c5c; -fx-text-fill: white; -fx-font-weight: bold;"
        }

        val boutons = HBox(10.0, btnOk, btnAnnuler).apply { alignment = Pos.CENTER }
        val layout = VBox(15.0, Label("Entrez le nom du joueur :"), field, boutons).apply {
            padding = Insets(20.0)
            alignment = Pos.CENTER
            style = "-fx-background-color: #FCF8E3;"
        }

        dialog.scene = Scene(layout, 280.0, 150.0)

        field.setOnAction { validerAjout(field.text, dialog) }
        btnOk.setOnAction { validerAjout(field.text, dialog) }
        btnAnnuler.setOnAction { dialog.close() }

        dialog.showAndWait()
    }

    private fun validerAjout(nom: String, dialog: Stage) {
        when {
            nom.trim().isBlank() -> {
                AlertManager.afficherAvertissement("Nom invalide", "Le nom ne peut pas être vide.")
            }
            !modele.ajouterJoueur(nom) -> {
                AlertManager.afficherAvertissement("Joueur existant", "Ce joueur existe déjà.")
            }
            else -> {
                vue.update(modele.joueursAjoutes, modele.joueursSelectionnes)
                dialog.close()
            }
        }
    }
}