package com.example.baby.network

import com.example.baby.data.*
import retrofit2.Response
import retrofit2.http.*

class BabyPatternRepository {

    // Activity
    suspend fun getActivitiesByDate(babyId: Int, date: String): Response<List<Activity>> {
        return RetrofitClient.service.getActivitiesByBabyIdAndDate(babyId, date)
    }

    // Sleep Pattern
    suspend fun registerSleepPattern(sleepPattern: SleepPattern): Response<PatternResponse> {
        return RetrofitClient.service.registerSleepPattern(sleepPattern)
    }

/*    suspend fun getSleepById(sleepId: Int): Response<SleepDetails> {
        return RetrofitClient.service.getSleepById(sleepId)
    }

    suspend fun deleteSleep(sleepId: Int): Response<PatternResponse> {
        return RetrofitClient.service.deleteSleep(sleepId)

    }*/

    suspend fun updateSleep(
        sleepId: Int,
        sleepUpdate: SleepDetails
    ): Response<PatternResponse> {
        return RetrofitClient.service.updateSleep(sleepId, sleepUpdate)
    }


    // Medicine Pattern
    suspend fun registerMedicinePattern(medicinePattern: MedicinePattern): Response<PatternResponse> {
        return RetrofitClient.service.registerMedicinePattern(medicinePattern)
    }

    /*    suspend fun getMedicineById(medicineId: Int): Response<MedicineDetails> {
            return RetrofitClient.service.getMedicineById(medicineId)

        }

        suspend fun deleteMedicine(medicineId: Int): Response<PatternResponse> {
            return RetrofitClient.service.deleteMedicine(medicineId)
        }

        suspend fun updateMedicine(
            medicineId: Int,
            medicineUpdate: MedicineDetails
        ): Response<PatternResponse> {
            return RetrofitClient.service.updateMedicine(medicineId, medicineUpdate)
        }*/
    //Defecation
    suspend fun registerDefecationPattern(defecationPattern: DefecationPattern): Response<PatternResponse> {
        return RetrofitClient.service.registerDefecation(defecationPattern)
    }

    //Bath
    suspend fun registerBathPattern(bathPattern: BathPattern): Response<PatternResponse> {
        return RetrofitClient.service.registerBath(bathPattern)
    }

}