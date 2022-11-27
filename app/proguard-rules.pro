# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-ignorewarnings
-dontwarn org.openxmlformats.**
#-keep public class com.mahmoudgalal.myphoneinvoice.fragments.InvoicesFragment.class
-keep  class org.openxmlformats.** { *; }
-keep  class com.bea.xml.** { *; }
-keep  class com.wutka.** { *; }
-keep  class aavx.xml.** { *; }
-keep  class org.apache.** { *; }
-keep class org.etsi.** { *; }
-keep class org.w3.** { *; }
-keep class com.microsoft.schemas.** { *; }
-keep  class schemasMicrosoftComOfficeOffice.** { *; }
-keep  class schemasMicrosoftComOfficeExcel.** { *; }
-keep  class schemasMicrosoftComVml.** { *; }
-keep class repackage.** { *; }
-keep class schemaorg_apache_xmlbeans.** { *; }
#-libraryjars libs/poi-ooxml-schemas-3.12-20150511-a.jar
#-libraryjars libs/poi-3.12-android-a.jar

#;libs/poi-3.12-android-a.jar