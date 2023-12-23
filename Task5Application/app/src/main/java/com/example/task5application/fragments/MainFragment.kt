package com.example.task5application.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.adapters.FilmAdapter
import com.example.task5application.adapters.LikedFilmsAdapter
import com.example.task5application.adapters.OnLikeClickListener
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.database.entity.LikeEntity
import com.example.task5application.databinding.FragmentMainBinding
import com.example.task5application.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragment:Fragment(R.layout.fragment_main), OnLikeClickListener {

    val binding:FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    var liked:ArrayList<FillmEntity> = ArrayList<FillmEntity>()
    var films:ArrayList<FillmEntity> = ArrayList()
    var userid:Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userid:Int = arguments?.getInt("USERID") ?: 0
        this.userid = userid
        Log.e("userid in main", userid.toString())
        lifecycleScope.launch {
            async {fetchDataLike(userid)}.await()
            async { fetchData()}.await()
            binding.filmsRw.adapter = FilmAdapter(this@MainFragment.requireContext(), films, this@MainFragment)
            binding.favoritsRw.adapter =
                LikedFilmsAdapter(this@MainFragment.requireContext(), liked, this@MainFragment)
            binding.imageButton.setOnClickListener {
                var fragment = NewFilmFragment()
                var bundle = Bundle()
                bundle.putInt("USERID", userid)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            binding.profileBtn.setOnClickListener {
                var fragment = ProfileFragment()
                var bundle = Bundle()
                bundle.putInt("USERID", userid)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    fun fetchDataLike(id:Int){
        lifecycleScope.launch(Dispatchers.IO){
            liked.addAll(Synchronizer.getDbInstance().filmDao.getLikedFilms(id))
        }
    }
    fun fetchData(){
        lifecycleScope.launch(Dispatchers.IO){
            films.addAll(Synchronizer.getDbInstance().filmDao.getAllFilms()?:ArrayList<FillmEntity>())
            films.removeAll(liked)
        }
    }
    override fun onLikeClickinfilm(item: FillmEntity) {
        // Обработка нажатия на кнопку лайка
        // Перемещение объекта из первого RecyclerView во второй
        (binding.filmsRw.adapter as FilmAdapter).items.remove(item)
        (binding.favoritsRw.adapter as LikedFilmsAdapter).items.add(item)
        (binding.filmsRw.adapter as FilmAdapter).notifyDataSetChanged()
        (binding.favoritsRw.adapter as LikedFilmsAdapter).notifyDataSetChanged()
        lifecycleScope.launch(Dispatchers.IO) {
            Synchronizer.getDbInstance().likeDao.createLike(LikeEntity(0, userid, item.id))
        }
    }

    override fun onLikeClickinLiked(item: FillmEntity) {
        (binding.filmsRw.adapter as FilmAdapter).items.add(item)
        (binding.favoritsRw.adapter as LikedFilmsAdapter).items.remove(item)
        (binding.filmsRw.adapter as FilmAdapter).notifyDataSetChanged()
        (binding.favoritsRw.adapter as LikedFilmsAdapter).notifyDataSetChanged()
        lifecycleScope.launch(Dispatchers.IO) {
            var like = async { Synchronizer.getDbInstance().likeDao.getLikeFILMANDUSER(userid, item.id) }.await()
            Synchronizer.getDbInstance().likeDao.deleteLike(like?.id ?: 0)
        }
    }

    override fun onfilmDelete(item: FillmEntity) {
        (binding.filmsRw.adapter as FilmAdapter).items.remove(item)
        (binding.filmsRw.adapter as FilmAdapter).notifyDataSetChanged()
        lifecycleScope.launch(Dispatchers.IO) {
            Synchronizer.getDbInstance().filmDao.deleteFilm(item.id)
        }
    }

    override fun onlikedDelete(item: FillmEntity) {
        (binding.favoritsRw.adapter as LikedFilmsAdapter).items.remove(item)
        (binding.favoritsRw.adapter as LikedFilmsAdapter).notifyDataSetChanged()
        lifecycleScope.launch(Dispatchers.IO) {
            async { Synchronizer.getDbInstance().likeDao.deleteallofFilm(item.id)}.await()
            Synchronizer.getDbInstance().filmDao.deleteFilm(item.id)
        }
    }
}