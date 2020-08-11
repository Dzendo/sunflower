/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding

// Стартовый класс sunflower
class GardenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        // надуваем и высвечиваем activity_garden.xml методом dataBinding (можно ViewBinding)
        // там только фрагмент для навишации nav_garden с NavHostFragment
        // А можно и хорошо бы включить onSupportNavigateUp() стандартно
    }
}
//  ************** Далее идем в nav_garden - там почти вся навигация ***************
// *************** и оттуда вызывается стартовый фрагмент HomeViewPagerFragment *****

// Можно переставить сюда или в Application вызов планировщика  из AppDatabase SeedDatabaseWorker:
/*
 // Запланировать WorkManager по лекции  Android Kotlin Основы 09.2: WorkManager
    private fun delayedInit() {
        applicationScope.launch {
            Log.w(SeedDatabaseWorker.TAG,"Запланирована")
            setupRecurringWork()
        }
    }
    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork() {  // настройка повторяющейся работы
       /* val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()
        */
        // Запланируется работа для WorkManager SunFlower
        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(request)

        val repeatingRequest = PeriodicWorkRequestBuilder<SeedDatabaseWorker>(15, TimeUnit.MINUTES)
               // (1, TimeUnit.DAYS)
               // .setConstraints(constraints)
                .build()

        //Timber.d("WorkManager: Periodic Work request for sync is scheduled")
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                SeedDatabaseWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }
 */