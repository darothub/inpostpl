package pl.inpost.recruitmenttask.presentation.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.ShipmentNetworkDatabase
import pl.inpost.recruitmenttask.data.local.dao.EventLogDao
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepositoryImpl
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShipmentNetworkDatabase {
        return Room.databaseBuilder(context, ShipmentNetworkDatabase::class.java, "shipments_db")
            .build()
    }

    @Provides
    fun provideShipmentNetworkDao(database: ShipmentNetworkDatabase): ShipmentNetworkDao {
        return database.getShipmentNetworkDao()
    }

    @Provides
    fun provideEventLogDao(database: ShipmentNetworkDatabase): EventLogDao {
        return database.getEventLogDao()
    }

    @Provides
    fun provideShipmentNetworkRepository(shipmentNetworkDao: ShipmentNetworkDao, api: ShipmentApi): ShipmentNetworkRepository =
        ShipmentNetworkRepositoryImpl(api, shipmentNetworkDao)
}