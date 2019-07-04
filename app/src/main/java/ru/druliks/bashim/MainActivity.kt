package ru.druliks.bashim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.druliks.bashim.data.*

const val tag:String="Main Activity"

class MainActivity : AppCompatActivity(),ChangeSourceListener {
    override fun sourceChanged(position: Int) {
        Log.d(tag,"from main = ")
        val intent= Intent(applicationContext, QuoteActivity::class.java)
        intent.putExtra(INTENT_NAME,adapter[position].name)
        intent.putExtra(INTENT_SITE_NAME,adapter[position].site)
        startActivity(intent)
    }

    @BindView(R.id.list)
    lateinit var listView:RecyclerView

    private val list: MutableList<SourceOfQuotes> = mutableListOf()
    val compositeDisposable:CompositeDisposable = CompositeDisposable()
    val repository:SearchRepository = SearchRepositoryProvider.provideSearchRepository()

    lateinit var adapter: SourceOfQuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        val lim=LinearLayoutManager(this)
        lim.orientation=LinearLayoutManager.VERTICAL
        listView.layoutManager=lim
        compositeDisposable.add(
            repository.searchSources()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { result->
                    result.forEach{list.addAll(it)}
                    adapter= SourceOfQuotesAdapter(list)
                    adapter.addListener(this)
                    listView.adapter=adapter
                    Log.d(tag,result.toString())
                }
        )
}
}
