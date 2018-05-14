package com.pokidin.a.bashimapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import com.pokidin.a.bashimapp.data.SourceOfQuotes
import kotlinx.android.synthetic.main.source_item.view.*

class SourceOfQuotesAdapter(list: MutableList<SourceOfQuotes>) : RecyclerView.Adapter<SourceOfQuotesAdapter.ViewHolder>() {

    private val mListeners: MutableList<ChangeSourceListener> = mutableListOf()
    private val mItems: MutableList<SourceOfQuotes> = list

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.textView.text = item.desc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.source_item, parent, false)
        return ViewHolder(view).listen { position, type ->
            changeSource(position)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.text!!
    }

    fun addListener(listener: ChangeSourceListener){
        mListeners.add(listener)
    }

    fun changeSource(position: Int){
        mListeners.forEach { it.sourceChanged(position) }
    }

    fun <T: RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T{
        itemView.setOnClickListener {
            event.invoke(adapterPosition, getItemViewType())
        }
        return this
    }

    operator fun get(position: Int):SourceOfQuotes{
        return mItems[position]
    }
}