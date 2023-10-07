package com.david.f1stats.ui.raceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.david.f1stats.databinding.FragmentRaceDetailBinding
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.TypeRace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceDetailFragment : Fragment() {

    private var _binding: FragmentRaceDetailBinding? = null
    private val raceDetailViewModel: RaceDetailViewModel by viewModels()
    private var raceDate: Long? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { raceDetailViewModel.start(it) }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        raceDetailViewModel.raceList.observe(viewLifecycleOwner) { race ->
            race?.forEach {
                when (it.type) {
                    TypeRace.RACE -> setRace(it)
                    TypeRace.QUALY -> setQualifying(it)
                    TypeRace.P3 -> setPractice3(it)
                    TypeRace.P2 -> setPractice2(it)
                    TypeRace.P1 -> setPractice1(it)
                    TypeRace.SPRINT -> setSprint(it)
                    TypeRace.SPRINT_SHOOTOUT -> setSprintShootout(it)
                    TypeRace.NONE -> {}
                }
            }
        }

        raceDetailViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }

        binding.raceWeekend.setOnClickListener {
            raceDetailViewModel.onAddToCalendarRequested(requireContext(), "F1 Race", "Race Circuit", raceDate!!)
        }

        return root
    }

    private fun setRace(race: RaceDetail){
        binding.nameRace.text = race.competition
        binding.locationRace.text = race.country
        binding.circuitRace.text = race.circuit
        binding.lapsRace.text = race.laps
        binding.raceDay.text = race.day
        binding.raceMonth.text = race.month
        binding.raceHour.text = race.hour
        raceDate = race.dateCalendar
    }

    private fun setQualifying(race: RaceDetail){
        binding.qualyDay.text = race.day
        binding.qualyMonth.text = race.month
        binding.qualyHour.text = race.hour
    }

    private fun setSprint(race: RaceDetail){
        binding.sprintDay.text = race.day
        binding.sprintMonth.text = race.month
        binding.sprintHour.text = race.hour
        binding.cvSprint.visibility = View.VISIBLE
    }

    private fun setSprintShootout(race: RaceDetail) {
        binding.sprintShootoutDay.text = race.day
        binding.sprintShootoutMonth.text = race.month
        binding.sprintShootoutHour.text = race.hour
        binding.cvSprintShootout.visibility = View.VISIBLE
    }

    private fun setPractice3(race: RaceDetail){
        binding.p3Day.text = race.day
        binding.p3Month.text = race.month
        binding.p3Hour.text = race.hour
        binding.cvP3.visibility = View.VISIBLE
    }

    private fun setPractice2(race: RaceDetail){
        binding.p2Day.text = race.day
        binding.p2Month.text = race.month
        binding.p2Hour.text = race.hour
        binding.cvP2.visibility = View.VISIBLE
    }

    private fun setPractice1(race: RaceDetail){
        binding.p1Day.text = race.day
        binding.p1Month.text = race.month
        binding.p1Hour.text = race.hour
        binding.cvP1.visibility = View.VISIBLE
    }
}
