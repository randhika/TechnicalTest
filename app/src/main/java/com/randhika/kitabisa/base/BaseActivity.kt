package com.randhika.kitabisa.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(),//NOSONAR
        HasSupportFragmentInjector {

    protected val NO_VIEW_MODEL_BINDING_VARIABLE = -1

    /**
     * The method returns the DataBinding instance of the Activity
     *
     * @return T generic type of DataBinding
     */
    var dataBinding: T? = null
        private set

    /**
     * The method returns ViewModel instance of the Activity
     *
     * @return V generic type of ViewModel
     */
    var viewModel: V? = null
        private set

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mFragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    /**
     * Override to set view model binding variable
     * return {@value #NO_VIEW_MODEL_BINDING_VARIABLE} in case no need to bind ViewModel into
     * DataBinding, which mean there is no ViewModel variable declaration on Activity layout.
     *
     * @return variable id, generated on BR(Binding Resource) class, e.g : BR.viewModel
     */
    abstract val viewModelBindingVariable: Int

    /**
     * Override to set Activity layout resource id
     *
     * @return layout resource id, generated on R(Resource) class, e.g : R.layout.main_menu_activity
     */
    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (this is ExtraInjectable && intent.extras != null) {
            (this as ExtraInjectable).injectExtras(intent.extras)
        }

        provideViewModel()
        performDataBinding()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return mFragmentDispatchingAndroidInjector
    }

    private fun performDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        if (viewModelBindingVariable != NO_VIEW_MODEL_BINDING_VARIABLE) {
            setViewModelBindingVariable()
        }
    }

    private fun setViewModelBindingVariable() {
        dataBinding?.setVariable(viewModelBindingVariable, viewModel)
        dataBinding?.executePendingBindings()
    }

    private fun provideViewModel() {
        val clazz = getViewModelClass(javaClass)
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(clazz)
    }

    private fun getViewModelClass(aClass: Class<*>): Class<V> {
        val type = aClass.genericSuperclass

        return if (type is ParameterizedType) {

            type.actualTypeArguments[1] as Class<V>
        } else {
            getViewModelClass(aClass.superclass)
        }
    }
}