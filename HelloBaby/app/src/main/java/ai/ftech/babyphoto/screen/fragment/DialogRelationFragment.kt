package ai.ftech.babyphoto.screen.fragment

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.screen.adapter.RelationAdapter
import ai.ftech.babyphoto.model.IRelation
import ai.ftech.babyphoto.model.Relation
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DialogRelationFragment : BottomSheetDialogFragment() {
    private lateinit var rvRelation: RecyclerView
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_relation_fragment, null)
        bottomSheetDialog?.setContentView(view)
        rvRelation = view.findViewById(R.id.rvDialogRelationRelation)
        setRecyclerviewRelation()
        return bottomSheetDialog!!
    }

    private fun setRecyclerviewRelation() {
        rvRelation!!.layoutManager = LinearLayoutManager(context)
        var arrayRelation: MutableList<Relation> = arrayListOf(
            Relation("Father"),
            Relation("Mother"),
            Relation("Brother"),
            Relation("Sister"),
            Relation("Aunt"),
            Relation("Uncle"),
            Relation("Grandpa"),
            Relation("Grandma"),
            Relation("Nanny"),
            Relation("Family Friend")
        )
        val iRelation: IRelation = object : IRelation {
            override fun getName(name: String) {

            }
        }
        val adapter = RelationAdapter(arrayRelation, iRelation)
        rvRelation.adapter = adapter
    }



}