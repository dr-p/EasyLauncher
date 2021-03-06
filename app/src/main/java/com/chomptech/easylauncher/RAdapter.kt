
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.chomptech.easylauncher.AppInfo
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.chomptech.easylauncher.MainActivity
import com.chomptech.easylauncher.R
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageInfo




class RAdapter(c: Context) : RecyclerView.Adapter<RAdapter.ViewHolder>(){

    var appList: MutableList<AppInfo>

    inner class ViewHolder//This is the subclass ViewHolder which simply
    //'holds the views' for us to show on each row
    (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        var textView: TextView
        var img: ImageView


        init {
            //Finds the views from our row.xml
            textView = itemView.findViewById(R.id.textIcon)
            img = itemView.findViewById(R.id.imgIcon) as ImageView
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            val pos = adapterPosition
            val context = v.getContext()

            val launchIntent = context.getPackageManager().getLaunchIntentForPackage(appList[pos].packageName!!.toString())
            context.startActivity(launchIntent)
            Toast.makeText(v.getContext(), appList[pos].label!!.toString(), Toast.LENGTH_LONG).show()
        }

        override fun onLongClick(view: View): Boolean {
            val pos = adapterPosition
            val context = view.getContext()
            val launchIntent = Intent(context.applicationContext, MainActivity::class.java)

            launchIntent.putExtra("appName", appList[pos].packageName!!.toString())
            context.startActivity(launchIntent)
            return true
        }
    }

    init {

        appList = ArrayList()
        /*
        //This is where we build our list of app details, using the app
        //object we created to store the label, package name and icon

        val pm = c.getPackageManager()
        appList = ArrayList()

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val allApps = pm.queryIntentActivities(i, 0)
        val allAppsAlpha = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        for (ri in allAppsAlpha) {
            if(pm.getLaunchIntentForPackage(ri.packageName) != null){
                //If you're here, then this is a launch-able app
                val app = AppInfo()
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
        })*/
    }
    fun addApps(a: MutableList<AppInfo>) {
        appList = a
    }
    override fun onBindViewHolder(viewHolder: RAdapter.ViewHolder, i: Int) {

        //Here we use the information in the list we created to define the views

        val appLabel = appList[i].label!!.toString()
        val appPackage = appList[i].packageName!!.toString()
        val appIcon = appList[i].icon

        val textView = viewHolder.textView
        textView.text = appLabel
        val imageView = viewHolder.img
        imageView.setImageDrawable(appIcon)
    }


    override fun getItemCount(): Int {

        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return appList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RAdapter.ViewHolder {

        //This is what adds the code we've written in here to our target view
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.row, parent, false)

        return ViewHolder(view)
    }


}