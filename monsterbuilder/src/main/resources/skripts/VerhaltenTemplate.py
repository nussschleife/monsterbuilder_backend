
class AkteurVerhalten(Akteur):
   
    state = States.ALIVE
    akteur = Akteur()
    gegner = []

    def __init__(self, akteur):
        self.akteur = akteur

    def reaktion(self, ereignis): #definieren was zurückgegeben werden muss
        pass
        
    def aktion(self, ereignis): #EreignisResult definieren als Map
        pass
    
    def angreifen(self, ereignis):
        pass

    def bewegen(self, ereignis):
        pass
    
    def _ausweichen(self, ereignis):
        pass

    def _sterben(self):
        state = States.DEAD


def initialise(akteur):
    return AkteurVerhalten(akteur)

#akteur pseudoklasse
#angriff etc.