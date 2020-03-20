package com.uhfsolutions.roomdatabase.Paging.model

class StackExchResp {
    var items:List<Items>? = null
    var has_more:Boolean? = null
    var backoff:Int? =  10
    var quota_max:Int? = null
    var quota_remaining:Int? =  null
}