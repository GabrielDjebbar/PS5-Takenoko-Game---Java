# Le projet Okonekat

Le projet Okonekat est une virtualisation du jeu de société Takenoko, avec deux bots qui jouent un certain nombre de parties, et on génère les statistiques pour voir les pourcentages

## Fonctionnalités

#Moteur du jeu
- L’ensemble des concepts (Caractères, bambous, irrigation, aménagements, météo..)
- Un ensemble d'objectifs : parcelle, panda, jardinier (couleurs et orientations)
- Une météo qui donne le nombre de tours, ainsi que un coup/meteo
- Un arbitre: donne la liste des coups possibles et valide les objectifs
- Partie graphique (non demandée) affichant parcelles, caractères, et irrigation
- Gestion du flux d'informations: logs console, statistiques.

#Bots pseudo-intelligents

Tous les bot jouent selon la météo.
- Random bot: joue aléatoirement tout le jeu (parcelles, irrigations, aménagements..)
- Smart bot 1 : joue intelligemment les objectifs panda.
- Smart bot 2 : joue intelligemment les objectifs Parcelle.



## Deploiment

	$ mvn clean install
	$ mvn exec:java


## Construit avec

* [Maven](https://maven.apache.org/) - Dependency Management


## Versionnement

Nous utilisons [Git](https://git-scm.com/) comme gestionnaire de versions. Pour les versions disponibles, regardez les  [tags dans ce dépot de bitbucket](https://mjollnir.unice.fr/bitbucket/projects/OKT/).


## Auteurs

* **Walid LARABI** - Polytech'Nice (2017-2020)
* **Natan BEKELE** - Polytech'Nice (2017-2020)
* **Yon KOOIJMAN** - Polytech'Nice (2017-2020)
* **Gabriel DJEBBAR** - Polytech'Nice (2017-2020)
