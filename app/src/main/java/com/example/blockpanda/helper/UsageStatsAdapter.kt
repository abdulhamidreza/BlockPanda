package com.example.blockpanda.helper;

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blockpanda.R
import com.suke.widget.SwitchButton
import java.text.DateFormat


class UsageStatsAdapter(private val mList: List<AppDetails>) : RecyclerView.Adapter<UsageStatsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.usage_stats_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]
        holder.imageView.setImageDrawable(item.drawable)
        holder.textView.text = item.usagesDb.appName

        holder.textViewDay.text = DateUtils.formatSameDayTime(
            item.pkgUsages.lastTimeUsed,
            System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM
        )
        holder.textViewWk.setText(
            DateUtils.formatElapsedTime(item.pkgUsages.totalTimeInForeground / 1000)
        );

        holder.textViewMn.text = DateUtils.formatSameDayTime(
            item.pkgUsages.firstTimeStamp,
            System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM
        )

        holder.remoteButtonView.setEnableEffect(true)
        holder.remoteButtonView.isChecked = item.usagesDb.isLock
        holder.remoteButtonView.setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                Log.d("*********** true", holder.textView.text.toString())
            } else {
                Log.d("*********** false", holder.textView.text.toString())
            }
        })
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val textView: TextView = itemView.findViewById(R.id.appNameTextView)
        val textViewDay: TextView = itemView.findViewById(R.id.textView)
        val textViewWk: TextView = itemView.findViewById(R.id.textView2)
        val textViewMn: TextView = itemView.findViewById(R.id.textView3)
        val remoteButtonView: com.suke.widget.SwitchButton = itemView.findViewById(R.id.remoteButtonView)
    }
}

