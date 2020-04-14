# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class carName to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file carName.
#-renamesourcefileattribute SourceFile
-keep class com.example.carbuzz.activity.**
-keep class com.example.carbuzz.firebaseRepo.**
-keep class com.example.carbuzz.fragment.**
-keep class com.example.carbuzz.utils.**
-keep class com.example.carbuzz.adapters.**