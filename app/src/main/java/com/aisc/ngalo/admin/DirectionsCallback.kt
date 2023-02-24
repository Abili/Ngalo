package com.aisc.ngalo.admin

import com.akexorcist.googledirection.model.Direction


/**
 * Interface for the response from the direction request of the Google Direction API.
 *
 * @since 1.0.0
 */
interface DirectionsCallback {
    /**
     * Retrieve the response from direction request successfully.
     *
     * @param direction The direction result from the Google Direction API
     * @since 1.0.0
     */
    fun onDirectionSuccess(direction: Direction?)

    /**
     * Retrieve the response from direction request with error result.
     *
     * @param t A throwable from the response of Google Direction API.
     * @since 1.0.0
     */
    fun onDirectionFailure(t: Throwable)
}