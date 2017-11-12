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