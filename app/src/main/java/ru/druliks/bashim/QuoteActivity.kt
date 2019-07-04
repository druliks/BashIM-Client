package ru.druliks.bashim

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
import ru.druliks.bashim.data.Quote
import ru.druliks.bashim.data.SearchRepository
import ru.druliks.bashim.data.SearchRepositoryProvider

const val INTENT_SITE_NAME="site"
const val INTENT_NAME="name"

class QuoteActivity : AppCompatActivity() {

    @BindView(R.id.list)
    lateinit var listView: RecyclerView

    private val list: MutableList<Quote> = mutableListOf()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val repository: SearchRepository =
        SearchRepositoryProvider.provideSearchRepository()

    lateinit var adapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        val lim= LinearLayoutManager(this)
        lim.orientation= LinearLayoutManager.VERTICAL
        listView.layoutManager=lim
        val site=intent.getStringExtra(INTENT_SITE_NAME)
        val name=intent.getStringExtra(INTENT_NAME)
        compositeDisposable.add(
            repository.searchQuetes(site,name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { result->
                    list.addAll(result)
                    listView.adapter= QuotesAdapter(list)
                    Log.d(tag,result.toString())
                }
        )
    }
}
