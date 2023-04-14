package com.aisc.ngalo

import com.google.android.gms.maps.model.LatLng

/**
 * Location Object used to know pickup and destination location
 */
class LocationObject {
    var coordinates: LatLng? = null
    var name = ""

    /**
     * LocationObject constructor
     * @param coordinates - latLng of the location
     * @param name - name of the location
     */
    constructor(coordinates: LatLng?, name: String) {
        this.coordinates = coordinates
        this.name = name
    }

    /**
     * LocationObject constructor
     * Creates an empty object
     */
    constructor() {}
}