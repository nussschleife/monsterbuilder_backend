# -*- coding: utf-8 -*-
global alleCharaktere, alleMonster, aktuellesEreignis, state, akteur, alleAkteure
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
class States:
   DEAD = 1
   WOUNDED = 3
   SCARED = 4
   ALIVE = 5

akteurstate = States.ALIVE

def angreifen(akteur, ereignis):
    global aktuellesEreignis
    gegner = min(list(alleGegner.values()), key=lambda x: x.getLebenspunkte())
    angriff = findeBestenAngriffGegenCharakter(gegner, akteur) if isinstance(akteur, Monster) else findeBestenAngriffGegenMonster(gegner, akteur)
    if(angriff and gegner):
        gegner = akteur.aktionAusfuehren(angriff, gegner)
        aktuellesEreignis.gegner = gegner.getName()
        aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
        if aktuellesEreignis.toedlich:
            del alleGegner[str(gegner.getName())]
        else:
            alleGegner[str(gegner.getName())] = gegner
    return gegner

def findeAktion(akteurKopie, ereignis):
    gegner = alleGegner.values()[0]
    if akteurKopie.getAlleAktionen() is not None and len(akteurKopie.getAlleAktionen()) > 0 and akteurKopie.getAlleAngriffe() is not None and len(akteurKopie.getAlleAngriffe()) > 0:
        gegner = angreifen(akteurKopie, ereignis)
    for con in akteurKopie.getConditions():
        con.verringereDauer(1)
    return gegner

def findeBestenAngriffGegenMonster(gegner, charakter):
    if len(charakter.getAlleAngriffe()) > 0:
        #if akteurstate is States.SCARED:
        #   return max(list(charakter.getAlleAngriffe()), lambda a: a.berechneSchaden())
        return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) == 0 and len(monster.getAlleZauber()) > 0:
        return monster.getAlleZauber().toArray()[0]
    if len(monster.getAlleAngriffe()) > 0:
        return monster.getAlleAngriffe().toArray()[0]

def findeAkteur():
    for key in alleAkteure.keys():
        if key == str(akteur.getName()):
            return alleAkteure[key]

##passiert bei Skriptaufruf##

akteurKopie = findeAkteur()
if akteurKopie.getLebenspunkte() < 10:
    akteurstate = States.SCARED
gegner = findeAktion(akteurKopie, aktuellesEreignis)