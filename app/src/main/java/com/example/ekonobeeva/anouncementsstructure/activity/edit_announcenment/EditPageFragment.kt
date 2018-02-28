package com.example.ekonobeeva.anouncementsstructure.activity.edit_announcenment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import android.widget.Toast
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.R.id.create_page
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.activity.disalogs.AddTextItemFragment
import com.example.ekonobeeva.anouncementsstructure.activity.disalogs.EditImageItemFragment
import com.example.ekonobeeva.anouncementsstructure.activity.disalogs.EditTextField
import com.example.ekonobeeva.anouncementsstructure.structure.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.TouchCallback
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.edit_fragment_page.*
import kotlinx.android.synthetic.main.header_save_back_press.*

class EditPageFragment : Fragment() {
    val TAG = "EditPageFragment"

    companion object {
        const val PAGE_INDEX_KEY = "PAGE_INDEX_KEY"
        const val NEW_PAGE_INDEX = -1

        fun newInstance(pageIndex: Int): EditPageFragment {
            return EditPageFragment().apply { arguments = Bundle().apply { putInt(PAGE_INDEX_KEY, pageIndex) } }
        }
    }

    private var copiedAnnouncePage: AnnouncePage = AnnouncePage(mutableListOf(), AlignType.TOP)
    private lateinit var adapter: GroupAdapter<ViewHolder>
    private var pageIndex: Int = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ");
        arguments?.getInt(PAGE_INDEX_KEY)?.let { pageIndex = it }
        return inflater.inflate(R.layout.edit_fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ");

        adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener(onItemClickListener)

        page_fragment_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        page_fragment_recycler.adapter = adapter

        ItemTouchHelper(touchCallback).attachToRecyclerView(page_fragment_recycler)

        initHeaderLayout()

        initFabSpeedDial()

        initAnnouncePage()

    }

    override fun onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume()
    }

    private fun initHeaderLayout(){
        Log.d(TAG, "initHeaderLayout: ");
        header.text = "Изменить страницу"

        back_press.setOnClickListener {
            Log.d(TAG, "initHeaderLayout: back_press");
            activity?.onBackPressed()
        }

        save.setOnClickListener {
            saveChanges()
            Log.d(TAG, "initHeaderLayout: save ");
            activity?.onBackPressed()
        }
    }

    private fun changeLayoutBias(float: Float){
        ConstraintSet().apply {
            clone(context, R.layout.edit_fragment_page)
            setVerticalBias(R.id.page_fragment_recycler, float)
            applyTo(page_fragment_id as ConstraintLayout)
        }

        changeAlignCenterMenuVisibility(copiedAnnouncePage.alignType != AlignType.CENTER)
    }

    private fun initAnnouncePage(){
        // берем из интерактора модель меням ее
        // сохраняем в бд

        if(pageIndex > NEW_PAGE_INDEX){
            copiedAnnouncePage = AnnounceBuffer.getModel().announcePages[pageIndex]
        }else{
            AnnounceBuffer.getModel().announcePages.add(copiedAnnouncePage)
            pageIndex = AnnounceBuffer.content.announcePages.size -1
            arguments?.putInt(PAGE_INDEX_KEY, pageIndex)
        }

        /*
      сразу добавляем в модель в интеракторе. и сетим индекс
      дальше работаем так, будто модель уже есть
       */
        adapter.clear()
        adapter.addAll(mapPageItemToRecyclerViewItems())

        changeLayoutBias( copiedAnnouncePage.alignType.getAssociatedBias())
    }


    private fun mapPageItemToRecyclerViewItems(): List<EditableItem>{
        val resultList = mutableListOf<EditableItem>()

        copiedAnnouncePage.items?.forEach {
            resultList.add(
                    when(it){
                        is Image -> {EditableImageItem(it.type, it.imageSize, it.imageUrl)}

                        is Text -> {textItemFactory(it)}

                        else -> {EditableItemTextHeader("", 0.5f)}
                    }
            )
        }

        return resultList
    }

    private fun textItemFactory(textClass: Text): EditableItem{
        return when(textClass.textType){
            TextType.HEADER -> {EditableItemTextHeader(textClass.text, copiedAnnouncePage.alignType.getAssociatedBias())}

            TextType.SUB_HEADER -> {EditableItemTextSubHeader(textClass.text, copiedAnnouncePage.alignType.getAssociatedBias())}

            TextType.LIST -> {EditableItemTextList(textClass.text, copiedAnnouncePage.alignType.getAssociatedBias())}

            TextType.PLAIN -> {EditableItemTextPlain(textClass.text, copiedAnnouncePage.alignType.getAssociatedBias())}

        }
    }

    private fun initFabSpeedDial(){
        /*
       добавление типа align
       добавление текста !фрагмент!
       добавление картинки - !фрагмент с презентером и интерактором!
        */
        create_page.setMenuListener(object : SimpleMenuListenerAdapter(){
            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                menuItem?.let {
                    when(it.itemId) {
                        R.id.add_pic -> {
                            replaceFragment(EditImageItemFragment.newInstance(pageIndex))
                        }

                        R.id.add_text -> {

                            replaceFragment(AddTextItemFragment.newInstance(copiedAnnouncePage.items?.size ?: 0, pageIndex))
                        }

                        R.id.add_align_center -> {

                            copiedAnnouncePage.alignType = AlignType.CENTER // presenter change bias с колбэком в initAnnouncePage()
                            initAnnouncePage()

                        }

                        R.id.add_align_left -> {
                            copiedAnnouncePage.alignType = AlignType.TOP
                            initAnnouncePage()
                        }


                    }
                }
                return true
            }
        })
    }

    private fun saveChanges(){
        // сохраняем в бд
        AnnounceBuffer.getModel().announcePages[pageIndex] = copiedAnnouncePage


    }

    private fun changeAlignCenterMenuVisibility(boolean: Boolean){
        create_page.setVisibilityMenuItem(R.id.add_align_center, boolean)
        create_page.setVisibilityMenuItem(R.id.add_align_left, !boolean)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView()
    }

    private fun replaceFragment(fragment: Fragment){
        (activity as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment)
                ?.addToBackStack(null)
                ?.commit()
    }


    private val touchCallback: TouchCallback by lazy {
        object : TouchCallback() {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT -> {
                        val pos = viewHolder.adapterPosition
                        adapter.remove(adapter.getItem(pos))

                        copiedAnnouncePage.items?.let{
                            if(it.size > pos){
                                it.removeAt(pos)
                            }
                        }
                    }

                }
            }

            override fun isLongPressDragEnabled(): Boolean {
                return false
            }
        }
    }

    private val onItemClickListener = OnItemClickListener { item, view ->
        if(item is EditableImageItem){
            Toast.makeText(context, "image", Toast.LENGTH_SHORT).show()
        }else{
            replaceFragment(EditTextField.newInstance(adapter.getAdapterPosition(item), pageIndex))
        }

    }





}