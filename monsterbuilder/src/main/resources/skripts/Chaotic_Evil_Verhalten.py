# -*- coding: utf-8 -*-
import random
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter

alleMonster = [] #am Kampf beteiligte Monster mit diesem Verhalten
alleCharaktere = []  #am Kampf beteiligte Charaktere mit diesem Verhalten
javaMonster = set([])
javaCharaktere = set([])

#was wird zurückgegeben? Ereignis/Akteur??

def initialisiere():
    for mon in javaMonster:
        alleMonster.append(PyMonster(mon))
    for char in javaCharaktere:
        alleCharaktere.append(PyCharakter(char))

def ende():
    return str(alleMonster.iterator().next().getLebenspunkte()) +' '+str(alleCharaktere.iterator().next().getLebenspunkte())

def holeAkteurMitId(id):
    return [akteur for akteur in alleAkteure if akteur.getId() is id ][0]


def schaden(akteurid, wert):
    #wir suchen akteur der dran ist (akteurid)
    akteur = holeAkteurMitId(akteurid)
    akteur.lebenspunkte -= wert
    return akteur.lebenspunkte <= 0
    #wozu braucht man den state? -> gucken wie es anderen charakteren geht
    #anhand der gegner aussuchen welche aktion man macht
    #gibt man den neuen akteur zurück oder die aktion? oder folgeereignisse?kjlk

def reaktion(akteurid, savingthrow):
    akteur = holeAkteurMitId(akteurid)
    akteur.reaktion(savingthrow)


#wenn ereignis reingereicht wird, betrifft das ja nur einen akteur.
#muss ereignis reingereicht werden oder ruft es nur methoden auf?


class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(object):
   
    state = States.ALIVE
    akteur = Akteur()
    gegner = []

    def __init__(self, akteur):
        self.akteur = akteur

    def initiative():
        return int(random()*20)

    def reaktion(self, save):
        return self._ausweichen()
        
    def aktion(self):
        i = int(random()*self.akteur.getAlleAngriffe().length)
        return self.akteur.getAlleAngriffe[i]


    def bewegen(self, laufweite):
        raise Exception('not implemented') 
    
    def _ausweichen(self, dc):
        return int(random()*25) >= dc


class MonsterVerhalten(AkteurVerhalten):

    state = States.ALIVE
    monster = Monster()

    def __init__(self, monster):
        self.monster = monster
        AkteurVerhalten.akteur = monster

    def initiative():
       return AkteurVerhalten.initiative()
    
    def reaktion(self):
       a = 2
       
    def aktion(self):
       if(self.state is States.WOUNDED):
          return self.monster.getAlleAktionen()[0]

    def bewegen(self):
       r = 0

class CharakterVerhalten(AkteurVerhalten):
    charakter = Charakter()
    state = States.ALIVE

    def __init__(self, charakter):
        self.charakter = charakter

class PyAkteur():
    lebenspunkte = 1
    ruestungsklasse = 1
    geschwindigkeit_ft = 1
    name = ""
    id = -1
    aktionen = []

    def __init__(self, akteur):
        self.lebenspunkte = akteur.getLebenspunkte()
        self.ruestungsklasse = akteur.getRuestungsklasse()
        self.name = akteur.getName()
        self.geschwindigkeit_ft = akteur.getGeschwingikeit_ft()
        self.id = akteur.getId()
        

class PyMonster(PyAkteur):
    elementvertraeglichkeiten = []

    def __init__(self, monster):
        super(PyMonster, self).__init__(self, monster)
        #self.elementverträglichkeiten = monster.getElementVerträglichkeiten()
        self.aktionen = monster.getAlleAngriffe()

class PyCharakter(PyAkteur):
    klasse = ""

    def __init__(self, charakter):
        super(PyCharakter, self).__init__(self, charakter)
        self.aktionen = charakter.getAlleAngriffe()
