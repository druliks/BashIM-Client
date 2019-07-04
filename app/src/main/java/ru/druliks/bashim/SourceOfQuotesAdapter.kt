package ru.druliks.bashim

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.source_item.view.*
import ru.druliks.bashim.data.SourceOfQuotes

class SourceOfQuotesAdapter(list: MutableList<SourceOfQuotes>):RecyclerView.Adapter<SourceOfQuotesAdapter.ViewHolder>(){

    private val mItems:MutableList<SourceOfQuotes> = list
    private val mListeners:MutableList<ChangeSourceListener> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view=layoutInflater.inflate(R.layout.source_item,parent,false)
        return ViewHolder(view).listen{postion,type->
            changeSource(postion)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=mItems[position]
        holder.title.text=item.desc
    }

    fun addListener(listener:ChangeSourceListener){
        mListeners.add(listener)
    }

    fun changeSource(int:Int){
        mListeners.forEach{it.sourceChanged(int)}
    }

    fun <T:RecyclerView.ViewHolder> T.listen(event:(position:Int, type:Int)->Unit):T{
        itemView.setOnClickListener{
            event.invoke(adapterPosition,getItemViewType())
        }
        return this
    }

    operator fun get(position: Int): SourceOfQuotes {
        return mItems[position]
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title=view.text!!
    }
}