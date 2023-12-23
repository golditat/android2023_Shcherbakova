package com.example.task5application.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.databinding.FragmentNewfilmBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class NewFilmFragment:Fragment(R.layout.fragment_newfilm) {
    val binding:FragmentNewfilmBinding by viewBinding(FragmentNewfilmBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userid:Int? = arguments?.getInt("USERID")
        binding.run {
            saveBtn.setOnClickListener {
                var name = editTextText2.text.toString()
                var year = editTextText3.text.toString().toInt()
                var country = editTextText4.text.toString()
                var deskription = editTextText5.text.toString()
                var image = editTextText6.text.toString()
                if(name != null && year!= null && country!=null && deskription!=null && correctImage(image)){
                    lifecycleScope.launch(Dispatchers.IO){
                        Synchronizer.getDbInstance().filmDao.insertNewFilm(FillmEntity(
                            0, image, name, year, country, 5.0
                        ))
                    }
                    val fragment = MainFragment()
                    var bundle:Bundle = Bundle()
                    bundle.putInt("USERID", userid?:0)
                    fragment.arguments=bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }else{
                    textView2.text = "not correct"
                }
            }
            backBtn.setOnClickListener {
                val fragment = MainFragment()
                var bundle = Bundle()
                bundle.putInt("USERID", userid?:0)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    fun correctImage(src:String):Boolean{
        val imageUrlPattern = "\\bhttps?://\\S+?\\.(png|jpe?g|gif|bmp)\\b"
        val pattern = Pattern.compile(imageUrlPattern, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(src)
        return matcher.matches()
    }
}