package com.randhika.kitabisa.feature

import android.arch.lifecycle.Observer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.randhika.kitabisa.BR
import com.randhika.kitabisa.R
import com.randhika.kitabisa.base.KitaBisaBaseActivity
import com.randhika.kitabisa.databinding.ActivityMainBinding
import com.randhika.kitabisa.model.FormSubmit

class MainActivity : KitaBisaBaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        var type = arrayOf("Sum", "Multiply", "Prime Number", "Fibonaci")
    }

    override val viewModelBindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setUpView() {
        val spinnerArrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, type)
        spinnerArrayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item) // The drop down view
        dataBinding?.type?.adapter = spinnerArrayAdapter

        dataBinding?.type?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                dataBinding?.reesult?.text = ""
                viewModel?.showFormY(position)
            }
        }

        dataBinding?.submit?.setOnClickListener {
            val formX = dataBinding?.form1?.text.toString()
            val formY = dataBinding?.form2?.text.toString()
            viewModel?.outPutClicked(formX, formY)
        }

        viewModel?.getFormY()?.observe(this, Observer {show ->
            dataBinding?.form2?.apply {
                show?.let {
                    visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel?.getOutputClicked()?.observe(this, Observer {type ->
            type?.let {
                if (validateInput(it)){
                    dataBinding?.reesult?.text = it.result
                } else {
                    Toast.makeText(this, "Mohon Isi Data", Toast.LENGTH_SHORT).show()
                    dataBinding?.submit?.startAnimation(
                        AnimationUtils.loadAnimation(this, R.anim.shake_error))
                }
            }
        })
    }

    private fun validateInput(form: FormSubmit): Boolean {
        when (form.type) {
            0,1 -> if (form.formX.isEmpty() || form.formY.isEmpty()) {
                return false
            }
            2,3 -> if (form.formX.isEmpty()) {
                return false
            }
        }
        return true
    }
}
