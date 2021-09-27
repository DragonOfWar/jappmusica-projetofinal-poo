# JAppMusica - Projeto Final de POO

Esse é um aplicativo de música feito em java como projeto final da matéria de POO-0001 do curso de Bacharelado em Ciências da Computação da UDESC CCT.
O objetivo do trabalho é criar um aplicativo de música com interface gráfica e conexão ao banco de dados, feito em Java. O trabalho possui os seguintes requisitos:
- Permitir que o usuário faça upload de novas músicas, selecionando um ou mais artistas
que participam da música;
- Permitir que o usuário liste as músicas cadastradas juntamente com o artista que
elaborou a música.
- Permitir que o usuário liste os artistas cadastrados juntamente com as músicas que este artista participa.
- Permitir que o usuário liste as playlists cadastradas juntamente com as músicas que a compõe;
- Permitir que o usuário toque uma prévia da música em qualquer lugar que seja possível vê-la (seja na hora de listar as músicas, ou na hora de listar as músicas de um artista ou na hora de listar uma playlist);
- Também deve ser possível que o usuário exclua e adicione novas músicas a uma playlist, além de poder alterar ou remover músicas e artistas do aplicativo;
- O aplicativo deve manter dados para múltiplos usuários, isto é, cada usuário terá suas músicas e playlists, além de solicitar a autenticação (usuário e senha) do usuário ao se conecte ao aplicativo;

## Instruções de uso

Para executar o programa, é necessário ter um banco de dados Postgres instalado na máquina. Crie um banco de dados com nome 'app-musica' e execute o sql disponível no arquivo chamado 'sql.sql'. Após criado o banco de dados, o programa poderá ser usado.