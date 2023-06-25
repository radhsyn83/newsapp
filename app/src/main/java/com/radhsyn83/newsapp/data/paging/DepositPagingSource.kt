package com.radhsyn83.newsapp.data.paging

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.radhsyn83.themoviedb.data.DataSource
//import java.lang.Exception
//
//class DepositPagingSource(val dataSource: DataSource) : PagingSource<Int, DepositModel.Data.Deposit>() {
//    override fun getRefreshKey(state: PagingState<Int, DepositModel.Data.Deposit>): Int? {
//        return state.anchorPosition
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DepositModel.Data.Deposit> {
//        return try {
//            val nextPage = params.key ?: 1
//            val res = dataSource.deposit(nextPage)
//            var nextPageNumber: Int? = null
//
//            if (res.code == 200) {
//                nextPageNumber = nextPage + 1
//            }
//
//            LoadResult.Page(
//                data = res.data?.deposit ?: arrayListOf(),
//                prevKey = null,
//                nextKey = nextPageNumber
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}