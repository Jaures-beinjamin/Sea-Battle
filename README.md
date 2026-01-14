# Sea Battle

## Jeu

Le jeu Sea Battle est une bataille navale simple qui se joue à plusieurs joueurs sur un même écran.

Le nombre de joueurs, la taille de la fenêtre et les bateaux (taille et nombre) peuvent être changés dans le fichier main.

### Déroulement

#### Placement des bateaux

Le joueur 1 commence et place ses bateaux sur sa grille.  

Pour placer un bateau, il faut cliquer et glisser dans la direction dans laquelle on veut le placer.  

Les bateaux placés seront en vert ; une fois tous les bateaux placés, cliquez une fois sur la grille pour cacher les bateaux et passer au joueur suivant.

<img width="500" alt="image" src="https://github.com/user-attachments/assets/e9cc195b-0512-4027-8ad9-df7600bde5ae" />

#### Bataille

Une fois que le dernier joueur a placé ses bateaux et a cliqué pour les cacher, la phase de bataille commence.

Le joueur 1 commence et tire sur un autre joueur en cliquant sur une case (il peut choisir n'importe quel joueur sauf lui-même).  

Une fois qu'il a tiré, il y a 3 possibilités selon la couleur de la case :
1. Bleu, il a tiré dans l'eau
2. Rose, il a touché un bateau
3. Rouge, il a touché et coulé un bateau

Le joueur suivant peut ensuite jouer.

<img width="500" height="1374" alt="image" src="https://github.com/user-attachments/assets/43147d54-134d-491c-8c00-3d22d356da29" />

#### Fin du jeu

Quand un joueur voit tous ses bateaux coulés, il est éliminé et son écran devient entièrement rouge. La partie continue avec les joueurs restants.  
La partie se termine quand il ne reste qu'un seul joueur en vie ; il devient alors le gagnant et son écran devient entièrement vert.

Pour recommencer une nouvelle partie, il suffit de cliquer sur un écran.
