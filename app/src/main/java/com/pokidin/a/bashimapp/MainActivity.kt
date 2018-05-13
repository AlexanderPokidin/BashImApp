package com.pokidin.a.bashimapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pokidin.a.bashimapp.data.SearchRepository
import com.pokidin.a.bashimapp.data.SearchRepositoryProvider
import com.pokidin.a.bashimapp.data.SourceOfQuotes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val tag = "MainActivity"

class MainActivity : AppCompatActivity() {

    val compositeDisposable:CompositeDisposable = CompositeDisposable()
    val repository:SearchRepository = SearchRepositoryProvider.provideSearchRepository()

    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable.add(
            repository.searchSourceOfQuotes()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result -> result.forEach { list.addAll(it) }
                    Log.d(tag, list.toString())
                    })
        )
    }
}
