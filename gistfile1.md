# YAML as QML lite

LWUIT is a more modern way to compose UI's for J2ME devices. It follows the "swing" / QWidget programming style (imperatively constructing the UI tree), which is easy to learn but tedious to write and especially to read / iterate on.

It looks like this

```java
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        ComponentGroup buttonGroup = new ComponentGroup();
        Label buttonLabel = new Label("Buttons");
        buttonLabel.setUIID("TitleLabel");
        f.addComponent(buttonLabel);
        final Button left = new Button("Left Alignment");
        final Button right = new Button("Right Alignment");
        final Button center = new Button("Center Alignment");
        buttonGroup.addComponent(left);
        buttonGroup.addComponent(right);
        buttonGroup.addComponent(center);
        f.addComponent(buttonGroup);
        
        left.getUnselectedStyle().setAlignment(Label.LEFT);
        left.getSelectedStyle().setAlignment(Label.LEFT);
        left.getPressedStyle().setAlignment(Label.LEFT);
        right.getUnselectedStyle().setAlignment(Label.RIGHT);
        right.getSelectedStyle().setAlignment(Label.RIGHT);
        right.getPressedStyle().setAlignment(Label.RIGHT);
        center.getUnselectedStyle().setAlignment(Label.CENTER);
        center.getSelectedStyle().setAlignment(Label.CENTER);
        center.getPressedStyle().setAlignment(Label.CENTER);
```

QML is a nice notation for describing UI's, it looks like this (intentionally omitting onClick handlers and id's):

```qml
Row {
  Button {
    text: "btn1"
  }
  Button {
    text: "btn2"
  }

}
```

We could approximate this with YAML like this:

```yaml
Row:
  Button: 
    text: "btn1"
  Button:
    text: "btn2"
```

The problem is that Row here is a YAML "mapping", i.e. it will have one item with key "Button", instead of it being a list of mappings with key Button.

What needs to be done is writing a new YAML parser that handles  mappings as lists of (key, value) pairs. Trying to port over the low level API from SnakeYAML could be a good starting point.

What makes Button different from vanilla attibute? Following QML convention, the distinction is made by capitalizing the first letter. "Button" means "instantiate Button object here, using these child properties to configure it" while "button": 12 would just mean value of "button" property is 12. YAML parser can be entirely oblivious about this convention, it is enforced in the code using the parsed structure.

Note that YAML, unlike QML, would contain no imperative code (javascript). All the event handlers and behavior would still be attached to the components on Java side.