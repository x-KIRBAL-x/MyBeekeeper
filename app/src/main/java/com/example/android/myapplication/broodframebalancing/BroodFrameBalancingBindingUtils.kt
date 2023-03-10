package com.example.android.myapplication.broodframebalancing

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.myapplication.R
import com.example.android.myapplication.convertNumericQuantityToString
import com.example.android.myapplication.database.Beehive

@BindingAdapter("BroodFrameBalanceName")
fun TextView.setBeeName(item: Beehive?){
    item?.let {
        text = "Beehive name: " +  item.beehiveName
    }
}

@BindingAdapter("BeehiveBroodFrameQuantity")
fun TextView.setBroodFrameQuantity(item: Beehive?){
    item?.let {
        text = "Broodframe quantity: " + convertNumericQuantityToString(item.broodFrame,context.resources)
    }
}

@BindingAdapter("BalanceBroodFrameNumber")
fun TextView.setBroodFrameNumber(item: Beehive?){
    item?.let {
        text = "Broodframe number: " + item.broodFrameNumber.toString()
    }
}

@BindingAdapter("BroodFrameBalanceImage")
fun ImageView.setBeeImage(item: Beehive?){
    item?.let {
        setImageResource(R.drawable.hive)
    }
}