create sequence id_usuario;
create table usuarios(
	id int,
	nome varchar(64),
	senha text,
	primary key (id)
);

create sequence id_artista;
create table artistas(
	id int,
	nome varchar(64),
	data_nascimento date,
	biografia text,
	id_dono int,
	primary key(id),
	foreign key(id_dono) references usuarios
);

create sequence id_musica;
create table musicas(
	id int,
	nome varchar(64),
	letra text,
	album varchar(64),
	id_dono int,
	data_lancamento date,
	arquivo bytea,
	primary key(id),
	foreign key (id_dono) references usuarios
);

create table participacoes (
	id_musica int,
	id_artista int,
	primary key(id_musica, id_artista),
	foreign key (id_musica) references musicas,
	foreign key (id_artista) references artistas
);

create table favoritos (
	id_musica int,
	id_usuario int,
	primary key (id_musica, id_usuario),
	foreign key (id_musica) references musicas,
	foreign key (id_usuario) references usuarios
);

create sequence id_playlist;
create table playlists(
	id int,
	nome varchar(32),
	id_dono int,
	primary key(id),
	foreign key(id_dono) references usuarios
);

create table playlist_musica(
	id_musica int,
	id_playlist int,
	primary key(id_musica, id_playlist),
	foreign key (id_musica) references musicas,
	foreign key (id_playlist) references playlists
);