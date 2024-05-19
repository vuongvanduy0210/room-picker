package com.gianghv.android.views.orderhistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemNotFoundBinding
import com.gianghv.android.databinding.ItemOrderHistoryBinding
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.OrderStatus
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.util.ext.loadImageCenterCrop
import com.gianghv.android.util.ext.parseDateDMYHM

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>(), Filterable {
    private val items = mutableListOf<Order>()
    private val displayItems = mutableListOf<Order>()
    private val rooms = mutableListOf<Room>()
    private var onItemClickListener: ((String) -> Unit)? = null

    fun setItems(items: List<Order>, rooms: List<Room>) {
        this.items.clear()
        this.items.addAll(items)
        this.rooms.clear()
        this.rooms.addAll(rooms)
        displayItems.clear()
        displayItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnOrderClickListener(onItemClickListener: (String) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class NotFoundViewHolder(binding: ItemNotFoundBinding) : ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): NotFoundViewHolder {
                return NotFoundViewHolder(
                    ItemNotFoundBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    class OrderViewHolder(val binding: ItemOrderHistoryBinding) : ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): OrderViewHolder {
                return OrderViewHolder(
                    ItemOrderHistoryBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> OrderViewHolder.create(parent)

            VIEW_TYPE_NOT_FOUND -> NotFoundViewHolder.create(parent)

            else -> NotFoundViewHolder.create(parent)
        }
    }

    override fun getItemCount(): Int {
        return if (displayItems.isEmpty()) 1 else displayItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            val item = displayItems[position]
            if (holder is OrderViewHolder) {
                val binding = holder.binding

                binding.textAmount.text = "${item.price} VND"
                binding.textPeopleCount.text = item.people.toString()

                when (item.typePayment) {
                    TypePayment.CASH -> {
                        binding.textPaymentMethod.text = "Tiền mặt"
                    }

                    TypePayment.VNPAY -> {
                        binding.textPaymentMethod.text = "VNPay"
                    }

                    else -> {
                        binding.textPaymentMethod.text = "Chưa chọn phương thức thanh toán"
                    }
                }

                when (item.status) {
                    OrderStatus.PENDING -> {
                        binding.textOrderStatus.text = "Chờ thanh toán"
                    }

                    OrderStatus.PAYED, OrderStatus.COMPLETED, OrderStatus.DEPOSIT -> {
                        binding.textOrderStatus.text = "Đã thanh toán"
                    }
                }

                val room = rooms.find { it.id == item.roomId }
                if (room != null) {
                    binding.textName.text = room.name
                    binding.textPrice.text = room.price.toString()
                }

                binding.textBookingDate.text = item.bookingDate.parseDateDMYHM()
                binding.textCheckinTime.text = item.startDate.parseDateDMYHM()
                binding.textCheckoutTime.text = item.endDate.parseDateDMYHM()

                binding.imageRoom.loadImageCenterCrop(room?.images?.firstOrNull()?.url ?: "")

                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(item.id)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (displayItems.isEmpty()) VIEW_TYPE_NOT_FOUND else VIEW_TYPE_ITEM
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_NOT_FOUND = 1

        const val FILTER_ALL = "Tất cả"
        const val FILTER_PENDING = "Chờ thanh toán"
        const val FILTER_PAID = "Đã thanh toán"
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                val result = FilterResults()

                if (charString.isEmpty()) {
                    displayItems.clear()
                    displayItems.addAll(items)
                    result.values = displayItems
                }

                when (constraint) {
                    FILTER_ALL -> {
                        displayItems.clear()
                        displayItems.addAll(items)
                    }

                    FILTER_PENDING -> {
                        displayItems.clear()
                        displayItems.addAll(items.filter { it.status == OrderStatus.PENDING })
                    }

                    FILTER_PAID -> {
                        displayItems.clear()
                        displayItems.addAll(items.filter {
                            it.status in listOf(
                                OrderStatus.PAYED, OrderStatus.COMPLETED, OrderStatus.DEPOSIT
                            )
                        })
                    }
                }

                return FilterResults()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }
}
