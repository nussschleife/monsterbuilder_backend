# -*- coding: utf-8 -*-

### INFO ###
#
#Angriffe:
#akteur.getAlleAngriffe()
#
#Zauber:
#akteur.getAlleZauber()
#
#Aktionen:
#akteur.getAlleAktionen()
#
#Lebenspunkte:
#akteur.getAlleLebenspunkte()
###


global alleCharaktere, alleMonster, aktuellesEreignis, state, akteur    
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
#koennen beliebig erweitert werden
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4

def findeAktion(akteur, ereignis):
    ereignis.toedlich = True
    ereignis.gegner = akteur.getName()
        
findeAktion(akteur, aktuellesEreignis)