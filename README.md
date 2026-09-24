# flip7-etu

#  Application JavaFX

## Membres de l'équipe

| Prénom Nom |
|---|
| Khémara PARC |
| Franck WAFFAING KAMDEM |
| Quentin FORGERIT |
| Tunar ISA |
| Simon PHAM-FRANCHETEAU |

## Présentation

Ce projet réalise une interfance IHM (développée en **Kotlin** avec **JavaFX**) qui permet de jouer au **Flip 7**. 
2 à 4 joueurs de jouer peuvent jouer jusqu'à ce qu'un joueur atteigne le score fixé.


Le jeu s'appuie sur la bibliothèque fournie `iut.info1.flip7-1.7.jar`. L'application suit le modèle **Modèle-Vue-Contrôleur** vu en cours.

---

## Fonctionnalités

- **Fenetre d'accueil** : lancement de la partie, consultation des règles ou quitter l'application
- **Gestion des joueurs** : ajout, suppression et réorganisation des joueurs avant la partie (2 minimum, 4 maximum)
- **Table de jeu** : affichage de la main courante du joueur actif, scores, statuts (EN JEU / STOP / PERDU / FREEZE), compteur de pioche et défausse
- **Cartes spéciales** : boîte de dialogue dédiée à la sélection de la cible lors du tirage d'une carte spéciale (Freeze, 3 à la suite, Deuxième Chance)
- **Gestion des manches** : détection automatique de fin de manche (Flip 7, tous les joueurs stoppés ou éliminés) et passage à la manche suivante
- **Fin de manche** : boîte de dialogue modale pour indiquer la fin de cette dernière et lancer la manche suivante
- **Doublon** : boîte de dialogue informant qu'un joueur a pioché un doublon et perd la manche
- **Fin de partie** : écran de classement final avec scores, bouton de retour au menu principal ou relance d'une nouvelle partie.
- **Règles du jeu** : accessibles depuis l'accueil et depuis la table de jeu via une boîte de dialogue modale

---

## Architecture

L'application respecte la structure **Modèle-Vue-Contrôleur (MVC)** :

### Modèle (`fr.iut.flip7.modele`)

Contient la logique métier et l'état de l'application :

| Classe | Rôle |
|---|---|
| `GestionJoueur` | Gère les listes de joueurs disponibles (`joueursAjoutes`) et sélectionnés (`joueursSelectionnes`) ; valide les préconditions de lancement |
| `Joueur` | Données d'un joueur : nom et score |
| `Carte` | Représente une carte du paquet : type (`TypeCarte`), valeur, texte et chemin image |
| `TypeCarte` | Énumération des types de cartes : `NUMERO`, `BONUS`, `FOIS_DEUX`, `FREEZE`, `TROIS_SUITE`, `DEUXIEME_CHANCE` |
| `Paquet` | Construit et mélange le paquet complet (79 cartes numéro, 6 bonus, 9 cartes spéciales) ; expose `piocher()` et `tailleRestante()` |
| `FinManche` | Contient le message de fin de manche transmis à la boîte de dialogue |
| `Regles` | Fournit le texte complet des règles via `getRegles()` |

### Vue (`fr.iut.flip7.vue` / `vue`)

Gère uniquement l'affichage. Chaque vue est un composant JavaFX autonome :

