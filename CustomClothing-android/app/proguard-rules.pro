# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
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

#如果加入一些自己的混淆规则 只需要在 proguard-rules.pro 中文件加入自己的混淆规则即可,
#其实google以及给我提供很好的打包规则, 即proguard-rules.pro 啥也不写, 我打出来的release包也是混淆好的
#但是我们会遇到一些情况, 不得不 添加自己的混淆规则:
#1. 代码中使用了反射，如一些ORM框架的使用
#          需要保证类名 方法不变, 不然混淆后, 就反射不了
#2. 使用GSON、fastjson等JSON解析框架所生成的对象类
#          生成的bean实体对象,内部大多是通过反射来生成, 不能混淆
#3. 引用了第三方开源框架或继承第三方SDK，如开源的okhttp网络访问框架，百度定位SDK等
#          在这些第三库的文档中 一班会给出 相应的 混淆规则, 复制过来即可
#4. 有用到WEBView的JS调用接口
#          没真么用过这块, 不是很熟, 网上那个看到的
#5. 继承了Serializable接口的类
#          在反序列画的时候, 需要正确的类名等, 在Android 中大多是实现 Parcelable来序列化的

# 对于一些基本指令的添加
#############################################
# 1，代码混淆压缩比，在0~7之间，默认为5，一般不做修改
# -optimizationpasses 5
# 2，混合时不使用大小写混合，混合后的类名为小写
# -dontusemixedcaseclassnames
# 3，指定不去忽略非公共库的类
# -dontskipnonpubliclibraryclasses
# 4，这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
# -verbose
# 5，指定不去忽略非公共库的类成员
# -dontskipnonpubliclibraryclassmembers
# 6，不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
# -dontpreverify

#########################################################
###################基本规则###############################
#1，不混淆反射
#-keepattributes Signature
#-keepattributes EnclosingMethod

#2，不混淆该包下所有的类
#-keep class czy.**{*;}  // 不混淆所有的com.czy.bean包下的类和这些类的所有成员变量，
#-keep class czy.*{*;}    // 如果是单个*的应该是一级目录的意思，两个*应该表示所有的意思

#3，不混淆某个包下的类
#-keep class com.czy.**  // 不混淆所有com.czy包下的类，** 换成具体的类名则表示不混淆某个具体的类

#4，不混淆ViewBinder结尾的类
#-keep class **$$ViewBinder {*;}

#5，所有Activity的子类不混淆，但其成员会被混淆,采用继承类型只保留了类名，属性还是会被混淆
#-keep public class * extends android.app.Activity

#6，实现了Serializable接口的不混淆
#-keep public class * implements java.io.Serializable

#########################################################


# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# Realm
-keep class io.realm.annotations.RealmModule        # 保留某个具体的类
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * {*;}
-dontwarn javax.**                                  # 编译时，不警告该包下的类
-dontwarn io.realm.**

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** {*;}                       # 保留该包下的所有的类以及类的所有成员
-keepattributes Signature                           # 保留反射
-keepattributes Exceptions

# Butterknife
-keep class butterknife.** {*;}                     # 保留该包下的所有的类以及类的所有成员
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder {*;}                     # 保留ViewBinde结尾的类，是Butterknife自动生成的
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;                        # 保留成员属性
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;                       # 保留成员方法
}
-dontwarn butterknife.compiler.**                   # 不警告该包下的类

# EventBus
-keepattributes *Annotation                                 # 保留Annotation注解不混淆
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;       # 保留方法
}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}              # 保留枚举

# OkHttp3
-keepattributes Signature           # 保留反射
-keepattributes *Annotation*        # 保留Annotation注解不混淆
-keep class okhttp3.** {*;}
-dontwarn okhttp3.**
-keep interface okhttp3.** {*;}     # 保留包下所有的接口

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* {*;}
-keep public class java.nio.* { *;}

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {    # *ArrayQueue*Field*类的正则
    long producerIndex;         # 保留指定的字段
    long consumerIndex;         # 保留指定的字段
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;       # 保留指定的字段
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;       # 保留指定的字段
}

# 保留本地native类的native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

-dontwarn com.google.auto.**        # 禁止警告该包的类

-keep class com.synnapps.carouselview.** {*;}           # 不混淆该自定义控件
-keep public class * extends android.app.Activity       # 保留所有Activity的子类名，但其成员会被混淆,需要加入{*;}才能保留成员
-keep public class * implements java.io.Serializable    # 不混淆Serializable的子类，但其成员会被混淆

# 主要用于json的装换，所以不混淆
-keep class com.tl.customclothing.http.response.** {*;}
-keep class com.tl.customclothing.http.request.** {*;}

###################自我总结########################
# 1，默认的Application类名不混淆，但成员名混淆
# 2，保留继承式的类时只会保留类名，其成员会被混淆
