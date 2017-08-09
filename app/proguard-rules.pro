# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#忽略警告
-ignorewarnings
#指定混淆级别
-optimizationpasses 5
#不跳过非公共库的类成员
-dontskipnonpubliclibraryclassmembers
#混淆类中的方法名也混淆
-useuniqueclassmembernames
#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
#将文件来源重命名为"SourceFile"字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable
#保留所有实现了Serializable接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保留Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
#测试相关的
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
-dontwarn org.mockito.**


#实体类
-keep public class com.zfsoftmh.entity.** { *; }

#第三方库
#retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

#Gson
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

#Rx
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

#OKhttp
-dontwarn okio.**

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#circleimageview
-dontwarn de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.** { *; }

#zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }

#expandablerecyclerview
-dontwarn com.bignerdranch.expandablerecyclerview.**
-keep class com.bignerdranch.expandablerecyclerview.** { *; }

#easyrecyclerviewsidebar
-dontwarn com.camnter.easyrecyclerviewsidebar.**
-keep class com.camnter.easyrecyclerviewsidebar.** { *; }

#stickyheadersrecyclerview
-dontwarn com.timehop.stickyheadersrecyclerview.**
-keep class com.timehop.stickyheadersrecyclerview.** { *; }

#avi
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#banner
-keep class com.youth.banner.** { *; }

#compressor
-dontwarn com.zxy.tiny.**
-keep class com.zxy.tiny.** { *; }

#navigationtabbar
-dontwarn devlight.io.**
-keep class devlight.io.** { *; }

#status bar
-dontwarn com.gyf.barlibrary.**
-keep class com.gyf.barlibrary.** { *; }

#date picker
-dontwarn com.appeaser.sublimepickerlibrary.**
-keep class com.appeaser.sublimepickerlibrary.** { *; }

#CircularFloatingActionMenu
-dontwarn com.oguzdev.circularfloatingactionmenu.library.**
-keep class com.oguzdev.circularfloatingactionmenu.library.** { *; }

#easyrecyclerview
-dontwarn com.jude.easyrecyclerview.**
-keep class com.jude.easyrecyclerview.** { *; }

#glide-transformations
-dontwarn jp.wasabeef.glide.transformations.**
-keep class jp.wasabeef.glide.transformations.** { *; }