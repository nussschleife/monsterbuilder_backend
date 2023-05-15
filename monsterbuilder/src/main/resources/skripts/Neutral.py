# -*- coding: utf-8 -*-
global alleCharaktere, alleMonster, aktuellesEreignis, state, akteur
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
class States:
   DEAD = 1
   WOUNDED = 3
   SCARED = 4
   ALIVE = 5

akteurstate = States.ALIVE

def angreifen(akteur, ereignis):
    global aktuellesEreignis
    gegner = alleGegner.values()[0]
    
    if len(akteur.getAlleAngriffe()) == 0:
        angriff = akteur.getAlleAktionen()[0]
    else:
        angriff = akteur.getAlleAngriffe()[0]

    if(angriff and gegner):
        gegner = akteur.aktionAusfuehren(angriff, gegner)
        ereignis.gegner = gegner.getName()
        aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
        if aktuellesEreignis.toedlich:
            del alleGegner[str(gegner.getName())]
    return gegner

def findeAktion(akteur, ereignis):
    if len(akteur.getAlleAktionen()) > 0:
        angreifen(akteur, ereignis)
    for con in akteur.getConditions():
        con.verringereDauer(1)


##passiert bei Skriptaufruf##
findeAktion(akteur, aktuellesEreignis)