# HMenu   [ ![Download](https://api.bintray.com/packages/juanlabrador/maven/HMenu/images/download.svg) ](https://bintray.com/juanlabrador/maven/HMenu/_latestVersion)
A horizontal menu to style iOS.

This library shows a horizontal menu, it is oriented to menus with many icons.

Features
--------
![Demo](buttons.png)

![Demo](demo.gif)


Usage
-----

```xml
<com.juanlabrador.hmenu.Menu
        android:id="@+id/main_add_entry"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentLeft="true"
        android:layout_marginBottom="16dp"
        android:layout_marginLeft="16dp"
        hmenu:icon="@drawable/icn_add_entry"
        hmenu:iconSize="52dp"
        hmenu:paddingBetweenIcons="50"
        hmenu:iconChildSize="46dp"
        hmenu:spacing="20dp"/>
```

This library have limitations.

* With "icon" you set the main icon.
* For "iconChildSize", you should be to set size more little than main icon.
* With "spacing" you add a spacing between main icon and first icon child, you must be to play with this value and "paddingBetweenIcons" for a proper display.

Download
--------------

```groovy
compile 'com.github.juanlabrador:hmenu:1.0@aar'
```

Log
---

1.0

- Add duration time animation
- Add padding between icons
- Add spacing
- Add icon main

Developed by
------------

* Juan Labrador - <juanjavierlabrador@gmail.com>
* Twitter: <a href="https://twitter.com/juanlabrador">@JuanLabrador</a>

Inspiration in
--------------

<a href="https://github.com/daCapricorn/ArcMenu">ArcMenu</a>
