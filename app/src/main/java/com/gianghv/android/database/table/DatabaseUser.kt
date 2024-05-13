package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.User

@Entity
data class DatabaseUser(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val role: String
)

fun DatabaseUser.asDomainModel(): User {
    return User(id, name, email, role)
}

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(it.id, it.name, it.email, it.role)
    }
}
