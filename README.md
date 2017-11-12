<p><a href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://camo.githubusercontent.com/8e7da7b6b632d5ef4bce9a550a5d5cfe400ca1fe/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6c6963656e73652d4170616368652532304c6963656e7365253230322e302d626c75652e7376673f7374796c653d666c6174" alt="GitHub license" data-canonical-src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat" style="max-width:100%;"></a></p>

# Honey UI

Full use of Koltin to achieve good-looking animated tools, authoring tools, coroutine tools, and other commonly used attributes of the extension tool. Kotlin completely drawn animated emoticons and placeholder

![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/honeyui.jpg)

HoneyUI consists of several parts:

* Honey View Animation
* Common property extensions
* Honey Design Font
* Convenient interface operation tool
* Emoji & Animation View with OnDraw - by Rita
* SvgToPath Util
* Convenient licensing management tools

More convenient and easy to use features constantly updated

## Using HoneyUI

Add this in your Project Gradle
```renderscript
allprojects {
    repositories {
        jcenter()
        maven { url "https://dl.bintray.com/kaysaith1900/maven"}
    }
}
```

Add this in your Module Gradle
```renderscript
dependencies {
  compile 'com.blinnnk:honeyui:0.2.3.5'  
}
```

Current Version of HoneyUI

```renderscript
Version = 0.2.3.5
```

### Honey Permission Util

Easy to check permission status

```kotlin
fun Activity.verifyMultiplePermission() {
    if (verifyMultiplePermissions(PermissionCategory.Write, PermissionCategory.Read)) {
        // Do Something
     }
}
```

Easy to monitor the status of the authorization check

```kotlin
fun Activity.requestMultiplePermission() {
    requestPermissionListener(PermissionCategory.Camera, MorePermissions) { hasPermission ->
      if (hasPermission) {
        // Do Something
      } else {
        // Do Something
      }
    }
}

```

### Honey Emoji View

Very Easy to use amazing and beautiful emoji view, These emoticons are drawn using ondraw brushes
Save memory, high efficiency and moving

<img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/cold.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/cry.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/flouringeyes.gif" width="96">
<img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/helplesslaugh.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/laughcry.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/sad.gif" width="96">
<img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/shy.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/thumb.gif" width="96"><img src="https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/angry.gif" width="96">

```kotlin
class MyLayout(context: Context) : LinearLayout(context) {
  init {
      val sobButton = HoneyEmojiButton(context, EmojiType.Sob) 
      addView(sobButton)
      val smileButton = HoneyEmojiButton(context, EmojiType.Smile)
      addView(smileButton)
  }
}
```

### IntelliJ IDEA project

If your project is not based on Gradle, just attach the required JARs from the jcenter repository as the library dependencies and that's it.