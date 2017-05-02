import requests
import random
import string

class RequestBuilder :
    def __init__(self):
        self.__addDishUrl = "http://localhost:8080/app/api/add/dish?name=%(name)s&weight=%(weight)s&type=%(type)s&cost=%(cost)s&currency=BYN&description=%(description)s&ingredients=%(ingredients)s&bitmapurl=%(url)s"
        self.__addRateUrl = "http://localhost:8080/app/api/add/rate?appid=%(appid)s&rate=%(rate)s&dishname=%(dishname)s"

    def __mixIngredients(self, ingredients, count = 3):
        return [random.choice(ingredients) for i in range(0, count + 1)]

    def __getRandomString(self, count = 10):
        return ''.join([random.choice(string.ascii_lowercase) for i in range(0, count)])

    def buildAddDishUri(self, dishType, ingredients, descriptions, count = 10):
        result = []
        for i in range(0, count):
            result.append(self.__addDishUrl % {"name" : random.choice(dishType["names"]),
                                             "weight" : str(random.randint(1, 300)),
                                             "type" : dishType["type"],
                                             "cost" : str(random.randint(1, 10)) + '.' + str(random.randint(1, 99)),
                                             "description" : random.choice(descriptions),
                                             "ingredients" : ",".join(self.__mixIngredients(ingredients)),
                                             "url" : "http://" + self.__getRandomString() + "/dish.png"
                                               })
        return result

    def buildAddRateUri(self, users, dishnames, count = 10):
        result = []
        for i in range(0, count):
            result.append(self.__addRateUrl % {"appid" : random.choice(users),
                                             "rate" : str(random.randint(1, 5)),
                                             "dishname" : random.choice(dishnames)
                                               })
        return result

    def buildUsers(self, count = 10):
        result = []
        for i in range(0, count):
            result.append(self.__getRandomString())
        return result

ingredients = [
    "milk",
    "flavor",
    "fish",
    "lemon",
    "eggs",
    "bread",
    "meat",
    "sugar",
    "salt"
]

descriptions = [
    "Very tasty!",
    "Incredible dish",
    "Best goes with red wine",
    "Like that!",
    "Will come here for it more and more"
]

coldSnacks = {
    "type" : "Cold Snack",
    "names" : [
        "Tartaire of beef tenderloin",
        "Caesar salad",
        "Cheese plate"
    ]
}

main = {
    "type" : "Main",
    "names" : [
        "Chicken fillet with cheese",
        "Meat steak",
        "Fried potato",
        "Fish with lemon"
    ]
}

deserts = {
    "type" : "Desert",
    "names" : [
        "Pancakes with chocolate and pineapples",
        "Fried banana with sugar",
        "Strawberry ice cream with oranges"
    ]
}


builder = RequestBuilder()

coldSnacksUris = builder.buildAddDishUri(coldSnacks, ingredients, descriptions)
mainUris = builder.buildAddDishUri(main, ingredients, descriptions)
desertsUris = builder.buildAddDishUri(deserts, ingredients, descriptions)

users = builder.buildUsers()

coldSnacksRateUris = builder.buildAddRateUri(users, coldSnacks["names"])
mainRateUris = builder.buildAddRateUri(users, main["names"])
desertsRateUris = builder.buildAddRateUri(users, deserts["names"])

print('Users:\n' + '\n'.join(users) + '\n')

def getMethod(uris):
    for uri in uris:
        print("\nProceed " + uri)
        requests.get(uri)

getMethod(coldSnacksUris)
getMethod(mainUris)
getMethod(desertsUris)
getMethod(coldSnacksRateUris)
getMethod(mainRateUris)
getMethod(desertsRateUris)
