package com.gianghv.android.views.orderhistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemNotFoundBinding
import com.gianghv.android.databinding.ItemOrderHistoryBinding
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.OrderStatus
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.util.ext.loadImageCenterCrop
import com.gianghv.android.util.ext.parseDateDMYHM
import timber.log.Timber

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    private val items = mutableListOf<Order>()
    private val rooms = mutableListOf<Room>()

    fun setItems(items: List<Order>, rooms: List<Room>) {
        this.items.clear()
        this.items.addAll(items)
        this.rooms.clear()
        this.rooms.addAll(rooms)
        notifyDataSetChanged()
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class NotFoundViewHolder(binding: ItemNotFoundBinding) : ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): NotFoundViewHolder {
                return NotFoundViewHolder(
                    ItemNotFoundBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
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
                        LayoutInflater.from(parent.context),
                        parent,
                        false
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
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            val item = items[position]
            if (holder is OrderViewHolder) {
                val binding = holder.binding

                binding.textAmount.text = "${item.price} VND"
                binding.textPeopleCount.text = item.people.toString()

                Timber.d("item.typePayment: ${item.typePayment}")

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
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) VIEW_TYPE_NOT_FOUND else VIEW_TYPE_ITEM
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_NOT_FOUND = 1
    }
}
