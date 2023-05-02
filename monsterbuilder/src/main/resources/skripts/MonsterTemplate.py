import random
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter

#man gibt ein Ereignis hier in das Skript und der Akteur agiert entsprechend 
#man hält instanzen der akteure mit dem alignment hier im skript
#man gibt Gegnerische akteure in die instanz
#was wird zurückgegeben? Ereignis/Akteur??

class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(object):
   
   state = States.ALIVE
   akteur = Akteur()
    
   def initiative(self):
      raise Exception('not implemented') 
    
   def reaktion(self):
      raise Exception('not implemented') 
       
   def aktion(self):
      raise Exception('not implemented') 
      
   def bewegen(self, laufweite):
      raise Exception('not implemented') 
    
class MonsterVerhalten(AkteurVerhalten):

    state = States.ALIVE
    monster = Monster()

    def __init__(self, monster):
        self.monster = monster

    def initiative():
       return int(random()*20)
    
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


def berechneAktion():
   return Aktion()
