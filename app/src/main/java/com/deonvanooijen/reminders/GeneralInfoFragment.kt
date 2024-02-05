package com.deonvanooijen.reminders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.deonvanooijen.reminders.databinding.DialogEditReminderBinding
import com.deonvanooijen.reminders.databinding.FragmentGeneralInfoBinding
import com.deonvanooijen.reminders.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GeneralInfoFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding
    private val preferences by lazy {
        requireActivity().getSharedPreferences(
            "general_info",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        binding.cardViewBinDay.setOnClickListener { showEditDialog(PREF_BIN_DAY) }
        binding.cardViewInsuranceNo.setOnClickListener { showEditDialog(PREF_INSURANCE_NO) }
    }

    private fun displayValues() {
        binding.textViewBinDayValue.text = preferences.getString(PREF_BIN_DAY, null)
        binding.textViewInsuranceValue.text = preferences.getString(PREF_INSURANCE_NO, null)
    }

    private fun showEditDialog(preferenceKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editTextValue.setText(preferences.getString(preferenceKey, null))
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit {
                    putString(
                        preferenceKey,
                        dialogBinding.editTextValue.text?.toString()
                    )
                }
                displayValues()
            }
            .setNegativeButton("Cancel") { _, _ ->

            }.show()
    }

    companion object {
        const val PREF_BIN_DAY = "pref_bin_day"
        const val PREF_INSURANCE_NO = "pref_insurance_no"
    }

}