package com.hector.mylibrary

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hector.mylibrary.interfaces.IToolbar

open class ToolbarActivity : AppCompatActivity(), IToolbar{

    protected var _toolbar: Toolbar? = null

    override fun toolbarToLoad(toolbar: Toolbar?) {
        _toolbar = toolbar
        _toolbar?.let { setSupportActionBar(_toolbar)}
    }

    override fun enableHomeDisplay(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

}