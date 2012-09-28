declarative-jme-ui
==================

Develop and maintain Java ME UIs in declarative form, a.k.a. poor man's QML


Problem: With LCDUI, you quickly hit the point when you have to fall back to the low level 
Canvas to render the UI you want. For example, the stock LCDUI list component only supports single text description, checkbox icon and icon. If you want something more complex (.e.g. for twitter application), you need 
to use Canvas.

Canvas api is quite low level - you basically paint on a Graphics object. To make this more pleasant,
we are introducing a declarative way to describe what should reside on the canvas. This approach is 
familiar to developers coming from the world of QML. In a nutshell:

- You store the data no display in a "model" (hash table with the application data)
- You declare the user interface in YAML document
- You ask the engine to render the document to "scene"
- The scene is painted to a Grophics object, i.e. to the canvas or an offscreen bitmap

Here's an example document, "mydoc.yaml".

```
Item:
  id: tweetDelegate
  Rect:
    width: 200
    height:50
      Text:
        text: tweetbody
      Image
        src: tweetavatar
        
        
List:
  model: tweetlist
  delegate: tweetDelegate
  
  
Rect:
  id: mybuttonrect
  color: (if (get buttonMouseArea 'pressed) "#fff" "#111")
  
MouseArea:
  id: buttonMouseArea
  fill: mybuttonrect
  
```

Here's how it works (pseudocode)

```

eng = new DCEngine()

Hashtable model = eng.getModel()
model = new Hashtable()

List tweets = new List()
tweets.append( { tweetbody: "hello world", tweetavatar: "a.jpg"})
tweets.append( { tweetbody: "foo bar", tweetavatar: "a.jpg"})

model['tweetlist'] = tweets

DCDoc doc = eng.loadDCL("mydoc.yaml") 

DCScene scene = eng.renderAll(doc)

img = Image.createImage(scene.width(), scene.height())

scene.paintTo(img.graphics())

```

  


