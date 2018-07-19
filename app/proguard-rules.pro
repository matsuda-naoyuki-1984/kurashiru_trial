

# ----------------------------------------
# RxJava
# ----------------------------------------
-dontwarn rx.internal.util.unsafe.**
-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
# ----------------------------------------
# Support Library
# ----------------------------------------
-dontwarn android.support.**
-keep class android.support.** { *; }

# ----------------------------------------
# Retrofit and OkHttp
# ----------------------------------------
-dontwarn okio.**
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn okhttp3.**
-dontwarn org.codehaus.mojo.animal_sniffer.**

# ----------------------------------------
# Picasso
# ----------------------------------------
-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**


# ----------------------------------------
# Otto
# ----------------------------------------
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
# ----------------------------------------
# Gson
# ----------------------------------------
-dontwarn sun.misc.Unsafe

# ----------------------------------------
# App
# ----------------------------------------
-keep class com.kurashiru.kurahirutrial.** { *; }
-keepnames class ** { *; }


# ----------------------------------------
# Android and Java
# ----------------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.preference.Preference

-keepclassmembers class **.R$* {
  public static <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn android.databinding.**
-dontwarn org.antlr.v4.**
