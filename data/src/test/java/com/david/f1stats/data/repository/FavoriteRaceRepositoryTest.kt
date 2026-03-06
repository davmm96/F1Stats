package com.david.f1stats.data.repository

import app.cash.turbine.test
import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.source.local.RaceDao
import com.david.f1stats.domain.model.Race
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.david.f1stats.data.model.favoriteRace.FavoriteRace as FavoriteRaceEntity

class FavoriteRaceRepositoryTest {

    private val raceDao: RaceDao = mockk()
    private val favoriteRaceMapper: FavoriteRaceMapper = mockk()
    private val repository = FavoriteRaceRepository(raceDao, favoriteRaceMapper)

    @Test
    fun `getFavoriteRaces returns sorted favorites`() = runTest {
        val entities = listOf(
            FavoriteRaceEntity(1, "Monaco GP", "Monaco", "2023"),
            FavoriteRaceEntity(2, "Spanish GP", "Spain", "2024"),
            FavoriteRaceEntity(3, "Australian GP", "Australia", "2024")
        )
        every { raceDao.getAllFavoriteRaces() } returns flowOf(entities)

        repository.getFavoriteRaces().test {
            val result = awaitItem()
            // Sorted by season desc, then competition asc
            assertEquals("Australian GP", result[0].competition)
            assertEquals("Spanish GP", result[1].competition)
            assertEquals("Monaco GP", result[2].competition)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getFavoriteRaces returns empty list when no favorites`() = runTest {
        every { raceDao.getAllFavoriteRaces() } returns flowOf(emptyList())

        repository.getFavoriteRaces().test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getAllFavoriteRacesIds delegates to dao`() = runTest {
        coEvery { raceDao.getAllFavoriteRaceIds() } returns listOf(1, 2, 3)

        val result = repository.getAllFavoriteRacesIds()

        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun `insertFavoriteRace maps and inserts`() = runTest {
        val race = Race("GP", "1-3", "Jan", "Country", 1, 100, "50 laps", "2024")
        val entity = FavoriteRaceEntity(100, "GP", "Country", "2024")
        every { favoriteRaceMapper.fromMap(race) } returns entity
        coEvery { raceDao.insertFavoriteRace(entity) } returns 1L

        repository.insertFavoriteRace(race)

        coVerify { raceDao.insertFavoriteRace(entity) }
    }

    @Test
    fun `deleteFavoriteRace delegates to dao`() = runTest {
        coEvery { raceDao.deleteFavoriteRace(100) } returns Unit

        repository.deleteFavoriteRace(100)

        coVerify { raceDao.deleteFavoriteRace(100) }
    }
}
