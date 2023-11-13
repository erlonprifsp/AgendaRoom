package br.ifsp.agendaroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.agendaroom.data.Contato
import br.ifsp.agendaroom.databinding.ContatoCelulaBinding

class ContatoAdapter(val contatosLista:ArrayList<Contato>): RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>(),
    Filterable {

    var listener: ContatoListener?=null

    var contatosListaFilterable = ArrayList<Contato>()

    private lateinit var binding: ContatoCelulaBinding

    init {
        this.contatosListaFilterable = contatosLista
    }

    fun setClickListener(listener: ContatoListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContatoViewHolder {

        binding = ContatoCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  ContatoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.nomeVH.text = contatosListaFilterable[position].nome
        holder.foneVH.text = contatosListaFilterable[position].fone
    }

    override fun getItemCount(): Int {
        return contatosListaFilterable.size
    }

    inner class ContatoViewHolder(view: ContatoCelulaBinding): RecyclerView.ViewHolder(view.root)
    {
        val nomeVH = view.nome
        val foneVH = view.fone

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ContatoListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    contatosListaFilterable = contatosLista
                else
                {
                    val resultList = ArrayList<Contato>()
                    for (row in contatosLista)
                        if (row.nome.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    contatosListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contatosListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                contatosListaFilterable = p1?.values as ArrayList<Contato>
                notifyDataSetChanged()
            }

        }
    }

}