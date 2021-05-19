package modularization.features.dashboard.interfaces

interface OnNavigate {

    /*val settingsPage: Int
    val joinPage: Int
    val meetingPage: Int
    val resultPage: Int*/

    /**
     * joinPage     1
     * settingsPage 2
     * meetingPage  3
     * resultPage   4
     */
    fun onNavigated(page: Int)
}