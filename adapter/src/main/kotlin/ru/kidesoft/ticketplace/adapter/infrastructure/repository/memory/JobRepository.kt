package ru.kidesoft.ticketplace.adapter.infrastructure.repository.memory

import kotlinx.coroutines.Job
import ru.kidesoft.ticketplace.adapter.application.port.JobPort

class JobRepository: JobPort {
    val jobMap = mutableMapOf<String, Job>()

    override fun saveJob(key: String, job: Job) {
        jobMap[key] = job
    }

    override fun getJob(key: String): Job? = jobMap[key]

    override fun deleteJob(key: String) {
        jobMap.remove(key)
    }
}

