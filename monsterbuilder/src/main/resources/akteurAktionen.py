import simplejson as json
with open('anObject.json','r') as f:
    obj = json.loads(f.read())
# print out obj['values'] obj['someBool'] ...
for key in obj:
    print(key, obj[key])

#aThing = DoStuffWithObject(obj)
#aThing.someOtherMethod('there')


class DoStuffWithObject(object):
    def __init__(self,obj):
        self.obj = obj
        self.changeObj()
        self.writeObj()

    def changeObj(self):
        self.obj['values'] = 100.134
        self.obj['length'] = 12
        self.obj['anotherVariable'] = 15
        self.obj['someString'] = "spam"

    def writeObj(self):
        ''' write back to file '''
        with open('anObject.json', 'w') as f:
            json.dump(self.obj, f)

    def someOtherMethod(self, s):
       ''' do something else '''
       print('hello {}'.format(s))
