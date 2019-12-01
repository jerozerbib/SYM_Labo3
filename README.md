# SYM - Laboratoire 03

Authors : Lionel Burgbacher, David Jaquet, Jeremy Zerbib

## Introduction

 Ce travail est constitué de manipulations qui vont nous permettre de nous familiariser avec l’utilisation de données environnementales. Celui-ci est divisé en deux laboratoires : dans cette première partie, nous nous intéresserons aux *codes-barres* et aux balises *NFC*, la seconde sera consacrée aux capteurs et à la communication *Bluetooth Low Energy*. 

## Questions

### NFC

#### Dans la manipulation ci-dessus, les tags NFC utilisés contiennent 4 valeurs textuelles codées en UTF-8 dans un format de message NDEF. Une personne malveillante ayant accès au porte-clés peut aisément copier les valeurs stockées dans celui-ci et les répliquer sur une autre puce NFC. A partir de l’API Android concernant les tags NFC, pouvez-vous imaginer une autre approche pour rendre plus compliqué le clonage des tags NFC ? Existe-il des limitations ? Voyez-vous d’autres possibilités ?

En regardant l'`API` Android sur les tags `NFC`, nous pouvons voir la méthode `getTechList` qui permet de récupérer toutes les technologies utilisées sur ce Tag.

Parmi ces technologies, nous pouvons voir que certains paramètres optionnels permettent de sécuriser les tags. Il est donc possible d'utiliser une technologie `MifareClassic` avec une surcouche `Mifare DESFire`, par exemple. Cette technologie apporte une couche de sécurité supplémentaire sur le Tag de par le fait que les communications sont chiffrées par un contrôle basé sur le partage de secrets. 

Le souci de cette approche est que si nous ne voulons pas avoir une connexion Internet active, il faut donc stocker la clé dans l'application. De par cette évidente erreur, l'attaquant peut facilement extraire la clé et donc l'utiliser pour cloner le Tag. 

Donc, dans un cas de scénario hors-ligne, il faut noter qu'utiliser ce genre de solutions est exclus. 

Il est possible d'utiliser un identifieur unique sur certains Tags `NFC` mis par le concepteur du Tag et qui ne peut pas être modifié. Il est donc possible de mettre en place une méthode de contrôle lors de la lecture du Tag pour vérifier si l'identifieur est fourni par nous ou non. En revanche, il est toujours possible d'extraire les données du Tag et de les cloner.

Il est aussi possible d'utiliser une clé asymétrique utilisée pour signer un challenge cryptographique avec cette dernière. En utilisant un challenge aléatoire, il est possible de vérifier si le Tag est le véritable Tag ou non. Il n'y a pas besoin de stocker de clé dans la mémoire. De ce fait, la vérification avec la clé publique suffit et il s'agit de la solution la plus sécurisée. 

