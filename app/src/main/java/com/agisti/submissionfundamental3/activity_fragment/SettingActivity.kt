package com.agisti.submissionfundamental3.activity_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agisti.submissionfundamental3.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = "Setting"

        supportFragmentManager.beginTransaction().add(R.id.setting, PreferenceFragment()).commit()

    }
}
