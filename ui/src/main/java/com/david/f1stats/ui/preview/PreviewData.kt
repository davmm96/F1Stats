package com.david.f1stats.ui.preview

import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.model.FavoriteRace
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam

val previewRaces = listOf(
    Race(
        competition = "Bahrain Grand Prix",
        dayInterval = "28-30",
        month = "MAR",
        country = "Bahrain",
        idCompetition = 1,
        idRace = 101,
        laps = "57 Laps",
        season = "2025"
    ),
    Race(
        competition = "Saudi Arabian Grand Prix",
        dayInterval = "04-06",
        month = "APR",
        country = "Saudi Arabia",
        idCompetition = 2,
        idRace = 102,
        laps = "50 Laps",
        season = "2025"
    ),
    Race(
        competition = "Australian Grand Prix",
        dayInterval = "11-13",
        month = "APR",
        country = "Australia",
        idCompetition = 3,
        idRace = 103,
        laps = "58 Laps",
        season = "2025"
    ),
)

val previewCircuits = listOf(
    Circuit(
        id = 1,
        name = "Bahrain International Circuit",
        country = "Bahrain",
        length = "5.412 km",
        laps = "57",
        firstGP = "2004",
        lapRecordTime = "1:31.447",
        lapRecordDriver = "Pedro de la Rosa (2005)",
        imageURL = ""
    ),
    Circuit(
        id = 2,
        name = "Jeddah Corniche Circuit",
        country = "Saudi Arabia",
        length = "6.174 km",
        laps = "50",
        firstGP = "2021",
        lapRecordTime = "1:30.734",
        lapRecordDriver = "Lewis Hamilton (2021)",
        imageURL = ""
    ),
)

val previewRankingDrivers = listOf(
    RankingDriver(
        position = 1,
        idDriver = 1,
        image = "",
        name = "Max Verstappen",
        team = "Red Bull Racing",
        points = "575",
        idTeam = 1
    ),
    RankingDriver(
        position = 2,
        idDriver = 2,
        image = "",
        name = "Lando Norris",
        team = "McLaren",
        points = "374",
        idTeam = 2
    ),
    RankingDriver(
        position = 3,
        idDriver = 3,
        image = "",
        name = "Charles Leclerc",
        team = "Ferrari",
        points = "356",
        idTeam = 3
    ),
    RankingDriver(
        position = 4,
        idDriver = 4,
        image = "",
        name = "Carlos Sainz",
        team = "Ferrari",
        points = "290",
        idTeam = 3
    ),
    RankingDriver(
        position = 5,
        idDriver = 5,
        image = "",
        name = "Lewis Hamilton",
        team = "Mercedes",
        points = "245",
        idTeam = 4
    ),
)

val previewDriverDetail = DriverDetail(
    name = "Max Verstappen",
    nationality = "Dutch",
    country = "Netherlands",
    birthdate = "30/09/1997",
    image = "",
    number = "1",
    gpEntered = "200",
    worldChampionships = "4",
    podiums = "110",
    wins = "62",
    points = "2920.5",
    teamImage = ""
)

val previewRankingTeams = listOf(
    RankingTeam(idTeam = 1, points = "860", position = 1, name = "Red Bull Racing", image = ""),
    RankingTeam(idTeam = 2, points = "666", position = 2, name = "McLaren", image = ""),
    RankingTeam(idTeam = 3, points = "652", position = 3, name = "Ferrari", image = ""),
    RankingTeam(idTeam = 4, points = "409", position = 4, name = "Mercedes", image = ""),
    RankingTeam(idTeam = 5, points = "97", position = 5, name = "Aston Martin", image = ""),
)

val previewFavoriteRaces = listOf(
    FavoriteRace(
        id = 101,
        competition = "Bahrain Grand Prix",
        country = "Bahrain",
        season = "2025"
    ),
    FavoriteRace(
        id = 102,
        competition = "Saudi Arabian Grand Prix",
        country = "Saudi Arabia",
        season = "2025"
    ),
    FavoriteRace(
        id = 103,
        competition = "Australian Grand Prix",
        country = "Australia",
        season = "2025"
    ),
)
