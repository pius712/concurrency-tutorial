package com.pius.concurrency.member

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity


@Entity
data class MemberEntity(
    val name:String
): BaseEntity() {
}