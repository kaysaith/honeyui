# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
#   http://proguard.sourceforge.net/index.html#manual/usage.html
#   http://proguard.sourceforge.net/index.html#manual/examples.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


# Some important configuration is based on the annotations in the code.
# We have to specify what the annotations mean to ProGuard.

# General Options
-verbose
-dontpreverify

# Suppress duplicate warning for system classes;  Blaze is passing android.jar
# to proguard multiple times.
-dontnote android.**
-dontnote dalvik.**
-dontnote com.android.**
-dontnote google.**
-dontnote com.google.**
-dontnote java.**
-dontnote javax.**
-dontnote junit.**
-dontnote org.apache.**
-dontnote org.json.**
-dontnote org.w3c.dom.**
-dontnote org.xml.sax.**
-dontnote org.xmlpull.v1.**


# Stop warnings about missing unused classes
-dontwarn android.**
-dontwarn dalvik.**
-dontwarn com.android.**
-dontwarn google.**
-dontwarn com.google.**
-dontwarn java.**
-dontwarn javax.**
-dontwarn junit.**
-dontwarn org.apache.**
-dontwarn org.json.**
-dontwarn org.w3c.dom.**
-dontwarn org.xml.sax.**
-dontwarn org.xmlpull.v1.**

# Input/Output Options
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

# Keep Options
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class **.R
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

-keepclassmembers,allowoptimization enum * {
    <init>(...);
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# In the optimization step, ProGuard will then remove calls to such methods, if it can determine that the return values aren't used.

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

#Specifies that the access modifiers of classes and class members may be broadened during processing.
#Counter-indication: you probably shouldn't use this option when processing code that is to be used as a library, since classes and class members that weren't designed to be public in the API may become public.
-allowaccessmodification

# Obfuscation Options
#-dontobfuscate
-dontusemixedcaseclassnames

#For app
#-keepattributes *Annotation*

-optimizationpasses 10
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses, Signature, SourceFile,LineNumberTable, Exceptions

-printmapping build/mapping.txt

-renamesourcefileattribute SourceFile

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Sentry Java
-dontwarn javax.naming.**
-dontwarn javax.servlet.**
-dontwarn org.slf4j.**

# Only necessary if you downloaded the SDK jar directly instead of from maven.
-keep class com.shaded.fasterxml.jackson.** { *; }

# Sun Misc
-dontwarn sun.misc.****

# PBKDF2withHmacSHA512
-keep class org.spongycastle.** { *; }

# V8 Render
-keep class android.support.v8.renderscript.** { *; }

# Jackson
-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings { <fields>; }
-keepclassmembers class kotlin.Metadata { public <methods>; }
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keep class android.support.design.widget.TabLayout { *; }
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-adaptclassstrings com.example.Test
