package com.sudansh.deliveries.ui

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MapRenderer<T : ClusterItem>(mContext: Context, map: GoogleMap?, mClusterManager: ClusterManager<T>?) :
        DefaultClusterRenderer<T>(mContext, map, mClusterManager) {

    override fun shouldRenderAsCluster(cluster: Cluster<T>?): Boolean {
        return false//cluster?.size ?: 0 > 5
    }
}