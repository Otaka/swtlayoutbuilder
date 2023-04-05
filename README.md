

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.otaka/swtlayoutbuilder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.otaka/swtlayoutbuilder)
# SWTLayoutBuilder
Java SWT layout builder that helps to create complex forms.

Quick example:
```java
new SwtLayoutBuilder(shell).borderLayout().gapBetweenComponents(5,5)
        .addToCenter(createColorBlock(shell, "A",color(SWT.COLOR_GREEN)))
        .addToLeft(createColorBlock(shell, "B",color(SWT.COLOR_RED)))
        .addToRight(createColorBlock(shell, "B",color(SWT.COLOR_YELLOW)))
        .addToTop(createColorBlock(shell, "B",color(SWT.COLOR_MAGENTA)))
        .addToBottom(createColorBlock(shell, "B",color(SWT.COLOR_BLUE)))
        .finish();
```

The builder supports several layouts to simplify some popular cases:
* Components placement with wrapping
* Place component at left/right/top/left/center of parent component
* RuleLayout that sequentially executes list of rules how to place components relative to each other.

## Maven
```xml
<dependency>
    <groupId>io.github.otaka</groupId>
    <artifactId>swtlayoutbuilder</artifactId>
    <version>0.1</version>
</dependency>
```



## SwtLayoutBuilder.borderLayout
Very similar to swing BorderLayout wrapped in builder syntax
```java
new SwtLayoutBuilder(shell).borderLayout().gapBetweenComponents(5,5)
        .addToCenter(createColorBlock(shell, "A",color(SWT.COLOR_GREEN)))
        .addToLeft(createColorBlock(shell, "B",color(SWT.COLOR_RED)))
        .addToRight(createColorBlock(shell, "B",color(SWT.COLOR_YELLOW)))
        .addToTop(createColorBlock(shell, "B",color(SWT.COLOR_MAGENTA)))
        .addToBottom(createColorBlock(shell, "B",color(SWT.COLOR_BLUE)))
        .finish();
```

## SwtLayoutBuilder.rowWithWrappingLayout
This is RowLayout wrapped in builder syntax. It places the components in horizontal/vertical row.
All components touch each other(but you can specify gap between components), but whole group can be aligned left/right/center.
```java
new SwtLayoutBuilder(shell).rowWithWrappingLayout().margins(10, 10, 10, 10)
        .gapBetweenComponents(5)
        .horizontal()
        .componentPlacementDirection(RowWithWrappingBuilder.ComponentOrientation.RIGHT_TO_LEFT)
        .add(createColorBlock(shell, "Component 1", color(SWT.COLOR_DARK_CYAN), true))
        .preferredSize(SWT.DEFAULT, 10)
        .add(createColorBlock(shell, "Component 2", color(SWT.COLOR_RED), true))
        .add(createColorBlock(shell, "Component 3", color(SWT.COLOR_YELLOW), true))
        .finish();
```

## SwtLayoutBuilder.ruleLayout
The flexible layout that can move any edge of component to other edges of other components.

It can work with components and reference them by id or reference them as **current** or **previous** or mark them as **anchorN**.

**Current** component - component that was last added with method **add**, when you add next component,
the **current** becomes **previous** component and new component becomes **current** component.

Most important methods are move methods
* moveToPrevious - move current component's edge to previous component's edge
* moveToParent - move current component's edge to parent component's edge
* moveToId - move current component's edge to component's edge that has specified id
* moveIdToId - move component with id to other component's edge with id2
* moveParentToId - move parent's edge to other component's edge with id
* moveParentToCurrent - move parent's edge to current component's edge
* moveToAnchor1..moveToAnchor3 - move current component's edge to component's edge marked as anchorN



Example:
```java
new SwtLayoutBuilder(shell).ruleLayout()
        .add(createColorBlock(shell, "1 component", color(SWT.COLOR_WHITE), true)).preferredSize(100, 100)
        .add(createColorBlock(shell, "2 component", color(SWT.COLOR_GREEN), true)).preferredSize(100, 100)
        .moveToPrevious(Edge.TOP, Edge.BOTTOM, 5)
        .add(createColorBlock(shell, "Center component", color(SWT.COLOR_YELLOW), true)).preferredSize(100, 100)
        .moveToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
        .moveToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
        .add(createLabel(shell, "some label 1"))
        .moveToPrevious(Edge.VER_CENTER, Edge.VER_CENTER, 0)
        .moveToPrevious(Edge.LEFT, Edge.RIGHT, 5)

        .add(createColorBlock(shell, "Bottom component", color(SWT.COLOR_BLUE), true))
        .moveToParent(Edge.BOTTOM, Edge.BOTTOM, 1)
        .moveToParent(Edge.LEFT, Edge.LEFT, 10)
        .moveToParent(Edge.RIGHT, Edge.RIGHT, -10)

        .add("square", createColorBlock(shell, "Square component", color(SWT.COLOR_GRAY), true)).preferredSize(100, 5)
        .moveToParent(Edge.RIGHT, Edge.RIGHT, 1)
        .moveToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
        .finish());
```
### How the layout works
All move rules are collected in single list, and when SWT decides to layout the component, the layout just executes all move rules one by one. 
There are no complex calculations to resolve constraints incompatibilities. 

You can easily create something like the following:
```java
    .moveToParent(Edge.LEFT, Edge.LEFT,1)
    .moveToParent(Edge.LEFT, Edge.LEFT,10) //this rule just overwrite the previous rule
```
### Anchors
If you have layout where there is one anchor component and other components placed relative to it,
it will be not very convenient to use *linkToPrevious* because current and previous components will change everytime.
It will be possible to assign id to anchor component, and then link other components with *linkToId* method, but there is a shortcut.
You can make something like this: 
```java
    .add(component1)
    .anchor1()
    ...
    .add(component2)
    .moveToAnchor1(Edge.LEFT, Edge.LEFT, 0)
```
There are 3 anchors and there are methods to move to them:
* anchor1() moveToAnchor1()
* anchor2() moveToAnchor2()
* anchor3() moveToAnchor3()

You can freely mark any component as anchor after method **add(Component)**
```java
.add(component1)
    .anchor1()
.add(component2)
    .anchor1() //mark new component as anchor1
.add(component3)
    .anchor1()
```

### Groups
In case if you have group of components, and then you want to attach the component to edge of bounding box of the group, you can use the following methods:

* **.createGroup(String id)** - create new component group with some id and make it current 
* **.addToCurrentGroup()** - add current component to current group
* **.addToGroup(String groupId)** - add current component to specified group

When the group is created, you can reference its edges in move methods.
You can move the group like any other components, referencing it by id. But group behaves a bit different:
* it does not have baseline
* you cannot change height or width of the group

Other things like moving edges LEFT/RIGHT/HOR_CENTER/VER_CENTER works just fine. 