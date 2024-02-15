package com.example.baby.viewModel

import androidx.lifecycle.ViewModel
import com.example.baby.data.Activity
import java.time.LocalDateTime

class BabyPatternViewModel : ViewModel() {
    var dummy: List<Activity> = mutableListOf(
        Activity(
            activityId = 1,
            activityType = "Type 1",
            startTime = LocalDateTime.of(2024, 2, 15, 13, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 14, 16, 53, 203_000_000),
            specificStatus = "Status 1",
            memo = "Memo 1"
        ),
        Activity(
            activityId = 2,
            activityType = "Type 2",
            startTime = LocalDateTime.of(2024, 2, 15, 14, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 15, 16, 53, 203_000_000),
            specificStatus = "Status 2",
            memo = "Memo 2"
        ),
        Activity(
            activityId = 3,
            activityType = "Type 3",
            startTime = LocalDateTime.of(2024, 2, 15, 15, 16, 53, 203_000_000),
            endTime = LocalDateTime.of(2024, 2, 15, 16, 16, 53, 203_000_000),
            specificStatus = "Status 3",
            memo = "Memo 3"
        )

    )


}