package com.chomptech.easylauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.content.pm.ResolveInfo
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_apps_drawer.*
import kotlinx.android.synthetic.main.activity_main.*


//https://www.androidauthority.com/make-a-custom-android-launcher-837342-837342/
//https://www.androidauthority.com/how-to-use-recycler-views-836053/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chromeIcon = findViewById<View>(R.id.chromeButton) as ImageView
        chromeIcon.setImageDrawable(getActivityIcon(this, "com.android.chrome", "com.google.android.apps.chrome.Main"))
    }

    fun onChromeButtonClick(v: View) {
        //val launchIntent = packageManager.getLaunchIntentForPackage("com.android.chrome")
        val launchIntent = Intent(this, AppsDrawer::class.java)
        startActivity(launchIntent)
    }

    fun getActivityIcon(context: Context, packageName: String, activityName: String): Drawable {
        val pm = context.packageManager
        val intent = Intent()
        intent.component = ComponentName(packageName, activityName)
        val resolveInfo = pm.resolveActivity(intent, 0)

        return resolveInfo.loadIcon(pm)
    }
}