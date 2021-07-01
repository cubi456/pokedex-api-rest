#------------------------------
#Reseteo de la base de datos
#------------------------------
DROP DATABASE pokedex;
DROP USER 'pokedex_user'@'%';
flush privileges;
