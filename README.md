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
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://dl.bintray.com/kaysaith1900/maven"}
    }
}
```

Add this in your Module Gradle
```
dependencies {
  compile 'com.blinnnk:honeyui:0.2.3.3'  
}
```

Current Version of HoneyUI

```
Version = 0.2.3.3
```

### Honey Permission Util

Easy to check permission status

```
if (activity.verifyMultiplePermissions(
        PermissionCategory.Write,
        PermissionCategory.Read)
        ) {
            // Do Something
        }

```

Easy to monitor the status of the authorization check

```
activity.requestPermissionListener(PermissionCategory.Camera, ...) { hasPermission ->
      if (hasPermission) {
        // Do Something
      } else {
        // Do Something
      }
    }

```

### Honey Emoji View

Very Easy to user amazing and beautiful emoji view, These emoticons are drawn using ondraw brushes
Save memory, high efficiency and moving

![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/cold.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/cry.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/flouringeyes.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/helplesslaugh.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/laughcry.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/sad.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/shy.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/thumb.gif)
![](https://github.com/kaysaith/honeyui/blob/master/honey/src/main/res/drawable/angry.gif)

```
val scaredButton = HoneyEmojiButton(context, EmojiType.Sob) 
addView(scaredButton)
val smileButton by lazy { HoneyEmojiButton(context, EmojiType.Smile) }
addView(smileButton)

```

### IntelliJ IDEA project

If your project is not based on Gradle, just attach the required JARs from the jcenter repository as the library dependencies and that's it.