package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.repository.FavoriteRaceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoriteUseCasesTest {

    private val repository: FavoriteRaceRepository = mockk()

    @Test
    fun `GetAllFavoriteRacesUseCase returns flow of favorites`() = runTest {
        val favorites = listOf(
            FavoriteRace(1, "Spanish GP", "Spain", "2024"),
            FavoriteRace(2, "Monaco GP", "Monaco", "2024")
        )
        every { repository.getFavoriteRaces() } returns flowOf(favorites)
        val useCase = GetAllFavoriteRacesUseCase(repository)

        val result = useCase().first()

        assertEquals(2, result.size)
        assertEquals("Spanish GP", result[0].competition)
    }

    @Test
    fun `GetAllFavoriteRacesIdsUseCase returns list of ids`() = runTest {
        coEvery { repository.getAllFavoriteRacesIds() } returns listOf(1, 2, 3)
        val useCase = GetAllFavoriteRacesIdsUseCase(repository)

        val result = useCase()

        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun `InsertFavoriteRaceUseCase delegates to repository`() = runTest {
        val race = Race("Spanish GP", "20-22", "Jun", "Spain", 1, 100, "66 laps", "2024")
        coEvery { repository.insertFavoriteRace(race) } returns Unit
        val useCase = InsertFavoriteRaceUseCase(repository)

        useCase(race)

        coVerify(exactly = 1) { repository.insertFavoriteRace(race) }
    }

    @Test
    fun `DeleteFavoriteUseCase delegates to repository`() = runTest {
        coEvery { repository.deleteFavoriteRace(100) } returns Unit
        val useCase = DeleteFavoriteUseCase(repository)

        useCase(100)

        coVerify(exactly = 1) { repository.deleteFavoriteRace(100) }
    }

    @Test
    fun `GetAllFavoriteRacesUseCase returns empty flow when no favorites`() = runTest {
        every { repository.getFavoriteRaces() } returns flowOf(emptyList())
        val useCase = GetAllFavoriteRacesUseCase(repository)

        val result = useCase().first()

        assertTrue(result.isEmpty())
    }
}
