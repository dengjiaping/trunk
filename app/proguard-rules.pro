# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\liuguiyu-pc\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified myclass name to the JavaScript interface
# myclass:
#-keepclassmembers myclass fqcn.of.javascript.interface.for.webview {
#   public *;
#}
    #指定代码的压缩级别
    -optimizationpasses 5

    #包明不混合大小写
    -dontusemixedcaseclassnames

    #不去忽略非公共的库类
    -dontskipnonpubliclibraryclasses

     #优化  不优化输入的类文件
    -dontoptimize

     #预校验
    -dontpreverify

     #混淆时是否记录日志
    -verbose

     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

    #保护注解
    -keepattributes *Annotation*

    # 保持哪些类不被混淆
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService

   #如果有引用v4包可以添加下面这行
    -keep public class * extends android.support.v4.app.Fragment

    #百度地图混淆设置
    -keep class com.baidu.**{*;}
    -keep class vi.com.gdi.bgl.**{*;}

    #黄油手混淆配置
    -dontwarn butterknife.internal.**
    -keep class **$$ViewInjector { *; }
    -keepnames class * { @butterknife.InjectView *;}

    #友盟推送混淆设置
    -dontwarn com.taobao.**
    -dontwarn anet.channel.**
    -dontwarn anetwork.channel.**
    -dontwarn org.android.**
    -dontwarn org.apache.thrift.**
    -dontwarn com.xiaomi.**
    -dontwarn com.huawei.**

    -keepattributes *Annotation*

    -keep class com.taobao.** {*;}
    -keep class org.android.** {*;}
    -keep class anet.channel.** {*;}
    -keep class com.umeng.** {*;}
    -keep class com.xiaomi.** {*;}
    -keep class com.huawei.** {*;}
    -keep class org.apache.thrift.** {*;}

    -keep class com.alibaba.sdk.android.**{*;}
    -keep class com.ut.**{*;}
    -keep class com.ta.**{*;}

    -keep public class **.R$*{
       public static final int *;
    }

    #友盟分享混淆设置
    -dontshrink
    -dontoptimize
    -dontwarn com.google.android.maps.**
    -dontwarn android.webkit.WebView
    -dontwarn com.umeng.**
    -dontwarn com.tencent.weibo.sdk.**
    -dontwarn com.facebook.**
    -keep public class javax.**
    -keep public class android.webkit.**
    -dontwarn android.support.v4.**
    -keep enum com.facebook.**
    -keepattributes Exceptions,InnerClasses,Signature
    -keepattributes *Annotation*
    -keepattributes SourceFile,LineNumberTable

   -keep public interface com.facebook.**
   -keep public interface com.tencent.**
   -keep public interface com.umeng.socialize.**
   -keep public interface com.umeng.socialize.sensor.**
   -keep public interface com.umeng.scrshot.**

   -keep public class com.umeng.socialize.* {*;}

   -keep class com.facebook.**
   -keep class com.facebook.** { *; }
   -keep class com.umeng.scrshot.**
   -keep public class com.tencent.** {*;}
   -keep class com.umeng.socialize.sensor.**
   -keep class com.umeng.socialize.handler.**
   -keep class com.umeng.socialize.handler.*
   -keep class UMMoreHandler{*;}
   -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
   -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
   -keep class im.yixin.sdk.api.YXMessage {*;}
   -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

   -dontwarn twitter4j.**
   -keep class twitter4j.** { *; }

   -keep class com.tencent.** {*;}
   -dontwarn com.tencent.**
   -keep public class com.umeng.soexample.R$*{
     public static final int *;
   }
   -keep public class com.umeng.soexample.R$*{
     public static final int *;
   }
   -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
   }

   -keep class com.tencent.open.TDialog$*
   -keep class com.tencent.open.TDialog$* {*;}
   -keep class com.tencent.open.PKDialog
   -keep class com.tencent.open.PKDialog {*;}
   -keep class com.tencent.open.PKDialog$*
   -keep class com.tencent.open.PKDialog$* {*;}

   -keep class com.sina.** {*;}
   -dontwarn com.sina.**
   -keep class  com.alipay.share.sdk.** {*;}
   -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
   }

  -keep class com.linkedin.** { *; }
  -keepattributes Signature

     #友盟统计混淆设置
   -keepclassmembers class * {
      public <init> (org.json.JSONObject);
   }

    -keep public class com.histudent.jwsoft.histudent.R$*{
      public static final int *;
    }

    -keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
    }

    -keep public class com.umeng.fb.ui.ThreadView { }

    -keep class com.ut.mini.internal.UTOriginalCustomHitBuilder{*;}

    -keep class com.taobao.accs.utl.**{*;}

     #Xutils混淆
    -dontwarn com.lidroid.xutils.**
    -keep class com.lidroid.xutils.** { *; }

     #数据库混淆
     -keep class com.histudent.jwsoft.histudent.commen.model.SaveAccountModel { *; }
     -keep class com.histudent.jwsoft.histudent.commen.model.SavePostDataModel { *; }
     -keep public class com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel{ *; }
     -keep public class com.histudent.jwsoft.histudent.commen.model.FileInfo{ *; }
     -keep public class com.histudent.jwsoft.histudent.comment2.bean.LocationDB{ *; }
     -keep public class com.histudent.jwsoft.histudent.comment2.utils.MyDBData{ *; }
     -keep public class com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel{ *; }

     #butterknife混淆
     -keep class butterknife.** { *; }
     -dontwarn butterknife.internal.**
     -keep class **$$ViewBinder { *; }

     -keepclasseswithmembernames class * {
         @butterknife.* <fields>;
     }

     -keepclasseswithmembernames class * {
         @butterknife.* <methods>;
     }

     #Eventbus混淆设置
     -keepattributes *Annotation*
     -keepclassmembers class ** {
            @org.greenrobot.eventbus.Subscribe <methods>;
         }
     -keep enum org.greenrobot.eventbus.ThreadMode { *; }

     # Only required if you use AsyncExecutor
     -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
           <init>(Java.lang.Throwable);
     }

    #云信混淆设置
    -dontwarn com.netease.**
    -dontwarn io.netty.**
    -keep class com.netease.** {*;}
    #如果 netty 使用的官方版本，它中间用到了反射，因此需要 keep。如果使用的是我们提供的版本，则不需要 keep
    -keep class io.netty.** {*;}

    #如果你使用全文检索插件，需要加入
    -dontwarn org.apache.lucene.**
    -keep class org.apache.lucene.** {*;}

    #==========================================================

   -keep class android.graphics.** { *; }

   -dontwarn com.alibaba.fastjson.**

   -dontskipnonpubliclibraryclassmembers
   -dontskipnonpubliclibraryclasses

   -keep class com.alibaba.fastjson.** { *; }

   -keepclassmembers class * {
        public <methods>;
    }

   -keepattributes InnerClasses,Signature

   #============================================================

    #忽略警告
    -ignorewarning

    ##记录生成的日志数据,gradle build时在本项目根目录输出##

    #apk 包内所有 myclass 的内部结构
    -dump class_files.txt
    #未混淆的类和成员
    -printseeds seeds.txt
    #列出从 apk 中删除的代码
    -printusage unused.txt
    #混淆前后的映射
    -printmapping mapping.txt

    ########记录生成的日志数据，gradle build时 在本项目根目录输出-end######


    #####混淆保护自己项目的部分代码以及引用的第三方jar包library#######

    #-libraryjars libs/umeng-analytics-v5.2.4.jar

    #三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
    #-libraryjars libs/sdk-v1.0.0.jar
    #-libraryjars libs/look-v1.0.1.jar

    #如果不想混淆 keep 掉
    -keep class com.lippi.recorder.iirfilterdesigner.** {*; }
    #友盟
    -keep class com.umeng.**{*;}
    #项目特殊处理代码

    #忽略警告
    -dontwarn com.lippi.recorder.utils**
    #保留一个完整的包
    -keep class com.lippi.recorder.utils.** {
        *;
     }

    -keep class  com.lippi.recorder.utils.AudioRecorder{*;}


    #如果引用了v4或者v7包
    -dontwarn android.support.**

    ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####

    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }

    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }

    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }

    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

    #保持 Serializable 不被混淆并且enum 类也不被混淆
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

    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers myclass * implements java.io.Serializable即可
    #-keepclassmembers enum * {
    #  public static **[] values();
    #  public static ** valueOf(java.lang.String);
    #}

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }

    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }

    #避免混淆泛型 如果混淆报错建议关掉
    #–keepattributes Signature

    #移除log 测试了下没有用还是建议自己定义一个开关控制是否输出日志
    #-assumenosideeffects myclass android.util.Log {
    #    public static boolean isLoggable(java.lang.String, int);
    #    public static int v(...);
    #    public static int i(...);
    #    public static int w(...);
    #    public static int d(...);
    #    public static int e(...);
    #}

    #如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
    #gson
    #-libraryjars libs/gson-2.2.2.jar
    -keepattributes Signature
    # Gson specific classes
    -keep class sun.misc.Unsafe { *; }
    # Application classes that will be serialized/deserialized over Gson
    -keep class com.google.gson.examples.android.model.** { *; }
    -keep class com.histudent.jwsoft.histudent.model.bean.** { *; }
    -keep class com.histudent.jwsoft.histudent.model.http.**{ *; }
    -keep class com.histudent.jwsoft.histudent.body.mine.model.**{ *; }
    -keep class com.histudent.jwsoft.histudent.commen.bean.**{ *; }



    -keep public class android.net.http.SslError

    -dontwarn android.webkit.WebView
    -dontwarn android.net.http.SslError
    -dontwarn android.webkit.WebViewClient


    #BaseRecyclerViewAdapterHelper 使用此库 缩减代码量 70%
    -keep class com.chad.library.adapter.** {
    *;
    }
    -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
    -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
    -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
         <init>(...);
    }
