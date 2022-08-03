package ai.ftech.babyphoto.fragment

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.adapter.RelationAdapter
import ai.ftech.babyphoto.model.Relation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogRelationFragment : BottomSheetDialogFragment() {
    private var rvRelation: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? =
            layoutInflater.inflate(R.layout.dialog_relation_fragment, container, false)
        rvRelation = view!!.findViewById(R.id.rvDialogRelationRelation)
        setRecyclerviewRelation()
        return view
    }

    private fun setRecyclerviewRelation() {
        rvRelation!!.layoutManager = LinearLayoutManager(context)
        var arrayRelation: MutableList<Relation> = ArrayList()
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Father"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Mother"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Brother"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Sister"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Aunt"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Uncle"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Grandpa"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Grandma"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Nanny"))
        arrayRelation.add(Relation(R.drawable.ic_checkbox_24px, "Family Friend"))

        val adapter = RelationAdapter(arrayRelation)
        rvRelation?.adapter = adapter


    }

}