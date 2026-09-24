package fr.iut.flip7.util

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

object AlertManager {

    fun afficherAvertissement(titre: String, message: String) {
        val alerte = Alert(Alert.AlertType.WARNING)
        alerte.title = titre
        alerte.headerText = null
        alerte.contentText = message
        alerte.showAndWait()
    }

    fun confirmerSuppression(nomJoueur: String): Boolean {
        val confirmation = Alert(Alert.AlertType.CONFIRMATION)
        confirmation.title = "Confirmation de suppression"
        confirmation.headerText = "Supprimer le joueur"
        confirmation.contentText = "Voulez-vous vraiment supprimer \"$nomJoueur\" ?"
        val result = confirmation.showAndWait()
        return result.isPresent && result.get() == ButtonType.OK
    }

    fun afficherInformation(titre: String, message: String) {
        val info = Alert(Alert.AlertType.INFORMATION)
        info.title = titre
        info.headerText = null
        info.contentText = message
        info.showAndWait()
    }
}