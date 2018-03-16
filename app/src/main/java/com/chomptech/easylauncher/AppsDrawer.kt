package com.chomptech.easylauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import RAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import android.content.pm.ResolveInfo
import android.content.Intent
import com.chomptech.easylauncher.R.id.appsList
import android.content.pm.PackageManager
import android.os.AsyncTask
import com.chomptech.easylauncher.AppsDrawer.myThread
import java.util.*


class AppsDrawer : AppCompatActivity() {
    val radapter = RAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)

        val recyclerView = findViewById(R.id.appsList) as RecyclerView

        recyclerView.adapter = radapter
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        myThread().execute()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    inner class myThread : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg Params: Void): String {
    /*
            val pm = packageManager
            appList = ArrayList().toInt()

            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)

            val allApps = pm.queryIntentActivities(i, 0)
            for (ri in allApps) {
                val app = AppInfo()
                app.label = ri.loadLabel(pm)
                app.packageName = ri.activityInfo.packageName
                app.icon = ri.activityInfo.loadIcon(pm)
                radapter.addApp(app)
            }*/


            val pm = applicationContext.getPackageManager()
            var appList = ArrayList<AppInfo>()

            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)

            val allApps = pm.queryIntentActivities(i, 0)
            val allAppsAlpha = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            for (ri in allAppsAlpha) {
                if(pm.getLaunchIntentForPackage(ri.packageName) != null){
                    //If you're here, then this is a launch-able app
                    val app = AppInfo()

                    /* after deleting 'Google Play' from name must find other property to use for sorting
                    if (ri.loadLabel(pm).contains("Google Play ")) {
                        app.label = ri.loadLabel(pm).substring(11)
                    } else {
                        app.label = ri.loadLabel(pm)
                    }*/
                    app.label = ri.loadLabel(pm)
                    app.packageName = ri.packageName
                    //app.packageName = allApps[temp].activityInfo.packageName
                    //app.icon = allApps[temp].activityInfo.loadIcon(pm)
                    app.icon = ri.loadIcon(pm)
                    appList.add(app)
                }
            }
            Collections.sort(appList, object : Comparator<AppInfo> {
                override fun compare(p0: AppInfo?, p1: AppInfo?): Int {
                    return p0!!.label.toString().compareTo(p1!!.label.toString())
                }
            })
            radapter.addApps(appList)
            return "Success"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            updateStuff()
        }

        fun updateStuff() {
            radapter.notifyItemInserted(radapter.itemCount - 1)

        }

    }
}
