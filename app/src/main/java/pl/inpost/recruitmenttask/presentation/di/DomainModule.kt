package pl.inpost.recruitmenttask.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.ShipmentNetworkDatabase
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentGroupByHighlightUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideDGetShipmentGroupByHighlightUseCase(repository: ShipmentNetworkRepository) =
        GetShipmentGroupByHighlightUseCase(repository)
}