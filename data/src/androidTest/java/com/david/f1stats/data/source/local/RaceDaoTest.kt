package com.david.f1stats.data.source.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RaceDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: RaceDao

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.raceDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveFavoriteRace() = runTest {
        val race = FavoriteRace(1, "Spanish GP", "Spain", "2024")
        dao.insertFavoriteRace(race)

        dao.getAllFavoriteRaces().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Spanish GP", result[0].competition)
            assertEquals("Spain", result[0].country)
            assertEquals("2024", result[0].season)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun insertMultipleAndRetrieveAll() = runTest {
        dao.insertFavoriteRace(FavoriteRace(1, "Spanish GP", "Spain", "2024"))
        dao.insertFavoriteRace(FavoriteRace(2, "Monaco GP", "Monaco", "2024"))
        dao.insertFavoriteRace(FavoriteRace(3, "British GP", "UK", "2024"))

        dao.getAllFavoriteRaces().test {
            val result = awaitItem()
            assertEquals(3, result.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun deleteFavoriteRace() = runTest {
        dao.insertFavoriteRace(FavoriteRace(1, "Spanish GP", "Spain", "2024"))
        dao.insertFavoriteRace(FavoriteRace(2, "Monaco GP", "Monaco", "2024"))

        dao.deleteFavoriteRace(1)

        dao.getAllFavoriteRaces().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Monaco GP", result[0].competition)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun getAllFavoriteRaceIds() = runTest {
        dao.insertFavoriteRace(FavoriteRace(10, "GP1", "C1", "2024"))
        dao.insertFavoriteRace(FavoriteRace(20, "GP2", "C2", "2024"))

        val ids = dao.getAllFavoriteRaceIds()
        assertEquals(2, ids.size)
        assertTrue(ids.contains(10))
        assertTrue(ids.contains(20))
    }

    @Test
    fun getFavoriteRaceById_existing() = runTest {
        dao.insertFavoriteRace(FavoriteRace(1, "Spanish GP", "Spain", "2024"))

        val result = dao.getFavoriteRaceById(1)
        assertEquals("Spanish GP", result?.competition)
    }

    @Test
    fun getFavoriteRaceById_nonExisting() = runTest {
        val result = dao.getFavoriteRaceById(999)
        assertNull(result)
    }

    @Test
    fun insertReplaceOnConflict() = runTest {
        dao.insertFavoriteRace(FavoriteRace(1, "Old GP", "Old", "2023"))
        dao.insertFavoriteRace(FavoriteRace(1, "New GP", "New", "2024"))

        dao.getAllFavoriteRaces().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("New GP", result[0].competition)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        dao.getAllFavoriteRaces().test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun emptyDatabaseReturnsEmptyIds() = runTest {
        val ids = dao.getAllFavoriteRaceIds()
        assertTrue(ids.isEmpty())
    }

    @Test
    fun flowUpdatesWhenDataChanges() = runTest {
        dao.getAllFavoriteRaces().test {
            assertTrue(awaitItem().isEmpty())

            dao.insertFavoriteRace(FavoriteRace(1, "GP1", "C1", "2024"))
            assertEquals(1, awaitItem().size)

            dao.insertFavoriteRace(FavoriteRace(2, "GP2", "C2", "2024"))
            assertEquals(2, awaitItem().size)

            dao.deleteFavoriteRace(1)
            assertEquals(1, awaitItem().size)

            cancelAndConsumeRemainingEvents()
        }
    }
}
