package com.gianghv.android.views.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gianghv.android.databinding.ItemNotFoundBinding
import com.gianghv.android.databinding.ItemRoomCardBinding
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomType
import com.gianghv.android.util.ext.dateFormatterDMYHM
import com.gianghv.android.util.ext.dateFormatterZ
import com.gianghv.android.util.ext.loadImageCenterCrop
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.util.Date

class HotelAdapter : RecyclerView.Adapter<HotelAdapter.ViewHolder>(), Filterable {
    private val items = mutableListOf<Room>()
    private val displayItems = mutableListOf<Room>()
    private val orderList = mutableListOf<Order>()

    private var filterConstrain: FilterConstrain? = null
    private var onItemClick: ((Room) -> Unit)? = null
    fun updateItems(items: List<Room>) {
        this.items.clear()
        this.items.addAll(items)
        filter.filter(filterConstrain?.let { filterBuilder(it) })
    }

    fun setOnItemClick(onItemClick: (Room) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun updateOrderList(items: List<Order>) {
        this.orderList.clear()
        this.orderList.addAll(items)
        filter.filter(filterConstrain?.let { filterBuilder(it) })
    }

    open class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    class NoResultViewHolder(binding: ItemNotFoundBinding) : ViewHolder(binding) {
        companion object {
            fun createBinding(parent: ViewGroup): ItemNotFoundBinding {
                val inflater = LayoutInflater.from(parent.context)
                return ItemNotFoundBinding.inflate(inflater, parent, false)
            }
        }
    }

    class RoomViewHolder(binding: ItemRoomCardBinding) : ViewHolder(binding) {
        companion object {
            fun createBinding(parent: ViewGroup): ItemRoomCardBinding {
                val inflater = LayoutInflater.from(parent.context)
                return ItemRoomCardBinding.inflate(inflater, parent, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = when (viewType) {
            TYPE_NO_RESULT -> NoResultViewHolder.createBinding(parent)
            else -> RoomViewHolder.createBinding(parent)
        }
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (displayItems.isEmpty()) 1 else displayItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (displayItems.isEmpty()) TYPE_NO_RESULT else TYPE_ROOM
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ROOM) {
            val binding = holder.binding as ItemRoomCardBinding
            val item = displayItems[position]
            binding.textName.text = item.name
            binding.textPrice.text = item.price.toString()
            binding.textDesc.text = item.desc
            binding.textNumberOfPeople.text = item.countPeople.toString()

            if (item.type == RoomType.Vip) {
                binding.textRoomTypeVip.visibility = View.VISIBLE
            } else {
                binding.textRoomTypeVip.visibility = View.GONE
            }

            if (item.evaluation.isEmpty()) {
                binding.textEvaluation.visibility = View.GONE
            } else {
                binding.textEvaluation.visibility = View.VISIBLE
                binding.textEvaluation.text = "${item.getEvaluationAverage()}"
            }

            binding.imageThumbnail.loadImageCenterCrop(item.images.firstOrNull()?.url.toString())

            binding.root.setOnClickListener { onItemClick?.invoke(item) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                Timber.d("performFiltering: $constraint")
                val constrain = getFilterConstrain(constraint.toString())
                println("FIlter constrain: $constrain")
                filterConstrain = constrain
                if (constrain == null) {
                    results.values = items
                } else {
                    var filteredData = mutableListOf<Room>()
                    filteredData.addAll(items)

                    Timber.d("FIRST FILTER: $filteredData")

                    val checkKeyword = constrain.searchKeyword.isNullOrEmpty() || constrain.searchKeyword == "null"
                    if (!checkKeyword) {
                        filteredData = filteredData.filter {
                            it.name.contains(
                                constrain.searchKeyword.toString(),
                                ignoreCase = true
                            )
                        }.toMutableList()

                        Timber.d("Search key word not null")
                        Timber.d("SECOND FILTER: $filteredData")
                    }

                    val checkInOut =
                        constrain.checkinDay.isNullOrEmpty() ||
                            constrain.checkoutDay.isNullOrEmpty() ||
                            constrain.checkinDay == "null" ||
                            constrain.checkoutDay == "null"

                    if (!checkInOut) {
                        filteredData = filteredData.filter {
                            isRoomAvailableByStartEndDate(
                                it,
                                constrain.checkinDay,
                                constrain.checkoutDay
                            )
                        }.toMutableList()

                        Timber.d("CHECKIN/CHECKOUT FILTER")
                        Timber.d("THIRD FILTER: $filteredData")
                    }

                    if (constrain.people != null) {
                        filteredData = filteredData.filter { it.countPeople >= constrain.people }.toMutableList()

                        Timber.d("PEOPLE FILTER")
                        Timber.d("FOURTH FILTER: $filteredData")
                    }

                    if (constrain.minPrice != null && constrain.maxPrice != null) {
                        filteredData = filteredData.filter {
                            Timber.d("min price: ${constrain.minPrice} max price: ${constrain.maxPrice}")
                            it.price in constrain.minPrice..constrain.maxPrice
                        }.toMutableList()

                        Timber.d("Min/max price FILTER")
                        Timber.d("FIFTH FILTER: $filteredData")
                    }

                    results.values = filteredData
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.values?.let {
                    displayItems.clear()
                    displayItems.addAll(it as List<Room>)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun isRoomAvailableByStartEndDate(roomId: Room, checkinDay: String?, checkoutDay: String?): Boolean {
        var isNonOverlapping = true
        val checkin = checkinDay?.dateFormatterDMYHM()
        val checkout = checkoutDay?.dateFormatterDMYHM()
        orderList.filter { it.roomId == roomId.id }.forEach { order ->

            val startDate = order.startDate.dateFormatterZ()
            val endDate = order.endDate.dateFormatterZ()

            if (checkin != null && checkout != null && startDate != null && endDate != null) {
                if (dateRangesOverlap(startDate, endDate, checkin, checkout)) {
                    isNonOverlapping = isNonOverlapping and false
                }
            }
        }
        return isNonOverlapping
    }

    private fun dateRangesOverlap(startA: Date, endA: Date, startB: Date, endB: Date): Boolean {
        return startA.before(endB) && startB.before(endA) || startA == startB && endA == endB
    }

    private fun filterBuilder(filterConstrain: FilterConstrain): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(FilterConstrain::class.java)
        return jsonAdapter.toJson(filterConstrain)
    }

    private fun getFilterConstrain(json: String): FilterConstrain? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(FilterConstrain::class.java)
        return jsonAdapter.fromJson(json)
    }

    private data class FilterConstrain(
        val searchKeyword: String?,
        val checkinDay: String?,
        val checkoutDay: String?,
        val people: Int?,
        val minPrice: Int?,
        val maxPrice: Int?
    )

    companion object {
        const val TYPE_NO_RESULT = 0
        const val TYPE_ROOM = 1

        fun filterBuilder(
            searchKeyword: String?,
            checkinDay: String?,
            checkoutDay: String?,
            people: Int?,
            minPrice: Int?,
            maxPrice: Int?
        ): String {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(FilterConstrain::class.java)

            val filterConstrain = FilterConstrain(
                searchKeyword,
                checkinDay,
                checkoutDay,
                people,
                minPrice,
                maxPrice
            )

            return jsonAdapter.toJson(filterConstrain)
        }
    }
}
