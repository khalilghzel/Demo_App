package com.wbconcept.myapplication.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wbconcept.myapplication.R
import kotlinx.android.synthetic.main.profile.*

class Profile_Details : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        back.setOnClickListener({
            supportFinishAfterTransition();

         })
    }

}