package com.example.android.myapplication.addnewgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.myapplication.R
import com.example.android.myapplication.database.BeeDatabase
import com.example.android.myapplication.databinding.FragmentAddNewGroupBinding

class AddNewGroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentAddNewGroupBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_new_group, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = AddNewGroupFragmentArgs.fromBundle(requireArguments())

        val dataSource = BeeDatabase.getInstance(application).beeDatabaseDao
        val viewModelFactory = AddNewGroupViewModelFactory(dataSource,arguments.groupKey)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.setLifecycleOwner(this)

        val addNewGroupViewModel = ViewModelProvider(this, viewModelFactory).get(AddNewGroupViewModel::class.java)

        binding.addNewGroupViewModel = addNewGroupViewModel

        addNewGroupViewModel.clickDoneButton.observe(this, Observer {
            if (it!=null) {
                        if( binding.newGroupName.text.toString() != "" && binding.newGroupLocation.text.toString() != ""){
                            val groupNameLenght: Int = binding.newGroupName.text.toString().length
                            val groupLocationLenght: Int = binding.newGroupLocation.text.toString().length
                            if (groupNameLenght<12) {
                                if (groupLocationLenght<21) {
                                    if (it == 1) {
                                        addNewGroupViewModel.setvalue(
                                            binding.newGroupName.text.toString(),
                                            binding.newGroupLocation.text.toString()
                                        )
                                        this.findNavController()
                                            .navigate(AddNewGroupFragmentDirections.actionAddNewGroupFragmentToBeeGroupsFragment())
                                        addNewGroupViewModel.doneNavigatingToGroupsFragment()
                                    }
                                }
                                else{
                                    Toast.makeText(application,resources.getString(R.string.group_location_lenght_warrning),Toast.LENGTH_SHORT).show()
                                }
                            }
                            else{
                                Toast.makeText(application,resources.getString(R.string.group_name_lenght_warrning),Toast.LENGTH_SHORT).show()
                            }
                    }
                    else{
                        Toast.makeText(application,resources.getString(R.string.empty_field_warrning),Toast.LENGTH_SHORT).show()
                    }

            }
        })

        binding.newGroupName.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 11){
                binding.beegroupnamelayout.error = "No More!"
            }
            else if (text.length <= 11){
                binding.beegroupnamelayout.error = null
            }
        }

        binding.newGroupLocation.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20){
                binding.groupLocationLayout.error = "No More!"
            }
            else if(text.length <= 20){
                binding.groupLocationLayout.error = null
            }
        }

        return binding.root
    }
}