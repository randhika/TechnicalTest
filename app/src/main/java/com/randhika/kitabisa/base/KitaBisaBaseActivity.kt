package com.randhika.kitabisa.base

import android.app.ActivityManager
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem

abstract class KitaBisaBaseActivity<T : ViewDataBinding, V : ViewModel> : BaseActivity<T, V>() {

    var isActivityInBackground: Boolean = false
        private set
    var isActivityStopped: Boolean = false
        private set

    protected abstract fun setUpView()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setUpView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        isActivityInBackground = true
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        isActivityStopped = false
        super.onStart()
    }

    override fun onStop() {
        isActivityInBackground = true
        isActivityStopped = true
        super.onStop()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        isActivityInBackground = false
    }

    companion object {

        protected val LOADING_DIALOG_TAG = "loading"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    protected fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}