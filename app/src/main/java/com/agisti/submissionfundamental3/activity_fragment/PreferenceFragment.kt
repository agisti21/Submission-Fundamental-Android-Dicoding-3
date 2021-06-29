package com.agisti.submissionfundamental3.activity_fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.agisti.submissionfundamental3.R
import com.agisti.submissionfundamental3.alarm.AlarmReminder

class PreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var SET: String
    private lateinit var switchPreference: SwitchPreference
    private lateinit var alarmReminder: AlarmReminder


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init(){
        SET = resources.getString(R.string.set)

        switchPreference = findPreference<SwitchPreference>(SET) as SwitchPreference

    }
    private fun setSummaries(){
        val sh = preferenceManager.sharedPreferences

        switchPreference.isChecked = sh.getBoolean(SET, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        alarmReminder = AlarmReminder()

        if (key == SET) {
            if (switchPreference.isChecked){
                context?.let { alarmReminder.setRepeatingAlarm(it, AlarmReminder.TYPE_REPEATING, "09:00" ,resources.getString(R.string.message)) }
            }else{
                context?.let { alarmReminder.cancelAlarm(it, AlarmReminder.TYPE_REPEATING) }
            }
        }
    }
}