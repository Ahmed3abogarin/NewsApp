package com.ahmed.newsapp.di

import android.app.Application
import androidx.room.Room
import com.ahmed.newsapp.data.manager.LocalUserImpl
import com.ahmed.newsapp.domain.repository.NewsRepository
import com.ahmed.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.ahmed.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.ahmed.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.ahmed.newsapp.data.local.NewsDao
import com.ahmed.newsapp.data.local.NewsDatabase
import com.ahmed.newsapp.data.local.NewsTypeConvertor
import com.ahmed.newsapp.domain.manager.LocalUserManager
import com.ahmed.newsapp.domain.usecases.news.DeleteArticle
import com.ahmed.newsapp.domain.usecases.news.GetNews
import com.ahmed.newsapp.domain.usecases.news.NewsUseCases
import com.ahmed.newsapp.domain.usecases.news.SearchNews
import com.ahmed.newsapp.domain.usecases.news.SelectArticle
import com.ahmed.newsapp.domain.usecases.news.SelectArticles
import com.ahmed.newsapp.domain.usecases.news.UpsertArticle
import com.ahmed.newsapp.data.remote.NewsApi
import com.ahmed.newsapp.data.repository.NewsRepositoryImpl
import com.ahmed.newsapp.utils.Constants.BASE_URL
import com.ahmed.newsapp.utils.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // specify the lifecycle. (SingletonComponent::class) means will as long as the application is alive
object AppModule {
    /*
    inside the module we define the dependencies we want to provide
     */

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserImpl(application)

    // also we need to provide the two use cases


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // to convert the json response to kotlin class
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration() // if you update something room will migrate it for you
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao


}