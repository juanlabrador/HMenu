# HorizontalMenu   [![Download](https://api.bintray.com/packages/juanlabrador/maven/HorizontalMenu/images/download.svg) ](https://bintray.com/juanlabrador/maven/HorizontalMenu/_latestVersion)
A horizontal menu to style iOS.

<a href='https://bintray.com/juanlabrador/maven/HorizontalMenu/view?source=watch' alt='Get automatic notifications about new "HorizontalMenu" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

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
compile 'com.github.juanlabrador.hmenu:hmenu:1.0'
```

Log
---

1.0

- Add duration time animation
- Add padding between icons
- Add spacing
- Add icon main

Developed by
============

* Juan Labrador - <juanjavierlabrador@gmail.com>
* Twitter: <a href="https://twitter.com/juanlabrador">@JuanLabrador</a>

Inspiration in
--------------

<a href="https://github.com/daCapricorn/ArcMenu">ArcMenu</a>

License
--------

    Copyright 2015 Juan Labrador.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.