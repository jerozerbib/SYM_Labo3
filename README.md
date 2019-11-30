# SYM - Laboratoire 03

Authors : Lionel Burgbacher, David Jaquet, Jeremy Zerbib

## Introduction

 Ce travail est constitué de manipulations qui vont nous permettre de nous familiariser avec l’utilisation de données environnementales. Celui-ci est divisé en deux laboratoires : dans cette première partie nous nous intéresserons aux *codes-barres* et aux balises *NFC*, la seconde sera consacrée aux capteurs et à la communication *Bluetooth Low Energy*. 

## Questions

### NFC

#### Dans la manipulation ci-dessus, les tags NFC utilisés contiennent 4 valeurs textuelles codées en UTF-8 dans un format de message NDEF. Une personne malveillante ayant accès au porte-clés peut aisément copier les valeurs stockées dans celui-ci et les répliquer sur une autre puce NFC.

#### A partir de l’API Android concernant les tags NFC 4 , pouvez-vous imaginer une autre approche pour rendre plus compliqué le clonage des tags NFC ? Existe-il des limitations ? Voyez-vous d’autres possibilités ?



### Code-barre/QR-Code

### Quelle est la quantité maximale de données pouvant être stockée sur un QR-code ? Veuillez expérimenter, avec le générateur conseillé de codes-barres (QR), de générer différentes tailles de QR-codes. Pensez-vous qu’il est envisageable d’utiliser confortablement des QR-codes complexes (par exemple du contenant >500 caractères de texte ou une vCard très complète) ?

Il existe plusieurs versions de QR-Codes. A priori, selon le site [qrcode.com](https://www.qrcode.com/en/about/version.html), il y aurait 40 versions de QR-Codes différentes allant d'une taille de *21x21* à *177x177* modules. Un module est un carré blanc ou noir, tous de la même taille, ce qui engendre des tailles assez grandes dans le dernier cas. De ce fait, pour la plus grosse version, la taille maximale d'un QR-Code est de 23,648 bits, soit 7,089 caractères numériques, 4'296 caractères alphanumériques,  2'953 caractères binaires ou 1'817 kanji. Selon le site de générateurs de QR, il serait possible d'avoir une taille plus grande, *300x300*, mais nous n'avons pas trouvé d'informations à ce sujet sur d'autres sources d'informations. 

Depuis le générateur, il a été aisé de créer des QR de grandes tailles. Un texte de plus de 500 caractères n'a pas posé de problème. En revanche, pour une vCard très complète, nous pourrions rencontrer certains problèmes si l'utilisateur renseigne le champ `Notes` de manière trop détaillée. Cela pourrait poser des problèmes s'il dépasse le nombre de caractères maximum du QR.  L'analyse des champs de la vCard doit être faite du côté applicatif pour pouvoir lire les champs du QR.

### Il existe de très nombreux services sur Internet permettant de générer des QR-codes dynamiques. Veuillez expliquer ce que sont les QR-codes dynamiques. Quels sont les avantages et respectivement les inconvénients à utiliser ceux-ci en comparaison avec des QR-codes statiques. Vous adapterez votre réponse à une utilisation depuis une plateforme mobile.

Les QR-codes dynamiques sont  définis par le fait que nous pouvons modifier le contenu du QR-code à la volée. Le principe de base du QR-Code dynamique est que les données ne sont jamais stockées directement. Le mécanisme veut qu'une URL raccourcie  est passée dans les données du QR et cette dernière redirige vers ce que nous voulions lui passer. L'avantage de ce mécanisme est que nous n'avons pas besoin de créer un nouveau QR si l'on veut modifier les données à envoyer. 

Les avantages d'une telle méthode sont les suivants : 

- Possibilité de changer les données du QR sans soucis. Prenons l'exemple d'une personne qui charge une vCard sur un QR. Nous pouvons facilement imaginer que cette personne au bout d'un certain temps a certaines informations qui ont été modifiées. Grâce à l'utilisation de QR-Codes dynamique, ces informations sont directement chargées sans aucune modification du QR.
- Possibilité de surveiller les activités de scans. En effet, il est possible de savoir combien de personnes ont scanné le QR, leur localisation et certaines informations sur l'appareil qui a scanné. Cela permet de faire des statistiques qui peuvent être utiles lors de certaines campagnes marketing ou politiques. Cela permet de faire un positionnement sur le marché et de planifier comme il faut certains budget ou de faire des prévisions électorales.
- Possibilité de générer des QR petits et moins denses. Le problème vient du fait qu'un QR stocke des données sur les lignes et colonnes de ses modules. De ce fait, un QR statique peut être très dense si une grande quantité d'information est stockée donc peu aisé à scanner. ![https://blog-trycontechnologi.netdna-ssl.com/blog/wp-content/uploads/2019/08/Dynamic-QR-Code-2-1.png](./assets/Dynamic-QR-Code-2-1.png)
- Possibilité de désactiver et de réactiver sur demande un QR dynamique. Selon le générateur de QR utilisé, il y a un moyen de réactiver le QR désactivé.
- Possibilité de faire un QR optimisé pour mobile, suivant le générateur de QR utilisé. En effet, dans certains cas, il est très utile de faire des pages de redirection pour mobile. Par exemple, lorsqu'un développeur veut promouvoir une application pour `Android` et `iOS`, il voudrait que les utilisateurs scannent le même QR et redirigés vers la bonne page.  En effet, si l'utilisateur Android scanne le QR, il ne veut pas être redirigé vers l'Apple Store et vice-versa. Grâce au mécanisme établit précédemment, il est possible de savoir quel `OS` est utilisé et donc de faire une redirection conditionnelle.

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
- App Store selon l'OS comme expliqué avant 

Tous ces contenus sont modifiables et accessibles via certains widgets fournis sur les sites de génération de QR.

[Source](https://uqr.me/qr-code-generator/blog/dynamic-qr-codes-vs-static-qr-codes/)

Le seul désavantage trouvé à l'utilisation de QR dynamique est que nous sommes obligés d'avoir une connexion Internet pour pouvoir accéder au contenu du QR. Les QR statiques peuvent être utilisés de manière hors ligne.

[Source](https://uqr.me/qr-code-generator/blog/dynamic-qr-codes-vs-static-qr-codes/)