| Classe | Description |
|---|---|
| `FenetreAccueil` | Menu principal (logo, boutons Lancer / Règles / Quitter) ; héberge la pop-up modale des règles |
| `FenetreJoueur` | Configuration des joueurs avant la partie (deux listes, boutons de déplacement et d'action) |
| `FenetreJeu` | Table de jeu principale : panneau du joueur actif à gauche, tableau des adversaires à droite ; héberge les quatre pop-ups de jeu |
| `FenetreFinJeu` | Écran de classement final (grille positionnée avec `GridPane`) |
| `CarteGraphique` | Composant graphique dédié à l'affichage d'une carte (à compléter) |

Les boîtes de dialogue modales sont portées directement par les vues qui en ont besoin :

| Pop-up | Vue porteuse | Méthode |
|---|---|---|
| Règles du Jeu | `FenetreAccueil` et `FenetreJeu` | `afficherPopUpRegles()` |
| Fin de manche | `FenetreJeu` | `afficherDialogueFinManche()` |
| Doublon (manche perdue) | `FenetreJeu` | `afficherDialogueDoublon()` |
| Sélection de cible (carte spéciale) | `FenetreJeu` | `afficherDialogueCiblage()` |

### Contrôleur (`fr.iut.flip7.controleur`)

Gère les actions de l'utilisateur et fait le lien entre la vue et le modèle :

| Classe | Responsabilité |
|---|---|
| `ControleurAjouterJoueur` | Valide la saisie (nom non vide, non dupliqué) et ajoute un joueur via `GestionJoueur` |
| `ControleurSupprimerJoueur` | Demande confirmation puis supprime le joueur sélectionné |
| `ControleurDeplacerDroite` | Déplace un joueur de la liste « Joueurs ajoutés » vers « Pour cette partie » |
| `ControleurDeplacerGauche` | Retire un joueur de la liste « Pour cette partie » vers « Joueurs ajoutés » |
| `ControleurLancerPartie` | Vérifie que `peutLancerPartie()` (2 à 4 joueurs sélectionnés) est satisfait, puis ouvre `FenetreJeu` |
| `ControleurJeu` | Orchestre le déroulement d'une manche : pioche, application des cartes, changement de tour, détection de fin de manche |
| `ControleurFinManche` | Gère la transition entre manches et déclenche la fin de partie le cas échéant |

### Utilitaires (`fr.iut.flip7.utilitaires`)

| Fichier | Rôle |
|---|---|
| `Regles.kt` | Fournit `getRegles()` retournant le texte complet des règles, injecté dans `FenetreAccueil` et `FenetreJeu` |
| `StylesCss.kt` | Centralise les chaînes de style JavaFX réutilisées dans les vues |
| `Alertes.kt` | Factorise les boîtes de dialogue de confirmation (ajout de joueur, suppression, sélection de cible) |

### Organisation des packages

```
src/
└── main/
    └── kotlin/
        ├── Main.kt
        ├── controleur/
        │   ├── ControleurAjouterJoueur.kt
        │   ├── ControleurSupprimerJoueur.kt
        │   ├── ControleurDeplacerDroite.kt
        │   ├── ControleurDeplacerGauche.kt
        │   ├── ControleurLancerPartie.kt
        │   ├── ControleurJeu.kt
        │   └── ControleurFinManche.kt
        ├── modele/
        │   ├── Carte.kt
        │   ├── TypeCarte.kt
        │   ├── FinManche.kt
        │   ├── GestionJoueur.kt
        │   ├── Joueur.kt
        │   ├── Paquet.kt
        │   └── Regles.kt
        ├── vue/
        │   ├── FenetreAccueil.kt
        │   ├── FenetreJoueur.kt
        │   ├── FenetreJeu.kt
        │   ├── FenetreFinJeu.kt
        │   └── CarteGraphique.kt
        └── utilitaires/
            ├── Regles.kt
            ├── StylesCss.kt
            └── Alertes.kt

libs/
└── iut.info1.flip7-1.7.jar

images/
├── LOGO_FLIP7.png
└── images_carte/
    ├── 0.png … 12.png
    ├── +2.png … +10.png
    ├── x2.png
    ├── freeze.png
    ├── 3alasuite.png
    └── 2emechance.png
```

---

## Interactions utilisateur

L'application fournit des retours explicites pour chaque action

### Boîtes de dialogue

| Déclencheur | Type | Contenu | Choix |
|---|---|---|---|
| Clic sur « Règles du Jeu » | Information (modale) | Texte complet des règles (`Regles.getRegles()`) | OK |
| Clic sur « Ajouter joueur » | Saisie + confirmation | Champ de nom, validation (non vide, non dupliqué) | Oui / Annuler |
| Clic sur « Supprimer joueur » | Confirmation | « Voulez-vous vraiment supprimer ce joueur ? » | Oui / Non |
| Clic sur « Quitter » (en cours de partie) | Confirmation | « Voulez-vous vraiment quitter la partie ? » | Oui / Non |
| Tirage d'une carte spéciale (Freeze / 3 à la suite) | Sélection (modale) | Affichage de la carte, liste des joueurs ciblables | Joueur 1…4 |
| Confirmation de cible | Confirmation | « Êtes-vous sûr de viser [Joueur X] ? » | Oui / Non |
| Doublon pioché | Information (modale) | « [Joueur] a pioché un doublon. Il perd cette manche ! » | OK |
| Fin de manche (Flip 7, tous stoppés…) | Information (modale) | Message de `FinManche` (ex. « La manche est terminée ») | Manche suivante |

### Vérifications et messages d'erreur

- Le bouton **Lancer la partie** déclenche un avertissement si le nombre de joueurs sélectionnés est inférieur à 2 ou supérieur à 4 (vérifié par `GestionJoueur.peutLancerPartie()`).
- La saisie du nom de joueur est validée : un nom vide (ou composé uniquement d'espaces) ou déjà présent dans `joueursAjoutes` ou `joueursSelectionnes` est refusé avec un message explicite.
- Les joueurs inéligibles comme cible (par exemple déjà éliminés) sont grisés dans la boîte de dialogue de ciblage (`afficherDialogueCiblage`).

### Retours visuels

- Le statut de chaque joueur (EN JEU / STOP / PERDU / FREEZE) est affiché en couleur dans le panneau droit de `FenetreJeu` : rouge pour un joueur éliminé par doublon, bleu pour un joueur gelé (FREEZE), orange pour un joueur stoppé.
- La barre de statut en haut de `FenetreJeu` indique l'état courant de la partie (ex. « Attente choix joueur », « Attente Utilisation Carte Spéciale »).
- Les cartes du joueur actif sont affichées avec leurs images réelles ; en cas d'image manquante, un rectangle de remplacement avec le texte de la carte est affiché sans plantage (gestion d'exception dans `vueCarte()`).

---

## Execution et compilation

| Logiciel | Version | Rôle |
|---|---|---|
| Kotlin | 2.3.0 | Langage principal |
| JavaFX | 17 | Interface graphique |
| Gradle (Wrapper) | — | Build et gestion des dépendances |
| `iut.info1.flip7-1.7.jar` | 1.7 | Mécanique du jeu (bibliothèque fournie) |
| JUnit Jupiter | 5 | Tests unitaires |
| GitLab | — | Dépôt |

---

## Compilation

Le projet utilise **Gradle** : il n'est pas nécessaire d'installer Gradle. Un **JDK 11** (ou plus) doit être disponible sur la machine.

> **Note réseau (IUT de Nantes) :** le `gradle.properties` configure automatiquement le proxy `srv-proxy-etu-2.iut-nantes.univ-nantes.prive:3128`. En dehors du réseau de l'IUT, supprimez ou commentez les lignes `systemProp.http.proxy*` et `systemProp.https.proxy*` dans `gradle.properties`.

**Linux / macOS**

dans le terminal : 
```
./gradlew build
```


### avec Gradle 

**Linux / macOS**

dans le terminal :
```
./gradlew run
```

### avec IntelliJ IDEA

1. Ouvrir le projet (`File > Open` → sélectionner le dossier racine).
2. Attendre la synchronisation Gradle.
3. Ouvrir `src/main/kotlin/Main.kt`.
4. Cliquer sur le bouton 'Run' dans Main.kt

> **Environnement Silverblue (IUT) :** avant de lancer l'application, ouvrir un terminal et exécuter `xhost +`, puis configurer la variable d'environnement `DISPLAY=:0` dans `Run/Debug Configurations > Environment variables` (si jamais cela ne fonctionne pas, forcer la variable d'environnement avec `GDK_BACKEND=x11` et ajouter aux options de machines virtuelles, `-Dglass.gtk.uiScale=1.0 -Dprism.order=sw`)

## Structure du projet

```

├── build.gradle.kts          # Configuration Gradle (Kotlin, JavaFX, dépendances)
├── settings.gradle.kts       
├── gradle.properties         # Proxy réseau IUT
├── libs/
│   └── iut.info1.flip7-1.7.jar   # Bibliothèque mécanique du jeu (fournie)
├── images/
│   ├── LOGO_FLIP7.png
│   └── images_carte/             # Images des cartes (0.png … freeze.png …)
└── src/
    ├── main/
    │   └── kotlin/
    │       ├── Main.kt
    │       ├── controleur/
    │       ├── modele/
    │       ├── vue/
    │       └── utilitaires/
    └── test/
        └── kotlin/
```

---

## Dépendances entre vues

La navigation entre les fenêtres est gérée par le `Stage` (`stagePrincipal`) :

```
FenetreAccueil
  ├── [boîte de dialogue modale] Règles du Jeu
  └── → FenetreJoueur (gestion des joueurs)
        └── → FenetreJeu (table de jeu)
              ├── [boîte de dialogue modale] Règles du Jeu
              ├── [boîte de dialogue modale] Doublon (manche perdue)
              ├── [boîte de dialogue modale] Fin de Manche
              ├── [boîte de dialogue modale] Sélection Carte Spéciale
              └── → FenetreFinJeu (classement final)
                    ├── → FenetreAccueil (retour menu)
                    └── → FenetreJoueur (relancer une partie)
```

- `FenetreAccueil` est la base de la navigation ; les règles du jeu et la gestion des joueurs en dépendent directement.
- `FenetreJeu` ne peut être atteinte qu'après la configuration des joueurs dans `FenetreJoueur`.
- Les boîtes de dialogue modales de jeu sont portées par `FenetreJeu` elle-même (méthodes internes), ce qui évite tout couplage supplémentaire entre classes.

---

## Choix de conception

### MVC

Le MVC a été retenu pour isoler strictement les responsabilités : le modèle (`GestionJoueur`, `Paquet`, `Joueur`…) ; les vues ne contiennent pas de logique métier ; les contrôleurs relaient juste les informations. Cette séparation a facilité la répartition du travail au sein du groupe (architecture modulaire).

### Utilisation du binding 



Les propriétés de taille des boutons et images dans `FenetreAccueil` et `FenetreJoueur` sont liées aux dimensions de la fenêtre via `prefWidthProperty().bind(...)` et `prefHeightProperty().bind(...)`, ce qui rend l'interface redimensionnable.
Ainsi que pour fixer le score à atteindre en début de partie dans `FenetreJoueur`.

### Boîtes de dialogue modales pour les règles et la fin de manche

Les règles du jeu et la fin de manche sont implémentées en tant que **boîtes de dialogue modales** (et non comme des scènes à part entière) pour deux raisons :

1. **Contexte** : ces informations s'affichent en premier plan de la scène en cours (accueil ou table de jeu) en conservant la fenetre.
2. **Blocage des fenêtres en arrière-plan** : une boîte de dialogue modale (`Modality.APPLICATION_MODAL`) bloque toute interaction avec la fenêtre parente tant qu'elle n'est pas fermée, garantissant que le joueur prend connaissance de la fin de manche avant que la suivante ne commence ainsi que la lecture des règles du jeu.

---

## Utilisation de l'IA 

Concernant l'utilisation de l'intelligence artificielle, nous avons utilisé :
- ChatGPT pour corriger les fautes d'orthographe et faire les tableaux ainsi qu'une partie (en-têtes et titre) de la mise en page du markdown (passage de docx au format .md), ainsi que pour comprendre les messages d'erreurs lors des bugs,problèmes de build et de compilation
- Claude/Gemini pour corriger les dimensions de certains conteners dans nos graphe de scène afin que cela soit compatible avec notre dimension de scène choisie, partie reflexion (boîte noire/table de décision) vérifier la robustesse et la couverture de nos cas de tests (n'ont pas pu être implémenter à temps), écriture d'une partie du css de 2 de nos vues (par manque de temps), réécriture  et explication de la logique de certaines vues qui entrainaient des bugs incompréhensibles du fait d'un oubli d'utilisation de la bibliothèque au départ. 

