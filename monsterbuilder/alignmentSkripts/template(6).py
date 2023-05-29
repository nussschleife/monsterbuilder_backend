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
    global alleGegner
    global aktuellesEreignis
    ereignis = aktuellesEreignis
    if len(akteur.getAlleAktionen()) > 0:
    ##aktion aussuchen und verwenden##
        aktion = akteur.getAlleAktionen()[0]
        gegner = alleGegner.values()[0]
        ereignis.gegner = gegner.getName()
        gegner = akteur.aktionAusfuehren(aktion, gegner) 
        ##es wird geschaut ob der Gegner gestorben ist##
        ereignis.toedlich = gegner.lebenspunkte <= 0
        if ereignis.toedlich:
            del alleGegner[str(gegner.getName())]
    ##nach der Aktion werden potentielle conditions runtergezaehlt##
    for con in akteur.getConditions():
        con.verringereDauer(1)
  
##hier koennen States gesetzt werden##
akteurstate = States.ALIVE
##Aktion wird waehren der Simulation ausgefuehrt##
findeAktion(akteur, aktuellesEreignis)

def log():
    return alleGegner.values()