warning: in the working copy of '.idea/misc.xml', LF will be replaced by CRLF the next time Git touches it
[1mdiff --git a/.idea/deploymentTargetSelector.xml b/.idea/deploymentTargetSelector.xml[m
[1mindex 344a011..eb2f85f 100644[m
[1m--- a/.idea/deploymentTargetSelector.xml[m
[1m+++ b/.idea/deploymentTargetSelector.xml[m
[36m@@ -4,11 +4,11 @@[m
     <selectionStates>[m
       <SelectionState runConfigName="app">[m
         <option name="selectionMode" value="DROPDOWN" />[m
[31m-        <DropdownSelection timestamp="2024-09-10T08:16:27.665779400Z">[m
[32m+[m[32m        <DropdownSelection timestamp="2024-10-30T11:37:23.786665Z">[m
           <Target type="DEFAULT_BOOT">[m
[31m-            <handle>[m
[31m-              <DeviceId pluginId="LocalEmulator" identifier="path=C:\Users\Le Anh Tuan Dung\.android\avd\TD_-_Pixel_3_API_35.avd" />[m
[31m-            </handle>[m
[32m+[m[32m            <template>[m
[32m+[m[32m              <DeviceId pluginId="FirebaseDirectAccess" type="TEMPLATE" identifier="model_id=shiba/34" />[m
[32m+[m[32m            </template>[m
           </Target>[m
         </DropdownSelection>[m
         <DialogSelection />[m
[1mdiff --git a/.idea/gradle.xml b/.idea/gradle.xml[m
[1mindex 0897082..e11dba8 100644[m
[1m--- a/.idea/gradle.xml[m
[1m+++ b/.idea/gradle.xml[m
[36m@@ -5,11 +5,12 @@[m
     <option name="linkedExternalProjectsSettings">[m
       <GradleProjectSettings>[m
         <option name="externalProjectPath" value="$PROJECT_DIR$" />[m
[31m-        <option name="gradleJvm" value="#GRADLE_LOCAL_JAVA_HOME" />[m
[32m+[m[32m        <option name="gradleJvm" value="jbr-17" />[m
         <option name="modules">[m
           <set>[m
             <option value="$PROJECT_DIR$" />[m
             <option value="$PROJECT_DIR$/app" />[m
[32m+[m[32m            <option value="$PROJECT_DIR$/testwishlist" />[m
           </set>[m
         </option>[m
         <option name="resolveExternalAnnotations" value="false" />[m
[1mdiff --git a/.idea/misc.xml b/.idea/misc.xml[m
[1mindex 8978d23..ac801d8 100644[m
[1m--- a/.idea/misc.xml[m
[1m+++ b/.idea/misc.xml[m
[36m@@ -1,9 +1,6 @@[m
 <project version="4">[m
   <component name="ExternalStorageConfigurationManager" enabled="true" />[m
   <component name="ProjectRootManager" version="2" languageLevel="JDK_17" default="true" project-jdk-name="jbr-17" project-jdk-type="JavaSDK">[m
[31m-    <output url="file://$PROJECT_DIR$/build/classes" />[m
[31m-  </component>[m
[31m-  <component name="ProjectType">[m
[31m-    <option name="id" value="Android" />[m
[32m+[m[32m    <output url="file://$PROJECT_DIR$/out" />[m
   </component>[m
 </project>[m
\ No newline at end of file[m
[1mdiff --git a/app/build.gradle.kts b/app/build.gradle.kts[m
[1mindex c8d9f7c..5005837 100644[m
[1m--- a/app/build.gradle.kts[m
[1m+++ b/app/build.gradle.kts[m
[36m@@ -54,4 +54,9 @@[m [mdependencies {[m
     //implementation(platform(libs.firebase.bom))[m
     implementation(libs.firebase.auth)[m
     implementation(libs.play.services.auth)[m
[32m+[m[32m    //wishlist[m
[32m+[m[32m    implementation("androidx.recyclerview:recyclerview:1.3.2")[m
[32m+[m[32m    // For control over item selection of both touch and mouse driven selection[m
[32m+[m[32m    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")[m
[32m+[m[32m    implementation("androidx.cardview:cardview:1.0.0")[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/activity_main.xml b/app/src/main/res/layout/activity_main.xml[m
[1mindex a3f6330..ff7e6d3 100644[m
[1m--- a/app/src/main/res/layout/activity_main.xml[m
[1m+++ b/app/src/main/res/layout/activity_main.xml[m
[36m@@ -16,9 +16,7 @@[m
         app:layout_constraintHorizontal_bias="0.0"[m
         app:layout_constraintStart_toStartOf="parent"[m
         app:layout_constraintTop_toTopOf="parent"[m
[31m-        app:layout_constraintVertical_bias="0.0">[m
[31m-[m
[31m-    </FrameLayout>[m
[32m+[m[32m        app:layout_constraintVertical_bias="0.0"/>[m
 [m
     <com.google.android.material.bottomnavigation.BottomNavigationView[m
         android:id="@+id/bottomNavigationView"[m
[1mdiff --git a/app/src/main/res/layout/card.xml b/app/src/main/res/layout/card.xml[m
[1mindex 5f90332..0cb1f58 100644[m
[1m--- a/app/src/main/res/layout/card.xml[m
[1m+++ b/app/src/main/res/layout/card.xml[m
[36m@@ -6,139 +6,168 @@[m
     android:layout_height="match_parent">[m
 [m
     <!-- Guideline 14: 80% chiều cao của layout -->[m
[31m-    <androidx.constraintlayout.widget.Guideline[m
[31m-        android:id="@+id/guideline14"[m
[31m-        android:layout_width="wrap_content"[m
[31m-        android:layout_height="wrap_content"[m
[31m-        android:orientation="horizontal"[m
[31m-        app:layout_constraintGuide_percent="0.8" />[m
[31m-[m
[31m-    <!-- Guideline 9: 60% chiều cao của layout -->[m
[31m-    <androidx.constraintlayout.widget.Guideline[m
[31m-        android:id="@+id/guideline9"[m
[31m-        android:layout_width="wrap_content"[m
[31m-        android:layout_height="wrap_content"[m
[31m-        android:orientation="horizontal"[m
[31m-        app:layout_constraintGuide_percent="0.6" />[m
[31m-[m
[31m-    <!-- ConstraintLayout thứ hai, chứa CardView -->[m
[31m-    <androidx.constraintlayout.widget.ConstraintLayout[m
[31m-        android:id="@+id/cl_Producting"[m
[32m+[m[32m    <androidx.cardview.widget.CardView[m
[32m+[m[32m        android:id="@+id/all"[m
         android:layout_width="0dp"[m
         android:layout_height="0dp"[m
[31m-        app:layout_constraintBottom_toTopOf="@+id/guideline9"[m
[31m-        app:layout_constraintEnd_toEndOf="parent"[m
[31m-        app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m        android:layout_margin="10dp"[m
[32m+[m[32m        app:cardBackgroundColor="#F4F4F4"[m
[32m+[m[32m        app:cardCornerRadius="20dp"[m
[32m+[m[32m        app:layout_constraintBottom_toBottomOf="parent"[m
[32m+[m[32m        app:layout_constraintLeft_toLeftOf="parent"[m
[32m+[m[32m        app:layout_constraintRight_toRightOf="parent"[m
         app:layout_constraintTop_toTopOf="parent">[m
 [m
[31m-        <androidx.cardview.widget.CardView[m
[32m+[m[32m        <androidx.constraintlayout.widget.ConstraintLayout[m
[32m+[m[32m            android:id="@+id/cl_Producting"[m
             android:layout_width="0dp"[m
[31m-            android:layout_height="0dp"[m
[31m-            app:cardCornerRadius="18dp"[m
[31m-            app:layout_constraintBottom_toBottomOf="parent"[m
[31m-            app:layout_constraintEnd_toEndOf="parent"[m
[31m-            app:layout_constraintStart_toStartOf="parent"[m
[31m-            app:layout_constraintTop_toTopOf="parent">[m
[31m-[m
[31m-            <ImageView[m
[31m-                android:id="@+id/iv_Product"[m
[31m-                android:layout_width="match_parent"[m
[31m-                android:layout_height="match_parent"[m
[31m-                app:srcCompat="@drawable/product_img" />[m
[32m+[m[32m            android:layout_height="0dp">[m
[32m+[m
[32m+[m[32m        </androidx.constraintlayout.widget.ConstraintLayout>[m
[32m+[m
[32m+[m[32m        <androidx.constraintlayout.widget.ConstraintLayout[m
[32m+[m[32m            android:id="@+id/cl_ProductPrice"[m
[32m+[m[32m            android:layout_width="match_parent"[m
[32m+[m[32m            android:layout_height="match_parent"[m
[32m+[m[32m            tools:layout_marginTop="@dimen/mediarouter_chooser_list_item_padding_start">[m
 [m
             <TextView[m
[31m-                android:id="@+id/tv_SaleTag"[m
[31m-                android:layout_width="50dp"[m
[32m+[m[32m                android:id="@+id/tv_Price"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
                 android:layout_height="wrap_content"[m
[31m-                android:background="@drawable/sale_tag"[m
[31m-                android:text="25%"[m
[31m-                android:textAlignment="center" />[m
[32m+[m[32m                android:layout_marginStart="32dp"[m
[32m+[m[32m                android:layout_marginTop="72dp"[m
[32m+[m[32m                android:lineSpacingExtra="2dp"[m
[32m+[m[32m                android:text="$1299"[m
[32m+[m[32m                android:textSize="15sp"[m
[32m+[m[32m                app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                app:layout_constraintTop_toTopOf="@+id/guideline14" />[m
 [m
             <ImageButton[m
[31m-                android:id="@+id/ib_WishlistAdd"[m
[31m-                android:layout_width="44dp"[m
[31m-                android:layout_height="33dp"[m
[31m-                android:layout_gravity="right"[m
[31m-                app:srcCompat="@drawable/heart_selector"[m
[32m+[m[32m                android:id="@+id/ibt_Add"[m
[32m+[m[32m                android:layout_width="wrap_content"[m
[32m+[m[32m                android:layout_height="wrap_content"[m
[32m+[m[32m                android:layout_marginEnd="16dp"[m
[32m+[m[32m                android:layout_marginBottom="20dp"[m
[32m+[m[32m                app:layout_constraintBottom_toBottomOf="parent"[m
[32m+[m[32m                app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m                app:layout_constraintTop_toTopOf="@+id/guideline14"[m
[32m+[m[32m                app:layout_constraintVertical_bias="0.275"[m
[32m+[m[32m                app:srcCompat="@drawable/group_34012"[m
                 tools:ignore="SpeakableTextPresentCheck" />[m
[31m-        </androidx.cardview.widget.CardView>[m
 [m
[31m-    </androidx.constraintlayout.widget.ConstraintLayout>[m
[31m-[m
[31m-    <androidx.constraintlayout.widget.ConstraintLayout[m
[31m-        android:id="@+id/cl_ProductName"[m
[31m-        android:layout_width="0dp"[m
[31m-        android:layout_height="0dp"[m
[31m-        app:layout_constraintBottom_toTopOf="@+id/guideline14"[m
[31m-        app:layout_constraintEnd_toEndOf="parent"[m
[31m-        app:layout_constraintStart_toStartOf="parent"[m
[31m-        app:layout_constraintTop_toTopOf="@+id/guideline9">[m
[31m-[m
[31m-        <LinearLayout[m
[31m-            android:id="@+id/linearLayout2"[m
[31m-            android:layout_width="wrap_content"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            android:orientation="horizontal"[m
[31m-            app:layout_constraintStart_toStartOf="parent"[m
[31m-            app:layout_constraintTop_toBottomOf="@+id/tv_Name">[m
[31m-[m
[31m-            <TextView[m
[31m-                android:id="@+id/tv_Brand"[m
[32m+[m[32m            <androidx.constraintlayout.widget.Guideline[m
[32m+[m[32m                android:id="@+id/guideline14"[m
                 android:layout_width="wrap_content"[m
                 android:layout_height="wrap_content"[m
[31m-                android:text="Lenovo" />[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                app:layout_constraintGuide_percent="0.6" />[m
 [m
[31m-            <ImageView[m
[31m-                android:id="@+id/iv_Icon"[m
[31m-                android:layout_width="20dp"[m
[31m-                android:layout_height="19dp"[m
[31m-                app:srcCompat="@drawable/twitter_verified_badge_1" />[m
[31m-        </LinearLayout>[m
[32m+[m[32m            <androidx.constraintlayout.widget.Guideline[m
[32m+[m[32m                android:id="@+id/guideline9"[m
[32m+[m[32m                android:layout_width="44dp"[m
[32m+[m[32m                android:layout_height="144dp"[m
[32m+[m[32m                android:orientation="horizontal"[m
[32m+[m[32m                app:layout_constraintGuide_percent="0.42" />[m
[32m+[m
[32m+[m[32m            <androidx.constraintlayout.widget.ConstraintLayout[m
[32m+[m[32m                android:id="@+id/cl_ProductName"[m
[32m+[m[32m                android:layout_width="0dp"[m
[32m+[m[32m                android:layout_height="0dp"[m
[32m+[m[32m                app:layout_constraintBottom_toTopOf="@+id/guideline14"[m
[32m+[m[32m                app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m                app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                app:layout_constraintTop_toTopOf="@+id/guideline9">[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/tv_Name"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="23dp"[m
[32m+[m[32m                    android:letterSpacing="0.03"[m
[32m+[m[32m                    android:text="Legion 5 2021"[m
[32m+[m[32m                    android:textAlignment="viewStart"[m
[32m+[m[32m                    android:textSize="15sp"[m
[32m+[m[32m                    android:textStyle="bold"[m
[32m+[m[32m                    app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                    app:layout_constraintTop_toTopOf="parent" />[m
[32m+[m
[32m+[m[32m                <LinearLayout[m
[32m+[m[32m                    android:id="@+id/linearLayout2"[m
[32m+[m[32m                    android:layout_width="184dp"[m
[32m+[m[32m                    android:layout_height="41dp"[m
[32m+[m[32m                    android:orientation="horizontal"[m
[32m+[m[32m                    app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                    app:layout_constraintTop_toBottomOf="@+id/tv_Name">[m
[32m+[m
[32m+[m[32m                    <TextView[m
[32m+[m[32m                        android:id="@+id/tv_Brand"[m
[32m+[m[32m                        android:layout_width="wrap_content"[m
[32m+[m[32m                        android:layout_height="wrap_content"[m
[32m+[m[32m                        android:text="Lenovo" />[m
[32m+[m
[32m+[m[32m                    <ImageView[m
[32m+[m[32m                        android:id="@+id/iv_Icon"[m
[32m+[m[32m                        android:layout_width="20dp"[m
[32m+[m[32m                        android:layout_height="19dp"[m
[32m+[m[32m                        app:srcCompat="@drawable/twitter_verified_badge_1" />[m
[32m+[m[32m                </LinearLayout>[m
[32m+[m
[32m+[m[32m            </androidx.constraintlayout.widget.ConstraintLayout>[m
[32m+[m
[32m+[m[32m            <androidx.constraintlayout.widget.ConstraintLayout[m
[32m+[m[32m                android:id="@+id/cl_Product"[m
[32m+[m[32m                android:layout_width="0dp"[m
[32m+[m[32m                android:layout_height="0dp"[m
[32m+[m[32m                app:layout_constraintBottom_toTopOf="@+id/cl_ProductName"[m
[32m+[m[32m                app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m                app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                app:layout_constraintTop_toTopOf="parent">[m
[32m+[m
[32m+[m[32m                <TextView[m
[32m+[m[32m                    android:id="@+id/tv_SaleTag"[m
[32m+[m[32m                    android:layout_width="60dp"[m
[32m+[m[32m                    android:layout_height="25dp"[m
[32m+[m[32m                    android:background="@drawable/sale_tag"[m
[32m+[m[32m                    android:elevation="5dp"[m
[32m+[m[32m                    android:text="TextView"[m
[32m+[m[32m                    android:textAlignment="center"[m
[32m+[m[32m                    android:textSize="14sp"[m
[32m+[m[32m                    app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                    app:layout_constraintTop_toTopOf="parent" />[m
[32m+[m
[32m+[m[32m                <ImageButton[m
[32m+[m[32m                    android:id="@+id/ib_WishlistAdd"[m
[32m+[m[32m                    android:layout_width="wrap_content"[m
[32m+[m[32m                    android:layout_height="wrap_content"[m
[32m+[m[32m                    android:elevation="5dp"[m
[32m+[m[32m                    app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m                    app:layout_constraintTop_toTopOf="parent"[m
[32m+[m[32m                    app:srcCompat="@drawable/heart_selector"[m
[32m+[m[32m                    tools:ignore="SpeakableTextPresentCheck" />[m
[32m+[m
[32m+[m[32m                <androidx.cardview.widget.CardView[m
[32m+[m[32m                    android:layout_width="0dp"[m
[32m+[m[32m                    android:layout_height="0dp"[m
[32m+[m[32m                    app:cardCornerRadius="20dp"[m
[32m+[m[32m                    app:layout_constraintBottom_toBottomOf="parent"[m
[32m+[m[32m                    app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m                    app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m                    app:layout_constraintTop_toTopOf="parent">[m
[32m+[m
[32m+[m[32m                    <ImageView[m
[32m+[m[32m                        android:id="@+id/iv_Product"[m
[32m+[m[32m                        android:layout_width="wrap_content"[m
[32m+[m[32m                        android:layout_height="178dp"[m
[32m+[m[32m                        app:srcCompat="@drawable/product_img" />[m
[32m+[m
[32m+[m[32m                </androidx.cardview.widget.CardView>[m
[32m+[m[32m            </androidx.constraintlayout.widget.ConstraintLayout>[m
[32m+[m
[32m+[m[32m        </androidx.constraintlayout.widget.ConstraintLayout>[m
[32m+[m[32m    </androidx.cardview.widget.CardView>[m
 [m
[31m-        <TextView[m
[31m-            android:id="@+id/tv_Name"[m
[31m-            android:layout_width="0dp"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            android:letterSpacing="0.03"[m
[31m-            android:text="Legion 5 2021"[m
[31m-            android:textAlignment="viewStart"[m
[31m-            android:textSize="15sp"[m
[31m-            android:textStyle="bold"[m
[31m-            app:layout_constraintEnd_toEndOf="parent"[m
[31m-            app:layout_constraintStart_toStartOf="parent"[m
[31m-            app:layout_constraintTop_toTopOf="parent" />[m
[31m-    </androidx.constraintlayout.widget.ConstraintLayout>[m
[31m-[m
[31m-    <androidx.constraintlayout.widget.ConstraintLayout[m
[31m-        android:id="@+id/cl_ProductPrice"[m
[31m-        android:layout_width="match_parent"[m
[31m-        android:layout_height="143dp"[m
[31m-        app:layout_constraintBottom_toBottomOf="parent"[m
[31m-        app:layout_constraintEnd_toEndOf="parent"[m
[31m-        app:layout_constraintStart_toStartOf="parent"[m
[31m-        app:layout_constraintTop_toBottomOf="@+id/cl_ProductName">[m
[31m-[m
[31m-        <TextView[m
[31m-            android:id="@+id/tv_Price"[m
[31m-            android:layout_width="wrap_content"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            android:layout_marginTop="64dp"[m
[31m-            android:lineSpacingExtra="2dp"[m
[31m-            android:text="$1299"[m
[31m-            android:textSize="15sp"[m
[31m-            app:layout_constraintStart_toStartOf="parent"[m
[31m-            app:layout_constraintTop_toTopOf="parent" />[m
[31m-[m
[31m-        <ImageButton[m
[31m-            android:id="@+id/ibt_Add"[m
[31m-            android:layout_width="wrap_content"[m
[31m-            android:layout_height="wrap_content"[m
[31m-            android:layout_marginTop="44dp"[m
[31m-            android:layout_marginEnd="40dp"[m
[31m-            app:layout_constraintEnd_toEndOf="parent"[m
[31m-            app:layout_constraintTop_toTopOf="parent"[m
[31m-            app:srcCompat="@drawable/group_34012"[m
[31m-            tools:ignore="SpeakableTextPresentCheck" />[m
[31m-    </androidx.constraintlayout.widget.ConstraintLayout>[m
[32m+[m[32m    <!-- Guideline 9: 60% chiều cao của layout -->[m
[32m+[m
[32m+[m[32m    <!-- ConstraintLayout thứ hai, chứa CardView -->[m
 [m
 </androidx.constraintlayout.widget.ConstraintLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/layout/wishlist.xml b/app/src/main/res/layout/wishlist.xml[m
[1mindex cdc89f2..da8192b 100644[m
[1m--- a/app/src/main/res/layout/wishlist.xml[m
[1m+++ b/app/src/main/res/layout/wishlist.xml[m
[36m@@ -1,6 +1,30 @@[m
 <?xml version="1.0" encoding="utf-8"?>[m
 <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
[31m-             android:layout_width="match_parent"[m
[31m-             android:layout_height="match_parent">[m
[32m+[m[32m    xmlns:app="http://schemas.android.com/apk/res-auto"[m
[32m+[m[32m    xmlns:tools="http://schemas.android.com/tools"[m
[32m+[m[32m    android:layout_width="match_parent"[m
[32m+[m[32m    android:layout_height="match_parent">[m
[32m+[m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:id="@+id/tv_WishList"[m
[32m+[m[32m        android:layout_width="match_parent"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_marginTop="80dp"[m
[32m+[m[32m        android:text="Sản phẩm yêu thích"[m
[32m+[m[32m        android:textAlignment="textStart"[m
[32m+[m[32m        android:textStyle="bold"[m
[32m+[m[32m        app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m        app:layout_constraintHorizontal_bias="0.0"[m
[32m+[m[32m        app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m        app:layout_constraintTop_toTopOf="parent" />[m
[32m+[m
[32m+[m[32m    <androidx.recyclerview.widget.RecyclerView[m
[32m+[m[32m        android:id="@+id/rc_WishList"[m
[32m+[m[32m        android:layout_width="0dp"[m
[32m+[m[32m        android:layout_height="0dp"[m
[32m+[m[32m        app:layout_constraintBottom_toBottomOf="parent"[m
[32m+[m[32m        app:layout_constraintEnd_toEndOf="parent"[m
[32m+[m[32m        app:layout_constraintStart_toStartOf="parent"[m
[32m+[m[32m        app:layout_constraintTop_toBottomOf="@+id/tv_WishList" />[m
 [m
 </androidx.constraintlayout.widget.ConstraintLayout>[m
\ No newline at end of file[m
[1mdiff --git a/gradle/libs.versions.toml b/gradle/libs.versions.toml[m
[1mindex c4f1caf..a48f202 100644[m
[1m--- a/gradle/libs.versions.toml[m
[1m+++ b/gradle/libs.versions.toml[m
[36m@@ -1,5 +1,5 @@[m
 [versions][m
[31m-agp = "8.5.2"[m
[32m+[m[32magp = "8.6.0"[m
 junit = "4.13.2"[m
 junitVersion = "1.2.1"[m
 espressoCore = "3.6.1"[m
[1mdiff --git a/settings.gradle.kts b/settings.gradle.kts[m
[1mindex 0295329..886eb6d 100644[m
[1m--- a/settings.gradle.kts[m
[1m+++ b/settings.gradle.kts[m
[36m@@ -21,4 +21,3 @@[m [mdependencyResolutionManagement {[m
 [m
 rootProject.name = "AndroidProject"[m
 include(":app")[m
[31m- [m
\ No newline at end of file[m
