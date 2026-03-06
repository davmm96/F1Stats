package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Season
import com.david.f1stats.domain.model.StatusRaceEnum
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.domain.model.TypeRaceEnum
import com.david.f1stats.domain.repository.DriverRepository
import com.david.f1stats.domain.repository.RaceRepository
import com.david.f1stats.domain.repository.SeasonRepository
import com.david.f1stats.domain.repository.TeamRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetDetailUseCasesTest {

    @Test
    fun `GetDriverDetailUseCase returns driver detail`() = runTest {
        val repository: DriverRepository = mockk()
        val driver = DriverDetail(
            "Verstappen",
            "Dutch",
            "NL",
            "1997",
            "img",
            "#1",
            "200",
            "3",
            "30",
            "60",
            "2500",
            "team"
        )
        coEvery { repository.getDriverDetail(1) } returns Result.Success(driver)
        val useCase = GetDriverDetailUseCase(repository)

        val result = useCase(1)

        assertTrue(result is Result.Success)
        assertEquals("Verstappen", (result as Result.Success).data.name)
    }

    @Test
    fun `GetTeamDetailUseCase returns team detail`() = runTest {
        val repository: TeamRepository = mockk()
        val team = TeamDetail("Red Bull", "Milton Keynes", "img", "2005", "6", "120", "100", "90")
        coEvery { repository.getTeamDetail(1) } returns Result.Success(team)
        val useCase = GetTeamDetailUseCase(repository)

        val result = useCase(1)

        assertTrue(result is Result.Success)
        assertEquals("Red Bull", (result as Result.Success).data.name)
    }

    @Test
    fun `GetRaceDetailsUseCase returns race details`() = runTest {
        val repository: RaceRepository = mockk()
        val details = listOf(
            RaceDetail(
                "Circuit",
                "GP",
                "22",
                "Jun",
                "15:00",
                "Spain",
                1,
                "66 laps",
                TypeRaceEnum.RACE,
                1000L,
                StatusRaceEnum.SCHEDULED
            )
        )
        coEvery { repository.getRaceDetails(1) } returns Result.Success(details)
        val useCase = GetRaceDetailsUseCase(repository)

        val result = useCase(1)

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
    }

    @Test
    fun `GetRaceResultUseCase returns race results`() = runTest {
        val repository: RaceRepository = mockk()
        val results = listOf(RaceResult("1", "VER", "1:30:00", "25", 1))
        coEvery { repository.getRaceResult(1) } returns Result.Success(results)
        val useCase = GetRaceResultUseCase(repository)

        val result = useCase(1)

        assertTrue(result is Result.Success)
        assertEquals("VER", (result as Result.Success).data[0].driverAbbr)
    }

    @Test
    fun `GetRaceCompletedUseCase returns completed races`() = runTest {
        val repository: RaceRepository = mockk()
        coEvery { repository.getCompletedRaces() } returns Result.Success(emptyList())
        val useCase = GetRaceCompletedUseCase(repository)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }

    @Test
    fun `GetSeasonsUseCase returns seasons`() = runTest {
        val repository: SeasonRepository = mockk()
        val seasons = listOf(Season("2024"), Season("2023"))
        coEvery { repository.getSeasons() } returns Result.Success(seasons)
        val useCase = GetSeasonsUseCase(repository)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
    }

    @Test
    fun `GetRankingTeamUseCase returns error on failure`() = runTest {
        val repository = mockk<com.david.f1stats.domain.repository.RankingRepository>()
        coEvery { repository.getRankingTeam() } returns Result.Error(Exception("Network error"))
        val useCase = GetRankingTeamUseCase(repository)

        val result = useCase()

        assertTrue(result is Result.Error)
    }
}
