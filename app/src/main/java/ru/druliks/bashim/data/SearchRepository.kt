package ru.druliks.bashim.data

import io.reactivex.Observable

class SearchRepository (
    val apiService: BashImApiService
){
    fun searchQuetes(site:String,name:String):Observable<List<Quote>>{
        return apiService.searchQuetes(site,name,50)
    }

    fun searchSources():Observable<List<List<SourceOfQuotes>>>{
        return apiService.searchSources()
    }
}