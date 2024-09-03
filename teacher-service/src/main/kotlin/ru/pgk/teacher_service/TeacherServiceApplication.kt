package ru.pgk.teacher_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.transaction.annotation.EnableTransactionManagement

@[SpringBootApplication EnableTransactionManagement EnableCaching EnableDiscoveryClient]
class TeacherServiceApplication

fun main(args: Array<String>) {
    runApplication<TeacherServiceApplication>(*args)
}
