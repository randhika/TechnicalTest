package com.randhika.kitabisa.feature

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.randhika.kitabisa.core.SingleLiveEvent
import com.randhika.kitabisa.model.FormSubmit
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    private var mType: Int = 0

    private lateinit var mResult: String

    private var mShowFormYLiveData = MutableLiveData<Boolean>()

    private val mOutputClicked = SingleLiveEvent<FormSubmit>()

    fun showFormY(type: Int) {
        mType = type
        when(type) {
            0 -> mShowFormYLiveData.value = true
            1 -> mShowFormYLiveData.value = true
            2 -> mShowFormYLiveData.value = false
            3 -> mShowFormYLiveData.value = false
        }
    }

    fun getFormY(): LiveData<Boolean> {
        return mShowFormYLiveData
    }

    fun getOutputClicked(): SingleLiveEvent<FormSubmit> {
        return mOutputClicked
    }

    fun outPutClicked(formX: String, formY: String) {
        when(mType) {
            0 -> sum(formX, formY)
            1 -> multiply(formX, formY)
            2 -> prime(formX)
            3 -> fibonacci(formX)
        }

        val form = FormSubmit(
            mType, formX, formY, mResult
        )

        mOutputClicked.value = form
    }

    private fun sum(formX: String, formY: String) {
        val x = formX.toBigDecimal()
        val y = formY.toBigDecimal()

        mResult = x.add(y).toString()
    }

    private fun multiply(formX: String, formY: String) {
        val x = formX.toBigDecimal()
        val y = formY.toBigDecimal()

        mResult = x.multiply(y).toString()
    }

    private fun  prime(formX: String) {
        val x = formX.toInt()
        val result = StringBuilder()
        var count = 0
        val number = Integer.MAX_VALUE
        for (i in 1..number) {
            var counter = 0
            for (num in i downTo 1) {
                if (i % num == 0) {
                    counter++
                }
            }

            if (counter == 2) {
                result.append(i).append(" ")
                count++
            }

            if (count == x) {
                break
            }
        }

        mResult = result.toString()
    }

    private fun fibonacci(formX: String) {
        val x = formX.toInt()
        val result = StringBuilder()
        var one = 0
        var two = 1
        for (i in 0 until x) {
            result.append(one).append(" ")
            val temp = one + two
            one = two
            two = temp
        }

        mResult = result.toString()
    }
}