[Source](https://stackoverflow.com/questions/22878634/how-to-prevent-nfc-tag-cloning?noredirect=1)

#### Est-ce qu’une solution basée sur la vérification de la présence d’un iBeacon sur l’utilisateur, par exemple sous la forme d’un porte-clés serait préférable ? Veuillez en discuter.

Une solution utilisant un `iBeacon` ne serait pas optimale car il n'y a pas de support natif pour *Android*. Cela rajoute des complications supplémentaires pour un développeur *Android*. 

De plus, les `iBeacon` n'implémentent pas de solutions contre le clonage de manière native. De manière globale, *Apple* ne laisse aucun de ses *iDevices* regarder un `iBeacon` qui n'a pas un `ProximityUUID` connu.  Sachant que toutes les autres technologies peuvent lire ces `ProximityUUID` inconnus, il semble que cette approche est futile en termes de sécurité. Il existe des outils sur *Android* permettant de déployer son propre `iBeacon`.  L'application [Beacon Simulator](https://play.google.com/store/apps/details?id=net.alea.beaconsimulator) en est un exemple. Cette dernière permet de simuler un `iBeacon`.

Des solutions permettant de patcher ces problèmes sont détaillées [ici](https://stackoverflow.com/questions/23383606/how-to-prevent-cloning-in-ibeacons-and-avoid-conflicts-among-beacons), mais elles ne sont que très peu recommandées. 

En conclusion, il est peu préférable d'utiliser un `iBeacon` pour vérifier la validité d'un Tag `NFC`. 

### Code-barre/QR-Code

#### Quelle est la quantité maximale de données pouvant être stockée sur un QR-code ? Veuillez expérimenter, avec le générateur conseillé de codes-barres (QR), de générer différentes tailles de QR-codes. Pensez-vous qu’il est envisageable d’utiliser confortablement des QR-codes complexes (par exemple du contenant >500 caractères de texte ou une vCard très complète) ?

Il existe plusieurs versions de QR-Codes. Les QR-Codes sont composés de modules. Ces modules sont des carrés qui peuvent être noir ou blanc et ont tous la même taille. Ces carrés mis ensemble génèrent un QR-Code d'une taille variable en fonction du nombre de modules.

En effectuant des tests avec le [site](http://generator.code-qr.net/#text) fourni dans la donnée du laboratoire, nous arrivons à générer des QR-Codes de taille comprise entre *85x85* et *547x547* modules. Cependant, selon le site [qrcode.com](https://www.qrcode.com/en/about/version.html), il serait possible de descendre le nombre de modules jusqu'à un minimum de *21x21*. Il y aurait 40 versions de QR-Codes différentes en fonction du nombre de modules. Toujours selon le site `qrcode.com`, un QR-Codes de 177x177 modules a une taille de 23'648 bits, soit 7'089 caractères numériques, 4'296 caractères alphanumériques,  2'953 caractères binaires ou 1'817 kanji. Il est important de noter que ces chiffres sont valables pour le plus bas niveau de `ECC`. Malheureusement, nous n'avons pas trouvée de chiffres pour la limite supérieure trouvé grâce au générateur de QR-Codes.

Depuis le générateur, il a été aisé de créer des QR de grandes tailles. Un texte de plus de 500 caractères n'a pas posé de problème. En revanche, pour une vCard très complète, nous pourrions rencontrer certains problèmes si l'utilisateur renseigne le champ `Notes` de manière trop détaillée. Cela pourrait poser des problèmes s'il dépasse le nombre de caractères maximum du QR.  L'analyse des champs de la vCard doit être faite du côté applicatif pour pouvoir lire les champs du QR.

#### Il existe de très nombreux services sur Internet permettant de générer des QR-codes dynamiques. Veuillez expliquer ce que sont les QR-codes dynamiques. Quels sont les avantages et respectivement les inconvénients à utiliser ceux-ci en comparaison avec des QR-codes statiques. Vous adapterez votre réponse à une utilisation depuis une plateforme mobile.

Les QR-codes dynamiques sont définis par le fait que nous pouvons modifier le contenu du QR-code à la volée. Le principe de base du QR-Code dynamique est que les données ne sont jamais stockées directement. Le mécanisme veut qu'une URL raccourcie soit passée dans les données du QR et cette dernière redirige vers ce que nous voulions lui passer. L'avantage de ce mécanisme est que nous n'avons pas besoin de créer un nouveau QR si on veut modifier les données à envoyer. 

Les avantages d'une telle méthode sont les suivants : 

- Possibilité de changer les données du QR sans soucis. Prenons l'exemple d'une personne qui charge une vCard sur un QR. Nous pouvons facilement imaginer que cette personne, au bout d'un certain temps, a certaines informations qui ont été modifiées. Grâce à l'utilisation de QR-Codes dynamique, ces informations sont directement chargées sans aucune modification du QR.
- Possibilité de surveiller les activités de scans. En effet, il est possible de savoir combien de personnes ont scanné le QR, leur localisation et certaines informations sur l'appareil qui a scanné. Cela permet de faire des statistiques qui peuvent être utiles lors de certaines campagnes marketing ou politiques. Cela permet de faire un positionnement sur le marché et de planifier comme il faut certains budgets ou de faire des prévisions électorales.
- Possibilité de générer des QR petits et moins denses. Le problème vient du fait qu'un QR stocke des données sur les lignes et colonnes de ses modules. De ce fait, un QR statique peut être très dense si une grande quantité d'information est stockée donc peu aisé à scanner. ![https://blog-trycontechnologi.netdna-ssl.com/blog/wp-content/uploads/2019/08/Dynamic-QR-Code-2-1.png](./assets/Dynamic-QR-Code-2-1.png)
- Possibilité de désactiver et de réactiver sur demande un QR dynamique. Selon le générateur de QR utilisé, il y a un moyen de réactiver le QR désactivé.
- Possibilité de faire un QR optimisé pour mobile, suivant le générateur de QR utilisé. En effet, dans certains cas, il est très utile de faire des pages de redirection pour mobile. Par exemple, lorsqu'un développeur veut promouvoir une application pour `Android` et `iOS`, il voudrait que les utilisateurs scannent le même QR et redirigés vers la bonne page.  En effet, si l'utilisateur Android scanne le QR, il ne veut pas être redirigé vers l'Apple Store et vice-versa. Grâce au mécanisme établi précédemment, il est possible de savoir quel `OS` est utilisé et donc de faire une redirection conditionnelle.

[Source](https://scanova.io/blog/blog/2014/03/06/what-is-a-dynamic-qr-code/)

De plus, il y a une possibilité de choisir parmi une multitude de contenu pour un QR dynamique. 

- URL
- Page mobile
- vCard
- Téléchargement de fichiers jusqu'à *5MB*
- Adresse sur Google Map
- Video Youtube
- Réseaux sociaux 
- Compte paypal
- App Store ou le Play Store en fonction de l'OS comme expliqué ci-dessus

Tous ces contenus sont modifiables et accessibles via certains widgets fournis sur les sites de génération de QR.

[Source](https://uqr.me/qr-code-generator/blog/dynamic-qr-codes-vs-static-qr-codes/)

Le seul désavantage trouvé à l'utilisation de QR dynamique est que nous sommes obligés d'avoir une connexion Internet pour pouvoir accéder au contenu du QR. Les QR statiques peuvent être utilisés de manière hors ligne.

[Source](https://uqr.me/qr-code-generator/blog/dynamic-qr-codes-vs-static-qr-codes/)

