package com.galaxy.randomduck.di

import com.galaxy.randomduck.one_duck.data.remote.DuckApi
import com.galaxy.randomduck.one_duck.data.repository.DuckRepositoryImpl
import com.galaxy.randomduck.one_duck.domain.repository.DuckRepository
import com.galaxy.randomduck.one_duck.domain.use_case.GetRandomDuck
import com.galaxy.randomduck.util.BaseUrl.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDuckApi(): DuckApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DuckApi::class.java)

    }

    @Provides
    @Singleton
    fun provideDuckRepository(api: DuckApi): DuckRepository{
        return DuckRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: DuckRepository): GetRandomDuck{
        return GetRandomDuck(repository)
    }
}