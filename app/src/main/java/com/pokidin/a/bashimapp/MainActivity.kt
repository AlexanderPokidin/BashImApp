package com.pokidin.a.bashimapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.pokidin.a.bashimapp.data.SearchRepository
import com.pokidin.a.bashimapp.data.SearchRepositoryProvider
import com.pokidin.a.bashimapp.data.SourceOfQuotes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val tag = "MainActivity"

class MainActivity : AppCompatActivity(), ChangeSourceListener {
    override fun sourceChanged(position: Int) {
        Log.d(tag, "from main = ${adapter[position]}")
    }

    @BindView(R.id.list)
    lateinit var listView: RecyclerView
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepository()
    lateinit var adapter: SourceOfQuotesAdapter

    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm

        compositeDisposable.add(
                repository.searchSourceOfQuotes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            result.forEach { list.addAll(it) }
                            listView.adapter = SourceOfQuotesAdapter(list)
                            Log.d(tag, list.toString())
                        })
        )
    }
}
