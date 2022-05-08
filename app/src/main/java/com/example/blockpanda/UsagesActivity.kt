package com.example.blockpanda

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blockpanda.helper.*
import com.example.blockpanda.room.Usages
import com.example.blockpanda.room.UsagesViewModel
import java.util.*


class UsagesActivity : AppCompatActivity() {
    private val TAG = "UsageStatsActivity************"
    private val localLOGV = false
    private var mUsageStatsManager: UsageStatsManager? = null
    private var mInflater: LayoutInflater? = null
    private var mPm: PackageManager? = null

    // Constants defining order for display order
    private val _DISPLAY_ORDER_USAGE_TIME = 0
    private val _DISPLAY_ORDER_LAST_TIME_USED = 1

    private var mDisplayOrder = _DISPLAY_ORDER_USAGE_TIME
    private val mLastTimeUsedComparator = LastTimeUsedComparator()
    private val mUsageTimeComparator = UsageTimeComparator()
    private val mAppLabelMap = ArrayMap<String, String>()
    private val mPackageStats = ArrayList<UsageStats>()
    var contacts: ArrayList<AppDetails>? = null

    private val usageViewModel: UsagesViewModel by viewModels {
        UsagesViewModel.UsagesViewModelFactory((application as MyApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_stats)


        // check for usage access permission
        if (!CheckUsageStatsPermission.hasUsageStatsPermission(this)) {
            PromptAlertUserDialogs.showDialogUsageAccessRequired(this)
        }
        //Todo on start showAutoStartPermissionScreen()

        mUsageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        mInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mPm = packageManager

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.rvContacts)
        recyclerview.layoutManager = LinearLayoutManager(this)
        contacts = getAppDetails(usageStateList())
        val adapter = UsageStatsAdapter(contacts!!)
        recyclerview.adapter = adapter


        //Db Call
        var flagDbOnce = true
        usageViewModel.insertNewApp( usageStateList(), mPm)


        usageViewModel.getAllUserList.observe(this) { appList ->
            run {
                if (flagDbOnce) {
                    flagDbOnce = false

                }
            }

        }

    }


    fun usageStateList(): ArrayList<UsageStats> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -5)
        val stats = mUsageStatsManager!!.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST,
            cal.timeInMillis, System.currentTimeMillis()
        )
        if (stats == null) {
            Log.e(TAG, "stats is null")
            mPackageStats
        }
        val map = ArrayMap<String, UsageStats>()
        val statCount = stats.size
        Log.e(TAG, "stats size $statCount")
        for (i in 0 until statCount) {
            val pkgStats = stats[i]
            if (!AndroidPackageManagerWrappers.isSystemApp(applicationContext, pkgStats.packageName)) {
                // load application labels for each application
                try {
                    val appInfo = mPm!!.getApplicationInfo(pkgStats.packageName, 0)
                    val label = appInfo.loadLabel(mPm!!).toString()
                    mAppLabelMap[pkgStats.packageName] = label
                    val existingStats = map[pkgStats.packageName]
                    if (existingStats == null) {
                        map[pkgStats.packageName] = pkgStats
                    } else {
                        existingStats.add(pkgStats)
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    // This package may be gone.
                }
            }
        }
        mPackageStats.addAll(map.values)
        // Sort list
        sortList(_DISPLAY_ORDER_USAGE_TIME)
        return mPackageStats
    }

    class LastTimeUsedComparator : Comparator<UsageStats> {
        override fun compare(a: UsageStats, b: UsageStats): Int {
            // return by descending order
            return (b.lastTimeUsed - a.lastTimeUsed).toInt()
        }
    }

    class UsageTimeComparator : Comparator<UsageStats> {
        override fun compare(a: UsageStats, b: UsageStats): Int {
            return (b.totalTimeInForeground - a.totalTimeInForeground).toInt()
        }
    }


    private fun sortList(mDisplayOrder: Int) {
        if (mDisplayOrder == this._DISPLAY_ORDER_USAGE_TIME) {
            if (localLOGV) Log.i(TAG, "Sorting by usage time")
            Collections.sort(mPackageStats, mUsageTimeComparator)
        } else if (mDisplayOrder == _DISPLAY_ORDER_LAST_TIME_USED) {
            if (localLOGV) Log.i(TAG, "Sorting by last time used")
            Collections.sort(mPackageStats, mLastTimeUsedComparator)
        }
    }


    fun getAppDetails(apps: ArrayList<UsageStats>): ArrayList<AppDetails> {
        var appList = arrayListOf<AppDetails>()
        for (i in apps) {
            val s = AndroidPackageManagerWrappers.getAppIcons(baseContext)
            val icon: Drawable = s.get(i.packageName)!!
            appList.add(
                AppDetails(
                    icon, Usages(
                        "", "", false, false, false, false, false,
                        "", "", "", "", false, "", "", false
                    ), i
                )
            )
        }
        return appList
    }

}

