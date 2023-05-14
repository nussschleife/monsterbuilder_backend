import sys
# -*- coding: utf-8 -*-
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4

akteurstate = States.ALIVE

def angreifen(akteur, ereignis):
    global aktuellesEreignis
    gegner = min(list(alleGegner.values()), key=lambda x: x.getLebenspunkte())
    angriff = findeBestenAngriffGegenCharakter(gegner, akteur) if isinstance(akteur, Monster) else findeBestenAngriffGegenMonster(gegner, akteur)

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

def findeBestenAngriffGegenMonster(gegner, charakter):

    if akteurstate is States.SCARED:
        return charakter.getAlleAngriffe().stream().max(Comparator.comparing(lambda a: a.berechneSchaden()))
    return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) is 0:
        return monster.getAlleZauber().toArray()[0]
    return monster.getAlleAngriffe().toArray()[0]

def main(monster):
    return monster.getName()


main(sys.argv[1])