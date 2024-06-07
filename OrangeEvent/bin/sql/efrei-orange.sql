drop database if exists orange_efrei;
create database orange_efrei;
use orange_efrei;

create table client (
    idclient int(3) not null auto_increment,
    nom varchar(50),
    prenom varchar(50),
    adresse varchar(50),
    email varchar(50),
    primary key (idclient)
);

create table produit(
    idproduit int(3) not null auto_increment,
    designation varchar(50),
    prixAchat varchar(50),
    dateAchat date,
    categorie enum ("Telephone", "Inscription", "Television"),
    idclient int(3) NOT null,
    primary key (idproduit),
    foreign key (idclient) references client(idclient)
);

create table technicien(
    idtechnicien int(3) not null auto_increment,
    nom varchar(50),
    prenom varchar(50),
    specialite varchar(50),
    dateEmbauche date,
    primary key (idtechnicien)
);

create table intervention(
    idinter int(3) not null auto_increment,
    description text,
    prixInter float,
    dateInter date,
    idproduit int(3) not null, 
    idtechnicien int(3) not null,
    primary key (idinter),
    foreign key (idproduit) references produit (idproduit),
    foreign key (idtechnicien) references technicien(idtechnicien)
);

create table user(
    iduser int(3) not null auto_increment,
    nom varchar(50),
    prenom varchar(50),
    email varchar(50),
    mdp varchar(50),
    role enum("admin", "user"),
    primary key (iduser)
);

insert into user values
    (null,"Paul","Ibrahima","a@gmail.com","123","admin"),
    (null,"julien","corentin","b@gmail.com","456","user");

    update user set mdp = sha1(mdp);