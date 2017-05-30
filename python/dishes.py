import requests
import random
import string

dishes = [
{
	"name": "Tartare of beef tenderloin",
	"weight": "200",
	"type": "Cold Snacks",
	"cost": "14.00",
	"description": "Very delicate tartare cooked from finely chopped raw beef. Served with Worcester sauce.",
	"ingredients": "onions, pickled cucumbers, capers, parsley, egg yolk, mustard, tobasco, Worcester sauce",
	"bitmapurl": "http://lacipolla.ru/images/docs//Image/beeftartar-p-xxl.jpg"
},
{
	"name": "Salad with shrimp, avocado and smoked trout",
	"weight": "200",
	"type": "Cold Snacks",
	"cost": "16.00",
	"description": "Delicate smoked trout in conjunction with slices of grapefruit will not leave you indifferent.",
	"ingredients": "shrimp, avocado, trout, olive oil, salt, parsley, grapefruit",
	"bitmapurl": "http://cs620119.vk.me/v620119036/ba83/_XGUU8rTXEM.jpg"
},
{
	"name": "Greek salad",
	"weight": "220",
	"type": "Cold Snacks",
	"cost": "12.00",
	"description": "Standard Greek salad brought to you from natural ingredients. So fresh.",
	"ingredients": "tomatoes, cucumbers, onions, pepper, Feta cheese, olives, olive oil, lemon juice",
	"bitmapurl": "http://saridis.ru/wp-content/uploads/2012/12/grecheskij-salat.jpg"
},
{
	"name": "Warm salad with salmon and mushrooms",
	"weight": "160",
	"type": "Hot Appetizers",
	"cost": "17.00",
	"description": "Soft mushrooms and raw warmed salmon showered with orange juice. Delicious",
	"ingredients": "mushrooms, salmon, orange juice, carrot, Frize salad, garlic, salt, white pepper, fennel",
	"bitmapurl": "http://www.gastronom.ru/binfiles/images/00000013/00024134.jpg"
},{
	"name": "Eggplant rolls baked with mozzarella",
	"weight": "200",
	"type": "Hot Appetizers",
	"cost": "12.00",
	"description": "Hot mozzarella melted in eggplant rolls with soft texture and pleasant flavour",
	"ingredients": "eggplants, mozzarella, olive oil, oregano, garlic, tomatoes, salt, pepper, flour",
	"bitmapurl": "http://v.img.com.ua/b/orig/4/b7/18bd2a9c247414b00b5818337f254b74.jpg"
},{
	"name": "Mediterranean fish soup",
	"weight": "350",
	"type": "Soups",
	"cost": "23.00",
	"description": "Usual fish soup that it's cooked on mediterranean sea",
	"ingredients": "olive oil, tomatoes, white dry wine, tomato paste, carrot, fennel, celery, onion, garlic, fish fillet, langoustines",
	"bitmapurl": "http://povarixa.ru/images/photos/medium/article1793.jpg",
},{
	"name": "Velouté sauce of white mushrooms",
	"weight": "330",
	"type": "Soups",
	"cost": "15.00",
	"description": "Soup velouté (from the French velouté - velvety) is exactly the same how it's called. Delicate, creamy, velvety, this soup is obtained very fragrant! Lovers of mushroom soups will certainly appreciate it!",
	"ingredients": "mushrooms, potatoes, onions, carrot, white dry wine, cream, olive oil, butter, salt, pepper",
	"bitmapurl": "http://www.vkusnyblog.ru/wp-content/uploads/2014/09/sup-pure-iz-belyh-gribov.jpg"
},{
	"name": "Tournedos steak with vegetables, mushroom sauce and foie gras",
	"weight": "350",
	"type": "Main Course",
	"cost": "31.00",
	"description": "Pleasant medium rare tournedos steak exactly what a man needs. Soft, delicate foie gras ideally complements the dish",
	"ingredients": "fillet mignons, salt, black pepper, truffle juice, butter, vegetable oil, French bread, foie gras, Madeira",
	"bitmapurl": "https://static01.nyt.com/images/2012/02/29/dining/29TOURNEDOS_SPAN/29TOURNEDOS_SPAN-articleLarge-v2.jpg"
},{
	"name": "Steak 'New-York' with fragrant oil",
	"weight": "350",
	"type": "Main Course",
	"cost": "55.00",
	"description": "This fantastic stip steak is the perfect dinner for date night or any special occasion that desserves something extra delicious on the table. A little butter adds richness and keeps the leaner-than-usual beef moist without adding much in the way of total fat",
	"ingredients": "olive oil, lemon juice, garlic, Worcester sauce, black pepper, steaks, Tarragon sause",
	"bitmapurl": "http://www.e3meatco.com/wp-content/uploads/2015/05/21LN1114_01.jpg"
},{
	"name": "Duck leg confit terrine with foie gras and cranberry sauce",
	"weight": "320",
	"type": "Main Course",
	"cost": "30.00",
	"description": "Duck or goose confit (con-fee) is one of the most luxurious of foods in French cuisine. Gently cured duck legs bathed in their own fat and slowly cooked to falling-off-the-bone perfection. Then the skin is crisped in a pan or oven, giving you the sinful combination of silky meat and crackling skin. It’ll roll your eyes back it’s so good.",
	"ingredients": "duck, honey, salt, black pepper, rosemary, thyme, Worcester sauce, olive oil, cranberry, foie gras",
	"bitmapurl": "http://vkusnodoma.net/wp-content/uploads/2015/08/utka_konfi.jpg"
},{
	"name": "Assorted vegetables on the grill",
	"weight": "120",
	"type": "Garnishes",
	"cost": "9.00",
	"description": "The Chinese technique for cooking vegetables is fast, doesn’t waste nutrients, and preserves their flavor and color. Vegetables are cooked in a lot of water and then throwing the water away",
	"ingredients": "carrot, tomatoes, cucumbers, onions, pepper, cabbage, broccoli, parsley",
	"bitmapurl": "http://www.vegkitchen.com/wp-content/uploads/2011/05/Grilled-vegetable-assortment.jpg"
},{
	"name": "Potatoes with mushrooms",
	"weight": "120",
	"type": "Garnishes",
	"cost": "7.00",
	"description": "These potatoes with mushrooms are so awesome that they will make your head spin! It only takes a few basic ingredients to make this satisfying garnish. This recipe has been used for many years already",
	"ingredients": "potatoes, mushrooms, butter",
	"bitmapurl": "http://rasamalaysia.com/wp-content/uploads/2015/01/butter_sauteed_potato_mushroom3.jpg"
},{
	"name": "Fragrant basmati rice",
	"weight": "160",
	"type": "Garnishes",
	"cost": "5.00",
	"description": "Basmati is a variety of long, slender-grained aromatic rice which is traditionally from the Indian subcontinent.",
	"ingredients": "rice",
	"bitmapurl": "http://www.carmelizedcandies.com/lib/029/163-easy-lemon-basmati-rice-recipe.jpg"
}
]

urls = []
count = 10
rateUrl = "http://localhost:8080/app/api/add/rate?appid=%(appid)s&rate=%(rate)s&dishname=%(dishname)s"
addUrl = "http://localhost:8080/app/api/add/dish?name=%(name)s&weight=%(weight)s&type=%(type)s&cost=%(cost)s&currency=BYN&description=%(description)s&ingredients=%(ingredients)s&bitmapurl=%(bitmapurl)s"
for dish in dishes:
    urls.append(addUrl % {"name" : dish["name"],
                                     "weight" : dish["weight"],
                                     "type" : dish["type"],
                                     "cost" : dish["cost"],
                                     "description" : dish["description"],
                                     "ingredients" : dish["ingredients"],
                                     "bitmapurl" : dish["bitmapurl"]
                                       })

def genStr(length):
	return ''.join(random.choice(string.ascii_lowercase) for i in range(length))

for i in range(0, count):
    urls.append(rateUrl % {"appid" : genStr(8),
                                     "rate" : str(random.randint(1, 5)),
                                     "dishname" : random.choice(dishes)["name"]
                                       })

for url in urls:
	req = requests.get(url)
	print(req.text)
