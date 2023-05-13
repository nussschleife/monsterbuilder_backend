
INSERT INTO Sprache (name) VALUES ('Draconic'),('Elven'),('Common'),('Infernal'),('Goblin');
INSERT INTO Trait (name) VALUES ('Elemental'),('Plant'),('Fey'),('Fiend'),('Zombie');
INSERT INTO Schadensart (name) VALUES('Piercing'),('Bludgeoning'),('Good'),('Psychic'),('Evil'),('Fire'),('Cold');
INSERT INTO Ability_Score (name, score_Name, score) VALUES ('DEXTERITY','DEXTERITY', 1), ('CHARISMA','CHARISMA', 1), ('INTELLIGENCE','INTELLIGENCE',0), ('WISDOM','WISDOM', 0), ('CONSTITUTION', 'CONSTITUTION', 3), ('STRENGTH', 'STRENGTH', 4);
INSERT INTO Waffen_Angriff (name, ability_score_name, angriff_Modifikator, schaden_Modifikator, wuerfelanzahl, wuerfel, reichweite_ft, schadensart_ID) VALUES('standardAngriffLevel1', 'STRENGTH', 9, 4, 1, 'W8', 30, 1);
INSERT INTO Waffen_Angriff (name, ability_score_name, angriff_Modifikator, schaden_Modifikator, wuerfelanzahl, wuerfel, reichweite_ft, schadensart_ID) VALUES('standardAngriffLevel2', 'STRENGTH', 10, 4, 1, 'W8', 30, 1);
INSERT INTO Waffen_Angriff (name, ability_score_name, angriff_Modifikator, schaden_Modifikator, wuerfelanzahl, wuerfel, reichweite_ft, schadensart_ID) VALUES('standardAngriffLevel3', 'STRENGTH', 11, 4, 1, 'W8', 30, 1);
INSERT INTO CONDITION(disc, dauer, name) VALUES ('PRONE', 3, 'PRONE');
INSERT INTO CONDITION(disc, dauer, name) VALUES ('ENFEEBLED', 3, 'ENFEEBLED');
INSERT INTO CONDITION(disc, dauer) VALUES ('PETRIFIED', 3);
INSERT INTO Saving_Throw (schwierigkeit, typ) VALUES (3, 'WILL'), (8, 'FORTITUDE'), (7, 'REFLEX');
INSERT INTO Saving_Throw (schwierigkeit, typ) VALUES (4, 'WILL'), (10, 'FORTITUDE'), (8, 'REFLEX');
INSERT INTO Saving_Throw (schwierigkeit, typ) VALUES (7, 'WILL'), (9, 'FORTITUDE'), (6, 'REFLEX');
INSERT INTO ZAUBER(dtype, name, angriffzauber_angriff, level, reichweite_ft) VALUES ('ANGRIFF','rankenhieb', 1, 1, 30);
INSERT INTO ZAUBER (dtype, name, reichweite_ft, ability_score_name, condition_id, zauber_save, level) VALUES ('EFFEKT', 'kalter atem', 120, 'INTELLIGENCE', 1, 1, 1);
INSERT INTO Charakter(id, level, name, lebenspunkte, ruestungsklasse, geschwindigkeit_ft) VALUES (1,1, 'StandardCharakterLevel1', 21, 20, 30);
INSERT INTO Charakter(id, level, name, lebenspunkte, ruestungsklasse, geschwindigkeit_ft) VALUES (2,2, 'StandardCharakterLevel2', 34, 22, 30);
INSERT INTO Charakter(id, level, name, lebenspunkte, ruestungsklasse, geschwindigkeit_ft) VALUES (3,3, 'StandardCharakterLevel3', 47, 23, 30);

INSERT INTO akteur_score(akteur_id, score_id) VALUES (1,1), (1,2), (1,3), (1,4), (1,5), (1,6);
INSERT INTO akteur_score(akteur_id, score_id) VALUES (2,1), (2,2), (2,3), (2,4), (2,5), (2,6);
INSERT INTO akteur_score(akteur_id, score_id) VALUES (3,1), (3,2), (3,3), (3,4), (3,5), (3,6);
INSERT INTO akteur_save(akteur_id, save_id) VALUES (1,2), (1,9),(1,1);
INSERT INTO akteur_save(akteur_id, save_id) VALUES (1,8), (1,3),(1,4);
INSERT INTO akteur_save(akteur_id, save_id) VALUES (1,5), (1,6),(1,7);

--angriffe__
INSERT INTO akteur_ANGRIFF VALUES (1,1), (2,2), (3,3);