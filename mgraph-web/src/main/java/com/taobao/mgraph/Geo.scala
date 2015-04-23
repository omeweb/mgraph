package com.taobao.mgraph

import ch.hsr.geohash._
import ch.hsr.geohash.util._

object Geo {
    def getDistance(lat1: Double, longt1: Double, lat2: Double, longt2: Double): Double = {
        val PI = 3.14159265358979323; // 圆周率
        val R = 6371229; // 地球的半径

        var x: Double = 0
        var y: Double = 0
        var distance: Double = 0;

        x = (longt2 - longt1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);

        distance;
    }

    def main(args: Array[String]) {
        //Geohash，如果geohash的位数是6位数的时候，大概为附近1千米…
        var hash = GeoHash.withCharacterPrecision(39.9232, 116.3906, 8); //后面的精度不能太小
        println(hash.toString())
        println(hash.toBase32())

        //临近的8个点
        for (item <- hash.getAdjacent) {
            println(item.toBase32())
        }

        val p1 = hash.getBoundingBoxCenterPoint;
        var p2 = hash.getAdjacent.last.getBoundingBoxCenterPoint

        //2个点之间的距离
        println(VincentyGeodesy.distanceInMeters(p1, p2))

        println(getDistance(p1.getLatitude, p1.getLongitude, p2.getLatitude, p2.getLongitude))

        println(getDistance(39.9905060000, 116.3165520000, 40.0585830000, 116.6221280000))
    }
}